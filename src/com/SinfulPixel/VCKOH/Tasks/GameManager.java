package com.SinfulPixel.VCKOH.Tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.SinfulPixel.VCKOH.VCKOH;

public class GameManager {
	static VCKOH plugin;
	static ArrayList<String> time = new ArrayList<String>();
	static ArrayList<Location> locations = new ArrayList<Location>();
	
	public GameManager(VCKOH plugin){GameManager.plugin = plugin;}
	/*public static void cacheTimes(){
		System.out.println("Caching run times.");
		for(int i=0;i<500;i++){
			if(plugin.getConfig().contains("Times."+i)){
				time.add(plugin.getConfig().getString("Times."+i));	
				System.out.println("Caching: "+plugin.getConfig().getString("Times"+i));
			}
		}
		System.out.println("Caching run times...Complete.");
	}
	public static void cacheLoc(){
		System.out.println("Caching locations.");
		for(int i=0;i<500;i++){
			if (plugin.getConfig().contains("Locations"+i)){
				World w = Bukkit.getWorld("Locations"+i+".World");
				double x = plugin.getConfig().getDouble("Locations"+i+".X");
				double y = plugin.getConfig().getDouble("Locations"+i+".Y");
				double z = plugin.getConfig().getDouble("Locations"+i+".Z");
				locations.add(new Location(w,x,y,z));
				System.out.println("Caching: "+w+x+y+z);
				}
			}
		System.out.println("Caching locations...Complete.");
	}*/
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
	public static void checkTime(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		if(time.contains(dateFormat.format(date).toString())){
			System.out.println("Starting game.");
		}
	}
}
