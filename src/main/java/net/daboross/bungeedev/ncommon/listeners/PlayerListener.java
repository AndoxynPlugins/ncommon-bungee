/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bungeedev.ncommon.listeners;

import net.daboross.bungeedev.ncommon.ColorList;
import net.daboross.bungeedev.ncommon.motd.MOTDConfig;
import net.daboross.bungeedev.ncommon.utils.ConnectorUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 *
 */
public class PlayerListener implements Listener {

    public static final String JOIN_FORMAT = ColorList.PREFIX_Q + "%s" + ChatColor.GRAY + " joined";
    public static final String LEAVE_FORMAT = ColorList.PREFIX_Q + "%s" + ChatColor.GRAY + " left";
    private final ProxyServer server;
    private final MOTDConfig motd;

    public PlayerListener(ProxyServer server, MOTDConfig motd) {
        this.server = server;
        this.motd = motd;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PostLoginEvent evt) {
        String message = String.format(JOIN_FORMAT, evt.getPlayer().getName());
        server.broadcast(message);
        ConnectorUtils.consoleMessage(message);
        ProxiedPlayer p = evt.getPlayer();
        for (String line : motd.getConfig()) {
            p.sendMessage(line);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerDisconnectEvent evt) {
        String message = String.format(LEAVE_FORMAT, evt.getPlayer().getName());
        server.broadcast(message);
        ConnectorUtils.consoleMessage(message);
    }
}
