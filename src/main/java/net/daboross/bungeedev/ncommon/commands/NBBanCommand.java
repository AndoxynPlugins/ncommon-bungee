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

import net.daboross.bungeedev.mysqlmap.api.MapTable;
import net.daboross.bungeedev.mysqlmap.api.ResultRunnable;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NBBanCommand extends Command {

    private final NCommonPlugin plugin;
    private final MapTable<String, String> banTable;

    public NBBanCommand(NCommonPlugin plugin) {
        super("nbban", "ncommon.nbban");
        this.plugin = plugin;
        banTable = plugin.getDatabase().getStringToStringTable("ncommon_nbban_reasons");
    }

    @Override
    public void execute(final CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /nbban <Player> <Reason>");
        }
        final String message = banMessage(sender instanceof ProxiedPlayer ? sender.getName() : "Server", args);
        final String name = args[0].toLowerCase();
        banTable.set(name, message, new ResultRunnable<Boolean>() {
            @Override
            public void runWithResult(Boolean value) {
                if (value) {
                    sender.sendMessage("Successfully banned " + name + " for " + message + ".");
                    ProxiedPlayer proxiedPlayer = plugin.getProxy().getPlayer(name);
                    if (proxiedPlayer != null) {
                        proxiedPlayer.disconnect(message);
                    }
                } else {
                    sender.sendMessage("Failed to ban " + name + ", error printed to console.");
                }
            }
        });
    }

    private String banMessage(String name, String[] args) {
        StringBuilder builder = new StringBuilder("[Banned by ").append(name).append("]");
        for (int i = 1; i < args.length; i++) {
            builder.append(" ").append(args[i]);
        }
        return ChatColor.translateAlternateColorCodes('&', builder.toString());
    }
}
