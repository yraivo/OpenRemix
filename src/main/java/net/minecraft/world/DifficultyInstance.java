package net.minecraft.world;

import net.minecraft.util.MathHelper;

public class DifficultyInstance {
    private EnumDifficulty worldDifficulty;
    private float additionalDifficulty;

    private float calculateAdditionalDifficulty(
            EnumDifficulty enumDifficulty, long i, long j, float f) {
        if (enumDifficulty == EnumDifficulty.PEACEFUL) {
            return 0.0F;
        }

        int k = enumDifficulty == EnumDifficulty.HARD ? 1 : 0;
        float f1 = 0.75F;
        float f2 = MathHelper.clamp(((float) i + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
        f1 += f2;
        float f3 = 0.0F;
        f3 += MathHelper.clamp((float) j / 3600000.0F, 0.0F, 1.0F) * (k != 0 ? 1.0F : 0.75F);
        f3 += MathHelper.clamp(f * 0.25F, 0.0F, f2);
        if (enumDifficulty == EnumDifficulty.EASY) {
            f3 *= 0.5F;
        }

        f1 += f3;
        return enumDifficulty.getDifficultyId() * f1;
    }

    public DifficultyInstance(EnumDifficulty bxAValue, long i, long j, float f) {
        this.worldDifficulty = bxAValue;
        this.additionalDifficulty = this.calculateAdditionalDifficulty(bxAValue, i, j, f);
    }

    public float getAdditionalDifficulty() {
        return this.additionalDifficulty;
    }

    public float getClampedAdditionalDifficulty() {
        return this.additionalDifficulty < 2.0F
                ? 0.0F
                : (this.additionalDifficulty > 4.0F
                        ? 1.0F
                        : (this.additionalDifficulty - 2.0F) / 2.0F);
    }
}
