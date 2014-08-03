package com.SinfulPixel.VCKOH.Tasks;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.SinfulPixel.VCKOH.VCKOH;

public class GameManager {
	static VCKOH plugin;
	public static ArrayList<String> time = new ArrayList<String>();
	public static ArrayList<Location> locations = new ArrayList<Location>();
	static Boolean started = false;
	static Random rand = new Random();
	static int onehrtimeout = 0;
	
	public GameManager(VCKOH plugin){GameManager.plugin = plugin;}
	public static void cacheTimes(){
		System.out.println("Caching run times.");
		List<String> times = plugin.getConfig().getStringList("VCKOH.Times");
		for(String s:times){
			time.add(s);
			System.out.println("Caching: "+s);
		}
		System.out.println("Caching run times...Complete.");
	}
	public static void cacheLoc(){
		System.out.println("Caching locations.");
		List<String> locs = plugin.getConfig().getStringList("VCKOH.Locations");
		for(String s:locs){
			String[] t = s.split(",");
			System.out.println("Caching: "+t[0]+","+t[1]+","+t[2]+","+t[3]);
			locations.add(new Location(Bukkit.getWorld(t[0]),Double.parseDouble(t[1]),Double.parseDouble(t[2]),Double.parseDouble(t[3])));
		}
		System.out.println("Caching locations...Complete.");
	}
	public static void checkTime(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		if(time.contains(dateFormat.format(date).toString())&&started==false){
			int size = locations.size();
			Location l = locations.get(rand.nextInt(size));
			Tasks.createPoint(l);
			Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+"King Of The Hill Started at: X:"+l.getX()+" Y:"+l.getY()+" Z:"+l.getZ());
			started=true;
			onehrtimeout = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable(){
				public void run() {
					started=false;
					Tasks.regenPoint(Tasks.point.get(true));
					Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+"King Of The Hill Timed Out, Another One Will Begin Shortly.");
				}
			}, 7200L);
		}
	}
	public static void popChest(Inventory inv){
		try {
			inv.clear();
			Random rand = new Random();
			File itemFile = new File(plugin.getDataFolder() + File.separator+ "Items.yml");
			if (itemFile.exists()) {
				FileConfiguration ic = YamlConfiguration.loadConfiguration(itemFile);
				int chestRand = rand.nextInt(3)+1;
				int ri = rand.nextInt(ic.getInt("Items.Amount"));
				for (int j = 0; j < chestRand; j++) {
					ItemStack is = new ItemStack(Material.getMaterial(ic.getString("Items." + ri + ".Item")), 1);
					if (ic.contains("Items." +  ri + ".Enchantment")) {
						for (int k = 0; k < ic.getConfigurationSection("Items." +  ri+ ".Enchantment").getKeys(false).size(); k++) {
							is.addUnsafeEnchantment(Enchantment.getByName(ic.getString("Items."+ ri + ".Enchantment."+ k)), rand.nextInt(3) + 1);
						}
					}
					if (ic.contains("Items." + ri + ".Mob")) {
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ic.getString("Items." + ri + ".Mob").toUpperCase());
						is.setItemMeta(im);
					}
					inv.addItem(is);
				}
			}
		} catch (Exception i) {
		}
	}
	public static void createItemFile() throws IOException{
		File itemFile = new File(plugin.getDataFolder()+File.separator+"Items.yml");
		if(!itemFile.exists()){
			itemFile.createNewFile();
			System.out.println("[VCKOH]: Creating Item Config.....");
		    FileConfiguration fc = YamlConfiguration.loadConfiguration(itemFile);
		    fc.set("Items.Amount", 2);
		    fc.set("Items.0.Item", "DIAMOND_SWORD");
		    fc.set("Items.0.Enchantment.0", "FIRE_ASPECT");
		    fc.set("Items.1.Item", "DIAMOND_SWORD");
		    fc.set("Items.1.Enchantment.0", "DAMAGE_ALL");
		    fc.save(itemFile);
			System.out.println("[VCKOH]: Creating Item Config.....COMPLETE");
		}
	}
	public static void createCmds() throws IOException{
		List<String> defKit = new ArrayList<String>();
		defKit.add("kit tools %player%");
		defKit.add("kit dtools %player%");
		List<String> defKey = new ArrayList<String>();
		defKey.add("lootchest give %player% Legendary 5");
		defKey.add("lootchest give %player% Rare 10");
		File cmdFile = new File(plugin.getDataFolder()+File.separator+"Commands.yml");
		if(!cmdFile.exists()){
			cmdFile.createNewFile();
			System.out.println("[VCKOH]: Creating Command Config.....");
		    FileConfiguration fc = YamlConfiguration.loadConfiguration(cmdFile);
		    fc.set("Commands.Kit", defKit);
		    fc.set("Commands.Key", defKey);
		    fc.save(cmdFile);
		    System.out.println("[VCKOH]: Creating Command Config.....COMPLETE");
		}
	}
	public static void cacheCmds(){
		File cmdFile = new File(plugin.getDataFolder()+File.separator+"Commands.yml");
		if(cmdFile.exists()){
			System.out.println("[VCKOH] Caching Commands:");
		    FileConfiguration fc = YamlConfiguration.loadConfiguration(cmdFile);
			for(String s : fc.getStringList("Commands.Kit")){
				GUIManager.kitCmds.add(s);
				System.out.println("[VCKOH] Caching Commands: [KIT]"+s);
			}
			for(String s : fc.getStringList("Commands.Key")){
				GUIManager.lkCmds.add(s);
				System.out.println("[VCKOH] Caching Commands: [KEY]"+s);
			}
			System.out.println("[VCKOH] Caching Commands: DONE!");
		}
	}
}
