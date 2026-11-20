package net.minecraft.client.resources;

import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.SkinManagerCacheLoader1;
import net.minecraft.client.resources.SkinManagerLoadSkinIImageBuffer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.minecraft.MinecraftSessionService;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SkinManager {
    private static ExecutorService THREAD_POOL;
    private TextureManager textureManager;
    private static String f;

    private LoadingCache skinCacheLoader;
    private File skinCacheDir;
    private MinecraftSessionService sessionService;

    static MinecraftSessionService method_36b847f74af2a364076a(
            SkinManager skinManager) {
        return skinManager.sessionService;
    }

    public SkinManager(
            TextureManager aqPValue, File file1, MinecraftSessionService minecraftsessionservice) {
        this.textureManager = aqPValue;
        this.skinCacheDir = file1;
        this.sessionService = minecraftsessionservice;
        CacheBuilder cachebuilder = CacheBuilder.newBuilder();

        this.skinCacheLoader =
                cachebuilder
                        .expireAfterAccess(15L, TimeUnit.SECONDS)
                        .build(new SkinManagerCacheLoader1(this));
    }

    public ResourceLocation loadSkin(
            MinecraftProfileTexture minecraftprofiletexture,
            Type type,
            SkinManagerSkinAvailableCallback skinManagerSkinAvailableCallback) {
        String text = I18n.getLegacyResourceFlowMarker();
        ResourceLocation resourceLocation =
                new ResourceLocation(f + minecraftprofiletexture.getHash());
        String textValue = text;
        ITextureObject texture = this.textureManager.getTexture(resourceLocation);
        if (texture != null) {
            if (skinManagerSkinAvailableCallback == null) {
                return resourceLocation;
            }

            skinManagerSkinAvailableCallback.skinAvailable(type, resourceLocation, minecraftprofiletexture);
            if (textValue != null) {
                return resourceLocation;
            }
        }

        File file1 = new File(this.skinCacheDir, minecraftprofiletexture.getHash().substring(0, 2));
        File file2 = new File(file1, minecraftprofiletexture.getHash());
        ImageBufferDownload imageBufferDownload =
                type == Type.SKIN ? new ImageBufferDownload() : null;
        ThreadDownloadImageData threadDownloadImageData =
                new ThreadDownloadImageData(
                        file2,
                        minecraftprofiletexture.getUrl(),
                        DefaultPlayerSkin.getDefaultSkinLegacy(),
                        new SkinManagerLoadSkinIImageBuffer(
                                this,
                                imageBufferDownload,
                                skinManagerSkinAvailableCallback,
                                type,
                                resourceLocation,
                                minecraftprofiletexture));
        this.textureManager.loadTexture(resourceLocation, threadDownloadImageData);
        return resourceLocation;
    }

    public void loadProfileTextures(
            GameProfile gameprofile,
            SkinManagerSkinAvailableCallback skinManagerSkinAvailableCallback,
            boolean flag) {
        net.minecraft.client.resources.SkinManager.THREAD_POOL.submit(
                new SkinManagerLoadProfileTexturesRunnable(
                        this, gameprofile, flag, skinManagerSkinAvailableCallback));
    }

    // region String decoding support

    // endregion

    public Map loadSkinFromCache(GameProfile gameprofile) {
        return (Map) this.skinCacheLoader.getUnchecked(gameprofile);
    }

    public ResourceLocation loadSkin(
            MinecraftProfileTexture minecraftprofiletexture, Type type) {
        return this.loadSkin(
                minecraftprofiletexture, type, (SkinManagerSkinAvailableCallback) null);
    }

    static {
        f = "skins/";
        net.minecraft.client.resources.SkinManager.THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    // region String decoding support

    // endregion
}
