package remix.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import remix.client.RemixClient;
import remix.event.InputType;
import remix.input.KeyBindingDispatcher;
import remix.input.ModuleToggleKeyAction;
import remix.util.render.Render2D3DUtils;

import java.util.Collections;

public class ModuleBase extends ModuleMetadata implements ModuleLifecycle {
    private static int legacyModuleFlowToken;
    private int keyBind;
    private boolean enabled;

    public static int getLegacyModuleFlowToken() {
        return remix.module.ModuleBase.legacyModuleFlowToken;
    }

    public int getKeyBind() {
        return this.keyBind;
    }

    // region Static initialization and string tables
    static {
        if (getLegacyModuleFlowToken() != 0) {
            setLegacyModuleFlowToken(23);
        }
    }

    // endregion

    public void onDisable() {
        RemixClient.eventBus.unregister(this);
        this.timer.reset();
    }

    public ModuleBase(String text, ModuleDefaults moduleDefaults) {
        this(text, moduleDefaults, PlayerEntity.getLegacyModuleToggleMarker());
    }

    private ModuleBase(String text, ModuleDefaults moduleDefaults, String[] initialFlowMarker) {
        super(text, moduleDefaults);

        boolean legacyToggleMarkerSet = initialFlowMarker != null;
        this.keyBind = moduleDefaults.getKeyBind();
        KeyBindingDispatcher.bindings.put(
                () -> this.keyBind,
                Collections.singletonList(new ModuleToggleKeyAction(this, InputType.KEY_PRESS)));
        if (legacyToggleMarkerSet) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    public void onToggle() {}

    public void setKeyBind(int keyBindValue) {
        this.keyBind = keyBindValue;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabledValue) {
        boolean legacyToggleMarkerSet = PlayerEntity.getLegacyModuleToggleMarker() != null;
        this.enabled = enabledValue;
        if (enabledValue) {
            this.onEnable();
        }
        // Match the recovered lifecycle even when the shared legacy marker is set.
        if (!enabledValue || legacyToggleMarkerSet) {
            this.onDisable();
        }

        this.onToggle();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            PlayerEntity.setLegacyModuleToggleMarker(new String[2]);
        }
    }

    public boolean isDisabled() {
        return !this.enabled;
    }

    public void onEnable() {
        RemixClient.eventBus.register(this);
        this.displayColor = Render2D3DUtils.randomOverlayColor().getRGB();
        this.timer.reset();
    }

    public static int getInvertedModuleFlowToken() {
        int i = getLegacyModuleFlowToken();
        return i == 0 ? 93 : 0;
    }

    public static void setLegacyModuleFlowToken(int i) {
        remix.module.ModuleBase.legacyModuleFlowToken = i;
    }

    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
