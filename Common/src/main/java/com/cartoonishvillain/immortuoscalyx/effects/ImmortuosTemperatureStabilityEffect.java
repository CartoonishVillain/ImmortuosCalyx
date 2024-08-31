package com.cartoonishvillain.immortuoscalyx.effects;

import com.cartoonishvillain.immortuoscalyx.mixin.BiomeInvoker;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class ImmortuosTemperatureStabilityEffect extends MobEffect {

    public ImmortuosTemperatureStabilityEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //We only want this running on the server side, and to reduce the amount of operations, we will only run this check every 100 ticks (5 seconds)
        //This will mean every 5 seconds we'll have a small spike of activity, but overall should keep performance a little smoother.
        if (!pLivingEntity.level().isClientSide && (pLivingEntity.tickCount % 100 == 0)) {
            Level level = pLivingEntity.level();
            Holder<Biome> biome = level.getBiome(pLivingEntity.blockPosition());
            float temperature = ((BiomeInvoker) (Object) biome.value()).invokeGetHeightAdjustedTemperature(pLivingEntity.blockPosition());

            //If the temperature is below this threshold, add the resistance and strength effects
            if (temperature < 0.5f) {
                pLivingEntity.addEffect(
                        new MobEffectInstance(
                                Services.PLATFORM.INFECTION_SPEED(),
                                120,
                                1,
                                true,
                                false,
                                false
                        )
                );
            }

            //if the temperature is above this threshold, and the entity is not in water, weaken them
            if (temperature > 0.9f && !pLivingEntity.isInWaterRainOrBubble()) {
                pLivingEntity.addEffect(
                        new MobEffectInstance(
                                Services.PLATFORM.INFECTION_SLOW(),
                                120,
                                1,
                                true,
                                false,
                                false
                        )
                );
            }
        }

        return super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}
