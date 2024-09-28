package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.client.entity.layer.BloodiedHumanLayer;
import com.cartoonishvillain.immortuoscalyx.client.entity.layer.DarkPlayerLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

public class RenderInfectedHumanEntity extends HumanoidMobRenderer<InfectedHumanEntity, HumanoidModel<InfectedHumanEntity>> {
    protected final static ResourceLocation TEXTURE = DefaultPlayerSkin.getDefaultTexture();
    boolean slimModel = false;

    public RenderInfectedHumanEntity(EntityRendererProvider.Context pContext) {
        super(pContext, new HumanoidModel<>(pContext.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new DarkPlayerLayer(this));
        this.addLayer(new BloodiedHumanLayer(this));
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedHumanEntity infectedHumanEntity) {
        ResourceLocation textureToRender = TEXTURE;
        if (infectedHumanEntity.getPUUID().isPresent() && infectedHumanEntity.getResourceLocation() == null) {
            if (infectedHumanEntity.skinProfile.isEmpty()) {
                SkullBlockEntity.fetchGameProfile(infectedHumanEntity.getPUUID().get()).thenAccept(p -> infectedHumanEntity.skinProfile = p);
            }
            textureToRender = infectedHumanEntity.skinProfile.map(s -> Minecraft.getInstance().getSkinManager().getInsecureSkin(s)).map(PlayerSkin::texture).orElse(TEXTURE);
            boolean isSlim = infectedHumanEntity.skinProfile.map(s -> Minecraft.getInstance().getSkinManager().getInsecureSkin(s)).map(PlayerSkin::model).orElse(PlayerSkin.Model.WIDE) == PlayerSkin.Model.SLIM;
            if (textureToRender != DefaultPlayerSkin.getDefaultTexture() && textureToRender != DefaultPlayerSkin.get(infectedHumanEntity.getPUUID().get()).texture()) {
                infectedHumanEntity.setResourceLocation(textureToRender);
                infectedHumanEntity.setSlim(isSlim);
                slimModel = isSlim;
            }
        } else if (infectedHumanEntity.getResourceLocation() != null) {
            textureToRender = infectedHumanEntity.getResourceLocation();
        }
        return textureToRender;
    }
}
