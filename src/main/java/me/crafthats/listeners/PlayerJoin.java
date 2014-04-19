package me.crafthats.listeners;

import me.crafthats.Main;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoin implements Listener {

	private Main plugin = (Main) Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		plugin.updateNotice(e.getPlayer());
		HatPlayerManager.createHatPlayer(e.getPlayer());

		if (plugin.getConfig().getBoolean("give-hat-item")) {
			Player p = e.getPlayer();
			ItemStack hatItem = new ItemStack(Material.getMaterial(plugin.getConfig().getString("hat-item")), 1, (short) plugin.getConfig().getInt("hat-item-data-value"));
			ItemMeta hatItemMeta = hatItem.getItemMeta();
			hatItemMeta.setDisplayName(plugin.getConfig().getString("hat-item-name").replace('&', '§'));
			hatItem.setItemMeta(hatItemMeta);
			p.getInventory().setItem(plugin.getConfig().getInt("hat-item-slot") - 1, hatItem);
		}
	}


}
