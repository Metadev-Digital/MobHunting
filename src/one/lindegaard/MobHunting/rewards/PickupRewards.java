package one.lindegaard.MobHunting.rewards;

import one.lindegaard.Core.rewards.Reward;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.compatibility.BagOfGoldCompat;
import one.lindegaard.MobHunting.compatibility.ProtocolLibCompat;
import one.lindegaard.MobHunting.compatibility.ProtocolLibHelper;
import one.lindegaard.MobHunting.util.Misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class PickupRewards {

	private MobHunting plugin;

	public PickupRewards(MobHunting plugin) {
		this.plugin = plugin;
	}

	public void rewardPlayer(Player player, Item item, CallBack callBack) {
		if (Reward.isReward(item)) {
			double done = 0;
			Reward reward = Reward.getReward(item);
			if (reward.isBagOfGoldReward() || reward.isItemReward()) {
				callBack.setCancelled(true);
				if (BagOfGoldCompat.isSupported()) {
					done = plugin.getRewardManager().getEconomy().depositPlayer(player, reward.getMoney()).amount;
				} else if (reward.getMoney() != 0 && !plugin.getConfigManager().dropMoneyOnGroundUseItemAsCurrency) {
							// If not Gringotts
									done = plugin.getRewardManager().depositPlayer(player, reward.getMoney()).amount;
									} else {
					done = plugin.getRewardManager().addBagOfGoldPlayer(player, reward.getMoney());
				}
			}
			if (done > 0) {
				item.remove();
				if (plugin.getRewardManager().getDroppedMoney().containsKey(item.getEntityId()))
					plugin.getRewardManager().getDroppedMoney().remove(item.getEntityId());
				if (ProtocolLibCompat.isSupported())
					ProtocolLibHelper.pickupMoney(player, item);

				if (reward.getMoney() == 0) {
					plugin.getMessages().debug("%s picked up a %s (# of rewards left=%s)", player.getName(),
							plugin.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM") ? "ITEM"
									: reward.getDisplayname(),
							plugin.getRewardManager().getDroppedMoney().size());
				} else {
					plugin.getMessages().debug(
							"%s picked up a %s with a value:%s (# of rewards left=%s)(PickupRewards)", player.getName(),
							plugin.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM") ? "ITEM"
									: reward.getDisplayname(),
							plugin.getRewardManager().format(Misc.round(reward.getMoney())),
							plugin.getRewardManager().getDroppedMoney().size());
					plugin.getMessages().playerActionBarMessageQueue(player,
							plugin.getMessages().getString("mobhunting.moneypickup", "money",
									plugin.getRewardManager().format(reward.getMoney()), "rewardname",
									ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
											+ (reward.getDisplayname().isEmpty()
													? plugin.getConfigManager().dropMoneyOnGroundSkullRewardName
													: reward.getDisplayname())));

				}
			}
		}
	}

	public interface CallBack {

		void setCancelled(boolean canceled);

	}

}
