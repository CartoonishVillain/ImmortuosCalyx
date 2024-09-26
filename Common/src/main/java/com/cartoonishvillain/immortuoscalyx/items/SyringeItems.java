package com.cartoonishvillain.immortuoscalyx.items;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler.useAntiParasitic;
import static com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler.useCalyxanide;

public class SyringeItems extends Item {
    Syringes syringeType;

    public SyringeItems(Properties pProperties, Syringes syringeType) {
        super(pProperties);
        this.syringeType = syringeType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching()) {
            boolean used = false;
            switch (syringeType) {
                case CALYXANIDE -> {
                    used = true;
                    useCalyxanide(pPlayer);
                }
                case ANTIPARASITIC -> {
                    used = true;
                    useAntiParasitic(pPlayer);
                }

                case IMMORTUOS_SAMPLE -> {
                    used = true;
                    if (pPlayer instanceof ServerPlayer) AbstractInfectionHandler.useImmortuosSample((ServerPlayer) pPlayer);
                }
            }

            if (used) {
                pPlayer.getItemInHand(pUsedHand).shrink(1);
                pPlayer.level().playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), Services.PLATFORM.INJECT(), SoundSource.PLAYERS);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        boolean used = false;
        switch (syringeType) {
            case CALYXANIDE -> {
                used = true;
                useCalyxanide(pTarget);
            }

            case ANTIPARASITIC -> {
                used = true;
                useAntiParasitic(pTarget);
            }

            case IMMORTUOS_SAMPLE -> {
                used = true;
                if (pTarget instanceof ServerPlayer) AbstractInfectionHandler.useImmortuosSample((ServerPlayer) pTarget);
            }
        }
        if (used) {
            pStack.shrink(1);
            pTarget.level().playSound(null, pTarget.getX(), pTarget.getY(), pTarget.getZ(), Services.PLATFORM.INJECT(), SoundSource.PLAYERS);
        }
        return false;
    }
}
