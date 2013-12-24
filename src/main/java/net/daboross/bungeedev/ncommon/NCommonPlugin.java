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

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import lombok.Getter;
import net.daboross.bungeedev.mysqlmap.SQLConnectionInfo;
import net.daboross.bungeedev.mysqlmap.SQLDatabaseConnection;
import net.daboross.bungeedev.mysqlmap.api.DatabaseConnection;
import net.daboross.bungeedev.ncommon.commands.ConfigCommand;
import net.daboross.bungeedev.ncommon.commands.ListCommand;
import net.daboross.bungeedev.ncommon.commands.LsCommand;
import net.daboross.bungeedev.ncommon.commands.NBBanCommand;
import net.daboross.bungeedev.ncommon.commands.NCommonReloadCommand;
import net.daboross.bungeedev.ncommon.commands.SQLMapTestCommand;
import net.daboross.bungeedev.ncommon.commands.WCommand;
import net.daboross.bungeedev.ncommon.commands.WICommand;
import net.daboross.bungeedev.ncommon.config.AliasConfig;
import net.daboross.bungeedev.ncommon.config.SharedConfig;
import net.daboross.bungeedev.ncommon.listeners.NBBanListener;
import net.daboross.bungeedev.ncommon.listeners.PingListener;
import net.daboross.bungeedev.ncommon.listeners.PlayerListener;
import net.daboross.bungeedev.ncommon.motd.MOTDConfig;
import net.daboross.bungeedev.ncommon.utils.ConnectorUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class NCommonPlugin extends Plugin {

    @Getter
    private MOTDConfig motd;
    @Getter
    private SharedConfig config;
    @Getter
    private AliasConfig aliasConfig;
    @Getter
    private DatabaseConnection database;

    @Override
    public void onEnable() {
        try {
            config = new SharedConfig(this);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "SharedConfig", ex);
            return;
        }
        motd = new MOTDConfig(this);
        try {
            aliasConfig = new AliasConfig(this);
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Failed to load Aliases", ex);
        }
        PluginManager pm = getProxy().getPluginManager();
        getProxy().registerChannel("NCommon");
        String host = config.getString("sql.host", null);
        int port = config.getInt("sql.port", -1);
        String username = config.getString("sql.username", null);
        String password = config.getString("sql.password", null);
        String databaseName = config.getString("sql.database", null);
        if (host == null || username == null || password == null || databaseName == null || port == -1) {
            getLogger().log(Level.WARNING, "SQL Info incomplete");
        } else {
            SQLConnectionInfo info = new SQLConnectionInfo(host, port, databaseName, username, password);
            try {
                database = new SQLDatabaseConnection(this, info);
            } catch (SQLException ex) {
                getLogger().log(Level.WARNING, "Failed to connect to database", ex);
            }
        }
        pm.registerCommand(this, new ConfigCommand(config));
        pm.registerCommand(this, new ListCommand());
        pm.registerCommand(this, new WCommand());
        pm.registerCommand(this, new WICommand());
        pm.registerCommand(this, new LsCommand());
        pm.registerCommand(this, new NCommonReloadCommand(this));
        pm.registerListener(this, new PlayerListener(this));
        pm.registerListener(this, new PingListener(config));
        pm.registerListener(this, aliasConfig);
        pm.registerListener(this, new ConnectorUtils());
        if (database != null) {
            pm.registerCommand(this, new SQLMapTestCommand(this));
            pm.registerCommand(this, new NBBanCommand(this));
            pm.registerListener(this, new NBBanListener(this));
        }
    }

    @Override
    public void onDisable() {
        try {
            config.save();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "SharedConfig", ex);
        }
        if (database != null) {
            getLogger().log(Level.INFO, "Waiting for SQL to finish.");
            database.waitTillAllDone();
            getLogger().log(Level.INFO, "Done waiting, resuming shutdown.");
        }
    }
}
