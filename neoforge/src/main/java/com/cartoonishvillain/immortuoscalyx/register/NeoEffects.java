package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.effects.GenericModdedEffect;
import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosTemperatureCongealmentEffect;
import com.cartoonishvillain.immortuoscalyx.effects.ImmortuosTemperatureStabilityEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import com.cartoonishvillain.immortuoscalyx.Constants;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoEffects {
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_BLIND;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_WATER_BREATH;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_COAGULATION;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_STRENGTH;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_WEAKEN;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_RESIST;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_VULNERABLE;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_STABILITY;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_SPEED;
    public static Supplier<MobEffect> IMMORTUOS_TEMP_SLOW;
    public static Supplier<MobEffect> IMMORTUOS_CHAT;


    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Constants.MOD_ID);

    public static void init(IEventBus modbus) {
        IMMORTUOS_BLIND = MOB_EFFECTS.register("immortuos_blind", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_WATER_BREATH = MOB_EFFECTS.register("immortuos_water_breath", () -> new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        IMMORTUOS_TEMP_COAGULATION = MOB_EFFECTS.register("immortuos_temperature_coagulation", () -> new ImmortuosTemperatureCongealmentEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_TEMP_STRENGTH = MOB_EFFECTS.register("immortuos_strength", () -> new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_strength"), 2, AttributeModifier.Operation.ADD_VALUE));
        IMMORTUOS_TEMP_WEAKEN = MOB_EFFECTS.register("immortuos_weaken", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_weaken"), -2, AttributeModifier.Operation.ADD_VALUE));
        IMMORTUOS_TEMP_RESIST = MOB_EFFECTS.register("immortuos_resist", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_TEMP_VULNERABLE = MOB_EFFECTS.register("immortuos_vulnerable", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_TEMP_STABILITY = MOB_EFFECTS.register("immortuos_temperature_stability", () -> new ImmortuosTemperatureStabilityEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_TEMP_SPEED = MOB_EFFECTS.register("immortuos_speed", () -> new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        IMMORTUOS_TEMP_SLOW = MOB_EFFECTS.register("immortuos_slow", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_slow"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        IMMORTUOS_CHAT = MOB_EFFECTS.register("immortuos_chat", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        MOB_EFFECTS.register(modbus);
    }
}
