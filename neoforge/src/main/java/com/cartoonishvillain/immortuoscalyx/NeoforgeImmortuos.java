package com.cartoonishvillain.immortuoscalyx;


import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.cartoonishvillain.immortuoscalyx.register.NeoEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(Constants.MOD_ID)
public class NeoforgeImmortuos {

    public NeoforgeImmortuos(IEventBus eventBus) {
        CommonImmortuos.init();
        NeoEffects.init(eventBus);
        PlayerInfectionCapability.loadDataAttachment(eventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commandLoad(RegisterCommandsEvent event){
        SetInfectionCommands.register(event.getDispatcher());
        GetInfectionCommands.register(event.getDispatcher());
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            Services.PLATFORM.clientUpdate();
        }
    }
}