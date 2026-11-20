package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import optifine.PlayerItemsLayer;
import optifine.Reflector;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class RenderManager {
    private double renderPosX;
    private double renderPosZ;
    public float playerViewX;
    private boolean renderOutlines;

    private FontRenderer textRenderer;
    private double renderPosY;
    public GameSettings options;
    private boolean renderShadow;
    public float playerViewY;

    private boolean debugBoundingBox;
    public double viewerPosZ;
    private Map skinMap;
    private RenderPlayer playerRenderer;
    public Entity pointedEntity;
    public TextureManager renderEngine;
    public Entity livingPlayer;
    public World worldObj;
    private Map entityRenderMap = Maps.newHashMap();

    public double viewerPosY;
    public double viewerPosX;

    public Render getEntityClassRenderObject(Class oclass) {
        Render render = (Render) this.entityRenderMap.get(oclass);
        if (render == null && oclass != Entity.class) {
            render = this.getEntityClassRenderObject(oclass.getSuperclass());
            this.entityRenderMap.put(oclass, render);
        }

        return render;
    }

    public double getDistanceToCamera(double d0, double d1, double d2) {
        double d3 = d0 - this.viewerPosX;
        double d4 = d1 - this.viewerPosY;
        double d5 = d2 - this.viewerPosZ;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public boolean renderEntitySimple(Entity entity, float f) {
        return this.renderEntityStatic(entity, f, false);
    }

    public void setDebugBoundingBox(boolean flag) {
        this.debugBoundingBox = flag;
    }

    private void renderDebugBoundingBox(
            Entity entity, double d0, double d1, double d2, float f3, float f) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        float f1 = entity.width / 2.0F;
        BoundingBox boundingBox = entity.getEntityBoundingBox();
        BoundingBox boundingBoxValue =
                new BoundingBox(
                        boundingBox.minX - entity.posX + d0,
                        boundingBox.minY - entity.posY + d1,
                        boundingBox.minZ - entity.posZ + d2,
                        boundingBox.maxX - entity.posX + d0,
                        boundingBox.maxY - entity.posY + d1,
                        boundingBox.maxZ - entity.posZ + d2);
        RenderGlobal.drawOutlinedBoundingBox(boundingBoxValue, 16777215);
        if (entity instanceof LivingEntity) {

            RenderGlobal.drawOutlinedBoundingBox(
                    new BoundingBox(
                            d0 - f1,
                            d1 + entity.getEyeHeight() - 0.01F,
                            d2 - f1,
                            d0 + f1,
                            d1 + entity.getEyeHeight() + 0.01F,
                            d2 + f1),
                    16711680);
        }

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        Vec3 vec3 = entity.getLook(f);
        worldRenderer.startDrawing(3);
        worldRenderer.setOpaqueVertexColorFromRgb(255);
        worldRenderer.addVertex(d0, d1 + entity.getEyeHeight(), d2);
        worldRenderer.addVertex(
                d0 + vec3.x * 3.0, d1 + entity.getEyeHeight() + vec3.y * 3.0, d2 + vec3.z * 2.0);
        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }

    public void setPlayerViewY(float playerViewYValue) {
        this.playerViewY = playerViewYValue;
    }


    public void setRenderOutlines(boolean flag) {
        this.renderOutlines = flag;
    }

    public void setRenderShadow(boolean flag) {
        this.renderShadow = flag;
    }

    public double getViewerPosX() {
        return this.viewerPosX;
    }

    public boolean isDebugBoundingBox() {
        return this.debugBoundingBox;
    }

    public boolean doRenderEntity(
            Entity entity, double d0, double d1, double d2, float f, float f1, boolean flag) {
        Render render = null;

        try {
            render = this.getEntityRenderObject(entity);
            if (render != null && this.renderEngine != null) {
                try {
                    if (render instanceof RendererLivingEntity) {
                        ((RendererLivingEntity) render).setRenderOutlines(this.renderOutlines);
                    }

                    render.doRender(entity, d0, d1, d2, f, f1);
                } catch (Throwable throwable2) {
                    throw new ReportedException(
                            CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
                }

                try {
                    if (!this.renderOutlines) {
                        render.doRenderShadowAndFire(entity, d0, d1, d2, f, f1);
                    }
                } catch (Throwable throwable1) {
                    throw new ReportedException(
                            CrashReport.makeCrashReport(
                                    throwable1, "Post-rendering entity in world"));
                }

                if (this.debugBoundingBox && !entity.isInvisible() && !flag) {
                    try {
                        this.renderDebugBoundingBox(entity, d0, d1, d2, f, f1);
                    } catch (Throwable throwable) {
                        throw new ReportedException(
                                CrashReport.makeCrashReport(
                                        throwable, "Rendering entity hitbox in world"));
                    }
                }

                return true;
            } else {
                return this.renderEngine == null;
            }
        } catch (Throwable throwable3) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Entity being rendered");
            entity.addEntityCrashInfo(crashReportCategory);
            CrashReportCategory crashReportCategoryValue =
                    crashReport.makeCategory("Renderer details");
            crashReportCategoryValue.addCrashSection("Assigned renderer", render);
            crashReportCategoryValue.addCrashSection(
                    "Location", CrashReportCategory.getCoordinateInfo(d0, d1, d2));
            crashReportCategoryValue.addCrashSection("Rotation", f);
            crashReportCategoryValue.addCrashSection("Delta", f1);
            throw new ReportedException(crashReport);
        }
    }

    public boolean renderEntityWithPosYaw(
            Entity entity, double d0, double d1, double d2, float f, float f1) {
        return this.doRenderEntity(entity, d0, d1, d2, f, f1, false);
    }

    public void setWorld(World aDMValue) {
        this.worldObj = aDMValue;
    }

    public double getViewerPosY() {
        return this.viewerPosY;
    }

    public FontRenderer getFontRenderer() {
        return this.textRenderer;
    }

    public void cacheActiveRenderInfo(
            World aDMValue,
            FontRenderer aDGValue,
            Entity aDLValue,
            Entity aDJValue,
            GameSettings aDHValue,
            float f) {
        {
            Render.getLegacyEntityRenderState();

            this.worldObj = aDMValue;
            this.options = aDHValue;
            this.livingPlayer = aDLValue;

            this.pointedEntity = aDJValue;
            this.textRenderer = aDGValue;
            if (aDLValue instanceof LivingEntity && ((LivingEntity) aDLValue).isPlayerSleeping()) {
                {
                    BlockState blockState = aDMValue.getBlockState(new BlockPos(aDLValue));
                    Block block = blockState.getBlock();
                    if (Reflector.callBoolean(
                            Reflector.Ia, aDMValue, new BlockPos(aDLValue), aDLValue)) {
                        Facing facing =
                                (Facing)
                                        Reflector.call(
                                                block,
                                                Reflector.Gx,
                                                aDMValue,
                                                new BlockPos(aDLValue));
                        int horizontalIndex = facing.getHorizontalIndex();
                        this.playerViewY = horizontalIndex * 90 + 180;
                        this.playerViewX = 0.0F;

                        
                    } else {

                        if (block == Blocks.bed) {
                            int j = ((Facing) blockState.getValue(BlockBed.AGE)).getHorizontalIndex();
                            this.playerViewY = j * 90 + 180;
                            this.playerViewX = 0.0F;
                        }
                    }
                }

                
            } else {

                this.playerViewY =
                        aDLValue.prevRotationYaw
                                + (aDLValue.rotationYaw - aDLValue.prevRotationYaw) * f;
                this.playerViewX =
                        aDLValue.prevRotationPitch
                                + (aDLValue.rotationPitch - aDLValue.prevRotationPitch) * f;
            }
        }

        if (aDHValue.thirdPersonView == 2) {
            this.playerViewY += 180.0F;
        }

        this.viewerPosX = aDLValue.lastTickPosX + (aDLValue.posX - aDLValue.lastTickPosX) * f;
        this.viewerPosY = aDLValue.lastTickPosY + (aDLValue.posY - aDLValue.lastTickPosY) * f;
        this.viewerPosZ = aDLValue.lastTickPosZ + (aDLValue.posZ - aDLValue.lastTickPosZ) * f;
    }

    public void setRenderPosition(
            double renderPosXValue, double renderPosYValue, double renderPosZValue) {
        this.renderPosX = renderPosXValue;
        this.renderPosY = renderPosYValue;
        this.renderPosZ = renderPosZValue;
    }

    public double getRenderOriginX() {
        return this.renderPosX;
    }

    public double getRenderPosX() {
        return this.renderPosX;
    }

    public double getRenderPosY() {
        return this.renderPosY;
    }

    public double getRenderPosZ() {
        return this.renderPosZ;
    }

    public RenderManager(TextureManager aDKValue, RenderItem renderItem) {
        this.skinMap = Maps.newHashMap();
        this.renderOutlines = false;
        this.renderShadow = true;
        this.debugBoundingBox = false;
        this.renderEngine = aDKValue;
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
        this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7F));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7F));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7F));
        this.entityRenderMap.put(
                EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7F));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5F));
        this.entityRenderMap.put(
                EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3F));
        this.entityRenderMap.put(
                EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4F));
        this.entityRenderMap.put(
                EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3F));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
        this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
        this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
        this.entityRenderMap.put(
                SlimeEntity.class, new RenderSlime(this, new ModelSlime(16), 0.25F));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.entityRenderMap.put(
                EntityGiantZombie.class,
                new RenderGiantZombie(this, new ModelZombie(), 0.5F, 6.0F));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
        Render.getLegacyEntityRenderState();

        this.entityRenderMap.put(SquidEntity.class, new RenderSquid(this, new ModelSquid(), 0.7F));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.entityRenderMap.put(BatEntity.class, new RenderBat(this));
        this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
        this.entityRenderMap.put(Entity.class, new RenderEntity(this));
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, renderItem));
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
        this.entityRenderMap.put(
                EntitySnowball.class, new RenderSnowball(this, Items.snowball, renderItem));
        this.entityRenderMap.put(
                EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, renderItem));
        this.entityRenderMap.put(
                EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, renderItem));
        this.entityRenderMap.put(
                EntityEgg.class, new RenderSnowball(this, Items.egg, renderItem));
        this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, renderItem));
        this.entityRenderMap.put(
                EntityExpBottle.class,
                new RenderSnowball(this, Items.experience_bottle, renderItem));
        this.entityRenderMap.put(
                EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, renderItem));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, renderItem));
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));

        this.entityRenderMap.put(
                EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.playerRenderer = new RenderPlayer(this);
        this.skinMap.put("default", this.playerRenderer);
        this.skinMap.put("slim", new RenderPlayer(this, true));
        PlayerItemsLayer.register(this.skinMap);
        if (Reflector.GG.exists()) {
            Reflector.call(Reflector.GG, this, this.entityRenderMap);
        }
    }

    public Render getEntityRenderObject(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            String text = ((AbstractClientPlayer) entity).getSkinType();
            RenderPlayer renderPlayer = (RenderPlayer) this.skinMap.get(text);
            return renderPlayer != null ? renderPlayer : this.playerRenderer;
        } else {
            return this.getEntityClassRenderObject(entity.getClass());
        }
    }

    public boolean renderEntityStatic(Entity entity, float f, boolean flag) {
        if (entity.ticksExisted == 0) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
        }

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
        float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        int brightnessForRender = entity.getBrightnessForRender(f);
        if (entity.isBurning()) {
            brightnessForRender = 15728880;
        }

        int j = brightnessForRender % 65536;
        int k = brightnessForRender / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return this.doRenderEntity(
                entity,
                d0 - this.renderPosX,
                d1 - this.renderPosY,
                d2 - this.renderPosZ,
                f1,
                f,
                flag);
    }

    public void setEntityRenderMap(Map map) {
        this.entityRenderMap = map;
    }

    public double getViewerPosZ() {
        return this.viewerPosZ;
    }

    public double getRenderOriginY() {
        return this.renderPosY;
    }

    public boolean shouldRender(
            Entity entity, ICamera iCamera, double d0, double d1, double d2) {
        Render entityRenderObject = this.getEntityRenderObject(entity);
        return entityRenderObject != null
                && entityRenderObject.shouldRender(entity, iCamera, d0, d1, d2);
    }

    public double getRenderOriginZ() {
        return this.renderPosZ;
    }

    public Map<String, RenderPlayer> getSkinMap() {
        return Collections.unmodifiableMap(this.skinMap);
    }

    public boolean isRenderShadow() {
        return this.renderShadow;
    }

    public void renderWitherSkull(Entity entity, float f) {
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
        Render entityRenderObject = this.getEntityRenderObject(entity);
        if (entityRenderObject != null && this.renderEngine != null) {
            int brightnessForRender = entity.getBrightnessForRender(f);
            int j = brightnessForRender % 65536;
            int k = brightnessForRender / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            entityRenderObject.renderName(
                    entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ);
        }
    }

    public Map getEntityRenderMap() {
        return this.entityRenderMap;
    }
}
