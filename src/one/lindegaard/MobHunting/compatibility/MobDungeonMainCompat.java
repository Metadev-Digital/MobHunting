package one.lindegaard.MobHunting.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import one.lindegaard.MobHunting.MobHunting;

public class MobDungeonMainCompat implements Listener {
	public MobDungeonMainCompat() {
		Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
		Bukkit.getConsoleSender().sendMessage(MobHunting.PREFIX + "Enabling MobDungeon Compatibility");
	}

}
