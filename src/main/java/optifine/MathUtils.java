package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import remix.util.time.ElapsedTimer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class MathUtils {
    public static Random random;

    private static MinecraftClient uP;

    public static double roundToStep(double d0, double d1) {
        return Math.round(d0 / d1) * d1;
    }

    public static double method_0fab0c79f7499fe6b457(Vec3 vec3) {
        return uP.player.getDistanceSq(vec3.x, vec3.y, vec3.z);
    }

    public static double getDistanceSq(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        double d6 = d0 - d3;
        double d7 = d1 - d4;
        double d8 = d2 - d5;
        return MathHelper.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
    }

    public static String formatNumeric(double d0) {
        return d0 == (long) d0 ? String.format("%d", (long) d0) : String.format("%s", d0);
    }

    public static double stringSimilarity(String first, String second) {
        int longestLength = Math.max(first.length(), second.length());
        if (longestLength == 0) {
            return 1.0;
        }
        int distance = first.length() >= second.length() ? caseInsensitiveEditDistance(first, second) : caseInsensitiveEditDistance(second, first);
        return (double) (longestLength - distance) / longestLength;
    }

    public static float getRandomFloat(float f, float f1) {
        return f + random.nextFloat() * (f1 - f);
    }

    public static double fh() {
        return random.nextDouble();
    }

    public static double round(double d0, int i) {
        return i < 0 ? d0 : new BigDecimal(d0).setScale(i, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean isHovered(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        return d4 >= d0 && d5 > d1 && d4 <= d0 + d2 && d5 <= d1 + d3;
    }

    public static double roundToDisplay(double d0) {
        return Double.parseDouble(formatNumeric(d0));
    }

    public static double square(double d0) {
        return d0 * d0;
    }

    public static double getRandomDouble(double d0, double d1) {
        return d0 + random.nextFloat() * (d1 - d0);
    }

    public static int caseInsensitiveEditDistance(String first, String second) {
        String left = first.toLowerCase();
        String right = second.toLowerCase();
        int[] costs = new int[right.length() + 1];
        ElapsedTimer.fM();

        for (int column = 0; column < costs.length; column++) {
            costs[column] = column;
        }
        for (int row = 1; row <= left.length(); row++) {
            int previousDiagonal = costs[0];
            costs[0] = row;
            for (int column = 1; column < costs.length; column++) {
                int previousRowCost = costs[column];
                costs[column] = left.charAt(row - 1) == right.charAt(column - 1)
                        ? previousDiagonal
                        : Math.min(Math.min(costs[column - 1], previousRowCost), previousDiagonal) + 1;
                previousDiagonal = previousRowCost;
            }
        }
        return costs[right.length()];
    }

    // region Static initialization and string tables
    static {
        random = ThreadLocalRandom.current();
        uP = MinecraftClient.getMinecraft();
    }

    // endregion

    public static float aN() {
        return random.nextFloat();
    }

    public static double getDistanceToPlayer(Entity entity) {
        double posX = entity.posX;
        ElapsedTimer.fM();

        double posY = entity.posY;
        double posZ = entity.posZ;

        if (posY > -0.2) {
            posY = entity.posY - (uP.player.posY + 1.0);

            return posX * posX + posY * posY + posZ * posZ;
        }

        if (posY < 0.3) {
            posY = entity.posY + 1.0 - uP.player.posY;
        }

        return posX * posX + posY * posY + posZ * posZ;
    }
}
