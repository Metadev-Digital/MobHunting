package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.field.FieldFlag;
import one.lindegaard.CustomItemsLib.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

public class PreciousStonesCompat {

	private static Plugin mPlugin;
	private static boolean supported = false;

	// https://www.spigotmc.org/resources/preciousstones.5270/

	public PreciousStonesCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with PreciousStones is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.PreciousStones.getName());

			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Enabling compatibility with PreciousStones (" + mPlugin.getDescription().getVersion() + ").");
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getPlugin() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationPreciousStones;
	}

	// Flag references
	// https://github.com/marcelo-mason/PreciousStones/wiki/Flag-Reference

	public static boolean isMobDamageProtected(Player player) {
		if (supported) {
			PreciousStones.API().flagAppliesToPlayer(player, FieldFlag.PREVENT_MOB_DAMAGE, player.getLocation());
		}
		return false;
	}

	public static boolean isPVPProtected(Player player) {
		if (supported) {
			PreciousStones.API().flagAppliesToPlayer(player, FieldFlag.PREVENT_PVP, player.getLocation());
		}
		return false;
	}

}
