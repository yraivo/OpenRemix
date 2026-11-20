package remix.util.world;

import remix.util.time.ElapsedTimer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;

public final class LiquidStepHelper {
    private static final MinecraftClient ec = MinecraftClient.getMinecraft();

    public static RayTraceResult method_0c0ae08b75c219c3546e(double d0, float[] afloat) {
        Vec3 positionEyes = ec.player.getPositionEyes(1.0F);
        Vec3 vectorForRotation = ec.player.getVectorForRotation(afloat[1], afloat[0]);
        Vec3 vec3 =
                positionEyes.addVector(
                        vectorForRotation.x * d0,
                        vectorForRotation.y * d0,
                        vectorForRotation.z * d0);
        return ec.world.rayTraceBlocks(positionEyes, vec3, false, false, true);
    }

    public static double cl() {
        int[] aint1 = ElapsedTimer.fM();
        double d0 = 0.0;
        double d1 = Math.round(ec.player.posY);
        int[] aint = aint1;

        while (d1 > 0.0) {
            BlockPos blockPos = new BlockPos(ec.player.posX, d1, ec.player.posZ);
            Block block = ec.world.getBlockState(blockPos).getBlock();
            if (block.getMaterial() != Material.AIR
                    && block.isFullCube()
                    && block.isCollidable()
                    && !(block instanceof BlockAir)) {
                d0 = d1;

                break;
            }

            if (d1 < 0.0 && aint != null) {
                break;
            }

            d1--;
        }

        return ec.player.posY - d0 - 1.0;
    }

    public static Block method_2097da77b8365fb9f687(int i, int j, int k) {
        return ec.world.getBlockState(new BlockPos(i, j, k)).getBlock();
    }

    public static boolean aY() {
        double posX = ec.player.posX;
        double minY = ec.player.getEntityBoundingBox().minY;
        double posZ = ec.player.posZ;
        BlockState blockState = ec.world.getBlockState(new BlockPos(posX, minY, posZ));
        return blockState.getBlock().getMetaFromState(blockState) < 4;
    }
}
