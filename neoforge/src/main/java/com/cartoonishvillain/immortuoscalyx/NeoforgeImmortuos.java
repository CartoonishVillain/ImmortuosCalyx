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
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

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
        NeoVillagers.init(eventBus);
        PlayerInfectionDataAttachment.loadDataAttachment(eventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commandLoad(RegisterCommandsEvent event){
        SetInfectionCommands.register(event.getDispatcher());
        GetInfectionCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void serverAboutToStartEvent(ServerAboutToStartEvent event) {
        NeoStructureGen.addNewVillageBuilding(event);
    }

    @SubscribeEvent
    public void villagerEvent(VillagerTradesEvent event) {
        List<VillagerTrades.ItemListing> trades = event.getTrades().get(1);
        List<VillagerTrades.ItemListing> trades2 = event.getTrades().get(2);
        List<VillagerTrades.ItemListing> trades3 = event.getTrades().get(3);
        List<VillagerTrades.ItemListing> trades4 = event.getTrades().get(4);
        List<VillagerTrades.ItemListing> trades5 = event.getTrades().get(5);

        if (event.getType() == NeoVillagers.GENETIC_RESEARCHER.get()) {
            trades.add(
                    new BasicItemListing(
                            new ItemStack(Items.STICK, 32),
                            new ItemStack(Items.EMERALD, 1),
                            10, 8 , 0.02f)
            );
        }
    }

    //VillagerTrades.TRADES.put(
    //                GENETIC_RESEARCHER.get(),
    //                new Int2ObjectOpenHashMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.STICK, 32, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.ARROW, 1, 16, 1), new VillagerTrades.ItemsAndEmeraldsToItems(Blocks.GRAVEL, 10, 1, Items.FLINT, 10, 12, 1, 0.05F)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.FLINT, 26, 12, 10), new VillagerTrades.ItemsForEmeralds(Items.BOW, 2, 1, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.STRING, 14, 16, 20), new VillagerTrades.ItemsForEmeralds(Items.CROSSBOW, 3, 1, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.FEATHER, 24, 16, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.BOW, 2, 3, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.TRIPWIRE_HOOK, 8, 12, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.CROSSBOW, 3, 3, 15), new VillagerTrades.TippedArrowForItemsAndEmeralds(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30)}))
    //        );

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