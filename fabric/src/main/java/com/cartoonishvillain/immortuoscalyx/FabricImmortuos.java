package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricImmortuos implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonImmortuos.init();

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            SetInfectionCommands.register(dispatcher);
            GetInfectionCommands.register(dispatcher);
        }));
    }
}
