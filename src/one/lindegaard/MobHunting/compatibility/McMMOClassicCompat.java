package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingEvent;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerMagicHunterEvent;
import com.gmail.nossr50.events.skills.fishing.McMMOPlayerShakeEvent;

import one.lindegaard.Core.Materials.Materials;
import one.lindegaard.MobHunting.DamageInformation;
import one.lindegaard.MobHunting.MobHunting;

public class McMMOClassicCompat implements Listener {

	private static boolean supported = false;
	private static Plugin mPlugin;
	public static final String MH_MCMMO = "MH:MCMMO";

	public McMMOClassicCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with McMMO is disabled in config.yml");
		} else {
			mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.mcMMO.getName());

			if (mPlugin.getDescription().getVersion().compareTo("2.0") >= 0) {
				Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with McMMO (" + getMcMmoAPI().getDescription().getVersion() + ")");
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET + "McMMO Level rewards is "
								+ (MobHunting.getInstance().getConfigManager().enableMcMMOLevelRewards ? "enabled"
										: "disabled"));
				supported = true;
			} else if (mPlugin.getDescription().getVersion().compareTo("1.5.00") >= 0) {
				Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
				Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
						+ "Enabling compatibility with McMMO (" + getMcMmoAPI().getDescription().getVersion() + ")");
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET + "McMMO Level rewards is "
								+ (MobHunting.getInstance().getConfigManager().enableMcMMOLevelRewards ? "enabled"
										: "disabled"));
				supported = true;
			} else {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				console.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RED + "Your current version of McMMO ("
						+ mPlugin.getDescription().getVersion()
						+ ") is not supported by MobHunting. Please update McMMO to version 1.5.00 or newer.");
			}
		}

	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public static Plugin getMcMmoAPI() {
		return mPlugin;
	}

	public static boolean isSupported() {
		return supported;
	}

	public static boolean isMcMMO(Entity entity) {
		if (isSupported())
			return entity.hasMetadata(MH_MCMMO);
		return false;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationMcMMO;
	}

	public static String getSKillTypeName(DamageInformation info) {
		SkillType skilltype = null;
		if (Materials.isAxe(info.getWeapon()))
			skilltype = SkillType.AXES;
		else if (Materials.isSword(info.getWeapon()))
			skilltype = SkillType.SWORDS;
		else if (Materials.isBow(info.getWeapon()))
			skilltype = SkillType.ARCHERY;
		else if (Materials.isUnarmed(info.getWeapon()))
			skilltype = SkillType.UNARMED;
		return skilltype.getName();
	}
	
	public static void addXP2(Player player, String skillType, int XP, String xpGainReason) {
		ExperienceAPI.addXP(player, skillType, XP, xpGainReason);
	}

	public static void addLevel(Player player, String skillType, int levels) {
		ExperienceAPI.addLevel(player, skillType, levels);
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void Fish2(McMMOPlayerFishingTreasureEvent event) {
		Player p = event.getPlayer();
		ItemStack s = event.getTreasure();
		MobHunting.getInstance().getMessages().debug("McMMO-FishingEvent1: %s caught a %s", p.getName(), s.getType());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void Fish3(McMMOPlayerFishingEvent event) {
		Player p = event.getPlayer();
		MobHunting.getInstance().getMessages().debug("McMMO-FishingEvent2: %s is fishing", p.getName());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void Fish4(McMMOPlayerMagicHunterEvent event) {
		Player p = event.getPlayer();
		ItemStack is = event.getTreasure();
		MobHunting.getInstance().getMessages().debug("McMMO-FishingEvent3: %s, Treasure = %s", p.getName(),
				is.getType());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void Fish5(McMMOPlayerShakeEvent event) {
		Player p = event.getPlayer();
		ItemStack is = event.getDrop();
		MobHunting.getInstance().getMessages().debug("McMMO-FishingEvent4: %s, Drop = %s", p.getName(), is.getType());
	}

}
