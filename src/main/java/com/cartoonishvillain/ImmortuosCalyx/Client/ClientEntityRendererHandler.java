package com.cartoonishvillain.ImmortuosCalyx.Client;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEntityRendererHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){

        EntityRenderers.register(Register.INFECTEDVILLAGER.get(), RenderInfectedVillagerEntity::new);
        EntityRenderers.register(Register.INFECTEDIG.get(), RenderInfectedIGEntity::new);
        EntityRenderers.register(Register.INFECTEDPLAYER.get(), RenderInfectedPlayerEntity::new);
        EntityRenderers.register(Register.INFECTEDHUMAN.get(), RenderInfectedHumanEntity::new);
        EntityRenderers.register(Register.INFECTEDDIVER.get(), RenderInfectedDiverEntity::new);
//        RenderingRegistry.registerEntityRenderingHandler(Register.INFECTEDHUMAN.get(), RenderInfectedHumanEntity::new);
//        RenderingRegistry.registerEntityRenderingHandler(Register.INFECTEDDIVER.get(), RenderInfectedDiverEntity::new);
//        RenderingRegistry.registerEntityRenderingHandler(Register.INFECTEDVILLAGER.get(), RenderInfectedVillagerEntity::new);
//        RenderingRegistry.registerEntityRenderingHandler(Register.INFECTEDIG.get(), RenderInfectedIGEntity::new);
//        RenderingRegistry.registerEntityRenderingHandler(Register.INFECTEDPLAYER.get(), RenderInfectedPlayerEntity::new);
    }
}
