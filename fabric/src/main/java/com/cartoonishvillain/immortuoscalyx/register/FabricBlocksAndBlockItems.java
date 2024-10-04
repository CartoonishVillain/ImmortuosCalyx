package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.blocks.GeneEncoder;
import com.cartoonishvillain.immortuoscalyx.blocks.InfectionScanner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricBlocksAndBlockItems {

    public static Supplier<Block> SCANNER_BLOCK;
    public static Supplier<Item> SCANNER_BLOCKITEM;
    public static Supplier<Block> GENE_ENCODER;
    public static Supplier<Item> GENE_ENCODERITEM;


    public static void init() {
        SCANNER_BLOCK = registerBlock("infection_scanner", new InfectionScanner());
        SCANNER_BLOCKITEM = registerItem("infection_scanner", new BlockItem(SCANNER_BLOCK.get(), new Item.Properties()));
        GENE_ENCODER = registerBlock("gene_encoder", new GeneEncoder());
        GENE_ENCODERITEM = registerItem("gene_encoder", new BlockItem(GENE_ENCODER.get(), new Item.Properties()));

    }

    private static Supplier<Block> registerBlock(String name, Block block) {
        Block registered = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), block);
        return () -> registered;
    }

    private static Supplier<Item> registerItem(String name, Item item) {
        Item registered = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), item);
        return () -> registered;
    }
}
