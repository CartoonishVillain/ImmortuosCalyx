package com.cartoonishvillain.ImmortuosCalyx.Infection;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import com.cartoonishvillain.ImmortuosCalyx.Register;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InfectionHandler {

    //known infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity victim, float infectChance, int amount){
        victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get());
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
        });
    }


    //unknown infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity aggressor, LivingEntity victim, int amount){
        AtomicInteger infectChance = new AtomicInteger(0);
        aggressor.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            infectChance.set(h.getInfectionProgress());
        });

        victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get());
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance.get() - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
        });
    }

    //Infected player attacks a non-player entity.
    public static void infectEntityByPlayer(PlayerEntity aggressor, LivingEntity victim, int amount){
        AtomicInteger infectChance = new AtomicInteger(0);
        aggressor.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            if(h.getInfectionProgress() >= ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get()) infectChance.set(h.getInfectionProgress());
        });

        victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get());
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance.get() - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
        });
    }

    //PVP Contagion
    public static void infectPlayerByPlayer(PlayerEntity aggressor, PlayerEntity victim, int amount) {
        if (ImmortuosCalyx.config.PVPCONTAGION.get() && (!ImmortuosCalyx.DimensionExclusion.contains(victim.level.dimension().location()) || !ImmortuosCalyx.commonConfig.PLAYERINFECTIONINCLEANSE.get())) {
            AtomicInteger infectChance = new AtomicInteger(0);
            aggressor.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                if (h.getInfectionProgress() >= ImmortuosCalyx.config.PLAYERINFECTIONTHRESHOLD.get())
                    infectChance.set(h.getInfectionProgress());
            });

            AtomicBoolean successfulTransfer = new AtomicBoolean(false);
            victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h -> {
                int armorProtection = (int) (victim.getArmorValue() * ImmortuosCalyx.config.ARMORRESISTMULTIPLIER.get());
                double bioResist = h.getResistance();
                int InfectThreshold = (int) ((infectChance.get() - armorProtection) / bioResist);
                if (InfectThreshold > victim.getRandom().nextInt(100)) {
                    if(h.getInfectionProgress() == 0) successfulTransfer.set(true);
                    h.setInfectionProgressIfLower(amount);
                }
            });

            if(successfulTransfer.get()){
                aggressor.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
                    h.addInfectionProgress(-ImmortuosCalyx.config.PVPCONTAGIONRELIEF.get());
                });
                if(!aggressor.level.isClientSide)aggressor.level.playSound(null, aggressor.blockPosition(), Register.HUMANHURT.get(), SoundCategory.PLAYERS, 1f, 1.2f);
            }
        }
    }

    //Guaranteed Contagion.
    public static void staticInfect(LivingEntity entity, int amount){
        entity.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            h.setInfectionProgressIfLower(amount);
        });
    }

    //Non-Attack Vector infection. Armor ignored.
    public static void bioInfect(LivingEntity victim, float infectChance, int amount){
        victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
        });
    }

    //Non-Attack Vector infection. Unknown chance.
    public static void commonAerosol(LivingEntity victim, LivingEntity transmissionVector, int amount){
        AtomicInteger infectChance = new AtomicInteger(0);
        transmissionVector.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            infectChance.set(h.getInfectionProgress());
        });

        victim.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            double bioResist = h.getResistance();
            int InfectThreshold = (int) ((infectChance.get())/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                h.setInfectionProgressIfLower(amount);
            }
        });
    }

}
