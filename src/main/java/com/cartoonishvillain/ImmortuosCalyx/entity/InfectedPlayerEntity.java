package com.cartoonishvillain.ImmortuosCalyx.entity;

import com.cartoonishvillain.ImmortuosCalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class InfectedPlayerEntity extends Monster implements InfectedEntity {

    private static final EntityDataAccessor<Optional<UUID>> PUUID = SynchedEntityData.defineId(InfectedPlayerEntity.class, EntityDataSerializers.OPTIONAL_UUID);


    public InfectedPlayerEntity(EntityType<InfectedPlayerEntity> type, Level worldIn) {
        super(type, worldIn);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(PUUID, Optional.empty());
    }


    public void setPUUID(UUID uuid){
        this.getEntityData().set(PUUID, Optional.of(uuid));
    }


    public UUID getPUUID(){
        return getEntityData().get(PUUID).orElse(new UUID(0L,0L));
    }

    public void setname(Component name){setCustomName(name);}

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.85D)
                .add(Attributes.ATTACK_DAMAGE, 2D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.targetSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.5D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<AbstractGolem>(this, AbstractGolem.class, 10, true, false, this::shouldAttackMonster));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, false, this::shouldAttack));
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
    protected SoundEvent getAmbientSound() { return Register.HUMANAMBIENT.get(); }

    @Override
    protected SoundEvent getDeathSound() {return Register.HUMANDEATH.get(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.HUMANHURT.get(); }

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
    }

}
