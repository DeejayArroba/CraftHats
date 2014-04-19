package me.crafthats.listeners;

import me.crafthats.MessageManager;
import me.crafthats.hats.Hat;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryClick implements Listener {

	MessageManager msg = MessageManager.getInstance();
	Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ItemStack itemStack = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		Inventory inventory = e.getInventory();

		HatPlayer hatPlayer = HatPlayerManager.getHatPlayer(player);

		if (hatPlayer == null)
			return;

		if (itemStack == null)
			return;

		if (itemStack.getType() == Material.AIR)
			return;

		if (inventory.getTitle().equals(hatPlayer.getInventory().getTitle())) {
			e.setCancelled(true);

			Hat hat = HatManager.getHat(itemStack);

			if (hat == null)
				return;

			if (plugin.getConfig().getBoolean("per-hat-permissions"))
				if (!player.hasPermission("crafthats.hat.*") && !player.isOp())
					if (hatPlayer.getPlayer().hasPermission("crafthats.hat." + hat.getName()))
						if (!player.hasPermission("crafthats.hat." + hat.getName())) {
							msg.bad(player, plugin.getConfig().getString("no-permission-message"));
							player.closeInventory();
							return;
						}

			if (hatPlayer.hasHat(hat)) {
				hatPlayer.use(hat);
			} else {
				hatPlayer.buy(hat);
			}
			return;
		}

		if (e.getSlotType() == SlotType.ARMOR) {
			Hat hat = HatManager.getHat(itemStack);
			if (hat != null) {
				if (hatPlayer.isWearingHat()) {
					e.setCancelled(true);
					msg.bad(player, plugin.getConfig().getString("reset-hat-message"));
				}
			}
		}

		if (e.getSlotType() == SlotType.QUICKBAR) {
			if (!plugin.getConfig().getBoolean("can-move-hat-item")) {
				if (e.getSlot() == plugin.getConfig().getInt("hat-item-slot") - 1) {
					e.setCancelled(true);
				}
			}
		}

	}
}
