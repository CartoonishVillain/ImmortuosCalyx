package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoTab {
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> IMMORTUOS_TAB;

    private static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static void init(IEventBus eventBus) {
        IMMORTUOS_TAB = TAB.register("immortuos", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".immortuos"))
                .icon(() -> new ItemStack(NeoBlocksAndBlockItems.SCANNER_BLOCKITEM.get()))
                .displayItems(((itemDisplayParameters, output) -> {
                    output.accept(NeoBlocksAndBlockItems.SCANNER_BLOCKITEM.get());
                    output.accept(NeoItems.HEALTH_SCANNER.get());
                    output.accept(NeoItems.SYRINGE.get());
                    output.accept(NeoItems.ANTI_PARASITIC.get());
                    output.accept(NeoItems.CALYXANIDE.get());
                    output.accept(NeoItems.IMMORTUOS_SAMPLE.get());
                    output.accept(NeoItems.IMMORTUOS_EGGS.get());
                    output.accept(NeoItems.DIVER_SPAWN_EGG.get());
                    output.accept(NeoItems.HUMAN_SPAWN_EGG.get());
                }))
                .build());
        TAB.register(eventBus);
    }
}
