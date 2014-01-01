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
package net.daboross.bungeedev.ncommon.config;

public class CommandAlias {

    private final String required;
    private final String replace;
    private final int requiredLength;

    public CommandAlias(String required, String replace) {
        this.required = "/" + required.trim().toLowerCase();
        this.requiredLength = this.required.length();
        this.replace = "/" + replace.trim();
    }

    public String getFullReplacement(String message) {
        if (message.toLowerCase().startsWith(required)) {
            return replace + message.substring(requiredLength);
        } else {
            return message;
        }
    }

    @Override
    public String toString() {
        return replace;
    }
}
