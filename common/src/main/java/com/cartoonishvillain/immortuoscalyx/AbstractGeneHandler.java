package com.cartoonishvillain.immortuoscalyx;


import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AbstractGeneHandler {
    public static float turtleDamageHandler(float damageIncoming, int amplitude) {
        return damageIncoming * (0.95f - (0.05f * (float) amplitude));
    }

    public static float frogGeneJumpBoost(float jumpStrength, int amplitude) {
        return jumpStrength + (0.10f + (0.05F * (float) amplitude));
    }

    public static float staggerDamageHandler(float damageDealt, int amplifier) {
        return damageDealt * (1.05f + (0.02f * amplifier));
    }

    public static void tickHeliophobia(ServerPlayer player) {
        if (player.hasEffect(Services.PLATFORM.CONTAMINATION_HELIOPHOBIA())) {
            if (!(player.isInPowderSnow || player.wasInPowderSnow || player.isInWaterRainOrBubble()) && player.level().canSeeSky(player.blockPosition())) {
                player.setRemainingFireTicks(20);
            }
        }
    }

    public static void tickDestabilized(ServerPlayer player) {
        if (player.hasEffect(Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION())) {
            if (player.getRandom().nextInt(500) < 5) {
                player.setRemainingFireTicks(20 * player.getEffect(Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION()).getAmplifier());
            }
        }
    }

    public static void tickEnderman(ServerPlayer player) {
        if (player.hasEffect(Services.PLATFORM.GENE_ENDERMAN())) {
            MobEffectInstance instance = player.getEffect(Services.PLATFORM.GENE_ENDERMAN());
            if (instance != null) {
                if ((player.isInWaterRainOrBubble())) {
                    player.addEffect(
                            new MobEffectInstance(Services.PLATFORM.GENE_ENDERMAN_DRAWBACK(), 420, instance.getAmplifier(), true, false, true)
                    );
                    player.removeEffect(Services.PLATFORM.GENE_ENDERMAN_ACTIVE());
                } else {
                    player.addEffect(
                            new MobEffectInstance(Services.PLATFORM.GENE_ENDERMAN_ACTIVE(), 420, instance.getAmplifier(), true, false, true)
                    );
                    player.removeEffect(Services.PLATFORM.GENE_ENDERMAN_DRAWBACK());
                }
            }
        } else {
            if (player.hasEffect(Services.PLATFORM.GENE_ENDERMAN_ACTIVE()) || player.hasEffect(Services.PLATFORM.GENE_ENDERMAN_DRAWBACK())) {
                player.removeEffect(Services.PLATFORM.GENE_ENDERMAN_ACTIVE());
                player.removeEffect(Services.PLATFORM.GENE_ENDERMAN_DRAWBACK());
            }
        }
    }

    public static void vindicatorGeneCheck(DamageSource damageSource) {
        if (damageSource.getDirectEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) damageSource.getDirectEntity();
            if (player.getMainHandItem().is(ItemTags.AXES) && player.hasEffect(Services.PLATFORM.GENE_VINDICATOR())) {
                MobEffectInstance instance = player.getEffect(Services.PLATFORM.GENE_VINDICATOR());
                if (instance != null) {
                    player.addEffect(new MobEffectInstance(Services.PLATFORM.GENE_VINDICATOR_ACTIVE(), 600, instance.getAmplifier()/10, true, false, true));
                }
            }
        }
    }

    public static void magmaCubeFunction(ServerLevel level, List<Entity> effectedEntities, Vec3 userPos, float dmg) {
        // for every x and z coord 2 blocks away from the source, spawn a magma cube particle
        for (double x = -2; x <= 2; x = x+0.1) {
            for (double z = -2; z <= 2; z = z+0.1) {
                level.sendParticles(
                        ParticleTypes.FLAME, userPos.x()+x, (double) userPos.y(), userPos.z()+z, 1,  0d, 0d, 0d, 0d
                );
            }
        }

        level.playSound(null, userPos.x, userPos.y, userPos.z, SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1f, 1f);

        // Hurt all living entities involved.
        for (Entity entity : effectedEntities) {
            if (entity instanceof LivingEntity) entity.hurt(
                    new DamageSource(level.registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.ON_FIRE)
                    ), dmg
            );
        }
    }
}
