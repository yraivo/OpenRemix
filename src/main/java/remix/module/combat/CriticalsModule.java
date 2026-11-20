package remix.module.combat;

import net.minecraft.block.material.Material;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.potion.Potion;
import net.minecraft.util.RayTraceResult;

import optifine.MathUtils;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.movement.SpeedModule;
import remix.setting.Setting;
import remix.util.movement.MovementUtils;

/**
 * Criticals module: applies mode-specific position offsets or motion phases around attacks.
 * Supported modes: PACKET, PACKET2, HYPIXEL, DOWN, EXPERIMENTAL, EDIT, HOVER, NOGROUND, JUMP.
 */
public final class CriticalsModule extends ModuleBase {

    private double[] packet2Offsets;
    private final Setting<CriticalsMode> modeSetting =
            new Setting("Mode", this, CriticalsMode.PACKET);
    private double[] packetOffsets;
    private double[] hypixelOffsets;
    private Setting hurtTimeSetting;

    private double verticalJitter;
    private boolean wasOnGround;
    private double[] experimentalOffsets;
    private float previousFallDistance;
    private Setting randomizeSetting;
    private double[] downOffsets;
    private final Setting<CriticalsDelayMode> delayModeSetting =
            new Setting("DelayMode", this, CriticalsDelayMode.CUSTOM);
    private boolean raisedEditPhase;

    private final Setting delaySetting = new Setting("Delay", this, 100.0, 0.0, 1000.0, 50.0);

    public CriticalsModule() {
        super("Criticals", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));
        this.hurtTimeSetting = new Setting("HurtTime", this, 14.0, 0.0, 20.0, 1.0);
        this.randomizeSetting = new Setting("Randomize", this, false);
        this.packetOffsets = new double[] {0.051, 0.0, 0.0125, 0.0};
        this.packet2Offsets = new double[] {-0.0125, 0.051, 0.0};
        this.hypixelOffsets = new double[] {0.045F, 0.0010000283, 0.0436F, 0.0010000283};
        this.downOffsets = new double[] {0.051, -0.0015, 0.0125, 0.0};
        this.experimentalOffsets = new double[] {0.0, 0.06249999999999, 0.0011};
    }

    @Override
    public void onDisable() {
        this.raisedEditPhase = false;
        super.onDisable();
    }

    @Override
    public String getSuffix() {
        return ((CriticalsMode) this.modeSetting.getValue()).getDisplayName();
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() != PacketDirection.SEND) {
            return;
        }

        CriticalsMode mode = (CriticalsMode) this.modeSetting.getValue();
        if (CriticalsMode.getAttackMode(mode) == CriticalsAttackMode.EDIT
                || !(event.getPacket() instanceof C0APacketAnimation)) {
            return;
        }

        LocalPlayer player = minecraft.player;
        AuraModule aura = ClientServices.moduleManager.aura;
        CriticalsDelayMode delayMode = (CriticalsDelayMode) this.delayModeSetting.getValue();
        RayTraceResult mouseOver = minecraft.objectMouseOver;
        Entity target =
                aura.currentTarget != null
                        ? aura.currentTarget
                        : (mouseOver != null ? mouseOver.entityHit : null);

        if (target == null || !this.canSpoofCritical()) {
            return;
        }

        boolean delayAllowed =
                (delayMode == CriticalsDelayMode.CUSTOM
                                && target.hurtResistantTime < this.hurtTimeSetting.asDouble()
                                && this.timer.elapsedAndReset((long) this.delaySetting.asDouble()))
                        || (delayMode == CriticalsDelayMode.AFTER && target.hurtResistantTime != 0);

        if (!delayAllowed) {
            return;
        }

        double[] offsets;
        switch (mode) {
            case PACKET:
                offsets = this.packetOffsets;
                break;
            case PACKET2:
                offsets = this.packet2Offsets;
                break;
            case HYPIXEL:
                offsets = this.hypixelOffsets;
                break;
            case DOWN:
                offsets = this.downOffsets;
                break;
            case EXPERIMENTAL:
                offsets = this.experimentalOffsets;
                break;
            default:
                offsets = new double[0];
                break;
        }

        double previousOffset = 0.0;
        float accumulatedFall = player.fallDistance;
        boolean randomize = this.randomizeSetting.asBoolean();

        for (double offset : offsets) {
            if (randomize) {
                if (offset + 2.0E-4 <= 0.0625 && offset != 0.0) {
                    offset += MathUtils.getRandomDouble(-2.0E-4, 2.0E-4);
                } else if (offset >= 0.0625) {
                    offset += MathUtils.getRandomFloat(-2.0E-4F, 0.0F);
                } else {
                    offset += MathUtils.getRandomDouble(0.0, 2.0E-4);
                }
            }

            this.sendPacketWithoutEvent(
                    new PlayerPositionPacket(
                            player.posX, player.posY + offset, player.posZ, false));

            if (previousOffset > offset) {
                accumulatedFall = (float) (accumulatedFall + (previousOffset - offset));
            }

            previousOffset = offset;
        }

        if (delayMode == CriticalsDelayMode.AFTER) {
            target.hurtResistantTime = 0;
        }

        player.fallDistance += accumulatedFall;
        player.prevPosY += accumulatedFall;
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() != MotionPhase.PRE) {
            return;
        }

        CriticalsMode mode = (CriticalsMode) this.modeSetting.getValue();
        LocalPlayer player = minecraft.player;

        if (CriticalsMode.getAttackMode(mode) != CriticalsAttackMode.PACKET) {
            AuraModule aura = ClientServices.moduleManager.aura;
            LivingEntity currentTarget = aura.currentTarget;
            float baseSpeed = 0.082F;
            float yOffset = 0.002F;

            if (this.randomizeSetting.asBoolean()) {
                baseSpeed += MathUtils.getRandomFloat(-0.005F, 0.005F);
                if (this.verticalJitter == 0.0) {
                    this.verticalJitter = MathUtils.getRandomFloat(-5.0E-4F, 5.0E-4F);
                }
                yOffset = (float) (yOffset + this.verticalJitter);
            }

            switch (mode) {
                case EXPERIMENTAL: // EXPERIMENTAL
                    if (this.canSpoofCritical() && currentTarget != null && this.wasOnGround) {
                        event.setY(event.getY() + yOffset);
                        event.onGround = false;
                        player.fallDistance += yOffset;
                    }
                    break;

                case EDIT: // EDIT
                    if (this.canSpoofCritical() && currentTarget != null && this.wasOnGround) {
                        if (!this.raisedEditPhase) {
                            event.setY(event.getY() + MovementUtils.getBaseMoveSpeed(baseSpeed));
                            event.onGround = true;
                            this.raisedEditPhase = true;
                            break;
                        }

                        player.prevPosY += baseSpeed - yOffset;
                        event.setY(event.getY() + yOffset);
                        event.onGround = false;
                        this.raisedEditPhase = false;
                    }
                    break;

                case HOVER: // HOVER
                    if (this.canSpoofCritical() && currentTarget != null && this.wasOnGround) {
                        if (!this.raisedEditPhase) {
                            event.setY(event.getY() + MovementUtils.getBaseMoveSpeed(baseSpeed));
                            this.raisedEditPhase = true;
                        } else {
                            player.prevPosY += baseSpeed - yOffset;
                            event.setY(event.getY() + yOffset);
                        }
                        event.onGround = false;
                    } else {
                        this.verticalJitter = 0.0;
                        this.raisedEditPhase = false;
                    }
                    break;

                case NOGROUND: // NOGROUND
                    event.onGround = false;
                    if (player.fallDistance == 0.0F) {
                        player.fallDistance = this.previousFallDistance;
                    }
                    this.previousFallDistance = player.fallDistance;
                    break;
            }
        }

        this.wasOnGround = player.onGround;
    }

    private boolean canSpoofCritical() {
        LocalPlayer player = minecraft.player;
        SpeedModule speed = ClientServices.moduleManager.speed;
        return player.onGround
                && !speed.isEnabled()
                && player.ridingEntity == null
                && !player.isOnLadder()
                && !player.isInWater()
                && !player.isPotionActive(Potion.BLINDNESS)
                && !player.isInsideOfMaterial(Material.lava);
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.modeSetting.getValue() == CriticalsMode.JUMP) {
            LocalPlayer player = minecraft.player;
            if (player.onGround) {
                AuraModule aura = ClientServices.moduleManager.aura;
                if (aura.isEnabled() && aura.currentTarget != null) {
                    player.jump();
                }
            }
        }
    }

    public boolean isAuraAttackTimingAllowed() {
        AuraModule aura = ClientServices.moduleManager.aura;
        boolean prePhaseAllowed =
                !aura.isEnabled() || aura.attackPhase.getValue() == AuraAttackPhase.PRE;
        return prePhaseAllowed == this.raisedEditPhase
                || this.modeSetting.getValue() != CriticalsMode.EDIT;
    }
}
