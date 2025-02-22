package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.NeoForgeInfectionPlayerData;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import com.cartoonishvillain.immortuoscalyx.items.HealthScanner;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.NeoEffects;
import com.cartoonishvillain.immortuoscalyx.register.NeoEntity;
import com.cartoonishvillain.immortuoscalyx.register.NeoItems;
import com.cartoonishvillain.immortuoscalyx.register.NeoSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.ArrayList;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionDataAttachment.INFECTION_DATA;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public void setInfectionPercentage(ServerPlayer serverPlayer, int infectionPercentage) {
        NeoForgeInfectionPlayerData playerData = serverPlayer.getData(INFECTION_DATA);
        playerData.setInfectionPercent(infectionPercentage);
        serverPlayer.setData(INFECTION_DATA, playerData);
    }

    @Override
    public int getInfectionPercentage(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getInfectionPercent();
    }

    @Override
    public ArrayList<Symptom> getSymptoms(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getSymptoms();
    }

    @Override
    public void addSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.chirpAndAddSymptomEffect(serverPlayer);
        serverPlayer.getData(INFECTION_DATA).addSymptom(symptom.getSymptom());
    }

    @Override
    public void removeSymptom(ServerPlayer serverPlayer, AbstractSymptom symptom) {
        symptom.removeSymptomEffect(serverPlayer);
        serverPlayer.getData(INFECTION_DATA).removeSymptom(symptom.getSymptom());
    }

    @Override
    public void setSymptoms(ServerPlayer player, ArrayList<Symptom> symptoms) {
        player.getData(INFECTION_DATA).setSymptoms(symptoms);
    }

    @Override
    public boolean tickInfection(ServerPlayer serverPlayer) {
        MobEffectInstance instance = null;
        if (serverPlayer.hasEffect(
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IMMORTUOS.get())
        )) {
            instance = serverPlayer.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IMMORTUOS.get()));
        }

        return serverPlayer.getData(INFECTION_DATA).tickInfection(instance);
    }

    @Override
    public void setResistance(ServerPlayer serverPlayer, float resistance) {
        serverPlayer.getData(INFECTION_DATA).setResistance(resistance);
    }

    @Override
    public float getResistance(ServerPlayer serverPlayer) {
        return serverPlayer.getData(INFECTION_DATA).getResistance();
    }

    @Override
    public Holder<MobEffect> INFECTION_BLIND() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_BLIND.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WATER_BREATHING() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_WATER_BREATH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_COAGULATION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_STRENGTH() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_STRENGTH.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_RESIST() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_RESIST.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_WEAKEN() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_WEAKEN.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_VULNERABLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_VULNERABLE.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED_TEMPERATURE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_STABILITY.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SPEED() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_SPEED.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_SLOW() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_TEMP_SLOW.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CHAT() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CHAT.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONTAGION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CONTAGION.get());
    }

    @Override
    public Holder<MobEffect> INFECTION_CONSUMPTION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.IMMORTUOS_CONSUME.get());
    }

    @Override
    public Holder<MobEffect> GENE_TURTLE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_TURTLE.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_IRON_GOLEM_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_TEMP_IRON_GOLEM.get());
    }

    @Override
    public Holder<MobEffect> GENE_FROG() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.GENE_FROG.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HELIOPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_HELIOPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_HYDROPHOBIA() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_HYDROPHOBIA.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_GENETIC_DESTABILIZATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_GENETIC_DESTABLIZATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_STAGGER() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_STAGGER.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_KNEE_PASTAFICATION.get());
    }

    @Override
    public Holder<MobEffect> CONTAMINATION_KNEE_PASTAFICATION_ACTIVE() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(NeoEffects.CONTAMINATION_KNEE_PASTAFICATION_TEMP.get());
    }

    @Override
    public SoundEvent HUMANOID_AMBIENT() {
        return NeoSoundEvents.HUMANAMBIENT.value();
    }

    @Override
    public SoundEvent HUMANOID_HURT() {
        return NeoSoundEvents.HUMANHURT.value();
    }

    @Override
    public SoundEvent HUMANOID_DEATH() {
        return NeoSoundEvents.HUMANDEATH.value();
    }

    @Override
    public SoundEvent INJECT() {
        return NeoSoundEvents.INJECT.value();
    }

    @Override
    public SoundEvent EXTRACT() {
        return NeoSoundEvents.EXTRACT.value();
    }

    @Override
    public SoundEvent SCAN_BAD() {
        return NeoSoundEvents.SCANBAD.value();
    }

    @Override
    public SoundEvent SCAN_GOOD() {
        return NeoSoundEvents.SCANCLEAR.value();
    }

    @Override
    public EntityType<? extends Monster> getInfectedHuman() {
        return NeoEntity.INFECTEDHUMAN.get();
    }

    @Override
    public Item EMPTY_SYRINGE() {
        return NeoItems.SYRINGE.value();
    }

    @Override
    public Item CALYXANIDE() {
        return NeoItems.CALYXANIDE.value();
    }

    @Override
    public Item IMMORTUOS_SAMPLE() {
        return NeoItems.IMMORTUOS_SAMPLE.value();
    }

    @Override
    public Item IMMORTUOS_EGG() {
        return NeoItems.IMMORTUOS_EGGS.value();
    }

    @Override
    public Item ANTIPARASITIC() {
        return NeoItems.ANTI_PARASITIC.value();
    }

    @Override
    public Item HEALTH_SCANNER() {
        return NeoItems.HEALTH_SCANNER.value();
    }
}