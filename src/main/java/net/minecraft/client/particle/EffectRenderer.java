package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import optifine.OptiFineConfig;
import optifine.Reflector;
import net.minecraft.client.particle.EffectRendererLayerCallable;
import net.minecraft.client.particle.EffectRendererParticleCallable;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.NoRenderModule;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EffectRenderer {
    private static String particleFlowMarker;

    private static ResourceLocation particleTextures;
    private Random rand;

    private static String[] packedTextFragments;
    private List particleEmitters;
    protected World worldObj;
    private Map particleTypes;
    private List[][] fxLayers = new List[4][];
    private TextureManager renderer;

    private boolean reuseBarrierParticle(EntityFX entityFX, List<EntityFX> entityFXList) {
        for (java.util.Iterator<?> __recoveryIterator171_1335 = (entityFXList).iterator();
                (__recoveryIterator171_1335).hasNext(); ) {
            EntityFX entityFXValue = (EntityFX) (__recoveryIterator171_1335).next();
            if (entityFXValue instanceof Barrier
                    && entityFX.posX == entityFXValue.posX
                    && entityFX.posY == entityFXValue.posY
                    && entityFX.posZ == entityFXValue.posZ) {
                entityFXValue.particleAge = 0;
                return true;
            }
        }

        return false;
    }

    private void moveToLayer(EntityFX entityFX, int i, int j) {
        String text = getParticleFlowMarker();
        int k = 0;
        String textValue = text;

        while (k < 4) {
            if (this.fxLayers[k][i].contains(entityFX)) {
                this.fxLayers[k][i].remove(entityFX);
                this.fxLayers[k][j].add(entityFX);
            }

            k++;
            if (textValue == null) {
                break;
            }
        }
    }

    private void tickParticleLayer(int i) {
        String text = getParticleFlowMarker();
        int j = 0;
        String textValue = text;

        while (j < 2) {
            this.updateEffectAlphaLayer(this.fxLayers[i][j]);
            j++;
            if (textValue == null) {
                break;
            }
        }
    }

    public void addBlockHitEffects(BlockPos blockPos, RayTraceResult rayTraceResult) {
        Block block = this.worldObj.getBlockState(blockPos).getBlock();
        boolean flag =
                Reflector.callBoolean(
                        block,
                        Reflector.ForgeBlock_addHitEffects,
                        new Object[] {this.worldObj, rayTraceResult, this});
        if (block != null && !flag) {
            this.addBlockHitEffects(blockPos, rayTraceResult.sideHit);
        }
    }

    public String getStatistics() {
        String text = getParticleFlowMarker();
        int i = 0;
        String textValue = text;
        int j = 0;

        while (j < 4) {
            int k = 0;

            while (true) {
                if (k < 2) {
                    i += this.fxLayers[j][k].size();
                    k++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    return packedTextFragments[0] + i;
                }
                break;
            }
        }

        return packedTextFragments[0] + i;
    }

    public void clearEffects(World world) {
        boolean legacyFirstBucketOnly = getParticleFlowMarker() == null;
        this.worldObj = world;
        int layers = legacyFirstBucketOnly ? 1 : this.fxLayers.length;
        int buckets = legacyFirstBucketOnly ? 1 : 2;
        for (int layer = 0; layer < layers; layer++) {
            for (int bucket = 0; bucket < buckets; bucket++) {
                this.fxLayers[layer][bucket].clear();
            }
        }
        this.particleEmitters.clear();
    }

    public void moveToAlphaLayer(EntityFX entityFX) {
        this.moveToLayer(entityFX, 1, 0);
    }

    public static String getParticleFlowMarker() {
        return net.minecraft.client.particle.EffectRenderer.particleFlowMarker;
    }

    private void tickParticle(EntityFX entityFX) {
        try {
            entityFX.onUpdate();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Particle being ticked");
            int fxLayer = entityFX.getFXLayer();
            crashReportCategory.addCrashSectionCallable(
                    "Particle", new ParticleCrashDetail(this, entityFX));
            crashReportCategory.addCrashSectionCallable(
                    "Particle Type", new ParticleTextureCrashDetail(this, fxLayer));
            throw new ReportedException(crashReport);
        }
    }

    public void updateEffects() {
        String text = getParticleFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < 4) {
            this.tickParticleLayer(i);
            i++;
            if (textValue == null) {
                break;
            }
        }

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_6239 = (this.particleEmitters).iterator();
                (__recoveryIterator171_6239).hasNext(); ) {
            EntityParticleEmitter entityParticleEmitter =
                    (EntityParticleEmitter) (__recoveryIterator171_6239).next();
            entityParticleEmitter.onUpdate();
            if (entityParticleEmitter.isDead) {
                arrayList.add(entityParticleEmitter);
            }

            if (textValue == null) {
                break;
            }
        }

        this.particleEmitters.removeAll(arrayList);
    }

    public void emitParticleAtEntity(Entity entity, EnumParticleTypes enumParticleTypes) {
        this.particleEmitters.add(
                new EntityParticleEmitter(this.worldObj, entity, enumParticleTypes));
    }

    public void moveToNoAlphaLayer(EntityFX entityFX) {
        this.moveToLayer(entityFX, 0, 1);
    }

    public EffectRenderer(World world, TextureManager textures) {
        boolean legacyFirstBucketOnly = getParticleFlowMarker() == null;
        this.particleEmitters = Lists.newArrayList();
        this.rand = new Random();
        this.particleTypes = Maps.newHashMap();
        this.worldObj = world;
        this.renderer = textures;

        int layers = legacyFirstBucketOnly ? 1 : this.fxLayers.length;
        int buckets = legacyFirstBucketOnly ? 1 : 2;
        for (int layer = 0; layer < layers; layer++) {
            this.fxLayers[layer] = new List[2];
            for (int bucket = 0; bucket < buckets; bucket++) {
                this.fxLayers[layer][bucket] = Lists.newArrayList();
            }
        }
        this.registerVanillaParticles();
    }

    public void addBlockHitEffects(BlockPos blockPos, Facing facing) {
        BlockState blockState = this.worldObj.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getRenderType() != -1) {
            int x = blockPos.getX();
            int j = blockPos.getY();
            int k = blockPos.getZ();
            float f = 0.1F;
            double d0 =
                    x
                            + this.rand.nextDouble()
                                    * (block.getBlockBoundsMaxX()
                                            - block.getBlockBoundsMinX()
                                            - f * 2.0F)
                            + f
                            + block.getBlockBoundsMinX();
            double d1 =
                    j
                            + this.rand.nextDouble()
                                    * (block.getBlockBoundsMaxY()
                                            - block.getBlockBoundsMinY()
                                            - f * 2.0F)
                            + f
                            + block.getBlockBoundsMinY();
            double d2 =
                    k
                            + this.rand.nextDouble()
                                    * (block.getBlockBoundsMaxZ()
                                            - block.getBlockBoundsMinZ()
                                            - f * 2.0F)
                            + f
                            + block.getBlockBoundsMinZ();
            if (facing == Facing.DOWN) {
                d1 = j + block.getBlockBoundsMinY() - f;
            }

            if (facing == Facing.UP) {
                d1 = j + block.getBlockBoundsMaxY() + f;
            }

            if (facing == Facing.NORTH) {
                d2 = k + block.getBlockBoundsMinZ() - f;
            }

            if (facing == Facing.SOUTH) {
                d2 = k + block.getBlockBoundsMaxZ() + f;
            }

            if (facing == Facing.WEST) {
                d0 = x + block.getBlockBoundsMinX() - f;
            }

            if (facing == Facing.EAST) {
                d0 = x + block.getBlockBoundsMaxX() + f;
            }

            this.addEffect(
                    new EntityDiggingFX(this.worldObj, d0, d1, d2, 0.0, 0.0, 0.0, blockState)
                            .applyBlockTint(blockPos)
                            .multiplyVelocity(0.2F)
                            .multipleParticleScaleBy(0.6F));
        }
    }

    private void updateEffectAlphaLayer(List list) {
        String text = getParticleFlowMarker();
        ArrayList arrayList = Lists.newArrayList();
        String textValue = text;
        int i = 0;

        while (i < list.size()) {
            EntityFX entityFX = (EntityFX) list.get(i);
            this.tickParticle(entityFX);
            if (entityFX.isDead) {
                arrayList.add(entityFX);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        list.removeAll(arrayList);
    }

    public void renderParticles(Entity view, float partialTicks) {
        boolean legacyFirstBucketOnly = getParticleFlowMarker() == null;
        float rotationX = ActiveRenderInfo.getRotationX();
        float rotationZ = ActiveRenderInfo.getRotationZ();
        float rotationYZ = ActiveRenderInfo.getRotationYZ();
        float rotationXY = ActiveRenderInfo.getRotationXY();
        float rotationXZ = ActiveRenderInfo.getRotationXZ();
        EntityFX.interpPosX = view.lastTickPosX + (view.posX - view.lastTickPosX) * partialTicks;
        EntityFX.interpPosY = view.lastTickPosY + (view.posY - view.lastTickPosY) * partialTicks;
        EntityFX.interpPosZ = view.lastTickPosZ + (view.posZ - view.lastTickPosZ) * partialTicks;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);

        int layers = legacyFirstBucketOnly ? 1 : 3;
        int buckets = legacyFirstBucketOnly ? 1 : 2;
        for (int layer = 0; layer < layers; layer++) {
            for (int bucket = 0; bucket < buckets; bucket++) {
                List particles = this.fxLayers[layer][bucket];
                if (particles.isEmpty()) {
                    continue;
                }
                this.beginParticleBucket(layer, bucket, legacyFirstBucketOnly);
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer vertices = tessellator.getWorldRenderer();
                vertices.startDrawingQuads();
                for (int index = 0; index < particles.size(); index++) {
                    EntityFX particle = (EntityFX) particles.get(index);
                    vertices.setPackedLightmap(particle.getBrightnessForRender(partialTicks));
                    try {
                        particle.renderParticle(vertices, view, partialTicks,
                                rotationX, rotationXZ, rotationZ, rotationYZ, rotationXY);
                    } catch (Throwable failure) {
                        CrashReport report = CrashReport.makeCrashReport(failure, "Rendering Particle");
                        CrashReportCategory detail = report.makeCategory("Particle being rendered");
                        detail.addCrashSectionCallable("Particle", new EffectRendererParticleCallable(this, particle));
                        detail.addCrashSectionCallable("Particle Type", new EffectRendererLayerCallable(this, layer));
                        throw new ReportedException(report);
                    }
                    if (legacyFirstBucketOnly) {
                        break;
                    }
                }
                tessellator.draw();
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
    }

    private void beginParticleBucket(int layer, int bucket, boolean legacyFlow) {
        if (bucket == 0) {
            GlStateManager.depthMask(false);
        }
        if (bucket != 0 || legacyFlow) {
            GlStateManager.depthMask(true);
        }
        if (layer != 1) {
            this.renderer.bindTexture(particleTextures);
        }
        if (layer == 1 || legacyFlow) {
            this.renderer.bindTexture(TextureMap.locationBlocksTexture);
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderLitParticles(Entity entity, float f) {

        float f2 = MathHelper.cos(entity.rotationYaw * (float) (Math.PI / 180.0));
        float f3 = MathHelper.sin(entity.rotationYaw * (float) (Math.PI / 180.0));
        String text = getParticleFlowMarker();
        float f4 = -f3 * MathHelper.sin(entity.rotationPitch * (float) (Math.PI / 180.0));
        float f5 = f2 * MathHelper.sin(entity.rotationPitch * (float) (Math.PI / 180.0));
        String textValue = text;
        float f6 = MathHelper.cos(entity.rotationPitch * (float) (Math.PI / 180.0));
        int i = 0;

        while (i < 2) {
            List list = this.fxLayers[3][i];
            if (!list.isEmpty()) {
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                int j = 0;

                while (j < list.size()) {
                    EntityFX entityFX = (EntityFX) list.get(j);
                    worldRenderer.setPackedLightmap(entityFX.getBrightnessForRender(f));
                    entityFX.renderParticle(worldRenderer, entity, f, f2, f6, f3, f4, f5);
                    j++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    public EntityFX spawnEffectParticle(
            int i, double d0, double d1, double d2, double d3, double d4, double d5, int... aint) {
        IParticleFactory iParticleFactory = (IParticleFactory) this.particleTypes.get(i);
        if (iParticleFactory != null) {
            EntityFX entityFX =
                    iParticleFactory.getEntityFX(i, this.worldObj, d0, d1, d2, d3, d4, d5, aint);
            if (entityFX != null) {
                this.addEffect(entityFX);
                return entityFX;
            }
        }

        return null;
    }

    public static void setParticleFlowMarker(String text) {
        net.minecraft.client.particle.EffectRenderer.particleFlowMarker = text;
    }

    public void addEffect(EntityFX entityFX) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NoRenderModule noRender = ClientServices.moduleManager.noRender;
        if (entityFX != null
                && (!noRender.isEnabled() || !noRender.particlesSetting.asBoolean())
                && (!(entityFX instanceof EntityFireworkSparkFX)
                        || OptiFineConfig.isFireworkParticles())) {
            int fxLayer = entityFX.getFXLayer();
            int j = entityFX.getAlpha() != 1.0F ? 0 : 1;
            if (this.fxLayers[fxLayer][j].size() >= 4000) {
                this.fxLayers[fxLayer][j].remove(0);
            }

            if (!(entityFX instanceof Barrier)
                    || !this.reuseBarrierParticle(entityFX, this.fxLayers[fxLayer][j])) {
                this.fxLayers[fxLayer][j].add(entityFX);
            }
        }
    }

    private void registerVanillaParticles() {
        this.registerParticle(
                EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFXFactory());
        this.registerParticle(
                EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFXFactory());
        this.registerParticle(
                EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFXFactory());
        this.registerParticle(
                EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFXFactory());
        this.registerParticle(
                EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFXFactory());
        this.registerParticle(
                EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFXFactory());
        this.registerParticle(
                EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFXFactory());
        this.registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FXFactory());
        this.registerParticle(
                EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FXMagicFactory());
        this.registerParticle(
                EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFXFactory());
        this.registerParticle(
                EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFXFactory());
        this.registerParticle(
                EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFXFactory());
        this.registerParticle(
                EnumParticleTypes.SPELL_INSTANT.getParticleID(),
                new EntitySpellParticleFXInstantFactory());
        this.registerParticle(
                EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFXMobFactory());
        this.registerParticle(
                EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(),
                new EntitySpellParticleFXAmbientMobFactory());
        this.registerParticle(
                EnumParticleTypes.SPELL_WITCH.getParticleID(),
                new EntitySpellParticleFXWitchFactory());
        this.registerParticle(
                EnumParticleTypes.DRIP_WATER.getParticleID(),
                new EntityDropParticleFXWaterFactory());
        this.registerParticle(
                EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFXLavaFactory());
        this.registerParticle(
                EnumParticleTypes.VILLAGER_ANGRY.getParticleID(),
                new EntityHeartFXAngryVillagerFactory());
        this.registerParticle(
                EnumParticleTypes.VILLAGER_HAPPY.getParticleID(),
                new EntityAuraFXHappyVillagerFactory());
        this.registerParticle(
                EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFXFactory());
        this.registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFXFactory());
        this.registerParticle(
                EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFXFactory());
        this.registerParticle(
                EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(),
                new EntityEnchantmentTableParticleFXEnchantmentTable());
        this.registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFXFactory());
        this.registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFXFactory());
        this.registerParticle(
                EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFXFactory());
        this.registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFXFactory());
        this.registerParticle(
                EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFXFactory());
        this.registerParticle(
                EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFXSnowballFactory());
        this.registerParticle(
                EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFXFactory());
        this.registerParticle(
                EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFXSlimeFactory());
        this.registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFXFactory());
        this.registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new BarrierFactory());
        this.registerParticle(
                EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFXFactory());
        this.registerParticle(
                EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFXFactory());
        this.registerParticle(
                EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFXFactory());
        this.registerParticle(
                EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFXFactory());
        this.registerParticle(
                EnumParticleTypes.EXPLOSION_LARGE.getParticleID(),
                new EntityLargeExplodeFXFactory());
        this.registerParticle(
                EnumParticleTypes.FIREWORKS_SPARK.getParticleID(),
                new EntityFireworkStarterFX_Factory());
        this.registerParticle(
                EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearanceFactory());
    }

    public void registerParticle(int i, IParticleFactory iParticleFactory) {
        this.particleTypes.put(i, iParticleFactory);
    }

    public void addBlockDestroyEffects(BlockPos position, BlockState state) {
        boolean legacyFirstFragmentOnly = getParticleFlowMarker() == null;
        Block block = state.getBlock();
        boolean createFragments = this.shouldSpawnBlockFragments(position, block, legacyFirstFragmentOnly);
        if (!createFragments) {
            return;
        }

        BlockState actualState = block.getActualState(state, this.worldObj, position);
        int steps = legacyFirstFragmentOnly ? 1 : 4;
        for (int xIndex = 0; xIndex < steps; xIndex++) {
            for (int yIndex = 0; yIndex < steps; yIndex++) {
                for (int zIndex = 0; zIndex < steps; zIndex++) {
                    double x = position.getX() + (xIndex + 0.5) / 4.0;
                    double y = position.getY() + (yIndex + 0.5) / 4.0;
                    double z = position.getZ() + (zIndex + 0.5) / 4.0;
                    this.addEffect(new EntityDiggingFX(this.worldObj, x, y, z,
                            x - position.getX() - 0.5, y - position.getY() - 0.5, z - position.getZ() - 0.5,
                            actualState).applyBlockTint(position));
                }
            }
        }
    }

    private boolean shouldSpawnBlockFragments(BlockPos position, Block block, boolean legacyFlow) {
        if (Reflector.forgeBlockAddDestroyEffects.exists() && Reflector.forgeBlockIsAir.exists()) {
            Reflector.callBoolean(block, Reflector.forgeBlockIsAir, new Object[] {this.worldObj, position});
            boolean fallback = !Reflector.callBoolean(block, Reflector.forgeBlockIsAir, new Object[] {this.worldObj, position})
                    && !Reflector.callBoolean(block, Reflector.forgeBlockAddDestroyEffects, new Object[] {this.worldObj, position, this});
            if (!legacyFlow) {
                return fallback;
            }
        }
        return block.getMaterial() != Material.AIR;
    }

    static {
        net.minecraft.client.particle.EffectRenderer.packedTextFragments = new String[4];
        net.minecraft.client.particle.EffectRenderer.packedTextFragments[0] = new String("");
        net.minecraft.client.particle.EffectRenderer.packedTextFragments[1] = new String("N0GcXb");
        String s2 = packedTextFragments[1];
        setParticleFlowMarker(s2);
        net.minecraft.client.particle.EffectRenderer.particleTextures =
                new ResourceLocation("textures/particle/particles.png");
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
