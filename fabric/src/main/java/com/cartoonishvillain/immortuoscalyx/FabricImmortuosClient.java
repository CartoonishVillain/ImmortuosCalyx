package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.client.RenderDiverEntity;
import com.cartoonishvillain.immortuoscalyx.client.RenderInfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.FabricEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FabricImmortuosClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(FabricEntity.INFECTED_HUMAN.get(), RenderInfectedHumanEntity::new);
        EntityRendererRegistry.register(FabricEntity.INFECTED_DIVER.get(), RenderDiverEntity::new);

        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            Services.PLATFORM.clientUpdate();
        });
    }
}
