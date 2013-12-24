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
package net.daboross.bungeedev.ncommon.listeners;

import net.daboross.bungeedev.mysqlmap.api.MapTable;
import net.daboross.bungeedev.mysqlmap.api.ResultRunnable;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class NBBanListener implements Listener {

    private final NCommonPlugin plugin;
    private final MapTable<String, String> banTable;

    public NBBanListener(NCommonPlugin plugin) {
        this.plugin = plugin;
        banTable = plugin.getDatabase().getStringToStringTable("ncommon_nbban_reasons");
    }

    @EventHandler
    public void onPreLogin(final LoginEvent evt) {
        if (!evt.isCancelled()) {
            final String name = evt.getConnection().getName();
            evt.registerIntent(plugin);
            banTable.get(name, new ResultRunnable<String>() {
                @Override
                public void runWithResult(String value) {
                    if (value != null) {
                        evt.setCancelled(true);
                        evt.setCancelReason(value);
                    }
                    evt.completeIntent(plugin);
                }
            });
        }
    }
}
