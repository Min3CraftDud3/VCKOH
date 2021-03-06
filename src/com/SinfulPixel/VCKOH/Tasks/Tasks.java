package com.SinfulPixel.VCKOH.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.SinfulPixel.VCKOH.VCKOH;

/**
 * Created by Min3 on 7/22/2014.
 */
public class Tasks implements Listener {
    VCKOH plugin;
    public Tasks(VCKOH plugin){this.plugin = plugin;}
    public static HashMap<Boolean,Location> point = new HashMap<Boolean,Location>();
    public static ArrayList<Location> bl = new ArrayList<Location>();
    public static HashMap<String,Long> capper = new HashMap<String,Long>();
    static int fifteenMin =0;
    @EventHandler
    public void onObjClick(PlayerInteractEvent e){
    	if(GameManager.started){
        final Player p = e.getPlayer();
        Action a = e.getAction();
        if(a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.LEFT_CLICK_BLOCK)){
            Block b = e.getClickedBlock();
            if(bl.contains(b.getLocation())){
            	final Location ll = b.getLocation();
            	if(capper.isEmpty()){
            		if(GameManager.started)
            		//Bukkit.getScheduler().cancelTask(GameManager.onehrtimeout);
                    Bukkit.broadcastMessage(ChatColor.GOLD+p.getName()+" is king of the hill. Kill them!");
                    capper.put(p.getName(),System.currentTimeMillis());
                    
                    fifteenMin = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable(){
        				public void run() {
        					try{getNearbyEntities(ll,15);
        					Long time = (System.currentTimeMillis() - capper.get(p.getName()))/1000;
        					if(time == 60){
        						Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+p.getName()+" Has held the hill for 1 minute.");	
        					}
        					if(time == 300){
        						Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+p.getName()+" Has held the hill for 5 minutes.");
        					}
        					if(time == 600){
        						Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+p.getName()+" Has held the hill for 10 minutes.");
        					}
        					if(time == 900){
        						GameManager.started=false;
            					Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+p.getName()+" is King Of The Hill.");
            					regenPoint(point.get(true));
            					Bukkit.getScheduler().cancelTask(fifteenMin);
            					VCKOH.winner.put(p.getUniqueId(), p.getName());
        					}
        				}catch(Exception e1){}
        				}
                    }
        			, 0L,20L);
                    
            	}
            }
        }
        }
    	
    }
    @EventHandler
    public void onBeacon(InventoryOpenEvent e){
    	if(e.getInventory().getType().equals(InventoryType.BEACON)){
    		e.setCancelled(true);
    	}
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(GameManager.started){
        if(capper.containsKey(p.getName())){
        	Bukkit.getScheduler().cancelTask(fifteenMin);
            capper.remove(p.getName());
            Bukkit.broadcastMessage(ChatColor.GOLD+"The king has been killed, take control of the hill.");
            
        }
        }
    }
    public static void createPoint(Location l){
        int x = (int)l.getX();
        int y = (int)l.getY();
        int z = (int)l.getZ();
        World w = l.getWorld();
        w.getBlockAt(l).setType(Material.BEACON);
        //Location loc = new Location(w,x,y+1,z);
        bl.add(l);
        point.put(true,l.getBlock().getLocation());
        for(int x1=x-1;x1<=x+1;x1++){
            for(int z1=z-1;z1<=z+1;z1++){
                w.getBlockAt(x1,y-1,z1).setType(Material.IRON_BLOCK);
            }
        }
    }
    public static void regenPoint(Location l){
    	int x = (int)l.getX();
        int y = (int)l.getY();
        int z = (int)l.getZ();
        World w = l.getWorld();
        w.getBlockAt(l).setType(Material.AIR);
        point.remove(l.getBlock());
        bl.clear();
        for(int x1=x-1;x1<=x+1;x1++){
            for(int z1=z-1;z1<=z+1;z1++){
                w.getBlockAt(x1,y-1,z1).setType(Material.GRASS);
            }
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e){
    	if(e.getBlock().getType().equals(Material.IRON_BLOCK)||e.getBlock().getType().equals(Material.BEACON)){
    		if(!checkPoint(e.getBlock().getLocation())){
    			if(!e.getPlayer().isOp() || !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
    			e.setCancelled(true);
    			}
    		}
    	}
    }
    public static boolean checkPoint(Location l){
		int radius = 10;
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (l.getBlock().getRelative(x, y, z)
							.getType() == Material.BEACON) {
						return false;
						}
					}
				}
			}
		return true;
	}
	public static List<Entity> getNearbyEntities(Location where, int range) {
		List<Entity> found = new ArrayList<Entity>();
		for (Entity entity : where.getWorld().getEntities()) {
			if (!isInBorder(where, entity.getLocation(), range)) {
				found.add(entity);
				if(entity instanceof Player){
					Player p = (Player)entity;
					if(capper.containsKey(p.getName())){
						Bukkit.getScheduler().cancelTask(fifteenMin);
			            capper.remove(p.getName());
			            Bukkit.broadcastMessage(ChatColor.GOLD+"The king has left the hill, take control of the hill.");  
			        }
				}
			}
		}
		return found;
	}
	public static boolean isInBorder(Location center, Location notCenter, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
		int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();
		if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
			return false;
		}
		return true;
	}
}
