package metadev.digital.MetaMobHunting.achievements;

import org.bukkit.inventory.ItemStack;

import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.mobs.ExtendedMob;
import metadev.digital.MetaMobHunting.mobs.MobPlugin;

public class BasicHuntAchievement implements ProgressAchievement {

	private MobHunting plugin;
	private ExtendedMob mExtendedMob;

	public BasicHuntAchievement(MobHunting plugin, ExtendedMob extendedMob) {
		this.plugin = plugin;
		mExtendedMob = extendedMob;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.hunter.1.name", "mob", mExtendedMob.getLocalizedName());
	}

	@Override
	public String getID() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level1-" + mExtendedMob.getMobName().toLowerCase();
		else
			return mExtendedMob.getMobPlugin().name().toLowerCase() + "-hunting-level1-"
					+ mExtendedMob.getMobtype().toLowerCase();

	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.hunter.1.description", "count", getNextLevel(), "mob",
				mExtendedMob.getLocalizedName());
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialHunter1;
	}

	@Override
	public int getNextLevel() {
		return mExtendedMob.getProgressAchievementLevel1();
	}

	@Override
	public String inheritFrom() {
		return null;
	}

	@Override
	public String nextLevelId() {
		if (mExtendedMob.getMobPlugin() == MobPlugin.Minecraft)
			return "hunting-level2-" + mExtendedMob.getMobtype().toLowerCase();
		else
			return mExtendedMob.getMobPlugin() + "-hunting-level2-" + mExtendedMob.getMobtype().toLowerCase();
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialHunter1Cmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialHunter1CmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return mExtendedMob.getInventoryAchivementItem(mExtendedMob.getMobName(), 1, 0);
	}

	@Override
	public ExtendedMob getExtendedMob() {
		return mExtendedMob;
	}
}
