package optifine;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.BlockModelRendererAmbientOcclusionFace;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import java.util.BitSet;
import java.util.List;

public class RenderEnv {
    private BitSet boundsFlags;
    private GameSettings gameSettings;
    private int blockId = -1;
    private BlockState blockState;
    private static ThreadLocal threadLocalInstance = new ThreadLocal();
    private int breakingAnimation;
    private BlockPosM colorizerBlockPosM;
    private BlockModelRendererAmbientOcclusionFace aoFace;
    private BlockPos blockPos;
    private int metadata = -1;
    private IBlockAccess blockAccess;
    private float[] quadBounds;
    private boolean[] borderFlags;

    public BlockState getBlockState() {
        return this.blockState;
    }

    private void reset(IBlockAccess iBlockAccess, BlockState blockState, BlockPos blockPos) {
        this.blockAccess = iBlockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.boundsFlags.clear();
    }

    private RenderEnv(IBlockAccess iBlockAccess, BlockState blockState, BlockPos blockPos) {
        this.breakingAnimation = -1;
        this.quadBounds = new float[Facing.VALUES.length * 2];
        this.boundsFlags = new BitSet(3);
        this.aoFace = new BlockModelRendererAmbientOcclusionFace();
        this.blockAccess = iBlockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.gameSettings = optifine.OptiFineConfig.getGameSettings();
    }

    public boolean isBreakingAnimation(List list) {
        int i = ReflectorMethod.f();
        if (this.breakingAnimation < 0 && list.size() > 0) {
            if (list.get(0) instanceof BreakingFour) {
                this.breakingAnimation = 1;
                if (i == 0) {
                    return this.breakingAnimation == 1;
                }
            }

            this.breakingAnimation = 0;
        }

        return this.breakingAnimation == 1;
    }

    public BlockPosM getColorizerBlockPosM() {
        if (this.colorizerBlockPosM == null) {
            this.colorizerBlockPosM = new optifine.BlockPosM(0, 0, 0);
        }

        return this.colorizerBlockPosM;
    }

    public int getMetadata() {
        int i = ReflectorMethod.f();
        if (this.metadata < 0) {
            if (this.blockState instanceof BlockStateBase) {
                BlockStateBase blockStateValue = (BlockStateBase) this.blockState;
                this.metadata = blockStateValue.getMetadata();
                if (i == 0) {
                    return this.metadata;
                }
            }

            this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
        }

        return this.metadata;
    }

    public float[] getQuadBounds() {
        return this.quadBounds;
    }

    public BitSet getBoundsFlags() {
        return this.boundsFlags;
    }

    public boolean isBreakingAnimation() {
        return this.breakingAnimation == 1;
    }

    public boolean isBreakingAnimation(BakedQuad bakedQuad) {
        int i = ReflectorMethod.f();
        if (this.breakingAnimation < 0) {
            if (bakedQuad instanceof BreakingFour) {
                this.breakingAnimation = 1;
                if (i == 0) {
                    return this.breakingAnimation == 1;
                }
            }

            this.breakingAnimation = 0;
        }

        return this.breakingAnimation == 1;
    }

    public int method_9b417d27949cba33a0d0() {
        int i = optifine.ReflectorMethod.f();
        if (this.blockId < 0) {
            if (this.blockState instanceof net.minecraft.block.state.BlockStateBase) {
                net.minecraft.block.state.BlockStateBase blockStateValue = (net.minecraft.block.state.BlockStateBase) this.blockState;
                this.blockId = blockStateValue.getBlockId();
                if (i == 0) {
                    return this.blockId;
                }
            }

            this.blockId = net.minecraft.block.Block.getIdFromBlock(this.blockState.getBlock());
        }

        return this.blockId;
    }

    public BlockModelRendererAmbientOcclusionFace getAoFace() {
        return this.aoFace;
    }

    public boolean[] getBorderFlags() {
        if (this.borderFlags == null) {
            this.borderFlags = new boolean[4];
        }

        return this.borderFlags;
    }

    public static RenderEnv getInstance(
            IBlockAccess iBlockAccess, BlockState blockState, BlockPos blockPos) {
        optifine.RenderEnv renderEnv = (optifine.RenderEnv) optifine.RenderEnv.threadLocalInstance.get();
        if (renderEnv == null) {
            renderEnv = new optifine.RenderEnv(iBlockAccess, blockState, blockPos);
            optifine.RenderEnv.threadLocalInstance.set(renderEnv);
            return renderEnv;
        } else {
            renderEnv.reset(iBlockAccess, blockState, blockPos);
            return renderEnv;
        }
    }
}
