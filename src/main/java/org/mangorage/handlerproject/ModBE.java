package org.mangorage.handlerproject;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mangorage.handlerproject.core.HandlerMode;
import org.mangorage.handlerproject.core.LimitedSlotLogic;
import org.mangorage.handlerproject.core.SlotItemStackHandler;
import org.mangorage.handlerproject.core.SlotItemStackHandlerProxy;
import org.mangorage.handlerproject.core.SlotLogic;
import org.mangorage.handlerproject.core.SlotLogicBuilder;


public class ModBE extends BlockEntity {
    private final SlotItemStackHandler handler = new SlotItemStackHandler(
            SlotLogicBuilder.create()
                    .slot(0, new LimitedSlotLogic(HandlerMode.BOTH, 10, stack -> stack.is(Tags.Items.ORES)))
                    .build(),
            1
    );

    private final LazyOptional<IItemHandler> LAZY_HANDLER_UP = LazyOptional.of(
            () -> new SlotItemStackHandlerProxy(
                    SlotLogicBuilder.create()
                            .slot(0, new SlotLogic(HandlerMode.INSERT, stack -> stack.is(Tags.Items.ORES)))
                            .build(),
                    handler
            )
    );

    private final LazyOptional<IItemHandler> LAZY_HANDLER_WEST = LazyOptional.of(
            () -> new SlotItemStackHandlerProxy(
                    SlotLogicBuilder.create()
                            .slot(0, new SlotLogic(HandlerMode.INSERT, stack -> stack.is(Tags.Items.ORES)))
                            .build(),
                    handler
            )
    );

    private final LazyOptional<IItemHandler> LAZY_HANDLER_DOWN = LazyOptional.of(
            () -> new SlotItemStackHandlerProxy(
                    SlotLogicBuilder.create()
                            .slot(0, new SlotLogic(HandlerMode.EXTRACT, stack -> stack.is(Tags.Items.ORES)))
                            .build(),
                    handler
            )
    );

    private Item filter = null;


    public ModBE(BlockPos p_155229_, BlockState p_155230_) {
        super(HandlerProject.EXAMPLE_BE.get(), p_155229_, p_155230_);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.UP)
                return LAZY_HANDLER_UP.cast();
            if (side == Direction.DOWN)
                return LAZY_HANDLER_DOWN.cast();
            if (side == Direction.WEST)
                return LAZY_HANDLER_WEST.cast();
        }
        return LazyOptional.empty();
    }

    public void setFilter(Item item) {
        this.filter = item;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (filter != null) {
            CompoundTag filterTag = filter.getDefaultInstance().save(new CompoundTag());
            tag.put("filter", filterTag);
        }
        CompoundTag inventory = handler.serializeNBT();
        tag.put("inventory", inventory);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("filter")) {
            ItemStack stack = ItemStack.of(tag.getCompound("filter"));
            filter = stack.getItem();
        }
        if (tag.contains("inventory"))
            handler.deserializeNBT(tag.getCompound("inventory"));
    }

    public void tick() {

    }
}
