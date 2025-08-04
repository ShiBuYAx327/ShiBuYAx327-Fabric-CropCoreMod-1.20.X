package net.shibuya.cropcoremod;

import net.fabricmc.api.ModInitializer;

import net.shibuya.cropcoremod.block.ModBlocks;
import net.shibuya.cropcoremod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cropcoremod implements ModInitializer {
	public static final String MOD_ID = "cropcoremod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItem();
		ModBlocks.registerModBlock();
	}
}