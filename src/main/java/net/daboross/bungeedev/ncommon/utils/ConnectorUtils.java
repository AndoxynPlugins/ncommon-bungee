/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bungeedev.ncommon.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;

/**
 *
 * @author daboross
 */
public class ConnectorUtils {

    public static void setDisplayName(Server server, String name) {
        if (server == null) {
            throw new IllegalArgumentException("server == null");
        }
        sendMessageServer(server, "SetDisplayName", name);
    }

    public static void consoleMessage(String message) {
        sendMessage("ConsoleMessage", message);
    }

    public static void sendWithPermission(String permission, String message, String... excludes) {
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

    private static void sendMessage(String... message) {
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
        for (Map.Entry<String, ServerInfo> server : ProxyServer.getInstance().getServers().entrySet()) {
            server.getValue().sendData("NCommon", data);
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
