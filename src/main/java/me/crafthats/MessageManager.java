package me.crafthats;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

	private String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA
			+ "CraftHats" + ChatColor.GRAY + "] " + ChatColor.RESET;
	private static MessageManager instance = new MessageManager();

	public static MessageManager getInstance() {
		return instance;
	}

	public void good(CommandSender sender, String message) {
		sender.sendMessage(prefix + ChatColor.GREEN + message);
	}

	public void info(CommandSender sender, String message) {
		sender.sendMessage(prefix + ChatColor.YELLOW + message);
	}

	public void bad(CommandSender sender, String message) {
		sender.sendMessage(prefix + ChatColor.RED + message);
	}

}
