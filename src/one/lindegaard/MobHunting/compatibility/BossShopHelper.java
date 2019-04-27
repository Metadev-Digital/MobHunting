package one.lindegaard.MobHunting.compatibility;

import java.util.UUID;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.BSShop;
import org.black_ixx.bossshop.core.enums.BSBuyType;
import org.black_ixx.bossshop.core.enums.BSPriceType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.Core.rewards.CustomItems;
import one.lindegaard.Core.rewards.Reward;

public class BossShopHelper {

	public static boolean openShop(MobHunting plugin, Player p, String shop_name) {

		//BossShopCompat.getBossShop().getAPI().
		BSShop shop = BossShopCompat.getBossShop().getAPI().getShop(shop_name);

		if (shop == null) {
			p.sendMessage(ChatColor.RED + "Shop " + shop_name + " not found...");
			return false;
		}

		BSBuy buy = BossShopCompat.getBossShop().getAPI().createBSBuy(BSBuyType.Money, BSPriceType.Free, 0,
				null, null, 3, null);
		BSBuy sell = BossShopCompat.getBossShop().getAPI().createBSBuy(BSBuyType.Money, BSPriceType.Money, 1,
				10, "bought bag of gold", 4, null);
		
		BossShopCompat.getBossShop().getAPI().openShop(p, shop);

		UUID uuid = UUID.fromString(Reward.MH_REWARD_BAG_OF_GOLD_UUID);
		
		ItemStack is = new CustomItems().getCustomtexture(uuid,
				MobHunting.getInstance().getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
				MobHunting.getInstance().getConfigManager().dropMoneyOnGroundSkullTextureValue,
				MobHunting.getInstance().getConfigManager().dropMoneyOnGroundSkullTextureSignature, 10, UUID.randomUUID(), uuid);

		// ItemStack menu_item = new ItemStack(is);
		BossShopCompat.getBossShop().getAPI().addItemToShop(is, buy, shop);
		BossShopCompat.getBossShop().getAPI().addItemToShop(is, sell, shop);

		BossShopCompat.getBossShop().getAPI().finishedAddingItemsToShop(shop);
		
		return true;
	}

}
