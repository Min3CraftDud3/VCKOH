package com.SinfulPixel.VCKOH.Tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.SinfulPixel.VCKOH.VCKOH;

public class GameManager {
	static VCKOH plugin;
	static ArrayList<String> time = new ArrayList<String>();
	static ArrayList<Location> locations = new ArrayList<Location>();
	
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
			System.out.println("Caching: "+t);
			locations.add(new Location(Bukkit.getWorld(t[0]),Double.parseDouble(t[1]),Double.parseDouble(t[2]),Double.parseDouble(t[3])));
		}
		System.out.println("Caching locations...Complete.");
	}
}
