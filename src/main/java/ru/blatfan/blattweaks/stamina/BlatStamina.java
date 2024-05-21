package ru.blatfan.blattweaks.stamina;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.blatfan.blatlibs.config.BaseConfig;
import ru.blatfan.blattweaks.BlatTweaks;

import java.util.HashMap;
import java.util.Map;

public class BlatStamina {
    public static BaseConfig config;
    
    public static void setup(BlatTweaks plugin){
        config=new BaseConfig(plugin, "modules/stamina.yml");
        
        config.addDefault("start.stamina", (double) 20);
        config.addDefault("start.staminaMax", (double) 20);
        config.addDefault("start.wasteStamina", 0.1);
        config.copyDefault(true);
        config.save();
        
        plugin.getServer().getPluginManager().registerEvents(new StaminaEvents(config), plugin);
        new StaminaPlaceholders().register();
    }
}
