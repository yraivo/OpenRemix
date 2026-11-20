package remix.ui.clickgui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;

import optifine.MathUtils;

import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleManager;
import remix.module.ModuleMetadata;
import remix.ui.font.ClientFontRenderer;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

public final class ClickGuiCategoryPanel extends ClickGuiComponent {
    public boolean dragging;
    public final double sQ = 20.0;
    private final double sV = 20.0;
    private ModuleCategory category;
    private double dragOffsetX;
    private static ResourceLocation collapsedCategoryIcon;

    private ClickGuiScrollBar scrollBar;
    private double dragOffsetY;
    private final List<ClickGuiComponent> moduleButtons = new ArrayList<>();

    private ResourceLocation categoryIcon;
    private static ClientFontRenderer fontRenderer;
    private static ResourceLocation expandedCategoryIcon;
    private double contentHeight;

    public boolean expanded;

    public void initializeChildren() {
        List<ModuleMetadata> modules =
                new ArrayList<>(ModuleManager.getModulesByCategory(this.category));
        modules.sort((left, right) ->
                Collator.getInstance().compare(left.getDisplayName(), right.getDisplayName()));
        double y = this.getY() + 20.0;
        int legacyPanelState = EntityCreature.getLegacyClickGuiChildLimitState();
        for (ModuleMetadata module : modules) {
            ClickGuiModuleButton button =
                    new ClickGuiModuleButton(
                            this, (ModuleBase) module, this.x, y, this.width, 20.0);
            this.moduleButtons.add(button);
            y += button.getHeight();
            if (legacyPanelState != 0) {
                break;
            }
        }
        this.scrollBar =
                new ClickGuiScrollBar(
                        this.category.getColor(),
                        this.x + this.width - 2.0,
                        this.y + 20.0,
                        2.0,
                        this.height,
                        this.getContentHeight());
        this.layoutModuleButtons();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntityCreature.setLegacyClickGuiChildLimitState(legacyPanelState + 1);
        }
    }

    public double getContentHeight() {
        boolean firstButtonOnly = EntityCreature.getLegacyClickGuiChildLimitState() != 0;
        double height = 0.0;
        for (ClickGuiComponent button : this.moduleButtons) {
            height += button.getHeight();
            if (firstButtonOnly) {
                break;
            }
        }
        return height;
    }

    public boolean containsPoint(int i, int j) {
        return MathUtils.isHovered(
                this.x, this.y, this.width, 20.0 + (this.expanded ? this.height : 0.0), i, j);
    }

    public void draw(int i, int j) {
        boolean firstButtonOnly = EntityCreature.getLegacyClickGuiChildLimitState() != 0;
        if (this.dragging) {
            double d0 = this.x;
            double d1 = this.y;
            this.x = i + this.dragOffsetX;
            this.y = j + this.dragOffsetY;
            double d2 = this.x - d0;
            double d4 = this.y - d1;

            for (ClickGuiComponent clickGuiComponent : this.moduleButtons) {
                clickGuiComponent.moveBy(d2, d4);
                if (firstButtonOnly) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                    break;
                }
            }

            if (this.scrollBar != null) {
                this.scrollBar.x += d2;
                this.scrollBar.y += d4;
            }
        }

        Gui.drawRect(
                this.x - 2.0,
                this.y - 2.0,
                this.x + this.width + 2.0,
                this.y + 20.0,
                this.category.getColor());
        Gui.drawRect(
                this.x - 2.0,
                this.y + 10.0,
                this.x + this.width + 2.0,
                this.y + 20.0,
                new Color(this.category.getColor()).darker().getRGB());
        boolean icon = ClientServices.moduleManager.clickGui.iconSetting.asBoolean();
        float f = (float) (this.x + this.width / 2.0) - (icon ? 10 : 0);
        float f1 =
                (float)
                        (this.y
                                + 10.0
                                - remix.ui.clickgui.ClickGuiCategoryPanel.fontRenderer.getHeight()
                                        / 2.0F);
        String text = this.category.getDisplayName();
        int l = remix.ui.clickgui.ClickGuiCategoryPanel.fontRenderer.getStringWidth(text) / 2;
        if (icon) {
            Render2D3DUtils.drawIcon(
                    this.categoryIcon, (float) this.x + 8.0F, (float) (this.y + 4.2), 25.0F, 25.0F);
        }

        remix.ui.clickgui.ClickGuiCategoryPanel.fontRenderer.drawCenteredString(
                this.category.getDisplayName(), f, f1, -1);
        Render2D3DUtils.drawIcon(
                this.expanded
                        ? remix.ui.clickgui.ClickGuiCategoryPanel.expandedCategoryIcon
                        : remix.ui.clickgui.ClickGuiCategoryPanel.collapsedCategoryIcon,
                (float) (this.x + this.width) - 17.99F,
                (float) (this.y + 4.2),
                25.0F,
                25.0F);
        if (this.expanded) {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            Render2D3DUtils.scissor(
                    this.x, this.y + 20.0, this.x + this.width, this.y + 20.0 + this.height, 2);
            double d3 =
                    this.y
                            + (this.scrollBar.contentHeight > this.scrollBar.height
                                    ? this.scrollBar.contentHeight
                                    : (this.height != this.scrollBar.contentHeight
                                            ? this.height
                                            : this.height + 20.0));
            switch (((ClickGuiBackgroundMode)
                    ClientServices.moduleManager.clickGui.backgroundSetting.getValue())) {
                case COLORFUL:
                    Gui.drawRect(
                            this.x,
                            this.y + 20.0,
                            this.x + this.width,
                            d3,
                            this.category.getColor());
                    Gui.drawRect(this.x, this.y + 20.0, this.x + this.width, d3, -1744830464);
                    if (!firstButtonOnly) {
                        break;
                    }
                case DARK:
                    Gui.drawRect(this.x, this.y + 20.0, this.x + this.width, d3, -1744830464);
            }

            for (ClickGuiComponent clickGuiComponentValue : this.moduleButtons) {
                if (this.scrollBar != null) {
                    double d5 = this.scrollBar.getContentScrollOffset();
                    clickGuiComponentValue.moveBy(0.0, -d5);
                }

                clickGuiComponentValue.draw(i, j);
                if (firstButtonOnly) {
                    break;
                }
            }

            GL11.glDisable(3089);
            GL11.glPopMatrix();
            if (MathUtils.isHovered(this.x, this.y + 20.0, this.width, this.contentHeight, i, j)
                    || this.scrollBar.isDragging()) {
                this.scrollBar.draw(i, j);
                if (!firstButtonOnly) {
                    return;
                }
            }

            this.scrollBar.scrollDelta = 0.0;
        }
    }

    public ClickGuiCategoryPanel(
            double d0, double d1, double d2, double d3, ModuleCategory moduleCategory) {
        super(d0, d1, d2, d3);
        this.category = moduleCategory;
        this.categoryIcon =
                new ResourceLocation(
                        "remix/gui/clickgui/" + moduleCategory.getDisplayName() + ".png");
        this.initializeChildren();
    }

    public void layoutModuleButtons() {
        double y = this.moduleButtons.isEmpty() ? this.y + 20.0 : this.moduleButtons.get(0).getY();
        boolean firstButtonOnly = EntityCreature.getLegacyClickGuiChildLimitState() != 0;
        double height = 0.0;
        for (ClickGuiComponent button : this.moduleButtons) {
            button.moveBy(0.0, y - button.y);
            double buttonHeight = button.getHeight();
            height += buttonHeight;
            y += buttonHeight;
            if (firstButtonOnly) {
                break;
            }
        }
        this.contentHeight = height;
        this.scrollBar.setContentHeight(height);
    }

    public boolean keyPressed(int key) {
        boolean firstButtonOnly = EntityCreature.getClickGuiChildTraversalToken() == 0;
        for (ClickGuiComponent button : this.moduleButtons) {
            if (button.keyPressed(key)) {
                return true;
            }
            if (firstButtonOnly) {
                break;
            }
        }
        return false;
    }

    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        boolean firstButtonOnly = EntityCreature.getClickGuiChildTraversalToken() == 0;
        if (button == 0) {
            this.dragging = false;
        }
        if (this.scrollBar != null && this.scrollBar.isDragging()) {
            this.scrollBar.mouseReleased(mouseX, mouseY, button);
        }
        for (ClickGuiComponent child : this.moduleButtons) {
            if (child.mouseReleased(mouseX, mouseY, button)) {
                return true;
            }
            if (firstButtonOnly) {
                break;
            }
        }
        return false;
    }

    public ModuleCategory getCategory() {
        return this.category;
    }

    public boolean mousePressed(int mouseX, int mouseY, int button) {
        boolean firstButtonOnly = EntityCreature.getClickGuiChildTraversalToken() == 0;
        if (MathUtils.isHovered(this.x, this.y, this.width, 20.0, mouseX, mouseY)
                || this.dragging) {
            if (button == 0) {
                this.dragging = true;
                this.dragOffsetX = this.x - mouseX;
                this.dragOffsetY = this.y - mouseY;
            }
            if (button == 1 || (button == 0 && firstButtonOnly)) {
                this.expanded = !this.expanded;
            }
            return true;
        }
        if (this.scrollBar != null && this.scrollBar.containsPoint(mouseX, mouseY)) {
            this.scrollBar.mousePressed(mouseX, mouseY, button);
            return true;
        }
        if (this.expanded && this.containsPoint(mouseX, mouseY)) {
            for (ClickGuiComponent child : this.moduleButtons) {
                if (child.mousePressed(mouseX, mouseY, button)) {
                    return true;
                }
                if (firstButtonOnly) {
                    break;
                }
            }
        }
        return false;
    }

    public void moveBy(double dx, double dy) {
        boolean firstButtonOnly = EntityCreature.getLegacyClickGuiChildLimitState() != 0;
        for (ClickGuiComponent button : this.moduleButtons) {
            button.moveBy(dx, dy);
            if (firstButtonOnly) {
                break;
            }
        }
        this.scrollBar.moveBy(dx, dy);
        super.moveBy(dx, dy);
    }

    static {
        remix.ui.clickgui.ClickGuiCategoryPanel.fontRenderer = RemixClient.fonts.roboto19;
        remix.ui.clickgui.ClickGuiCategoryPanel.collapsedCategoryIcon =
                new ResourceLocation("remix/gui/clickgui/Closed.png");
        remix.ui.clickgui.ClickGuiCategoryPanel.expandedCategoryIcon =
                new ResourceLocation("remix/gui/clickgui/Expanded.png");
    }
}
