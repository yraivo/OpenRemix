package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenerator {
    private boolean doBlockNotify;
    private static String b;
    private static String[] packedTextFragments;

    public WorldGenerator(boolean doBlockNotifyValue) {
        this.doBlockNotify = doBlockNotifyValue;
    }

    public static void b(String text) {
        b = text;
    }

    protected void func_175906_a(World world, BlockPos blockPos, Block block) {
        this.func_175905_a(world, blockPos, block, 0);
    }

    public void func_175904_e() {}

    // region String decoding support

    // endregion

    protected void setBlockAndNotifyAdequately(
            World world, BlockPos blockPos, BlockState blockState) {
        String text = method_c60693f457aa70be7160();
        if (this.doBlockNotify) {
            world.setBlockState(blockPos, blockState, 3);
            if (text != null) {
                return;
            }
        }

        world.setBlockState(blockPos, blockState, 2);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.gen.feature.WorldGenerator.packedTextFragments[0] = new String("QwIHK");
    }

    // endregion

    public WorldGenerator() {
        this(false);
    }

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() == null) {
            b(packedTextFragments[0]);
        }
    }

    public abstract boolean generate(World world, Random random, BlockPos blockPos);

    protected void func_175905_a(World world, BlockPos blockPos, Block block, int i) {
        this.setBlockAndNotifyAdequately(world, blockPos, block.getStateFromMeta(i));
    }
}
