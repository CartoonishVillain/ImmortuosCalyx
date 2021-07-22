package com.cartoonishvillain.ImmortuosCalyx.Entity;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfectedIGEntity extends IronGolem implements InfectedEntity {


    public InfectedIGEntity(EntityType<InfectedIGEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 125D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
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

    public boolean shouldAttackMonster(@Nullable LivingEntity entity) {
        if(entity != null){
            return !(entity instanceof InfectedEntity);
        }else return false;
    }


    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 5 + this.level.random.nextInt(5);
    }

    @Override
    protected SoundEvent getDeathSound() {return Register.IGDEATH.get(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.IGHURT.get(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() < 100) h.setInfectionProgress(100);
        });
    }
}
