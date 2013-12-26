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

import static net.md_5.bungee.api.ChatColor.*;

public final class ColorList {

    /**
     * This is the main color.
     */
    public static final String REG = DARK_AQUA.toString();
    /**
     * This is the color used at the top of a list or info panel (usually use
     * this panel in the part that is explaining what the information is about).
     */
    public static final String TOP = DARK_GREEN.toString();
    /**
     * This is the color for player usernames.
     * <br>In 'john did this', 'john' would be this color.
     */
    public static final String NAME = GREEN.toString();
    /**
     * This is the color for general data information (times, dates, counts).
     * <br>'50 minutes ago' would be this color.
     */
    public static final String DATA = NAME;
    /**
     * This is the color for commands (in help messages).
     * <br>'/command' would be this color
     */
    public static final String CMD = GREEN.toString();
    /**
     * This is the color for arguments of a command (in help messages).
     * <br>in '/command sub [player] [page]', 'player' and 'page' would be this
     * color
     */
    public static final String ARGS = AQUA.toString();
    /**
     * This is the color for arguments of a command (in help messages).
     * <br>in '/command sub [player] [page]', '[' and ']' would be this color.
     */
    public static final String ARGS_SURROUNDER = DARK_AQUA.toString();
    /**
     * This is the color for messages saying that there is an error or the user
     * can't use the command.
     */
    public static final String ERR = DARK_RED.toString();
    /**
     * This is the color for the arguments that have caused an error, or are
     * illegal.
     */
    public static final String ERR_ARGS = RED.toString();
    /**
     * This is the color for the divider slash in various places.
     * <br>In 'player/nick', '/' would be this color.
     */
    public static final String DIVIDER = DARK_GRAY.toString();
    public static final String PREFIX_Q = DARK_GRAY + "[" + DARK_RED + "$" + DARK_GRAY + "] " + GRAY;
}
