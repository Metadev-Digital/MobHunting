package one.lindegaard.MobHunting.rewards;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import one.lindegaard.MobHunting.Messages;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.compatibility.ProtocolLibCompat;
import one.lindegaard.MobHunting.compatibility.ProtocolLibHelper;
import one.lindegaard.MobHunting.mobs.MinecraftMob;
import one.lindegaard.MobHunting.util.Misc;

public class RewardListeners implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPickupReward(PlayerPickupItemEvent event) {
		// This event is NOT called when the inventory is full.
		if (event.isCancelled())
			return;
		Item item = event.getItem();
		if (Reward.isReward(item)) {
			Reward reward = Reward.getReward(item);
			Player player = event.getPlayer();
			// If not Gringotts
			if (reward.getMoney() != 0)
				if (!MobHunting.getConfigManager().dropMoneyOnGroundUseAsCurrency) {
					MobHunting.getRewardManager().depositPlayer(player, reward.getMoney());
					if (ProtocolLibCompat.isSupported())
						ProtocolLibHelper.pickupMoney(player, item);
					item.remove();
					event.setCancelled(true);
					Messages.playerActionBarMessage(player, Messages.getString("mobhunting.moneypickup", "money",
							MobHunting.getRewardManager().format(reward.getMoney())));
				} else {
					boolean found = false;
					HashMap<Integer, ? extends ItemStack> slots = player.getInventory()
							.all(item.getItemStack().getType());
					for (int slot : slots.keySet()) {
						ItemStack is = player.getInventory().getItem(slot);
						if (Reward.isReward(is)) {
							Reward rewardInSlot = Reward.getReward(is);
							if ((reward.isBagOfGoldReward() || reward.isItemReward())
									&& rewardInSlot.getRewardUUID().equals(reward.getRewardUUID())) {
								ItemMeta im = is.getItemMeta();
								Reward newReward = Reward.getReward(is);
								newReward.setMoney(newReward.getMoney() + reward.getMoney());
								im.setLore(newReward.getHiddenLore());
								String displayName = MobHunting.getConfigManager().dropMoneyOnGroundItemtype
										.equalsIgnoreCase("ITEM")
												? MobHunting.getRewardManager().format(newReward.getMoney())
												: newReward.getDisplayname() + " ("
														+ MobHunting.getRewardManager().format(newReward.getMoney())
														+ ")";
								im.setDisplayName(
										ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
												+ displayName);
								is.setItemMeta(im);
								is.setAmount(1);
								event.setCancelled(true);
								if (ProtocolLibCompat.isSupported())
									ProtocolLibHelper.pickupMoney(player, item);
								item.remove();
								Messages.debug("Added %s to item in slot %s, new value is %s",
										MobHunting.getRewardManager().format(reward.getMoney()), slot,
										MobHunting.getRewardManager().format(newReward.getMoney()));
								found = true;
								break;
							}
						}
					}

					if (!found) {
						ItemStack is = item.getItemStack();
						ItemMeta im = is.getItemMeta();
						String displayName = MobHunting.getConfigManager().dropMoneyOnGroundItemtype
								.equalsIgnoreCase("ITEM") ? MobHunting.getRewardManager().format(reward.getMoney())
										: reward.getDisplayname() + " ("
												+ MobHunting.getRewardManager().format(reward.getMoney()) + ")";
						im.setDisplayName(ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
								+ displayName);
						im.setLore(reward.getHiddenLore());
						is.setItemMeta(im);
						item.setItemStack(is);
						item.setMetadata(RewardManager.MH_REWARD_DATA,
								new FixedMetadataValue(MobHunting.getInstance(), new Reward(reward)));
					}
				}
			if (RewardManager.getDroppedMoney().containsKey(item.getEntityId()))
				RewardManager.getDroppedMoney().remove(item.getEntityId());
			if (reward.getMoney() == 0)
				Messages.debug("%s picked up a %s (# of rewards left=%s)", player.getName(),
						reward.getDisplayname(), RewardManager.getDroppedMoney().size());
			else
				Messages.debug("%s picked up a %s with a value:%s (# of rewards left=%s)", player.getName(),
						reward.getDisplayname(), MobHunting.getRewardManager().format(reward.getMoney()),
						RewardManager.getDroppedMoney().size());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropReward(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;
		Item item = event.getItemDrop();
		// ItemStack is = item.getItemStack();
		Player player = event.getPlayer();
		if (Reward.isReward(item)) {
			Reward reward = Reward.getReward(item);
			double money = reward.getMoney();
			if (money == 0) {
				item.setCustomName(ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
						+ reward.getDisplayname());
				Messages.debug("%s dropped a %s (# of rewards left=%s)", player.getName(), reward.getDisplayname(),
						RewardManager.getDroppedMoney().size());
			} else {
				String displayName = MobHunting.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM")
						? MobHunting.getRewardManager().format(money)
						: reward.getDisplayname() + " (" + MobHunting.getRewardManager().format(money) + ")";
				item.setCustomName(
						ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor) + displayName);
				RewardManager.getDroppedMoney().put(item.getEntityId(), money);
				if (!MobHunting.getConfigManager().dropMoneyOnGroundUseAsCurrency)
					RewardManager.getEconomy().withdrawPlayer(player, money);
				Messages.debug("%s dropped %s money. (# of rewards left=%s)", player.getName(),
						MobHunting.getRewardManager().format(money), RewardManager.getDroppedMoney().size());
				Messages.playerActionBarMessage(player, Messages.getString("mobhunting.moneydrop", "money",
						MobHunting.getRewardManager().format(money)));
			}
			item.setCustomNameVisible(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDespawnRewardEvent(ItemDespawnEvent event) {
		if (event.isCancelled())
			return;

		if (Reward.isReward(event.getEntity())) {
			if (RewardManager.getDroppedMoney().containsKey(event.getEntity().getEntityId())) {
				RewardManager.getDroppedMoney().remove(event.getEntity().getEntityId());
				Messages.debug("The reward was lost - despawned (# of rewards left=%s)",
						RewardManager.getDroppedMoney().size());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryPickupRewardEvent(InventoryPickupItemEvent event) {
		if (event.isCancelled())
			return;

		Item item = event.getItem();
		if (!item.hasMetadata(RewardManager.MH_REWARD_DATA))
			return;

		if (MobHunting.getConfigManager().denyHoppersToPickUpMoney
				&& event.getInventory().getType() == InventoryType.HOPPER) {
			// Messages.debug("A %s tried to pick up the the reward, but this is
			// disabled in config.yml",
			// event.getInventory().getType());
			event.setCancelled(true);
		} else {
			// Messages.debug("The reward was picked up by %s",
			// event.getInventory().getType());
			if (RewardManager.getDroppedMoney().containsKey(item.getEntityId()))
				RewardManager.getDroppedMoney().remove(item.getEntityId());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveOverRewardEvent(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();
		if (player.getInventory().firstEmpty() == -1 && !player.getCanPickupItems()) {
			Iterator<Entity> entityList = ((Entity) player).getNearbyEntities(1, 1, 1).iterator();
			while (entityList.hasNext()) {
				Entity entity = entityList.next();
				if (!(entity instanceof Item))
					continue;
				Item item = (Item) entity;
				ItemStack isOnground = item.getItemStack();
				if (Reward.isReward(isOnground) && RewardManager.getDroppedMoney().containsKey(entity.getEntityId())) {
					Reward rewardOnGround = Reward.getReward(isOnground);
					double moneyOnGround = rewardOnGround.getMoney();
					// If not Gringotts
					if (rewardOnGround.getMoney() != 0) {
						if (ProtocolLibCompat.isSupported())
							ProtocolLibHelper.pickupMoney(player, entity);
						if (RewardManager.getDroppedMoney().containsKey(entity.getEntityId()))
							RewardManager.getDroppedMoney().remove(entity.getEntityId());
						if (!MobHunting.getConfigManager().dropMoneyOnGroundUseAsCurrency) {
							MobHunting.getRewardManager().depositPlayer(player, moneyOnGround);
							entity.remove();
							Messages.debug("%s picked up the %s money. (# of rewards left=%s)", player.getName(),
									MobHunting.getRewardManager().format(rewardOnGround.getMoney()),
									RewardManager.getDroppedMoney().size());
							Messages.playerActionBarMessage(player, Messages.getString("mobhunting.moneypickup",
									"money", MobHunting.getRewardManager().format(rewardOnGround.getMoney())));
						} else {

							boolean found = false;
							HashMap<Integer, ? extends ItemStack> slots = player.getInventory()
									.all(item.getItemStack().getType());
							for (int slot : slots.keySet()) {
								ItemStack is = player.getInventory().getItem(slot);
								if (Reward.isReward(is)) {
									Reward reward = Reward.getReward(is);
									if ((rewardOnGround.isBagOfGoldReward() || rewardOnGround.isItemReward())
											&& reward.getRewardUUID().equals(rewardOnGround.getRewardUUID())) {
										ItemMeta im = is.getItemMeta();
										Reward newReward = Reward.getReward(is);
										newReward.setMoney(newReward.getMoney() + rewardOnGround.getMoney());
										im.setLore(newReward.getHiddenLore());
										String displayName = MobHunting.getConfigManager().dropMoneyOnGroundItemtype
												.equalsIgnoreCase("ITEM")
														? MobHunting.getRewardManager().format(newReward.getMoney())
														: newReward.getDisplayname() + " (" + MobHunting
																.getRewardManager().format(newReward.getMoney()) + ")";
										im.setDisplayName(ChatColor
												.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
												+ displayName);
										is.setItemMeta(im);
										is.setAmount(1);
										event.setCancelled(true);
										if (ProtocolLibCompat.isSupported())
											ProtocolLibHelper.pickupMoney(player, item);
										item.remove();
										Messages.debug("ItemStack in slot %s added value %s, new value %s", slot,
												MobHunting.getRewardManager().format(rewardOnGround.getMoney()),
												MobHunting.getRewardManager().format(newReward.getMoney()));
										found = true;
										break;
									}
								}
							}

							if (!found) {
								ItemStack is = item.getItemStack();
								ItemMeta im = is.getItemMeta();
								String displayName = MobHunting.getConfigManager().dropMoneyOnGroundItemtype
										.equalsIgnoreCase("ITEM")
												? MobHunting.getRewardManager().format(rewardOnGround.getMoney())
												: rewardOnGround.getDisplayname() + " (" + MobHunting.getRewardManager()
														.format(rewardOnGround.getMoney()) + ")";
								im.setDisplayName(
										ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
												+ displayName);
								im.setLore(rewardOnGround.getHiddenLore());
								is.setItemMeta(im);
								item.setItemStack(is);
								item.setMetadata(RewardManager.MH_REWARD_DATA,
										new FixedMetadataValue(MobHunting.getInstance(), new Reward(rewardOnGround)));
							}

						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHitRewardEvent(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		Entity targetEntity = null;
		Iterator<Entity> nearby = projectile.getNearbyEntities(1, 1, 1).iterator();
		while (nearby.hasNext()) {
			targetEntity = nearby.next();
			if (Reward.isReward(targetEntity)) {
				if (RewardManager.getDroppedMoney().containsKey(targetEntity.getEntityId()))
					RewardManager.getDroppedMoney().remove(targetEntity.getEntityId());
				targetEntity.remove();
				Messages.debug("The reward was hit by %s and removed. (# of rewards left=%s)", projectile.getType(),
						RewardManager.getDroppedMoney().size());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRewardBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled())
			return;

		ItemStack is = event.getItemInHand();
		Block block = event.getBlockPlaced();
		if (Reward.isReward(is)) {
			Reward reward = Reward.getReward(is);
			if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
				reward.setMoney(0);
				Messages.learn(event.getPlayer(), Messages.getString("mobhunting.learn.no-duplication"));
			}
			if (reward.getMoney() == 0)
				reward.setUniqueId(UUID.randomUUID());
			Messages.debug("Placed block-reward:%s", reward.toString());
			block.setMetadata(RewardManager.MH_REWARD_DATA, new FixedMetadataValue(MobHunting.getInstance(), reward));
			RewardManager.getLocations().put(reward.getUniqueUUID(), reward);
			RewardManager.getReward().put(reward.getUniqueUUID(), block.getLocation());
			RewardManager.saveReward(reward.getUniqueUUID());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRewardBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled())
			return;

		Block block = event.getBlock();
		if (Reward.hasReward(block)) {
			Reward reward = Reward.getReward(block);
			block.getDrops().clear();
			block.setType(Material.AIR);
			block.removeMetadata(RewardManager.MH_REWARD_DATA, MobHunting.getInstance());
			ItemStack is;
			if (reward.isBagOfGoldReward()) {
				is = CustomItems.getCustomtexture(reward.getRewardUUID(), reward.getDisplayname(),
						MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureValue,
						MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureSignature, reward.getMoney(),
						reward.getUniqueUUID());
			} else {
				// Is it an EnderDragon
				if (reward.getDisplayname().equalsIgnoreCase(MinecraftMob.Skeleton.getFriendlyName()))
					is = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
				else if (reward.getDisplayname().equalsIgnoreCase(MinecraftMob.WitherSkeleton.getFriendlyName()))
					is = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
				else if (reward.getDisplayname().equalsIgnoreCase(MinecraftMob.Zombie.getFriendlyName()))
					is = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
				else if (reward.getDisplayname().equalsIgnoreCase(MinecraftMob.Creeper.getFriendlyName()))
					is = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
				else if (reward.getDisplayname().equalsIgnoreCase(MinecraftMob.EnderDragon.getFriendlyName()))
					is = new ItemStack(Material.SKULL_ITEM, 1, (short) 5);
				else
					is = CustomItems.getCustomtexture(reward.getRewardUUID(), reward.getDisplayname(),
							MinecraftMob.getTexture(reward.getDisplayname()),
							MinecraftMob.getSignature(reward.getDisplayname()), reward.getMoney(),
							reward.getUniqueUUID());
				is = RewardManager.setDisplayNameAndHiddenLores(is, reward.getDisplayname(), reward.getMoney(),
						reward.getRewardUUID());
			}
			Item item = block.getWorld().dropItemNaturally(block.getLocation(), is);
			if (reward.getMoney() == 0)
				item.setCustomName(ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
						+ reward.getDisplayname());
			else
				item.setCustomName(ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
						+ MobHunting.getRewardManager().format(reward.getMoney()));
			item.setCustomNameVisible(true);
			item.setMetadata(RewardManager.MH_REWARD_DATA,
					new FixedMetadataValue(MobHunting.getInstance(), new Reward(reward.getHiddenLore())));
			if (RewardManager.getLocations().containsKey(reward.getUniqueUUID()))
				RewardManager.getLocations().remove(reward.getUniqueUUID());
			if (RewardManager.getReward().containsKey(reward.getUniqueUUID()))
				RewardManager.getReward().remove(reward.getUniqueUUID());
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClickReward(InventoryClickEvent event) {
		if (event.isCancelled())
			return;

		// Inventory inv = event.getClickedInventory();
		InventoryAction action = event.getAction();
		// ClickType clickType = event.getClick();
		ItemStack isCurrentSlot = event.getCurrentItem();
		if (isCurrentSlot == null)
			return;
		ItemStack isCursor = event.getCursor();
		Player player = (Player) event.getWhoClicked();
		if ((isCurrentSlot.getType() == Material.SKULL_ITEM
				|| isCurrentSlot.getType() == Material.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundItem))
				&& isCurrentSlot.getType() == isCursor.getType() && action == InventoryAction.SWAP_WITH_CURSOR) {
			if (Reward.isReward(isCurrentSlot) && Reward.isReward(isCursor)) {
				ItemMeta imCurrent = isCurrentSlot.getItemMeta();
				ItemMeta imCursor = isCursor.getItemMeta();
				Reward reward1 = new Reward(imCurrent.getLore());
				Reward reward2 = new Reward(imCursor.getLore());
				if ((reward1.isBagOfGoldReward() || reward1.isItemReward())
						&& reward1.getRewardUUID().equals(reward2.getRewardUUID())) {
					reward2.setMoney(reward1.getMoney() + reward2.getMoney());
					imCursor.setLore(reward2.getHiddenLore());
					imCursor.setDisplayName(ChatColor.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundTextColor)
							+ (MobHunting.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM")
									? MobHunting.getRewardManager().format(reward2.getMoney())
									: reward2.getDisplayname() + " ("
											+ MobHunting.getRewardManager().format(reward2.getMoney()) + ")"));
					isCursor.setItemMeta(imCursor);
					isCurrentSlot.setAmount(0);
					isCurrentSlot.setType(Material.AIR);
					Messages.debug("%s merged two rewards", player.getName());
				}
			}

		} else if (isCursor.getType() == Material.AIR && (isCurrentSlot.getType() == Material.SKULL_ITEM
				|| isCurrentSlot.getType() == Material.valueOf(MobHunting.getConfigManager().dropMoneyOnGroundItem))
				&& action == InventoryAction.PICKUP_HALF) {
			if (Reward.isReward(isCurrentSlot)) {
				Reward reward = Reward.getReward(isCurrentSlot);
				if (reward.isBagOfGoldReward() || reward.isItemReward()) {
					double money = reward.getMoney() / 2;
					if (Misc.floor(money) >= MobHunting.getConfigManager().minimumReward) {
						event.setCancelled(true);
						if (MobHunting.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM")) {
							isCurrentSlot = RewardManager.setDisplayNameAndHiddenLores(isCurrentSlot.clone(),
									reward.getDisplayname(), Misc.ceil(money), reward.getRewardUUID());
						} else {
							isCurrentSlot = CustomItems.getCustomtexture(reward.getRewardUUID(),
									reward.getDisplayname(),
									MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureValue,
									MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureSignature,
									Misc.ceil(money), UUID.randomUUID());
						}

						event.setCurrentItem(isCurrentSlot);

						if (MobHunting.getConfigManager().dropMoneyOnGroundItemtype.equalsIgnoreCase("ITEM")) {
							isCursor = RewardManager.setDisplayNameAndHiddenLores(isCurrentSlot.clone(),
									reward.getDisplayname(), Misc.floor(money), reward.getRewardUUID());
						} else {
							isCursor = CustomItems.getCustomtexture(reward.getRewardUUID(), reward.getDisplayname(),
									MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureValue,
									MobHunting.getConfigManager().dropMoneyOnGroundSkullTextureSignature,
									Misc.floor(money), UUID.randomUUID());
						}
						event.setCursor(isCursor);

						Messages.debug("%s halfed a reward in two (%s,%s)", player.getName(),
								MobHunting.getRewardManager().format(Misc.floor(money)),
								MobHunting.getRewardManager().format(Misc.ceil(money)));
					}
				}
			}
		}
	}

}
