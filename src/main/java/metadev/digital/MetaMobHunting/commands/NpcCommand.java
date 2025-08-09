package metadev.digital.MetaMobHunting.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import metadev.digital.MetaMobHunting.Messages.MessageHelper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import metadev.digital.metacustomitemslib.Tools;
import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.StatType;
import metadev.digital.MetaMobHunting.compatibility.CitizensCompat;
import metadev.digital.MetaMobHunting.npc.MasterMobHunter;
import metadev.digital.MetaMobHunting.npc.MasterMobHunterTrait;
import metadev.digital.MetaMobHunting.storage.TimePeriod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class NpcCommand implements ICommand, Listener {

	private MobHunting plugin;

	public NpcCommand(MobHunting plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	// Used case (???)
	// /mh npc create <stat type> <period> <number>
	// /mh npc remove
	// /mh npc update
	// /mh npc spawn
	// /mh npc despawn
	// /mh npc select
	// /mh npc tphere
	// /mh npc sethome

	@Override
	public String getName() {
		return "npc";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "citizens" };
	}

	@Override
	public String getPermission() {
		return "mobhunting.npc";
	}

	@Override
	public String[] getUsageString(String label, CommandSender sender) {
		return new String[] {
				ChatColor.GOLD + label + ChatColor.GREEN + " create <stattype> <period> <number>" + ChatColor.WHITE
						+ " - to create a MasterMobHunter NPC",
				ChatColor.GOLD + label + ChatColor.GREEN + " remove" + ChatColor.WHITE
						+ " - to remove the selected MasterMobHunter",
				ChatColor.GOLD + label + ChatColor.GREEN + " update" + ChatColor.WHITE
						+ " - to update selected the MasterMobHunter NPC" };
	}

	@Override
	public String getDescription() {
		return plugin.getMessages().getString("mobhunting.commands.npc.description");
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

		String[] subcmds = { "create", "remove", "select", "spawn", "despawn", "update", "tphere", "sethome" };
		ArrayList<String> items = new ArrayList<String>();
		if (plugin.getCompatibilityManager().isPluginLoaded(CitizensCompat.class)) {
			if (args.length == 1) {
				for (String cmd : subcmds)
					items.add(cmd);
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("create")) {
					StatType[] values = StatType.values();
					for (int i = 0; i < values.length; i++)
						if (values[i] != null)
							items.add(ChatColor.stripColor(values[i].translateName().replace(" ", "_")));
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("create")) {
					TimePeriod[] values = TimePeriod.values();
					for (int i = 0; i < values.length; i++)
						items.add(ChatColor.stripColor(values[i].translateName().replace(" ", "_")));
				}
			}
		}

		if (!args[args.length - 1].trim().isEmpty()) {
			String match = args[args.length - 1].trim().toLowerCase();
			Iterator<String> it = items.iterator();
			while (it.hasNext()) {
				String name = it.next();
				if (!name.toLowerCase().startsWith(match))
					it.remove();
			}
		}
		return items;
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {

		if (args.length == 0)
			return false;
		Player p = (Player) sender;
		NPC npc;
		if (plugin.getCompatibilityManager().isPluginLoaded(CitizensCompat.class)) {
			// MasterMobHunterManager masterMobHunterManager =
			// CitizensCompat.getManager();
			npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
			if (npc == null && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")
					|| args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("despawn")
					|| args[0].equalsIgnoreCase("tphere") || args[0].equalsIgnoreCase("sethome"))) {
				plugin.getMessages().senderSendMessage(sender,
						plugin.getMessages().getString("mobhunting.commands.npc.no_npc_selected"));
				return true;
			}

			if (args.length == 1 && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete"))) {
				if (CitizensCompat.getMasterMobHunterManager().contains(npc.getId())) {
					CitizensCompat.getMasterMobHunterManager().remove(npc.getId());
				}

				plugin.getMessages().senderSendMessage(sender, plugin.getMessages()
						.getString("mobhunting.commands.npc.removed", "npcid", npc.getId(), "npcname", npc.getName()));
				npc.destroy();
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("spawn")) {
				npc.spawn(npc.getStoredLocation());
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("despawn")) {
				npc.despawn();
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("tphere")) {
				if (CitizensCompat.getMasterMobHunterManager().contains(npc.getId())) {
					npc.teleport(((Player) sender).getLocation(), TeleportCause.PLUGIN);
					Block b = Tools.getTargetBlock((Player) sender, 200);
					if (b != null)
						npc.faceLocation(b.getLocation());
					// npc.getEntity().teleport((Player)sender);
				}
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("sethome")) {
				if (CitizensCompat.getMasterMobHunterManager().contains(npc.getId())) {
					CitizensCompat.getMasterMobHunterManager().get(npc.getId()).setHome(npc.getEntity().getLocation());
					plugin.getMessages().senderSendMessage(sender,
							plugin.getMessages().getString("mobhunting.commands.npc.home_set"));
				}
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("update")) {
				plugin.getMessages().senderSendMessage(sender,
						plugin.getMessages().getString("mobhunting.commands.npc.updating"));
				CitizensCompat.getMasterMobHunterManager().forceUpdate();
				return true;

			} else if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
				if (npc != null)
					plugin.getMessages().senderSendMessage(sender, plugin.getMessages().getString(
							"mobhunting.commands.npc.selected", "npcname", npc.getName(), "npcid", npc.getId()));
				else
					plugin.getMessages().senderSendMessage(sender,
							plugin.getMessages().getString("bagofgold.commands.npc.not_selected"));

				return true;

			} else if (args.length == 4 && args[0].equalsIgnoreCase("create")) {
				StatType statType = StatType.parseStat(args[1]);
				if (statType == null) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.unknown_stattype", "stattype", args[1]));
					return true;
				}
				TimePeriod period = TimePeriod.parsePeriod(args[2]);
				if (period == null) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED + plugin.getMessages()
							.getString("mobhunting.commands.base.unknown_timeperiod", "period", args[2]));
					return true;
				}
				int rank = Integer.valueOf(args[3]);
				if (rank < 1 || rank > 25) {
					plugin.getMessages().senderSendMessage(sender, ChatColor.RED
							+ plugin.getMessages().getString("mobhunting.commands.npc.unknown_rank", "rank", args[3]));
					return true;
				}
				NPCRegistry registry = CitizensAPI.getNPCRegistry();
				npc = registry.createNPC(EntityType.PLAYER, "MasterMobHunter");
				npc.addTrait(MasterMobHunterTrait.class);
				CitizensCompat.getMasterMobHunterManager().put(npc.getId(),
						new MasterMobHunter(plugin, npc.getId(), statType, period, 0, rank));
				npc.spawn(p.getLocation());
				CitizensCompat.getMasterMobHunterManager().update(npc);
				plugin.getMessages().senderSendMessage(sender, ChatColor.GREEN
						+ plugin.getMessages().getString("mobhunting.commands.npc.created", "npcid", npc.getId()));
				MessageHelper.debug("Creating MasterMobHunter: id=%s,stat=%s,per=%s,rank=%s", npc.getId(),
						statType.translateName(), period, rank);
				return true;
			}

		}
		return false;
	}
}
