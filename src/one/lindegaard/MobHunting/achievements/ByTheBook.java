package one.lindegaard.MobHunting.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.events.MobHuntKillEvent;

public class ByTheBook implements Achievement, Listener {

	private MobHunting plugin;

	public ByTheBook(MobHunting plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.bythebook.name");
	}

	@Override
	public String getID() {
		return "bythebook"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.bythebook.description");
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialByTheBook;
	}

	@EventHandler
	public void onKill(MobHuntKillEvent event) {
		//TODO: check Materials
		if ((event.getDamageInfo().getWeapon().getType() == Material.BOOK
				|| event.getDamageInfo().getWeapon().getType() == Material.WRITTEN_BOOK
				//|| event.getDamageInfo().getWeapon().getType() == Material.BOOK_AND_QUILL)
				|| event.getDamageInfo().getWeapon().getType() == Material.matchMaterial("BOOK_AND_QUILL"))
				&& (plugin.getRewardManager().getBaseKillPrize(event.getKilledEntity()) > 0))
			plugin.getAchievementManager().awardAchievement(this, event.getPlayer(),
					plugin.getExtendedMobManager().getExtendedMobFromEntity(event.getKilledEntity()));
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialByTheBookCmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialByTheBookCmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return new ItemStack(Material.BOOK);
	}
}
