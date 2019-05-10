package one.lindegaard.MobHunting.rewards;

import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import one.lindegaard.MobHunting.MobHunting;

public class EntityPickupItemEventListener implements Listener {

	// TODO: must be moved to bagofgold

	private PickupRewards pickupRewards;

	public EntityPickupItemEventListener(PickupRewards pickupRewards) {
		this.pickupRewards = pickupRewards;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
		// OBS: EntityPickupItemEvent does only exist in MC1.12 and newer

		// This event is NOT called when the inventory is full.
		if (event.isCancelled())
			return;

		Entity entity = event.getEntity();

		if (event.getEntity().getType() != EntityType.PLAYER) {
			// Entity is not a Player
			MobHunting.getInstance().getMessages().debug("A reward was picked up by a %s", entity.getType());
			if (entity.getType().equals(EntityType.ZOMBIE) || entity.getType().equals(EntityType.SKELETON)
					|| entity.getType().equals(EntityType.PIG_ZOMBIE)
					|| entity.getType().equals(EntityType.WITHER_SKELETON)) {
				MobHunting.getInstance().getMessages().debug("The pickup event was cancelled");
				event.setCancelled(true);
			}
			return;
		}
		if (((Player) entity).getGameMode() == GameMode.SPECTATOR) {
			event.setCancelled(true);
			return;
		} else if (((Player) entity).getInventory().firstEmpty() != -1)
			pickupRewards.rewardPlayer((Player) entity, event.getItem(), event::setCancelled);
		
	}

}
