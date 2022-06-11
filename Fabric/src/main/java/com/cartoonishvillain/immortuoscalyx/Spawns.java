package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.config.ImmortuosConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Predicate;

public class Spawns {
    private static final ImmortuosConfig config = FabricImmortuosCalyx.config;

    public static void initSpawns() {
        standardSpawns();
    }

    public static void standardSpawns() {
        Predicate<BiomeSelectionContext> spawnPredicate = overWorldNoOceanNoGoZones();
        Predicate<BiomeSelectionContext> oceanPredicate = onlyOcean();
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, Register.INFECTEDVILLAGER, config.dimensionsAndSpawnDetails.VILLAGER, 1, 1);
        BiomeModifications.addSpawn(oceanPredicate, MobCategory.MONSTER, Register.INFECTEDDIVER, config.dimensionsAndSpawnDetails.DIVER, 1, 1);
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, Register.INFECTEDHUMAN, config.dimensionsAndSpawnDetails.HUMAN, 1, 1);
    }

    public static Predicate<BiomeSelectionContext> overWorldNoOceanNoGoZones() {
        return BiomeSelectors.tag(BiomeTags.IS_OVERWORLD).and(shroomExclusion());
    }

    public static Predicate<BiomeSelectionContext> shroomExclusion() {
        return Predicate.not(BiomeSelectors.tag(ModdedBiomeTags.MushroomBiomes));
    }

    public static Predicate<BiomeSelectionContext> onlyOcean() {
        return BiomeSelectors.tag(BiomeTags.IS_OCEAN);
    }
}

