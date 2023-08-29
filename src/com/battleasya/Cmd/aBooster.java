package com.battleasya.Cmd;

import com.battleasya.Util.General;
import com.battleasya.BWUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class aBooster implements CommandExecutor {

    private final BWUtility plugin;

    public aBooster(BWUtility plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission("booster.admin")) {
            General.sendMessage(sender, "&8(&4&l!&8) &6Unknown Command.");
            return true;
        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("modifier")) {

                try {
                    plugin.expModifier = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    General.sendMessage(sender, "&cSyntax: /abooster modifier <amount>");
                    return true;
                }

                General.sendMessage(sender, "&8(&4&l!&8) &6Changed modifier to " + args[1] + ".");
                return true;

            }

        }

        General.sendMessage(sender, "&cSyntax: /abooster modifier <amount>");
        return true;

    }

}