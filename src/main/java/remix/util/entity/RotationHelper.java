package remix.util.entity;

import remix.util.movement.MovementPhysicsHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationHelper {
    private static final MinecraftClient ec = MinecraftClient.getMinecraft();

    public static float yawToEntity(Entity entity) {
        LocalPlayer player = ec.player;
        double deltaPosX = entity.posX - player.posX;
        double deltaPosZ = entity.posZ - player.posZ;
        return (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
    }

    public static float[] projectileRotations(
            LivingEntity livingEntity,
            double d0,
            boolean flag,
            float[] afloat,
            double d1,
            double d2) {
        Vec3 vec3 =
                flag
                        ? MovementPhysicsHelper.interpolateEntityPosition(livingEntity, 3.0F)
                        : livingEntity.getPositionVector();
        LocalPlayer player = ec.player;
        double d3 =
                vec3.y + livingEntity.getEyeHeight() / 2.0F - (player.posY + player.getEyeHeight());
        double d4 = vec3.x - player.posX;
        double d5 = vec3.z - player.posZ;
        double d6 = MathHelper.sqrt(d4 * d4 + d5 * d5);
        float f = (float) (Math.atan2(d5, d4) * 180.0 / Math.PI) - 90.0F;
        float f1 = (float) (-Math.toDegrees(projectileLaunchAngle(d1, d2, d6, d3)));
        float f2 = MathHelper.wrapAngleTo180(f - afloat[0]);
        float f3 = MathHelper.wrapAngleTo180(f1 - afloat[1]);
        f = (float) (afloat[0] + f2 / 100.0F * (100.0 - d0));
        f1 = (float) (afloat[1] + f3 / 100.0F * (100.0 - d0));
        return new float[] {f, f1};
    }

    public static float[] rotationsToPoint(Vec3 vec3) {
        LocalPlayer player = ec.player;
        float[] afloat = new float[] {player.rotationYaw, player.rotationPitch};
        return rotationsTo(vec3, afloat, 0.0F);
    }

    private static float projectileLaunchAngle(double d0, double d1, double d2, double d3) {
        double d4 = 2.0 * d3 * (d0 * d0);
        double d5 = d1 * (d2 * d2);
        double d6 = d1 * (d5 + d4);
        double d7 = d0 * d0 * d0 * d0 - d6;
        double d8 = MathHelper.sqrt(d7);
        double d9 = d0 * d0 + d8;
        double d10 = d0 * d0 - d8;
        double d11 = Math.atan2(d9, d1 * d2);
        double d12 = Math.atan2(d10, d1 * d2);
        return (float) Math.min(d11, d12);
    }

    public static float[] rotationsTo(Vec3 vec3, float[] afloat, float f) {
        LocalPlayer player = ec.player;
        return rotationsBetween(player.getPositionEyes(1.0F), vec3, afloat, f);
    }

    public static float[] rotationsBetween(Vec3 vec3, Vec3 vec3Value, float[] afloat, float f) {
        double d0 = vec3Value.x - vec3.x;
        double d1 = vec3Value.y - vec3.y;
        double d2 = vec3Value.z - vec3.z;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f1 = (float) Math.toDegrees(Math.atan2(d2, d0)) - 90.0F;
        float f2 = (float) (-Math.toDegrees(Math.atan2(d1, d3)));
        float f3 = MathHelper.wrapAngleTo180(f1 - afloat[0]);
        float f4 = MathHelper.clamp(f2 - afloat[1], -90.0F, 90.0F);
        f1 = afloat[0] + f3 / 100.0F * (100.0F - f);
        f2 = afloat[1] + f4 / 100.0F * (100.0F - f);
        float f5 = ec.gameSettings.mouseSensitivity;
        float f6 = f5 * 0.6F + 0.2F;
        float f7 = f6 * f6 * f6 * 8.0F;
        double d4 = f7 * 0.15F;
        f1 = (float) (f1 - f1 % d4);
        f2 = (float) (f2 - f2 % d4);
        return new float[] {f1, f2};
    }

    public static float angleDifference(float f, float f1) {
        float f2 = Math.abs(f % 360.0F - f1 % 360.0F);
        return Math.min(f2, 360.0F - f2);
    }

    public static float[] rotationsBetweenUnsmoothed(Vec3 vec3, Vec3 vec3Value) {
        double d0 = vec3.x - vec3Value.x;
        double d1 = vec3.y - vec3Value.y;
        double d2 = vec3.z - vec3Value.z;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (Math.atan2(d2, d0) * 180.0 / Math.PI) - 90.0F;
        float f1 = (float) (-(Math.atan2(d1, d3) * 180.0 / Math.PI));
        return new float[] {f, f1};
    }
}
