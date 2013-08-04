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
package net.daboross.bungeedev.commoncommands;

import net.daboross.bungeedev.commoncommands.commands.ListCommand;
import net.daboross.bungeedev.commoncommands.commands.WCommand;
import net.daboross.bungeedev.commoncommands.commands.WICommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 *
 * @author daboross
 */
public final class CommonCommands extends Plugin {

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new ListCommand());
        pm.registerCommand(this, new WCommand());
        pm.registerCommand(this, new WICommand());
    }

    @Override
    public void onDisable() {
    }
}
