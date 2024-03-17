package org.mangorage.handlerproject.core;

import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public record LimitedSlotLogic(HandlerMode mode, int limit, Predicate<ItemStack> isValidPredicate) implements ISlotLogic {
    @Override
    public boolean canExtract() {
        return mode == HandlerMode.EXTRACT || mode == HandlerMode.BOTH;
    }

    @Override
    public boolean canInsert() {
        return mode == HandlerMode.INSERT || mode == HandlerMode.BOTH;
    }

    @Override
    public boolean isValid(ItemStack stack) {
        return isValidPredicate.test(stack);
    }

    @Override
    public int getLimit() {
        return limit;
    }
}