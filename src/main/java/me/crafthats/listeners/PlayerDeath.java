package me.crafthats.listeners;

import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import me.crafthats.utils.ItemStackUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();

		HatPlayer hatPlayer = HatPlayerManager.getHatPlayer(player);
		for (ItemStack item : e.getDrops()) {
			if (item.equals(ItemStackUtil.getHatItem())) {
				e.getDrops().remove(item);
				break;
			}
		}

		for (ItemStack item : e.getDrops()) {
			if (hatPlayer.isWearingHat()) {
				e.getDrops().remove(player.getInventory().getHelmet());
				hatPlayer.resetHat();
				break;
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		ItemStackUtil.giveHatItem(e.getPlayer());
	}

}
