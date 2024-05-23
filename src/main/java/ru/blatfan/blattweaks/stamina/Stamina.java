package ru.blatfan.blattweaks.stamina;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ru.blatfan.blattweaks.BlatTweaks;


public class Stamina {
    public Player player;
    public double stamina;
    public double staminaMax;
    public double jumpDepletion;
    public double recoverRate;
    public double tireRate;
    
    private long lastUpdateTime = System.currentTimeMillis();
    
    public Stamina(Player player, double stamina, double staminaMax, double jumpDepletion,
                   double recoverRate, double tireRate){
        this.player=player;
        this.stamina=stamina;
        this.staminaMax=staminaMax;
        this.jumpDepletion=jumpDepletion;
        this.recoverRate=recoverRate;
        this.tireRate=tireRate;
    }
    
    void update() {
        if (System.currentTimeMillis() - this.lastUpdateTime >= 50L) {
            this.lastUpdateTime = System.currentTimeMillis();
            if (!this.player.isSprinting() || this.player.getGameMode() != GameMode.ADVENTURE && this.player.getGameMode() != GameMode.SURVIVAL) {
                this.stamina = this.bound(this.stamina + this.recoverRate);
            } else {
                this.stamina = this.bound(this.stamina - this.tireRate);
            }
        }
        
        if (this.stamina <= 0.0F) {
            this.player.setSprinting(false);
        }
    }
    
    void jumped() {
        this.stamina = this.bound(this.stamina - this.jumpDepletion);
    }
    
    private double bound(double i) {
        return Math.max(0.0F, Math.min(this.staminaMax, i));
    }
}
