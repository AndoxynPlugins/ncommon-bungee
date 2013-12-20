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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.md_5.bungee.api.ChatColor;

public class MOTDConfig {

    private final NCommonPlugin plugin;
    private final List<String> data;
    private final File dataFile;

    public MOTDConfig(NCommonPlugin plugin) {
        this.plugin = plugin;
        dataFile = new File(plugin.getDataFolder(), "motd.txt");
        data = new ArrayList<>();
        load();
    }

    private void load() {
        if (dataFile.exists()) {
            List<String> tempData;
            try {
                tempData = Files.readAllLines(dataFile.toPath(), Charset.forName("UTF-8"));
            } catch (IOException ex) {
                plugin.getLogger().log(Level.WARNING, "Failed to read " + dataFile.getAbsolutePath(), ex);
                return;
            }
            for (String line : tempData) {
                data.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        } else {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "IOException creating new file", e);
            }
        }
    }

    public void reload() {
        data.clear();
        load();
    }

    public List<String> getData() {
        return Collections.unmodifiableList(data);
    }
}
