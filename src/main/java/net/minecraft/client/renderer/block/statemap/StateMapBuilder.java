package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.IProperty;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class StateMapBuilder {
    private final List ignored = Lists.newArrayList();
    private String suffix;
    private IProperty name;

    public StateMapBuilder ignore(IProperty... iPropertyArray) {
        Collections.addAll(this.ignored, iPropertyArray);
        return this;
    }

    public StateMapBuilder withSuffix(String text) {
        this.suffix = text;
        return this;
    }

    public StateMapBuilder withName(IProperty aCaValue) {
        this.name = aCaValue;
        return this;
    }

    public StateMap build() {
        return new StateMap(this.name, this.suffix, this.ignored, null);
    }
}
