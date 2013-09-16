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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;

/**
 *
 */
public class ConnectorUtils {

    public static void setDisplayName(@NonNull Server server, @NonNull String name) {
        sendMessageServer(server, "SetDisplayName", name);
    }

    public static void consoleMessage(@NonNull String message) {
        sendMessage(false, "ConsoleMessage", message);
    }

    public static void sendWithPermission(@NonNull String permission, @NonNull String message, @NonNull String... excludes) {
        byte[] data;
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(b)) {
                out.writeUTF("SendWithPermission");
                out.writeUTF(permission);
                out.writeUTF(message);
                out.writeInt(excludes.length);
                for (String str : excludes) {
                    out.writeUTF(str);
                }
            }
            data = b.toByteArray();
        } catch (IOException ex) {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Error writing data to byte array", ex);
            return;
        }
        for (Map.Entry<String, ServerInfo> server : ProxyServer.getInstance().getServers().entrySet()) {
            if (!server.getValue().getPlayers().isEmpty()) {
                server.getValue().sendData("NCommon", data);
            }
        }
    }

    private static void sendMessage(boolean sendToNoPlayers, String... message) {
        byte[] data;
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(b)) {
                for (String str : message) {
                    out.writeUTF(str);
                }
            }
            data = b.toByteArray();
        } catch (IOException ex) {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Error writing data to byte array", ex);
            return;
        }
        if (sendToNoPlayers) {
            for (Map.Entry<String, ServerInfo> server : ProxyServer.getInstance().getServers().entrySet()) {
                server.getValue().sendData("NCommon", data);
            }
        } else {
            for (Map.Entry<String, ServerInfo> server : ProxyServer.getInstance().getServers().entrySet()) {
                if (!server.getValue().getPlayers().isEmpty()) {
                    server.getValue().sendData("NCommon", data);
                }
            }
        }
    }

    private static void sendMessageServer(Server server, String... message) {
        byte[] data;
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(b)) {
                for (String str : message) {
                    out.writeUTF(str);
                }
            }
            data = b.toByteArray();
        } catch (IOException ex) {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Error writing data to byte array", ex);
            return;
        }
        server.sendData("NCommon", data);
    }
}
