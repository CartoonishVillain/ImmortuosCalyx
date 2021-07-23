package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedIGEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedIGEntity extends MobRenderer<InfectedIGEntity, IronGolemModel<InfectedIGEntity>> {

//    public RenderInfectedIGEntity(EntityRenderDispatcher renderManager) {
//        super(renderManager, new IronGolemModel<>(), 0.5F);
//    }


    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedig.png");

    public RenderInfectedIGEntity(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new IronGolemModel<>(p_174304_.bakeLayer(ModelLayers.IRON_GOLEM)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedIGEntity entity) {
        return TEXTURE;
    }

}
