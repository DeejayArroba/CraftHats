package me.crafthats.hats;

import java.util.ArrayList;
import java.util.List;

import me.crafthats.Main;
import me.crafthats.MessageManager;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class HatPlayer {

	private Player player;
	private List<String> ownedHats = new ArrayList<String>();
	private JavaPlugin plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("CraftHats");
	private MessageManager msg = MessageManager.getInstance();
	private ItemStack previousHelmet;
	private Hat currentHat;
	private boolean wearingHat = false;

	public HatPlayer(Player player) {
		FileConfiguration config = plugin.getConfig();
		this.player = player;

		plugin.saveConfig();
		plugin.reloadConfig();

		String path = "players." + player.getName();

		List<String> ownedHatsFromConfig = config.getStringList(path);

		ownedHats = ownedHatsFromConfig;

		if (ownedHats.size() == 0) {
			msg.good(player, "Zero hats loaded :(");
		} else {
			msg.good(player, "Some hats were loaded!");
			msg.good(player, ownedHats.toString());
		}

	}

	public List<String> getOwnedHats() {
		return ownedHats;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean hasHat(Hat hat) {
		if (ownedHats.contains(hat.getName()))
			return true;
		return false;
	}

	public Inventory getInventory() {
		List<Hat> hats = HatManager.getHats();

		int slotCount;
		int itemCount = hats.size();
		int rest = itemCount % 9;

		if (rest == 0) {
			slotCount = itemCount;
		} else {
			slotCount = itemCount + (9 - rest);
		}

		Inventory inventory = Bukkit.createInventory(player, slotCount, "Hats");

		for (Hat hat : HatManager.getHats()) {
			boolean ownsHat = hasHat(hat);
			ItemStack hatItemStack = hat.getItemStack(ownsHat);
			inventory.addItem(hatItemStack);
		}

		return inventory;

	}

	public void use(Hat hat) {

		if (hat == null)
			return;

		if (hasHat(hat)) {
			resetHat();
			setPreviousHelmet();
			wearingHat = true;
			setCurrentHat(hat);

			ItemStack itemStack = hat.getItemStack(true);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setLore(null);
			itemStack.setItemMeta(itemMeta);

			player.getInventory().setHelmet(itemStack);

			player.closeInventory();

			msg.good(player, "Enjoy your new hat!");
		}

	}

	public void buy(Hat hat) {
		FileConfiguration config = plugin.getConfig();
		if (!hasHat(hat)) {

			Economy economy = Main.getEconomy();
			double balance = economy.getBalance(player.getName());
			double price = hat.getPrice();

			if (balance >= hat.getPrice()) {
				economy.withdrawPlayer(player.getName(), price);
				ownedHats.add(hat.getName());

				String path = "players." + player.getName();

				List<String> list = config.getStringList(path);
				list.add(hat.getName());
				config.set(path, list);

				plugin.saveConfig();
				plugin.reloadConfig();

				player.closeInventory();
				
				player.openInventory(getInventory());

				msg.good(player, "You bought a new hat!");
			} else
				msg.bad(player, "You can't afford that hat!");

		} else
			msg.info(player, "You already own that hat!");

	}

	public void setPreviousHelmet() {
		ItemStack helmet = player.getInventory().getHelmet();

		if (helmet == null) {
			previousHelmet = new ItemStack(Material.AIR);
		} else {
			previousHelmet = helmet;
		}

	}

	public Hat getCurrentHat() {
		return currentHat;
	}

	public void setCurrentHat(Hat hat) {
		currentHat = hat;
	}

	public void resetHat() {
		if (wearingHat && previousHelmet != null) {
			wearingHat = false;
			setCurrentHat(null);
			player.getInventory().setHelmet(previousHelmet);
		}
	}

	public boolean isWearingHat() {
		return wearingHat;
	}

	public void setWearingIsHat(boolean b) {
		wearingHat = b;
	}

}
