package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.ImmortuosConfigCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.FabricEffects;
import com.cartoonishvillain.immortuoscalyx.register.FabricEntity;
import com.cartoonishvillain.immortuoscalyx.register.FabricSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public class FabricImmortuos implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonImmortuos.init();
        FabricEffects.initEffects();
        FabricSoundEvents.initSounds();
        FabricEntity.initEntity();

        Predicate<BiomeSelectionContext> spawnPredicate = overWorldNoOceanNoGoZones();
        Predicate<BiomeSelectionContext> oceanPredicate = onlyOcean();
        BiomeModifications.addSpawn(oceanPredicate, MobCategory.MONSTER, FabricEntity.INFECTED_DIVER.get(), 1, 1, 1);
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, FabricEntity.INFECTED_HUMAN.get(), 1, 1, 1);
        SpawnPlacements.register(FabricEntity.INFECTED_HUMAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(FabricEntity.INFECTED_DIVER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, InfectedDiverEntity::checkDiverSpawnRules);

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            SetInfectionCommands.register(dispatcher);
            GetInfectionCommands.register(dispatcher);
            ImmortuosConfigCommands.register(dispatcher);
        }));

        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            Services.PLATFORM.clientUpdate();
        });
    }

    public static final TagKey<Biome> MushroomBiomes = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "land_spawnable"));


    public static Predicate<BiomeSelectionContext> overWorldNoOceanNoGoZones() {
        return BiomeSelectors.tag(BiomeTags.IS_OVERWORLD).and(shroomExclusion());
    }

    public static Predicate<BiomeSelectionContext> shroomExclusion() {
        return Predicate.not(BiomeSelectors.tag(MushroomBiomes));
    }

    public static Predicate<BiomeSelectionContext> onlyOcean() {
        return BiomeSelectors.tag(BiomeTags.IS_OCEAN);
    }
}
