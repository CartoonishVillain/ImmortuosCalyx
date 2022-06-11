package com.cartoonishvillain.immortuoscalyx.component;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InfectionHandler {

    //known infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity victim, float infectChance, int amount){
            int armorProtection = (int) (victim.getArmorValue() * Services.PLATFORM.getArmorResist());
            double bioResist = Services.PLATFORM.getResistance(victim);
            int InfectThreshold = (int) ((infectChance - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                Services.PLATFORM.setInfectionProgressIfLower(amount, victim);
            }
    }


    //unknown infectionChance. Non-Player attack vector.
    public static void infectEntity(LivingEntity aggressor, LivingEntity victim, int amount){
            int infectChance = 0;
            infectChance = Services.PLATFORM.getInfectionProgress(aggressor);

            int armorProtection = (int) (victim.getArmorValue() * Services.PLATFORM.getArmorResist());
            double bioResist = Services.PLATFORM.getResistance(victim);
            int InfectThreshold = (int) ((infectChance - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                Services.PLATFORM.setInfectionProgressIfLower(amount, victim);
            }
    }

    //Infected player attacks a non-player entity.
    public static void infectEntityByPlayer(Player aggressor, LivingEntity victim, int amount){
            AtomicInteger infectChance = new AtomicInteger(0);
            if(Services.PLATFORM.getInfectionProgress(aggressor) >= Services.PLATFORM.getPlayerInfection()) infectChance.set(Services.PLATFORM.getInfectionProgress(aggressor));

            int armorProtection = (int) (victim.getArmorValue() * Services.PLATFORM.getArmorResist());
            double bioResist = Services.PLATFORM.getResistance(victim);
            int InfectThreshold = (int) ((infectChance.get() - armorProtection)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                Services.PLATFORM.setInfectionProgressIfLower(amount, victim);
            }
    }

    //PVP Contagion
    public static void infectPlayerByPlayer(Player aggressor, Player victim, int amount) {
        if (Services.PLATFORM.getPVPContagion() && (!CommonImmortuos.DimensionExclusion.contains(victim.level.dimension().location()) || !Services.PLATFORM.getPlayerInfectionCleanse())) {
                AtomicInteger infectChance = new AtomicInteger(0);
                if (Services.PLATFORM.getInfectionProgress(aggressor) >= Services.PLATFORM.getPlayerInfection())
                    infectChance.set(Services.PLATFORM.getInfectionProgress(aggressor));

                AtomicBoolean successfulTransfer = new AtomicBoolean(false);
                int armorProtection = (int) (victim.getArmorValue() * Services.PLATFORM.getArmorResist());
                double bioResist = Services.PLATFORM.getResistance(victim);
                int InfectThreshold = (int) ((infectChance.get() - armorProtection) / bioResist);
                if (InfectThreshold > victim.getRandom().nextInt(100)) {
                    if(Services.PLATFORM.getInfectionProgress(victim) == 0) successfulTransfer.set(true);
                    Services.PLATFORM.setInfectionProgressIfLower(amount, victim);
                }

            if(successfulTransfer.get()){
                Services.PLATFORM.addInfectionProgress(-Services.PLATFORM.getPVPContagionRelief(), aggressor);
                if(!aggressor.level.isClientSide)aggressor.level.playSound(null, aggressor.blockPosition(), Services.PLATFORM.getHumanHurt(), SoundSource.PLAYERS, 1f, 1.2f);
            }
        }
    }

    //Guaranteed Contagion.
    public static void staticInfect(LivingEntity entity, int amount){
            Services.PLATFORM.setInfectionProgressIfLower(amount, entity);
    }


    public static void bioInfectCheck(ItemStack itemStack, Level level, LivingEntity livingEntity){
        if(!level.isClientSide && livingEntity instanceof Player && itemStack.isEdible() && CommonImmortuos.rawItem.contains(itemStack.getItem()) && (!CommonImmortuos.DimensionExclusion.contains(livingEntity.level.dimension().location()) || !Services.PLATFORM.getRawFoodInfectionCleanse())){
            InfectionHandler.bioInfect(livingEntity, Services.PLATFORM.getRawFoodValue(), 1);
        }
    }

    //Non-Attack Vector infection. Armor ignored.
    public static void bioInfect(LivingEntity victim, float infectChance, int amount){
            double bioResist = Services.PLATFORM.getResistance(victim);
            int InfectThreshold = (int) ((infectChance)/bioResist);
            if(InfectThreshold > victim.getRandom().nextInt(100)){
                Services.PLATFORM.setInfectionProgressIfLower(amount, victim);
            }
    }
}
