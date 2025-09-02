package io.chazza.advancementapi;

import metadev.digital.MetaMobHunting.Messages.MessageHelper;
import metadev.digital.MetaMobHunting.MobHunting;
import metadev.digital.MetaMobHunting.achievements.Achievement;
import metadev.digital.MetaMobHunting.achievements.ProgressAchievement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AdvancementManager {

	private MobHunting plugin;
	private static ArrayList<AdvancementAPI> knowAdvancements = new ArrayList<AdvancementAPI>();

	public AdvancementManager(MobHunting plugin) {
		this.plugin=plugin;
	}

	public void getAdvancementsFromAchivements() {

		ArrayList<Achievement> achivements = new ArrayList<>();
		achivements.addAll(plugin.getAchievementManager().getAllAchievements());

		AdvancementAPI huntbegins;
		Achievement ach = plugin.getAchievementManager().getAchievement("huntbegins");
		huntbegins = AdvancementAPI.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + ach.getID()))
				.title(ach.getName()).description(ach.getDescription()).icon("minecraft:bow")
				.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "default")
						.condition(Condition.builder("elytra", new ItemStack(Material.STONE, 1))))
				.hidden(false).toast(false).background(Background.STONE.toString()).frame(FrameType.CHALLENGE).build();
		huntbegins.add();
		knowAdvancements.add(huntbegins);
		achivements.remove(ach);

		for (Achievement achievement : achivements) {
			if (!(achievement instanceof ProgressAchievement)) {
				AdvancementAPI child = AdvancementAPI
						.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + achievement.getID()))
						.title(achievement.getName()).description(achievement.getDescription()).icon("minecraft:stone")
						.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "test")
								.condition(Condition.builder("elytra", achievement.getSymbol())))
						.hidden(false).toast(true).frame(FrameType.CHALLENGE).parent(huntbegins.getId().toString())
						.build();
				child.add();
				knowAdvancements.add(child);
			}
		}

		for (Achievement achievement : achivements) {
			if (achievement instanceof ProgressAchievement
					&& ((ProgressAchievement) achievement).inheritFrom() == null) {
				if (((ProgressAchievement) achievement).nextLevelId() != null) {
					AdvancementAPI child = AdvancementAPI
							.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + achievement.getID()))
							.title(achievement.getName()).description(achievement.getDescription())
							.icon("minecraft:stone")
							.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "test")
									.condition(Condition.builder("elytra", achievement.getSymbol())))
							.hidden(false).toast(true).frame(FrameType.TASK).parent(huntbegins.getId().toString())
							.build();
					child.add();
					knowAdvancements.add(child);
				} else {
					AdvancementAPI child = AdvancementAPI
							.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + achievement.getID()))
							.title(achievement.getName()).description(achievement.getDescription())
							.icon("minecraft:stone")
							.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "test")
									.condition(Condition.builder("elytra", achievement.getSymbol())))
							.hidden(false).toast(true).frame(FrameType.GOAL).parent(huntbegins.getId().toString())
							.build();
					child.add();
					knowAdvancements.add(child);
				}

				if ((achievement instanceof ProgressAchievement)
						&& ((ProgressAchievement) achievement).nextLevelId() != null)
					addNext(plugin.getAchievementManager()
							.getAchievement(((ProgressAchievement) achievement).nextLevelId()));
			}
		}
	}

	private void addNext(Achievement achievement) {
		if (achievement instanceof ProgressAchievement) {
			if (((ProgressAchievement) achievement).nextLevelId() != null) {
				AdvancementAPI child = AdvancementAPI
						.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + achievement.getID()))
						.title(achievement.getName()).description(achievement.getDescription()).icon("minecraft:stone")
						.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "test")
								.condition(Condition.builder("elytra", achievement.getSymbol())))
						.hidden(false).toast(true).frame(FrameType.TASK)
						.parent("mobhunting:hunter/" + ((ProgressAchievement) achievement).inheritFrom()).build();
				child.add();
				knowAdvancements.add(child);

				addNext(plugin.getAchievementManager()
						.getAchievement(((ProgressAchievement) achievement).nextLevelId()));

			} else {
				AdvancementAPI child = AdvancementAPI
						.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/" + achievement.getID()))
						.title(achievement.getName()).description(achievement.getDescription()).icon("minecraft:stone")
						.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "test")
								.condition(Condition.builder("elytra", achievement.getSymbol())))
						.hidden(false).toast(true).background(Background.STONE.toString()) // .background("minecraft:textures/gui/advancements/backgrounds/stone.png")
						.frame(FrameType.GOAL)
						.parent("mobhunting:hunter/" + ((ProgressAchievement) achievement).inheritFrom()).build();
				child.add();
				knowAdvancements.add(child);
			}
		}
	}

	/**
	 * updatePlayerAdvancements is run after Achievements is loaded from disk,
	 * when the player joins the server
	 * 
	 * @param player
	 */
	public void updatePlayerAdvancements(Player player) {
		for (AdvancementAPI api : knowAdvancements) {
			Achievement achievement = plugin.getAchievementManager()
					.getAchievement(api.getId().getKey().split("/")[1]);
			if (plugin.getAchievementManager().hasAchievement(achievement, player)) {
				api.grant(player);
			}
		}
	}

	/**
	 * updatePlayerAdvancements is run after Achievements is loaded from disk,
	 * when the player joins the server
	 * 
	 * @param player
	 */
	public void grantAdvancement(Player player, Achievement achievement) {
		for (AdvancementAPI api : knowAdvancements) {
			if (api.getId().getKey().split("/")[1].equalsIgnoreCase(achievement.getID())) {
				api.grant(player);
				break;
			}
		}
	}
	
	public boolean checkAdvancement(String adv) {
        NamespacedKey nsk = new NamespacedKey(MobHunting.getInstance(), "hunter/"   + adv);
        Advancement a = Bukkit.getServer().getAdvancement(nsk);
        if (a != null) {
            MessageHelper.debug("Advancement 'mobhunting:hunter/" + adv + "' exists :)");
            return true;
        } else {
			MessageHelper.debug("There is no advancement with that key, try reloading - /minecraft:reload");
            return false;
        }
	}
	
	//DOES NOT WORK
	public void showAdvancement(Player player, String message){
		AdvancementAPI huntbegins;
		huntbegins = AdvancementAPI.builder(new NamespacedKey(MobHunting.getInstance(), "hunter/temp"))
				.title("MobHunting").description(message).icon("minecraft:bow")
				.trigger(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "default")
						.condition(Condition.builder("elytra", new ItemStack(Material.STONE, 1))))
				.hidden(false).toast(true).background(Background.STONE.toString()).frame(FrameType.CHALLENGE).build();
		huntbegins.show(plugin, player);
	}
	
}
