package ru.blatfan.blattweaks.stamina;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.blatfan.blatlibs.config.BaseConfig;

import java.util.HashMap;

public class StaminaEvents implements Listener {
    private final BaseConfig config;
    
    public StaminaEvents(BaseConfig config){
        BlatStamina.players=new HashMap<>();
        this.config=config;
    }
    
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        if(!config.contains("data."+event.getPlayer().getName())) {
            config.set("data."+event.getPlayer().getName()+".stamina", config.getDouble("start.stamina"));
            config.set("data."+event.getPlayer().getName()+".stamina_max", config.getDouble("start.staminaMax"));
            config.set("data."+event.getPlayer().getName()+".jump_depletion", config.getDouble("start.jump_depletion"));
            config.set("data."+event.getPlayer().getName()+".recover_rate", config.getDouble("start.recover_rate"));
            config.set("data."+event.getPlayer().getName()+".tire_rate", config.getDouble("start.tire_rate"));
            
            config.save();
        }
        BlatStamina.players.put(event.getPlayer(), new Stamina(
            event.getPlayer(),
            config.getDouble("data."+event.getPlayer().getName()+".stamina"),
            config.getDouble("data."+event.getPlayer().getName()+".stamina_max"),
            config.getDouble("data."+event.getPlayer().getName()+".jump_depletion"),
            config.getDouble("data."+event.getPlayer().getName()+".recover_rate"),
            config.getDouble("data."+event.getPlayer().getName()+".tire_rate")
        ));
    }
    
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        BlatStamina.players.remove(event.getPlayer());
    }
}
