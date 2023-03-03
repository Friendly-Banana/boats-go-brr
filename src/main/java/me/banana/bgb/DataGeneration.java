package me.banana.bgb;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class DataGeneration implements DataGeneratorEntrypoint {
    private static class MyModelGenerator extends FabricModelProvider {
        private MyModelGenerator(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            registerSlab(blockStateModelGenerator, Bgb.ICE_SLAB_BLOCK);
            registerSlab(blockStateModelGenerator, Bgb.PACKED_ICE_SLAB_BLOCK);
            registerSlab(blockStateModelGenerator, Bgb.BLUE_ICE_SLAB_BLOCK);
            registerStair(blockStateModelGenerator, Bgb.ICE_STAIRS_BLOCK);
            registerStair(blockStateModelGenerator, Bgb.PACKED_ICE_STAIRS_BLOCK);
            registerStair(blockStateModelGenerator, Bgb.BLUE_ICE_STAIRS_BLOCK);
        }

        private static void registerStair(BlockStateModelGenerator blockStateModelGenerator, Block block) {
            Block baseBlock = Bgb.baseBlocks.get(block);
            TextureMap textureMap = TextureMap.all(baseBlock);
            Identifier inner = Models.INNER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, inner, regular, outer));
        }

        private static void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block block) {
            Block baseBlock = Bgb.baseBlocks.get(block);
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

    private static class MyRecipeGenerator extends FabricRecipeProvider {
        private MyRecipeGenerator(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
            slabRecipe(exporter, Bgb.ICE_SLAB_BLOCK);
            slabRecipe(exporter, Bgb.PACKED_ICE_SLAB_BLOCK);
            slabRecipe(exporter, Bgb.BLUE_ICE_SLAB_BLOCK);
            stairsRecipe(exporter, Bgb.ICE_STAIRS_BLOCK);
            stairsRecipe(exporter, Bgb.PACKED_ICE_STAIRS_BLOCK);
            stairsRecipe(exporter, Bgb.BLUE_ICE_STAIRS_BLOCK);

            compressIce(exporter, Bgb.ICE_SLAB_BLOCK, Bgb.PACKED_ICE_SLAB_BLOCK);
            compressIce(exporter, Bgb.ICE_STAIRS_BLOCK, Bgb.PACKED_ICE_STAIRS_BLOCK);
            compressIce(exporter, Bgb.PACKED_ICE_SLAB_BLOCK, Bgb.BLUE_ICE_SLAB_BLOCK);
            compressIce(exporter, Bgb.PACKED_ICE_STAIRS_BLOCK, Bgb.BLUE_ICE_STAIRS_BLOCK);
        }

        private static void slabRecipe(Consumer<RecipeJsonProvider> exporter, Block block) {
            Block baseBlock = Bgb.baseBlocks.get(block);
            // 3 blocks into 6 slabs
            RecipeProvider.createSlabRecipe(block, Ingredient.ofItems(baseBlock))
                .criterion(RecipeProvider.hasItem(block), RecipeProvider.conditionsFromItem(baseBlock))
                .offerTo(exporter);
            // 1 block into 2 slabs
            RecipeProvider.offerStonecuttingRecipe(exporter, block, baseBlock, 2);
            // 2 slabs into 1 block
            ShapelessRecipeJsonBuilder.create(baseBlock)
                .input(block, 2)
                .criterion(RecipeProvider.hasItem(block), RecipeProvider.conditionsFromItem(baseBlock))
                .offerTo(exporter, RecipeProvider.convertBetween(baseBlock, block));
        }

        private static void stairsRecipe(Consumer<RecipeJsonProvider> exporter, Block block) {
            Block baseBlock = Bgb.baseBlocks.get(block);
            // all 1 to 1
            RecipeProvider.createStairsRecipe(block, Ingredient.ofItems(baseBlock))
                .criterion(RecipeProvider.hasItem(block), RecipeProvider.conditionsFromItem(baseBlock))
                .offerTo(exporter);
            RecipeProvider.offerStonecuttingRecipe(exporter, block, baseBlock);
            RecipeProvider.offerStonecuttingRecipe(exporter, baseBlock, block);
        }

        private static void compressIce(Consumer<RecipeJsonProvider> exporter, Block input, Block output) {
            // 9 to 1 like ice to packed_ice
            ShapelessRecipeJsonBuilder.create(output)
                .input(input, 9)
                .criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, RecipeProvider.convertBetween(output, input));
        }
    }


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(MyModelGenerator::new);
        fabricDataGenerator.addProvider(MyRecipeGenerator::new);
    }
}