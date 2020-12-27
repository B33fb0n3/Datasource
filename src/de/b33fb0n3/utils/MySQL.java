package de.b33fb0n3.utils;

import de.b33fb0n3.datasource.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Plugin made by B33fb0n3YT
 * 26.12.2020
 * F*CKING SKIDDER!
 * Licensed by B33fb0n3YT
 * © All rights reserved
 */


public class MySQL {

    private String host;
    private String port;
    private String datenbank;
    private String username;
    private String passwort;
    private static Connection con;

    public MySQL() {
        this.host = Main.mysqlConfig.getString("Host");
        this.port = Main.mysqlConfig.getString("Port");
        this.datenbank = Main.mysqlConfig.getString("Datenbank");
        this.username = Main.mysqlConfig.getString("Username");
        this.passwort = Main.mysqlConfig.getString("Passwort");
    }

    public void connect() {
        if (!this.isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.datenbank, this.username, this.passwort);
                Bukkit.getConsoleSender().sendMessage(Main.Prefix + "MySQL Verbindung hergestellt!");
            } catch (SQLException e) {
                Main.error(e);
            }
        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage(Main.Prefix + "MySQL Verbindung getrennt!");
            } catch (SQLException e) {
                Main.error(e);
            }
        }
    }

    public boolean isConnected() {
        return (con == null ? false : true);
    }

    public static Connection getCon() {
        return con;
    }
}
