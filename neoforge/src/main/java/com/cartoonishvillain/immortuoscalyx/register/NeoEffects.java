package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.effects.GenericModdedEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import com.cartoonishvillain.immortuoscalyx.Constants;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoEffects {
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_BLIND;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_WATER_BREATH;

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Constants.MOD_ID);

    public static void init(IEventBus modbus) {
        IMMORTUOS_BLIND = MOB_EFFECTS.register("immortuos_blind", () -> new GenericModdedEffect(MobEffectCategory.HARMFUL, 4587519));
        IMMORTUOS_WATER_BREATH = MOB_EFFECTS.register("immortuos_water_breath", () -> new GenericModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        MOB_EFFECTS.register(modbus);
    }
}
