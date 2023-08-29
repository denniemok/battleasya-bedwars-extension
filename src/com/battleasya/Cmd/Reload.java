package com.battleasya.Cmd;

import com.battleasya.Util.General;
import com.battleasya.BWExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {
    private final BWExtension plugin;

    public Reload(BWExtension plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!sender.hasPermission("bwutility.reload")) {
            General.sendMessage(sender, "&8(&4&l!&8) &6Unknown Command.");
            return true;
        }

        if (args.length == 0) {
            plugin.reloadConfig();
            plugin.config.fetchConfig();
            return true;
        }

        General.sendMessage(sender, "&cSyntax: /bwureload");
        return true;

    }

}
