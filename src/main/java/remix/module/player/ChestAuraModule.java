package remix.module.player;

import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import remix.util.time.ElapsedTimer;
import net.minecraft.client.gui.GuiScreen;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.combat.AuraModule;
import remix.module.world.ScaffoldModule;
import remix.setting.Setting;

import com.google.common.collect.Lists;

import org.lwjgl.input.Keyboard;

import java.util.List;

/** ChestAura module: player events and 5 settings. */
public final class ChestAuraModule extends ModuleBase {
    private Setting delaySetting;
    public TileEntityChest lastOpenedChest;
    public ElapsedTimer openTimer;
    private Setting clickToOpenSetting;

    public final List<TileEntityChest> openedChests = Lists.newArrayList();

    public TileEntityChest targetChest;
    private Setting rangeSetting;
    public Setting throughWallsSetting;

    public Setting hideChestSetting;
    private GuiScreen unusedScreen;

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            AuraModule aura = ClientServices.moduleManager.aura;
            ScaffoldModule scaffold = ClientServices.moduleManager.scaffold;
            if (!this.openTimer.hasElapsed((long) this.delaySetting.asDouble())
                    || (this.clickToOpenSetting.asBoolean()
                            && !minecraft.gameSettings.keyBindUseItem.pressed)
                    || minecraft.currentScreen != null
                    || (aura.isEnabled() && aura.currentTarget != null)
                    || scaffold.isEnabled()) {
                return;
            }

            List<TileEntityChest> unopenedChests = this.collectUnopenedChests(this.rangeSetting.asDouble());
            if (!unopenedChests.isEmpty()) {
                unopenedChests.sort((chest1, chest2) -> {
                    BlockPos pos1 = chest1.getPos();
                    BlockPos pos2 = chest2.getPos();
                    double dist1 =
                            minecraft.player.getDistanceSq(
                                    pos1.getX(), pos1.getY(), pos1.getZ());
                    double dist2 =
                            minecraft.player.getDistanceSq(
                                    pos2.getX(), pos2.getY(), pos2.getZ());
                    return Double.compare(dist1, dist2);
                });
                this.targetChest = unopenedChests.get(0);
                BlockPos targetPos = this.targetChest.getPos();
                float[] rotations =
                        RotationHelper.rotationsTo(
                                new Vec3(targetPos).addVector(0.5, 0.5, 0.5),
                                new float[] {
                                    minecraft.player.rotationYaw, minecraft.player.rotationPitch
                                },
                                0.0F);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            } else {
                this.targetChest = null;
            }
        } else if (event.getPhase() == MotionPhase.POST) {
            if (this.targetChest != null) {
                BlockPos targetPos = this.targetChest.getPos();
                float[] rotations =
                        RotationHelper.rotationsTo(
                                new Vec3(targetPos).addVector(0.5, 0.5, 0.5),
                                new float[] {
                                    minecraft.player.rotationYaw, minecraft.player.rotationPitch
                                },
                                0.0F);
                RayTraceResult rayTraceResult =
                        RaycastHelper.rayTraceBlocks(
                                this.rangeSetting.asDouble(),
                                minecraft.timer.renderPartialTicks,
                                rotations);
                if (rayTraceResult != null
                        && minecraft
                                        .world
                                        .getBlockState(rayTraceResult.getBlockPos())
                                        .getBlock()
                                instanceof BlockChest
                        && minecraft.playerController.onPlayerRightClick(
                                minecraft.player,
                                minecraft.world,
                                minecraft.player.getCurrentEquippedItem(),
                                rayTraceResult.getBlockPos(),
                                rayTraceResult.sideHit,
                                rayTraceResult.hitVec)) {
                    minecraft.player.swingItem();
                    this.lastOpenedChest = this.targetChest;
                    this.targetChest = null;
                    this.openTimer.reset();
                }
            }
        }
    }

    public ChestAuraModule() {
        super("ChestAura", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.openTimer = new ElapsedTimer();
        this.delaySetting = new Setting("Delay", this, 150.0, 0.0, 1000.0, 50.0);
        this.rangeSetting = new Setting("Range", this, 4.0, 1.0, 6.0, 0.1);
        this.hideChestSetting = new Setting("HideChest", this, true);
        this.throughWallsSetting = new Setting("ThroughWalls", this, true);
        this.clickToOpenSetting = new Setting("Click to open", this, false);
        this.setName("Chest Aura");
    }

    private List<TileEntityChest> collectUnopenedChests(double range) {
        List<TileEntityChest> list = Lists.newArrayList();
        for (Object obj : minecraft.world.loadedTileEntityList) {
            if (obj instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) obj;
                BlockPos pos = chest.getPos();
                if (!this.openedChests.contains(chest)
                        && minecraft.player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ())
                                <= range * range) {
                    list.add(chest);
                }
            }
        }
        return list;
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.currentScreen instanceof GuiChest
                && this.targetChest != null) {
            KeyBinding[] keyBindingArray =
                    new KeyBinding[] {
                        minecraft.gameSettings.keyBindForward,
                        minecraft.gameSettings.keyBindBack,
                        minecraft.gameSettings.keyBindLeft,
                        minecraft.gameSettings.keyBindRight,
                        minecraft.gameSettings.keyBindJump
                    };

            for (KeyBinding keyBinding : keyBindingArray) {
                KeyBinding.setKeyBindState(
                        keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
            }
        }
    }

    @Override
    public void onDisable() {
        this.targetChest = null;
        super.onDisable();
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED
                && event.getPacket() instanceof S01PacketJoinGame
                && !this.openedChests.isEmpty()) {
            this.openedChests.clear();
        }
    }
}
