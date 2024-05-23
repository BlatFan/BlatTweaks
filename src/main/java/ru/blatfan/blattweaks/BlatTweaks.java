package ru.blatfan.blattweaks;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import ru.blatfan.blatlibs.BlatPlugin;
import ru.blatfan.blatlibs.config.BaseConfig;
import ru.blatfan.blattweaks.fishing.BlatFishing;
import ru.blatfan.blattweaks.stamina.BlatStamina;

public final class BlatTweaks extends BlatPlugin {
    @Getter
    private static BlatTweaks instance;
    private static BaseConfig modules;
    @Override
    public void onStartEnabling() {
        setPrefix(ChatColor.GRAY +"["+ChatColor.LIGHT_PURPLE+"BlatTweaks"+ChatColor.GRAY+"]"+ChatColor.RESET);
        setProjectID(116895);
        
        instance=this;
        
        configsReload();
        
        getCommandFramework().registerCommands(new TweaksCommands());
    }
    
    private static void modulesFile(){
        modules=new BaseConfig(instance, "modules.yml");
        
        modules.load();
        
        modules.addDefault("fishing", true);
        modules.addDefault("stamina", true);
        
        modules.copyDefault(true);
        modules.save();
    }
    
    public static void configsReload(){
        modulesFile();
        
        if(modules.getBoolean("fishing")) BlatFishing.setup(instance);
        if(modules.getBoolean("stamina")) BlatStamina.setup(instance);
    }
}
