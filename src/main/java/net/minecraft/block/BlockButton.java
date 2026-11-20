package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block {
    private boolean wooden;

    public static PropertyDirection FACING_PROP;

    public static PropertyBool POWERED_PROP;

    public int getMetaFromState(BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        byte b0;
        switch (((Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP))) {
            case EAST:
                b0 = 1;
                if (i != 0) {
                    break;
                }
            case WEST:
                b0 = 2;
                if (i != 0) {
                    break;
                }
            case SOUTH:
                b0 = 3;
                if (i != 0) {
                    break;
                }
            case NORTH:
                b0 = 4;
                if (i != 0) {
                    break;
                }
            case UP:
            default:
                b0 = 5;
                if (i != 0) {
                    break;
                }
            case DOWN:
                b0 = 0;
        }

        if ((Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP)) {
            b0 |= 8;
        }

        return b0;
    }

    public BlockState getStateFromMeta(int i) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing facing;
        switch (i & 7) {
            case 0:
                facing = Facing.DOWN;
                if (j != 0) {
                    break;
                }
            case 1:
                facing = Facing.EAST;
                if (j != 0) {
                    break;
                }
            case 2:
                facing = Facing.WEST;
                if (j != 0) {
                    break;
                }
            case 3:
                facing = Facing.SOUTH;
                if (j != 0) {
                    break;
                }
            case 4:
                facing = Facing.NORTH;
                if (j != 0) {
                    break;
                }
            case 5:
            default:
                facing = Facing.UP;
        }

        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockButton.FACING_PROP, facing)
                .withProperty(net.minecraft.block.BlockButton.POWERED_PROP, (i & 8) > 0);
    }

    private boolean checkForDrop(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockPos)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
            return false;
        } else {
            return true;
        }
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return !((boolean)
                        (((java.lang.Boolean)
                                        ((java.lang.Boolean)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockButton
                                                                .POWERED_PROP)))
                                .booleanValue()))
                ? 0
                : (blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP) == facing
                        ? 15
                        : 0);
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return ((boolean)
                        (((java.lang.Boolean)
                                        ((java.lang.Boolean)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockButton
                                                                .POWERED_PROP)))
                                .booleanValue()))
                ? 15
                : 0;
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.1875F;
        float f1 = 0.125F;
        float f2 = 0.125F;
        this.setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    private void checkForArrows(World world, BlockPos blockPos, BlockState blockState) {
        this.updateBlockBounds(blockState);
        List entitiesWithinAABB =
                world.getEntitiesWithinAABB(
                        EntityArrow.class,
                        new BoundingBox(
                                blockPos.getX() + this.minX,
                                blockPos.getY() + this.minY,
                                blockPos.getZ() + this.minZ,
                                blockPos.getX() + this.maxX,
                                blockPos.getY() + this.maxY,
                                blockPos.getZ() + this.maxZ));
        int i = !entitiesWithinAABB.isEmpty() ? 1 : 0;
        boolean value = (Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP);
        if (i != 0 && !value) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockButton.POWERED_PROP, true));
            this.notifyNeighbors(
                    world,
                    blockPos,
                    (Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.3F,
                    0.6F);
        }

        if (i == 0 && value) {
            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockButton.POWERED_PROP, false));
            this.notifyNeighbors(
                    world,
                    blockPos,
                    (Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.3F,
                    0.5F);
        }

        if (i != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockButton.FACING_PROP,
                    net.minecraft.block.BlockButton.POWERED_PROP
                });
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote
                && (Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP)) {
            if (this.wooden) {
                this.checkForArrows(world, blockPos, blockState);
                if (i == 0) {
                    return;
                }
            }

            world.setBlockState(
                    blockPos,
                    blockState.withProperty(net.minecraft.block.BlockButton.POWERED_PROP, false));
            this.notifyNeighbors(
                    world,
                    blockPos,
                    (Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP));
            world.playSoundEffect(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "random.click",
                    0.3F,
                    0.5F);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
    }

    static {
        net.minecraft.block.BlockButton.FACING_PROP = PropertyDirection.create("facing");
        net.minecraft.block.BlockButton.POWERED_PROP = PropertyBool.create("powered");
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP)) {
            this.notifyNeighbors(
                    world,
                    blockPos,
                    (net.minecraft.util.Facing)
                            blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP));
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing[] facingArray = Facing.values();
        int i = j;

        for (Facing facing : facingArray) {
            if (world.getBlockState(blockPos.offset(facing)).getBlock().isNormalCube()) {
                return true;
            }

            if (i != 0) {
                break;
            }
        }

        return false;
    }

    public int tickRate(World world) {
        return this.wooden ? 30 : 20;
    }

    private void notifyNeighbors(World world, BlockPos blockPos, Facing facing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(facing.getOpposite()), this);
    }

    protected BlockButton(boolean ahvValue) {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockButton.FACING_PROP, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockButton.POWERED_PROP, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.wooden = ahvValue;
    }

    private void updateBlockBounds(BlockState blockState) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP);
        boolean value = (Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP);
        int i = j;

        float f2 = (value ? 1 : 2) / 16.0F;

        switch (facing) {
            case EAST:
                this.setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
                if (i != 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
                if (i != 0) {
                    break;
                }
            case SOUTH:
                this.setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
                if (i != 0) {
                    break;
                }
            case NORTH:
                this.setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
                if (i != 0) {
                    break;
                }
            case UP:
                this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
                if (i != 0) {
                    break;
                }
            case DOWN:
                this.setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
        }
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
        return world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock().isNormalCube()
                ? this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockButton.FACING_PROP, facing)
                        .withProperty(net.minecraft.block.BlockButton.POWERED_PROP, false)
                : this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockButton.FACING_PROP,
                                net.minecraft.util.Facing.DOWN)
                        .withProperty(net.minecraft.block.BlockButton.POWERED_PROP, false);
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock().isNormalCube();
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (this.checkForDrop(world, blockPos, blockState)) {
            net.minecraft.util.Facing facing =
                    (net.minecraft.util.Facing)
                            blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP);
            if (!world.getBlockState(blockPos.offset(facing.getOpposite()))
                    .getBlock()
                    .isNormalCube()) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
                world.setBlockToAir(blockPos);
            }
        }
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
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
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP)) {
            return true;
        }

        world.setBlockState(
                blockPos,
                blockState.withProperty(net.minecraft.block.BlockButton.POWERED_PROP, true),
                3);
        world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        world.playSoundEffect(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                "random.click",
                0.3F,
                0.6F);
        this.notifyNeighbors(
                world,
                blockPos,
                (Facing) blockState.getValue(net.minecraft.block.BlockButton.FACING_PROP));
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
        return true;
    }

    public boolean isFullCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.updateBlockBounds(iBlockAccess.getBlockState(blockPos));
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!world.isRemote
                && this.wooden
                && !(java.lang.Boolean)
                        blockState.getValue(net.minecraft.block.BlockButton.POWERED_PROP)) {
            this.checkForArrows(world, blockPos, blockState);
        }
    }

    public boolean canProvidePower() {
        return true;
    }
}
