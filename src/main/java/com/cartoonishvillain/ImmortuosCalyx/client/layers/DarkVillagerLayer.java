package com.cartoonishvillain.ImmortuosCalyx.client.layers;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedVillagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DarkVillagerLayer extends RenderLayer<InfectedVillagerEntity, VillagerModel<InfectedVillagerEntity>> {
    protected final static ResourceLocation TEXTURE = new ResourceLocation(ImmortuosCalyx.MOD_ID, "textures/entity/darkenedvillagerskin.png");

    public DarkVillagerLayer(RenderLayerParent<InfectedVillagerEntity, VillagerModel<InfectedVillagerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, InfectedVillagerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!entitylivingbaseIn.isInvisible()){
            VertexConsumer bb = bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE));
            this.getParentModel().hatVisible(false);
            this.getParentModel().getHead().visible = true;
            this.getParentModel().renderToBuffer(matrixStackIn, bb, packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
    }
}
