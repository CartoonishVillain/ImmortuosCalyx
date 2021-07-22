package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedPlayerEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedPlayerEntity extends HumanoidMobRenderer<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> {
    public RenderInfectedPlayerEntity(EntityRenderDispatcher renderManager) {
        super(renderManager, new Model(), 0.5F);
        this.addLayer(new BloodiedPlayerLayer(this));
    }

    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedhuman.png");

    private static class Model extends HumanoidModel<InfectedPlayerEntity> {
        private static RenderType makeRenderType(ResourceLocation texture) {
            RenderType normal = RenderType.entityTranslucent(texture);
            return normal;}

        Model() {
            super(Model::makeRenderType, 0, 0, 64, 64);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InfectedPlayerEntity entity) {
        try{
        return PlayerSkinManager.getSkin(entity.getPUUID(), entity.getName().getString());
        } catch (NullPointerException e){
            return TEXTURE;
        }
    }

}
