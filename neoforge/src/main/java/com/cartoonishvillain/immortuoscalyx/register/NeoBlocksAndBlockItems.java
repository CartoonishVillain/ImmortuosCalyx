package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.blocks.GeneEncoder;
import com.cartoonishvillain.immortuoscalyx.blocks.InfectionScanner;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoBlocksAndBlockItems {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Constants.MOD_ID);
    private static final DeferredRegister<Item> BLOCKITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);


    public static DeferredHolder<Block, Block> SCANNER_BLOCK;
    public static DeferredHolder<Item, Item> SCANNER_BLOCKITEM;
    public static DeferredHolder<Block, Block> GENE_ENCODER;
    public static DeferredHolder<Item, Item> GENE_ENCODERITEM;


    public static void init(IEventBus modbus) {
        SCANNER_BLOCK = BLOCKS.register("infection_scanner", InfectionScanner::new);
        GENE_ENCODER = BLOCKS.register("gene_encoder", GeneEncoder::new);
        BLOCKS.register(modbus);
        SCANNER_BLOCKITEM = BLOCKITEMS.register("infection_scanner", () -> new BlockItem(SCANNER_BLOCK.get(), new Item.Properties()));
        GENE_ENCODERITEM = BLOCKITEMS.register("gene_encoder", () -> new BlockItem(GENE_ENCODER.get(), new Item.Properties()));
        BLOCKITEMS.register(modbus);
    }
}
