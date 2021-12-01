package com.cartoonishvillain.ImmortuosCalyx.client;

import com.cartoonishvillain.ImmortuosCalyx.client.layers.BloodiedDiverLayer;
import com.cartoonishvillain.ImmortuosCalyx.client.layers.BlueDiverLayer;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedDiverEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedDiverEntity extends HumanoidMobRenderer<InfectedDiverEntity, HumanoidModel<InfectedDiverEntity>> {

    protected final static ResourceLocation TEXTURE = DefaultPlayerSkin.getDefaultSkin();

    public RenderInfectedDiverEntity(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<InfectedDiverEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new BlueDiverLayer(this));
        this.addLayer(new BloodiedDiverLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedDiverEntity entity) {
    return TEXTURE;
    }
}
