package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecart extends MovingSound {
    private EntityMinecart minecart;

    private float distance = 0.0F;
    private static String kG;

    // region String decoding support

    // endregion

    static {
        kG = "minecraft:minecart.base";
    }

    public MovingSoundMinecart(EntityMinecart anvValue) {
        super(new ResourceLocation(kG));
        this.minecart = anvValue;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    public void update() {
        int opaqueControlState = SoundHandler.getOpaqueControlState();
        if (this.minecart.isDead) {
            this.donePlaying = true;
            if (opaqueControlState == 0) {
                return;
            }
        }

        this.xPosF = (float) this.minecart.posX;
        this.yPosF = (float) this.minecart.posY;
        this.zPosF = (float) this.minecart.posZ;
        float f =
                MathHelper.sqrt(
                        this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
        if (f >= 0.01) {
            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
            this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 0.5F) * 0.7F;
            if (opaqueControlState == 0) {
                return;
            }
        }

        this.distance = 0.0F;
        this.volume = 0.0F;
    }

    // region String decoding support

    // endregion
}
