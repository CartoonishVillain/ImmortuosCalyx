package com.cartoonishvillain.ImmortuosCalyx.Blocks;

import com.cartoonishvillain.ImmortuosCalyx.Infection.InfectionManagerCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfectionScanner extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public InfectionScanner() {
        super(Properties.of(Material.STONE).strength(3f).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops());
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
        if(!world.isClientSide()){
            blockState = redstoneStrength(entity, blockState);}
        world.setBlockAndUpdate(blockPos, blockState.setValue(POWERED, blockState.getValue(POWERED)));    }


    @Nonnull
    private BlockState redstoneStrength(Entity infected, BlockState state) {
        AtomicBoolean isinfected = new AtomicBoolean(false);
        infected.getCapability(InfectionManagerCapability.INSTANCE).ifPresent(h->{
            isinfected.set(h.getInfectionProgress() > 0);
        });
        boolean logic = isinfected.get();
        return state.setValue(POWERED, logic);
    }
    @Override
    public int getSignal (BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) { return state.getValue(POWERED) ? 15 : 0; }

}
