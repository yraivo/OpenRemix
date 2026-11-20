package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Vec3;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.movement.MovementPhysicsHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Tracks accumulated fall distance and the last grounded position; edits or sends ground packets
 * and optionally performs the configured setback when no ground is found below.
 */
public final class NoFallModule extends ModuleBase {

    private Setting setbackDistanceSetting;

    private Setting antiFallSetting;

    public double accumulatedFallDistance;

    private Setting voidCheckSetting;

    private Setting<NoFallSetBackMode> setBackSetting;

    private Vec3 lastGroundPosition;

    private Setting stopMotionSetting;

    private final Setting<NoFallMode> modeSetting = new Setting("Mode", this, NoFallMode.EDIT);





    @Subscribe(mc = MotionEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE && !minecraft.playerController.isInCreativeMode()) {

            LocalPlayer player = minecraft.player;

            if (player.onGround) {

                this.lastGroundPosition = player.getPositionVector();

                this.accumulatedFallDistance = 0.0;
            }

            float f = MovementPhysicsHelper.getMaxFallDistanceWithJump();

            if (player.fallDistance >= f) {

                switch (((NoFallMode) this.modeSetting.getValue())) {
                    case EDIT:
                        event.onGround = true;

                        this.accumulatedFallDistance =
                                this.accumulatedFallDistance + player.fallDistance;

                        player.fallDistance = 0.0F;

                        break;

                    case PACKET:
                        this.sendPacketWithoutEvent(new PlayerMovementPacket(true));

                        this.accumulatedFallDistance =
                                this.accumulatedFallDistance + player.fallDistance;

                        player.fallDistance = 0.0F;

                        break;

                    case HYPIXEL:
                        List list = MovementPhysicsHelper.GROUND_OFFSETS;

                        double d0 = event.getY() % 1.0;

                        list.sort(
                                Comparator.<Double>comparingDouble(
                                        doubleValue -> Math.abs(d0 - doubleValue)));

                        double d1 = event.getY() - d0 + (Double) list.get(0);

                        minecraft.player.setPosition(
                                minecraft.player.posX, d1, minecraft.player.posZ);

                        event.setY(d1);

                        event.onGround = true;

                        this.accumulatedFallDistance =
                                this.accumulatedFallDistance + player.fallDistance;

                        player.fallDistance = 0.0F;
                }
            }

            if (this.antiFallSetting.asBoolean()
                    && !ClientServices.moduleManager.highJump.isEnabled()
                    && this.lastGroundPosition != null) {

                double setbackDistance = this.setbackDistanceSetting.asDouble();

                Vec3 lastGroundPositionValue = this.lastGroundPosition;

                if ((this.accumulatedFallDistance > setbackDistance
                                || player.fallDistance > setbackDistance)
                        && (!this.voidCheckSetting.asBoolean()
                                || this.hasNoGroundBelow(
                                        minecraft
                                                .player
                                                .getEntityBoundingBox()
                                                .expand(
                                                        minecraft.player.motionX,
                                                        0.0,
                                                        minecraft.player.motionZ)))
                        && !ClientServices.moduleManager.flight.isEnabled()) {

                    switch (((NoFallSetBackMode) this.setBackSetting.getValue())) {
                        case LASTPOS:
                            double d5 = lastGroundPositionValue.x;

                            double d2 = lastGroundPositionValue.y;

                            double d3 = lastGroundPositionValue.z;

                            event.setX(d5);

                            event.setY(d2);

                            event.setZ(d3);

                            event.onGround = true;

                            player.setPositionAndUpdate(d5, d2, d3);

                            if (this.stopMotionSetting.asBoolean()) {

                                player.motionZ = player.motionX = 0.0;
                            }

                            return;

                        case FLAG:
                            event.setX(0.0);

                            event.setY(-999.0);

                            event.setZ(0.0);

                            event.setForcePositionAndRotationPacket(true);
                    }
                }
            }
        }
    }

    public NoFallModule() {

        super("NoFall", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));

        this.setBackSetting = new Setting("SetBack", this, NoFallSetBackMode.LASTPOS);

        this.setbackDistanceSetting = new Setting("SetbackDistance", this, 5.0, 1.0, 10.0, 1.0);

        this.antiFallSetting = new Setting("AntiFall", this, false);

        this.stopMotionSetting = new Setting("StopMotion", this, false);

        this.voidCheckSetting = new Setting("VoidCheck", this, false);

        this.setName("No Fall");
    }

    public String getSuffix() {

        return ((NoFallMode) this.modeSetting.getValue()).getDisplayName();
    }


    public boolean hasNoGroundBelow(BoundingBox bounds) {

        ClientWorld world = minecraft.world;

        LocalPlayer player = minecraft.player;

        double depth = 0.0;

        double maximumDepth = player.posY;

        while (depth < maximumDepth) {

            if (!world.getCollidingBoundingBoxes(player, bounds.expand(0.0, -depth, 0.0))
                    .isEmpty()) {

                return false;
            }

            depth += player.height;
        }

        return true;
    }
}
