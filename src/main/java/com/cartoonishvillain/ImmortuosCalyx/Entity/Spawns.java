package com.cartoonishvillain.ImmortuosCalyx.Entity;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;


@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {

    @SubscribeEvent
    public static void DiverSpawner(BiomeLoadingEvent event){
        if(event.getCategory() == Biome.BiomeCategory.OCEAN){
        MobSpawnSettings.SpawnerData spawners = new MobSpawnSettings.SpawnerData(Register.INFECTEDDIVER.get(), ImmortuosCalyx.commonConfig.DIVER.get(),1,1);
        event.getSpawns().addSpawn(MobCategory.MONSTER, spawners);}
        else if (event.getCategory() != Biome.BiomeCategory.THEEND || event.getCategory() != Biome.BiomeCategory.NETHER){
            ArrayList<MobSpawnSettings.SpawnerData> spawners = new ArrayList<>();
            spawners.add(new MobSpawnSettings.SpawnerData(Register.INFECTEDHUMAN.get(), ImmortuosCalyx.commonConfig.HUMAN.get(), 1, 1 ));
            spawners.add(new MobSpawnSettings.SpawnerData(Register.INFECTEDVILLAGER.get(), ImmortuosCalyx.commonConfig.VILLAGER.get(), 1, 1 ));
            for(MobSpawnSettings.SpawnerData spawner : spawners){event.getSpawns().addSpawn(MobCategory.MONSTER, spawner);}
        }
    }

    public static void PlacementManager(){
        SpawnPlacements.register(Register.INFECTEDDIVER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, Mob::checkMobSpawnRules);
        SpawnPlacements.register(Register.INFECTEDHUMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(Register.INFECTEDVILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}