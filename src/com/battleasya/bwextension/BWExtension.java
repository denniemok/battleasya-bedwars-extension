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
import java.util.HashSet;

public class BWExtension extends JavaPlugin {

    public MySQL ds;

    public Config config;

    public boolean boosterInAction;

    public int expModifier;

    public String activatedBy;

    public long timeStamp;

    public HashSet<String> expBuffer;

    public HashSet<String> consumedList;

    public HashMap<String, Long> cmdCDList;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        config = new Config(this);

        getConfiguration().fetchConfig();

        getCommand("bwstats").setExecutor(new Stats(this));
        getCommand("booster").setExecutor(new Booster(this));
        getCommand("abooster").setExecutor(new aBooster(this));
        getCommand("bwureload").setExecutor(new Reload(this));

        getServer().getPluginManager().registerEvents(new Event(this), this);

        ds = new MySQL(this);

        boolean connectMySQL = getDataSource().connect(getConfiguration().host
                , getConfiguration().port, getConfiguration().database
                , getConfiguration().username, getConfiguration().password
                , getConfiguration().flags);

        if (!connectMySQL) {
            getDataSource().disconnect();
            getServer().getPluginManager().disablePlugin(this);
        }

        boosterInAction = false;
        expModifier = 2;
        activatedBy = "";
        timeStamp = 0;

        expBuffer = new HashSet<>();
        consumedList = new HashSet<>();
        cmdCDList = new HashMap<>();

    }

    @Override
    public void onDisable() {
        getDataSource().disconnect();
    }

    public Config getConfiguration() {
        return config;
    }

    public MySQL getDataSource() {
        return ds;
    }

}
