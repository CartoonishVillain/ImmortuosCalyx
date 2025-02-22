package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.FabricEffects;
import com.cartoonishvillain.immortuoscalyx.register.FabricEntity;
import com.cartoonishvillain.immortuoscalyx.register.FabricItems;
import com.cartoonishvillain.immortuoscalyx.register.FabricSoundEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;

import java.util.ArrayList;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerComponentStarter.INFECTIONCOMPONENTINSTANCE;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage) {
        INFECTIONCOMPONENTINSTANCE.get(serverPlayer).setInfectionPercent(infectionPercentage);
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).getInfectionPercent();
    }

    @Override
    public ArrayList<Symptom> getSymptoms(ServerPlayer serverPlayer) {
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).getSymptoms();
    }

    @Override
    public void addSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.chirpAndAddSymptomEffect(serverPlayer);
        INFECTIONCOMPONENTINSTANCE.get(serverPlayer).addSymptom(symptom.getSymptom());
    }

    @Override
    public void removeSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.removeSymptomEffect(serverPlayer);
        INFECTIONCOMPONENTINSTANCE.get(serverPlayer).removeSymptom(symptom.getSymptom());
    }

    @Override
    public void setSymptoms(ServerPlayer player, ArrayList<Symptom> symptoms) {
        INFECTIONCOMPONENTINSTANCE.get(player).setSymptoms(symptoms);
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        MobEffectInstance instance = null;
        if (serverPlayer.hasEffect(
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_IMMORTUOS.get())
        )) {
            instance = serverPlayer.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_IMMORTUOS.get()));
        }

        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).tickInfection(instance);
    }

    @Override
    public void setResistance(ServerPlayer serverPlayer, float resistance) {
        INFECTIONCOMPONENTINSTANCE.get(serverPlayer).setResistance(resistance);
    }

    @Override
    public float getResistance(ServerPlayer serverPlayer) {
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).getResistance();
    }

    @Override
    public Holder<MobEffect> INFECTION_BLIND() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_BLIND.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WATER_BREATHING() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_WATER_BREATH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_COAGULATION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_STRENGTH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_RESIST() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_RESIST.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WEAKEN() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_WEAKEN.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_VULNERABLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_VULNERABLE.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_STABILITY.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_SPEED.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SLOW() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_TEMP_SLOW.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CHAT() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_CHAT.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONTAGION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_CONTAGION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONSUMPTION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.IMMORTUOS_CONSUME.get());
    }

    @Override
    public Holder<MobEffect> GENE_TURTLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_TURTLE.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_TEMP_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_FROG() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.GENE_FROG.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HELIOPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_HELIOPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HYDROPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_HYDROPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_GENETIC_DESTABILIZATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_GENETIC_DESTABLIZATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_STAGGER() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_STAGGER.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_KNEE_PASTAFICATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FabricEffects.CONTAMINATION_KNEE_PASTAFICATION_TEMP.get());
    }

    @Override
    public SoundEvent HUMANOID_AMBIENT() {
        return FabricSoundEvents.HUMANAMBIENT.get();
    }

    @Override
    public SoundEvent HUMANOID_HURT() {
        return FabricSoundEvents.HUMANHURT.get();
    }

    @Override
    public SoundEvent HUMANOID_DEATH() {
        return FabricSoundEvents.HUMANDEATH.get();
    }

    @Override
    public SoundEvent INJECT() {
        return FabricSoundEvents.INJECT.get();
    }

    @Override
    public SoundEvent EXTRACT() {
        return FabricSoundEvents.EXTRACT.get();
    }

    @Override
    public SoundEvent SCAN_BAD() {
        return FabricSoundEvents.SCANBAD.get();
    }

    @Override
    public SoundEvent SCAN_GOOD() {
        return FabricSoundEvents.SCANCLEAR.get();
    }

    @Override
    public EntityType<? extends Monster> getInfectedHuman() {
        return FabricEntity.INFECTED_HUMAN.get();
    }

    @Override
    public Item EMPTY_SYRINGE() {
        return FabricItems.SYRINGE.get();
    }

    @Override
    public Item CALYXANIDE() {
        return FabricItems.CALYXANIDE.get();
    }

    @Override
    public Item IMMORTUOS_SAMPLE() {
        return FabricItems.IMMORTUOS_SAMPLE.get();
    }

    @Override
    public Item IMMORTUOS_EGG() {
        return FabricItems.IMMORTUOS_EGGS.get();
    }

    @Override
    public Item ANTIPARASITIC() {
        return FabricItems.ANTI_PARASITIC.get();
    }

    @Override
    public Item HEALTH_SCANNER() {
        return FabricItems.HEALTH_SCANNER.get();
    }
}
