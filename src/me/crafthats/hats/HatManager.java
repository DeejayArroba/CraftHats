package me.crafthats.hats;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HatManager {

	private static List<Hat> loadedHats = new ArrayList<Hat>();
	private static JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CraftHats");

	public static void loadHats() {
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		ConfigurationSection configurationSection = config.getConfigurationSection("hats");
		loadedHats.clear();
		for (String name : configurationSection.getKeys(false)) {
			if (loadedHats.size() < 54) {
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
	}

	public static Hat getHat(String hatName) {
		for (Hat hat : loadedHats) {
			if (hat.getName() == hatName) {
				return hat;
			}
		}
		return null;
	}

	public static Hat getHatFromDisplayName(ItemStack itemStack) {
		for (Hat hat : loadedHats) {
			if (itemStack.getItemMeta().getDisplayName().equals(hat.getItemStack(true).getItemMeta().getDisplayName())) {
				return hat;
			}
		}
		return null;
	}

	public static Hat getHatFromMaterial(Material hatMaterial) {
		for (Hat hat : loadedHats) {
			if (hat.getMaterial() == hatMaterial) {
				return hat;
			}
		}
		return null;
	}

	public static List<Hat> getHats() {
		return loadedHats;
	}

}
