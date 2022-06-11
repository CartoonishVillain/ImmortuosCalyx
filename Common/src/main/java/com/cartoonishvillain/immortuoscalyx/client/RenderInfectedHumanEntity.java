package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.client.layers.BloodiedHumanLayer;
import com.cartoonishvillain.immortuoscalyx.client.layers.DarkHumanLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedHumanEntity extends HumanoidMobRenderer<InfectedHumanEntity, HumanoidModel<InfectedHumanEntity>>  {

    protected final static ResourceLocation TEXTURE = DefaultPlayerSkin.getDefaultSkin();

    public RenderInfectedHumanEntity(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<InfectedHumanEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new DarkHumanLayer(this));
        this.addLayer(new BloodiedHumanLayer(this));
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedHumanEntity entity) {
        return TEXTURE;
    }

}
