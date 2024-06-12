package metadev.digital.MetaMobHunting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import metadev.digital.MetaMobHunting.mobs.MobPlugin;
import metadev.digital.metacustomitemslib.mobs.MobType;
import metadev.digital.MetaMobHunting.mobs.ExtendedMob;

public class StatType {
	public static final StatType AchievementCount = new StatType("achievement_count", "stats.achievement_count");
	public static final StatType KillsTotal = new StatType("total_kill", "stats.total_kill");
	public static final StatType AssistsTotal = new StatType("total_assist", "stats.total_assist");
	public static final StatType CashTotal = new StatType("total_cash", "stats.total_cash");
	private static StatType[] mValues = new StatType[4 + MobPlugin.values().length * 3
			+ MobHunting.getInstance().getExtendedMobManager().getAllMobs().size() * 3];
	private static HashMap<String, StatType> mNameLookup = new HashMap<String, StatType>();

	static {
		mValues[0] = KillsTotal;
		mValues[1] = AssistsTotal;
		mValues[2] = AchievementCount;
		mValues[3] = CashTotal;

		int offset = 4, count = 0;
		
		// Adding plugin types (Minecraft_kills, MythicMob_kills, .....)
		for (int i = 0; i < MobPlugin.values().length; ++i)
			if (MobPlugin.values()[i].isSupported()) {
				mValues[offset + count] = new StatType(MobPlugin.values()[i] + "_kill",
						"stats." + MobPlugin.values()[i].name() + ".kills");
				count++;
				mValues[offset + count] = new StatType(MobPlugin.values()[i] + "_assist",
						"stats." + MobPlugin.values()[i].name() + ".assists");
				count++;
				mValues[offset + count] = new StatType(MobPlugin.values()[i] + "_cash",
						"stats." + MobPlugin.values()[i].name() + ".cashs");
				count++;
			}

		// Adding Vanilla Minecraft mobTypes
		offset = offset + count;// MobPlugin.values().length * 3;
		for (int i = 0; i < MobType.values().length; ++i) {
			mValues[offset + i] = new StatType(MobType.values()[i] + "_kill", "stats.name-format", "mob",
					"mobs." + MobType.values()[i].name() + ".name", "stattype", "stats.kills");
			mValues[offset + i + MobType.values().length] = new StatType(MobType.values()[i] + "_assist",
					"stats.name-format", "mob", "mobs." + MobType.values()[i].name() + ".name", "stattype",
					"stats.assists");
			mValues[offset + i + 2 * MobType.values().length] = new StatType(MobType.values()[i] + "_cash",
					"stats.name-format", "mob", "mobs." + MobType.values()[i].name() + ".name", "stattype",
					"stats.cashs");
		}

		// adding other mobtypes from other plugins
		Iterator<Entry<Integer, ExtendedMob>> itr = MobHunting.getInstance().getExtendedMobManager().getAllMobs()
				.entrySet().iterator();
		offset = offset + MobType.values().length * 3;

		while (itr.hasNext()) {
			ExtendedMob mob = (ExtendedMob) itr.next().getValue();
			if (mob.getMobPlugin() != MobPlugin.Minecraft && mob.getMobPlugin().isSupported()) {
				mValues[offset] = new StatType(mob.getMobPlugin().name() + "_" + mob.getMobtype() + "_Kill",
						"stats.name-format", "mob",
						"mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.kills");

				mValues[offset + 1] = new StatType(mob.getMobPlugin().name() + "_" + mob.getMobtype() + "_Assist",
						"stats.name-format", "mob",
						"mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.assists");

				mValues[offset + 2] = new StatType(mob.getMobPlugin().name() + "_" + mob.getMobtype() + "_cash",
						"stats.name-format", "mob",
						"mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.cashs");

				offset = offset + 3;
			}
		}


		for (int i = 0; i < mValues.length; ++i)
			if (mValues[i] != null)
				mNameLookup.put(mValues[i].mColumnName, mValues[i]);
	}

	private String mColumnName;
	private String mName;
	private String[] mExtra;

	public StatType(String columnName, String name, String... extra) {
		mColumnName = columnName;
		mName = name;
		mExtra = extra;
	}

	public static StatType fromMobType(ExtendedMob mob, boolean kill) {
		if (mob.getMobPlugin() == MobPlugin.Minecraft) {
			if (kill)
				return new StatType(mob.getMobtype() + "_Kill", "stats.name-format", "mob",
						"mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.kills");
			else
				return new StatType(mob.getMobtype() + "_Assist", "stats.name-format", "mob",
						"mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.assists");
		} else {
			if (kill)
				return new StatType(mob.getMobPlugin().name() + "_" + mob.getMobtype() + "_Kill", "stats.name-format",
						"mob", "mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.kills");
			else
				return new StatType(mob.getMobPlugin().name() + "_" + mob.getMobtype() + "_Assist", "stats.name-format",
						"mob", "mobs." + mob.getMobPlugin().name() + "_" + mob.getMobtype() + ".name", "stattype",
						"stats.assists");
		}
	}

	public static StatType fromColumnName(String columnName) {
		return mNameLookup.get(columnName);
	}

	public String getDBColumn() {
		return mColumnName;
	}

	public String translateName() {
		if (mExtra == null)
			return MobHunting.getInstance().getMessages().getString(mName);

		String[] extra = Arrays.copyOf(mExtra, mExtra.length);
		for (int i = 1; i < extra.length; i += 2)
			extra[i] = MobHunting.getInstance().getMessages().getString(extra[i]);

		return MobHunting.getInstance().getMessages().getString(mName, (Object[]) extra);
	}

	public String longTranslateName() {
		if (mExtra == null)
			return MobHunting.getInstance().getMessages().getString(mName);

		String[] extra = Arrays.copyOf(mExtra, mExtra.length);
		for (int i = 1; i < extra.length; i += 2)
			extra[i] = MobHunting.getInstance().getMessages().getString(extra[i]);

		return MobHunting.getInstance().getMessages().getString(mName, (Object[]) extra);
	}

	public static StatType[] values() {
		return mValues;
	}

	public static StatType getNextStatType(StatType st) {
		for (int i = 0; i < mValues.length; i++) {
			if (st.equals(mValues[i])) {
				if (i == mValues.length)
					return mValues[0];
				else
					return mValues[i + 1];
			}
		}
		return st;
	}

	public static StatType parseStat(String typeName) {
		for (StatType type : mValues) {
			if (type != null && (typeName.equalsIgnoreCase(type.getDBColumn())
					|| typeName.equalsIgnoreCase(type.translateName().replaceAll(" ", "_"))
					|| typeName.equalsIgnoreCase(ChatColor.stripColor(type.translateName()).replaceAll(" ", "_"))))
				return type;
		}

		return null;
	}

}
