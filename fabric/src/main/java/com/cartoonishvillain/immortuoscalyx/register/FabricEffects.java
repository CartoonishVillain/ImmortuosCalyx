package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.effects.GenericModdedEffect;
import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosTemperatureCongealmentEffect;
import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosTemperatureStabilityEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

/*
    While code reusage is minimal would like to shout out the immersive engineering team and BluSunrize for having such a neat license to help me get through this bit in particular
 */

public class FabricEffects {
    public static Supplier<MobEffect> IMMORTUOS_BLIND;
    public static Supplier<MobEffect> IMMORTUOS_WATER_BREATH;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_COAGULATION;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_STRENGTH;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_WEAKEN;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_RESIST;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_VULNERABLE;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_STABILITY;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_SPEED;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_SLOW;
    public static Supplier<MobEffect> IMMORTUOS_CHAT;


    public static void initEffects() {
        IMMORTUOS_BLIND = registerEffect("immortuos_blind", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_WATER_BREATH = registerEffect("immortuos_water_breath", new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        IMMORTUOS_TEMP_COAGULATION = registerEffect("immortuos_temperature_coagulation", new ImmortuosTemperatureCongealmentEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_TEMP_STRENGTH = registerEffect("immortuos_strength", new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_strength"), 2, AttributeModifier.Operation.ADD_VALUE));
        IMMORTUOS_TEMP_WEAKEN = registerEffect("immortuos_weaken", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_weaken"), -2, AttributeModifier.Operation.ADD_VALUE));
        IMMORTUOS_TEMP_RESIST = registerEffect("immortuos_resist", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_TEMP_VULNERABLE = registerEffect("immortuos_vulnerable", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_TEMP_STABILITY = registerEffect("immortuos_temperature_stability", new ImmortuosTemperatureStabilityEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_TEMP_SPEED = registerEffect("immortuos_speed", new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        IMMORTUOS_TEMP_SLOW = registerEffect("immortuos_slow", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_slow"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        IMMORTUOS_CHAT = registerEffect("immortuos_chat", new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
    }

    private static Supplier<MobEffect> registerEffect(String name, MobEffect effect) {
        MobEffect registered = Registry.register(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), effect);
        return () -> registered;
    }
}
