package me.banana.bgb;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class DataGeneration implements DataGeneratorEntrypoint {
    private static class MyModelGenerator extends FabricModelProvider {
        private final static String TOP = "_top";
        private final static String BOTTOM = "_bottom";
        private final static String INNER = "_outer";
        private final static String OUTER = "_inner";

        private MyModelGenerator(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            registerSlab(blockStateModelGenerator, Bgb.ICE_SLAB_BLOCK, Blocks.ICE);
            registerSlab(blockStateModelGenerator, Bgb.PACKED_ICE_SLAB_BLOCK, Blocks.PACKED_ICE);
            registerSlab(blockStateModelGenerator, Bgb.BLUE_ICE_SLAB_BLOCK, Blocks.BLUE_ICE);
            registerStair(blockStateModelGenerator, Bgb.ICE_STAIRS_BLOCK, Blocks.ICE);
            registerStair(blockStateModelGenerator, Bgb.PACKED_ICE_STAIRS_BLOCK, Blocks.PACKED_ICE);
            registerStair(blockStateModelGenerator, Bgb.BLUE_ICE_STAIRS_BLOCK, Blocks.BLUE_ICE);
        }

        private static void registerStair(BlockStateModelGenerator blockStateModelGenerator, Block block, Block baseBlock) {
            TextureMap textureMap = TextureMap.all(baseBlock);
            Identifier inner = Models.INNER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, inner, regular, outer));
        }

        private static void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Block baseBlock) {
            TextureMap textureMap = TextureMap.all(baseBlock);
            Identifier bottom = Models.SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, bottom, top, ModelIds.getBlockModelId(baseBlock)));
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            // automatically derived from blockmodels
        }
    }


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(MyModelGenerator::new);
    }
}