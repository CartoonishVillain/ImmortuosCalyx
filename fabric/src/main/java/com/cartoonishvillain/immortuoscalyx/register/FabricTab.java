package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FabricTab {
    public static final CreativeModeTab IMMORTUOS_GROUP = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".immortuos"))
            .icon(() -> new ItemStack(FabricBlocksAndBlockItems.SCANNER_BLOCKITEM.get()))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(FabricBlocksAndBlockItems.SCANNER_BLOCKITEM.get());
                output.accept(FabricItems.HEALTH_SCANNER.get());
                output.accept(FabricItems.SYRINGE.get());
                output.accept(FabricItems.ANTI_PARASITIC.get());
                output.accept(FabricItems.CALYXANIDE.get());
                output.accept(FabricItems.IMMORTUOS_SAMPLE.get());
                output.accept(FabricItems.IMMORTUOS_EGGS.get());
                output.accept(FabricItems.DIVER_SPAWN_EGG.get());
                output.accept(FabricItems.HUMAN_SPAWN_EGG.get());
            }))
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID , "immortuos"), IMMORTUOS_GROUP);
    }
}
