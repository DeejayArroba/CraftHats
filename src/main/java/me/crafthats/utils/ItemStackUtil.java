package me.crafthats.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ItemStackUtil {

	public static ItemStack getHatItem() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");
		ItemStack hatItem = new ItemStack(Material.getMaterial(plugin.getConfig().getString("hat-item")), 1, (short) plugin.getConfig().getInt("hat-item-data-value"));
		ItemMeta hatItemMeta = hatItem.getItemMeta();
		hatItemMeta.setDisplayName(ChatUtil.convertToChatColors(plugin.getConfig().getString("hat-item-name")));
		hatItem.setItemMeta(hatItemMeta);

		return hatItem;
	}

	public static ItemStack getResetItem(boolean isWearingHat) {
		ItemStack resetItem = new ItemStack(Material.FIREBALL);
		ItemMeta resetItemMeta = resetItem.getItemMeta();
		resetItemMeta.setDisplayName(ChatColor.GOLD + "Reset your hat");
		List<String> lore = new ArrayList<String>();
		String loreString = isWearingHat ?
				"You are wearing a hat. (Click here to reset it)" :
				"You are not wearing a hat.";
		lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + loreString);
		resetItem.setItemMeta(resetItemMeta);
		return resetItem;
	}

}
