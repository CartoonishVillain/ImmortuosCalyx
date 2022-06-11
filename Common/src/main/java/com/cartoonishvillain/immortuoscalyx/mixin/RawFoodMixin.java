package com.cartoonishvillain.immortuoscalyx.mixin;


import com.cartoonishvillain.immortuoscalyx.component.InfectionHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Item.class)
public class RawFoodMixin {
    private static final ArrayList<Item> rawItem = new ArrayList<>(Arrays.asList(Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COD, Items.SALMON, Items.ROTTEN_FLESH));

    @Inject(at = @At("HEAD"), method = "finishUsingItem")
    private void ImmortuosfinishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir){
        InfectionHandler.bioInfectCheck(itemStack, level, livingEntity);
    }


}
