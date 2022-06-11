package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.client.layers.BloodiedPlayerLayer;
import com.cartoonishvillain.immortuoscalyx.client.layers.DarkPlayerLayer;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderInfectedPlayerEntity extends HumanoidMobRenderer<InfectedPlayerEntity, HumanoidModel<InfectedPlayerEntity>> {

    protected final static ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/entity/infectedhuman.png");

    public RenderInfectedPlayerEntity(EntityRendererProvider.Context p_174169_) {
        super(p_174169_, new HumanoidModel<InfectedPlayerEntity>(p_174169_.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new DarkPlayerLayer(this));
        this.addLayer(new BloodiedPlayerLayer(this));
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
