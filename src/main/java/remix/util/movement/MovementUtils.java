package remix.util.movement;

import remix.util.time.ElapsedTimer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import remix.event.MoveEvent;

public final class MovementUtils {
    private static final double ix = 1.3;
    public static final double iz = 0.4751131221719457;
    private static final double ul = 0.7239819004524887;
    public static final double iw = 2.5;
    private static final double um = 0.221;
    private static final MinecraftClient minecraft = MinecraftClient.getMinecraft();
    private static final double rd = 0.5203619909502263;
    private static final double un = 0.5882352941176471;

    public static boolean hasMoved(Entity entity, double p171_1) {
        return java.lang.Math.abs(entity.posX - entity.prevPosX) > p171_1
                || java.lang.Math.abs(entity.posY - entity.prevPosY) > p171_1
                || java.lang.Math.abs(entity.posZ - entity.prevPosZ) > p171_1;
    }

    public static double getBaseMoveSpeed(double d0) {
        int[] aint2 = ElapsedTimer.fM();
        LocalPlayer player = remix.util.movement.MovementUtils.minecraft.player;
        int[] aint = aint2;
        ClientWorld world = remix.util.movement.MovementUtils.minecraft.world;
        BoundingBox boundingBox = player.getEntityBoundingBox();

        for (double d1 = 0.0; d1 < d0; d1 += 0.01) {
            int[] aint1 = new int[] {-2, -1, 0, 1, 2};
            int i = 0;

            for (int j = aint1.length; i < j; i++) {
                int k = aint1[i];
                if (world.getCollidingBoundingBoxes(
                                                player,
                                                boundingBox.offset(
                                                        player.motionX * k, d1, player.motionZ * k))
                                        .size()
                                > 0
                        || aint == null) {
                    return d1 - 0.01;
                }
                break;
            }

            return d0;
        }

        return d0;
    }

    public static double getFrictionSpeed() {
        LocalPlayer player = remix.util.movement.MovementUtils.minecraft.player;
        PotionEffect activePotionEffect = remix.util.movement.MovementUtils.minecraft.player.getActivePotionEffect(Potion.MOVE_SPEED);
        BlockPos blockPos =
                new BlockPos(
                        MathHelper.floor(player.posX),
                        MathHelper.floor(player.getEntityBoundingBox().minY) - 1,
                        MathHelper.floor(player.posZ));
        double d0 = 0.221;
        if (player.isInWeb) {
            d0 *= 0.4751131221719457;
        }

        if (player.isInWater()) {
            d0 *= 0.5203619909502263;
        }

        if (player.isBlocking()) {
            d0 *= 0.7239819004524887;
        }

        if (player.isSneaking()) {
            d0 *= 0.5882352941176471;
        }

        if (remix.util.movement.MovementUtils.minecraft.player.isSprinting()) {
            d0 *= 1.3;
        }

        if (activePotionEffect != null) {
            d0 *= 1.0 + 0.2 * (activePotionEffect.getAmplifier() + 1);
        }

        if (remix.util.movement.MovementUtils.minecraft.world.getBlockState(blockPos).getBlock().slipperiness == 0.98F) {
            d0 *= 2.5;
        }

        return d0;
    }

    public static void setSpeed(double d0) {
        LocalPlayer player = remix.util.movement.MovementUtils.minecraft.player;
        double d1 = MovementPhysicsHelper.getMoveDirectionYaw() * (float) (Math.PI / 180.0);
        player.motionX = -(Math.sin(d1) * d0);
        player.motionZ = Math.cos(d1) * d0;
    }

    public static void setHorizontalMotion(MoveEvent moveEvent, double d0) {
        float f = MovementPhysicsHelper.getMoveDirectionYaw() * (float) (Math.PI / 180.0);
        moveEvent.setX(-(MathHelper.sin(f) * d0));
        moveEvent.setZ(MathHelper.cos(f) * d0);
    }

    public static double minimumSpeedCandidate(double first, double second, double third) {
        double best = Double.compare(first, second) <= 0 ? first : second;
        return Double.compare(best, third) <= 0 ? best : third;
    }

    public static double maximumSpeedCandidate(double first, double second, double third) {
        double best = Double.compare(first, second) >= 0 ? first : second;
        return Double.compare(best, third) >= 0 ? best : third;
    }

    public static double getBaseMoveSpeed() {
        return remix.util.movement.MovementUtils.minecraft.player.isSprinting() ? 0.2873F : 0.221F;
    }

    public static double getSpeedWithPotion() {
        double d0 = getBaseMoveSpeed();
        PotionEffect activePotionEffect = remix.util.movement.MovementUtils.minecraft.player.getActivePotionEffect(Potion.MOVE_SPEED);
        if (activePotionEffect != null) {
            d0 *= 1.0 + 0.2 * (activePotionEffect.getAmplifier() + 1);
        }

        return d0;
    }

    public static boolean isMoving() {
        net.minecraft.client.entity.LocalPlayer player = remix.util.movement.MovementUtils.minecraft.player;
        return player.moveForward != 0.0F || player.moveStrafing != 0.0F;
    }

    public static double getJumpMotion() {
        double d0 = 0.42F;
        PotionEffect activePotionEffect = remix.util.movement.MovementUtils.minecraft.player.getActivePotionEffect(Potion.JUMP);
        if (activePotionEffect != null) {
            d0 += (activePotionEffect.getAmplifier() + 1) * 0.1F;
        }

        return d0;
    }
}
