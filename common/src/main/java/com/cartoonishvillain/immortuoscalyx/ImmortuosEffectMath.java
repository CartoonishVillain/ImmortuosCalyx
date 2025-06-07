package com.cartoonishvillain.immortuoscalyx;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class ImmortuosEffectMath {
    private Holder<MobEffect> mobEffectHolder;
    private int amplitudeDivisor; 
    ImmortuosEffectMath(Holder<MobEffect> mobEffect, int amplitudeDivisor) {
        mobEffectHolder = mobEffect;
        this.amplitudeDivisor = amplitudeDivisor;
    }

    ImmortuosEffectMath(Holder<MobEffect> mobEffect) {
        mobEffectHolder = mobEffect;
        amplitudeDivisor = 1;
    }

    public Holder<MobEffect> getMobEffectHolder() {
        return mobEffectHolder;
    }

    public int getAmplitudeDivisor() {
        return amplitudeDivisor;
    }
}
