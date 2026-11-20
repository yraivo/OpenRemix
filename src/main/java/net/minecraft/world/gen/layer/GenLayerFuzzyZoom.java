package net.minecraft.world.gen.layer;

public class GenLayerFuzzyZoom extends GenLayerZoom {
    protected int selectModeOrRandom(int i, int j, int k, int l) {
        return this.selectRandom(new int[] {i, j, k, l});
    }

    public GenLayerFuzzyZoom(long i, GenLayer genLayer) {
        super(i, genLayer);
    }
}
