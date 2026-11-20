package net.minecraft.client.resources.model;

import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomItem;

import com.google.common.collect.ComparisonChain;

class WeightedBakedModelMyWeighedRandomItem extends WeightedRandomItem implements Comparable {

    protected IBakedModel model;

    public int func_177634_a(
            WeightedBakedModelMyWeighedRandomItem weightedBakedModelMyWeighedRandomItem) {
        return ComparisonChain.start()
                .compare(weightedBakedModelMyWeighedRandomItem.itemWeight, this.itemWeight)
                .compare(
                        this.getCountQuads(), weightedBakedModelMyWeighedRandomItem.getCountQuads())
                .result();
    }

    @Override
    public String toString() {
        return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + '}';
    }

    protected int getCountQuads() {
        int i1 = ModelResourceLocation.aW();
        int j = this.model.getGeneralQuads().size();
        Facing[] facingArray = Facing.values();
        int k = facingArray.length;
        int l = 0;
        int i = i1;

        while (l < k) {
            Facing facing = facingArray[l];
            j += this.model.getFaceQuads(facing).size();
            l++;
            if (i != 0) {
                break;
            }
        }

        return j;
    }

    public WeightedBakedModelMyWeighedRandomItem(IBakedModel modelValue, int i) {
        super(i);
        this.model = modelValue;
    }

    @Override
    public int compareTo(Object object) {
        return this.func_177634_a((WeightedBakedModelMyWeighedRandomItem) object);
    }


}
