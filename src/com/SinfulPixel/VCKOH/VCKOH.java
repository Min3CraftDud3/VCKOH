package com.SinfulPixel.VCKOH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
    public static String pre = ChatColor.GOLD+"["+ChatColor.LIGHT_PURPLE+"KOH"+ChatColor.GOLD+"]"+ChatColor.RESET;
    public static HashMap<UUID,String> winner = new HashMap<UUID,String>();
    public static Inventory win;
    public void onEnable(){
    	try {
    		  win = getServer().createInventory(null, 9, "KOH Prizes");
    		  GameManager.createItemFile();
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
    public void onDisable(){
    	if(!Tasks.point.isEmpty()){
    	Tasks.regenPoint(Tasks.point.get(true));
    	}
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
    	Player p = (Player)sender;
        if(label.equalsIgnoreCase("KOH")){
        	if(args.length==0){
        		p.sendMessage(pre+"Usage: /KOH <info/create/times>");
        	}else if(args.length==1){
        		if(args[0].equalsIgnoreCase("info")){
        			p.sendMessage(ChatColor.GOLD+"oOo ____ VenomCraft King of the Hill ____ oOo");
        			p.sendMessage(ChatColor.GRAY+"Author: Min3CraftDud3");
        			p.sendMessage(ChatColor.GRAY+"Website: http://www.SinfulPixel.com");
        			p.sendMessage(ChatColor.GRAY+"Version: Final release 1.3");
        		}else if(args[0].equalsIgnoreCase("create")){
        			if(p.hasPermission("VCKOH.Create")){
        			List<String> locs = new ArrayList<String>();
        			p.sendMessage(pre+"Point Created at your location.");
        			GameManager.locations.add(new Location(p.getWorld(),p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ()));
        			for(int i=0;i<GameManager.locations.size();i++){
        				Location l = GameManager.locations.get(i);
        				locs.add(l.getWorld().getName()+","+(int)l.getX()+","+(int)l.getY()+","+(int)l.getZ());
        			}
        			getConfig().set("VCKOH.Locations", locs);
        			saveConfig();
        			}else{p.sendMessage("Must have permission to use this command.");}
        		}else if(args[0].equalsIgnoreCase("times")){
        			String time = StringUtils.join(GameManager.time,'|');
        			p.sendMessage(pre+"Game Start Times: "+time);
        		}else if(args[0].equalsIgnoreCase("claim")){
        			if(winner.containsKey(p.getUniqueId())){
        				GameManager.popChest(win);
        				p.openInventory(win);
        				winner.remove(p.getUniqueId());
        			}
        		}else{
        			p.sendMessage(pre+"Usage: /KOH <info/create/times>");
        			}
        		}
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
