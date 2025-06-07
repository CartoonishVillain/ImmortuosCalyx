package com.cartoonishvillain.immortuoscalyx.items;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.ImmortuosEffectMath;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;

public class DefaultGeneMethods {
    public static MobEffectInstance geneSelection(String gene, int quality) {
        ImmortuosEffectMath values = CommonImmortuos.getActiveGenes().get(gene);
        if (values != null) return new MobEffectInstance(values.getMobEffectHolder(), 420, quality/values.getAmplitudeDivisor(),
                true, false, true);
        else return null;
    }

    public static MobEffectInstance contaminationSelection(String gene, int quality) {
        ImmortuosEffectMath values = CommonImmortuos.getActiveContaminations().get(gene);
        if (values != null) return new MobEffectInstance(values.getMobEffectHolder(), 420, quality/values.getAmplitudeDivisor(),
                true, false, true);
        else return null;
    }

    public static String contaminationPicker(RandomSource source) {
        return CommonImmortuos.getActiveContaminations().keySet().stream().toList().get(source.nextInt(CommonImmortuos.getActiveContaminations().keySet().stream().toList().size()));
    }
}
