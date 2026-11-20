package net.minecraft.util;

import net.minecraft.entity.Entity;

public class BlockPos extends Vec3i {
    private static final int NUM_Y_BITS;
    private static Entity[] tW;
    private static final int NUM_X_BITS;
    private static long Y_MASK;
    private static long Z_MASK;
    private static final int NUM_Z_BITS;
    private static final int Y_SHIFT;
    public static final BlockPos ORIGIN;
    private static final int X_SHIFT;
    private static long X_MASK;

    public BlockPos offsetSouth() {
        return this.offsetSouth(1);
    }

    public BlockPos subtract(Vec3i vec3i) {
        return new BlockPos(
                this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
    }

    public BlockPos add(int i, int j, int k) {
        return new BlockPos(this.getX() + i, this.getY() + j, this.getZ() + k);
    }

    public BlockPos offsetNorth(int i) {
        return this.offset(Facing.NORTH, i);
    }

    public BlockPos offsetEast() {
        return this.offsetEast(1);
    }

    public BlockPos add(Vec3i vec3i) {
        return new BlockPos(
                this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
    }

    public BlockPos(Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }

    public BlockPos crossProductBP(Vec3i vec3i) {
        return new BlockPos(
                this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
                this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
                this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }

    public BlockPos offsetSouth(int i) {
        return this.offset(Facing.SOUTH, i);
    }

    public static void b(Entity[] entityArray) {
        tW = entityArray;
    }

    public BlockPos offsetEast(int i) {
        return this.offset(Facing.EAST, i);
    }

    public BlockPos offset(Facing facing, int i) {
        return new BlockPos(
                this.getX() + facing.getFrontOffsetX() * i,
                this.getY() + facing.getFrontOffsetY() * i,
                this.getZ() + facing.getFrontOffsetZ() * i);
    }

    public BlockPos offsetDown() {
        return this.offsetDown(1);
    }

    public BlockPos add(double d0, double d1, double d2) {
        return new BlockPos(this.getX() + d0, this.getY() + d1, this.getZ() + d2);
    }

    public BlockPos offsetWest(int i) {
        return this.offset(Facing.WEST, i);
    }

    public BlockPos offsetUp(int i) {
        return this.offset(Facing.UP, i);
    }

    public BlockPos offsetNorth() {
        return this.offsetNorth(1);
    }

    public static Entity[] eL() {
        return tW;
    }

    public BlockPos offsetDown(int i) {
        return this.offset(Facing.DOWN, i);
    }

    public BlockPos(Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static BlockPos fromLong(long i) {
        int j =
                (int)
                        (i
                                << 64 - net.minecraft.util.BlockPos.X_SHIFT - net.minecraft.util.BlockPos.NUM_X_BITS
                                >> 64 - net.minecraft.util.BlockPos.NUM_X_BITS);
        int k =
                (int)
                        (i
                                << 64 - net.minecraft.util.BlockPos.Y_SHIFT - net.minecraft.util.BlockPos.NUM_Y_BITS
                                >> 64 - net.minecraft.util.BlockPos.NUM_Y_BITS);
        int l = (int) (i << 64 - net.minecraft.util.BlockPos.NUM_Z_BITS >> 64 - net.minecraft.util.BlockPos.NUM_Z_BITS);
        return new BlockPos(j, k, l);
    }

    public BlockPos offsetWest() {
        return this.offsetWest(1);
    }

    public BlockPos offsetUp() {
        return this.offsetUp(1);
    }

    public long toLong() {
        return (this.getX() & net.minecraft.util.BlockPos.X_MASK) << net.minecraft.util.BlockPos.X_SHIFT
                | (this.getY() & net.minecraft.util.BlockPos.Y_MASK) << net.minecraft.util.BlockPos.Y_SHIFT
                | (this.getZ() & net.minecraft.util.BlockPos.Z_MASK) << 0;
    }

    // region Static initialization and string tables
    static {
        ORIGIN = new BlockPos(0, 0, 0);
        NUM_X_BITS = 1 + MathHelper.floorLog2(MathHelper.roundUpToPowerOfTwo(30000000));
        NUM_Z_BITS = net.minecraft.util.BlockPos.NUM_X_BITS;
        NUM_Y_BITS = 64 - BlockPos.NUM_X_BITS - BlockPos.NUM_Z_BITS;
        Y_SHIFT = 0 + net.minecraft.util.BlockPos.NUM_Z_BITS;
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[4];
        X_SHIFT = net.minecraft.util.BlockPos.Y_SHIFT + net.minecraft.util.BlockPos.NUM_Y_BITS;

        long i = 1L << net.minecraft.util.BlockPos.NUM_X_BITS;

        net.minecraft.util.BlockPos.X_MASK = i - 1L;

        i = 1L << net.minecraft.util.BlockPos.NUM_Y_BITS;

        net.minecraft.util.BlockPos.Y_MASK = i - 1L;

        i = 1L << net.minecraft.util.BlockPos.NUM_Z_BITS;

        net.minecraft.util.BlockPos.Z_MASK = i - 1L;
        b(aclass_b6df81f333fa31dc98ea);
    }

    // endregion

    public BlockPos(int i, int j, int k) {
        super(i, j, k);
    }

    public static Iterable getAllInBoxMutable(BlockPos blockPos, BlockPos blockPosValue) {
        BlockPos blockPosValue2 =
                new BlockPos(
                        Math.min(blockPos.getX(), blockPosValue.getX()),
                        Math.min(blockPos.getY(), blockPosValue.getY()),
                        Math.min(blockPos.getZ(), blockPosValue.getZ()));
        BlockPos blockPosValue3 =
                new BlockPos(
                        Math.max(blockPos.getX(), blockPosValue.getX()),
                        Math.max(blockPos.getY(), blockPosValue.getY()),
                        Math.max(blockPos.getZ(), blockPosValue.getZ()));
        return new MutableBlockBoxIterable(blockPosValue2, blockPosValue3);
    }

    public static Iterable getAllInBox(BlockPos blockPos, BlockPos blockPosValue) {
        BlockPos blockPosValue2 =
                new BlockPos(
                        Math.min(blockPos.getX(), blockPosValue.getX()),
                        Math.min(blockPos.getY(), blockPosValue.getY()),
                        Math.min(blockPos.getZ(), blockPosValue.getZ()));
        BlockPos blockPosValue3 =
                new BlockPos(
                        Math.max(blockPos.getX(), blockPosValue.getX()),
                        Math.max(blockPos.getY(), blockPosValue.getY()),
                        Math.max(blockPos.getZ(), blockPosValue.getZ()));
        return new BlockBoxIterable(blockPosValue2, blockPosValue3);
    }

    public BlockPos(Vec3 vec3) {
        this(vec3.x, vec3.y, vec3.z);
    }

    public BlockPos multiply(int i) {
        return new BlockPos(this.getX() * i, this.getY() * i, this.getZ() * i);
    }

    public BlockPos(double d0, double d1, double d2) {
        super(d0, d1, d2);
    }

    public Vec3i crossProduct(Vec3i vec3i) {
        return this.crossProductBP(vec3i);
    }

    public BlockPos offset(Facing facing) {
        return this.offset(facing, 1);
    }
}
