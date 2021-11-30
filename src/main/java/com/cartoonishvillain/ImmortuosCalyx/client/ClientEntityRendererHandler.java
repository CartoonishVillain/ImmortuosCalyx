package com.cartoonishvillain.ImmortuosCalyx.client;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityRendererHandler {
    private static final ResourceLocation INFECTEDHUMAN = new ResourceLocation(ImmortuosCalyx.MOD_ID, "infectedplayer");
    private static final ModelLayerLocation BLOODY = new ModelLayerLocation(INFECTEDHUMAN, "added");

//    @SubscribeEvent
//    public static void layerdef(EntityRenderersEvent.RegisterLayerDefinitions event){
//        event.registerLayerDefinition(INFECTEDHUMAN, new BloodiedPlayerLayer());
//    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Register.INFECTEDVILLAGER.get(), RenderInfectedVillagerEntity::new);
        event.registerEntityRenderer(Register.INFECTEDIG.get(), RenderInfectedIGEntity::new);
        event.registerEntityRenderer(Register.INFECTEDPLAYER.get(), RenderInfectedPlayerEntity::new);
        event.registerEntityRenderer(Register.INFECTEDHUMAN.get(), RenderInfectedHumanEntity::new);
        event.registerEntityRenderer(Register.INFECTEDDIVER.get(), RenderInfectedDiverEntity::new);
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){


    }
}
