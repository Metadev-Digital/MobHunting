package metadev.digital.MetaMobHunting.modifier;

import java.util.Iterator;
import java.util.Map.Entry;

import metadev.digital.MetaMobHunting.Messages.MessageHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import metadev.digital.MetaMobHunting.DamageInformation;
import metadev.digital.MetaMobHunting.HuntData;
import metadev.digital.MetaMobHunting.MobHunting;

public class RankBonus implements IModifier {

	@Override
	public String getName() {
		return ChatColor.GRAY + MobHunting.getInstance().getMessages().getString("bonus.rank.name");
	}

	@Override
	public double getMultiplier(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo,
			EntityDamageByEntityEvent lastDamageCause) {
		if (killer!=null && !killer.isOp()) {
			Iterator<Entry<String, String>> ranks = MobHunting.getInstance().getConfigManager().rankMultiplier.entrySet().iterator();
			double mul = 0;
			while (ranks.hasNext()) {
				Entry<String, String> rank = ranks.next();
				if (!rank.getKey().equalsIgnoreCase("mobhunting")
						&& !rank.getKey().equalsIgnoreCase("mobhunting.multiplier")) {
					if (killer.hasPermission(rank.getKey())) {
						mul = (Double.valueOf(rank.getValue()) > mul) ? Double.valueOf(rank.getValue()) : mul;
					}
				}
			}
			mul = (mul == 0) ? 1 : mul;
			return mul;
		} else if (MobHunting.getInstance().getConfigManager().rankMultiplier.containsKey("mobhunting.multiplier.op"))
			return Double.valueOf(MobHunting.getInstance().getConfigManager().rankMultiplier.get("mobhunting.multiplier.op"));
		return 1;
	}

	@Override
	public boolean doesApply(Entity deadEntity, Player killer, HuntData data, DamageInformation extraInfo,
			EntityDamageByEntityEvent lastDamageCause) {
		if (killer!=null && !killer.isOp()) {
			Iterator<Entry<String, String>> ranks = MobHunting.getInstance().getConfigManager().rankMultiplier.entrySet().iterator();
			boolean hasRank = false;
			while (ranks.hasNext()) {
				Entry<String, String> rank = ranks.next();
				if (!rank.getKey().equalsIgnoreCase("mobhunting")
						&& !rank.getKey().equalsIgnoreCase("mobhunting.multiplier")) {
					if (killer.hasPermission(rank.getKey())) {
						// MobHunting.getInstance().getMessages().debug("RankMultiplier Key=%s Value=%s",
						// rank.getKey(), rank.getValue());
						hasRank = true;
					}
				}
			}
			return hasRank;
		} else if (MobHunting.getInstance().getConfigManager().rankMultiplier.containsKey("mobhunting.multiplier.op")) {
			MessageHelper.debug("RankMultiplier Key=mobhunting.multiplier.op Value=%s Player is OP",
					MobHunting.getInstance().getConfigManager().rankMultiplier.get("mobhunting.multiplier.op"));
			return true;
		}
		MessageHelper.debug("%s has no Rank Multiplier", killer.getName());
		return false;
	}
}
