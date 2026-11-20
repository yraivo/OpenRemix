package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RandomPositionGenerator {
    private static Vec3 staticVector = new Vec3(0.0, 0.0, 0.0);

    public static Vec3 findRandomTargetBlockTowards(
            EntityCreature entityCreature, int p171_1, int p171_2, Vec3 vec3) {
        net.minecraft.entity.ai.RandomPositionGenerator.staticVector =
                vec3.subtract(entityCreature.posX, entityCreature.posY, entityCreature.posZ);
        return net.minecraft.entity.ai.RandomPositionGenerator.findRandomTargetBlock(
                entityCreature, p171_1, p171_2, net.minecraft.entity.ai.RandomPositionGenerator.staticVector);
    }

    private static Vec3 findRandomTargetBlock(
            EntityCreature entityCreature, int i, int j, Vec3 vec3) {
        String text;
        Random random;
        byte b0;
        int k;
        int l;
        int i1;
        float f;
        int j1;
        {
            random = entityCreature.getRNG();
            String textValue = EntityAIBase.cJ();
            b0 = 0;
            k = 0;
            l = 0;
            i1 = 0;
            f = -99999.0F;
            text = textValue;
            if (entityCreature.hasHome()) {
                double d0 =
                        entityCreature
                                        .getHomePosition()
                                        .distanceSq(
                                                MathHelper.floor(entityCreature.posX),
                                                MathHelper.floor(entityCreature.posY),
                                                MathHelper.floor(entityCreature.posZ))
                                + 4.0;
                double d1 = entityCreature.getMaximumHomeDistance() + i;
                j1 = d0 < d1 * d1 ? 1 : 0;
                if (!(text != null)) {

                    j1 = 0;
                }
            } else {

                j1 = 0;
            }
        }

        int i2 = 0;

        while (i2 < 10) {
            int k1 = random.nextInt(2 * i + 1) - i;
            int j2 = random.nextInt(2 * j + 1) - j;
            int l1 = random.nextInt(2 * i + 1) - i;
            if (vec3 == null || k1 * vec3.x + l1 * vec3.z >= 0.0) {
                label46:
                if (entityCreature.hasHome() && i > 1) {
                    BlockPos blockPos;
                    {
                        blockPos = entityCreature.getHomePosition();
                        if (entityCreature.posX > blockPos.getX()) {
                            k1 -= random.nextInt(i / 2);
                            if (!(text != null)) {

                                k1 += random.nextInt(i / 2);
                            }
                        } else {

                            k1 += random.nextInt(i / 2);
                        }
                    }

                    if (entityCreature.posZ > blockPos.getZ()) {
                        l1 -= random.nextInt(i / 2);
                        if (text != null) {
                            break label46;
                        }
                    }

                    l1 += random.nextInt(i / 2);
                }

                k1 += MathHelper.floor(entityCreature.posX);
                j2 += MathHelper.floor(entityCreature.posY);
                l1 += MathHelper.floor(entityCreature.posZ);
                BlockPos blockPosValue = new BlockPos(k1, j2, l1);
                if (j1 == 0 || entityCreature.isWithinHomeDistanceFromPosition(blockPosValue)) {
                    float blockPathWeight = entityCreature.getBlockPathWeight(blockPosValue);
                    if (blockPathWeight > f) {
                        f = blockPathWeight;
                        k = k1;
                        l = j2;
                        i1 = l1;
                        b0 = 1;
                    }
                }
            }

            i2++;
            if (text == null) {
                break;
            }
        }

        return b0 != 0 ? new Vec3(k, l, i1) : null;
    }

    public static Vec3 findRandomTarget(EntityCreature entityCreature, int p171_1, int p171_2) {
        return net.minecraft.entity.ai.RandomPositionGenerator.findRandomTargetBlock(
                entityCreature, p171_1, p171_2, (net.minecraft.util.Vec3) null);
    }

    public static Vec3 findRandomTargetBlockAwayFrom(
            EntityCreature entityCreature, int p171_1, int p171_2, Vec3 vec3) {
        net.minecraft.entity.ai.RandomPositionGenerator.staticVector =
                new net.minecraft.util.Vec3(entityCreature.posX, entityCreature.posY, entityCreature.posZ)
                        .subtract(vec3);
        return net.minecraft.entity.ai.RandomPositionGenerator.findRandomTargetBlock(
                entityCreature, p171_1, p171_2, net.minecraft.entity.ai.RandomPositionGenerator.staticVector);
    }
}
