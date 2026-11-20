package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.World;

import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate {

    public static PropertyBool POWERED;
    private BlockPressurePlateSensitivity sensitivity;

    protected int computeRedstoneStrength(World world, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BoundingBox boundingBox = this.getSensitiveAABB(blockPos);
        int i = j;
        List list;
        switch (this.sensitivity) {
            case EVERYTHING:
                list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, boundingBox);
                if (i != 0) {
                    break;
                }
            case MOBS:
                list = world.getEntitiesWithinAABB(LivingEntity.class, boundingBox);
                if (i == 0) {
                    return 0;
                }
                break;
            default:
                return 0;
        }

        if (!list.isEmpty()) {
            for (java.util.Iterator<?> __recoveryIterator171_1701 = (list).iterator();
                    (__recoveryIterator171_1701).hasNext(); ) {
                Entity entity = (Entity) (__recoveryIterator171_1701).next();
                if (!entity.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }

                if (i == 0) {
                    break;
                }
            }
        }

        return 0;
    }

    protected int getRedstoneStrength(BlockState blockState) {
        return ((java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockPressurePlate.POWERED))
                ? 15
                : 0;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockPressurePlate.POWERED = PropertyBool.create("powered");
    }

    protected BlockState setRedstoneStrength(BlockState blockState, int i) {
        return blockState.withProperty(net.minecraft.block.BlockPressurePlate.POWERED, i > 0);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockPressurePlate.POWERED);
    }

    protected BlockPressurePlate(Material material, BlockPressurePlateSensitivity ajAValue) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPressurePlate.POWERED, false));
        this.sensitivity = ajAValue;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockPressurePlate.POWERED, i == 1);
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        return ((java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockPressurePlate.POWERED))
                ? 1
                : 0;
    }
}
