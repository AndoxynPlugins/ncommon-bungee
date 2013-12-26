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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class NUtils {

    public static final String SERVER_NAME = String.format("%s!%sServer", ChatColor.DARK_GRAY, ChatColor.DARK_RED);

    // *** Name utils ***
    public static String name(CommandSender sender) {
        return sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getDisplayName() : SERVER_NAME;
    }

    // *** String utils ***
    public static String firstLetterCaps(String input) {
        if (input.length() == 0) {
            return input;
        }
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String translateColor(String input) {
        char[] array = input.toCharArray();
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '&' && "0123456789abcdeflmo".indexOf(array[i + 1]) > -1) {
                array[i] = ChatColor.COLOR_CHAR;
                array[i + 1] = Character.toLowerCase(array[i + 1]);
            }
        }
        return String.valueOf(array);
    }

    public static String arrayToString(String[] array, String seperator) {
        if (array.length == 0) {
            return "";
        } else if (array.length == 1) {
            return array[0];
        } else {
            StringBuilder resultBuilder = new StringBuilder(array[0]);
            for (int i = 1; i < array.length; i++) {
                resultBuilder.append(seperator).append(array[i]);
            }
            return resultBuilder.toString();
        }
    }

    public static String spaces(int num) {
        return repeat(' ', num);
    }

    public static String repeat(char c, int num) {
        char[] array = new char[num];
        Arrays.fill(array, c);
        return String.valueOf(array);
    }

    public static String arrayToString(int start, String[] array, String seperator) {
        if (array.length == 0) {
            return "";
        } else if (start >= array.length) {
            return "";
        } else if (array.length - 1 == start) {
            return array[start];
        } else {
            StringBuilder resultBuilder = new StringBuilder(array[start]);
            for (int i = start + 1; i < array.length; i++) {
                resultBuilder.append(seperator).append(array[i]);
            }
            return resultBuilder.toString();
        }
    }
    // *** User finder ***

    public static List<ProxiedPlayer> findUsers(String partialUser) {
        partialUser = partialUser.toLowerCase();
        Collection<ProxiedPlayer> online = ProxyServer.getInstance().getPlayers();
        List<ProxiedPlayer> result = new ArrayList<>();
        for (ProxiedPlayer p : online) {
            String name = p.getName().toLowerCase();
            if (name.equals(partialUser)) {
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
