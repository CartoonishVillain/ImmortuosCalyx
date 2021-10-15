package com.cartoonishvillain.ImmortuosCalyx.Events;

import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedDiverEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedIGEntity;
import com.cartoonishvillain.ImmortuosCalyx.Entity.InfectedPlayerEntity;
import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionDamage;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionHandler;
import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityInfectionEventManager {
    @SubscribeEvent
    public static void InfectionTicker(LivingEvent.LivingUpdateEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!(entity instanceof PlayerEntity)){
            entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= 1){
                    h.addInfectionTimer(1);
                    int timer = -1;
                    if (entity instanceof VillagerEntity){timer = ImmortuosCalyx.config.VILLAGERINFECTIONTIMER.get();}
                    else if(entity instanceof IronGolemEntity){timer = ImmortuosCalyx.config.IRONGOLEMTIMER.get();}
                    else {timer = ImmortuosCalyx.config.INFECTIONTIMER.get();}
                    if(h.getInfectionTimer() >= timer){
                        h.addInfectionProgress(1);
                        h.addInfectionTimer(-timer);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void InfectionLogic(LivingEvent.LivingUpdateEvent event){
        AtomicInteger infectionlevel = new AtomicInteger(0);
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            infectionlevel.getAndSet(h.getInfectionProgress());
        });
        int level = infectionlevel.get();
        if(level > 0){
            if(entity instanceof VillagerEntity){VillagerLogic((VillagerEntity) entity, level);}
            if(entity instanceof IronGolemEntity && !(entity instanceof InfectedIGEntity)){IGLogic((IronGolemEntity) entity, level);}
        }
    }


    public static void VillagerLogic(VillagerEntity entity, int level){
        if(level >= ImmortuosCalyx.config.VILLAGERSLOWTWO.get()){ // greater than or equal to 25
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
        }  else if(level >= ImmortuosCalyx.config.VILLAGERSLOWONE.get()){ //5-24%
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
        }
        if(level >= ImmortuosCalyx.config.VILLAGERLETHAL.get()){
            Random rand = new Random();
            int random = rand.nextInt(100);
            if(random < 1 && ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0){
                entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.INFECTIONDAMAGE.get());
            }
        }
    }

    public static void IGLogic(IronGolemEntity entity, int level){
        if(level >= ImmortuosCalyx.config.IRONGOLEMSLOW.get()){ entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5, 1, false, false)); }
        if(level >= ImmortuosCalyx.config.IRONGOLEMWEAK.get()){ entity.addEffect(new EffectInstance(Effects.WEAKNESS, 5, 1, false, false)); }
        if(level >= ImmortuosCalyx.config.IRONGOLEMLETHAL.get()){
            Random rand = new Random();
            int random = rand.nextInt(100);
            if(random < 1 && ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0){
                entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.INFECTIONDAMAGE.get());
            }
        }
    }


    @SubscribeEvent
    public static void antiTrade(PlayerInteractEvent.EntityInteract event) {//villager specific interaction modifier.
        if(!event.getTarget().getCommandSenderWorld().isClientSide() && event.getHand() == Hand.MAIN_HAND){
            if(event.getTarget() instanceof VillagerEntity){
                VillagerEntity villager = (VillagerEntity) event.getTarget();
                villager.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                    if(h.getInfectionProgress() >= ImmortuosCalyx.config.VILLAGERNOTRADE.get()){
                        event.setCanceled(true);
                        villager.setUnhappyCounter(40);
                        villager.getCommandSenderWorld().playSound(null, villager.getX(), villager.getY(), villager.getZ(), Register.VILIDLE.get(), SoundCategory.NEUTRAL, 1f, 1f);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void deathEntityReplacement(LivingDeathEvent event){
        LivingEntity entity = event.getEntityLiving();
        if (event.getSource().msgId.equals("infection")){
            World world = event.getEntityLiving().getCommandSenderWorld();
            if(!world.isClientSide()){
                ServerWorld serverWorld = (ServerWorld) world;
                if(entity instanceof PlayerEntity){
                    InfectedPlayerEntity infectedPlayerEntity = new InfectedPlayerEntity(Register.INFECTEDPLAYER.get(), world);
                    infectedPlayerEntity.setCustomName(entity.getName());
                    infectedPlayerEntity.setUUID(entity.getUUID());
                    infectedPlayerEntity.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
                    world.addFreshEntity(infectedPlayerEntity);}
                else if(entity instanceof AbstractVillagerEntity){Register.INFECTEDVILLAGER.get().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), SpawnReason.TRIGGERED, true, false); }
                else if(entity instanceof IronGolemEntity){Register.INFECTEDIG.get().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), SpawnReason.TRIGGERED, true, false);}
            }
        }
    }


    @SubscribeEvent
    public static void AerosolInfection(LivingEvent.LivingUpdateEvent event){
        LivingEntity sourceEntity = event.getEntityLiving();
        Random rand = new Random();
        if ((!ImmortuosCalyx.DimensionExclusion.contains(sourceEntity.level.dimension().location()) || !ImmortuosCalyx.commonConfig.HOSTILEAEROSOLINFECTIONINCLEANSE.get()) && !sourceEntity.level.isClientSide()){
            int AerosolRate = Integer.MAX_VALUE;
            boolean common = false;

            if(sourceEntity instanceof InfectedEntity) AerosolRate = ImmortuosCalyx.config.INFECTEDAERIALRATE.get();
            else if(sourceEntity instanceof ZombieEntity) AerosolRate = ImmortuosCalyx.config.ZOMBIEAERIALRATE.get();
            else if(sourceEntity instanceof VillagerEntity) AerosolRate = ImmortuosCalyx.config.FOLLOWERAERIALRATE.get();
            else {common = true; AerosolRate = ImmortuosCalyx.config.COMMONAERIALRATE.get();}

            if(AerosolRate != Integer.MAX_VALUE && rand.nextInt(AerosolRate) == 2) {
                ArrayList<Entity> entities = (ArrayList<Entity>) sourceEntity.level.getEntities(sourceEntity, sourceEntity.getBoundingBox().inflate(2), null);
                ArrayList<LivingEntity> livingEntityList = new ArrayList<LivingEntity>();
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        livingEntityList.add((LivingEntity) entity);
                    }
                }
                if (!common) {
                    for (LivingEntity victim : livingEntityList) {
                        InfectionHandler.bioInfect(victim, AerosolRate, 1);
                    }
                } else {
                    for (LivingEntity victim : livingEntityList) {
                        InfectionHandler.commonAerosol(victim, sourceEntity, 1);
                    }
                }
            }

        }

    }

    @SubscribeEvent
    public static void InfectedIGSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof InfectedIGEntity && !sEntity.level.isClientSide()){
            InfectedIGEntity entity = (InfectedIGEntity) sEntity;
            Set<PrioritizedGoal> prioritizedGoals = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, entity.targetSelector, "availableGoals");
            ArrayList<Goal> toRemove = new ArrayList<>();
            if(prioritizedGoals != null) {
                for (PrioritizedGoal prioritizedGoal : prioritizedGoals) {
                    toRemove.add(prioritizedGoal.getGoal());
                }
            }
            for(Goal goal : toRemove){
                entity.targetSelector.removeGoal(goal);
            }
            entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, PillagerEntity.class, 16, true, false,  entity::shouldAttack));
            entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(entity, MonsterEntity.class, 16, true, false,  entity::shouldAttackMonster));
            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, AbstractVillagerEntity.class, 16, true, false,  entity::shouldAttack));
            entity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity, PlayerEntity.class, 16, true, false,  entity::shouldAttack));
            entity.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(entity, GolemEntity.class, 16, true, false,  entity::shouldAttackMonster));

        }
    }

    @SubscribeEvent
    public static void GolemSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof GolemEntity && !sEntity.level.isClientSide() && !(sEntity instanceof InfectedEntity)){
            GolemEntity golemEntity = (GolemEntity) sEntity;
            golemEntity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(golemEntity, InfectedIGEntity.class, true, false));
        }
    }

    @SubscribeEvent
    public static void DiverSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof InfectedDiverEntity && !sEntity.level.isClientSide()){
            InfectedDiverEntity entity = (InfectedDiverEntity) sEntity;
            Set<PrioritizedGoal> prioritizedGoals = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, entity.targetSelector, "availableGoals");
            ArrayList<Goal> toRemove = new ArrayList<>();
            if(prioritizedGoals != null) {
                for (PrioritizedGoal prioritizedGoal : prioritizedGoals) {
                    toRemove.add(prioritizedGoal.getGoal());
                }
            }
            for(Goal goal : toRemove){
                entity.targetSelector.removeGoal(goal);
            }
            entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, PlayerEntity.class, 10, true, false, entity::okTarget));
            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, AbstractVillagerEntity.class, 10, true, false, entity::okTarget));
            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<GolemEntity>(entity, GolemEntity.class, 10, true, false, entity::shouldAttackMonster));
        }
    }

    @SubscribeEvent
    public static void VillagerSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof VillagerEntity && !((VillagerEntity) sEntity).isBaby() && !event.getWorld().isClientSide){
            sEntity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(event.getWorld().getRandom().nextInt(ImmortuosCalyx.config.VILLAGERFOLLOWERCHANCE.get()) < 2){
                    h.setInfectionProgressIfLower(1);
                    h.setFollower(true);}
            });
        }
    }

}
