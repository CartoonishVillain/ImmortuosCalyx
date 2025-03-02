package com.cartoonishvillain.immortuoscalyx.items;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;

public class DefaultGeneMethods {
    public static MobEffectInstance geneSelection(String gene, int quality) {
        return switch (gene) {
            case "gene_immortuos" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_IMMORTUOS(), 420, quality, true, false, true);
            case "gene_zombie" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_ZOMBIE(), 420, quality / 20, //every 20 adds an amplifier.
                    true, false, true
            );
            case "gene_ocelot" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_OCELOT(), 420, quality / 4,// Every 4 adds an amplifier.
                    true, false, true
            );
            case "gene_turtle" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_TURTLE(), 420, quality / 10, true, false, true
            );
            case "gene_iron_golem" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_IRON_GOLEM(), 420, quality / 15, true, false, true
            );
            case "gene_frog" -> new MobEffectInstance(
                    Services.PLATFORM.GENE_FROG(), 420, quality / 10, true, false, true
            );
            default -> null;
        };
    }

    public static MobEffectInstance contaminationSelection(String gene, int quality) {
        return switch (gene) {
            case "contamination_hydrophobia" -> new MobEffectInstance(
                    Services.PLATFORM.CONTAMINATION_HYDROPHOBIA(), 420, 1, true, false, true
            );
            case "contamination_genetic_destablization" -> new MobEffectInstance(
                    Services.PLATFORM.CONTAMINATION_GENETIC_DESTABILIZATION(), 420, quality/10,true, false, true
            );
            case "contamination_stagger" -> new MobEffectInstance(
                    Services.PLATFORM.CONTAMINATION_STAGGER(), 420, quality/2, true, false, true
            );
            case "contamination_knee_pastafication" -> new MobEffectInstance(
                    Services.PLATFORM.CONTAMINATION_KNEE_PASTAFICATION(), 420, quality/20, true, false, true
            );
            default -> new MobEffectInstance(
                    Services.PLATFORM.CONTAMINATION_HELIOPHOBIA(), 420, 0, true, false, true
            );
        };

    }

    public static String contaminationPicker(RandomSource source) {
        return switch (source.nextInt(5)) {
            case 1 -> "contamination_hydrophobia";
            case 2 -> "contamination_genetic_destablization";
            case 3 -> "contamination_stagger";
            case 4 -> "contamination_knee_pastafication";
            default -> "contamination_heliophobia";
        };
    }
}
