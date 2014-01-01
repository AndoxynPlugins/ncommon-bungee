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

import net.daboross.bungeedev.ncommon.ColorList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class WICommand extends Command {

    private final Plugin plugin;

    public WICommand(Plugin plugin) {
        super("wi");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorList.ERR + "Please specify a player");
            sender.sendMessage(ColorList.REG + "Usage: " + ColorList.CMD + "/wi" + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">");
            return;
        }
        ProxiedPlayer player = null;
        String lowerCaseArg = args[0].toLowerCase();
        for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
            if (p.getName().toLowerCase().contains(lowerCaseArg) || ChatColor.stripColor(p.getDisplayName()).toLowerCase().contains(lowerCaseArg)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            sender.sendMessage(ColorList.ERR + "Player " + ColorList.ERR_ARGS + args[0] + ColorList.ERR + " not found");
            sender.sendMessage(ColorList.REG + "Usage: " + ColorList.CMD + "/wi" + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">");
            return;
        }
        ServerInfo targetServer = player.getServer().getInfo();
        if (((sender instanceof ProxiedPlayer) && targetServer.equals(((ProxiedPlayer) sender).getServer().getInfo()))) {
            ((ProxiedPlayer) sender).chat("/wi " + player.getName());
        } else {
            sender.sendMessage(ColorList.NAME + player.getName() + ColorList.REG + ": " + ColorList.DATA + targetServer.getName());
        }
    }
}
