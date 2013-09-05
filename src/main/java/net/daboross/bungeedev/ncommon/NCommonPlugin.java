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
package net.daboross.bungeedev.ncommon;

import net.daboross.bungeedev.ncommon.listeners.PlayerListener;
import net.daboross.bungeedev.ncommon.commands.ListCommand;
import net.daboross.bungeedev.ncommon.commands.LsCommand;
import net.daboross.bungeedev.ncommon.commands.WCommand;
import net.daboross.bungeedev.ncommon.commands.WICommand;
import net.daboross.bungeedev.ncommon.listeners.PluginMessagingStop;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 *
 * @author daboross
 */
public final class NCommonPlugin extends Plugin {

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        getProxy().registerChannel("NCommon");
        pm.registerCommand(this, new ListCommand());
        pm.registerCommand(this, new WCommand());
        pm.registerCommand(this, new WICommand());
        pm.registerCommand(this, new LsCommand());
        pm.registerListener(this, new PlayerListener());
        pm.registerListener(this, new PluginMessagingStop());
    }

    @Override
    public void onDisable() {
    }
}
