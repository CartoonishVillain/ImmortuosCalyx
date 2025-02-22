package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoVillagers {
    private static DeferredRegister<PoiType> POI_HOLDER = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Constants.MOD_ID);
    private static DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(Registries.VILLAGER_PROFESSION, Constants.MOD_ID);

    public static DeferredHolder<PoiType, PoiType> GENETIC_RESEARCHER_POI;
    public static DeferredHolder<VillagerProfession, VillagerProfession> GENETIC_RESEARCHER;

    public static void init(IEventBus bus) {
        //Make Resource Key
        ResourceKey<PoiType> poiKey = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "genetic_researcher_poi"));

        GENETIC_RESEARCHER_POI = POI_HOLDER.register(
                "genetic_researcher_poi",
                () -> new PoiType(
                        ImmutableSet.copyOf(NeoBlocksAndBlockItems.GENE_ENCODER.get().getStateDefinition().getPossibleStates()),
                        1,
                        1
                )
        );

        GENETIC_RESEARCHER = VILLAGER_PROFESSION.register(
                "genetic_researcher",
                () -> new VillagerProfession(
                        "genetic_researcher",
                        holder -> holder.is(poiKey),
                        holder -> holder.is(poiKey),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        NeoSoundEvents.SCANCLEAR.get()
                ));

        POI_HOLDER.register(bus);
        VILLAGER_PROFESSION.register(bus);
    }
}
