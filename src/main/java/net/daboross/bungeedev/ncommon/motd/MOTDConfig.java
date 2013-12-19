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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import net.daboross.bungeedev.ncommon.NCommonPlugin;
import net.md_5.bungee.api.ChatColor;

public class MOTDConfig {

    private final FileUtils fileUtils;
    private final File configFile;
    private final List<String> config;

    public MOTDConfig(NCommonPlugin plugin) {
        this.fileUtils = new FileUtils(plugin);
        configFile = new File(new File(plugin.getDataFolder().getParentFile(), "MOTD"), "motd.txt");
        if (configFile.exists()) {
            config = fileUtils.readFile(configFile);
            for (int i = 0; i < config.size(); i++) {
                config.set(i, ChatColor.translateAlternateColorCodes('&', config.get(i)));
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "You have no {0}", configFile.getAbsolutePath());
            config = new ArrayList<>();
        }
    }

    public List<String> getConfig() {
        return Collections.unmodifiableList(config);
    }
}
