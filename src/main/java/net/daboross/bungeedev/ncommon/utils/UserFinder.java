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
package net.daboross.bungeedev.ncommon.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UserFinder {

    public static List<ProxiedPlayer> findUsers(String partialUser) {
        Collection<ProxiedPlayer> online = ProxyServer.getInstance().getPlayers();
        List<ProxiedPlayer> result = new ArrayList<>();
        partialUser = partialUser.toLowerCase();
        for (ProxiedPlayer p : online) {
            String name = p.getName().toLowerCase();
            if (name.equalsIgnoreCase(partialUser)) {
                result.clear();
                result.add(p);
                return result;
            } else if (name.contains(partialUser)) {
                result.add(p);
            } else {
                String display = ChatColor.stripColor(p.getDisplayName()).toLowerCase();
                if (display.contains(partialUser)) {
                    result.add(p);
                }
            }
        }
        return result;
    }
}
