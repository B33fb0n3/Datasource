package de.b33fb0n3.pluginname.utils;

import de.b33fb0n3.pluginname.datasource.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Plugin made by B33fb0n3YT 26.12.2020 F*CKING SKIDDER! Licensed by B33fb0n3YT © All rights reserved
 */


public class MySQLConnectionPool {

    private MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();

    public MySQLConnectionPool(FileConfiguration config) {
        try {
            dataSource.setPoolName("Maria DB Pool");
            dataSource.setUrl(config.getString("Host"));
            dataSource.setUser(config.getString("Username"));
            dataSource.setDatabaseName(config.getString("Datenbank"));
            dataSource.setPort(config.getInt("Port"));
            dataSource.setPassword(Main.mysqlConfig.getString("Passwort"));
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.initialize();
        } catch (SQLException e) {
            Main.logger().log(Level.WARNING, "Could not establish database connection.", e);
        }
    }

    public void disconnect() {
        dataSource.close();
    }

    public MariaDbPoolDataSource getDataSource() {
        return dataSource;
    }
}
