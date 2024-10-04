package com.cartoonishvillain.immortuoscalyx;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class FabricStructureGen {
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath("minecraft", "empty"));

    /**
     * Adds the building to the targeted pool.
     * We will call this in addNewVillageBuilding method further down to add to every village.
     *
     * Note: This is an additive operation which means multiple mods can do this and they stack with each other safely.
     */
    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry,
                                          Registry<StructureProcessorList> processorListRegistry,
                                          ResourceLocation poolRL,
                                          String nbtPieceRL,
                                          int weight) {

        // Grabs the processor list we want to use along with our piece.
        // This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
        // The reason why is the empty processor list in the world's registry is not the same instance as in that field once the world is started up.
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        // Grab the pool we want to add to
        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;

        // Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
        // Use .ofProcessedLegacySingle( for villages/outposts and .ofProcessedSingle( for everything else
        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        // Use AccessWideners or Accessor Mixin to make StructurePool's templates field public for us to see.
        // Weight is handled by how many times the entry appears in this list.
        // We do not need to worry about immutability as this field is created using Lists.newArrayList(); which makes a mutable list.
        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }

        // Use AccessWideners or Accessor Mixin to make StructurePool's elementCounts field public for us to see.
        // This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
        // So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
        //   NOTE: This is a com.mojang.datafixers.util.Pair. It is NOT a fastUtil pair class. Use the mojang class.
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }

    /**
     * We use ServerLifecycleEvents.SERVER_STARTING as the dynamic registry exists now and all JSON worldgen files were parsed.
     * Mod compat is best done here.
     */
    public static void addNewVillageBuilding(final MinecraftServer event) {
        Registry<StructureTemplatePool> templatePoolRegistry = event.registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = event.registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

        // Adds our piece to all village houses pool
        // Note, the resourcelocation is getting the pool files from the data folder. Not assets folder.
        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                ResourceLocation.parse("minecraft:village/plains/houses"),
                "immortuoscalyx:village/plains/houses/plains_lab", 4);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                ResourceLocation.parse("minecraft:village/snowy/houses"),
                "immortuoscalyx:village/snowy/houses/snowy_lab", 4);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                ResourceLocation.parse("minecraft:village/savanna/houses"),
                "immortuoscalyx:village/savanna/houses/savanna_lab", 4);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                ResourceLocation.parse("minecraft:village/taiga/houses"),
                "immortuoscalyx:village/taiga/houses/taiga_lab", 4);

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                ResourceLocation.parse("minecraft:village/desert/houses"),
                "immortuoscalyx:village/desert/houses/desert_lab", 4);
    }
}
