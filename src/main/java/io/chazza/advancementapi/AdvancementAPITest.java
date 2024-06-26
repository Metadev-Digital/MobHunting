package io.chazza.advancementapi;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;


/**
 * Created by ysl3000
 */
public class AdvancementAPITest {

	private final String worldName = "world";
   // private final File worldFile = new File(worldName);


    @SuppressWarnings("deprecation")
	public void createAndSave() {

		AdvancementAPI parent = AdvancementAPI.builder(new NamespacedKey("test", "my/firststeps"))
                .title("First Steps")
                .description("Starting")
                .icon("minecraft:wood_sword")
                .trigger(
                        Trigger.builder(
                                Trigger.TriggerType.CONSUME_ITEM, "test")
                                .condition(Condition.builder("potion", new ItemStack(Material.BREAD, 1))))
                .hidden(false)
                .toast(false)
                .background("minecraft:textures/gui/advancements/backgrounds/stone.png")
                .frame(FrameType.TASK)
                .build();

        parent.save(worldName);

        // you're able to use TextComponents @see https://www.spigotmc.org/wiki/the-chat-component-api/#colors-and-formatting
        TextComponent textComponent = new TextComponent("Addiction!");
        textComponent.setBold(true);
        textComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);

        AdvancementAPI advancementAPI = AdvancementAPI.builder(new NamespacedKey("test", "my/addiction"))
                .title(textComponent) // the TextComponent define above
                .description("Eat an Apple") // you can also use a normal String instead of the TextComponent
                .icon("minecraft:golden_apple")
                .trigger(
                        Trigger.builder(
                                Trigger.TriggerType.CONSUME_ITEM, "test") // triggers when consuming an item
                                .condition(Condition.builder("potion", new ItemStack(Material.APPLE, 1)))) //1 x apple
                .hidden(false) // Advancement is hidden before completed
                .toast(true) // should send a Toast Message -> popup right upper corner
                .background("minecraft:textures/gui/advancements/backgrounds/stone.png")
                .frame(FrameType.GOAL)
                .parent(parent.getId().toString()) // define a parent! example above
                .build();

        advancementAPI.save(worldName);

    }

}
