package one.lindegaard.MobHunting.achievements;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.events.MobHuntKillEvent;

public class MasterSniper implements Achievement, Listener {

	private MobHunting plugin;

	public MasterSniper(MobHunting plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.master-sniper.name");
	}

	@Override
	public String getID() {
		return "master-sniper";
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.master-sniper.description");
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialMasterSniper;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKillCompleted(MobHuntKillEvent event) {
		if (event.getPlayer().isInsideVehicle() && event.getDamageInfo().getWeapon().getType() == Material.BOW
				&& !event.getDamageInfo().isMeleWeapenUsed()
				&& event.getPlayer().getVehicle().getVelocity().length() > 0.2
				&& plugin.getRewardManager().getBaseKillPrize(event.getKilledEntity()) > 0) {
			double dist = event.getDamageInfo().getAttackerPosition().distance(event.getKilledEntity().getLocation());
			if (dist >= 40) {
				plugin.getAchievementManager().awardAchievement(this, event.getPlayer(),
						plugin.getExtendedMobManager().getExtendedMobFromEntity(event.getKilledEntity()));
			}
		}
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialMasterSniperCmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialMasterSniperCmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return new ItemStack(Material.BOW);
	}
}
