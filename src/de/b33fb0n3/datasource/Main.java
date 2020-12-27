package de.b33fb0n3.datasource;

import de.b33fb0n3.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main plugin;
    public static String Prefix = "§8┃ §aPrefix §8»";
    public ConsoleCommandSender console = getServer().getConsoleSender();
    private static File mysqlFile = new File("plugins/MySQL", "MySQL.yml");
    public static FileConfiguration mysqlConfig = YamlConfiguration.loadConfiguration(mysqlFile);
    private MySQL mySQL;

    @Override
    public void onEnable() {
        plugin = this;

        console.sendMessage(Main.Prefix + "§e[]=======================[]");
        console.sendMessage(Main.Prefix + "						 ");
        console.sendMessage(Main.Prefix + "§2Coded by: §dB33fb0n3YT/1BlauNitrox");
        loadConfig();
        mySQL = new MySQL();
        mySQL.connect();
        console.sendMessage(Main.Prefix + "§aPlugin wurde aktiviert!");
        console.sendMessage(Main.Prefix + "						 ");
        console.sendMessage(Main.Prefix + "§e[]=======================[]");
        init();
    }

    private void init() {
        initCommands();
        initListener();

//        try { // für mögliche Datenbank Erstellungen
//            PreparedStatement ps0 = MySQL.getCon().prepareStatement("CREATE TABLE IF NOT EXISTS coins (UUID VARCHAR(100), name VARCHAR(100), coin INTEGER(255))");
//            ps0.executeUpdate();
//        } catch (SQLException e) {
//            error(e);
//        }
    }

    private void initListener() {
        PluginManager pm = Bukkit.getPluginManager();
//        pm.registerEvents(new X(), this);
    }

    private void initCommands() {
//        getCommand("X").setExecutor(new X());
    }

    @Override
    public void onDisable() {
        console.sendMessage(Main.Prefix+ "§e[]=======================[]");
        console.sendMessage(Main.Prefix + "						 ");
        console.sendMessage(Main.Prefix+ "§2Coded by: §dB33fb0n3YT/1BlauNitrox");
        mySQL.disconnect();
        console.sendMessage(Main.Prefix + "§cPlugin wurde deaktiviert!");
        console.sendMessage(Main.Prefix+ "						 ");
        console.sendMessage(Main.Prefix+ "§e[]=======================[]");
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static void error(Exception e) {
        Main.getPlugin().console.sendMessage(Main.Prefix+ "§cFEHLER: §b" + e.getLocalizedMessage());
        for(int i = 0; i < e.getStackTrace().length; i++) {
            if(e.getStackTrace()[i].toString().contains("de.b33fb0n3")) {
                Main.getPlugin().console.sendMessage("§b"+ e.getStackTrace()[i]);
            }
        }
    }

    private void loadConfig() {
        mysqlConfig.options().header("Trage hier deine MySQL Daten ein! Diese Config gilt für alle Plugins von Tommunity");
        mysqlConfig.addDefault("Host", "localhost");
        mysqlConfig.addDefault("Port", 3306);
        mysqlConfig.addDefault("Datenbank", "DEINEDATENBANK");
        mysqlConfig.addDefault("Username", "DEINUSERNAME");
        mysqlConfig.addDefault("Passwort", "PASSWORT1234");
        mysqlConfig.options().copyDefaults(true);
        try {
            mysqlConfig.save(mysqlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
