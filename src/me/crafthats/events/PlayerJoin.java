package me.crafthats.events;

import me.crafthats.Main;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	private Main plugin = (Main) Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {

		plugin.updateNotice(e.getPlayer());
		HatPlayerManager.createHatPlayer(e.getPlayer());
		
	}
	
	
}
