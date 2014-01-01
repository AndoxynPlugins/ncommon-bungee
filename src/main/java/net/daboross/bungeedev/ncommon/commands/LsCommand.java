/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
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

import java.util.Collection;
import net.daboross.bungeedev.ncommon.utils.NUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class LsCommand extends Command {

    private final Plugin plugin;

    public LsCommand(Plugin plugin) {
        super("ls");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Collection<ProxiedPlayer> onlinePlayers = plugin.getProxy().getPlayers();
        sender.sendMessage(ChatColor.GRAY + "total " + ChatColor.RED + onlinePlayers.size());
        if (onlinePlayers.isEmpty()) {
            return;
        }
        int maxServerLength = 0;
        int maxNameLength = 0;
        for (ProxiedPlayer player : onlinePlayers) {
            maxNameLength = Math.max(maxNameLength, player.getName().length());
            maxServerLength = Math.max(maxServerLength, player.getServer().getInfo().getName().length());
        }
        for (ProxiedPlayer player : onlinePlayers) {
            sender.sendMessage(getLine(player, maxNameLength + 2, maxServerLength + 2));
        }
    }

    private String getLine(ProxiedPlayer player, int maxNameLength, int maxServerLength) {
        String name = player.getName();
        String server = player.getServer().getInfo().getName();
        return ChatColor.WHITE + name + NUtils.spaces(maxNameLength - name.length())
                + ChatColor.YELLOW + server + NUtils.spaces(maxServerLength - server.length())
                + player.getDisplayName();
    }
}
