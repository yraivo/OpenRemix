package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GuiScreenResourcePacks extends GuiScreen {
    private GuiScreen parentScreen;
    private List availableResourcePacks;
    private GuiResourcePackSelected selectedResourcePacksList;
    private GuiResourcePackAvailable availableResourcePacksList;
    private static Logger logger;

    private boolean changed = false;
    private List selectedResourcePacks;

    static {
        net.minecraft.client.gui.GuiScreenResourcePacks.logger = LogManager.getLogger();
    }

    protected void mouseReleased(int i, int j, int k) {
        super.mouseReleased(i, j, k);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public List getAvailableResourcePacks() {
        return this.availableResourcePacks;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.availableResourcePacksList.mouseClicked(i, j, k);
        this.selectedResourcePacksList.mouseClicked(i, j, k);
    }

    public List getSelectedResourcePacks() {
        return this.selectedResourcePacks;
    }

    public boolean hasResourcePackEntry(
            ResourcePackListEntry resourcePackListEntry) {
        return this.selectedResourcePacks.contains(resourcePackListEntry);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.selectedResourcePacksList.handleMouseInput();
        this.availableResourcePacksList.handleMouseInput();
    }

    public void initGui() {
        this.buttonList.add(
                new GuiOptionButton(
                        2,
                        this.width / 2 - 154,
                        this.height - 48,
                        I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(
                new GuiOptionButton(
                        1,
                        this.width / 2 + 4,
                        this.height - 48,
                        I18n.format("gui.done", new Object[0])));
        this.availableResourcePacks = Lists.newArrayList();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.selectedResourcePacks = Lists.newArrayList();
        ResourcePackRepository resourcePackRepository = this.mc.getResourcePackRepository();
        resourcePackRepository.refreshResourcePacks();
        ArrayList arrayList = Lists.newArrayList(resourcePackRepository.getRepositoryEntriesAll());
        arrayList.removeAll(resourcePackRepository.getRepositoryEntries());
        Iterator iterator = arrayList.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            ResourcePackRepositoryEntry resourcePackRepositoryEntry =
                    (ResourcePackRepositoryEntry) iterator.next();
            this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcePackRepositoryEntry));
            if (astring != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_28008 =
                        (Lists.reverse(resourcePackRepository.getRepositoryEntries())).iterator();
                (__recoveryIterator171_28008).hasNext(); ) {
            ResourcePackRepositoryEntry resourcePackRepositoryEntryValue =
                    (ResourcePackRepositoryEntry) (__recoveryIterator171_28008).next();
            this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcePackRepositoryEntryValue));
            if (astring != null) {
                break;
            }
        }

        this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
        this.availableResourcePacksList =
                new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
        this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
        this.availableResourcePacksList.registerScrollButtons(7, 8);
        this.selectedResourcePacksList =
                new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
        this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
        this.selectedResourcePacksList.registerScrollButtons(7, 8);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 2) {
                try {
                    remix.util.io.FileActions.openDirectory(
                            this.mc.getResourcePackRepository().getDirResourcepacks());
                } catch (IOException | RuntimeException exception) {
                    net.minecraft.client.gui.GuiScreenResourcePacks.logger.warn(
                            "Unable to open resource pack folder "
                                    + this.mc.getResourcePackRepository().getDirResourcepacks(),
                            exception);
                }
                return;
            }

            if (guiButton.id == 1) {
                if (this.changed) {
                    ArrayList arrayList = Lists.newArrayList();

                    for (java.util.Iterator<?> __recoveryIterator171_31006 = (this.selectedResourcePacks).iterator();
                            (__recoveryIterator171_31006).hasNext(); ) {
                        ResourcePackListEntry resourcePackListEntry =
                                (ResourcePackListEntry) (__recoveryIterator171_31006).next();
                        if (resourcePackListEntry instanceof ResourcePackListEntryFound) {
                            arrayList.add(
                                    ((ResourcePackListEntryFound) resourcePackListEntry).BV());
                        }

                        if (astring != null) {
                            break;
                        }
                    }

                    Collections.reverse(arrayList);
                    this.mc.getResourcePackRepository().setRepositories(arrayList);
                    this.mc.gameSettings.resourcePacks.clear();

                    for (java.util.Iterator<?> __recoveryIterator171_31582 = (arrayList).iterator();
                            (__recoveryIterator171_31582).hasNext(); ) {
                        ResourcePackRepositoryEntry resourcePackRepositoryEntry =
                                (ResourcePackRepositoryEntry) (__recoveryIterator171_31582).next();
                        this.mc.gameSettings.resourcePacks.add(resourcePackRepositoryEntry.getResourcePackName());
                        if (astring != null) {
                            break;
                        }
                    }

                    this.mc.gameSettings.saveOptions();
                    this.mc.refreshResources();
                }

                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    public List getListContaining(ResourcePackListEntry resourcePackListEntry) {
        return this.hasResourcePackEntry(resourcePackListEntry) ? this.selectedResourcePacks : this.availableResourcePacks;
    }

    public void markChanged() {
        this.changed = true;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawBackground(0);
        this.availableResourcePacksList.drawScreen(i, j, f);
        this.selectedResourcePacksList.drawScreen(i, j, f);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("resourcePack.title", new Object[0]),
                this.width / 2,
                16,
                16777215);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("resourcePack.folderInfo", new Object[0]),
                this.width / 2 - 77,
                this.height - 26,
                8421504);
        super.drawScreen(i, j, f);
    }

    public GuiScreenResourcePacks(GuiScreen aocValue) {
        this.parentScreen = aocValue;
    }
}
