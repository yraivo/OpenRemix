package net.minecraft.crash;

import java.util.concurrent.Callable;

final class BlockMetadataCrashDetail implements Callable {

    private static String[] packedTextFragments;

    int field_85063_a;

    BlockMetadataCrashDetail(int i) {
        this.field_85063_a = i;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        if (this.field_85063_a < 0) {
            return "Unknown? (Got " + this.field_85063_a + packedTextFragments[0];
        }

        String text =
                String.format("%4s", Integer.toBinaryString(this.field_85063_a))
                        .replace(packedTextFragments[1], packedTextFragments[2]);
        return String.format("%1$d / 0x%1$X / 0b%2$s", this.field_85063_a, text);
    }

    static {
        net.minecraft.crash.BlockMetadataCrashDetail.packedTextFragments = new String[4];
        net.minecraft.crash.BlockMetadataCrashDetail.packedTextFragments[0] = new String(")");
        net.minecraft.crash.BlockMetadataCrashDetail.packedTextFragments[1] = new String(" ");
        net.minecraft.crash.BlockMetadataCrashDetail.packedTextFragments[2] = new String("0");
    }
}
