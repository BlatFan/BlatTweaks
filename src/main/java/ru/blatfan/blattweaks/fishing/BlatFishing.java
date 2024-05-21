package ru.blatfan.blattweaks.fishing;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.blatfan.blatlibs.config.BaseConfig;
import ru.blatfan.blattweaks.BlatTweaks;
import ru.blatfan.blattweaks.Symbols;

import java.util.HashMap;
import java.util.Map;

public class BlatFishing {
    public static BaseConfig config;
    
    @Getter
    private static Map<Integer, Fish> fishes;
    
    public static void setup(BlatTweaks plugin){
        fishes = new HashMap<>();
        
        config=new BaseConfig(plugin, "modules/fishing.yml");
        
        config.addDefault("fishing_steps", 25);
        config.addDefault("prefix", ChatColor.BOLD+""+ChatColor.AQUA+"Рыбалка"+ChatColor.RESET+" "+ Symbols.arrow_chat);
        config.addDefault("more.enable", true);
        config.addDefault("more.text", "{PREFIX} " + ChatColor.GREEN +"Ещё ");
        config.addDefault("done.enable", true);
        config.addDefault("done.text", "{PREFIX} " + "§a§lПоймал!");
        config.copyDefault(true);
        config.save();
        
        for(String fish : config.getStringList("fishes"))
        {
            ItemStack item = new ItemStack(Material.valueOf(config.getRawString(fish+".material")));
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(config.getInt(fish+".model_data"));
            meta.setDisplayName(config.getString(fish+".name"));
            meta.setLore(config.getStringList(fish+".lore"));
            item.setItemMeta(meta);
            int chance = config.getInt(fish+".chance");
            addFish(new Fish(item, chance));
        }
        
        plugin.getServer().getPluginManager().registerEvents(new FishingEvents(config), plugin);
    }
    
    public static void addFish(Fish fish){
        fishes.put(fishes.size(), fish);
    }
    
    public static Fish getFish(int id){
        if(id>=fishes.size()) return null;
        return fishes.get(id);
    }
}

