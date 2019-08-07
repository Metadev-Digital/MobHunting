package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import one.lindegaard.Core.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

@SuppressWarnings("deprecation")
public class TitleManagerCompat {

	private static Plugin mPlugin;
	private static TitleManagerAPI api;
	private static boolean supported = false;

	// https://www.spigotmc.org/resources/titlemanager.1049/

	public TitleManagerCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with TitleManager is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.TitleManager.getName());
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Enabling compatibility with TitleManager ("
					+ mPlugin.getDescription().getVersion() + ")");
			if (mPlugin.getDescription().getVersion().compareTo("2.0") >= 0)
				api = getTitleManagerAPI();
			else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RED
						+ "You are using an old version of TitleManager. Consider updating.");
			}
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public TitleManagerAPI getTitleManagerAPI() {
		return (TitleManagerAPI) mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationTitleManager;
	}

	public static void setActionBar(Player player, String message) {
		if (supported) {

			if (api != null) {
				api.sendActionbar(player, message);
			} else {
				ActionbarTitleObject actionbar = new ActionbarTitleObject(message);
				actionbar.send(player);
			}
		}
	}

	@SuppressWarnings("unused")
	private static void sendTitles(Player player, String title, String subtitle, int fadein, int stay, int fadeout) {
		if (supported) {

			if (api != null) {
				api.sendTitles(player, title, subtitle, fadein, stay, fadeout);

			} else {
				TitleObject titleObject = new TitleObject(title, subtitle);
				titleObject.send(player);
			}
		}
	}

}
