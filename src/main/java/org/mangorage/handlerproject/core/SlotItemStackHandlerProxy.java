package org.mangorage.handlerproject.core;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class SlotItemStackHandlerProxy implements IItemHandler {
    private final ISlotLogicHandler handler;
    private final IItemHandler itemHandler;

    public SlotItemStackHandlerProxy(ISlotLogicHandler handler, IItemHandler itemHandler) {
        this.handler = handler;
        this.itemHandler = itemHandler;
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        var logic = handler.getSlotLogic(slot);
        return logic != null && logic.canInsert() && logic.isValid(stack) ? itemHandler.insertItem(slot, stack, simulate) : stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        var logic = handler.getSlotLogic(slot);
        return logic != null && logic.canExtract() ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return handler.hasSlotLogic(slot) && handler.getSlotLogic(slot).isValid(stack);
    }
}
