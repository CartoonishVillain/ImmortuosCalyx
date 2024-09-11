package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.ImmortuosConfigCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.FabricEffects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricImmortuos implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonImmortuos.init();
        FabricEffects.initEffects();

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            SetInfectionCommands.register(dispatcher);
            GetInfectionCommands.register(dispatcher);
            ImmortuosConfigCommands.register(dispatcher);
        }));

        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft) -> {
            Services.PLATFORM.clientUpdate();
        });
    }
}
