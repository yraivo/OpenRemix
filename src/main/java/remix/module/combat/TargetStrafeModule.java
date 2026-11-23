package remix.module.combat;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render3DEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2d;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/**
 * Computes horizontal movement around Aura's current target; changes strafe direction when
 * the next offset collides or lacks ground. The render handler draws the configured target radius.
 */
public final class TargetStrafeModule extends ModuleBase {

    private static final double[][] UNIT_RING = createUnitRing();

    private static double[][] createUnitRing() {
        double[][] points = new double[361][2];
        float step = (float) ((Math.PI * 2) / 360.0);
        for (int i = 0; i <= 360; i++) {
            points[i][0] = Math.cos(i * step);
            points[i][1] = Math.sin(i * step);
        }
        return points;
    }

    private Setting renderSetting;
    private final Setting rangeSetting = new Setting("Range", this, 2.0, 0.5, 6.0, 0.1);
    private int strafeDirection;

    private final Setting controllableSetting = new Setting("Controllable", this, true);
    private final Setting spacebarSetting = new Setting("Spacebar", this, true);

    private LivingEntity target;

    public TargetStrafeModule() {
        super("TargetStrafe", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));
        this.renderSetting = new Setting("Render", this, true);
        this.strafeDirection = 1;
        this.setName("Target Strafe");
    }

    private void horizontalMotionForYaw(float yaw, double speed, Vector2d result) {
        double yawRadians = yaw * (float) (Math.PI / 180.0);
        result.x = -(Math.sin(yawRadians) * speed);
        result.y = Math.cos(yawRadians) * speed;
    }

    @Subscribe(mc = MoveEvent.class, priority = SubscriberPriority.HIGH)
    public void onMove(MoveEvent event) {
        if (event.getPhase() != MotionPhase.PRE) {
            return;
        }

        AuraModule aura = ClientServices.moduleManager.aura;
        this.target = aura.currentTarget;
        if (!aura.isEnabled()
                || this.target == null
                || (this.spacebarSetting.asBoolean()
                        && !minecraft.gameSettings.keyBindJump.isKeyDown())) {
            return;
        }

        double speed = MathHelper.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
        double radius = this.rangeSetting.asDouble();
        float angleToTarget =
                (float)
                                (Math.atan2(
                                                minecraft.player.posZ - this.target.posZ,
                                                minecraft.player.posX - this.target.posX)
                                        * 180.0
                                        / Math.PI)
                        - 90.0F;
        double relX = 0.0;
        double relZ = 0.0;

        if (this.controllableSetting.asBoolean()) {
            if (minecraft.gameSettings.keyBindLeft.isKeyDown()) {
                this.strafeDirection = -1;
            } else if (minecraft.gameSettings.keyBindRight.isKeyDown()) {
                this.strafeDirection = 1;
            }
        }

        Vector2d motion = new Vector2d();
        int searchSteps = 0;
        while (searchSteps < 180 && MathHelper.sqrt(relX * relX + relZ * relZ) < speed) {
            double angleRad = (angleToTarget + 90.0F) * Math.PI / 180.0;
            double orbitX = Math.cos(angleRad) * radius;
            double orbitZ = Math.sin(angleRad) * radius;
            relX = orbitX + this.target.posX - minecraft.player.posX;
            relZ = orbitZ + this.target.posZ - minecraft.player.posZ;
            float moveYaw = (float) (Math.atan2(relZ, relX) * 180.0 / Math.PI) - 90.0F;
            this.horizontalMotionForYaw(moveYaw, speed, motion);

            boolean collides =
                    !minecraft
                            .world
                            .getCollidingBoundingBoxes(
                                    minecraft.player,
                                    minecraft
                                            .player
                                            .getEntityBoundingBox()
                                            .offset(motion.x, 0.0, motion.y))
                            .isEmpty();
            boolean emptyBelow =
                    minecraft
                            .world
                            .getCollidingBoundingBoxes(
                                    minecraft.player,
                                    minecraft
                                            .player
                                            .getEntityBoundingBox()
                                            .offsetBounds(
                                                    motion.x, -8.0, motion.y,
                                                    motion.x, 0.0, motion.y))
                            .isEmpty();

            if (collides || emptyBelow) {
                this.strafeDirection = -this.strafeDirection;
            }

            angleToTarget += this.strafeDirection;
            searchSteps++;
        }

        float finalYaw = (float) (Math.atan2(relZ, relX) * 180.0 / Math.PI) - 90.0F;
        this.horizontalMotionForYaw(finalYaw, speed, motion);
        boolean emptyBelow =
                minecraft
                        .world
                        .getCollidingBoundingBoxes(
                                minecraft.player,
                                minecraft
                                        .player
                                        .getEntityBoundingBox()
                                        .offsetBounds(
                                                motion.x, -8.0, motion.y,
                                                motion.x, 0.0, motion.y))
                        .isEmpty();
        if (emptyBelow) {
            finalYaw -= 180.0F;
        }

        double finalYawRad = finalYaw * (float) (Math.PI / 180.0);
        event.setX(-(Math.sin(finalYawRad) * speed));
        event.setZ(Math.cos(finalYawRad) * speed);
    }

    @Subscribe(mc = Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {
        if (event.getPhase() != MotionPhase.PRE || !this.renderSetting.asBoolean()) {
            return;
        }

        AuraModule aura = ClientServices.moduleManager.aura;
        this.target = aura.currentTarget;
        if (!aura.isEnabled()
                || this.target == null
                || (this.spacebarSetting.asBoolean()
                        && !minecraft.gameSettings.keyBindJump.isKeyDown())) {
            return;
        }

        Render2D3DUtils.beginUnoccludedWorldOverlay();
        Render2D3DUtils.glColor(((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor());
        GlStateManager.translate(
                -minecraft.getRenderManager().getRenderPosX(),
                -minecraft.getRenderManager().getRenderPosY(),
                -minecraft.getRenderManager().getRenderPosZ());
        GlStateManager.glLineWidth(1.0F);
        GlStateManager.ab(3);
        double radius = this.rangeSetting.asDouble();
        Vec3 interpolatedPos =
                MovementPhysicsHelper.interpolateEntityPosition(
                        this.target, minecraft.timer.renderPartialTicks);

        for (int i = 0; i < UNIT_RING.length; i++) {
            double circleX = UNIT_RING[i][0] * radius;
            double circleZ = UNIT_RING[i][1] * radius;
            GlStateManager.vertex3d(
                    interpolatedPos.x + circleX,
                    interpolatedPos.y,
                    interpolatedPos.z + circleZ);
        }

        GlStateManager.mO();
        Render2D3DUtils.endUnoccludedWorldOverlay();
    }
}
