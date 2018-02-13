package one.lindegaard.MobHunting.modifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import one.lindegaard.MobHunting.DamageInformation;
import one.lindegaard.MobHunting.HuntData;
import one.lindegaard.MobHunting.MobHunting;

public class SniperBonus implements IModifier {

	@Override
	public String getName() {
		return ChatColor.GRAY + MobHunting.getInstance().getMessages().getString("bonus.sniper.name");
	}

	@Override
	public double getMultiplier(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo,
			EntityDamageByEntityEvent lastDamageCause) {
		return 1 + ((MobHunting.getInstance().getConfigManager().bonusFarShot - 1) / 2);
	}

	@Override
	public boolean doesApply(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo,
			EntityDamageByEntityEvent lastDamageCause) {
		if (extraInfo.getWeapon().getType() == Material.BOW && !extraInfo.isMeleWeapenUsed()) {
			double dist = extraInfo.getAttackerPosition().distance(deadEntity.getLocation());
			if (dist >= 20 && dist < 50)
				return true;
		}

		return false;
	}

}
