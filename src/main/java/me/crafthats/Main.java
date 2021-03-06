package me.crafthats;

import me.crafthats.commands.HatCommand;
import me.crafthats.config.ConfigManager;
import me.crafthats.listeners.*;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayerManager;
import me.crafthats.utils.ItemStackUtil;
import me.crafthats.utils.MessageManager;
import me.crafthats.utils.Metrics;
import me.crafthats.utils.Updater;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

	public static Economy economy = null;
	MessageManager msg;
	public static boolean devBuild = true;
	public static boolean economyEnabled;

	@Override
	public void onEnable() {
		ConfigManager.load(this, "hats.yml");
		ConfigManager.load(this, "players.yml");
		saveDefaultConfig();

		if (getConfig().getBoolean("update-check") && !devBuild) {
			if (getConfig().getBoolean("auto-update")) {
				new Updater(this, 75524, this.getFile(), Updater.UpdateType.DEFAULT, true);
			} else {
				Updater updater = new Updater(this, 75524, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
				Updater.UpdateResult result = updater.getResult();
				if (result == Updater.UpdateResult.UPDATE_AVAILABLE) {
					getLogger().info("An update for " + this.getName() + " is available");
				}
			}
		}

		if (getConfig().getBoolean("metrics")) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				System.out.println("Failed to send metrics data");
			}
		}

		if (getConfig().getBoolean("economy"))
			if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
				economyEnabled = true;
				if (!setupEconomy()) {
					Logger.getLogger("Minecraft").severe(
							String.format("[%s] - Disabled due to no Economy plugin found!", getDescription().getName()));
					getServer().getPluginManager().disablePlugin(this);
					return;
				}
			} else {
				economyEnabled = false;
			}

		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

		getCommand("crafthats").setExecutor(new HatCommand());

		HatManager.loadHats();
		HatPlayerManager.createAllHatPlayers();
		msg = MessageManager.getInstance();

		for(Player p : Bukkit.getOnlinePlayers()) {
			ItemStackUtil.giveHatItem(p);
		}
	}

	@Override
	public void onDisable() {
		HatPlayerManager.resetAllHats();
		HatPlayerManager.removeAllHatPlayers();
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
				net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public static Economy getEconomy() {
		return economy;
	}

	public void updateNotice(Player p) {
		if (getConfig().getBoolean("update-check")) {
			if (!getConfig().getBoolean("auto-update")) {
				if (p.hasPermission("crafthats.updater") || p.isOp()) {
					Updater updater = new Updater(this, 75524, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
					Updater.UpdateResult result = updater.getResult();
					if (result == Updater.UpdateResult.UPDATE_AVAILABLE) {
						msg.info(p, "An update is available for " + this.getName() + ". Get it here: " + updater.getLatestFileLink());
					}
				}
			}
		}
	}

	public static boolean isEconomyEnabled() {
		return economyEnabled;
	}

}
