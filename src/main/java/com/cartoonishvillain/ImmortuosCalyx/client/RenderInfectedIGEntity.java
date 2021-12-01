package com.cartoonishvillain.ImmortuosCalyx.client;

import com.cartoonishvillain.ImmortuosCalyx.client.layers.BloodiedIGLayer;
import com.cartoonishvillain.ImmortuosCalyx.client.layers.DarkGolemLayer;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedIGEntity;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedIGEntity extends MobRenderer<InfectedIGEntity, IronGolemModel<InfectedIGEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation( "textures/entity/iron_golem/iron_golem.png");

    public RenderInfectedIGEntity(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new IronGolemModel<>(p_174304_.bakeLayer(ModelLayers.IRON_GOLEM)), 0.5f);
        this.addLayer(new DarkGolemLayer(this));
        this.addLayer(new BloodiedIGLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedIGEntity entity) {
        return TEXTURE;
    }

}
