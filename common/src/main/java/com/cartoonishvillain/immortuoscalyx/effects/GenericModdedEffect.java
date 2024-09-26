package com.cartoonishvillain.immortuoscalyx.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class GenericModdedEffect extends MobEffect implements ImmortuosEffect {
    public GenericModdedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
}
