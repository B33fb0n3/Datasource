package de.b33fb0n3.pluginname.commands;
packade com.company.product.

import de.b33fb0n3.pluginname.datasource.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class CommandVorlage implements CommandExecutor {

    private final DataSource source;

    public CommandVorlage(DataSource source) {
        this.source = source;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try (Connection conn = source.getConnection();
             PreparedStatement stmt = conn.prepareStatement("Select * from some_table where something = something")) {
            stmt.setString(1, "something");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                resultSet.getString("user");
            }
        } catch (SQLException e) {
            Main.logger().log(Level.WARNING, "Could not retrieve data from db", e);
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("[PERMISSION]")) {
                if (args.length == 0) {

                } else {
                    p.sendMessage(Main.Prefix + "§cBenutze: §e/[COMMAND]");
                }
            } else {
                p.sendMessage(Main.Prefix + "No PErm");
            }
        }

        return false;
    }

}

