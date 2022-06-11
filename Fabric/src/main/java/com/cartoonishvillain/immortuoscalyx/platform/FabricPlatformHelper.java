package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.FabricImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedIGEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedVillagerEntity;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

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
    public int getInfectionProgress(LivingEntity entity) {

        return INFECTION.get(entity).getInfectionProgress();
    }

    @Override
    public void setInfectionProgress(int progress, LivingEntity entity) {
        INFECTION.get(entity).setInfectionProgress(progress);
    }

    @Override
    public void setInfectionProgressIfLower(int progress, LivingEntity entity) {
        INFECTION.get(entity).setInfectionProgressIfLower(progress);
    }

    @Override
    public void addInfectionProgress(int change, LivingEntity entity) {
        INFECTION.get(entity).addInfectionProgress(change);
    }

    @Override
    public int getInfectionTimerStat(LivingEntity entity) {
        return INFECTION.get(entity).getInfectionTimer();
    }

    @Override
    public void addInfectionTimer(int timer, LivingEntity entity) {
        INFECTION.get(entity).addInfectionTimer(timer);
    }

    @Override
    public void setInfectionTimer(int timer, LivingEntity entity) {
        INFECTION.get(entity).setInfectionTimer(timer);
    }

    @Override
    public double getResistance(LivingEntity entity) {
        return INFECTION.get(entity).getResistance();
    }

    @Override
    public void setResistance(double Resistance, LivingEntity entity) {
        INFECTION.get(entity).setResistance(Resistance);
    }

    @Override
    public void addResistance(double Resistance, LivingEntity entity) {
        INFECTION.get(entity).addResistance(Resistance);
    }

    @Override
    public boolean getFollowerStatus(LivingEntity entity) {
        return INFECTION.get(entity).isFollower();
    }

    @Override
    public void isFollowerStatus(boolean follower, LivingEntity entity) {
        INFECTION.get(entity).setFollower(follower);
    }

    @Override
    public boolean getResistantDosage(LivingEntity entity) {
        return INFECTION.get(entity).isResistant();
    }

    @Override
    public void setResistantDosage(boolean dosage, LivingEntity entity) {
        INFECTION.get(entity).setResistant(dosage);
    }

    @Override
    public boolean getAntiChat() {
        return FabricImmortuosCalyx.config.playerToggles.ANTICHAT;
    }

    @Override
    public boolean getInfectedChatNoise() {
        return FabricImmortuosCalyx.config.playerToggles.INFECTEDCHATNOISE;
    }

    @Override
    public boolean getPVPContagion() {
        return FabricImmortuosCalyx.config.playerToggles.PVPCONTAGION;
    }

    @Override
    public boolean getHeatSlow() {
        return FabricImmortuosCalyx.config.playerToggles.HEATSLOW;
    }

    @Override
    public boolean getColdFast() {
        return FabricImmortuosCalyx.config.playerToggles.HEATSLOW;
    }

    @Override
    public boolean getWaterBreathing() {
        return FabricImmortuosCalyx.config.playerToggles.WATERBREATHING;
    }

    @Override
    public boolean getColdConduitPower() {
        return FabricImmortuosCalyx.config.playerToggles.COLDCONDUITPOWER;
    }

    @Override
    public boolean getWarmWeakness() {
        return FabricImmortuosCalyx.config.playerToggles.WARMWEAKNESS;
    }

    @Override
    public boolean getColdStrength() {
        return FabricImmortuosCalyx.config.playerToggles.COLDSTRENGTH;
    }

    @Override
    public boolean getBlindness() {
        return FabricImmortuosCalyx.config.playerToggles.BLINDNESS;
    }

    @Override
    public double getArmorResist() {
        return FabricImmortuosCalyx.config.contagionConfig.ARMORRESISTMULTIPLIER;
    }

    @Override
    public double getAPResist() {
        return FabricImmortuosCalyx.config.contagionConfig.RESISTGIVENAP;
    }

    @Override
    public int getInfectedEntityValue() {
        return FabricImmortuosCalyx.config.contagionConfig.INFECTEDENTITYINFECTIONVALUE;
    }

    @Override
    public int getZombieValue() {
        return FabricImmortuosCalyx.config.contagionConfig.ZOMBIEINFECTIONVALUE;
    }

    @Override
    public int getRawFoodValue() {
        return FabricImmortuosCalyx.config.contagionConfig.RAWFOODINFECTIONVALUE;
    }

    @Override
    public int getEffectMsgOne() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTMESSAGEONE;
    }

    @Override
    public int getEffectMsgTwo() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTMESSAGETWO;
    }

    @Override
    public int getEffectChat() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTCHAT;
    }

    @Override
    public int getPlayerInfection() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.PLAYERINFECTIONTHRESHOLD;
    }

    @Override
    public int getEffectSpeed() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTSPEED;
    }

    @Override
    public int getEffectWaterBreathing() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTWATERBREATH;
    }

    @Override
    public int getEffectStrength() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTSTRENGTH;
    }

    @Override
    public int getEffectBlind() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTBLIND;
    }

    @Override
    public int getEffectDamage() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTDAMAGE;
    }

    @Override
    public int getEffectImpediment() {
        return FabricImmortuosCalyx.config.playerSymptomProgression.EFFECTIMPEDIMENT;
    }

    @Override
    public int getFollowerChance() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERCHANCE;
    }

    @Override
    public int getFollowerImmunity() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERFOLLOWERIMMUNITY;
    }

    @Override
    public int getVillagerSlowOne() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERSLOWONE;
    }

    @Override
    public int getVillagerSlowTwo() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERSLOWTWO;
    }

    @Override
    public int getVillagerNoTrade() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERNOTRADE;
    }

    @Override
    public int getVillagerLethal() {
        return FabricImmortuosCalyx.config.entityToggles.VILLAGERLETHAL;
    }

    @Override
    public int getIronSlow() {
        return FabricImmortuosCalyx.config.entityToggles.IRONGOLEMSLOW;
    }

    @Override
    public int getIronWeak() {
        return FabricImmortuosCalyx.config.entityToggles.IRONGOLEMWEAK;
    }

    @Override
    public int getIronLethal() {
        return FabricImmortuosCalyx.config.entityToggles.IRONGOLEMLETHAL;
    }

    @Override
    public int getEggInfectionStart() {
        return FabricImmortuosCalyx.config.otherDetails.EGGINFECTIONSTART;
    }

    @Override
    public int getInfectionDamage() {
        return FabricImmortuosCalyx.config.otherDetails.INFECTIONDAMAGE;
    }

    @Override
    public int getPVPContagionRelief() {
        return FabricImmortuosCalyx.config.otherDetails.PVPCONTAGIONRELIEF;
    }

    @Override
    public int getPVPContagionAmount() {
        return FabricImmortuosCalyx.config.otherDetails.PVPCONTAGIONRELIEF;
    }

    @Override
    public int getInfectionTimer() {
        return FabricImmortuosCalyx.config.otherDetails.INFECTIONTIMER;
    }

    @Override
    public boolean isFormattedChat() {
        return FabricImmortuosCalyx.config.otherDetails.FORMATTEDINFECTCHAT;
    }

    @Override
    public boolean isInfectedDeath() {
        return FabricImmortuosCalyx.config.otherDetails.INFECTIONDEATH;
    }

    @Override
    public boolean getHostileInfectionCleanse() {
        return FabricImmortuosCalyx.config.dimensionsAndSpawnDetails.HOSTILEINFECTIONINCLEANSE;
    }

    @Override
    public boolean getPlayerInfectionCleanse() {
        return FabricImmortuosCalyx.config.dimensionsAndSpawnDetails.PLAYERINFECTIONINCLEANSE;
    }

    @Override
    public boolean getRawFoodInfectionCleanse() {
        return FabricImmortuosCalyx.config.dimensionsAndSpawnDetails.RAWFOODINFECTIONINCLEANSE;
    }

    @Override
    public SoundEvent getScanBad() {
        return Register.SCANBAD;
    }

    @Override
    public SoundEvent getScanGood() {
        return Register.SCANCLEAR;
    }

    @Override
    public SoundEvent getHumanAmbient() {
        return Register.HUMANAMBIENT;
    }

    @Override
    public SoundEvent getHumanDeath() {
        return Register.HUMANDEATH;
    }

    @Override
    public SoundEvent getHumanHurt() {
        return Register.HUMANHURT;
    }

    @Override
    public SoundEvent getIGHurt() {
        return Register.IGHURT;
    }

    @Override
    public SoundEvent getIGDeath() {
        return Register.IGDEATH;
    }

    @Override
    public SoundEvent getVilIdle() {
        return Register.VILIDLE;
    }

    @Override
    public SoundEvent getVilHurt() {
        return Register.VILHURT;
    }

    @Override
    public SoundEvent getVilDeath() {
        return Register.VILDEATH;
    }

    @Override
    public SoundEvent getInjectSound() {
        return Register.INJECT;
    }

    @Override
    public SoundEvent getExtractSound() {
        return Register.EXTRACT;
    }

    @Override
    public ArrayList<ResourceLocation> getDimensions() {
        return FabricImmortuosCalyx.getDimensions();
    }

    @Override
    public CreativeModeTab getTab() {
        return FabricImmortuosCalyx.TAB;
    }

    @Override
    public EntityType<InfectedPlayerEntity> getInfectedHuman() {
        return Register.INFECTEDPLAYER;
    }

    @Override
    public EntityType<InfectedVillagerEntity> getInfectedVillager() {
        return Register.INFECTEDVILLAGER;
    }

    @Override
    public EntityType<InfectedIGEntity> getInfectedIG() {
        return Register.INFECTEDIG;
    }

    @Override
    public Item getSyringe() {
        return Register.SYRINGE;
    }

    @Override
    public Item getAP() {
        return Register.GENERALANTIPARASITIC;
    }

    @Override
    public Item getEggs() {
        return Register.IMMORTUOSCALYXEGGS;
    }
}
