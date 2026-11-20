package remix.module.render;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import remix.event.annotation.Subscribe;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.render.WorldBrightnessOverride;

import java.util.ArrayList;
import java.util.List;

/** Xray module: render events and 2 settings. */
public final class XrayModule extends ModuleBase {

    public Setting<Float> exposedSetting;

    private float currentOpacity;

    public final Setting<Float> opacitySetting =
            new Setting("Opacity", this, 30.0, 0.0, 100.0, 1.0);

    public static List<Block> visibleBlocks;

    private float previousOpacity;
    private final WorldBrightnessOverride brightnessOverride = new WorldBrightnessOverride();
    private boolean previousRenderChunksMany;
    private boolean chunksOverrideApplied;

    public void onEnable() {

        super.onEnable();

        this.currentOpacity = (float) this.opacitySetting.asDouble();

        this.previousOpacity = (float) this.opacitySetting.asDouble();

        if (!this.chunksOverrideApplied) {
            this.previousRenderChunksMany = minecraft.renderChunksMany;
            this.chunksOverrideApplied = true;
        }
        minecraft.renderChunksMany = false;
        this.reloadRenderers();
    }

    public XrayModule() {

        super("Xray", new ModuleDefaults(ModuleCategory.RENDER).visible(false));

        this.exposedSetting = new Setting("Exposed", this, true);

        visibleBlocks.add(Blocks.gold_ore);

        visibleBlocks.add(Blocks.iron_ore);

        visibleBlocks.add(Blocks.coal_ore);

        visibleBlocks.add(Blocks.lapis_ore);

        visibleBlocks.add(Blocks.diamond_ore);

        visibleBlocks.add(Blocks.redstone_ore);

        visibleBlocks.add(Blocks.lit_redstone_ore);

        visibleBlocks.add(Blocks.tnt);

        visibleBlocks.add(Blocks.emerald_ore);

        visibleBlocks.add(Blocks.furnace);

        visibleBlocks.add(Blocks.lit_furnace);

        visibleBlocks.add(Blocks.diamond_block);

        visibleBlocks.add(Blocks.iron_block);

        visibleBlocks.add(Blocks.gold_block);

        visibleBlocks.add(Blocks.quartz_ore);

        visibleBlocks.add(Blocks.beacon);

        visibleBlocks.add(Blocks.mob_spawner);
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (minecraft.player == null || minecraft.world == null) {
            this.clearWorldState();
            return;
        }
        this.brightnessOverride.apply(minecraft.world.provider);
        this.currentOpacity = (float) this.opacitySetting.asDouble();
        if (this.previousOpacity != this.currentOpacity) {
            this.reloadRenderers();
            this.previousOpacity = this.currentOpacity;
        }
    }

    public void onDisable() {

        super.onDisable();

        if (this.chunksOverrideApplied) {
            minecraft.renderChunksMany = this.previousRenderChunksMany;
            this.chunksOverrideApplied = false;
        }

        this.currentOpacity = -1.0F;

        this.previousOpacity = -1.0F;

        this.clearWorldState();
        this.reloadRenderers();
    }

    public void clearWorldState() {
        this.brightnessOverride.release();
    }

    private void reloadRenderers() {
        if (minecraft.world != null && minecraft.renderGlobal != null) {
            minecraft.renderGlobal.loadRenderers();
        }
    }


    // region Static initialization and string tables

    static {
        visibleBlocks = new ArrayList<>();
    }

    // endregion

    public boolean isVisibleBlock(Block block) {

        return visibleBlocks.contains(block);
    }
}
