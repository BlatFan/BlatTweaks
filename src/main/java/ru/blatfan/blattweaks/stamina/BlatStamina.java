package ru.blatfan.blattweaks.stamina;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.blatfan.blatlibs.config.BaseConfig;
import ru.blatfan.blattweaks.BlatTweaks;

import java.util.Map;
import java.util.Objects;

public class BlatStamina {
    public static BaseConfig config;
    public static Map<Player, Stamina> players;
    
    public static void setup(BlatTweaks plugin){
        config=new BaseConfig(plugin, "modules/stamina.yml");
        
        config.addDefault("start.stamina", (double) 20);
        config.addDefault("start.staminaMax", (double) 20);
        config.addDefault("start.jump_depletion", 1);
        config.addDefault("start.recover_rate", 0.1);
        config.addDefault("start.tire_rate", 0.1);
        config.copyDefault(true);
        config.save();
        
        plugin.getServer().getPluginManager().registerEvents(new StaminaEvents(config), plugin);
        new StaminaPlaceholders().register();
        
        update();
    }
    
    public static void update() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BlatTweaks.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach((player) -> {
                ((Stamina) Objects.requireNonNull(players.get(player))).update();
            });
        }, 20L, 1L);
    }
}
