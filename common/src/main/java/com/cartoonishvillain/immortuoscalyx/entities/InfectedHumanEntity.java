package com.cartoonishvillain.immortuoscalyx.entities;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.UUID;

public class InfectedHumanEntity extends Monster implements InfectedEntity {

    private static final EntityDataAccessor<Optional<UUID>> PUUID = SynchedEntityData.defineId(InfectedHumanEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> PUSERNAME = SynchedEntityData.defineId(InfectedHumanEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> IS_TRANSFORMED_PLAYER = SynchedEntityData.defineId(InfectedHumanEntity.class, EntityDataSerializers.BOOLEAN);

    private ResourceLocation skinResource = null;

    public Optional<GameProfile> skinProfile = Optional.empty();

    private boolean isSlim = false;

    public InfectedHumanEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }

    public void setResourceLocation(ResourceLocation location) {
        skinResource = location;
    }

    public boolean isSlim() {
        return isSlim;
    }

    public void setSlim(boolean slim) {
        isSlim = slim;
    }

    public ResourceLocation getResourceLocation() {
        return skinResource;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(PUUID, Optional.empty());
        pBuilder.define(PUSERNAME, "");
        pBuilder.define(IS_TRANSFORMED_PLAYER, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (getPUUID().isPresent()) {
            pCompound.putUUID("puuid", getPUUID().get());
        }
        pCompound.putString("pname", getPUsername().get());
        pCompound.putBoolean("ptransformed", getIsTransformedPlayer());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("puuid")) setPUUID(pCompound.getUUID("puuid"));
        if (pCompound.contains("pname")) setPUsername(pCompound.getString("pname"));
        if (pCompound.contains("ptransformed")) setIsTransformedPlayer(pCompound.getBoolean("ptransformed"));
    }

    public void setPUUID(UUID uuid) {
        this.entityData.set(PUUID, Optional.of(uuid));
    }


    public Optional<UUID> getPUUID() {
        return this.entityData.get(PUUID);
    }

    public void setPUsername(String name) {
        this.entityData.set(PUSERNAME, name);
    }

    public Optional<String> getPUsername() {
        return Optional.of(this.entityData.get(PUSERNAME));
    }

    private void setIsTransformedPlayer(boolean value) {
        this.entityData.set(IS_TRANSFORMED_PLAYER, value);
    }

    public boolean getIsTransformedPlayer() {
        return this.entityData.get(IS_TRANSFORMED_PLAYER);
    }

    public static AttributeSupplier.Builder customAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.38D)
                .add(Attributes.ATTACK_DAMAGE, 2D);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof ServerPlayer && Services.PLATFORM.getInfectionPercentage((ServerPlayer) pEntity) < 1) {
            AbstractInfectionHandler.infectionCheck((ServerPlayer) pEntity, 50);
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.targetSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, 10, true, false, this::shouldAttackMonster));
    }


    public boolean shouldAttack(LivingEntity entity) {
        if(entity instanceof ServerPlayer){
            return Services.PLATFORM.getInfectionPercentage((ServerPlayer) entity) < 50;
        } else return entity != null;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        //Do not removed transformed players
        return !getIsTransformedPlayer();
    }

    @Override
    protected SoundEvent getAmbientSound() { return Services.PLATFORM.HUMANOID_AMBIENT(); }

    @Override
    protected SoundEvent getDeathSound() {return Services.PLATFORM.HUMANOID_DEATH(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return Services.PLATFORM.HUMANOID_HURT(); }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.15F, 1.0F);
    }
}
