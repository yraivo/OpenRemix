package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.HttpUtil;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ResourcePackRepository {
    public IMetadataSerializer rprMetadataSerializer;
    private List<ResourcePackRepositoryEntry> repositoryEntriesAll;
    private List<ResourcePackRepositoryEntry> repositoryEntries;
    private static Logger logger;
    private static FileFilter resourcePackFilter;

    public IResourcePack rprDefaultResourcePack;
    private final ReentrantLock lock = new ReentrantLock();
    private IResourcePack resourcePackInstance;
    private File dirServerResourcepacks;
    private File dirResourcepacks;

    private ListenableFuture serverPackDownload;

    private void fixDirResourcepacks() {
        if (!this.dirResourcepacks.isDirectory() && !this.dirResourcepacks.mkdirs()) {
            net.minecraft.client.resources.ResourcePackRepository.logger.debug(
                    "Unable to create resourcepack folder: " + this.dirResourcepacks);
        }
    }

    public void setRepositories(List list) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(list);
    }

    public void clearServerResourcePack() {
        this.lock.lock();

        try {
            if (this.serverPackDownload != null) {
                this.serverPackDownload.cancel(true);
            }

            this.serverPackDownload = null;
            this.resourcePackInstance = null;
        } finally {
            this.lock.unlock();
        }
    }

    public List getRepositoryEntries() {
        return ImmutableList.copyOf(this.repositoryEntries);
    }

    public void refreshResourcePacks() {
        List<ResourcePackRepositoryEntry> refreshed = new ArrayList<>();
        boolean firstPackOnly = I18n.getLegacyResourceFlowMarker() == null;
        for (File file : this.getResourcePackFiles()) {
            ResourcePackRepositoryEntry candidate =
                    new ResourcePackRepositoryEntry(this, file, null);
            this.addCurrentPackEntry(refreshed, candidate, firstPackOnly);
            if (firstPackOnly) {
                break;
            }
        }

        this.repositoryEntriesAll.removeAll(refreshed);
        for (ResourcePackRepositoryEntry stale : this.repositoryEntriesAll) {
            stale.closeResourcePack();
            if (firstPackOnly) {
                break;
            }
        }
        this.repositoryEntriesAll = refreshed;
    }

    private void addCurrentPackEntry(
            List<ResourcePackRepositoryEntry> refreshed,
            ResourcePackRepositoryEntry candidate,
            boolean firstPackOnly) {
        if (!this.repositoryEntriesAll.contains(candidate)) {
            try {
                candidate.loadPackMetadataAndIcon();
                refreshed.add(candidate);
                return;
            } catch (Exception invalidPack) {
                refreshed.remove(candidate);
                if (!firstPackOnly) {
                    return;
                }
            }
        }

        int previousIndex = this.repositoryEntriesAll.indexOf(candidate);
        if (previousIndex >= 0 && previousIndex < this.repositoryEntriesAll.size()) {
            refreshed.add(this.repositoryEntriesAll.get(previousIndex));
        }
    }

    static {
        net.minecraft.client.resources.ResourcePackRepository.logger = LogManager.getLogger();
        net.minecraft.client.resources.ResourcePackRepository.resourcePackFilter =
                new ResourcePackRepositoryResourcePackFilterFileFilter();
    }

    private List<File> getResourcePackFiles() {
        return this.dirResourcepacks.isDirectory()
                ? Arrays.asList(
                        this.dirResourcepacks.listFiles(
                                net.minecraft.client.resources.ResourcePackRepository
                                        .resourcePackFilter))
                : Collections.emptyList();
    }

    public List getRepositoryEntriesAll() {
        return ImmutableList.copyOf(this.repositoryEntriesAll);
    }

    public IResourcePack getResourcePackInstance() {
        return this.resourcePackInstance;
    }

    public ListenableFuture downloadResourcePack(String url, String expectedHash) {
        boolean legacyFilename = I18n.getLegacyResourceFlowMarker() == null;
        String cacheName = this.getServerPackCacheName(url, expectedHash, legacyFilename);
        if (cacheName == null) {
            return Futures.immediateFailedFuture(
                    new IllegalArgumentException("Invalid filename; must end in .zip"));
        }

        File cacheFile = new File(this.dirServerResourcepacks, cacheName);
        this.lock.lock();
        try {
            this.clearServerResourcePack();
            if (this.isMatchingCachedPack(cacheFile, expectedHash)) {
                return this.setResourcePackInstance(cacheFile);
            }
            return this.startServerPackDownload(url, cacheFile);
        } finally {
            this.lock.unlock();
        }
    }

    private String getServerPackCacheName(String url, String expectedHash, boolean legacyFilename) {
        if (expectedHash.matches("^[a-f0-9]{40}$") && !legacyFilename) {
            return expectedHash;
        }
        String filename = url.substring(url.lastIndexOf('/') + 1);
        int queryStart = filename.indexOf('?');
        if (queryStart >= 0) {
            filename = filename.substring(0, queryStart);
        }
        if (!filename.endsWith(".zip")) {
            return null;
        }
        return "legacy_" + filename.replaceAll("\\W", "");
    }

    private boolean isMatchingCachedPack(File cacheFile, String expectedHash) {
        if (!cacheFile.exists() || expectedHash.length() != 40) {
            return false;
        }
        try {
            String actualHash = Hashing.sha1().hashBytes(Files.toByteArray(cacheFile)).toString();
            if (actualHash.equals(expectedHash)) {
                return true;
            }
            logger.warn(
                    "File "
                            + cacheFile
                            + " had wrong hash (expected "
                            + expectedHash
                            + ", found "
                            + actualHash
                            + "). Deleting it.");
            FileUtils.deleteQuietly(cacheFile);
        } catch (IOException unreadable) {
            logger.warn("File " + cacheFile + " couldn't be hashed. Deleting it.", unreadable);
            FileUtils.deleteQuietly(cacheFile);
        }
        return false;
    }

    private ListenableFuture startServerPackDownload(String url, File cacheFile) {
        GuiScreenWorking downloadScreen = new GuiScreenWorking();
        Map requestHeaders = MinecraftClient.getSessionInfo();
        MinecraftClient client = MinecraftClient.getMinecraft();
        Futures.getUnchecked(
                client.addScheduledTask(
                        new DownloadResourcePackRunnable(this, client, downloadScreen)));
        SettableFuture resourceReload = SettableFuture.create();
        this.serverPackDownload =
                HttpUtil.downloadResourcePack(
                        cacheFile,
                        url,
                        requestHeaders,
                        52428800,
                        downloadScreen,
                        client.getProxy());
        remix.compat.library.LegacyLibraryAdapters.addCallback(
                this.serverPackDownload,
                new DownloadResourcePackFutureCallback(this, cacheFile, resourceReload));
        return this.serverPackDownload;
    }

    public ListenableFuture setResourcePackInstance(File file1) {
        this.resourcePackInstance = new FileResourcePack(file1);
        return MinecraftClient.getMinecraft().scheduleResourcesRefresh();
    }

    public ResourcePackRepository(
            File packDirectory,
            File serverPackCache,
            IResourcePack defaultPack,
            IMetadataSerializer metadataSerializer,
            GameSettings gameSettings) {
        this.repositoryEntriesAll = Lists.newArrayList();
        this.repositoryEntries = Lists.newArrayList();
        this.dirResourcepacks = remix.client.ClientPaths.resourcePackDirectory(packDirectory);
        boolean firstPackOnly = I18n.getLegacyResourceFlowMarker() == null;
        this.dirServerResourcepacks = serverPackCache;
        this.rprDefaultResourcePack = defaultPack;
        this.rprMetadataSerializer = metadataSerializer;
        this.fixDirResourcepacks();
        this.refreshResourcePacks();

        for (Object selectedName : gameSettings.resourcePacks) {
            this.selectPackByName((String) selectedName, firstPackOnly);
            if (firstPackOnly) {
                break;
            }
        }
    }

    private void selectPackByName(String selectedName, boolean firstPackOnly) {
        for (ResourcePackRepositoryEntry entry : this.repositoryEntriesAll) {
            if (entry.getResourcePackName().equals(selectedName)) {
                this.repositoryEntries.add(entry);
                break;
            }
            if (firstPackOnly) {
                break;
            }
        }
    }

    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }

    // Helper used only by this class.
    public static class DownloadResourcePackFutureCallback
            implements com.google.common.util.concurrent.FutureCallback {
        java.io.File hP;
        com.google.common.util.concurrent.SettableFuture aKy;
        net.minecraft.client.resources.ResourcePackRepository aKw;

        public void onFailure(java.lang.Throwable throwable) {
            this.aKy.setException(throwable);
        }

        public DownloadResourcePackFutureCallback(
                net.minecraft.client.resources.ResourcePackRepository aKwValue,
                java.io.File file1,
                com.google.common.util.concurrent.SettableFuture settablefuture) {
            this.aKw = aKwValue;
            this.hP = file1;
            this.aKy = settablefuture;
        }

        public void onSuccess(java.lang.Object object) {
            this.aKw.setResourcePackInstance(this.hP);
            this.aKy.set(null);
        }
    }

    // Helper used only by this class.
    public static class DownloadResourcePackRunnable implements java.lang.Runnable {
        net.minecraft.client.resources.ResourcePackRepository aKw;
        net.minecraft.client.MinecraftClient ec;
        net.minecraft.client.gui.GuiScreenWorking aKx;

        public DownloadResourcePackRunnable(
                net.minecraft.client.resources.ResourcePackRepository aKwValue,
                net.minecraft.client.MinecraftClient minecraftClient,
                net.minecraft.client.gui.GuiScreenWorking aKxValue) {
            this.aKw = aKwValue;
            this.ec = minecraftClient;
            this.aKx = aKxValue;
        }

        @java.lang.Override
        public void run() {
            this.ec.displayGuiScreen(this.aKx);
        }
    }
}
