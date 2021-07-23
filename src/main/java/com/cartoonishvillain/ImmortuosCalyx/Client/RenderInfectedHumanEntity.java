package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedHumanEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedHumanEntity extends HumanoidMobRenderer<InfectedHumanEntity, HumanoidModel<InfectedHumanEntity>>  {

//    public RenderInfectedHumanEntity(EntityRenderDispatcher renderManager) {
//        super(renderManager, new Model(), 0.5F);
//    }

    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedhuman.png");

    public RenderInfectedHumanEntity(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<InfectedHumanEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedHumanEntity entity) {
        return TEXTURE;
    }

}
