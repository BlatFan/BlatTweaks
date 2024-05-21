package ru.blatfan.blattweaks.fishing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.blatfan.blatlibs.config.BaseConfig;
import ru.blatfan.blatlibs.util.ActionBar;

import java.util.*;

public class FishingEvents implements Listener {
    private final BaseConfig config;
    
    private Map<Player, Integer> fishers;
    private Map<Player, Location> fishHooks;
    
    public FishingEvents(BaseConfig config)
    {
        this.config=config;
        this.fishers=new HashMap<>();
        this.fishHooks=new HashMap<>();
    }
    
    @EventHandler
    public void playerFishing(PlayerFishEvent event){
        if(!event.isCancelled()&&event.getState()== PlayerFishEvent.State.CAUGHT_FISH) {
            if(fishers.getOrDefault(event.getPlayer(), 0)==0){
                fishers.put(event.getPlayer(), 1);
                fishHooks.put(event.getPlayer(), event.getHook().getLocation());
            }
            if(fishers.getOrDefault(event.getPlayer(), 0)<config.getInt("fishing_steps")) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void itemInteract(PlayerInteractEvent event){
        if(event.getItem().getType()== Material.FISHING_ROD && event.getAction()== Action.RIGHT_CLICK_AIR){
            config.load();
            int steps = config.getInt("fishing_steps");
            
            int i = fishers.getOrDefault(event.getPlayer(), 0);
            
            if(i==0) return;
            
            int chance = new Random().nextInt(100)+1;
            
            fishers.remove(event.getPlayer());
            
            List<Fish> fishes = new ArrayList<>();
            
            for (int id=0; id<BlatFishing.getFishes().size(); id++){
                if(BlatFishing.getFishes().get(id).chance> chance)
                    fishes.add(BlatFishing.getFishes().get(id));
            }
            Fish fish=fishes.get(new Random().nextInt(fishes.size()));
            
            if(i<steps){
                event.setCancelled(true);
                fishers.put(event.getPlayer(), i+1);
                if(config.getBoolean("more.enable"))ActionBar.send(event.getPlayer(), config.getString("more.text").replace("{PREFIX}", config.getString("prefix"))+(steps-i));
                Location hook = fishHooks.get(event.getPlayer());
                for(float x=-0.5f; x<0.5f; x+= 0.1F)
                    for(float z = -0.5f; z <0.5f; z+= 0.5F)
                        hook.getWorld().spawnParticle(Particle.WATER_BUBBLE, hook, 5);
            }
            if(i>=steps) {
                fishHooks.remove(event.getPlayer());
                event.getPlayer().getInventory().addItem(Objects.requireNonNull(fish.item));
                if(config.getBoolean("done.enable"))ActionBar.send(event.getPlayer(), config.getString("done.text").replace("{PREFIX}", config.getString("prefix")));
            }
        }
    }
    
    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        fishers.remove(event.getPlayer());
        fishHooks.remove(event.getPlayer());
    }
}
