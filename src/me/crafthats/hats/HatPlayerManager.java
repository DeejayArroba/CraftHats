package me.crafthats.hats;

import me.crafthats.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class HatPlayerManager {

	private static List<HatPlayer> hatPlayers = new ArrayList<HatPlayer>();
	private static JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CraftHats");

	public static void createHatPlayer(Player player) {
		HatPlayer hatPlayer = new HatPlayer(player);
		hatPlayers.add(hatPlayer);
	}

	public static HatPlayer getHatPlayer(Player player) {
		for (HatPlayer hatPlayer : hatPlayers) {
			if (hatPlayer.getPlayer() == player) {
				return hatPlayer;
			}
		}
		return null;
	}

	public static void removeHatPlayer(Player player) {

		ConfigManager.reload(plugin, "players.yml");
		FileConfiguration config = ConfigManager.get("players.yml");

		HatPlayer hatPlayer = getHatPlayer(player);

		String path = "players." + player.getName();

		config.set(path, hatPlayer.getOwnedHats());

		hatPlayers.remove(hatPlayer);
	}

	public static void createAllHatPlayers() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			createHatPlayer(player);
		}
	}

	public static void removeAllHatPlayers() {
		hatPlayers.clear();
	}

	public static void resetAllHats() {
		for (HatPlayer hatPlayer : hatPlayers) {
			hatPlayer.resetHat();
		}
	}

}
