package org.mangorage.handlerproject.core;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public final class SlotLogicBuilder {
    public static SlotLogicBuilder create() {
        return new SlotLogicBuilder();
    }

    private final Int2ObjectMap<ISlotLogic> SLOT_LOGIC = new Int2ObjectArrayMap<>();

    private SlotLogicBuilder() {}

    public SlotLogicBuilder slot(int slot, ISlotLogic logic) {
        this.SLOT_LOGIC.put(slot, logic);
        return this;
    }

    public SlotLogicHandler build() {
        return new SlotLogicHandler(SLOT_LOGIC);
    }
}
