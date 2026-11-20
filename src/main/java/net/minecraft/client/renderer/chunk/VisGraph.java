package net.minecraft.client.renderer.chunk;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import optifine.IntegerCache;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;

public class VisGraph {
    private static final int[] boundaryCellIndices = new int[1352];
    private final BitSet opaqueOrVisited = new BitSet(4096);
    private static final int xStride = (int) Math.pow(16.0, 0.0);
    private int openCellCount = 4096;
    private static final int zStride = (int) Math.pow(16.0, 1.0);
    private static final int yStride = (int) Math.pow(16.0, 2.0);

    private int neighborIndex(int index, Facing direction) {
        int x = index & 15;
        int y = index >> 8 & 15;
        int z = index >> 4 & 15;
        if (direction == Facing.DOWN) return y == 0 ? -1 : index - net.minecraft.client.renderer.chunk.VisGraph.yStride;
        if (direction == Facing.UP) return y == 15 ? -1 : index + net.minecraft.client.renderer.chunk.VisGraph.yStride;
        if (direction == Facing.NORTH) return z == 0 ? -1 : index - net.minecraft.client.renderer.chunk.VisGraph.zStride;
        if (direction == Facing.SOUTH) return z == 15 ? -1 : index + net.minecraft.client.renderer.chunk.VisGraph.zStride;
        if (direction == Facing.WEST) return x == 0 ? -1 : index - net.minecraft.client.renderer.chunk.VisGraph.xStride;
        if (direction == Facing.EAST) return x == 15 ? -1 : index + net.minecraft.client.renderer.chunk.VisGraph.xStride;
        return -1;
    }

    public void markOpaque(BlockPos blockPos) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        if (!ClientServices.moduleManager.xray.isEnabled()) {
            this.opaqueOrVisited.set(getIndex(blockPos), true);
            this.openCellCount--;
        }
    }

    private Set<Facing> floodVisibleFaces(int startIndex) {
        EnumSet<Facing> visibleFaces = EnumSet.noneOf(Facing.class);
        ArrayDeque<Integer> pending = new ArrayDeque<>(384);
        boolean fullTraversal = RenderChunk.usesNormalChunkUpdateFlow();
        pending.add(IntegerCache.valueOf(startIndex));
        this.opaqueOrVisited.set(startIndex, true);

        while (!pending.isEmpty()) {
            int index = pending.remove();
            this.addBoundaryFaces(index, visibleFaces);
            for (Facing direction : Facing.VALUES) {
                int neighbor = this.neighborIndex(index, direction);
                if (neighbor >= 0 && !this.opaqueOrVisited.get(neighbor)) {
                    this.opaqueOrVisited.set(neighbor, true);
                    pending.add(IntegerCache.valueOf(neighbor));
                }
                if (!fullTraversal) {
                    break;
                }
            }
            if (!fullTraversal) {
                break;
            }
        }
        return visibleFaces;
    }

    public SetVisibility computeVisibility() {
        boolean legacyFlow = RenderChunk.isLegacyChunkUpdateFlow();
        SetVisibility visibility = new SetVisibility();
        if (4096 - this.openCellCount < 256) {
            visibility.setAllVisible(true);
            if (!legacyFlow) {
                return visibility;
            }
        }
        if (this.openCellCount == 0) {
            visibility.setAllVisible(false);
            if (!legacyFlow) {
                return visibility;
            }
        }

        for (int index : net.minecraft.client.renderer.chunk.VisGraph.boundaryCellIndices) {
            if (!this.opaqueOrVisited.get(index)) {
                visibility.setManyVisible(this.floodVisibleFaces(index));
            }
            if (legacyFlow) {
                break;
            }
        }
        return visibility;
    }

    private void addBoundaryFaces(int index, Set<Facing> faces) {
        int x = index & 15;
        int y = index >> 8 & 15;
        int z = index >> 4 & 15;
        if (x == 0) faces.add(Facing.WEST);
        if (x == 15) faces.add(Facing.EAST);
        if (y == 0) faces.add(Facing.DOWN);
        if (y == 15) faces.add(Facing.UP);
        if (z == 0) faces.add(Facing.NORTH);
        if (z == 15) faces.add(Facing.SOUTH);
    }

    private static int getIndex(int x, int y, int z) {
        return x | y << 8 | z << 4;
    }

    static {
        int i = 0;

        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                for (int l = 0; l < 16; l++) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        net.minecraft.client.renderer.chunk.VisGraph.boundaryCellIndices[i++] = getIndex(j, k, l);
                    }
                }
            }
        }
    }

    public Set<Facing> getVisibleFacingsFrom(BlockPos blockPos) {
        return this.floodVisibleFaces(net.minecraft.client.renderer.chunk.VisGraph.getIndex(blockPos));
    }

    private static int getIndex(BlockPos blockPos) {
        return getIndex(
                blockPos.getX() & 15, blockPos.getY() & 15, blockPos.getZ() & 15);
    }
}
