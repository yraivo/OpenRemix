package net.minecraft.client.resources;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

import com.google.common.base.Function;

public class SimpleReloadableResourceManagerReloadResourcesFunction implements Function {
    SimpleReloadableResourceManager aKH;

    public String apply(IResourcePack iResourcePack) {
        return iResourcePack.getPackName();
    }

    public SimpleReloadableResourceManagerReloadResourcesFunction(SimpleReloadableResourceManager aKHValue) {
        this.aKH = aKHValue;
    }

    public Object apply(Object object) {
        return this.apply((IResourcePack) object);
    }
}
