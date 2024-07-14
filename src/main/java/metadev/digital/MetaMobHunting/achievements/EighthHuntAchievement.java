package metadev.digital.MetaMobHunting.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.events.MobHuntFishingEvent;
import metadev.digital.MetaMobHunting.events.MobHuntKillEvent;
import metadev.digital.MetaMobHunting.mobs.ExtendedMob;
import metadev.digital.MetaMobHunting.mobs.MobPlugin;

public class EighthHuntAchievement implements ProgressAchievement, Listener {

	private MobHunting plugin;
	private ExtendedMob mExtendedMob;

	public EighthHuntAchievement(MobHunting plugin, ExtendedMob extendedMob) {
		this.plugin = plugin;
		mExtendedMob = extendedMob;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.hunter.8.name", "mob", mExtendedMob.getLocalizedName());
	}

	@Override
	public String getID() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level8-" + mExtendedMob.getMobName().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level8-"
					+ mExtendedMob.getMobtype().toLowerCase();

	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.hunter.8.description", "count", getNextLevel(), "mob",
				mExtendedMob.getLocalizedName());
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialHunter8;
	}

	@Override
	public int getNextLevel() {
		return mExtendedMob.getProgressAchievementLevel1() * 500;
	}

	@Override
	public String inheritFrom() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level7-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level7-"
					+ mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String nextLevelId() {
		return null;
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialHunter8Cmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialHunter8CmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return mExtendedMob.getInventoryAchivementItem(mExtendedMob.getMobName(), 8, 0);
	}

	@Override
	public ExtendedMob getExtendedMob() {
		return mExtendedMob;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKillCompleted(MobHuntKillEvent event) {
		if (mExtendedMob.matches(event.getKilledEntity()))
			plugin.getAchievementManager().awardAchievementProgress(this, event.getPlayer(),
					plugin.getExtendedMobManager().getExtendedMobFromEntity(event.getKilledEntity()), 1);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onFishingCompleted(MobHuntFishingEvent event) {
		if (mExtendedMob.matches(event.getFish())) {
			plugin.getAchievementManager().awardAchievementProgress(this, event.getPlayer(),
					plugin.getExtendedMobManager().getExtendedMobFromEntity(event.getFish()), 1);
		}
	}
}
