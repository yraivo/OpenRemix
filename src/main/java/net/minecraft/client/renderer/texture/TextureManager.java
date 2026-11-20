package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import optifine.OptiFineConfig;
import optifine.RandomMobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import shadersmod.client.ShadersTex;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
    private IResourceManager theResourceManager;

    private Map<String, Integer> mapTextureCounters;
    private static Logger logger;

    private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
    private static int f;

    private final List<ITickableTextureObject> listTickables = Lists.newArrayList();

    private static ReportedException method_7b8b77b0e24b2985ecf5(
            ReportedException reportedException) {
        return reportedException;
    }

    public ResourceLocation getDynamicTextureLocation(String text, DynamicTexture dynamicTexture) {
        int i = method_beea3e16fe3ec0995eb9();
        if (text.equals("logo")) {
            dynamicTexture = OptiFineConfig.getMojangLogoTexture(dynamicTexture);
        }

        Map map;
        Integer integer;
        {
            map = this.mapTextureCounters;
            integer = (Integer) map.get(text);
            if (integer == null) {
                integer = 1;
                if (!(i == 0)) {

                    integer = integer + 1;
                }
            } else {

                integer = integer + 1;
            }
        }

        map.put(text, integer);
        ResourceLocation resourceLocation =
                new ResourceLocation(String.format("dynamic/%s_%d", text, integer));
        this.loadTexture(resourceLocation, dynamicTexture);
        return resourceLocation;
    }

    public boolean loadTexture(ResourceLocation resourceLocation, ITextureObject iTextureObject) {
        byte b0 = 1;
        Object object = iTextureObject;

        try {
            iTextureObject.loadTexture(this.theResourceManager);
        } catch (IOException ioexception) {
            net.minecraft.client.renderer.texture.TextureManager.logger.warn(
                    "Failed to load texture: " + resourceLocation, ioexception);
            object = TextureUtil.missingTexture;
            b0 = 0;
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Resource location being registered");
            crashReportCategory.addCrashSection("Resource location", resourceLocation);
            crashReportCategory.addCrashSectionCallable(
                    "Texture object class", () -> iTextureObject.getClass().getName());
            throw new ReportedException(crashReport);
        }

        this.mapTextureObjects.put(resourceLocation, (ITextureObject) object);
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        int j = method_beea3e16fe3ec0995eb9();
        OptiFineConfig.dbg("*** Reloading textures ***");
        int i = j;
        OptiFineConfig.log("Resource packs: " + OptiFineConfig.kv());
        Map map = this.mapTextureObjects;
        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
            if (resourceLocation.getResourcePath().startsWith("mcpatcher/")) {
                ITextureObject iTextureObject = (ITextureObject) map.get(resourceLocation);
                if (iTextureObject instanceof AbstractTexture) {
                    AbstractTexture abstractTexture = (AbstractTexture) iTextureObject;
                    abstractTexture.cj();
                }

                iterator.remove();
            }

            if (i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_5127 = (map.entrySet()).iterator();
                (__recoveryIterator171_5127).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_5127).next();
            this.loadTexture((ResourceLocation) entry.getKey(), (ITextureObject) entry.getValue());
            if (i != 0) {
                break;
            }
        }
    }

    public TextureManager(IResourceManager aFjValue) {
        this.mapTextureCounters = Maps.newHashMap();
        this.theResourceManager = aFjValue;
    }

    public void deleteTexture(ResourceLocation resourceLocation) {
        ITextureObject texture = this.getTexture(resourceLocation);
        if (texture != null) {
            TextureUtil.deleteTexture(texture.getGlTextureId());
        }
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        int i = method_beea3e16fe3ec0995eb9();
        if (OptiFineConfig.isRandomMobs()) {
            resourceLocation = RandomMobs.getTextureLocation(resourceLocation);
        }

        Object object =
                ((net.minecraft.client.renderer.texture.ITextureObject)
                        this.mapTextureObjects.get(resourceLocation));
        if (object == null) {
            object = new SimpleTexture(resourceLocation);
            this.loadTexture(resourceLocation, (ITextureObject) object);
        }

        if (OptiFineConfig.isShaders()) {
            ShadersTex.bindTexture((ITextureObject) object);
            if (i == 0) {
                return;
            }
        }

        TextureUtil.bindTexture(
                ((net.minecraft.client.renderer.texture.ITextureObject) (object)).getGlTextureId());
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 1 : 0;
    }

    // region String decoding support

    // endregion

    public ITextureObject getTexture(ResourceLocation resourceLocation) {
        return ((net.minecraft.client.renderer.texture.ITextureObject)
                this.mapTextureObjects.get(resourceLocation));
    }

    public void tick() {
        int j = method_7eec03da329a017ec143();
        Iterator iterator = this.listTickables.iterator();
        int i = j;

        while (iterator.hasNext()) {
            ITickable iTickable = (ITickable) iterator.next();
            iTickable.tick();
            if (i == 0) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    static {
        b(116);
        net.minecraft.client.renderer.texture.TextureManager.logger = LogManager.getLogger();
    }

    public boolean loadTickableTexture(
            ResourceLocation resourceLocation, ITickableTextureObject iTickableTextureObject) {
        if (this.loadTexture(resourceLocation, iTickableTextureObject)) {
            this.listTickables.add(iTickableTextureObject);
            return true;
        } else {
            return false;
        }
    }

    public static void b(int i) {
        f = i;
    }

    public static int method_7eec03da329a017ec143() {
        return f;
    }
}
