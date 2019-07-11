package one.lindegaard.MobHunting.npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import one.lindegaard.Core.Tools;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.StatType;
import one.lindegaard.MobHunting.compatibility.CitizensCompat;
import one.lindegaard.MobHunting.storage.IDataCallback;
import one.lindegaard.MobHunting.storage.StatStore;
import one.lindegaard.MobHunting.storage.TimePeriod;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.metadata.FixedMetadataValue;

public class MasterMobHunter implements IDataCallback<List<StatStore>> {

	private MobHunting plugin;
	private NPC npc;
	private List<StatStore> stats;

	public MasterMobHunter(MobHunting plugin) {
		this.plugin = plugin;
	}

	public MasterMobHunter(MobHunting plugin, int id, StatType statType, TimePeriod period, int numberOfKills,
			int rank) {
		this.plugin = plugin;
		npc = CitizensAPI.getNPCRegistry().getById(id);
		npc.getTrait(MasterMobHunterTrait.class).stattype = statType.getDBColumn();
		npc.getTrait(MasterMobHunterTrait.class).period = period.getDBColumn();
		npc.getTrait(MasterMobHunterTrait.class).rank = rank;
		npc.getTrait(MasterMobHunterTrait.class).noOfKills = numberOfKills;
		npc.getTrait(MasterMobHunterTrait.class).signLocations = new ArrayList<Location>();
	}

	public MasterMobHunter(MobHunting plugin, NPC npc) {
		this.plugin = plugin;
		this.npc = npc;
		if (StatType.fromColumnName(npc.getTrait(MasterMobHunterTrait.class).stattype) == null) {
			plugin.getLogger().warning("NPC ID=" + npc.getId() + " has an invalid StatType. Resetting to "
					+ StatType.KillsTotal.getDBColumn());
			setStatType(StatType.KillsTotal);
		}
		if (TimePeriod.fromColumnName(npc.getTrait(MasterMobHunterTrait.class).period) == null) {
			plugin.getLogger().warning("NPC ID=" + npc.getId() + " has an invalid TimePeriod. Resetting to "
					+ TimePeriod.AllTime.getDBColumn());
			setPeriod(TimePeriod.AllTime);
		}
		if (npc.getTrait(MasterMobHunterTrait.class).signLocations == null)
			npc.getTrait(MasterMobHunterTrait.class).signLocations = new ArrayList<Location>();
	}

	public int getId() {
		return npc.getId();
	}

	public StatType getStatType() {
		return StatType.fromColumnName(npc.getTrait(MasterMobHunterTrait.class).stattype);
	}

	public void setStatType(StatType statType) {
		npc.getTrait(MasterMobHunterTrait.class).stattype = statType.getDBColumn();
	}

	public TimePeriod getPeriod() {
		return TimePeriod.fromColumnName(npc.getTrait(MasterMobHunterTrait.class).period);
	}

	public void setPeriod(TimePeriod period) {
		npc.getTrait(MasterMobHunterTrait.class).period = period.getDBColumn();
	}

	public int getNumberOfKills() {
		return npc.getTrait(MasterMobHunterTrait.class).noOfKills;
	}

	public void setNumberOfKills(int numberOfKills) {
		npc.getTrait(MasterMobHunterTrait.class).noOfKills = numberOfKills;
	}

	public int getRank() {
		return npc.getTrait(MasterMobHunterTrait.class).rank;
	}

	public void setRank(int rank) {
		npc.getTrait(MasterMobHunterTrait.class).rank = rank;
	}

	private void setSignLocations(List<Location> signLocations) {
		npc.getTrait(MasterMobHunterTrait.class).signLocations = signLocations;
	}

	public List<Location> getSignLocations() {
		return npc.getTrait(MasterMobHunterTrait.class).signLocations;
	}

	public void putSignLocation(Location location) {
		if (!npc.getTrait(MasterMobHunterTrait.class).signLocations.contains(location)) {
			MobHunting.getInstance().getMessages().debug("put signLocation into npc=%s", npc.getId());
			npc.getTrait(MasterMobHunterTrait.class).signLocations.add(location);
		}
	}

	public void removeLocation(Location location) {
		npc.getTrait(MasterMobHunterTrait.class).signLocations.remove(location);
	}

	public Location getHome() {
		if (npc.getTrait(MasterMobHunterTrait.class).home == null) {
			setHome(npc.getEntity().getLocation());
		}
		return npc.getTrait(MasterMobHunterTrait.class).home;
	}

	public void setHome(Location location) {
		MobHunting.getInstance().getMessages().debug("Set NPC ID=%s home = %s", npc.getId(), location);
		npc.getTrait(MasterMobHunterTrait.class).home = location;
	}

	private boolean isLoaded(Block block) {
		return (block.getWorld().isChunkLoaded(block.getX() >> 4, block.getZ() >> 4));
	}

	// ***********************************************************************************
	// RequestStats / DataCallBack
	// ***********************************************************************************
	public void update() {
		plugin.getDataStoreManager().requestStats(getStatType(), getPeriod(), 25, this);
	}

	public List<StatStore> getCurrentStats() {
		if (stats == null)
			return Collections.emptyList();
		return stats;
	}

	@Override
	public void onCompleted(List<StatStore> data) {
		ArrayList<StatStore> altData = new ArrayList<StatStore>(data.size());
		for (StatStore stat : data) {
			if (stat.getAmount() != 0 && stat.getPlayer().getName() != null) {
				altData.add(stat);
			}
		}
		stats = altData;
		refresh();
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	public void refresh() {
		if (getRank() < stats.size() + 1) {
			if (getRank() != 0) {
				if (!stats.get(getRank() - 1).getPlayer().getName().equals(npc.getName())) {
					npc.setName(stats.get(getRank() - 1).getPlayer().getName());
				}
				setNumberOfKills(stats.get(getRank() - 1).getAmount());
			}
		} else {
			npc.setName("NO KILLS");
			setNumberOfKills(0);
		}
		updateSigns();
	}

	private void updateSigns() {
		if (getSignLocations().size() > 0) {
			Iterator<Location> itr = getSignLocations().iterator();
			while (itr.hasNext()) {
				Location loc = itr.next();
				Block sb = loc.getBlock();
				if (isLoaded(sb)) {
					
					if (MasterMobHunterSign.isMHSign(sb)) {
						Sign s = (Sign) sb.getState();
						if (MasterMobHunterSign.isMHSign(s.getLine(0))) {
							sb.setMetadata(MasterMobHunterSign.MH_SIGN, new FixedMetadataValue(plugin, s.getLine(0)));
							s.setMetadata(MasterMobHunterSign.MH_SIGN, new FixedMetadataValue(plugin, s.getLine(0)));
							int id = MasterMobHunterSign.getNPCIdOnSign(sb);
							NPC npc = CitizensAPI.getNPCRegistry().getById(id);
							if (npc != null) {
								if (CitizensCompat.getMasterMobHunterManager().contains(npc.getId())) {
									MasterMobHunter mmh = CitizensCompat.getMasterMobHunterManager().get(npc.getId());
									mmh.putSignLocation(sb.getLocation());
									CitizensCompat.getMasterMobHunterManager().put(id, mmh);
								}
							}
						}
						s.setLine(1, (Tools.trimSignText(getRank() + "." + npc.getName())));
						s.setLine(2, (Tools.trimSignText(getPeriod().translateNameFriendly())));
						s.setLine(3, (Tools.trimSignText(getNumberOfKills() + " " + getStatType().translateName())));
						s.update();
						if (MasterMobHunterSign.isMHSign(sb)) {
							OfflinePlayer player = Bukkit.getPlayer(npc.getName());
							if (player != null && player.isOnline())
								MasterMobHunterSign.setPower(sb, MasterMobHunterSign.POWER_FROM_SIGN);
							else
								MasterMobHunterSign.removePower(sb);
						}
					}
				}
			}
		}
	}

	// ***************************************************************
	// read & convert to NPC stored data
	// ***************************************************************

	@SuppressWarnings("unchecked")
	public void read(ConfigurationSection section) throws InvalidConfigurationException, IllegalStateException {
		setStatType(StatType.fromColumnName(section.getString("stattype")));
		setPeriod(TimePeriod.fromColumnName(section.getString("period")));
		setNumberOfKills(section.getInt("kills"));
		setRank(section.getInt("rank"));
		if (section.contains("signs")) {
			List<Location> signLocations = new ArrayList<Location>();
			signLocations = (List<Location>) section.get("signs", signLocations);
			setSignLocations(signLocations);
		}
	}

	/**
	 * @return the npc
	 */
	public NPC getNpc() {
		return npc;
	}

	/**
	 * @param npc
	 *            the npc to set
	 */
	public void setNpc(NPC npc) {
		this.npc = npc;
	}

}
