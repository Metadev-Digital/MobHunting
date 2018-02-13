package one.lindegaard.MobHunting.compatibility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import de.hellfirepvp.api.CustomMobsAPI;
import de.hellfirepvp.api.data.ICustomMob;
import de.hellfirepvp.api.event.CustomMobDeathEvent;
import de.hellfirepvp.api.event.CustomMobSpawnEvent;
import de.hellfirepvp.api.event.CustomMobSpawnEvent.SpawnReason;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.mobs.MobPlugin;
import one.lindegaard.MobHunting.rewards.RewardData;

public class CustomMobsCompat implements Listener {

	// https://www.spigotmc.org/resources/custommobs.7339/

	private static boolean supported = false;
	private static Plugin mPlugin;
	private static HashMap<String, RewardData> mMobRewardData = new HashMap<String, RewardData>();
	private static File file = new File(MobHunting.getInstance().getDataFolder(), "custommobs-rewards.yml");
	private static YamlConfiguration config = new YamlConfiguration();
	public static final String MH_CUSTOMMOBS = "MH:CUSTOMMOBS";

	public CustomMobsCompat() {
		if (isDisabledInConfig()) {
			Bukkit.getConsoleSender()
					.sendMessage("[MobHunting] Compatibility with CustomMobs is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.CustomMobs.getName());

			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

			Bukkit.getConsoleSender().sendMessage("[MobHunting] Enabling Compatibility with CustomMobs ("
					+ getCustomMobs().getDescription().getVersion() + ")");

			supported = true;

			loadCustomMobsData();
			saveCustomMobsData();
		}
	}

	// **************************************************************************
	// LOAD & SAVE
	// **************************************************************************
	public static void loadCustomMobsData() {
		try {
			if (!file.exists())
				return;

			config.load(file);
			for (String key : CustomMobsAPI.getKnownMobTypes()) {
				if (config.contains(key)) {
					ConfigurationSection section = config.getConfigurationSection(key);
					RewardData mob = new RewardData();
					if (isCustomMob(key)) {
						mob.read(section);
						mob.setMobType(key);
						if (mob.getMobName() == null)
							mob.setMobName(mob.getMobType());
					} else
						mob = new RewardData(MobPlugin.CustomMobs, CustomMobsAPI.getCustomMob(key).getName(),
								CustomMobsAPI.getCustomMob(key).getDisplayName(), "10",
								"minecraft:give {player} iron_sword 1", "You got an Iron sword.", 1, 1, 0.02);

					mMobRewardData.put(key, mob);
					MobHunting.getInstance().getStoreManager().insertCustomMobs(key);
				}
			}
			MobHunting.getInstance().getMessages().injectMissingMobNamesToLangFiles();
			MobHunting.getInstance().getMessages().debug("Loaded %s CustomMobs", mMobRewardData.size());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void loadCustomMobsData(String key) {
		try {
			if (!file.exists())
				return;

			config.load(file);
			ConfigurationSection section = config.getConfigurationSection(key);
			if (isCustomMob(key)) {
				RewardData mob = new RewardData();
				mob.read(section);
				mob.setMobType(key);
				if (mob.getMobName() == null)
					mob.setMobName(mob.getMobType());
				mMobRewardData.put(key, mob);
				MobHunting.getInstance().getStoreManager().insertCustomMobs(key);
			} else {
				MobHunting.getInstance().getMessages().debug("The mob=%s cant be found in CustomMobs configuration file", key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public static void saveCustomMobsData() {
		try {
			config.options().header("This a extra MobHunting config data for the CustomMobs on your server.");

			if (mMobRewardData.size() > 0) {

				int n = 0;
				for (String str : mMobRewardData.keySet()) {
					ConfigurationSection section = config.createSection(str);
					mMobRewardData.get(str).save(section);
					n++;
				}

				if (n != 0) {
					MobHunting.getInstance().getMessages().debug("Saving Mobhunting extra CustomMobs data.");
					config.save(file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveCustomMobsData(String key) {
		try {
			if (mMobRewardData.containsKey(key)) {
				ConfigurationSection section = config.createSection(key);
				mMobRewardData.get(key).save(section);
				MobHunting.getInstance().getMessages().debug("Saving Mobhunting extra CustomMobs data.");
				config.save(file);
			} else {
				MobHunting.getInstance().getMessages().debug("ERROR! CustomMobs ID (%s) is not found in mMobRewardData", key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public static Plugin getCustomMobs() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isCustomMob(Entity entity) {
		if (isSupported())
			return entity.hasMetadata(MH_CUSTOMMOBS);
		return false;
	}

	public static boolean isCustomMob(String mob) {
		if (isSupported())
			return CustomMobsAPI.getCustomMob(mob) != null;
		return false;
	}

	public static String getCustomMobType(Entity killed) {
		if (killed.hasMetadata(MH_CUSTOMMOBS)) {
			List<MetadataValue> data = killed.getMetadata(MH_CUSTOMMOBS);
			MetadataValue value = data.get(0);
			return ((RewardData) value.value()).getMobType();
		} else
			return "";
	}

	public static HashMap<String, RewardData> getMobRewardData() {
		return mMobRewardData;
	}

	public static boolean isDisabledInConfig() {
		return MobHunting.getInstance().getConfigManager().disableIntegrationCustomMobs;
	}

	public static boolean isEnabledInConfig() {
		return !MobHunting.getInstance().getConfigManager().disableIntegrationCustomMobs;
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onCustomMobDeathEvent(CustomMobDeathEvent event) {
		// ICustomMob mob = event.getMob(); // The ICustomMob that was killed.
		// Player killer = event.getKiller(); // The Player that killed the
		// // CustomMob;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onCustomMobSpawnEvent(CustomMobSpawnEvent event) {
		// The ICustomMob that spawned.
		ICustomMob mob = event.getMob();

		// The LivingEntity instance that was spawned.
		LivingEntity entity = event.getEntity();

		MobHunting.getInstance().getMessages().debug("CustomMobSpawnEvent: MinecraftMobtype=%s CustomMobName=%s", entity.getType(), mob.getName());

		// Specific reason why the mob was spawned
		CustomMobSpawnEvent.SpawnReason reason = event.getReason();
		if (reason.equals(SpawnReason.SPAWNER) && !MobHunting.getInstance().getConfigManager().allowCustomMobsSpawners) {
			entity.setMetadata("MH:blocked", new FixedMetadataValue(MobHunting.getInstance(), true));
			// Block spawner = event.getSpawner();
			// Is only defined when the spawnReason is SPAWNER.
		}

		if (mMobRewardData != null && !mMobRewardData.containsKey(mob.getName())) {
			MobHunting.getInstance().getMessages().debug("New CustomMobName found=%s,%s", mob.getName(), mob.getDisplayName());
			String name = mob.getDisplayName() == null ? mob.getName() : mob.getDisplayName();
			mMobRewardData.put(mob.getName(), new RewardData(MobPlugin.CustomMobs, mob.getName(), name, "10",
					"minecraft:give {player} iron_sword 1", "You got an Iron sword.", 1, 1, 0.02));
			saveCustomMobsData(mob.getName());
			MobHunting.getInstance().getStoreManager().insertCustomMobs(mob.getName());
			// Update mob loaded into memory
			MobHunting.getInstance().getExtendedMobManager().updateExtendedMobs();
			MobHunting.getInstance().getMessages().injectMissingMobNamesToLangFiles();
		}

		entity.setMetadata(MH_CUSTOMMOBS,
				new FixedMetadataValue(MobHunting.getInstance(), mMobRewardData.get(mob.getName())));
	}

	public static int getProgressAchievementLevel1(String mobtype) {
		return mMobRewardData.get(mobtype).getAchivementLevel1();
	}

}
