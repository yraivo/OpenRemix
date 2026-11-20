package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public abstract class BlockLog extends BlockRotatedPillar {
    public static PropertyEnum AXIS_PROP;

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return super.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, livingEntity)
                .withProperty(
                        net.minecraft.block.BlockLog.AXIS_PROP,
                        BlockLogEnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        byte b0 = 4;
        int j = b0 + 1;
        int i = k;
        if (world.isAreaLoaded(blockPos.add(-j, -j, -j), blockPos.add(j, j, j))) {
            for (java.util.Iterator<?> __recoveryIterator171_1835 =
                            (BlockPos.getAllInBox(
                                            blockPos.add(-b0, -b0, -b0), blockPos.add(b0, b0, b0)))
                                    .iterator();
                    (__recoveryIterator171_1835).hasNext(); ) {
                BlockPos blockPosValue = (BlockPos) (__recoveryIterator171_1835).next();
                BlockState blockStateValue = world.getBlockState(blockPosValue);
                if (blockStateValue.getBlock().getMaterial() == Material.leaves
                        && !(Boolean) blockStateValue.getValue(BlockLeaves.CHECK_DECAY)) {
                    world.setBlockState(
                            blockPosValue,
                            blockStateValue.withProperty(BlockLeaves.CHECK_DECAY, true),
                            4);
                }

                if (i == 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F);
        this.setStepSound(net.minecraft.block.Block.soundTypeWood);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockLog.AXIS_PROP = PropertyEnum.create("axis", BlockLogEnumAxis.class);
    }
}
