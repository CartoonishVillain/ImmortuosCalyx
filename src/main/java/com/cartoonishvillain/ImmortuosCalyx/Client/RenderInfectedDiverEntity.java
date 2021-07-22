package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedDiverEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedDiverEntity extends HumanoidMobRenderer<InfectedDiverEntity, HumanoidModel<InfectedDiverEntity>> {
    public RenderInfectedDiverEntity(EntityRenderDispatcher renderManager) {
        super(renderManager, new Model(), 0.5F);
    }
    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infecteddiver.png");


    private static class Model extends HumanoidModel<InfectedDiverEntity> {
        private static RenderType makeRenderType(ResourceLocation texture) {
            RenderType normal = RenderType.entityTranslucent(texture);
            return normal;}

        Model() {
            super(Model::makeRenderType, 0, 0, 64, 64);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedDiverEntity entity) {
    return TEXTURE;
    }
}
