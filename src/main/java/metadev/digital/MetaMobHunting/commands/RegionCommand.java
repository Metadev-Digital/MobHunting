package metadev.digital.MetaMobHunting.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.compatibility.WorldGuardCompat;
import metadev.digital.MetaMobHunting.compatibility.WorldGuardMobHuntingFlag;

public class RegionCommand implements ICommand {

	private MobHunting plugin;

	public RegionCommand(MobHunting plugin) {
		this.plugin = plugin;
	}

	// Used case (???)
	// /mh region <id> MobHunting allow - args.length = 3 || arg[1]="mobhunting"
	// /mh region <id> MobHunting deny - args.length = 3 || arg[1]="mobhunting"
	// /mh region <id> MobHunting - args.length = 2 || arg[1]="mobhunting"
	// /mh region MobHunting allow - args.length = 2 || arg[0]="mobhunting"
	// /mh region MobHunting deny - args.length = 2 || arg[0]="mobhunting"
	// /mh region MobHunting - args.length = 1 || arg[0]="mobhunting"

	@Override
	public String getName() {
		return "region";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "rg", "worldguard", "flag" };
	}

	@Override
	public String getPermission() {
		return "mobhunting.region";
	}

	@Override
	public String[] getUsageString(String label, CommandSender sender) {
		return new String[] {
				ChatColor.GOLD + label + ChatColor.GREEN + " <id>" + ChatColor.WHITE + " mobhunting allow",
				ChatColor.GOLD + label + ChatColor.GREEN + " <id>" + ChatColor.WHITE + " mobhunting deny",
				ChatColor.GOLD + label + ChatColor.GREEN + " <id>" + ChatColor.WHITE + " mobhunting",
				ChatColor.GOLD + label + ChatColor.GREEN + " mobhunting allow",
				label + ChatColor.GREEN + " mobhunting deny",
				ChatColor.GOLD + label + ChatColor.GREEN + " mobhunting" + ChatColor.WHITE + " - to remove the flag" };
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("mobhunting.commands.region.description");
	}

	@Override
	public boolean canBeConsole() {
		return false;
	}

	@Override
	public boolean canBeCommandBlock() {
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String label, String[] args) {

		ArrayList<String> items = new ArrayList<String>();
		if (plugin.getCompatibilityManager().isPluginLoaded(WorldGuardCompat.class)) {
			if (args.length == 1) {
				if (sender instanceof Player) {

					LocalPlayer localPlayer = WorldGuardCompat.getWorldGuardPlugin().wrapPlayer((Player)sender);
					RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(localPlayer.getWorld());
					//ApplicableRegionSet set = regionManager.getApplicableRegions(((Player) sender).getLocation());
					ApplicableRegionSet set = regionManager.getApplicableRegions(localPlayer.getCardinalDirection().toBlockVector());
					if (set.size() > 0) {
						// player is in one or more regions, show regions on
						// this loction
						Iterator<ProtectedRegion> it = set.iterator();
						while (it.hasNext()) {
							ProtectedRegion area = it.next();
							items.add(area.getId());
						}
					} else {
						// Player is not in a region, show all regions in
						// world.
						//RegionManager rm = WorldGuardCompat.getWorldGuardPlugin().getRegionContainer().get(((Player) sender).getWorld());
						Iterator<Entry<String, ProtectedRegion>> i = regionManager.getRegions().entrySet().iterator();
						while (i.hasNext()) {
							ProtectedRegion pr = i.next().getValue();
							items.add(pr.getId());
						}
					}
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("mobhunting")) {
					items.add("allow");
					items.add("deny");
					items.add(" ");
				} else
					items.add("mobhunting");
			} else if (args.length == 3) {
				if (args[2].equalsIgnoreCase("mobhunting")) {
					items.add("allow");
					items.add("deny");
					items.add(" ");
				} else
					items.add("mobhunting");
			}
		}
		return items;
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		if (args.length == 0)
			return false;

		if (plugin.getCompatibilityManager().isPluginLoaded(WorldGuardCompat.class)) {
			if (sender instanceof Player) {
				LocalPlayer localPlayer = WorldGuardCompat.getWorldGuardPlugin().wrapPlayer((Player)sender);
				RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(localPlayer.getWorld());
				ApplicableRegionSet set = regionManager.getApplicableRegions(localPlayer.getCardinalDirection().toBlockVector());
				if (set.size() == 1) {
					// player is standing on a location with single region
					ProtectedRegion region = set.getRegions().iterator().next();
					if ((args.length == 1)) {
						if (args[0].equalsIgnoreCase("mobhunting"))
							return WorldGuardMobHuntingFlag.removeCurrentRegionFlag(sender, ((Player) sender).getWorld(),
									region, WorldGuardMobHuntingFlag.getMobHuntingFlag());
						else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownFlag", "flag", args[0]));
							return true;
						}
					} else if (args.length == 2 && args[1].equalsIgnoreCase("mobhunting")) {
						Iterator<ProtectedRegion> i = set.getRegions().iterator();
						while (i.hasNext()) {
							ProtectedRegion pr = i.next();
							if (pr.getId().equalsIgnoreCase(args[0])) {
								return WorldGuardMobHuntingFlag.removeCurrentRegionFlag(sender, ((Player) sender).getWorld(),
										pr, WorldGuardMobHuntingFlag.getMobHuntingFlag());
							}
						}
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
								.getString("mobhunting.commands.region.unknownRegionId", "regionid", args[0]));
						return true;
					} else { // args.length>2 update flag
						if (args[0].equalsIgnoreCase("mobhunting")) {
							return WorldGuardMobHuntingFlag.setCurrentRegionFlag(sender, ((Player) sender).getWorld(), region,
									WorldGuardMobHuntingFlag.getMobHuntingFlag(), args[1]);
						} else if (args[1].equalsIgnoreCase("mobhunting"))
							if (region.getId().equalsIgnoreCase(args[0]))
								return WorldGuardMobHuntingFlag.setCurrentRegionFlag(sender, ((Player) sender).getWorld(),
										region, WorldGuardMobHuntingFlag.getMobHuntingFlag(), args[2]);
							else {
								plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
										.getString("mobhunting.commands.region.unknownRegionId", "regionid", args[0]));
								return true;
							}
						else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownFlag", "flag", args[1]));
							return true;
						}
					}
				} else {
					// player is standing on a location with more than one
					// region
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("mobhunting")) {
							Iterator<ProtectedRegion> i = set.getRegions().iterator();
							while (i.hasNext()) {
								ProtectedRegion pr = i.next();
								if (pr.getId().equalsIgnoreCase(args[0])) {
									return WorldGuardMobHuntingFlag.removeCurrentRegionFlag(sender,
											((Player) sender).getWorld(), pr, WorldGuardMobHuntingFlag.getMobHuntingFlag());
								}
							}
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownRegionId", "regionid", args[0]));
							return true;
						} else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownFlag", "flag", args[1]));
							return true;
						}
					} else if (args.length >= 3) {
						if (args[1].equalsIgnoreCase("mobhunting")) {
							Iterator<Entry<String, ProtectedRegion>> i = regionManager.getRegions().entrySet().iterator();
							while (i.hasNext()) {
								ProtectedRegion region = i.next().getValue();
								if (region.getId().equalsIgnoreCase(args[0])) {
									return WorldGuardMobHuntingFlag.setCurrentRegionFlag(sender, ((Player) sender).getWorld(),
											region, WorldGuardMobHuntingFlag.getMobHuntingFlag(), args[2]);
								}
							}
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownRegionId", "regionid", args[0]));
							return true;
						} else {
							plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
									.getString("mobhunting.commands.region.unknownFlag", "flag", args[1]));
							return true;
						}
					} else {
						plugin.getMessages().senderSendMessage(sender, ChatColor.RED
								+ plugin.getMessages().getString("mobhunting.commands.region.specifyRegionId"));
						return true;
					}
				}
			}
		} else {
			plugin.getMessages().senderSendMessage(sender,
					ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.region.noWorldguardSupport"));
			return true;
		}
		return false;
	}
}
