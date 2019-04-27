package one.lindegaard.MobHunting.achievements;

import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.mobs.ExtendedMob;
import one.lindegaard.MobHunting.mobs.MobPlugin;

public class SeventhHuntAchievement implements ProgressAchievement {

	private MobHunting plugin;
	private ExtendedMob mExtendedMob;

	public SeventhHuntAchievement(MobHunting plugin, ExtendedMob extendedMob) {
		this.plugin = plugin;
		mExtendedMob = extendedMob;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.hunter.7.name", "mob", mExtendedMob.getFriendlyName());
	}

	@Override
	public String getID() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level7-" + mExtendedMob.getMobName().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level7-"
					+ mExtendedMob.getMobtype().toLowerCase();

	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.hunter.7.description", "count", getNextLevel(), "mob",
				mExtendedMob.getFriendlyName());
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialHunter7;
	}

	@Override
	public int getNextLevel() {
		return mExtendedMob.getProgressAchievementLevel1() * 100;
	}

	@Override
	public String inheritFrom() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level6-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level6-"
					+ mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String nextLevelId() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level8-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level8-"
					+ mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialHunter7Cmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialHunter7CmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return mExtendedMob.getCustomHead(mExtendedMob.getMobName(), 7, 0);
	}

	@Override
	public ExtendedMob getExtendedMob() {
		return mExtendedMob;
	}

}
