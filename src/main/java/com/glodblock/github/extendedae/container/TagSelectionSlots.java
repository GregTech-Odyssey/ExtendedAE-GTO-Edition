package com.glodblock.github.extendedae.container;

import appeng.api.stacks.AEKey;
import appeng.api.stacks.GenericStack;
import appeng.menu.slot.FakeSlot;
import appeng.util.ConfigInventory;

import java.util.function.Consumer;

public final class TagSelectionSlots {

    private final FakeSlot whitelist;
    private final FakeSlot blacklist;
    private Consumer<AEKey> whitelistChanged;
    private Consumer<AEKey> blacklistChanged;

    public TagSelectionSlots(Consumer<FakeSlot> addSlot) {
        whitelist = new FakeSlot(ConfigInventory.configTypes(1, this::whitelistChanged).createMenuWrapper(), 0);
        blacklist = new FakeSlot(ConfigInventory.configTypes(1, this::blacklistChanged).createMenuWrapper(), 0);
        addSlot.accept(whitelist);
        addSlot.accept(blacklist);
    }

    public FakeSlot whitelist() {
        return whitelist;
    }

    public FakeSlot blacklist() {
        return blacklist;
    }

    public void onWhitelistChanged(Consumer<AEKey> consumer) {
        whitelistChanged = consumer;
    }

    public void onBlacklistChanged(Consumer<AEKey> consumer) {
        blacklistChanged = consumer;
    }

    private void whitelistChanged() {
        notifyChanged(whitelist, whitelistChanged);
    }

    private void blacklistChanged() {
        notifyChanged(blacklist, blacklistChanged);
    }

    private static void notifyChanged(FakeSlot slot, Consumer<AEKey> consumer) {
        if (consumer != null) {
            var stack = GenericStack.fromItemStack(slot.getItem());
            consumer.accept(stack == null ? null : stack.what());
        }
    }
}
