package me.crafthats.listeners;

import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class PlayerInteract implements Listener {

	Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.getItemInHand().getType() == Material.getMaterial(plugin.getConfig().getString("hat-item"))) {
			e.setCancelled(true);
			Inventory inventory = HatPlayerManager.getHatPlayer(player).getInventory();
			player.openInventory(inventory);
		}

	}

}
