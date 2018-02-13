package one.lindegaard.MobHunting.modifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import one.lindegaard.MobHunting.DamageInformation;
import one.lindegaard.MobHunting.HuntData;
import one.lindegaard.MobHunting.MobHunting;

public class GrindingPenalty implements IModifier
{

	@Override
	public String getName()
	{
		return ChatColor.RED + MobHunting.getInstance().getMessages().getString("penalty.grinding.name");  //$NON-NLS-1$
	}

	@Override
	public double getMultiplier(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo, EntityDamageByEntityEvent lastDamageCause)
	{
		return data.getDampnerMultiplier();
	}

	@Override
	public boolean doesApply(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo, EntityDamageByEntityEvent lastDamageCause )
	{
		if(MobHunting.getInstance().getConfigManager().grindingDetectionEnabled && !MobHunting.getInstance().getGrindingManager().isWhitelisted(deadEntity.getLocation()))
			return data.getDampnerMultiplier() < 1;
		return false;
	}

}
