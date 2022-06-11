package com.cartoonishvillain.immortuoscalyx.events;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.ForgeImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.commands.SetInfectionRateCommand;
import com.cartoonishvillain.immortuoscalyx.infection.InfectionManager;
import com.cartoonishvillain.immortuoscalyx.infection.InfectionManagerCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerInfectionEffects {

    @SubscribeEvent
    public static void commandRegister(RegisterCommandsEvent event){
        SetInfectionRateCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof LivingEntity){
            InfectionManager provider = new InfectionManager();
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "infection"), provider);
        }
    }

    @SubscribeEvent
    public static void InfectionChat(ServerChatEvent event){
        Player player = event.getPlayer();
        if(ValidPlayer(player)) {
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                String name = player.getName().getString();
                String format = "<" + name + "> ";
                if (h.getInfectionProgress() >= ForgeImmortuosCalyx.config.EFFECTCHAT.get() && ForgeImmortuosCalyx.config.ANTICHAT.get() && ForgeImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {
                    event.setComponent(Component.literal(format + ChatFormatting.OBFUSCATED + event.getMessage()));
                }
                if (h.getInfectionProgress() >= ForgeImmortuosCalyx.config.EFFECTCHAT.get() && ForgeImmortuosCalyx.config.ANTICHAT.get() && !ForgeImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {
                    event.setCanceled(true);
                }
                ;//if the player's infection is @ or above 40%, they can no longer speak in text chat.
                if ((h.getInfectionProgress() >= ForgeImmortuosCalyx.config.EFFECTCHAT.get() && !player.getCommandSenderWorld().isClientSide() && ForgeImmortuosCalyx.config.INFECTEDCHATNOISE.get()))
                    player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT.get(), SoundSource.PLAYERS, 0.5f, 2f);
            });
        }
    }

    private static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }
}
