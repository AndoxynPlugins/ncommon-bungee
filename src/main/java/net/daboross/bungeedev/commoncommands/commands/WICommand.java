/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bungeedev.commoncommands.commands;

import net.daboross.bungeedev.commoncommands.ColorList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author daboross
 */
public class WICommand extends Command {
    
    public WICommand() {
        super("wi");
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
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
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
        Server targetServer = player.getServer();
        if (((sender instanceof ProxiedPlayer) && targetServer.equals(((ProxiedPlayer) sender).getServer()))) {
            ((ProxiedPlayer) sender).chat("/wi " + player.getName());
        } else {
            sender.sendMessage(ColorList.REG + "Server: " + targetServer.getInfo().getName());
        }
    }
}
