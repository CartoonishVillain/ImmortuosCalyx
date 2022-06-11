package com.cartoonishvillain.immortuoscalyx.blocks;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.ChatFormatting;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class ScannerBlockItem extends BlockItem {
    public ScannerBlockItem(Block block) {
        super(block, new Properties().tab(Services.PLATFORM.getTab()));
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> list, TooltipFlag p_77624_4_) {
        list.add(new TextComponent(ChatFormatting.BLUE + "Scans for infection when stepped on."));
        list.add(new TextComponent(ChatFormatting.BLUE + "If an infection is found, sends redstone signal."));
        list.add(new TextComponent(ChatFormatting.BLUE + "If one is not found, it removes it's signal."));

    }
}
