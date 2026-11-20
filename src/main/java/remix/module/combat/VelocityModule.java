package remix.module.combat;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import remix.event.impl.EntityPushEvent;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.combat.VelocityMode;
import remix.event.impl.WaterPushEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/**
 * Velocity module: scales received explosion and entity velocity.
 * FLAG rewrites the next movement packet, then waits for server position correction.
 * AAC modifies local hurt motion.
 */
public final class VelocityModule extends ModuleBase {

    private boolean rewriteNextMovementPacket;
    private Setting entitiesSetting;
    private Setting explosionsSetting;
    private Setting reverseSetting;
    private Setting waterSetting;
    private boolean awaitingFlagCorrection;

    private final Setting<VelocityMode> modeSetting =
            new Setting("Mode", this, VelocityMode.NORMAL);
    private Setting verticalSetting;
    private final Setting horizontalSetting =
            new Setting("Horizontal", this, 50.0, 0.0, 200.0, 1.0);

    public VelocityModule() {
        super("Velocity", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));
        this.verticalSetting = new Setting("Vertical", this, 50.0, 0.0, 200.0, 1.0);
        this.explosionsSetting = new Setting("Explosions", this, true);
        this.reverseSetting = new Setting("Reverse", this, false);
        this.waterSetting = new Setting("Water", this, false);
        this.entitiesSetting = new Setting("Entities", this, false);
    }

    @Override
    public String getSuffix() {
        return ((VelocityMode) this.modeSetting.getValue()).getDisplayName();
    }

    @Subscribe(mc = WaterPushEvent.class)
    public void onWaterPush(WaterPushEvent event) {
        if (this.waterSetting.asBoolean() && event.getEntity() instanceof LocalPlayer) {
            event.cancel();
        }
    }

    @Subscribe(mc = EntityPushEvent.class)
    public void onEntityPush(EntityPushEvent event) {
        if (this.entitiesSetting.asBoolean()) {
            event.cancel();
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.modeSetting.getValue() == VelocityMode.AAC
                && minecraft.player.hurtTime == 6
                && !minecraft.player.onGround) {
            minecraft.player.motionX *= -0.475;
            minecraft.player.motionY *= 0.9;
            minecraft.player.motionZ *= -0.475;
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        PacketDirection packetDirection = event.getDirection();
        Packet packet = event.getPacket();
        VelocityMode velocityMode = (VelocityMode) this.modeSetting.getValue();

        if (velocityMode == VelocityMode.FLAG) {
            if (packetDirection == PacketDirection.SEND) {
                if (packet instanceof PlayerMovementPacket && this.rewriteNextMovementPacket) {
                    PlayerMovementPacket playerMovementPacket = (PlayerMovementPacket) packet;
                    event.setPacket(
                            new PlayerPositionPacket(
                                    playerMovementPacket.getX(),
                                    playerMovementPacket.getY() - 0.006F,
                                    playerMovementPacket.getZ(),
                                    false));
                    this.rewriteNextMovementPacket = false;
                    this.awaitingFlagCorrection = true;
                    return;
                }
            } else if (packetDirection == PacketDirection.RECEIVED
                    && packet instanceof S08PacketPlayerPosLook
                    && minecraft.player != null
                    && this.awaitingFlagCorrection) {
                S08PacketPlayerPosLook posLookPacket = (S08PacketPlayerPosLook) packet;
                if (minecraft.player.getDistance(
                                posLookPacket.getX(),
                                posLookPacket.getY(),
                                posLookPacket.getZ())
                        <= 10.0) {
                    this.sendPacketWithoutEvent(
                            new PlayerPositionLookPacket(
                                    posLookPacket.getX(),
                                    posLookPacket.getY(),
                                    posLookPacket.getZ(),
                                    posLookPacket.yaw,
                                    posLookPacket.pitch,
                                    false));
                    event.setCancelled(true);
                    this.awaitingFlagCorrection = false;
                    return;
                }
            }
        }

        if ((velocityMode == VelocityMode.NORMAL || velocityMode == VelocityMode.FLAG)
                && packetDirection == PacketDirection.RECEIVED) {
            if (minecraft.player == null || minecraft.world == null) {
                return;
            }

            boolean isExplosion = packet instanceof S27PacketExplosion;
            boolean isEntityVelocity = packet instanceof S12PacketEntityVelocity;

            if (!isExplosion && !isEntityVelocity) {
                return;
            }

            boolean reverse = this.reverseSetting.asBoolean();
            double horizontalScale = this.horizontalSetting.asDouble() / 100.0 * (reverse ? -1 : 1);
            double verticalScale = reverse ? 1.0 : this.verticalSetting.asDouble() / 100.0;

            if (horizontalScale != 0.0 || verticalScale != 0.0) {
                if (isExplosion) {
                    if (!this.explosionsSetting.asBoolean()) {
                        return;
                    }

                    S27PacketExplosion explosionPacket = (S27PacketExplosion) packet;
                    explosionPacket.setMotionX((int) (explosionPacket.getMotionX() * horizontalScale));
                    explosionPacket.setMotionZ((int) (explosionPacket.getMotionZ() * horizontalScale));
                    explosionPacket.setMotionY((int) (explosionPacket.getMotionY() * verticalScale));
                    this.rewriteNextMovementPacket = true;
                    return;
                }

                S12PacketEntityVelocity entityVelocityPacket = (S12PacketEntityVelocity) packet;
                if (minecraft.world.getEntityByID(entityVelocityPacket.getEntityID()) == minecraft.player) {
                    entityVelocityPacket.setMotionX((int) (entityVelocityPacket.getMotionX() * horizontalScale));
                    entityVelocityPacket.setMotionZ((int) (entityVelocityPacket.getMotionZ() * horizontalScale));
                    entityVelocityPacket.setMotionY((int) (entityVelocityPacket.getMotionY() * verticalScale));
                    this.rewriteNextMovementPacket = true;
                }
                return;
            }

            // Both scales are 0.0 -> cancel velocity completely
            if (isExplosion) {
                event.setCancelled(true);
                this.rewriteNextMovementPacket = true;
                return;
            }

            S12PacketEntityVelocity entityVelocityPacket = (S12PacketEntityVelocity) packet;
            if (minecraft.world.getEntityByID(entityVelocityPacket.getEntityID()) == minecraft.player) {
                event.setCancelled(true);
                this.rewriteNextMovementPacket = true;
            }
        }
    }
}
