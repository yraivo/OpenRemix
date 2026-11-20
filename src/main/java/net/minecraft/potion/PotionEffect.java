package net.minecraft.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.legacy.auth.RemoteAuthService;
import remix.module.player.PotionSaverModule;

public class PotionEffect {

    private boolean showParticles;
    private int potionID;
    private static Logger LOGGER;
    private boolean isSplashPotion;
    private boolean isPotionDurationMax;
    private boolean isAmbient;
    private int amplifier;
    private static int[] legacyPotionFlowMarker;

    private int duration;
    private static String[] packedTextFragments;

    public CompoundTag writeCustomPotionEffectToNBT(CompoundTag compoundTag) {
        compoundTag.setByte("Id", (byte) this.getPotionID());
        compoundTag.setByte("Amplifier", (byte) this.getAmplifier());
        compoundTag.setInteger("Duration", this.getDuration());
        compoundTag.setBoolean("Ambient", this.getIsAmbient());
        compoundTag.setBoolean("ShowParticles", this.getIsShowParticles());
        return compoundTag;
    }

    public void combine(PotionEffect incoming) {
        boolean legacyFlow = getLegacyPotionFlowMarker() != null;
        if (this.potionID != incoming.potionID) {
            LOGGER.warn("This method should only be called for matching effects!");
        }

        boolean refreshed = false;
        if (incoming.amplifier > this.amplifier) {
            this.amplifier = incoming.amplifier;
            this.duration = incoming.duration;
            refreshed = true;
        }
        if ((!refreshed || legacyFlow)
                && incoming.amplifier == this.amplifier
                && this.duration < incoming.duration) {
            this.duration = incoming.duration;
            refreshed = true;
        }
        if ((!refreshed || legacyFlow) && !incoming.isAmbient && this.isAmbient) {
            this.isAmbient = incoming.isAmbient;
        }
        this.showParticles = incoming.showParticles;
    }

    public static PotionEffect readCustomPotionEffectFromNBT(CompoundTag compoundTag) {
        byte b0 = compoundTag.getByte("Id");
        if (b0 >= 0 && b0 < Potion.potionTypes.length && Potion.potionTypes[b0] != null) {
            byte b1 = compoundTag.getByte("Amplifier");
            int integer = compoundTag.getInteger("Duration");
            boolean booleanValue = compoundTag.getBoolean("Ambient");
            byte b2 = 1;
            if (compoundTag.hasKey("ShowParticles", 1)) {
                b2 =
                        remix.compat.compiler.JvmBooleanSlots.toByte(
                                compoundTag.getBoolean("ShowParticles"));
            }

            return new PotionEffect(
                    b0,
                    integer,
                    b1,
                    booleanValue,
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b2));
        } else {
            return null;
        }
    }

    public void performEffect(LivingEntity livingEntity) {
        if (this.duration > 0) {
            Potion.potionTypes[this.potionID].performEffect(livingEntity, this.amplifier);
        }
    }

    public PotionEffect(int i, int j, int k) {
        this(i, j, k, false, true);
    }

    @Override
    public int hashCode() {
        return this.potionID;
    }

    static {
        net.minecraft.potion.PotionEffect.packedTextFragments = new String[5];
        net.minecraft.potion.PotionEffect.packedTextFragments[0] = new String("");
        net.minecraft.potion.PotionEffect.packedTextFragments[1] = new String("(");
        net.minecraft.potion.PotionEffect.packedTextFragments[2] = new String(")");
        setLegacyPotionFlowMarker(null);
        net.minecraft.potion.PotionEffect.LOGGER = LogManager.getLogger();
    }

    public void setPotionDurationMax(boolean isPotionDurationMaxValue) {
        this.isPotionDurationMax = isPotionDurationMaxValue;
    }

    @Override
    public String toString() {
        boolean legacyFlow = getLegacyPotionFlowMarker() != null;
        String text = packedTextFragments[0];
        boolean showAmplifier = this.getAmplifier() > 0;
        if (showAmplifier) {
            text =
                    this.getEffectName()
                            + " x "
                            + (this.getAmplifier() + 1)
                            + ", Duration: "
                            + this.getDuration();
        }
        if (!showAmplifier || legacyFlow) {
            text = this.getEffectName() + ", Duration: " + this.getDuration();
        }

        if (this.isSplashPotion) {
            text = text + ", Splash: true";
        }

        if (!this.showParticles) {
            text = text + ", Particles: false";
        }

        String textValue =
                Potion.potionTypes[this.potionID].isUsable()
                        ? packedTextFragments[1] + text + packedTextFragments[2]
                        : text;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyPotionFlowMarker(new int[1]);
        }

        return textValue;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PotionEffect)) {
            return false;
        }

        PotionEffect potionEffect = (PotionEffect) object;
        return this.potionID == potionEffect.potionID
                && this.amplifier == potionEffect.amplifier
                && this.duration == potionEffect.duration
                && this.isSplashPotion == potionEffect.isSplashPotion
                && this.isAmbient == potionEffect.isAmbient;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public static void setLegacyPotionFlowMarker(int[] aint) {
        net.minecraft.potion.PotionEffect.legacyPotionFlowMarker = aint;
    }

    public boolean onUpdate(LivingEntity livingEntity) {
        if (this.duration > 0) {
            if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
                this.performEffect(livingEntity);
            }

            this.deincrementDuration();
        }

        return this.duration > 0;
    }

    // region String decoding support

    // endregion

    public static int[] getLegacyPotionFlowMarker() {
        return net.minecraft.potion.PotionEffect.legacyPotionFlowMarker;
    }

    public int getDuration() {
        return this.duration;
    }

    // region String decoding support

    // endregion

    public boolean getIsAmbient() {
        return this.isAmbient;
    }

    public PotionEffect(
            int potionIDValue, int j, int k, boolean isAmbientValue, boolean showParticlesValue) {
        this.potionID = potionIDValue;
        this.duration = j;
        this.amplifier = k;
        this.isAmbient = isAmbientValue;
        this.showParticles = showParticlesValue;
    }

    public String getEffectName() {
        return Potion.potionTypes[this.potionID].getName();
    }

    public int getPotionID() {
        return this.potionID;
    }

    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }

    public PotionEffect(int i, int j) {
        this(i, j, 0);
    }

    private int deincrementDuration() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        PotionSaverModule potionSaver = ClientServices.moduleManager.potionSaver;
        return (!potionSaver.isEnabled() || !potionSaver.shouldPausePotionTicks())
                        && RemoteAuthService.INSTANCE.profileAccepted
                ? --this.duration
                : this.duration;
    }

    // region String decoding support

    // endregion

    public void setSplashPotion(boolean isSplashPotionValue) {
        this.isSplashPotion = isSplashPotionValue;
    }

    public boolean getIsShowParticles() {
        return this.showParticles;
    }

    // region String decoding support

    // endregion

    // Recovered explicit constructors from original mapped instruction streams.
    public PotionEffect(PotionEffect potionEffect) {
        super();
        this.potionID = potionEffect.potionID;
        this.duration = potionEffect.duration;
        this.amplifier = potionEffect.amplifier;
        this.isAmbient = potionEffect.isAmbient;
        this.showParticles = potionEffect.showParticles;
    }
}
