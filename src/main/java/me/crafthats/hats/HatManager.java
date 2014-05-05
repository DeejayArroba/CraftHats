package me.crafthats.hats;

import me.crafthats.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class HatManager {

	private static List<Hat> loadedHats = new ArrayList<Hat>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	public static void loadHats() {
		ConfigManager.reload(plugin, "hats.yml");
		FileConfiguration config = ConfigManager.get("hats.yml");
		ConfigurationSection configurationSection = config.getConfigurationSection("hats");
		loadedHats.clear();
		for (String name : configurationSection.getKeys(false)) {
			if (loadedHats.size() < 54) {
				if(config.get("hats." + name + ".dataValue") != null) {
					config.set("hats." + name + ".data-value", config.get("hats." + name + ".dataValue"));
					config.set("hats." + name + ".dataValue", null);
				}
				double price = config.getDouble("hats." + name + ".price");
				Material material = Material.getMaterial(config.getString("hats." + name + ".material"));
				String displayName = config.getString("hats." + name + ".display-name");
				short dataValue = (short) config.getInt("hats." + name + ".data-value");

				if (material.isBlock()) {
					Hat hat = new Hat(name, displayName, price, material, dataValue);
					loadedHats.add(hat);
				}
			}
		}
		ConfigManager.save(plugin, "hats.yml");
	}

	public static Hat getHat(ItemStack itemStack, HatPlayer hatPlayer) {
		for (Hat hat : loadedHats) {
			if (hat.getItemStack(hatPlayer).equals(itemStack))
				return hat;
		}
		return null;
	}

	public static List<Hat> getHats() {
		return loadedHats;
	}

}
