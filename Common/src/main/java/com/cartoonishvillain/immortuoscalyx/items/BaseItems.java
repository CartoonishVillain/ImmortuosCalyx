package com.cartoonishvillain.immortuoscalyx.items;

import com.cartoonishvillain.immortuoscalyx.component.ItemUsages;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BaseItems extends Item{

    ArrayList<String> listOfLore;
    ItemFunctionality itemFunctionality;
    public BaseItems(Properties properties, ItemFunctionality itemFunctionality, String... lore) {
        super(properties);
        listOfLore = new ArrayList<>();
        listOfLore.addAll(List.of(lore));
        this.itemFunctionality = itemFunctionality;

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(player.isCrouching() && !itemFunctionality.equals(ItemFunctionality.NONE) && interactionHand.equals(InteractionHand.MAIN_HAND) && !level.isClientSide && player.getMainHandItem().getItem() instanceof BaseItems){
            if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.ANTIBIOTIC)){
                ItemUsages.useAntiParasitic(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.CALYXIDE)){
                ItemUsages.useCalyxide(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.EGGS)){
                ItemUsages.useImmortuosCalyxEggs(player.getMainHandItem(), player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.SCANNER)){
                ItemUsages.useScanner(player, player);
            } else if(((BaseItems) player.getMainHandItem().getItem()).getItemFunctionality().equals(ItemFunctionality.STABILIZE)){
                ItemUsages.useStabilize(player.getMainHandItem(), player);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        for(String loreItem : listOfLore) {
            tooltip.add(Component.literal(loreItem));
        }
    }

    public ItemFunctionality getItemFunctionality(){
        return itemFunctionality;
    }
}
