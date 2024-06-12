package metadev.digital.MetaMobHunting.compatibility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.comze_instancelabs.minigamesapi.events.PlayerJoinLobbyEvent;
import com.comze_instancelabs.minigamesapi.events.PlayerLeaveArenaEvent;

import metadev.digital.metacustomitemslib.compatibility.CompatPlugin;
import metadev.digital.MetaMobHunting.MobHunting;

public class MinigamesLibCompat implements Listener {

	private static boolean supported = false;
	private static Plugin mPlugin;
	private static List<UUID> playersPlayingMinigames = new ArrayList<UUID>();
	public static final String MH_MINIGAMESLIB = "MH:MINIGAMESLIB";

	// https://www.spigotmc.org/resources/minigameslib.23844/

	public MinigamesLibCompat() {

		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(
					MobHunting.PREFIX_WARNING + "Compatibility with MinigamesLib is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MinigamesLib.getName());
			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
			Bukkit.getConsoleSender().sendMessage(MobHunting.PREFIX + "Enabling compatibility with MinigamesLib (v"
					+ mPlugin.getDescription().getVersion() + ")");
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************
	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMinigamesLib;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isPlayingMinigame(Player player) {

		if (isSupported())
			return playersPlayingMinigames.contains(player.getUniqueId());
		return false;
	}

	// **************************************************************************
	// EVENTS
	// https://github.com/instance01/MinigamesAPI/tree/master/API/src/main/java/com/comze_instancelabs/minigamesapi/events
	// **************************************************************************
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoinLobby(PlayerJoinLobbyEvent event) {
		MobHunting.getInstance().getMessages().debug("PlayerJoinLobbyEvent was run...");
		Player player = event.getPlayer();
		playersPlayingMinigames.add(player.getUniqueId());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
		MobHunting.getInstance().getMessages().debug("PlayerLeave was run...");
		Player player = event.getPlayer();
		if (!playersPlayingMinigames.remove(player.getUniqueId())) {
			MobHunting.getInstance().getMessages().debug("Player: %s is not in MiniGamesLib", player.getName());
		}
	}

}
