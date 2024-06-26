package metadev.digital.MetaMobHunting.mobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import metadev.digital.MetaMobHunting.compatibility.BossCompat;
import metadev.digital.MetaMobHunting.compatibility.CitizensCompat;
// TODO: POSSIBLY DEPRECATED import metadev.digital.MetaMobHunting.compatibility.CustomMobsCompat;
import metadev.digital.MetaMobHunting.compatibility.EliteMobsCompat;
import metadev.digital.MetaMobHunting.compatibility.HerobrineCompat;
import metadev.digital.MetaMobHunting.compatibility.InfernalMobsCompat;
// TODO: POSSIBLY DEPRECATED import metadev.digital.MetaMobHunting.compatibility.MysteriousHalloweenCompat;
import metadev.digital.MetaMobHunting.compatibility.MythicMobsCompat;
// TODO: POSSIBLY DEPRECATED import metadev.digital.MetaMobHunting.compatibility.SmartGiantsCompat;
// TODO: POSSIBLY DEPRECATED import metadev.digital.MetaMobHunting.compatibility.TARDISWeepingAngelsCompat;

public enum MobPlugin {
	Minecraft("Minecraft", 0), MythicMobs("MythicMobs", 1), Citizens("Citizens", 2), InfernalMobs("InfernalMobs", 7), Herobrine("Herobrine",8), EliteMobs("EliteMobs",9), Boss("Boss",10);

	private final String name;
	private final Integer id;

	private MobPlugin(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean equalsName(String otherName) {
		return (otherName != null) && name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public MobPlugin valueOf(int id) {
		return MobPlugin.values()[id];
	}

	public String getName() {
		return name;
	}

	public boolean isSupported() {
		switch (this) {
		case Minecraft:
			return true;
		case Citizens:
			return CitizensCompat.isSupported();
		/** // TODO: POSSIBLY DEPRECATED case CustomMobs:
			return CustomMobsCompat.isSupported();
		case MysteriousHalloween:
			return MysteriousHalloweenCompat.isSupported();*/
		case MythicMobs:
			return MythicMobsCompat.isSupported();
		/** // TODO: POSSIBLY DEPRECATED case SmartGiants:
			return SmartGiantsCompat.isSupported();
		case TARDISWeepingAngels:
			return TARDISWeepingAngelsCompat.isSupported(); */
		case InfernalMobs:
			return InfernalMobsCompat.isSupported();
		case Herobrine:
			return HerobrineCompat.isSupported();
		case EliteMobs:
			return EliteMobsCompat.isSupported();
		case Boss:
			return BossCompat.isSupported();
		default:
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			console.sendMessage(ChatColor.RED + "[MobHunting] Missing pluginType '" + this.name() + "' in MobPlugin");
		}
		return false;
	}

}
