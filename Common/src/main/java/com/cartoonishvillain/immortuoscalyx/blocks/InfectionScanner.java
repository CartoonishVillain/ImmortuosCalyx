package com.cartoonishvillain.immortuoscalyx.blocks;

import com.cartoonishvillain.immortuoscalyx.entities.InfectedEntity;
import com.cartoonishvillain.immortuoscalyx.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class InfectionScanner extends Block {
    int tickdelay = 0;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public InfectionScanner() {
        super(Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops());
        this.registerDefaultState(defaultBlockState().setValue(POWERED, false));
    }

    @Override
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void stepOn(Level world, BlockPos blockPos, BlockState p_152433_, Entity entity) {
        BlockState blockState = world.getBlockState(blockPos);
        if(!world.isClientSide()) {
            blockState = redstoneStrength(entity, blockState);
            world.setBlockAndUpdate(blockPos, blockState.setValue(POWERED, blockState.getValue(POWERED)));
            if (tickdelay <= 0) {
                if (blockState.getValue(POWERED)) {
                    world.playSound(null, blockPos, Services.PLATFORM.SCAN_BAD(), SoundSource.BLOCKS, 1, 1);
                    tickdelay = 30;
                } else {
                    world.playSound(null, blockPos, Services.PLATFORM.SCAN_GOOD(), SoundSource.BLOCKS, 1, 1);
                    tickdelay = 30;
                }
            } else tickdelay--;
        }
    }


    private BlockState redstoneStrength(Entity infected, BlockState state) {
        if(infected instanceof ServerPlayer) {
            boolean logic = Services.PLATFORM.getInfectionPercentage((ServerPlayer) infected) > 0;
            return state.setValue(POWERED, logic);
        }
        return state.setValue(POWERED, infected instanceof InfectedEntity);
    }
    @Override
    public int getSignal (BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) { return state.getValue(POWERED) ? 15 : 0; }

}
