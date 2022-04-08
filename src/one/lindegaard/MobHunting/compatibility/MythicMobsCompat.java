package one.lindegaard.MobHunting.compatibility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.StatType;
import one.lindegaard.MobHunting.mobs.ExtendedMobRewardData;

public class MythicMobsCompat {

	private static boolean supported = false;
	private static Plugin mPlugin;
	private static HashMap<String, ExtendedMobRewardData> mMobRewardData = new HashMap<String, ExtendedMobRewardData>();
	private static File file = new File(MobHunting.getInstance().getDataFolder(), "mythicmobs-rewards.yml");
	private static YamlConfiguration config = new YamlConfiguration();

	public static final String MH_MYTHICMOBS = "MH:MYTHICMOBS";

	public enum MythicMobVersion {
		NOT_DETECTED, MYTHICMOBS_V251, MYTHICMOBS_V400, MYTHICMOBS_V500 
	};

	public static MythicMobVersion mmVersion = MythicMobVersion.NOT_DETECTED;

	public MythicMobsCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with MythicMobs is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MythicMobs.getName());
			if (mPlugin.getDescription().getVersion().compareTo("5.0.0") >= 0) {

				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with MythicMobs (" + mPlugin.getDescription().getVersion() + ")");
				mmVersion = MythicMobVersion.MYTHICMOBS_V500;
				supported = true;
				Bukkit.getPluginManager().registerEvents(new MythicMobsV400Compat(), MobHunting.getInstance());

			} else if (mPlugin.getDescription().getVersion().compareTo("4.0.0") >= 0) {

				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with MythicMobs (" + mPlugin.getDescription().getVersion() + ")");
				mmVersion = MythicMobVersion.MYTHICMOBS_V400;
				supported = true;
				Bukkit.getPluginManager().registerEvents(new MythicMobsV400Compat(), MobHunting.getInstance());

			} else if (mPlugin.getDescription().getVersion().compareTo("2.5.1") >= 0) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with MythicMobs (" + mPlugin.getDescription().getVersion() + ")");
				mmVersion = MythicMobVersion.MYTHICMOBS_V251;
				supported = true;
				Bukkit.getPluginManager().registerEvents(new MythicMobsV251Compat(), MobHunting.getInstance());

			} else {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				console.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "MythicMobs is outdated. Please update to V2.5.1 or newer. Integration will be disabled");
				return;
			}
			MythicMobsCompat.loadMythicMobsData();
			MythicMobsCompat.saveMythicMobsData();
		}
	}

	public static boolean isSupported() {
		return supported;
	}

	public static void setSupported(boolean status) {
		supported = status;
	}

	public static MythicMobVersion getMythicMobVersion() {
		return mmVersion;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMythicmobs;
	}

	public static HashMap<String, ExtendedMobRewardData> getMobRewardData() {
		return mMobRewardData;
	}

	public static boolean isMythicMob(String mob) {
		switch (mmVersion) {
		case MYTHICMOBS_V251:
			return MythicMobsV251Compat.isMythicMobV251(mob);
		case MYTHICMOBS_V400:
			return MythicMobsV400Compat.isMythicMobV400(mob);
		case MYTHICMOBS_V500:
			return MythicMobsV500Compat.isMythicMobV500(mob);
		case NOT_DETECTED:
			break;
		default:
			break;
		}
		return false;
	}

	public static String getMythicMobName(String mob) {
		switch (mmVersion) {
		case MYTHICMOBS_V251:
			return MythicMobsV251Compat.getMythicMobV251(mob).getInternalName();
		case MYTHICMOBS_V400:
			return MythicMobsV400Compat.getMythicMobV400(mob).getInternalName();
		case MYTHICMOBS_V500:
			return MythicMobsV500Compat.getMythicMobV500(mob).getInternalName();
		case NOT_DETECTED:
			break;
		default:
			break;
		}
		return "Unknown";
	}

	public static boolean isMythicMob(Entity killed) {
		if (isSupported())
			return killed.hasMetadata(MH_MYTHICMOBS);
		return false;
	}

	public static String getMythicMobType(Entity killed) {
		List<MetadataValue> data = killed.getMetadata(MythicMobsCompat.MH_MYTHICMOBS);
		for (MetadataValue mdv : data) {
			if (mdv.value() instanceof ExtendedMobRewardData)
				return ((ExtendedMobRewardData) mdv.value()).getMobType();
		}
		return null;
	}

	public static int getProgressAchievementLevel1(String mobtype) {
		return mMobRewardData.get(mobtype).getAchivementLevel1();
	}

	// **************************************************************************
	// LOAD & SAVE
	// **************************************************************************
	public static void loadMythicMobsData() {
		try {
			if (!file.exists())
				return;
			
			MobHunting.getInstance().getMessages().debug("Loading extra MobRewards for MythicMobs mobs.");

			config.load(file);
			int n = 0;
			for (String key : config.getKeys(false)) {
				ConfigurationSection section = config.getConfigurationSection(key);
				if (isMythicMob(key)) {
					ExtendedMobRewardData mob = new ExtendedMobRewardData();
					mob.read(section);
					mob.setMobType(key);
					mMobRewardData.put(key, mob);
					MobHunting.getInstance().getStoreManager().insertMissingMythicMobs(key);
					n++;
				} else {
					MobHunting.getInstance().getMessages()
							.debug("The mob=%s can't be found in MythicMobs configuration files", key);
				}
			}
			MobHunting.getInstance().getMessages().debug("Loaded %s MythicMobs", n);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static void loadMythicMobsData(String key) {
		try {
			if (!file.exists())
				return;

			config.load(file);
			ConfigurationSection section = config.getConfigurationSection(key);
			if (isMythicMob(key)) {
				ExtendedMobRewardData mob = new ExtendedMobRewardData();
				mob.read(section);
				mob.setMobType(key);
				mMobRewardData.put(key, mob);
				int n = StatType.values().length;
				StatType.values()[n + 1] = new StatType(mob.getMobType() + "_kill", mob.getMobName());
				StatType.values()[n + 2] = new StatType(mob.getMobType() + "_assist", mob.getMobName());
				MobHunting.getInstance().getStoreManager().insertMissingMythicMobs(key);
			} else {
				MobHunting.getInstance().getMessages()
						.debug("The mob=%s can't be found in MythicMobs configuration files", key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void saveMythicMobsData() {
		try {
			config.options().header("This a extra MobHunting config data for the MythicMobs on your server.");

			if (mMobRewardData.size() > 0) {

				int n = 0;
				for (String str : mMobRewardData.keySet()) {
					ConfigurationSection section = config.createSection(str);
					mMobRewardData.get(str).save(section);
					n++;
				}

				if (n != 0) {
					MobHunting.getInstance().getMessages().debug("Saving Mobhunting extra MythicMobs data.");
					config.save(file);
				}
			} else {
				MobHunting.getInstance().getMessages().debug("No Mythicmobs");
			}
		} catch (IOException e) {
			MobHunting.getInstance().getMessages().debug("Could not save extra MythicMobs data.");
			e.printStackTrace();
		}
	}

	public static void saveMythicMobsData(String key) {
		try {
			if (mMobRewardData.containsKey(key)) {
				ConfigurationSection section = config.createSection(key);
				mMobRewardData.get(key).save(section);
				MobHunting.getInstance().getMessages().debug("Saving Mobhunting extra MythicMobs data.");
				config.save(file);
			} else {
				MobHunting.getInstance().getMessages().debug("ERROR! MythicMobs ID (%s) is not found in mMobRewardData",
						key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
