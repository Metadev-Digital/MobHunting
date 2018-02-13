package one.lindegaard.MobHunting.achievements;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.events.MobHuntKillEvent;

public class RecordHungry implements Achievement, Listener {

	private MobHunting plugin;

	public RecordHungry(MobHunting plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return plugin.getMessages().getString("achievements.recordhungry.name");
	}

	@Override
	public String getID() {
		return "recordhungry";
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("achievements.recordhungry.description");
	}

	@Override
	public double getPrize() {
		return plugin.getConfigManager().specialRecordHungry;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDeath(MobHuntKillEvent event) {
		if (!(event.getKilledEntity() instanceof Creeper)
				|| !plugin.getMobHuntingManager().isHuntEnabledInWorld(event.getKilledEntity().getWorld())
				|| (plugin.getRewardManager().getBaseKillPrize(event.getKilledEntity()) <= 0))
			return;

		Creeper killed = (Creeper) event.getKilledEntity();

		if (!(killed.getLastDamageCause() instanceof EntityDamageByEntityEvent))
			return;

		EntityDamageByEntityEvent damage = (EntityDamageByEntityEvent) killed.getLastDamageCause();

		if (damage.getDamager() instanceof Arrow && ((Arrow) damage.getDamager()).getShooter() instanceof Skeleton) {
			Skeleton skele = (Skeleton) ((Arrow) damage.getDamager()).getShooter();

			if (killed.getTarget() instanceof Player) {
				Player target = (Player) killed.getTarget();

				if (skele.getTarget() == target && target.getGameMode() != GameMode.CREATIVE
						&& plugin.getMobHuntingManager().isHuntEnabled(target))
					plugin.getAchievementManager().awardAchievement(this, target,
							plugin.getExtendedMobManager().getExtendedMobFromEntity(event.getKilledEntity()));
			}
		}
	}

	@Override
	public String getPrizeCmd() {
		return plugin.getConfigManager().specialRecordHungryCmd;
	}

	@Override
	public String getPrizeCmdDescription() {
		return plugin.getConfigManager().specialRecordHungryCmdDesc;
	}

	@Override
	public ItemStack getSymbol() {
		return new ItemStack(Material.BREAD);
	}
}
