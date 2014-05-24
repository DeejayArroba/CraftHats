package me.crafthats.listeners;

import me.crafthats.utils.ItemStackUtil;
import me.crafthats.utils.MessageManager;
import me.crafthats.hats.Hat;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

		if (e.getSlotType() == SlotType.QUICKBAR) {
			if (player.getGameMode() != GameMode.CREATIVE)
				if (!plugin.getConfig().getBoolean("can-move-hat-item"))
					if (itemStack.equals(ItemStackUtil.getHatItem())) {
						e.setCancelled(true);
						return;
					}
		}

		if (e.getSlot() == 39)
			if (hatPlayer.isWearingHat()) {
				e.setCancelled(true);
				msg.bad(player, plugin.getConfig().getString("reset-hat-message"));
				return;
			}

		if (hatPlayer == null)
			return;

		if (itemStack == null)
			return;

		if (itemStack.getType() == Material.AIR)
			return;

		if (inventory.getTitle().equals(hatPlayer.getInventory().getTitle())) {
			e.setCancelled(true);

			Hat hat = HatManager.getHat(itemStack, hatPlayer);

			if (hat == null)
				return;

			if (plugin.getConfig().getBoolean("per-hat-permissions")) {
				if (!player.hasPermission("crafthats.hat.*") && !player.isOp()) {
					if (hatPlayer.getPlayer().hasPermission("crafthats.hat." + hat.getName())) {
						if (!player.hasPermission("crafthats.hat." + hat.getName())) {
							msg.bad(player, plugin.getConfig().getString("no-permission-message"));
							player.closeInventory();
							return;
						}
					}
				}
			}

			if (hatPlayer.hasHat(hat)) {
				hatPlayer.use(hat);
			} else {
				if (hat.getPrice() > 0) {
					hatPlayer.buy(hat);
				} else {
					hatPlayer.use(hat);
				}
			}

			return;
		}
	}
}
