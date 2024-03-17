package org.mangorage.handlerproject.core;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public record SlotLogicHandler(Int2ObjectMap<ISlotLogic> map) implements ISlotLogicHandler{

    @Override
    public ISlotLogic getSlotLogic(int slot) {
        return map.get(slot);
    }

    @Override
    public boolean hasSlotLogic(int slot) {
        return map.containsKey(slot);
    }
}
