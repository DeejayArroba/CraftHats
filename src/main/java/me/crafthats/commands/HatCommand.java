package me.crafthats.commands;

import me.crafthats.config.ConfigManager;
import me.crafthats.utils.MessageManager;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class HatCommand implements CommandExecutor {
	Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");
	MessageManager msg = MessageManager.getInstance();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("crafthats") || label.equalsIgnoreCase("hats") || label.equalsIgnoreCase("hat")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					if (!sender.hasPermission("crafthats.use")) {
						msg.bad(player, "You don't have permission!");
						return true;
					}
					Inventory inventory = HatPlayerManager.getHatPlayer(player).getInventory();
					player.openInventory(inventory);
				} else if (args[0].equalsIgnoreCase("reset")) {
					HatPlayer hatPlayer = HatPlayerManager.getHatPlayer(player);
					if (hatPlayer.isWearingHat()) {
						hatPlayer.resetHat();
						msg.good(player, "Your hat has been reset!");
					} else {
						msg.info(player, "You are not wearing a hat!");
					}
				} else if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("crafthats.admin")) {
						msg.bad(player, "You don't have permission!");
						return true;
					}

					HatManager.loadHats();
					ConfigManager.reload(plugin, "hats.yml");
					ConfigManager.reload(plugin, "players.yml");
					plugin.reloadConfig();
					msg.good(player, "CraftHats config was reloaded!");
				}
			}
			return true;
		}

		return false;
	}
}
