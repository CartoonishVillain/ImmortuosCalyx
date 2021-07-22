package com.cartoonishvillain.ImmortuosCalyx.Events;

import com.google.common.util.concurrent.AtomicDouble;
import com.cartoonishvillain.ImmortuosCalyx.Entity.*;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionDamage;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManager;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
            Player player = event.player;
            player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSPEED.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
                    if(temperature > 0.9 && ImmortuosCalyx.config.HEATSLOW.get()){player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 0, true, false));}
                    else if(temperature < 0.275 && ImmortuosCalyx.config.COLDFAST.get()){player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, true, false));}
                }
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTSTRENGTH.get()){
                    BlockPos CurrentPosition = new BlockPos(player.getX(), player.getY(), player.getZ());
                    float temperature = player.level.getBiomeManager().getBiome(CurrentPosition).getTemperature(CurrentPosition);
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
        player.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            String name = player.getName().getString();
            String format = "<" + name + "> ";
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {event.setComponent(new TextComponent(format +ChatFormatting.OBFUSCATED + event.getMessage()));}
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && ImmortuosCalyx.config.ANTICHAT.get() && !ImmortuosCalyx.config.FORMATTEDINFECTCHAT.get()) {event.setCanceled(true);};//if the player's infection is @ or above 40%, they can no longer speak in text chat.
            if((h.getInfectionProgress() >= ImmortuosCalyx.config.EFFECTCHAT.get() && !player.getCommandSenderWorld().isClientSide() && ImmortuosCalyx.config.INFECTEDCHATNOISE.get()))player.level.playSound(null, player.blockPosition(), Register.HUMANAMBIENT.get(), SoundSource.PLAYERS, 0.5f, 2f);
        });
    }

    @SubscribeEvent public static void InfectOtherPlayer(AttackEntityEvent event){
        if(event.getEntity() instanceof Player && event.getTarget() instanceof Player && ImmortuosCalyx.config.PVPCONTAGION.get() && (!ImmortuosCalyx.DimensionExclusion.contains(event.getTarget().level.dimension().location()) || !ImmortuosCalyx.commonConfig.PLAYERINFECTIONINCLEANSE.get())){
            Player target = (Player) event.getTarget(); //the player who got hit
            Player aggro = (Player) event.getEntity(); //the player that hit
            int protection = target.getArmorValue(); //grabs the armor value of the target
            AtomicInteger infectionRateGrabber = new AtomicInteger(); //funky value time
            aggro.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                int infectionProgression = h.getInfectionProgress();
                infectionRateGrabber.addAndGet(infectionProgression); // sets the atomic int equal to the infection % of the attacker

            });
            AtomicDouble resistRateGrabber = new AtomicDouble(); //grab resistance from
            target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                Double resist = Double.valueOf(h.getResistance());
                resistRateGrabber.addAndGet(resist);
            });
            AtomicBoolean infectedGrabber = new AtomicBoolean(false); //A surprise bool that will help us later
            int convert = infectionRateGrabber.intValue();// converts the atomic int into a normal int for better math
            double resist = (double) resistRateGrabber.get();
            int threshold = ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get();
            double armorInfectResist = ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get();
            if(convert >= threshold){ // if the attacker's infection rate is at or above the threshold.
                int conversionThreshold = (int) ((convert - (protection*armorInfectResist))/resist); // creates mimimum score needed to not get infected
                Random rand = new Random();
                if(conversionThreshold > rand.nextInt(100)){ // rolls for infection. If random value rolls below threshold, target is at risk of infection.
                    target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                        int infect = ImmortuosCalyx.config.PVPCONTAGIONAMOUNT.get();
                        if(h.getInfectionProgress() == 0){h.addInfectionProgress(infect); infectedGrabber.set(true);} // if target is not already infected, and the risk for infection exists, they are freshly infected. Surprise bool gets activated
                    });
                }
            }
            if(infectedGrabber.get()){aggro.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{ //if surprise bool is activated, it means the aggressor player successfully infected someone. Removing part of the parasite and having it get on someone else.
                h.addInfectionProgress(-ImmortuosCalyx.config.PVPCONTAGIONRELIEF.get()); });//because of that, symptoms are reduced.
                if(!aggro.getCommandSenderWorld().isClientSide)aggro.level.playSound(null, aggro.blockPosition(), Register.HUMANHURT.get(), SoundSource.PLAYERS, 1f, 1.2f);}
        }
    }

    @SubscribeEvent public static void InfectFromMobAttack(net.minecraftforge.event.entity.living.LivingAttackEvent event){
        if (event.getSource().getEntity() instanceof LivingEntity && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.HOSTILEINFECTIONINCLEANSE.get())) {
            LivingEntity aggro = (LivingEntity) event.getSource().getEntity();
            LivingEntity target = event.getEntityLiving();
            int convert = 0;
            int protection = (int) (target.getArmorValue() * ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get());
            AtomicDouble InfectionResGrabber = new AtomicDouble(1);
            AtomicBoolean InfectedGrabber = new AtomicBoolean(false);
            target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                InfectionResGrabber.set(h.getResistance());
                if (h.getInfectionProgress() > 0) {
                    InfectedGrabber.getAndSet(true);
                }
            });
            double resist = InfectionResGrabber.get();
            if (!InfectedGrabber.get()) { // if target is already infected, it isn't worth running all of this.
                if (aggro instanceof Player) {
                    if(target instanceof  Player) return;
                    else{
                        AtomicInteger inf = new AtomicInteger();
                        aggro.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                            if(h.getInfectionProgress() >= ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get()){
                                inf.set(h.getInfectionProgress());
                            }
                        });
                        convert = inf.get();
                    }
                } else if (aggro instanceof InfectedHumanEntity || aggro instanceof InfectedDiverEntity || aggro instanceof InfectedPlayerEntity) {
                    convert = 95;
                } else if (aggro instanceof InfectedIGEntity) {
                    convert = 75;
                } else if (aggro instanceof InfectedVillagerEntity) {
                    if (target instanceof IronGolem || target instanceof AbstractVillager) {
                        convert = 100; //villagers have a higher chance of infecting iron golems and other villagers.
                    } else convert = 55;
                } else {
                    AtomicInteger inf = new AtomicInteger(0);
                    aggro.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                        inf.set(h.getInfectionProgress());
                    });
                    convert = inf.get();
                }
                int InfectThreshold = (int) ((convert - protection)/resist);
                Random rand = new Random();
                if(InfectThreshold > rand.nextInt(100)){
                    target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                        h.addInfectionProgress(1);
                    });
                }
            }
        }
    }

    static Item[] rawItem = new Item[]{Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH};
    @SubscribeEvent
    public static void rawFood(LivingEntityUseItemEvent.Finish event){
        if(event.getEntity() instanceof Player && (!ImmortuosCalyx.DimensionExclusion.contains(event.getEntity().level.dimension().location()) || !ImmortuosCalyx.commonConfig.RAWFOODINFECTIONINCLEANSE.get())){
            Player player = (Player) event.getEntity();
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
}
