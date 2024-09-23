package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.client.entity.layer.BloodiedHumanLayer;
import com.cartoonishvillain.immortuoscalyx.client.entity.layer.DarkPlayerLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedHumanEntity extends HumanoidMobRenderer<InfectedHumanEntity, HumanoidModel<InfectedHumanEntity>> {
    protected final static ResourceLocation TEXTURE = DefaultPlayerSkin.getDefaultTexture();

    public RenderInfectedHumanEntity(EntityRendererProvider.Context pContext) {
        super(pContext, new HumanoidModel<InfectedHumanEntity>(pContext.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new DarkPlayerLayer(this));
        this.addLayer(new BloodiedHumanLayer(this));
    }


    @Override
    public ResourceLocation getTextureLocation(InfectedHumanEntity infectedHumanEntity) {
        try {
            if (infectedHumanEntity.getPUUID().isPresent() && !infectedHumanEntity.getPUsername().isEmpty()) {
                if (infectedHumanEntity.getResourceLocation() == null) { //while the internal skin resource location is null, make the request
                    infectedHumanEntity.setResourceLocation(TEXTURE); //Set the location to the default so we only make the one request
                    Minecraft.getInstance().getSkinManager().getOrLoad(new GameProfile(infectedHumanEntity.getPUUID().get(), infectedHumanEntity.getPUsername())).thenAccept(playerSkin -> {
                                infectedHumanEntity.setResourceLocation(playerSkin.texture()); //set the texture to the new one when the skin arrives.
                            }
                    );
                } else return infectedHumanEntity.getResourceLocation(); //If the values are all present, display them.
            } else return TEXTURE; //If the values aren't set, display the default
        } catch (NullPointerException e) {
            return TEXTURE; //If all else fails, display the default.
        }
        return TEXTURE;
    }
}
