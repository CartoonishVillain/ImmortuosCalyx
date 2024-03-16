package com.cartoonishvillain.immortuoscalyx;

import com.cartoonishvillain.immortuoscalyx.commands.GetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionCommands;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability;
import com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapabilityManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event){
        SetInfectionCommands.register(event.getDispatcher());
        GetInfectionCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void playerRegister(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player){
            PlayerInfectionCapabilityManager provider = new PlayerInfectionCapabilityManager();
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "infectiondata"), provider);
        }
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            Player originalPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();

            AtomicInteger infectionPercent = new AtomicInteger(Integer.MAX_VALUE);
            AtomicInteger infectionTicks = new AtomicInteger(Integer.MAX_VALUE);

            originalPlayer.revive();

            originalPlayer.getCapability(PlayerInfectionCapability.INSTANCE).ifPresent(h->{
                infectionTicks.set(h.getInfectionPercent());
                infectionPercent.set(h.getTicks());
            });

            originalPlayer.kill();

            newPlayer.getCapability(PlayerInfectionCapability.INSTANCE).ifPresent(h->{
                h.setTicks(infectionTicks.get());
                h.setInfectionPercent(infectionPercent.get());
            });
        }
    }
}
