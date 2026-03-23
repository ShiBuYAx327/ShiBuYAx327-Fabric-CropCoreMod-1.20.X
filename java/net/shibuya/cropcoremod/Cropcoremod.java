package net.shibuya.cropcoremod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.shibuya.cropcoremod.block.ModBlocks;
import net.shibuya.cropcoremod.command.RedeemCommand;
import net.shibuya.cropcoremod.component.ModDataComponentTypes;
import net.shibuya.cropcoremod.item.ModItemGroups;
import net.shibuya.cropcoremod.item.ModItems;
import net.shibuya.cropcoremod.util.HammerUsageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cropcoremod implements ModInitializer {
	public static final String MOD_ID = "cropcoremod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();

		ModItems.registerModItem();
		ModBlocks.registerModBlock();

        ModDataComponentTypes.registerDataComponentTypes();

        FuelRegistry.INSTANCE.add(ModItems.STARLIGHT_ASHES, 600);

        PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            RedeemCommand.register(dispatcher);
        });

    }
}