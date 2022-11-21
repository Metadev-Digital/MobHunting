package one.lindegaard.MobHunting.compatibility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.event.CitizensDisableEvent;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.TraitInfo;
import one.lindegaard.CustomItemsLib.compatibility.CompatPlugin;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.commands.NpcCommand;
import one.lindegaard.MobHunting.mobs.MobPlugin;
import one.lindegaard.MobHunting.mobs.ExtendedMobRewardData;
import one.lindegaard.MobHunting.npc.MasterMobHunter;
import one.lindegaard.MobHunting.npc.MasterMobHunterEvents;
import one.lindegaard.MobHunting.npc.MasterMobHunterManager;
import one.lindegaard.MobHunting.npc.MasterMobHunterTrait;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

public class CitizensCompat implements Listener {

	private static boolean supported = false;
	private static CitizensPlugin citizensAPI;
	private static HashMap<String, ExtendedMobRewardData> mMobRewardData = new HashMap<String, ExtendedMobRewardData>();
	private static MasterMobHunterManager mMasterMobHunterManager;
	private static File fileMobRewardData = new File(MobHunting.getInstance().getDataFolder(), "citizens-rewards.yml");
	private static YamlConfiguration config = new YamlConfiguration();
	public static final String MH_CITIZENS = "MH:CITIZENS";

	public CitizensCompat() {
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
					+ "Compatibility with Citizens2 is disabled in config.yml");
		} else {
			citizensAPI = (CitizensPlugin) Bukkit.getPluginManager().getPlugin(CompatPlugin.Citizens.getName());
			if (citizensAPI == null)
				return;

			TraitInfo trait = TraitInfo.create(MasterMobHunterTrait.class).withName("MasterMobHunter");
			citizensAPI.getTraitFactory().registerTrait(trait);
			Bukkit.getConsoleSender()
					.sendMessage(ChatColor.GOLD + "[MobHunting] " + ChatColor.RESET
							+ "Enabling compatibility with Citizens2 ("
							+ getCitizensPlugin().getDescription().getVersion() + ")");

			Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());

		}
	}

	// **************************************************************************
	// LOAD & SAVE
	// **************************************************************************
	public static void loadCitizensData() {
		try {
			if (!fileMobRewardData.exists())
				return;

			config.load(fileMobRewardData);
			int n = 0;
			for (String key : config.getKeys(false)) {
				if (isNPC(Integer.valueOf(key))) {
					ConfigurationSection section = config.getConfigurationSection(key);
					ExtendedMobRewardData rewardData = new ExtendedMobRewardData();
					rewardData.read(section);
					if (mMobRewardData.get(key) == null || mMobRewardData.get(key).getMobName().equals(""))
						rewardData.setMobName("Unknown");
					mMobRewardData.put(key, rewardData);
					MobHunting.getInstance().getStoreManager().insertCitizensMobs(key);
					n++;
				} else {
					MobHunting.getInstance().getMessages().debug("The mob=%s can't be found in Citizens saves.yml file",
							key);
				}
			}
			if (n > 0)
				MobHunting.getInstance().getMessages().debug("Loaded %s MobRewards Citizens2.", n);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static void saveCitizensData() {
		try {
			config.options().header("This a extra MobHunting config data for the Citizens/NPC's on your server.");

			if (mMobRewardData.size() > 0) {

				int n = 0;
				for (String key : mMobRewardData.keySet()) {
					ConfigurationSection section = config.createSection(key);
					mMobRewardData.get(key).save(section);
					n++;
				}

				if (n > 0) {
					MobHunting.getInstance().getMessages().debug("Saving %s MobRewards for Citizens2 to file.",
							mMobRewardData.size());
					config.save(fileMobRewardData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveCitizensData(String key) {
		try {
			if (mMobRewardData.containsKey(key)) {
				ConfigurationSection section = config.createSection(key);
				mMobRewardData.get(key).save(section);
				MobHunting.getInstance().getMessages().debug("Saving MobRewardData for Citizens2: ID=%s.", key);
				config.save(fileMobRewardData);
			} else {
				MobHunting.getInstance().getMessages()
						.debug("ERROR! Sentry/Sentinel ID (%s) is not found in mMobRewardData", key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public static void shutdown() {
		if (supported) {
			TraitInfo trait = TraitInfo.create(MasterMobHunterTrait.class).withName("MasterMobHunter");
			citizensAPI.getTraitFactory().deregisterTrait(trait);
		}
	}

	public static CitizensPlugin getCitizensPlugin() {
		return citizensAPI;
	}

	public static boolean isSupported() {
		if (supported && citizensAPI != null && CitizensAPI.hasImplementation())
			return supported;
		else
			return false;
	}

	public static boolean isNPC(Entity entity) {
		if (isSupported())
			return CitizensAPI.getNPCRegistry().isNPC(entity);
		return false;
	}

	public static boolean isNPC(Integer id) {
		if (isSupported())
			return CitizensAPI.getNPCRegistry().getById(id) != null;
		return false;
	}

	public static int getNPCId(Entity entity) {
		return CitizensAPI.getNPCRegistry().getNPC(entity).getId();
	}

	public static String getNPCName(Entity entity) {
		String name = CitizensAPI.getNPCRegistry().getNPC(entity).getFullName();
		if (name.equals(""))
			name = String.valueOf(CitizensAPI.getNPCRegistry().getNPC(entity).getId());
		return name;
	}

	public static NPC getNPC(Entity entity) {
		return CitizensAPI.getNPCRegistry().getNPC(entity);
	}

	public static boolean isSentryOrSentinelOrSentries(Entity entity) {
		if (isNPC(entity))
			return CitizensAPI.getNPCRegistry().getNPC(entity)
					.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentry"))
					|| CitizensAPI.getNPCRegistry().getNPC(entity)
							.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentinel"))
					|| CitizensAPI.getNPCRegistry().getNPC(entity)
							.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentries"));
		return false;
	}

	public static boolean isSentryOrSentinelOrSentries(String mobtype) {
		if (CitizensCompat.isNPC(Integer.valueOf(mobtype)))
			return CitizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
					.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentry"))
					|| CitizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
							.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentinel"))
					|| CitizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
							.hasTrait(CitizensAPI.getTraitFactory().getTraitClass("Sentries"));
		else
			return false;
	}

	public static HashMap<String, ExtendedMobRewardData> getMobRewardData() {
		return mMobRewardData;
	}

	public static boolean isEnabledInConfig() {
		return MobHunting.getInstance().getConfigManager().enableIntegrationCitizens;
	}

	public static int getProgressAchievementLevel1(String mobtype) {
		return mMobRewardData.get(mobtype).getAchivementLevel1();
	}

	/**
	 * Get the MasterMobHunterManager
	 * 
	 * @return
	 */
	public static MasterMobHunterManager getMasterMobHunterManager() {
		return mMasterMobHunterManager;
	}
	
	public void setSkin(Integer id) {
		//CitizensAPI.
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onCitizensEnableEvent(CitizensEnableEvent event) {
		MobHunting.getInstance().getMessages().debug("Citizens2 was enabled");

		supported = true;

		loadCitizensData();
		

		mMasterMobHunterManager = new MasterMobHunterManager(MobHunting.getInstance());

		int counter = 0;
		NPCRegistry n = CitizensAPI.getNPCRegistry();
		for (Iterator<NPC> npcList = n.iterator(); npcList.hasNext();) {
			NPC npc = npcList.next();
			if (isSentryOrSentinelOrSentries(npc.getEntity())) {
				if (mMobRewardData != null && !mMobRewardData.containsKey(String.valueOf(npc.getId()))) {
					MobHunting.getInstance().getMessages().debug("A new Sentinel or Sentry NPC was found. ID=%s,%s",
							npc.getId(), npc.getName());
					mMobRewardData.put(String.valueOf(npc.getId()),
							new ExtendedMobRewardData(MobPlugin.Citizens, "npc", npc.getFullName(), true, "10", 1,
									"You killed a Citizen", new ArrayList<HashMap<String, String>>(), 1, 0.02));
					saveCitizensData(String.valueOf(npc.getId()));
				}
			}
			if (CitizensCompat.getMasterMobHunterManager().isMasterMobHunter(npc.getEntity())) {
				if (!CitizensCompat.getMasterMobHunterManager().contains(npc.getId())) {
					MasterMobHunter masterMobHunter = new MasterMobHunter(MobHunting.getInstance(), npc);
					CitizensCompat.getMasterMobHunterManager().put(npc.getId(), masterMobHunter);
					ExtendedMobRewardData rewardData = new ExtendedMobRewardData(MobPlugin.Citizens, "npc", npc.getFullName(), true, "0", 1,
							"You killed a Citizen", new ArrayList<HashMap<String, String>>(), 1, 0.02);
					CitizensCompat.getMobRewardData().put(String.valueOf(npc.getId()), rewardData);
					npc.getEntity().setMetadata(CitizensCompat.MH_CITIZENS,
							new FixedMetadataValue(MobHunting.getInstance(), rewardData));
					MobHunting.getInstance().getStoreManager().insertCitizensMobs(String.valueOf(npc.getId()));
					counter++;
				}
			}
		}
		if (counter > 0) {
			MobHunting.getInstance().getExtendedMobManager().updateExtendedMobs();
			MobHunting.getInstance().getMessages().injectMissingMobNamesToLangFiles();
		}

		Bukkit.getPluginManager().registerEvents(new MasterMobHunterEvents(),MobHunting.getInstance());

		MobHunting.getInstance().getCommandDispatcher().registerCommand(new NpcCommand(MobHunting.getInstance()));
		
		saveCitizensData();
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onCitizensDisableEvent(CitizensDisableEvent event) {
		// MobHunting.getInstance().getMessages().debug("CitizensDisableEvent -
		// saving");
	}

}