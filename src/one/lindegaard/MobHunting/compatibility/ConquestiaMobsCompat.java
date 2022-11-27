package one.lindegaard.MobHunting.compatibility;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.conquestiamc.cqmobs.CqMobs;

import one.lindegaard.CustomItemsLib.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

public class ConquestiaMobsCompat implements Listener {

	// https://www.spigotmc.org/resources/conquesita-mobs.21307/

	private static boolean supported = false;
	private static Plugin mPlugin;
	public static final String MH_CONQUESTIAMOBS = "MH:CQMOBS";

	public ConquestiaMobsCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(
					MobHunting.PREFIX_WARNING + "Compatibility with ConquestiaMobs is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.ConquestiaMobs.getName());

			if (mPlugin.getDescription().getVersion().compareTo("3.3.3") >= 0) {
				Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
				Bukkit.getConsoleSender().sendMessage(MobHunting.PREFIX + "Enabling Compatibility with ConquestiaMobs ("
						+ getCustomMobs().getDescription().getVersion() + ")");
				supported = true;
			} else {
				Bukkit.getConsoleSender().sendMessage(MobHunting.PREFIX_WARNING
						+ "Your current version of ConqustiaMobs (" + mPlugin.getDescription().getVersion()
						+ ") is not supported by MobHunting. Please update ConquestiaMobs to version 3.3.3 or newer.");
			}
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

	public static boolean isCqMobs(Entity entity) {
		if (isSupported())
			return entity.hasMetadata(MH_CONQUESTIAMOBS);
		return false;
	}

	public static Integer getCqLevel(Entity entity) {
		if (isSupported())
			return CqMobs.getMobLevel(entity);
		return 0;
	}

	public static double getCqMultiplier(Entity killed) {
		List<MetadataValue> data = killed.getMetadata(MH_CONQUESTIAMOBS);
		MetadataValue value = data.get(0);
		return (double) value.value();
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationConquestiaMobs;
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void ConquestiaMobSpawnEvent(EntitySpawnEvent event) {
		Entity entity = event.getEntity();
		if (CqMobs.isLeveledMob(entity)) {
			int level = CqMobs.getMobLevel(entity);
			// Messages.debug("ConquestiaMobSpawnEvent: MinecraftMobtype=%s
			// Level=%s", entity.getType(), level);
			entity.setMetadata(MH_CONQUESTIAMOBS, new FixedMetadataValue(mPlugin, level));
		}
	}

}
