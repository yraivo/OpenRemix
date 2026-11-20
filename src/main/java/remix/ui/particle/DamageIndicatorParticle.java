package remix.ui.particle;

import remix.util.time.ElapsedTimer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public final class DamageIndicatorParticle extends EntityFX {
    protected static final float xo = 0.1F;
    protected float xp;
    private int xq;
    protected boolean xr = true;
    protected boolean xs = true;
    protected static final float xt = 3.0F;
    protected static final int xu = 12;
    protected static final double xv = 1.5;
    protected String xw;

    public DamageIndicatorParticle(
            String text,
            int i,
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5) {
        super(worldValue, d0, d1, d2, d3, d4, d5);
        this.xp = 1.0F;
        this.particleTextureJitterX = 0.0F;
        this.particleTextureJitterY = 0.0F;
        this.particleGravity = 0.1F;
        this.particleScale = 3.0F;
        this.particleMaxAge = 12;
        this.xw = text;
        this.xq = i;
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f1,
            float f2,
            float f8,
            float f9,
            float f10) {
        int[] aint;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        {
            f3 = -MinecraftClient.getMinecraft().player.rotationYaw;
            int[] aint1 = ElapsedTimer.fM();
            f4 = MinecraftClient.getMinecraft().player.rotationPitch;
            f5 =
                    (float)
                            (this.prevPosX
                                    + (this.posX - this.prevPosX) * f
                                    - net.minecraft.client.particle.EntityFX.interpPosX);
            aint = aint1;
            f6 =
                    (float)
                            (this.prevPosY
                                    + (this.posY - this.prevPosY) * f1
                                    - net.minecraft.client.particle.EntityFX.interpPosY);
            f7 =
                    (float)
                            (this.prevPosZ
                                    + (this.posZ - this.prevPosZ) * f2
                                    - net.minecraft.client.particle.EntityFX.interpPosZ);
            GL11.glPushMatrix();
            if (this.xr) {
                GL11.glDepthFunc(519);

                
            } else {

                GL11.glDepthFunc(515);
            }
        }

        GL11.glTranslatef(f5, f6, f7);
        GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glScaled(
                this.particleScale * 0.008, this.particleScale * 0.008, this.particleScale * 0.008);
        GL11.glScaled(this.xp, this.xp, this.xp);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FontRenderer anp = MinecraftClient.getMinecraft().fontRendererObj;
        anp.drawStringWithShadow(
                this.xw,
                -MathHelper.floor(anp.getStringWidth(this.xw) / 2.0F) + 1,
                -MathHelper.floor(anp.FONT_HEIGHT / 2.0F) + 1,
                this.xq);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthFunc(515);
        GL11.glPopMatrix();
        if (this.xs) {
            this.particleScale *= 1.08F;
            if (!(this.particleScale > 6.300000000000001)) {
                return;
            }

            this.xs = false;

            return;
        }

        this.particleScale *= 0.96F;
    }

    public DamageIndicatorParticle(
            double d0,
            World worldValue,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            double d6) {
        super(worldValue, d1, d2, d3, d4, d5, d6);
        this.xp = 1.0F;
        this.particleTextureJitterX = 0.0F;
        this.particleTextureJitterY = 0.0F;
        this.particleGravity = 0.1F;
        this.particleScale = 3.0F;
        this.particleMaxAge = 12;
        this.xq = d0 < 0.0 ? -11141291 : -43691;
        this.xw = Double.toString(Math.abs(d0));
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.motionY = this.motionY - 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (this.onGround) {
            this.motionY = -this.motionY;
            this.setDead();
        }
    }

    public int getFXLayer() {
        return 3;
    }

    protected DamageIndicatorParticle(World worldValue, double d0, double d1, double d2) {
        this(0.0, worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
    }
}
