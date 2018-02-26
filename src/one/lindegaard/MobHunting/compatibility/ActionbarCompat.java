package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import one.lindegaard.MobHunting.MobHunting;

public class ActionbarCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;

	// http://dev.bukkit.org/bukkit-plugins/actionbar/
	// https://www.spigotmc.org/resources/actionbar.1458/

	public ActionbarCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender()
					.sendMessage("[MobHunting] Compatibility with Actionbar is disabled in config.yml ");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.Actionbar.getName());

			Bukkit.getConsoleSender().sendMessage("[MobHunting] Enabling compatibility with Actionbar ("
					+ getActionbar().getDescription().getVersion() + ")");
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getActionbar() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationActionbar;
	}

	public static void setMessage(Player player, String text) {
		if (supported) {
			MobHunting.getInstance().getMessages().debug(
					"[WARNING] ActionbarCompat: setMessage() is not made yet. I cant get access to source code or API.");
			player.sendMessage(text);

			// Show a specific actionbar group
			// showActionbar(Player player, String actionbar);

			// Remove a specific actionbar group previously shown using the show
			// command, API, announcement or trigger eve
			// removeActionbarOverride(Player player, String actionbar);

			// Removes all overrides.​
			// resetDefaultActionbar(Player player);
		}
	}

}
