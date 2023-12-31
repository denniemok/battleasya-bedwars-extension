package com.battleasya.bwextension.command;

import com.battleasya.bwextension.BWExtension;
import com.battleasya.bwextension.handler.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Stats implements CommandExecutor {

    private final BWExtension plugin;

    public Stats(BWExtension plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (args.length == 0) {
            printHelp(sender);
            return true;
        }

        if (args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "stats":
                    printStats(sender, sender.getName());
                    break;
                case "rankings":
                    printRankings(sender, sender.getName());
                    break;
                case "top":
                    printLeaderboard(sender, "Wins");
                    break;
                default:
                    printHelp(sender);
                    break;
            }

            return true;

        }

        if (args.length == 2) {

            if (args[1].length() > 16) {
                printHelp(sender);
                return true;
            }

            if (args[0].equalsIgnoreCase("stats")) {
                if (sender.hasPermission("bwstats.stats.others")) {
                    printStats(sender, args[1]);
                } else {
                    Util.sendMessage(sender, "&8[&3Stats&8] &cPurchase Royale Membership To Unlock This Feature!");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("rankings")) {
                if (sender.hasPermission("bwstats.stats.others")) {
                    printRankings(sender, args[1]);
                } else {
                    Util.sendMessage(sender, "&8[&3Stats&8] &cPurchase Royale Membership To Unlock This Feature!");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("top")) {

                switch (args[1].toLowerCase()) {
                    case "wins":
                        printLeaderboard(sender, "Wins");
                        break;
                    case "loses":
                        printLeaderboard(sender, "Loses");
                        break;
                    case "kills":
                        printLeaderboard(sender, "Kills");
                        break;
                    case "deaths":
                        printLeaderboard(sender, "Deaths");
                        break;
                    case "beds-destroyed":
                        printLeaderboard(sender, "Beds_Destroyed");
                        break;
                    case "finalkills":
                        printLeaderboard(sender, "Final_Kills");
                        break;
                    case "finaldeaths":
                        printLeaderboard(sender, "Final_Deaths");
                        break;
                    default:
                        printHelp(sender);
                        break;
                }

                return true;

            }

        }

        printHelp(sender);
        return true;

    }

    public void printHelp(CommandSender sender) {
        Util.sendMessage(sender,"");
        Util.sendMessage(sender, "&8[&3Stats&8] &7Plugin made by &aKaytlynJay&7.");
        Util.sendMessage(sender, "&8[&3Stats&8] &c/stats [name]");
        Util.sendMessage(sender, "&8[&3Stats&8] &c/rankings [name]");
        Util.sendMessage(sender, "&8[&3Stats&8] &c/leaderboard [type]");
        Util.sendMessage(sender, "&8[&3Stats&8] &c[type]: wins, loses, kills, deaths");
        Util.sendMessage(sender, "&8[&3Stats&8] &c[type]: beds-destroyed, final-kills, final-deaths");
        Util.sendMessage(sender,"");
    }

    public void printLeaderboard(CommandSender sender, String type) {
        if (notInCooldown(sender)) {
            plugin.getDataSource().getLeaderboardAsync(sender, type);
        }
    }

    public void printStats(CommandSender sender, String playerName) {
        if (notInCooldown(sender)) {
            plugin.getDataSource().getStatsAsync(sender, playerName);
        }
    }

    public void printRankings(CommandSender sender, String playerName) {
        if (notInCooldown(sender)) {
            plugin.getDataSource().getRankingsAsync(sender, playerName);
        }
    }

    public boolean notInCooldown(CommandSender sender) {
        if(plugin.cmdCDList.containsKey(sender.getName())) {
            long secondsLeft = ((plugin.cmdCDList.get(sender.getName())/1000)+10) - (System.currentTimeMillis()/1000);
            if (secondsLeft > 0) {
                Util.sendMessage(sender,"&8[&3Stats&8] &cCooldown: " + secondsLeft + " seconds left.");
                return false;
            }
        }
        plugin.cmdCDList.put(sender.getName(), System.currentTimeMillis());
        return true;
    }

}
