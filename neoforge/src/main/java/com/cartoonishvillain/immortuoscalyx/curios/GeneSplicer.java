package com.cartoonishvillain.immortuoscalyx.curios;

import com.cartoonishvillain.immortuoscalyx.Constants;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GeneSplicer extends Item implements ICurioItem {
    int slots = 0;
    public GeneSplicer(Item.Properties settings, int slotsAdded) {
        super(settings);
        slots = slotsAdded;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
        modifiers.put(
                SlotAttribute.getOrCreate("gene"), new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gene_slot"), slots, AttributeModifier.Operation.ADD_VALUE)
        );
        return modifiers;
    }
}
