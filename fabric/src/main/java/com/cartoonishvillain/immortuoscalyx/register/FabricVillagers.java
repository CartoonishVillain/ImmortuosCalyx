package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class FabricVillagers {
    private static ResourceKey<PoiType> poiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    public static ResourceKey<PoiType> GENETIC_RESEARCHER_POI_KEY;
    public static Supplier<PoiType> GENETIC_RESEARCHER_POI;
    public static Supplier<VillagerProfession> GENETIC_RESEARCHER;

    public static void initVillager() {
        GENETIC_RESEARCHER_POI_KEY = poiKey("genetic_researcher_poi");

        GENETIC_RESEARCHER_POI = registerVillagerPOI("genetic_researcher_poi", FabricBlocksAndBlockItems.GENE_ENCODER.get());
        GENETIC_RESEARCHER = registerVillagerProfession("genetic_researcher", GENETIC_RESEARCHER_POI_KEY);

        VillagerTrades.TRADES.put(
                FabricVillagers.GENETIC_RESEARCHER.get(),
                new Int2ObjectOpenHashMap(
                        ImmutableMap.of(
                                1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(FabricItems.IMMORTUOS_EGGS.get(), 1, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.HONEYCOMB, 5, 3, 2)},
                                2, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(FabricItems.ANTI_PARASITIC.get(), 3, 1, 10), new VillagerTrades.ItemsForEmeralds(FabricItems.GENE_RIPPER.get(), 5, 1, 5)},
                                3, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(FabricBlocksAndBlockItems.SCANNER_BLOCKITEM.get(), 6, 1, 20), new VillagerTrades.ItemsForEmeralds(FabricItems.GENE_SPLICER.get(), 26, 1, 10)},
                                4, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(FabricItems.CALYXANIDE.get(), 12, 1, 30), new VillagerTrades.ItemsForEmeralds(FabricItems.IMMORTUOS_SAMPLE.get(), 10, 1, 15)},
                                5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(FabricItems.ADVANCED_GENE_SPLICER.get(), 56, 1, 30), new VillagerTrades.ItemsForEmeralds(FabricItems.UNIDENTIFIED_GENE.get(), 17, 1, 15)}))
        );
    }

    private static Supplier<PoiType> registerVillagerPOI(String name, Block block) {
        PoiType registered = PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name),
                1,
                1,
                block
        );
        return () -> registered;
    }

    private static Supplier<VillagerProfession> registerVillagerProfession(String name, ResourceKey<PoiType> poiKey) {
        VillagerProfession registered = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name),
                new VillagerProfession(
                        name,
                        holder -> holder.is(poiKey),
                        holder -> holder.is(poiKey),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        FabricSoundEvents.SCANCLEAR.get()
                        ));
        return () -> registered;
    }
}
