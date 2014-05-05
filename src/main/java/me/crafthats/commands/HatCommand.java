package me.crafthats.commands;

import me.crafthats.utils.AbstractCommand;
import me.crafthats.utils.MessageManager;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayer;
import me.crafthats.hats.HatPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HatCommand extends AbstractCommand {
	JavaPlugin plugin;
	MessageManager msg = MessageManager.getInstance();

	public HatCommand(String command, String usage, String description, JavaPlugin plugin, List<String> aliases) {
		super(command, usage, description, aliases);
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
				msg.good(player, "CraftHats config reloaded!");
			}

			return true;
		}

		return false;
	}
}
