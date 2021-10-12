package com.cartoonishvillain.ImmortuosCalyx.Events;

import com.cartoonishvillain.ImmortuosCalyx.Entity.*;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionDamage;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionHandler;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManager;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerInfectionEventManager {

    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof LivingEntity){
            InfectionManager provider = new InfectionManager();
            event.addCapability(new ResourceLocation(ImmortuosCalyx.MOD_ID, "infection"), provider);
        }
    }

    @SubscribeEvent
    public static void InfectionTicker(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START){
        PlayerEntity player = event.player;
        player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h ->{
            if(h.getInfectionProgress() >= 1){
                h.addInfectionTimer(1);
                int timer = ImmortuosCalyx.config.INFECTIONTIMER.get();
                if(h.getInfectionTimer() >= timer){
                    h.addInfectionProgress(1);
                    h.addInfectionTimer(-timer);
                    EffectController(event.player);
                }
            }
        });}
    }

    @SubscribeEvent public static void InfectionTickEffects(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START){
            PlayerEntity player = event.player;
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSPEED.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
                    if(temperature > 0.9 && ImmortuosCalyx.config.HEATSLOW.get()){player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 0, true, false));}
                    else if(temperature < 0.275 && ImmortuosCalyx.config.COLDFAST.get()){player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSTRENGTH.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
                    if(temperature > 0.275 && ImmortuosCalyx.config.WARMWEAKNESS.get()){player.addEffect(new EffectInstance(Effects.WEAKNESS, 5, 0, true, false));}
                    else if (temperature <= 0.275 && ImmortuosCalyx.config.COLDSTRENGTH.get()) {player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTBLIND.get() && ImmortuosCalyx.config.BLINDNESS.get()){
                    player.addEffect(new EffectInstance(Effects.BLINDNESS, 50, 1, true, false));
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTDAMAGE.get()){
                    Random random = new Random();
                    int randomValue = random.nextInt(100);
                    if(randomValue < 1 && ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0){
                        player.hurt(InfectionDamage.causeInfectionDamage(player), ImmortuosCalyx.config.INFECTIONDAMAGE.get());
                    }
                }
            });

        }
    }

    @SubscribeEvent
    public static void InfectionChat(ServerChatEvent event){
        PlayerEntity player = event.getPlayer();
        player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            String name = player.getName().getString();
            String format = "<" + name + "> ";
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {event.setComponent(new StringTextComponent(format +TextFormatting.OBFUSCATED + event.getMessage()));}
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && !ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {event.setCanceled(true);};//if the player's infection is @ or above 40%, they can no longer speak in text chat.
            if((h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && !player.getCommandSenderWorld().isClientSide() && ImmortuosCalyx.config.INFECTEDCHATNOISE.get()))player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT.get(), SoundCategory.PLAYERS, 0.5f, 2f);
        });
    }

    @SubscribeEvent
    public static void InfectMobFromAttack(LivingAttackEvent event){
        if(event.getSource().getEntity() instanceof LivingEntity && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.HOSTILEINFECTIONINCLEANSE.get()) && !event.getEntityLiving().level.isClientSide()){
            LivingEntity aggro = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();

            if(aggro instanceof PlayerEntity){
                if(victim instanceof PlayerEntity) InfectionHandler.infectPlayerByPlayer((PlayerEntity) aggro,(PlayerEntity) victim, 1);
                else InfectionHandler.infectEntityByPlayer((PlayerEntity) aggro, victim, 1);
            }
            else if (aggro instanceof InfectedHumanEntity || aggro instanceof InfectedDiverEntity || aggro instanceof InfectedPlayerEntity) InfectionHandler.infectEntity(victim, 95, 1);
            else if (aggro instanceof InfectedIGEntity) InfectionHandler.infectEntity(victim, 75, 1);
            else if (aggro instanceof InfectedVillagerEntity){
                if(victim instanceof AbstractVillagerEntity || victim instanceof GolemEntity) InfectionHandler.infectEntity(victim, 100, 1);
                else InfectionHandler.infectEntity(victim, 55, 1);
            }
            else InfectionHandler.infectEntity(aggro, victim, 1);
        }
    }


    static Item[] rawItem = new Item[]{Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH};
    @SubscribeEvent
    public static void rawFood(LivingEntityUseItemEvent.Finish event){
        if(event.getEntity() instanceof PlayerEntity && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.RAWFOODINFECTIONINCLEANSE.get())){
            PlayerEntity player = (PlayerEntity) event.getEntity();
            boolean raw = false;
            for(Item item : rawItem){if(item.equals(event.getItem().getItem())){raw = true; break;}}
            if(raw){
                player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                    if(h.getInfectionProgress() == 0){
                        Random rand = new Random();
                        if(rand.nextInt(100) < (ImmortuosCalyx.config.RAWFOODINFECTIONVALUE.get()/(h.getResistance()))) h.setInfectionProgress(1);
                    }
                });
            }
        }
    }

    private static void EffectController(PlayerEntity inflictedPlayer){
        inflictedPlayer.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            int progressionLogic = h.getInfectionProgress(); //this used to be a switch. I love switches. But switches require constants. These are not constant values anymore. Too bad.
                if(progressionLogic == ImmortuosCalyx.config.EFFECTMESSAGEONE.get()){
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "Your throat feels sore"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTMESSAGETWO.get()){
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "Your mind feels foggy"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTCHAT.get()){
                    if (ImmortuosCalyx.config.ANTICHAT.get())
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You feel something moving around in your head, you try to yell, but nothing comes out"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get()){
                    if (ImmortuosCalyx.config.PVPCONTAGION.get())
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "There is something on your skin and you can't get it off.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTSPEED.get()){
                    if (ImmortuosCalyx.config.COLDFAST.get() && ImmortuosCalyx.config.HEATSLOW.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You start feeling ill in warm environments, and better in cool ones.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.COLDFAST.get() && !ImmortuosCalyx.config.HEATSLOW.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You begin to feel better in cool environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.HEATSLOW.get() && !ImmortuosCalyx.config.COLDFAST.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You begin feeling ill in warm environments..."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTSTRENGTH.get()){
                    if (ImmortuosCalyx.config.COLDSTRENGTH.get() && ImmortuosCalyx.config.WARMWEAKNESS.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You begin to feel weak in all but the coldest environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.COLDSTRENGTH.get() && !ImmortuosCalyx.config.WARMWEAKNESS.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You begin to feel strong in cold environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.WARMWEAKNESS.get() && !ImmortuosCalyx.config.COLDSTRENGTH.get())
                        inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You begin to feel weak in warm environments.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTBLIND.get()){
                    if(ImmortuosCalyx.config.BLINDNESS.get())
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "Your vision gets darker and darker.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTDAMAGE.get()){
                    if(ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0)
                    inflictedPlayer.sendMessage(new StringTextComponent(TextFormatting.RED + "You feel an overwhelming pain in your head..."), inflictedPlayer.getUUID());}
        });
    }
}
