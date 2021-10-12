package com.cartoonishvillain.ImmortuosCalyx.Infection;

import com.cartoonishvillain.ImmortuosCalyx.ImmortuosCalyx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

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
}
