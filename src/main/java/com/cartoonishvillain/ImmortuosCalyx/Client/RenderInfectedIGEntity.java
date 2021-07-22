package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedHumanEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedIGEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedIGEntity extends MobRenderer<InfectedIGEntity, IronGolemModel<InfectedIGEntity>> {

    public RenderInfectedIGEntity(EntityRenderDispatcher renderManager) {
        super(renderManager, new IronGolemModel<>(), 0.5F);
    }


    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedig.png");

    private static class Model extends HumanoidModel<InfectedHumanEntity> {
        private static RenderType makeRenderType(ResourceLocation texture) {
            RenderType normal = RenderType.entityTranslucent(texture);
            return normal;}

        Model() {
            super(Model::makeRenderType, 0, 0, 64, 64);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedIGEntity entity) {
        return TEXTURE;
    }

}
