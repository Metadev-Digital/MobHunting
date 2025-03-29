package metadev.digital.MetaMobHunting.compatibility;
// TODO: REMOVE
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import io.hotmail.com.jacob_vejvoda.infernal_mobs.infernal_mobs;
import metadev.digital.metacustomitemslib.compatibility.CompatPlugin;
import metadev.digital.metacustomitemslib.mobs.MobType;
import metadev.digital.MetaMobHunting.MobHunting;

public class InfernalMobsCompat implements Listener {

	// https://www.spigotmc.org/resources/infernal-mobs.2156/

	private static boolean supported = false;
	private static Plugin mPlugin;
	private static HashMap<String, Double> mMobRewardData = new HashMap<String, Double>();
	private static infernal_mobs api;
	public static final String MH_INFERNALMOBS = "MH:INFERNALMOBS";

	public InfernalMobsCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(
					MobHunting.PREFIX_WARNING + "Compatibility with InfernalMobs is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.InfernalMobs.getName());

			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

			Bukkit.getConsoleSender().sendMessage(MobHunting.PREFIX + "Enabling Compatibility with InfernalMobs ("
					+ getInfernalMobs().getDescription().getVersion() + ")");

			api = (infernal_mobs) mPlugin;

			loadInfernalMobsData();

			MobHunting.getInstance().getStoreManager().insertInfernalMobs();

			supported = true;
		}
	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public static infernal_mobs getInfernalMobs() {
		return (infernal_mobs) mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isInfernalMob(Entity entity) {
		if (isSupported())
			return entity.hasMetadata(MH_INFERNALMOBS) || api.idSearch(entity.getUniqueId()) != -1;
		return false;
	}

	public static HashMap<String, Double> getMobRewardData() {
		return mMobRewardData;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationInfernalMobs;
	}

	// **************************************************************************
	// LOAD & SAVE
	// **************************************************************************
	public static void loadInfernalMobsData() {
		for (MobType mob : MobType.values()) {
			String key = mob.getMobType();
			mMobRewardData.put(key, 1.0);
		}
		MobHunting.getInstance().getMessages().debug("Loaded %s InfernalMobs", mMobRewardData.size());
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onInfernalMobDeathEvent(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (isInfernalMob(entity)) {
			if (api.findMobAbilities(entity.getUniqueId()) != null)
				entity.setMetadata(MH_INFERNALMOBS,
						new FixedMetadataValue(MobHunting.getInstance(), api.findMobAbilities(entity.getUniqueId())));
		}
	}

	public static int getProgressAchievementLevel1(String mobtype) {
		MobType mob = MobType.valueOf(mobtype);
		if (mob != null)
			return MobHunting.getInstance().getConfigManager().getProgressAchievementLevel1(mob);
		else
			return 100;
	}

}
