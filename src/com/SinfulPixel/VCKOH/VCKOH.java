package com.SinfulPixel.VCKOH;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.SinfulPixel.VCKOH.Tasks.GameManager;
import com.SinfulPixel.VCKOH.Tasks.Tasks;

/**
 * Created by Min3 on 7/21/2014.
 */
public class VCKOH extends JavaPlugin {
    Tasks t = new Tasks(this);
    GameManager gm = new GameManager(this);
    int i = 0;
    public void onEnable(){
    	try {
		      saveConfig();
		      setupConfig(getConfig());
		      saveConfig(); 
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
    	GameManager.cacheTimes();
	    GameManager.cacheLoc();
        getServer().getPluginManager().registerEvents(t,this);
        i = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable(){public void run() {GameManager.checkTime();}}, 0L, 60L);
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
                GameManager.checkTime();
            }
        }
        return false;
    }
    private void setupConfig(FileConfiguration config) throws IOException{
    	String[] times = {"00:00","04:00","08:00","12:00","16:00","20:00"};
    	String[] locs = {"world,-100,100,500","world,100,75,350","world,100,100,100"};
	    if (!new File(getDataFolder(), "RESET.FILE").exists()) {
	      new File(getDataFolder(), "RESET.FILE").createNewFile();
	      config.set("VCKOH.Creator","Min3CraftDud3");
	      config.set("VCKOH.WebSite","http://www.SinfulPixel.com");
	      config.set("VCKOH.Capture_Radius_Blocks", 15);
	      config.set("VCKOH.Capture_Time_Mins", 15);	
	      config.set("VCKOH.Game_TimeOut_Hrs", 1);
	      config.set("VCKOH.Times",times);
	      config.set("VCKOH.Locations",locs);
	      saveConfig();
	     }
	  }
}
