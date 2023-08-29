package com.battleasya.Cmd;

import com.battleasya.Hdlr.Util;
import com.battleasya.BWExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class aBooster implements CommandExecutor {

    private final BWExtension plugin;

    public aBooster(BWExtension plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission("booster.admin")) {
            Util.sendMessage(sender, "&8(&4&l!&8) &6Unknown Command.");
            return true;
        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("modifier")) {

                try {
                    plugin.expModifier = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    Util.sendMessage(sender, "&cSyntax: /abooster modifier <amount>");
                    return true;
                }

                Util.sendMessage(sender, "&aChanged modifier to " + args[1] + ".");
                return true;

            }

        }

        Util.sendMessage(sender, "&cSyntax: /abooster modifier <amount>");
        return true;

    }

}