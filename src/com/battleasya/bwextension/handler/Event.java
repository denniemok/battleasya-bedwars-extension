package com.battleasya.bwextension.handler;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import com.alonsoaliaga.alonsolevels.api.events.ExperienceChangeEvent;
import com.battleasya.bwextension.BWExtension;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Event implements Listener {

    private final BWExtension plugin;

    public Event(BWExtension plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){

        String playerName = event.getPlayer().getName();

        plugin.cmdCDList.remove(playerName);
        plugin.expBuffer.remove(playerName);

    }

    @EventHandler
    public void onExpChange(ExperienceChangeEvent event) {

        if (!plugin.boosterInAction) {
            return;
        }

        if (event.getChangeType() == ExperienceChangeEvent.ChangeType.MODIFY) {

            Player p = event.getPlayer();
            String playerName = p.getName();

            int exp = (event.getNewExperience() - event.getOldExperience()) * (plugin.expModifier - 1);

            if (exp <= 0) {
                return;
            }

            if (plugin.expBuffer.containsKey(playerName)) {
                plugin.expBuffer.remove(playerName);
            } else {
                plugin.expBuffer.put(playerName, 1);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', " &8+&3" + exp + " Extra BedWars Experience! &b(Boosted by " + plugin.activatedBy + ")"));
                AlonsoLevelsAPI.addExperience(p.getUniqueId(), exp); // when add exp, it triggers another onExpChange event
            }

        }

    }

}
