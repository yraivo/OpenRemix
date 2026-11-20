package remix.module.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.RayTraceResult;
import remix.module.movement.HighJumpMode;
import remix.util.world.LiquidStepHelper;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Arms a mode-specific jump and, in HYPIXEL mode, advances a synthetic vertical position/motion sequence while suppressing normal movement. */
public final class HighJumpModule extends ModuleBase {
    private boolean packetJumpActive;
    private double simulatedVerticalMotion;

    private double simulatedY;

    private Setting<HighJumpMode> modeSetting = new Setting<>("Mode", this, HighJumpMode.HYPIXEL);
    private Setting jumpHeightSetting = new Setting("JumpHeight", this, 3.7, 0.0, 9.0, 0.1);
    private boolean jumpArmed;
    private int packetJumpTicks;

    @Override
    public void onEnable() {
        LocalPlayer player = minecraft.player;

        if (player != null) {
            player.speedInAir = 0.02F;
            if (this.modeSetting.getValue() == HighJumpMode.CUBECRAFT) {
                float f = 0.0F;
                float f1 = MovementPhysicsHelper.getMaxFallDistanceWithJump();

                while (f < f1) {
                    this.sendPacketWithoutEvent(
                            new PlayerPositionPacket(player.posX, player.posY, player.posZ, false));
                    f += 0.06F;
                }

                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(player.posX, player.posY, player.posZ, true));
                this.jumpArmed = true;
            }
        }

        this.packetJumpActive = false;
        super.onEnable();
    }

    @Override
    public String getSuffix() {
        return this.modeSetting.getValue().getDisplayName();
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        HighJumpMode mode = this.modeSetting.getValue();
        if (mode == HighJumpMode.MCC) {
            if (minecraft.player.posY <= 0.0) {
                minecraft.player.motionY = 9.0;
            }
        } else if (mode == HighJumpMode.CUBECRAFT) {
            if (this.jumpArmed) {
                minecraft.player.motionY = this.jumpHeightSetting.asDouble();
                this.jumpArmed = false;
            }

            minecraft.timer.timerSpeed = 0.8F;
        }
    }

    public boolean isDownwardSweepClear(double minimumOffset, BoundingBox bounds) {
        ClientWorld world = minecraft.world;
        LocalPlayer player = minecraft.player;
        double offsetY = -0.125;

        while (offsetY > minimumOffset) {
            if (!world.getCollidingBoundingBoxes(player, bounds.expand(0.0, offsetY, 0.0))
                    .isEmpty()) {
                return false;
            }

            offsetY -= 0.125;
        }

        return true;
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE
                && this.modeSetting.getValue() == HighJumpMode.HYPIXEL) {
            LocalPlayer player = minecraft.player;
            if (player.onGround) {
                this.jumpArmed = true;
            }

            RayTraceResult rayTraceResult =
                    LiquidStepHelper.method_0c0ae08b75c219c3546e(
                            40.0, new float[] {player.rotationYaw, 90.0F});
            BlockPos blockPos = rayTraceResult.getBlockPos();
            boolean inAir =
                    blockPos != null
                            && minecraft.world.getBlockState(blockPos).getBlock()
                                    instanceof BlockAir;
            if (player.fallDistance >= 3.0F && inAir && !this.packetJumpActive && this.jumpArmed) {
                this.simulatedY = event.getY();
                this.simulatedVerticalMotion = -9.0;
                event.setY(this.simulatedY + this.simulatedVerticalMotion);
                this.simulatedY += this.simulatedVerticalMotion;
                this.jumpArmed = false;
                this.packetJumpActive = true;
                this.packetJumpTicks = 0;

                return;
            }

            if (this.packetJumpActive) {
                this.simulatedVerticalMotion -= 0.08;
                this.simulatedVerticalMotion *= 0.98F;
                if (this.packetJumpTicks < 3) {
                    this.simulatedY += this.simulatedVerticalMotion;
                    event.setY(this.simulatedY);
                } else if (this.packetJumpTicks == 3) {
                    this.simulatedVerticalMotion = this.jumpHeightSetting.asDouble();
                    this.simulatedY += this.simulatedVerticalMotion;
                    event.setY(this.simulatedY);
                } else if (this.simulatedY < player.posY) {
                    this.simulatedY += this.simulatedVerticalMotion;
                    event.setY(this.simulatedY);
                } else {
                    this.simulatedY += this.simulatedVerticalMotion;
                    event.setY(this.simulatedY);
                    player.setPosition(player.posX, this.simulatedY, player.posZ);
                    this.simulatedVerticalMotion -= 0.08;
                    this.simulatedVerticalMotion *= 0.98F;
                    player.motionY = this.simulatedVerticalMotion;
                    this.packetJumpActive = false;
                    this.packetJumpTicks = 0;
                }

                this.packetJumpTicks++;
            }
        }
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE
                && this.modeSetting.getValue() == HighJumpMode.HYPIXEL
                && this.packetJumpActive) {
            event.setX(0.0);
            event.setY(0.0);
            event.setZ(0.0);
        }
    }

    @Override
    public void onDisable() {
        minecraft.timer.timerSpeed = 1.0F;
        super.onDisable();
    }

    public HighJumpModule() {
        super("HighJump", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
        this.setName("High Jump");
    }
}
