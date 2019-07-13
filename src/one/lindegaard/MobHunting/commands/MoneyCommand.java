package one.lindegaard.MobHunting.commands;

import one.lindegaard.BagOfGold.BagOfGold;
import one.lindegaard.Core.Tools;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.compatibility.BagOfGoldCompat;
import one.lindegaard.MobHunting.compatibility.BossShopCompat;
import one.lindegaard.MobHunting.compatibility.BossShopHelper;
import one.lindegaard.MobHunting.rewards.CustomItems;
import one.lindegaard.MobHunting.rewards.Reward;
import one.lindegaard.MobHunting.util.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyCommand implements ICommand {

	private MobHunting plugin;

	public MoneyCommand(MobHunting plugin) {
		this.plugin = plugin;
	}

	// Admin command
	// /mh money drop <amount> - to drop <amount money> where player look.
	// Permission needed mobhunt.money.drop

	// /mh money drop <playername> <amount> - to drop <amount money> where
	// player look.
	// Permission needed mobhunt.money.drop

	// /mh money sell - to sell all bag of gold you are holding in your hand.
	// Permission needed mobhunt.money.sell

	// /mh money sell <amount> - to sell <amount money> of the bag of gold you
	// have in your hand.
	// Permission needed mobhunt.money.sell

	@Override
	public String getName() {
		return "money";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "gold", "bag", plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias };
	}

	@Override
	public String getPermission() {
		return null; // "mobhunting.money";
	}

	@Override
	public String[] getUsageString(String label, CommandSender sender) {
		return new String[] {
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " drop <amount>" + ChatColor.WHITE + " - to drop <amount> of "
						+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim() + ", where you look.",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " drop <playername> " + ChatColor.YELLOW + "<amount>" + ChatColor.WHITE
						+ " - to drop <amount> of " + plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()
						+ " 3 block in front of the <player>.",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " give <player>" + ChatColor.YELLOW + " <amount>" + ChatColor.WHITE
						+ " - to give the player a " + plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()
						+ " in his inventory.",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " take <player>" + ChatColor.YELLOW + " <amount>" + ChatColor.WHITE
						+ " - to take <amount> gold from the "
						+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()
						+ " in the players inventory",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " sell" + ChatColor.WHITE + " - to sell the "
						+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim() + " in your hand.",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " sell" + ChatColor.YELLOW + " <amount>" + ChatColor.WHITE
						+ " - to sell some of the gold in your "
						+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim() + " and get the money.",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN + " buy"
						+ ChatColor.YELLOW + " <amount>" + ChatColor.WHITE
						+ " - to buy some more gold with your money and put it into your "
						+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim() + ".",
				ChatColor.GOLD + plugin.getConfigManager().dropMoneyOnGroundMoneyCommandAlias + ChatColor.GREEN
						+ " shop" + ChatColor.WHITE + " - to open the MobHunting BossShop." };
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("mobhunting.commands.money.description", "rewardname",
				plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim());
	}

	@Override
	public boolean canBeConsole() {
		return true;
	}

	@Override
	public boolean canBeCommandBlock() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {

		CustomItems customItems = new CustomItems();

		if (args.length == 1) {
			// /mh money help
			// Show help
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
				return false;
		}

		if (args.length == 0
				|| (args.length >= 1 && (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")))) {
			// mh money
			// mh money balance
			// mh money balance <player>
			// show the total amount of "bag of gold" in the players inventory.

			if (sender.hasPermission("mobhunting.money.balance") || sender.hasPermission("mobhunting.money.*")) {
				OfflinePlayer offlinePlayer = null;
				boolean other = false;
				if (args.length <= 1) {
					if (!(sender instanceof Player)) {
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
								.getString("mobhunting.commands.base.noconsole", "command", "'money balance'"));
						return true;
					} else
						offlinePlayer = (Player) sender;

				} else {
					if (sender.hasPermission("mobhunting.money.balance.other")
							|| sender.hasPermission("mobhunting.money.*")) {
						offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
						other = true;
					} else {
						plugin.getMessages().senderSendMessage(sender,
								ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission",
										"perm", "mobhunting.money.balance.other", "command", "money <playername>"));
						return true;
					}
				}

				double balance = plugin.getRewardManager().getBalance(offlinePlayer);

				if (other)
					plugin.getMessages().senderSendMessage(sender,
							ChatColor.GREEN + plugin.getMessages().getString("mobhunting.commands.money.balance",
									"playername", offlinePlayer.getName(), "money",
									plugin.getRewardManager().format(balance), "rewardname",
									ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
											+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()));
				else
					plugin.getMessages().senderSendMessage(sender,
							ChatColor.GREEN + plugin.getMessages().getString("mobhunting.commands.money.balance",
									"playername", "You", "money", plugin.getRewardManager().format(balance),
									"rewardname",
									ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
											+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()));
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.balance", "command", "money"));
			}
			return true;

		} else if (args.length == 1 && Bukkit.getServer().getOfflinePlayer(args[0]) == null) {
			plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
					.getString("mobhunting.commands.base.unknown_playername", "playername", args[0]));
			return true;

		} else if (args.length == 2 && args[0].equalsIgnoreCase("shop")) {
			// /mh money shop - to open a shop, where the player can buy or sell
			// "Bag of gold"

			// MobHunting.registerPlugin(BossShopCompat.class, "BossShop");

			String shopName = args[1];

			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (BossShopCompat.isSupported()) {
					if (player.hasPermission("mobhunting.money.shop") || sender.hasPermission("mobhunting.money.*")) {
						BossShopHelper.openShop(plugin, player, shopName);
						return true;
					} else {
						plugin.getMessages().senderSendMessage(sender,
								ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission",
										"perm", "mobhunting.money.shop", "command", "shop"));
						return true;
					}
				} else {
					plugin.getMessages().senderSendMessage(sender,
							ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.money.no-bossshop"));
					return true;
				}
			} else {
				// not allowed in console
				plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
						.getString("mobhunting.commands.base.noconsole", "command", "'money shop'"));
				return true;
			}
		}

		else if (args.length >= 2 && args[0].equalsIgnoreCase("drop") || args[0].equalsIgnoreCase("place"))

		{
			// /mh money drop <amount>
			// /mh money drop <player> <amount>
			if (sender.hasPermission("mobhunting.money.drop") || sender.hasPermission("mobhunting.money.*")) {
				if (args.length == 2 && !(sender instanceof Player)) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED
							+ plugin.getMessages().getString("mobhunting.commands.base.playername-missing"));
				} else {
					if (args[1].matches("\\d+(\\.\\d+)?")) {
						Player player = (Player) sender;
						Location location = Tools.getTargetBlock(player, 20).getLocation();
						double money = Misc.floor(Double.valueOf(args[1]));
						if (money > plugin.getConfigManager().limitPerBag * 100) {
							money = plugin.getConfigManager().limitPerBag * 100;
							plugin.getMessages().senderSendMessage(sender,
									ChatColor.RED
											+ plugin.getMessages().getString("mobhunting.commands.money.to_big_number",
													"number", args[1], "maximum", money));
						}
						plugin.getMessages().debug("The BagOfGold was dropped at %s", location);
						plugin.getRewardManager().dropMoneyOnGround_RewardManager(player, null, location, money);
						plugin.getMessages().playerActionBarMessageQueue(player,
								plugin.getMessages().getString("mobhunting.moneydrop", "rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName,
										"money", plugin.getRewardManager().format(money)));
					} else if (Bukkit.getServer().getOfflinePlayer(args[1]).isOnline()) {
						if (args[2].matches("\\d+(\\.\\d+)?")) {
							Player player = ((Player) Bukkit.getServer().getOfflinePlayer(args[1]));
							Location location = Tools.getTargetBlock(player, 3).getLocation();
							double money = Misc.floor(Double.valueOf(args[2]));
							if (money > plugin.getConfigManager().limitPerBag * 100) {
								money = plugin.getConfigManager().limitPerBag * 100;
								plugin.getMessages().senderSendMessage(sender,
										ChatColor.RED + plugin.getMessages().getString(
												"mobhunting.commands.money.to_big_number", "number", args[2], "maximum",
												money));
							}
							plugin.getMessages().debug("The BagOfGold was dropped at %s", location);
							plugin.getRewardManager().dropMoneyOnGround_RewardManager(player, null, location, money);
							plugin.getMessages().playerActionBarMessageQueue(player,
									plugin.getMessages().getString("mobhunting.moneydrop", "rewardname",
											ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
													+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
											"money", plugin.getRewardManager().getEconomy().format(money)));
						} else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.base.not_a_number", "number", args[2]));
						}
					} else {
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
								.getString("mobhunting.commands.base.playername-missing", "player", args[1]));
					}
				}
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.drop", "command", "money drop"));
			}
			return true;

		} else if (args.length >= 2 && args[0].equalsIgnoreCase("give")) {
			// /mh money give <player> <amount>
			if (sender.hasPermission("mobhunting.money.give") || sender.hasPermission("mobhunting.money.*")) {
				if (args.length == 2 && !(sender instanceof Player)) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED
							+ plugin.getMessages().getString("mobhunting.commands.base.playername-missing"));
					return true;
				}

				OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
				if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.playername-missing", "player", args[1]));
					return true;
				}

				if (args[2].matches("\\d+(\\.\\d+)?")) {
					double amount = Misc.round(Double.valueOf(args[2]));
					if (amount > plugin.getConfigManager().limitPerBag * 100) {
						amount = plugin.getConfigManager().limitPerBag * 100;
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages().getString(
								"mobhunting.commands.money.to_big_number", "number", args[2], "maximum", amount));
					}

					if (BagOfGoldCompat.isSupported()) {
						plugin.getMessages().debug("BagOfGold supported, using depositPlayer");
						BagOfGold.getAPI().getEconomyManager().depositPlayer(offlinePlayer, amount);
					} else {
						if (plugin.getConfigManager().dropMoneyOnGroundUseItemAsCurrency) {
							if (offlinePlayer.isOnline()) {
								Player player = (Player) offlinePlayer;
								// double result =
								// BagOfGold.getAPI().getEconomyManager().addMoneyToPlayer(player, amount);
								double result = plugin.getRewardManager().addBagOfGoldPlayer(player, amount);
								if (Misc.round(result) != Misc.round(amount)) {
									plugin.getRewardManager().dropMoneyOnGround_RewardManager(player, null,
											player.getLocation(), Misc.floor(amount - result));
									// BagOfGold.getAPI().getEconomyManager().dropMoneyOnGround_EconomyManager(player,
									// null, player.getLocation(), Misc.floor(amount - result));
								}
								if (Misc.round(result) == Misc.round(amount)) {
									plugin.getMessages().playerActionBarMessageQueue(player, plugin.getMessages()
											.getString("mobhunting.commands.money.give", "rewardname", ChatColor
													.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
													+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
													"money", plugin.getRewardManager().format(amount)));
									plugin.getMessages().senderSendMessage(sender, plugin.getMessages().getString(
											"mobhunting.commands.money.give-sender", "rewardname",
											ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
													+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
											"money", plugin.getRewardManager().format(amount), "player",
											player.getName()));
								}
							} else {
								plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
										.getString("mobhunting.commands.base.playername-missing", "player", args[1]));
							}
						} else {
							// BagOfGOld is not a currency. So /mh money give 25
							// is the same as /eco give player 25
							plugin.getMobHuntingEconomyManager().add(offlinePlayer, amount);
							//plugin.getRewardManager().getEconomy().depositPlayer(offlinePlayer, amount);
						}
					}
				} else {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.not_a_number", "number", args[2]));
				}
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.give", "command", "money give"));
			}
			return true;
		}

		else if (args.length >= 2 && args[0].equalsIgnoreCase("take"))

		{
			// /mh money take <player> <amount>
			if (sender.hasPermission("mobhunting.money.take") || sender.hasPermission("mobhunting.money.*")) {
				if (args.length == 2 && !(sender instanceof Player)) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED
							+ plugin.getMessages().getString("mobhunting.commands.base.playername-missing"));
					return true;
				}
				OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[1]);
				if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED
							+ plugin.getMessages().getString("mobhunting.commands.base.playername-missing"));
					return true;
				}
				if (args[2].matches("\\d+(\\.\\d+)?")) {
					double rest = Misc.round(Double.valueOf(args[2]));
					if (rest > plugin.getConfigManager().limitPerBag * 100) {
						rest = plugin.getConfigManager().limitPerBag * 100;
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages().getString(
								"mobhunting.commands.money.to_big_number", "number", args[2], "maximum", rest));
					}

					double taken = 0;
					if (BagOfGoldCompat.isSupported()) {
						plugin.getMessages().debug("BagOfGold supported, using withdrawPlayer");
						BagOfGold.getAPI().getEconomyManager().withdrawPlayer(offlinePlayer, rest);
					} else if (plugin.getConfigManager().dropMoneyOnGroundUseItemAsCurrency) {
						if (Bukkit.getServer().getOfflinePlayer(args[1]).isOnline()) {
							Player player = ((Player) Bukkit.getServer().getOfflinePlayer(args[1]));
							taken = plugin.getRewardManager().removeBagOfGoldPlayer(player, rest);
						} else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED
									+ plugin.getMessages().getString("mobhunting.commands.base.playername-missing"));
							return true;
						}
						if (taken != 0) {
							if (offlinePlayer.isOnline())
								plugin.getMessages().playerActionBarMessageQueue((Player) offlinePlayer,
										plugin.getMessages().getString("mobhunting.commands.money.take", "rewardname",
												ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
														+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName,
												"money", plugin.getRewardManager().format(taken)));
							plugin.getMessages().senderSendMessage(sender,
									plugin.getMessages().getString("mobhunting.commands.money.take-sender",
											"rewardname",
											ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
													+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
											"money", plugin.getRewardManager().format(taken), "player",
											offlinePlayer.getName()));
						}
					} else {
						// BagOfGold is not a currency. /mh money take player 25
						// will work as /eco take player 25
						plugin.getMobHuntingEconomyManager().subtract(offlinePlayer, rest);
						//plugin.getRewardManager().getEconomy().withdrawPlayer(offlinePlayer, rest);
					}
				} else
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.not_a_number", "number", args[2]));
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.take", "command", "money take"));
			}
			return true;

		} else if ((args.length == 1 && args[0].equalsIgnoreCase("sell"))
				|| (args.length == 2 && args[0].equalsIgnoreCase("sell") && (args[1].matches("\\d+(\\.\\d+)?")))) {
			// /mh money sell
			// /mh money sell <amount>
			if (sender.hasPermission("mobhunting.money.sell") || sender.hasPermission("mobhunting.money.*")) {
				if (!(sender instanceof Player)) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.noconsole", "command", "'money sell'"));
					return true;
				}

				Player player = (Player) sender;
				if (args.length == 1) {
					ItemStack is = player.getItemInHand();
					if (Reward.isReward(is)) {
						Reward reward = Reward.getReward(is);
						if (BagOfGoldCompat.isSupported() && reward.isBagOfGoldReward()) {
							plugin.getMessages().playerSendMessage(player,
									plugin.getMessages().getString("mobhunting.money.you_cant_sell_and_buy_bagofgold",
											"itemname", reward.getDisplayname()));
							return true;
						}
						plugin.getMobHuntingEconomyManager().add(player, reward.getMoney());
						//plugin.getRewardManager().getEconomy().depositPlayer(player, reward.getMoney());
						is.setType(Material.AIR);
						is.setAmount(0);
						is.setItemMeta(null);
						player.setItemInHand(is);
						plugin.getMessages().playerActionBarMessageQueue(player,
								plugin.getMessages().getString("mobhunting.commands.money.sell", "rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ reward.getDisplayname(),
										"money", plugin.getRewardManager().format(reward.getMoney())));
					} else {
						plugin.getMessages().playerSendMessage(player,
								plugin.getMessages().getString("mobhunting.bagofgoldsign.hold_reward_in_hand",
										"rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName));

					}
				} else if ((args[0].equalsIgnoreCase("sell") && (args[1].matches("\\d+(\\.\\d+)?")))) {
					double sold = 0;
					double toBeSold = Misc.floor(Double.valueOf(args[1]));
					for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
						ItemStack is = player.getInventory().getItem(slot);
						if (Reward.isReward(is)) {
							Reward reward = Reward.getReward(is);
							if (reward.isBagOfGoldReward()) {
								double saldo = Misc.floor(reward.getMoney());
								if (saldo > toBeSold) {
									reward.setMoney(saldo - toBeSold);
									is = customItems.getCustomtexture(reward.getRewardType(),
											plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
											plugin.getConfigManager().dropMoneyOnGroundSkullTextureValue,
											plugin.getConfigManager().dropMoneyOnGroundSkullTextureSignature,
											saldo - toBeSold, UUID.randomUUID(), reward.getSkinUUID());
									player.getInventory().setItem(slot, is);
									sold = sold + toBeSold;
									toBeSold = 0;
									break;
								} else {
									is.setItemMeta(null);
									is.setType(Material.AIR);
									is.setAmount(0);
									player.getInventory().setItem(slot, is);
									sold = sold + saldo;
									toBeSold = toBeSold - saldo;
								}
							}
						}
					}
					if (sold > 0) {
						plugin.getMobHuntingEconomyManager().add(player, sold);
						//plugin.getRewardManager().getEconomy().depositPlayer(player, sold);
						plugin.getMessages().playerActionBarMessageQueue(player,
								plugin.getMessages().getString("mobhunting.commands.money.sell", "rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
										"money", plugin.getRewardManager().format(sold)));
					} else {
						plugin.getMessages().playerActionBarMessageQueue(player,
								plugin.getMessages().getString("mobhunting.commands.money.sell.nobag", "rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim()));
					}
				}
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.sell", "command", "money sell"));
			}
			return true;
		}

		else if (args.length >= 2 && args[0].equalsIgnoreCase("buy")) {
			// /mh money buy <amount>
			Player player = (Player) sender;
			if (sender.hasPermission("mobhunting.money.buy") || sender.hasPermission("mobhunting.money.*")) {
				if (BagOfGoldCompat.isSupported()) {
					plugin.getMessages().playerSendMessage(player,
							plugin.getMessages().getString("mobhunting.money.you_cant_sell_and_buy_bagofgold",
									"itemname", plugin.getConfigManager().dropMoneyOnGroundSkullRewardName));
					return true;
				}
				if (args.length == 2 && args[1].matches("\\d+(\\.\\d+)?")) {
					if (plugin.getRewardManager().getEconomy().hasMoney(player, Misc.floor(Double.valueOf(args[1])))) {
						// if (BagOfGoldCompat.isSupported()) {
						if (plugin.getConfigManager().dropMoneyOnGroundUseItemAsCurrency) {
							plugin.getRewardManager().dropMoneyOnGround_RewardManager(player, null,
									player.getLocation(), Misc.floor(Double.valueOf(args[1])));
						} else if (player.getInventory().firstEmpty() == -1)
							plugin.getRewardManager().dropMoneyOnGround_RewardManager(player, null,
									player.getLocation(), Misc.floor(Double.valueOf(args[1])));
						else {
							ItemStack is = customItems.getCustomtexture(
									UUID.fromString(Reward.MH_REWARD_BAG_OF_GOLD_UUID),
									plugin.getConfigManager().dropMoneyOnGroundSkullRewardName.trim(),
									plugin.getConfigManager().dropMoneyOnGroundSkullTextureValue,
									plugin.getConfigManager().dropMoneyOnGroundSkullTextureSignature,
									Misc.floor(Double.valueOf(args[1])), UUID.randomUUID(),
									UUID.fromString(Reward.MH_REWARD_BAG_OF_GOLD_UUID));
							player.getInventory().addItem(is);
						}
						plugin.getRewardManager().withdrawPlayer(player, Misc.floor(Double.valueOf(args[1])));
						plugin.getMessages().playerActionBarMessageQueue(player,
								plugin.getMessages().getString("mobhunting.commands.money.buy", "rewardname",
										ChatColor.valueOf(plugin.getConfigManager().dropMoneyOnGroundTextColor)
												+ plugin.getConfigManager().dropMoneyOnGroundSkullRewardName,
										"money",
										plugin.getRewardManager().format(Misc.floor(Double.valueOf(args[1])))));
					} else {
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
								.getString("mobhunting.commands.money.not-enough-money", "money", args[1]));
					}
				} else {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.not_a_number", "number", args[1]));
				}
			} else {
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.base.nopermission", "perm",
								"mobhunting.money.buy", "command", "money buy"));
			}
			return true;
		}
		return false;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
		ArrayList<String> items = new ArrayList<String>();
		if (args.length == 1) {
			items.add("drop");
			items.add("give");
			items.add("take");
			items.add("sell");
			items.add("buy");
			items.add("shop");
		} else if (args.length == 2)
			for (Player player : Bukkit.getOnlinePlayers())
				items.add(player.getName());

		if (!args[args.length - 1].trim().isEmpty()) {
			String match = args[args.length - 1].trim().toLowerCase();

			items.removeIf(name -> !name.toLowerCase().startsWith(match));
		}
		return items;
	}
}
