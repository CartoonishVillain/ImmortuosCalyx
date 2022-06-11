package com.cartoonishvillain.immortuoscalyx;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Spawns {
    public record SpawnModifiers(HolderSet<Biome> biomes, SpawnerData spawn) implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && this.biomes.contains(biome)) {
                builder.getMobSpawnSettings().addSpawn(MobCategory.MONSTER, this.spawn);
                //Logging statement, in a vague attempt to see if anything shows up.
                Constants.LOG.info(this.spawn.type.toString() + "Has been registered! Registered in: " + biome.toString());
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return ForgeImmortuosCalyx.SPAWNCODEC.get();
        }
    }
}