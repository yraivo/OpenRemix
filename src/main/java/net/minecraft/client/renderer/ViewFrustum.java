package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ViewFrustum {
    protected int countChunksZ;
    protected int countChunksY;
    protected RenderGlobal renderGlobal;
    public RenderChunk[] renderChunks;
    protected World world;
    protected int countChunksX;

    protected void setCountChunksXYZ(int i) {
        int j = i * 2 + 1;
        this.countChunksX = j;
        this.countChunksY = 16;
        this.countChunksZ = j;
    }

    protected void createRenderChunks(IRenderChunkFactory iRenderChunkFactory) {
        int j = this.countChunksX * this.countChunksY * this.countChunksZ;
        int l1 = WorldRenderer.getLegacyRenderFlowMarker();
        this.renderChunks = new RenderChunk[j];
        int k = 0;
        int i = l1;
        int l = 0;

        while (l < this.countChunksX) {
            int i1 = 0;

            while (true) {
                if (i1 < this.countChunksY) {
                    int j1 = 0;

                    while (j1 < this.countChunksZ) {
                        int k1 = (j1 * this.countChunksY + i1) * this.countChunksX + l;
                        BlockPos blockPos = new BlockPos(l * 16, i1 * 16, j1 * 16);
                        this.renderChunks[k1] =
                                iRenderChunkFactory.makeRenderChunk(
                                        this.world, this.renderGlobal, blockPos, k++);
                        j1++;
                        if (i == 0) {
                            break;
                        }
                    }

                    i1++;
                    if (i != 0) {
                        continue;
                    }
                }

                l++;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    protected RenderChunk getRenderChunk(BlockPos blockPos) {
        int i = MathHelper.floorDiv(blockPos.getX(), 16);
        int j = MathHelper.floorDiv(blockPos.getY(), 16);
        int k = MathHelper.floorDiv(blockPos.getZ(), 16);
        if (j >= 0 && j < this.countChunksY) {
            i %= this.countChunksX;
            if (i < 0) {
                i += this.countChunksX;
            }

            k %= this.countChunksZ;
            if (k < 0) {
                k += this.countChunksZ;
            }

            int l = (k * this.countChunksY + j) * this.countChunksX + i;
            return this.renderChunks[l];
        } else {
            return null;
        }
    }

    private int method_ee7b208cf9490b71965c(int i, int j, int k) {
        int l = k * 16;
        int i1 = l - i + j / 2;
        if (i1 < 0) {
            i1 -= j - 1;
        }

        return l - i1 / j * j;
    }

    public void deleteGlResources() {
        int l = WorldRenderer.getLegacyRenderFlowToken();
        RenderChunk[] aAkValue = this.renderChunks;
        int j = aAkValue.length;
        int i = l;
        int k = 0;

        while (k < j) {
            RenderChunk renderChunk = aAkValue[k];
            renderChunk.deleteGlResources();
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    public void markBlocksForUpdate(int i, int j, int k, int l, int i1, int j1) {
        int l1 = MathHelper.floorDiv(i, 16);
        int i2 = MathHelper.floorDiv(j, 16);
        int j2 = MathHelper.floorDiv(k, 16);
        int i5 = WorldRenderer.getLegacyRenderFlowMarker();
        int k2 = MathHelper.floorDiv(l, 16);
        int l2 = MathHelper.floorDiv(i1, 16);
        int i3 = MathHelper.floorDiv(j1, 16);
        int k1 = i5;
        int j3 = l1;

        while (j3 <= k2) {
            int k3 = j3 % this.countChunksX;
            if (k3 < 0) {
                k3 += this.countChunksX;
            }

            int l3 = i2;

            while (true) {
                if (l3 <= l2) {
                    int i4 = l3 % this.countChunksY;
                    if (i4 < 0) {
                        i4 += this.countChunksY;
                    }

                    int j4 = j2;

                    while (j4 <= i3) {
                        int k4 = j4 % this.countChunksZ;
                        if (k4 < 0) {
                            k4 += this.countChunksZ;
                        }

                        int l4 = (k4 * this.countChunksY + i4) * this.countChunksX + k3;
                        RenderChunk renderChunk = this.renderChunks[l4];
                        renderChunk.setNeedsUpdate(true);
                        j4++;
                        if (k1 == 0) {
                            break;
                        }
                    }

                    l3++;
                    if (k1 != 0) {
                        continue;
                    }
                }

                j3++;
                if (k1 == 0) {
                    return;
                }
                break;
            }
        }
    }

    public void updateChunkPositions(double d0, double d1) {
        int j = MathHelper.floor(d0) - 8;
        int k = MathHelper.floor(d1) - 8;
        int k2 = WorldRenderer.getLegacyRenderFlowMarker();
        int l = this.countChunksX * 16;
        int i = k2;
        int i1 = 0;

        while (i1 < this.countChunksX) {
            int j1 = this.method_ee7b208cf9490b71965c(j, l, i1);
            int k1 = 0;

            while (true) {
                if (k1 < this.countChunksZ) {
                    int l1 = this.method_ee7b208cf9490b71965c(k, l, k1);
                    int i2 = 0;

                    while (i2 < this.countChunksY) {
                        int j2 = i2 * 16;
                        RenderChunk renderChunk =
                                this.renderChunks[
                                        (k1 * this.countChunksY + i2) * this.countChunksX + i1];
                        BlockPos blockPos = renderChunk.getPosition();
                        if (blockPos.getX() != j1
                                || blockPos.getY() != j2
                                || blockPos.getZ() != l1) {
                            BlockPos blockPosValue = new BlockPos(j1, j2, l1);
                            if (!blockPosValue.equals(renderChunk.getPosition())) {
                                renderChunk.setPosition(blockPosValue);
                            }
                        }

                        i2++;
                        if (i == 0) {
                            break;
                        }
                    }

                    k1++;
                    if (i != 0) {
                        continue;
                    }
                }

                i1++;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    public ViewFrustum(
            World awfValue, int i, RenderGlobal aAjValue, IRenderChunkFactory iRenderChunkFactory) {
        this.renderGlobal = aAjValue;
        this.world = awfValue;
        this.setCountChunksXYZ(i);
        this.createRenderChunks(iRenderChunkFactory);
    }
}
