package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

import java.util.List;

public final class EncodedStageBlock {
    private EncodedStageBlock encodedStageBlock;
    private static boolean legacyBlockFlow;
    private List<ByteTransformNode> list;
    private boolean reversed;

    public void setEncodedStageBlock(EncodedStageBlock bWjValue) {
        this.encodedStageBlock = bWjValue;
    }

    public EncodedStageBlock(
            EncodedStageBlock encodedStageBlockValue, List<ByteTransformNode> list, boolean flag) {
        super();
        boolean initialFlowFlag = isLegacyBlockFlow();

        boolean flag2 = initialFlowFlag;

        this.encodedStageBlock = encodedStageBlockValue;
        boolean flag1 = flag2;
        this.list = list;
        this.reversed = flag;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyBlockFlow(!flag1);
        }
    }

    public static boolean isLegacyBlockFlow() {
        return remix.compat.legacy.codec.EncodedStageBlock.legacyBlockFlow;
    }

    public EncodedStageBlock(List<ByteTransformNode> list, boolean flag) {
        boolean flag2 = isLegacyBlockFlow();
        this.list = list;
        this.reversed = flag;
        boolean flag1 = flag2;
        this.encodedStageBlock = null;
        if (flag1) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    public static void setLegacyBlockFlow(boolean flag) {
        remix.compat.legacy.codec.EncodedStageBlock.legacyBlockFlow = flag;
    }

    static {
        if (!isStandardBlockFlow()) {
            setLegacyBlockFlow(true);
        }
    }

    public static boolean isStandardBlockFlow() {
        boolean flag = isLegacyBlockFlow();
        return !flag;
    }

    public boolean isReversed() {
        return this.reversed;
    }

    public EncodedStageBlock getEncodedStageBlock() {
        return this.encodedStageBlock;
    }

    public List<ByteTransformNode> getList() {
        return this.list;
    }
}
