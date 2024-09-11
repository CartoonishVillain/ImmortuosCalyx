package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.data.player.NeoForgeInfectionPlayerData;
import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.NeoEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.ArrayList;

import static com.cartoonishvillain.immortuoscalyx.data.player.PlayerInfectionCapability.INFECTION_DATA;

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
        return serverPlayer.getData(INFECTION_DATA).tickInfection();
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
}