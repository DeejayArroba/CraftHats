package me.crafthats.listeners;

import me.crafthats.MessageManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

	MessageManager msg = MessageManager.getInstance();

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			HatPlayer hatPlayer = HatPlayerManager.getHatPlayer(player);
			if(hatPlayer.isWearingHat()) {
				hatPlayer.resetHat();
				msg.info(player, "You took damage, so your hat has been reset!");
			}
		}

	}

}
