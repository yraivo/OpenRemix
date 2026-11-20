package net.minecraft.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class StructureComponent {

    private static String[] packedTextFragments;

    protected int componentType;
    public StructureBoundingBox boundingBox;
    private static int dD;
    protected Facing coordBaseMode;

    protected void randomlyPlaceBlock(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            float f,
            int i,
            int j,
            int k,
            BlockState blockState) {
        if (random.nextFloat() < f) {
            this.setBlockState(world, blockState, i, j, k, structureBoundingBox);
        }
    }

    protected StructureComponent(int componentTypeValue) {
        this.componentType = componentTypeValue;
    }

    public static int cR() {
        return dD;
    }

    public BlockPos getBoundingBoxCenter() {
        return new BlockPos(this.boundingBox.getCenter());
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {}

    public void readStructureBaseNBT(World world, CompoundTag compoundTag) {
        if (compoundTag.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(compoundTag.getIntArray("BB"));
        }

        int integer = compoundTag.getInteger(packedTextFragments[1]);
        this.coordBaseMode = integer == -1 ? null : Facing.getHorizontal(integer);
        this.componentType = compoundTag.getInteger("GD");
        this.readStructureFromNBT(compoundTag);
    }

    public static int f() {
        int i = cR();
        return i == 0 ? 107 : 0;
    }

    protected abstract void writeStructureToNBT(CompoundTag compoundTag);

    protected abstract void readStructureFromNBT(CompoundTag compoundTag);

    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    protected void fillWithBlocks(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            BlockState blockState,
            BlockState blockStateValue,
            boolean flag) {
        int k2 = f();
        int l1 = j;
        int k1 = k2;

        while (l1 <= i1) {
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    int j2 = k;

                    while (j2 <= j1) {
                        if (!flag
                                || this.getBlockStateFromPos(
                                                        world, i2, l1, j2, structureBoundingBox)
                                                .getBlock()
                                                .getMaterial()
                                        != Material.AIR) {
                            if (l1 != j && l1 != i1 && i2 != i && i2 != l && j2 != k && j2 != j1) {
                                this.setBlockState(
                                        world, blockStateValue, i2, l1, j2, structureBoundingBox);
                                if (!(k1 == 0)) {

                                    this.setBlockState(
                                            world, blockState, i2, l1, j2, structureBoundingBox);
                                }
                            } else {

                                this.setBlockState(
                                        world, blockState, i2, l1, j2, structureBoundingBox);
                            }
                        }

                        j2++;
                        if (k1 != 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 == 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 != 0) {
                    return;
                }
                break;
            }
        }
    }

    public abstract boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox);

    static {
        net.minecraft.world.gen.structure.StructureComponent.packedTextFragments = new String[4];
        net.minecraft.world.gen.structure.StructureComponent.packedTextFragments[0] =
                new String("O");
        net.minecraft.world.gen.structure.StructureComponent.packedTextFragments[1] =
                new String("O");
        b(69);
    }

    protected void fillWithAir(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1) {
        int k2 = f();
        int l1 = j;
        int k1 = k2;

        while (l1 <= i1) {
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    int j2 = k;

                    while (j2 <= j1) {
                        this.setBlockState(
                                world,
                                Blocks.air.getDefaultState(),
                                i2,
                                l1,
                                j2,
                                structureBoundingBox);
                        j2++;
                        if (k1 != 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 == 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 != 0) {
                    return;
                }
                break;
            }
        }
    }

    public StructureComponent() {}

    public static StructureComponent findIntersecting(
            List list, StructureBoundingBox structureBoundingBox) {
        for (java.util.Iterator<?> __recoveryIterator171_30273 = (list).iterator();
                (__recoveryIterator171_30273).hasNext(); ) {
            StructureComponent structureComponent =
                    (StructureComponent) (__recoveryIterator171_30273).next();
            if (structureComponent.getBoundingBox() != null
                    && structureComponent.getBoundingBox().intersectsWith(structureBoundingBox)) {
                return structureComponent;
            }
        }

        return null;
    }

    protected void setBlockState(
            World world,
            BlockState blockState,
            int i,
            int j,
            int k,
            StructureBoundingBox structureBoundingBox) {
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        if (structureBoundingBox.isVecInside(blockPos)) {
            world.setBlockState(blockPos, blockState, 2);
        }
    }

    public static void b(int i) {
        dD = i;
    }

    // region String decoding support

    // endregion

    protected void clearCurrentPositionBlocksUpwards(
            World world, int i, int j, int k, StructureBoundingBox structureBoundingBox) {
        int i1 = f();
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        int l = i1;
        if (structureBoundingBox.isVecInside(blockPos)) {
            while (!world.isAirBlock(blockPos) && blockPos.getY() < 255) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState(), 2);
                blockPos = blockPos.offsetUp();
                if (l != 0) {
                    break;
                }
            }
        }
    }

    protected int getYWithOffset(int i) {
        return this.coordBaseMode == null ? i : i + this.boundingBox.minY;
    }

    public CompoundTag createStructureBaseNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setString("id", MapGenStructureIO.getStructureComponentName(this));
        compoundTag.setTag("BB", this.boundingBox.toNBTTagIntArray());
        compoundTag.setInteger(
                packedTextFragments[0],
                this.coordBaseMode == null ? -1 : this.coordBaseMode.getHorizontalIndex());
        compoundTag.setInteger("GD", this.componentType);
        this.writeStructureToNBT(compoundTag);
        return compoundTag;
    }

    // region String decoding support

    // endregion

    protected int getMetadataWithOffset(Block block, int i) {
        int j = cR();
        if (block == Blocks.rail) {
            if (this.coordBaseMode == Facing.WEST || this.coordBaseMode == Facing.EAST) {
                if (i == 1) {
                    return 0;
                }

                return 1;
            }
        } else if (block instanceof BlockDoor) {
            if (this.coordBaseMode == Facing.SOUTH) {
                if (i == 0) {
                    return 2;
                }

                if (i == 2) {
                    return 0;
                }
            } else {
                if (this.coordBaseMode == Facing.WEST) {
                    return i + 1 & 3;
                }

                if (this.coordBaseMode == Facing.EAST) {
                    return i + 3 & 3;
                }
            }
        } else {
            if (block != Blocks.stone_stairs
                    && block != Blocks.oak_stairs
                    && block != Blocks.nether_brick_stairs
                    && block != Blocks.stone_brick_stairs
                    && block != Blocks.sandstone_stairs) {
                if (block == Blocks.ladder) {
                    if (this.coordBaseMode == Facing.SOUTH) {
                        if (i == Facing.NORTH.getIndex()) {
                            return Facing.SOUTH.getIndex();
                        }

                        if (i == Facing.SOUTH.getIndex()) {
                            return Facing.NORTH.getIndex();
                        }

                        return i;
                    } else {
                        if (this.coordBaseMode == Facing.WEST) {
                            if (i == Facing.NORTH.getIndex()) {
                                return Facing.WEST.getIndex();
                            }

                            if (i == Facing.SOUTH.getIndex()) {
                                return Facing.EAST.getIndex();
                            }

                            if (i == Facing.WEST.getIndex()) {
                                return Facing.NORTH.getIndex();
                            }

                            if (i == Facing.EAST.getIndex()) {
                                return Facing.SOUTH.getIndex();
                            }
                        } else if (this.coordBaseMode == Facing.EAST) {
                            if (i == Facing.NORTH.getIndex()) {
                                return Facing.EAST.getIndex();
                            }

                            if (i == Facing.SOUTH.getIndex()) {
                                return Facing.WEST.getIndex();
                            }

                            if (i == Facing.WEST.getIndex()) {
                                return Facing.NORTH.getIndex();
                            }

                            if (i == Facing.EAST.getIndex()) {
                                return Facing.SOUTH.getIndex();
                            }

                            return i;
                        }

                        return i;
                    }
                }

                if (block == Blocks.stone_button) {
                    if (this.coordBaseMode == Facing.SOUTH) {
                        if (i == 3) {
                            return 4;
                        }

                        if (i == 4) {
                            return 3;
                        }

                        return i;
                    } else {
                        if (this.coordBaseMode == Facing.WEST) {
                            if (i == 3) {
                                return 1;
                            }

                            if (i == 4) {
                                return 2;
                            }

                            if (i == 2) {
                                return 3;
                            }

                            if (i == 1) {
                                return 4;
                            }
                        } else if (this.coordBaseMode == Facing.EAST) {
                            if (i == 3) {
                                return 2;
                            }

                            if (i == 4) {
                                return 1;
                            }

                            if (i == 2) {
                                return 3;
                            }

                            if (i == 1) {
                                return 4;
                            }

                            return i;
                        }

                        return i;
                    }
                }

                if (block != Blocks.tripwire_hook && !(block instanceof BlockDirectional)) {
                    if (block == Blocks.piston
                            || block == Blocks.sticky_piston
                            || block == Blocks.lever
                            || block == Blocks.dispenser) {
                        if (this.coordBaseMode == Facing.SOUTH) {
                            if (i == Facing.NORTH.getIndex() || i == Facing.SOUTH.getIndex()) {
                                return Facing.getFront(i).getOpposite().getIndex();
                            }

                            return i;
                        } else {
                            if (this.coordBaseMode == Facing.WEST) {
                                if (i == Facing.NORTH.getIndex()) {
                                    return Facing.WEST.getIndex();
                                }

                                if (i == Facing.SOUTH.getIndex()) {
                                    return Facing.EAST.getIndex();
                                }

                                if (i == Facing.WEST.getIndex()) {
                                    return Facing.NORTH.getIndex();
                                }

                                if (i == Facing.EAST.getIndex()) {
                                    return Facing.SOUTH.getIndex();
                                }
                            } else if (this.coordBaseMode == Facing.EAST) {
                                if (i == Facing.NORTH.getIndex()) {
                                    return Facing.EAST.getIndex();
                                }

                                if (i == Facing.SOUTH.getIndex()) {
                                    return Facing.WEST.getIndex();
                                }

                                if (i == Facing.WEST.getIndex()) {
                                    return Facing.NORTH.getIndex();
                                }

                                if (i == Facing.EAST.getIndex()) {
                                    return Facing.SOUTH.getIndex();
                                }

                                return i;
                            }

                            return i;
                        }
                    }

                    return i;
                }

                Facing facing = Facing.getHorizontal(i);
                if (this.coordBaseMode == Facing.SOUTH) {
                    if (facing == Facing.SOUTH || facing == Facing.NORTH) {
                        return facing.getOpposite().getHorizontalIndex();
                    }
                } else if (this.coordBaseMode == Facing.WEST) {
                    if (facing == Facing.NORTH) {
                        return Facing.WEST.getHorizontalIndex();
                    }

                    if (facing == Facing.SOUTH) {
                        return Facing.EAST.getHorizontalIndex();
                    }

                    if (facing == Facing.WEST) {
                        return Facing.NORTH.getHorizontalIndex();
                    }

                    if (facing == Facing.EAST) {
                        return Facing.SOUTH.getHorizontalIndex();
                    }
                } else if (this.coordBaseMode == Facing.EAST) {
                    if (facing == Facing.NORTH) {
                        return Facing.EAST.getHorizontalIndex();
                    }

                    if (facing == Facing.SOUTH) {
                        return Facing.WEST.getHorizontalIndex();
                    }

                    if (facing == Facing.WEST) {
                        return Facing.NORTH.getHorizontalIndex();
                    }

                    if (facing == Facing.EAST) {
                        return Facing.SOUTH.getHorizontalIndex();
                    }
                }

                if (j != 0) {
                    return i;
                }
            }

            if (this.coordBaseMode == Facing.SOUTH) {
                if (i == 2) {
                    return 3;
                }

                if (i == 3) {
                    return 2;
                }
            } else if (this.coordBaseMode == Facing.WEST) {
                if (i == 0) {
                    return 2;
                }

                if (i == 1) {
                    return 3;
                }

                if (i == 2) {
                    return 0;
                }

                if (i == 3) {
                    return 1;
                }
            } else if (this.coordBaseMode == Facing.EAST) {
                if (i == 0) {
                    return 2;
                }

                if (i == 1) {
                    return 3;
                }

                if (i == 2) {
                    return 1;
                }

                if (i == 3) {
                    return 0;
                }
            }
        }

        return i;
    }

    // region String decoding support

    // endregion

    protected BlockState getBlockStateFromPos(
            World world, int i, int j, int k, StructureBoundingBox structureBoundingBox) {
        int l = this.getXWithOffset(i, k);
        int yWithOffset = this.getYWithOffset(j);
        int j1 = this.getZWithOffset(i, k);
        return !structureBoundingBox.isVecInside(new BlockPos(l, yWithOffset, j1))
                ? Blocks.air.getDefaultState()
                : world.getBlockState(new BlockPos(l, yWithOffset, j1));
    }

    protected void placeDoorCurrentPosition(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            int i,
            int j,
            int k,
            Facing facing) {
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        if (structureBoundingBox.isVecInside(blockPos)) {
            ItemDoor.placeDoor(world, blockPos, facing.rotateYCCW(), Blocks.oak_door);
        }
    }

    public int getComponentType() {
        return this.componentType;
    }

    protected void fillWithRandomizedBlocks(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            boolean flag,
            Random random,
            StructureComponentBlockSelector structureComponentBlockSelector) {
        int k2 = cR();
        int l1 = j;
        int k1 = k2;

        while (l1 <= i1) {
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    int j2 = k;

                    while (j2 <= j1) {
                        if (!flag
                                || this.getBlockStateFromPos(
                                                        world, i2, l1, j2, structureBoundingBox)
                                                .getBlock()
                                                .getMaterial()
                                        != Material.AIR) {
                            structureComponentBlockSelector.selectBlocks(
                                    random,
                                    i2,
                                    l1,
                                    j2,
                                    l1 == j || l1 == i1 || i2 == i || i2 == l || j2 == k
                                            || j2 == j1);
                            this.setBlockState(
                                    world,
                                    structureComponentBlockSelector.getBlockState(),
                                    i2,
                                    l1,
                                    j2,
                                    structureBoundingBox);
                        }

                        j2++;
                        if (k1 == 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 != 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 == 0) {
                    return;
                }
                break;
            }
        }
    }

    protected int getXWithOffset(int i, int j) {
        if (this.coordBaseMode == null) {
            return i;
        }

        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                return this.boundingBox.minX + i;
            case WEST:
                return this.boundingBox.maxX - j;
            case EAST:
                return this.boundingBox.minX + j;
            default:
                return i;
        }
    }

    protected void randomlyRareFillWithBlocks(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            BlockState blockState,
            boolean flag) {
        int k2 = cR();
        float f = l - i + 1;
        float f1 = i1 - j + 1;
        float f2 = j1 - k + 1;
        float f3 = i + f / 2.0F;
        int k1 = k2;
        float f4 = k + f2 / 2.0F;
        int l1 = j;

        while (l1 <= i1) {
            float f5 = (l1 - j) / f1;
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    float f6 = (i2 - f3) / (f * 0.5F);
                    int j2 = k;

                    while (j2 <= j1) {
                        float f7 = (j2 - f4) / (f2 * 0.5F);
                        if (!flag
                                || this.getBlockStateFromPos(
                                                        world, i2, l1, j2, structureBoundingBox)
                                                .getBlock()
                                                .getMaterial()
                                        != Material.AIR) {
                            float f8 = f6 * f6 + f5 * f5 + f7 * f7;
                            if (f8 <= 1.05F) {
                                this.setBlockState(
                                        world, blockState, i2, l1, j2, structureBoundingBox);
                            }
                        }

                        j2++;
                        if (k1 == 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 != 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 == 0) {
                    return;
                }
                break;
            }
        }
    }

    protected boolean generateDispenserContents(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            int i,
            int j,
            int k,
            int l,
            List list,
            int i1) {
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        if (structureBoundingBox.isVecInside(blockPos)
                && world.getBlockState(blockPos).getBlock() != Blocks.dispenser) {
            world.setBlockState(
                    blockPos,
                    Blocks.dispenser.getStateFromMeta(
                            this.getMetadataWithOffset(Blocks.dispenser, l)),
                    2);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                WeightedRandomChestContent.generateDispenserContents(
                        random, list, (TileEntityDispenser) tileEntity, i1);
            }

            return true;
        } else {
            return false;
        }
    }

    protected boolean isLiquidInStructureBoundingBox(
            World world, StructureBoundingBox structureBoundingBox) {
        int j = Math.max(this.boundingBox.minX - 1, structureBoundingBox.minX);
        int k = Math.max(this.boundingBox.minY - 1, structureBoundingBox.minY);
        int l = Math.max(this.boundingBox.minZ - 1, structureBoundingBox.minZ);
        int i1 = Math.min(this.boundingBox.maxX + 1, structureBoundingBox.maxX);
        int j3 = f();
        int j1 = Math.min(this.boundingBox.maxY + 1, structureBoundingBox.maxY);
        int i = j3;
        int k1 = Math.min(this.boundingBox.maxZ + 1, structureBoundingBox.maxZ);

        label91:
        for (int l1 = j; l1 <= i1; l1++) {
            int i2 = l;

            while (true) {
                if (i2 <= k1) {
                    if (world.getBlockState(new BlockPos(l1, k, i2))
                            .getBlock()
                            .getMaterial()
                            .isLiquid()) {
                        return true;
                    }

                    if (world.getBlockState(new BlockPos(l1, j1, i2))
                            .getBlock()
                            .getMaterial()
                            .isLiquid()) {
                        return true;
                    }

                    i2++;
                    if (i == 0) {
                        continue;
                    }
                }
                break label91;
            }
        }

        label72:
        for (int j2 = j; j2 <= i1; j2++) {
            int l2 = k;

            while (true) {
                if (l2 <= j1) {
                    if (world.getBlockState(new BlockPos(j2, l2, l))
                            .getBlock()
                            .getMaterial()
                            .isLiquid()) {
                        return true;
                    }

                    if (world.getBlockState(new BlockPos(j2, l2, k1))
                            .getBlock()
                            .getMaterial()
                            .isLiquid()) {
                        return true;
                    }

                    l2++;
                    if (i == 0) {
                        continue;
                    }
                }
                break label72;
            }
        }

        for (int k2 = l; k2 <= k1; k2++) {
            for (int i3 = k; i3 <= j1; i3++) {
                if (world.getBlockState(new BlockPos(j, i3, k2))
                        .getBlock()
                        .getMaterial()
                        .isLiquid()) {
                    return true;
                }

                if (world.getBlockState(new BlockPos(i1, i3, k2))
                        .getBlock()
                        .getMaterial()
                        .isLiquid()) {
                    return true;
                }
                break;
            }

            return false;
        }

        return false;
    }

    protected void method_1b7f0bf882063fb4ee50(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            float f,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            BlockState blockState,
            BlockState blockStateValue,
            boolean flag) {
        int k2 = f();
        int l1 = j;
        int k1 = k2;

        while (l1 <= i1) {
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    int j2 = k;

                    while (j2 <= j1) {
                        if (random.nextFloat() <= f
                                && (!flag
                                        || this.getBlockStateFromPos(
                                                                world,
                                                                i2,
                                                                l1,
                                                                j2,
                                                                structureBoundingBox)
                                                        .getBlock()
                                                        .getMaterial()
                                                != Material.AIR)) {
                            if (l1 != j && l1 != i1 && i2 != i && i2 != l && j2 != k && j2 != j1) {
                                this.setBlockState(
                                        world, blockStateValue, i2, l1, j2, structureBoundingBox);
                                if (!(k1 == 0)) {

                                    this.setBlockState(
                                            world, blockState, i2, l1, j2, structureBoundingBox);
                                }
                            } else {

                                this.setBlockState(
                                        world, blockState, i2, l1, j2, structureBoundingBox);
                            }
                        }

                        j2++;
                        if (k1 != 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 == 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 != 0) {
                    return;
                }
                break;
            }
        }
    }

    protected boolean generateChestContents(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            int i,
            int j,
            int k,
            List list,
            int l) {
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        if (structureBoundingBox.isVecInside(blockPos)
                && world.getBlockState(blockPos).getBlock() != Blocks.chest) {
            BlockState blockState = Blocks.chest.getDefaultState();
            world.setBlockState(
                    blockPos, Blocks.chest.correctFacing(world, blockPos, blockState), 2);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                WeightedRandomChestContent.generateChestContents(
                        random, list, (TileEntityChest) tileEntity, l);
            }

            return true;
        } else {
            return false;
        }
    }

    protected void replaceAirAndLiquidDownwards(
            World world,
            BlockState blockState,
            int i,
            int j,
            int k,
            StructureBoundingBox structureBoundingBox) {
        int l1 = f();
        int xWithOffset = this.getXWithOffset(i, k);
        int l = l1;
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, j1, k1))) {
            while ((world.isAirBlock(new BlockPos(xWithOffset, j1, k1))
                            || world.getBlockState(new BlockPos(xWithOffset, j1, k1))
                                    .getBlock()
                                    .getMaterial()
                                    .isLiquid())
                    && j1 > 1) {
                world.setBlockState(new BlockPos(xWithOffset, j1, k1), blockState, 2);
                j1--;
                if (l != 0) {
                    break;
                }
            }
        }
    }

    protected int getZWithOffset(int i, int j) {
        if (this.coordBaseMode == null) {
            return j;
        }

        switch (this.coordBaseMode) {
            case NORTH:
                return this.boundingBox.maxZ - j;
            case SOUTH:
                return this.boundingBox.minZ + j;
            case WEST:
            case EAST:
                return this.boundingBox.minZ + i;
            default:
                return j;
        }
    }
}
