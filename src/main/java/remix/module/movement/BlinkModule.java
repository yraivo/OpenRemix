package remix.module.movement;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.Render3DEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.movement.MovementUtils;
import remix.util.time.ElapsedTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Buffers selected outgoing actions, tracks a rendered trail and optional ghost player. Restart
 * flushes and recaptures the origin; FreeCam causes the buffered queue to be discarded on cleanup.
 */
public final class BlinkModule extends ModuleBase {

    private Queue<Packet> bufferedPackets;

    private double[] lastTrailPosition;

    private int maxTrailPoints;

    private Setting disableOnDeathSetting;

    private Setting maxDistanceSetting;

    private Setting delaySetting;

    private List<double[]> trailPositions;

    private Setting showEntitySetting;

    private ElapsedTimer pulseTimer;

    private double originZ;

    private double originX;

    public EntityOtherPlayerMP ghostPlayer;

    private Setting intermittentSetting;

    public Setting<BlinkOnDamageMode> onDamageSetting;

    private double originY;

    private void flushAndRemoveGhost() {

        if (minecraft.player != null && minecraft.world != null) {

            boolean enabled = ClientServices.moduleManager.freeCam.isEnabled();

            if (enabled) {

                this.bufferedPackets.clear();
            }

            this.flushPacketQueue(this.bufferedPackets);

            this.trailPositions.clear();

            if (this.ghostPlayer != null || minecraft.world.getEntityByID(-1337) != null) {

                minecraft.world.removeEntityFromWorld(-1337);

                this.ghostPlayer = null;
            }
        }
    }

    private void captureOriginAndCreateGhost() {

        if (minecraft.player != null && minecraft.world != null) {

            this.originX = minecraft.player.posX;

            this.originY = minecraft.player.posY;

            this.originZ = minecraft.player.posZ;

            this.lastTrailPosition = new double[] {this.originX, this.originY, this.originZ};

            if (this.showEntitySetting.asBoolean()) {

                if (minecraft.world.getEntityByID(-1337) != null) {

                    minecraft.world.removeEntityFromWorld(-1337);
                }

                this.ghostPlayer =
                        new EntityOtherPlayerMP(minecraft.world, minecraft.player.getGameProfile());

                this.ghostPlayer.clonePlayer(minecraft.player, true);

                this.ghostPlayer.copyLocationAndAnglesFrom(minecraft.player);

                this.ghostPlayer.rotationYawHead = minecraft.player.rotationYawHead;

                minecraft.world.addEntityToWorld(-1337, this.ghostPlayer);
            }
        }
    }

    private void flushPacketQueue(Queue<Packet> queue) {

        while (!this.bufferedPackets.isEmpty()) {

            this.sendPacketWithoutEvent((Packet) queue.poll());
        }
    }

    public String getSuffix() {

        return String.valueOf(this.bufferedPackets.size());
    }

    public BlinkModule() {
        super("Blink", new ModuleDefaults(ModuleCategory.MOVEMENT).keyBind(38));

        this.intermittentSetting = new Setting("Intermittent", this, false);

        this.delaySetting = new Setting("Delay", this, 300.0, 50.0, 2000.0, 50.0);

        this.maxDistanceSetting = new Setting("MaxDistance", this, 50.0, 5.0, 200.0, 1.0);

        this.showEntitySetting = new Setting("ShowEntity", this, true);

        this.disableOnDeathSetting = new Setting("DisableOnDeath", this, true);

        this.onDamageSetting = new Setting("OnDamage", this, BlinkOnDamageMode.STAY);

        this.ghostPlayer = null;

        this.bufferedPackets = new ConcurrentLinkedQueue<>();

        this.trailPositions = new ArrayList<>();

        this.pulseTimer = new ElapsedTimer();

        this.maxTrailPoints = 1000;
    }

    public void onDisable() {

        super.onDisable();

        this.flushAndRemoveGhost();
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.disableOnDeathSetting.asBoolean()
                && minecraft.player != null
                && (minecraft.player.isDead || minecraft.player.getHealth() <= 0.0F)) {

            this.restartOrDisable(false, true);
        }

        if (this.intermittentSetting.asBoolean()) {

            long delay = (long) this.delaySetting.asDouble();

            if (this.pulseTimer.elapsedAndReset(delay)) {

                this.restartOrDisable(true, false);

                this.pulseTimer.reset();
            }
        }
    }

    @Subscribe(mc = Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {

        GlStateManager.pushMatrix();

        minecraft.entityRenderer.disableLightmap();

        GlStateManager.enableBlend();

        GlStateManager.disableTexture2D();

        GlStateManager.disableDepth();

        GlStateManager.depthMask(false);

        GlStateManager.blendFunc(770, 771);

        GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);

        GlStateManager.translate(
                -minecraft.getRenderManager().getRenderOriginX(),
                -minecraft.getRenderManager().getRenderOriginY(),
                -minecraft.getRenderManager().getRenderOriginZ());

        GlStateManager.glLineWidth(2.0F);

        GlStateManager.ab(3);

        for (double[] adouble : this.trailPositions) {

            GlStateManager.vertex3d(adouble[0], adouble[1], adouble[2]);
        }

        GlStateManager.vertex3d(
                minecraft.getRenderManager().getRenderOriginX(),
                minecraft.getRenderManager().getRenderOriginY() - 1.722,
                minecraft.getRenderManager().getRenderOriginZ());

        GlStateManager.mO();

        GlStateManager.depthMask(true);

        GlStateManager.enableDepth();

        GlStateManager.enableTexture2D();

        minecraft.entityRenderer.enableLightmap();

        GlStateManager.popMatrix();
    }


    private boolean shouldBufferPacket(Packet packet) {

        return packet instanceof PlayerMovementPacket
                || packet instanceof C0BPacketEntityAction
                || packet instanceof UseEntityPacket
                || packet instanceof UseItemPacket
                || packet instanceof C00PacketKeepAlive
                || packet instanceof C0APacketAnimation;
    }

    public void restartOrDisable(boolean restart, boolean disable) {

        if (disable) {

            this.toggle();

        } else {

            if (restart) {

                this.flushAndRemoveGhost();

                this.captureOriginAndCreateGhost();
            }
        }
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            if (this.bufferedPackets.isEmpty()) {

                this.restartOrDisable(true, false);
            }

            if (minecraft.player.getDistance(this.originX, this.originY, this.originZ)
                    > this.maxDistanceSetting.asDouble()) {

                this.restartOrDisable(true, false);
            }

            double[] adouble =
                    new double[] {
                        minecraft.player.posX,
                        minecraft.player.getEntityBoundingBox().minY,
                        minecraft.player.posZ
                    };

            double d0 = 0.0;

            int i = 0;

            while (i < this.lastTrailPosition.length) {

                d0 += Math.pow(adouble[i] - this.lastTrailPosition[i], 2.0);

                i++;
            }

            if (Math.sqrt(d0) > 0.5) {

                this.trailPositions.add(adouble);

                this.lastTrailPosition = adouble;

                if (this.trailPositions.size() > this.maxTrailPoints) {

                    this.trailPositions.remove(0);
                }
            }
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.SEND) {

            Packet packet = event.getPacket();

            if (this.shouldBufferPacket(packet)) {

                this.bufferPacketIfGrounded(packet);

                event.cancel();
            }
        }
    }

    private void bufferPacketIfGrounded(Packet packet) {

        if (MovementUtils.hasMoved(minecraft.player, 0.0)) {

            this.bufferedPackets.add(packet);
        }
    }

    public void onEnable() {

        super.onEnable();

        this.captureOriginAndCreateGhost();
    }
}
