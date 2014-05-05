package me.crafthats.listeners;

import me.crafthats.Main;
import me.crafthats.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

	private Main plugin = (Main) Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (!plugin.getConfig().getBoolean("can-drop-hat-item")) {
				if (e.getItemDrop().getItemStack().equals(ItemStackUtil.getHatItem())) {
					e.getItemDrop().remove();
					e.getPlayer().getInventory().setItem(plugin.getConfig().getInt("hat-item-slot") - 1, ItemStackUtil.getHatItem());
				}
			}
		}
	}

}
