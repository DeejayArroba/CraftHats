package me.crafthats.listeners;

import me.crafthats.Main;
import me.crafthats.hats.HatPlayerManager;
import me.crafthats.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	private Main plugin = (Main) Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		plugin.updateNotice(e.getPlayer());
		HatPlayerManager.createHatPlayer(e.getPlayer());

		if (plugin.getConfig().getBoolean("give-hat-item")) {
			Player p = e.getPlayer();

			p.getInventory().setItem(plugin.getConfig().getInt("hat-item-slot") - 1, ItemStackUtil.getHatItem());
		}
	}


}
