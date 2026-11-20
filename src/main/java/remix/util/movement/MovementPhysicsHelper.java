package remix.util.movement;

import remix.util.time.ElapsedTimer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Arrays;
import java.util.List;

public final class MovementPhysicsHelper {
    private static final MinecraftClient ec = MinecraftClient.getMinecraft();
    public static final List<Double> GROUND_OFFSETS =
            Arrays.asList(
                    0.0, 0.015625, 0.0625, 0.125, 0.1875, 0.25, 0.3125, 0.375, 0.5, 0.625, 0.75,
                    0.875);

    public static boolean isCollidingWithBlocks(BoundingBox boundingBox) {
        ElapsedTimer.fM();

        ClientWorld world = ec.world;
        int minX = MathHelper.floor(boundingBox.minX);
        int maxX = MathHelper.floor(boundingBox.maxX);
        int minY = MathHelper.floor(boundingBox.minY);
        int maxY = MathHelper.floor(boundingBox.maxY);
        int minZ = MathHelper.floor(boundingBox.minZ);
        int maxZ = MathHelper.floor(boundingBox.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    Block block = world.getBlockState(blockPos).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        BoundingBox blockBox =
                                block.getCollisionBoundingBox(
                                        world, blockPos, world.getBlockState(blockPos));
                        if (blockBox != null && boundingBox.intersectsWith(blockBox)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static double getHorizontalSpeed() {
        LocalPlayer player = ec.player;
        double motionX = player.motionX;
        double motionZ = player.motionZ;
        return MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
    }

    private static Vec3 interpolatePosition(Vec3 vec3, Vec3 vec3Value, float p171_2) {
        double d0 = vec3.x + (vec3.x - vec3Value.x) * p171_2;
        double d1 = vec3.y + (vec3.y - vec3Value.y) * p171_2;
        double d2 = vec3.z + (vec3.z - vec3Value.z) * p171_2;
        return new net.minecraft.util.Vec3(d0, d1, d2);
    }

    public static float getMaxFallDistanceWithJump() {
        LocalPlayer player = ec.player;
        PotionEffect activePotionEffect = player.getActivePotionEffect(Potion.JUMP);
        int amplifier = activePotionEffect != null ? activePotionEffect.getAmplifier() + 1 : 0;
        return player.getMaxFallHeight() + amplifier;
    }

    public static Vec3 interpolateEntityPosition(Entity entity, float partialTicks) {
        return interpolatePosition(
                new Vec3(entity.posX, entity.posY, entity.posZ),
                new Vec3(entity.prevPosX, entity.prevPosY, entity.prevPosZ),
                partialTicks);
    }

    public static boolean predictHorizontalCollision(int ticks) {
        LocalPlayer player = ec.player;
        ClientWorld world = ec.world;
        BoundingBox boundingBox = player.getEntityBoundingBox();
        ElapsedTimer.fM();

        for (int step = 0; step < ticks; step++) {
            BoundingBox predictedBox = boundingBox.addCoord(player.motionX * step, 0.0, player.motionZ * step);
            if (!world.getCollidingBoundingBoxes(player, predictedBox).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static float getMoveDirectionYaw() {
        ElapsedTimer.fM();

        LocalPlayer player = ec.player;

        float forward = player.moveForward;
        float strafing = player.moveStrafing;
        float offset = forward == 0.0F ? 90.0F : (forward < 0.0F ? -45.0F : 45.0F);
        float yaw = player.rotationYawHead;
        if (forward < 0.0F) {
            yaw += 180.0F;
        }

        if (strafing < 0.0F) {
            yaw += offset;
            return yaw;
        }

        if (strafing > 0.0F) {
            yaw -= offset;
        }

        return yaw;
    }

    public static Vec3 offsetDirectional(Vec3 vec3, double distance) {
        if (distance == 0.0) {
            return vec3;
        }

        float yaw = getMoveDirectionYaw();
        double yawRadians = (yaw + 90.0F) * Math.PI / 180.0;
        double offsetX = Math.cos(yawRadians) * distance;
        double offsetZ = Math.sin(yawRadians) * distance;
        return vec3.addVector(offsetX, 0.0, offsetZ);
    }
}
