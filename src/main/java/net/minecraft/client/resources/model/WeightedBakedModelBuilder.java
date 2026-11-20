package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;

import java.util.List;

public class WeightedBakedModelBuilder {
    private List listItems = Lists.newArrayList();

    public IBakedModel first() {
        return ((net.minecraft.client.resources.model.WeightedBakedModelMyWeighedRandomItem) this.listItems.get(0)).model;
    }

    public WeightedBakedModel build() {
        java.util.Collections.sort(this.listItems);
        return new net.minecraft.client.resources.model.WeightedBakedModel(this.listItems);
    }

    public WeightedBakedModelBuilder add(
            IBakedModel iBakedModel, int p171_1) {
        this.listItems.add(new net.minecraft.client.resources.model.WeightedBakedModelMyWeighedRandomItem(iBakedModel, p171_1));
        return this;
    }
}
