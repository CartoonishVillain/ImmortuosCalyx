package com.cartoonishvillain.immortuoscalyx;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {

//    @SubscribeEvent
//    public static void DiverSpawner(BiomeLoadingEvent event){
//        if(event.getCategory() == Biome.BiomeCategory.OCEAN){
//        MobSpawnSettings.SpawnerData spawners = new MobSpawnSettings.SpawnerData(Register.INFECTEDDIVER.get(), ForgeImmortuosCalyx.commonConfig.DIVER.get(),1,1);
//        event.getSpawns().addSpawn(MobCategory.MONSTER, spawners);}
//        else if (event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.MUSHROOM){
//            ArrayList<MobSpawnSettings.SpawnerData> spawners = new ArrayList<>();
//            spawners.add(new MobSpawnSettings.SpawnerData(Register.INFECTEDHUMAN.get(), ForgeImmortuosCalyx.commonConfig.HUMAN.get(), 1, 1 ));
//            spawners.add(new MobSpawnSettings.SpawnerData(Register.INFECTEDVILLAGER.get(), ForgeImmortuosCalyx.commonConfig.VILLAGER.get(), 1, 1 ));
//            for(MobSpawnSettings.SpawnerData spawner : spawners){event.getSpawns().addSpawn(MobCategory.MONSTER, spawner);}
//        }
//    }

//    public static void PlacementManager(){
//        SpawnPlacements.register(Register.INFECTEDDIVER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, Mob::checkMobSpawnRules);
//        SpawnPlacements.register(Register.INFECTEDHUMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
//        SpawnPlacements.register(Register.INFECTEDVILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
//    }
//
//    public record spawnModifiers(HolderSet<Biome> biomes) implements BiomeModifier {
//
//        @Override
//        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
//            if (phase == Phase.ADD) {
//                if(biome.get)
//
//            }
//        }
//
//        @Override
//        public Codec<? extends BiomeModifier> codec() {
//            return null;
//        }
//    }
}