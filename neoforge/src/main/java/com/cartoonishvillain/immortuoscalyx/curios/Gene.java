package com.cartoonishvillain.immortuoscalyx.curios;

import com.cartoonishvillain.immortuoscalyx.CommonImmortuos;
import com.cartoonishvillain.immortuoscalyx.damage.ImmortuosDamageTypes;
import com.cartoonishvillain.immortuoscalyx.data.gene.GeneComponent;
import com.cartoonishvillain.immortuoscalyx.items.DefaultGeneMethods;
import com.cartoonishvillain.immortuoscalyx.register.NeoDataComponentType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gene extends Item implements ICurioItem {
    public Gene(Item.Properties settings) {
        super(settings.durability(1));
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
        GeneComponent.GeneRecord geneData = stack.getComponents().getOrDefault(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord("", "", "", 0, true));
        LivingEntity entity = slotContext.entity();
        if (!geneData.geneValue1().isBlank()) {
            MobEffectInstance instance = DefaultGeneMethods.geneSelection(geneData.geneValue1(), geneData.quality());
            if (instance != null) {
                entity.removeEffect(instance.getEffect());
            }
        }

        if (!geneData.geneValue2().isBlank()) {
            MobEffectInstance instance = DefaultGeneMethods.geneSelection(geneData.geneValue2(), geneData.quality());
            if (instance != null) {
                entity.removeEffect(instance.getEffect());
            }
        }

        if (!geneData.contaminationValue().isBlank()) {
            MobEffectInstance instance = DefaultGeneMethods.contaminationSelection(geneData.contaminationValue(), geneData.quality());
            if (instance != null) {
                entity.removeEffect(instance.getEffect());
            }
        }

        entity.hurt(
                new DamageSource(entity.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ImmortuosDamageTypes.organ_damage)
                ), 6
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        GeneComponent.GeneRecord geneData = stack.getComponents().getOrDefault(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord("", "", "", 0, false));

        List<Component> addedComponents = new ArrayList<>();
        boolean showDisabledText = false;
        if (!geneData.geneValue1().isBlank()) {
            if (CommonImmortuos.getActiveGenes().containsKey(geneData.geneValue1())) {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue1()).withStyle(ChatFormatting.BLUE));
            } else {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue1()).withStyle(ChatFormatting.GRAY));
                showDisabledText = true;
            }
        }

        if (!geneData.geneValue2().isBlank()) {
            if (CommonImmortuos.getActiveGenes().containsKey(geneData.geneValue2())) {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue2()).withStyle(ChatFormatting.BLUE));
            } else {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue2()).withStyle(ChatFormatting.GRAY));
                showDisabledText = true;
            }
        }

        if (!geneData.contaminationValue().isBlank()) {
            if (CommonImmortuos.getActiveContaminations().containsKey(geneData.contaminationValue())) {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.contaminationValue()).withStyle(ChatFormatting.RED));
            } else {
                addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.contaminationValue()).withStyle(ChatFormatting.GRAY));
                showDisabledText = true;
            }
        }

        if (showDisabledText) {
            addedComponents.add(Component.translatable("gene.immortuos.disabledgene").withStyle(ChatFormatting.GRAY));
        }

        if (!addedComponents.isEmpty()) {
            addedComponents.add(Component.translatable("gene.immortuoscalyx.quality", geneData.quality()).withStyle(ChatFormatting.GOLD));

        }

        tooltipComponents.addAll(addedComponents);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity entity = slotContext.entity();
        if (entity.tickCount % 100 == 0 && !entity.level().isClientSide) {
            GeneComponent.GeneRecord geneData = stack.getComponents().getOrDefault(NeoDataComponentType.NEO_GENE_COMPONENT.get(), new GeneComponent.GeneRecord("", "", "", 0, true));

            if (!geneData.geneValue1().isBlank()) {
                MobEffectInstance effectInstance = DefaultGeneMethods.geneSelection(geneData.geneValue1(), geneData.quality());
                if (effectInstance != null) entity.addEffect(effectInstance);
            }

            if (!geneData.geneValue2().isBlank()) {
                MobEffectInstance effectInstance = DefaultGeneMethods.geneSelection(geneData.geneValue2(), geneData.quality());
                if (effectInstance != null) entity.addEffect(effectInstance);
            }

            if (!geneData.contaminationValue().isBlank()) {
                MobEffectInstance effectInstance = DefaultGeneMethods.contaminationSelection(geneData.contaminationValue(), geneData.quality());
                if (effectInstance != null) entity.addEffect(effectInstance);
            }
        }
    }
}
