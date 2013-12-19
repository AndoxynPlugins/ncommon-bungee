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

import net.daboross.bukkitdev.mysqlmap.SQLConnectionInfo;
import net.daboross.bukkitdev.mysqlmap.SQLDatabaseConnection;
import net.daboross.bukkitdev.mysqlmap.api.DatabaseConnection;
import net.daboross.bukkitdev.mysqlmap.api.MapTable;
import net.daboross.bukkitdev.mysqlmap.api.ResultRunnable;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.daboross.bungeedev.ncommon.config.SharedConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SQLMapTestCommand extends Command {

    private final MapTable<String, String> table;

    public SQLMapTestCommand(NCommonPlugin plugin) throws Exception {
        super("sqltest");
        SharedConfig c = plugin.getConfig();
        SQLConnectionInfo info = new SQLConnectionInfo(c.getString("sql.host"), c.getInt("sql.port"), c.getString("sql.database"), c.getString("sql.username"), c.getString("sql.password"));
        DatabaseConnection connection = new SQLDatabaseConnection(plugin, info);
        this.table = connection.getStringToStringTable("such_sql");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.GREEN + "Usage: /sqltest <set|get> <key> [value]");
        }
        switch (args[1]) {
            case "set":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/sqltest set <key> <value>");
                }
                table.set(args[1], args[2], new ResultRunnable<Boolean>() {
                    @Override
                    public void runWithResult(Boolean value) {
                        if (value) {
                            sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " to " + ChatColor.DARK_GREEN + args[2] + ChatColor.GREEN + ".");
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "Failed to set " + ChatColor.RED + args[1] + ChatColor.DARK_RED + " to " + ChatColor.RED + args[2] + ChatColor.DARK_RED + ".");
                        }
                    }
                });
                break;
            case "get":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/sqltest get <key>");
                }
                table.get(args[1], new ResultRunnable<String>() {
                    @Override
                    public void runWithResult(String value) {
                        sender.sendMessage(ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " is set to " + ChatColor.DARK_GRAY + value);
                    }
                });
                break;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/sqltest <set|get>");
        }
    }
}
