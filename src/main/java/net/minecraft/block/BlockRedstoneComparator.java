package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
    public static PropertyEnum MODE;

    public static PropertyBool POWERED;

    private EntityItemFrame findItemFrame(World world, Facing facing, BlockPos blockPos) {
        List entitiesWithinAABB =
                world.getEntitiesWithinAABB(
                        EntityItemFrame.class,
                        new BoundingBox(
                                blockPos.getX(),
                                blockPos.getY(),
                                blockPos.getZ(),
                                blockPos.getX() + 1,
                                blockPos.getY() + 1,
                                blockPos.getZ() + 1),
                        new FindItemFramePredicate(this, facing));
        return entitiesWithinAABB.size() == 1 ? (EntityItemFrame) entitiesWithinAABB.get(0) : null;
    }

    protected BlockState getUnpoweredState(BlockState blockState) {
        Boolean obool =
                (Boolean) blockState.getValue(net.minecraft.block.BlockRedstoneComparator.POWERED);
        BlockRedstoneComparatorMode value =
                (BlockRedstoneComparatorMode)
                        blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        return Blocks.unpowered_comparator
                .getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, facing)
                .withProperty(net.minecraft.block.BlockRedstoneComparator.POWERED, obool)
                .withProperty(net.minecraft.block.BlockRedstoneComparator.MODE, value);
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityComparator();
    }

    protected void updateState(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isBlockTickPending(blockPos, this)) {
            int j = this.calculateOutput(world, blockPos, blockState);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            int k =
                    tileEntity instanceof TileEntityComparator
                            ? ((TileEntityComparator) tileEntity).getOutputSignal()
                            : 0;
            if (j != k
                    || this.isPowered(blockState)
                            != this.shouldBePowered(world, blockPos, blockState)) {
                if (this.isFacingTowardsRepeater(world, blockPos, blockState)) {
                    world.updateBlockTick(blockPos, this, 2, -1);
                    if (i != 0) {
                        return;
                    }
                }

                world.updateBlockTick(blockPos, this, 2, 0);
            }
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDirectional.AGE,
                    net.minecraft.block.BlockRedstoneComparator.MODE,
                    net.minecraft.block.BlockRedstoneComparator.POWERED
                });
    }

    protected BlockState getPoweredState(BlockState blockState) {
        Boolean obool =
                (Boolean) blockState.getValue(net.minecraft.block.BlockRedstoneComparator.POWERED);
        BlockRedstoneComparatorMode value =
                (BlockRedstoneComparatorMode)
                        blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        return Blocks.powered_comparator
                .getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, facing)
                .withProperty(net.minecraft.block.BlockRedstoneComparator.POWERED, obool)
                .withProperty(net.minecraft.block.BlockRedstoneComparator.MODE, value);
    }

    protected int calculateInputStrength(World world, BlockPos blockPos, BlockState blockState) {
        int j = super.calculateInputStrength(world, blockPos, blockState);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockPos blockPosValue = blockPos.offset(facing);
        int i = k;
        Block block = world.getBlockState(blockPosValue).getBlock();
        if (block.hasComparatorInputOverride()) {
            j = block.getComparatorInputOverride(world, blockPosValue);
            if (i == 0) {
                return j;
            }
        }

        if (j < 15 && block.isNormalCube()) {
            blockPosValue = blockPosValue.offset(facing);
            block = world.getBlockState(blockPosValue).getBlock();
            if (block.hasComparatorInputOverride()) {
                j = block.getComparatorInputOverride(world, blockPosValue);
                if (i == 0) {
                    return j;
                }
            }

            if (block.getMaterial() == Material.AIR) {
                EntityItemFrame entityItemFrame = this.findItemFrame(world, facing, blockPosValue);
                if (entityItemFrame != null) {
                    j = entityItemFrame.f();
                }
            }
        }

        return j;
    }

    public boolean onBlockEventReceived(
            World world, BlockPos blockPos, BlockState blockState, int i, int j) {
        super.onBlockEventReceived(world, blockPos, blockState, i, j);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity == null ? false : tileEntity.receiveClientEvent(i, j);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                .getHorizontalIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockRedstoneComparator.POWERED)) {
            i |= 8;
        }

        if (blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE)
                == BlockRedstoneComparatorMode.SUBTRACT) {
            i |= 4;
        }

        return i;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (this.isRepeaterPowered) {
            world.setBlockState(
                    blockPos,
                    this.getUnpoweredState(blockState)
                            .withProperty(
                                    net.minecraft.block.BlockRedstoneComparator.POWERED, true),
                    4);
        }

        this.onStateChange(world, blockPos, blockState);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.comparator;
    }

    protected boolean isPowered(BlockState blockState) {
        return this.isRepeaterPowered
                || (Boolean)
                        blockState.getValue(net.minecraft.block.BlockRedstoneComparator.POWERED);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
        this.notifyNeighbors(world, blockPos, blockState);
    }

    private int calculateOutput(World world, BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE)
                        == BlockRedstoneComparatorMode.SUBTRACT
                ? Math.max(
                        this.calculateInputStrength(world, blockPos, blockState)
                                - this.getPowerOnSides(world, blockPos, blockState),
                        0)
                : this.calculateInputStrength(world, blockPos, blockState);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        world.setTileEntity(blockPos, this.createNewTileEntity(world, 0));
    }

    protected int getDelay(BlockState blockState) {
        return 2;
    }

    private void onStateChange(World world, BlockPos blockPos, BlockState blockState) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = this.calculateOutput(world, blockPos, blockState);
        int i = l;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        int k = 0;
        if (tileEntity instanceof TileEntityComparator) {
            TileEntityComparator tileEntityComparator = (TileEntityComparator) tileEntity;
            k = tileEntityComparator.getOutputSignal();
            tileEntityComparator.setOutputSignal(j);
        }

        if (k != j
                || blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE)
                        == BlockRedstoneComparatorMode.COMPARE) {
            {
                boolean flag1 = this.shouldBePowered(world, blockPos, blockState);
                boolean powered = this.isPowered(blockState);
                if (powered && !flag1) {
                    world.setBlockState(
                            blockPos,
                            blockState.withProperty(
                                    net.minecraft.block.BlockRedstoneComparator.POWERED, false),
                            2);
                    if (!(i != 0)) {

                        if (!powered && flag1) {
                            world.setBlockState(
                                    blockPos,
                                    blockState.withProperty(
                                            net.minecraft.block.BlockRedstoneComparator.POWERED,
                                            true),
                                    2);
                        }
                    }
                } else {

                    if (!powered && flag1) {
                        world.setBlockState(
                                blockPos,
                                blockState.withProperty(
                                        net.minecraft.block.BlockRedstoneComparator.POWERED, true),
                                2);
                    }
                }
            }

            this.notifyNeighbors(world, blockPos, blockState);
        }
    }

    protected int getActiveSignal(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = iBlockAccess.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityComparator
                ? ((TileEntityComparator) tileEntity).getOutputSignal()
                : 0;
    }

    public BlockRedstoneComparator(boolean flag) {
        super(flag);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockRedstoneComparator.POWERED, false)
                        .withProperty(
                                net.minecraft.block.BlockRedstoneComparator.MODE,
                                BlockRedstoneComparatorMode.COMPARE));
        this.isBlockContainer = true;
    }

    static {
        net.minecraft.block.BlockRedstoneComparator.POWERED = PropertyBool.create("powered");
        net.minecraft.block.BlockRedstoneComparator.MODE =
                PropertyEnum.create("mode", BlockRedstoneComparatorMode.class);
    }

    protected boolean shouldBePowered(World world, BlockPos blockPos, BlockState blockState) {
        int inputStrength = this.calculateInputStrength(world, blockPos, blockState);
        if (inputStrength >= 15) {
            return true;
        }

        if (inputStrength == 0) {
            return false;
        }

        int j = this.getPowerOnSides(world, blockPos, blockState);
        return j == 0 ? true : inputStrength >= j;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (!playerEntity.capabilities.allowEdit) {
            return false;
        }

        blockState = blockState.cycleProperty(net.minecraft.block.BlockRedstoneComparator.MODE);
        world.playSoundEffect(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                "random.click",
                0.3F,
                blockState.getValue(net.minecraft.block.BlockRedstoneComparator.MODE)
                                == BlockRedstoneComparatorMode.SUBTRACT
                        ? 0.55F
                        : 0.5F);
        world.setBlockState(blockPos, blockState, 2);
        this.onStateChange(world, blockPos, blockState);
        return true;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.comparator;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.getHorizontal(i))
                .withProperty(net.minecraft.block.BlockRedstoneComparator.POWERED, (i & 8) > 0)
                .withProperty(
                        net.minecraft.block.BlockRedstoneComparator.MODE,
                        (i & 4) > 0
                                ? BlockRedstoneComparatorMode.SUBTRACT
                                : BlockRedstoneComparatorMode.COMPARE);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockDirectional.AGE,
                        livingEntity.getHorizontalFacing().getOpposite())
                .withProperty(net.minecraft.block.BlockRedstoneComparator.POWERED, false)
                .withProperty(
                        net.minecraft.block.BlockRedstoneComparator.MODE,
                        BlockRedstoneComparatorMode.COMPARE);
    }

    // Helper used only by this class.
    public static class FindItemFramePredicate implements com.google.common.base.Predicate {
        net.minecraft.util.Facing aAH;
        net.minecraft.block.BlockRedstoneComparator aWK;

        public FindItemFramePredicate(
                net.minecraft.block.BlockRedstoneComparator aWKValue,
                net.minecraft.util.Facing aAHValue) {
            this.aWK = aWKValue;
            this.aAH = aAHValue;
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity != null && entity.getHorizontalFacing() == this.aAH;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }
    }
}
