package one.lindegaard.MobHunting.modifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import one.lindegaard.MobHunting.DamageInformation;
import one.lindegaard.MobHunting.HuntData;
import one.lindegaard.MobHunting.MobHunting;

public class FriendleFireBonus implements IModifier
{
	@Override
	public String getName()
	{
		return ChatColor.DARK_GREEN + MobHunting.getInstance().getMessages().getString("bonus.friendlyfire.name"); //$NON-NLS-1$
	}

	@Override
	public double getMultiplier( Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo, EntityDamageByEntityEvent lastDamageCause )
	{
		return MobHunting.getInstance().getConfigManager().bonusFriendlyFire;
	}

	@Override
	public boolean doesApply(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo, EntityDamageByEntityEvent lastDamageCause )
	{
		if(lastDamageCause == null)
			return false;
		if(lastDamageCause.getDamager() instanceof Monster || (lastDamageCause.getDamager() instanceof Projectile && (((Projectile)lastDamageCause.getDamager()).getShooter() instanceof Monster || ((Projectile)lastDamageCause.getDamager()).getShooter() instanceof Ghast)))
			return true;
		return false;
	}

}
