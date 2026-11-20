package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.GameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import remix.module.movement.LongJumpMode;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.movement.MovementUtils;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.command.ClientCommandManager;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.LinkedList;

/** LongJump module: move/motion/packet/update events and 4 settings. */
public final class LongJumpModule extends ModuleBase {
    private double previousBoostSpeed;
    private Setting incrementSpeedSetting;
    private Setting boostSetting;
    private boolean jumpStarted;
    private int serverHotbarSlot;
    private boolean wasOnGround;
    private double lastHorizontalDistance;
    private double moveSpeed;
    private Setting autoDisableSetting;
    private boolean boostArmed;
    private final Setting<LongJumpMode> modeSetting =
            new Setting("Mode", this, LongJumpMode.NCP);

    public void onEnable() {
        LocalPlayer player = minecraft.player;
        if (player != null) {
            LongJumpMode mode = (LongJumpMode) this.modeSetting.getValue();
            if (mode == LongJumpMode.NCP) {
                float f = 0.0F;
                float f1 = MovementPhysicsHelper.getMaxFallDistanceWithJump();

                while (f < f1) {
                    this.sendPacketWithoutEvent(
                            new PlayerPositionPacket(
                                    player.posX,
                                    player.posY + 0.0625 - 1.0E-5,
                                    player.posZ,
                                    false));
                    this.sendPacketWithoutEvent(
                            new PlayerPositionPacket(
                                    player.posX, player.posY, player.posZ, false));
                    f = (float) (f + 0.06249);
                }

                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(player.posX, player.posY, player.posZ, true));
            } else if (mode == LongJumpMode.MINEPLEX) {
                Container container = player.inventoryContainer;
                for (int slot = 8; slot > 0; slot--) {
                    if (container.getSlot(36 + slot).getStack() == null) {
                        this.serverHotbarSlot = slot;
                        this.sendPacketWithoutEvent(new HeldItemChangePacket(slot));
                        break;
                    }
                }
            }
        }

        this.lastHorizontalDistance = this.moveSpeed = this.previousBoostSpeed = MovementUtils.getSpeedWithPotion();
        this.wasOnGround = this.jumpStarted = this.boostArmed = false;
        super.onEnable();
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        LocalPlayer player = minecraft.player;
        double d0 = player.posX - player.prevPosX;
        double d1 = player.posZ - player.prevPosZ;
        this.lastHorizontalDistance = MathHelper.sqrt(d0 * d0 + d1 * d1);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED
                && this.modeSetting.getValue() == LongJumpMode.MINEPLEX
                && event.getPacket() instanceof S08PacketPlayerPosLook) {
            this.toggle();
        }
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            LocalPlayer player = minecraft.player;
            LongJumpMode mode = (LongJumpMode) this.modeSetting.getValue();

            if (mode == LongJumpMode.NCP || mode == LongJumpMode.CUBECRAFT) {
                if (player.hurtResistantTime == 19 || mode == LongJumpMode.NCP) {
                    this.boostArmed = true;
                }

                if (this.boostArmed) {
                    if (!MovementUtils.isMoving()) {
                        this.moveSpeed = MovementUtils.getSpeedWithPotion();
                        this.wasOnGround = false;
                        this.lastHorizontalDistance = 0.0;
                        MovementUtils.setHorizontalMotion(event, 0.0);
                        return;
                    }

                    if (player.onGround) {
                        if (this.jumpStarted && this.autoDisableSetting.asBoolean()) {
                            this.toggle();
                            return;
                        }

                        if (this.wasOnGround) {
                            event.setY(player.motionY = MovementUtils.getJumpMotion());
                            this.moveSpeed = this.lastHorizontalDistance * 2.145F;
                            this.jumpStarted = true;
                        } else {
                            this.moveSpeed = MovementUtils.getBaseMoveSpeed() * this.boostSetting.asDouble();
                        }
                    } else {
                        if (this.wasOnGround && mode != LongJumpMode.CUBECRAFT) {
                            this.moveSpeed = this.lastHorizontalDistance
                                    - 0.66 * (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion())
                                    - 1.0E-5;
                        } else {
                            LinkedList<Double> list = new LinkedList<>();
                            list.add(this.lastHorizontalDistance - this.lastHorizontalDistance / 160.0);
                            list.add(this.lastHorizontalDistance - (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion()) / 33.3);
                            list.add(this.lastHorizontalDistance - (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion()) * 0.02);
                            list.sort(Double::compare);
                            this.moveSpeed = list.get(0) - 1.0E-5;
                        }
                    }

                    this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                    this.wasOnGround = player.onGround;
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    return;
                }

                MovementUtils.setHorizontalMotion(event, 0.0);
                return;
            } else if (mode == LongJumpMode.MINEPLEX) {
                if (!this.jumpStarted) {
                    if (!player.onGround || !MovementUtils.isMoving()) {
                        return;
                    }

                    BlockPos blockPos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
                    MovementUtils.setHorizontalMotion(
                            event,
                            player.ticksExisted % 2 == 0 ? -this.moveSpeed : this.moveSpeed);
                    this.moveSpeed = this.lastHorizontalDistance + this.incrementSpeedSetting.asDouble();
                    Container container = player.inventoryContainer;

                    if (container.getSlot(36 + this.serverHotbarSlot).getStack() == null) {
                        for (int j = 8; j > 0; j--) {
                            if (container.getSlot(36 + j) == null) {
                                this.serverHotbarSlot = j;
                                this.sendPacketWithoutEvent(new HeldItemChangePacket(j));
                                break;
                            }
                        }
                    }

                    if (container.getSlot(36 + this.serverHotbarSlot).getStack() != null) {
                        ClientCommandManager.printMessage("Please clear one of your hotbar slots!");
                        this.toggle();
                    }

                    minecraft.playerController.onPlayerRightClick(
                            player,
                            minecraft.world,
                            null,
                            blockPos,
                            Facing.UP,
                            new Vec3(Math.random(), 1.0, Math.random()));

                    if (this.moveSpeed >= this.boostSetting.asDouble()) {
                        event.setY(player.motionY = MovementUtils.getJumpMotion());
                        MovementUtils.setHorizontalMotion(event, 0.0);
                        minecraft.timer.timerSpeed = 2.5F;
                        this.jumpStarted = true;
                    }

                    return;
                }

                minecraft.timer.timerSpeed = 1.0F;
                if (!this.boostArmed) {
                    this.boostArmed = true;
                    this.lastHorizontalDistance = this.moveSpeed = this.boostSetting.asDouble();
                }

                if (this.serverHotbarSlot != player.inventory.currentItem) {
                    this.serverHotbarSlot = player.inventory.currentItem;
                    this.sendPacketWithoutEvent(new HeldItemChangePacket(this.serverHotbarSlot));
                }

                this.moveSpeed *= 0.982;
                if (player.fallDistance <= 0.0F) {
                    event.setY(player.motionY += 0.038);
                } else if (player.fallDistance < 1.2) {
                    event.setY(player.motionY += 0.032);
                }

                this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                if (!MovementUtils.isMoving()) {
                    this.moveSpeed = 0.0;
                }

                MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                return;
            } else if (mode == LongJumpMode.MINEPLEX2) {
                GameTimer timerValue = minecraft.timer;
                timerValue.timerSpeed = 1.0F;
                if (MovementUtils.isMoving()) {
                    if (player.onGround) {
                        event.setY(player.motionY = 0.4);
                        timerValue.timerSpeed = 3.0F;
                        this.moveSpeed = 0.0;
                    } else if (this.wasOnGround) {
                        this.moveSpeed = Math.max(
                                this.previousBoostSpeed + this.incrementSpeedSetting.asDouble(),
                                MovementUtils.getSpeedWithPotion());
                    } else {
                        this.moveSpeed = this.lastHorizontalDistance - this.lastHorizontalDistance / 64.0;
                        this.previousBoostSpeed = this.moveSpeed;
                    }

                    if (this.moveSpeed >= this.boostSetting.asDouble() && !this.jumpStarted) {
                        event.setY(player.motionY = 0.425);
                        this.jumpStarted = true;
                    }

                    MovementUtils.setHorizontalMotion(
                            event,
                            minecraft.player.ticksExisted % 2 != 0 && !this.jumpStarted
                                    ? -this.moveSpeed
                                    : this.moveSpeed);
                }

                this.wasOnGround = player.onGround;
            }
        }
    }

    public void onDisable() {
        minecraft.timer.timerSpeed = 1.0F;
        LongJumpMode mode = (LongJumpMode) this.modeSetting.getValue();

        if (mode == LongJumpMode.MINEPLEX) {
            if (this.serverHotbarSlot != minecraft.player.inventory.currentItem) {
                this.sendPacketWithoutEvent(
                        new HeldItemChangePacket(minecraft.player.inventory.currentItem));
            }
        } else if (mode == LongJumpMode.NCP || mode == LongJumpMode.CUBECRAFT) {
            MovementUtils.setSpeed(0.0);
        }

        minecraft.timer.timerSpeed = 1.0F;
        super.onDisable();
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.POST && this.modeSetting.getValue() == LongJumpMode.NCP2) {
            if (minecraft.player.onGround
                    && this.autoDisableSetting.asBoolean()
                    && this.jumpStarted) {
                MovementUtils.setSpeed(0.0);
                this.toggle();
                return;
            }

            if (MovementUtils.isMoving()) {
                if (!minecraft.player.onGround) {
                    if (this.boostArmed) {
                        this.boostArmed = false;
                        MovementUtils.setSpeed(
                                MovementUtils.getSpeedWithPotion() * this.boostSetting.asDouble());
                        return;
                    }

                    MovementUtils.setSpeed(
                            MathHelper.sqrt(
                                    minecraft.player.motionX * minecraft.player.motionX
                                            + minecraft.player.motionZ * minecraft.player.motionZ));
                    return;
                }

                this.boostArmed = true;
                minecraft.player.jump();
                this.jumpStarted = true;
                return;
            }

            MovementUtils.setSpeed(0.0);
        }
    }

    public LongJumpModule() {
        super("LongJump", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
        this.boostSetting = new Setting("Boost", this, 2.0, 1.0, 5.0, 0.05);
        this.incrementSpeedSetting = new Setting("Speed", this, 0.05, 0.01, 0.2, 0.01);
        this.autoDisableSetting = new Setting("AutoDisable", this, true);
    }
}
