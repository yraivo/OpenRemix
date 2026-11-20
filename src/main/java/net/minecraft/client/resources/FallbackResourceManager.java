package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FallbackResourceManager implements IResourceManager {
    private static Logger logger;
    private static String[] packedTextFragments;
    private IMetadataSerializer frmMetadataSerializer;
    protected final List resourcePacks = Lists.newArrayList();
    private static String d;

    // region String decoding support

    // endregion

    public Set getResourceDomains() {
        return null;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public FallbackResourceManager(IMetadataSerializer aFcValue) {
        this.frmMetadataSerializer = aFcValue;
    }

    static {
        net.minecraft.client.resources.FallbackResourceManager.packedTextFragments = new String[2];
        net.minecraft.client.resources.FallbackResourceManager.packedTextFragments[0] = new String("roKYxc");
        net.minecraft.client.resources.FallbackResourceManager.packedTextFragments[1] = new String("\u0006]\u0014\fWH.");
        d = ".mcmeta";
        net.minecraft.client.resources.FallbackResourceManager.logger = LogManager.getLogger();
    }

    public void addResourcePack(IResourcePack iResourcePack) {
        this.resourcePacks.add(iResourcePack);
    }

    public List getAllResources(ResourceLocation resourceLocation) throws IOException {
        String text = I18n.getLegacyResourceFlowMarker();
        ArrayList arrayList = Lists.newArrayList();
        ResourceLocation resourceLocationValue = getLocationMcmeta(resourceLocation);
        Iterator iterator = this.resourcePacks.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            IResourcePack iResourcePack = (IResourcePack) iterator.next();
            if (iResourcePack.resourceExists(resourceLocation)) {
                InputStream inputstream =
                        iResourcePack.resourceExists(resourceLocationValue)
                                ? this.getInputStream(
                                        resourceLocationValue, iResourcePack)
                                : null;
                arrayList.add(
                        new SimpleResource(
                                iResourcePack.getPackName(),
                                resourceLocation,
                                this.getInputStream(resourceLocation, iResourcePack),
                                inputstream,
                                this.frmMetadataSerializer));
            }

            if (textValue == null) {
                break;
            }
        }

        if (arrayList.isEmpty()) {
            throw new FileNotFoundException(resourceLocation.toString());
        } else {
            return arrayList;
        }
    }

    protected InputStream getInputStream(
            ResourceLocation resourceLocation, IResourcePack iResourcePack) throws IOException {
        InputStream inputstream = iResourcePack.getInputStream(resourceLocation);
        return (InputStream)
                (net.minecraft.client.resources.FallbackResourceManager.logger.isDebugEnabled()
                        ? new FallbackResourceManagerImputStreamLeakedResourceLogger(
                                inputstream, resourceLocation, iResourcePack.getPackName())
                        : inputstream);
    }

    static Logger getLogger() {
        return net.minecraft.client.resources.FallbackResourceManager.logger;
    }

    static ResourceLocation getLocationMcmeta(ResourceLocation resourceLocation) {
        return new ResourceLocation(
                resourceLocation.getResourceDomain(), resourceLocation.getResourcePath() + d);
    }

    public IResource getResource(ResourceLocation resourceLocation) throws IOException {
        IResourcePack iResourcePack = null;
        String text = I18n.getLegacyResourceFlowMarker();
        ResourceLocation resourceLocationValue = getLocationMcmeta(resourceLocation);
        String textValue = text;
        int i = this.resourcePacks.size() - 1;

        while (i >= 0) {
            IResourcePack iResourcePackValue = (IResourcePack) this.resourcePacks.get(i);
            if (iResourcePack == null && iResourcePackValue.resourceExists(resourceLocationValue)) {
                iResourcePack = iResourcePackValue;
            }

            if (iResourcePackValue.resourceExists(resourceLocation)) {
                InputStream inputstream = null;
                if (iResourcePack != null) {
                    inputstream =
                            this.getInputStream(resourceLocationValue, iResourcePack);
                }

                SimpleResource simpleResource =
                        new SimpleResource(
                                iResourcePackValue.getPackName(),
                                resourceLocation,
                                this.getInputStream(
                                        resourceLocation, iResourcePackValue),
                                inputstream,
                                this.frmMetadataSerializer);
                if (Entity.getLegacyGlobalFlowMarker() != null) {
                    I18n.setLegacyResourceFlowMarker(packedTextFragments[0]);
                }

                return simpleResource;
            }

            i--;
            if (textValue == null) {
                break;
            }
        }

        throw new FileNotFoundException(resourceLocation.toString());
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
