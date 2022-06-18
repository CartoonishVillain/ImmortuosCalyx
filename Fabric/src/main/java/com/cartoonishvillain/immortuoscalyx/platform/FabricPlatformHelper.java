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

import static com.cartoonishvillain.immortuoscalyx.FabricImmortuosCalyx.CONFIG;
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
        return CONFIG.getOrDefault("ANTICHAT", true);
    }

    @Override
    public boolean getInfectedChatNoise() {
        return CONFIG.getOrDefault("INFECTEDCHATNOISE", true);
    }

    @Override
    public boolean getPVPContagion() {
        return CONFIG.getOrDefault("PVPCONTAGION", true);
    }

    @Override
    public boolean getHeatSlow() {
        return CONFIG.getOrDefault("HEATSLOW", true);
    }

    @Override
    public boolean getColdFast() {
        return CONFIG.getOrDefault("COLDFAST", true);
    }

    @Override
    public boolean getWaterBreathing() {
        return CONFIG.getOrDefault("WATERBREATHING", true);
    }

    @Override
    public boolean getColdConduitPower() {
        return CONFIG.getOrDefault("COLDCONDUITPOWER", true);
    }

    @Override
    public boolean getWarmWeakness() {
        return CONFIG.getOrDefault("WARMWEAKNESS", true);
    }

    @Override
    public boolean getColdStrength() {
        return CONFIG.getOrDefault("COLDSTRENGTH", true);
    }

    @Override
    public boolean getBlindness() {
        return CONFIG.getOrDefault("BLINDNESS", true);
    }

    @Override
    public double getArmorResist() {
        return CONFIG.getOrDefault("ARMORRESISTMULTIPLIER", 2);
    }

    @Override
    public double getAPResist() {
        return CONFIG.getOrDefault("RESISTGIVENAP", 6);
    }

    @Override
    public int getInfectedEntityValue() {
        return CONFIG.getOrDefault("INFECTEDENTITYINFECTIONVALUE", 90);
    }

    @Override
    public int getZombieValue() {
        return CONFIG.getOrDefault("ZOMBIEINFECTIONVALUE", 20);
    }

    @Override
    public int getRawFoodValue() {
        return CONFIG.getOrDefault("RAWFOODINFECTIONVALUE", 10);
    }

    @Override
    public int getEffectMsgOne() {
        return CONFIG.getOrDefault("EFFECTMESSAGEONE", 10);
    }

    @Override
    public int getEffectMsgTwo() {
        return CONFIG.getOrDefault("EFFECTMESSAGETWO", 25);
    }

    @Override
    public int getEffectChat() {
        return CONFIG.getOrDefault("EFFECTCHAT", 40);
    }

    @Override
    public int getPlayerInfection() {
        return CONFIG.getOrDefault("PLAYERINFECTIONTHRESHOLD", 50);
    }

    @Override
    public int getEffectSpeed() {
        return CONFIG.getOrDefault("EFFECTSPEED", 60);
    }

    @Override
    public int getEffectWaterBreathing() {
        return CONFIG.getOrDefault("EFFECTWATERBREATH", 67);
    }

    @Override
    public int getEffectStrength() {
        return CONFIG.getOrDefault("EFFECTSTRENGTH", 85);
    }

    @Override
    public int getEffectBlind() {
        return CONFIG.getOrDefault("EFFECTBLIND", 95);
    }

    @Override
    public int getEffectDamage() {
        return CONFIG.getOrDefault("EFFECTDAMAGE", 100);
    }

    @Override
    public int getEffectImpediment() {
        return CONFIG.getOrDefault("EFFECTIMPEDIMENT", 89);
    }

    @Override
    public int getFollowerChance() {
        return CONFIG.getOrDefault("VILLAGERFOLLOWERCHANCE", 25);
    }

    @Override
    public int getFollowerImmunity() {
        return CONFIG.getOrDefault("VILLAGERFOLLOWERIMMUNITY", 2);
    }

    @Override
    public int getVillagerSlowOne() {
        return CONFIG.getOrDefault("VILLAGERSLOWONE", 5);
    }

    @Override
    public int getVillagerSlowTwo() {
        return CONFIG.getOrDefault("VILLAGERSLOWTWO", 15);
    }

    @Override
    public int getVillagerNoTrade() {
        return CONFIG.getOrDefault("VILLAGERNOTRADE", 37);
    }

    @Override
    public int getVillagerLethal() {
        return CONFIG.getOrDefault("VILLAGERLETHAL", 60);
    }

    @Override
    public int getIronSlow() {
        return CONFIG.getOrDefault("IRONGOLEMSLOW", 5);
    }

    @Override
    public int getIronWeak() {
        return CONFIG.getOrDefault("IRONGOLEMWEAK", 5);
    }

    @Override
    public int getIronLethal() {
        return CONFIG.getOrDefault("IRONGOLEMLETHAL", 110);
    }

    @Override
    public int getEggInfectionStart() {
        return CONFIG.getOrDefault("EGGINFECTIONSTART", 1);
    }

    @Override
    public int getInfectionDamage() {
        return CONFIG.getOrDefault("INFECTIONDAMAGE", 1);
    }

    @Override
    public int getPVPContagionRelief() {
        return CONFIG.getOrDefault("PVPCONTAGIONRELIEF", 5);
    }

    @Override
    public int getPVPContagionAmount() {
        return CONFIG.getOrDefault("PVPCONTAGIONAMOUNT", 1);
    }

    @Override
    public int getInfectionTimer() {
        return CONFIG.getOrDefault("INFECTIONTIMER", 450);
    }

    @Override
    public boolean isFormattedChat() {
        return CONFIG.getOrDefault("FORMATTEDINFECTCHAT", false);
    }

    @Override
    public boolean isInfectedDeath() {
        return CONFIG.getOrDefault("INFECTIONDEATH", true);
    }

    @Override
    public boolean getHostileInfectionCleanse() {
        return CONFIG.getOrDefault("HOSTILEINFECTIONINCLEANSE", true);
    }

    @Override
    public boolean getPlayerInfectionCleanse() {
        return CONFIG.getOrDefault("PLAYERINFECTIONINCLEANSE", false);
    }

    @Override
    public boolean getRawFoodInfectionCleanse() {
        return CONFIG.getOrDefault("RAWFOODINFECTIONINCLEANSE", true);
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
