package net.minecraft.block.state.pattern;

import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;

import com.google.common.cache.LoadingCache;

public class BlockPatternPatternHelper {
    private Facing finger;
    private LoadingCache lcache;
    private BlockPos pos;
    private Facing thumb;

    public Facing getFinger() {
        return this.finger;
    }

    public BlockWorldState translateOffset(int i, int j, int k) {
        return (BlockWorldState)
                this.lcache.getUnchecked(
                        BlockPattern.translateOffset(
                                this.pos, this.getFinger(), this.getThumb(), i, j, k));
    }

    public BlockPatternPatternHelper(
            BlockPos blockPos, Facing aAcValue, Facing aAAValue, LoadingCache loadingcache) {
        this.pos = blockPos;
        this.finger = aAcValue;
        this.thumb = aAAValue;
        this.lcache = loadingcache;
    }

    public Facing getThumb() {
        return this.thumb;
    }
}
