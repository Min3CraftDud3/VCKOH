package com.SinfulPixel.VCKOH.Tasks;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.SinfulPixel.VCKOH.VCKOH;

public class GUIManager implements Listener{
	VCKOH plugin;
	public static ArrayList<String> kitCmds = new ArrayList<String>();
	public static ArrayList<String> lkCmds = new ArrayList<String>();
	public GUIManager(VCKOH instance){
		plugin = instance;
	}
	public static Inventory classSelect = Bukkit.createInventory(null, 9, "VCKOH Prize Select");
	static {
		//Assault Setup
		ArrayList<String> lrArmor = new ArrayList<String>();
		lrArmor.add("Click to receive a random kit.");
		ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE,1);
		ItemMeta armorIM = armor.getItemMeta();
		armorIM.setDisplayName(ChatColor.RED+"Kit Prize");
		armorIM.setLore(lrArmor);
		armor.setItemMeta(armorIM);
		//Support Setup
		ArrayList<String> lrlootkey = new ArrayList<String>();
		lrlootkey.add("Click to receive Loot Keys");
		ItemStack lootkey = new ItemStack(Material.TRIPWIRE_HOOK,1);
		ItemMeta lootkeyIM = lootkey.getItemMeta();
		lootkeyIM.setDisplayName(ChatColor.RED+"Loot Key Prize");
		lootkeyIM.setLore(lrlootkey);
		lootkey.setItemMeta(lootkeyIM);
		//Engineer Class
		ArrayList<String> lritem = new ArrayList<String>();
		lritem.add("Click to receive a random items.");
		ItemStack item = new ItemStack(Material.PORTAL,1);
		ItemMeta itemIM = item.getItemMeta();
		itemIM.setDisplayName(ChatColor.RED+"Item Prize");
		itemIM.setLore(lritem);
		item.setItemMeta(itemIM);	
		
		//Menu
		classSelect.setItem(3, armor);
		classSelect.setItem(4, lootkey);
		classSelect.setItem(5, item);
		}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
	Player player = (Player) event.getWhoClicked(); 
	ItemStack clicked = event.getCurrentItem(); 
	Inventory inventory = event.getInventory(); 
	if (inventory.getName().equals(classSelect.getName())) {
		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.RED+"Kit Prize")) { 
			event.setCancelled(true); 
			player.closeInventory(); 
			//Run CMD
			runCmd("kit",player);
			VCKOH.winner.remove(player.getUniqueId());
		}else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.RED+"Loot Key Prize")) { 
			event.setCancelled(true);
			player.closeInventory(); 
			//Run CMD
			runCmd("key",player);
			VCKOH.winner.remove(player.getUniqueId());
		}else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.RED+"Item Prize")) { 
			event.setCancelled(true);
			player.closeInventory();
			GameManager.popChest(VCKOH.win);
			player.openInventory(VCKOH.win);
			VCKOH.winner.remove(player.getUniqueId());
		}
	}
	}
	public static void runCmd(String type, Player p){
		Random r = new Random();
		int len;
		if(type.equalsIgnoreCase("kit")){
			len = kitCmds.size();
			String cmd = kitCmds.get(r.nextInt(len));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", p.getName()));
			len = 0;
		}else if(type.equalsIgnoreCase("key")){
			len = lkCmds.size();
			String cmd = lkCmds.get(r.nextInt(len));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", p.getName()));
			len = 0;
		}
	}
}
