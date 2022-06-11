package com.cartoonishvillain.immortuoscalyx.mixin;

import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class SyringeUsageMixin {


    @Inject(at = @At("TAIL"), method = "interactOn")
    private void ImmortuosinteractOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir){
        Player player = (Player) (Object) this;
        if(!entity.level.isClientSide() && player.getMainHandItem().getItem().equals(Services.PLATFORM.getSyringe()) && interactionHand.equals(InteractionHand.MAIN_HAND)){
            boolean extract = false;
            if(entity instanceof Slime){
                extract = true;
                player.getMainHandItem().shrink(1);
                ItemStack itemStack = new ItemStack(Services.PLATFORM.getAP());
                player.getInventory().add(itemStack);
            }
            if(entity instanceof InfectedEntity || Services.PLATFORM.getInfectionProgress((LivingEntity) entity) > 50){
                extract = true;
                player.getMainHandItem().shrink(1);
                ItemStack itemStack = new ItemStack(Services.PLATFORM.getEggs());
                player.getInventory().add(itemStack);
            }

            if (extract) player.level.playSound(null, player.getX(), player.getY(), player.getZ(), Services.PLATFORM.getExtractSound(), SoundSource.PLAYERS, 1, 1);
        }
    }

}
