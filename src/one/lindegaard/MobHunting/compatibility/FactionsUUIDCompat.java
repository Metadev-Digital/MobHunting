package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import one.lindegaard.Core.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

public class FactionsUUIDCompat {

	private static Plugin mPlugin;

	// https://www.spigotmc.org/resources/factionsuuid.1035/

	public FactionsUUIDCompat() {
		mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.Factions.getName());
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getPlugin() {
		return mPlugin;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationFactions;
	}

	public static boolean isInSafeZone(Player player) {
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		Faction faction_home = fplayer.getFaction();
		FLocation flocation = fplayer.getLastStoodAt();
		Faction faction_here = Board.getInstance().getFactionAt(flocation);

		if (faction_home.isSafeZone() || (faction_home != null && faction_home.equals(faction_here))) {
			MobHunting.getInstance().getMessages().debug("player is in home faction / safe zone: %s",
					faction_home.getDescription());
			return true;
		} else {
			MobHunting.getInstance().getMessages().debug("player is NOT in home faction / safe zone .");
			return false;
		}
	}

	public static boolean isInWilderness(Player player) {
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		FLocation flocation = fplayer.getLastStoodAt();
		Faction faction_here = Board.getInstance().getFactionAt(flocation);
		if (faction_here.isWilderness()) {
			MobHunting.getInstance().getMessages().debug("%s is in Wilderness", player.getName());
			return true;
		} else
			return false;
	}

	public static boolean isInWarZone(Player player) {
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		FLocation flocation = fplayer.getLastStoodAt();
		Faction faction_here = Board.getInstance().getFactionAt(flocation);
		if (faction_here.isWarZone()) {
			MobHunting.getInstance().getMessages().debug("%s is in War", player.getName());
			return true;
		}
		return false;
	}
}
