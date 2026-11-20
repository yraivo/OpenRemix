package remix.check;

import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ImpossiblePitchCheck extends AbstractHackerCheck {


    public ImpossiblePitchCheck() {
        super(
                "ImpossiblePitch",
                "AimImpossiblePitch",
                "Aim (Impossible Pitch)",
                HackerCheckCategory.MISC);
    }

    public int getMaxViolations() {
        return 1;
    }

    public int getViolationWeight() {
        return 1;
    }

    public boolean checkPlayer(PlayerEntity playerEntity, UUID uuid) {
        return Math.abs(playerEntity.rotationPitch) > 90.0F;
    }
}
