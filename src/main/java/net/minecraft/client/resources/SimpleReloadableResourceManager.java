package net.minecraft.client.resources;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
    private IMetadataSerializer rmMetadataSerializer;

    private static Logger logger;

    private List reloadListeners;
    private Set setResourceDomains;
    public final Map domainResourceManagers = Maps.newHashMap();
    private static Joiner joinerResourcePacks;

    public void reloadResourcePack(IResourcePack iResourcePack) {
        String text = I18n.getLegacyResourceFlowMarker();
        Iterator iterator = iResourcePack.getResourceDomains().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            String textValue2 = (String) iterator.next();
            this.setResourceDomains.add(textValue2);
            FallbackResourceManager fallbackResourceManager =
                    (FallbackResourceManager) this.domainResourceManagers.get(textValue2);
            if (fallbackResourceManager == null) {
                fallbackResourceManager = new FallbackResourceManager(this.rmMetadataSerializer);
                this.domainResourceManagers.put(textValue2, fallbackResourceManager);
            }

            fallbackResourceManager.addResourcePack(iResourcePack);
            if (textValue == null) {
                break;
            }
        }
    }

    public void registerReloadListener(
            IResourceManagerReloadListener iResourceManagerReloadListener) {
        this.reloadListeners.add(iResourceManagerReloadListener);
        iResourceManagerReloadListener.onResourceManagerReload(this);
    }

    public IResource getResource(ResourceLocation resourceLocation) throws IOException {
        IResourceManager iResourceManager =
                (IResourceManager)
                        this.domainResourceManagers.get(resourceLocation.getResourceDomain());

        try {
            if (iResourceManager != null) {
                return iResourceManager.getResource(resourceLocation);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        throw new FileNotFoundException(resourceLocation.toString());
    }

    public Set getResourceDomains() {
        return this.setResourceDomains;
    }

    public List getAllResources(ResourceLocation resourceLocation) throws IOException {
        IResourceManager iResourceManager =
                (IResourceManager)
                        this.domainResourceManagers.get(resourceLocation.getResourceDomain());

        try {
            if (iResourceManager != null) {
                return iResourceManager.getAllResources(resourceLocation);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        throw new FileNotFoundException(resourceLocation.toString());
    }

    private void method_784e95624d6d70fe7f50() {
        String text = I18n.getLegacyResourceFlowMarker();
        Iterator iterator = this.reloadListeners.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            IResourceManagerReloadListener iResourceManagerReloadListener =
                    (IResourceManagerReloadListener) iterator.next();
            iResourceManagerReloadListener.onResourceManagerReload(this);
            if (textValue == null) {
                break;
            }
        }
    }

    public SimpleReloadableResourceManager(IMetadataSerializer aKmValue) {
        this.reloadListeners = Lists.newArrayList();
        this.setResourceDomains = Sets.newLinkedHashSet();
        this.rmMetadataSerializer = aKmValue;
    }

    static {
        net.minecraft.client.resources.SimpleReloadableResourceManager.logger =
                LogManager.getLogger();
        net.minecraft.client.resources.SimpleReloadableResourceManager.joinerResourcePacks =
                Joiner.on(", ");
    }

    public void reloadResources(List list) {
        String text = I18n.getLegacyResourceFlowMarker();
        this.clearResources();
        net.minecraft.client.resources.SimpleReloadableResourceManager.logger.info(
                "Reloading ResourceManager: "
                        + net.minecraft.client.resources.SimpleReloadableResourceManager
                                .joinerResourcePacks
                                .join(
                                        Iterables.transform(
                                                list,
                                                new SimpleReloadableResourceManagerReloadResourcesFunction(
                                                        this))));
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_28457 = (list).iterator();
                (__recoveryIterator171_28457).hasNext(); ) {
            IResourcePack iResourcePack = (IResourcePack) (__recoveryIterator171_28457).next();
            this.reloadResourcePack(iResourcePack);
            if (textValue == null) {
                break;
            }
        }

        this.method_784e95624d6d70fe7f50();
    }

    private void clearResources() {
        this.domainResourceManagers.clear();
        this.setResourceDomains.clear();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
