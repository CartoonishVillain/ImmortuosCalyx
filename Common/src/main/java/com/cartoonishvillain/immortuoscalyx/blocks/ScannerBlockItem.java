package com.cartoonishvillain.immortuoscalyx.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ScannerBlockItem extends BlockItem {
    public ScannerBlockItem(Block block) {
        super(block, new Properties());
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("block.immortuos.scanner").withStyle(ChatFormatting.BLUE));
    }
}
