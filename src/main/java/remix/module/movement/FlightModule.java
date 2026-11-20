package remix.module.movement;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import optifine.MathUtils;
import remix.util.time.ElapsedTimer;
import remix.module.movement.FlightBlinkMode;
import remix.module.movement.FlightBoostMode;
import remix.module.movement.FlightDamageMode;
import remix.module.movement.FlightMode;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.movement.MovementUtils;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.StepEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Flight module: motion/move/packet/step events and 16 settings. */
public final class FlightModule extends ModuleBase {
    private Setting blinkDelaySetting;
    private Setting timerDurationSetting;
    private Setting damageModeSetting;
    private Setting compensateSetting;
    private Setting speedSetting;
    private boolean awaitingComboCorrection;
    private int motionTicks;
    private Setting baseBoostSetting;
    private Setting blinkSetting;
    private Setting waitSetting;
    private Setting timerBoostSetting;
    private Setting viewBobbingSetting;
    private Setting boostSetting;
    private double lastHorizontalDistance;
    private Setting<FlightBoostMode> boostModeSetting;
    private FlightMode comboMode;
    private Queue<Packet> bufferedPackets;
    private Setting antiKickSetting;
    private BlockState replacedBlockState;
    private Setting timerSetting;
    public Setting<FlightBlinkMode> blinkModeSetting;
    private boolean wasOnGround;
    private boolean receivedVelocity;
    private boolean damageSequenceSent;
    private BlockPos replacedBlockPosition;
    private ElapsedTimer blinkTimer;
    private double moveSpeed;
    private Vec3 anchorPosition;
    private int boostStage;
    private final Setting<FlightMode> modeSetting;
    private Setting damageSetting;
    private Setting stopMotionSetting;

    private double findGroundY() {
        LocalPlayer player = minecraft.player;
        double groundY = 0.0;
        double posY = player.posY;
        double d0 = 9.0;

        while (posY > 0.0) {
            BlockPos blockPos = new BlockPos(player.posX, posY, player.posZ);
            if (minecraft.world.getBlockState(blockPos).getBlock().getMaterial() != Material.AIR) {
                if (d0 == 0.05) {
                    return posY;
                }

                posY += d0;
                d0 = 0.05;
            }

            posY -= d0;
        }

        return 0.0;
    }

    public void onEnable() {
        LocalPlayer player = minecraft.player;
        if (player != null) {
            this.comboMode = player.onGround ? FlightMode.HYPIXEL : FlightMode.VANILLA;
            this.anchorPosition = minecraft.player.getPositionVector();
            this.lastHorizontalDistance = this.moveSpeed = MovementUtils.getSpeedWithPotion();
            this.boostStage = 0;
            this.awaitingComboCorrection = player.fallDistance < 2.0F
                    && ClientServices.moduleManager.noFall.accumulatedFallDistance < 2.0;
            this.receivedVelocity = this.damageSequenceSent = false;
        }

        this.blinkTimer.reset();
        this.timer.reset();
        super.onEnable();
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        LocalPlayer localPlayer = minecraft.player;
        FlightMode flightMode = this.modeSetting.getValue() == FlightMode.HYPIXELCOMBO
                ? this.comboMode
                : (FlightMode) this.modeSetting.getValue();

        switch (flightMode) {
            case MCCENTRAL:
                localPlayer.jump();
                break;

            case VANILLA:
                double speed = this.speedSetting.asDouble();
                MovementUtils.setSpeed(MovementUtils.isMoving() ? speed : 0.0);
                localPlayer.motionY = localPlayer.movementInput.jump
                        ? speed / 2.0
                        : (localPlayer.movementInput.sneak ? -speed / 2.0 : 0.0);

                if (this.antiKickSetting.asBoolean() && this.timer.elapsedAndReset(1000L)) {
                    double groundY = this.findGroundY();
                    double posY = localPlayer.posY;

                    while (posY > groundY) {
                        this.sendPacket(new PlayerPositionPacket(localPlayer.posX, posY, localPlayer.posZ, true));
                        if (posY - 9.0 < groundY) {
                            break;
                        }
                        posY -= 9.0;
                    }

                    this.sendPacket(new PlayerPositionPacket(localPlayer.posX, groundY, localPlayer.posZ, true));
                    posY = groundY;

                    while (posY < localPlayer.posY) {
                        this.sendPacket(new PlayerPositionPacket(localPlayer.posX, posY, localPlayer.posZ, true));
                        if (posY + 9.0 > localPlayer.posY) {
                            break;
                        }
                        posY += 9.0;
                    }

                    this.sendPacket(new PlayerPositionPacket(localPlayer.posX, localPlayer.posY, localPlayer.posZ, true));
                }
                break;

            case CUBECRAFT:
                if (MovementUtils.isMoving()) {
                    if (localPlayer.onGround) {
                        MovementUtils.setSpeed(0.0);
                        localPlayer.jump();
                    } else {
                        int ticksExisted = localPlayer.ticksExisted;
                        minecraft.timer.timerSpeed = ticksExisted % 2 == 0 ? 1.5F : 0.15F;
                        MovementUtils.setSpeed(ticksExisted % 2 == 0 ? 0.0 : 2.0);
                    }
                } else {
                    MovementUtils.setSpeed(0.0);
                }
                break;

            case CUBECRAFT2:
            case HYPIXEL:
                if (!localPlayer.onGround
                        || (!this.boostSetting.asBoolean() && this.modeSetting.getValue() == FlightMode.HYPIXEL)) {
                    float timerSpeed = this.timerBoostSetting.asBoolean()
                            && !this.timer.hasElapsed((long) this.timerDurationSetting.asDouble())
                            ? (float) this.timerSetting.asDouble()
                            : 1.0F;

                    GameSettings gameSettings = minecraft.gameSettings;
                    double motionY = gameSettings.keyBindJump.pressed
                            ? 1.25
                            : (gameSettings.keyBindSneak.pressed ? -1.25 : 0.0);

                    if (!this.boostSetting.asBoolean() && this.modeSetting.getValue() != FlightMode.CUBECRAFT2) {
                        localPlayer.onGround = true;
                    }

                    localPlayer.motionY = motionY;
                    minecraft.timer.timerSpeed = timerSpeed;
                }
                break;

            default:
                break;
        }

        double d4 = localPlayer.posX - localPlayer.prevPosX;
        double d5 = localPlayer.posZ - localPlayer.prevPosZ;
        this.lastHorizontalDistance = MathHelper.sqrt(d4 * d4 + d5 * d5);
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            FlightMode flightMode = this.modeSetting.getValue() == FlightMode.HYPIXELCOMBO
                    ? this.comboMode
                    : (FlightMode) this.modeSetting.getValue();

            if (flightMode == FlightMode.HYPIXEL) {
                List list = MovementPhysicsHelper.GROUND_OFFSETS;
                if (!this.waitSetting.asBoolean() || this.receivedVelocity) {
                    if (this.motionTicks % 4 == 2) {
                        event.setY(event.getY() + 0.0031F);
                    } else if (this.motionTicks % 4 == 3) {
                        event.setY(event.getY() + 0.0052F);
                    }
                    this.motionTicks++;
                }

                event.onGround = list.contains(event.getY() % 1.0);
            } else if (flightMode == FlightMode.MCCENTRAL) {
                event.onGround = true;
            }
        }
    }

    private void sendDamageSequence(double requiredFallDistance, FlightDamageMode damageMode) {
        LocalPlayer player = minecraft.player;
        double simulatedFallDistance = 0.0;
        double[] verticalOffsets;

        switch (damageMode) {
            case NCP:
                verticalOffsets = new double[] {0.06249, 0.0};
                break;
            case RANDOMIZED:
                verticalOffsets = new double[] {
                    MathUtils.getRandomFloat(0.051F, 0.0625F),
                    MathUtils.getRandomDouble(1.0E-5, 0.001)
                };
                break;
            case HYPIXEL:
                double d2 = MathUtils.fh() * 0.001;
                verticalOffsets = new double[] {0.06F + d2, 0.015625, 0.03F + d2};
                break;
            default:
                verticalOffsets = new double[] {requiredFallDistance, 0.0};
                break;
        }

        double previousOffset = 0.0;
        while (simulatedFallDistance < requiredFallDistance) {
            for (double offset : verticalOffsets) {
                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(player.posX, player.posY + offset, player.posZ, false));
                if (previousOffset > offset) {
                    simulatedFallDistance += previousOffset - offset;
                }
                previousOffset = offset;
            }
        }

        this.sendPacketWithoutEvent(
                new PlayerPositionPacket(player.posX, player.posY, player.posZ, true));
    }

    public FlightModule() {
        super("Flight", new ModuleDefaults(ModuleCategory.MOVEMENT).keyBind(34));
        this.modeSetting = new Setting("Mode", this, FlightMode.HYPIXEL);
        this.boostModeSetting = new Setting("BoostMode", this, FlightBoostMode.NCP1);
        this.damageModeSetting = new Setting("DamageMode", this, FlightDamageMode.NCP);
        this.speedSetting = new Setting("Speed", this, 2.0, 0.1, 10.0, 0.1);
        this.timerSetting = new Setting("Timer", this, 2.0, 1.0, 10.0, 0.1);
        this.timerDurationSetting = new Setting("TimerDuration", this, 400.0, 50.0, 1000.0, 50.0);
        this.blinkModeSetting = new Setting("BlinkMode", this, FlightBlinkMode.PULSE);
        this.blinkDelaySetting = new Setting("BlinkDelay", this, 800.0, 50.0, 2000.0, 50.0);
        this.waitSetting = new Setting("Wait", this, false);
        this.boostSetting = new Setting("Boost", this, false);
        this.baseBoostSetting = new Setting("BaseBoost", this, false);
        this.damageSetting = new Setting("Damage", this, false);
        this.blinkSetting = new Setting("Blink", this, false);
        this.compensateSetting = new Setting("Compensate", this, false);
        this.viewBobbingSetting = new Setting("ViewBobbing", this, false);
        this.stopMotionSetting = new Setting("StopMotion", this, false);
        this.timerBoostSetting = new Setting("TimerBoost", this, false);
        this.antiKickSetting = new Setting("AntiKick", this, false);
        this.blinkTimer = new ElapsedTimer();
        this.bufferedPackets = new ConcurrentLinkedQueue<>();
    }

    public void flushBufferedPackets() {
        while (!this.bufferedPackets.isEmpty()) {
            this.sendPacketWithoutEvent(this.bufferedPackets.poll());
        }
    }

    public void onDisable() {
        minecraft.timer.timerSpeed = 1.0F;

        if (this.stopMotionSetting.asBoolean()) {
            MovementUtils.setSpeed(0.0);
        }

        while (!this.bufferedPackets.isEmpty()) {
            this.sendPacketWithoutEvent(this.bufferedPackets.poll());
        }

        super.onDisable();
    }

    @Subscribe(mc = StepEvent.class)
    public void onStep(StepEvent event) {
        if (this.modeSetting.getValue() == FlightMode.HYPIXEL) {
            event.setCancelled(true);
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();

        if (event.getDirection() == PacketDirection.RECEIVED) {
            if (packet instanceof S08PacketPlayerPosLook) {
                this.awaitingComboCorrection = false;
                this.timer.reset();
                return;
            }

            if (!(packet instanceof S12PacketEntityVelocity)
                    || minecraft.world.getEntityByID(
                                    ((S12PacketEntityVelocity) event.getPacket()).getEntityID())
                            != minecraft.player) {
                return;
            }

            this.receivedVelocity = true;
            return;
        }

        if (event.getDirection() == PacketDirection.SEND && this.blinkSetting.asBoolean()) {
            if (this.shouldBufferPacket(packet)) {
                this.bufferedPackets.add(packet);
                event.cancel();
            }

            if (this.blinkModeSetting.getValue() == FlightBlinkMode.PULSE
                    && this.blinkTimer.elapsedAndReset((long) this.blinkDelaySetting.asDouble())) {
                this.flushBufferedPackets();
            }
        }
    }

    public String getSuffix() {
        return ((FlightMode) this.modeSetting.getValue()).getDisplayName();
    }

    private boolean shouldBufferPacket(Packet packet) {
        return packet instanceof PlayerMovementPacket
                || packet instanceof C0BPacketEntityAction
                || packet instanceof UseEntityPacket
                || packet instanceof UseItemPacket
                || packet instanceof C00PacketKeepAlive
                || packet instanceof C0APacketAnimation;
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        LocalPlayer player = minecraft.player;
        FlightMode flightMode = this.modeSetting.getValue() == FlightMode.HYPIXELCOMBO
                ? this.comboMode
                : (FlightMode) this.modeSetting.getValue();

        if (event.getPhase() == MotionPhase.PRE) {
            if (this.viewBobbingSetting.asBoolean()) {
                player.cameraYaw = 0.099999994F;
            }

            if (this.modeSetting.getValue() == FlightMode.HYPIXELCOMBO
                    && this.awaitingComboCorrection) {
                event.setX(0.0);
                event.setZ(0.0);
            }

            if (flightMode == FlightMode.CUBECRAFT2 && !this.receivedVelocity) {
                event.setX(0.0);
                event.setZ(0.0);
                return;
            }

            if (flightMode == FlightMode.CUBECRAFT2 || flightMode == FlightMode.HYPIXEL) {
                if (!this.damageSequenceSent && this.damageSetting.asBoolean()) {
                    this.sendDamageSequence(
                            MovementPhysicsHelper.getMaxFallDistanceWithJump() + 1.0E-5,
                            (FlightDamageMode) this.damageModeSetting.getValue());
                    this.damageSequenceSent = true;
                }

                if (this.waitSetting.asBoolean() && !this.receivedVelocity && player.onGround) {
                    event.setX(0.0);
                    event.setZ(0.0);
                    return;
                }

                if (this.boostSetting.asBoolean() || this.modeSetting.getValue() == FlightMode.CUBECRAFT2) {
                    double d0 = this.speedSetting.asDouble() / 2.0;
                    double d1 = this.boostModeSetting.getValue() == FlightBoostMode.NCP3
                            ? d0
                            : MathUtils.getRandomFloat(2.145F, 2.147F);

                    if (MovementUtils.isMoving()) {
                        if (player.onGround) {
                            if (this.baseBoostSetting.asBoolean() && this.boostStage == 0) {
                                this.moveSpeed = MovementUtils.getBaseMoveSpeed();
                                this.boostStage = 1;
                            } else if (this.wasOnGround && this.boostStage == 2) {
                                event.setY(player.motionY = MovementUtils.getJumpMotion() - MathUtils.getRandomFloat(0.01F, 0.02F));
                                this.moveSpeed = this.lastHorizontalDistance * d1;
                                this.boostStage = 0;
                            } else {
                                if (this.baseBoostSetting.asBoolean()) {
                                    this.lastHorizontalDistance = MovementUtils.getBaseMoveSpeed();
                                }
                                if (this.boostModeSetting.getValue() == FlightBoostMode.NCP3) {
                                    this.moveSpeed = this.lastHorizontalDistance * d1;
                                } else {
                                    this.moveSpeed = this.lastHorizontalDistance * this.speedSetting.asDouble();
                                }
                                this.boostStage = 2;
                            }
                        } else {
                            if (this.wasOnGround && this.boostModeSetting.getValue() == FlightBoostMode.NCP1) {
                                this.moveSpeed = this.lastHorizontalDistance
                                        - 0.66 * (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion())
                                        - 1.0E-5;
                            } else {
                                LinkedList<Double> list = new LinkedList<>();
                                list.add(this.lastHorizontalDistance - this.lastHorizontalDistance / 160.0);
                                list.add(this.lastHorizontalDistance - (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion()) / 33.3);
                                list.add(this.lastHorizontalDistance - (this.lastHorizontalDistance - MovementUtils.getSpeedWithPotion()) * 0.02);
                                list.sort(Double::compare);
                                this.moveSpeed = list.get(2) - 1.0E-5;
                            }
                        }

                        if (this.compensateSetting.asBoolean() && !player.onGround && this.wasOnGround) {
                            double d4 = minecraft.player.posY % 1.0;
                            List groundOffsets = MovementPhysicsHelper.GROUND_OFFSETS;
                            if (!groundOffsets.contains(d4)) {
                                double d2 = MovementUtils.getJumpMotion();
                                d2 -= 0.08;
                                d2 *= 0.98F;
                                double d3 = d4 + d2;
                                groundOffsets.sort(Comparator.<Double>comparingDouble(d1x -> Math.abs(d3 - d1x)));
                                event.setY(player.motionY = (Double) groundOffsets.get(0) - d4);
                            }
                        }

                        this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                        MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                        this.wasOnGround = player.onGround;
                    } else {
                        MovementUtils.setHorizontalMotion(event, 0.0);
                    }
                }
            } else if (flightMode == FlightMode.CUBECRAFT) {
                if (this.anchorPosition == null) {
                    this.anchorPosition = minecraft.player.getPositionVector().addVector(0.0, -1.0E-5, 0.0);
                }
                player.posY = this.anchorPosition.y;
            }

            if (flightMode == FlightMode.COLLISION || flightMode == FlightMode.CUBECRAFT) {
                if (!minecraft.gameSettings.keyBindSneak.pressed) {
                    BlockPos replacedBlockPositionValue = new BlockPos(player.posX, (int) player.posY - 1.0E-5, player.posZ);
                    BlockState replacedBlockStateValue = minecraft.world.getBlockState(replacedBlockPositionValue);
                    minecraft.world.setBlockState(replacedBlockPositionValue, Blocks.barrier.getDefaultState(), 3);
                    this.replacedBlockPosition = replacedBlockPositionValue;
                    this.replacedBlockState = replacedBlockStateValue;
                }
            }
        } else if (event.getPhase() == MotionPhase.POST) {
            if ((this.modeSetting.getValue() == FlightMode.COLLISION || this.modeSetting.getValue() == FlightMode.CUBECRAFT)
                    && this.replacedBlockPosition != null
                    && this.replacedBlockState != null) {
                minecraft.world.setBlockState(this.replacedBlockPosition, this.replacedBlockState, 3);
                this.replacedBlockPosition = null;
                this.replacedBlockState = null;
            }
        }
    }
}
