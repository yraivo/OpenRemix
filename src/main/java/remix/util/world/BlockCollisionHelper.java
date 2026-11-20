package remix.util.world;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class BlockCollisionHelper {
    private static final MinecraftClient ec = MinecraftClient.getMinecraft();
    private static String[] packedTextFragments;

    public static boolean isCollidingWithBlocks(
            PlayerEntity playerEntity, double d0, double d1) {
        BoundingBox boundingBox = playerEntity.getEntityBoundingBox().expand(d0, d1, d0);
        String text = LivingEntity.Kl();
        List list = getCollisionBoxes(playerEntity, boundingBox);
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_1046 = (list).iterator();
                (__recoveryIterator171_1046).hasNext(); ) {
            BoundingBox boundingBoxValue = (BoundingBox) (__recoveryIterator171_1046).next();
            if (boundingBox.minY <= boundingBoxValue.maxY) {
                return true;
            }

            if (textValue == null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            LivingEntity.b(packedTextFragments[0]);
        }

        return false;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.world.BlockCollisionHelper.packedTextFragments[0] = new String("HYjVEc");
    }

    // endregion

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public static List<BoundingBox> getCollisionBoxes(
            Entity entity, BoundingBox boundingBox) {
        ArrayList arrayList = Lists.newArrayList();
        String text = LivingEntity.Kl();
        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        String textValue = text;
        int l = MathHelper.floor(boundingBox.maxY + 1.0);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = i1;

            while (true) {
                if (l1 < j1) {
                    if (ec.world.isBlockLoaded(new BlockPos(k1, 64, l1))) {
                        int i2 = k - 1;

                        while (i2 < l) {
                            BlockPos blockPos;
                            boolean flag1;
                            label51:
                            {
                                blockPos = new BlockPos(k1, i2, l1);
                                boolean flag = entity.isOutsideBorder();
                                flag1 =
                                        ec.world.isInsideBorder(
                                                ec.world.getWorldBorder(), entity);
                                if (flag && flag1) {
                                    entity.setOutsideBorder(false);
                                    if (textValue != null) {
                                        break label51;
                                    }

                                    Entity.setLegacyGlobalFlowMarker(new String[1]);
                                }

                                if (!flag && !flag1) {
                                    entity.setOutsideBorder(true);
                                }
                            }

                            BlockState blockState;
                            {
                                if (!ec.world.getWorldBorder().contains(blockPos) && flag1) {
                                    blockState = Blocks.stone.getDefaultState();
                                    if (!(textValue != null)) {

                                        blockState = ec.world.getBlockState(blockPos);
                                    }
                                } else {

                                    blockState = ec.world.getBlockState(blockPos);
                                }
                            }

                            blockState
                                    .getBlock()
                                    .addCollisionBoxesToList(
                                            ec.world,
                                            blockPos,
                                            blockState,
                                            boundingBox,
                                            arrayList,
                                            entity);
                            i2++;
                            if (textValue == null) {
                                break;
                            }
                        }
                    }

                    l1++;
                    if (textValue != null) {
                        continue;
                    }
                }

                k1++;
                if (textValue == null) {
                    return arrayList;
                }
                break;
            }
        }

        return arrayList;
    }
}
