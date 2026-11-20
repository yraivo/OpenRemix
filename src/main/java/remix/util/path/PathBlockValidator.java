package remix.util.path;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

public class PathBlockValidator {
    private static MinecraftClient ec = MinecraftClient.getMinecraft();

    public static boolean isPassable(BlockPos position) {
        int traversalMarker = AStarPathfinder.getLegacyTraversalMarker();
        BlockState feet = ec.world.getBlockState(position);
        BlockState head = ec.world.getBlockState(position.add(0, 1, 0));
        boolean passable = !feet.getBlock().getMaterial().isSolid() && !head.getBlock().getMaterial().isSolid();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AStarPathfinder.setLegacyTraversalMarker(traversalMarker + 1);
        }
        return passable;
    }

    public static double manhattanDistance(BlockPos blockPos, BlockPos blockPosValue) {
        return Math.abs(blockPos.getX() - blockPosValue.getX())
                + Math.abs(blockPos.getY() - blockPosValue.getY())
                + Math.abs(blockPos.getZ() - blockPosValue.getZ());
    }
}
