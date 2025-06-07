package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.ImmortuosConfigCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetGeneCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.*;
import com.cartoonishvillain.incapacitated.Incapacitated;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.intellij.lang.annotations.Identifier;

import java.util.function.Predicate;

import static com.cartoonishvillain.immortuoscalyx.CommonImmortuos.MushroomBiomes;

public class FabricImmortuos implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonImmortuos.init();
        FabricEffects.initEffects();
        FabricSoundEvents.initSounds();
        FabricEntity.initEntity();
        FabricBlocksAndBlockItems.init();
        FabricItems.init();
        FabricTab.init();
        FabricItemComponents.initComponents();
        FabricVillagers.initVillager();

        Predicate<BiomeSelectionContext> spawnPredicate = overWorldNoOceanNoGoZones();
        Predicate<BiomeSelectionContext> oceanPredicate = onlyOcean();
        BiomeModifications.addSpawn(oceanPredicate, MobCategory.MONSTER, FabricEntity.INFECTED_DIVER.get(), 2, 1, 4);
        BiomeModifications.addSpawn(spawnPredicate, MobCategory.MONSTER, FabricEntity.INFECTED_HUMAN.get(), 5, 1, 4);
        SpawnPlacements.register(FabricEntity.INFECTED_HUMAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(FabricEntity.INFECTED_DIVER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, InfectedDiverEntity::checkDiverSpawnRules);

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            SetInfectionCommands.register(dispatcher);
            GetInfectionCommands.register(dispatcher);
            ImmortuosConfigCommands.register(dispatcher);
            SetGeneCommands.register(dispatcher);
        }));

        ServerLifecycleEvents.SERVER_STARTING.register((event) -> {
            FabricStructureGen.addNewVillageBuilding(event);
            if (Services.PLATFORM.isModLoaded("incapacitated")) {
                Incapacitated.instantKillDamageSourcesMessageID.add("infection_damage");
                Incapacitated.instantKillDamageSourcesMessageID.add("organ_damage");
                Incapacitated.noMercyDamageSourcesMessageID.add("infection_damage");
                Incapacitated.noMercyDamageSourcesMessageID.add("organ_damage");
            }
        });

        ServerLifecycleEvents.SERVER_STARTED.register((event) -> {
            CommonImmortuos.bootStrapGenes();
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Services.PLATFORM.sendConfigPacket(
                    Constants.encodeSCV(CommonImmortuos.getActiveGenes().keySet().stream().toList()),
                    Constants.encodeSCV(CommonImmortuos.getActiveContaminations().keySet().stream().toList()),
                    handler.getPlayer()
            );
        });

        PayloadTypeRegistry.playS2C().register(ImmortuosCalyxPayload.TYPE, ImmortuosCalyxPayload.STREAM_CODEC);
    }

    public record ImmortuosCalyxPayload(String genesEnabled, String contaminationsEnabled) implements CustomPacketPayload {
        public static final StreamCodec<ByteBuf, ImmortuosCalyxPayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                ImmortuosCalyxPayload::genesEnabled,
                ByteBufCodecs.STRING_UTF8,
                ImmortuosCalyxPayload::contaminationsEnabled,
                ImmortuosCalyxPayload::new
        );

        public static final CustomPacketPayload.Type<ImmortuosCalyxPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuosconfigpayload"));


        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

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
