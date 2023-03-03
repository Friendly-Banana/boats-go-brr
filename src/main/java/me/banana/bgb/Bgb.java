package me.banana.bgb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class Bgb implements ModInitializer {
    public final static String MOD_ID = "bgb";

    public final static Map<Block, Block> baseBlocks = new HashMap<>();

    // normal ice
    public static final SlabBlock ICE_SLAB_BLOCK = registerSlabBlock("ice_slab", Blocks.ICE);
    public static final StairsBlock ICE_STAIRS_BLOCK = registerStairsBlock("ice_stairs", Blocks.ICE);
    public static final BlockItem ICE_SLAB_ITEM = registerBlockItem("ice_slab", ICE_SLAB_BLOCK);
    public static final BlockItem ICE_STAIRS_ITEM = registerBlockItem("ice_stairs", ICE_STAIRS_BLOCK);

    // packed ice
    public static final SlabBlock PACKED_ICE_SLAB_BLOCK = registerSlabBlock("packed_ice_slab", Blocks.PACKED_ICE);
    public static final StairsBlock PACKED_ICE_STAIRS_BLOCK = registerStairsBlock("packed_ice_stairs", Blocks.PACKED_ICE);
    public static final BlockItem PACKED_ICE_SLAB_ITEM = registerBlockItem("packed_ice_slab", PACKED_ICE_SLAB_BLOCK);
    public static final BlockItem PACKED_ICE_STAIRS_ITEM = registerBlockItem("packed_ice_stairs", PACKED_ICE_STAIRS_BLOCK);

    // blue ice
    public static final SlabBlock BLUE_ICE_SLAB_BLOCK = registerSlabBlock("blue_ice_slab", Blocks.BLUE_ICE);
    public static final StairsBlock BLUE_ICE_STAIRS_BLOCK = registerStairsBlock("blue_ice_stairs", Blocks.BLUE_ICE);
    public static final BlockItem BLUE_ICE_SLAB_ITEM = registerBlockItem("blue_ice_slab", BLUE_ICE_SLAB_BLOCK);
    public static final BlockItem BLUE_ICE_STAIRS_ITEM = registerBlockItem("blue_ice_stairs", BLUE_ICE_STAIRS_BLOCK);

    private static BlockItem registerBlockItem(String id, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    }

    private static SlabBlock registerSlabBlock(String id, Block baseBlock) {
        SlabBlock block = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), new SlabBlock(FabricBlockSettings.copyOf(baseBlock)));
        baseBlocks.put(block, baseBlock);
        return block;
    }

    private static StairsBlock registerStairsBlock(String id, Block baseBlock) {
        StairsBlock block = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), new StairsBlock(baseBlock.getDefaultState(), FabricBlockSettings.copyOf(baseBlock)));
        baseBlocks.put(block, baseBlock);
        return block;
    }

    @Override
    public void onInitialize() {
    }
}
