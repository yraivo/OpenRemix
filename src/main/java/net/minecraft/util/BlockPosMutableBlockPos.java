package net.minecraft.util;

public final class BlockPosMutableBlockPos extends BlockPos {
    public int z;
    public int y;
    public int x;

    public int getX() {
        return this.x;
    }

    @Override
    public Vec3i crossProduct(Vec3i vec3i) {
        return super.crossProductBP(vec3i);
    }

    public BlockPosMutableBlockPos(int p171_0, int p171_1, int p171_2, Object p171_3) {
        this(p171_0, p171_1, p171_2);
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    private BlockPosMutableBlockPos(int p171_0, int p171_1, int p171_2) {
        super(0, 0, 0);
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }
}
