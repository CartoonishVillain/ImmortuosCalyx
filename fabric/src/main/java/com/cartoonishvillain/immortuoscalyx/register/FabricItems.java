package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.items.HealthScanner;
import com.cartoonishvillain.immortuoscalyx.items.LoreItem;
import com.cartoonishvillain.immortuoscalyx.items.SyringeItems;
import com.cartoonishvillain.immortuoscalyx.items.Syringes;
import com.cartoonishvillain.immortuoscalyx.items.trinkets.Gene;
import com.cartoonishvillain.immortuoscalyx.items.trinkets.GeneSplicer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FabricItems {
    public static Supplier<Item> ANTI_PARASITIC;
    public static Supplier<Item> CALYXANIDE;
    public static Supplier<Item> HEALTH_SCANNER;
    public static Supplier<Item> IMMORTUOS_EGGS;
    public static Supplier<Item> IMMORTUOS_SAMPLE;
    public static Supplier<Item> SYRINGE;

    public static Supplier<Item> DIVER_SPAWN_EGG;
    public static Supplier<Item> HUMAN_SPAWN_EGG;

    public static Supplier<Item> GENE_SPLICER;
    public static Supplier<Item> ADVANCED_GENE_SPLICER;

    public static Supplier<Item> UNIDENTIFIED_GENE;
    public static Supplier<Item> IDENTIFIED_GENE;
    public static Supplier<Item> GENE_RIPPER;

    public static void init() {
        ANTI_PARASITIC = registerItem("anti_parasitic", new SyringeItems(new Item.Properties(), Syringes.ANTIPARASITIC));
        CALYXANIDE = registerItem("calyxanide", new SyringeItems(new Item.Properties(), Syringes.CALYXANIDE));
        HEALTH_SCANNER = registerItem("health_scanner", new HealthScanner(new Item.Properties().stacksTo(1)));
        IMMORTUOS_EGGS = registerItem("immortuos_eggs", new Item(new Item.Properties().food(new FoodProperties(2, 1, true, 2, Optional.empty(), new ArrayList<>()))));
        IMMORTUOS_SAMPLE = registerItem("immortuos_sample", new SyringeItems(new Item.Properties(), Syringes.IMMORTUOS_SAMPLE));
        SYRINGE = registerItem("syringe",  new SyringeItems(new Item.Properties(), Syringes.EMPTY));
        DIVER_SPAWN_EGG = registerItem("infected_diver_spawn_egg", new SpawnEggItem(FabricEntity.INFECTED_DIVER.get(), 2565927, 1973620, new Item.Properties()));
        HUMAN_SPAWN_EGG = registerItem("human_spawn_egg", new SpawnEggItem(FabricEntity.INFECTED_HUMAN.get(), 2565927, 5065244, new Item.Properties()));
        GENE_SPLICER = registerItem("gene_splicer", new GeneSplicer(new Item.Properties().stacksTo(1), 1));
        ADVANCED_GENE_SPLICER = registerItem("advanced_gene_splicer", new GeneSplicer(new Item.Properties().stacksTo(1), 2));
        UNIDENTIFIED_GENE = registerItem("unidentified_gene", new LoreItem(new Item.Properties().stacksTo(16), List.of(Component.translatable("lore.immortuoscalyx.unidentified_gene").withStyle(ChatFormatting.BLUE))));
        IDENTIFIED_GENE = registerItem("identified_gene", new Gene(new Item.Properties().stacksTo(1)));
        GENE_RIPPER = registerItem("gene_ripper", new LoreItem(new Item.Properties().stacksTo(1), List.of(Component.translatable("lore.immortuoscalyx.gene_ripper").withStyle(ChatFormatting.BLUE))));
    }

    private static Supplier<Item> registerItem(String name, Item item) {
        Item registered = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), item);
        return () -> registered;
    }
}
