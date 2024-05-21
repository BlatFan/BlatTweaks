package ru.blatfan.blattweaks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import ru.blatfan.blatlibs.commandframework.Command;
import ru.blatfan.blatlibs.commandframework.CommandArguments;

public class TweaksCommands {
    
    @Command(
        name="blattweaks"
    )
    public void blattweaks(CommandArguments arg){
        PluginDescriptionFile dec = BlatTweaks.getInstance().getDescription();
        arg.sendMessage("");
        arg.sendMessage("");
        arg.sendMessage(ChatColor.GRAY+"---------------------"+BlatTweaks.getInstance().getPrefix().replace("[", "").replace("]", "")+ChatColor.GRAY+"--------------------");
        arg.sendMessage(ChatColor.GRAY+"Автор: "+ ChatColor.LIGHT_PURPLE +dec.getAuthors().get(0));
        arg.sendMessage(ChatColor.GRAY+"Версия: "+ ChatColor.LIGHT_PURPLE +dec.getVersion());
        arg.sendMessage(ChatColor.GRAY+"Версия API: "+ ChatColor.LIGHT_PURPLE +dec.getAPIVersion());
        arg.sendMessage(ChatColor.GRAY+"--------------------------------------------------");
        arg.sendMessage("");
        arg.sendMessage("");
    }
    
    @Command(
        name="blattweaks.reload"
    )
    public void blattweaksReload(CommandArguments arg){
        BlatTweaks.configsReload();
        
        arg.sendMessage(BlatTweaks.getInstance().getPrefix()+" §aПерезагруженно");
        BlatTweaks.getInstance().getConsole().info("Reloaded");
    }
}
