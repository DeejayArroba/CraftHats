package me.crafthats.listeners;

import me.crafthats.hats.HatPlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerQuitEvent e) {

		HatPlayerManager.removeHatPlayer(e.getPlayer());
		
	}
	
	
}
