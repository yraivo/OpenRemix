package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLeverEnumOrientation;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class BetterSnow {
    private static IBakedModel modelSnowLayer = null;

    public static IBakedModel getModelSnowLayer() {
        return optifine.BetterSnow.modelSnowLayer;
    }

    private static boolean checkBlock(Block block, BlockState blockState) {
        if (block.isFullCube()) {
            return false;
        }

        if (block.isOpaqueCube()) {
            return false;
        }

        if (block instanceof BlockSnow) {
            return false;
        }

        if (!(block instanceof BlockBush)
                || !(block instanceof BlockDoublePlant)
                        && !(block instanceof BlockFlower)
                        && !(block instanceof BlockMushroom)
                        && !(block instanceof BlockSapling)
                        && !(block instanceof BlockTallGrass)) {
            if (block instanceof BlockFence
                    || block instanceof BlockFenceGate
                    || block instanceof BlockFlowerPot
                    || block instanceof BlockPane
                    || block instanceof BlockReed
                    || block instanceof BlockWall) {
                return true;
            }

            if (block instanceof BlockRedstoneTorch
                    && blockState.getValue(BlockTorch.FACING_PROP) == Facing.UP) {
                return true;
            }

            if (block instanceof BlockLever) {
                Comparable comparable = blockState.getValue(BlockLever.FACING);
                if (comparable == BlockLeverEnumOrientation.UP_X
                        || comparable == BlockLeverEnumOrientation.UP_Z) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static BlockState getStateSnowLayer() {
        return Blocks.snow_layer.getDefaultState();
    }

    public static boolean shouldRender(
            IBlockAccess iBlockAccess, Block block, BlockState blockState, BlockPos blockPos) {
        return !checkBlock(block, blockState) ? false : hasSnowNeighbours(iBlockAccess, blockPos);
    }

    public static void update() {
        optifine.BetterSnow.modelSnowLayer =
                OptiFineConfig.getMinecraft()
                        .getBlockRendererDispatcher()
                        .getBlockModelShapes()
                        .getModelForState(Blocks.snow_layer.getDefaultState());
    }

    private static boolean hasSnowNeighbours(IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block blz = Blocks.snow_layer;
        return iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock() != blz
                        && iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock() != blz
                        && iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock() != blz
                        && iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock() != blz
                ? false
                : iBlockAccess.getBlockState(blockPos.offsetDown()).getBlock().isOpaqueCube();
    }
}
