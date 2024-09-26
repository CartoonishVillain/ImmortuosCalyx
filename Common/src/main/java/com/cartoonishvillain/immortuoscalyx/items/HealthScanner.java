package com.cartoonishvillain.immortuoscalyx.items;

import com.cartoonishvillain.immortuoscalyx.AbstractInfectionHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HealthScanner extends Item {
    public HealthScanner(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching() && pPlayer instanceof ServerPlayer)
            AbstractInfectionHandler.selfHealthCheck((ServerPlayer) pPlayer);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
