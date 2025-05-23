package com.cartoonishvillain.immortuoscalyx.register;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.cartoonishvillain.immortuoscalyx.effects.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoEffects {
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_BLIND;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_WATER_BREATH;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_COAGULATION;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_STRENGTH;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_WEAKEN;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_RESIST;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_VULNERABLE;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_STABILITY;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_SPEED;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_TEMP_SLOW;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_CHAT;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_CONTAGION;
    public static DeferredHolder<MobEffect, MobEffect> IMMORTUOS_CONSUME;
    public static DeferredHolder<MobEffect, MobEffect> GENE_IMMORTUOS;
    public static DeferredHolder<MobEffect, MobEffect> GENE_ZOMBIE;
    public static DeferredHolder<MobEffect, MobEffect> GENE_OCELOT;
    public static DeferredHolder<MobEffect, MobEffect> GENE_TURTLE;
    public static DeferredHolder<MobEffect, MobEffect> GENE_IRON_GOLEM;
    public static DeferredHolder<MobEffect, MobEffect> GENE_TEMP_IRON_GOLEM;
    public static DeferredHolder<MobEffect, MobEffect> GENE_FROG;
    public static DeferredHolder<MobEffect, MobEffect> GENE_SILVERFISH;
    public static DeferredHolder<MobEffect, MobEffect> GENE_ENDERMAN;
    public static DeferredHolder<MobEffect, MobEffect> GENE_ENDERMAN_ACTIVE;
    public static DeferredHolder<MobEffect, MobEffect> GENE_ENDERMAN_DRAWBACK;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_HELIOPHOBIA;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_HYDROPHOBIA;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_GENETIC_DESTABLIZATION;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_STAGGER;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_KNEE_PASTAFICATION;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_KNEE_PASTAFICATION_TEMP;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_GIANT;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_GLASS;
    public static DeferredHolder<MobEffect, MobEffect> CONTAMINATION_SHADY;

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
        IMMORTUOS_CONTAGION = MOB_EFFECTS.register("immortuos_contagion", () -> new GenericModdedEffect(MobEffectCategory.NEUTRAL, 4587519));
        IMMORTUOS_CONSUME = MOB_EFFECTS.register("immortuos_consumption", () -> new ImmortuosConsumptionEffect(MobEffectCategory.HARMFUL, 4587519));

        GENE_IMMORTUOS = MOB_EFFECTS.register("immortuos_gene_immortuos", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        GENE_ZOMBIE = MOB_EFFECTS.register("immortuos_gene_zombie", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_zombie"), 1, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_OCELOT = MOB_EFFECTS.register("immortuos_gene_ocelot", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_ocelot"), 0.002, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_TURTLE = MOB_EFFECTS.register("immortuos_gene_turtle", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4587519));
        GENE_IRON_GOLEM = MOB_EFFECTS.register("immortuos_gene_iron_golem", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519));
        GENE_TEMP_IRON_GOLEM = MOB_EFFECTS.register("immortuos_gene_active_iron_golem", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_iron_golem"), 1, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_FROG = MOB_EFFECTS.register("immortuos_gene_frog", () ->  new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519));
        GENE_SILVERFISH = MOB_EFFECTS.register("immortuos_gene_silverfish", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519).addAttributeModifier(
                Attributes.SCALE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_silverfish"), -0.0035185185185185, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_ENDERMAN = MOB_EFFECTS.register("immortuos_gene_enderman", () -> new GeneModdedEffect(MobEffectCategory.NEUTRAL, 4598519));
        GENE_ENDERMAN_ACTIVE = MOB_EFFECTS.register("immortuos_gene_enderman_active", () -> new GeneModdedEffect(MobEffectCategory.BENEFICIAL, 4598519).addAttributeModifier(
                Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_enderman_active"), 0.02, AttributeModifier.Operation.ADD_VALUE
        ).addAttributeModifier(
                Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_enderman_active"), 0.02, AttributeModifier.Operation.ADD_VALUE
        ));
        GENE_ENDERMAN_DRAWBACK = MOB_EFFECTS.register("immortuos_gene_enderman_drawback", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519).addAttributeModifier(
                Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_enderman_drawback"), -0.05, AttributeModifier.Operation.ADD_VALUE
        ));

        CONTAMINATION_HELIOPHOBIA = MOB_EFFECTS.register("immortuos_contamination_heliophobia", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_HYDROPHOBIA = MOB_EFFECTS.register("immortuos_contamination_hydrophobia", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_GENETIC_DESTABLIZATION = MOB_EFFECTS.register("immortuos_contamination_genetic_destablization", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_STAGGER = MOB_EFFECTS.register("immortuos_contamination_stagger", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_KNEE_PASTAFICATION = MOB_EFFECTS.register("immortuos_contamination_knee_pastafication", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519));
        CONTAMINATION_KNEE_PASTAFICATION_TEMP = MOB_EFFECTS.register("immortuos_contamination_knee_pastafication_temp", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519).addAttributeModifier(
                Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_knee_pastafication"), -0.05, AttributeModifier.Operation.ADD_VALUE
        ));
        CONTAMINATION_GIANT = MOB_EFFECTS.register("immortuos_contamination_giant", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519).addAttributeModifier(
                Attributes.SCALE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_giant"), 0.025, AttributeModifier.Operation.ADD_VALUE
        ).addAttributeModifier(
                Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_giant"), 0.025, AttributeModifier.Operation.ADD_VALUE
        ).addAttributeModifier(
                Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_giant"), 0.025, AttributeModifier.Operation.ADD_VALUE
        ));
        CONTAMINATION_GLASS = MOB_EFFECTS.register("immortuos_contamination_glass", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4598519).addAttributeModifier(
                Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "immortuos_glass"), -0.075, AttributeModifier.Operation.ADD_VALUE
        ));
        CONTAMINATION_SHADY = MOB_EFFECTS.register("immortuos_contamination_shady", () -> new GeneModdedEffect(MobEffectCategory.HARMFUL, 4597519));
        MOB_EFFECTS.register(modbus);
    }
}
