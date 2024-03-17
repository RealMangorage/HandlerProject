package org.mangorage.handlerproject.core;

public interface ISlotLogicHandler {
    ISlotLogic getSlotLogic(int slot);
    boolean hasSlotLogic(int slot);
}
