package com.cartoonishvillain.ImmortuosCalyx.events;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedDiverEntity;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedEntity;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedIGEntity;
import com.cartoonishvillain.ImmortuosCalyx.entity.InfectedPlayerEntity;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionDamage;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionHandler;
import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionManagerCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = ImmortuosCalyx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityInfectionEventManager {
    @SubscribeEvent
    public static void InfectionTicker(LivingEvent.LivingUpdateEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!(entity instanceof Player)){
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() >= 1){
                 h.addInfectionTimer(1);
                 int timer = -1;
                 if (entity instanceof Villager){timer = ImmortuosCalyx.config.VILLAGERINFECTIONTIMER.get();}
                 else if(entity instanceof IronGolem){timer = ImmortuosCalyx.config.IRONGOLEMTIMER.get();}
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
        AtomicBoolean isFollower = new AtomicBoolean(false);
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            infectionlevel.getAndSet(h.getInfectionProgress());
            isFollower.set(h.isFollower());
        });
        int level = infectionlevel.get();
        if(level > 0){
            if(entity instanceof Villager){VillagerLogic((Villager) entity, level, isFollower.get());}
            if(entity instanceof IronGolem && !(entity instanceof InfectedIGEntity)){IGLogic((IronGolem) entity, level);}
        }
    }


    public static void VillagerLogic(Villager entity, int level, boolean isFollower){
        if(!isFollower) {
            if (level >= ImmortuosCalyx.config.VILLAGERSLOWTWO.get()) { // greater than or equal to 25
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
            } else if (level >= ImmortuosCalyx.config.VILLAGERSLOWONE.get()) { //5-24%
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
            }
            if (level >= ImmortuosCalyx.config.VILLAGERLETHAL.get()) {
                Random rand = new Random();
                int random = rand.nextInt(100);
                if (random < 1 && ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0) {
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.INFECTIONDAMAGE.get());
                }
            }
        }
        else {
            if (level >= ImmortuosCalyx.config.VILLAGERSLOWTWO.get() * ImmortuosCalyx.config.VILLAGERFOLLOWERIMMUNITY.get()) { // greater than or equal to 25
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
            } else if (level >= ImmortuosCalyx.config.VILLAGERSLOWONE.get() * ImmortuosCalyx.config.VILLAGERFOLLOWERIMMUNITY.get()) { //5-24%
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false));
            }
            if (level >= ImmortuosCalyx.config.VILLAGERLETHAL.get() * ImmortuosCalyx.config.VILLAGERFOLLOWERIMMUNITY.get()) {
                Random rand = new Random();
                int random = rand.nextInt(100);
                if (random < 1 && ImmortuosCalyx.config.INFECTIONDAMAGE.get() > 0) {
                    entity.hurt(InfectionDamage.causeInfectionDamage(entity), ImmortuosCalyx.config.INFECTIONDAMAGE.get());
                }
            }
        }
    }

    public static void IGLogic(IronGolem entity, int level){
        if(level >= ImmortuosCalyx.config.IRONGOLEMSLOW.get()){ entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 1, false, false)); }
        if(level >= ImmortuosCalyx.config.IRONGOLEMWEAK.get()){ entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 1, false, false)); }
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
        if(!event.getTarget().getCommandSenderWorld().isClientSide() && event.getHand() == InteractionHand.MAIN_HAND){
            if(event.getTarget() instanceof Villager){
            Villager villager = (Villager) event.getTarget();
            villager.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= ImmortuosCalyx.config.VILLAGERNOTRADE.get()){
                    event.setCanceled(true);
                    villager.setUnhappyCounter(40);
                    villager.getCommandSenderWorld().playSound(null, villager.getX(), villager.getY(), villager.getZ(), Register.VILIDLE.get(), SoundSource.NEUTRAL, 1f, 1f);
                }
            });
            }
        }
    }

    private static final ArrayList<String> DISQUALIFYINGDAMAGE = new ArrayList<>(List.of("lightningBolt", "lava", "outOfWorld", "explosion"));
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void deathEntityReplacement(LivingDeathEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!event.isCanceled()) { // if the death is not canceled for some reason.
            if (event.getSource().msgId.equals("infection")) {
                Level world = event.getEntityLiving().getCommandSenderWorld();
                if (!world.isClientSide()) {
                    ServerLevel serverWorld = (ServerLevel) world;
                    summonConversion(serverWorld, entity);
                }
                //If the target doesn't die through very extensive means such as lava, struck by lightning, the void, or explosions, and the config is enabled, we may still go through with this.
            } else if (!DISQUALIFYINGDAMAGE.contains(event.getSource().msgId) && ImmortuosCalyx.config.INFECTONDEATH.get()) {
                Level world = event.getEntity().getCommandSenderWorld();
                if (!world.isClientSide()) {
                    entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                        //Chance to be converted is multiplied by 2 for every % above 50.
                        float chance = (h.getInfectionProgress() - 50) * 2;
                        if(chance > world.getRandom().nextInt(100)){
                            ServerLevel serverWorld = (ServerLevel) world;
                            summonConversion(serverWorld, entity);
                        }
                    });
                }
            }
        }
    }

    private static void summonConversion(ServerLevel serverWorld, LivingEntity entity){
        if (entity instanceof Player) {
            InfectedPlayerEntity infectedPlayerEntity = new InfectedPlayerEntity(Register.INFECTEDPLAYER.get(), serverWorld);
            infectedPlayerEntity.setCustomName(entity.getName());
            infectedPlayerEntity.setPUUID(entity.getUUID());
            infectedPlayerEntity.setPos(entity.getX(), entity.getY() + 0.1, entity.getZ());
            serverWorld.addFreshEntity(infectedPlayerEntity);
        } else if (entity instanceof AbstractVillager) {
            Register.INFECTEDVILLAGER.get().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), MobSpawnType.TRIGGERED, true, false);
        } else if (entity instanceof IronGolem) {
            Register.INFECTEDIG.get().spawn(serverWorld, new ItemStack(Items.AIR), null, entity.blockPosition(), MobSpawnType.TRIGGERED, true, false);
        }
    }

    @SubscribeEvent
    public static void InfectionByAir(LivingEvent.LivingUpdateEvent event){
        LivingEntity sourceEntity = event.getEntityLiving();
        Random rand = new Random();

        if ((!ImmortuosCalyx.DimensionExclusion.contains(sourceEntity.level.dimension().location()) || !ImmortuosCalyx.commonConfig.HOSTILEAEROSOLINFECTIONINCLEANSE.get()) && !sourceEntity.level.isClientSide()){
            int AerosolRate = Integer.MAX_VALUE;
            boolean common = false;

            if(sourceEntity instanceof InfectedEntity) AerosolRate = ImmortuosCalyx.config.INFECTEDAERIALRATE.get();
            else if(sourceEntity instanceof Zombie) AerosolRate = ImmortuosCalyx.config.ZOMBIEAERIALRATE.get();
            else if(sourceEntity instanceof Villager) AerosolRate = ImmortuosCalyx.config.FOLLOWERAERIALRATE.get();
            else {common = true; AerosolRate = ImmortuosCalyx.config.COMMONAERIALRATE.get();}

            if(AerosolRate != Integer.MAX_VALUE && rand.nextInt(AerosolRate) == 2) {
                ArrayList<Entity> entities = (ArrayList<Entity>) sourceEntity.level.getEntities(sourceEntity, sourceEntity.getBoundingBox().inflate(2), entity -> true);
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

//    @SubscribeEvent
//    public static void InfectedIGSpawnEvent(EntityJoinWorldEvent event){
//        Entity sEntity = event.getEntity();
//        if(sEntity instanceof InfectedIGEntity && !sEntity.level.isClientSide()){
//            InfectedIGEntity entity = (InfectedIGEntity) sEntity;
//            Set<WrappedGoal> prioritizedGoals = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, entity.targetSelector, "f_25345_");
//            ArrayList<Goal> toRemove = new ArrayList<>();
//            if(prioritizedGoals != null) {
//                for (WrappedGoal prioritizedGoal : prioritizedGoals) {
//                    toRemove.add(prioritizedGoal.getGoal());
//                }
//            }
//            for(Goal goal : toRemove){
//                entity.targetSelector.removeGoal(goal);
//            }
//            entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, Pillager.class, 16, true, false,  entity::shouldAttack));
//            entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(entity, Monster.class, 16, true, false,  entity::shouldAttackMonster));
//            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, AbstractVillager.class, 16, true, false,  entity::shouldAttack));
//            entity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity, Player.class, 16, true, false,  entity::shouldAttack));
//            entity.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(entity, AbstractGolem.class, 16, true, false,  entity::shouldAttackMonster));
//
//        }
//    }

    @SubscribeEvent
    public static void GolemSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof AbstractGolem && !sEntity.level.isClientSide() && !(sEntity instanceof InfectedEntity)){
            AbstractGolem golemEntity = (AbstractGolem) sEntity;
            golemEntity.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(golemEntity, InfectedIGEntity.class, true, false));
        }
    }

    @SubscribeEvent
    public static void DiverSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof InfectedDiverEntity && !sEntity.level.isClientSide()){
            InfectedDiverEntity entity = (InfectedDiverEntity) sEntity;
            Set<WrappedGoal> prioritizedGoals = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, entity.targetSelector, "f_25345_");
            ArrayList<Goal> toRemove = new ArrayList<>();
            if(prioritizedGoals != null) {
                for (WrappedGoal prioritizedGoal : prioritizedGoals) {
                    toRemove.add(prioritizedGoal.getGoal());
                }
            }
            for(Goal goal : toRemove){
                entity.targetSelector.removeGoal(goal);
            }
            entity.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(entity, Player.class, 10, true, false, entity::okTarget));
            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(entity, AbstractVillager.class, 10, true, false, entity::okTarget));
            entity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractGolem>(entity, AbstractGolem.class, 10, true, false, entity::shouldAttackMonster));
        }
    }

    @SubscribeEvent
    public static void VillagerSpawnEvent(EntityJoinWorldEvent event){
        Entity sEntity = event.getEntity();
        if(sEntity instanceof Villager && !((Villager) sEntity).isBaby() && !event.getWorld().isClientSide()){
            sEntity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(event.getWorld().getRandom().nextInt(ImmortuosCalyx.config.VILLAGERFOLLOWERCHANCE.get()) < 2){
                h.setInfectionProgressIfLower(1);
                h.setFollower(true);}
            });
        }
    }

}
