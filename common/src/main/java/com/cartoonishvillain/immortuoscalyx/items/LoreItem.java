package com.cartoonishvillain.immortuoscalyx.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LoreItem extends Item {
    private List<Component> components;
    public LoreItem(Properties pProperties, List<Component> components) {
        super(pProperties);
        this.components = components;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.addAll(components);
    }
}
