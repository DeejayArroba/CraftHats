package me.crafthats.hats;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Hat {

	private Material material;
	private String name;
	private String displayName;
	private double price;
	private short dataValue;

	public Hat(String name, String displayName, double price, Material material, short dataValue) {

		this.name = name;
		this.displayName = displayName;
		this.price = price;
		this.material = material;
		this.dataValue = dataValue;

	}

	public ItemStack getItemStack(boolean ownsHat) {
		ItemStack itemStack = new ItemStack(material, 1, dataValue);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(getDisplayName());
		List<String> lore = new ArrayList<String>();

		if (price <= 0)
			lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Price: FREE!");
		else
			lore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Price:" + price);

		if (ownsHat) {
			lore.add(ChatColor.GREEN + "You own this hat.");
		} else
			lore.add(ChatColor.RED + "You don't own this hat.");

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return ChatColor.AQUA + displayName;
	}

	public double getPrice() {
		return price;
	}

	public Material getMaterial() {
		return material;
	}

}
