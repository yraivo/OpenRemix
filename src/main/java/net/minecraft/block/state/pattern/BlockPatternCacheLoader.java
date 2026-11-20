package net.minecraft.block.state.pattern;

import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.cache.CacheLoader;

class BlockPatternCacheLoader extends CacheLoader {
    private World world;

    public Object load(Object object) {
        return this.method_0bbe7a31efde4ee7a9d3((BlockPos) object);
    }

    public BlockPatternCacheLoader(World world) {
        this.world = world;
    }

    public BlockWorldState method_0bbe7a31efde4ee7a9d3(BlockPos blockPos) {
        return new BlockWorldState(this.world, blockPos);
    }
}
