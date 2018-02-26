package one.lindegaard.MobHunting.compatibility;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import com.kiwifisher.mobstacker.utils.StackUtils;

import one.lindegaard.MobHunting.MobHunting;

public class MobStackerCompat implements Listener {

	// https://www.spigotmc.org/resources/mobstacker.15596/
	// Source code: https://github.com/Rocologo/MobStacker

	private static boolean supported = false;
	private static Plugin mPlugin;

	public MobStackerCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getLogger().info("[MobHunting] Compatibility with MobStacker is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MobStacker.getName());

			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

			Bukkit.getLogger().info("[MobHunting] Enabling Compatibility with MobStacker ("
					+ mPlugin.getDescription().getVersion() + ")");
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public static Plugin getMobstacker() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMobStacker;
	}

	public static boolean isStackedMob(Entity entity) {
		if (isSupported())
			return entity.hasMetadata("max-stack");
		return false;
		// return entity.hasMetadata("quantity"); // quantity is removed from
		// the dead entity
		// return StackUtils.hasRequiredData(entity);
	}

	public static int getStackSize(Entity deadEntity) {
		return StackUtils.getStackSize((LivingEntity) deadEntity);
	}

	public static boolean killHoleStackOnDeath(Entity entity) {
		// DeathReasons
		// https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html
		List<String> validDeathReasons = mPlugin.getConfig().getStringList("kill-whole-stack-on-death.reasons");
		return mPlugin.getConfig().getBoolean("kill-whole-stack-on-death.enable")
				&& validDeathReasons.contains(entity.getLastDamageCause().getCause().name());
	}

	public static boolean multiplyLoot() {
		return mPlugin.getConfig().getBoolean("kill-whole-stack-on-death.multiply-loot");
	}

	public static boolean isGrindingStackedMobsAllowed() {
		return MobHunting.getInstance().getConfigManager().isGrindingStackedMobsAllowed;
	}

}
