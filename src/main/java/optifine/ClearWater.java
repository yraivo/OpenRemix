package optifine;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ClearWater {

    public static void updateWaterOpacity(GameSettings gameSettings, World world) {
        int i = ReflectorMethod.cR();
        if (gameSettings != null) {
            byte b0 = 3;
            if (gameSettings.ofClearWater) {
                b0 = 1;
            }

            BlockLeavesBase.setLightOpacity(Blocks.water, b0);
            BlockLeavesBase.setLightOpacity(Blocks.flowing_water, b0);
        }

        if (world != null) {
            IChunkProvider chunkProvider = world.getChunkProvider();
            if (chunkProvider != null) {
                Entity entity = OptiFineConfig.getMinecraft().getRenderViewEntity();
                if (entity != null) {
                    int j = (int) entity.posX / 16;
                    int k = (int) entity.posZ / 16;
                    int l = j - 512;
                    int i1 = j + 512;
                    int j1 = k - 512;
                    int k1 = k + 512;
                    int l1 = 0;
                    int i2 = l;

                    while (i2 < i1) {
                        int j2 = j1;

                        while (j2 < k1) {
                            if (chunkProvider.chunkExists(i2, j2)) {
                                Chunk chunk = chunkProvider.provideChunk(i2, j2);
                                if (chunk != null && !(chunk instanceof EmptyChunk)) {
                                    int k2 = i2 << 4;
                                    int l2 = j2 << 4;
                                    int i3 = k2 + 16;
                                    int j3 = l2 + 16;
                                    BlockPosM blockPosM = new BlockPosM(0, 0, 0);
                                    BlockPosM blockPosMValue = new BlockPosM(0, 0, 0);
                                    int k3 = k2;

                                    while (k3 < i3) {
                                        int l3 = l2;

                                        while (l3 < j3) {
                                            blockPosM.setXyz(k3, 0, l3);
                                            BlockPos blockPos =
                                                    world.getPrecipitationHeight(blockPosM);
                                            int i4 = 0;

                                            label73:
                                            {
                                                do {
                                                    if (i4 >= blockPos.getY()) {
                                                        break label73;
                                                    }

                                                    blockPosMValue.setXyz(k3, i4, l3);
                                                    BlockState blockState =
                                                            world.getBlockState(blockPosMValue);
                                                    if (blockState.getBlock().getMaterial()
                                                            == Material.water) {
                                                        break;
                                                    }

                                                    i4++;
                                                } while (i != 0);

                                                world.markBlocksDirtyVertical(
                                                        k3,
                                                        l3,
                                                        blockPosMValue.getY(),
                                                        blockPos.getY());
                                                l1++;
                                            }

                                            l3++;
                                            if (i == 0) {
                                                break;
                                            }
                                        }

                                        k3++;
                                        if (i == 0) {
                                            break;
                                        }
                                    }
                                }
                            }

                            j2++;
                            if (i == 0) {
                                break;
                            }
                        }

                        i2++;
                        if (i == 0) {
                            break;
                        }
                    }

                    if (l1 > 0) {
                        String text = "server";
                        if (OptiFineConfig.isMinecraftThread()) {
                            text = "client";
                        }

                        OptiFineConfig.dbg("ClearWater (" + text + ") relighted " + l1 + " chunks");
                    }
                }
            }
        }
    }


}
