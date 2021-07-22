package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedHumanEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedHumanEntity extends HumanoidMobRenderer<InfectedHumanEntity, HumanoidModel<InfectedHumanEntity>> implements EntityRendererProvider {
    public RenderInfectedHumanEntity(EntityRenderDispatcher renderManager) {
        super(renderManager, new Model(), 0.5F);
    }

    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedhuman.png");

    @Override
    public EntityRenderer create(Context p_174010_) {
        return null;
    }

    private static class Model extends HumanoidModel<InfectedHumanEntity> {
        private static RenderType makeRenderType(ResourceLocation texture) {
            RenderType normal = RenderType.entityTranslucent(texture);
            return normal;}

        Model() {
            super(Model::makeRenderType, 0, 0, 64, 64);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedHumanEntity entity) {
        return TEXTURE;
    }

}
