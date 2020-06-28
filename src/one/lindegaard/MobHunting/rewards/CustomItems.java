package one.lindegaard.MobHunting.rewards;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import one.lindegaard.Core.Core;
import one.lindegaard.Core.PlayerSettings;
import one.lindegaard.Core.Strings;
import one.lindegaard.Core.mobs.MobType;
import one.lindegaard.Core.server.Servers;
import one.lindegaard.Core.shared.Skins;
import one.lindegaard.Core.rewards.CoreCustomItems;
import one.lindegaard.Core.rewards.Reward;
import one.lindegaard.Core.rewards.RewardType;
import one.lindegaard.MobHunting.MobHunting;

public class CustomItems {

	private MobHunting plugin;

	public CustomItems() {
		this.plugin = MobHunting.getInstance();
	}

	// How to get Playerskin
	// https://www.spigotmc.org/threads/how-to-get-a-players-texture.244966/
	// https://minecraft-heads.com/

	/**
	 * Return an ItemStack with the Players head texture.
	 *
	 * @param name
	 * @param money
	 * @return
	 */
	public ItemStack getPlayerHead(UUID uuid, String name, int amount, double money) {
		ItemStack skull = CoreCustomItems.getDefaultPlayerHead(amount);
		skull.setAmount(amount);
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
		PlayerSettings ps = Core.getPlayerSettingsManager().getPlayerSettings(offlinePlayer);
		if (ps.getTexture() == null || ps.getSignature() == null || ps.getTexture().isEmpty()
				|| ps.getSignature().isEmpty()) {
			plugin.getMessages().debug("No skin found i database");
			String[] onlineSkin = new String[2];
			if (offlinePlayer.isOnline()) {
				Player player = (Player) offlinePlayer;
				Skins sk = CoreCustomItems.getSkinsClass();
				if (sk != null) {
					plugin.getMessages().debug("Trying to fecth skin from Online Player Profile");
					onlineSkin = sk.getSkin(player);
				} else {
					plugin.getMessages().debug("Trying to fecth skin from Minecraft Servers");
					onlineSkin = getSkinFromUUID(uuid);
				}
			}

			if ((onlineSkin == null || onlineSkin[0] == null || onlineSkin[0].isEmpty() || onlineSkin[1] == null
					|| onlineSkin[1].isEmpty()) && Servers.isMC112OrNewer())
				return getPlayerHeadOwningPlayer(uuid, name, amount, money);

			if (onlineSkin != null && onlineSkin[0] != null && !onlineSkin[0].isEmpty() && onlineSkin[1] != null
					&& !onlineSkin[1].isEmpty()) {
				ps.setTexture(onlineSkin[0]);
				ps.setSignature(onlineSkin[1]);
				Core.getPlayerSettingsManager().setPlayerSettings(ps);
			} else {
				plugin.getMessages().debug("Empty skin");
				return skull;
			}
		} else {
			if (offlinePlayer.isOnline()) {
				Player player = (Player) offlinePlayer;
				Skins sk = CoreCustomItems.getSkinsClass();
				if (sk != null) {
					String[] skin = sk.getSkin(player);
					if (skin != null && skin[0] != null && !skin[0].equals(ps.getTexture())) {
						plugin.getMessages().debug("%s has changed skin, updating database with new skin. (%s,%s)",
								player.getName(), ps.getTexture(), skin[0]);
						ps.setTexture(skin[0]);
						ps.setSignature(skin[1]);
						Core.getPlayerSettingsManager().setPlayerSettings(ps);
					}
				}
			} else
				plugin.getMessages().debug("%s using skin from database", offlinePlayer.getName());
		}

		skull = new ItemStack(new CoreCustomItems().getCustomtexture(
				new Reward(offlinePlayer.getName(), money, RewardType.KILLER, uuid), ps.getTexture(),
				ps.getSignature()));

		skull.setAmount(amount);
		return skull;
	}

	private String[] getSkinFromUUID(UUID uuid) {
		try {
			URL url_1 = new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1;
			reader_1 = new InputStreamReader(url_1.openStream());

			JsonElement json = new JsonParser().parse(reader_1);
			if (json.isJsonObject()) {
				JsonObject textureProperty = json.getAsJsonObject().get("properties").getAsJsonArray().get(0)
						.getAsJsonObject();
				String texture = textureProperty.get("value").getAsString();
				String signature = textureProperty.get("signature").getAsString();

				return new String[] { texture, signature };
			} else {
				plugin.getMessages().debug("(1) Could not get skin data from session servers!");
				return null;
			}
		} catch (IOException e) {
			plugin.getMessages().debug("(2) Could not get skin data from session servers!");
			return null;
		}
	}

	public ItemStack getPlayerHeadOwningPlayer(UUID uuid, String name, int amount, double money) {
		ItemStack skull = CoreCustomItems.getDefaultPlayerHead(amount);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		skull.setItemMeta(skullMeta);
		skull = Reward.setDisplayNameAndHiddenLores(skull, name, money, new ArrayList<String>(Arrays.asList(
				"Hidden(0):" + name, "Hidden(1):" + String.format(Locale.ENGLISH, "%.5f", money),
				"Hidden(2):" + RewardType.KILLED.getType(), "Hidden(4):" + uuid,
				"Hidden(5):"
						+ Strings.encode(String.format(Locale.ENGLISH, "%.5f", money) + RewardType.KILLED.getType()),
				Core.getMessages().getString("core.reward.lore"))));
		plugin.getMessages().debug("CustomItems: set the skin using OwningPlayer/Owner (%s)", name);
		return skull;
	}

	public ItemStack getCustomHead(MobType minecraftMob, String name, int amount, double money, UUID skinUUID) {
		ItemStack skull = new ItemStack(Material.matchMaterial("PLAYER_HEAD"));
		switch (minecraftMob) {
		case Skeleton:
			skull = CoreCustomItems.getDefaultSkeletonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case WitherSkeleton:
			skull = CoreCustomItems.getDefaultWitherSkeletonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case Zombie:
			skull = CoreCustomItems.getDefaultZombieHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case PvpPlayer:
			skull = getPlayerHead(skinUUID, name, amount, money);
			break;

		case Creeper:
			skull = CoreCustomItems.getDefaultCreeperHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case EnderDragon:
			skull = CoreCustomItems.getDefaultEnderDragonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		default:
			ItemStack is = new ItemStack(
					new CoreCustomItems().getCustomtexture(new Reward(name, money, RewardType.KILLED, skinUUID),
							minecraftMob.getTextureValue(), minecraftMob.getTextureSignature()));
			is.setAmount(amount);
			return is;
		}
		return skull;
	}

}