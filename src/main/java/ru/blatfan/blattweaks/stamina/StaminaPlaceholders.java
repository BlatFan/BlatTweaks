package ru.blatfan.blattweaks.stamina;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StaminaPlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "stamina";
    }
    
    @Override
    public @NotNull String getAuthor() {
        return "BlatFan";
    }
    
    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }
    
    @Override
    public boolean persist() {
        return true;
    }
    
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("stamina")) {
            return "" + Math.round(BlatStamina.players.get(player.getPlayer()).stamina);
        }
        if(params.equalsIgnoreCase("stamina_max")) {
            return "" + Math.round(BlatStamina.players.get(player.getPlayer()).staminaMax);
        }
        if(params.equalsIgnoreCase("jump_depletion")) {
            return "" + BlatStamina.players.get(player.getPlayer()).jumpDepletion;
        }
        if(params.equalsIgnoreCase("recover_rate")) {
            return "" + BlatStamina.players.get(player.getPlayer()).recoverRate;
        }
        if(params.equalsIgnoreCase("tire_rate")) {
            return "" + BlatStamina.players.get(player.getPlayer()).tireRate;
        }
        return "";
    }
}