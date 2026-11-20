package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockVine extends Block {

    public static PropertyBool[] ALL_FACES;
    public static PropertyBool EAST;
    public static int field_176276_R;
    public static PropertyBool SOUTH;
    public static int field_176275_S;
    public static int field_176271_T;
    public static PropertyBool NORTH;
    public static PropertyBool UP;
    public static int field_176272_Q;

    public static PropertyBool WEST;

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public BlockVine() {
        super(Material.vine);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockVine.UP, false)
                        .withProperty(net.minecraft.block.BlockVine.NORTH, false)
                        .withProperty(net.minecraft.block.BlockVine.EAST, false)
                        .withProperty(net.minecraft.block.BlockVine.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockVine.WEST, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    static {
        net.minecraft.block.BlockVine.UP = PropertyBool.create("up");
        net.minecraft.block.BlockVine.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockVine.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockVine.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockVine.WEST = PropertyBool.create("west");
        net.minecraft.block.BlockVine.ALL_FACES =
                new PropertyBool[] {
                    net.minecraft.block.BlockVine.UP,
                    net.minecraft.block.BlockVine.NORTH,
                    net.minecraft.block.BlockVine.SOUTH,
                    net.minecraft.block.BlockVine.WEST,
                    net.minecraft.block.BlockVine.EAST
                };
        net.minecraft.block.BlockVine.field_176272_Q = func_176270_b(Facing.SOUTH);
        net.minecraft.block.BlockVine.field_176276_R = func_176270_b(Facing.NORTH);
        net.minecraft.block.BlockVine.field_176275_S = func_176270_b(Facing.EAST);
        net.minecraft.block.BlockVine.field_176271_T = func_176270_b(Facing.WEST);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {

        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag = false;
        if ((java.lang.Boolean)
                iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockVine.WEST)) {
            f4 = java.lang.Math.max(f4, 0.0625F);
            f1 = 0.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if ((java.lang.Boolean)
                iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockVine.EAST)) {
            f1 = java.lang.Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if ((java.lang.Boolean)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockVine.NORTH)) {
            f6 = java.lang.Math.max(f6, 0.0625F);
            f3 = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((java.lang.Boolean)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockVine.SOUTH)) {
            f3 = java.lang.Math.min(f3, 0.9375F);
            f6 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        {
            boolean runGuardedCode;
            try {
                runGuardedCode =
                        !((flag)
                                || !this.canPlaceOn(
                                        iBlockAccess
                                                .getBlockState(blockPos.offsetUp())
                                                .getBlock()));
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw net.minecraft.block.BlockVine.method_9a70918140b0a7642dba(
                        illegalargumentexception);
            }
            if (runGuardedCode) {

                f2 = java.lang.Math.min(f2, 0.9375F);
                f5 = 1.0F;
                f1 = 0.0F;
                f4 = 1.0F;
                f3 = 0.0F;
                f6 = 1.0F;
            }
        }

        this.setBlockBounds(f1, f2, f3, f4, f5, f6);
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        switch (facing) {
            case UP:
                return this.canPlaceOn(world.getBlockState(blockPos.offsetUp()).getBlock());
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                return this.canPlaceOn(
                        world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock());
            default:
                return false;
        }
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
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockVine.UP, false)
                        .withProperty(net.minecraft.block.BlockVine.NORTH, false)
                        .withProperty(net.minecraft.block.BlockVine.EAST, false)
                        .withProperty(net.minecraft.block.BlockVine.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockVine.WEST, false);
        return facing.getAxis().isHorizontal()
                ? blockState.withProperty(getPropertyFor(facing.getOpposite()), true)
                : blockState;
    }

    private boolean recheckGrownSides(World world, BlockPos blockPos, BlockState blockState) {
        BlockState blockStateValue = blockState;
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Iterator iterator = FacingPlane.HORIZONTAL.iterator();
        int i = j;

        while (iterator.hasNext()) {
            Facing facing = (Facing) iterator.next();
            PropertyBool propertyBool = getPropertyFor(facing);
            if ((Boolean) blockState.getValue(propertyBool)
                    && !this.canPlaceOn(world.getBlockState(blockPos.offset(facing)).getBlock())) {
                BlockState blockStateValue2 = world.getBlockState(blockPos.offsetUp());
                if (blockStateValue2.getBlock() != this
                        || !(Boolean) blockStateValue2.getValue(propertyBool)) {
                    blockState = blockState.withProperty(propertyBool, false);
                }
            }

            if (i != 0) {
                break;
            }
        }

        if (getNumGrownFaces(blockState) == 0) {
            return false;
        }

        if (blockStateValue != blockState) {
            world.setBlockState(blockPos, blockState, 2);
        }

        return true;
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int p171_2) {
        return iBlockAccess.getBiomeGenForCoords(blockPos).getFoliageColorAtPos(blockPos);
    }

    public BlockState getStateFromMeta(int p171_0) {
        net.minecraft.block.state.BlockState blockState;
        net.minecraft.block.properties.PropertyBool propertyBool;
        byte b0;
        label52:
        {
            try {
                blockState = this.getDefaultState();
                propertyBool = net.minecraft.block.BlockVine.NORTH;
                if ((p171_0 & net.minecraft.block.BlockVine.field_176276_R) > 0) {
                    b0 = 1;
                    break label52;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception3) {
                throw net.minecraft.block.BlockVine.method_9a70918140b0a7642dba(
                        illegalargumentexception3);
            }

            b0 = 0;
        }

        label45:
        {
            try {
                blockState =
                        blockState.withProperty(propertyBool, java.lang.Boolean.valueOf((b0 != 0)));
                propertyBool = net.minecraft.block.BlockVine.EAST;
                if ((p171_0 & net.minecraft.block.BlockVine.field_176275_S) > 0) {
                    b0 = 1;
                    break label45;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception2) {
                throw net.minecraft.block.BlockVine.method_9a70918140b0a7642dba(
                        illegalargumentexception2);
            }

            b0 = 0;
        }

        label38:
        {
            try {
                blockState =
                        blockState.withProperty(propertyBool, java.lang.Boolean.valueOf((b0 != 0)));
                propertyBool = net.minecraft.block.BlockVine.SOUTH;
                if ((p171_0 & net.minecraft.block.BlockVine.field_176272_Q) > 0) {
                    b0 = 1;
                    break label38;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
                throw net.minecraft.block.BlockVine.method_9a70918140b0a7642dba(
                        illegalargumentexception1);
            }

            b0 = 0;
        }

        try {
            blockState =
                    blockState.withProperty(propertyBool, java.lang.Boolean.valueOf((b0 != 0)));
            propertyBool = net.minecraft.block.BlockVine.WEST;
            if ((p171_0 & net.minecraft.block.BlockVine.field_176271_T) > 0) {
                return blockState.withProperty(net.minecraft.block.BlockVine.WEST, true);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw net.minecraft.block.BlockVine.method_9a70918140b0a7642dba(
                    illegalargumentexception);
        }

        return blockState.withProperty(propertyBool, false);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState.withProperty(
                net.minecraft.block.BlockVine.UP,
                iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock().isSolidFullCube());
    }

    public int getRenderColor(BlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    private static int func_176270_b(Facing facing) {
        return 1 << facing.getHorizontalIndex();
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public static PropertyBool getPropertyFor(Facing facing) {
        switch (facing) {
            case UP:
                return net.minecraft.block.BlockVine.UP;
            case NORTH:
                return net.minecraft.block.BlockVine.NORTH;
            case SOUTH:
                return net.minecraft.block.BlockVine.SOUTH;
            case EAST:
                return net.minecraft.block.BlockVine.EAST;
            case WEST:
                return net.minecraft.block.BlockVine.WEST;
            default:
                throw new IllegalArgumentException(facing + " is an invalid choice");
        }
    }

    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    public int getMetaFromState(BlockState blockState) {
        int i = 0;
        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockVine.NORTH)) {
            i |= net.minecraft.block.BlockVine.field_176276_R;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockVine.EAST)) {
            i |= net.minecraft.block.BlockVine.field_176275_S;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockVine.SOUTH)) {
            i |= net.minecraft.block.BlockVine.field_176272_Q;
        }

        if ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockVine.WEST)) {
            i |= net.minecraft.block.BlockVine.field_176271_T;
        }

        return i;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote && world.rand.nextInt(4) == 0) {
            byte b0 = 4;
            int j = 5;
            byte b1 = 0;
            int k = -b0;

            label242:
            while (k <= b0) {
                int l = -b0;

                while (l <= b0) {
                    int i1 = -1;

                    while (i1 <= 1) {
                        if (world.getBlockState(blockPos.add(k, i1, l)).getBlock() == this) {
                            if (--j <= 0) {
                                b1 = 1;
                                if (i == 0) {
                                    break label242;
                                }
                            }
                        }

                        i1++;
                        if (i != 0) {
                            break;
                        }
                    }

                    l++;
                    if (i != 0) {
                        break;
                    }
                }

                k++;
                if (i != 0) {
                    break;
                }
            }

            Facing facing = Facing.random(random);
            if (facing == Facing.UP
                    && blockPos.getY() < 255
                    && world.isAirBlock(blockPos.offsetUp())) {
                if (b1 != 0) {
                    return;
                }

                BlockState blockStateValue = blockState;

                for (java.util.Iterator<?> __recoveryIterator171_41844 =
                                (FacingPlane.HORIZONTAL).iterator();
                        (__recoveryIterator171_41844).hasNext(); ) {
                    Facing facingValue = (Facing) (__recoveryIterator171_41844).next();
                    if (random.nextBoolean()
                            || !this.canPlaceOn(
                                    world.getBlockState(blockPos.offset(facingValue).offsetUp())
                                            .getBlock())) {
                        blockStateValue =
                                blockStateValue.withProperty(getPropertyFor(facingValue), false);
                        if (i != 0) {
                            break;
                        }
                    }
                }

                if ((Boolean) blockStateValue.getValue(net.minecraft.block.BlockVine.NORTH)
                        || (Boolean) blockStateValue.getValue(net.minecraft.block.BlockVine.EAST)
                        || (Boolean) blockStateValue.getValue(net.minecraft.block.BlockVine.SOUTH)
                        || (Boolean) blockStateValue.getValue(net.minecraft.block.BlockVine.WEST)) {
                    world.setBlockState(blockPos.offsetUp(), blockStateValue, 2);
                }

                if (i == 0) {
                    return;
                }
            }

            if (facing.getAxis().isHorizontal()
                    && !(Boolean) blockState.getValue(getPropertyFor(facing))) {
                if (b1 != 0) {
                    return;
                }

                label188:
                {
                    BlockPos blockPosValue = blockPos.offset(facing);
                    Block block = world.getBlockState(blockPosValue).getBlock();
                    if (block.blockMaterial == Material.AIR) {
                        label255:
                        {
                            Facing facingValue2 = facing.rotateY();
                            Facing facingValue3 = facing.rotateYCCW();
                            boolean value =
                                    (Boolean) blockState.getValue(getPropertyFor(facingValue2));
                            boolean valueValue =
                                    (Boolean) blockState.getValue(getPropertyFor(facingValue3));
                            BlockPos blockPosValue2 = blockPosValue.offset(facingValue2);
                            BlockPos blockPosValue3 = blockPosValue.offset(facingValue3);
                            if (value
                                    && this.canPlaceOn(
                                            world.getBlockState(blockPosValue2).getBlock())) {
                                world.setBlockState(
                                        blockPosValue,
                                        this.getDefaultState()
                                                .withProperty(getPropertyFor(facingValue2), true),
                                        2);
                                if (i == 0) {
                                    break label255;
                                }
                            }

                            if (valueValue
                                    && this.canPlaceOn(
                                            world.getBlockState(blockPosValue3).getBlock())) {
                                world.setBlockState(
                                        blockPosValue,
                                        this.getDefaultState()
                                                .withProperty(getPropertyFor(facingValue3), true),
                                        2);
                                if (i == 0) {
                                    break label255;
                                }
                            }

                            if (value
                                    && world.isAirBlock(blockPosValue2)
                                    && this.canPlaceOn(
                                            world.getBlockState(blockPos.offset(facingValue2))
                                                    .getBlock())) {
                                world.setBlockState(
                                        blockPosValue2,
                                        this.getDefaultState()
                                                .withProperty(
                                                        getPropertyFor(facing.getOpposite()), true),
                                        2);
                                if (i == 0) {
                                    break label255;
                                }
                            }

                            if (valueValue
                                    && world.isAirBlock(blockPosValue3)
                                    && this.canPlaceOn(
                                            world.getBlockState(blockPos.offset(facingValue3))
                                                    .getBlock())) {
                                world.setBlockState(
                                        blockPosValue3,
                                        this.getDefaultState()
                                                .withProperty(
                                                        getPropertyFor(facing.getOpposite()), true),
                                        2);
                                if (i == 0) {
                                    break label255;
                                }
                            }

                            if (this.canPlaceOn(
                                    world.getBlockState(blockPosValue.offsetUp()).getBlock())) {
                                world.setBlockState(blockPosValue, this.getDefaultState(), 2);
                            }
                        }

                        if (i == 0) {
                            break label188;
                        }
                    }

                    if (block.blockMaterial.isOpaque() && block.isFullCube()) {
                        world.setBlockState(
                                blockPos, blockState.withProperty(getPropertyFor(facing), true), 2);
                    }
                }

                if (i == 0) {
                    return;
                }
            }

            if (blockPos.getY() > 1) {
                BlockPos blockPosValue4 = blockPos.offsetDown();
                BlockState blockStateValue2 = world.getBlockState(blockPosValue4);
                Block blockValue = blockStateValue2.getBlock();
                if (blockValue.blockMaterial == Material.AIR) {
                    BlockState blockStateValue3 = blockState;

                    for (java.util.Iterator<?> __recoveryIterator171_49682 =
                                    (FacingPlane.HORIZONTAL).iterator();
                            (__recoveryIterator171_49682).hasNext(); ) {
                        Facing facingValue4 = (Facing) (__recoveryIterator171_49682).next();
                        if (random.nextBoolean()) {
                            blockStateValue3 =
                                    blockStateValue3.withProperty(
                                            getPropertyFor(facingValue4), false);
                            if (i != 0) {
                                break;
                            }
                        }
                    }

                    if (!(Boolean) blockStateValue3.getValue(net.minecraft.block.BlockVine.NORTH)
                            && !(Boolean)
                                    blockStateValue3.getValue(net.minecraft.block.BlockVine.EAST)
                            && !(Boolean)
                                    blockStateValue3.getValue(net.minecraft.block.BlockVine.SOUTH)
                            && !(Boolean)
                                    blockStateValue3.getValue(net.minecraft.block.BlockVine.WEST)) {
                        return;
                    }

                    world.setBlockState(blockPosValue4, blockStateValue3, 2);
                    if (i == 0) {
                        return;
                    }
                }

                if (blockValue == this) {
                    BlockState blockStateValue4 = blockStateValue2;

                    for (java.util.Iterator<?> __recoveryIterator171_51001 =
                                    (FacingPlane.HORIZONTAL).iterator();
                            (__recoveryIterator171_51001).hasNext(); ) {
                        Facing facingValue5 = (Facing) (__recoveryIterator171_51001).next();
                        PropertyBool propertyBool = getPropertyFor(facingValue5);
                        if (random.nextBoolean() || !(Boolean) blockState.getValue(propertyBool)) {
                            blockStateValue4 = blockStateValue4.withProperty(propertyBool, false);
                        }

                        if (i != 0) {
                            break;
                        }
                    }

                    if ((Boolean) blockStateValue4.getValue(net.minecraft.block.BlockVine.NORTH)
                            || (Boolean)
                                    blockStateValue4.getValue(net.minecraft.block.BlockVine.EAST)
                            || (Boolean)
                                    blockStateValue4.getValue(net.minecraft.block.BlockVine.SOUTH)
                            || (Boolean)
                                    blockStateValue4.getValue(net.minecraft.block.BlockVine.WEST)) {
                        world.setBlockState(blockPosValue4, blockStateValue4, 2);
                    }
                }
            }
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockVine.UP,
                    net.minecraft.block.BlockVine.NORTH,
                    net.minecraft.block.BlockVine.EAST,
                    net.minecraft.block.BlockVine.SOUTH,
                    net.minecraft.block.BlockVine.WEST
                });
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote
                && playerEntity.getCurrentEquippedItem() != null
                && playerEntity.getCurrentEquippedItem().getItem() == Items.shears) {
            playerEntity.triggerAchievement(
                    StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            spawnAsEntity(world, blockPos, new ItemStack(Blocks.vine, 1, 0));
            if (i == 0) {
                return;
            }
        }

        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity);
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isReplaceable(World world, BlockPos blockPos) {
        return true;
    }

    private boolean canPlaceOn(Block block) {
        return block.isFullCube() && block.blockMaterial.blocksMovement();
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!world.isRemote && !this.recheckGrownSides(world, blockPos, blockState)) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public static int getNumGrownFaces(BlockState blockState) {
        int j = 0;
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        PropertyBool[] aUpValue = net.minecraft.block.BlockVine.ALL_FACES;
        int i = k;

        for (PropertyBool propertyBool : aUpValue) {
            if ((Boolean) blockState.getValue(propertyBool)) {
                j++;
            }

            if (i == 0) {
                break;
            }
        }

        return j;
    }
}
