package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedDiverEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedDiverEntity extends HumanoidMobRenderer<InfectedDiverEntity, HumanoidModel<InfectedDiverEntity>> {
//    public RenderInfectedDiverEntity(EntityRenderDispatcher renderManager) {
//        super(renderManager, new Model(), 0.5F);
//    }
    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infecteddiver.png");

    public RenderInfectedDiverEntity(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<InfectedDiverEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedDiverEntity entity) {
    return TEXTURE;
    }
}
