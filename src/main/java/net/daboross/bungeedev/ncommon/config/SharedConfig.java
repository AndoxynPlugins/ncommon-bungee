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
package net.daboross.bungeedev.ncommon.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SharedConfig {

    private final JSONObject config;
    public final File configFile;

    public SharedConfig(Plugin p) throws IOException {
        configFile = new File(p.getDataFolder(), "shared-config.json");
        if (!configFile.exists()) {
            config = new JSONObject();
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(configFile)) {
                config = new JSONObject(new JSONTokener(fileInputStream));
            }
        }
    }

    public void save() throws IOException {
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }
        if (!configFile.exists()) {
            configFile.createNewFile();
        }
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.forName("UTF-8"))) {
                config.write(writer, 2, 0);
                writer.flush();
            }
            fos.flush();
        }
    }

    public String getString(String key) {
        return config.optString(key);
    }

    public String getString(String key, String def) {
        return config.optString(key, def);
    }

    public int getInt(String key) {
        return config.optInt(key);
    }

    public int getInt(String key, int def) {
        return config.optInt(key, def);
    }

    public double getDouble(String key) {
        return config.optDouble(key);
    }

    public double getDouble(String key, double def) {
        return config.optDouble(key, def);
    }

    public boolean getBoolean(String key) {
        return config.optBoolean(key);
    }

    public boolean getBoolean(String key, boolean def) {
        return config.optBoolean(key, def);
    }

    public JSONArray getList(String key) {
        JSONArray array = config.optJSONArray(key);
        if (array == null) {
            array = new JSONArray();
            config.put(key, array);
        }
        return array;
    }

    public JSONObject getObject(String key) {
        JSONObject object = config.optJSONObject(key);
        if (object == null) {
            object = new JSONObject();
            config.put(key, object);
        }
        return object;
    }

    public void set(String key, String value) {
        config.put(key, ChatColor.translateAlternateColorCodes('&', value.replace("\\n", "\n")));
    }

    public JSONObject getConfig() {
        return config;
    }
}
