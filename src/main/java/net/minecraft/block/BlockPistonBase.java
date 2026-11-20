package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPistonBase extends Block {

    public static PropertyBool EXTENDED;

    private boolean isSticky;
    public static PropertyDirection FACING;

    private void checkForMove(World world, BlockPos blockPos, BlockState blockState) {
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockPistonBase.FACING);
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        boolean flag = this.shouldBeExtended(world, blockPos, facing);
        int i = j;
        if (flag && !(Boolean) blockState.getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {
            if (!new BlockPistonStructureHelper(world, blockPos, facing, true).aY()) {
                return;
            }

            world.addBlockEvent(blockPos, this, 0, facing.getIndex());
            if (i != 0) {
                return;
            }
        }

        if (!flag && (Boolean) blockState.getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, false),
                    2);
            world.addBlockEvent(blockPos, this, 1, facing.getIndex());
        }
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        world.setBlockState(
                blockPos,
                blockState.withProperty(
                        net.minecraft.block.BlockPistonBase.FACING,
                        net.minecraft.block.BlockPistonBase.getFacingFromEntity(
                                world, blockPos, livingEntity)),
                2);
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isFullCube() {
        return false;
    }

    public BlockPistonBase(boolean ahvValue) {
        super(Material.piston);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPistonBase.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, false));
        this.isSticky = ahvValue;
        this.setStepSound(net.minecraft.block.Block.soundTypePiston);
        this.setHardness(0.5F);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public static Facing getFacing(int p171_0) {
        int j = p171_0 & 7;
        return j > 5 ? null : net.minecraft.util.Facing.getFront(j);
    }

    private boolean shouldBeExtended(World world, BlockPos blockPos, Facing facing) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing[] facingArray = Facing.values();
        int i = j;

        for (Facing facingValue : facingArray) {
            if (facingValue != facing
                    && world.isSidePowered(blockPos.offset(facingValue), facingValue)) {
                return true;
            }

            if (i != 0) {
                break;
            }
        }

        if (world.isSidePowered(blockPos, Facing.NORTH)) {
            return true;
        }

        BlockPos blockPosValue = blockPos.offsetUp();

        for (Facing facingValue2 : Facing.values()) {
            if (facingValue2 != Facing.DOWN
                    && world.isSidePowered(blockPosValue.offset(facingValue2), facingValue2)) {
                return true;
            }

            if (i != 0) {
                break;
            }
        }

        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        int i = j;
        if (blockState.getBlock() == this
                && (Boolean) blockState.getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {

            Facing facing =
                    (Facing) blockState.getValue(net.minecraft.block.BlockPistonBase.FACING);
            if (facing != null) {
                switch (facing) {
                    case DOWN:
                        this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                        if (i == 0) {
                            break;
                        }
                    case UP:
                        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                        if (i == 0) {
                            break;
                        }
                    case NORTH:
                        this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                        if (i == 0) {
                            break;
                        }
                    case SOUTH:
                        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                        if (i == 0) {
                            break;
                        }
                    case WEST:
                        this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                        if (i == 0) {
                            break;
                        }
                    case EAST:
                        this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                }
            }

            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public BlockState getStateForEntityRender(BlockState blockState) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockPistonBase.FACING, net.minecraft.util.Facing.UP);
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockPistonBase.FACING,
                    net.minecraft.block.BlockPistonBase.EXTENDED
                });
    }

    // region String decoding support

    // endregion

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float p171_3,
            float p171_4,
            float p171_5,
            int p171_6,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockPistonBase.FACING,
                        net.minecraft.block.BlockPistonBase.getFacingFromEntity(
                                world, blockPos, livingEntity))
                .withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, false);
    }

    public boolean onBlockEventReceived(
            World world, BlockPos blockPos, BlockState blockState, int i, int j) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockPistonBase.FACING);
        int k = l;
        if (!world.isRemote) {
            boolean flag = this.shouldBeExtended(world, blockPos, facing);
            if (flag && i == 1) {
                world.setBlockState(
                        blockPos,
                        blockState.withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, true),
                        2);
                return false;
            }

            if (!flag && i == 0) {
                return false;
            }
        }

        if (i == 0) {
            if (!this.doMove(world, blockPos, facing, true)) {
                return false;
            }

            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, true),
                    2);
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "tile.piston.out",
                    0.5F,
                    world.rand.nextFloat() * 0.25F + 0.6F);
            if (k == 0) {
                return true;
            }
        }

        if (i == 1) {
            TileEntity tileEntity = world.getTileEntity(blockPos.offset(facing));
            if (tileEntity instanceof TileEntityPiston) {
                ((TileEntityPiston) tileEntity).clearPistonTileEntity();
            }

            {
                world.setBlockState(
                        blockPos,
                        Blocks.piston_extension
                                .getDefaultState()
                                .withProperty(BlockPistonMoving.FACING, facing)
                                .withProperty(
                                        BlockPistonMoving.TYPE,
                                        this.isSticky
                                                ? BlockPistonExtensionEnumPistonType.STICKY
                                                : BlockPistonExtensionEnumPistonType.DEFAULT),
                        3);
                world.setTileEntity(
                        blockPos,
                        BlockPistonMoving.newTileEntity(
                                this.getStateFromMeta(j), facing, false, true));
                if (this.isSticky) {
                    BlockPos blockPosValue =
                            blockPos.add(
                                    facing.getFrontOffsetX() * 2,
                                    facing.getFrontOffsetY() * 2,
                                    facing.getFrontOffsetZ() * 2);
                    Block block = world.getBlockState(blockPosValue).getBlock();
                    byte b0 = 0;
                    if (block == Blocks.piston_extension) {
                        TileEntity tileEntityValue = world.getTileEntity(blockPosValue);
                        if (tileEntityValue instanceof TileEntityPiston) {
                            TileEntityPiston tileEntityPiston = (TileEntityPiston) tileEntityValue;
                            if (tileEntityPiston.getFacing() == facing
                                    && tileEntityPiston.isExtending()) {
                                tileEntityPiston.clearPistonTileEntity();
                                b0 = 1;
                            }
                        }
                    }

                    if (b0 == 0
                            && block.getMaterial() != Material.AIR
                            && canPush(block, world, blockPosValue, facing.getOpposite(), false)
                            && (block.getMobilityFlag() == 0
                                    || block == Blocks.piston
                                    || block == Blocks.sticky_piston)) {
                        this.doMove(world, blockPos, facing, false);
                    }

                    if (!(k == 0)) {

                        world.setBlockToAir(blockPos.offset(facing));
                    }
                } else {

                    world.setBlockToAir(blockPos.offset(facing));
                }
            }

            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "tile.piston.in",
                    0.5F,
                    world.rand.nextFloat() * 0.15F + 0.6F);
        }

        return true;
    }

    static {
        net.minecraft.block.BlockPistonBase.FACING = PropertyDirection.create("facing");
        net.minecraft.block.BlockPistonBase.EXTENDED = PropertyBool.create("extended");
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }

    public static Facing getFacingFromEntity(
            World world, BlockPos blockPos, LivingEntity livingEntity) {
        if (MathHelper.abs((float) livingEntity.posX - blockPos.getX()) < 2.0F
                && MathHelper.abs((float) livingEntity.posZ - blockPos.getZ()) < 2.0F) {
            double d0 = livingEntity.posY + livingEntity.getEyeHeight();
            if (d0 - blockPos.getY() > 2.0) {
                return Facing.UP;
            }

            if (blockPos.getY() - d0 > 0.0) {
                return Facing.DOWN;
            }
        }

        return livingEntity.getHorizontalFacing().getOpposite();
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((net.minecraft.util.Facing)
                                        blockState.getValue(
                                                net.minecraft.block.BlockPistonBase.FACING))
                                .getIndex();
        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {
            i |= 8;
        }

        return i;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public static boolean canPush(
            Block block, World world, BlockPos blockPos, Facing facing, boolean flag) {
        if (block == Blocks.obsidian) {
            return false;
        }

        if (!world.getWorldBorder().contains(blockPos)) {
            return false;
        }

        if (blockPos.getY() >= 0 && (facing != Facing.DOWN || blockPos.getY() != 0)) {
            if (blockPos.getY() <= world.getHeight() - 1
                    && (facing != Facing.UP || blockPos.getY() != world.getHeight() - 1)) {
                if (block != Blocks.piston && block != Blocks.sticky_piston) {
                    if (block.getBlockHardness(world, blockPos) == -1.0F) {
                        return false;
                    }

                    if (block.getMobilityFlag() == 2) {
                        return false;
                    }

                    if (block.getMobilityFlag() == 1) {
                        if (!flag) {
                            return false;
                        }

                        return true;
                    }
                } else if ((Boolean)
                        world.getBlockState(blockPos)
                                .getValue(net.minecraft.block.BlockPistonBase.EXTENDED)) {
                    return false;
                }

                return !(block instanceof ITileEntityProvider);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public BlockState getStateFromMeta(int p171_0) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockPistonBase.FACING,
                        net.minecraft.block.BlockPistonBase.getFacing(p171_0))
                .withProperty(net.minecraft.block.BlockPistonBase.EXTENDED, (p171_0 & 8) > 0);
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
    }

    private boolean doMove(World world, BlockPos blockPos, Facing facing, boolean flag) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!flag) {
            world.setBlockToAir(blockPos.offset(facing));
        }

        BlockPistonStructureHelper blockPistonStructureHelper =
                new BlockPistonStructureHelper(world, blockPos, facing, flag);
        List list = blockPistonStructureHelper.getBlocksToMove();
        List listValue = blockPistonStructureHelper.getBlocksToDestroy();
        if (!blockPistonStructureHelper.aY()) {
            return false;
        }

        int j = list.size() + listValue.size();
        Block[] blockArray = new Block[j];
        Facing facingValue = flag ? facing : facing.getOpposite();
        int k = listValue.size() - 1;

        while (k >= 0) {
            BlockPos blockPosValue = (BlockPos) listValue.get(k);
            Block block = world.getBlockState(blockPosValue).getBlock();
            block.dropBlockAsItem(world, blockPosValue, world.getBlockState(blockPosValue), 0);
            world.setBlockToAir(blockPosValue);
            blockArray[--j] = block;
            k--;
            if (i != 0) {
                break;
            }
        }

        k = list.size() - 1;

        while (k >= 0) {
            BlockPos blockPosValue2 = (BlockPos) list.get(k);
            BlockState blockState = world.getBlockState(blockPosValue2);
            Block blockValue = blockState.getBlock();
            blockValue.getMetaFromState(blockState);
            world.setBlockToAir(blockPosValue2);
            blockPosValue2 = blockPosValue2.offset(facingValue);
            world.setBlockState(
                    blockPosValue2,
                    Blocks.piston_extension
                            .getDefaultState()
                            .withProperty(net.minecraft.block.BlockPistonBase.FACING, facing),
                    4);
            world.setTileEntity(
                    blockPosValue2,
                    BlockPistonMoving.newTileEntity(blockState, facing, flag, false));
            blockArray[--j] = blockValue;
            k--;
            if (i != 0) {
                break;
            }
        }

        BlockPos blockPosValue3 = blockPos.offset(facing);
        if (flag) {
            BlockPistonExtensionEnumPistonType blockPistonExtensionEnumPistonType =
                    this.isSticky
                            ? BlockPistonExtensionEnumPistonType.STICKY
                            : BlockPistonExtensionEnumPistonType.DEFAULT;
            BlockState blockStateValue =
                    Blocks.piston_head
                            .getDefaultState()
                            .withProperty(BlockPistonExtension.FACING, facing)
                            .withProperty(
                                    BlockPistonExtension.TYPE, blockPistonExtensionEnumPistonType);
            BlockState blockStateValue2 =
                    Blocks.piston_extension
                            .getDefaultState()
                            .withProperty(BlockPistonMoving.FACING, facing)
                            .withProperty(
                                    BlockPistonMoving.TYPE,
                                    this.isSticky
                                            ? BlockPistonExtensionEnumPistonType.STICKY
                                            : BlockPistonExtensionEnumPistonType.DEFAULT);
            world.setBlockState(blockPosValue3, blockStateValue2, 4);
            world.setTileEntity(
                    blockPosValue3,
                    BlockPistonMoving.newTileEntity(blockStateValue, facing, true, false));
        }

        int l = listValue.size() - 1;

        while (l >= 0) {
            world.notifyNeighborsOfStateChange((BlockPos) listValue.get(l), blockArray[j++]);
            l--;
            if (i != 0) {
                break;
            }
        }

        l = list.size() - 1;

        while (l >= 0) {
            world.notifyNeighborsOfStateChange((BlockPos) list.get(l), blockArray[j++]);
            l--;
            if (i != 0) {
                break;
            }
        }

        if (flag) {
            world.notifyNeighborsOfStateChange(blockPosValue3, Blocks.piston_head);
            world.notifyNeighborsOfStateChange(blockPos, this);
        }

        return true;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
}
