package com.SinfulPixel.VCKOH;

import com.SinfulPixel.VCKOH.Tasks.Tasks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Min3 on 7/21/2014.
 */
public class VCKOH extends JavaPlugin {
    Tasks t = new Tasks(this);
    public void onEnable(){
        getServer().getPluginManager().registerEvents(t,this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("test")){
            if(sender instanceof Player){
            	if(args.length==0){
                Tasks.createPoint(((Player) sender).getLocation());
                sender.sendMessage("Creating Point, Check it out!");
            	}else if(args.length==1){
                	Tasks.regenPoint(Tasks.point.get(true));
                	sender.sendMessage("Regenning.");
                }
            }else{
                sender.sendMessage("Must be a player to test.");
            }
        }
        return false;
    }
}
