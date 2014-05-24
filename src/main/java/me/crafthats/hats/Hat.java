package me.crafthats.hats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Hat {

	private Material material;
	private String name;
	private String displayName;
	private String description;
	private double price;
	private short dataValue;
	Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftHats");

	public Hat(String name, String displayName, double price, Material material, short dataValue, String description) {
		this.name = name;
		this.displayName = displayName;
		this.price = price;
		this.material = material;
		this.dataValue = dataValue;
		this.description = description;
	}

	public ItemStack getItemStack(HatPlayer hatPlayer) {
		ItemStack itemStack = new ItemStack(material, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(getDisplayName());
		itemStack.setDurability(dataValue);
		List<String> lore = new ArrayList<String>();
		boolean ownsHat = hatPlayer.getOwnedHats().contains(this.getName());

		if(!description.equals(""))
			lore.add(ChatColor.GOLD + description);

		if (price <= 0) {
			lore.add(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "Price: FREE! " + ChatColor.GRAY + "(Click to use)");
		} else {
			lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Price: " + price);
			if (ownsHat)
				lore.add(ChatColor.GREEN + "You own this hat " + ChatColor.GRAY + "(Click to use)");
			else {
				lore.add(ChatColor.RED + "You don't own this hat " + ChatColor.GRAY + "(Click to buy)");
			}
		}

		if (plugin.getConfig().getBoolean("per-hat-permissions"))
			if (!hatPlayer.getPlayer().isOp())
				if (hatPlayer.getPlayer().hasPermission("crafthats.hat." + getName()))
					lore.add(ChatColor.ITALIC + "" + ChatColor.RED + "You don't have permission for this hat.");

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return  ChatColor.AQUA + "" +ChatColor.BOLD + displayName;
	}

	public double getPrice() {
		return price;
	}

	public Material getMaterial() {
		return material;
	}

}
