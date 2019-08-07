package one.lindegaard.MobHunting.achievements;

import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.mobs.ExtendedMob;
import one.lindegaard.MobHunting.mobs.MobPlugin;

public class FourthHuntAchievement implements ProgressAchievement {
	
	private MobHunting plugin;
	private ExtendedMob mExtendedMob;

	public FourthHuntAchievement(MobHunting plugin, ExtendedMob entity) {
		this.plugin=plugin;
		mExtendedMob = entity;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.hunter.4.name", "mob", mExtendedMob.getFriendlyName());
	}

	@Override
	public String getID() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level4-" + mExtendedMob.getMobName().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level4-" + mExtendedMob.getMobtype().toLowerCase();

	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.hunter.4.description", "count", getNextLevel(), "mob",
				mExtendedMob.getFriendlyName());
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialHunter4;
	}

	@Override
	public int getNextLevel() {
		return mExtendedMob.getProgressAchievementLevel1() * 10;
	}

	@Override
	public String inheritFrom() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level3-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level3-" + mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String nextLevelId() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level5-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level5-" + mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialHunter4Cmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialHunter4CmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return mExtendedMob.getInventoryAchivementItem(mExtendedMob.getMobName(), 4, 0);
	}

	@Override
	public ExtendedMob getExtendedMob() {
		return mExtendedMob;
	}
}
