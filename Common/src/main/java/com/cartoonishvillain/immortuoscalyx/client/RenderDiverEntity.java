package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.client.entity.layer.BloodiedDiverLayer;
import com.cartoonishvillain.immortuoscalyx.client.entity.layer.BlueDiverLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedDiverEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class RenderDiverEntity  extends HumanoidMobRenderer<InfectedDiverEntity, HumanoidModel<InfectedDiverEntity>> {
    protected final static ResourceLocation TEXTURE = DefaultPlayerSkin.getDefaultTexture();

    public RenderDiverEntity(EntityRendererProvider.Context pContext) {
        super(pContext, new HumanoidModel<InfectedDiverEntity>(pContext.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new BlueDiverLayer(this));
        this.addLayer(new BloodiedDiverLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedDiverEntity infectedDiverEntity) {
        return TEXTURE;
    }
}
