package net.daboross.bungeedev.ncommon.listeners;

import lombok.RequiredArgsConstructor;
import net.daboross.bungeedev.ncommon.config.SharedConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.ServerPing.*;

@RequiredArgsConstructor
public class MaintenancePing {

    private static final String KICK = ChatColor.translateAlternateColorCodes('&', "&4N&5L&4M&5C&e is down for maintenance.\n&eWe'll be back soon!");
    private static final String DEF_PING1 = ChatColor.translateAlternateColorCodes('&', "&2 < &4N&5L&4M&5C&2 >");
    private static final String DEF_PING2 = ChatColor.translateAlternateColorCodes('&', "&2 < &4N&5L&4M&5C&2 | &4Check &cwww.nlmc.pw&4 for more info. &2 >");

    private final SharedConfig config;
    private int nextPing;

    @EventHandler
    public void onLogin(LoginEvent evt) {
        evt.setCancelled(true);
        evt.setCancelReason(KICK);
    }

    @EventHandler
    public void onPing(ProxyPingEvent evt) {
        ServerPing ping = evt.getResponse();
        ping.setPlayers(new Players(55, 55, getPlayers(config.getString("ping.players", "Testing this\nPlayer List\nIs fun").split("\n"))));
        String[] pings;
        if (config.getBoolean("ping.maintenance", false)) {
            ping.setVersion(new Protocol(config.getString("ping.version", "1.7.5"), Integer.MAX_VALUE));
            pings = config.getString("ping.ping2", DEF_PING2).split("\n");
        } else {
            pings = config.getString("ping.ping1", DEF_PING1).split("\n");
        }
        if (nextPing > pings.length) {
            nextPing = 0;
        }
        ping.setDescription(pings[nextPing]);
    }

    private PlayerInfo[] getPlayers(String... names) {
        PlayerInfo[] result = new PlayerInfo[names.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new PlayerInfo(names[i], null);
        }
        return result;
    }
}
