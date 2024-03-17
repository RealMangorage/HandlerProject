package org.mangorage.handlerproject.core;

import net.minecraft.world.item.ItemStack;

public interface ISlotLogic {
    boolean canExtract();
    boolean canInsert();
    boolean isValid(ItemStack stack);
    default int getLimit() {
        return 64;
    }
}
