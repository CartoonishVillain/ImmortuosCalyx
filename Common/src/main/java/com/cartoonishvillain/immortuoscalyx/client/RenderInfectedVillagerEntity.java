package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.client.layers.BloodiedVillagerLayer;
import com.cartoonishvillain.immortuoscalyx.client.layers.DarkVillagerLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedVillagerEntity;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedVillagerEntity extends MobRenderer<InfectedVillagerEntity, VillagerModel<InfectedVillagerEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation("textures/entity/villager/villager.png");

    public RenderInfectedVillagerEntity(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new VillagerModel<InfectedVillagerEntity>(p_174304_.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
        this.addLayer(new DarkVillagerLayer(this));
        this.addLayer(new BloodiedVillagerLayer(this));
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedVillagerEntity entity) {
        return TEXTURE;
    }

}
