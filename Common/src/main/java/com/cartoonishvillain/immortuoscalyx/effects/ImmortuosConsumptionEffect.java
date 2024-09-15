package com.cartoonishvillain.immortuoscalyx.effects;

import com.cartoonishvillain.immortuoscalyx.damage.ImmortuosDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ImmortuosConsumptionEffect extends MobEffect implements ImmortuosEffect {
    public ImmortuosConsumptionEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(
                new DamageSource(pLivingEntity.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ImmortuosDamageTypes.infection_damage)
        ), 5);
        return super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
