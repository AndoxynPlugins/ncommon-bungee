package net.daboross.bungeedev.ncommon.listeners;

import java.net.InetSocketAddress;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.daboross.bungeedev.ncommon.config.SharedConfig;
import net.md_5.bungee.api.NewServerPing;
import net.md_5.bungee.api.NewServerPing.Players;
import net.md_5.bungee.api.NewServerPing.Protocol;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@RequiredArgsConstructor
public class MaintenancePing implements Listener {

    private final SharedConfig config;
    private final Random r = new Random();

    @EventHandler
    public void onLogin(LoginEvent evt) {
        if (config.getBoolean("ping.maintenance", false)) {
            evt.setCancelled(true);
            evt.setCancelReason(config.getString("maintenance.kick", ""));
        }
    }

    @EventHandler
    public void onPing(ProxyPingEvent evt) {
        NewServerPing newPing = evt.getNewResponse();
        ServerPing oldPing = evt.getResponse();
        String version;
        int protocolVersion;
        String motd;
        if (evt.isNewProtocol()) {
            version = newPing.getVersion().getName();
            protocolVersion = newPing.getVersion().getProtocol();
        } else {
            version = oldPing.getGameVersion();
            protocolVersion = oldPing.getProtocolVersion();
        }
        String[] descriptions;
        InetSocketAddress hostAddress = evt.getConnection().getVirtualHost();
        String host = hostAddress == null ? null : hostAddress.getHostString();
        if (host != null && !host.contains("nlmc")) {
            descriptions = config.getString("ping.notusingnlmc", "").split("\n");
        } else if (config.getBoolean("ping.maintenance", false)) {
            String[] versions = config.getString("ping.version", "1.7.2").split("\n");
            version = versions[r.nextInt(versions.length)];
            protocolVersion = Integer.MAX_VALUE;
            descriptions = config.getString("ping.ping2", "").split("\n");
        } else {
            descriptions = config.getString("ping.ping1", "").split("\n");
        }
        motd = descriptions[r.nextInt(descriptions.length)];
        if (evt.isNewProtocol()) {
            newPing.setDescription(motd);
            newPing.setVersion(new Protocol(version, protocolVersion));
            int online = newPing.getPlayers().getOnline();
            newPing.setPlayers(new Players(20 + online, online, config.getString("ping.players", "").split("\n")));
        } else {
            int online = ping.getCurrentPlayers();
            evt.setResponse(new ServerPing((byte) protocolVersion, version, motd, online, 20 + online));
        }
    }
}
