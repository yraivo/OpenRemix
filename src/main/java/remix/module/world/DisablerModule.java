package remix.module.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import optifine.MathUtils;
import remix.module.world.DisablerFlagMode;
import remix.module.world.DisablerMode;
import remix.util.time.ElapsedTimer;
import remix.server.hypixel.HypixelServerDetector;
import remix.event.MotionPhase;
import remix.util.movement.MovementUtils;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.command.ClientCommandManager;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/** Disabler module: world events and 4 settings. */
public final class DisablerModule extends ModuleBase {
    private Setting cancelFlagSetting;
    private Setting flagSetting;
    private Setting flagDelaySetting;
    private ElapsedTimer flagTimer;
    private ElapsedTimer movementGateTimer;
    private int packetSequence = 1;
    private boolean awaitingFlagCorrection;
    private final Setting<DisablerFlagMode> flagModeSetting;
    private static final ScheduledExecutorService delayedPacketExecutor =
            Executors.newSingleThreadScheduledExecutor();
    private List<Packet> bufferedPackets;
    private final Setting<DisablerMode> disablerSetting =
            new Setting("Mode", this, DisablerMode.TRANSACTION);

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            if ((this.flagSetting.asBoolean() || this.disablerSetting.getValue() == DisablerMode.FLAG)
                    && this.flagTimer.elapsedAndReset((long) this.flagDelaySetting.asDouble())) {
                double offset = minecraft.player.motionY >= 0.0
                        ? 11.0F + ThreadLocalRandom.current().nextFloat()
                        : -11.0F - ThreadLocalRandom.current().nextFloat();

                DisablerFlagMode flagMode = (DisablerFlagMode) this.flagModeSetting.getValue();
                if (flagMode == DisablerFlagMode.EDIT) {
                    event.setY(event.getY() + offset);
                } else if (flagMode == DisablerFlagMode.PACKET) {
                    this.sendPacketWithoutEvent(
                            new PlayerPositionPacket(
                                    event.getX(),
                                    event.getY() + offset,
                                    event.getZ(),
                                    event.onGround));
                }
            }

            DisablerMode mode = (DisablerMode) this.disablerSetting.getValue();
            if (mode == DisablerMode.STUPID) {
                this.sendPacketWithoutEvent(
                        new C15PacketClientSettings(
                                minecraft.gameSettings.language,
                                minecraft.gameSettings.renderDistanceChunks,
                                minecraft.gameSettings.chatVisibility,
                                minecraft.gameSettings.chatColours,
                                5));
                this.sendPacketWithoutEvent(
                        new C0CPacketInput(
                                minecraft.player.moveStrafing,
                                minecraft.player.moveForward,
                                minecraft.player.movementInput.jump,
                                minecraft.player.movementInput.sneak));
                this.sendPacketWithoutEvent(
                        new C0BPacketEntityAction(minecraft.player, C0BPacketEntityActionAction.RIDING_JUMP));
                this.sendPacketWithoutEvent(
                        new C0BPacketEntityAction(minecraft.player, C0BPacketEntityActionAction.STOP_SLEEPING));
                this.sendPacketWithoutEvent(
                        new C0BPacketEntityAction(minecraft.player, C0BPacketEntityActionAction.OPEN_INVENTORY));
                this.sendPacketWithoutEvent(
                        new C16PacketClientStatus(C16PacketClientStatusEnumState.PERFORM_RESPAWN));
                this.sendPacketWithoutEvent(
                        new C13PacketPlayerAbilities(minecraft.player.capabilities));

                for (PlayerEntity playerEntity : minecraft.world.playerEntities) {
                    if (playerEntity != minecraft.player) {
                        this.sendPacketWithoutEvent(
                                new C18PacketSpectate(minecraft.player.getUniqueId()));
                        break;
                    }
                }
            } else if (mode == DisablerMode.HYPIXELFLAG) {
                if (!this.awaitingFlagCorrection && minecraft.player.onGround) {
                    double jumpMotion = MovementUtils.getJumpMotion();
                    if (this.packetSequence == 1) {
                        event.setY(event.getY() + jumpMotion);
                        this.packetSequence++;
                    } else {
                        event.setY(event.getY() + jumpMotion + (jumpMotion - 0.08) * 0.98F);
                        ClientCommandManager.printMessage(
                                "Please wait a couple seconds! (Disabler method by @FinalException)");
                        this.awaitingFlagCorrection = true;
                        this.packetSequence = 1;
                    }
                    event.onGround = false;
                } else if (!this.awaitingFlagCorrection) {
                    event.setY(event.getY() - 0.0784000015258789);
                    this.awaitingFlagCorrection = true;
                }
                event.setCancelled(true);
            } else if (mode == DisablerMode.PACKET) {
                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(
                                minecraft.player.posX,
                                minecraft.player.posY - 1.0E-10,
                                minecraft.player.posZ,
                                false));
                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(
                                minecraft.player.posX,
                                minecraft.player.posY,
                                minecraft.player.posZ,
                                minecraft.player.onGround));
                this.sendPacketWithoutEvent(
                        new PlayerPositionPacket(
                                minecraft.player.posX,
                                minecraft.player.posY - 1.0E-10,
                                minecraft.player.posZ,
                                false));
            }
        } else if (event.getPhase() == MotionPhase.POST) {
            if (this.disablerSetting.getValue() == DisablerMode.LAG
                    && this.timer.elapsedAndReset((long) MathUtils.getRandomFloat(100.0F, 250.0F))
                    && !this.bufferedPackets.isEmpty()) {
                for (Packet packet : this.bufferedPackets) {
                    this.sendPacketWithoutEvent(packet);
                }
                this.bufferedPackets.clear();
                this.timer.reset();
            }
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();
        DisablerMode mode = (DisablerMode) this.disablerSetting.getValue();

        switch (mode) {
            case TRANSACTION:
                if (event.getDirection() == PacketDirection.SEND
                        && minecraft.player != null
                        && minecraft.world != null
                        && (packet instanceof C0FPacketConfirmTransaction
                                || packet instanceof C00PacketKeepAlive)) {
                    delayedPacketExecutor.schedule(
                            () -> this.sendPacketWithoutEvent(packet), 800L, TimeUnit.MILLISECONDS);
                    event.setCancelled(true);
                }
                break;

            case FLAG:
                if (event.getDirection() == PacketDirection.RECEIVED
                        && this.cancelFlagSetting.asBoolean()
                        && packet instanceof S08PacketPlayerPosLook
                        && minecraft.player != null) {
                    S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook) packet;
                    if (minecraft.player.getDistance(s08.getX(), s08.getY(), s08.getZ()) <= 10.0) {
                        this.sendPacket(
                                new PlayerPositionLookPacket(
                                        s08.getX(), s08.getY(), s08.getZ(), s08.yaw, s08.pitch, true));
                        event.setCancelled(true);
                    }
                }
                break;

            case LAG:
                if (event.getDirection() == PacketDirection.SEND && minecraft.player != null) {
                    this.bufferedPackets.add(packet);
                    event.setCancelled(true);
                }
                break;

            case TIMER:
                if (event.getDirection() == PacketDirection.SEND
                        && packet instanceof PlayerMovementPacket) {
                    if (this.packetSequence % 3 != 0) {
                        event.setCancelled(true);
                    }
                    this.packetSequence++;
                }
                break;

            case HYPIXELFLAG:
                if (event.getDirection() == PacketDirection.RECEIVED
                        && packet instanceof S08PacketPlayerPosLook) {
                    ClientCommandManager.printMessage("The AntiCheat Should be Disabled now! (5s)");
                    this.toggle();
                }
                break;

            case HYPIXEL:
                if (!HypixelServerDetector.isOnHypixel()) {
                    return;
                }
                if (event.getDirection() == PacketDirection.SEND) {
                    if (packet instanceof C00PacketKeepAlive) {
                        event.setCancelled(true);
                        return;
                    }
                    if (packet instanceof C0FPacketConfirmTransaction) {
                        C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction) packet;
                        if (c0f.getId() == 0 && c0f.getUid() < 0) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                    if (packet instanceof C13PacketPlayerAbilities) {
                        event.setCancelled(true);
                        return;
                    }
                } else if (event.getDirection() == PacketDirection.RECEIVED
                        && packet instanceof S00PacketKeepAlive) {
                    boolean isFlying = minecraft.player.capabilities.isFlying;
                    boolean allowFlying = minecraft.player.capabilities.allowFlying;
                    minecraft.player.capabilities.isFlying = true;
                    minecraft.player.capabilities.allowFlying = true;
                    this.sendPacketWithoutEvent(
                            new C13PacketPlayerAbilities(minecraft.player.capabilities));
                    minecraft.player.capabilities.isFlying = isFlying;
                    minecraft.player.capabilities.allowFlying = allowFlying;
                }
                break;

            case RIDING:
                if (event.getDirection() == PacketDirection.SEND) {
                    if (packet instanceof PlayerMovementPacket) {
                        this.sendPacket(
                                new C0CPacketInput(
                                        minecraft.player.moveStrafing,
                                        minecraft.player.moveForward,
                                        minecraft.player.movementInput.jump,
                                        minecraft.player.movementInput.sneak));
                    }
                    if (packet instanceof C0BPacketEntityAction) {
                        event.setCancelled(true);
                    }
                }
                break;

            case PING:
                if (event.getDirection() == PacketDirection.SEND
                        && packet instanceof C00PacketKeepAlive) {
                    C00PacketKeepAlive c00 = (C00PacketKeepAlive) packet;
                    event.setPacket(new C00PacketKeepAlive(c00.getKey() + 1));
                }
                break;

            case MMC:
                if (event.getDirection() == PacketDirection.SEND) {
                    if (packet instanceof C00PacketKeepAlive
                            || packet instanceof C0FPacketConfirmTransaction) {
                        delayedPacketExecutor.schedule(
                                () -> this.sendPacketWithoutEvent(packet), 1000L, TimeUnit.MILLISECONDS);
                        event.setCancelled(true);
                    } else if (packet instanceof PlayerMovementPacket) {
                        if (!this.movementGateTimer.hasElapsed(1000L)) {
                            event.setCancelled(true);
                        }
                    }
                } else if (event.getDirection() == PacketDirection.RECEIVED) {
                    if (packet instanceof S07PacketRespawn) {
                        this.movementGateTimer.reset();
                    }
                }
                break;

            case ROTATION:
                if (event.getDirection() == PacketDirection.SEND) {
                    if (packet instanceof PlayerPositionPacket) {
                        PlayerPositionPacket posPacket = (PlayerPositionPacket) packet;
                        event.setPacket(
                                new PlayerPositionLookPacket(
                                        posPacket.getX(),
                                        posPacket.getY(),
                                        posPacket.getZ(),
                                        minecraft.player.rotationYaw + MathUtils.aN() * 1.0E-5F,
                                        minecraft.player.rotationPitch,
                                        posPacket.isOnGround()));
                    } else if (packet instanceof PlayerMovementPacket) {
                        PlayerMovementPacket movPacket = (PlayerMovementPacket) packet;
                        event.setPacket(
                                new PlayerPositionLookPacket(
                                        minecraft.player.posX,
                                        minecraft.player.posY,
                                        minecraft.player.posZ,
                                        minecraft.player.rotationYaw + MathUtils.aN() * 1.0E-5F,
                                        minecraft.player.rotationPitch,
                                        movPacket.isOnGround()));
                    }
                }
                break;

            default:
                break;
        }
    }

    public String getSuffix() {
        return ((DisablerMode) this.disablerSetting.getValue()).getDisplayName();
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (this.disablerSetting.getValue() == DisablerMode.HYPIXELFLAG
                && this.awaitingFlagCorrection) {
            event.setX(minecraft.player.motionX = 0.0);
            event.setY(minecraft.player.motionY = 0.0);
            event.setZ(minecraft.player.motionZ = 0.0);
            return;
        }

        if (this.disablerSetting.getValue() == DisablerMode.MMC) {
            if (!this.movementGateTimer.hasElapsed(1000L)) {
                event.setX(0.0);
                event.setY(0.0);
                event.setZ(0.0);
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.disablerSetting.getValue() == DisablerMode.PING
                && remix.module.ModuleMetadata.clientServices != null
                && ClientServices.commands != null
                && minecraft.player != null) {
            ClientCommandManager.printMessage("Please relog for the disabler to properly work");
        }

        this.timer.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.packetSequence = 1;
        this.awaitingFlagCorrection = false;
        super.onDisable();
    }

    public DisablerModule() {
        super("Disabler", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
        this.flagModeSetting = new Setting("FlagMode", this, DisablerFlagMode.EDIT);
        this.flagDelaySetting = new Setting("FlagDelay", this, 150.0, 0.0, 1000.0, 50.0);
        this.flagSetting = new Setting("Flag", this, true);
        this.cancelFlagSetting = new Setting("CancelFlag", this, true);
        this.bufferedPackets = new ArrayList<>();
        this.flagTimer = new ElapsedTimer();
        this.movementGateTimer = new ElapsedTimer();
    }
}
