package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.data.gene.GeneComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoDataComponentType {
    private static final DeferredRegister.DataComponents DATA_REGISTER = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
    public static Supplier<DataComponentType<GeneComponent.GeneRecord>> NEO_GENE_COMPONENT;

    public static void initDataComponent(IEventBus bus) {
        NEO_GENE_COMPONENT = DATA_REGISTER.registerComponentType(
                "gene_component",
                builder -> builder
                        .persistent(GeneComponent.BASIC_CODEC)
                        .networkSynchronized(GeneComponent.BASIC_STREAM_CODEC)
        );
        DATA_REGISTER.register(bus);
    }

}
