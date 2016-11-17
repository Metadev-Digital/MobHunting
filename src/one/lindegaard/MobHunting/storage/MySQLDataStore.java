package one.lindegaard.MobHunting.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import one.lindegaard.MobHunting.Messages;
import one.lindegaard.MobHunting.MobHunting;
import one.lindegaard.MobHunting.StatType;
import one.lindegaard.MobHunting.mobs.MobPlugin;
import one.lindegaard.MobHunting.util.UUIDHelper;

public class MySQLDataStore extends DatabaseDataStore {

	// *******************************************************************************
	// SETUP / INITIALIZE
	// *******************************************************************************

	@Override
	protected Connection setupConnection() throws SQLException, DataStoreException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// return DriverManager.getConnection(
			// "jdbc:mysql://" + MobHunting.getConfigManager().databaseHost +
			// "/"
			// + MobHunting.getConfigManager().databaseName +
			// "?autoReconnect=true",
			// MobHunting.getConfigManager().databaseUsername,
			// MobHunting.getConfigManager().databasePassword);
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser(MobHunting.getConfigManager().databaseUsername);
			dataSource.setPassword(MobHunting.getConfigManager().databasePassword);
			if (MobHunting.getConfigManager().databaseHost.contains(":")) {
				dataSource.setServerName(MobHunting.getConfigManager().databaseHost.split(":")[0]);
				dataSource.setPort(Integer.valueOf(MobHunting.getConfigManager().databaseHost.split(":")[1]));
			} else {
				dataSource.setServerName(MobHunting.getConfigManager().databaseHost);
			}
			dataSource.setDatabaseName(MobHunting.getConfigManager().databaseName + "?autoReconnect=true");
			return dataSource.getConnection();
		} catch (ClassNotFoundException e) {
			throw new DataStoreException("MySQL not present on the classpath");
		}
	}

	@Override
	protected void openPreparedStatements(Connection connection, PreparedConnectionType preparedConnectionType)
			throws SQLException {
		switch (preparedConnectionType) {
		case SAVE_PLAYER_STATS:
			mSavePlayerStats = connection.prepareStatement(
					"INSERT IGNORE INTO mh_Daily(ID, MOB_ID, PLAYER_ID) VALUES(DATE_FORMAT(NOW(), '%Y%j'),?,?);");
			break;
		case LOAD_ARCHIEVEMENTS:
			mLoadAchievements = connection
					.prepareStatement("SELECT ACHIEVEMENT, DATE, PROGRESS FROM mh_Achievements WHERE PLAYER_ID = ?;");
			break;
		case SAVE_ACHIEVEMENTS:
			mSaveAchievement = connection.prepareStatement("REPLACE INTO mh_Achievements VALUES(?,?,?,?);");
			break;
		case GET1PLAYER:
			mGetPlayerData[0] = connection.prepareStatement("SELECT * FROM mh_Players WHERE UUID=?;");
			break;
		case GET2PLAYERS:
			mGetPlayerData[1] = connection.prepareStatement("SELECT * FROM mh_Players WHERE UUID IN (?,?);");
			break;
		case GET5PLAYERS:
			mGetPlayerData[2] = connection.prepareStatement("SELECT * FROM mh_Players WHERE UUID IN (?,?,?,?,?);");
			break;
		case GET10PLAYERS:
			mGetPlayerData[3] = connection
					.prepareStatement("SELECT * FROM mh_Players WHERE UUID IN (?,?,?,?,?,?,?,?,?,?);");
			break;
		case GET_PLAYER_UUID:
			mGetPlayerUUID = connection.prepareStatement("SELECT UUID FROM mh_Players WHERE NAME=?;");
			break;
		case UPDATE_PLAYER_NAME:
			mUpdatePlayerName = connection.prepareStatement("UPDATE mh_Players SET NAME=? WHERE UUID=?;");
			break;
		case UPDATE_PLAYER_SETTINGS:
			mUpdatePlayerSettings = connection
					.prepareStatement("UPDATE mh_Players SET LEARNING_MODE=?,MUTE_MODE=? WHERE UUID=?;");
			break;
		case INSERT_PLAYER_DATA:
			mInsertPlayerData = connection.prepareStatement(
					"INSERT INTO mh_Players (UUID,NAME,LEARNING_MODE,MUTE_MODE) " + "VALUES(?,?,?,?);");
			break;
		case GET_BOUNTIES:
			mGetBounties = connection.prepareStatement(
					"SELECT * FROM mh_Bounties where STATUS=0 AND (BOUNTYOWNER_ID=? OR WANTEDPLAYER_ID=? OR NOT NPC_ID=0);");
			break;
		case INSERT_BOUNTY:
			mInsertBounty = connection.prepareStatement("REPLACE INTO mh_Bounties "
					+ "(MOBTYPE, BOUNTYOWNER_ID, WANTEDPLAYER_ID, NPC_ID, MOB_ID, WORLDGROUP, "
					+ "CREATED_DATE, END_DATE, PRIZE, MESSAGE, STATUS) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?);");
			break;
		case UPDATE_BOUNTY:
			mUpdateBounty = connection.prepareStatement("UPDATE mh_Bounties SET PRIZE=?,MESSAGE=?,END_DATE=?,STATUS=?"
					+ " WHERE WANTEDPLAYER_ID=? AND BOUNTYOWNER_ID=? AND WORLDGROUP=?;");
			break;
		case GET_PLAYER_BY_PLAYER_ID:
			mGetPlayerByPlayerId = connection.prepareStatement("SELECT UUID FROM mh_Players WHERE PLAYER_ID=?;");
			break;
		case DELETE_BOUNTY:
			mDeleteBounty = connection.prepareStatement(
					"DELETE FROM mh_Bounties WHERE WANTEDPLAYER_ID=? AND BOUNTYOWNER_ID=? AND WORLDGROUP=?;");
			break;
		case LOAD_MOBS:
			mLoadMobs = connection.prepareStatement("SELECT * FROM mh_Mobs;");
			break;
		case INSERT_MOBS:
			mInsertMobs = connection.prepareStatement("INSERT INTO mh_Mobs (PLUGIN_ID, MOBTYPE) VALUES (?,?);");
			break;
		case UPDATE_MOBS:
			mUpdateMobs = connection
					.prepareStatement("UPDATE mh_Mobs (PLUGIN_ID,MOBTYPE) VALUES (?,?) WHERE MOB_ID=?;");
			break;
		}

	}

	// *******************************************************************************
	// LoadStats / SaveStats
	// *******************************************************************************

	@SuppressWarnings("deprecation")
	@Override
	public List<StatStore> loadPlayerStats(StatType type, TimePeriod period, int count) throws DataStoreException {
		ArrayList<StatStore> list = new ArrayList<StatStore>();
		String id;
		// If The NPC has an invalid period or timeperiod return and empty list
		if (period == null || type == null) {
			return list;
		}
		switch (period) {
		case Day:
			id = "DATE_FORMAT(NOW(), '%Y%j')";
			break;
		case Week:
			id = "DATE_FORMAT(NOW(), '%Y%U')";
			break;
		case Month:
			id = "DATE_FORMAT(NOW(), '%Y%c')";
			break;
		case Year:
			id = "DATE_FORMAT(NOW(), '%Y')";
			break;
		default:
			id = null;
			break;
		}

		String column = "";
		if (type.getDBColumn().equalsIgnoreCase("achievement_count"))
			column = "sum(achievement_count) amount ";
		else if (type.getDBColumn().equalsIgnoreCase("total_kill"))
			column = "sum(total_kill) amount ";
		else if (type.getDBColumn().equalsIgnoreCase("total_assist"))
			column = "sum(total_assist) amount ";
		else if (type.getDBColumn().substring(type.getDBColumn().lastIndexOf("_"), type.getDBColumn().length())
				.equalsIgnoreCase("_kill"))
			column = "mh_Mobs.mob_id, mh_Mobs.MOBTYPE mt, sum(total_kill) amount ";
		else if (type.getDBColumn().substring(type.getDBColumn().lastIndexOf("_"), type.getDBColumn().length())
				.equalsIgnoreCase("_assist"))
			column = "mh_Mobs.mob_id, mh_Mobs.MOBTYPE mt, sum(total_assist) amount ";
		else
			column = "sum(total_kill) amount ";

		String wherepart = "";
		if (type.getDBColumn().equalsIgnoreCase("total_kill") || type.getDBColumn().equalsIgnoreCase("total_assist")
				|| type.getDBColumn().equalsIgnoreCase("achievement_count")) {
			wherepart = (id != null ? " AND ID=" + id : "");
		} else {
			wherepart = (id != null
					? " AND ID=" + id + " and mh_Mobs.MOB_ID=" + MobHunting.getExtendedMobManager().getMobIdFromMobTypeAndPluginID(
							type.getDBColumn().substring(0, type.getDBColumn().lastIndexOf("_")), MobPlugin.Minecraft)
					: " AND mh_Mobs.MOB_ID=" + MobHunting.getExtendedMobManager().getMobIdFromMobTypeAndPluginID(
							type.getDBColumn().substring(0, type.getDBColumn().lastIndexOf("_")), MobPlugin.Minecraft));
		}

		try {
			Statement statement = mConnection.createStatement();
			ResultSet results = statement
					.executeQuery("SELECT " + column + ", PLAYER_ID, mh_Players.UUID uuid, mh_Players.NAME name"
							+ " from mh_" + period.getTable() + " inner join mh_Players using (PLAYER_ID)"
							+ " inner join mh_Mobs using (MOB_ID) WHERE NAME IS NOT NULL " + wherepart
							+ " GROUP BY PLAYER_ID ORDER BY AMOUNT DESC LIMIT " + count);
			while (results.next()) {
				OfflinePlayer offlinePlayer = null;
				try {
					if (results.getString("uuid").equals(""))
						offlinePlayer = Bukkit.getOfflinePlayer(results.getString("name"));
					else
						offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(results.getString("uuid")));
				} catch (Exception e) {
					Bukkit.getLogger()
							.warning("Could not find player name for PLAYER_ID:" + results.getString("PLAYER_ID"));
				}
				if (offlinePlayer == null)
					Messages.debug("getOfflinePlayer(%s) was not in cache.", results.getString("name"));
				else
					list.add(new StatStore(type, offlinePlayer, results.getInt("amount")));
			}
			results.close();
			statement.close();
			return list;
		} catch (SQLException e) {
			throw new DataStoreException(e);
		}
	}

	@Override
	public void savePlayerStats(Set<StatStore> stats) throws DataStoreException {
		try {
			Messages.debug("Saving PlayerStats to Database.");
			// Make sure the stats are available for each player
			openPreparedStatements(mConnection, PreparedConnectionType.SAVE_PLAYER_STATS);
			mSavePlayerStats.clearBatch();
			for (StatStore st : stats) {
				int mob_id = 0;
				if (!st.getType().getDBColumn().substring(0, st.getType().getDBColumn().lastIndexOf("_"))
						.equalsIgnoreCase("achievement"))
					// if (!st.getType().equals(StatType.AchievementCount))
					mob_id = st.getMob().getMob_id();
				mSavePlayerStats.setInt(2, getPlayerId(st.getPlayer()));
				mSavePlayerStats.setInt(1, mob_id);
				mSavePlayerStats.addBatch();
			}
			mSavePlayerStats.executeBatch();
			mSavePlayerStats.close();

			// Now add each of the stats
			Statement statement = mConnection.createStatement();

			for (StatStore stat : stats) {
				String column = "";
				int mob_id;
				if (stat.getType().getDBColumn().substring(0, stat.getType().getDBColumn().lastIndexOf("_"))
						.equalsIgnoreCase("achievement")) {
					column = "achievement_count";
					mob_id = 0;
				} else {
					column = "total" + stat.getType().getDBColumn().substring(
							stat.getType().getDBColumn().lastIndexOf("_"), stat.getType().getDBColumn().length());
					mob_id = stat.getMob().getMob_id();
				}
				int amount = stat.getAmount();
				int player_id = getPlayerId(stat.getPlayer());
				statement
						.addBatch(
								String.format(
										"UPDATE mh_Daily SET %1$s = %1$s + %2$d, MOB_ID=%3$d WHERE ID = DATE_FORMAT(NOW(), '%%Y%%j')"
												+ " AND MOB_ID=%3$d AND PLAYER_ID = %4$d;",
										column, amount, mob_id, player_id));

			}
			statement.executeBatch();
			statement.close();
			mConnection.commit();
			Messages.debug("Saved.");
		} catch (SQLException e) {
			rollback();
			throw new DataStoreException(e);
		}
	}

	// *******************************************************************************
	// DATABASE SETUP / MIGRATION
	// *******************************************************************************

	@Override
	protected void setupV2Tables(Connection connection) throws SQLException {

		Statement create = connection.createStatement();

		// Prefix tables to mh_
		try {
			ResultSet rs = create.executeQuery("SELECT * from Daily LIMIT 0");
			rs.close();
			create.executeUpdate("RENAME TABLE Players TO mh_Players");
			create.executeUpdate("RENAME TABLE Daily TO mh_Daily");
			create.executeUpdate("RENAME TABLE Weekly TO mh_Weekly");
			create.executeUpdate("RENAME TABLE Monthly TO mh_Monthly");
			create.executeUpdate("RENAME TABLE Yearly TO mh_Yearly");
			create.executeUpdate("RENAME TABLE AllTime TO mh_AllTime");
			create.executeUpdate("RENAME TABLE Achievements TO mh_Achievements");

			create.executeUpdate("DROP TRIGGER IF EXISTS DailyInsert");
			create.executeUpdate("DROP TRIGGER IF EXISTS DailyUpdate");

		} catch (SQLException e) {
		}

		// Create new empty tables if they do not exist
		String lm = MobHunting.getConfigManager().learningMode ? "1" : "0";
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_Players (UUID CHAR(40) PRIMARY KEY, NAME CHAR(20), PLAYER_ID INTEGER NOT NULL AUTO_INCREMENT, "
						+ "KEY PLAYER_ID (PLAYER_ID), LEARNING_MODE INTEGER NOT NULL DEFAULT " + lm
						+ ", MUTE_MODE INTEGER NOT NULL DEFAULT 0)");
		String dataString = "";
		for (StatType type : StatType.values())
			dataString += ", " + type.getDBColumn() + " INTEGER NOT NULL DEFAULT 0";
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_Daily (ID CHAR(7) NOT NULL, PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE"
						+ dataString + ", PRIMARY KEY(ID, PLAYER_ID))");
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_Weekly (ID CHAR(6) NOT NULL, PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE"
						+ dataString + ", PRIMARY KEY(ID, PLAYER_ID))");
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_Monthly (ID CHAR(6) NOT NULL, PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE"
						+ dataString + ", PRIMARY KEY(ID, PLAYER_ID))");
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_Yearly (ID CHAR(4) NOT NULL, PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE"
						+ dataString + ", PRIMARY KEY(ID, PLAYER_ID))");
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_AllTime (PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE"
						+ dataString + ", PRIMARY KEY(PLAYER_ID))");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Achievements "
				+ "(PLAYER_ID INTEGER REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE, "
				+ "ACHIEVEMENT VARCHAR(64) NOT NULL, DATE DATETIME NOT NULL, "
				+ "PROGRESS INTEGER NOT NULL, PRIMARY KEY(PLAYER_ID, ACHIEVEMENT))");
		if (!MobHunting.getConfigManager().disablePlayerBounties)
			create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Bounties (" + "BOUNTYOWNER_ID INTEGER NOT NULL, "
					+ "MOBTYPE CHAR(6), " + "WANTEDPLAYER_ID INTEGER NOT NULL, " + "NPC_ID INTEGER, "
					+ "MOB_ID CHAR(40), " + "WORLDGROUP CHAR(20) NOT NULL, " + "CREATED_DATE BIGINT NOT NULL, "
					+ "END_DATE BIGINT NOT NULL, " + "PRIZE FLOAT NOT NULL, " + "MESSAGE CHAR(64), "
					+ "STATUS INTEGER NOT NULL DEFAULT 0, "
					+ "PRIMARY KEY(WORLDGROUP, WANTEDPLAYER_ID, BOUNTYOWNER_ID), "
					+ "FOREIGN KEY(BOUNTYOWNER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE, "
					+ "FOREIGN KEY(WANTEDPLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE" + ")");

		// Setup Database triggers
		setupTriggerV2(connection);

		create.close();

		connection.commit();

		performTableMigrateFromV1toV2(connection);

	}

	private void setupTriggerV2(Connection connection) throws SQLException {

		Statement create = connection.createStatement();

		// Workaround for no create trigger if not exists
		try {
			create.executeUpdate(
					"create trigger mh_DailyInsert after insert on mh_Daily for each row begin insert ignore into mh_Weekly(ID, PLAYER_ID) values(DATE_FORMAT(NOW(), '%Y%U'), NEW.PLAYER_ID); insert ignore into mh_Monthly(ID, PLAYER_ID) values(DATE_FORMAT(NOW(), '%Y%c'), NEW.PLAYER_ID); insert ignore into mh_Yearly(ID, PLAYER_ID) values(DATE_FORMAT(NOW(), '%Y'), NEW.PLAYER_ID); insert ignore into mh_AllTime(PLAYER_ID) values(NEW.PLAYER_ID); end"); //$NON-NLS-1$

			// Create the cascade update trigger. It will allow us to only
			// modify the Daily table, and the rest will happen automatically
			StringBuilder updateStringBuilder = new StringBuilder();

			for (StatType type : StatType.values()) {
				if (updateStringBuilder.length() != 0)
					updateStringBuilder.append(", ");

				updateStringBuilder.append(String.format("%s = (%1$s + (NEW.%1$s - OLD.%1$s)) ", type.getDBColumn()));
			}

			String updateString = updateStringBuilder.toString();

			StringBuilder updateTrigger = new StringBuilder();
			updateTrigger.append("create trigger mh_DailyUpdate after update on mh_Daily for each row begin ");

			// Weekly
			updateTrigger.append(" update mh_Weekly set ");
			updateTrigger.append(updateString);
			updateTrigger.append(" where ID=DATE_FORMAT(NOW(), '%Y%U') AND PLAYER_ID=New.PLAYER_ID;");

			// Monthly
			updateTrigger.append(" update mh_Monthly set ");
			updateTrigger.append(updateString);
			updateTrigger.append(" where ID=DATE_FORMAT(NOW(), '%Y%c') AND PLAYER_ID=New.PLAYER_ID;");

			// Yearly
			updateTrigger.append(" update mh_Yearly set ");
			updateTrigger.append(updateString);
			updateTrigger.append(" where ID=DATE_FORMAT(NOW(), '%Y') AND PLAYER_ID=New.PLAYER_ID;");

			// AllTime
			updateTrigger.append(" update mh_AllTime set ");
			updateTrigger.append(updateString);
			updateTrigger.append(" where PLAYER_ID=New.PLAYER_ID;");

			updateTrigger.append("END");

			create.executeUpdate(updateTrigger.toString());
		} catch (SQLException e) {
			// Do Nothing
		}

		create.close();
		connection.commit();
	}

	private void performTableMigrateFromV1toV2(Connection connection) throws SQLException {
		performUUIDMigrateV2(connection);
		performAddNewMobsIntoV2(connection);
	}

	private void performUUIDMigrateV2(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			ResultSet rs = statement.executeQuery("SELECT UUID from `mh_Players` LIMIT 0");
			rs.close();
			statement.close();
			return; // UUIDs are in place

		} catch (SQLException e) {
		}

		System.out.println("[MobHunting] Migrating MobHunting Database Player ID to Player UUID.");

		// Add missing columns
		// Statement statement = connection.createStatement();
		statement.executeUpdate(
				"alter table `mh_Players` add column `UUID` CHAR(40) default '**UNSPEC**' NOT NULL first");

		// Get UUID and update table
		ResultSet rs = statement.executeQuery("select `NAME` from `mh_Players`");
		UUIDHelper.initialize();

		PreparedStatement insert = connection.prepareStatement("update `mh_Players` set `UUID`=? where `NAME`=?");

		StringBuilder failString = new StringBuilder();
		int failCount = 0;
		while (rs.next()) {
			String player = rs.getString(1);
			UUID id = UUIDHelper.getKnown(player);
			if (id != null) {
				insert.setString(1, id.toString());
				insert.setString(2, player);
				insert.addBatch();
			} else {
				if (failString.length() != 0)
					failString.append(", ");
				failString.append(player);
				++failCount;
			}
		}

		UUIDHelper.clearCache();

		rs.close();

		if (failCount > 0) {
			System.err.println("[MobHunting] " + failCount + " accounts failed to convert:");
			System.err.println("[MobHunting] " + failString.toString());
		}

		insert.executeBatch();
		insert.close();

		int modified = statement.executeUpdate("delete from `mh_Players` where `UUID`='**UNSPEC**'");
		System.out.println("[MobHunting]" + modified + " players were removed due to missing UUIDs");

		statement.executeUpdate("alter table `mh_Players` drop primary key");
		statement.executeUpdate("alter table `mh_Players` modify `UUID` CHAR(40) NOT NULL PRIMARY KEY first");

		System.out.println("[MobHunting] Player UUID migration complete.");

		statement.close();
		connection.commit();
	}

	private void performAddNewMobsIntoV2(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();

		try {
			ResultSet rs = statement.executeQuery("SELECT Bat_kill from `mh_Daily` LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding Passive Mobs to MobHunting Database ");

			statement.executeUpdate("alter table `mh_Daily` add column `Bat_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Bat_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Bat_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Bat_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Bat_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Bat_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Bat_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Bat_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Bat_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Bat_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Chicken_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Chicken_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Chicken_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Chicken_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Chicken_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Chicken_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Chicken_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Chicken_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Chicken_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Chicken_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Cow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Cow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Cow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Cow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Cow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Cow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Cow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Cow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Cow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Cow_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Horse_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Horse_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Horse_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Horse_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Horse_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Horse_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Horse_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Horse_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Horse_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Horse_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `MushroomCow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Daily` add column `MushroomCow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `MushroomCow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `MushroomCow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `MushroomCow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `MushroomCow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `MushroomCow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `MushroomCow_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `MushroomCow_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `MushroomCow_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Ocelot_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Ocelot_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Ocelot_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Ocelot_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Ocelot_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Ocelot_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Ocelot_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Ocelot_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Ocelot_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Ocelot_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Pig_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Pig_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Pig_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Pig_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Pig_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Pig_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Pig_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Pig_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Pig_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Pig_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate(
					"alter table `mh_Daily` add column `PassiveRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Daily` add column `PassiveRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `PassiveRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `PassiveRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `PassiveRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `PassiveRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `PassiveRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `PassiveRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `PassiveRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `PassiveRabbit_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Sheep_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Sheep_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Sheep_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Sheep_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Sheep_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Sheep_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Sheep_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Sheep_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Sheep_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Sheep_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Snowman_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Snowman_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Snowman_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Snowman_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Snowman_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Snowman_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Snowman_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Snowman_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Snowman_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Snowman_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Squid_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Squid_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Squid_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Squid_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Squid_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Squid_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Squid_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Squid_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Squid_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Squid_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Villager_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Villager_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Villager_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Villager_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Villager_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Monthly` add column `Villager_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Villager_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Villager_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Villager_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_AllTime` add column `Villager_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Wolf_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Wolf_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Wolf_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Wolf_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Wolf_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Wolf_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Wolf_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Wolf_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Wolf_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Wolf_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding passive mobs complete.");

		}
		try {
			ResultSet rs = statement.executeQuery("SELECT EnderDragon_kill from `mh_Daily` LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding EnderDragon to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `EnderDragon_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Daily` add column `EnderDragon_assist`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `EnderDragon_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `EnderDragon_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `EnderDragon_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `EnderDragon_assist`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `EnderDragon_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `EnderDragon_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `EnderDragon_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `EnderDragon_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding EnderDragon complete.");

		}
		try {
			ResultSet rs = statement.executeQuery("SELECT IronGolem_kill from `mh_Daily` LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding IronGolem to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `IronGolem_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `IronGolem_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `IronGolem_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `IronGolem_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `IronGolem_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `IronGolem_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `IronGolem_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `IronGolem_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `IronGolem_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `IronGolem_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding IronGolem complete.");

		}
		try {
			ResultSet rs = statement.executeQuery("SELECT PvpPlayer_kill from `mh_Daily` LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding new PvpPlayer to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `PvpPlayer_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `PvpPlayer_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `PvpPlayer_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `PvpPlayer_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `PvpPlayer_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `PvpPlayer_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `PvpPlayer_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `PvpPlayer_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `PvpPlayer_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `PvpPlayer_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding new PvpPlayer complete.");

		}
		try {
			ResultSet rs = statement.executeQuery("SELECT Giant_kill from `mh_Daily` LIMIT 0");
			rs.close();

		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding new 1.8 Mobs to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `Endermite_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Endermite_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Endermite_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `Endermite_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Endermite_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `Endermite_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Endermite_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `Endermite_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Endermite_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `Endermite_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Giant_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Giant_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Giant_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Giant_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Giant_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Giant_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Giant_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Giant_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Giant_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Giant_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Guardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Guardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_eekly` add column `Guardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Guardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Guardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Monthly` add column `Guardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Guardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Guardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Guardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_AllTime` add column `Guardian_assist`  INTEGER NOT NULL DEFAULT 0");

			statement
					.executeUpdate("alter table `mh_Daily` add column `KillerRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Daily` add column `KillerRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `KillerRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `KillerRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `KillerRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `KillerRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `KillerRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `KillerRabbit_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `KillerRabbit_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `KillerRabbit_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding new 1.8 Mobs complete.");

		}

		try {
			ResultSet rs = statement.executeQuery("SELECT Shulker_kill from `mh_Daily` LIMIT 0");
			rs.close();

		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding new 1.9 Mobs (Shulker) to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `Shulker_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Shulker_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Shulker_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Shulker_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Shulker_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Shulker_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Shulker_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Shulker_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Shulker_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Shulker_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding new 1.9 Mobs (Shulker) complete.");

		}

		try {
			ResultSet rs = statement.executeQuery("SELECT PolarBear_kill from `mh_Daily` LIMIT 0");
			rs.close();

		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding new 1.10 Mobs (Polar Bear) to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `PolarBear_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `PolarBear_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `PolarBear_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Weekly` add column `PolarBear_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `PolarBear_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `PolarBear_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `PolarBear_kill`  INTEGER NOT NULL DEFAULT 0");
			statement
					.executeUpdate("alter table `mh_Yearly` add column `PolarBear_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `PolarBear_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `PolarBear_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding new 1.10 Mobs (Polar Bear) complete.");

		}

		try {
			ResultSet rs = statement.executeQuery("SELECT Stray_kill from mh_Daily LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding new 1.10 Mobs (Stray + Husk) to MobHunting Database.");

			statement.executeUpdate("alter table `mh_Daily` add column `Stray_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Stray_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Stray_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Stray_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Stray_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Stray_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Stray_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Stray_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Stray_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Stray_assist`  INTEGER NOT NULL DEFAULT 0");

			statement.executeUpdate("alter table `mh_Daily` add column `Husk_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Daily` add column `Husk_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Husk_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Weekly` add column `Husk_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Husk_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Monthly` add column `Husk_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Husk_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_Yearly` add column `Husk_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Husk_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate("alter table `mh_AllTime` add column `Husk_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding new 1.10 Mobs (Stray + Husk) complete.");
		}

		try {
			ResultSet rs = statement.executeQuery("SELECT ElderGuardian_kill from mh_Daily LIMIT 0");
			rs.close();
		} catch (SQLException e) {

			System.out.println("[MobHunting] Adding 1.8 Mob (Elder Guardian) to MobHunting Database.");

			statement.executeUpdate(
					"alter table `mh_Daily` add column `ElderGuardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Daily` add column `ElderGuardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `ElderGuardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Weekly` add column `ElderGuardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `ElderGuardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Monthly` add column `ElderGuardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `ElderGuardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_Yearly` add column `ElderGuardian_assist`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `ElderGuardian_kill`  INTEGER NOT NULL DEFAULT 0");
			statement.executeUpdate(
					"alter table `mh_AllTime` add column `ElderGuardian_assist`  INTEGER NOT NULL DEFAULT 0");

			System.out.println("[MobHunting] Adding 1.8 Mob (Elder Guardian) complete.");
		}

		try {
			ResultSet rs = statement.executeQuery("SELECT LEARNING_MODE from mh_Players LIMIT 0");
			rs.close();
		} catch (SQLException e) {
			System.out.println("[MobHunting] Adding new Player leaning mode to MobHunting Database.");
			String lm = MobHunting.getConfigManager().learningMode ? "1" : "0";
			statement.executeUpdate(
					"alter table `mh_Players` add column `LEARNING_MODE` INTEGER NOT NULL DEFAULT " + lm);
		}

		try {
			ResultSet rs = statement.executeQuery("SELECT MUTE_MODE from mh_Players LIMIT 0");
			rs.close();
		} catch (SQLException e) {
			System.out.println("[MobHunting] Adding new Player mute mode to MobHunting Database.");
			statement.executeUpdate("alter table `mh_Players` add column `MUTE_MODE` INTEGER NOT NULL DEFAULT 0");
		}

		System.out.println("[MobHunting] Updating database triggers.");
		statement.executeUpdate("DROP TRIGGER IF EXISTS `mh_DailyInsert`");
		statement.executeUpdate("DROP TRIGGER IF EXISTS `mh_DailyUpdate`");
		setupTriggerV2(connection);

		statement.close();
		connection.commit();
	}

	// *******************************************************************************
	// V3 DATABASE SETUP / MIGRATION
	// *******************************************************************************

	@Override
	protected void setupV3Tables(Connection connection) throws SQLException {
		Statement create = connection.createStatement();

		// Create new empty tables if they do not exist
		String lm = MobHunting.getConfigManager().learningMode ? "1" : "0";
		Messages.debug("Create mh_Players if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Players " + "(UUID CHAR(40) ," + " NAME VARCHAR(20),"
				+ " PLAYER_ID INTEGER NOT NULL AUTO_INCREMENT," + " LEARNING_MODE INTEGER NOT NULL DEFAULT " + lm + ","
				+ " MUTE_MODE INTEGER NOT NULL DEFAULT 0," + " PRIMARY KEY (PLAYER_ID))");

		Messages.debug("Create mh_Mobs if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Mobs " + "(MOB_ID INTEGER NOT NULL AUTO_INCREMENT,"
				+ " PLUGIN_ID INTEGER NOT NULL," 
				+ " MOBTYPE VARCHAR(30)," 
				+ " PRIMARY KEY(MOB_ID))");

		Messages.debug("Create mh_Daily if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Daily " + "(ID CHAR(7) NOT NULL,"
				+ " MOB_ID INTEGER NOT NULL," + " PLAYER_ID INTEGER NOT NULL," + " ACHIEVEMENT_COUNT INTEGER DEFAULT 0,"
				+ " TOTAL_KILL INTEGER DEFAULT 0," + " TOTAL_ASSIST INTEGER DEFAULT 0,"
				+ " PRIMARY KEY(ID, MOB_ID, PLAYER_ID),"
				+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE,"
				+ " FOREIGN KEY(MOB_ID) REFERENCES mh_Mobs(MOB_ID) ON DELETE CASCADE)");

		Messages.debug("Create mh_Weekly if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Weekly " + "(ID CHAR(6) NOT NULL,"
				+ " MOB_ID INTEGER NOT NULL," + " PLAYER_ID INTEGER NOT NULL," + " ACHIEVEMENT_COUNT INTEGER DEFAULT 0,"
				+ " TOTAL_KILL INTEGER DEFAULT 0," + " TOTAL_ASSIST INTEGER DEFAULT 0,"
				+ " PRIMARY KEY(ID, MOB_ID, PLAYER_ID),"
				+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE,"
				+ " FOREIGN KEY(MOB_ID) REFERENCES mh_Mobs(MOB_ID) ON DELETE CASCADE)");

		Messages.debug("Create mh_Monthly if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Monthly " + "(ID CHAR(6) NOT NULL,"
				+ " MOB_ID INTEGER NOT NULL," + " PLAYER_ID INTEGER NOT NULL," + " ACHIEVEMENT_COUNT INTEGER DEFAULT 0,"
				+ " TOTAL_KILL INTEGER DEFAULT 0," + " TOTAL_ASSIST INTEGER DEFAULT 0,"
				+ " PRIMARY KEY(ID, MOB_ID, PLAYER_ID),"
				+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE,"
				+ " FOREIGN KEY(MOB_ID) REFERENCES mh_Mobs(MOB_ID) ON DELETE CASCADE)");

		Messages.debug("Create mh_Yearly if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Yearly " + "(ID CHAR(4) NOT NULL,"
				+ " MOB_ID INTEGER NOT NULL," + " PLAYER_ID INTEGER NOT NULL," + " ACHIEVEMENT_COUNT INTEGER DEFAULT 0,"
				+ " TOTAL_KILL INTEGER DEFAULT 0," + " TOTAL_ASSIST INTEGER DEFAULT 0,"
				+ " PRIMARY KEY(ID, MOB_ID, PLAYER_ID),"
				+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE,"
				+ " FOREIGN KEY(MOB_ID) REFERENCES mh_Mobs(MOB_ID) ON DELETE CASCADE)");

		Messages.debug("Create mh_AllTime if missing");
		create.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mh_AllTime " + "(MOB_ID INTEGER NOT NULL," + " PLAYER_ID INTEGER NOT NULL,"
						+ " ACHIEVEMENT_COUNT INTEGER DEFAULT 0," + " TOTAL_KILL INTEGER DEFAULT 0,"
						+ " TOTAL_ASSIST INTEGER DEFAULT 0," + " PRIMARY KEY(MOB_ID, PLAYER_ID),"
						+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE,"
						+ " FOREIGN KEY(MOB_ID) REFERENCES mh_Mobs(MOB_ID) ON DELETE CASCADE)");

		Messages.debug("Create mh_Achievements if missing");
		create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Achievements " 
				+ "(PLAYER_ID INTEGER NOT NULL,"
				+ " ACHIEVEMENT VARCHAR(64) NOT NULL," 
				+ " DATE DATETIME NOT NULL," 
				+ " PROGRESS INTEGER NOT NULL,"
				+ " PRIMARY KEY(PLAYER_ID, ACHIEVEMENT),"
				+ " FOREIGN KEY(PLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE)");

		if (!MobHunting.getConfigManager().disablePlayerBounties) {
			Messages.debug("Create mh_Bounties if missing");
			create.executeUpdate("CREATE TABLE IF NOT EXISTS mh_Bounties (" + "BOUNTYOWNER_ID INTEGER NOT NULL, "
					+ "MOBTYPE CHAR(6), " + "WANTEDPLAYER_ID INTEGER NOT NULL, " + "NPC_ID INTEGER, "
					+ "MOB_ID VARCHAR(40), " + "WORLDGROUP VARCHAR(20) NOT NULL, " + "CREATED_DATE BIGINT NOT NULL, "
					+ "END_DATE BIGINT NOT NULL, " + "PRIZE FLOAT NOT NULL, " + "MESSAGE VARCHAR(64), "
					+ "STATUS INTEGER NOT NULL DEFAULT 0, "
					+ "PRIMARY KEY(WORLDGROUP, WANTEDPLAYER_ID, BOUNTYOWNER_ID), "
					+ "FOREIGN KEY(BOUNTYOWNER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE, "
					+ "FOREIGN KEY(WANTEDPLAYER_ID) REFERENCES mh_Players(PLAYER_ID) ON DELETE CASCADE" + ")");
		}

		// Setup Database triggers
		create.executeUpdate("DROP TRIGGER IF EXISTS `mh_DailyInsert`");
		create.executeUpdate("DROP TRIGGER IF EXISTS `mh_DailyUpdate`");
		// setupTriggerV3(connection);

		insertMissingVanillaMobs();

		create.close();
		connection.commit();

		Messages.debug("MobHunting V3 Database created.");

	}

	@Override
	protected void setupTriggerV3(Connection connection) throws SQLException {
		// TODO: create trigger
		Statement create = connection.createStatement();

		// Workaround for no create trigger if not exists
		try {
			create.executeUpdate("create trigger mh_DailyInsert after insert on mh_Daily for each row" + " begin"

					+ " insert ignore into mh_Weekly(ID, MOB_ID, PLAYER_ID, ACHIEVEMENT_COUNT, TOTAL_KILL, TOTAL_ASSIST)"
					+ " values(DATE_FORMAT(NOW(), '%Y%U'), NEW.MOB_ID, NEW.PLAYER_ID, NEW.ACHIEVEMENT_COUNT, NEW.TOTAL_KILL, NEW.TOTAL_ASSIST);"

					+ " insert ignore into mh_Monthly(ID, MOB_ID, PLAYER_ID, ACHIEVEMENT_COUNT, TOTAL_KILL, TOTAL_ASSIST)"
					+ " values(DATE_FORMAT(NOW(), '%Y%c'), NEW.MOB_ID, NEW.PLAYER_ID, NEW.ACHIEVEMENT_COUNT, NEW.TOTAL_KILL, NEW.TOTAL_ASSIST);"

					+ " insert ignore into mh_Yearly(ID, MOB_ID, PLAYER_ID, ACHIEVEMENT_COUNT, TOTAL_KILL, TOTAL_ASSIST)"
					+ " values(DATE_FORMAT(NOW(), '%Y'), NEW.MOB_ID, NEW.PLAYER_ID, NEW.ACHIEVEMENT_COUNT, NEW.TOTAL_KILL, NEW.TOTAL_ASSIST);"

					+ " insert ignore into mh_AllTime(MOB_ID, PLAYER_ID, ACHIEVEMENT_COUNT, TOTAL_KILL, TOTAL_ASSIST)"
					+ " values(NEW.MOB_ID, NEW.PLAYER_ID, NEW.ACHIEVEMENT_COUNT, NEW.TOTAL_KILL, NEW.TOTAL_ASSIST);"

					+ " end");

			// Create the cascade update trigger. It will allow us to only
			// modify the Daily table, and the rest will happen automatically
			StringBuilder updateStringBuilder = new StringBuilder();

			updateStringBuilder.append(String.format("%s = (%1$s + (NEW.%1$s - OLD.%1$s)), ", "ACHIEVEMENT_COUNT"));
			updateStringBuilder.append(String.format("%s = (%1$s + (NEW.%1$s - OLD.%1$s)), ", "TOTAL_KILL"));
			updateStringBuilder.append(String.format("%s = (%1$s + (NEW.%1$s - OLD.%1$s)) ", "TOTAL_ASSIST"));

			String updateString = updateStringBuilder.toString();

			StringBuilder updateTrigger = new StringBuilder();
			updateTrigger.append("create trigger mh_DailyUpdate after update on mh_Daily for each row begin ");

			// Weekly
			updateTrigger.append(" update mh_Weekly set ");
			updateTrigger.append(updateString);
			updateTrigger
					.append(" where ID=DATE_FORMAT(NOW(), '%Y%U') AND MOB_ID=New.MOB_ID AND PLAYER_ID=New.PLAYER_ID;");

			// Monthly
			updateTrigger.append(" update mh_Monthly set ");
			updateTrigger.append(updateString);
			updateTrigger
					.append(" where ID=DATE_FORMAT(NOW(), '%Y%c') AND MOB_ID=New.MOB_ID AND PLAYER_ID=New.PLAYER_ID;");

			// Yearly
			updateTrigger.append(" update mh_Yearly set ");
			updateTrigger.append(updateString);
			updateTrigger
					.append(" where ID=DATE_FORMAT(NOW(), '%Y') AND MOB_ID=New.MOB_ID AND PLAYER_ID=New.PLAYER_ID;");

			// AllTime
			updateTrigger.append(" update mh_AllTime set ");
			updateTrigger.append(updateString);
			updateTrigger.append(" where MOB_ID=New.MOB_ID AND PLAYER_ID=New.PLAYER_ID;");

			updateTrigger.append("END");

			create.executeUpdate(updateTrigger.toString());
		} catch (SQLException e) {
			// Do Nothing
		}

		create.close();
		connection.commit();
	}

}
