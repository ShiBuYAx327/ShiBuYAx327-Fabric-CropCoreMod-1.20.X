package net.shibuya.cropcoremod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.shibuya.cropcoremod.Cropcoremod;
import net.shibuya.cropcoremod.block.ModBlocks;
import net.shibuya.cropcoremod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> PINK_GARNET_SMELTABLES = List.of(ModItems.RAW_PINK_GARNET, ModBlocks.PINK_GARNET_ORE,
                ModBlocks.PINK_GARNET_DEEPSLATE_ORE);

        offerSmelting(exporter, PINK_GARNET_SMELTABLES, RecipeCategory.MISC, ModItems.PINK_GARNET, 0.25f, 200, "pink_garnet");
        offerBlasting(exporter, PINK_GARNET_SMELTABLES, RecipeCategory.MISC, ModItems.PINK_GARNET, 0.25f, 100, "pink_garnet");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PINK_GARNET, RecipeCategory.DECORATIONS, ModBlocks.PINK_GARNET_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RAW_PINK_GARNET_BLOCK)
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .input('R', ModItems.RAW_PINK_GARNET)
                .criterion(hasItem(ModItems.RAW_PINK_GARNET), conditionsFromItem(ModItems.RAW_PINK_GARNET))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_PINK_GARNET, 9)
                .input(ModBlocks.RAW_PINK_GARNET_BLOCK)
                .criterion(hasItem(ModBlocks.RAW_PINK_GARNET_BLOCK), conditionsFromItem(ModBlocks.RAW_PINK_GARNET_BLOCK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_PINK_GARNET, 32)
                .input(ModBlocks.MAGIC_BLOCK)
                .criterion(hasItem(ModBlocks.MAGIC_BLOCK), conditionsFromItem(ModBlocks.MAGIC_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "raw_pink_garnet_from_magic_block"));

        offerSmithingTrimRecipe(exporter, ModItems.KAUPEN_SMITHING_TEMPLATE, Identifier.of(Cropcoremod.MOD_ID, "kaupen"));





        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PINK_GARNET_SWORD)
                .pattern("P")
                .pattern("P")
                .pattern("S")
                .input('P', ModItems.PINK_GARNET) // หัว
                .input('S', ModItems.IRON_STICK)          // ด้าม
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_sword"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PINK_GARNET_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PINK_GARNET)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PINK_GARNET_SHOVEL)
                .pattern("P")
                .pattern("S")
                .pattern("S")
                .input('P', ModItems.PINK_GARNET)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_shovel"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PINK_GARNET_AXE)
                .pattern("PP")
                .pattern("PS")
                .pattern(" S")
                .input('P', ModItems.PINK_GARNET)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.PINK_GARNET_HOE)
                .pattern("PP")
                .pattern(" S")
                .pattern(" S")
                .input('P', ModItems.PINK_GARNET)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_hoe"));



        //เพิ่มเอง
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                .pattern("  P")
                .pattern(" S ")
                .pattern("S  ")
                .input('P', ModItems.PINK_GARNET)
                .input('S', Items.STICK)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .criterion("has_stick",conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "chisel_from_pink_diagonal"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PINK_GARNET_STAIRS, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_stairs"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PINK_GARNET_SLAB, 6)
                .pattern("###")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_slab"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PINK_GARNET_WALL, 6)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_wall"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PINK_GARNET_BUTTON)
                .pattern("#")
                .input('#', ModItems.PINK_GARNET)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_button"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PINK_GARNET_PRESSURE_PLATE)
                .pattern("##")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_pressure_plate"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.PINK_GARNET_FENCE, 3)
                .pattern("#S#")
                .pattern("#S#")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_fence"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PINK_GARNET_FENCE_GATE)
                .pattern("S#S")
                .pattern("S#S")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .input('S', ModItems.IRON_STICK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_fence_gate"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PINK_GARNET_DOOR, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_door"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.PINK_GARNET_TRAPDOOR, 2)
                .pattern("###")
                .pattern("###")
                .input('#', ModBlocks.PINK_GARNET_BLOCK)
                .criterion("has_pg_block", conditionsFromItem(ModBlocks.PINK_GARNET_BLOCK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_trapdoor"));

        //เพิ่มเอง
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.IRON_STICK, 2)
                .pattern(" I ")
                .pattern(" S ")
                .pattern(" I ")
                .input('I', Items.IRON_INGOT)
                .input('S', Items.STICK)
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "iron_stick"));



        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PINK_GARNET_HELMET)
                .pattern("PPP")
                .pattern("P P")
                .input('P', ModItems.PINK_GARNET)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_helmet"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PINK_GARNET_CHESTPLATE)
                .pattern("P P")
                .pattern("PPP")
                .pattern("PPP")
                .input('P', ModItems.PINK_GARNET)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_chestplate"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PINK_GARNET_LEGGINGS)
                .pattern("PPP")
                .pattern("P P")
                .pattern("P P")
                .input('P', ModItems.PINK_GARNET)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_leggings"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PINK_GARNET_BOOTS)
                .pattern("P P")
                .pattern("P P")
                .input('P', ModItems.PINK_GARNET)
                .criterion("has_pink_garnet", conditionsFromItem(ModItems.PINK_GARNET))
                .offerTo(exporter, Identifier.of(Cropcoremod.MOD_ID, "pink_garnet_boots"));

    }
}
