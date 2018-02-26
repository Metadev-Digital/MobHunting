package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;

import one.lindegaard.MobHunting.MobHunting;

public class HologramsCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;

	// https://www.spigotmc.org/resources/holograms.4924/

	public HologramsCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender()
					.sendMessage("[MobHunting] Compatibility with Holograms is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.Holograms.getName());

			Bukkit.getConsoleSender().sendMessage("[MobHunting] Enabling compatibility with Holograms ("
					+ mPlugin.getDescription().getVersion() + ").");

			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getHologramsPlugin() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationHolograms;
	}

	public static HologramManager getHologramManager() {
		return ((HologramPlugin) mPlugin).getHologramManager();
	}


}
