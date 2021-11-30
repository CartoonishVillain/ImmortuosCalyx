package com.cartoonishvillain.ImmortuosCalyx.client;

import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedVillagerEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedVillagerEntity extends MobRenderer<InfectedVillagerEntity, VillagerModel<InfectedVillagerEntity>> {
//    public RenderInfectedVillagerEntity(EntityRenderDispatcher renderManager) {
//        super(renderManager, new VillagerModel<InfectedVillagerEntity>(0), 0.5F);
//    }

    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/infectedvillager.png");

    public RenderInfectedVillagerEntity(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new VillagerModel<InfectedVillagerEntity>(p_174304_.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedVillagerEntity entity) {
        return TEXTURE;
    }

}
