package com.glodblock.github.extendedae.util;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

import appeng.client.gui.widgets.NumberEntryWidget;

import com.glodblock.github.glodium.reflect.ReflectKit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Ae2ReflectClient {

    private static final Field fNumberEntryWidget_buttons;
    private static final Method mAbstractContainerScreen_findSlot;

    static {
        try {
            fNumberEntryWidget_buttons = ReflectKit.reflectField(NumberEntryWidget.class, "buttons");
            mAbstractContainerScreen_findSlot = ReflectKit.reflectMethod(AbstractContainerScreen.class, new String[] { "findSlot", "m_97744_" }, double.class, double.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize AE2 reflection hacks!", e);
        }
    }

    public static List<Button> getButton(NumberEntryWidget owner) {
        return ReflectKit.readField(owner, fNumberEntryWidget_buttons);
    }

    public static Slot getSlot(AbstractContainerScreen<?> owner, double x, double y) {
        return ReflectKit.executeMethod2(owner, mAbstractContainerScreen_findSlot, x, y);
    }
}
