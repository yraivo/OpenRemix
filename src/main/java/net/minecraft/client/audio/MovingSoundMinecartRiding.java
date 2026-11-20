package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecartRiding extends MovingSound {

    private static String kG;
    private EntityMinecart minecart;
    private PlayerEntity player;

    static {
        kG = "minecraft:minecart.inside";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public MovingSoundMinecartRiding(PlayerEntity anzValue, EntityMinecart anyValue) {
        super(new ResourceLocation(kG));
        this.player = anzValue;
        this.minecart = anyValue;
        this.attenuationType = SoundAttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    public void update() {
        int opaqueControlBranchValue = SoundHandler.getOpaqueControlBranchValue();
        if (!this.minecart.isDead
                && this.player.isRiding()
                && this.player.ridingEntity == this.minecart) {
            {
                float f =
                        MathHelper.sqrt(
                                this.minecart.motionX * this.minecart.motionX
                                        + this.minecart.motionZ * this.minecart.motionZ);
                if (f >= 0.01) {
                    this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 1.0F) * 0.75F;
                    if (!(opaqueControlBranchValue != 0)) {

                        this.volume = 0.0F;
                    }
                } else {

                    this.volume = 0.0F;
                }
            }

            if (opaqueControlBranchValue != 0) {
                return;
            }
        }

        this.donePlaying = true;
    }
}
