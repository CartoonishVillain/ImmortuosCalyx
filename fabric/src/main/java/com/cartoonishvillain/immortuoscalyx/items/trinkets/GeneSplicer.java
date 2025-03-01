package com.cartoonishvillain.immortuoscalyx.items.trinkets;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class GeneSplicer extends TrinketItem {
    int slots = 0;
    public GeneSplicer(Properties settings, int slotsAdded) {
        super(settings);
        slots = slotsAdded;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, ResourceLocation slotIdentifier) {
        var modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);

        SlotAttributes.addSlotModifier(modifiers, "head/gene", slotIdentifier, slots, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}
