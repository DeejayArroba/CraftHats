package me.crafthats;

import me.crafthats.commands.HatCommand;
import me.crafthats.events.InventoryClick;
import me.crafthats.events.PlayerJoin;
import me.crafthats.events.PlayerLeave;
import me.crafthats.hats.HatManager;
import me.crafthats.hats.HatPlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

	public static Economy economy = null;
	MessageManager msg = MessageManager.getInstance();

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		if(getConfig().getBoolean("update-check")) {
			if (getConfig().getBoolean("auto-update")) {
				@SuppressWarnings("unused")
				Updater updater = new Updater(this, 70538, this.getFile(), Updater.UpdateType.DEFAULT, true);
			} else {
				Updater updater = new Updater(this, 70538, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
				Updater.UpdateResult result = updater.getResult();
				if (result == Updater.UpdateResult.UPDATE_AVAILABLE) {
					getLogger().info("An update for " + this.getName() + " is available");
				}
			}
		}

		/*
		Commented this because the MCStats website wasn't working at the time.

		if(getConfig().getBoolean("metrics")) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				System.out.println("Failed to send metrics data");
			}
		}

		*/


		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
		new HatCommand("crafthats", "/<command>", "Main CraftHats command.", this, Arrays.asList("hats", "hat"));

		if (!setupEconomy()) {
			Logger.getLogger("Minecraft").severe(
					String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		HatManager.loadHats();
		HatPlayerManager.createAllHatPlayers();
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
		if(getConfig().getBoolean("update-check")) {
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

}
