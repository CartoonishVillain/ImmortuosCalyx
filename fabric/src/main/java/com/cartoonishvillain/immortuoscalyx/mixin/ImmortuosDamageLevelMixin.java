package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.AbstractGeneHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ImmortuosDamageLevelMixin {
    @Inject(at = @At("RETURN"), method = "getDamageAfterMagicAbsorb", cancellable = true)
    public void immortuosGetDamageAfterMagicAbsorb(DamageSource pDamageSource, float pDamageAmount, CallbackInfoReturnable<Float>  cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        float damageDealt = cir.getReturnValue();

        //Stagger Contamination Handling
        if (
                entity.hasEffect(Services.PLATFORM.CONTAMINATION_STAGGER())
        ) {
            damageDealt = AbstractGeneHandler.staggerDamageHandler(damageDealt, entity.getEffect(Services.PLATFORM.CONTAMINATION_STAGGER()).getAmplifier());
        }

        //Turtle Gene Handling
        if (
                entity.hasEffect(Services.PLATFORM.GENE_TURTLE()) && entity.isInWaterRainOrBubble() && !pDamageSource.is(DamageTypeTags.BYPASSES_RESISTANCE)
        ) {
            damageDealt = AbstractGeneHandler.turtleDamageHandler(damageDealt, entity.getEffect(Services.PLATFORM.GENE_TURTLE()).getAmplifier());
        }

        //Infection symptom handling
        if (
                damageDealt == 0.0F || //If the damage is already zero, do not run
                 pDamageSource.is(DamageTypeTags.BYPASSES_ENCHANTMENTS) ||  //If the damage bypasses enchantments do not run
                  !(entity.hasEffect(Services.PLATFORM.INFECTION_VULNERABLE()) || entity.hasEffect(Services.PLATFORM.INFECTION_RESIST()))) { // If the user doesn't have any modded resistance effect, do not run
        } else {
            //if none of the above is true, run the code based on the effect present
            if (entity.hasEffect(Services.PLATFORM.INFECTION_RESIST())) damageDealt = damageDealt * 0.75f; //25% Damage reduction
            if (entity.hasEffect(Services.PLATFORM.INFECTION_VULNERABLE())) damageDealt = damageDealt * 1.25f; //25% Damage increase
        }

        //Iron golem gene handling
        if (damageDealt != 0.0f && entity.hasEffect(Services.PLATFORM.GENE_IRON_GOLEM())) {
            entity.addEffect(
                    new MobEffectInstance(
                            Services.PLATFORM.GENE_IRON_GOLEM_ACTIVE(),
                            100, //5 seconds
                            entity.getEffect(Services.PLATFORM.GENE_IRON_GOLEM()).getAmplifier(),
                            true,
                            false,
                            false

                    )
            );
        }

        //Knee Pastafication Handling
        if (damageDealt != 0.0f && pDamageSource.is(DamageTypes.FALL) && entity.hasEffect(Services.PLATFORM.CONTAMINATION_KNEE_PASTAFICATION())) {
            entity.addEffect(
                    new MobEffectInstance(
                            Services.PLATFORM.CONTAMINATION_KNEE_PASTAFICATION_ACTIVE(),
                            100, //5 seconds
                            entity.getEffect(Services.PLATFORM.CONTAMINATION_KNEE_PASTAFICATION()).getAmplifier(),
                            true,
                            false,
                            false
                    )
            );
        }
        cir.setReturnValue(damageDealt);
    }
}
