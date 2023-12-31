package com.battleasya.bwextension.datasource;

import com.battleasya.bwextension.BWExtension;
import com.battleasya.bwextension.handler.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class MySQL {

    private final BWExtension plugin;

    public MySQL(BWExtension plugin) {
        this.plugin = plugin;
    }

    private Connection con = null;

    public boolean connect(String host, String port, String database, String username, String password, String flags) {

        if (con != null) {
            return true;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + flags, username, password);
        } catch (ClassNotFoundException e) {
            plugin.getLogger().severe("[BW-Utility] Couldn't Find the MySQL Driver.");
            return false;
        } catch (SQLException e) {
            plugin.getLogger().severe("[BW-Utility] Failed to Connect to MySQL Database.");
            return false;
        }

        plugin.getLogger().info("[BW-Utility] Connected to MySQL Database.");
        return true;

    }

    public void disconnect() {

        if (con == null) {
            return;
        }

        try {
            con.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("[BW-Utility] Failed to Disconnect from MySQL Database.");
            return;
        }

        con = null;
        plugin.getLogger().info("[BW-Utility] Disconnected from MySQL Database.");

    }

    public void getLeaderboardAsync(CommandSender sender, String type) {

        (new BukkitRunnable() {
            @Override
            public void run() {

                String[][] leaderboard = new String[10][2];

                PreparedStatement pst = null;
                ResultSet rs = null;

                try {
                    pst = con.prepareStatement("SELECT NAME, " + type.toUpperCase()
                            + " FROM BedWars ORDER BY " + type.toUpperCase() + " DESC LIMIT 10");
                    rs = pst.executeQuery();
                    rs.beforeFirst();
                    int i = 0;
                    while (rs.next()) {
                        leaderboard[i][0] = rs.getString("NAME");
                        leaderboard[i][1] = rs.getString(type.toUpperCase());
                        i++;
                    }
                } catch (SQLException e) {
                    plugin.getLogger().severe("[BW-Utility] Cannot Pass getLeaderboardAsync Task.");
                } finally {
                    close(pst);
                    close(rs);
                }

                (new BukkitRunnable() {
                    @Override
                    public void run() {

                        Util.sendMessage(sender, "");
                        Util.sendMessage(sender, "&b&nLeaderboard (Top " + type + ")");
                        Util.sendMessage(sender, "");

                        for (int i = 0; i < 10; i++) {
                            if (leaderboard[i][0] != null) {
                                Util.sendMessage(sender, "&e" + (i + 1)
                                        + ". &f" + leaderboard[i][0] + " &7(" + leaderboard[i][1] + ")");
                            }
                        }

                        Util.sendMessage(sender, "");

                    }
                }).runTask(plugin);

            }
        }).runTaskAsynchronously(plugin);

    }

    public void getStatsAsync(CommandSender sender, String playerName) {

        (new BukkitRunnable() {
            @Override
            public void run() {

                String[] stats = new String[8];

                PreparedStatement pst = null;
                ResultSet rs = null;

                try {

                    pst = con.prepareStatement("SELECT KILLS, DEATHS, ROUND((KILLS/DEATHS),1) AS KDR, WINS, LOSES, FINAL_KILLS, FINAL_DEATHS, BEDS_DESTROYED FROM BedWars WHERE NAME = ?");
                    pst.setString(1, playerName);

                    rs = pst.executeQuery();
                    rs.beforeFirst();

                    while (rs.next()) {
                        stats[0] = rs.getString("KILLS");
                        stats[1] = rs.getString("DEATHS");
                        stats[2] = rs.getString("KDR");
                        stats[3] = rs.getString("WINS");
                        stats[4] = rs.getString("LOSES");
                        stats[5] = rs.getString("FINAL_KILLS");
                        stats[6] = rs.getString("FINAL_DEATHS");
                        stats[7] = rs.getString("BEDS_DESTROYED");
                    }

                } catch (SQLException e) {

                    plugin.getLogger().severe("[BW-Utility] Cannot Pass getStatsAsync Task.");

                } finally {

                    close(pst);
                    close(rs);

                }

                (new BukkitRunnable() {
                    @Override
                    public void run() {

                        Util.sendMessage(sender,"");
                        Util.sendMessage(sender,"&b&n" + playerName + "'s Stats");
                        Util.sendMessage(sender,"");

                        Util.sendMessage(sender, "&eKills: &f" + stats[0]);
                        Util.sendMessage(sender, "&eDeaths: &f" + stats[1]);
                        Util.sendMessage(sender, "&eKDR: &f" + stats[2]);
                        Util.sendMessage(sender, "&eWins: &f" + stats[3]);
                        Util.sendMessage(sender, "&eLoses: &f" + stats[4]);
                        Util.sendMessage(sender, "&eFinal Kills: &f" + stats[5]);
                        Util.sendMessage(sender, "&eFinal Deaths: &f" + stats[6]);
                        Util.sendMessage(sender, "&eBeds Destroyed: &f" + stats[7]);

                        Util.sendMessage(sender, "");

                    }
                }).runTask(plugin);

            }
        }).runTaskAsynchronously(plugin);

    }

    public void getRankingsAsync(CommandSender sender, String playerName) {

        (new BukkitRunnable() {
            @Override
            public void run() {

                String[] rank = new String[7];

                PreparedStatement pst = null;
                ResultSet rs = null;

                try {

                    pst = con.prepareStatement("SELECT DISTINCT (SELECT COUNT(*)+1 FROM BedWars WHERE KILLS > (SELECT KILLS FROM BedWars WHERE NAME = ?)) AS KILLS_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE DEATHS > (SELECT DEATHS FROM BedWars WHERE NAME = ?)) AS DEATHS_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE WINS > (SELECT WINS FROM BedWars WHERE NAME = ?)) AS WINS_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE LOSES > (SELECT LOSES FROM BedWars WHERE NAME = ?)) AS LOSES_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE FINAL_KILLS > (SELECT FINAL_KILLS FROM BedWars WHERE NAME = ?)) AS FINAL_KILLS_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE FINAL_DEATHS > (SELECT FINAL_DEATHS FROM BedWars WHERE NAME = ?)) AS FINAL_DEATHS_RANK, " +
                            "(SELECT COUNT(*)+1 FROM BedWars WHERE BEDS_DESTROYED > (SELECT BEDS_DESTROYED FROM BedWars WHERE NAME = ?)) AS BEDS_DESTROYED_RANK FROM BedWars");

                    pst.setString(1, playerName);
                    pst.setString(2, playerName);
                    pst.setString(3, playerName);
                    pst.setString(4, playerName);
                    pst.setString(5, playerName);
                    pst.setString(6, playerName);
                    pst.setString(7, playerName);

                    rs = pst.executeQuery();
                    rs.beforeFirst();

                    while (rs.next()) {
                        rank[0] = rs.getString("KILLS_RANK");
                        rank[1] = rs.getString("DEATHS_RANK");
                        rank[2] = rs.getString("WINS_RANK");
                        rank[3] = rs.getString("LOSES_RANK");
                        rank[4] = rs.getString("FINAL_KILLS_RANK");
                        rank[5] = rs.getString("FINAL_DEATHS_RANK");
                        rank[6] = rs.getString("BEDS_DESTROYED_RANK");
                    }

                } catch (SQLException e) {

                    plugin.getLogger().severe("[BW-Utility] Cannot Pass getRankingsAsync Task.");

                } finally {

                    close(pst);
                    close(rs);

                }

                (new BukkitRunnable() {
                    @Override
                    public void run() {

                        Util.sendMessage(sender, "");
                        Util.sendMessage(sender, "&b&n" + playerName + "'s Rankings");
                        Util.sendMessage(sender, "");

                        Util.sendMessage(sender, "&eKills: &f#" + rank[0]);
                        Util.sendMessage(sender, "&eDeaths: &f#" + rank[1]);
                        Util.sendMessage(sender, "&eWins: &f#" + rank[2]);
                        Util.sendMessage(sender, "&eLoses: &f#" + rank[3]);
                        Util.sendMessage(sender, "&eFinal Kills: &f#" + rank[4]);
                        Util.sendMessage(sender, "&eFinal Deaths: &f#" + rank[5]);
                        Util.sendMessage(sender, "&eBeds Destroyed: &f#" + rank[6]);

                        Util.sendMessage(sender, "");

                    }
                }).runTask(plugin);

            }
        }).runTaskAsynchronously(plugin);

    }

    private void close(Statement st) {

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                plugin.getLogger().severe("[BW-Utility] Failed to Close Statement.");
            }
        }

    }

    private void close(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                plugin.getLogger().severe("[BW-Utility] Failed to Close ResultSet.");
            }
        }

    }

}