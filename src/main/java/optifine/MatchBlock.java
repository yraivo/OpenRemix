package optifine;

import net.minecraft.block.state.BlockStateBase;

public class MatchBlock {
    private int blockId = -1;
    private static String[] packedTextFragments;
    private int[] metadatas = null;

    public boolean matches(BlockStateBase blockStateBase) {
        return blockStateBase.getBlockId() != this.blockId
                ? false
                : Matches.metadata(blockStateBase.getMetadata(), this.metadatas);
    }

    public int[] getMetadatas() {
        return this.metadatas;
    }

    public boolean matches(int i, int j) {
        return i != this.blockId ? false : Matches.metadata(j, this.metadatas);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        optifine.MatchBlock.packedTextFragments[0] = new String("");
        optifine.MatchBlock.packedTextFragments[1] = new String(":");
    }

    // endregion

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public MatchBlock(int blockIdValue, int[] aint) {
        this.blockId = blockIdValue;
        this.metadatas = aint;
    }

    public MatchBlock(int blockIdValue, int j) {
        this.blockId = blockIdValue;
        if (j >= 0 && j <= 15) {
            this.metadatas = new int[] {j};
        }
    }

    public void a(int i) {
        int j = ReflectorMethod.cR();
        if (this.metadatas != null && i >= 0 && i <= 15) {
            int k = 0;

            while (k < this.metadatas.length) {
                if (this.metadatas[k] == i) {
                    return;
                }

                k++;
                if (j == 0) {
                    break;
                }
            }

            this.metadatas = OptiFineConfig.addIntToArray(this.metadatas, i);
        }
    }

    public MatchBlock(int blockIdValue) {
        this.blockId = blockIdValue;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.blockId
                + packedTextFragments[1]
                + OptiFineConfig.arrayToString(this.metadatas);
    }

    // region String decoding support

    // endregion

    public int getBlockId() {
        return this.blockId;
    }
}
