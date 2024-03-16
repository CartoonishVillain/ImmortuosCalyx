package com.cartoonishvillain.immortuoscalyx;


import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(Constants.MOD_ID)
public class NeoforgeImmortuos {

    public NeoforgeImmortuos(IEventBus eventBus) {
        CommonImmortuos.init();
        PlayerInfectionCapability.loadDataAttachment(eventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void commandLoad(RegisterCommandsEvent event){
        SetInfectionCommands.register(event.getDispatcher());
        GetInfectionCommands.register(event.getDispatcher());
    }
}