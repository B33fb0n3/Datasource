package de.b33fb0n3.commands;

import de.b33fb0n3.datasource.Main;
import de.b33fb0n3.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVorlage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("[PERMISSION]")) {
                if(args.length == 0) {

                } else
                    p.sendMessage(Main.Prefix + "§cBenutze: §e/[COMMAND]");
            } else
                p.sendMessage(Main.Prefix + MessageManager.NOPERMISSION);
        }

        return false;
    }

}

