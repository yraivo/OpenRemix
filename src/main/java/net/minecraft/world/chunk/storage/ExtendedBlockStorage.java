package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;
import optifine.Reflector;

import java.util.List;

public class ExtendedBlockStorage {
    private NibbleArray skylightArray;
    private int tickRefCount;
    private NibbleArray blocklightArray;
    private int yBase;
    private static String g;
    private int blockRefCount;
    private static String[] packedTextFragments;
    private char[] data;

    public int getExtBlockMetadata(int i, int j, int k) {
        BlockState blockState = this.get(i, j, k);
        return blockState.getBlock().getMetaFromState(blockState);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.chunk.storage.ExtendedBlockStorage.packedTextFragments[0] = new String("diTdob");
    }

    // endregion

    public int getExtSkylightValue(int i, int j, int k) {
        return this.skylightArray.get(i, j, k);
    }

    public void setSkylightArray(NibbleArray bJdValue) {
        this.skylightArray = bJdValue;
    }

    public static String cJ() {
        return g;
    }

    public void setExtBlocklightValue(int i, int j, int k, int l) {
        this.blocklightArray.set(i, j, k, l);
    }

    // region String decoding support

    // endregion

    public ExtendedBlockStorage(int yBaseValue, boolean flag) {
        this.yBase = yBaseValue;
        this.data = new char[4096];
        this.blocklightArray = new NibbleArray();
        if (flag) {
            this.skylightArray = new NibbleArray();
        }
    }

    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }

    public NibbleArray getBlocklightArray() {
        return this.blocklightArray;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (cJ() == null) {
            b(packedTextFragments[0]);
        }
    }

    public int getExtBlocklightValue(int i, int j, int k) {
        return this.blocklightArray.get(i, j, k);
    }

    public static void b(String text) {
        g = text;
    }

    public boolean getNeedsRandomTick() {
        return this.tickRefCount > 0;
    }

    public NibbleArray getSkylightArray() {
        return this.skylightArray;
    }

    public void setExtSkylightValue(int i, int j, int k, int l) {
        this.skylightArray.set(i, j, k, l);
    }

    public void l() {
        String text = cJ();
        List objectList = Block.BLOCK_STATE_IDS.getObjectList();
        int i = objectList.size();
        String textValue = text;
        int j = 0;
        int k = 0;
        int l = 0;

        label41:
        while (l < 16) {
            int i1 = l << 8;
            int j1 = 0;

            while (true) {
                if (j1 < 16) {
                    int k1 = i1 | j1 << 4;
                    int l1 = 0;

                    while (l1 < 16) {
                        char c0 = this.data[k1 | l1];
                        if (c0 > 0) {
                            j++;
                            if (c0 < i) {
                                BlockState blockState = (BlockState) objectList.get(c0);
                                if (blockState != null) {
                                    Block block = blockState.getBlock();
                                    if (block.getTickRandomly()) {
                                        k++;
                                    }
                                }
                            }
                        }

                        l1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    j1++;
                    if (textValue != null) {
                        continue;
                    }
                }

                l++;
                if (textValue == null) {
                    break label41;
                }
                break;
            }
        }

        this.blockRefCount = j;
        this.tickRefCount = k;
    }

    public char[] getData() {
        return this.data;
    }

    public BlockState get(int i, int j, int k) {
        BlockState blockState =
                (BlockState) Block.BLOCK_STATE_IDS.getByValue(this.data[j << 8 | k << 4 | i]);
        return blockState != null ? blockState : Blocks.air.getDefaultState();
    }

    public void set(int i, int j, int k, BlockState blockState) {
        if (Reflector.GL.isInstance(blockState)) {
            blockState = (BlockState) Reflector.call(blockState, Reflector.HT, new Object[0]);
        }

        BlockState blockStateValue = this.get(i, j, k);
        Block block = blockStateValue.getBlock();
        Block blockValue = blockState.getBlock();
        if (block != Blocks.air) {
            this.blockRefCount--;
            if (block.getTickRandomly()) {
                this.tickRefCount--;
            }
        }

        if (blockValue != Blocks.air) {
            this.blockRefCount++;
            if (blockValue.getTickRandomly()) {
                this.tickRefCount++;
            }
        }

        this.data[j << 8 | k << 4 | i] = (char) Block.BLOCK_STATE_IDS.get(blockState);
    }

    public void setData(char[] achar) {
        this.data = achar;
    }

    public Block getBlockByExtId(int i, int j, int k) {
        return this.get(i, j, k).getBlock();
    }

    public int getYLocation() {
        return this.yBase;
    }

    public void setBlocklightArray(NibbleArray bJeValue) {
        this.blocklightArray = bJeValue;
    }
}
