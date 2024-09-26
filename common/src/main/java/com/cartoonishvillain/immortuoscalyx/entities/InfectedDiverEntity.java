package com.cartoonishvillain.immortuoscalyx.entities;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class InfectedDiverEntity extends Drowned implements InfectedEntity {


    public InfectedDiverEntity(EntityType<InfectedDiverEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38F)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0D);
    }


    @Override
    public boolean okTarget(LivingEntity entity) {
        if(entity instanceof ServerPlayer){
            return Services.PLATFORM.getInfectionPercentage((ServerPlayer) entity) < 50;
        } else return entity != null;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof ServerPlayer && Services.PLATFORM.getInfectionPercentage((ServerPlayer) pEntity) < 1) {
            AbstractInfectionHandler.infectionCheck((ServerPlayer) pEntity, 50);
        }
        return super.doHurtTarget(pEntity);
    }



    @Override
    protected SoundEvent getAmbientSound() { return Services.PLATFORM.HUMANOID_AMBIENT(); }

    @Override
    protected SoundEvent getDeathSound() {return Services.PLATFORM.HUMANOID_DEATH(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Services.PLATFORM.HUMANOID_DEATH(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    public void setBaby(boolean childZombie) {}

    @Override
    protected boolean isSunSensitive() {return false;}

    public static boolean checkDiverSpawnRules(EntityType<InfectedDiverEntity> p_218956_, ServerLevelAccessor p_218957_, MobSpawnType p_218958_, BlockPos p_218959_, RandomSource p_218960_) {
            Holder<Biome> holder = p_218957_.getBiome(p_218959_);
            boolean flag = p_218957_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_218957_, p_218959_, p_218960_) && (p_218958_ == MobSpawnType.SPAWNER || p_218957_.getFluidState(p_218959_).is(FluidTags.WATER));
            if (holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
                return p_218960_.nextInt(15) == 0 && flag;
            } else {
                return p_218960_.nextInt(40) == 0 && flag;
            }
    }
}
