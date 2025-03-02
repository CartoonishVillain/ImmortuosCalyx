package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.curios.Gene;
import com.cartoonishvillain.immortuoscalyx.curios.GeneSplicer;
import com.cartoonishvillain.immortuoscalyx.items.HealthScanner;
import com.cartoonishvillain.immortuoscalyx.items.SyringeItems;
import com.cartoonishvillain.immortuoscalyx.items.Syringes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class NeoItems {
    public static DeferredHolder<Item, Item> ANTI_PARASITIC;
    public static DeferredHolder<Item, Item> CALYXANIDE;
    public static DeferredHolder<Item, Item> HEALTH_SCANNER;
    public static DeferredHolder<Item, Item> IMMORTUOS_EGGS;
    public static DeferredHolder<Item, Item> IMMORTUOS_SAMPLE;
    public static DeferredHolder<Item, Item> SYRINGE;

    public static DeferredHolder<Item, Item> DIVER_SPAWN_EGG;
    public static DeferredHolder<Item, Item> HUMAN_SPAWN_EGG;

    public static DeferredHolder<Item, Item> GENE_SPLICER;
    public static DeferredHolder<Item, Item> ADVANCED_GENE_SPLICER;

    public static Supplier<Item> UNIDENTIFIED_GENE;
    public static Supplier<Item> IDENTIFIED_GENE;

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

    public static void init(IEventBus modbus) {
        ANTI_PARASITIC = ITEMS.register("anti_parasitic", () -> new SyringeItems(new Item.Properties(), Syringes.ANTIPARASITIC));
        CALYXANIDE = ITEMS.register("calyxanide", () -> new SyringeItems(new Item.Properties(), Syringes.CALYXANIDE));
        HEALTH_SCANNER = ITEMS.register("health_scanner", () -> new HealthScanner(new Item.Properties().stacksTo(1)));
        IMMORTUOS_EGGS = ITEMS.register("immortuos_eggs", () -> new Item(new Item.Properties().food(new FoodProperties(2, 1, true, 2, Optional.empty(), new ArrayList<>()))));
        IMMORTUOS_SAMPLE = ITEMS.register("immortuos_sample", () -> new SyringeItems(new Item.Properties(), Syringes.IMMORTUOS_SAMPLE));
        SYRINGE = ITEMS.register("syringe", () -> new SyringeItems(new Item.Properties(), Syringes.EMPTY));
        DIVER_SPAWN_EGG = ITEMS.register("infected_diver_spawn_egg", () -> new DeferredSpawnEggItem(NeoEntity.INFECTEDDIVER, 2565927, 1973620, new Item.Properties()));
        HUMAN_SPAWN_EGG = ITEMS.register("human_spawn_egg", () -> new DeferredSpawnEggItem(NeoEntity.INFECTEDHUMAN, 2565927, 5065244, new Item.Properties()));
        GENE_SPLICER = ITEMS.register("gene_splicer", () -> new GeneSplicer(new Item.Properties(), 1));
        ADVANCED_GENE_SPLICER = ITEMS.register("advanced_gene_splicer", () -> new GeneSplicer(new Item.Properties(), 2));
        UNIDENTIFIED_GENE = ITEMS.register("unidentified_gene", () -> new Item(new Item.Properties()));
        IDENTIFIED_GENE = ITEMS.register("identified_gene", () -> new Gene(new Item.Properties()));
        ITEMS.register(modbus);
    }
}
