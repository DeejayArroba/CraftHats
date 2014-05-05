package me.crafthats.listeners;

import me.crafthats.utils.MessageManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDamage implements Listener {

	MessageManager msg = MessageManager.getInstance();
	Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			HatPlayer hatPlayer = HatPlayerManager.getHatPlayer(player);
			if(hatPlayer.isWearingHat()) {
				hatPlayer.resetHat();
				msg.info(player, plugin.getConfig().getString("damage-message"));
			}
		}

	}

}
