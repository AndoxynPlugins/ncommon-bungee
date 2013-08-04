/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bungeedev.commoncommands.commands;

import net.daboross.bungeedev.commoncommands.ColorList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author daboross
 */
public class WCommand extends Command {

    public WCommand() {
        super("w");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ColorList.ERR + "This command must be run by a player.");
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) sender;
        Server server = p.getServer();
        p.chat("/w " + server.getInfo().getName());
    }
}
