package com.cartoonishvillain.immortuoscalyx.platform.services;

import com.cartoonishvillain.immortuoscalyx.entities.InfectedHumanEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedIGEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedVillagerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    int getInfectionProgress(LivingEntity entity);
    void setInfectionProgress(int progress, LivingEntity entity);
    void setInfectionProgressIfLower(int progress, LivingEntity entity);
    void addInfectionProgress(int change, LivingEntity entity);
    int getInfectionTimerStat(LivingEntity entity);
    void addInfectionTimer(int timer, LivingEntity entity);
    void setInfectionTimer(int timer, LivingEntity entity);
    double getResistance(LivingEntity entity);
    void setResistance(double Resistance, LivingEntity entity);
    void addResistance(double Resistance, LivingEntity entity);
    boolean getFollowerStatus(LivingEntity entity);
    void isFollowerStatus(boolean follower, LivingEntity entity);
    boolean getResistantDosage(LivingEntity entity);
    void setResistantDosage(boolean dosage, LivingEntity entity);

    boolean getAntiChat();
    boolean getInfectedChatNoise();
    boolean getPVPContagion();
    boolean getHeatSlow();
    boolean getColdFast();
    boolean getWaterBreathing();
    boolean getColdConduitPower();
    boolean getWarmWeakness();
    boolean getColdStrength();
    boolean getBlindness();

    double getArmorResist();
    double getAPResist();
    int getInfectedEntityValue();
    int getZombieValue();
    int getRawFoodValue();

    int getEffectMsgOne();
    int getEffectMsgTwo();
    int getEffectChat();
    int getPlayerInfection();
    int getEffectSpeed();
    int getEffectWaterBreathing();
    int getEffectStrength();
    int getEffectBlind();
    int getEffectDamage();
    int getEffectImpediment();

    int getFollowerChance();
    int getFollowerImmunity();
    int getVillagerSlowOne();
    int getVillagerSlowTwo();
    int getVillagerNoTrade();
    int getVillagerLethal();
    int getIronSlow();
    int getIronWeak();
    int getIronLethal();

    int getEggInfectionStart();
    int getInfectionDamage();
    int getPVPContagionRelief();
    int getPVPContagionAmount();
    int getInfectionTimer();
    boolean isFormattedChat();
    boolean isInfectedDeath();

    boolean getHostileInfectionCleanse();
    boolean getPlayerInfectionCleanse();
    boolean getRawFoodInfectionCleanse();

    boolean getVoiceChatModSupport();

    SoundEvent getScanBad();
    SoundEvent getScanGood();

    SoundEvent getHumanAmbient();
    SoundEvent getHumanDeath();
    SoundEvent getHumanHurt();

    SoundEvent getIGHurt();
    SoundEvent getIGDeath();

    SoundEvent getVilIdle();
    SoundEvent getVilHurt();
    SoundEvent getVilDeath();

    SoundEvent getInjectSound();
    SoundEvent getExtractSound();

    ArrayList<ResourceLocation> getDimensions();
    CreativeModeTab getTab();

    EntityType<InfectedPlayerEntity> getInfectedHuman();
    EntityType<InfectedVillagerEntity> getInfectedVillager();
    EntityType<InfectedIGEntity> getInfectedIG();

    Item getSyringe();
    Item getAP();
    Item getEggs();

    boolean isClearToReplace(LivingEntity entity);

}
