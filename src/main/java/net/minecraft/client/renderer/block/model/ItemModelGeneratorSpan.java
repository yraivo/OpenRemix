package net.minecraft.client.renderer.block.model;

class ItemModelGeneratorSpan {
    private int cz;
    private int dD;
    private int c;
    private ItemModelGeneratorSpanFacing spanFacing;

    public int method_7eec03da329a017ec143() {
        return this.cz;
    }

    public void a(int i) {
        int j = BakedQuad.cM();
        if (i < this.cz) {
            this.cz = i;
            if (j != 0) {
                return;
            }
        }

        if (i > this.c) {
            this.c = i;
        }
    }

    public int method_beea3e16fe3ec0995eb9() {
        return this.c;
    }

    public ItemModelGeneratorSpanFacing getSpanFacing() {
        return this.spanFacing;
    }

    public ItemModelGeneratorSpan(ItemModelGeneratorSpanFacing azaValue, int i, int j) {
        this.spanFacing = azaValue;
        this.cz = i;
        this.c = i;
        this.dD = j;
    }

    public int aW() {
        return this.dD;
    }
}
