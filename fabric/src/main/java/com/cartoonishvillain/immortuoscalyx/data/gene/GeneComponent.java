package com.cartoonishvillain.immortuoscalyx.data.gene;

import com.cartoonishvillain.immortuoscalyx.Constants;
import net.minecraft.resources.ResourceLocation;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.ComponentV3;

public interface GeneComponent extends ComponentV3 {
    ComponentKey<GeneComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_component"), GeneComponent.class
    );

    static GeneComponent get(Object provider) {
        return KEY.get(provider);
    }

    String getGene1();

    String getGene2();

    String getContamination();

    int getGeneQuality();

    void setData(String gene1, String gene2, String contamination, int quality, boolean wasEquipped);
}
