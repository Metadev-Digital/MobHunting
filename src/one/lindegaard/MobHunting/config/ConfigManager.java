package one.lindegaard.MobHunting.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import one.lindegaard.MobHunting.MobHunting;

import org.bukkit.configuration.InvalidConfigurationException;

public class ConfigManager extends AutoConfig {

	private MobHunting plugin;

	public ConfigManager(MobHunting plugin, File file) {

		super(file);
		this.plugin = plugin;

		setCategoryComment("header",
				"########################################################################"
						+ "\nMobHunting Configuration File"
						+ "\n########################################################################\n");

		setCategoryComment("example", "########################################################################"
				+ "\nExample of a mob configuration of rewards for killing a mob."
				+ "\n########################################################################"
				+ "\nHere is where you set the base prize in $ for killing a mob of each type"
				+ "\nYou can either set a decimal number ex 1.23 or a range 1.23:2.23"
				+ "\n\nFor each kill you can run a console command to give the player a reward."
				+ "\nYou can use the following variables:"
				+ "\n{killer},{killed},{player},{killed_player},{prize},{world},"
				+ "\n{killerpos},{killedpos}. Killerpos and Killedpos will have the "
				+ "\nformat <x> <y> <z>. Which could be used to /summon items. "
				+ "\nAn example could be /summon apple {killedpos} 2. to summon two apples where"
				+ "\nwhere the mob was killed or /summon apple {killerpos} 1. to summon an"
				+ "\nan apple where the player is." + "\nAnother example could be to give the player permission to fly"
				+ "\nfor 1 hour or use give command to the player items."
				+ "\n\nYou can also specify the message send to the player."
				+ "\nThe text can be color coded with these codes:"
				+ "\nhttp://minecraft.gamepedia.com/Formatting_codes"
				+ "\n\nYou can run many console commands on each line, each command" + "\nmust be separated by |"
				+ "\nThe player will have the cmd run in {mob_cmd_run_chance} times in average. If mob_cmd_run_chance=0 it"
				+ "\nwill never run. If f.ex. mob_cmd_run_chance=0.50 and it will run run every second time in average."
				+ "\n\nThe mobname_head_prize is only used if you want the dropped heads after killing a mob to have a value."
				+ "\nPlease also check the \"dropmoneyonground\" section in this file.");

		setCategoryComment("mobs",
				"########################################################################"
						+ "\nRewards for killing mobs."
						+ "\n########################################################################"
						+ "\nHere is where you set the rewards for killing agressive mobs.");

		setCategoryComment("mobs.blaze", "### Blaze settings ###");
		setCategoryComment("mobs.cave_spider", "### Cave Spider settings ###");
		setCategoryComment("mobs.creeper", "### Creeper settings ###");
		setCategoryComment("mobs.elder_guardian", "### Elder Guardian settings ###");
		setCategoryComment("mobs.enderman", "### Enderman settings ###");
		setCategoryComment("mobs.endermite", "### Endermite settings ###");
		setCategoryComment("mobs.ghast", "### Ghast settings ###");
		setCategoryComment("mobs.giant", "### Giant settings ###");
		setCategoryComment("mobs.iron_golem", "### Iron Golem settings ###");
		setCategoryComment("mobs.guardian", "### Guardian settings ###");
		setCategoryComment("mobs.husk", "### Husk settings ###");
		setCategoryComment("mobs.killer_rabbit", "### Killer Rabbit settings ###");
		setCategoryComment("mobs.magma_cube", "### Magma Cube settings ###");
		setCategoryComment("mobs.polar_bear", "### Polar Bear settings ###");
		setCategoryComment("mobs.slime", "### Slime settings ###");
		setCategoryComment("mobs.shulker", "### Shulker settings ###");
		setCategoryComment("mobs.silverfish", "### Silverfish settings ###");
		setCategoryComment("mobs.skeleton", "### Skeleton settings ###");
		setCategoryComment("mobs.spider", "### Spider settings ###");
		setCategoryComment("mobs.stray", "### Stray settings ###");
		setCategoryComment("mobs.zombie", "### Zombie settings ###");
		setCategoryComment("mobs.zombie_pigman", "### Zombie Pigman settings ###");
		setCategoryComment("mobs.vex", "### Vex settings ###");
		setCategoryComment("mobs.witch", "### Witch settings ###");
		setCategoryComment("mobs.wither_skeleton", "### Wither Skeleton settings ###");

		setCategoryComment("boss",
				"########################################################################"
						+ "\nRewards for killing bosses"
						+ "\n########################################################################"
						+ "\nHere is where you set the base prize in $ for killing the bosses");

		setCategoryComment("boss.wither", "### Wither settings ###");
		setCategoryComment("boss.ender_dragon", "### Ender Dragon settings ###");

		setCategoryComment("villager",
				"########################################################################"
						+ "\nRewards for killing villagers"
						+ "\n########################################################################"
						+ "\nHere is where you set the base prize in $ for killing the villagers"
						+ "\nMobHunting only handle Villagers on profession level, all careers is "
						+ "\nhandles as their profession. Info anbout Profession and Caarer:"
						+ "\nhttp://minecraft.gamepedia.com/Villager#Professions_and_careers");

		setCategoryComment("villager.blacksmith", "### Blacksmith settings ###");
		setCategoryComment("villager.butcher", "### Butcher settings ###");
		setCategoryComment("villager.evoker", "### Evoker settings ###");
		setCategoryComment("villager.farmer", "### Farmer settings ###");
		setCategoryComment("villager.illusioner", "### Illusioner settings ###");
		setCategoryComment("villager.nitwit", "### Nitwit settings ###");
		setCategoryComment("villager.priest", "### Priest settings ###");
		setCategoryComment("villager.villager", "### Villager settings ###");
		setCategoryComment("villager.vindicator", "### Vindicator settings ###");
		setCategoryComment("villager.zombie_villager", "### Zombie Villager settings ###");

		setCategoryComment("passive",
				"########################################################################"
						+ "\nRewards for killing passive mobs"
						+ "\n########################################################################"
						+ "\nHere is where you set the base prize in $ for killing passive/friendly mobs."
						+ "\nBy default the player does not get a reward for killing friendly mobs."
						+ "\nIf you make the number negative, the reward will be a fine for killing a passive animal.");

		setCategoryComment("passive.bat", "### Bat settings ###");
		setCategoryComment("passive.chicken", "### Chicken settings ###");
		setCategoryComment("passive.cow", "### Cow settings ###");
		setCategoryComment("passive.donkey", "### Donkey settings ###");
		setCategoryComment("passive.horse", "### Horse settings ###");
		setCategoryComment("passive.llama", "### Llama settings ###");
		setCategoryComment("passive.mule", "### Mule settings ###");
		setCategoryComment("passive.mushroom_cow", "### Mushroom Cow settings ###");
		setCategoryComment("passive.ocelot", "### Ocelot settings ###");
		setCategoryComment("passive.parrot", "### Parrot settings ###");
		setCategoryComment("passive.pig", "### Pig settings ###");
		setCategoryComment("passive.rabbit", "### Rabbit settings ###");
		setCategoryComment("passive.sheep", "### Sheep settings ###");
		setCategoryComment("passive.skeleton_horse", "### Skeleton Horse settings ###");
		setCategoryComment("passive.snowman", "### Snowman settings ###");
		setCategoryComment("passive.squid", "### Squid settings ###");
		setCategoryComment("passive.wolf", "### Wolf settings ###");
		setCategoryComment("passive.zombie_horse", "### Zombie Horse settings ###");

		setCategoryComment("fishing",
				"########################################################################" + "\nRewards for fishing"
						+ "\n########################################################################"
						+ "\nHere is where you set the base prize in $ for catching a fish");

		setCategoryComment("fishing.raw_fish", "### Raw Fish settings ###");
		setCategoryComment("fishing.raw_salmon", "### Raw Salmon settings ###");
		setCategoryComment("fishing.clownfish", "### Clownfish settings ###");
		setCategoryComment("fishing.pufferfish", "### Pufferfish settings ###");

		setCategoryComment("pvp", "########################################################################"
				+ "\nPvp rewards" + "\n########################################################################"
				+ "\nPvp configuration. Set pvp_allowed = true if you want give the players a reward when they kill eachother."
				+ "\nYou can alsp run a console command when this happens to give the player a reward or punish him."
				+ "\nYou can you the following variables {player},{world},{killed_player}."
				+ "\nAn example could be to give the player permission to fly "
				+ "\nfor 1 hour or use give command to the player items."
				+ "\nYou can also specify the message send to the player."
				+ "\nYou can run many console commands on each line, each command" + "\nmust be separated by |");

		setCategoryComment("pvp.player.money",
				"The kill prize can be a number to steal x dollars from the killed player,"
						+ "\nor it can be a cut in percent of his balance. Rob from victiom is about where the money comes from."
						+ "\nIf FALSE the money comes from from the server, if TRUE the money comes from the dead player."
						+ "\nIf you dont want the player to get any money for PVP kills, you MUST set pvp_kill_prize: 0");

		setCategoryComment("pvp.player.head", "Drop a head of the killed player");

		setCategoryComment("achievements",
				"########################################################################"
						+ "\nSpecial / Achievements rewards"
						+ "\n########################################################################"
						+ "\nHere is where you set the prize in $ for achieving a special kill. "
						+ "\nFor each achievment you can run a console command to give the player a reward. "
						+ "\nYou can use the following variables {player},{world}, {killerpos},"
						+ "\n{monstertype} and more can be added on request."
						+ "\nmonstertype is the monstername. A valid list can be found in your "
						+ "\nlang file. Ex. if it is mobs.skeleton.name, monstertype will return skeleton"
						+ "\nAn example command could be to give the player permission to fly "
						+ "\nfor 1 hour or use give command to the player items."
						+ "\nYou can also specify the message send to the player."
						+ "\nYou can run many console commands on each line, each command" + "\nmust be separated by |"
						+ "\nAchievements will not be shown in the GUI if there is a reward for killing the mob,"
						+ "\nunless you set show_achievements_without_reward=true.");

		setCategoryComment("achievements.specials",
				"########################################################################" + "\n### Specials ###"
						+ "\n########################################################################");

		setCategoryComment("achievements.hunter",
				"########################################################################" + "\n### Hunter Levels ###"
						+ "\n########################################################################");

		setCategoryComment("achievements.hunter.mob_level", "Achievement Hunter Levels - First Mob level"
				+ "\nHere is where you set how many mobs to kill to reach next level per mob."
				+ "\nYou can only set the number of mobs to kill to reach level 1. the next"
				+ "\nlevels is automatically calculated this way." + "\nLevel 1: 100   (100 kills)"
				+ "\nLevel 2: x 2.5 (250 kills)" + "\nLevel 3: x 5   (500 kills)" + "\nLevel 4: x 10  (1000 kills)"
				+ "\nLevel 5: x 25  (2500 kills)" + "\nLevel 6: x 50  (5000 kills)" + "\nLevel 7: x 100 (10000 kills)"
				+ "\nLevel Achievements can be disabled by setting the number to 0");

		setCategoryComment("assists",
				"########################################################################"
						+ "\nRewards for assisting killings"
						+ "\n########################################################################"
						+ "\nThey players can get an extra reward if they help each other killing mobs.");

		setCategoryComment("grinding",
				"########################################################################"
						+ "\nGrinding detection settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of the grinding detection.");

		setCategoryComment("grinding.area", "Area grinding detection."
				+ "\nEnabling this prevents a player from earning too much money from using a mob grinder."
				+ "\nSet 'enable_grinding_detection: false' to disable the grinding detection."
				+ "\nOBS: You can whitelist an area to allow grinding using '/mobhunt whitelistarea <add|remove>'"
				+ "\nif the area is detected as a grinding area. See also '/mobhunt checkgrinding'"
				+ "\nFor each kill MobHunting check the number of kills within the range"
				+ "\nIf number of kills exceeds 10, the reward will decrese with 10% until the 'number of deaths'"
				+ "\nis reached, whereafter the reward will be zero.");

		setCategoryComment("grinding.farms", "Detect Grinding Farms."
				+ "\nWhen this is true, the plugin will try to detect if the players has build a Mob Grinding Farm."
				+ "\nFarm detection can be completly disabled or you can whitelist an area using the whitelist"
				+ "\ncommand if you want the players to harvest mobs from a farm.");

		setCategoryComment("grinding.nether_gold_farms", "Nether Gold Farm detection."
				+ "\nWhen this is true, the plugin will try to detect if the players has build a Nether Gold Farm."
				+ "\nThere is no guarantie that the plugin can detect all types of Nether Gold farms, but it has"
				+ "\nbeen testet on this one: https://www.youtube.com/watch?v=jQWG9Q7HoUA"
				+ "\nWhen searching for grinding the plugin measures how many mobs dies per timeframe within a range."
				+ "\nBe careful if you chance this number there is a risk for false positives.");

		setCategoryComment("grinding.otherfarms",
				"Other Farm detection."
						+ "\nWhen this is true, the plugin will try to detect if the players has build other Farms"
						+ "\nwhere different mobs is falling into death. The plugin is still counting mobs which"
						+ "\ndies from falling, with in a range and a time frame.");

		setCategoryComment("multiplier",
				"########################################################################" + "\nMultiplier Section"
						+ "\n########################################################################" + "\n");

		setCategoryComment("multiplier.penalty",
				"########################################################################" + "\nPenalty multipliers"
						+ "\n########################################################################"
						+ "\nThese are penalty multipliers that can modify the base prize. "
						+ "\nREMEMBER: These are not in $ but they are a multiplier. "
						+ "\nSetting to 1 will disable them.");

		setCategoryComment("multiplier.killstreak",
				"########################################################################"
						+ "\nReward for kills in a row"
						+ "\n########################################################################"
						+ "\nSet the multiplier when the player kills 1,2,3,4 mob in a row without getting damage."
						+ "\nKillstreak will be disabled if you set the multiplier: 1.0");

		setCategoryComment("multiplier.rank", "########################################################################"
				+ "\nRank multipliers" + "########################################################################"
				+ "\nYou can add multipliers for players with different ranks/groups. To do this\"\n"
				+ "\nyou must set give the user/group permissions with a format like this:"
				+ "\nmobhunting.multiplier.guest" + "\nmobhunting.multiplier.guardian" + "\nmobhunting.multiplier.staff"
				+ "\nmobhunting.multiplier.hasVoted" + "\nmobhunting.multiplier.donator"
				+ "\nmobhunting.multiplier.op <____ Notice 'op' is reserved for OP'ed players!"
				+ "\nOP'ed players will only get the OP multiplier"
				+ "\nyou can make your own permission nodes. You just need to keep the format"
				+ "\nmobhunting.multiplier.name 'value' in your permissions file and the "
				+ "\nformat below in this file.");

		setCategoryComment("multiplier.difficulty",
				"########################################################################" + "\nPenalty multipliers"
						+ "\n########################################################################"
						+ "\nYou can chance the multiplier for different world difficulties."
						+ "\nA player which play in a HARD world should get more that a player "
						+ "\na player who is player in a peaceful world.The difficulty multipliers"
						+ "\nwith the mobs basic reward."
						+ "\nREMEMBER: These are not money, but a multiplier. Setting to 1 will disable them.");

		setCategoryComment("multiplier.bonus",
				"########################################################################" + "\n Bonus multipliers"
						+ "\n########################################################################"
						+ "\nThese are bonus multipliers that can modify the base prize. "
						+ "\nREMEMBER: These are not in $ but they are a multiplier. "
						+ "\nSetting to 1 will disable them.");

		setCategoryComment("bounties",
				"########################################################################" + "\nBounty settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of the Bounty Command or you can disable"
						+ "\nthe command completely.");

		setCategoryComment("plugins",
				"########################################################################"
						+ "\nIntegration to other plugins."
						+ "\n########################################################################");

		setCategoryComment("plugins.disguises",
				"########################################################################" + "\nDisguises rewards"
						+ "\n########################################################################"
						+ "\nHere is where can define the actions when a player is under disguise (attacker)"
						+ "\n or when the attacked (victim)");

		setCategoryComment("plugins.citizens",
				"########################################################################"
						+ "\nCitizens / MasterMobHunter settings."
						+ "\n########################################################################");

		setCategoryComment("plugins.stackedmobs",
				"########################################################################" + "\nStacked mobs settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of stacked mobs integration, or you can disable"
						+ "\nintegration completely.");

		setCategoryComment("plugins.custommobs",
				"########################################################################" + "\nCustomMob settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of CustomMobs Integration, or you can disable"
						+ "\nintegration completely." + "\nhttps://www.spigotmc.org/resources/custommobs.7339/");

		setCategoryComment("plugins.infernalmobs",
				"########################################################################" + "\nInfernalMobs settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of InfernalMobs Integration, or you can disable"
						+ "\nintegration completely." + "\nhttps://www.spigotmc.org/resources/infernal_mobs.2156/");

		setCategoryComment("plugins.levelmobs",
				"########################################################################"
						+ "\nLevel Mob Settings (Conquestian / LorinthsRPGMobs"
						+ "\n########################################################################");

		setCategoryComment("plugins.levelmobs.conquestiamobs",
				"########################################################################"
						+ "\nConquestia Mobs settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of ConquestiaMobs Integration, or you can disable"
						+ "\nintegration completely." + "\nhttps://www.spigotmc.org/resources/conquesita_mobs.21307/");

		setCategoryComment("plugins.levelmobs.lorinthsrpgmobs",
				"########################################################################" + "\nLorinthsRPGMobs"
						+ "\n########################################################################"
						+ "Disable integration with LorinthsRpgMobs"
						+ "\nhttps://dev.bukkit.org/projects/lorinthsrpgmobs");

		setCategoryComment("plugins.factions",
				"########################################################################"
						+ "\nFactions / FactionsUUID settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of the Factions / FactionsUUID integration, or you can disable"
						+ "\nintegration completely." + "\nhttps://www.spigotmc.org/resources/factions.1900/"
						+ "\nhttps://www.spigotmc.org/resources/factionsuuid.1035/");

		setCategoryComment("plugins.towny",
				"########################################################################" + "\nTowny settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of the Towny integration, or you can disable"
						+ "\nintegration completely." + "\nhttp://towny.palmergames.com/");

		setCategoryComment("plugins.residence",
				"########################################################################" + "\nTowny settings"
						+ "\n########################################################################"
						+ "\nHere you can chance the behavior of the Residence integration, or you can disable"
						+ "\nintegration completely."
						+ "\nhttps://www.spigotmc.org/resources/residence_1_7_10_up_to_1_11.11480/");

		setCategoryComment("plugins.mcmmo",
				"########################################################################" + "\nIntegration to McMMO"
						+ "\n########################################################################"
						+ "\nThis section only relevant if you use McMMO."
						+ "\nHere you configure if the player will get McMMO Levels for MobHunting kills and"
						+ "\nand the chance to get the xp.");

		setCategoryComment("plugins.crackshot",
				"########################################################################"
						+ "\nIntegration to CrackShot"
						+ "\n########################################################################"
						+ "\nThis section only relevant if you use CrackShot."
						+ "\nHere you configure if the player will get a multiplier for using a CrackShot weapon");

		setCategoryComment("plugins.mobarena",
				"########################################################################" + "\nMobArena"
						+ "\n########################################################################");

		setCategoryComment("plugins.pvparena",
				"########################################################################" + "\nPVPArena"
						+ "\n########################################################################"
						+ "\nHere is where can configure how mobhunting acts when killing players while playing PvpArena");

		setCategoryComment("plugins.mythicmobs",
				"########################################################################" + "\nMythicMobs"
						+ "\n########################################################################");

		setCategoryComment("plugins.mypet", "########################################################################"
				+ "\nMyPet" + "\n########################################################################");

		setCategoryComment("plugins.minigames",
				"########################################################################" + "\nMinigames"
						+ "\n########################################################################");

		setCategoryComment("plugins.minigameslib",
				"########################################################################" + "\nMinigamesLib"
						+ "\n########################################################################");

		setCategoryComment("plugins.worldguard",
				"########################################################################" + "\nWorldguard"
						+ "\n########################################################################");

		setCategoryComment("plugins.essentials",
				"########################################################################" + "\nEssentials"
						+ "\n########################################################################");

		setCategoryComment("plugins.battlearena",
				"########################################################################" + "\nBattleArena"
						+ "\n########################################################################");

		setCategoryComment("plugins.bossbarapi",
				"########################################################################" + "\nBossBarAPI"
						+ "\n########################################################################");

		setCategoryComment("plugins.barapi", "########################################################################"
				+ "\nBarApi" + "\n########################################################################");

		setCategoryComment("plugins.titleapi",
				"########################################################################" + "\nTitleApi"
						+ "\n########################################################################");

		setCategoryComment("plugins.vanishnopacket",
				"########################################################################" + "\nVanishNoPackets"
						+ "\n########################################################################");

		setCategoryComment("plugins.titlemanager",
				"########################################################################" + "\nTitlemanager"
						+ "\n########################################################################");

		setCategoryComment("plugins.actionbar",
				"########################################################################" + "\nActionbar"
						+ "\n########################################################################");

		setCategoryComment("plugins.actionbarapi",
				"########################################################################" + "\nActionbarAPI"
						+ "\n########################################################################");

		setCategoryComment("plugins.actionannouncer",
				"########################################################################" + "\nActionAnnouncer"
						+ "\n########################################################################");

		setCategoryComment("plugins.gringotts",
				"########################################################################" + "\nGringotts"
						+ "\n########################################################################");

		setCategoryComment("plugins.tardis_weepingangles",
				"########################################################################" + "\nTARDIS Weeping Angels"
						+ "\n########################################################################");

		setCategoryComment("plugins.protocollib",
				"########################################################################" + "\nProtocolLib"
						+ "\n########################################################################");

		setCategoryComment("plugins.mysterious_halloween",
				"########################################################################" + "\nMysterousHalloween"
						+ "\n########################################################################");

		setCategoryComment("plugins.smartgiants",
				"########################################################################" + "\nSmartGiants"
						+ "\n########################################################################");

		setCategoryComment("plugins.placeholderapi",
				"########################################################################" + "\nPlaceholderApi"
						+ "\n########################################################################");

		setCategoryComment("plugins.bossshop",
				"########################################################################" + "\nBossShop"
						+ "\n########################################################################");

		setCategoryComment("plugins.extra_hard_mode",
				"########################################################################" + "\nExtraHardMode"
						+ "\n########################################################################");

		setCategoryComment("plugins.herobrine",
				"########################################################################" + "\nHerobrine"
						+ "\n########################################################################");

		setCategoryComment("plugins.holograms",
				"########################################################################" + "\nHolograms"
						+ "\n########################################################################");

		setCategoryComment("plugins.holographic_displays",
				"########################################################################" + "\nHolograpic Displays"
						+ "\n########################################################################");

		setCategoryComment("plugins.precious_stones",
				"########################################################################" + "\nPrecious Stones"
						+ "\n########################################################################");

		setCategoryComment("dropmoneyonground",
				"########################################################################"
						+ "\nDropMoneyOnGround Settings"
						+ "\n########################################################################");

		setCategoryComment("database",
				"########################################################################" + "\nDatabase Settings."
						+ "\n########################################################################");

		setCategoryComment("updates", "########################################################################"
				+ "\nUpdate settings" + "\n########################################################################");

		setCategoryComment("general", "########################################################################"
				+ "\nGeneral Settings" + "\n########################################################################");

	}

	// #####################################################################################
	// Mobs
	// #####################################################################################
	@ConfigField(name = "enabled", category = "example.mobname", comment = "Enable MobHunting rewards for this mob (true or false)")
	public boolean defaultEnabled = true;
	@ConfigField(name = "message", category = "example.mobname", comment = "The message you want when this mob is killed")
	public String defaultMessage = "The Mobname dropped {prize} BagOfGold.";
	@ConfigField(name = "amount", category = "example.mobname.money", comment = "The amount of money you want to be dropped / paid")
	public String defaultMoney = "10.0";
	@ConfigField(name = "chance", category = "example.mobname.money", comment = "The chance to drop/pay the amount of money (0-1)")
	public double defaultMoneyChance = 1;
	@ConfigField(name = "commands", category = "example.mobname", comment = "You can use any command you want.")
	public List<HashMap<String, String>> defaultCommands = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "pex user {player} add any.permission {world}");
		values1.put("chance", "0.5");
		values1.put("message", "You got permission to do ..... ");
		defaultCommands.add(values1);

		HashMap<String, String> values2 = new HashMap<String, String>();
		values2.put("cmd", "give {player} Iron_ingot 1");
		values2.put("chance", "0.2");
		values2.put("message", "You got an iron ingot!");
		defaultCommands.add(values2);

		HashMap<String, String> values3 = new HashMap<String, String>();
		values3.put("cmd", "say {player} killed an entity");
		values3.put("chance", "1");
		values3.put("message", "You killed an entity");
		defaultCommands.add(values3);

	}

	@ConfigField(name = "drophead", category = "example.mobname.head", comment = "Set to true or false if you want a head to be dropped as a reward")
	public boolean defaultHeadDropHead = true;
	@ConfigField(name = "value", category = "example.mobname.head", comment = "The value you want the head to have when dropped")
	public String defaultHeadValue = "5";
	@ConfigField(name = "chance", category = "example.mobname.head", comment = "The chance to drop a head (a number between 0 and 1")
	public double defaultHeadChance = 0.5;
	@ConfigField(name = "message", category = "example.mobname.head", comment = "The message you want when a head is dropped")
	public String defaultHeadMessage = "You got a Skull";

	// =====Blaze============================================
	@ConfigField(name = "blaze.enabled", category = "mobs")
	public boolean blazeEnabled = true;
	@ConfigField(name = "blaze.message", category = "mobs")
	public String blazeMessage = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "blaze.money.amount", category = "mobs")
	public String blazeMoney = "10.0";
	@ConfigField(name = "blaze.money.chance", category = "mobs")
	public double blazeMoneyChance = 1;
	@ConfigField(name = "blaze.commands", category = "mobs")
	public List<HashMap<String, String>> blazeCommands = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		blazeCommands.add(values1);
	}
	@ConfigField(name = "blaze.head.drophead", category = "mobs")
	public boolean blazeHeadDropHead = true;
	@ConfigField(name = "blaze.head.value", category = "mobs")
	public String blazeHeadPrize = "0";
	@ConfigField(name = "blaze.head.chance", category = "mobs")
	public double blazeHeadDropChance = 0.10;
	@ConfigField(name = "blaze.head.message", category = "mobs")
	public String blazeHeadMessage = "You got a Blaze skull";

	// =====Cave Spider============================================
	@ConfigField(name = "cave_spider.enabled", category = "mobs")
	public boolean caveSpiderEnabled = true;
	@ConfigField(name = "cave_spider.message", category = "mobs")
	public String caveSpiderCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "cave_spider.money.amount", category = "mobs")
	public String caveSpiderPrize = "10:20";
	@ConfigField(name = "cave_spider.money.chance", category = "mobs")
	public double caveSpiderRunChance = 1;
	@ConfigField(name = "cave_spider.commands", category = "mobs")
	public List<HashMap<String, String>> caveSpiderCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		caveSpiderCmdNew.add(values1);
	}
	@ConfigField(name = "cave_spider.head.drophead", category = "mobs")
	public boolean caveSpiderHeadDropHead = true;
	@ConfigField(name = "cave_spider.head.value", category = "mobs")
	public String caveSpiderHeadPrize = "0";
	@ConfigField(name = "cave_spider.head.chance", category = "mobs")
	public double caveSpiderHeadDropChance = 0.1;
	@ConfigField(name = "cave_spider.head.message", category = "mobs")
	public String caveSpiderHeadMessage = "You got a Cave Spider skull.";

	// =====Creeper============================================
	@ConfigField(name = "creeper.enabled", category = "mobs")
	public boolean creeperEnabled = true;
	@ConfigField(name = "creeper.message", category = "mobs")
	public String creeperCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "creeper.money.amount", category = "mobs")
	public String creeperPrize = "10.0";
	@ConfigField(name = "creeper.money.chance", category = "mobs")
	public double creeperCmdRunChance = 1;
	@ConfigField(name = "creeper.commands", category = "mobs")
	public List<HashMap<String, String>> creeperCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		creeperCmdNew.add(values1);
	}
	@ConfigField(name = "creeper.head.drophead", category = "mobs")
	public boolean creeperHeadDropHead = true;
	@ConfigField(name = "creeper.head.value", category = "mobs")
	public String creeperHeadPrize = "0";
	@ConfigField(name = "creeper.head.chance", category = "mobs")
	public double creeperHeadDropChance = 0.05;
	@ConfigField(name = "creeper.head.message", category = "mobs")
	public String creeperHeadMessage = "You got a Creeper skull";

	// =====Elder Guardian============================================
	@ConfigField(name = "elder_guardian.enabled", category = "mobs")
	public boolean elderGuardianEnabled = true;
	@ConfigField(name = "elder_guardian.message", category = "mobs")
	public String elderGuardianCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "elder_guardian.money.amount", category = "mobs")
	public String elderGuardianPrize = "40:80";
	@ConfigField(name = "elder_guardian.money.chance", category = "mobs")
	public double elderGuardianCmdRunChance = 1;
	@ConfigField(name = "elder_guardian.commands", category = "mobs")
	public List<HashMap<String, String>> elderGuardianCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.33");
		values1.put("message", "");
		elderGuardianCmdNew.add(values1);
	}
	@ConfigField(name = "elder_guardian.head.drophead", category = "mobs")
	public boolean elderGuardianHeadDropHead = true;
	@ConfigField(name = "elder_guardian.head.value", category = "mobs")
	public String elderGuardianHeadPrize = "0";
	@ConfigField(name = "elder_guardian.head.chance", category = "mobs")
	public double elderGuardianHeadDropChance = 0.33;
	@ConfigField(name = "elder_guardian.head.message", category = "mobs")
	public String elderGuardianHeadMessage = "You got a Elder Guardian skull";

	// =====Enderman============================================
	@ConfigField(name = "enderman.enabled", category = "mobs")
	public boolean endermanEnabled = true;
	@ConfigField(name = "enderman.message", category = "mobs")
	public String endermanCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "enderman.money.amount", category = "mobs")
	public String endermanPrize = "20:40";
	@ConfigField(name = "enderman.money.chance", category = "mobs")
	public double endermanCmdRunChance = 1;
	@ConfigField(name = "enderman.commands", category = "mobs")
	public List<HashMap<String, String>> endermanCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.33");
		values1.put("message", "");
		endermanCmdNew.add(values1);
	}
	@ConfigField(name = "enderman.head.drophead", category = "mobs")
	public boolean endermanHeadDropHead = true;
	@ConfigField(name = "enderman.head.value", category = "mobs")
	public String endermanHeadPrize = "0";
	@ConfigField(name = "enderman.head.chance", category = "mobs")
	public double endermanHeadDropChance = 0.33;
	@ConfigField(name = "enderman.head.message", category = "mobs")
	public String endermanHeadMessage = "You got a Enderman skull";

	// =====Endermite============================================
	@ConfigField(name = "endermite.enabled", category = "mobs")
	public boolean endermiteEnabled = true;
	@ConfigField(name = "enderman.message", category = "mobs")
	public String endermiteCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "endermite.money.amount", category = "mobs")
	public String endermitePrize = "10";
	@ConfigField(name = "endermite.money.chance", category = "mobs")
	public double endermiteCmdRunChance = 1;
	@ConfigField(name = "endermite.commands", category = "mobs")
	public List<HashMap<String, String>> endermiteCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		endermiteCmdNew.add(values1);
	}
	@ConfigField(name = "endermite.head.drophead", category = "mobs")
	public boolean endermiteHeadDropHead = true;
	@ConfigField(name = "endermite.head.value", category = "mobs")
	public String endermiteHeadPrize = "1";
	@ConfigField(name = "endermite.head.chance", category = "mobs")
	public double endermiteHeadDropChance = 0.10;
	@ConfigField(name = "endermite.head.message", category = "mobs")
	public String endermiteHeadMessage = "You got a Enderman skull";

	// =====Ghast============================================
	@ConfigField(name = "ghast.enabled", category = "mobs")
	public boolean ghastEnabled = true;
	@ConfigField(name = "ghast.message", category = "mobs")
	public String ghastCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "ghast.money.amount", category = "mobs")
	public String ghastPrize = "40:80";
	@ConfigField(name = "ghast.money.chance", category = "mobs")
	public double ghastCmdRunChance = 1;
	@ConfigField(name = "ghast.commands", category = "mobs")
	public List<HashMap<String, String>> ghastCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		ghastCmdNew.add(values1);
	}
	@ConfigField(name = "ghast.head.drophead", category = "mobs")
	public boolean ghastHeadDropHead = true;
	@ConfigField(name = "ghast.head.value", category = "mobs")
	public String ghastHeadPrize = "0";
	@ConfigField(name = "ghast.head.chance", category = "mobs")
	public double ghastHeadDropChance = 0.10;
	@ConfigField(name = "ghast.head.message", category = "mobs")
	public String ghastHeadMessage = "You got a Ghast skull";

	// =====Giant============================================
	@ConfigField(name = "giant.enabled", category = "mobs")
	public boolean giantEnabled = true;
	@ConfigField(name = "giant.message", category = "mobs")
	public String giantCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "giant.money.amount", category = "mobs")
	public String giantPrize = "5.0";
	@ConfigField(name = "giant.money.chance", category = "mobs")
	public double giantCmdRunChance = 1;
	@ConfigField(name = "giant.commands", category = "mobs")
	public List<HashMap<String, String>> giantCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		giantCmdNew.add(values1);
	}
	@ConfigField(name = "giant.head.drophead", category = "mobs")
	public boolean giantHeadDropHead = true;
	@ConfigField(name = "giant.head.value", category = "mobs")
	public String giantHeadPrize = "0";
	@ConfigField(name = "giant.head.chance", category = "mobs")
	public double giantHeadDropChance = 0.10;
	@ConfigField(name = "giant.head.message", category = "mobs")
	public String giantHeadMessage = "You got a Giant skull";

	// =====Iron Golem============================================
	@ConfigField(name = "iron_golem.enabled", category = "mobs")
	public boolean ironGolemEnabled = true;
	@ConfigField(name = "iron_golem.message", category = "mobs")
	public String ironGolemCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "iron_golem.money.amount", category = "mobs")
	public String ironGolemPrize = "20:40";
	@ConfigField(name = "iron_golem.money.chance", category = "mobs")
	public double ironGolemCmdRunChance = 1;
	@ConfigField(name = "iron_golem.commands", category = "mobs")
	public List<HashMap<String, String>> ironGolemCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		ironGolemCmdNew.add(values1);
	}
	@ConfigField(name = "iron_golem.head.drophead", category = "mobs")
	public boolean ironGolemHeadDropHead = true;
	@ConfigField(name = "iron_golem.head.value", category = "mobs")
	public String ironGolemHeadPrize = "0";
	@ConfigField(name = "iron_golem.head.chance", category = "mobs")
	public double ironGolemHeadDropChance = 0.10;
	@ConfigField(name = "iron_golem.head.message", category = "mobs")
	public String ironGolemHeadMessage = "You got a Iron Golem skull";

	// =====Guardian============================================
	@ConfigField(name = "guardian.enabled", category = "mobs")
	public boolean guardianEnabled = true;
	@ConfigField(name = "guardian.message", category = "mobs")
	public String guardianCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "guardian.money.amount", category = "mobs")
	public String guardianPrize = "20:40";
	@ConfigField(name = "guardian.money.chance", category = "mobs")
	public double guardianCmdRunChance = 1;
	@ConfigField(name = "guardian.commands", category = "mobs")
	public List<HashMap<String, String>> guardianCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		guardianCmdNew.add(values1);
	}
	@ConfigField(name = "guardian.head.drophead", category = "mobs")
	public boolean guardianHeadDropHead = true;
	@ConfigField(name = "guardian.head.value", category = "mobs")
	public String guardianHeadPrize = "0";
	@ConfigField(name = "guardian.head.chance", category = "mobs")
	public double guardianHeadDropChance = 0.10;
	@ConfigField(name = "guardian.head.message", category = "mobs")
	public String guardianHeadMessage = "You got a Guardian skull";

	// =====Husk============================================
	@ConfigField(name = "husk.enabled", category = "mobs")
	public boolean huskEnabled = true;
	@ConfigField(name = "husk.message", category = "mobs")
	public String huskCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "husk.money.amount", category = "mobs")
	public String huskPrize = "9:13";
	@ConfigField(name = "husk.money.chance", category = "mobs")
	public double huskCmdRunChance = 1;
	@ConfigField(name = "husk.commands", category = "mobs")
	public List<HashMap<String, String>> huskCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.20");
		values1.put("message", "");
		huskCmdNew.add(values1);
	}
	@ConfigField(name = "husk.head.drophead", category = "mobs")
	public boolean huskHeadDropHead = true;
	@ConfigField(name = "husk.head.value", category = "mobs")
	public String huskHeadPrize = "0";
	@ConfigField(name = "husk.head.chance", category = "mobs")
	public double huskHeadDropChance = 0.20;
	@ConfigField(name = "husk.head.message", category = "mobs")
	public String huskHeadMessage = "You got a Husk skull";

	// =====Killer Rabbit============================================
	@ConfigField(name = "killer_rabbit.enabled", category = "mobs")
	public boolean killerRabbitEnabled = true;
	@ConfigField(name = "killer_rabbit.message", category = "mobs")
	public String killerRabbitCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "killer_rabbit.money.amount", category = "mobs")
	public String killerRabbitPrize = "200";
	@ConfigField(name = "killer_rabbit.money.chance", category = "mobs")
	public double killerRabbitCmdRunChance = 1;
	@ConfigField(name = "killer_rabbit.commands", category = "mobs")
	public List<HashMap<String, String>> killerRabbitCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.25");
		values1.put("message", "");
		killerRabbitCmdNew.add(values1);
	}
	@ConfigField(name = "killer_rabbit.head.drophead", category = "mobs")
	public boolean killerRabbitHeadDropHead = true;
	@ConfigField(name = "killer_rabbit.head.value", category = "mobs")
	public String killerRabbitHeadPrize = "10";
	@ConfigField(name = "killer_rabbit.head.chance", category = "mobs")
	public double killerRabbitHeadDropChance = 0.25;
	@ConfigField(name = "killer_rabbit.head.message", category = "mobs")
	public String killerRabbitHeadMessage = "You got a Killer Rabbit skull";

	// =====Magma Cube============================================
	@ConfigField(name = "magma_cube.enabled", category = "mobs")
	public boolean magmaCubeEnabled = true;
	@ConfigField(name = "magma_cube.message", category = "mobs")
	public String magmaCubeCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "magma_cube.money.amount", category = "mobs")
	public String magmaCubePrize = "10:20";
	@ConfigField(name = "magma_cube.money.chance", category = "mobs")
	public double magmaCubeCmdRunChance = 1;
	@ConfigField(name = "magma_cube.commands", category = "mobs")
	public List<HashMap<String, String>> magmaCubeCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		magmaCubeCmdNew.add(values1);
	}
	@ConfigField(name = "magma_cube.head.drophead", category = "mobs")
	public boolean magmaCubeHeadDropHead = true;
	@ConfigField(name = "magma_cube.head.value", category = "mobs")
	public String magmaCubeHeadPrize = "0";
	@ConfigField(name = "magma_cube.head.chance", category = "mobs")
	public double magmaCubeHeadDropChance = 0.10;
	@ConfigField(name = "magma_cube.head.message", category = "mobs")
	public String magmaCubeHeadMessage = "You got a Magma Cube skull";

	// =====Polar Bear============================================
	@ConfigField(name = "polar_bear.enabled", category = "mobs")
	public boolean polarBearEnabled = true;
	@ConfigField(name = "polar_bear.message", category = "mobs")
	public String polarBearCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "polar_bear.money.amount", category = "mobs")
	public String polarBearPrize = "25";
	@ConfigField(name = "polar_bear.money.chance", category = "mobs")
	public double polarBearCmdRunChance = 1;
	@ConfigField(name = "polar_bear.commands", category = "mobs")
	public List<HashMap<String, String>> polarBearCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.25");
		values1.put("message", "");
		polarBearCmdNew.add(values1);
	}
	@ConfigField(name = "polar_bear.head.drophead", category = "mobs")
	public boolean polarBearHeadDropHead = true;
	@ConfigField(name = "polar_bear.head.value", category = "mobs")
	public String polarBearHeadPrize = "0";
	@ConfigField(name = "polar_bear.head.chance", category = "mobs")
	public double polarBearHeadDropChance = 0.25;
	@ConfigField(name = "polar_bear.head.message", category = "mobs")
	public String polarBearHeadMessage = "You got a Polar Bear skull";

	// =====Slime============================================
	@ConfigField(name = "slime.enabled", category = "mobs")
	public boolean slimeEnabled = true;
	@ConfigField(name = "slime.message", category = "mobs")
	public String slimeCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "slime.money.amount", category = "mobs")
	public String slimePrize = "25";
	@ConfigField(name = "slime.money.chance", category = "mobs")
	public double slimeCmdRunChance = 1;
	@ConfigField(name = "slime.commands", category = "mobs")
	public List<HashMap<String, String>> slimeCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		slimeCmdNew.add(values1);
	}
	@ConfigField(name = "slime.head.drophead", category = "mobs")
	public boolean slimeHeadDropHead = true;
	@ConfigField(name = "slime.head.value", category = "mobs")
	public String slimeHeadPrize = "0";
	@ConfigField(name = "slime.head.chance", category = "mobs")
	public double slimeHeadDropChance = 0.05;
	@ConfigField(name = "slime.head.message", category = "mobs")
	public String slimeHeadMessage = "You got a Slime skull";

	// =====Shulker============================================
	@ConfigField(name = "shulker.enabled", category = "mobs")
	public boolean shulkerEnabled = true;
	@ConfigField(name = "shulker.message", category = "mobs")
	public String shulkerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "shulker.money.amount", category = "mobs")
	public String shulkerPrize = "25";
	@ConfigField(name = "shulker.money.chance", category = "mobs")
	public double shulkerCmdRunChance = 1;
	@ConfigField(name = "shulker.commands", category = "mobs")
	public List<HashMap<String, String>> shulkerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.50");
		values1.put("message", "");
		shulkerCmdNew.add(values1);
	}
	@ConfigField(name = "shulker.head.drophead", category = "mobs")
	public boolean shulkerHeadDropHead = true;
	@ConfigField(name = "shulker.head.value", category = "mobs")
	public String shulkerHeadPrize = "0";
	@ConfigField(name = "shulker.head.chance", category = "mobs")
	public double shulkerHeadDropChance = 0.50;
	@ConfigField(name = "shulker.head.message", category = "mobs")
	public String shulkerHeadMessage = "You got a Shulker skull";

	// =====Silverfish============================================
	@ConfigField(name = "silverfish.enabled", category = "mobs")
	public boolean silverfishEnabled = true;
	@ConfigField(name = "silverfish.message", category = "mobs")
	public String silverfishCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "silverfish.money.amount", category = "mobs")
	public String silverfishPrize = "10";
	@ConfigField(name = "silverfish.money.chance", category = "mobs")
	public double silverfishCmdRunChance = 1;
	@ConfigField(name = "silverfish.commands", category = "mobs")
	public List<HashMap<String, String>> silverfishCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.20");
		values1.put("message", "");
		silverfishCmdNew.add(values1);
	}
	@ConfigField(name = "silverfish.head.drophead", category = "mobs")
	public boolean silverfishHeadDropHead = true;
	@ConfigField(name = "silverfish.head.value", category = "mobs")
	public String silverfishHeadPrize = "0";
	@ConfigField(name = "silverfish.head.chance", category = "mobs")
	public double silverfishHeadDropChance = 0.20;
	@ConfigField(name = "silverfish.head.message", category = "mobs")
	public String silverfishHeadMessage = "You got a Silverfish skull";

	// =====Skeleton============================================
	@ConfigField(name = "skeleton.enabled", category = "mobs")
	public boolean skeletonEnabled = true;
	@ConfigField(name = "skeleton.message", category = "mobs")
	public String skeletonCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "skeleton.money.amount", category = "mobs")
	public String skeletonPrize = "10:30";
	@ConfigField(name = "skeleton.money.chance", category = "mobs")
	public double skeletonCmdRunChance = 1;
	@ConfigField(name = "skeleton.commands", category = "mobs")
	public List<HashMap<String, String>> skeletonCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		skeletonCmdNew.add(values1);
	}
	@ConfigField(name = "skeleton.head.drophead", category = "mobs")
	public boolean skeletonHeadDropHead = true;
	@ConfigField(name = "skeleton.head.value", category = "mobs")
	public String skeletonHeadPrize = "0";
	@ConfigField(name = "skeleton.head.chance", category = "mobs")
	public double skeletonHeadDropChance = 0.05;
	@ConfigField(name = "skeleton.head.message", category = "mobs")
	public String skeletonHeadMessage = "You got a Skeleton skull";

	// =====Spider============================================
	@ConfigField(name = "spider.enabled", category = "mobs")
	public boolean spiderEnabled = true;
	@ConfigField(name = "spider.message", category = "mobs")
	public String spiderMessage = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "spider.money.amount", category = "mobs")
	public String spiderMoney = "5.5:10.5";
	@ConfigField(name = "spider.money.chance", category = "mobs")
	public double spiderMoneyChance = 1;
	@ConfigField(name = "spider.commands", category = "mobs")
	public List<HashMap<String, String>> spiderCommands = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05"); 
		values1.put("message", "");
		spiderCommands.add(values1);
	}
	@ConfigField(name = "spider.head.drophead", category = "mobs")
	public boolean spiderHeadDropHead = true;
	@ConfigField(name = "spider.head.value", category = "mobs")
	public String spiderHeadPrize = "0";
	@ConfigField(name = "spider.head.chance", category = "mobs")
	public double spiderHeadDropChance = 0.05;
	@ConfigField(name = "spider.head.message", category = "mobs")
	public String spiderHeadMessage = "You got a Spider skull";

	// =====Stray============================================
	@ConfigField(name = "stray.enabled", category = "mobs")
	public boolean strayEnabled = true;
	@ConfigField(name = "stray.message", category = "mobs")
	public String strayCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "stray.money.amount", category = "mobs")
	public String strayPrize = "15:35";
	@ConfigField(name = "stray.money.chance", category = "mobs")
	public double strayCmdRunChance = 1;
	@ConfigField(name = "stray.commands", category = "mobs")
	public List<HashMap<String, String>> strayCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.20");
		values1.put("message", "");
		strayCmdNew.add(values1);
	}
	@ConfigField(name = "stray.head.drophead", category = "mobs")
	public boolean strayHeadDropHead = true;
	@ConfigField(name = "stray.head.value", category = "mobs")
	public String strayHeadPrize = "0";
	@ConfigField(name = "stray.head.chance", category = "mobs")
	public double strayHeadDropChance = 0.20;
	@ConfigField(name = "stray.head.message", category = "mobs")
	public String strayHeadMessage = "You got a Stray skull";

	// =====Zombie============================================
	@ConfigField(name = "zombie.enabled", category = "mobs")
	public boolean zombieEnabled = true;
	@ConfigField(name = "zombie.message", category = "mobs")
	public String zombieCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "zombie.money.amount", category = "mobs")
	public String zombiePrize = "7:11";
	@ConfigField(name = "zombie.money.chance", category = "mobs")
	public double zombieCmdRunChance = 1;
	@ConfigField(name = "zombie.commands", category = "mobs")
	public List<HashMap<String, String>> zombieCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.005");
		values1.put("message", "");
		zombieCmdNew.add(values1);
	}
	@ConfigField(name = "zombie.head.drophead", category = "mobs")
	public boolean zombieHeadDropHead = true;
	@ConfigField(name = "zombie.head.value", category = "mobs")
	public String zombieHeadPrize = "0";
	@ConfigField(name = "zombie.head.chance", category = "mobs")
	public double zombieHeadDropChance = 0.005;
	@ConfigField(name = "zombie.head.message", category = "mobs")
	public String zombieHeadMessage = "You got a Zombie skull";

	// =====Zombie Pigman============================================
	@ConfigField(name = "zombie_pigman.enabled", category = "mobs")
	public boolean zombiePigmanEnabled = true;
	@ConfigField(name = "zombie_pigman.message", category = "mobs")
	public String zombiePigmanCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "zombie_pigman.money.amount", category = "mobs")
	public String zombiePigmanPrize = "4:8";
	@ConfigField(name = "zombie_pigman.money.chance", category = "mobs")
	public double zombiePigmanCmdRunChance = 1;
	@ConfigField(name = "zombie_pigman.commands", category = "mobs")
	public List<HashMap<String, String>> zombiePigmanCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		zombiePigmanCmdNew.add(values1);
	}
	@ConfigField(name = "zombie_pigman.head.drophead", category = "mobs")
	public boolean zombiePigmanHeadDropHead = true;
	@ConfigField(name = "zombie_pigman.head.value", category = "mobs")
	public String zombiePigmanHeadPrize = "0";
	@ConfigField(name = "zombie_pigman.head.chance", category = "mobs")
	public double zombiePigmanHeadDropChance = 0.10;
	@ConfigField(name = "zombie_pigman.head.message", category = "mobs")
	public String zombiePigmanHeadMessage = "You got a Zombie Pigman skull";

	// =====Vex============================================
	@ConfigField(name = "vex.enabled", category = "mobs")
	public boolean vexEnabled = true;
	@ConfigField(name = "vex.message", category = "mobs")
	public String vexCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "vex.money.amount", category = "mobs")
	public String vexPrize = "10:15";
	@ConfigField(name = "vex.money.chance", category = "mobs")
	public double vexCmdRunChance = 1;
	@ConfigField(name = "vex.commands", category = "mobs")
	public List<HashMap<String, String>> vexCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.50");
		values1.put("message", "");
		vexCmdNew.add(values1);
	}
	@ConfigField(name = "vex.head.drophead", category = "mobs")
	public boolean vexHeadDropHead = true;
	@ConfigField(name = "vex.head.value", category = "mobs")
	public String vexHeadPrize = "0";
	@ConfigField(name = "vex.head.chance", category = "mobs")
	public double vexHeadDropChance = 0.50;
	@ConfigField(name = "vex.head.message", category = "mobs")
	public String vexHeadMessage = "You got a Vex skull";

	// =====Witch============================================
	@ConfigField(name = "witch.enabled", category = "mobs")
	public boolean witchEnabled = true;
	@ConfigField(name = "witch.message", category = "mobs")
	public String witchCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "witch.money.amount", category = "mobs")
	public String witchPrize = "10:15";
	@ConfigField(name = "witch.money.chance", category = "mobs")
	public double witchCmdRunChance = 1;
	@ConfigField(name = "witch.commands", category = "mobs")
	public List<HashMap<String, String>> witchCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		witchCmdNew.add(values1);
	}
	@ConfigField(name = "witch.head.drophead", category = "mobs")
	public boolean witchHeadDropHead = true;
	@ConfigField(name = "witch.head.value", category = "mobs")
	public String witchHeadPrize = "0";
	@ConfigField(name = "witch.head.chance", category = "mobs")
	public double witchHeadDropChance = 0.05;
	@ConfigField(name = "witch.head.message", category = "mobs")
	public String witchHeadMessage = "You got a Witch skull";

	// =====Wither Skeleton============================================
	@ConfigField(name = "wither_skeleton.enabled", category = "mobs")
	public boolean witherSkeletonEnabled = true;
	@ConfigField(name = "wither_skeleton.message", category = "mobs")
	public String witherSkeletonCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "wither_skeleton.money.amount", category = "mobs")
	public String witherSkeletonPrize = "30:50";
	@ConfigField(name = "wither_skeleton.money.chance", category = "mobs")
	public double witherSkeletonCmdRunChance = 1;
	@ConfigField(name = "wither_skeleton.commands", category = "mobs")
	public List<HashMap<String, String>> witherSkeletonCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		witherSkeletonCmdNew.add(values1);
	}
	@ConfigField(name = "wither_skeleton.head.drophead", category = "mobs")
	public boolean witherSkeletonHeadDropHead = true;
	@ConfigField(name = "wither_skeleton.head.value", category = "mobs")
	public String witherSkeletonHeadPrize = "0";
	@ConfigField(name = "wither_skeleton.head.chance", category = "mobs")
	public double witherSkeletonHeadDropChance = 0.10;
	@ConfigField(name = "wither_skeleton.head.message", category = "mobs")
	public String witherSkeletonHeadMessage = "You got a Wither Skeleton skull";

	// #####################################################################################
	// Bosses
	// #####################################################################################
	// =====Wither============================================
	@ConfigField(name = "wither.enabled", category = "boss")
	public boolean witherEnabled = true;
	@ConfigField(name = "wither.message", category = "boss")
	public String witherCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "wither.money.amount", category = "boss")
	public String witherPrize = "1000:2000";
	@ConfigField(name = "wither.money.chance", category = "boss")
	public double witherCmdRunChance = 1;
	@ConfigField(name = "wither.commands", category = "boss")
	public List<HashMap<String, String>> witherCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} gold_ingot 1");
		values1.put("chance", "0.50");
		values1.put("message", "");
		witherCmdNew.add(values1);

		HashMap<String, String> values2 = new HashMap<String, String>();
		values2.put("cmd", "give {player} diamond 10");
		values2.put("chance", "0.50");
		values2.put("message", "");
		witherCmdNew.add(values2);
	}
	@ConfigField(name = "wither.head.drophead", category = "boss")
	public boolean witherHeadDropHead = true;
	@ConfigField(name = "wither.head.value", category = "boss")
	public String witherHeadPrize = "500";
	@ConfigField(name = "wither.head.chance", category = "boss")
	public double witherHeadDropChance = 0.50;
	@ConfigField(name = "wither.head.message", category = "boss")
	public String witherHeadMessage = "You got a Wither skull";

	// =====Ender Dragon============================================
	@ConfigField(name = "ender_dragon.enabled", category = "boss")
	public boolean enderDragonEnabled = true;
	@ConfigField(name = "ender_dragon.message", category = "boss")
	public String enderDragonCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "ender_dragon.money.amount", category = "boss")
	public String enderDragonPrize = "2000.0:5000.0";
	@ConfigField(name = "ender_dragon.money.chance", category = "boss")
	public double enderDragonCmdRunChance = 0.10;
	@ConfigField(name = "ender_dragon.commands", category = "boss")
	public List<HashMap<String, String>> enderDragonCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.5");
		values1.put("message", "You got an Iron Ingot.");
		enderDragonCmdNew.add(values1);
		HashMap<String, String> values2 = new HashMap<String, String>();
		values2.put("cmd", "give {player} diamond 10");
		values2.put("chance", "0.5");
		values2.put("message", "You got ten Diamonds.");
		enderDragonCmdNew.add(values2);
	}
	@ConfigField(name = "ender_dragon.head.drophead", category = "boss")
	public boolean enderDragonHeadDropHead = true;
	@ConfigField(name = "ender_dragon.head.value", category = "boss")
	public String enderDragonHeadPrize = "1000";
	@ConfigField(name = "ender_dragon.head.chance", category = "boss")
	public double enderDragonHeadDropChance = 0.5;
	@ConfigField(name = "ender_dragon.head.message", category = "boss")
	public String enderDragonHeadMessage = "You got a Ender Dragon skull";

	// Usage: /summon <EntityName> [x] [y] [z] [dataTag]
	// Try this!!!! /summon Minecart ~ ~ ~20 {Riding:{id:EnderDragon}}
	// Then enter to the minecart
	// WITH THAT YOU CAN RIDE AN ENDERDRAGON!!!

	// /summon Minecart ~ ~ ~ {Riding:{Creeper,Riding:{id:Ozelot}}}
	// ...Yes..Ocelot need to be spelled Ozelot..

	// /summon Skeleton ~ ~ ~
	// {Riding:{id:Spider},Equipment:[{id:57},{id:310},{id:310},{id:310},{id:310}]}

	// #####################################################################################
	// Villagers
	// #####################################################################################
	// =====Blacksmith============================================
	@ConfigField(name = "blacksmith.enabled", category = "villager")
	public boolean blacksmithEnabled = true;
	@ConfigField(name = "blacksmith.message", category = "villager")
	public String blacksmithCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "blacksmith.money.amount", category = "villager")
	public String blacksmithPrize = "1:2";
	@ConfigField(name = "blacksmith.money.chance", category = "villager")
	public double blacksmithCmdRunChance = 1;
	@ConfigField(name = "blacksmith.commands", category = "villager")
	public List<HashMap<String, String>> blacksmithCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		blacksmithCmdNew.add(values1);
	}
	@ConfigField(name = "blacksmith.head.drophead", category = "villager")
	public boolean blacksmithHeadDropHead = true;
	@ConfigField(name = "blacksmith.head.value", category = "villager")
	public String blacksmithHeadPrize = "0";
	@ConfigField(name = "blacksmith.head.chance", category = "villager")
	public double blacksmithHeadDropChance = 0.10;
	@ConfigField(name = "blacksmith.head.message", category = "villager")
	public String blacksmithHeadMessage = "You got a Blacksmith skull";

	// =====Butcher============================================
	@ConfigField(name = "butcher.enabled", category = "villager")
	public boolean butcherEnabled = true;
	@ConfigField(name = "butcher.message", category = "villager")
	public String butcherCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "butcher.money.amount", category = "villager")
	public String butcherPrize = "1:2";
	@ConfigField(name = "butcher.money.chance", category = "villager")
	public double butcherCmdRunChance = 1;
	@ConfigField(name = "butcher.commands", category = "villager")
	public List<HashMap<String, String>> butcherCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		butcherCmdNew.add(values1);
	}
	@ConfigField(name = "butcher.head.drophead", category = "villager")
	public boolean butcherHeadDropHead = true;
	@ConfigField(name = "butcher.head.value", category = "villager")
	public String butcherHeadPrize = "0";
	@ConfigField(name = "butcher.head.chance", category = "villager")
	public double butcherHeadDropChance = 0.10;
	@ConfigField(name = "butcher.head.message", category = "villager")
	public String butcherHeadMessage = "You got a Butcher skull";

	// =====Evoker============================================
	@ConfigField(name = "evoker.enabled", category = "villager")
	public boolean evokerEnabled = true;
	@ConfigField(name = "evoker.message", category = "villager")
	public String evokerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "evoker.money.amount", category = "villager")
	public String evokerPrize = "10";
	@ConfigField(name = "evoker.money.chance", category = "villager")
	public double evokerCmdRunChance = 0.50;
	@ConfigField(name = "evoker.commands", category = "villager")
	public List<HashMap<String, String>> evokerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		evokerCmdNew.add(values1);
	}
	@ConfigField(name = "evoker.head.drophead", category = "villager")
	public boolean evokerHeadDropHead = true;
	@ConfigField(name = "evoker.head.value", category = "villager")
	public String evokerHeadPrize = "0";
	@ConfigField(name = "evoker.head.chance", category = "villager")
	public double evokerHeadDropChance = 0.50;
	@ConfigField(name = "evoker.head.message", category = "villager")
	public String evokerHeadMessage = "You got a Evoker skull";

	// =====Farmer============================================
	@ConfigField(name = "farmer.enabled", category = "villager")
	public boolean farmerEnabled = true;
	@ConfigField(name = "farmer.message", category = "villager")
	public String farmerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "farmer.money.amount", category = "villager")
	public String farmerPrize = "1:2";
	@ConfigField(name = "farmer.money.chance", category = "villager")
	public double farmerCmdRunChance = 1;
	@ConfigField(name = "farmer.commands", category = "villager")
	public List<HashMap<String, String>> farmerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		farmerCmdNew.add(values1);
	}
	@ConfigField(name = "farmer.head.drophead", category = "villager")
	public boolean farmerHeadDropHead = true;
	@ConfigField(name = "farmer.head.value", category = "villager")
	public String farmerHeadPrize = "0";
	@ConfigField(name = "farmer.head.chance", category = "villager")
	public double farmerHeadDropChance = 0.10;
	@ConfigField(name = "farmer.head.message", category = "villager")
	public String farmerHeadMessage = "You got a Farmer skull";

	// =====Illusioner============================================
	@ConfigField(name = "illusioner.enabled", category = "villager")
	public boolean illusionerEnabled = true;
	@ConfigField(name = "illusioner.message", category = "villager")
	public String illusionerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "illusioner.money.amount", category = "villager")
	public String illusionerPrize = "30:50";
	@ConfigField(name = "illusioner.money.chance", category = "villager")
	public double illusionerCmdRunChance = 0.10;
	@ConfigField(name = "illusioner.commands", category = "villager")
	public List<HashMap<String, String>> illusionerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.10");
		values1.put("message", "");
		illusionerCmdNew.add(values1);
	}
	@ConfigField(name = "illusioner.head.drophead", category = "villager")
	public boolean illusionerHeadDropHead = true;
	@ConfigField(name = "illusioner.head.value", category = "villager")
	public String illusionerHeadPrize = "0";
	@ConfigField(name = "illusioner.head.chance", category = "villager")
	public double illusionerHeadDropChance = 0.10;
	@ConfigField(name = "illusioner.head.message", category = "villager")
	public String illusionerHeadMessage = "You got a Illusioner skull";

	// =====Librarian============================================
	@ConfigField(name = "librarian.enabled", category = "villager")
	public boolean librarianEnabled = true;
	@ConfigField(name = "librarian.message", category = "villager")
	public String librarianCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "librarian.money.amount", category = "villager")
	public String librarianPrize = "1:2";
	@ConfigField(name = "librarian.money.chance", category = "villager")
	public double librarianCmdRunChance = 1;
	@ConfigField(name = "librarian.commands", category = "villager")
	public List<HashMap<String, String>> librarianCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.5");
		values1.put("message", "");
		librarianCmdNew.add(values1);
	}
	@ConfigField(name = "librarian.head.drophead", category = "villager")
	public boolean librarianHeadDropHead = true;
	@ConfigField(name = "librarian.head.value", category = "villager")
	public String librarianHeadPrize = "0";
	@ConfigField(name = "librarian.head.chance", category = "villager")
	public double librarianHeadDropChance = 0.50;
	@ConfigField(name = "librarian.head.message", category = "villager")
	public String librarianHeadMessage = "You got a Librarian skull";

	// =====Nitwit============================================
	@ConfigField(name = "nitwit.enabled", category = "villager")
	public boolean nitwitEnabled = true;
	@ConfigField(name = "nitwit.message", category = "villager")
	public String nitwitCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "nitwit.money.amount", category = "villager")
	public String nitwitPrize = "1:2";
	@ConfigField(name = "nitwit.money.chance", category = "villager")
	public double nitwitCmdRunChance = 1;
	@ConfigField(name = "nitwit.commands", category = "villager")
	public List<HashMap<String, String>> nitwitCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.5");
		values1.put("message", "");
		nitwitCmdNew.add(values1);
	}
	@ConfigField(name = "nitwit.head.drophead", category = "villager")
	public boolean nitwitHeadDropHead = true;
	@ConfigField(name = "nitwit.head.value", category = "villager")
	public String nitwitHeadPrize = "0";
	@ConfigField(name = "nitwit.head.chance", category = "villager")
	public double nitwitHeadDropChance = 0.5;
	@ConfigField(name = "nitwit.head.message", category = "villager")
	public String nitwitHeadMessage = "You got a Nitwit skull";

	// =====Priest============================================
	@ConfigField(name = "priest.enabled", category = "villager")
	public boolean priestEnabled = true;
	@ConfigField(name = "priest.message", category = "villager")
	public String priestCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "priest.money.amount", category = "villager")
	public String priestPrize = "1:2";
	@ConfigField(name = "priest.money.chance", category = "villager")
	public double priestCmdRunChance = 1;
	@ConfigField(name = "priest.commands", category = "villager")
	public List<HashMap<String, String>> priestCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.5");
		values1.put("message", "");
		priestCmdNew.add(values1);
	}
	@ConfigField(name = "priest.head.drophead", category = "villager")
	public boolean priestHeadDropHead = true;
	@ConfigField(name = "priest.head.value", category = "villager")
	public String priestHeadPrize = "0";
	@ConfigField(name = "priest.head.chance", category = "villager")
	public double priestHeadDropChance = 0.50;
	@ConfigField(name = "priest.head.message", category = "villager")
	public String priestHeadMessage = "You got a Priest skull";

	// =====Villager============================================
	@ConfigField(name = "villager.enabled", category = "villager")
	public boolean villagerEnabled = true;
	@ConfigField(name = "villager.message", category = "villager")
	public String villagerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "villager.money.amount", category = "villager")
	public String villagerPrize = "1";
	@ConfigField(name = "villager.money.chance", category = "villager")
	public double villagerCmdRunChance = 1;
	@ConfigField(name = "villager.commands", category = "villager")
	public List<HashMap<String, String>> villagerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.3");
		values1.put("message", "");
		villagerCmdNew.add(values1);
	}
	@ConfigField(name = "villager.head.drophead", category = "villager")
	public boolean villagerHeadDropHead = true;
	@ConfigField(name = "villager.head.value", category = "villager")
	public String villagerHeadPrize = "0";
	@ConfigField(name = "villager.head.chance", category = "villager")
	public double villagerHeadDropChance = 0.30;
	@ConfigField(name = "villager.head.message", category = "villager")
	public String villagerHeadMessage = "You got a Villager skull";

	// =====Vindicator============================================
	@ConfigField(name = "vindicator.enabled", category = "villager")
	public boolean vindicatorEnabled = true;
	@ConfigField(name = "vindicator.message", category = "villager")
	public String vindicatorCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "vindicator.money.amount", category = "villager")
	public String vindicatorPrize = "10:15";
	@ConfigField(name = "vindicator.money.chance", category = "villager")
	public double vindicatorCmdRunChance = 1;
	@ConfigField(name = "vindicator.commands", category = "villager")
	public List<HashMap<String, String>> vindicatorCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		vindicatorCmdNew.add(values1);
	}
	@ConfigField(name = "vindicator.head.drophead", category = "villager")
	public boolean vindicatorHeadDropHead = true;
	@ConfigField(name = "vindicator.head.value", category = "villager")
	public String vindicatorHeadPrize = "0";
	@ConfigField(name = "vindicator.head.chance", category = "villager")
	public double vindicatorHeadDropChance = 0.05;
	@ConfigField(name = "vindicator.head.message", category = "villager")
	public String vindicatorHeadMessage = "You got a Vindicator skull";

	// =====Zombie Villager============================================
	@ConfigField(name = "zombie_villager.enabled", category = "villager")
	public boolean zombieVillagerEnabled = true;
	@ConfigField(name = "zombie_villager.message", category = "villager")
	public String zombieVillagerCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "zombie_villager.money.amount", category = "villager")
	public String zombieVillagerPrize = "1:2";
	@ConfigField(name = "zombie_villager.money.chance", category = "villager")
	public double zombieVillagerCmdRunChance = 1;
	@ConfigField(name = "zombie_villager.commands", category = "villager")
	public List<HashMap<String, String>> zombieVillagerCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		zombieVillagerCmdNew.add(values1);
	}
	@ConfigField(name = "zombie_villager.head.drophead", category = "villager")
	public boolean zombieVillagerHeadDropHead = true;
	@ConfigField(name = "zombie_villager.head.value", category = "villager")
	public String zombieVillagerHeadPrize = "0";
	@ConfigField(name = "zombie_villager.head.chance", category = "villager")
	public double zombieVillagerHeadDropChance = 0.05;
	@ConfigField(name = "zombie_villager.head.message", category = "villager")
	public String zombieVillagerHeadMessage = "You got a Zombie Villager skull";

	// #####################################################################################
	// Passive Mobs
	// #####################################################################################
	// =====Bat============================================
	@ConfigField(name = "bat.enabled", category = "passive")
	public boolean batEnabled = true;
	@ConfigField(name = "bat.message", category = "passive")
	public String batCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "bat.money.amount", category = "passive")
	public String batPrize = "0";
	@ConfigField(name = "bat.money.chance", category = "passive")
	public double batCmdRunChance = 0.05;
	@ConfigField(name = "bat.commands", category = "passive")
	public List<HashMap<String, String>> batCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		batCmdNew.add(values1);
	}
	@ConfigField(name = "bat.head.drophead", category = "passive")
	public boolean batHeadDropHead = true;
	@ConfigField(name = "bat.head.value", category = "passive")
	public String batHeadPrize = "0";
	@ConfigField(name = "bat.head.chance", category = "passive")
	public double batHeadDropChance = 0.05;
	@ConfigField(name = "bat.head.message", category = "passive")
	public String batHeadMessage = "You got a Bat skull";

	// =====Chicken============================================
	@ConfigField(name = "chicken.enabled", category = "passive")
	public boolean chickenEnabled = true;
	@ConfigField(name = "chicken.message", category = "passive")
	public String chickenCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "chicken.money.amount", category = "passive")
	public String chickenPrize = "0";
	@ConfigField(name = "chicken.money.chance", category = "passive")
	public double chickenCmdRunChance = 1;
	@ConfigField(name = "chicken.commands", category = "passive")
	public List<HashMap<String, String>> chickenCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		chickenCmdNew.add(values1);
	}
	@ConfigField(name = "chicken.head.drophead", category = "passive")
	public boolean chickenHeadDropHead = true;
	@ConfigField(name = "chicken.head.value", category = "passive")
	public String chickenHeadPrize = "0";
	@ConfigField(name = "chicken.head.chance", category = "passive")
	public double chickenHeadDropChance = 0.05;
	@ConfigField(name = "chicken.head.message", category = "passive")
	public String chickenHeadMessage = "You got a Chicken skull";

	// =====Cow============================================
	@ConfigField(name = "cow.enabled", category = "passive")
	public boolean cowEnabled = true;
	@ConfigField(name = "cow.message", category = "passive")
	public String cowCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "cow.money.amount", category = "passive")
	public String cowPrize = "0";
	@ConfigField(name = "cow.money.chance", category = "passive")
	public double cowCmdRunChance = 1;
	@ConfigField(name = "cow.commands", category = "passive")
	public List<HashMap<String, String>> cowCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		cowCmdNew.add(values1);
	}
	@ConfigField(name = "cow.head.drophead", category = "passive")
	public boolean cowHeadDropHead = true;
	@ConfigField(name = "cow.head.value", category = "passive")
	public String cowHeadPrize = "0";
	@ConfigField(name = "cow.head.chance", category = "passive")
	public double cowHeadDropChance = 0.05;
	@ConfigField(name = "cow.head.message", category = "passive")
	public String cowHeadMessage = "You got a Cow skull";

	// =====Donkey============================================
	@ConfigField(name = "donkey.enabled", category = "passive")
	public boolean donkeyEnabled = true;
	@ConfigField(name = "donkey.message", category = "passive")
	public String donkeyCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "donkey.money.amount", category = "passive")
	public String donkeyPrize = "5";
	@ConfigField(name = "donkey.money.chance", category = "passive")
	public double donkeyCmdRunChance = 1;
	@ConfigField(name = "donkey.commands", category = "passive")
	public List<HashMap<String, String>> donkeyCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		donkeyCmdNew.add(values1);
	}
	@ConfigField(name = "donkey.head.drophead", category = "passive")
	public boolean donkeyHeadDropHead = true;
	@ConfigField(name = "donkey.head.value", category = "passive")
	public String donkeyHeadPrize = "0";
	@ConfigField(name = "donkey.head.chance", category = "passive")
	public double donkeyHeadDropChance = 0.05;
	@ConfigField(name = "donkey.head.message", category = "passive")
	public String donkeyHeadMessage = "You got a Donkey skull";

	// =====Horse============================================
	@ConfigField(name = "horse.enabled", category = "passive")
	public boolean horseEnabled = true;
	@ConfigField(name = "horse.message", category = "passive")
	public String horseCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "horse.money.amount", category = "passive")
	public String horsePrize = "0";
	@ConfigField(name = "horse.money.chance", category = "passive")
	public double horseCmdRunChance = 0.05;
	@ConfigField(name = "horse.commands", category = "passive")
	public List<HashMap<String, String>> horseCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		horseCmdNew.add(values1);
	}
	@ConfigField(name = "horse.head.drophead", category = "passive")
	public boolean horseHeadDropHead = true;
	@ConfigField(name = "horse.head.value", category = "passive")
	public String horseHeadPrize = "0";
	@ConfigField(name = "horse.head.chance", category = "passive")
	public double horseHeadDropChance = 0.05;
	@ConfigField(name = "horse.head.message", category = "passive")
	public String horseHeadMessage = "You got a Horse skull";

	// =====Llama============================================
	@ConfigField(name = "llama.enabled", category = "passive")
	public boolean llamaEnabled = true;
	@ConfigField(name = "llama.message", category = "passive")
	public String llamaCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "llama.money.amount", category = "passive")
	public String llamaPrize = "0";
	@ConfigField(name = "llama.money.chance", category = "passive")
	public double llamaCmdRunChance = 1;
	@ConfigField(name = "llama.commands", category = "passive")
	public List<HashMap<String, String>> llamaCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		llamaCmdNew.add(values1);
	}
	@ConfigField(name = "llama.head.drophead", category = "passive")
	public boolean llamaHeadDropHead = true;
	@ConfigField(name = "llama.head.value", category = "passive")
	public String llamaHeadPrize = "0";
	@ConfigField(name = "llama.head.chance", category = "passive")
	public double llamaHeadDropChance = 0.05;
	@ConfigField(name = "llama.head.message", category = "passive")
	public String llamaHeadMessage = "You got a Llama skull";

	// =====Mule============================================
	@ConfigField(name = "mule.enabled", category = "passive")
	public boolean muleEnabled = true;
	@ConfigField(name = "mule.message", category = "passive")
	public String muleCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "mule.money.amount", category = "passive")
	public String mulePrize = "0";
	@ConfigField(name = "mule.money.chance", category = "passive")
	public double muleCmdRunChance = 1;
	@ConfigField(name = "mule.commands", category = "passive")
	public List<HashMap<String, String>> muleCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		muleCmdNew.add(values1);
	}
	@ConfigField(name = "mule.head.drophead", category = "passive")
	public boolean muleHeadDropHead = true;
	@ConfigField(name = "mule.head.value", category = "passive")
	public String muleHeadPrize = "0";
	@ConfigField(name = "mule.head.chance", category = "passive")
	public double muleHeadDropChance = 0.05;
	@ConfigField(name = "mule.head.message", category = "passive")
	public String muleHeadMessage = "You got a Mule skull";

	// =====Mushroom Cow============================================
	@ConfigField(name = "mushroom_cow.enabled", category = "passive")
	public boolean mushroomCowEnabled = true;
	@ConfigField(name = "mushroom_cow.message", category = "passive")
	public String mushroomCowCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "mushroom_cow.money.amount", category = "passive")
	public String mushroomCowPrize = "0";
	@ConfigField(name = "mushroom_cow.money.chance", category = "passive")
	public double mushroomCowCmdRunChance = 1;
	@ConfigField(name = "mushroom_cow.commands", category = "passive")
	public List<HashMap<String, String>> mushroomCowCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		mushroomCowCmdNew.add(values1);
	}
	@ConfigField(name = "mushroom_cow.head.drophead", category = "passive")
	public boolean mushroomCowHeadDropHead = true;
	@ConfigField(name = "mushroom_cow.head.value", category = "passive")
	public String mushroomCowHeadPrize = "0";
	@ConfigField(name = "mushroom_cow.head.chance", category = "passive")
	public double mushroomCowHeadDropChance = 0.05;
	@ConfigField(name = "mushroom_cow.head.message", category = "passive")
	public String mushroomCowHeadMessage = "You got a Mushroom Cow skull";

	// =====Ocelot============================================
	@ConfigField(name = "ocelot.enabled", category = "passive")
	public boolean ocelotEnabled = true;
	@ConfigField(name = "ocelot.message", category = "passive")
	public String ocelotCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "ocelot.money.amount", category = "passive")
	public String ocelotPrize = "0";
	@ConfigField(name = "ocelot.money.chance", category = "passive")
	public double ocelotCmdRunChance = 1;
	@ConfigField(name = "ocelot.commands", category = "passive")
	public List<HashMap<String, String>> ocelotCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		ocelotCmdNew.add(values1);
	}
	@ConfigField(name = "ocelot.head.drophead", category = "passive")
	public boolean ocelotHeadDropHead = true;
	@ConfigField(name = "ocelot.head.value", category = "passive")
	public String ocelotHeadPrize = "0";
	@ConfigField(name = "ocelot.head.chance", category = "passive")
	public double ocelotHeadDropChance = 0.05;
	@ConfigField(name = "ocelot.head.message", category = "passive")
	public String ocelotHeadMessage = "You got a Ocelot skull";

	// =====Parrot============================================
	@ConfigField(name = "parrot.enabled", category = "passive")
	public boolean parrotEnabled = true;
	@ConfigField(name = "parrot.message", category = "passive")
	public String parrotCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "parrot.money.amount", category = "passive")
	public String parrotPrize = "2";
	@ConfigField(name = "parrot.money.chance", category = "passive")
	public double parrotCmdRunChance = 1;
	@ConfigField(name = "parrot.commands", category = "passive")
	public List<HashMap<String, String>> parrotCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		parrotCmdNew.add(values1);
	}
	@ConfigField(name = "parrot.head.drophead", category = "passive")
	public boolean parrotHeadDropHead = true;
	@ConfigField(name = "parrot.head.value", category = "passive")
	public String parrotHeadPrize = "0";
	@ConfigField(name = "parrot.head.chance", category = "passive")
	public double parrotHeadDropChance = 0.10;
	@ConfigField(name = "parrot.head.message", category = "passive")
	public String parrotHeadMessage = "You got a Parrot skull";

	// =====Pig============================================
	@ConfigField(name = "pig.enabled", category = "passive")
	public boolean pigEnabled = true;
	@ConfigField(name = "pig.message", category = "passive")
	public String pigCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "pig.money.amount", category = "passive")
	public String pigPrize = "0";
	@ConfigField(name = "pig.money.chance", category = "passive")
	public double pigCmdRunChance = 1;
	@ConfigField(name = "pig.commands", category = "passive")
	public List<HashMap<String, String>> pigCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		pigCmdNew.add(values1);
	}
	@ConfigField(name = "pig.head.drophead", category = "passive")
	public boolean pigHeadDropHead = true;
	@ConfigField(name = "pig.head.value", category = "passive")
	public String pigHeadPrize = "0";
	@ConfigField(name = "pig.head.chance", category = "passive")
	public double pigHeadDropChance = 0.05;
	@ConfigField(name = "pig.head.message", category = "passive")
	public String pigHeadMessage = "You got a Pig skull";

	// =====Rabbit============================================
	@ConfigField(name = "rabbit.enabled", category = "passive")
	public boolean rabbitEnabled = true;
	@ConfigField(name = "rabbit.message", category = "passive")
	public String rabbitCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "rabbit.money.amount", category = "passive")
	public String rabbitPrize = "0";
	@ConfigField(name = "rabbit.money.chance", category = "passive")
	public double rabbitCmdRunChance = 1;
	@ConfigField(name = "rabbit.commands", category = "passive")
	public List<HashMap<String, String>> rabbitCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		rabbitCmdNew.add(values1);
	}
	@ConfigField(name = "rabbit.head.drophead", category = "passive")
	public boolean rabbitHeadDropHead = true;
	@ConfigField(name = "rabbit.head.value", category = "passive")
	public String rabbitHeadPrize = "0";
	@ConfigField(name = "rabbit.head.chance", category = "passive")
	public double rabbitHeadDropChance = 0.05;
	@ConfigField(name = "rabbit.head.message", category = "passive")
	public String rabbitHeadMessage = "You got a Rabbit skull";

	// =====Sheep============================================
	@ConfigField(name = "sheep.enabled", category = "passive")
	public boolean sheepEnabled = true;
	@ConfigField(name = "sheep.message", category = "passive")
	public String sheepCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "sheep.money.amount", category = "passive")
	public String sheepPrize = "0";
	@ConfigField(name = "sheep.money.chance", category = "passive")
	public double sheepCmdRunChance = 1;
	@ConfigField(name = "sheep.commands", category = "passive")
	public List<HashMap<String, String>> sheepCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		sheepCmdNew.add(values1);
	}
	@ConfigField(name = "sheep.head.drophead", category = "passive")
	public boolean sheepHeadDropHead = true;
	@ConfigField(name = "sheep.head.value", category = "passive")
	public String sheepHeadPrize = "0";
	@ConfigField(name = "sheep.head.chance", category = "passive")
	public double sheepHeadDropChance = 0.05;
	@ConfigField(name = "sheep.head.message", category = "passive")
	public String sheepHeadMessage = "You got a Sheep skull";

	// =====Skeleton Horse============================================
	@ConfigField(name = "skeleton_horse.enabled", category = "passive")
	public boolean skeletonHorseEnabled = true;
	@ConfigField(name = "skeleton_horse.message", category = "passive")
	public String skeletonHorseCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "skeleton_horse.money.amount", category = "passive")
	public String skeletonHorsePrize = "10";
	@ConfigField(name = "skeleton_horse.money.chance", category = "passive")
	public double skeletonHorseCmdRunChance = 1;
	@ConfigField(name = "skeleton_horse.commands", category = "passive")
	public List<HashMap<String, String>> skeletonHorseCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		skeletonHorseCmdNew.add(values1);
	}
	@ConfigField(name = "skeleton_horse.head.drophead", category = "passive")
	public boolean skeletonHorseHeadDropHead = true;
	@ConfigField(name = "skeleton_horse.head.value", category = "passive")
	public String skeletonHorseHeadPrize = "0";
	@ConfigField(name = "skeleton_horse.head.chance", category = "passive")
	public double skeletonHorseHeadDropChance = 0.05;
	@ConfigField(name = "skeleton_horse.head.message", category = "passive")
	public String skeletonHorseHeadMessage = "You got a Skeleton Horse skull";

	// =====Snowman============================================
	@ConfigField(name = "snowman.enabled", category = "passive")
	public boolean snowmanEnabled = true;
	@ConfigField(name = "snowman.message", category = "passive")
	public String snowmanCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "snowman.money.amount", category = "passive")
	public String snowmanPrize = "0";
	@ConfigField(name = "snowman.money.chance", category = "passive")
	public double snowmanCmdRunChance = 0;
	@ConfigField(name = "snowman.commands", category = "passive")
	public List<HashMap<String, String>> snowmanCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		snowmanCmdNew.add(values1);
	}
	@ConfigField(name = "snowman.head.drophead", category = "passive")
	public boolean snowmanHeadDropHead = true;
	@ConfigField(name = "snowman.head.value", category = "passive")
	public String snowmanHeadPrize = "0";
	@ConfigField(name = "snowman.head.chance", category = "passive")
	public double snowmanHeadDropChance = 0.05;
	@ConfigField(name = "snowman.head.message", category = "passive")
	public String snowmanHeadMessage = "You got a Snowman skull";

	// =====Squid============================================
	@ConfigField(name = "squid.enabled", category = "passive")
	public boolean squidEnabled = true;
	@ConfigField(name = "squid.message", category = "passive")
	public String squidCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "squid.money.amount", category = "passive")
	public String squidPrize = "0";
	@ConfigField(name = "squid.money.chance", category = "passive")
	public double squidCmdRunChance = 1;
	@ConfigField(name = "squid.commands", category = "passive")
	public List<HashMap<String, String>> squidCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		squidCmdNew.add(values1);
	}
	@ConfigField(name = "squid.head.drophead", category = "passive")
	public boolean squidHeadDropHead = true;
	@ConfigField(name = "squid.head.value", category = "passive")
	public String squidHeadPrize = "0";
	@ConfigField(name = "squid.head.chance", category = "passive")
	public double squidHeadDropChance = 0.05;
	@ConfigField(name = "squid.head.message", category = "passive")
	public String squidHeadMessage = "You got a Squid skull";

	// =====Wolf============================================
	@ConfigField(name = "wolf.enabled", category = "passive")
	public boolean wolfEnabled = true;
	@ConfigField(name = "wolf.message", category = "passive")
	public String wolfCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "wolf.money.amount", category = "passive")
	public String wolfPrize = "-10";
	@ConfigField(name = "wolf.money.chance", category = "passive")
	public double wolfCmdRunChance = 1;
	@ConfigField(name = "wolf.commands", category = "passive")
	public List<HashMap<String, String>> wolfCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		wolfCmdNew.add(values1);
	}
	@ConfigField(name = "wolf.head.drophead", category = "passive")
	public boolean wolfHeadDropHead = true;
	@ConfigField(name = "wolf.head.value", category = "passive")
	public String wolfHeadPrize = "0";
	@ConfigField(name = "wolf.head.chance", category = "passive")
	public double wolfHeadDropChance = 0.05;
	@ConfigField(name = "wolf.head.message", category = "passive")
	public String wolfHeadMessage = "You got a Wolf skull";

	// =====Zombie Horse============================================
	@ConfigField(name = "zombie_horse.enabled", category = "passive")
	public boolean zombieHorseEnabled = true;
	@ConfigField(name = "zombie_horse.message", category = "passive")
	public String zombieHorseCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "zombie_horse.money.amount", category = "passive")
	public String zombieHorsePrize = "-10";
	@ConfigField(name = "zombie_horse.money.chance", category = "passive")
	public double zombieHorseCmdRunChance = 1;
	@ConfigField(name = "zombie_horse.commands", category = "passive")
	public List<HashMap<String, String>> zombieHorseCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.25");
		values1.put("message", "");
		zombieHorseCmdNew.add(values1);
	}
	@ConfigField(name = "zombie_horse.head.drophead", category = "passive")
	public boolean zombieHorseHeadDropHead = true;
	@ConfigField(name = "zombie_horse.head.value", category = "passive")
	public String zombieHorseHeadPrize = "0";
	@ConfigField(name = "zombie_horse.head.chance", category = "passive")
	public double zombieHorseHeadDropChance = 0.25;
	@ConfigField(name = "zombie_horse.head.message", category = "passive")
	public String zombieHorseHeadMessage = "You got a Zombie Horse skull";

	// #####################################################################################
	// Fish / Fishing
	// #####################################################################################

	@ConfigField(name = "enable_fishing_rewards", category = "fishing", comment = "Set this to true if you want to disable all fishing rewards / features.")
	public boolean enableFishingRewards = true;

	// =====Raw Fish============================================
	@ConfigField(name = "raw_fish.enabled", category = "fishing")
	public boolean rawFishEnabled = true;
	@ConfigField(name = "raw_fish.message", category = "fishing")
	public String rawFishCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "raw_fish.money.amount", category = "fishing")
	public String rawFishPrize = "1:3";
	@ConfigField(name = "raw_fish.money.chance", category = "fishing")
	public double rawFishCmdRunChance = 1;
	@ConfigField(name = "raw_fish.commands", category = "fishing")
	public List<HashMap<String, String>> rawFishCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.05");
		values1.put("message", "");
		rawFishCmdNew.add(values1);
	}
	@ConfigField(name = "raw_fish.head.drophead", category = "fishing")
	public boolean rawFishHeadDropHead = true;
	@ConfigField(name = "raw_fish.head.value", category = "fishing")
	public String rawFishHeadPrize = "0";
	@ConfigField(name = "raw_fish.head.chance", category = "fishing")
	public double rawFishHeadDropChance = 0.05;
	@ConfigField(name = "raw_fish.head.message", category = "fishing")
	public String rawFishHeadMessage = "You got a Raw Fish skull";

	// =====Raw Salmon============================================
	@ConfigField(name = "raw_salmon.enabled", category = "fishing")
	public boolean rawSalmonEnabled = true;
	@ConfigField(name = "raw_salmon.message", category = "fishing")
	public String rawSalmonCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "raw_salmon.money.amount", category = "fishing")
	public String rawSalmonPrize = "2:8";
	@ConfigField(name = "raw_salmon.money.chance", category = "fishing")
	public double rawSalmonCmdRunChance = 1;
	@ConfigField(name = "raw_salmon.commands", category = "fishing")
	public List<HashMap<String, String>> rawSalmonCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.1");
		values1.put("message", "");
		rawSalmonCmdNew.add(values1);
	}
	@ConfigField(name = "raw_salmon.head.drophead", category = "fishing")
	public boolean rawSalmonHeadDropHead = true;
	@ConfigField(name = "raw_salmon.head.value", category = "fishing")
	public String rawSalmonHeadPrize = "0";
	@ConfigField(name = "raw_salmon.head.chance", category = "fishing")
	public double rawSalmonHeadDropChance = 0.10;
	@ConfigField(name = "raw_salmon.head.message", category = "fishing")
	public String rawSalmonHeadMessage = "You got a Raw Salmon skull";

	// =====Clownfish============================================
	@ConfigField(name = "clownfish.enabled", category = "fishing")
	public boolean clownfishEnabled = true;
	@ConfigField(name = "clownfish.message", category = "fishing")
	public String clownfishCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "clownfish.money.amount", category = "fishing")
	public String clownfishPrize = "20:40";
	@ConfigField(name = "clownfish.money.chance", category = "fishing")
	public double clownfishCmdRunChance = 1;
	@ConfigField(name = "clownfish.commands", category = "fishing")
	public List<HashMap<String, String>> clownfishCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.5");
		values1.put("message", "");
		clownfishCmdNew.add(values1);
	}
	@ConfigField(name = "clownfish.head.drophead", category = "fishing")
	public boolean clownfishHeadDropHead = true;
	@ConfigField(name = "clownfish.head.value", category = "fishing")
	public String clownfishHeadPrize = "0";
	@ConfigField(name = "clownfish.head.chance", category = "fishing")
	public double clownfishHeadDropChance = 0.5;
	@ConfigField(name = "clownfish.head.message", category = "fishing")
	public String clownfishHeadMessage = "You got a Clownfish skull";

	// =====Pufferfish============================================
	@ConfigField(name = "pufferfish.enabled", category = "fishing")
	public boolean pufferfishEnabled = true;
	@ConfigField(name = "pufferfish.message", category = "fishing")
	public String pufferfishCmdDesc = "The {killed} dropped {prize} {rewardname}";
	@ConfigField(name = "pufferfish.money.amount", category = "fishing")
	public String pufferfishPrize = "5:15";
	@ConfigField(name = "pufferfish.money.chance", category = "fishing")
	public double pufferfishCmdRunChance = 1;
	@ConfigField(name = "pufferfish.commands", category = "fishing")
	public List<HashMap<String, String>> pufferfishCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "give {player} Iron_ingot 1");
		values1.put("chance", "0.4");
		values1.put("message", "");
		pufferfishCmdNew.add(values1);
	}
	@ConfigField(name = "pufferfish.head.drophead", category = "fishing")
	public boolean pufferfishHeadDropHead = true;
	@ConfigField(name = "pufferfish.head.value", category = "fishing")
	public String pufferfishHeadPrize = "0";
	@ConfigField(name = "pufferfish.head.chance", category = "fishing")
	public double pufferfishHeadDropChance = 0.40;
	@ConfigField(name = "pufferfish.head.message", category = "fishing")
	public String pufferfishHeadMessage = "You got a Pufferfish skull";

	// #####################################################################################
	// PVP
	// #####################################################################################
	@ConfigField(name = "pvp_allowed", category = "pvp.player", comment = "Set pvpAllowed=false to disable rewards on killing other players.")
	public boolean pvpAllowed = true;
	@ConfigField(name = "rob_from_victim", category = "pvp.player", comment = "Set rob_from_victim=true to steal from the victim or "
			+ "\nrob_from_victim=false to get the reward money from the server.")
	public boolean robFromVictim = true;
	@ConfigField(name = "message", category = "pvp.player")
	public String pvpCmdDesc = "";
	@ConfigField(name = "amount", category = "pvp.player.money")
	public String pvpKillPrize = "1.0%";
	@ConfigField(name = "chance", category = "pvp.player.money.")
	public double pvpCmdRunChance = 1;
	@ConfigField(name = "message", category = "pvp.player", comment = "Write the message to the killer, describing the reward / console commands")
	public String pvpKillCmdDesc = "You got {killed_player}\'s skull";
	@ConfigField(name = "commands", category = "pvp.player", comment = "One or more console commands to be run when a player kills another player.")
	public List<HashMap<String, String>> pvpCmdNew = new ArrayList<HashMap<String, String>>();
	{
		HashMap<String, String> values1 = new HashMap<String, String>();
		values1.put("cmd", "");
		values1.put("chance", "0.5");
		values1.put("message", "");
		pvpCmdNew.add(values1);
	}
	@ConfigField(name = "drophead", category = "pvp.player.head")
	public boolean pvpHeadDropHead = true;
	@ConfigField(name = "value", category = "pvp.player.head", comment = "The Head price if you want playerheads to have a value like the bag of gold.")
	public String pvpHeadPrize = "10";
	@ConfigField(name = "chance", category = "pvp.player.head")
	public double pvpHeadDropChance = 1;
	@ConfigField(name = "message", category = "pvp.player.head")
	public String pvpHeadMessage = "You got a player skull";

	// #####################################################################################
	// Specials / Achievements
	// #####################################################################################
	@ConfigField(name = "disable_achievements_in_worlds", category = "achievements", comment = "Put the names of the worlds here where you want to disable achievements."
			+ "\nPlayers will still get rewards for killings.")
	public String[] disableAchievementsInWorlds = { "worldname", "worldname2" };
	@ConfigField(name = "show_achievements_without_reward", category = "achievements", comment = "Set this to true if you want to see achievements when you use /mobhunt achievements"
			+ "\nallthough there is no reward for this.")
	public boolean showAchievementsWithoutAReward = false;

	@ConfigField(name = "money", category = "achievements.specials.charged_kill")
	public double specialCharged = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.charged_kill")
	public String specialChargedCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.charged_kill")
	public String specialChargedCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.creeper_punch")
	public double specialCreeperPunch = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.creeper_punch")
	public String specialCreeperPunchCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.creeper_punch")
	public String specialCreeperPunchCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.axe_murderer")
	public double specialAxeMurderer = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.axe_murderer")
	public String specialAxeMurdererCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.axe_murderer")
	public String specialAxeMurdererCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.david_and_goliath")
	public double davidAndGoliat = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.david_and_goliath")
	public String davidAndGoliatCmd = "give {player} diamond_helmet 1";
	@ConfigField(name = "message", category = "achievements.specials.david_and_goliath")
	public String davidAndGoliatCmdDesc = "You got 1000 and a Diamond Helmet for the kill";

	@ConfigField(name = "money", category = "achievements.specials.recordhungry")
	public double specialRecordHungry = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.recordhungry")
	public String specialRecordHungryCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.recordhungry")
	public String specialRecordHungryCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.infighting")
	public double specialInfighting = 2000;
	@ConfigField(name = "commands", category = "achievements.specials.infighting")
	public String specialInfightingCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "messages", category = "achievements.specials.infighting")
	public String specialInfightingCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.by_the_book")
	public double specialByTheBook = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.by_the_book")
	public String specialByTheBookCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.by_the_book")
	public String specialByTheBookCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.creepercide")
	public double specialCreepercide = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.creepercide")
	public String specialCreepercideCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.creepercide")
	public String specialCreepercideCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.hunt_begins")
	public double specialHuntBegins = 500;
	@ConfigField(name = "commands", category = "achievements.specials.hunt_begins")
	public String specialHuntBeginsCmd = "";
	@ConfigField(name = "message", category = "achievements.specials.hunt_begins")
	public String specialHuntBeginsCmdDesc = "";

	@ConfigField(name = "money", category = "achievements.specials.itsmagic")
	public double specialItsMagic = 2000;
	@ConfigField(name = "commands", category = "achievements.specials.itsmagic")
	public String specialItsMagicCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.itsmagic")
	public String specialItsMagicCmdDesc = "Enjoy you Gold ingot";

	@ConfigField(name = "money", category = "achievements.specials.fancypants")
	public double specialFancyPants = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.fancypants")
	public String specialFancyPantsCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.fancypants")
	public String specialFancyPantsCmdDesc = "Enjoy you Gold ingot";

	@ConfigField(name = "money", category = "achievements.specials.master_sniper")
	public double specialMasterSniper = 2000;
	@ConfigField(name = "commands", category = "achievements.specials.master_sniper")
	public String specialMasterSniperCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.master_sniper")
	public String specialMasterSniperCmdDesc = "Enjoy you Gold ingot";

	@ConfigField(name = "money", category = "achievements.specials.justintime")
	public double specialJustInTime = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.justintime")
	public String specialJustInTimeCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.justintime")
	public String specialJustInTimeCmdDesc = "Enjoy you Gold ingot";

	@ConfigField(name = "money", category = "achievements.specials.fangmaster")
	public double specialFangMaster = 1000;
	@ConfigField(name = "commands", category = "achievements.specials.fangmaster")
	public String specialFangMasterCmd = "give {player} gold_ingot 1";
	@ConfigField(name = "message", category = "achievements.specials.fangmaster")
	public String specialFangMasterCmdDesc = "Enjoy your Gold ingot";

	@ConfigField(name = "money", category = "achievements.hunter.level1")
	public double specialHunter1 = 1000;
	@ConfigField(name = "commands", category = "achievements.hunter.level1")
	public String specialHunter1Cmd = "give {player} gold_ingot 5";
	@ConfigField(name = "message", category = "achievements.hunter.level1")
	public String specialHunter1CmdDesc = "Enjoy your 5 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level2")
	public double specialHunter2 = 2500;
	@ConfigField(name = "commands", category = "achievements.hunter.level2")
	public String specialHunter2Cmd = "give {player} gold_ingot 10";
	@ConfigField(name = "message", category = "achievements.hunter.level2")
	public String specialHunter2CmdDesc = "Enjoy your 10 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level3")
	public double specialHunter3 = 5000;
	@ConfigField(name = "commands", category = "achievements.hunter.level3")
	public String specialHunter3Cmd = "give {player} gold_ingot 20";
	@ConfigField(name = "message", category = "achievements.hunter.level3")
	public String specialHunter3CmdDesc = "Enjoy your 20 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level4")
	public double specialHunter4 = 10000;
	@ConfigField(name = "commands", category = "achievements.hunter.level4")
	public String specialHunter4Cmd = "give {player} gold_ingot 25";
	@ConfigField(name = "message", category = "achievements.hunter.level4")
	public String specialHunter4CmdDesc = "Enjoy your 25 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level5")
	public double specialHunter5 = 20000;
	@ConfigField(name = "commands", category = "achievements.hunter.level5")
	public String specialHunter5Cmd = "give {player} gold_ingot 40";
	@ConfigField(name = "message", category = "achievements.hunter.level5")
	public String specialHunter5CmdDesc = "Enjoy your 40 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level6")
	public double specialHunter6 = 40000;
	@ConfigField(name = "commands", category = "achievements.hunter.level6")
	public String specialHunter6Cmd = "give {player} gold_ingot 50";
	@ConfigField(name = "message", category = "achievements.hunter.level6")
	public String specialHunter6CmdDesc = "Enjoy your 50 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level7")
	public double specialHunter7 = 80000;
	@ConfigField(name = "commands", category = "achievements.hunter.level7")
	public String specialHunter7Cmd = "give {player} gold_ingot 60";
	@ConfigField(name = "message", category = "achievements.hunter.level7")
	public String specialHunter7CmdDesc = "Enjoy your 60 Gold ingots";

	@ConfigField(name = "money", category = "achievements.hunter.level8")
	public double specialHunter8 = 160000;
	@ConfigField(name = "commands", category = "achievements.hunter.level8")
	public String specialHunter8Cmd = "give {player} gold_ingot 120";
	@ConfigField(name = "message", category = "achievements.hunter.level8")
	public String specialHunter8CmdDesc = "Enjoy your 120 Gold ingots";

	// #####################################################################################
	// Achievement Hunter Levels
	// #####################################################################################
	@ConfigField(name = "bat_level1", category = "achievements.hunter.mob_level")
	public int batLevel1 = 100;

	@ConfigField(name = "blaze_level1", category = "achievements.hunter.mob_level")
	public int blazeLevel1 = 80;

	@ConfigField(name = "blacksmith_level1", category = "achievements.hunter.mob_level")
	public int blacksmithLevel1 = 100;

	@ConfigField(name = "bonusmob_level1", category = "achievements.hunter.mob_level")
	public int bonusMobLevel1 = 20;

	@ConfigField(name = "butcher_level1", category = "achievements.hunter.mob_level")
	public int butcherLevel1 = 100;
	@ConfigField(name = "cartographer_level1", category = "achievements.hunter.mob_level")
	public int cartographerLevel1 = 100;

	@ConfigField(name = "cave_spider_level1", category = "achievements.hunter.mob_level")
	public int caveSpiderLevel1 = 100;

	@ConfigField(name = "chicken_level1", category = "achievements.hunter.mob_level")
	public int chickenLevel1 = 100;

	@ConfigField(name = "clownfish_level1", category = "achievements.hunter.mob_level")
	public int clownfishLevel1 = 100;

	@ConfigField(name = "cow_level1", category = "achievements.hunter.mob_level")
	public int cowLevel1 = 100;

	@ConfigField(name = "creeper_level1", category = "achievements.hunter.mob_level")
	public int creeperLevel1 = 100;

	@ConfigField(name = "donkey_level1", category = "achievements.hunter.mob_level")
	public int donkeyLevel1 = 100;

	@ConfigField(name = "elder_guardian_level1", category = "achievements.hunter.mob_level")
	public int elderGuardianLevel1 = 50;

	@ConfigField(name = "enderdragon_level1", category = "achievements.hunter.mob_level")
	public int enderdragonLevel1 = 20;

	@ConfigField(name = "enderman_level1", category = "achievements.hunter.mob_level")
	public int endermanLevel1 = 100;

	@ConfigField(name = "endermite_level1", category = "achievements.hunter.mob_level")
	public int endermiteLevel1 = 100;

	@ConfigField(name = "evoker_level1", category = "achievements.hunter.mob_level")
	public int evokerLevel1 = 50;

	@ConfigField(name = "farmer_level1", category = "achievements.hunter.mob_level")
	public int farmerLevel1 = 100;

	@ConfigField(name = "ghast_level1", category = "achievements.hunter.mob_level")
	public int ghastLevel1 = 80;

	@ConfigField(name = "giant_level1", category = "achievements.hunter.mob_level")
	public int giantLevel1 = 100;

	@ConfigField(name = "guardian_level1", category = "achievements.hunter.mob_level")
	public int guardianLevel1 = 100;

	@ConfigField(name = "horse_level1", category = "achievements.hunter.mob_level")
	public int horseLevel1 = 100;

	@ConfigField(name = "husk_level1", category = "achievements.hunter.mob_level")
	public int huskLevel1 = 100;

	@ConfigField(name = "illusioner_level1", category = "achievements.hunter.mob_level")
	public int illusionerLevel1 = 100;

	@ConfigField(name = "iron_golem_level1", category = "achievements.hunter.mob_level")
	public int ironGolemLevel1 = 100;

	@ConfigField(name = "killerrabbit_level1", category = "achievements.hunter.mob_level")
	public int killerRabbitLevel1 = 100;

	@ConfigField(name = "librarian_level1", category = "achievements.hunter.mob_level")
	public int librarianLevel1 = 100;

	@ConfigField(name = "llama_level1", category = "achievements.hunter.mob_level")
	public int llamaLevel1 = 100;

	@ConfigField(name = "magma_cube_level1", category = "achievements.hunter.mob_level")
	public int magmaCubeLevel1 = 100;

	@ConfigField(name = "mule_level1", category = "achievements.hunter.mob_level")
	public int muleLevel1 = 100;

	@ConfigField(name = "mushroom_cow_level1", category = "achievements.hunter.mob_level")
	public int mushroomCowLevel1 = 100;

	@ConfigField(name = "nitwit_level1", category = "achievements.hunter.mob_level")
	public int nitwitLevel1 = 100;

	@ConfigField(name = "ocelot_level1", category = "achievements.hunter.mob_level")
	public int ocelotLevel1 = 100;

	@ConfigField(name = "parrot_level1", category = "achievements.hunter.mob_level")
	public int parrotLevel1 = 100;

	@ConfigField(name = "pig_level1", category = "achievements.hunter.mob_level")
	public int pigLevel1 = 100;

	@ConfigField(name = "polar_bear_level1", category = "achievements.hunter.mob_level")
	public int polarBearLevel1 = 100;

	@ConfigField(name = "priest_level1", category = "achievements.hunter.mob_level")
	public int priestLevel1 = 100;

	@ConfigField(name = "pvpplayer_level1", category = "achievements.hunter.mob_level")
	public int pvpPlayerLevel1 = 100;

	@ConfigField(name = "pufferfish_level1", category = "achievements.hunter.mob_level")
	public int pufferfishLevel1 = 100;

	@ConfigField(name = "rabbit_level1", category = "achievements.hunter.mob_level")
	public int rabbitLevel1 = 100;

	@ConfigField(name = "rawfish_level1", category = "achievements.hunter.mob_level")
	public int rawfishLevel1 = 100;

	@ConfigField(name = "rawsalmon_level1", category = "achievements.hunter.mob_level")
	public int rawsalmonLevel1 = 100;

	@ConfigField(name = "sheep_level1", category = "achievements.hunter.mob_level")
	public int sheepLevel1 = 100;

	@ConfigField(name = "shulker_level1", category = "achievements.hunter.mob_level")
	public int shulkerLevel1 = 100;

	@ConfigField(name = "silverfish_level1", category = "achievements.hunter.mob_level")
	public int silverfishLevel1 = 100;

	@ConfigField(name = "skeleton_level1", category = "achievements.hunter.mob_level")
	public int skeletonLevel1 = 100;

	@ConfigField(name = "skeletonhorse_level1", category = "achievements.hunter.mob_level")
	public int skeletonHorseLevel1 = 100;

	@ConfigField(name = "slime_base_level1", category = "achievements.hunter.mob_level")
	public int slimeLevel1 = 100;

	@ConfigField(name = "snowman_level1", category = "achievements.hunter.mob_level")
	public int snowmanLevel1 = 100;

	@ConfigField(name = "spider_level1", category = "achievements.hunter.mob_level")
	public int spiderLevel1 = 100;

	@ConfigField(name = "squid_level1", category = "achievements.hunter.mob_level")
	public int squidLevel1 = 100;

	@ConfigField(name = "stray_level1", category = "achievements.hunter.mob_level")
	public int strayLevel1 = 100;

	@ConfigField(name = "vex_level1", category = "achievements.hunter.mob_level")
	public int vexLevel1 = 100;

	@ConfigField(name = "villager_level1", category = "achievements.hunter.mob_level")
	public int villagerLevel1 = 100;

	@ConfigField(name = "vindicator_level1", category = "achievements.hunter.mob_level")
	public int vindicatorLevel1 = 100;

	@ConfigField(name = "witch_level1", category = "achievements.hunter.mob_level")
	public int witchLevel1 = 80;

	@ConfigField(name = "wither_level1", category = "achievements.hunter.mob_level")
	public int witherLevel1 = 20;

	@ConfigField(name = "wither_skeleton_level1", category = "achievements.hunter.mob_level")
	public int witherSkeletonLevel1 = 80;

	@ConfigField(name = "wolf_level1", category = "achievements.hunter.mob_level")
	public int wolfLevel1 = 100;

	@ConfigField(name = "zombie_level1", category = "achievements.hunter.mob_level")
	public int zombieLevel1 = 100;

	@ConfigField(name = "zombiehorse_level1", category = "achievements.hunter.mob_level")
	public int zombieHorseLevel1 = 100;

	@ConfigField(name = "zombie_pigman_level1", category = "achievements.hunter.mob_level")
	public int zombiePigmanLevel1 = 100;

	@ConfigField(name = "zombie_villager_level1", category = "achievements.hunter.mob_level")
	public int zombieVillagerLevel1 = 100;

	// #####################################################################################
	// Assists
	// #####################################################################################
	@ConfigField(name = "enable", category = "assists", comment = "Enabling assist allows the second last player to attack a mob to get some money from it")
	public boolean enableAssists = true;
	@ConfigField(name = "multiplier", category = "assists", comment = "This should be a value that is multiplied against the mobs base kill value."
			+ "\nThis is used to determine how much money an assister gets.")
	public double assistMultiplier = 0.25;
	@ConfigField(name = "allow_killstreak", category = "assists", comment = "Should killstreak be applied to assists")
	public boolean assistAllowKillstreak = false;
	@ConfigField(name = "timeout", category = "assists", comment = "Time in seconds after attacking a mob that can be counted as an assist")
	public int assistTimeout = 4;

	// #####################################################################################
	// Grinding detection
	// #####################################################################################
	@ConfigField(name = "enable_grinding_detection", category = "grinding")
	public boolean grindingDetectionEnabled = true;
	@ConfigField(name = "disable_grinding_detection_in_worlds", category = "grinding", comment = "Put the names of the worlds here where you want to disable grinding detection"
			+ "\nYou would typically do this in creative worlds.")
	public String[] disableGrindingDetectionInWorlds = { "worldname", "worldname2" };

	@ConfigField(name = "grinding_stacked_mobs_allowed", category = "grinding", comment = "Killing stacked mobs (created by a mob stacking plugin) "
			+ "\nis by nature detected as grinding and by default allowed. If you want to the the grinding detection to detect"
			+ "\nkillings of stacked to be detected as gring, you must set grinding_stacked_mobs_allowed to false.")
	public boolean isGrindingStackedMobsAllowed = true;

	// Area grinding
	@ConfigField(name = "detect_grinding_areas", category = "grinding.area")
	public boolean areaDetectionEnabled = true;
	@ConfigField(name = "grinding_detection_range", category = "grinding.area")
	public int grindingDetectionRange = 15;
	@ConfigField(name = "grinding_detection_number_of_death", category = "grinding.area")
	public int grindingDetectionNumberOfDeath = 20;
	@ConfigField(name = "disable_natural_item_drops_on_player_grinding", category = "grinding.area")
	public boolean disableNaturalItemDropsOnPlayerGrinding = false;
	@ConfigField(name = "disable_natural_xp_drops_on_player_grinding", category = "grinding.area")
	public boolean disableNaturalXPDropsOnPlayerGrinding = false;
	@ConfigField(name = "blacklist_player_grinding_spots_as_server_worldwide_spots", category = "grinding.area")
	public boolean blacklistPlayerGrindingSpotsServerWorldWide = false;

	// Farm detection
	@ConfigField(name = "detect_farms", category = "grinding.farms")
	public boolean detectFarms = true;

	// NetherGoldXPFarm
	@ConfigField(name = "detect_nether_gold_farms", category = "grinding.farms.nether_gold_farms")
	public boolean detectNetherGoldFarms = true;
	@ConfigField(name = "seconds_to_search_for_grinding", category = "grinding.farms.nether_gold_farms")
	public int secondsToSearchForGrinding = 30;
	@ConfigField(name = "range_to_search_for_grinding", category = "grinding.farms.nether_gold_farms")
	public double rangeToSearchForGrinding = 4;
	@ConfigField(name = "number_of_deaths_when_searching_for_grinding", category = "grinding.farms.nether_gold_farms")
	public int numberOfDeathsWhenSearchingForGringding = 5;
	@ConfigField(name = "disable_natural_item_drops", category = "grinding.farms.nether_gold_farms")
	public boolean disableNaturalItemDropsOnNetherGoldFarms = false;
	@ConfigField(name = "disable_natural_xp_drops", category = "grinding.farms.nether_gold_farms")
	public boolean disableNaturalXPDropsOnNetherGoldFarms = false;

	// other farms
	@ConfigField(name = "detect_other_farms", category = "grinding.farms.otherfarms")
	public boolean detectOtherFarms = true;
	@ConfigField(name = "seconds_to_search_for_grinding", category = "grinding.farms.otherfarms")
	public int secondsToSearchForGrindingOnOtherFarms = 30;
	@ConfigField(name = "range_to_search_for_grinding", category = "grinding.farms.otherfarms")
	public double rangeToSearchForGrindingOnOtherFarms = 4;
	@ConfigField(name = "number_of_deaths_when_searching_for_grinding", category = "grinding.farms.otherfarms")
	public int numberOfDeathsWhenSearchingForGringdingOnOtherFarms = 10;
	@ConfigField(name = "disable_natural_item_drops", category = "grinding.farms.otherfarms")
	public boolean disableNaturalItemDropsOnOtherFarms = false;
	@ConfigField(name = "disable_natural_xp_drops", category = "grinding.farms.otherfarms")
	public boolean disableNaturalXPDropsOnOtherFarms = false;

	// #####################################################################################
	// Multipier Section
	// #####################################################################################
	// #####################################################################################
	// Bonuses _ multipliers
	// #####################################################################################
	@ConfigField(name = "sneaky", category = "multiplier.bonus")
	public double bonusSneaky = 2.0;
	@ConfigField(name = "return_to_sender", category = "multiplier.bonus")
	public double bonusReturnToSender = 2.0;
	@ConfigField(name = "push_off_cliff", category = "multiplier.bonus")
	public double bonusSendFalling = 2.0;
	@ConfigField(name = "no_weapon", category = "multiplier.bonus")
	public double bonusNoWeapon = 2.0;
	@ConfigField(name = "far_shot", category = "multiplier.bonus", comment = "This is the PRO_Sniper bonus. The Sniper bonus is calulated as half of PRO_Sniper bonus."
			+ "\nIf If PRO Sniper (far_shot) is 2, then Sniper will be = 1+((far_shot_1)/2)=1.5")
	public double bonusFarShot = 2.0;
	@ConfigField(name = "mounted", category = "multiplier.bonus")
	public double bonusMounted = 1.5;
	@ConfigField(name = "friendly_fire", category = "multiplier.bonus")
	public double bonusFriendlyFire = 4;
	@ConfigField(name = "bonus_mob", category = "multiplier.bonus")
	public double bonusBonusMob = 10;
	@ConfigField(name = "bonusMob_head_prize", category = "multiplier.bonus")
	public String bonusMobHeadPrize = "100";

	@ConfigField(name = "critical", category = "multiplier.bonus")
	public double bonusCritical = 2;
	@ConfigField(name = "bonus_mob_chance", category = "multiplier.bonus", comment = "This is the chance (% chance 0_100) that a bonus mob will spawn.")
	public double bonusMobChance = 0.2;
	@ConfigField(name = "babyMultiplier", category = "multiplier.bonus", comment = "Bonus for killing a Baby mob.")
	public double babyMultiplier = 1.2;

	// #####################################################################################
	// Killstreaks Multiplier
	// #####################################################################################
	@ConfigField(name = "level1", category = "multiplier.killstreak")
	public int killstreakLevel1 = 5;
	@ConfigField(name = "level1_multiplier", category = "multiplier.killstreak")
	public double killstreakLevel1Mult = 1.5;
	@ConfigField(name = "level2", category = "multiplier.killstreak")
	public int killstreakLevel2 = 10;
	@ConfigField(name = "level2_multiplier", category = "multiplier.killstreak")
	public double killstreakLevel2Mult = 2;
	@ConfigField(name = "level3", category = "multiplier.killstreak")
	public int killstreakLevel3 = 20;
	@ConfigField(name = "level3_multiplier", category = "multiplier.killstreak")
	public double killstreakLevel3Mult = 3;
	@ConfigField(name = "level4", category = "multiplier.killstreak")
	public int killstreakLevel4 = 40;
	@ConfigField(name = "level4_multiplier", category = "multiplier.killstreak")
	public double killstreakLevel4Mult = 4;

	// #####################################################################################
	// Multiplier by rank / permission
	// #####################################################################################
	@ConfigField(name = "rank_multiplier", category = "multiplier.rank", comment = "Ranks")
	public HashMap<String, String> rankMultiplier = new HashMap<String, String>();
	{
		rankMultiplier.put("mobhunting.multiplier.guest", "0.9");
		rankMultiplier.put("mobhunting.multiplier.guardian", "1.02");
		rankMultiplier.put("mobhunting.multiplier.staff", "1.05");
		rankMultiplier.put("mobhunting.multiplier.hasVoted", "2");
		rankMultiplier.put("mobhunting.multiplier.donator", "3");
	}

	// #####################################################################################
	// Multiplier pr World Difficulty
	// #####################################################################################
	@ConfigField(name = "world_difficulty_multiplier", category = "multiplier.difficulty", comment = "This is the reward multiplier for the WorldDifficulty. Note that extrahard is "
			+ "\nused for worlds where the plugin ExtraHardMode is enabled.")
	public HashMap<String, String> difficultyMultiplier = new HashMap<String, String>();
	{
		difficultyMultiplier.put("peaceful", "0.5");
		difficultyMultiplier.put("easy", "0.75");
		difficultyMultiplier.put("normal", "1");
		difficultyMultiplier.put("hard", "2");
		difficultyMultiplier.put("extrahard", "2.5");
	}

	// #####################################################################################
	// Flying Penalty Multiplier
	// #####################################################################################
	@ConfigField(name = "flyingPenalty", category = "multiplier.penalty", comment = "If a player flies at any point in a fight, this penalty will be applied")
	public double penaltyFlying = 0.2;

	@ConfigField(name = "mob_rob_from_player", category = "multiplier.penalty", comment = "This is the penalty if the player gets killed by a mob."
			+ "\nSet mob_rob_from_player=10 to let the mob steal 10 dollars"
			+ "\n or 10% to let the mob steal 10% of the players balance."
			+ "\nSet mob_rob_from_player=0 to disable this")
	public String mobKillsPlayerPenalty = "0%";

	// #####################################################################################
	// Bounty Settings
	// #####################################################################################
	@ConfigField(name = "enable_player_bounties", category = "bounties", comment = "Set to true if you want to disable players to be able to put bounties on each other.")
	public boolean enablePlayerBounties = true;
	@ConfigField(name = "bounty_return_pct", category = "bounties", comment = "Here you set how much of a bound the bounty owner get back if "
			+ "\nhe drop the bounty on another player")
	public int bountyReturnPct = 50;
	@ConfigField(name = "bounty_duration", category = "bounties", comment = "Here you set the number of days the Bounty is collectable."
			+ "\nAfter the number of days the Bounty will be removed automatically")
	public int bountyDaysToLive = 30;
	@ConfigField(name = "enable_random_bounty", category = "bounties", comment = "Set enable_random_bounty=false to disable random bounties")
	public boolean enableRandomBounty = true;
	@ConfigField(name = "time_between_random_bounties", category = "bounties", comment = "Time between Random Bounty is created in minutes")
	public int timeBetweenRandomBounties = 60;
	@ConfigField(name = "minimum_number_of_online_players", category = "bounties", comment = "Minimum number of players before the server starts to make random bounties")
	public int minimumNumberOfOnlinePlayers = 5;
	@ConfigField(name = "chance_to_create_a_random_bounty", category = "bounties", comment = "Chance that a bounty is created on a player after the minimum time. Must be a number between 0 and 1. (0 = never, 0.5 = 50% 1 = always)")
	public double chanceToCreateBounty = 0.5;
	@ConfigField(name = "random_bounty_prize", category = "bounties", comment = "Random Bounty. Can be a number 100 or a range 100:200")
	public String randomBounty = "50:100";

	// #####################################################################################
	// Integration
	// #####################################################################################
	// #####################################################################################
	// Disguises
	// #####################################################################################
	@ConfigField(name = "enable_integration_i_disguise", category = "plugins.disguises", comment = "Enable/disable integration with iDisguise")
	public boolean enableIntegrationIDisguise = true;

	@ConfigField(name = "enable_integration_disguisecraft", category = "plugins.disguises", comment = "Enable/disable integration with DisguiseCcraft")
	public boolean enableIntegrationDisguiseCraft = true;

	@ConfigField(name = "enable_integration_libsdisguises", category = "plugins.disguises", comment = "Enable/disable integration with LibsDisguises")
	public boolean enableIntegrationLibsDisguises = true;

	@ConfigField(name = "remove_disguise_when_attacking", category = "plugins.disguises", comment = "Set pvpAllowed=false to disable rewards on killing other players.")
	public boolean removeDisguiseWhenAttacking = true;

	@ConfigField(name = "remove_disguise_when_attacked", category = "plugins.disguises", comment = "Set pvpAllowed=false to disable rewards on killing other players.")
	public boolean removeDisguiseWhenAttacked = true;

	@ConfigField(name = "undercover_multiplier", category = "plugins.disguises", comment = "Bonus multiplier for killing while disgused."
			+ "\nCan be both positive an negative = reward or penalty"
			+ "\nand over and under 1 = raise or lower the reward. ")
	public double undercoverMultiplier = 0.95;
	@ConfigField(name = "cover_blown_multiplier", category = "plugins.disguises", comment = "Bonus multiplier for killing a disgused player."
			+ "\nCan be both positive an negative = reward or penalty"
			+ "\nand over and under 1 = raise or lower the reward. ")
	public double coverBlownMultiplier = 1.2;

	// #####################################################################################
	// Citizens / MasterMobHunter Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_citizens", category = "plugins.citizens", comment = "Enable/disable integration with Citizens2")
	public boolean enableIntegrationCitizens = true;
	@ConfigField(name = "masterMobHunter_check_every", category = "plugins.citizens", comment = "Set the number of seconds between each check. Recommended setting is"
			+ "\nmasterMobHunter_check_every: 300 ~ to update all MasterMobHunters every 5th minute."
			+ "\nBe careful not to lower this number too much. It can cause lag and server crashes "
			+ "\nbecause of database lockings.")
	public int masterMobHuntercheckEvery = 300;

	// #####################################################################################
	// Stacked Mobs Settings
	// #####################################################################################
	@ConfigField(name = "mobstacker.enable_integration_mobstacker", category = "plugins.stackedmobs", comment = "Enable/disable integration with MobStacker."
			+ "\nhttps://www.spigotmc.org/resources/mobstacker.15596/")
	public boolean enableIntegrationMobStacker = true;

	@ConfigField(name = "stackmob.enable_integration_stackmob", category = "plugins.stackedmobs", comment = "Enable/disable integration with StackMob."
			+ "\nhttps://www.spigotmc.org/resources/stackmob.29999/")
	public boolean enableIntegrationStackMob = true;

	@ConfigField(name = "get_reward_from_stacked_mobs", category = "plugins.stackedmobs", comment = "Set to true if you want stacked mobs to pay a reward.")
	public boolean getRewardFromStackedMobs = true;

	// #####################################################################################
	// CustomMobs Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_custommobs", category = "plugins.custommobs", comment = "Enable/disable integration with CustomMobs"
			+ "\nhttps://dev.bukkit.org/bukkit_plugins/custom_mobs/")
	public boolean enableIntegrationCustomMobs = true;

	@ConfigField(name = "allow_custom_mobspawners_and_eggs", category = "plugins.custommobs", comment = "Can the players earn money on mobs spawned from CustomMobs Spawners and eggs?")
	public boolean allowCustomMobsSpawners = false;

	// #####################################################################################
	// InfernalMobs Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_infernalmobs", category = "plugins.infernalmobs", comment = "Enable/disable integration with InfernalMobs")
	public boolean enableIntegrationInfernalMobs = true;

	@ConfigField(name = "multiplier_per_level", category = "plugins.infernalmobs", comment = "For InfernalMobs mob prize is calculated by the minecraft reward x multiplier_per_level^Infernal_Level"
			+ "\nEx.If multiplier=1.2 and level is 3 normal reward will be multiplied with 1.2*1.2*1.2=1,728")
	public double multiplierPerInfernalLevel = 1.25;

	// #####################################################################################
	// ConquestiaMobs / LorinthsRpgMobs Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_conquestiamobs", category = "plugins.levelmobs.conquestia", comment = "Enable/disable integration with ConquestiaMobs"
			+ "\nhttps://www.spigotmc.org/resources/conquesita_mobs.21307/")
	public boolean enableIntegrationConquestiaMobs = true;

	@ConfigField(name = "enable_integration_lorinthsrpgmobs", category = "plugins.levelmobs.lorinthsrpgmobs")
	public boolean enableIntegrationLorinthsRpgMobs = true;

	@ConfigField(name = "multiplier_per_level", category = "plugins.levelmobs", comment = "This is the multiplier per level mutiplied with the basic reward."
			+ "\nBecareful not to ruin the server economy by making the multiplier to big."
			+ "\nExample: If the reward is 10 and the multiplier is 1.05, the calculated" + "\nreward is:"
			+ "\nLevel 1: reward=10" + "\nLevel 2: reward=10*1.05=10.5" + "\nLevel 3: reward=10*1.05*1.05=11.03"
			+ "\nLevel 4: reward=10*1.05*1.05*1.05=11.58" + "\nLevel 5: reward=10*1.05*1.05*1.05*1.05=12.16"
			+ "\nLevel 6: reward=10*1.05*1.05*1.05*1.05*1.05=12.76"
			+ "\nLevel 7: reward=10*1.05*1.05*1.05*1.05*1.05*1.05=13.40"
			+ "\nLevel 8: reward=10*1.05*1.05*1.05*1.05*1.05*1.05*1.05=14.07"
			+ "\nLevel 9: reward=10*1.05*1.05*1.05*1.05*1.05*1.05*1.05*1.05=14.77"
			+ "\nLevel 10: reward=10*1.05*1.05*1.05*.....=15.51" + "\nLevel 20: reward=10*1.05*1.05*1.05*.....=25..27"
			+ "\nLevel 30: reward=10*1.05*1.05*1.05*.....=41.61" + "\nLevel 40: reward=10*1.05*1.05*1.05*.....=67.05"
			+ "\nLevel 50: reward=10*1.05*1.05*1.05*.....=109.21"
			+ "\nLevel 100: reward=10*1.05*1.05*1.05*.....=1252.39")
	public double mulitiplierPerLevel = 1.05;

	// #####################################################################################
	// Factions / FactionsUUID Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_factions", category = "plugins.factions", comment = "Enable/disable integration with Factions."
			+ "\nhttps://www.massivecraft.com/" + "\nhttps://www.spigotmc.org/resources/factions.1900/"
			+ "\nhttps://www.spigotmc.org/resources/factionsuuid.1035/")
	public boolean enableIntegrationFactions = true;

	@ConfigField(name = "factions_warzone_multiplier", category = "plugins.factions", comment = "This is the bonus when a player kills a mob or a player in a Factions WarZone.")
	public double factionWarZoneBonusMultiplier = 1.1;

	// #####################################################################################
	// Towny Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_towny", category = "plugins.towny", comment = "Enable/disable integration with Towny."
			+ "\nhttp://towny.palmergames.com/")
	public boolean enableIntegrationTowny = true;

	@ConfigField(name = "disable_rewards_in_home_town", category = "plugins.towny", comment = "Disable rewards when the player is in his hometown."
			+ "\nhttp://towny.palmergames.com/")
	public boolean disableRewardsInHomeTown = true;

	@ConfigField(name = "disable_naturally_drops_and_xp_in_home_town", category = "plugins.towny", comment = "Disable naturally drops and xp drops when the player kill mobs in his home town.")
	public boolean disableNaturallyRewardsInHomeTown = false;

	// #####################################################################################
	// Residence Settings
	// #####################################################################################
	@ConfigField(name = "enable_integration_residence", category = "plugins.residence", comment = "Enable/disable integration with Residence."
			+ "\nhttp://towny.palmergames.com/")
	public boolean enableIntegrationResidence = true;

	@ConfigField(name = "disable_rewards_in_home_town", category = "plugins.residence", comment = "Disable rewards when the player is protected against damage."
			+ "\nhttp://towny.palmergames.com/")
	public boolean disableRewardsInHomeResidence = true;

	@ConfigField(name = "disable_naturally_drops_and_xp_in_protected_residence", category = "plugins.residence", comment = "Disable naturally drops and xp drops when the player kill mobs in his home town.")
	public boolean disableNaturallyRewardsInProtectedResidence = false;

	// #####################################################################################
	// McMMO integration
	// #####################################################################################
	@ConfigField(name = "enable_integration_mcmmo", category = "plugins.mcmmo", comment = "Enable/disable the integration with McMMO."
			+ "\nhttps://www.spigotmc.org/resources/mcmmo.2445/")
	public boolean enableIntegrationMcMMO = true;

	@ConfigField(name = "enable_mcmmo_level_rewards", category = "plugins.mcmmo", comment = "Set 'enable_mcmmo_level_rewards: true' to let the players get Level as a MobHunting reward.")
	public boolean enableMcMMOLevelRewards = true;

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.bat")
	public String batMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.bat")
	public double batMcMMOSkillRewardChance = 0.025;

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.blacksmith")
	public String blacksmithMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.blacksmith")
	public double blacksmithMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.blaze")
	public String blazeMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.blaze")
	public double blazeMcMMOSkillRewardChance = 0.05;
	// Hostile, normal challenge

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.bonusmob")
	public String bonusMobMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.bonusmob")
	public double bonusMobMcMMOSkillRewardChance = 0.05;
	// No opinion yet, I'm not quite sure what a bonus mob is

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.butcher")
	public String butcherMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.butcher")
	public double butcherMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.cartographer")
	public String cartographerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.cartographer")
	public double cartographerMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.cave_spider")
	public String caveSpiderMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.cave_spider")
	public double caveSpiderMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.chicken")
	public String chickenMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.chicken")
	public double chickenMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.clownfish")
	public String clownfishMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.clownfish")
	public double clownfishMcMMOSkillRewardChance = 0.075;
	// Fishing Hard

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.cow")
	public String cowMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.cow")
	public double cowMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.creeper")
	public String creeperMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.creeper")
	public double creeperMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.donkey")
	public String donkeyMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.donkey")
	public double donkeyMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.elder_guardian")
	public String elderGuardianMcMMOSkillRewardAmount = "1:2";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.elder_guardian")
	public double elderGuardianMcMMOSkillRewardChance = 0.1;
	// Hostile mob, challenging

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.enderdragon")
	public String enderdragonMcMMOSkillRewardAmount = "5";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.enderdragon")
	public double enderdragonMcMMOSkillRewardChance = 0.33;
	// Hostile mob, hard

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.enderman")
	public String endermanMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.enderman")
	public double endermanMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.endermite")
	public String endermiteMcMMOSkillRewardAmount = "1:2";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.endermite")
	public double endermiteMcMMOSkillRewardChance = 0.2;
	// Hostile mob, easy (but rare)

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.evoker")
	public String evokerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.evoker")
	public double evokerMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.farmer")
	public String farmerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.farmer")
	public double farmerMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.ghast")
	public String ghastMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.ghast")
	public double ghastMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.giant")
	public String giantMcMMOSkillRewardAmount = "1:2";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.giant")
	public double giantMcMMOSkillRewardChance = 0.1;
	// Not really a part of the standard game, but I imagine they are Hostile,
	// Challenging

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.guardian")
	public String guardianMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.guardian")
	public double guardianMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal (because of the terrain and beam attack)

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.horse")
	public String horseMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.horse")
	public double horseMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.husk")
	public String huskMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.husk")
	public double huskMcMMOSkillRewardChance = 0.04;
	// Passive mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.illusioner")
	public String illusionerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.illusioner")
	public double illusionerMcMMOSkillRewardChance = 0.05;
	// Hostile mob, looks normal on Youtube videos (haven't tried 1.12)

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.iron_golem")
	public String ironGolemMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmov.iron_golem")
	public double ironGolemMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.killer_rabbit")
	public String killerRabbitMcMMOSkillRewardAmount = "5";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.killer_rabbit")
	public double killerRabbitMcMMOSkillRewardChance = 1.0;
	// Hostile mob, easy (but extremely rare)

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.llama")
	public String llamaMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.llama")
	public double llamaMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.librarian")
	public String librarianMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.librarian")
	public double librarianMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.magma_cube")
	public String magmaCubeMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.magma_cube")
	public double magmaCubeMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.mule")
	public String muleMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.mule")
	public double muleMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.mushroom_cow")
	public String mushroomCowMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.mushroom_cow.")
	public double mushroomCowMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.nitwit")
	public String nitwitMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.nitwit")
	public double nitwitMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.ocelot")
	public String ocelotMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.ocelot")
	public double ocelotMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.parrot")
	public String parrotMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.parrot")
	public double parrotMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.pig")
	public String pigMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.pig")
	public double pigMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.polar_bear")
	public String polarBearMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.polar_bear")
	public double polarBearMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.priest")
	public String priestMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.priest")
	public double priestMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.pufferfish")
	public String pufferfishMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.pufferfish")
	public double pufferfishMcMMOSkillRewardChance = 0.06;
	// Fishing Hard

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.pvpplayer")
	public String pvpPlayerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.pvpplayer")
	public double pvpPlayerMcMMOSkillRewardChance = 0.025;
	// Easy to abuse

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.rabbit")
	public String rabbitMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.rabbit")
	public double rabbitMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.raw_fish")
	public String rawfishMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.raw_fish")
	public double rawfishMcMMOSkillRewardChance = 0.05;
	// Fishing Easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.raw_salmon")
	public String rawsalmonMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.raw_salmon")
	public double rawsalmonMcMMOSkillRewardChance = 0.06;
	// Fishing normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.sheep")
	public String sheepMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.sheep")
	public double sheepMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.shulker")
	public String shulkerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.shulker")
	public double shulkerMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.silverfish")
	public String silverfishMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.silverfish")
	public double silverfishMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.skeleton")
	public String skeletonMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.skeleton")
	public double skeletonMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.skeletonhorse")
	public String skeletonHorseMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.skeletonhorse")
	public double skeletonHorseMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.slime_base")
	public String slimeMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.slime_base")
	public double slimeMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.snowman")
	public String snowmanMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.snowman")
	public double snowmanMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.spider")
	public String spiderMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.spider")
	public double spiderMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.squid")
	public String squidMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.squid")
	public double squidMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.stray")
	public String strayMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.stray")
	public double strayMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.vex")
	public String vexMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.vex")
	public double vexMcMMOSkillRewardChance = 0.04;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.villager")
	public String villagerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.villager")
	public double villagerMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.vindicator")
	public String vindicatorMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.vindicator")
	public double vindicatorMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.witch")
	public String witchMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.witch")
	public double witchMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.wither")
	public String witherMcMMOSkillRewardAmount = "5";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.wither")
	public double witherMcMMOSkillRewardChance = 0.33;
	// Hostile mob, hard (and rare considering the summoning requirements)

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.wither_skeleton")
	public String witherSkeletonMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.wither_skeleton")
	public double witherSkeletonMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.wolf")
	public String wolfMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.wolf")
	public double wolfMcMMOSkillRewardChance = 0.04;
	// Hostile mob (kind of, needs to be hit first), easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.zombie")
	public String zombieMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.zombie")
	public double zombieMcMMOSkillRewardChance = 0.4;
	// Hostile mob, easy

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.zombiehorse")
	public String zombieHorseMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.zombiehorse")
	public double zombieHorseMcMMOSkillRewardChance = 0.025;
	// Passive mob, risk free

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.zombie_pigman")
	public String zombiePigManMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.zombie_pigman")
	public double zombiePigManMcMMOSkillRewardChance = 0.05;
	// Hostile mob, normal

	@ConfigField(name = "skillreward_amount", category = "plugins.mcmmo.mobs.zombie_villager")
	public String zombieVillagerMcMMOSkillRewardAmount = "1";
	@ConfigField(name = "skillreward_chance", category = "plugins.mcmmo.mobs.zombie_villager")
	public double zombieVillagerMcMMOSkillRewardChance = 0.04;

	// #####################################################################################
	// CrackShot integration
	// #####################################################################################
	@ConfigField(name = "crackshot.enable_integration_crackshot", category = "plugins", comment = "Enable/disable integration with CrackShot."
			+ "\nhttps://dev.bukkit.org/projects/crackshot")
	public boolean enableIntegrationCrackShot = true;

	@ConfigField(name = "crackshot.crackshot_multiplier", category = "plugins", comment = "Multiplier used when a Crackshot weapon was used to kill a mob or a player")
	public double crackShot = 0.7;

	// #####################################################################################
	// MobArena integration
	// #####################################################################################
	@ConfigField(name = "mobarena.enable_integration_mobarena", category = "plugins", comment = "Enable/Disable integration with MobArena")
	public boolean enableIntegrationMobArena = true;

	@ConfigField(name = "mobarena.mobarena_get_rewards", category = "plugins", comment = "Set to true if you want the players to get rewards while playing MobArena.")
	public boolean mobarenaGetRewards = false;

	// #####################################################################################
	// Pvparena integration
	// #####################################################################################
	@ConfigField(name = "pvparena.enable_integration_pvparena", category = "plugins", comment = "Enable/Disable integration with PvpArena")
	public boolean enableIntegrationPvpArena = true;

	@ConfigField(name = "pvparena.pvparena_get_rewards", category = "plugins", comment = "Set to true if you want the players to get rewards while playing pvpArena.")
	public boolean pvparenaGetRewards = false;

	// #####################################################################################
	// Plugin integration
	// #####################################################################################
	@ConfigField(name = "mythicmobs.enable_integration_mythicmobs", category = "plugins", comment = "Enable/Disable integration with MythicMobs")
	public boolean enableIntegrationMythicmobs = true;

	@ConfigField(name = "mypet.enable_integration_mypet", category = "plugins", comment = "Enable/Disable integration with MyPet")
	public boolean enableIntegrationMyPet = true;

	@ConfigField(name = "minigames.enable_integration_minigames", category = "plugins", comment = "Enable/Disable integration with MiniGames")
	public boolean enableIntegrationMinigames = true;

	@ConfigField(name = "minigameslib.enable_integration_minigameslib", category = "plugins", comment = "Enable/Disable integration with MiniGamesLib"
			+ "\nhttps://www.spigotmc.org/resources/minigameslib.23844/")
	public boolean enableIntegrationMinigamesLib = true;

	@ConfigField(name = "worldguard.enable_integration_worldguard", category = "plugins", comment = "Enable/Disable integration with WorldGuard")
	public boolean enableIntegrationWorldGuard = true;

	@ConfigField(name = "essentials.enable_integration_essentials", category = "plugins", comment = "Enable/Disable integration with Essentials"
			+ "\nhttp://dev.bukkit.org/bukkit_plugins/essentialsx/")
	public boolean enableIntegrationEssentials = true;

	@ConfigField(name = "battlearena.enable_integration_battlearena", category = "plugins", comment = "Enable/Disable integration with BattleArena")
	public boolean enableIntegrationBattleArena = true;

	@ConfigField(name = "bossbarapi.enable_integration_bossbarapi", category = "plugins", comment = "Enable/Disable integration with BossBarAPI. If you want messages in player chat you can set this to true.")
	public boolean enableIntegrationBossBarAPI = true;

	@ConfigField(name = "barapi.enable_integration_barapi", category = "plugins", comment = "Enable/Disable integration with BarAPI. If you want messages in player chat you can set this to true."
			+ "\nhttps://dev.bukkit.org/projects/bar_api")
	public boolean enableIntegrationBarAPI = true;

	@ConfigField(name = "titleapi.enable_integration_titleapi", category = "plugins", comment = "Enable/Disable integration with TitleAPI")
	public boolean enableIntegrationTitleAPI = true;

	@ConfigField(name = "vanishnopacket.enable_integration_vanishnopacket", category = "plugins", comment = "Enable/Disable integration with VanishNoPacket")
	public boolean enableIntegrationVanishNoPacket = true;

	@ConfigField(name = "titlemanager.enable_integration_titlemanager", category = "plugins", comment = "Enable/Disable integration with TitleManger. If you want messages in player chat you can set this to true."
			+ "\nhttps://www.spigotmc.org/resources/titlemanager.1049/")
	public boolean enableIntegrationTitleManager = true;

	@ConfigField(name = "actionbar.enable_integration_actionbar", category = "plugins", comment = "Enable/Disable integration with Actionbar. If you want messages in player chat you can set this to true.")
	public boolean enableIntegrationActionbar = true;

	@ConfigField(name = "actionbarapi.enable_integration_actionbarapi", category = "plugins", comment = "Enable/Disable integration with ActionBarAPI. If you want messages in player chat you can set this to true."
			+ "\nhttps://www.spigotmc.org/resources/actionbarapi_1_8_1_9_1_10.1315/")
	public boolean enableIntegrationActionBarAPI = true;

	@ConfigField(name = "actionannouncer.enable_integration_actionannouncer", category = "plugins", comment = "Enable/Disable integration with ActionAnnouncer. If you want messages in player chat you can set this to true."
			+ "\nhttps://www.spigotmc.org/resources/actionannouncer.1320/")
	public boolean enableIntegrationActionAnnouncer = true;

	@ConfigField(name = "gringotts.enable_integration_gringotts", category = "plugins", comment = "Enable/Disable integration with Gringotts Economy."
			+ "\nhttp://dev.bukkit.org/bukkit_plugins/gringotts/")
	public boolean enableIntegrationGringotts = true;

	@ConfigField(name = "tardis_weepingangles.enable_integration_tardis_weeping_angels", category = "plugins", comment = "Enable/Disable integration with TARDIS Weeping Angels."
			+ "\nhttp://dev.bukkit.org/bukkit_plugins/tardisweepingangels/")
	public boolean enableIntegrationTARDISWeepingAngels = true;

	@ConfigField(name = "protocollib.enable_integration_protocollib", category = "plugins", comment = "Enable/Disable integration with ProtocolLib."
			+ "\nhttps://www.spigotmc.org/resources/protocollib.1997/")
	public boolean enableIntegrationProtocolLib = true;

	@ConfigField(name = "mysterious_halloween.enable_integration_mysterious_halloween", category = "plugins", comment = "Enable/Disable integration with MysteriousHalloween."
			+ "\nhttps://www.spigotmc.org/resources/mysterioushalloween.13059/")
	public boolean enableIntegrationMysteriousHalloween = true;

	@ConfigField(name = "smartgiants.enable_integration_smartgiants", category = "plugins", comment = "Enable/Disable integration with SmartGiants."
			+ "\nhttps://www.spigotmc.org/threads/smartgiants.55208/")
	public boolean enableIntegrationSmartGiants = true;

	@ConfigField(name = "placeholderapi.enable_integration_placeholderapi", category = "plugins", comment = "Enable/Disable integration with PlaceholderAPI."
			+ "\nhttps://www.spigotmc.org/resources/placeholderapi.6245/")
	public boolean enableIntegrationPlaceholderAPI = true;

	@ConfigField(name = "bossshop.enable_integration_bossshop", category = "plugins", comment = "Enable/Disable integration with BossShop."
			+ "\nhttps://www.spigotmc.org/resources/bossshop_powerful_and_playerfriendly_chest_gui_shop_menu_plugin.222/")
	public boolean enableIntegrationBossShop = true;

	@ConfigField(name = "extra_hard_mode.enable_integration_extra_hard_mode", category = "plugins", comment = "Enable/Disable integration with ExtraHardmode."
			+ "\nhttps://www.spigotmc.org/resources/extra_hard_mode.19673/")
	public boolean enableIntegrationExtraHardMode = true;

	@ConfigField(name = "herobrine.enable_integration_herobrine", category = "plugins", comment = "Enable/Disable integration with Herobrine."
			+ "\nhttps://www.theprogrammersworld.net/Herobrine/")
	public boolean enableIntegrationHerobrine = true;

	@ConfigField(name = "holograms.enable_integration_holograms", category = "plugins", comment = "Enable/Disable integration with Holograms."
			+ "\nhttps://www.spigotmc.org/resources/holograms.4924/")
	public boolean enableIntegrationHolograms = true;

	@ConfigField(name = "holographic_displays.enable_integration_holographic_displays", category = "plugins", comment = "Enable/Disable integration with Holograms."
			+ "\nhttps://dev.bukkit.org/projects/holographic_displays")
	public boolean enableIntegrationHolographicDisplays = true;

	@ConfigField(name = "precious_stones.enable_integration_preciousstones", category = "plugins", comment = "Enable/Disable integration with PreciousStones."
			+ "\nhttps://www.spigotmc.org/resources/preciousstones.5270/")
	public boolean enableIntegrationPreciousStones = true;

	// #####################################################################################
	// DropMoneyOnGround settings
	// #####################################################################################
	@ConfigField(name = "drop_money_on_ground", category = "dropmoneyonground", comment = "When a player get a money reward for a kill, the money will go directly"
			+ "\ninto his pocket. If you set dropMoneyOnGround=true the reward will "
			+ "\ndropped on ground to be picked up by the player."
			+ "\nNegative rewards will always be taken from the player. ")
	public boolean dropMoneyOnGroup = true;

	@ConfigField(name = "drop_money_on_ground_itemtype", category = "dropmoneyonground", comment = "Here you can set the type of the ITEM to be dropped."
			+ "\nYou can choose between \"ITEM\",\"KILLED\",\"SKULL\",\"KILLER\". The default is ITEM."
			+ "\nThe value will be showed above the item." + "\nITEM: The reward is dropped as a normal Minecraft item."
			+ "\nKILLED: The reward is dropped as the head of the mob/player you killed."
			+ "\nSKULL: The reward is dropped as a SKULL with a custom texture. You can generate custom texture value"
			+ "\nand custom texture signature at http://mineskin.org"
			+ "\nKILLER: The reward is dropped as the killers head."
			+ "\n\nOBS: If the Gringotts plugin is installed and support not disabled, the droped item will be the Gringotts chosen item."
			+ "\nExamples:" + "\n\nBag of gold: (https://mineskin.org/6875)"
			+ "\n\ndrop_money_on_ground_skull_reward_name: 'Bag of gold'"
			+ "\ndrop_money_on_ground_skull_texture_value: 'eyJ0aW1lc3RhbXAiOjE0ODU5MTIwNjk3OTgsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM5NmNlMTNmZjYxNTVmZGYzMjM1ZDhkMjIxNzRjNWRlNGJmNTUxMmYxYWRlZGExYWZhM2ZjMjgxODBmM2Y3In19fQ=='"
			+ "\ndrop_money_on_ground_skull_texture_signature: 'm8u2ChI43ySVica7pcY0CsCuMCGgAdN7c9f/ZOxDZsPzJY8eiDrwxLIh6oPY1rvE1ja/rmftPSmdnbeHYrzLQ18QBzehFp8ZVegPsd9iNHc4FuD7nr1is2FD8M8AWAZOViiwlUKnfd8avb3SKfvFmhmVhQtE+atJYQrXhJwiqR4S+KTccA6pjIESM3AWlbCOmykg31ey7MQWB4YgtRp8NyFD3HNTLZ8alcEXBuG3t58wYBEME1UaOFah45tHuV1FW+iGBHHFWLu1UsAbg0Uw87Pp+KSTUGrhdwSc/55czILulI8IUnUfxmkaThRjd7g6VpH/w+9jLvm+7tOwfMQZlXp9104t9XMVnTAchzQr6mB3U6drCsGnuZycQzEgretQsUh3hweN7Jzz5knl6qc1n3Sn8t1yOvaIQLWG1f3l6irPdl28bwEd4Z7VDrGqYgXsd2GsOK/gCQ7rChNqbJ2p+jCja3F3ZohfmTYOU8W7DJ8Ne+xaofSuPnWODnZN9x+Y+3RE3nzH9tzP+NBMsV3YQXpvUD7Pepg7ScO+k9Fj3/F+KfBje0k6xfl+75s7kR3pNWQI5EVrO6iuky6dMuFPUBfNfq33fZV6Tqr/7o24aKpfA4WwJf91G9mC18z8NCgFR6iK4cPGmkTMvNtxUQ3MoB0LCOkRcbP0i7qxHupt8xE='"
			+ "\n\nBag of gold (alternative): (https://mineskin.org/3384)"
			+ "\n\ndrop_money_on_ground_skull_reward_name: 'Bag of gold'"
			+ "\ndrop_money_on_ground_skull_texture_value: 'eyJ0aW1lc3RhbXAiOjE0NzQzMzI0MzY1MDYsInByb2ZpbGVJZCI6IjNlMjZiMDk3MWFjZDRjNmQ5MzVjNmFkYjE1YjYyMDNhIiwicHJvZmlsZU5hbWUiOiJOYWhlbGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg2NzczZDc0Y2Y1MDhmZDc3Yzc4MmZmZDI5ZGYyZmU0N2ZiNzE0YjViMGQ3ZGU2N2Q1Mjg2OTMxZTJmMWRmMiJ9fX0='"
			+ "\ndrop_money_on_ground_skull_texture_signature: 'JdvJksowuxYQ0eqf56J+Dmczg7zvlw2DbIc58Q33kRt65uMUNn2iRCQsbNpztC1cAAgyYMOyFDiOUZQeIK03CSRoPLDtWp2u501YoGKqhjgrE0V0UDh3JetWKz4Ob0KmATtY+4R2vSoMjHFEFppM0Oq+8ZER12FAiVEMAzeseFN3Z9fWAMc/V10LoquGBpq6ExTfSCEEMDEGZopF1T8ZBKL0vf4DVendfz4v3yl7bRBzISZEAnF+ECTa9z36r8HRqS8+s0eO/AWYQcRaKIu9H+wSK5F/1v+rgifeSlMAnt1Na8m1b5tMfNuq6pXxWCq4nUGgYVTOLUinqs9ZcFz3Z6Mtx5YtymKk2M0mzxmTm9+AeOL4s3K/UrJYQlcmLBJSv4hd6EigJXoashzWNCHKmFDYCdEhh4FArq4G9vRZtoudcTeMsvi0VmXIgER8U5iSfoTtzXcGbf/GT0ECtgfeA40f5oCqyE4nXreudMmvlDCBr/KHbILQWeeH/jhtYqQ6OwJb3Ji2Bs9F5fQmICSqk7X4yKzexf8rdDhOG1z+/TCot7K8unPVuQx46sXPeP7t2hCiHOXMAnOMt8vuL3gQUURIEM6fMryjmlKsgvk8Jo0gawavRCIZQtA6vT0JRRnSAchzEOA7QP1iiVV3LnwX9Yqw7oMJ/+REV1hWesuzDOc='"
			+ "\n\nChest: (https://mineskin.org/3136)" + "\n\ndrop_money_on_ground_skull_reward_name: 'Treasure chest'"
			+ "\ndrop_money_on_ground_skull_texture_value: 'eyJ0aW1lc3RhbXAiOjE0NzI4Mzk3Nzk2ODMsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJJbnZlbnRpdmVHYW1lcyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY5NDcxMjQ1YmNhN2M0ZmUwNjQ0MGQ5YjRiOWY3NDIxN2VkNzM0M2FhZDU5YTc5MThiMWExZDYxZDhiYTZkYSJ9fX0='"
			+ "\ndrop_money_on_ground_skull_texture_signature: 'lVA2QIbvybpzhcXof5yWz/7nkHdhG/3MGO+1DyD1txdRCALV6BRwsDUBwIUg06MkLUpBkjmiOvFcCRgal/jDE/xkkJPyk2tb/w4NtQ5PiPiAe0oInVnuiSIVFIE4tnsCdvX0joll3uKwVu6XY3t1KEsqJATcPhA5hslVn1iOp/IfMziIfuCzzob04rScpwcw0mLNtbtbMVAl6LYR9gXVuOkAfXujuYq4lbI/iW0yuLxSAzr8i9QWBP2ftup4qQHwocQRTdUE6/G5G9LwJWXhhnqKWjgjfvL0y2FRFJkgN1cvuq7DvUDBVsePnRIHwU5YvBPMjcZe/KE8VPTSodsN84/+++5p95Puxe1DXMX822xR71IQsxM7eax7Ffrr/Tzxw2rSDh9ivGGlRAB85OHwp/ouUgWNSrT8inNMYImque9EuZku9p3OFet8iZsFhkMXANeNtTVL7LKV7/L/0YWwoeyBnw5QQqvGyWKw3dac5eDkRNCyCtdDIntM5vsd8FxnIFj36zxLWgmrJmOM9hg5PBM4gcDxxryBcug8jSe+W9XDU39OOJotXajj8dgSL8yUn+d7l4Qvat/vJbAE8lonMl7P0P9QBPzmcIUvlRMuHSpRZQYkoCbwc2Filahd/5INtm7I4Y28XYzzupdwLk3cavKfOloL5YrWNqaZr/+9Tbk='"
			+ "\n\nBirthday present: (https://mineskin.org/4743)"
			+ "\n\ndrop_money_on_ground_skull_reward_name: 'Birthday present'"
			+ "\ndrop_money_on_ground_skull_texture_value: 'eyJ0aW1lc3RhbXAiOjE0Nzk5MzEzNDMxMjgsInByb2ZpbGVJZCI6IjNlMjZiMDk3MWFjZDRjNmQ5MzVjNmFkYjE1YjYyMDNhIiwicHJvZmlsZU5hbWUiOiJOYWhlbGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NmNDRkZjIzMjBiNzYzMTI0N2FhZGY1OWMwZWNlOTdhNGJiNTdkZjI4YzFjZWU3OTM0ZjZhZTI4YWY4OTg5In19fQ=='"
			+ "\ndrop_money_on_ground_skull_texture_signature: 'k1xQ6E1NuxG1ZN7nlQqRJltYrJn44XHVhNA9pSEu2Pt2mkuixMxhIDj2Tg6o+JWlTyGfXtPVWLxygeGymmeSGaVcmDTaCALg7PL11ZfSzSWSxaIufNbj1EcSi264jg5FrAa/2/DnFsgu16wjlWiIGtjCzgx2QabY8YofoPKw6Y6Y5FHZJVXpT8Rsxs8ok6ZHtfm/ZyyTgvRSzh2mKmVyQIYJ1ZKxuqWhDQfbtBpu3dlEzMAEJo85Dvb7uIFYa7WFitjFJue/c9qpqAnazWFLrx33nYpjjeYhcfAvsaNQW3JVFEkyxzEgzOHbdsbiZcqTCwO+49whu175xOqT7XhouEubDT7A3H1jiSvQvkUZJv/GzUF4qFYHSfxhr6OWoBrRGwWmPdcrYx7fUWKo43CAqa5inaiTV4gU70BWrx5i3LhIJxpnspAyTXs8tZBxeoh8IizWD7uXkYYqh3j9cwuHoxfwZuMpOx9CPTC6R/YwJ1YK5OgJBY1+QhNw+NOilWT3jTok82elFvOLm3a5yLyVs+/UPmLD7rZsFm7/DD3VnRcpgjKRiyy2j9vYsYLyNE2BVLVJxBVk2yyy9u7L4VR6PO+8v2dh9DQl7vM2ORCxKPl2lt6woHWM2+eT1PXr16LtMtAOGYT8mlKFhp8Ou2+9fu4AqWkX7n3swU6XLiK5cJs='"
			+ "\n\nChoose between \"ITEM\",\"KILLED\",\"SKULL\",\"KILLER\"")
	public String dropMoneyOnGroundItemtype = "SKULL";

	@ConfigField(name = "drop_money_use_item_as_currency", category = "dropmoneyonground", comment = "Use the reward as a currency (bag of gold) which can be sold, bought, stored in a"
			+ "\nprotected chest or a protected area (a Bank?). Check the command /mh money sell."
			+ "\nIf false the bag of gold will be picked up as money, if true the bag of gold "
			+ "\nwill be picked up as an item. OBS: If you want to use the bags as an Economy "
			+ "\nreplacing Essentials/CraftConomy/... and more, then you have to install the"
			+ "\nBagOfGold plugin as well. (https://dev.bukkit.org/projects/bagofgold)")
	public boolean dropMoneyOnGroundUseAsCurrency = true;

	@ConfigField(name = "drop_money_command_alias", category = "dropmoneyonground", comment = "Here you can chance the command /mh money ... to /mh <alias> ..."
			+ "\nExample: gold,bag,silver,coin,???? ")
	public String dropMoneyOnGroundMoneyCommandAlias = "money";

	@ConfigField(name = "drop_money_on_ground_item", category = "dropmoneyonground", comment = "Here you can set which item should be used when you have "
			+"\nchosen drop_money_on_ground_itemtype: ITEM. "
			+ "\nUse Minecraft Item names like: " + "\nGOLD_NUGGET, DIAMOND, GOLD_INGOT, EMERALD, GOLDEN_APPLE ")
	public String dropMoneyOnGroundItem = "GOLD_INGOT";

	@ConfigField(name = "drop_money_on_ground_text_color", category = "dropmoneyonground", comment = "Here you can set of the color of the number above the dropped item. \nUse color names like WHITE, RED, BLUE, GOLD")
	public String dropMoneyOnGroundTextColor = "WHITE";

	@ConfigField(name = "drop_money_on_ground_skull_reward_name", category = "dropmoneyonground", comment = "This is the name of the reward")
	public String dropMoneyOnGroundSkullRewardName = "Bag of gold";

	@ConfigField(name = "drop_money_on_ground_skull_reward_name_plural", category = "dropmoneyonground", comment = "This is the name of the reward in plural")
	public String dropMoneyOnGroundSkullRewardNamePlural = "Bag of gold";

	@ConfigField(name = "drop_money_on_ground_skull_texture_value", category = "dropmoneyonground", comment = "This is the Custom Texture Value generated at http://mineskin.org")
	public String dropMoneyOnGroundSkullTextureValue = "eyJ0aW1lc3RhbXAiOjE0ODU5MTIwNjk3OTgsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzM5NmNlMTNmZjYxNTVmZGYzMjM1ZDhkMjIxNzRjNWRlNGJmNTUxMmYxYWRlZGExYWZhM2ZjMjgxODBmM2Y3In19fQ==";

	@ConfigField(name = "drop_money_on_ground_skull_texture_signature", category = "dropmoneyonground", comment = "This is the Custom Texture Signature generated at http://mineskin.org")
	public String dropMoneyOnGroundSkullTextureSignature = "m8u2ChI43ySVica7pcY0CsCuMCGgAdN7c9f/ZOxDZsPzJY8eiDrwxLIh6oPY1rvE1ja/rmftPSmdnbeHYrzLQ18QBzehFp8ZVegPsd9iNHc4FuD7nr1is2FD8M8AWAZOViiwlUKnfd8avb3SKfvFmhmVhQtE+atJYQrXhJwiqR4S+KTccA6pjIESM3AWlbCOmykg31ey7MQWB4YgtRp8NyFD3HNTLZ8alcEXBuG3t58wYBEME1UaOFah45tHuV1FW+iGBHHFWLu1UsAbg0Uw87Pp+KSTUGrhdwSc/55czILulI8IUnUfxmkaThRjd7g6VpH/w+9jLvm+7tOwfMQZlXp9104t9XMVnTAchzQr6mB3U6drCsGnuZycQzEgretQsUh3hweN7Jzz5knl6qc1n3Sn8t1yOvaIQLWG1f3l6irPdl28bwEd4Z7VDrGqYgXsd2GsOK/gCQ7rChNqbJ2p+jCja3F3ZohfmTYOU8W7DJ8Ne+xaofSuPnWODnZN9x+Y+3RE3nzH9tzP+NBMsV3YQXpvUD7Pepg7ScO+k9Fj3/F+KfBje0k6xfl+75s7kR3pNWQI5EVrO6iuky6dMuFPUBfNfq33fZV6Tqr/7o24aKpfA4WwJf91G9mC18z8NCgFR6iK4cPGmkTMvNtxUQ3MoB0LCOkRcbP0i7qxHupt8xE=";

	@ConfigField(name = "deny_hoppers_to_pickup_money_on_ground", category = "dropmoneyonground", comment = "Dark room mobspawners usually collect items in a HOPPER. This is allowed by default."
			+ "\nIf you want to deny HOPPERS to collect MobHunting Money rewards "
			+ "\nset \"deny_hoppers_to_pickup_money_on_ground\"=false")
	public boolean denyHoppersToPickUpMoney = true;

	// #####################################################################################
	// Database
	// #####################################################################################
	@ConfigField(name = "type", category = "database", comment = "Type of database to use. Valid values are: sqlite, mysql")
	public String databaseType = "sqlite";

	@ConfigField(name = "database_name", category = "database")
	public String databaseName = "mobhunting";

	@ConfigField(name = "username", category = "database.mysql")
	public String databaseUsername = "user";

	@ConfigField(name = "password", category = "database.mysql")
	public String databasePassword = "password";

	@ConfigField(name = "host", category = "database.mysql")
	public String databaseHost = "localhost:3306";

	@ConfigField(name = "database_version", category = "database", comment = "This is the database layout version. Mostly for internal use and you should not need"
			+ "\nto chance this value. In case you decide to delete your database and let it recreate"
			+ "\nor if you chance database type sqlite/mysql you should set this value to 0 again.")
	public int databaseVersion = 0;

	// #####################################################################################
	// Update Settings
	// #####################################################################################
	@ConfigField(name = "update_check", category = "updates", comment = "Check if there is a new version of the plugin available.")
	public boolean updateCheck = true;

	@ConfigField(name = "check_every", category = "updates", comment = "Set the number of seconds between each check. Recommended setting is"
			+ "\ncheck_every: 7200 ~ to check every second hour.")
	public int checkEvery = 7200;

	@ConfigField(name = "autoupdate", category = "updates", comment = "Set 'autoupdate: true' if you want new updates downloaded and installed."
			+ "\nYou will still have to reboot the server manually.")
	public boolean autoupdate = false;

	// #####################################################################################
	// Generel settings
	// #####################################################################################
	@ConfigField(name = "disabled_in_worlds", category = "general", comment = "Put the names of the worlds here that you do not wish for mobhunting to be enabled in.")
	public String[] disabledInWorlds = { "worldname", "worldname2" };

	@ConfigField(name = "language", category = "general", comment = "The language (file) to use. You can put the name of the language file as the language code "
			+ "\n(eg. en_US, de_DE, fr_FR, ect.) or you can specify the name of a custom file without the .lang\nPlease check the lang/ folder for a list of all available translations.")
	public String language = "en_US";

	@ConfigField(name = "disable_money_rewards_from_mobspawners_and_eggs", category = "general", comment = "Can the players earn money on mobs spawned from mobspawners, eggs and from eggs from Dispensers?"
			+ "\nIf you disable this you are still able to get rewards from specific Spawners, if you white list the area "
			+ "\nusing '/mh whitelistarea'.")
	public boolean disableMoneyRewardsFromMobSpawnersEggsAndDispensers = true;
	@ConfigField(name = "disable_naturally_dropped_items_from_mobspawners_and_eggs", category = "general", comment = "Let the players get the naturally dropped items from mobs spawned from mobspawners, eggs and from eggs from Dispensers ?")
	public boolean disableNaturallyDroppedItemsFromMobSpawnersEggsAndDispensers = false;
	@ConfigField(name = "disable_naturally_dropped_xp_from_mobspawners_and_eggs", category = "general", comment = "Let the players get the naturally dropped XP from mobs spawned from mobspawners, eggs and from eggs from Dispensers ?")
	public boolean disableNaturallyDroppedXPFromMobSpawnersEggsAndDispensers = false;

	@ConfigField(name = "disable_mobhunting_advancements", category = "general", comment = "As of V 5.0.0 MobHunting utilizises the Advancement system (L key) to to show which"
			+ "\nAchievements the players has made. This is still BETA feature and it is only Supported"
			+"\non Spigot Servers and if you have any problems, you can set 'disable_mobhunting_advancements: true "
			+"\nand the reload the plugin.")
	public boolean disableMobHuntingAdvancements = false;

	@ConfigField(name = "use_actionbar_for_broadcasts", category = "general", comment = "Broadcast messages will be send in the ActionBar if MobHunting finds a supported ActionBar plugin.")
	public boolean useActionBarforBroadcasts = true;

	@ConfigField(name = "broadcast_achievement", category = "general", comment = "Should achievements be broadcasted?")
	public boolean broadcastAchievement = true;

	@ConfigField(name = "broadcast_first_achievement", category = "general", comment = "Should the hunt begins achievement be broadcasted?")
	public boolean broadcastFirstAchievement = true;

	@ConfigField(name = "save_period", category = "general", comment = "Time between saves in ticks (20 ticks ~ 1 sec) This number must be higher that 1200 ticks = 2 minutes,"
			+ "\nbut I recommend to save every 5th minute = 6000 ticks")
	public int savePeriod = 6000;

	@ConfigField(name = "leaderboard_update_period", category = "general", comment = "Time between leaderboard updates in ticks (20 ticks ~ 1 sec) This number must be higher that 1200 ticks = 2 minutes,"
			+ "\nbut I recommend to update leaderboards max every 5 min = 6000 ticks")
	public int leaderboardUpdatePeriod = 6000;

	@ConfigField(name = "kill_timeout", category = "general", comment = "Time in seconds after attacking a mob that can be counted as a kill")
	public int killTimeout = 4;

	@ConfigField(name = "debug", category = "general", comment = "If kills are not being registered in mob hunting. Enable this to see why they arent")
	public boolean killDebug = false;

	@ConfigField(name = "reward_rounding", category = "general", comment = "Rounding of rewards when you uses a range or %. (ex creeperPrize=10:30) the reward."
			+ "\nAll numbers except 0 can be used. "
			+ "\nSet rounding_reward=1 if you want integers. IE. 10,11,12,13,14..."
			+ "\nSet rounding_reward=0.01 if you want 2 decimals 10.00, 10.01, 10.02... integers."
			+ "\nSet rounding_reward=5 if you want multipla of 5 IE. 10,15,20,25..."
			+ "\nSet rounding_reward=2 if you want multipla of 2 IE. 10,12,14,16...")
	public double rewardRounding = 0.01;

	@ConfigField(name = "minimum_reward", category = "general", comment = "This is the minimum reward which will which will be paid to the player 0.01 will be fine"
			+ "\nin most installation, but Gringott users who want very low rewards (like 0.001  for killing"
			+ "\na mob) will have to lower the minimum reward. Remember that some multipliers are less than 1"
			+ "\n and grinding detection and penalties. The minimum_reward should therefor be less than 10%"
			+ "\n of smallest reward. In the Gringotts example minimum_reward should be 0.0001 or 0.00005.")
	public double minimumReward = 0.01;

	@ConfigField(name = "newplayer_learning_mode", category = "general", comment = "When a new playerjoins the server he will by default start"
			+ "\nin 'LEARNING MODE' and get extra information about when he get rewards and not,"
			+ "\nwhen killing Mobs. The player can disable this InGame by using the command '/mobhunt learn'")
	public boolean learningMode = false;

	@ConfigField(name = "use_gui_for_achievements", category = "general", comment = "When use_gui_for_achivements=true the status of players achievements will"
			+ "\nbe showed in a Inventory GUI.")
	public boolean useGuiForAchievements = true;

	@ConfigField(name = "use_gui_for_bounties", category = "general", comment = "When use_gui_for_bounties=true the open bounties and most wanted players will"
			+ "\nbe showed in a Inventory GUI.")
	public boolean useGuiForBounties = true;

	@ConfigField(name = "disable_natural_item_drops", category = "general", comment = "Disable natural drops when a mob is killed "
			+ "\n(because player is grinding or protected by Worldguard or in God mode or similar)"
			+ "\nIf you want the mobs to drops normal rewards set " + "\n\"disable_natural_item_drops\"=false")
	public boolean disableNaturalItemDrops = true;

	@ConfigField(name = "disable_natural_xp_drops", category = "general", comment = "Disable natural xp drops when a mob is killed"
			+ "\n(because player is grinding or protected by Worldguard or in God mode or similar)"
			+ "\nIf you want the mobs to drop normal XP set " + "\n\"disable_natural_xp_drops\"=false")
	public boolean disableNatualXPDrops = true;

	@ConfigField(name = "try_to_cancel_natural_drops_when_in_creative", category = "general", comment = "Try to cancel natural drops when a mob is killed the player is in creative mode."
			+ "\nIf you want the mobs to drops normal rewards set "
			+ "\n\"try_to_cancel_natural_drops_when_in_creative\"=false")
	public boolean tryToCancelNaturalDropsWhenInCreative = true;

	@ConfigField(name = "try_to_cancel_xp_drops_when_in_creative", category = "general", comment = "Try to cancel XP drops when a mob is killed while the player is in creative mode."
			+ "\nIf you want the mobs to drop normal XP set " + "\n\"try_to_cancel_xp_drops_when_in_creative\"=false")
	public boolean tryToCancelXPDropsWhenInCreative = true;

	@ConfigField(name = "config_version", category = "general", comment = "Do not chance this value unless you know what you are doing. It's meant for internal use.")
	public int configVersion = 1;

	@Override
	protected void onPostLoad() throws InvalidConfigurationException {
		plugin.getMessages().setLanguage(language + ".lang");
	}

	// #####################################################################################
	// Convert config data from old format to new format.
	// #####################################################################################
	public boolean convertConfig(ConfigManagerOld mConfig0) {
		plugin.getMessages().debug("blazePrize=%s old=%s", this.blazeMoney, mConfig0.blazePrize);
		this.blazeMoney = mConfig0.blazePrize;
		this.blazeCommands = convertCommands(mConfig0.blazeCmd, mConfig0.blazeCmdRunChance);
		this.blazeMessage = mConfig0.blazeCmdDesc;
		this.blazeMoneyChance = 1;
		this.blazeHeadPrize = mConfig0.blazeHeadPrize;
		this.blazeHeadDropChance = mConfig0.blazeCmdRunChance;
		this.blazeHeadMessage = "You got a skull";

		this.caveSpiderPrize = mConfig0.caveSpiderPrize;
		this.caveSpiderCmdNew = convertCommands(mConfig0.caveSpiderCmd, mConfig0.caveSpiderRunChance);
		this.caveSpiderCmdDesc = mConfig0.caveSpiderCmdDesc;
		this.caveSpiderRunChance = 1;
		this.caveSpiderHeadPrize = mConfig0.caveSpiderHeadPrize;
		this.caveSpiderHeadDropChance = mConfig0.caveSpiderRunChance;
		this.caveSpiderHeadMessage = "You got a skull";

		this.creeperPrize = mConfig0.creeperPrize;
		this.creeperCmdNew = convertCommands(mConfig0.creeperCmd, mConfig0.creeperCmdRunChance);
		this.creeperCmdDesc = mConfig0.creeperCmdDesc;
		this.creeperCmdRunChance = 1;
		this.creeperHeadPrize = mConfig0.creeperHeadPrize;
		this.creeperHeadDropChance = mConfig0.creeperCmdRunChance;
		this.creeperHeadMessage = "You got a skull";

		this.elderGuardianPrize = mConfig0.elderGuardianPrize;
		this.elderGuardianCmdNew = convertCommands(mConfig0.elderGuardianCmd, mConfig0.elderGuardianCmdRunChance);
		this.elderGuardianCmdDesc = mConfig0.elderGuardianCmdDesc;
		this.elderGuardianCmdRunChance = 1;
		this.elderGuardianHeadPrize = mConfig0.elderGuardianHeadPrize;
		this.elderGuardianHeadDropChance = mConfig0.elderGuardianCmdRunChance;
		this.elderGuardianHeadMessage = "You got a skull";

		this.endermanPrize = mConfig0.endermanPrize;
		this.endermanCmdNew = convertCommands(mConfig0.endermanCmd, mConfig0.endermanCmdRunChance);
		this.endermanCmdDesc = mConfig0.endermanCmdDesc;
		this.endermanCmdRunChance = 1;
		this.endermanHeadPrize = mConfig0.endermanHeadPrize;
		this.endermanHeadDropChance = mConfig0.endermanCmdRunChance;
		this.endermanHeadMessage = "You got a skull";

		this.endermitePrize = mConfig0.endermitePrize;
		this.endermiteCmdNew = convertCommands(mConfig0.endermiteCmd, mConfig0.endermiteCmdRunChance);
		this.endermiteCmdDesc = mConfig0.endermiteCmdDesc;
		this.endermiteCmdRunChance = 1;
		this.endermiteHeadPrize = mConfig0.endermiteHeadPrize;
		this.endermiteHeadDropChance = mConfig0.endermiteCmdRunChance;
		this.endermiteHeadMessage = "You got a skull";

		this.ghastPrize = mConfig0.ghastPrize;
		this.ghastCmdNew = convertCommands(mConfig0.ghastCmd, mConfig0.ghastCmdRunChance);
		this.ghastCmdDesc = mConfig0.ghastCmdDesc;
		this.ghastCmdRunChance = 1;
		this.ghastHeadPrize = mConfig0.ghastHeadPrize;
		this.ghastHeadDropChance = mConfig0.ghastCmdRunChance;
		this.ghastHeadMessage = "You got a skull";

		this.giantPrize = mConfig0.giantPrize;
		this.giantCmdNew = convertCommands(mConfig0.giantCmd, mConfig0.giantCmdRunChance);
		this.giantCmdDesc = mConfig0.giantCmdDesc;
		this.giantCmdRunChance = 1;
		this.giantHeadPrize = mConfig0.giantHeadPrize;
		this.giantHeadDropChance = mConfig0.giantCmdRunChance;
		this.giantHeadMessage = "You got a skull";

		this.ironGolemPrize = mConfig0.ironGolemPrize;
		this.ironGolemCmdNew = convertCommands(mConfig0.ironGolemCmd, mConfig0.ironGolemCmdRunChance);
		this.ironGolemCmdDesc = mConfig0.ironGolemCmdDesc;
		this.ironGolemCmdRunChance = 1;
		this.ironGolemHeadPrize = mConfig0.ironGolemHeadPrize;
		this.ironGolemHeadDropChance = mConfig0.ironGolemCmdRunChance;
		this.ironGolemHeadMessage = "You got a skull";

		this.guardianPrize = mConfig0.guardianPrize;
		this.guardianCmdNew = convertCommands(mConfig0.guardianCmd, mConfig0.guardianCmdRunChance);
		this.guardianCmdDesc = mConfig0.guardianCmdDesc;
		this.guardianCmdRunChance = 1;
		this.guardianHeadPrize = mConfig0.guardianHeadPrize;
		this.ironGolemHeadDropChance = mConfig0.ironGolemCmdRunChance;
		this.ironGolemHeadMessage = "You got a skull";

		this.huskPrize = mConfig0.huskPrize;
		this.huskCmdNew = convertCommands(mConfig0.huskCmd, mConfig0.huskCmdRunChance);
		this.huskCmdDesc = mConfig0.huskCmdDesc;
		this.huskCmdRunChance = 1;
		this.huskHeadPrize = mConfig0.huskHeadPrize;
		this.huskHeadDropChance = mConfig0.huskCmdRunChance;
		this.huskHeadMessage = "You got a skull";

		this.killerRabbitPrize = mConfig0.killerrabbitPrize;
		this.killerRabbitCmdNew = convertCommands(mConfig0.killerrabbitCmd, mConfig0.killerrabbitCmdRunChance);
		this.killerRabbitCmdDesc = mConfig0.killerrabbitCmdDesc;
		this.killerRabbitCmdRunChance = 1;
		this.killerRabbitHeadPrize = mConfig0.killerrabbitHeadPrize;
		this.killerRabbitHeadDropChance = mConfig0.killerrabbitCmdRunChance;
		this.killerRabbitHeadMessage = "You got a skull";

		this.magmaCubePrize = mConfig0.magmaCubePrize;
		this.magmaCubeCmdNew = convertCommands(mConfig0.magmaCubeCmd, mConfig0.magmaCubeCmdRunChance);
		this.magmaCubeCmdDesc = mConfig0.magmaCubeCmdDesc;
		this.magmaCubeCmdRunChance = 1;
		this.magmaCubeHeadPrize = mConfig0.magmaCubeHeadPrize;
		this.magmaCubeHeadDropChance = mConfig0.magmaCubeCmdRunChance;
		this.magmaCubeHeadMessage = "You got a skull";

		this.polarBearPrize = mConfig0.polarBearPrize;
		this.polarBearCmdNew = convertCommands(mConfig0.polarBearCmd, mConfig0.polarBearCmdRunChance);
		this.polarBearCmdDesc = mConfig0.polarBearCmdDesc;
		this.polarBearCmdRunChance = 1;
		this.polarBearHeadPrize = mConfig0.polarBearHeadPrize;
		this.polarBearHeadDropChance = mConfig0.polarBearCmdRunChance;
		this.polarBearHeadMessage = "You got a skull";

		this.slimePrize = mConfig0.slimeTinyPrize;
		this.slimeCmdNew = convertCommands(mConfig0.slimeCmd, mConfig0.slimeCmdRunChance);
		this.slimeCmdDesc = mConfig0.slimeCmdDesc;
		this.slimeCmdRunChance = 1;
		this.slimeHeadPrize = mConfig0.slimeHeadPrize;
		this.slimeHeadDropChance = mConfig0.slimeCmdRunChance;
		this.slimeHeadMessage = "You got a skull";

		this.strayPrize = mConfig0.strayPrize;
		this.strayCmdNew = convertCommands(mConfig0.strayCmd, mConfig0.strayCmdRunChance);
		this.strayCmdDesc = mConfig0.strayCmdDesc;
		this.strayCmdRunChance = 1;
		this.strayHeadPrize = mConfig0.strayHeadPrize;
		this.strayHeadDropChance = mConfig0.strayCmdRunChance;
		this.strayHeadMessage = "You got a skull";

		this.silverfishPrize = mConfig0.silverfishPrize;
		this.silverfishCmdNew = convertCommands(mConfig0.silverfishCmd, mConfig0.silverfishCmdRunChance);
		this.silverfishCmdDesc = mConfig0.silverfishCmdDesc;
		this.silverfishCmdRunChance = 1;
		this.silverfishHeadPrize = mConfig0.silverfishHeadPrize;
		this.silverfishHeadDropChance = mConfig0.silverfishCmdRunChance;
		this.silverfishHeadMessage = "You got a skull";

		this.skeletonPrize = mConfig0.skeletonPrize;
		this.skeletonCmdNew = convertCommands(mConfig0.skeletonCmd, mConfig0.skeletonCmdRunChance);
		this.skeletonCmdDesc = mConfig0.skeletonCmdDesc;
		this.skeletonCmdRunChance = 1;
		this.skeletonHeadPrize = mConfig0.skeletonHeadPrize;
		this.skeletonHeadDropChance = mConfig0.skeletonCmdRunChance;
		this.skeletonHeadMessage = "You got a skull";

		this.spiderMoney = mConfig0.spiderPrize;
		this.spiderCommands = convertCommands(mConfig0.spiderCmd, mConfig0.spiderCmdRunChance);
		this.spiderMessage = mConfig0.spiderCmdDesc;
		this.spiderMoneyChance = 1;
		this.spiderHeadPrize = mConfig0.spiderHeadPrize;
		this.spiderHeadDropChance = mConfig0.spiderCmdRunChance;
		this.spiderHeadMessage = "You got a skull";

		this.shulkerPrize = mConfig0.shulkerPrize;
		this.shulkerCmdNew = convertCommands(mConfig0.shulkerCmd, mConfig0.shulkerCmdRunChance);
		this.shulkerCmdDesc = mConfig0.shulkerCmdDesc;
		this.shulkerCmdRunChance = 1;
		this.shulkerHeadPrize = mConfig0.shulkerHeadPrize;
		this.shulkerHeadDropChance = mConfig0.shulkerCmdRunChance;
		this.shulkerHeadMessage = "You got a skull";

		this.zombiePrize = mConfig0.zombiePrize;
		this.zombieCmdNew = convertCommands(mConfig0.zombieCmd, mConfig0.zombieCmdRunChance);
		this.zombieCmdDesc = mConfig0.zombieCmdDesc;
		this.zombieCmdRunChance = 1;
		this.zombieHeadPrize = mConfig0.zombieHeadPrize;
		this.zombieHeadDropChance = mConfig0.zombieCmdRunChance;
		this.zombieHeadMessage = "You got a skull";

		this.zombiePigmanPrize = mConfig0.zombiePigmanPrize;
		this.zombiePigmanCmdNew = convertCommands(mConfig0.zombiePigmanCmd, mConfig0.zombiepigmanCmdRunChance);
		this.zombiePigmanCmdDesc = mConfig0.zombiePigmanCmdDesc;
		this.zombiePigmanCmdRunChance = 1;
		this.zombiePigmanHeadPrize = mConfig0.zombiePigmanHeadPrize;
		this.zombiePigmanHeadDropChance = mConfig0.zombiepigmanCmdRunChance;
		this.zombiePigmanHeadMessage = "You got a skull";

		this.vexPrize = mConfig0.vexPrize;
		this.vexCmdNew = convertCommands(mConfig0.vexCmd, mConfig0.vexCmdRunChance);
		this.vexCmdDesc = mConfig0.vexCmdDesc;
		this.vexCmdRunChance = 1;
		this.vexHeadPrize = mConfig0.vexHeadPrize;
		this.vexHeadDropChance = mConfig0.vexCmdRunChance;
		this.vexHeadMessage = "You got a skull";

		this.witchPrize = mConfig0.witchPrize;
		this.witchCmdNew = convertCommands(mConfig0.witchCmd, mConfig0.witchCmdRunChance);
		this.witchCmdDesc = mConfig0.witchCmdDesc;
		this.witchCmdRunChance = 1;
		this.witchHeadPrize = mConfig0.witchHeadPrize;
		this.witchHeadDropChance = mConfig0.witchCmdRunChance;
		this.witchHeadMessage = "You got a skull";

		this.witherSkeletonPrize = mConfig0.witherSkeletonPrize;
		this.witherSkeletonCmdNew = convertCommands(mConfig0.witherSkeletonCmd, mConfig0.witherSkeletonCmdRunChance);
		this.witherSkeletonCmdDesc = mConfig0.witherSkeletonCmdDesc;
		this.witherSkeletonCmdRunChance = 1;
		this.witherSkeletonHeadPrize = mConfig0.witherSkeletonHeadPrize;
		this.witherSkeletonHeadDropChance = mConfig0.witherSkeletonCmdRunChance;
		this.witherSkeletonHeadMessage = "You got a skull";

		this.witherPrize = mConfig0.witherPrize;
		this.witherCmdNew = convertCommands(mConfig0.witherCmd, mConfig0.witherCmdRunChance);
		this.witherCmdDesc = mConfig0.witherCmdDesc;
		this.witherCmdRunChance = 1;
		this.witherHeadPrize = mConfig0.witherHeadPrize;
		this.witherHeadDropChance = mConfig0.witherCmdRunChance;
		this.witherHeadMessage = "You got a skull";

		this.enderDragonPrize = mConfig0.enderdragonPrize;
		this.enderDragonCmdNew = convertCommands(mConfig0.enderdragonCmd, mConfig0.enderdragonCmdRunChance);
		this.enderDragonCmdDesc = mConfig0.enderdragonCmdDesc;
		this.enderDragonCmdRunChance = 1;
		this.enderDragonHeadPrize = mConfig0.enderdragonHeadPrize;
		this.enderDragonHeadDropChance = mConfig0.enderdragonCmdRunChance;
		this.enderDragonHeadMessage = "You got a skull";

		this.blacksmithPrize = mConfig0.blacksmithPrize;
		this.blacksmithCmdNew = convertCommands(mConfig0.blacksmithCmd, mConfig0.blacksmithCmdRunChance);
		this.blacksmithCmdDesc = mConfig0.blacksmithCmdDesc;
		this.blacksmithCmdRunChance = 1;
		this.blacksmithHeadPrize = mConfig0.blacksmithHeadPrize;
		this.blacksmithHeadDropChance = mConfig0.blacksmithCmdRunChance;
		this.blacksmithHeadMessage = "You got a skull";

		this.butcherPrize = mConfig0.butcherPrize;
		this.butcherCmdNew = convertCommands(mConfig0.butcherCmd, mConfig0.butcherCmdRunChance);
		this.butcherCmdDesc = mConfig0.butcherCmdDesc;
		this.butcherCmdRunChance = 1;
		this.butcherHeadPrize = mConfig0.butcherHeadPrize;
		this.butcherHeadDropChance = mConfig0.butcherCmdRunChance;
		this.butcherHeadMessage = "You got a skull";

		this.evokerPrize = mConfig0.evokerPrize;
		this.evokerCmdNew = convertCommands(mConfig0.evokerCmd, mConfig0.evokerCmdRunChance);
		this.evokerCmdDesc = mConfig0.evokerCmdDesc;
		this.evokerCmdRunChance = 1;
		this.evokerHeadPrize = mConfig0.evokerHeadPrize;
		this.evokerHeadDropChance = mConfig0.evokerCmdRunChance;
		this.evokerHeadMessage = "You got a skull";

		this.farmerPrize = mConfig0.farmerPrize;
		this.farmerCmdNew = convertCommands(mConfig0.farmerCmd, mConfig0.farmerCmdRunChance);
		this.farmerCmdDesc = mConfig0.farmerCmdDesc;
		this.farmerCmdRunChance = 1;
		this.farmerHeadPrize = mConfig0.farmerHeadPrize;
		this.farmerHeadDropChance = mConfig0.farmerCmdRunChance;
		this.farmerHeadMessage = "You got a skull";

		this.illusionerPrize = mConfig0.illusionerPrize;
		this.illusionerCmdNew = convertCommands(mConfig0.illusionerCmd, mConfig0.illusionerCmdRunChance);
		this.illusionerCmdDesc = mConfig0.illusionerCmdDesc;
		this.illusionerCmdRunChance = 1;
		this.illusionerHeadPrize = mConfig0.illusionerHeadPrize;
		this.illusionerHeadDropChance = mConfig0.illusionerCmdRunChance;
		this.illusionerHeadMessage = "You got a skull";

		this.librarianPrize = mConfig0.librarianPrize;
		this.librarianCmdNew = convertCommands(mConfig0.librarianCmd, mConfig0.librarianCmdRunChance);
		this.librarianCmdDesc = mConfig0.librarianCmdDesc;
		this.librarianCmdRunChance = 1;
		this.librarianHeadPrize = mConfig0.librarianHeadPrize;
		this.librarianHeadDropChance = mConfig0.librarianCmdRunChance;
		this.librarianHeadMessage = "You got a skull";

		this.nitwitPrize = mConfig0.nitwitPrize;
		this.nitwitCmdNew = convertCommands(mConfig0.nitwitCmd, mConfig0.nitwitCmdRunChance);
		this.nitwitCmdDesc = mConfig0.nitwitCmdDesc;
		this.nitwitCmdRunChance = 1;
		this.nitwitHeadPrize = mConfig0.nitwitHeadPrize;
		this.nitwitHeadDropChance = mConfig0.nitwitCmdRunChance;
		this.nitwitHeadMessage = "You got a skull";

		this.priestPrize = mConfig0.priestPrize;
		this.priestCmdNew = convertCommands(mConfig0.priestCmd, mConfig0.priestCmdRunChance);
		this.priestCmdDesc = mConfig0.priestCmdDesc;
		this.priestCmdRunChance = 1;
		this.priestHeadPrize = mConfig0.priestHeadPrize;
		this.priestHeadDropChance = mConfig0.priestCmdRunChance;
		this.priestHeadMessage = "You got a skull";

		this.villagerPrize = mConfig0.villagerPrize;
		this.villagerCmdNew = convertCommands(mConfig0.villagerCmd, mConfig0.villagerCmdRunChance);
		this.villagerCmdDesc = mConfig0.villagerCmdDesc;
		this.villagerCmdRunChance = 1;
		this.villagerHeadPrize = mConfig0.villagerHeadPrize;
		this.villagerHeadDropChance = mConfig0.villagerCmdRunChance;
		this.villagerHeadMessage = "You got a skull";

		this.vindicatorPrize = mConfig0.vindicatorPrize;
		this.vindicatorCmdNew = convertCommands(mConfig0.vindicatorCmd, mConfig0.vindicatorCmdRunChance);
		this.vindicatorCmdDesc = mConfig0.vindicatorCmdDesc;
		this.vindicatorCmdRunChance = 1;
		this.vindicatorHeadPrize = mConfig0.vindicatorHeadPrize;
		this.vindicatorHeadDropChance = mConfig0.vindicatorCmdRunChance;
		this.vindicatorHeadMessage = "You got a skull";

		this.zombieVillagerPrize = mConfig0.zombieVillagerPrize;
		this.zombieVillagerCmdNew = convertCommands(mConfig0.zombieVillagerCmd, mConfig0.zombieVillagerCmdRunChance);
		this.zombieVillagerCmdDesc = mConfig0.zombieVillagerCmdDesc;
		this.zombieVillagerCmdRunChance = 1;
		this.zombieVillagerHeadPrize = mConfig0.zombieVillagerHeadPrize;
		this.zombieVillagerHeadDropChance = mConfig0.zombieVillagerCmdRunChance;
		this.zombieVillagerHeadMessage = "You got a skull";

		this.batPrize = mConfig0.batPrize;
		this.batCmdNew = convertCommands(mConfig0.batCmd, mConfig0.batCmdRunChance);
		this.batCmdDesc = mConfig0.batCmdDesc;
		this.batCmdRunChance = 1;
		this.batHeadPrize = mConfig0.batHeadPrize;
		this.batHeadDropChance = mConfig0.batCmdRunChance;
		this.batHeadMessage = "You got a skull";

		this.chickenPrize = mConfig0.chickenPrize;
		this.chickenCmdNew = convertCommands(mConfig0.chickenCmd, mConfig0.chickenCmdRunChance);
		this.chickenCmdDesc = mConfig0.chickenCmdDesc;
		this.chickenCmdRunChance = 1;
		this.chickenHeadPrize = mConfig0.chickenHeadPrize;
		this.chickenHeadDropChance = mConfig0.chickenCmdRunChance;
		this.chickenHeadMessage = "You got a skull";

		this.cowPrize = mConfig0.cowPrize;
		this.cowCmdNew = convertCommands(mConfig0.cowCmd, mConfig0.cowCmdRunChance);
		this.cowCmdDesc = mConfig0.cowCmdDesc;
		this.cowCmdRunChance = 1;
		this.cowHeadPrize = mConfig0.cowHeadPrize;
		this.cowHeadDropChance = mConfig0.cowCmdRunChance;
		this.cowHeadMessage = "You got a skull";

		this.donkeyPrize = mConfig0.donkeyPrize;
		this.donkeyCmdNew = convertCommands(mConfig0.donkeyCmd, mConfig0.donkeyCmdRunChance);
		this.donkeyCmdDesc = mConfig0.donkeyCmdDesc;
		this.donkeyCmdRunChance = 1;
		this.donkeyHeadPrize = mConfig0.donkeyHeadPrize;
		this.donkeyHeadDropChance = mConfig0.donkeyCmdRunChance;
		this.donkeyHeadMessage = "You got a skull";

		this.horsePrize = mConfig0.horsePrize;
		this.horseCmdNew = convertCommands(mConfig0.horseCmd, mConfig0.horseCmdRunChance);
		this.horseCmdDesc = mConfig0.horseCmdDesc;
		this.horseCmdRunChance = 1;
		this.horseHeadPrize = mConfig0.horseHeadPrize;
		this.horseHeadDropChance = mConfig0.horseCmdRunChance;
		this.horseHeadMessage = "You got a skull";

		this.llamaPrize = mConfig0.llamaPrize;
		this.llamaCmdNew = convertCommands(mConfig0.llamaCmd, mConfig0.llamaCmdRunChance);
		this.llamaCmdDesc = mConfig0.llamaCmdDesc;
		this.llamaCmdRunChance = 1;
		this.llamaHeadPrize = mConfig0.llamaHeadPrize;
		this.llamaHeadDropChance = mConfig0.llamaCmdRunChance;
		this.llamaHeadMessage = "You got a skull";

		this.mulePrize = mConfig0.mulePrize;
		this.muleCmdNew = convertCommands(mConfig0.muleCmd, mConfig0.muleCmdRunChance);
		this.muleCmdDesc = mConfig0.muleCmdDesc;
		this.muleCmdRunChance = 1;
		this.muleHeadPrize = mConfig0.muleHeadPrize;
		this.muleHeadDropChance = mConfig0.muleCmdRunChance;
		this.muleHeadMessage = "You got a skull";

		this.mushroomCowPrize = mConfig0.mushroomCowPrize;
		this.mushroomCowCmdNew = convertCommands(mConfig0.mushroomCowCmd, mConfig0.mushroomCowCmdRunChance);
		this.mushroomCowCmdDesc = mConfig0.mushroomCowCmdDesc;
		this.mushroomCowCmdRunChance = 1;
		this.mushroomCowHeadPrize = mConfig0.mushroomCowHeadPrize;
		this.mushroomCowHeadDropChance = mConfig0.mushroomCowCmdRunChance;
		this.mushroomCowHeadMessage = "You got a skull";

		this.ocelotPrize = mConfig0.ocelotPrize;
		this.ocelotCmdNew = convertCommands(mConfig0.ocelotCmd, mConfig0.ocelotCmdRunChance);
		this.ocelotCmdDesc = mConfig0.ocelotCmdDesc;
		this.ocelotCmdRunChance = 1;
		this.ocelotHeadPrize = mConfig0.ocelotHeadPrize;
		this.ocelotHeadDropChance = mConfig0.ocelotCmdRunChance;
		this.ocelotHeadMessage = "You got a skull";

		this.parrotPrize = mConfig0.parrotPrize;
		this.parrotCmdNew = convertCommands(mConfig0.parrotCmd, mConfig0.parrotCmdRunChance);
		this.parrotCmdDesc = mConfig0.parrotCmdDesc;
		this.parrotCmdRunChance = 1;
		this.parrotHeadPrize = mConfig0.parrotHeadPrize;
		this.parrotHeadDropChance = mConfig0.parrotCmdRunChance;
		this.parrotHeadMessage = "You got a skull";

		this.pigPrize = mConfig0.pigPrize;
		this.pigCmdNew = convertCommands(mConfig0.pigCmd, mConfig0.pigCmdRunChance);
		this.pigCmdDesc = mConfig0.pigCmdDesc;
		this.pigCmdRunChance = 1;
		this.pigHeadPrize = mConfig0.pigHeadPrize;
		this.pigHeadDropChance = mConfig0.pigCmdRunChance;
		this.pigHeadMessage = "You got a skull";

		this.rabbitPrize = mConfig0.rabbitPrize;
		this.rabbitCmdNew = convertCommands(mConfig0.rabbitCmd, mConfig0.rabbitCmdRunChance);
		this.rabbitCmdDesc = mConfig0.rabbitCmdDesc;
		this.rabbitCmdRunChance = 1;
		this.rabbitHeadPrize = mConfig0.rabbitHeadPrize;
		this.rabbitHeadDropChance = mConfig0.rabbitCmdRunChance;
		this.rabbitHeadMessage = "You got a skull";

		this.sheepPrize = mConfig0.sheepPrize;
		this.sheepCmdNew = convertCommands(mConfig0.sheepCmd, mConfig0.sheepCmdRunChance);
		this.sheepCmdDesc = mConfig0.sheepCmdDesc;
		this.sheepCmdRunChance = 1;
		this.sheepHeadPrize = mConfig0.sheepHeadPrize;
		this.sheepHeadDropChance = mConfig0.sheepCmdRunChance;
		this.sheepHeadMessage = "You got a skull";

		this.skeletonHorsePrize = mConfig0.skeletonhorsePrize;
		this.skeletonHorseCmdNew = convertCommands(mConfig0.skeletonhorseCmd, mConfig0.skeletonhorseCmdRunChance);
		this.skeletonHorseCmdDesc = mConfig0.skeletonhorseCmdDesc;
		this.skeletonHorseCmdRunChance = 1;
		this.skeletonHorseHeadPrize = mConfig0.skeletonHorseHeadPrize;
		this.skeletonHorseHeadDropChance = mConfig0.skeletonhorseCmdRunChance;
		this.skeletonHorseHeadMessage = "You got a skull";

		this.snowmanPrize = mConfig0.snowmanPrize;
		this.snowmanCmdNew = convertCommands(mConfig0.snowmanCmd, mConfig0.snowmanCmdRunChance);
		this.snowmanCmdDesc = mConfig0.snowmanCmdDesc;
		this.snowmanCmdRunChance = 1;
		this.snowmanHeadPrize = mConfig0.snowmanHeadPrize;
		this.snowmanHeadDropChance = mConfig0.snowmanCmdRunChance;
		this.snowmanHeadMessage = "You got a skull";

		this.squidPrize = mConfig0.squidPrize;
		this.squidCmdNew = convertCommands(mConfig0.squidCmd, mConfig0.squidCmdRunChance);
		this.squidCmdDesc = mConfig0.squidCmdDesc;
		this.squidCmdRunChance = 1;
		this.squidHeadPrize = mConfig0.squidHeadPrize;
		this.squidHeadDropChance = mConfig0.squidCmdRunChance;
		this.squidHeadMessage = "You got a skull";

		this.wolfPrize = mConfig0.wolfPrize;
		this.wolfCmdNew = convertCommands(mConfig0.wolfCmd, mConfig0.wolfCmdRunChance);
		this.wolfCmdDesc = mConfig0.wolfCmdDesc;
		this.wolfCmdRunChance = 1;
		this.wolfHeadPrize = mConfig0.wolfHeadPrize;
		this.wolfHeadDropChance = mConfig0.wolfCmdRunChance;
		this.wolfHeadMessage = "You got a skull";

		this.zombieHorsePrize = mConfig0.zombiehorsePrize;
		this.zombieHorseCmdNew = convertCommands(mConfig0.zombiehorseCmd, mConfig0.zombiehorseCmdRunChance);
		this.zombieHorseCmdDesc = mConfig0.zombiehorseCmdDesc;
		this.zombieHorseCmdRunChance = 1;
		this.zombieHorseHeadPrize = mConfig0.zombiehorseHeadPrize;
		this.zombieHorseHeadDropChance = mConfig0.zombiehorseCmdRunChance;
		this.zombieHorseHeadMessage = "You got a skull";

		this.rawFishPrize = mConfig0.rawFishPrize;
		this.rawFishCmdNew = convertCommands(mConfig0.rawFishCmd, mConfig0.rawFishCmdRunChance);
		this.rawFishCmdDesc = mConfig0.rawFishCmdDesc;
		this.rawFishCmdRunChance = 1;
		this.rawFishHeadPrize = mConfig0.rawFishHeadPrize;
		this.rawFishHeadDropChance = mConfig0.rawFishCmdRunChance;
		this.rawFishHeadMessage = "You got a skull";

		this.rawSalmonPrize = mConfig0.rawSalmonPrize;
		this.rawSalmonCmdNew = convertCommands(mConfig0.rawSalmonCmd, mConfig0.rawSalmonCmdRunChance);
		this.rawSalmonCmdDesc = mConfig0.rawSalmonCmdDesc;
		this.rawSalmonCmdRunChance = 1;
		this.rawSalmonHeadPrize = mConfig0.rawSalmonHeadPrize;
		this.rawSalmonHeadDropChance = mConfig0.rawSalmonCmdRunChance;
		this.rawSalmonHeadMessage = "You got a skull";

		this.clownfishPrize = mConfig0.clownfishPrize;
		this.clownfishCmdNew = convertCommands(mConfig0.clownfishCmd, mConfig0.clownfishCmdRunChance);
		this.clownfishCmdDesc = mConfig0.clownfishCmdDesc;
		this.clownfishCmdRunChance = 1;
		this.clownfishHeadPrize = mConfig0.clownfishHeadPrize;
		this.clownfishHeadDropChance = mConfig0.clownfishCmdRunChance;
		this.clownfishHeadMessage = "You got a skull";

		this.pufferfishPrize = mConfig0.pufferfishPrize;
		this.pufferfishCmdNew = convertCommands(mConfig0.pufferfishCmd, mConfig0.pufferfishCmdRunChance);
		this.pufferfishCmdDesc = mConfig0.pufferfishCmdDesc;
		this.pufferfishCmdRunChance = 1;
		this.pufferfishHeadPrize = mConfig0.pufferfishHeadPrize;
		this.pufferfishHeadDropChance = mConfig0.pufferfishCmdRunChance;
		this.pufferfishHeadMessage = "You got a skull";

		this.pvpAllowed = mConfig0.pvpAllowed;
		this.robFromVictim = mConfig0.robFromVictim;
		this.pvpKillCmdDesc = mConfig0.pvpKillCmdDesc;
		this.pvpKillPrize = mConfig0.pvpKillPrize;
		this.pvpCmdRunChance = 1;
		this.pvpCmdNew = convertCommands(mConfig0.pvpKillCmd, mConfig0.pvpKillCmdRunChance);
		this.pvpHeadPrize = mConfig0.pvpHeadPrize;
		this.pvpHeadDropChance = mConfig0.pvpKillCmdRunChance;
		this.pvpHeadMessage = "You got a skull";

		this.updateCheck = mConfig0.updateCheck;
		this.checkEvery = mConfig0.checkEvery;
		this.autoupdate = mConfig0.autoupdate;

		this.enablePlayerBounties = !mConfig0.disablePlayerBounties;
		this.enableFishingRewards = !mConfig0.disableFishingRewards;
		this.enableIntegrationCitizens = !mConfig0.disableIntegrationCitizens;
		this.enableIntegrationIDisguise = !mConfig0.disableIntegrationIDisguise;
		this.enableIntegrationDisguiseCraft = !mConfig0.disableIntegrationDisguiseCraft;
		this.enableIntegrationLibsDisguises = !mConfig0.disableIntegrationLibsDisguises;
		this.enableIntegrationMobStacker = !mConfig0.disableIntegrationMobStacker;
		this.enableIntegrationStackMob = !mConfig0.disableIntegrationStackMob;
		this.enableIntegrationCustomMobs = !mConfig0.disableIntegrationCustomMobs;
		this.enableIntegrationInfernalMobs = !mConfig0.disableIntegrationInfernalMobs;
		this.enableIntegrationConquestiaMobs = !mConfig0.disableIntegrationConquestiaMobs;
		this.enableIntegrationLorinthsRpgMobs = !mConfig0.disableIntegrationLorinthsRpgMobs;
		this.enableIntegrationFactions = !mConfig0.disableIntegrationFactions;
		this.enableIntegrationTowny = !mConfig0.disableIntegrationTowny;
		this.enableIntegrationResidence = !mConfig0.disableIntegrationResidence;
		this.enableIntegrationMcMMO = !mConfig0.disableIntegrationMcMMO;

		this.enableMcMMOLevelRewards = mConfig0.enableMcMMOLevelRewards;
		this.batMcMMOSkillRewardAmount = mConfig0.batMcMMOSkillRewardAmount;
		this.batMcMMOSkillRewardChance = mConfig0.batMcMMOSkillRewardChance;
		this.blacksmithMcMMOSkillRewardAmount = mConfig0.blacksmithMcMMOSkillRewardAmount;
		this.blacksmithMcMMOSkillRewardChance = mConfig0.blacksmithMcMMOSkillRewardChance;
		this.blazeMcMMOSkillRewardAmount = mConfig0.blazeMcMMOSkillRewardAmount;
		this.blazeMcMMOSkillRewardChance = mConfig0.blazeMcMMOSkillRewardChance;
		this.bonusMobMcMMOSkillRewardAmount = mConfig0.bonusMobMcMMOSkillRewardAmount;
		this.bonusMobMcMMOSkillRewardChance = mConfig0.bonusMobMcMMOSkillRewardChance;
		this.butcherMcMMOSkillRewardAmount = mConfig0.butcherMcMMOSkillRewardAmount;
		this.butcherMcMMOSkillRewardChance = mConfig0.butcherMcMMOSkillRewardChance;
		this.cartographerMcMMOSkillRewardAmount = mConfig0.cartographerMcMMOSkillRewardAmount;
		this.cartographerMcMMOSkillRewardChance = mConfig0.cartographerMcMMOSkillRewardChance;
		this.caveSpiderMcMMOSkillRewardAmount = mConfig0.caveSpiderMcMMOSkillRewardAmount;
		this.caveSpiderMcMMOSkillRewardChance = mConfig0.caveSpiderMcMMOSkillRewardChance;
		this.chickenMcMMOSkillRewardAmount = mConfig0.chickenMcMMOSkillRewardAmount;
		this.chickenMcMMOSkillRewardChance = mConfig0.chickenMcMMOSkillRewardChance;
		this.clownfishMcMMOSkillRewardAmount = mConfig0.clownfishMcMMOSkillRewardAmount;
		this.clownfishMcMMOSkillRewardChance = mConfig0.clownfishMcMMOSkillRewardChance;
		this.cowMcMMOSkillRewardAmount = mConfig0.cowMcMMOSkillRewardAmount;
		this.cowMcMMOSkillRewardChance = mConfig0.cowMcMMOSkillRewardChance;
		this.creeperMcMMOSkillRewardAmount = mConfig0.creeperMcMMOSkillRewardAmount;
		this.creeperMcMMOSkillRewardChance = mConfig0.creeperMcMMOSkillRewardChance;
		this.donkeyMcMMOSkillRewardAmount = mConfig0.donkeyMcMMOSkillRewardAmount;
		this.donkeyMcMMOSkillRewardChance = mConfig0.donkeyMcMMOSkillRewardChance;
		this.elderGuardianMcMMOSkillRewardAmount = mConfig0.elderGuardianMcMMOSkillRewardAmount;
		this.elderGuardianMcMMOSkillRewardChance = mConfig0.elderGuardianMcMMOSkillRewardChance;
		this.enderdragonMcMMOSkillRewardAmount = mConfig0.enderdragonMcMMOSkillRewardAmount;
		this.enderdragonMcMMOSkillRewardChance = mConfig0.enderdragonMcMMOSkillRewardChance;
		this.endermanMcMMOSkillRewardAmount = mConfig0.endermanMcMMOSkillRewardAmount;
		this.endermanMcMMOSkillRewardChance = mConfig0.endermanMcMMOSkillRewardChance;
		this.endermiteMcMMOSkillRewardAmount = mConfig0.endermiteMcMMOSkillRewardAmount;
		this.endermiteMcMMOSkillRewardChance = mConfig0.endermiteMcMMOSkillRewardChance;
		this.evokerMcMMOSkillRewardAmount = mConfig0.evokerMcMMOSkillRewardAmount;
		this.evokerMcMMOSkillRewardChance = mConfig0.evokerMcMMOSkillRewardChance;
		this.farmerMcMMOSkillRewardAmount = mConfig0.farmerMcMMOSkillRewardAmount;
		this.farmerMcMMOSkillRewardChance = mConfig0.farmerMcMMOSkillRewardChance;
		this.ghastMcMMOSkillRewardAmount = mConfig0.ghastMcMMOSkillRewardAmount;
		this.ghastMcMMOSkillRewardChance = mConfig0.ghastMcMMOSkillRewardChance;
		this.giantMcMMOSkillRewardAmount = mConfig0.giantMcMMOSkillRewardAmount;
		this.giantMcMMOSkillRewardChance = mConfig0.giantMcMMOSkillRewardChance;
		this.guardianMcMMOSkillRewardAmount = mConfig0.guardianMcMMOSkillRewardAmount;
		this.guardianMcMMOSkillRewardChance = mConfig0.guardianMcMMOSkillRewardChance;
		this.horseMcMMOSkillRewardAmount = mConfig0.horseMcMMOSkillRewardAmount;
		this.horseMcMMOSkillRewardChance = mConfig0.horseMcMMOSkillRewardChance;
		this.huskMcMMOSkillRewardAmount = mConfig0.huskMcMMOSkillRewardAmount;
		this.huskMcMMOSkillRewardChance = mConfig0.huskMcMMOSkillRewardChance;
		this.illusionerMcMMOSkillRewardAmount = mConfig0.illusionerMcMMOSkillRewardAmount;
		this.illusionerMcMMOSkillRewardChance = mConfig0.illusionerMcMMOSkillRewardChance;
		this.ironGolemMcMMOSkillRewardAmount = mConfig0.ironGolemMcMMOSkillRewardAmount;
		this.ironGolemMcMMOSkillRewardChance = mConfig0.ironGolemMcMMOSkillRewardChance;
		this.killerRabbitMcMMOSkillRewardAmount = mConfig0.killerRabbitMcMMOSkillRewardAmount;
		this.killerRabbitMcMMOSkillRewardChance = mConfig0.killerRabbitMcMMOSkillRewardChance;
		this.llamaMcMMOSkillRewardAmount = mConfig0.llamaMcMMOSkillRewardAmount;
		this.llamaMcMMOSkillRewardChance = mConfig0.llamaMcMMOSkillRewardChance;
		this.librarianMcMMOSkillRewardAmount = mConfig0.librarianMcMMOSkillRewardAmount;
		this.librarianMcMMOSkillRewardChance = mConfig0.librarianMcMMOSkillRewardChance;
		this.magmaCubeMcMMOSkillRewardAmount = mConfig0.magmaCubeMcMMOSkillRewardAmount;
		this.magmaCubeMcMMOSkillRewardChance = mConfig0.magmaCubeMcMMOSkillRewardChance;
		this.muleMcMMOSkillRewardAmount = mConfig0.muleMcMMOSkillRewardAmount;
		this.muleMcMMOSkillRewardChance = mConfig0.muleMcMMOSkillRewardChance;
		this.mushroomCowMcMMOSkillRewardAmount = mConfig0.mushroomCowMcMMOSkillRewardAmount;
		this.mushroomCowMcMMOSkillRewardChance = mConfig0.mushroomCowMcMMOSkillRewardChance;
		this.nitwitMcMMOSkillRewardAmount = mConfig0.nitwitMcMMOSkillRewardAmount;
		this.nitwitMcMMOSkillRewardChance = mConfig0.nitwitMcMMOSkillRewardChance;
		this.ocelotMcMMOSkillRewardAmount = mConfig0.ocelotMcMMOSkillRewardAmount;
		this.ocelotMcMMOSkillRewardChance = mConfig0.ocelotMcMMOSkillRewardChance;
		this.parrotMcMMOSkillRewardAmount = mConfig0.parrotMcMMOSkillRewardAmount;
		this.parrotMcMMOSkillRewardChance = mConfig0.parrotMcMMOSkillRewardChance;
		this.pigMcMMOSkillRewardAmount = mConfig0.pigMcMMOSkillRewardAmount;
		this.pigMcMMOSkillRewardChance = mConfig0.pigMcMMOSkillRewardChance;
		this.polarBearMcMMOSkillRewardAmount = mConfig0.polarBearMcMMOSkillRewardAmount;
		this.polarBearMcMMOSkillRewardChance = mConfig0.polarBearMcMMOSkillRewardChance;
		this.priestMcMMOSkillRewardAmount = mConfig0.priestMcMMOSkillRewardAmount;
		this.priestMcMMOSkillRewardChance = mConfig0.priestMcMMOSkillRewardChance;
		this.pufferfishMcMMOSkillRewardAmount = mConfig0.pufferfishMcMMOSkillRewardAmount;
		this.pufferfishMcMMOSkillRewardChance = mConfig0.pufferfishMcMMOSkillRewardChance;
		this.pvpPlayerMcMMOSkillRewardAmount = mConfig0.pvpPlayerMcMMOSkillRewardAmount;
		this.pvpPlayerMcMMOSkillRewardChance = mConfig0.pvpPlayerMcMMOSkillRewardChance;
		this.rabbitMcMMOSkillRewardAmount = mConfig0.rabbitMcMMOSkillRewardAmount;
		this.rabbitMcMMOSkillRewardChance = mConfig0.rabbitMcMMOSkillRewardChance;
		this.rawfishMcMMOSkillRewardAmount = mConfig0.rawfishMcMMOSkillRewardAmount;
		this.rawfishMcMMOSkillRewardChance = mConfig0.rawfishMcMMOSkillRewardChance;
		this.rawsalmonMcMMOSkillRewardAmount = mConfig0.rawsalmonMcMMOSkillRewardAmount;
		this.rawsalmonMcMMOSkillRewardChance = mConfig0.rawsalmonMcMMOSkillRewardChance;
		this.sheepMcMMOSkillRewardAmount = mConfig0.sheepMcMMOSkillRewardAmount;
		this.sheepMcMMOSkillRewardChance = mConfig0.sheepMcMMOSkillRewardChance;
		this.shulkerMcMMOSkillRewardAmount = mConfig0.shulkerMcMMOSkillRewardAmount;
		this.shulkerMcMMOSkillRewardChance = mConfig0.shulkerMcMMOSkillRewardChance;
		this.silverfishMcMMOSkillRewardAmount = mConfig0.silverfishMcMMOSkillRewardAmount;
		this.silverfishMcMMOSkillRewardChance = mConfig0.silverfishMcMMOSkillRewardChance;
		this.skeletonMcMMOSkillRewardAmount = mConfig0.skeletonMcMMOSkillRewardAmount;
		this.skeletonMcMMOSkillRewardChance = mConfig0.skeletonMcMMOSkillRewardChance;
		this.skeletonHorseMcMMOSkillRewardAmount = mConfig0.skeletonHorseMcMMOSkillRewardAmount;
		this.skeletonHorseMcMMOSkillRewardChance = mConfig0.skeletonHorseMcMMOSkillRewardChance;
		this.slimeMcMMOSkillRewardAmount = mConfig0.slimeMcMMOSkillRewardAmount;
		this.slimeMcMMOSkillRewardChance = mConfig0.slimeMcMMOSkillRewardChance;
		this.snowmanMcMMOSkillRewardAmount = mConfig0.snowmanMcMMOSkillRewardAmount;
		this.snowmanMcMMOSkillRewardChance = mConfig0.snowmanMcMMOSkillRewardChance;
		this.spiderMcMMOSkillRewardAmount = mConfig0.spiderMcMMOSkillRewardAmount;
		this.spiderMcMMOSkillRewardChance = mConfig0.spiderMcMMOSkillRewardChance;
		this.squidMcMMOSkillRewardAmount = mConfig0.squidMcMMOSkillRewardAmount;
		this.squidMcMMOSkillRewardChance = mConfig0.squidMcMMOSkillRewardChance;
		this.strayMcMMOSkillRewardAmount = mConfig0.strayMcMMOSkillRewardAmount;
		this.strayMcMMOSkillRewardChance = mConfig0.strayMcMMOSkillRewardChance;
		this.vexMcMMOSkillRewardAmount = mConfig0.vexMcMMOSkillRewardAmount;
		this.vexMcMMOSkillRewardChance = mConfig0.vexMcMMOSkillRewardChance;
		this.villagerMcMMOSkillRewardAmount = mConfig0.villagerMcMMOSkillRewardAmount;
		this.villagerMcMMOSkillRewardChance = mConfig0.villagerMcMMOSkillRewardChance;
		this.vindicatorMcMMOSkillRewardAmount = mConfig0.vindicatorMcMMOSkillRewardAmount;
		this.vindicatorMcMMOSkillRewardChance = mConfig0.vindicatorMcMMOSkillRewardChance;
		this.witchMcMMOSkillRewardAmount = mConfig0.witchMcMMOSkillRewardAmount;
		this.witchMcMMOSkillRewardChance = mConfig0.witherMcMMOSkillRewardChance;
		this.witherMcMMOSkillRewardAmount = mConfig0.witchMcMMOSkillRewardAmount;
		this.witherMcMMOSkillRewardChance = mConfig0.witherMcMMOSkillRewardChance;
		this.witherSkeletonMcMMOSkillRewardAmount = mConfig0.witherSkeletonMcMMOSkillRewardAmount;
		this.witherSkeletonMcMMOSkillRewardChance = mConfig0.witherSkeletonMcMMOSkillRewardChance;
		this.wolfMcMMOSkillRewardAmount = mConfig0.wolfMcMMOSkillRewardAmount;
		this.wolfMcMMOSkillRewardChance = mConfig0.wolfMcMMOSkillRewardChance;
		this.zombieMcMMOSkillRewardAmount = mConfig0.zombieMcMMOSkillRewardAmount;
		this.zombieMcMMOSkillRewardChance = mConfig0.zombieMcMMOSkillRewardChance;
		this.zombieHorseMcMMOSkillRewardAmount = mConfig0.zombieHorseMcMMOSkillRewardAmount;
		this.zombieHorseMcMMOSkillRewardChance = mConfig0.zombieHorseMcMMOSkillRewardChance;
		this.zombiePigManMcMMOSkillRewardAmount = mConfig0.zombiePigManMcMMOSkillRewardAmount;
		this.zombiePigManMcMMOSkillRewardChance = mConfig0.zombiePigManMcMMOSkillRewardChance;
		this.zombieVillagerMcMMOSkillRewardAmount = mConfig0.zombieVillagerMcMMOSkillRewardAmount;
		this.zombieVillagerMcMMOSkillRewardChance = mConfig0.zombieVillagerMcMMOSkillRewardChance;

		this.enableIntegrationCrackShot = !mConfig0.disableIntegrationCrackShot;
		this.enableIntegrationMobArena = !mConfig0.disableIntegrationMobArena;
		this.enableIntegrationPvpArena = !mConfig0.disableIntegrationPvpArena;

		this.enableIntegrationMythicmobs = !mConfig0.disableIntegrationMythicmobs;
		this.enableIntegrationMyPet = !mConfig0.disableIntegrationMyPet;
		this.enableIntegrationMinigames = !mConfig0.disableIntegrationMinigames;
		this.enableIntegrationMinigamesLib = !mConfig0.disableIntegrationMinigamesLib;
		this.enableIntegrationWorldGuard = !mConfig0.disableIntegrationWorldGuard;
		this.enableIntegrationEssentials = !mConfig0.disableIntegrationEssentials;
		this.enableIntegrationBattleArena = !mConfig0.disableIntegrationBattleArena;
		this.enableIntegrationBossBarAPI = !mConfig0.disableIntegrationBossBarAPI;
		this.enableIntegrationBarAPI = !mConfig0.disableIntegrationBarAPI;
		this.enableIntegrationTitleAPI = !mConfig0.disableIntegrationTitleAPI;
		this.enableIntegrationVanishNoPacket = !mConfig0.disableIntegrationVanishNoPacket;
		this.enableIntegrationTitleManager = !mConfig0.disableIntegrationTitleManager;
		this.enableIntegrationActionbar = !mConfig0.disableIntegrationActionbar;
		this.enableIntegrationActionBarAPI = !mConfig0.disableIntegrationActionBarAPI;
		this.enableIntegrationActionAnnouncer = !mConfig0.disableIntegrationActionAnnouncer;
		this.enableIntegrationGringotts = !mConfig0.disableIntegrationGringotts;
		this.enableIntegrationTARDISWeepingAngels = !mConfig0.disableIntegrationTARDISWeepingAngels;
		this.enableIntegrationProtocolLib = !mConfig0.disableIntegrationProtocolLib;
		this.enableIntegrationMysteriousHalloween = !mConfig0.disableIntegrationMysteriousHalloween;
		this.enableIntegrationSmartGiants = !mConfig0.disableIntegrationSmartGiants;
		this.enableIntegrationPlaceholderAPI = !mConfig0.disableIntegrationPlaceholderAPI;
		this.enableIntegrationBossShop = !mConfig0.disableIntegrationBossShop;
		this.enableIntegrationExtraHardMode = !mConfig0.disableIntegrationExtraHardMode;
		this.enableIntegrationHerobrine = !mConfig0.disableIntegrationHerobrine;
		this.enableIntegrationHolograms = !mConfig0.disableIntegrationHolograms;
		this.enableIntegrationHolographicDisplays = !mConfig0.disableIntegrationHolographicDisplays;
		this.enableIntegrationPreciousStones = !mConfig0.disableIntegrationPreciousStones;

		this.bonusSneaky = mConfig0.bonusSneaky;
		this.bonusReturnToSender = mConfig0.bonusReturnToSender;
		this.bonusSendFalling = mConfig0.bonusSendFalling;
		this.bonusNoWeapon = mConfig0.bonusNoWeapon;
		this.bonusFarShot = mConfig0.bonusFarShot;
		this.bonusMounted = mConfig0.bonusMounted;
		this.bonusFriendlyFire = mConfig0.bonusFriendlyFire;
		this.bonusBonusMob = mConfig0.bonusBonusMob;
		this.bonusMobHeadPrize = mConfig0.bonusMobHeadPrize;
		this.bonusCritical = mConfig0.bonusCritical;
		this.bonusMobChance = mConfig0.bonusMobChance;
		this.babyMultiplier = mConfig0.babyMultiplier;
		this.disableAchievementsInWorlds = mConfig0.disableAchievementsInWorlds;
		this.showAchievementsWithoutAReward = mConfig0.showAchievementsWithoutAReward;
		this.specialCharged = mConfig0.specialCharged;
		this.specialChargedCmd = mConfig0.specialChargedCmd;
		this.specialChargedCmdDesc = mConfig0.specialChargedCmdDesc;
		this.specialCreeperPunch = mConfig0.specialCreeperPunch;
		this.specialCreeperPunchCmd = mConfig0.specialChargedCmd;
		this.specialCreeperPunchCmdDesc = mConfig0.specialCreepercideCmdDesc;
		this.specialAxeMurderer = mConfig0.specialAxeMurderer;
		this.specialAxeMurdererCmd = mConfig0.specialAxeMurdererCmd;
		this.specialAxeMurdererCmdDesc = mConfig0.specialAxeMurdererCmdDesc;
		this.davidAndGoliat = mConfig0.davidAndGoliat;
		this.davidAndGoliatCmd = mConfig0.davidAndGoliatCmd;
		this.davidAndGoliatCmdDesc = mConfig0.davidAndGoliatCmdDesc;
		this.specialRecordHungry = mConfig0.specialRecordHungry;
		this.specialRecordHungryCmd = mConfig0.specialRecordHungryCmd;
		this.specialRecordHungryCmdDesc = mConfig0.specialRecordHungryCmdDesc;
		this.specialInfighting = mConfig0.specialInfighting;
		this.specialInfightingCmd = mConfig0.specialInfightingCmd;
		this.specialInfightingCmdDesc = mConfig0.specialInfightingCmdDesc;
		this.specialByTheBook = mConfig0.specialByTheBook;
		this.specialByTheBookCmd = mConfig0.specialByTheBookCmd;
		this.specialByTheBookCmdDesc = mConfig0.specialByTheBookCmdDesc;
		this.specialCreepercide = mConfig0.specialCreepercide;
		this.specialCreepercideCmd = mConfig0.specialCreepercideCmd;
		this.specialCreepercideCmdDesc = mConfig0.specialCreepercideCmdDesc;
		this.specialHuntBegins = mConfig0.specialHuntBegins;
		this.specialHuntBeginsCmd = mConfig0.specialHuntBeginsCmd;
		this.specialHuntBeginsCmdDesc = mConfig0.specialHuntBeginsCmdDesc;
		this.specialItsMagic = mConfig0.specialItsMagic;
		this.specialItsMagicCmd = mConfig0.specialItsMagicCmd;
		this.specialItsMagicCmdDesc = mConfig0.specialItsMagicCmdDesc;
		this.specialFancyPants = mConfig0.specialFancyPants;
		this.specialFancyPantsCmd = mConfig0.specialFancyPantsCmd;
		this.specialFancyPantsCmdDesc = mConfig0.specialFancyPantsCmdDesc;
		this.specialMasterSniper = mConfig0.specialMasterSniper;
		this.specialMasterSniperCmd = mConfig0.specialMasterSniperCmd;
		this.specialMasterSniperCmdDesc = mConfig0.specialMasterSniperCmdDesc;
		this.specialJustInTime = mConfig0.specialJustInTime;
		this.specialJustInTimeCmd = mConfig0.specialJustInTimeCmd;
		this.specialJustInTimeCmdDesc = mConfig0.specialJustInTimeCmdDesc;
		this.specialFangMaster = mConfig0.specialFangMaster;
		this.specialFangMasterCmd = mConfig0.specialFangMasterCmd;
		this.specialFangMasterCmdDesc = mConfig0.specialFangMasterCmdDesc;

		this.specialHunter1 = mConfig0.specialHunter1;
		this.specialHunter1Cmd = mConfig0.specialHunter1Cmd;
		this.specialHunter1CmdDesc = mConfig0.specialHunter1CmdDesc;
		this.specialHunter2 = mConfig0.specialHunter2;
		this.specialHunter2Cmd = mConfig0.specialHunter2Cmd;
		this.specialHunter2CmdDesc = mConfig0.specialHunter2CmdDesc;
		this.specialHunter3 = mConfig0.specialHunter3;
		this.specialHunter3Cmd = mConfig0.specialHunter3Cmd;
		this.specialHunter3CmdDesc = mConfig0.specialHunter3CmdDesc;
		this.specialHunter4 = mConfig0.specialHunter4;
		this.specialHunter4Cmd = mConfig0.specialHunter4Cmd;
		this.specialHunter4CmdDesc = mConfig0.specialHunter4CmdDesc;
		this.specialHunter5 = mConfig0.specialHunter5;
		this.specialHunter5Cmd = mConfig0.specialHunter5Cmd;
		this.specialHunter5CmdDesc = mConfig0.specialHunter5CmdDesc;
		this.specialHunter6 = mConfig0.specialHunter6;
		this.specialHunter6Cmd = mConfig0.specialHunter6Cmd;
		this.specialHunter6CmdDesc = mConfig0.specialHunter6CmdDesc;
		this.specialHunter7 = mConfig0.specialHunter7;
		this.specialHunter7Cmd = mConfig0.specialHunter7Cmd;
		this.specialHunter7CmdDesc = mConfig0.specialHunter7CmdDesc;
		this.specialHunter8 = mConfig0.specialHunter8;
		this.specialHunter8Cmd = mConfig0.specialHunter8Cmd;
		this.specialHunter8CmdDesc = mConfig0.specialHunter8CmdDesc;

		/**
		 * HashMap<String, String> values1 = new HashMap<String, String>();
		 * values1.put("money", String.valueOf(mConfig0.specialHunter1));
		 * values1.put("cmd", mConfig0.specialHunter1Cmd); values1.put("message",
		 * mConfig0.specialHunter1CmdDesc); this.specialHunter.put("level1", values1);
		 * HashMap<String, String> values2 = new HashMap<String, String>();
		 * values2.put("money", String.valueOf(mConfig0.specialHunter2));
		 * values2.put("cmd", mConfig0.specialHunter2Cmd); values2.put("message",
		 * mConfig0.specialHunter2CmdDesc); this.specialHunter.put("level2", values2);
		 * HashMap<String, String> values3 = new HashMap<String, String>();
		 * values3.put("money", String.valueOf(mConfig0.specialHunter3));
		 * values3.put("cmd", mConfig0.specialHunter3Cmd); values3.put("message",
		 * mConfig0.specialHunter3CmdDesc); this.specialHunter.put("level3", values3);
		 * HashMap<String, String> values4 = new HashMap<String, String>();
		 * values4.put("money", String.valueOf(mConfig0.specialHunter4));
		 * values4.put("cmd", mConfig0.specialHunter4Cmd); values4.put("message",
		 * mConfig0.specialHunter4CmdDesc); this.specialHunter.put("level4", values4);
		 * HashMap<String, String> values5 = new HashMap<String, String>();
		 * values5.put("money", String.valueOf(mConfig0.specialHunter5));
		 * values5.put("cmd", mConfig0.specialHunter5Cmd); values5.put("message",
		 * mConfig0.specialHunter5CmdDesc); this.specialHunter.put("level5", values5);
		 * HashMap<String, String> values6 = new HashMap<String, String>();
		 * values6.put("money", String.valueOf(mConfig0.specialHunter6));
		 * values6.put("cmd", mConfig0.specialHunter6Cmd); values6.put("message",
		 * mConfig0.specialHunter6CmdDesc); this.specialHunter.put("level6", values6);
		 * HashMap<String, String> values7 = new HashMap<String, String>();
		 * values7.put("money", String.valueOf(mConfig0.specialHunter7));
		 * values7.put("cmd", mConfig0.specialHunter7Cmd); values7.put("message",
		 * mConfig0.specialHunter7CmdDesc); this.specialHunter.put("level7", values7);
		 * HashMap<String, String> values8 = new HashMap<String, String>();
		 * values8.put("money", String.valueOf(mConfig0.specialHunter8));
		 * values8.put("cmd", mConfig0.specialHunter8Cmd); values8.put("message",
		 * mConfig0.specialHunter8CmdDesc); this.specialHunter.put("level8", values8);
		 **/

		this.batLevel1 = mConfig0.batLevel1;
		this.blazeLevel1 = mConfig0.blazeLevel1;
		this.blacksmithLevel1 = mConfig0.blacksmithLevel1;
		this.bonusMobLevel1 = mConfig0.bonusMobLevel1;
		this.butcherLevel1 = mConfig0.butcherLevel1;
		this.cartographerLevel1 = mConfig0.cartographerLevel1;
		this.caveSpiderLevel1 = mConfig0.caveSpiderLevel1;
		this.chickenLevel1 = mConfig0.chickenLevel1;
		this.clownfishLevel1 = mConfig0.clownfishLevel1;
		this.cowLevel1 = mConfig0.cowLevel1;
		this.creeperLevel1 = mConfig0.creeperLevel1;
		this.donkeyLevel1 = mConfig0.donkeyLevel1;
		this.elderGuardianLevel1 = mConfig0.elderGuardianLevel1;
		this.enderdragonLevel1 = mConfig0.enderdragonLevel1;
		this.endermanLevel1 = mConfig0.endermanLevel1;
		this.endermiteLevel1 = mConfig0.endermiteLevel1;
		this.evokerLevel1 = mConfig0.evokerLevel1;
		this.farmerLevel1 = mConfig0.farmerLevel1;
		this.ghastLevel1 = mConfig0.ghastLevel1;
		this.giantLevel1 = mConfig0.giantLevel1;
		this.guardianLevel1 = mConfig0.guardianLevel1;
		this.horseLevel1 = mConfig0.horseLevel1;
		this.huskLevel1 = mConfig0.huskLevel1;
		this.illusionerLevel1 = mConfig0.illusionerLevel1;
		this.ironGolemLevel1 = mConfig0.ironGolemLevel1;
		this.killerRabbitLevel1 = mConfig0.rabbitLevel1;
		this.librarianLevel1 = mConfig0.librarianLevel1;
		this.llamaLevel1 = mConfig0.llamaLevel1;
		this.magmaCubeLevel1 = mConfig0.magmaCubeLevel1;
		this.muleLevel1 = mConfig0.muleLevel1;
		this.mushroomCowLevel1 = mConfig0.mushroomCowLevel1;
		this.nitwitLevel1 = mConfig0.nitwitLevel1;
		this.ocelotLevel1 = mConfig0.ocelotLevel1;
		this.parrotLevel1 = mConfig0.ocelotLevel1;
		this.pigLevel1 = mConfig0.pigLevel1;
		this.polarBearLevel1 = mConfig0.polarBearLevel1;
		this.priestLevel1 = mConfig0.priestLevel1;
		this.pvpPlayerLevel1 = mConfig0.pvpPlayerLevel1;
		this.pufferfishLevel1 = mConfig0.pufferfishLevel1;
		this.rabbitLevel1 = mConfig0.rabbitLevel1;
		this.rawfishLevel1 = mConfig0.rawfishLevel1;
		this.rawsalmonLevel1 = mConfig0.rawsalmonLevel1;
		this.sheepLevel1 = mConfig0.sheepLevel1;
		this.shulkerLevel1 = mConfig0.shulkerLevel1;
		this.silverfishLevel1 = mConfig0.silverfishLevel1;
		this.skeletonLevel1 = mConfig0.skeletonLevel1;
		this.skeletonHorseLevel1 = mConfig0.skeletonHorseLevel1;
		this.slimeLevel1 = mConfig0.slimeLevel1;
		this.snowmanLevel1 = mConfig0.snowmanLevel1;
		this.spiderLevel1 = mConfig0.spiderLevel1;
		this.squidLevel1 = mConfig0.squidLevel1;
		this.strayLevel1 = mConfig0.strayLevel1;
		this.vexLevel1 = mConfig0.vexLevel1;
		this.villagerLevel1 = mConfig0.villagerLevel1;
		this.vindicatorLevel1 = mConfig0.vindicatorLevel1;
		this.witchLevel1 = mConfig0.witchLevel1;
		this.witherLevel1 = mConfig0.witherLevel1;
		this.witherSkeletonLevel1 = mConfig0.witherSkeletonLevel1;
		this.wolfLevel1 = mConfig0.wolfLevel1;
		this.zombieLevel1 = mConfig0.zombieLevel1;
		this.zombieHorseLevel1 = mConfig0.zombieHorseLevel1;
		this.zombiePigmanLevel1 = mConfig0.zombiePigmanLevel1;
		this.zombieVillagerLevel1 = mConfig0.zombieVillagerLevel1;
		this.enableAssists = mConfig0.enableAssists;
		this.assistMultiplier = mConfig0.assistMultiplier;
		this.assistAllowKillstreak = mConfig0.assistAllowKillstreak;
		this.assistTimeout = mConfig0.assistTimeout;

		this.grindingDetectionEnabled = mConfig0.grindingDetectionEnabled;

		this.areaDetectionEnabled = true;
		this.grindingDetectionRange = mConfig0.grindingDetectionRange;
		this.grindingDetectionNumberOfDeath = mConfig0.grindingDetectionNumberOfDeath;
		this.disableNaturalItemDropsOnPlayerGrinding = mConfig0.disableNaturalItemDropsOnPlayerGrinding;
		this.disableNaturalXPDropsOnPlayerGrinding = mConfig0.disableNaturalXPDropsOnPlayerGrinding;
		this.blacklistPlayerGrindingSpotsServerWorldWide = mConfig0.blacklistPlayerGrindingSpotsServerWorldWide;
		this.isGrindingStackedMobsAllowed = mConfig0.isGrindingStackedMobsAllowed;
		this.detectFarms = mConfig0.detectFarms;
		this.detectNetherGoldFarms = mConfig0.detectNetherGoldFarms;
		this.disableGrindingDetectionInWorlds = mConfig0.disableGrindingDetectionInWorlds;
		this.secondsToSearchForGrinding = mConfig0.secondsToSearchForGrinding;
		this.rangeToSearchForGrinding = mConfig0.rangeToSearchForGrinding;
		this.numberOfDeathsWhenSearchingForGringding = mConfig0.numberOfDeathsWhenSearchingForGringding;
		this.disableNaturalItemDropsOnNetherGoldFarms = mConfig0.disableNaturalItemDropsOnNetherGoldFarms;
		this.disableNaturalXPDropsOnNetherGoldFarms = mConfig0.disableNaturalXPDropsOnNetherGoldFarms;
		this.detectOtherFarms = mConfig0.detectOtherFarms;
		this.secondsToSearchForGrindingOnOtherFarms = mConfig0.secondsToSearchForGrindingOnOtherFarms;
		this.rangeToSearchForGrindingOnOtherFarms = mConfig0.rangeToSearchForGrindingOnOtherFarms;
		this.numberOfDeathsWhenSearchingForGringdingOnOtherFarms = mConfig0.numberOfDeathsWhenSearchingForGringdingOnOtherFarms;
		this.disableNaturalItemDropsOnOtherFarms = mConfig0.disableNaturalItemDropsOnOtherFarms;
		this.disableNaturalXPDropsOnOtherFarms = mConfig0.disableNaturalXPDropsOnOtherFarms;
		this.penaltyFlying = mConfig0.penaltyFlying;
		this.mobKillsPlayerPenalty = mConfig0.mobKillsPlayerPenalty;
		this.killstreakLevel1 = mConfig0.killstreakLevel1;
		this.killstreakLevel1Mult = mConfig0.killstreakLevel1Mult;
		this.killstreakLevel2 = mConfig0.killstreakLevel2;
		this.killstreakLevel2Mult = mConfig0.killstreakLevel2Mult;
		this.killstreakLevel3 = mConfig0.killstreakLevel3;
		this.killstreakLevel3Mult = mConfig0.killstreakLevel3Mult;
		this.killstreakLevel4 = mConfig0.killstreakLevel4;
		this.killstreakLevel4Mult = mConfig0.killstreakLevel4Mult;
		this.rankMultiplier = mConfig0.rankMultiplier;
		this.difficultyMultiplier = mConfig0.difficultyMultiplier;

		this.removeDisguiseWhenAttacking = mConfig0.removeDisguiseWhenAttacking;
		this.removeDisguiseWhenAttacked = mConfig0.removeDisguiseWhenAttacked;
		this.undercoverMultiplier = mConfig0.undercoverMultiplier;
		this.coverBlownMultiplier = mConfig0.coverBlownMultiplier;
		this.masterMobHuntercheckEvery = mConfig0.masterMobHuntercheckEvery;
		this.bountyReturnPct = mConfig0.bountyReturnPct;
		this.bountyDaysToLive = mConfig0.bountyDaysToLive;
		this.enableRandomBounty = mConfig0.enableRandomBounty;
		this.timeBetweenRandomBounties = mConfig0.timeBetweenRandomBounties;
		this.minimumNumberOfOnlinePlayers = mConfig0.minimumNumberOfOnlinePlayers;
		this.chanceToCreateBounty = mConfig0.chanceToCreateBounty;
		this.randomBounty = mConfig0.randomBounty;

		this.getRewardFromStackedMobs = mConfig0.getRewardFromStackedMobs;
		this.allowCustomMobsSpawners = mConfig0.allowCustomMobsSpawners;
		this.multiplierPerInfernalLevel = mConfig0.multiplierPerInfernalLevel;
		this.mulitiplierPerLevel = mConfig0.mulitiplierPerLevel;
		this.factionWarZoneBonusMultiplier = mConfig0.factionWarZoneBonusMultiplier;
		this.disableRewardsInHomeTown = mConfig0.disableRewardsInHomeTown;
		this.disableNaturallyRewardsInHomeTown = mConfig0.disableNaturallyRewardsInHomeTown;
		this.disableRewardsInHomeResidence = mConfig0.disableRewardsInHomeTown;
		this.disableNaturallyRewardsInProtectedResidence = mConfig0.disableNaturallyRewardsInProtectedResidence;

		this.dropMoneyOnGroup = mConfig0.dropMoneyOnGroup;
		this.dropMoneyOnGroundItemtype = mConfig0.dropMoneyOnGroundItemtype;
		this.dropMoneyOnGroundUseAsCurrency = mConfig0.dropMoneyOnGroundUseAsCurrency;
		this.dropMoneyOnGroundMoneyCommandAlias = mConfig0.dropMoneyOnGroundMoneyCommandAlias;
		this.dropMoneyOnGroundItem = mConfig0.dropMoneyOnGroundItem;
		this.dropMoneyOnGroundTextColor = mConfig0.dropMoneyOnGroundTextColor;
		this.dropMoneyOnGroundSkullRewardName = mConfig0.dropMoneyOnGroundSkullRewardName;
		this.dropMoneyOnGroundSkullRewardNamePlural = mConfig0.dropMoneyOnGroundSkullRewardNamePlural;
		this.dropMoneyOnGroundSkullTextureValue = mConfig0.dropMoneyOnGroundSkullTextureValue;
		this.dropMoneyOnGroundSkullTextureSignature = mConfig0.dropMoneyOnGroundSkullTextureSignature;
		this.denyHoppersToPickUpMoney = mConfig0.denyHoppersToPickUpMoney;

		this.crackShot = mConfig0.crackShot;
		this.mobarenaGetRewards = mConfig0.mobarenaGetRewards;
		this.pvparenaGetRewards = mConfig0.pvparenaGetRewards;

		// #####################################################################################
		// Database
		// #####################################################################################
		this.databaseType = mConfig0.databaseType;
		this.databaseUsername = mConfig0.databaseUsername;
		this.databasePassword = mConfig0.databasePassword;
		this.databaseHost = mConfig0.databaseHost;
		this.databaseName = mConfig0.databaseName;
		this.databaseVersion = mConfig0.databaseVersion;

		// #####################################################################################
		// Update Settings
		// #####################################################################################
		this.updateCheck = mConfig0.updateCheck;
		this.checkEvery = mConfig0.checkEvery;
		this.autoupdate = mConfig0.autoupdate;

		// #####################################################################################
		// Generel settings
		// #####################################################################################
		this.disabledInWorlds = mConfig0.disabledInWorlds;
		this.language = mConfig0.language;
		this.disableMoneyRewardsFromMobSpawnersEggsAndDispensers = mConfig0.disableMoneyRewardsFromMobSpawnersEggsAndDispensers;
		this.disableNaturallyDroppedItemsFromMobSpawnersEggsAndDispensers = mConfig0.disableNaturallyDroppedItemsFromMobSpawnersEggsAndDispensers;
		this.disableNaturallyDroppedXPFromMobSpawnersEggsAndDispensers = mConfig0.disableNaturallyDroppedXPFromMobSpawnersEggsAndDispensers;
		this.disableMobHuntingAdvancements = mConfig0.disableMobHuntingAdvancements;
		this.useActionBarforBroadcasts = mConfig0.useActionBarforBroadcasts;
		this.broadcastAchievement = mConfig0.broadcastAchievement;
		this.broadcastFirstAchievement = mConfig0.broadcastFirstAchievement;
		this.savePeriod = mConfig0.savePeriod;
		this.leaderboardUpdatePeriod = mConfig0.leaderboardUpdatePeriod;
		this.killTimeout = mConfig0.killTimeout;
		this.killDebug = mConfig0.killDebug;
		this.rewardRounding = mConfig0.rewardRounding;
		this.minimumReward = mConfig0.minimumReward;
		this.learningMode = mConfig0.learningMode;
		this.useGuiForAchievements = mConfig0.useGuiForAchievements;
		this.useGuiForBounties = mConfig0.useGuiForBounties;
		this.disableNaturalItemDrops = mConfig0.disableNaturalItemDrops;
		this.disableNatualXPDrops = mConfig0.disableNatualXPDrops;
		this.tryToCancelNaturalDropsWhenInCreative = mConfig0.tryToCancelNaturalDropsWhenInCreative;
		this.tryToCancelXPDropsWhenInCreative = mConfig0.tryToCancelXPDropsWhenInCreative;

		return true;
	}

	public static List<HashMap<String, String>> convertCommands(String str, double chance) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		if (!str.equals("")) {
			String[] commands = str.split("\\|");
			for (int n = commands.length; n > 0; n--) {
				HashMap<String, String> cmd = new HashMap<String, String>();
				cmd.put("cmd", commands[n - 1]);
				cmd.put("chance", String.valueOf(chance));
				cmd.put("message", "");
				result.add(cmd);
			}
		}
		return result;
	}

}