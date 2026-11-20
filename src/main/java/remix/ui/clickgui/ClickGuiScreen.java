package remix.ui.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.PanelsJsonConfig;
import remix.module.ModuleCategory;

import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ClickGuiScreen extends GuiScreen {
    public static int scaleFactor;
    private int dragStartY;
    private int previousGuiScale;
    private double previousMouseX;
    private boolean draggingSelection;

    private double previousMouseY;
    private List<ClickGuiCategoryPanel> selectedPanels;

    private int dragStartX;
    private int guiScale;
    private final List<ClickGuiCategoryPanel> categoryPanels = new ArrayList<>();

    public void updateScreen() {
        int guiScaleValue = this.mc.gameSettings.guiScale;
        if (this.guiScale != guiScaleValue) {
            this.previousGuiScale = this.guiScale;
            this.guiScale = guiScaleValue;
            ScaledResolution scaledResolution =
                    new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            remix.ui.clickgui.ClickGuiScreen.scaleFactor = scaledResolution.getScaleFactor();
        }

        super.updateScreen();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void keyTyped(char character, int key) throws IOException {
        boolean visitAllPanels = EntityLiving.canVisitAllClickGuiPanels();
        for (ClickGuiCategoryPanel panel : this.categoryPanels) {
            if (panel.keyPressed(key)) {
                return;
            }
            if (!visitAllPanels) {
                break;
            }
        }
        super.keyTyped(character, key);
    }

    public void onGuiClosed() {
        if (this.mc.entityRenderer.theShaderGroup != null) {
            this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            this.mc.entityRenderer.theShaderGroup = null;
        }

        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(PanelsJsonConfig.class);
    }

    public void initGui() {
        if (OpenGlHelper.shadersSupported
                && this.mc.getRenderViewEntity() instanceof PlayerEntity) {
            if (this.mc.entityRenderer.theShaderGroup != null) {
                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }

            this.mc.entityRenderer.loadShader(
                    new ResourceLocation("shaders/post/blur.json"));
        }

        this.buttonList.add(new GuiButton(1, 5, 100, 55, 20, "Reset Gui"));
    }

    public List<ClickGuiCategoryPanel> getCategoryPanels() {
        return this.categoryPanels;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 1) {
            this.categoryPanels.clear();
            this.buildCategoryPanels();
        }
    }

    public void buildCategoryPanels() {
        boolean firstPanelOnly = EntityLiving.isLegacyClickGuiPanelLimitEnabled();
        double y = 20.0;
        for (ModuleCategory category : ModuleCategory.values()) {
            if (category != ModuleCategory.HIDDEN) {
                this.addCategoryPanel(120.0, y, category);
                y += 25.0;
            }
            if (firstPanelOnly) {
                break;
            }
        }
    }

    public void onResize(MinecraftClient minecraftClient, int i, int j) {
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        remix.ui.clickgui.ClickGuiScreen.scaleFactor = scaledResolution.getScaleFactor();
        super.onResize(minecraftClient, i, j);
    }

    protected void mouseReleased(int screenX, int screenY, int button) {
        boolean visitAllPanels = EntityLiving.canVisitAllClickGuiPanels();
        double scale = 2.0 / scaleFactor;
        int mouseX = (int) (screenX / scale);
        int mouseY = (int) (screenY / scale);
        for (ClickGuiCategoryPanel panel : this.categoryPanels) {
            if (panel.mouseReleased(mouseX, mouseY, button) || !visitAllPanels) {
                break;
            }
        }
        if (this.draggingSelection) {
            if (mouseX == this.dragStartX && mouseY == this.dragStartY && !this.selectedPanels.isEmpty()) {
                this.selectedPanels.clear();
            }
            this.draggingSelection = false;
        }
    }

    public ClickGuiScreen() {
        this.selectedPanels = new ArrayList<>();
        this.guiScale = this.mc.gameSettings.guiScale;
        this.previousGuiScale = -1;
        this.buildCategoryPanels();
    }

    public void drawScreen(int screenX, int screenY, float partialTicks) {
        double scale = 2.0 / scaleFactor;
        int mouseX = (int) (screenX / scale);
        int mouseY = (int) (screenY / scale);
        boolean firstPanelOnly = EntityLiving.isLegacyClickGuiPanelLimitEnabled();

        GlStateManager.pushMatrix();
        try {
            GlStateManager.scale(scale, scale, 1.0);
            if (this.draggingSelection) {
                double dx = mouseX - this.previousMouseX;
                double dy = mouseY - this.previousMouseY;
                for (ClickGuiCategoryPanel panel : this.selectedPanels) {
                    panel.moveBy(dx, dy);
                    if (firstPanelOnly) {
                        Entity.setLegacyGlobalFlowMarker(new String[5]);
                        break;
                    }
                }
            }
            for (int index = this.categoryPanels.size() - 1; index >= 0; index--) {
                this.categoryPanels.get(index).draw(mouseX, mouseY);
                if (firstPanelOnly) {
                    break;
                }
            }
            for (ClickGuiCategoryPanel panel : this.selectedPanels) {
                Gui.drawRect(panel.x, panel.y, panel.x + panel.width, panel.y + 20.0,
                        new Color(0, 0, 0, 50).getRGB());
                if (firstPanelOnly) {
                    break;
                }
            }
            this.previousMouseX = mouseX;
            this.previousMouseY = mouseY;
            for (GuiButton button : this.buttonList) {
                button.drawButton(this.mc, mouseX, mouseY);
                if (firstPanelOnly) {
                    break;
                }
            }
        } finally {
            GlStateManager.popMatrix();
        }
    }

    static {
        remix.ui.clickgui.ClickGuiScreen.scaleFactor = 2;
    }

    protected void mouseClicked(int screenX, int screenY, int button) throws IOException {
        double scale = 2.0 / scaleFactor;
        boolean firstPanelOnly = EntityLiving.isLegacyClickGuiPanelLimitEnabled();
        int mouseX = (int) (screenX / scale);
        int mouseY = (int) (screenY / scale);
        super.mouseClicked(mouseX, mouseY, button);

        for (ClickGuiCategoryPanel panel : this.categoryPanels) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && panel.containsPoint(mouseX, mouseY) && button == 0) {
                boolean wasSelected = this.selectedPanels.remove(panel);
                if (!wasSelected || firstPanelOnly) {
                    this.selectedPanels.add(panel);
                }
                return;
            }
            if (panel.mousePressed(mouseX, mouseY, button)) {
                this.draggingSelection = false;
                return;
            }
            if (firstPanelOnly) {
                break;
            }
        }
        if (!this.selectedPanels.isEmpty()) {
            this.draggingSelection = true;
            this.dragStartX = mouseX;
            this.dragStartY = mouseY;
        }
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntityLiving.setLegacyClickGuiPanelLimit(!firstPanelOnly);
        }
    }

    public ClickGuiCategoryPanel addCategoryPanel(
            double d0, double d1, ModuleCategory moduleCategory) {
        ClickGuiCategoryPanel clickGuiCategoryPanel =
                new ClickGuiCategoryPanel(d0, d1, 100.0, 240.0, moduleCategory);
        this.categoryPanels.add(clickGuiCategoryPanel);
        return clickGuiCategoryPanel;
    }
}
