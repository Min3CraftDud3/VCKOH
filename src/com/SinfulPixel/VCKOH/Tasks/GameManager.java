package com.SinfulPixel.VCKOH.Tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
			System.out.println("Starting game.");
			int size = locations.size();
			Location l = locations.get(rand.nextInt(size));
			Tasks.createPoint(l);
			Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+"King Of The Hill Started at: X:"+l.getX()+" Y:"+l.getY()+" Z:"+l.getZ());
			started=true;
			onehrtimeout = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable(){
				public void run() {
					started=false;
					Bukkit.broadcastMessage(VCKOH.pre+ChatColor.GOLD+"King Of The Hill Timed Out, Another One Will Begin Shortly.");
				}
			}, 7200L);
		}
	}
}
