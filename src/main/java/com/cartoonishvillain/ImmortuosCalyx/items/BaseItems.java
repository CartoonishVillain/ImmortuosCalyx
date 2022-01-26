package com.cartoonishvillain.ImmortuosCalyx.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BaseItems extends Item{

    ArrayList<String> listOfLore;
    public BaseItems(Item.Properties properties, String... lore) {
        super(properties);
        listOfLore = new ArrayList<>();
        listOfLore.addAll(List.of(lore));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        for(String loreItem : listOfLore) {
            tooltip.add(new TextComponent(loreItem));
        }
    }
}
