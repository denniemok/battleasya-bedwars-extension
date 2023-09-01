package com.battleasya.bwextension;

import com.battleasya.bwextension.command.Reload;
import com.battleasya.bwextension.command.aBooster;
import com.battleasya.bwextension.handler.Config;
import com.battleasya.bwextension.datasource.MySQL;
import com.battleasya.bwextension.command.Booster;
import com.battleasya.bwextension.command.Stats;
import com.battleasya.bwextension.handler.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BWExtension extends JavaPlugin {

    public MySQL ds;

    public Config config;

    public boolean boosterInAction;

    public int expModifier;

    public String activatedBy;

    public long timeStamp;

    public HashMap<String, Integer> expBuffer;

    public HashMap<String, Integer> consumedList;

    public HashMap<String, Long> cmdCDList;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        config = new Config(this);
        config.fetchConfig();

        getCommand("bwstats").setExecutor(new Stats(this));
        getCommand("booster").setExecutor(new Booster(this));
        getCommand("abooster").setExecutor(new aBooster(this));
        getCommand("bwureload").setExecutor(new Reload(this));

        getServer().getPluginManager().registerEvents(new Event(this), this);

        ds = new MySQL(this);
        boolean connectMySQL = ds.connect(config.host, config.port
                , config.database, config.username, config.password, config.flags);

        if (!connectMySQL) {
            ds.disconnect();
            getServer().getPluginManager().disablePlugin(this);
        }

        boosterInAction = false;
        expModifier = 2;
        activatedBy = "";
        timeStamp = 0;

        expBuffer = new HashMap<>();
        consumedList = new HashMap<>();
        cmdCDList = new HashMap<>();

    }

    @Override
    public void onDisable() {
        ds.disconnect();
    }

}