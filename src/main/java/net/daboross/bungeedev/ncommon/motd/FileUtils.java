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
package net.daboross.bungeedev.ncommon.motd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.md_5.bungee.api.plugin.Plugin;

/**
 *
 */
public class FileUtils {

    private final Plugin plugin;

    public FileUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Reads a file into an array list of strings
     *
     * @param file this is the file to read from
     * @return The text in the file, or null if it doesn't exist
     */
    public List<String> readFile(File file) {
        ArrayList<String> lines = new ArrayList<String>();
        if (file.canRead()) {
            try (FileReader fr = new FileReader(file)) {
                try (BufferedReader bf = new BufferedReader(fr)) {
                    while (true) {
                        String line = bf.readLine();
                        if (line == null) {
                            break;
                        }
                        lines.add(line);
                    }
                }
            } catch (Exception ex) {
                plugin.getLogger().log(Level.WARNING, "[FileUtils] Exception reading file " + file.getAbsolutePath(), ex);
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "[FileUtils] No read access to file {0}", file.getAbsolutePath());
        }
        lines.trimToSize();
        return lines;
    }
}
