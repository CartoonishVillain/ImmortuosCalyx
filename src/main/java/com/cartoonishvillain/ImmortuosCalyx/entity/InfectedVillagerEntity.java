package com.cartoonishvillain.ImmortuosCalyx.entity;

import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class InfectedVillagerEntity extends Monster implements InfectedEntity {

    public static final Predicate<LivingEntity> ATTACKPREDICATE = (Target) ->{
          AtomicBoolean shouldTarget = new AtomicBoolean(true);
          Target.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
              if(h.getInfectionProgress() > 0) shouldTarget.set(false);
          });
          return shouldTarget.get();
    };


    public InfectedVillagerEntity(EntityType<InfectedVillagerEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.25D)
                .add(Attributes.ATTACK_DAMAGE, 2D);
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.targetSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Pillager>(this, Pillager.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, ATTACKPREDICATE));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, false, ATTACKPREDICATE));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<AbstractGolem>(this, AbstractGolem.class, 10, true, false, this::shouldAttackMonster));
    }



    public boolean shouldAttack(@Nullable LivingEntity entity) {
        if(entity != null){
            AtomicBoolean infectedThreshold = new AtomicBoolean(false);
            entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= 50) infectedThreshold.set(true);
            });
            if(infectedThreshold.get()) return false;
            else return true;
        }else return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 5 + this.level.random.nextInt(5);
    }

    @Override
    protected SoundEvent getAmbientSound() { return Register.VILIDLE.get(); }

    @Override
    protected SoundEvent getDeathSound() {return Register.VILDEATH.get(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.VILHURT.get(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() < 100) h.setInfectionProgress(100);
        });

        if(getTarget() instanceof Villager || getTarget() instanceof Animal){
            AtomicBoolean shouldContinueTarget = new AtomicBoolean(true);
            getTarget().getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                if(h.getInfectionProgress() > 0) shouldContinueTarget.set(false);
            });
            if(!shouldContinueTarget.get()) this.setTarget(null);
        }
    }
}