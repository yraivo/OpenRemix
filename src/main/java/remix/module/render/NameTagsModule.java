package remix.module.render;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.WorldSettingsGameType;
import remix.ui.hud.HudColor;
import remix.module.render.NameTagsHealthStyle;
import remix.module.render.NameTagsPingStyle;
import remix.util.entity.RaycastHelper;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render2DEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.ClientServices;
import remix.command.TargetsClientCommand;
import remix.compat.vecmath.Vector2f;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.player.AutoHypixelModule;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;

import org.lwjgl.opengl.Display;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map.Entry;

/** NameTags module: render events and 17 settings. */
public final class NameTagsModule extends ModuleBase {
    private Setting hoverPingSetting;
    private Setting armorBackgroundSetting;
    private Setting neutralSetting;
    private Setting<NameTagsPingStyle> pingStyleSetting;
    private Setting hoverHealthSetting;
    private Setting pingSetting;
    private static String[] packedTextFragments;
    private Setting mobsSetting;
    private final Setting<NameTagsHealthStyle> healthStyleSetting =
            new Setting("HealthStyle", this, NameTagsHealthStyle.PERCENTAGE);
    private Setting hoverArmorSetting;
    private Setting invisiblesSetting;
    private Setting teamColorSetting;
    private Setting playersSetting;
    private Setting gameModeSetting;
    private Setting animalsSetting;
    private Setting healthSetting;
    private Setting armorSetting;
    private Setting hoverGameModeSetting;

    public NameTagsModule() {
        super("NameTags", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.pingStyleSetting = new Setting("PingStyle", this, NameTagsPingStyle.NUMERIC);
        this.healthSetting = new Setting("Health", this, true);
        this.hoverHealthSetting = new Setting("HoverHealth", this, false);
        this.pingSetting = new Setting("Ping", this, true);
        this.hoverPingSetting = new Setting("HoverPing", this, true);
        this.gameModeSetting = new Setting("GameMode", this, true);
        this.hoverGameModeSetting = new Setting("HoverGameMode", this, false);
        this.armorSetting = new Setting("Armor", this, true);
        this.hoverArmorSetting = new Setting("HoverArmor", this, true);
        this.armorBackgroundSetting = new Setting("ArmorBackground", this, true);
        this.teamColorSetting = new Setting("TeamColor", this, false);
        this.playersSetting = new Setting("Players", this, true);
        this.mobsSetting = new Setting("Mobs", this, false);
        this.animalsSetting = new Setting("Animals", this, false);
        this.neutralSetting = new Setting("Neutral", this, false);
        this.invisiblesSetting = new Setting("Invisibles", this, true);
    }

    private int hsvToRgb(float hue, float saturation, float brightness) {
        int i = (int) (hue * 6.0F) % 6;
        float f3 = hue * 6.0F - i;
        float f4 = brightness * (1.0F - saturation);
        float f5 = brightness * (1.0F - f3 * saturation);
        float f6 = brightness * (1.0F - (1.0F - f3) * saturation);
        float f7;
        float f8;
        float f9;
        switch (i) {
            case 0:
                f7 = brightness;
                f8 = f6;
                f9 = f4;
                break;
            case 1:
                f7 = f5;
                f8 = brightness;
                f9 = f4;
                break;
            case 2:
                f7 = f4;
                f8 = brightness;
                f9 = f6;
                break;
            case 3:
                f7 = f4;
                f8 = f5;
                f9 = brightness;
                break;
            case 4:
                f7 = f6;
                f8 = f4;
                f9 = brightness;
                break;
            case 5:
                f7 = brightness;
                f8 = f4;
                f9 = f5;
                break;
            default:
                throw new RuntimeException(
                        "Something went wrong when converting from HSV to RGB. Input was "
                                + hue
                                + ", "
                                + saturation
                                + ", "
                                + brightness);
        }

        int j = MathHelper.clamp((int) (f7 * 255.0F), 0, 255);
        int k = MathHelper.clamp((int) (f8 * 255.0F), 0, 255);
        int l = MathHelper.clamp((int) (f9 * 255.0F), 0, 255);
        return j << 16 | k << 8 | l;
    }

    private int getHealthColor(LivingEntity entity) {
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(health, maxHealth) / maxHealth);
        return this.hsvToRgb(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }

    public boolean shouldRenderEntity(Entity entity) {
        AntiBotModule antiBot = ClientServices.moduleManager.antiBot;
        return entity != minecraft.player
                && entity.isEntityAlive()
                && (this.invisiblesSetting.asBoolean() || !entity.isInvisible())
                && (!antiBot.isEnabled() || !antiBot.isBot(entity))
                && (entity instanceof PlayerEntity && this.playersSetting.asBoolean()
                        || (entity instanceof MonsterEntity
                                        || entity instanceof EntityGolem
                                        || entity instanceof SlimeEntity
                                        || entity instanceof EntityDragon)
                                && this.mobsSetting.asBoolean()
                        || (entity instanceof BatEntity
                                        || entity instanceof SquidEntity
                                        || entity instanceof EntityVillager)
                                && this.neutralSetting.asBoolean()
                        || entity instanceof AnimalEntity && this.animalsSetting.asBoolean());
    }

    private float ceilHealth(LivingEntity entity) {
        int i = (int) Math.ceil(entity.getHealth());
        int j = (int) (i + 0.5F);
        return j;
    }

    private float roundToIncrement(float value, float increment) {
        return Math.round(value * (1.0F / increment)) / (1.0F / increment);
    }

    // region Static initialization and string tables
    static {
        remix.module.render.NameTagsModule.packedTextFragments = new String[8];
        remix.module.render.NameTagsModule.packedTextFragments[0] = new String("");
        remix.module.render.NameTagsModule.packedTextFragments[1] = new String("");
        remix.module.render.NameTagsModule.packedTextFragments[2] = new String("%");
        remix.module.render.NameTagsModule.packedTextFragments[3] = new String("");
        remix.module.render.NameTagsModule.packedTextFragments[4] = new String(" ");
        remix.module.render.NameTagsModule.packedTextFragments[5] = new String("g");
    }

    // endregion

    public boolean isPointInsideBounds(
            int x, int y, Vector2f origin, Vector2f size) {
        return x >= origin.getX()
                && x <= origin.getX() + size.getX()
                && Display.getHeight() - y >= origin.getY()
                && Display.getHeight() - y <= origin.getY() + size.getY();
    }

    @Subscribe(mc = Render2DEvent.class, priority = SubscriberPriority.HIGH)
    public void onRender2D(Render2DEvent event) {
        ScaledResolution resolution = event.getResolution();
        double scaleFactor = resolution.getScaleFactor();
        GlStateManager.pushMatrix();
        GlStateManager.scale(
                1.0 / scaleFactor, 1.0 / scaleFactor, 1.0 / scaleFactor);
        LocalPlayer player = minecraft.player;
        FontRenderer anp = minecraft.fontRendererObj;
        RayTraceResult rayTraceResult =
                RaycastHelper.rayTrace(
                        new float[] {player.rotationYaw, player.rotationPitch}, 255.0, true, 1.0F);

        for (Object item : ClientServices.moduleManager.esp.projectedBoxVertices.entrySet()) {
            Entry entry = (Entry) item;
            LivingEntity key = (LivingEntity) entry.getKey();
            if (this.shouldRenderEntity(key)) {
                double[][] adouble = (double[][]) entry.getValue();
                double minX = Double.NaN;
                double minY = Double.NaN;
                double minZ = Double.NaN;
                double maxX = Double.NaN;
                double maxY = Double.NaN;
                double maxZ = Double.NaN;

                for (double[] pt : adouble) {
                    minX = Math.min(Double.isNaN(minX) ? pt[0] : minX, pt[0]);
                    minY = Math.min(Double.isNaN(minY) ? pt[1] : minY, pt[1]);
                    minZ = Math.min(Double.isNaN(minZ) ? pt[2] : minZ, pt[2]);
                    maxX = Math.max(Double.isNaN(maxX) ? pt[0] : maxX, pt[0]);
                    maxY = Math.max(Double.isNaN(maxY) ? pt[1] : maxY, pt[1]);
                    maxZ = Math.max(Double.isNaN(maxZ) ? pt[2] : maxZ, pt[2]);
                }

                if (minZ < 0.0 || minZ >= 1.0 || maxZ < 0.0 || maxZ >= 1.0) {
                    continue;
                }

                int nameColor = -1;
                boolean isPlayer = key instanceof PlayerEntity;
                String textValue2 = key.getName();

                if (isPlayer && (textValue2.equals("Vladymyr") || textValue2.equals("CumHam") || TargetsClientCommand.isTarget(key))) {
                    nameColor = HudColor.getRainbowColor(15000.0, 1).getRGB();
                } else if (ClientServices.friendManager.isFriend(textValue2)) {
                    nameColor = 3173872;
                } else {
                    if (isPlayer && this.teamColorSetting.asBoolean()) {
                        PlayerEntity playerEntity = (PlayerEntity) key;
                        Team team = playerEntity.getTeam();
                        if (team instanceof ScorePlayerTeam) {
                            ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam) team;
                            String prefix = scorePlayerTeam.getColorPrefix();
                            if (prefix.length() >= 2 && prefix.charAt(0) == '§') {
                                int l = "0123456789abcdefklmnor".indexOf(prefix.toLowerCase().charAt(1));
                                if (l != -1) {
                                    nameColor = anp.colorCode[l];
                                }
                            }
                        }
                    }

                    if (key.isSneaking()) {
                        nameColor = -1048576;
                    } else if (key.isInvisible()) {
                        nameColor = -13312;
                    }
                }

                double d12 = (minX + maxX) / 2.0;
                int l3 = anp.FONT_HEIGHT;
                double d13 = minY;
                AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
                boolean hasBounty = autoHypixel.isEnabled() && autoHypixel.bountyHunterSetting.asBoolean();
                int bounty = -1;
                if (hasBounty) {
                    bounty = autoHypixel.getBounty(key);
                    hasBounty = bounty != -1;
                }

                do {
                    d13 -= 13.0;
                    int i4 = anp.getStringWidth(textValue2) / 2;
                    int j4 = l3 / 2;
                    Render2D3DUtils.drawBorder(
                            d12 - i4 - 2.0,
                            d13 - j4 + 2.0,
                            d12 + i4 + 2.0,
                            d13 + j4 * 2,
                            1.0,
                            this.withAlpha(Integer.MIN_VALUE, 0.2F));
                    Gui.drawRect(
                            d12 - i4 - 2.0,
                            d13 - j4 + 2.0,
                            d12 + i4 + 2.0,
                            d13 + j4 * 2 + 1.0,
                            Integer.MIN_VALUE);
                    anp.drawString(textValue2, (int) d12 - i4, (int) d13, nameColor, true);

                    if (nameColor == -662245) {
                        break;
                    }

                    boolean hovered = this.isPointInsideBounds(
                            resolution.getWidth(),
                            resolution.getHeight(),
                            new Vector2f((float) (d12 - i4 - 2.0), (float) (d13 - j4 + 2.0)),
                            new Vector2f(i4 * 2 + 2.0F, l3))
                            || (rayTraceResult != null && rayTraceResult.entityHit == key);

                    PlayerInfo playerInfo = minecraft.getNetworkHandler().getPlayerInfo(textValue2);
                    boolean showGameMode = isPlayer && this.gameModeSetting.asBoolean() && (!this.hoverGameModeSetting.asBoolean() || hovered);
                    byte b2 = 0;

                    if (showGameMode) {
                        WorldSettingsGameType worldSettingsGameType = WorldSettingsGameType.NOT_SET;
                        WorldSettingsGameType worldSettingsGameTypeValue =
                                playerInfo != null ? playerInfo.getGameType() : worldSettingsGameType;
                        if (worldSettingsGameTypeValue != worldSettingsGameType) {
                            String textValue4 = worldSettingsGameTypeValue.fg().substring(0, 1);
                            EnumChatFormatting enumChatFormatting;
                            if (worldSettingsGameTypeValue == WorldSettingsGameType.SURVIVAL) {
                                enumChatFormatting = EnumChatFormatting.RED;
                            } else if (worldSettingsGameTypeValue == WorldSettingsGameType.CREATIVE) {
                                enumChatFormatting = EnumChatFormatting.GREEN;
                            } else if (worldSettingsGameTypeValue == WorldSettingsGameType.ADVENTURE) {
                                enumChatFormatting = EnumChatFormatting.GOLD;
                            } else {
                                enumChatFormatting = EnumChatFormatting.WHITE;
                            }

                            b2 = 1;
                            String textValue5 =
                                    String.format(
                                            "\u00a77[\u00a7f%s%s\u00a77]" /* "§7[§f%s%s§7]" */,
                                            enumChatFormatting,
                                            textValue4);
                            int j3 = anp.getStringWidth(textValue5) >> 1;
                            Render2D3DUtils.drawBorder(
                                    d12 - (i4 + j3 * 3 + 1),
                                    d13 - j4 + 2.0,
                                    d12 - (i4 + j3 + 1),
                                    d13 + j4 + 3.0,
                                    1.0,
                                    this.withAlpha(Integer.MIN_VALUE, 0.2F));
                            Gui.drawRect(
                                    d12 - (i4 + j3 * 3 + 1),
                                    d13 - j4 + 2.0,
                                    d12 - (i4 + j3 + 1),
                                    d13 + j4 + 5.0,
                                    Integer.MIN_VALUE);
                            anp.drawStringWithShadow(
                                    textValue5,
                                    (int) (d12 - (i4 + j3 * 3)),
                                    (int) (d13 - j4 + 4.0),
                                    -1);
                        }
                    }

                    if (isPlayer && this.pingSetting.asBoolean() && (!this.hoverPingSetting.asBoolean() || hovered)) {
                        int k4 = playerInfo != null ? playerInfo.getResponseTime() : 0;
                        if (k4 != 0) {
                            boolean numericPing = this.pingStyleSetting.getValue() == NameTagsPingStyle.NUMERIC;
                            String textValue6 = String.format("\u00a77[\u00a7f%s\u00a77]", k4);
                            double d14 = numericPing ? anp.getStringWidth(textValue6) >> 1 : 8.0;
                            double d15 = b2 != 0 ? 14.0 : 1.0;
                            double d8 = numericPing ? (b2 != 0 ? 9 : 4) : 10.0;
                            double d9 = i4 + d14 * 3.0 + d15 + (b2 != 0 ? 0 : -3);
                            double d10 = d12 - (i4 + d14 * 3.0 + d15);
                            Render2D3DUtils.drawBorder(
                                    d12 - (i4 + d15 + d8),
                                    d13 - j4 + 2.0,
                                    d10,
                                    d13 + j4 + 3.0,
                                    1.0,
                                    this.withAlpha(Integer.MIN_VALUE, 0.2F));
                            Gui.drawRect(
                                    d12 - (i4 + d15 + d8),
                                    d13 - j4 + 2.0,
                                    d10,
                                    d13 + j4 + 5.0,
                                    Integer.MIN_VALUE);

                            if (numericPing) {
                                anp.drawStringWithShadow(textValue6, (int) (d12 - d9), (int) (d13 - j4 + 4.0), -1);
                            } else {
                                minecraft.ingameGUI.getTabList().drawPing(
                                        (int) (d12 - d9) + 15,
                                        0,
                                        (int) (d13 - j4 + 4.0),
                                        playerInfo);
                            }
                        }
                    }

                    if (this.healthSetting.asBoolean() && (!this.hoverHealthSetting.asBoolean() || hovered)) {
                        float f = this.ceilHealth(key);
                        NameTagsHealthStyle healthStyle = (NameTagsHealthStyle) this.healthStyleSetting.getValue();

                        if (healthStyle == NameTagsHealthStyle.HEALTH_BAR) {
                            float f1 = (i4 + 2) * (f / key.getMaxHealth());
                            Gui.drawRect(
                                    d12 - i4 - 2.0,
                                    d13 + j4 + 4.0,
                                    d12 + i4 + 2.0,
                                    d13 + j4 + 7.0,
                                    -9892341);
                            Gui.drawRect(
                                    d12 - i4 - 2.0,
                                    d13 + j4 + 4.0,
                                    d12 - i4 - 2.0 + f1 * 2.0F,
                                    d13 + j4 + 7.0,
                                    this.getHealthColor(key));
                        } else {
                            StringBuilder stringbuilder1 = new StringBuilder();
                            if (healthStyle == NameTagsHealthStyle.NUMERIC) {
                                stringbuilder1.append(String.valueOf(f).replace(".0", packedTextFragments[0]));
                            } else if (healthStyle == NameTagsHealthStyle.PERCENTAGE) {
                                stringbuilder1.append(
                                        String.valueOf(
                                                this.roundToIncrement(
                                                        f / key.getMaxHealth() * 100.0F,
                                                        1.0F)).replace(".0", packedTextFragments[1]))
                                        .append(packedTextFragments[2]);
                            }

                            int k3 = anp.getStringWidth(stringbuilder1.toString()) >> 1;
                            Render2D3DUtils.drawBorder(
                                    d12 + i4 + 3.0,
                                    d13 - j4 + 2.0,
                                    d12 + i4 + k3 * 2 + 5,
                                    d13 + j4 + 3.0,
                                    1.0,
                                    this.withAlpha(Integer.MIN_VALUE, 0.2F));
                            Gui.drawRect(
                                    d12 + i4 + 3.0,
                                    d13 - j4 + 2.0,
                                    d12 + i4 + k3 * 2 + 5,
                                    d13 + j4 + 5.0,
                                    Integer.MIN_VALUE);
                            anp.drawString(
                                    stringbuilder1.toString(),
                                    (int) (d12 + i4 + 4.0),
                                    (int) (d13 - j4 + 4.0),
                                    this.getHealthColor(key),
                                    true);
                        }
                    }

                    if (isPlayer && this.armorSetting.asBoolean() && (!this.hoverArmorSetting.asBoolean() || hovered)) {
                        PlayerEntity playerEntity = (PlayerEntity) key;
                        RenderItem renderItem = minecraft.getRenderItem();
                        ArrayList<ItemStack> armorAndHeldItems = new ArrayList<>();

                        for (int j5 = 3; j5 >= 0; j5--) {
                            ItemStack itemStack = playerEntity.inventory.armorInventory[j5];
                            if (itemStack != null) {
                                armorAndHeldItems.add(itemStack);
                            }
                        }

                        if (playerEntity.getCurrentEquippedItem() != null) {
                            armorAndHeldItems.add(playerEntity.getCurrentEquippedItem());
                        }

                        int itemSize = armorAndHeldItems.size();
                        if (itemSize > 0) {
                            GlStateManager.pushMatrix();
                            Item brr = Items.golden_apple;
                            boolean armorBackground = this.armorBackgroundSetting.asBoolean();
                            double d11 = d12 - (itemSize * 9 - 2);
                            GlStateManager.translate(d11, d13 - 22.0, 0.0);
                            RenderHelper.enableGUIStandardItemLighting();

                            for (int i2 = 0; i2 < itemSize; i2++) {
                                ItemStack itemStackValue2 = armorAndHeldItems.get(i2);
                                GlStateManager.pushMatrix();
                                GlStateManager.translate(i2 * 18, 0.0F, 0.0F);

                                if (armorBackground) {
                                    Render2D3DUtils.drawBorder(
                                            2.0,
                                            2.0,
                                            15.0,
                                            13.0,
                                            4.0,
                                            this.withAlpha(1073741824, 0.2F));
                                    Gui.drawRect(-1.0, 0.0, 17.0, 16.0, Integer.MIN_VALUE);
                                }

                                renderItem.renderItemModelIntoGUI(itemStackValue2, 0, 0);
                                renderItem.renderItemOverlayIntoGUI(anp, itemStackValue2, 0, 0, packedTextFragments[3]);
                                GlStateManager.scale(0.5, 0.5, 0.5);
                                GlStateManager.disableDepth();
                                GlStateManager.disableLighting();
                                NBTTagList nbtTagList = itemStackValue2.getEnchantmentTagList();

                                if (itemStackValue2.getItem() == brr && itemStackValue2.hasEffect() && itemStackValue2.getItemDamage() > 0) {
                                    Gui.drawRect(-2.0, 0.0, anp.getStringWidth("OP"), 8.0, 1073741824);
                                    anp.drawStringWithShadow("OP", 0.0F, 0.0F, -65536);
                                } else if (nbtTagList != null) {
                                    int j2 = nbtTagList.tagCount();
                                    if (j2 >= 6) {
                                        Gui.drawRect(-2.0, 0.0, anp.getStringWidth("OP") + 2, 8.0, -2145117178);
                                        anp.drawStringWithShadow("OP", 0.0F, 0.0F, -65536);
                                    } else {
                                        int drawY = 0;
                                        for (int k2 = j2 - 1; k2 >= 0; k2--) {
                                            CompoundTag compoundTag = nbtTagList.getCompoundTagAt(k2);
                                            int l2 = compoundTag.getInteger("lvl");
                                            int integer = compoundTag.getInteger("id");
                                            Enchantment enchantment = Enchantment.getEnchantmentById(integer);
                                            if (enchantment != null) {
                                                String textValue8 = enchantment.getName().substring(0, 2);
                                                anp.drawStringWithShadow(
                                                        String.format("%s%s", textValue8, l2),
                                                        0.0F,
                                                        drawY,
                                                        -1);
                                                drawY += 8;
                                            }
                                        }
                                    }
                                }

                                int l5 = itemStackValue2.stackSize;
                                if (i2 == 0 && l5 != 1) {
                                    String textValue9 = Integer.toString(l5);
                                    Gui.drawRect(-2.0, 0.0, anp.getStringWidth(textValue9), 8.0, 1073741824);
                                    anp.drawStringWithShadow(textValue9, 22.0F, 1.0F, 2345895);
                                }

                                GlStateManager.enableLighting();
                                GlStateManager.enableDepth();
                                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                                GlStateManager.popMatrix();
                            }

                            RenderHelper.disableStandardItemLighting();
                            GlStateManager.popMatrix();
                        }
                    }

                    if (hasBounty) {
                        textValue2 = bounty + packedTextFragments[5];
                        nameColor = -662245;
                        hasBounty = false;
                    }
                } while (hasBounty);
            }
        }

        GlStateManager.popMatrix();
    }

    private int withAlpha(int packedColor, float alpha) {
        Color color = new Color(packedColor);
        float f1 = 0.003921569F * color.getRed();
        float f2 = 0.003921569F * color.getGreen();
        float f3 = 0.003921569F * color.getBlue();
        return new Color(f1, f2, f3, alpha).getRGB();
    }

    private static RuntimeException preserveRuntimeException(RuntimeException cause) {
        return cause;
    }
}
