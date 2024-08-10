package me.banana.bgb;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), new BlockItem(block, new Item.Settings()));
    }

    private static SlabBlock registerSlabBlock(String id, Block baseBlock) {
        SlabBlock block = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, id), new SlabBlock(AbstractBlock.Settings.copy(baseBlock)));
        baseBlocks.put(block, baseBlock);
        return block;
    }

    private static StairsBlock registerStairsBlock(String id, Block baseBlock) {
        StairsBlock block = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, id), new StairsBlock(baseBlock.getDefaultState(), AbstractBlock.Settings.copy(baseBlock)));
        baseBlocks.put(block, baseBlock);
        return block;
    }

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(items -> {
            items.add(Items.ICE);
            items.add(ICE_SLAB_ITEM);
            items.add(ICE_STAIRS_ITEM);
            items.add(Items.PACKED_ICE);
            items.add(PACKED_ICE_SLAB_ITEM);
            items.add(PACKED_ICE_STAIRS_ITEM);
            items.add(Items.BLUE_ICE);
            items.add(BLUE_ICE_SLAB_ITEM);
            items.add(BLUE_ICE_STAIRS_ITEM);
        });
    }
}
