package com.cartoonishvillain.immortuoscalyx.platform;

import com.cartoonishvillain.immortuoscalyx.ForgeImmortuosCalyx;
import com.cartoonishvillain.immortuoscalyx.Register;
import com.cartoonishvillain.immortuoscalyx.configs.CommonConfig;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedIGEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedPlayerEntity;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedVillagerEntity;
import com.cartoonishvillain.immortuoscalyx.infection.InfectionManagerCapability;
import com.cartoonishvillain.immortuoscalyx.platform.services.IPlatformHelper;
import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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
    public int getInfectionProgress(LivingEntity entity) {
        AtomicInteger val = new AtomicInteger(0);
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            val.set(h.getInfectionProgress());
        });
        return val.get();
    }

    @Override
    public void setInfectionProgress(int progress, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setInfectionProgress(progress);
        });
    }

    @Override
    public void setInfectionProgressIfLower(int progress, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setInfectionProgressIfLower(progress);
        });
    }

    @Override
    public void addInfectionProgress(int change, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.addInfectionProgress(change);
        });
    }

    @Override
    public int getInfectionTimerStat(LivingEntity entity) {
        AtomicInteger val = new AtomicInteger(0);
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            val.set(h.getInfectionTimer());
        });
        return val.get();
    }

    @Override
    public void addInfectionTimer(int timer, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.addInfectionTimer(timer);
        });
    }

    @Override
    public void setInfectionTimer(int timer, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setInfectionTimer(timer);
        });
    }

    @Override
    public double getResistance(LivingEntity entity) {
        AtomicDouble val = new AtomicDouble(0);
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            val.set(h.getResistance());
        });
        return val.get();
    }

    @Override
    public void setResistance(double Resistance, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setResistance(Resistance);
        });
    }

    @Override
    public void addResistance(double Resistance, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.addResistance(Resistance);
        });
    }

    @Override
    public boolean getFollowerStatus(LivingEntity entity) {
        AtomicBoolean val = new AtomicBoolean(false);
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            val.set(h.isFollower());
        });
        return val.get();
    }

    @Override
    public void isFollowerStatus(boolean follower, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setFollower(follower);
        });
    }

    @Override
    public boolean getResistantDosage(LivingEntity entity) {
        AtomicBoolean val = new AtomicBoolean(false);
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            val.set(h.isResistant());
        });
        return val.get();
    }

    @Override
    public void setResistantDosage(boolean dosage, LivingEntity entity) {
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
            h.setResistant(dosage);
        });
    }

    @Override
    public boolean getAntiChat() {
        return ForgeImmortuosCalyx.config.ANTICHAT.get();
    }

    @Override
    public boolean getInfectedChatNoise() {
        return ForgeImmortuosCalyx.config.INFECTEDCHATNOISE.get();
    }

    @Override
    public boolean getPVPContagion() {
        return ForgeImmortuosCalyx.config.PVPCONTAGION.get();
    }

    @Override
    public boolean getHeatSlow() {
        return ForgeImmortuosCalyx.config.HEATSLOW.get();
    }

    @Override
    public boolean getColdFast() {
        return ForgeImmortuosCalyx.config.COLDFAST.get();
    }

    @Override
    public boolean getWaterBreathing() {
        return ForgeImmortuosCalyx.config.WATERBREATHING.get();
    }

    @Override
    public boolean getColdConduitPower() {
        return ForgeImmortuosCalyx.config.COLDCONDUITPOWER.get();
    }

    @Override
    public boolean getWarmWeakness() {
        return ForgeImmortuosCalyx.config.WARMWEAKNESS.get();
    }

    @Override
    public boolean getColdStrength() {
        return ForgeImmortuosCalyx.config.COLDSTRENGTH.get();
    }

    @Override
    public boolean getBlindness() {
        return ForgeImmortuosCalyx.config.BLINDNESS.get();
    }

    @Override
    public double getArmorResist() {
        return ForgeImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get();
    }

    @Override
    public double getAPResist() {
        return ForgeImmortuosCalyx.config.RESISTGIVENAP.get();
    }

    @Override
    public int getInfectedEntityValue() {
        return ForgeImmortuosCalyx.config.INFECTEDENTITYINFECTIONVALUE.get();
    }

    @Override
    public int getZombieValue() {
        return ForgeImmortuosCalyx.config.ZOMBIEINFECTIONVALUE.get();
    }

    @Override
    public int getRawFoodValue() {
        return ForgeImmortuosCalyx.config.RAWFOODINFECTIONVALUE.get();
    }

    @Override
    public int getEffectMsgOne() {
        return ForgeImmortuosCalyx.config.EFFECTMESSAGEONE.get();
    }

    @Override
    public int getEffectMsgTwo() {
        return ForgeImmortuosCalyx.config.EFFECTMESSAGETWO.get();
    }

    @Override
    public int getEffectChat() {
        return ForgeImmortuosCalyx.config.EFFECTCHAT.get();
    }

    @Override
    public int getPlayerInfection() {
        return ForgeImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get();
    }

    @Override
    public int getEffectSpeed() {
        return ForgeImmortuosCalyx.config.EFFECTSPEED.get();
    }

    @Override
    public int getEffectWaterBreathing() {
        return ForgeImmortuosCalyx.config.EFFECTWATERBREATH.get();
    }

    @Override
    public int getEffectStrength() {
        return ForgeImmortuosCalyx.config.EFFECTSTRENGTH.get();
    }

    @Override
    public int getEffectBlind() {
        return ForgeImmortuosCalyx.config.EFFECTBLIND.get();
    }

    @Override
    public int getEffectDamage() {
        return ForgeImmortuosCalyx.config.EFFECTDAMAGE.get();
    }

    @Override
    public int getEffectImpediment() {
        return ForgeImmortuosCalyx.config.EFFECTIMPEDIMENT.get();
    }

    @Override
    public int getFollowerChance() {
        return ForgeImmortuosCalyx.config.VILLAGERFOLLOWERCHANCE.get();
    }

    @Override
    public int getFollowerImmunity() {
        return ForgeImmortuosCalyx.config.VILLAGERFOLLOWERIMMUNITY.get();
    }

    @Override
    public int getVillagerSlowOne() {
        return ForgeImmortuosCalyx.config.VILLAGERSLOWONE.get();
    }

    @Override
    public int getVillagerSlowTwo() {
        return ForgeImmortuosCalyx.config.VILLAGERSLOWTWO.get();
    }

    @Override
    public int getVillagerNoTrade() {
        return ForgeImmortuosCalyx.config.VILLAGERNOTRADE.get();
    }

    @Override
    public int getVillagerLethal() {
        return ForgeImmortuosCalyx.config.VILLAGERLETHAL.get();
    }

    @Override
    public int getIronSlow() {
        return ForgeImmortuosCalyx.config.IRONGOLEMSLOW.get();
    }

    @Override
    public int getIronWeak() {
        return ForgeImmortuosCalyx.config.IRONGOLEMWEAK.get();
    }

    @Override
    public int getIronLethal() {
        return ForgeImmortuosCalyx.config.IRONGOLEMLETHAL.get();
    }

    @Override
    public int getEggInfectionStart() {
        return ForgeImmortuosCalyx.config.EGGINFECTIONSTART.get();
    }

    @Override
    public int getInfectionDamage() {
        return ForgeImmortuosCalyx.config.INFECTIONDAMAGE.get();
    }

    @Override
    public int getPVPContagionRelief() {
        return ForgeImmortuosCalyx.config.PVPCONTAGIONRELIEF.get();
    }

    @Override
    public int getPVPContagionAmount() {
        return ForgeImmortuosCalyx.config.PVPCONTAGIONAMOUNT.get();
    }

    @Override
    public int getInfectionTimer() {
        return ForgeImmortuosCalyx.config.INFECTIONTIMER.get();
    }

    @Override
    public boolean isFormattedChat() {
        return ForgeImmortuosCalyx.config.FORMATTEDINFECTCHAT.get();
    }

    @Override
    public boolean isInfectedDeath() {
        return ForgeImmortuosCalyx.config.INFECTONDEATH.get();
    }

    @Override
    public boolean getHostileInfectionCleanse() {
        return ForgeImmortuosCalyx.commonConfig.HOSTILEINFECTIONINCLEANSE.get();
    }

    @Override
    public boolean getPlayerInfectionCleanse() {
        return ForgeImmortuosCalyx.commonConfig.PLAYERINFECTIONINCLEANSE.get();
    }

    @Override
    public boolean getRawFoodInfectionCleanse() {
        return ForgeImmortuosCalyx.commonConfig.RAWFOODINFECTIONINCLEANSE.get();
    }

    @Override
    public boolean getVoiceChatModSupport() {
        return ForgeImmortuosCalyx.config.VOICECHATSUPPORT.get();
    }

    @Override
    public SoundEvent getScanBad() {
        return Register.SCANBAD.get();
    }

    @Override
    public SoundEvent getScanGood() {
        return Register.SCANCLEAR.get();
    }

    @Override
    public SoundEvent getHumanAmbient() {
        return Register.HUMANAMBIENT.get();
    }

    @Override
    public SoundEvent getHumanDeath() {
        return Register.HUMANDEATH.get();
    }

    @Override
    public SoundEvent getHumanHurt() {
        return Register.HUMANHURT.get();
    }

    @Override
    public SoundEvent getIGHurt() {
        return Register.IGHURT.get();
    }

    @Override
    public SoundEvent getIGDeath() {
        return Register.IGDEATH.get();
    }

    @Override
    public SoundEvent getVilIdle() {
        return Register.VILIDLE.get();
    }

    @Override
    public SoundEvent getVilHurt() {
        return Register.VILHURT.get();
    }

    @Override
    public SoundEvent getVilDeath() {
        return Register.VILDEATH.get();
    }

    @Override
    public SoundEvent getInjectSound() {
        return Register.INJECT.get();
    }

    @Override
    public SoundEvent getExtractSound() {
        return Register.EXTRACT.get();
    }

    @Override
    public ArrayList<ResourceLocation> getDimensions() {
        return CommonConfig.getDimensions();
    }

    @Override
    public CreativeModeTab getTab() {
        return ForgeImmortuosCalyx.TAB;
    }

    @Override
    public EntityType<InfectedPlayerEntity> getInfectedHuman() {
        return Register.INFECTEDPLAYER.get();
    }

    @Override
    public EntityType<InfectedVillagerEntity> getInfectedVillager() {
        return Register.INFECTEDVILLAGER.get();
    }

    @Override
    public EntityType<InfectedIGEntity> getInfectedIG() {
        return Register.INFECTEDIG.get();
    }

    @Override
    public Item getSyringe() {
        return Register.SYRINGE.get();
    }

    @Override
    public Item getAP() {
        return Register.GENERALANTIPARASITIC.get();
    }

    @Override
    public Item getEggs() {
        return Register.IMMORTUOSCALYXEGGS.get();
    }
}
