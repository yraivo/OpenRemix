package net.minecraft.client.renderer;

public enum GlStateManagerTexGen {
    aQa(initializeConstantState(), 0, constantTextFragment(2), 0),
    aQg(constantTextFragment(4), 1, constantTextFragment(5), 1),
    aQf(constantTextFragment(7), 2, constantTextFragment(8), 2),
    aQb(constantTextFragment(10), 3, constantTextFragment(11), 3);
    private static GlStateManagerTexGen[] VALUES;

    private static GlStateManagerTexGen[] aQc;

    private static String[] packedTextFragments;

    static {
        aQc = new GlStateManagerTexGen[] {aQa, aQg, aQf, aQb};
        net.minecraft.client.renderer.GlStateManagerTexGen.VALUES =
                new GlStateManagerTexGen[] {aQa, aQg, aQf, aQb};
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    GlStateManagerTexGen(String text, int j, String textValue, int k) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static GlStateManagerTexGen[] __recovery_values171() {
        return aQc.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments = new String[12];
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[0] = new String("S");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[1] = new String("S");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[2] = new String("S");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[3] = new String("T");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[4] = new String("T");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[5] = new String("T");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[6] = new String("R");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[7] = new String("R");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[8] = new String("R");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[9] = new String("Q");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[10] =
                new String("Q");
        net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[11] =
                new String("Q");
        return net.minecraft.client.renderer.GlStateManagerTexGen.packedTextFragments[1];
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
