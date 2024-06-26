package metadev.digital.MetaMobHunting.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import metadev.digital.MetaMobHunting.HuntData;
import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.grinding.Area;

public class CheckGrindingCommand implements ICommand {

	private MobHunting plugin;

	public CheckGrindingCommand(MobHunting plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "checkgrinding";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "isgrinding", "grinding", "checkarea" };
	}

	@Override
	public String getPermission() {
		return "mobhunting.checkgrinding";
	}

	@Override
	public String[] getUsageString(String label, CommandSender sender) {
		return new String[] { ChatColor.GOLD + label };
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("mobhunting.commands.grinding.description");
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
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		if (args.length != 0)
			return false;

		Location loc = ((Player) sender).getLocation();

		if (plugin.getGrindingManager().isWhitelisted(loc)) {
			plugin.getMessages().senderSendMessage(sender,
					ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.grinding.whitelisted"));
			Area area = plugin.getGrindingManager().getWhitelistArea(loc);
			plugin.getGrindingManager().showGrindingArea((Player) sender, area, null);
		} else if (plugin.getGrindingManager().isGrindingArea(loc)) {
			plugin.getMessages().senderSendMessage(sender,
					ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.grinding.blacklisted"));
			Area area = plugin.getGrindingManager().getGrindingArea(loc);
			plugin.getGrindingManager().showGrindingArea((Player) sender, area, null);
			
		} /**else if (plugin.getGrindingManager().isGrindingArea(loc.clone().subtract(0, loc.getY() + 65, 0))) {
			plugin.getMessages().senderSendMessage(sender,
					ChatColor.RED + plugin.getMessages().getString("mobhunting.commands.grinding.blacklisted.void"));
			Area area = plugin.getGrindingManager().getGrindingArea(loc);
			plugin.getGrindingManager().showGrindingArea((Player) sender, area, null);

		} **/ else {
			Area area = null;
			ArrayList<Player> players = new ArrayList<Player>();
			for (Player player : Bukkit.getOnlinePlayers()) {
				HuntData data = new HuntData(player);
				area = data.getPlayerGrindingArea(loc);
				if (area != null)
					players.add(player);
			}

			if (players.isEmpty())
				plugin.getMessages().senderSendMessage(sender,
						ChatColor.GREEN + plugin.getMessages().getString("mobhunting.commands.grinding.not-grinding"));
			else {
				String playerList = "";

				for (Player player : players) {
					if (!playerList.isEmpty())
						playerList += ", ";

					playerList += player.getName();
				}

				plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
						.getString("mobhunting.commands.grinding.player-grinding", "players", playerList));
				plugin.getGrindingManager().showGrindingArea((Player) sender, area, null);
			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
		return null;
	}

}
