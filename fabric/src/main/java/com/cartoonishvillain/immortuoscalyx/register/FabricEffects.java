package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.effects.*;
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
    public static Supplier<MobEffect> IMMORTUOS_CONTAGION;
    public static Supplier<MobEffect> IMMORTUOS_CONSUME;
    public static Supplier<MobEffect> GENE_IMMORTUOS;
    public static Supplier<MobEffect> GENE_ZOMBIE;
    public static Supplier<MobEffect> GENE_OCELOT;
    public static Supplier<MobEffect> GENE_TURTLE;
    public static Supplier<MobEffect> GENE_IRON_GOLEM;
    public static Supplier<MobEffect> GENE_TEMP_IRON_GOLEM;
    public static Supplier<MobEffect> GENE_FROG;
    public static Supplier<MobEffect> CONTAMINATION_HELIOPHOBIA;
    public static Supplier<MobEffect> CONTAMINATION_HYDROPHOBIA;
    public static Supplier<MobEffect> CONTAMINATION_GENETIC_DESTABLIZATION;
    public static Supplier<MobEffect> CONTAMINATION_STAGGER;
    public static Supplier<MobEffect> CONTAMINATION_KNEE_PASTAFICATION;
    public static Supplier<MobEffect> CONTAMINATION_KNEE_PASTAFICATION_TEMP;


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
        IMMORTUOS_CONTAGION = registerEffect("immortuos_contagion", new GenericModdedEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_CONSUME = registerEffect("immortuos_consumption", new ImmortuosConsumptionEffect(MobEffectCategory.HARMFUL, 4587519));
        GENE_IMMORTUOS = registerEffect("immortuos_gene_immortuos", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        GENE_ZOMBIE = registerEffect("immortuos_gene_zombie", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_zombie"), 1, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_OCELOT = registerEffect("immortuos_gene_ocelot", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_ocelot"), 0.002, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_TURTLE = registerEffect("immortuos_gene_turtle", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        GENE_IRON_GOLEM = registerEffect("immortuos_gene_iron_golem", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519));
        GENE_TEMP_IRON_GOLEM = registerEffect("immortuos_gene_active_iron_golem", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_iron_golem"), 1, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_FROG = registerEffect("immortuos_gene_frog", new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519));
        CONTAMINATION_HELIOPHOBIA = registerEffect("immortuos_contamination_heliophobia", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_HYDROPHOBIA = registerEffect("immortuos_contamination_hydrophobia", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_GENETIC_DESTABLIZATION = registerEffect("immortuos_contamination_genetic_destablization", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_STAGGER = registerEffect("immortuos_contamination_stagger", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_KNEE_PASTAFICATION = registerEffect("immortuos_contamination_knee_pastafication", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_KNEE_PASTAFICATION_TEMP = registerEffect("immortuos_contamination_knee_pastafication_temp", new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_knee_pastafication"), -0.0075, AttributeModifier.Operation.ADD_VALUE
        ));
    }

    private static Supplier<MobEffect> registerEffect(String name, MobEffect effect) {
        MobEffect registered = Registry.register(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), effect);
        return () -> registered;
    }
}
