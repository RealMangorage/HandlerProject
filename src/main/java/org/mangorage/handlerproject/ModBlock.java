package org.mangorage.handlerproject;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ModBlock extends Block implements EntityBlock {
    public ModBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new ModBE(p_153215_, p_153216_);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            var item = player.getItemInHand(hand).getItem();
            level.getBlockEntity(pos, HandlerProject.EXAMPLE_BE.get()).ifPresent(e -> e.setFilter(item));
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    @SuppressWarnings("all")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (BlockEntityTicker<T>) new BlockEntityTicker<ModBE>() {

            @Override
            public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_, ModBE p_155256_) {
                if (!p_155253_.isClientSide())
                    p_155256_.tick();
            }
        };
    }
}
