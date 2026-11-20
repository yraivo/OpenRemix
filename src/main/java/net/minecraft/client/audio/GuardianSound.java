package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class GuardianSound extends MovingSound {
    private static String mC;

    private EntityGuardian guardian;

    public GuardianSound(EntityGuardian anuValue) {
        super(new ResourceLocation(mC));
        this.guardian = anuValue;
        this.attenuationType = SoundAttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    static {
        mC = "minecraft:mob.guardian.attack";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void update() {
        int opaqueControlState = SoundHandler.getOpaqueControlState();
        if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
            this.xPosF = (float) this.guardian.posX;
            this.yPosF = (float) this.guardian.posY;
            this.zPosF = (float) this.guardian.posZ;
            float f = this.guardian.func_175477_p(0.0F);
            this.volume = 0.0F + 1.0F * f * f;
            this.pitch = 0.7F + 0.5F * f;
            if (opaqueControlState == 0) {
                return;
            }
        }

        this.donePlaying = true;
    }
}
