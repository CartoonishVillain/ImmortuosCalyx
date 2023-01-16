package com.cartoonishvillain.immortuoscalyx.component;

import com.cartoonishvillain.immortuoscalyx.damage.InfectionDamage;
import com.cartoonishvillain.immortuoscalyx.entities.*;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComponentTicker {

    public static void tickEntity(LivingEntity livingEntity){
        if(!livingEntity.level.isClientSide){
            resistanceDownTick(livingEntity);
            InfectionTicker(livingEntity);
            InfectionTickPotionEffects(livingEntity);
        }
    }


    private static void resistanceDownTick(LivingEntity livingEntity){
        if((!(livingEntity instanceof Player) || ValidPlayer((Player) livingEntity)))
            Services.PLATFORM.addResistance(-0.001, livingEntity);
            if(Services.PLATFORM.getResistance(livingEntity) < 1) {
                Services.PLATFORM.setResistance(1, livingEntity);
            }
    }


    public static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }

    private static void InfectionTicker(LivingEntity entity){
            if(Services.PLATFORM.getInfectionProgress(entity) >= 1 && (!(entity instanceof Player) || ValidPlayer((Player) entity))){
                Services.PLATFORM.addInfectionTimer(1, entity);
                int timer = Services.PLATFORM.getInfectionTimer();
                if(Services.PLATFORM.getInfectionTimerStat(entity) >= timer){
                    Services.PLATFORM.addInfectionTimer(-timer, entity);
                    if(Services.PLATFORM.getResistantDosage(entity) && Services.PLATFORM.getInfectionProgress(entity) == Services.PLATFORM.getEffectImpediment()) {return;}
                    if(Services.PLATFORM.getResistantDosage(entity) && Services.PLATFORM.getInfectionProgress(entity) > Services.PLATFORM.getEffectImpediment()) {Services.PLATFORM.setInfectionProgress(Services.PLATFORM.getEffectImpediment(), entity); return;}
                    Services.PLATFORM.addInfectionProgress(1, entity);

                    if(entity instanceof Player) {PlayerMessageSender((Player) entity);}
                }
            }
    }

    private static void InfectionTickPotionEffects(LivingEntity entity){
        if(entity instanceof Player){
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getEffectSpeed()){
                BlockPos CurrentPosition = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
                float temperature = entity.level.getBiomeManager().getBiome(CurrentPosition).value().getBaseTemperature();
                if(temperature > 0.9 && Services.PLATFORM.getHeatSlow()){entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 0, true, false));}
                else if(temperature < 0.275 && Services.PLATFORM.getColdFast()){entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, true, false));}
            }

            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getEffectWaterBreathing()){
                BlockPos CurrentPosition = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
                float temperature = entity.level.getBiomeManager().getBiome(CurrentPosition).value().getBaseTemperature();
                if(Services.PLATFORM.getWaterBreathing()){entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 5, 0, true, false));}
                if(Services.PLATFORM.getColdConduitPower() && (temperature <= 0.3 || entity.level.getBiomeManager().getBiome(CurrentPosition).toString().contains("cold")) && entity.isInWaterOrBubble()){entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 5, 0, true, false));}
            }

            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getEffectStrength()){
                BlockPos CurrentPosition = new BlockPos(entity.getX(), entity.getY(), entity.getZ());
                float temperature = entity.level.getBiomeManager().getBiome(CurrentPosition).value().getBaseTemperature();
                if(temperature > 0.275 && Services.PLATFORM.getWarmWeakness()){entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 0, true, false));}
                else if (temperature <= 0.275 && Services.PLATFORM.getColdStrength()) {entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0, true, false));}
            }
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getEffectBlind() && Services.PLATFORM.getBlindness()){
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 1, true, false));
            }
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getEffectDamage()){
                Random random = new Random();
                int randomValue = random.nextInt(100);
                if(randomValue < 1 && Services.PLATFORM.getInfectionDamage() > 0){
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), Services.PLATFORM.getInfectionDamage());
                }
            }
        } else if(entity instanceof Villager){
            if(!Services.PLATFORM.getFollowerStatus(entity)) {
                if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerSlowTwo()) { // greater than or equal to 25
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
                } else if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerSlowOne()) { //5-24%
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
                }
                if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerLethal()) {
                    Random rand = new Random();
                    int random = rand.nextInt(100);
                    if (random < 1 && Services.PLATFORM.getInfectionDamage() > 0) {
                        entity.hurt(InfectionDamage.causeInfectionDamage(entity), Services.PLATFORM.getInfectionDamage());
                    }
                }
            }
            else {
                if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerSlowTwo() * Services.PLATFORM.getFollowerImmunity()) { // greater than or equal to 25
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
                } else if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerSlowOne() * Services.PLATFORM.getFollowerImmunity()) { //5-24%
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
                }
                if (Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getVillagerLethal() * Services.PLATFORM.getFollowerImmunity()) {
                    Random rand = new Random();
                    int random = rand.nextInt(100);
                    if (random < 1 && Services.PLATFORM.getInfectionDamage() > 0) {
                        entity.hurt(InfectionDamage.causeInfectionDamage(entity), Services.PLATFORM.getInfectionDamage());
                    }
                }
            }
        } else if(entity instanceof IronGolem && !(entity instanceof InfectedEntity)){
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getIronSlow()){ entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false)); }
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getIronWeak()){ entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false)); }
            if(Services.PLATFORM.getInfectionProgress(entity) >= Services.PLATFORM.getIronLethal()){
                Random rand = new Random();
                int random = rand.nextInt(100);
                if(random < 1 && Services.PLATFORM.getInfectionDamage() > 0){
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), Services.PLATFORM.getInfectionDamage());
                }
            }
        }
    }

    private static void PlayerMessageSender(Player afflictedPlayer){
        int progressionLogic = Services.PLATFORM.getInfectionProgress(afflictedPlayer); //this used to be a switch. I love switches. But switches require constants. These are not constant values anymore. Too bad.
        if(progressionLogic == Services.PLATFORM.getEffectMsgOne()){
            afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "Your throat feels sore"));}

        else if(progressionLogic == Services.PLATFORM.getEffectMsgTwo()){
            afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "Your mind feels foggy"));}

        else if(progressionLogic == Services.PLATFORM.getEffectChat()){
            if (Services.PLATFORM.getAntiChat())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You feel something moving around in your head, you try to yell, but nothing comes out"));}

        else if(progressionLogic == Services.PLATFORM.getPlayerInfection()){
            if (Services.PLATFORM.getPVPContagion())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "There is something on your skin and you can't get it off.."));}

        else if(progressionLogic == Services.PLATFORM.getEffectSpeed()){
            if (Services.PLATFORM.getColdFast() && Services.PLATFORM.getHeatSlow())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You start feeling ill in warm environments, and better in cool ones.."));
            else if (Services.PLATFORM.getColdFast() && !Services.PLATFORM.getHeatSlow())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You begin to feel better in cool environments.."));
            else if (Services.PLATFORM.getHeatSlow() && !Services.PLATFORM.getColdFast())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You begin feeling ill in warm environments..."));}

        else if(progressionLogic == Services.PLATFORM.getEffectWaterBreathing()){
            if(Services.PLATFORM.getWaterBreathing()){
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.BLUE + "You begin to feel relieved while diving into the murky depths..."));
            }
        }
        else if(progressionLogic == Services.PLATFORM.getEffectStrength()){
            if (Services.PLATFORM.getColdStrength() && Services.PLATFORM.getWarmWeakness())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You begin to feel weak in all but the coldest environments.."));
            else if (Services.PLATFORM.getColdStrength() && !Services.PLATFORM.getWarmWeakness())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You begin to feel strong in cold environments.."));
            else if (Services.PLATFORM.getWarmWeakness() && !Services.PLATFORM.getColdStrength())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You begin to feel weak in warm environments.."));}

        else if(progressionLogic == Services.PLATFORM.getEffectBlind()){
            if(Services.PLATFORM.getBlindness())
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "Your vision gets darker and darker.."));}

        else if(progressionLogic == Services.PLATFORM.getEffectDamage()){
            if(Services.PLATFORM.getInfectionDamage() > 0)
                afflictedPlayer.sendSystemMessage(Component.literal(ChatFormatting.RED + "You feel an overwhelming pain in your head..."));}
    }

    private static final ArrayList<String> DISQUALIFYINGDAMAGE = new ArrayList<>(List.of("lightningBolt", "lava", "outOfWorld", "explosion"));

    public static void infectedEntityConverter(DamageSource damageSource, LivingEntity entity) {
        if (damageSource.msgId.equals("infection")) {
            Level world = entity.level;
            if (!world.isClientSide()) {
                ServerLevel serverWorld = (ServerLevel) world;
                infectedEntitySummoner(entity, serverWorld);
            }
        } else if (!DISQUALIFYINGDAMAGE.contains(damageSource.msgId) && Services.PLATFORM.isInfectedDeath()) {
            Level world = entity.getLevel();
            if (!world.isClientSide() && !(entity instanceof Villager) && !(entity instanceof InfectedEntity)) {
                    //Chance to be converted is multiplied by 2 for every % above 50.
                    float chance = (Services.PLATFORM.getInfectionProgress(entity) - 50) * 2;
                    if (chance > world.getRandom().nextInt(100)) {
                        ServerLevel serverWorld = (ServerLevel) world;
                        infectedEntitySummoner(entity, serverWorld);
                    }
            }
        }
    }

    private static void infectedEntitySummoner(LivingEntity entity, ServerLevel serverWorld){
        if(entity instanceof Player && Services.PLATFORM.isClearToReplace(entity)){
            InfectedPlayerEntity infectedPlayerEntity = new InfectedPlayerEntity(Services.PLATFORM.getInfectedHuman(), serverWorld);
            infectedPlayerEntity.setCustomName(entity.getName());
            infectedPlayerEntity.setPUUID(entity.getUUID());
            infectedPlayerEntity.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
            serverWorld.addFreshEntity(infectedPlayerEntity);}
        else if(entity instanceof AbstractVillager){Services.PLATFORM.getInfectedVillager().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), MobSpawnType.TRIGGERED, true, false); }
        else if(entity instanceof IronGolem){Services.PLATFORM.getInfectedIG().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), MobSpawnType.TRIGGERED, true, false);}
    }

    public static void attackHelper(LivingEntity aggro, LivingEntity victim){
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
