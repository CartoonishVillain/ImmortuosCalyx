package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.data.gene.GeneItemComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricItemComponents {
    public static Supplier<DataComponentType> GENE_DATA;


    public static void initComponents() {
        GENE_DATA = registerComponent("gene_component", GeneItemComponent.GeneData.COMPONENT_TYPE);
    }

    private static Supplier<DataComponentType> registerComponent(String name, DataComponentType<GeneItemComponent.GeneData> data) {
        DataComponentType<GeneItemComponent.GeneData> registered =
                Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), data);
        return () -> registered;
    }
}
