package metadev.digital.MetaMobHunting;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import metadev.digital.MetaMobHunting.compatibility.CMIHelper;
import metadev.digital.MetaMobHunting.leaderboard.HologramLeaderboard;

public class HologramManager { //TODO: Need additional hologram manager beyond CMI

	private MobHunting plugin;

	private HashMap<String, HologramLeaderboard> holograms = new HashMap<>();

	public HologramManager(MobHunting plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, HologramLeaderboard> getHolograms() {
		return holograms;
	}

	public void createHologramLeaderboard(HologramLeaderboard hologramLeaderboard) {
		holograms.put(hologramLeaderboard.getHologramName(), hologramLeaderboard);

		if (CMIHelper.isCMILoaded()) {
			CMIHelper.createHologramFromLeaderboard(hologramLeaderboard);
		}

		hologramLeaderboard.update();
	}

	public void deleteHolographicLeaderboard(String hologramName) {
		 if (CMIHelper.isCMILoaded()) {
			 CMIHelper.deleteHologramByName(hologramName);
		}
		holograms.remove(hologramName);
	}

	public String listHolographicLeaderboard() {
		String str = "";
		if (CMIHelper.isCMILoaded()) {
			if (holograms.size() == 0) {
				str = plugin.getMessages().getString("mobhunting.holograms.no-holograms");
			} else {
				str = "Holograms: ";
				for (String hologramName : holograms.keySet()) {
					str = str + hologramName + ", ";
				}
				str = str.substring(0, str.length() - 2);
			}
		}
		return str;
	}

	public void updateHolographicLeaderboard(String hologramName) {
		if (CMIHelper.isCMILoaded()) {
			holograms.get(hologramName).update();
		}
	}

	// *******************************************************************
	// HOLOGRAM LEADERBOARDS
	// *******************************************************************
	private YamlConfiguration hologramConfig = new YamlConfiguration();
	private File hologramFile = new File(MobHunting.getInstance().getDataFolder(), "hologram-leaderboards.yml");

	public void deleteHologramLeaderboard(String hologramName) throws IllegalArgumentException {

		deleteHolographicLeaderboard(hologramName);
		hologramConfig.set(hologramName, null);

		try {
			hologramConfig.save(hologramFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveHologramLeaderboards() {
		hologramConfig.options().header("Always make a backup before changing this file.\n"
				+ "The format of the Holographic Leaderboards can be changed using the Java String.format() syntax.\n"
				+ "(https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html).\n"
				+ "The colors can be changed using '§' and the normal Minecraft color codes. (http://ess.khhq.net/mc/)\n"
				+ "If you make a wrong format you can always delete the formatting lines and restart the server.");

		for (HologramLeaderboard board : getHolograms().values()) {
			ConfigurationSection section = hologramConfig.createSection(board.getHologramName());
			board.write(section);
		}

		try {
			hologramConfig.save(hologramFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveHologramLeaderboard(String hologramName) {
		hologramConfig.options().header("Always make a backup before changing this file.\n"
				+ "The format of the Holographic Leaderboards can be changed using the Java String.format() syntax.\n"
				+ "(https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html).\n"
				+ "The colors can be changed using '§' and the normal Minecraft color codes. (http://ess.khhq.net/mc/)\n"
				+ "If you make a wrong format you can always delete the formatting lines and restart the server.");

		ConfigurationSection section = hologramConfig.createSection(hologramName);
		HologramLeaderboard board = getHolograms().get(hologramName);
		board.write(section);

		try {
			hologramConfig.save(hologramFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadHologramLeaderboards() {

		if (!hologramFile.exists())
			return;

		try {
			hologramConfig.load(hologramFile);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender()
					.sendMessage(ChatColor.RED + "Could not read Hologram Leaderboard file: hologram-leaderboards.yml");
			if (plugin.getConfigManager().killDebug)
				e.printStackTrace();
		}

		Iterator<String> keys = hologramConfig.getKeys(false).iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			ConfigurationSection section = hologramConfig.getConfigurationSection(key);
			HologramLeaderboard board = new HologramLeaderboard(plugin);
			try {
				board.read(section);
				createHologramLeaderboard(board);
			} catch (InvalidConfigurationException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
			}
		}

		if (getHolograms().size() > 0)
			plugin.getMessages().debug("%s Holographic Leaderboards loaded", getHolograms().size());

	}

	public void loadHologramLeaderboard(String hologramName) {

		if (!hologramFile.exists())
			return;

		try {
			hologramConfig.load(hologramFile);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender()
					.sendMessage(ChatColor.RED + "Could not read Hologram Leaderboard file: hologram-leaderboards.yml");
			if (plugin.getConfigManager().killDebug)
				e.printStackTrace();
		}

		ConfigurationSection section = hologramConfig.getConfigurationSection(hologramName);
		HologramLeaderboard board = new HologramLeaderboard(plugin);
		try {
			board.read(section);
			createHologramLeaderboard(board);
		} catch (InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
		}

		plugin.getMessages().debug("The Holographic Leaderboard '%s' was loaded from file.", hologramName);

	}

}
