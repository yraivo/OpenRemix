package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;

import optifine.Reflector;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public class GuiOverlayDebug extends Gui {
    private static String[] packedTextFragments;
    private FontRenderer fontRenderer;

    private MinecraftClient mc;

    protected void renderDebugInfoLeft() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        List list = this.eN();
        int i = 0;
        String[] astring = astring1;

        while (i < list.size()) {
            String text = (String) list.get(i);
            if (!Strings.isNullOrEmpty(text)) {
                int j = this.fontRenderer.FONT_HEIGHT;
                int k = this.fontRenderer.getStringWidth(text);

                int l = 2 + j * i;
                drawRect(1.0, l - 1, 2 + k + 1, l + j - 1, -1873784752);
                this.fontRenderer.drawString(text, 2, l, 14737632);
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    private boolean isReducedDebug() {
        return this.mc.player.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo;
    }

    private static long method_6be2ddaf1bd1d43d74fc(long i) {

        long j = i / 1024L;

        return j / 1024L;
    }

    public GuiOverlayDebug(MinecraftClient apcValue) {
        this.mc = apcValue;
        this.fontRenderer = apcValue.fontRendererObj;
    }

    static {
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments = new String[11];
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[0] = new String("/");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[1] = new String(")");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[3] = new String("/");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[4] = new String(")");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[6] = new String("");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[7] = new String("");
        net.minecraft.client.gui.GuiOverlayDebug.packedTextFragments[8] = new String("");
    }

    protected void renderDebugInfoRight(ScaledResolution scaledResolution) {
        List list = this.tZ();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < list.size()) {
            String text = (String) list.get(i);
            if (!Strings.isNullOrEmpty(text)) {
                int j = this.fontRenderer.FONT_HEIGHT;
                int k = this.fontRenderer.getStringWidth(text);
                int l = scaledResolution.getWidth() - 2 - k;
                int i1 = 2 + j * i;
                drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
                this.fontRenderer.drawString(text, l, i1, 14737632);
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    protected List eN() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        BlockPos blockPos =
                new BlockPos(
                        this.mc.getRenderViewEntity().posX,
                        this.mc.getRenderViewEntity().getEntityBoundingBox().minY,
                        this.mc.getRenderViewEntity().posZ);
        String[] astring = astring1;
        if (this.isReducedDebug()) {
            return Lists.newArrayList(
                    new String[] {
                        "Minecraft 1.8 ("
                                + this.mc.getVersion()
                                + packedTextFragments[0]
                                + ClientBrandRetriever.getClientModName()
                                + packedTextFragments[1],
                        this.mc.debug,
                        this.mc.renderGlobal.getDebugInfoChunks(),
                        this.mc.renderGlobal.getDebugInfoEntities(),
                        "P: "
                                + this.mc.effectRenderer.getStatistics()
                                + ". T: "
                                + this.mc.world.getDebugLoadedEntities(),
                        this.mc.world.getProviderName(),
                        packedTextFragments[2],
                        String.format(
                                "Chunk-relative: %d %d %d",
                                blockPos.getX() & 15, blockPos.getY() & 15, blockPos.getZ() & 15)
                    });
        }

        Entity entity = this.mc.getRenderViewEntity();
        Facing facing = entity.getHorizontalFacing();
        String text = "Invalid";
        switch (facing) {
            case NORTH:
                text = "Towards negative Z";
                if (astring == null) {
                    break;
                }
            case SOUTH:
                text = "Towards positive Z";
                if (astring == null) {
                    break;
                }
            case WEST:
                text = "Towards negative X";
                if (astring == null) {
                    break;
                }
            case EAST:
                text = "Towards positive X";
        }

        ArrayList arrayList =
                Lists.newArrayList(
                        new String[] {
                            "Minecraft 1.8 ("
                                    + this.mc.getVersion()
                                    + packedTextFragments[3]
                                    + ClientBrandRetriever.getClientModName()
                                    + packedTextFragments[4],
                            this.mc.debug,
                            this.mc.renderGlobal.getDebugInfoChunks(),
                            this.mc.renderGlobal.getDebugInfoEntities(),
                            "P: "
                                    + this.mc.effectRenderer.getStatistics()
                                    + ". T: "
                                    + this.mc.world.getDebugLoadedEntities(),
                            this.mc.world.getProviderName(),
                            packedTextFragments[5],
                            String.format(
                                    "XYZ: %.3f / %.5f / %.3f",
                                    this.mc.getRenderViewEntity().posX,
                                    this.mc.getRenderViewEntity().getEntityBoundingBox().minY,
                                    this.mc.getRenderViewEntity().posZ),
                            String.format(
                                    "Block: %d %d %d",
                                    blockPos.getX(), blockPos.getY(), blockPos.getZ()),
                            String.format(
                                    "Chunk: %d %d %d in %d %d %d",
                                    blockPos.getX() & 15,
                                    blockPos.getY() & 15,
                                    blockPos.getZ() & 15,
                                    blockPos.getX() >> 4,
                                    blockPos.getY() >> 4,
                                    blockPos.getZ() >> 4),
                            String.format(
                                    "Facing: %s (%s) (%.1f / %.1f)",
                                    facing,
                                    text,
                                    MathHelper.wrapAngleTo180(entity.rotationYaw),
                                    MathHelper.wrapAngleTo180(entity.rotationPitch))
                        });
        if (this.mc.world != null && this.mc.world.isBlockLoaded(blockPos)) {
            Chunk chunkFromBlockCoords = this.mc.world.getChunkFromBlockCoords(blockPos);
            arrayList.add(
                    "Biome: "
                            + chunkFromBlockCoords.getBiome(
                                            blockPos, this.mc.world.getWorldChunkManager())
                                    .biomeName);
            arrayList.add(
                    "Light: "
                            + chunkFromBlockCoords.setLight(blockPos, 0)
                            + " ("
                            + chunkFromBlockCoords.getLightFor(EnumSkyBlock.SKY, blockPos)
                            + " sky, "
                            + chunkFromBlockCoords.getLightFor(EnumSkyBlock.BLOCK, blockPos)
                            + " block)");
            DifficultyInstance difficultyInstance =
                    this.mc.world.getDifficultyForLocation(blockPos);
            if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
                EntityPlayerMP entityPlayerMP =
                        this.mc
                                .getIntegratedServer()
                                .getConfigurationManager()
                                .getPlayerByUUID(this.mc.player.getUniqueId());
                if (entityPlayerMP != null) {
                    difficultyInstance =
                            entityPlayerMP.world.getDifficultyForLocation(
                                    new BlockPos(entityPlayerMP));
                }
            }

            String textValue = "Local Difficulty: %.2f (Day %d)";
            Object[] aobject = new Object[] {difficultyInstance.getAdditionalDifficulty(), null};
            long worldTime = this.mc.world.getWorldTime();

            aobject[1] = worldTime / 24000L;
            arrayList.add(String.format(textValue, aobject));
        }

        if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            arrayList.add(
                    "Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
        }

        if (this.mc.objectMouseOver != null
                && this.mc.objectMouseOver.typeOfHit == RayHitType.BLOCK
                && this.mc.objectMouseOver.getBlockPos() != null) {
            BlockPos blockPosValue = this.mc.objectMouseOver.getBlockPos();
            arrayList.add(
                    String.format(
                            "Looking at: %d %d %d",
                            blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ()));
        }

        return arrayList;
    }

    protected List tZ() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        long i = Runtime.getRuntime().maxMemory();
        String[] astring = astring1;
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;
        astring1 =
                new String[] {
                    String.format(
                            "Java: %s %dbit",
                            System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                };
        String text = "Mem: % 2d%% %03d/%03dMB";
        Object[] aobject = new Object[3];

        aobject[0] = l * 100L / i;
        aobject[1] = method_6be2ddaf1bd1d43d74fc(l);
        aobject[2] = method_6be2ddaf1bd1d43d74fc(i);
        astring1[1] = String.format(text, aobject);
        text = "Allocated: % 2d%% %03dMB";
        aobject = new Object[2];

        aobject[0] = j * 100L / i;
        aobject[1] = method_6be2ddaf1bd1d43d74fc(j);
        astring1[2] = String.format(text, aobject);
        astring1[3] = packedTextFragments[6];
        astring1[4] =
                String.format(
                        "Display: %dx%d (%s)",
                        Display.getWidth(), Display.getHeight(), GL11.glGetString(7936));
        astring1[5] = GL11.glGetString(7937);
        astring1[6] = GL11.glGetString(7938);
        ArrayList arrayList = Lists.newArrayList(astring1);
        if (Reflector.Gi.exists()) {
            Object object = Reflector.call(Reflector.FS, new Object[0]);
            arrayList.add(packedTextFragments[7]);
            arrayList.addAll(
                    (Collection) Reflector.call(object, Reflector.Gi, new Object[] {false}));
        }

        if (this.isReducedDebug()) {
            return arrayList;
        }

        if (this.mc.objectMouseOver != null
                && this.mc.objectMouseOver.typeOfHit == RayHitType.BLOCK
                && this.mc.objectMouseOver.getBlockPos() != null) {
            BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
            BlockState blockState = this.mc.world.getBlockState(blockPos);
            if (this.mc.world.getWorldType() != WorldType.DEBUG_WORLD) {
                blockState =
                        blockState.getBlock().getActualState(blockState, this.mc.world, blockPos);
            }

            arrayList.add(packedTextFragments[8]);
            arrayList.add(
                    String.valueOf(Block.blockRegistry.getNameForObject(blockState.getBlock())));
            UnmodifiableIterator unmodifiableiterator =
                    blockState.getProperties().entrySet().iterator();

            while (unmodifiableiterator.hasNext()) {
                Entry entry;
                Object object1;
                {
                    entry = (Entry) unmodifiableiterator.next();
                    object1 = ((Comparable) entry.getValue()).toString();
                    if (entry.getValue() == Boolean.TRUE) {
                        object1 = EnumChatFormatting.GREEN + (String) object1;
                        if (!(astring == null)) {

                            if (entry.getValue() == Boolean.FALSE) {
                                object1 = EnumChatFormatting.RED + (String) object1;
                            }
                        }
                    } else {

                        if (entry.getValue() == Boolean.FALSE) {
                            object1 = EnumChatFormatting.RED + (String) object1;
                        }
                    }
                }

                arrayList.add(((IProperty) entry.getKey()).getName() + ": " + object1);
                if (astring != null) {
                    break;
                }
            }
        }

        return arrayList;
    }

    public void renderDebugInfo(ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("debug");
        GlStateManager.pushMatrix();
        this.renderDebugInfoLeft();
        this.renderDebugInfoRight(scaledResolution);
        GlStateManager.popMatrix();
        this.mc.mcProfiler.endSection();
    }
}
