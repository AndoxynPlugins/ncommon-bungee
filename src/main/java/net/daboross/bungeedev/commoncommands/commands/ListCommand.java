/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bungeedev.commoncommands.commands;

import java.util.Collection;
import java.util.Iterator;
import net.daboross.bungeedev.commoncommands.ColorList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author daboross
 */
public class ListCommand extends Command {

    public ListCommand() {
        super("list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Collection<ProxiedPlayer> onlinePlayers = ProxyServer.getInstance().getPlayers();
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
