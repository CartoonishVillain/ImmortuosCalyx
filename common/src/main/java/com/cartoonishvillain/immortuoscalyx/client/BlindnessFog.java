package com.cartoonishvillain.immortuoscalyx.client;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.client.renderer.FogRenderer.BlindnessFogFunction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class BlindnessFog extends BlindnessFogFunction {
    @Override
    public Holder<MobEffect> getMobEffect() {
        return Services.PLATFORM.INFECTION_BLIND();
    }
}
