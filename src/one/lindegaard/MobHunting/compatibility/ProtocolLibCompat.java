package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import one.lindegaard.CustomItemsLib.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

public class ProtocolLibCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;

	// https://www.spigotmc.org/resources/protocollib.1997/

	public ProtocolLibCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with ProtocolLib is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.ProtocolLib.getName());
			if (mPlugin.getDescription().getVersion().compareTo("4.4.0") < 0) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RED
								+ "Your current version of ProtocolLib (" + mPlugin.getDescription().getVersion()
								+ ") is not supported by MobHunting, please upgrade to 4.4.0 or newer.");
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with ProtocolLib (" + mPlugin.getDescription().getVersion() + ").");
				ProtocolLibHelper.enableProtocolLib();
				supported = true;
			}
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getProtocoloLib() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationProtocolLib;
	}

}
