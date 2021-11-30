package com.cartoonishvillain.ImmortuosCalyx.client;

import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedPlayerEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;

public class BloodiedPlayerLayer extends RenderLayer<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> {
    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/bloodiedtexture.png");

    public BloodiedPlayerLayer(RenderLayerParent<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, InfectedPlayerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entitylivingbaseIn.isInvisible()){
            renderColoredCutoutModel(this.getParentModel(), TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
        }
    }
}
