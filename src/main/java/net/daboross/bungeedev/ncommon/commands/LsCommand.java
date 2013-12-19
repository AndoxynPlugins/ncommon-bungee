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
package net.daboross.bungeedev.ncommon.commands;

import java.util.Arrays;
import java.util.Collection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LsCommand extends Command {

    public LsCommand() {
        super("ls");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Collection<ProxiedPlayer> onlinePlayers = ProxyServer.getInstance().getPlayers();
        sender.sendMessage(ChatColor.GRAY + "total " + ChatColor.RED + onlinePlayers.size());
        if (onlinePlayers.isEmpty()) {
            return;
        }
        int maxServerLength = 0;
        int maxNameLength = 0;
        for (ProxiedPlayer player : onlinePlayers) {
            maxNameLength = Math.max(maxNameLength, (player == null ? "null" : player.getName()).length());
            maxServerLength = Math.max(maxServerLength, (player == null ? "null" : player.getServer().getInfo().getName()).length());
        }
        for (ProxiedPlayer player : onlinePlayers) {
            sender.sendMessage(getLine(player, maxNameLength + 2, maxServerLength + 2));
        }
    }

    private String getLine(ProxiedPlayer player, int maxNameLength, int maxServerLength) {
        String name = player == null ? "null" : player.getName();
        String server = player == null ? "null" : player.getServer().getInfo().getName();
        return ChatColor.WHITE + name + fillIn(maxNameLength - name.length())
                + ChatColor.YELLOW + server + fillIn(maxServerLength - server.length())
                + (player == null ? "null" : player.getDisplayName());
    }

    private String fillIn(int num) {
        char[] array = new char[num];
        Arrays.fill(array, ' ');
        return String.valueOf(array);
    }
}
