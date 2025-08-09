package metadev.digital.MetaMobHunting.compatibility;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import metadev.digital.MetaMobHunting.Messages.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.MetaMobHunting.MobHunting;

public class CompatibilityManager implements Listener {

	private MobHunting plugin;
	private static HashSet<Object> mCompatClasses = new HashSet<Object>();
	private static HashMap<SupportedPluginEntities, Class<?>> mWaitingCompatClasses = new HashMap<SupportedPluginEntities, Class<?>>();

	public CompatibilityManager(MobHunting plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, MobHunting.getInstance());
	}

	public void registerPlugin(@SuppressWarnings("rawtypes") Class c, SupportedPluginEntities pluginName) {
		try {
			register(c, pluginName);
		} catch (Exception e) {
			MessageHelper.error("MetaMobHunting could not register with [" + pluginName
							+ "] please check if [" + pluginName + "] is compatible with the server ["
							+ Bukkit.getServer().getBukkitVersion() + "]");
			if (plugin.getConfigManager().killDebug)
				e.printStackTrace();
		}
	}

	/**
	 * Registers the compatability handler if the plugin specified is loaded
	 * 
	 * @param compatibilityHandler The class that will be created
	 * @param pluginName           The name of the plugin to check
	 */
	private void register(Class<?> compatibilityHandler, SupportedPluginEntities pluginName) {
		if (Bukkit.getPluginManager().isPluginEnabled(pluginName.getName())) {
			try {
				mCompatClasses.add(compatibilityHandler.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else
			mWaitingCompatClasses.put(pluginName, compatibilityHandler);
	}

	/**
	 * detect if the compatibility class is loaded.
	 * 
	 * @param class1 - The Compatibility class ex. "WorldGuardCompat.class"
	 * @return true if loaded.
	 */
	public boolean isPluginLoaded(Class<?> class1) {
		Iterator<Object> i = mCompatClasses.iterator();
		while (i.hasNext()) {
			Class<?> c = i.next().getClass();
			if (c.getName().equalsIgnoreCase(class1.getName()))
				return true;
		}
		return false;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPluginEnabled(PluginEnableEvent event) {
		SupportedPluginEntities compatPlugin = SupportedPluginEntities.getSupportedPlugin(event.getPlugin().getName());
		if (mWaitingCompatClasses.containsKey(compatPlugin)) {
			registerPlugin(mWaitingCompatClasses.get(compatPlugin), compatPlugin);
			mWaitingCompatClasses.remove(compatPlugin);
		}
	}

}
