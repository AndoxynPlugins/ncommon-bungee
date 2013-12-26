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

import java.util.Collection;
import java.util.Iterator;
import net.daboross.bungeedev.ncommon.ColorList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class ListCommand extends Command {

    private final Plugin plugin;

    public ListCommand(Plugin plugin) {
        super("list");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Collection<ProxiedPlayer> onlinePlayers = plugin.getProxy().getPlayers();
        if (onlinePlayers.isEmpty()) {
            sender.sendMessage(ColorList.REG + "There are no players online.");
        } else if (onlinePlayers.size() == 1) {
            sender.sendMessage(ColorList.REG + "There is one player online:");
            sender.sendMessage(ColorList.NAME + onlinePlayers.iterator().next().getDisplayName());
        } else {
            sender.sendMessage(ColorList.TOP + "There are " + ColorList.DATA + onlinePlayers.size() + ColorList.TOP + " players online:");
            Iterator<ProxiedPlayer> iterator = onlinePlayers.iterator();
            StringBuilder messageBuilder = new StringBuilder(ColorList.NAME).append(iterator.next().getDisplayName());
            while (iterator.hasNext()) {
                ProxiedPlayer p = iterator.next();
                messageBuilder.append(ColorList.DIVIDER).append(", ").append(ColorList.NAME).append(p.getDisplayName());
            }
            sender.sendMessage(messageBuilder.toString());
        }
    }
}
