package me.crafthats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class MessageManager {

	private Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	private String prefix = plugin.getConfig().getString("chat-prefix").replace('&', '§') + " " + ChatColor.RESET;

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
