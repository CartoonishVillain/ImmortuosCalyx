package com.cartoonishvillain.ImmortuosCalyx.Entity;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfectedIGEntity extends IronGolemEntity implements InfectedEntity {


    public InfectedIGEntity(EntityType<InfectedIGEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute customAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 125D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
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
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    protected boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 5 + this.world.rand.nextInt(5);
    }

    @Override
    protected SoundEvent getDeathSound() {return Register.IGDEATH.get(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Register.IGHURT.get(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() < 100) h.setInfectionProgress(100);
        });
    }
}
