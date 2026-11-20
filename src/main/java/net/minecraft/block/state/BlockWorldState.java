package net.minecraft.block.state;

import com.google.common.base.Predicate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockWorldState {
    private World world;
    private TileEntity tileEntity;
    private BlockPos pos;
    private boolean tileEntityInitialized;
    private BlockState state;

    public TileEntity getTileEntity() {
        if (this.tileEntity == null && !this.tileEntityInitialized) {
            this.tileEntity = this.world.getTileEntity(this.pos);
            this.tileEntityInitialized = true;
        }

        return this.tileEntity;
    }

    public BlockWorldState(World world, BlockPos blockPos) {
        this.world = world;
        this.pos = blockPos;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public static Predicate hasState(Predicate predicate) {
        return new BlockWorldStatePredicate(predicate);
    }

    public BlockState getBlockState() {
        if (this.state == null && this.world.isBlockLoaded(this.pos)) {
            this.state = this.world.getBlockState(this.pos);
        }

        return this.state;
    }

    // Helper used only by this class.
    static final class BlockWorldStatePredicate implements com.google.common.base.Predicate {
        com.google.common.base.Predicate field_177504_a;

        public boolean func_177503_a(net.minecraft.block.state.BlockWorldState blockWorldState) {
            return blockWorldState != null
                    && this.field_177504_a.apply(blockWorldState.getBlockState());
        }

        BlockWorldStatePredicate(com.google.common.base.Predicate predicate) {
            this.field_177504_a = predicate;
        }

        public boolean apply(java.lang.Object object) {
            return this.func_177503_a((net.minecraft.block.state.BlockWorldState) object);
        }
    }
}
