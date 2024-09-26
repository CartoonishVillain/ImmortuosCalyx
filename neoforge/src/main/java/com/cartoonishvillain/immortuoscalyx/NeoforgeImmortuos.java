package com.cartoonishvillain.immortuoscalyx;


import com.cartoonishvillain.immortuoscalyx.client.RenderDiverEntity;
import com.cartoonishvillain.immortuoscalyx.client.RenderInfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionDataAttachment;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.*;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@Mod(Constants.MOD_ID)
public class NeoforgeImmortuos {

    public NeoforgeImmortuos(IEventBus eventBus) {
        CommonImmortuos.init();
        NeoEffects.init(eventBus);
        NeoSoundEvents.init(eventBus);
        NeoEntity.init(eventBus);
        NeoBlocksAndBlockItems.init(eventBus);
        NeoItems.init(eventBus);
        NeoTab.init(eventBus);
        PlayerInfectionDataAttachment.loadDataAttachment(eventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commandLoad(RegisterCommandsEvent event){
        SetInfectionCommands.register(event.getDispatcher());
        GetInfectionCommands.register(event.getDispatcher());
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void attributeAttachment(EntityAttributeCreationEvent event) {
            event.put(NeoEntity.INFECTEDHUMAN.get(), InfectedHumanEntity.customAttributes().build());
            event.put(NeoEntity.INFECTEDDIVER.get(), InfectedDiverEntity.customAttributes().build());
        }

        @SubscribeEvent
        public static void spawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(NeoEntity.INFECTEDHUMAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
            event.register(NeoEntity.INFECTEDDIVER.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, InfectedDiverEntity::checkDiverSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        }
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            Services.PLATFORM.clientUpdate();
        }

        @SubscribeEvent
        public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(NeoEntity.INFECTEDHUMAN.get(), RenderInfectedHumanEntity::new);
            event.registerEntityRenderer(NeoEntity.INFECTEDDIVER.get(), RenderDiverEntity::new);
        }
    }
}