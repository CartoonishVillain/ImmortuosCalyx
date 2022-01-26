package com.cartoonishvillain.ImmortuosCalyx.events;

import com.cartoonishvillain.ImmortuosCalyx.entity.*;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionDamage;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionHandler;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionManager;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
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
        Player player = event.player;
        player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h ->{
            if(h.getInfectionProgress() >= 1 && ValidPlayer(event.player)){
                h.addInfectionTimer(1);
                int timer = ImmortuosCalyx.config.INFECTIONTIMER.get();
                if(h.getInfectionTimer() >= timer){
                    if(h.isResistant() && h.getInfectionProgress() == ImmortuosCalyx.config.EFFECTIMPEDEMENT.get()) {return;}
                    if(h.isResistant() && h.getInfectionProgress() > ImmortuosCalyx.config.EFFECTIMPEDEMENT.get()) {h.setInfectionProgress(ImmortuosCalyx.config.EFFECTIMPEDEMENT.get()); return;}
                    h.addInfectionProgress(1);
                    h.addInfectionTimer(-timer);
                    EffectController(event.player);
                }
            }
        });}
    }

    @SubscribeEvent public static void InfectionTickEffects(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && ValidPlayer(event.player)){
            Player player = event.player;
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSPEED.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getBaseTemperature();
                    if(temperature > 0.9 && ImmortuosCalyx.config.HEATSLOW.get()){player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 0, true, false));}
                    else if(temperature < 0.275 && ImmortuosCalyx.config.COLDFAST.get()){player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTWATERBREATH.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getBaseTemperature();
                    if(ImmortuosCalyx.config.WATERBREATHING.get()){player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 5, 0, true, false));}
                    if(ImmortuosCalyx.config.COLDCONDUITPOWER.get() && (temperature <= 0.3 || player.level.getBiomeManager().getBiome(CurrentPosition).getRegistryName().getPath().contains("cold")) && player.isInWaterOrBubble()){player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSTRENGTH.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getBaseTemperature();
                    if(temperature > 0.275 && ImmortuosCalyx.config.WARMWEAKNESS.get()){player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 0, true, false));}
                    else if (temperature <= 0.275 && ImmortuosCalyx.config.COLDSTRENGTH.get()) {player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTBLIND.get() && ImmortuosCalyx.config.BLINDNESS.get()){
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 1, true, false));
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
        Player player = event.getPlayer();
        if(ValidPlayer(player)) {
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                String name = player.getName().getString();
                String format = "<" + name + "> ";
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {
                    event.setComponent(new TextComponent(format + ChatFormatting.OBFUSCATED + event.getMessage()));
                }
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && !ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {
                    event.setCanceled(true);
                }
                ;//if the player's infection is @ or above 40%, they can no longer speak in text chat.
                if ((h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && !player.getCommandSenderWorld().isClientSide() && ImmortuosCalyx.config.INFECTEDCHATNOISE.get()))
                    player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT.get(), SoundSource.PLAYERS, 0.5f, 2f);
            });
        }
    }

    @SubscribeEvent
    public static void InfectMobFromAttack(LivingAttackEvent event){
        if(event.getSource().getEntity() instanceof LivingEntity && !event.getEntityLiving().level.isClientSide && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.HOSTILEINFECTIONINCLEANSE.get() && !event.getEntityLiving().level.isClientSide())){
            LivingEntity aggro = (LivingEntity) event.getSource().getEntity();
            LivingEntity victim = event.getEntityLiving();

            if(aggro instanceof Player && ValidPlayer((Player) aggro)){
                if(victim instanceof Player) InfectionHandler.infectPlayerByPlayer((Player) aggro,(Player) victim, 1);
                else InfectionHandler.infectEntityByPlayer((Player) aggro, victim, 1);
            }
            else if (aggro instanceof InfectedHumanEntity || aggro instanceof InfectedDiverEntity || aggro instanceof InfectedPlayerEntity) InfectionHandler.infectEntity(victim, 95, 1);
            else if (aggro instanceof InfectedIGEntity) InfectionHandler.infectEntity(victim, 75, 1);
            else if (aggro instanceof InfectedVillagerEntity){
                if(victim instanceof Villager || victim instanceof AbstractGolem) InfectionHandler.infectEntity(victim, 100, 1);
                else InfectionHandler.infectEntity(victim, 55, 1);
            }
            else if(!(aggro instanceof Player)) InfectionHandler.infectEntity(aggro, victim, 1);
        }
    }

    static ArrayList<Item> rawItem = new ArrayList<>(Arrays.asList(Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH));
    @SubscribeEvent
    public static void rawFood(LivingEntityUseItemEvent.Finish event){
        if(event.getEntity() instanceof Player && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.RAWFOODINFECTIONINCLEANSE.get())){
            Player player = (Player) event.getEntity();
            boolean raw = rawItem.contains(event.getItem().getItem());
            if(raw){
                InfectionHandler.bioInfect(player,  ImmortuosCalyx.config.RAWFOODINFECTIONVALUE.get(), 1);
            }
        }
    }

    private static void EffectController(Player inflictedPlayer){
        inflictedPlayer.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            int progressionLogic = h.getInfectionProgress(); //this used to be a switch. I love switches. But switches require constants. These are not constant values anymore. Too bad.
                if(progressionLogic == ImmortuosCalyx.config.EFFECTMESSAGEONE.get()){
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your throat feels sore"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTMESSAGETWO.get()){
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your mind feels foggy"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTCHAT.get()){
                    if (ImmortuosCalyx.config.ANTICHAT.get())
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You feel something moving around in your head, you try to yell, but nothing comes out"), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get()){
                    if (ImmortuosCalyx.config.PVPCONTAGION.get())
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "There is something on your skin and you can't get it off.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTSPEED.get()){
                    if (ImmortuosCalyx.config.COLDFAST.get() && ImmortuosCalyx.config.HEATSLOW.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You start feeling ill in warm environments, and better in cool ones.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.COLDFAST.get() && !ImmortuosCalyx.config.HEATSLOW.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel better in cool environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.HEATSLOW.get() && !ImmortuosCalyx.config.COLDFAST.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin feeling ill in warm environments..."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTWATERBREATH.get()){
                    if(ImmortuosCalyx.config.WATERBREATHING.get()){
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.BLUE + "You begin to feel relieved while diving into the murky depths..."), inflictedPlayer.getUUID());
                    }
                }

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTSTRENGTH.get()){
                    if (ImmortuosCalyx.config.COLDSTRENGTH.get() && ImmortuosCalyx.config.WARMWEAKNESS.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel weak in all but the coldest environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.COLDSTRENGTH.get() && !ImmortuosCalyx.config.WARMWEAKNESS.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel strong in cold environments.."), inflictedPlayer.getUUID());
                    else if (ImmortuosCalyx.config.WARMWEAKNESS.get() && !ImmortuosCalyx.config.COLDSTRENGTH.get())
                        inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You begin to feel weak in warm environments.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTBLIND.get()){
                    if(ImmortuosCalyx.config.BLINDNESS.get())
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "Your vision gets darker and darker.."), inflictedPlayer.getUUID());}

                else if(progressionLogic == ImmortuosCalyx.config.EFFECTDAMAGE.get()){
                    if(ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0)
                    inflictedPlayer.sendMessage(new TextComponent(ChatFormatting.RED + "You feel an overwhelming pain in your head..."), inflictedPlayer.getUUID());}
        });
    }

    private static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }
}
