package com.cartoonishvillain.immortuoscalyx.component;

import com.cartoonishvillain.immortuoscalyx.damage.InternalOrganDamage;
import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static com.cartoonishvillain.immortuoscalyx.platform.Services.PLATFORM;


public class ItemUsages {
    public static void useAntiParasitic(ItemStack stack, LivingEntity target){
        stack.shrink(1);
        AntiParasiticCure(target);
        target.level.playSound(null, target.getX(), target.getY(), target.getZ(), PLATFORM.getInjectSound(), SoundSource.PLAYERS, 1, 1);
    }

    public static void useCalyxide(ItemStack stack, LivingEntity target){
        stack.shrink(1);
        CalyxideCure(target);
        target.level.playSound(null, target.getX(), target.getY(), target.getZ(), PLATFORM.getInjectSound(), SoundSource.PLAYERS, 1, 1);
    }


    public static void useImmortuosCalyxEggs(ItemStack stack, LivingEntity target){
        stack.shrink(1);
        ImmortuosEggsInfection(target);
        target.level.playSound(null, target.getX(), target.getY(), target.getZ(), PLATFORM.getInjectSound(), SoundSource.PLAYERS, 1, 1);
    }

    public static void useStabilize(ItemStack stack, LivingEntity target){
        stack.shrink(1);
        AddResistance(target);
        target.level.playSound(null, target.getX(), target.getY(), target.getZ(), PLATFORM.getInjectSound(), SoundSource.PLAYERS, 1, 1);
    }

    public static void useScanner(LivingEntity target, LivingEntity user) {
        if (user instanceof Player) {
            if (target.equals(user)) {
                selfScan((Player) user);
            } else scan((Player) user, target);
        }
    }

    private static void scan(Player a, LivingEntity t){
        if(t instanceof Player){
            a.sendSystemMessage(Component.literal("===(" + t.getScoreboardName() + "'s stats)==="));
            a.sendSystemMessage(Component.literal("Health: " + t.getHealth()));
            a.sendSystemMessage(Component.literal("Food: " + ((Player) t).getFoodData().getFoodLevel()));
            a.sendSystemMessage(Component.literal("Infection Level: " + PLATFORM.getInfectionProgress(t) + "%"));
            a.sendSystemMessage(Component.literal("Resistance Multiplier: " + PLATFORM.getResistance(t)));
            a.sendSystemMessage(Component.literal("Stabilized: " + PLATFORM.getResistantDosage(t)));
        } else if(t instanceof InfectedEntity){
            a.sendSystemMessage(Component.literal("===(Target completely infected)==="));
        } else {
                a.sendSystemMessage(Component.literal("===(" + t.getName().getString() + "'s stats)==="));
                a.sendSystemMessage(Component.literal("Health: " + t.getHealth()));
                a.sendSystemMessage(Component.literal("Infection Rate: " + PLATFORM.getInfectionProgress(t) + "%"));
                a.sendSystemMessage(Component.literal("Stabilized: " + PLATFORM.getResistantDosage(t)));
                if(a.isCreative() && t instanceof Villager){
                    a.sendSystemMessage(Component.literal("Immortuos Follower: " + PLATFORM.getFollowerStatus(t)));
                }
        }
    }

    private static void selfScan(Player p){
            p.sendSystemMessage(Component.literal("===(" + p.getScoreboardName() + "'s stats)==="));
            p.sendSystemMessage(Component.literal("Saturation level: " + p.getFoodData().getSaturationLevel()));
            p.sendSystemMessage(Component.literal("Infection Level: " + PLATFORM.getInfectionProgress(p) + "%"));
            p.sendSystemMessage(Component.literal("Resistance Multiplier: " + PLATFORM.getResistance(p)));
            p.sendSystemMessage(Component.literal("Stabilized: " + PLATFORM.getResistantDosage(p)));
    }

    private static void AntiParasiticCure(LivingEntity target) {
            if(PLATFORM.getInfectionProgress(target) <= 40){//is only effective in curing in dormant phase
                PLATFORM.addInfectionProgress(-10, target);
            }
            if(PLATFORM.getInfectionProgress(target) < 0) PLATFORM.setInfectionProgress(0, target);
            //TODO: ImmortuosCalyx.config.RESISTGIVENAP.get()
            PLATFORM.setResistance(6, target);
        target.hurt(InternalOrganDamage.causeInternalDamage(target), 2f);  //divide the reduced infection rate by 5, multiply by 4. 100 infection rate -> 25/5 = 5, * 4 = 20. Vanilla instant kill.
    }

    private static void CalyxideCure(LivingEntity target){
            if(PLATFORM.getInfectionProgress(target) >= 75){// 75 is internal damage threshold.
                int tempinfection = PLATFORM.getInfectionProgress(target);
                tempinfection -= 70; // reduce 75 to 1, 100 to 25, and all the numbers in between
                target.hurt(InternalOrganDamage.causeInternalDamage(target), ((tempinfection/5)*4));  //divide the reduced infection rate by 5, multiply by 4. 100 infection rate -> 25/5 = 5, * 4 = 20. Vanilla instant kill.
            }

            PLATFORM.addInfectionProgress(-40, target);
            if(PLATFORM.getInfectionProgress(target) < 0) PLATFORM.setInfectionProgress(0, target);
    }

    private static void AddResistance(Entity target){
        if(target instanceof LivingEntity)
        PLATFORM.setResistantDosage(true, (LivingEntity) target);
    }

    private static void ImmortuosEggsInfection(LivingEntity target){
        PLATFORM.setInfectionProgressIfLower(1, target);
    }
}
