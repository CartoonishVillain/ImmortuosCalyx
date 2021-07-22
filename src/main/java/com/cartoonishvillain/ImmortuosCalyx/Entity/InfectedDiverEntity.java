package com.cartoonishvillain.ImmortuosCalyx.Entity;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfectedDiverEntity extends Drowned implements InfectedEntity {


    public InfectedDiverEntity(EntityType<InfectedDiverEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0D);
    }


    @Override
    public boolean okTarget(@Nullable LivingEntity entity) {
        if(entity != null){
            AtomicBoolean infectedThreshold = new AtomicBoolean(false);
            entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                if(h.getInfectionProgress() >= 50) infectedThreshold.set(true);
            });
            if(infectedThreshold.get()) return false;
            return !this.level.isDay() || entity.isInWater();

       }else return false;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 5 + this.level.random.nextInt(7);
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
    public void setBaby(boolean childZombie) {}
    @Override
    protected boolean isSunSensitive() {return false;}
    @Override
    public void aiStep() {
        super.aiStep();
        this.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            if (h.getInfectionProgress() < 100) h.setInfectionProgress(100);
        });
    }

}
