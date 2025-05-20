package com.cartoonishvillain.immortuoscalyx.items.trinkets;

import com.cartoonishvillain.immortuoscalyx.damage.ImmortuosDamageTypes;
import com.cartoonishvillain.immortuoscalyx.data.gene.GeneItemComponent;
import com.cartoonishvillain.immortuoscalyx.items.DefaultGeneMethods;
import com.cartoonishvillain.immortuoscalyx.register.FabricItemComponents;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class Gene extends TrinketItem {
    public Gene(Properties settings) {
        super(settings.durability(1));
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        GeneItemComponent.GeneData geneData = stack.getComponents().getOrDefault(FabricItemComponents.GENE_DATA.get(), new GeneItemComponent.GeneData("", "", "", 0, true));

        if (!geneData.geneValue1().isBlank()) {
            entity.removeEffect(DefaultGeneMethods.geneSelection(geneData.geneValue1(), geneData.quality()).getEffect());
        }

        if (!geneData.geneValue2().isBlank()) {
            entity.removeEffect(DefaultGeneMethods.geneSelection(geneData.geneValue2(), geneData.quality()).getEffect());
        }

        if (!geneData.contaminationValue().isBlank()) {
            entity.removeEffect(DefaultGeneMethods.contaminationSelection(geneData.contaminationValue(), geneData.quality()).getEffect());
        }

        entity.hurt(
                new DamageSource(entity.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ImmortuosDamageTypes.organ_damage)
                ), 6
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        GeneItemComponent.GeneData geneData = stack.getComponents().getOrDefault(FabricItemComponents.GENE_DATA.get(), new GeneItemComponent.GeneData("", "", "", 0, false));

        List<Component> addedComponents = new ArrayList<>();
        if (!geneData.geneValue1().isBlank()) {
            addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue1()).withStyle(ChatFormatting.BLUE));
        }

        if (!geneData.geneValue2().isBlank()) {
            addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.geneValue2()).withStyle(ChatFormatting.BLUE));
        }

        if (!geneData.contaminationValue().isBlank()) {
            addedComponents.add(Component.translatable("gene.immortuoscalyx." + geneData.contaminationValue()).withStyle(ChatFormatting.RED));
        }

        if (!addedComponents.isEmpty()) {
            addedComponents.add(Component.translatable("gene.immortuoscalyx.quality", geneData.quality()).withStyle(ChatFormatting.GOLD));
        }

        tooltipComponents.addAll(addedComponents);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        if (entity.tickCount % 100 == 0 && !entity.level().isClientSide) {
            GeneItemComponent.GeneData geneData = stack.getComponents().getOrDefault(FabricItemComponents.GENE_DATA.get(), new GeneItemComponent.GeneData("", "", "", 0, true));

            if (!geneData.geneValue1().isBlank()) {
                entity.addEffect(DefaultGeneMethods.geneSelection(geneData.geneValue1(), geneData.quality()));
            }

            if (!geneData.geneValue2().isBlank()) {
                entity.addEffect(DefaultGeneMethods.geneSelection(geneData.geneValue2(), geneData.quality()));
            }

            if (!geneData.contaminationValue().isBlank()) {
                entity.addEffect(DefaultGeneMethods.contaminationSelection(geneData.contaminationValue(), geneData.quality()));
            }
        }
    }
}
