package com.cartoonishvillain.immortuoscalyx.blocks;

import com.cartoonishvillain.immortuoscalyx.platform.Services;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class GeneEncoder extends HorizontalDirectionalBlock {
    public static final MapCodec<GeneEncoder> CODEC = simpleCodec(p -> new GeneEncoder());

    public GeneEncoder() {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops());
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide() && pHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack mainStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);

            // Identify Gene condition - Unidentified Gene in one hand, Honey comb in the other.
            if (
                    (mainStack.getItem() == Services.PLATFORM.UNIDENTIFIED_GENE() && offStack.getItem() == Items.HONEYCOMB) ||
                    (mainStack.getItem() == Items.HONEYCOMB && offStack.getItem() == Services.PLATFORM.UNIDENTIFIED_GENE())
            ) {
                //Always succeed
                pLevel.playSound(null, pPos, Services.PLATFORM.EXTRACT(), SoundSource.BLOCKS);
                mainStack.shrink(1);
                offStack.shrink(1);
                ItemStack identifiedGene = new ItemStack(Holder.direct(Services.PLATFORM.IDENTIFIED_GENE()));
                Services.PLATFORM.updateGeneAndGiveToPlayer(pPlayer, identifiedGene, getRandomGene(pLevel.getRandom()), pLevel.random.nextInt(39)+1);
            }
            if (
                    (mainStack.getItem() == Services.PLATFORM.IDENTIFIED_GENE() && offStack.getItem() == Services.PLATFORM.IDENTIFIED_GENE())
            ) {
                Services.PLATFORM.tryGeneCombination(pPlayer, mainStack, offStack);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    private String getRandomGene(RandomSource random) {
        return switch (random.nextInt(8)) {
            case 1 -> "gene_zombie";
            case 2 -> "gene_ocelot";
            case 3 -> "gene_turtle";
            case 4 -> "gene_iron_golem";
            case 5 -> "gene_frog";
            case 6 -> "gene_silverfish";
            case 7 -> "gene_enderman";
            default -> "gene_immortuos";
        };
    }
}
