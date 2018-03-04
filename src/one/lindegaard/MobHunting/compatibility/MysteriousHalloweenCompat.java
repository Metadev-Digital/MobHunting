package one.lindegaard.MobHunting.compatibility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.mobs.MobPlugin;
import one.lindegaard.MobHunting.rewards.RewardData;
import me.F_o_F_1092.MysteriousHalloween.MysteriousHalloweenAPI;
import me.F_o_F_1092.MysteriousHalloween.MysteriousHalloweenAPI.MobType;

public class MysteriousHalloweenCompat implements Listener {

	private static Plugin mPlugin;
	private static boolean supported = false;
	private static HashMap<String, RewardData> mMobRewardData = new HashMap<String, RewardData>();
	private static File file = new File(MobHunting.getInstance().getDataFolder(), "MysteriousHalloween-rewards.yml");
	private static YamlConfiguration config = new YamlConfiguration();
	public static final String MH_MYSTERIOUSHALLOWEEN = "MH:MysteriousHalloween";

	// https://www.spigotmc.org/resources/mysterioushalloween.13059/

	public MysteriousHalloweenCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getLogger().info("[MobHunting] Compatibility with MysteriousHalloween is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MysteriousHalloween.getName());

			if (mPlugin.getDescription().getVersion().compareTo("1.3.2") >= 0) {

				Bukkit.getLogger().info("[MobHunting] Enabling compatibility with MysteriousHalloween ("
						+ mPlugin.getDescription().getVersion() + ")");

				supported = true;

				Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

				loadMysteriousHalloweenMobsData();
				saveMysteriousHalloweenMobsData();
			} else {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				console.sendMessage(ChatColor.RED + "[MobHunting] Your current version of MysteriousHalloween ("
						+ mPlugin.getDescription().getVersion()
						+ ") has no API implemented. Please update to V1.3.2 or newer.");
			}
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMysteriousHalloween;
	}

	/**
	 * Returns whether an entity is a MysteriousHalloween entity.
	 *
	 * @param entity
	 *            the entity to check
	 * @return true if the entity is a MysteriousHalloween entity
	 */
	public static boolean isMysteriousHalloween(Entity entity) {
		if (isSupported())
			return MysteriousHalloweenAPI.isEntity(entity);
		return false;
	}

	/**
	 * Returns the Monster type for a MysteriousHalloween entity.
	 *
	 * @param entity
	 *            the entity to get the mob type for
	 * @return the mob type or null if it is not MysteriousHalloween entity
	 */
	public static MobType getMysteriousHalloweenType(Entity entity) {
		if (isMysteriousHalloween(entity))
			return MysteriousHalloweenAPI.getMobType(entity);
		return null;
	}

	public static HashMap<String, RewardData> getMobRewardData() {
		return mMobRewardData;
	}

	// **************************************************************************
	// LOAD & SAVE
	// **************************************************************************
	public static void loadMysteriousHalloweenMobsData() {
		try {
			if (!file.exists()) {
				for (MobType monster : MysteriousHalloweenAPI.getMobTypes()) {
					mMobRewardData.put(monster.name(),
							new RewardData(MobPlugin.MysteriousHalloween, monster.name(),
									MysteriousHalloweenAPI.getMobTypeName(monster),
									true,"40:60",1,"You killed a MysteriousHalloween mob",
									new ArrayList<HashMap<String,String>>(), 1, 0.02));
					saveMysteriousHalloweenMobsData(mMobRewardData.get(monster.name()).getMobType());
				}
				return;
			}

			config.load(file);
			for (String key : config.getKeys(false)) {
				ConfigurationSection section = config.getConfigurationSection(key);
				RewardData mob = new RewardData();
				mob.read(section);
				mob.setMobType(key);
				mMobRewardData.put(key, mob);
				MobHunting.getInstance().getStoreManager().insertMysteriousHalloweenMobs(key);
			}
			MobHunting.getInstance().getMessages().injectMissingMobNamesToLangFiles();
			MobHunting.getInstance().getMessages().debug("Loaded %s MysteriousHalloween-Mobs", mMobRewardData.size());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static void loadMysteriousHalloweenMobsData(String key) {
		try {
			if (!file.exists()) {
				return;
			}

			config.load(file);
			ConfigurationSection section = config.getConfigurationSection(key);
			RewardData mob = new RewardData();
			mob.read(section);
			mob.setMobType(key);
			mMobRewardData.put(key, mob);
			MobHunting.getInstance().getStoreManager().insertMysteriousHalloweenMobs(key);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void saveMysteriousHalloweenMobsData() {
		try {
			config.options().header("This a extra MobHunting config data for the MysteriousHalloween on your server.");

			if (mMobRewardData.size() > 0) {

				int n = 0;
				for (String str : mMobRewardData.keySet()) {
					ConfigurationSection section = config.createSection(str);
					mMobRewardData.get(str).save(section);
					n++;
				}

				if (n != 0) {
					MobHunting.getInstance().getMessages().debug("Saving Mobhunting extra MysteriousHalloween data.");
					config.save(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveMysteriousHalloweenMobsData(String key) {
		try {
			if (mMobRewardData.containsKey(key)) {
				ConfigurationSection section = config.createSection(key);
				mMobRewardData.get(key).save(section);
				MobHunting.getInstance().getMessages().debug("Saving extra MysteriousHalloweens data for mob=%s (%s)", key,
						mMobRewardData.get(key).getMobName());
				config.save(file);
			} else {
				MobHunting.getInstance().getMessages().debug("ERROR! MysteriousHalloween ID (%s) is not found in mMobRewardData", key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onMysteriousHalloweenSpawnEvent(EntitySpawnEvent event) {

		Entity entity = event.getEntity();

		if (isMysteriousHalloween(entity)) {

			MobType monster = getMysteriousHalloweenType(entity);

			if (mMobRewardData != null && !mMobRewardData.containsKey(monster.name())) {
				MobHunting.getInstance().getMessages().debug("New MysteriousHalloween mob found=%s (%s)", monster.name(), monster.toString());
				mMobRewardData.put(monster.name(),
						new RewardData(MobPlugin.MysteriousHalloween, monster.name(),
								MysteriousHalloweenAPI.getMobTypeName(monster), 
								true,"40:60",1,"You killed a MysteriousHalloween mob",
								new ArrayList<HashMap<String,String>>(), 1, 0.02));
				saveMysteriousHalloweenMobsData(monster.name());
				MobHunting.getInstance().getStoreManager().insertMysteriousHalloweenMobs(monster.name());
				// Update mob loaded into memory
				MobHunting.getInstance().getExtendedMobManager().updateExtendedMobs();
				MobHunting.getInstance().getMessages().injectMissingMobNamesToLangFiles();
			}

			event.getEntity().setMetadata(MH_MYSTERIOUSHALLOWEEN,
					new FixedMetadataValue(mPlugin, mMobRewardData.get(monster.name())));
		}
	}

	public static int getProgressAchievementLevel1(String mobtype) {
		return mMobRewardData.get(mobtype).getAchivementLevel1();
	}

}
