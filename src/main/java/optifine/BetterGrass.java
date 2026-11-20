package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class BetterGrass {
    private static IBakedModel modelCubeMycelium;
    private static IBakedModel modelCubeGrass;

    private static IBakedModel modelEmpty;
    private static IBakedModel modelCubeSnow;

    public static List getFaceQuads(
            IBlockAccess iBlockAccess, Block block, BlockPos blockPos, Facing facing, List list) {
        if (facing == Facing.UP || facing == Facing.DOWN) {
            return list;
        }

        if (block instanceof BlockMycelium) {
            return OptiFineConfig.jA()
                    ? (getBlockAt(blockPos.offsetDown(), facing, iBlockAccess) == Blocks.mycelium
                            ? optifine.BetterGrass.modelCubeMycelium.getFaceQuads(facing)
                            : list)
                    : optifine.BetterGrass.modelCubeMycelium.getFaceQuads(facing);
        }

        if (block instanceof BlockGrass) {
            Block blockValue = iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            int i = blockValue != Blocks.snow && blockValue != Blocks.snow_layer ? 0 : 1;
            if (!OptiFineConfig.jA()) {
                if (i != 0) {
                    return optifine.BetterGrass.modelCubeSnow.getFaceQuads(facing);
                }

                return optifine.BetterGrass.modelCubeGrass.getFaceQuads(facing);
            }

            if (i != 0) {
                if (getBlockAt(blockPos, facing, iBlockAccess) == Blocks.snow_layer) {
                    return optifine.BetterGrass.modelCubeSnow.getFaceQuads(facing);
                }
            } else if (getBlockAt(blockPos.offsetDown(), facing, iBlockAccess) == Blocks.grass) {
                return optifine.BetterGrass.modelCubeGrass.getFaceQuads(facing);
            }
        }

        return list;
    }

    private static Block getBlockAt(BlockPos blockPos, Facing facing, IBlockAccess iBlockAccess) {
        BlockPos blockPosValue = blockPos.offset(facing);
        return iBlockAccess.getBlockState(blockPosValue).getBlock();
    }

    public static void update() {
        optifine.BetterGrass.modelCubeGrass =
                BlockModelUtils.makeModelCube("minecraft:blocks/grass_top", 0);
        optifine.BetterGrass.modelCubeSnow =
                BlockModelUtils.makeModelCube("minecraft:blocks/snow", -1);
        optifine.BetterGrass.modelCubeMycelium =
                BlockModelUtils.makeModelCube("minecraft:blocks/mycelium_top", -1);
    }

    static {
        optifine.BetterGrass.modelEmpty =
                new SimpleBakedModel(
                        new ArrayList(),
                        new ArrayList(),
                        false,
                        false,
                        (TextureAtlasSprite) null,
                        (ItemCameraTransforms) null);
        optifine.BetterGrass.modelCubeMycelium = null;
        optifine.BetterGrass.modelCubeSnow = null;
        optifine.BetterGrass.modelCubeGrass = null;
    }
}
