package org.mangorage.handlerproject.core;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SlotItemStackHandler extends ItemStackHandler {
    private final ISlotLogicHandler handler;

    public SlotItemStackHandler(ISlotLogicHandler handler) {
        this.handler = handler;
    }

    public SlotItemStackHandler(ISlotLogicHandler handler, int size)
    {
        super(size);
        this.handler = handler;
    }

    public SlotItemStackHandler(ISlotLogicHandler handler, NonNullList<ItemStack> stacks)
    {
        super(stacks);
        this.handler = handler;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return handler.hasSlotLogic(slot) ? handler.getSlotLogic(slot).isValid(stack) : super.isItemValid(slot, stack);
    }

    @Override
    public int getSlotLimit(int slot) {
        return handler.hasSlotLogic(slot) ? handler.getSlotLogic(slot).getLimit() : super.getSlotLimit(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return handler.hasSlotLogic(slot) ? handler.getSlotLogic(slot).canInsert() ? super.insertItem(slot, stack, simulate) : stack : stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return handler.hasSlotLogic(slot) ? handler.getSlotLogic(slot).canExtract() ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY : ItemStack.EMPTY;
    }
}
