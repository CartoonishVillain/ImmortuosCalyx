package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.infection.AbstractSymptom;
import com.cartoonishvillain.immortuoscalyx.infection.Symptom;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.cartoonishvillain.immortuoscalyx.register.FabricEffects;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

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
        return INFECTIONCOMPONENTINSTANCE.get(serverPlayer).tickInfection();
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
}
