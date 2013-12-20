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

import net.daboross.bungeedev.ncommon.ColorList;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class NCommonReloadCommand extends Command {

    private final NCommonPlugin plugin;

    public NCommonReloadCommand(NCommonPlugin plugin) {
        super("nreload", "ncommon.reloadalias");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.getAliasConfig().reloadConfig();
        sender.sendMessage(ColorList.REG + "Aliases reloaded.");
        plugin.getMotd().reload();
        sender.sendMessage(ColorList.REG + "Motd reloaded.");
    }
}
