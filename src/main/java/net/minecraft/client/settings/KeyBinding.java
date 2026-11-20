package net.minecraft.client.settings;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;
import remix.input.KeyAction;
import remix.input.KeyBindingDispatcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class KeyBinding implements Comparable {
    private static final IntHashMap hash = new IntHashMap();
    private static final Set keybindSet = Sets.newHashSet();
    private int keyCode;
    private int pressTime;
    public boolean pressed;
    private static final List keybindArray = Lists.newArrayList();
    private String keyCategory;
    private int keyCodeDefault;
    private String keyDescription;

    public String getKeyDescription() {
        return this.keyDescription;
    }

    private void unpressKey() {
        this.pressTime = 0;
        this.pressed = false;
    }

    public static Set getKeybinds() {
        return net.minecraft.client.settings.KeyBinding.keybindSet;
    }

    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }

    public String getKeyCategory() {
        return this.keyCategory;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public static void resetKeyBindingArrayAndHash() {
        GameSettings.BK();

        net.minecraft.client.settings.KeyBinding.hash.clearMap();
        Iterator iterator = net.minecraft.client.settings.KeyBinding.keybindArray.iterator();

        while (iterator.hasNext()) {
            KeyBinding keyBinding = (KeyBinding) iterator.next();
            net.minecraft.client.settings.KeyBinding.hash.addKey(keyBinding.keyCode, keyBinding);
        }
    }

    public static void setKeyBindState(int i, boolean pressedValue) {
        if (i != 0) {
            KeyBinding keyBinding = (KeyBinding) net.minecraft.client.settings.KeyBinding.hash.lookup(i);
            if (keyBinding != null) {
                keyBinding.pressed = pressedValue;
            }
        }
    }

    public static void unPressAllKeys() {
        GameSettings.BK();

        Iterator iterator = net.minecraft.client.settings.KeyBinding.keybindArray.iterator();

        while (iterator.hasNext()) {
            KeyBinding keyBinding = (KeyBinding) iterator.next();
            keyBinding.unpressKey();
        }
    }

    public boolean isPressed() {
        if (this.pressTime == 0) {
            return false;
        }

        this.pressTime--;
        return true;
    }

    public boolean isKeyDown() {
        return !KeyBindingDispatcher.noKeyActionDispatched && !KeyAction.inputTypeReadBeforeEligibilityCheck ? KeyAction.inputTypeReadBeforeEligibilityCheck : this.pressed;
    }

    public KeyBinding(String text, int keyCodeDefaultValue, String textValue) {
        this.keyDescription = text;
        this.keyCode = keyCodeDefaultValue;
        this.keyCodeDefault = keyCodeDefaultValue;
        this.keyCategory = textValue;
        net.minecraft.client.settings.KeyBinding.keybindArray.add(this);
        net.minecraft.client.settings.KeyBinding.hash.addKey(keyCodeDefaultValue, this);
        net.minecraft.client.settings.KeyBinding.keybindSet.add(textValue);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((KeyBinding) object);
    }

    public static void onKeyPress(int i) {
        if (i != 0) {
            KeyBinding keyBinding = (KeyBinding) net.minecraft.client.settings.KeyBinding.hash.lookup(i);
            if (keyBinding != null) {
                keyBinding.pressTime++;
            }
        }
    }

    public void setKeyCode(int keyCodeValue) {
        this.keyCode = keyCodeValue;
    }

    public int compareTo(KeyBinding keyBinding) {
        int i =
                I18n.format(this.keyCategory, new Object[0])
                        .compareTo(I18n.format(keyBinding.keyCategory, new Object[0]));
        if (i == 0) {
            i =
                    I18n.format(this.keyDescription, new Object[0])
                            .compareTo(I18n.format(keyBinding.keyDescription, new Object[0]));
        }

        return i;
    }
}
