package com.cartoonishvillain.ImmortuosCalyx.Items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BaseItems extends Item{
    String lore1;
    String lore2;
    String lore3;
    String lore4;
    public BaseItems(Item.Properties properties, String lore1, String lore2, String lore3, String lore4) {
        super(properties);
        this.lore1 = lore1;
        this.lore2 = lore2;
        this.lore3 = lore3;
        this.lore4 = lore4;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(lore1 != ""){
            tooltip.add(new StringTextComponent(lore1));
            if(lore2 != ""){
                tooltip.add(new StringTextComponent(lore2));
                if(lore3 != ""){
                    tooltip.add(new StringTextComponent(lore3));
                    if(lore4 != ""){
                        tooltip.add(new StringTextComponent(lore4));
                    }
                }
            }
        }
    }
}
