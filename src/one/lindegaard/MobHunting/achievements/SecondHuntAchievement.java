package one.lindegaard.MobHunting.achievements;

import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.mobs.ExtendedMob;
import one.lindegaard.MobHunting.mobs.MobPlugin;

public class SecondHuntAchievement implements ProgressAchievement {
	private MobHunting plugin;
	private ExtendedMob mExtendedMob;

	public SecondHuntAchievement(MobHunting plugin, ExtendedMob extendedMob) {
		this.plugin=plugin;
		mExtendedMob = extendedMob;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.hunter.2.name", "mob", mExtendedMob.getFriendlyName());
	}

	@Override
	public String getID() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level2-" + mExtendedMob.getMobName().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level2-" + mExtendedMob.getMobtype().toLowerCase();

	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.hunter.2.description", "count", getNextLevel(), "mob",
				mExtendedMob.getFriendlyName());
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialHunter2;
	}

	@Override
	public int getNextLevel() {
		return (int) Math.round(mExtendedMob.getProgressAchievementLevel1() * 2.5);
	}

	@Override
	public String inheritFrom() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level1-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level1-" + mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String nextLevelId() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level3-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level3-" + mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialHunter2Cmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialHunter2CmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return mExtendedMob.getCustomHead(plugin, mExtendedMob.getMobName(), 2, 0);
	}

	@Override
	public ExtendedMob getExtendedMob() {
		return mExtendedMob;
	}
}
