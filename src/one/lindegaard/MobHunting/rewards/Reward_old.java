package one.lindegaard.MobHunting.rewards;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

import one.lindegaard.Core.Strings;
import one.lindegaard.Core.Tools;
import one.lindegaard.Core.mobs.MobType;
import one.lindegaard.MobHunting.MobHunting;

public class Reward_old {

	public final static String MH_REWARD_DATA = "MH:HiddenRewardData";

	// Unique random generated UUID for "Bag of gold" rewards
	public final static String MH_REWARD_BAG_OF_GOLD_UUID = "b3f74fad-429f-4801-9e31-b8879cbae96f";
	// Unique random generated UUID for MobHead/Playerhead rewards
	public final static String MH_REWARD_KILLED_UUID = "2351844f-f400-4fa4-9642-35169c5b048a";
	// Unique random generated UUID for ITEM rewards
	public final static String MH_REWARD_ITEM_UUID = "3ffe9c3b-0445-4c35-a952-c2aaf5aeac76";
	// Unique random generated UUID for KILLER head rewards
	public final static String MH_REWARD_KILLER_UUID = "d81f1076-c91c-44c0-98c3-02a2ee88aa97";

	// Reserved - not used
	public final static String MH_REWARD_CHEST_UUID = "";
	public final static String MH_REWARD_COINS_UUID = "cd05873a-b50e-4be2-9582-71770fab4034";
	public final static String MH_REWARD_SACK_UUID = "a3cf92ff-af45-458a-a633-f71760adee6f";
	public final static String MH_REWARD_DUST_UUID = "19e165cb-e47f-4f68-8e96-f13e28c07c08";

	private String displayname = ""; // Hidden(0)
	private double money = 0; // Hidden(1)
	private UUID rewardType = null; // Hidden(2)
	private UUID uniqueId; // Hidden(3)
	private UUID skinUUID; // Hidden(4)
	private String encodedHash; // Hidden(5) -

	public Reward_old() {
		this.displayname = "Skull";
		this.money = 0;
		this.rewardType = UUID.fromString("7059c845-8556-4874-806c-27bb77adee96");
		this.uniqueId = UUID.randomUUID();
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public Reward_old(Reward_old reward) {
		this.displayname = reward.getDisplayName();
		this.money = reward.getMoney();
		this.rewardType = reward.getRewardType();
		this.skinUUID = reward.getSkinUUID();
		this.uniqueId = reward.getUniqueUUID();
		this.encodedHash = reward.getEncodedHash();
	}

	public Reward_old(String displayName, double money, UUID rewardType, UUID uniqueId, UUID skinUUID) {
		this.displayname = displayName;
		this.money = money;
		this.rewardType = rewardType;
		this.uniqueId = uniqueId;
		this.skinUUID = skinUUID;
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public Reward_old(List<String> lore) {
		setReward(lore);
	}

	private String makeDecodedHash() {
		return String.format(Locale.ENGLISH, "%.5f", money) + rewardType.toString();
	}

	public boolean checkHash() {
		if (this.encodedHash != null)
			return makeDecodedHash().equals(Strings.decode(this.encodedHash));
		else
			return true;
	}

	public void updateEncodedHash() {
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public void setReward(List<String> lore) {
		String moneyStr = "", rewardTypeStr = "";
		for (int n = 0; n < lore.size(); n++) {
			String str = lore.get(n);

			// DisplayName
			if (str.startsWith("Hidden(0):"))
				this.displayname = str.substring(10);

			// Money
			else if (str.startsWith("Hidden(1):")) {
				moneyStr = str.substring(10);
				this.money = Double.valueOf(moneyStr);
			}

			// RewardType
			else if (str.startsWith("Hidden(2):")) {
				rewardTypeStr = str.substring(10);
				this.rewardType = UUID.fromString(str.substring(10));
			}

			// Unique UUID
			else if (str.startsWith("Hidden(3):"))
				this.uniqueId = money == 0 ? UUID.randomUUID() : UUID.fromString(str.substring(10));

			// Skin UUID
			else if (str.startsWith("Hidden(4):"))
				this.skinUUID = str.length() > 10 ? UUID.fromString(str.substring(10)) : null;

			// MobHunting Reward
			else if (str.equalsIgnoreCase(MobHunting.getAPI().getMessages().getString("mobhunting.reward.lore")))
				continue;

			// Hash
			else if (str.startsWith("Hidden(5):")) {
				this.encodedHash = str.substring(10);
				String compareHash = Strings.encode(moneyStr + rewardTypeStr);
				if (!encodedHash.equalsIgnoreCase(compareHash)) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting]" + ChatColor.RED
							+ "[Warning] A player has tried to change the value of a BagOfGold Item. Value set to 0!");
					money = 0;
					updateEncodedHash();
				}
			}
		}
	}

	public ArrayList<String> getHiddenLore() {
		if (rewardType.equals(UUID.fromString(MH_REWARD_BAG_OF_GOLD_UUID)))
			return new ArrayList<String>(Arrays.asList("Hidden(0):" + displayname, // displayname
					"Hidden(1):" + String.format(Locale.ENGLISH, "%.5f", money), // value
					"Hidden(2):" + rewardType.toString(), // type
					money == 0 ? "Hidden(3):" : "Hidden(3):" + uniqueId.toString(), // uniqueid
					"Hidden(4):" + (skinUUID == null ? "" : skinUUID.toString()), // SkinUUID
					"Hidden(5):" + encodedHash)); // Hash
		else
			return new ArrayList<String>(Arrays.asList("Hidden(0):" + displayname, // displayname
					"Hidden(1):" + String.format(Locale.ENGLISH, "%.5f", money), // value
					"Hidden(2):" + rewardType.toString(), // type
					money == 0 ? "Hidden(3):" : "Hidden(3):" + uniqueId.toString(), // uniqueId
					"Hidden(4):" + (skinUUID == null ? "" : skinUUID.toString()), // SkinUUID
					"Hidden(5):" + encodedHash, MobHunting.getAPI().getMessages().getString("mobhunting.reward.lore"))); // skin

	}

	/**
	 * @return the displayname
	 */
	public String getDisplayName() {
		return displayname;
	}

	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @return the uuid
	 */
	public UUID getRewardType() {
		return rewardType;
	}

	/**
	 * @return the Unique_old
	 */
	public UUID getUniqueUUID() {
		return uniqueId;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayname(String displayName) {
		this.displayname = displayName;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
		updateEncodedHash();
	}

	/**
	 * @param rewardType the uuid to set
	 */
	public void setRewardType(UUID rewardType) {
		this.rewardType = rewardType;
		updateEncodedHash();
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(UUID uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Get the skin UUID for the reward
	 * 
	 * @return
	 */
	public UUID getSkinUUID() {
		return skinUUID;
	}

	/**
	 * Set the skin UUID for the reward
	 * 
	 * @param skinUUID
	 */
	public void setSkinUUID(UUID skinUUID) {
		this.skinUUID = skinUUID;
	}

	/**
	 * @return the hash
	 */
	public String getEncodedHash() {
		return encodedHash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.encodedHash = hash;
	}

	public String toString() {
		return "{DisplayName=" + displayname + ", money=" + String.format(Locale.ENGLISH, "%.5f", money) + ", Type="
				+ rewardType + ", UniqueID=" + uniqueId + ", Skin=" + skinUUID + "}";
	}

	public void save(ConfigurationSection section) {
		section.set("description", displayname);
		section.set("money", String.format(Locale.ENGLISH, "%.5f", money));
		section.set("uuid", rewardType.toString());
		section.set("uniqueid", uniqueId.toString());
		section.set("skinuuid", skinUUID == null ? "" : skinUUID.toString());
		section.set("hash", encodedHash == null ? "" : Strings.decode(encodedHash));
	}

	public void read(ConfigurationSection section) throws InvalidConfigurationException {
		displayname = section.getString("description");
		money = Double.valueOf(section.getString("money").replace(",", "."));
		rewardType = UUID.fromString(section.getString("uuid"));
		uniqueId = UUID.fromString(section.getString("uniqueid"));
		String str = section.getString("skinuuid", "");
		if (str.equalsIgnoreCase("")) {
			if (rewardType.equals(UUID.fromString(MH_REWARD_BAG_OF_GOLD_UUID)))
				this.skinUUID = UUID.fromString(MH_REWARD_BAG_OF_GOLD_UUID);
			else if (rewardType.equals(UUID.fromString(MH_REWARD_KILLER_UUID))) {
				@SuppressWarnings("deprecation")
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(displayname);
				if (offlinePlayer != null)
					skinUUID = offlinePlayer.getUniqueId();
			} else if (rewardType.equals(UUID.fromString(MH_REWARD_KILLED_UUID))) {
				MobType mob = MobType.getMinecraftMobType(displayname);
				if (mob != null) {
					skinUUID = mob.getPlayerUUID();
				} else
					this.skinUUID = null;
			} else
				this.skinUUID = null;
		} else
			skinUUID = UUID.fromString(section.getString("skinuuid"));
		encodedHash = Strings.encode(section.getString("hash", makeDecodedHash()));
	}

	public boolean isMoney() {
		return isBagOfGoldReward() || isItemReward();
	}

	public boolean isBagOfGoldReward() {
		return rewardType.toString().equalsIgnoreCase(MH_REWARD_BAG_OF_GOLD_UUID);
	}

	public boolean isKilledHeadReward() {
		return rewardType.toString().equalsIgnoreCase(MH_REWARD_KILLED_UUID);
	}

	public boolean isKillerHeadReward() {
		return rewardType.toString().equalsIgnoreCase(MH_REWARD_KILLER_UUID);
	}

	public boolean isItemReward() {
		return rewardType.toString().equalsIgnoreCase(MH_REWARD_ITEM_UUID);
	}

	public static boolean isReward(Item item) {
		return item.hasMetadata(MH_REWARD_DATA) || isReward(item.getItemStack());
	}

	public static Reward_old getReward(Item item) {
		if (item.hasMetadata(MH_REWARD_DATA))
			for (MetadataValue mv : item.getMetadata(MH_REWARD_DATA)) {
				if (mv.value() instanceof Reward_old)
					return (Reward_old) item.getMetadata(MH_REWARD_DATA).get(0).value();
			}
		return getReward(item.getItemStack());
	}

	public static boolean isReward(ItemStack itemStack) {
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()
				&& itemStack.getItemMeta().getLore().size() > 2) {
			String lore = itemStack.getItemMeta().getLore().get(2);
			return lore.equals("Hidden(2):" + MH_REWARD_BAG_OF_GOLD_UUID)
					|| lore.equals("Hidden(2):" + MH_REWARD_KILLED_UUID)
					|| lore.equals("Hidden(2):" + MH_REWARD_KILLER_UUID)
					|| lore.equals("Hidden(2):" + MH_REWARD_ITEM_UUID);
		} else
			return false;
	}

	public static Reward_old getReward(ItemStack itemStack) {
		return new Reward_old(itemStack.getItemMeta().getLore());
	}

	public static boolean isReward(Block block) {
		return block.hasMetadata(MH_REWARD_DATA);
	}

	public static Reward_old getReward(Block block) {
		return (Reward_old) block.getMetadata(MH_REWARD_DATA).get(0).value();
	}

	public static boolean isReward(Entity entity) {
		return entity.hasMetadata(MH_REWARD_DATA);
	}

	public static Reward_old getReward(Entity entity) {
		return (Reward_old) entity.getMetadata(MH_REWARD_DATA).get(0).value();
	}

	/**
	 * setDisplayNameAndHiddenLores: add the Display name and the (hidden) Lores.
	 * The lores identifies the reward and contain secret information.
	 * 
	 * @param skull  - The base itemStack without the information.
	 * @param reward - The reward information is added to the ItemStack
	 * @return the updated ItemStack.
	 */
	public static ItemStack setDisplayNameAndHiddenLores(ItemStack skull, Reward_old reward) {
		ItemMeta skullMeta = skull.getItemMeta();
		skullMeta.setLore(reward.getHiddenLore());

		if (reward.getMoney() == 0)
			skullMeta.setDisplayName(reward.getDisplayName());
		else
			skullMeta.setDisplayName(reward.isItemReward() ? Tools.format(reward.getMoney())
					: reward.getDisplayName() + " (" + Tools.format(reward.getMoney()) + ")");
		skull.setItemMeta(skullMeta);
		return skull;
	}

}
