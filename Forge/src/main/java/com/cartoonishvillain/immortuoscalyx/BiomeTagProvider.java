package com.cartoonishvillain.immortuoscalyx;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BiomeTagProvider extends BiomeTagsProvider {
    public BiomeTagProvider(DataGenerator p_211094_, ExistingFileHelper existingFileHelper) {
        super(p_211094_, Constants.MOD_ID, existingFileHelper);
        addTags();
    }

    @Override
    protected void addTags() {
        tag(ModdedBiomeTags.MushroomBiomes).add(Biomes.MUSHROOM_FIELDS);
    }
}
