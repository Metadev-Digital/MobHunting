package one.lindegaard.MobHunting.compatibility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.garbagemule.MobArena.events.ArenaPlayerDeathEvent;
import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.garbagemule.MobArena.events.ArenaKillEvent;
import com.garbagemule.MobArena.events.ArenaCompleteEvent;
import com.garbagemule.MobArena.events.ArenaEndEvent;
import com.garbagemule.MobArena.events.ArenaPlayerReadyEvent;
import com.garbagemule.MobArena.events.ArenaStartEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;

import one.lindegaard.Core.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;

public class MobArenaCompat implements Listener {

	private static Plugin mPlugin;
	private static List<UUID> playersPlayingMobArena = new ArrayList<UUID>();
	private static boolean supported = false;

	public MobArenaCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with MobArena is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MobArena.getName());

			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Enabling compatibility with MobArena (" + getMobArena().getDescription().getVersion() + ")");
			supported = true;
		}
	}

	// **************************************************************************
	// OTHER
	// **************************************************************************

	public Plugin getMobArena() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	private static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMobArena;
	}

	/**
	 * Determine if the player is currently playing MobArena
	 * 
	 * @param player
	 * @return Returns true when the player is in game.
	 */
	public static boolean isPlayingMobArena(Player player) {
		if (isSupported())
			return playersPlayingMobArena.contains(player.getUniqueId());
		return false;
	}

	/**
	 * Add the player to the list of active MobArena players.
	 * 
	 * @param player
	 */
	public static void startPlayingMobArena(Player player) {
		playersPlayingMobArena.add(player.getUniqueId());
	}

	/**
	 * Remove the player from list of active MobArena players
	 * 
	 * @param player
	 */
	public static void stopPlayingMobArena(Player player) {
		if (!playersPlayingMobArena.remove(player.getUniqueId())) {
			MobHunting.getInstance().getMessages().debug("Player: %s is not playing MobArena", player.getName());
		}
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************
	// Happens when the player joins the Arena /ma join
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaPlayerJoinEvent(ArenaPlayerJoinEvent event) {
		MobHunting.getInstance().getMessages().debug("Player %s joined MobArena: %s", event.getPlayer().getName(),
				event.getArena());
		startPlayingMobArena(event.getPlayer());
	}

	// Happens when the player leave the Arena /ma leave
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaPlayerLeaveEvent(ArenaPlayerLeaveEvent event) {
		MobHunting.getInstance().getMessages().debug("Player %s left MobArena: %s", event.getPlayer().getName(),
				event.getArena());
		stopPlayingMobArena(event.getPlayer());
	}

	// Happens when the player dies
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaPlayerDeathEvent(ArenaPlayerDeathEvent event) {
		Player player = event.getPlayer();
		if (playersPlayingMobArena.remove(player.getUniqueId())) {
			MobHunting.getInstance().getMessages().debug("Player: %s died while playing MobArena", player.getName());
		}
	}

	// Happens when the player hits the Iron block (waiting for other player to
	// do the same)
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaPlayerReadyEvent(ArenaPlayerReadyEvent event) {
	}

	// Happens when???
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaCompleteEvent(ArenaCompleteEvent event) {
	}

	// Happens when a/the player kill a Mob
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaKillEvent(ArenaKillEvent event) {
	}

	// Happens when the all players are ready and they enter the Arena
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaStartEvent(ArenaStartEvent event) {
	}

	// Happens when the all players are dead and in "Jail"
	@EventHandler(priority = EventPriority.NORMAL)
	private void onArenaEndEvent(ArenaEndEvent event) {
	}

	// Happens everytime a new wave begin
	@EventHandler(priority = EventPriority.NORMAL)
	private void onNewWareEvent(NewWaveEvent event) {
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (playersPlayingMobArena.remove(player.getUniqueId())) {
			MobHunting.getInstance().getMessages().debug("Player: %s left the game while playing MobArena", player.getName());
		}
	}
	
	

}
