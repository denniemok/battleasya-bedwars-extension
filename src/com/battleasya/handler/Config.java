package com.battleasya.handler;

import com.battleasya.BWExtension;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private final BWExtension plugin;

    public Config(BWExtension plugin) {
        this.plugin = plugin;
    }

    public String host;
    public String port;
    public String database;
    public String username;
    public String password;
    public String flags;

    public void fetchConfig() {

        FileConfiguration config = plugin.getConfig();

        host = config.getString("host");
        port = config.getString("port");
        database = config.getString("database");
        username = config.getString("username");
        password = config.getString("password");
        flags = config.getString("flags");

    }

}
