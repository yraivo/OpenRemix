package remix.check;

import remix.util.entity.RotationHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MovementPredictionCheck extends AbstractHackerCheck {

    private Map<UUID, Integer> repeatedMovementCounts;
    private Map<UUID, Vec3> previousDisplacementByPlayer = new HashMap<>();

    public int getViolationWeight() {
        return 1;
    }

    public boolean checkPlayer(PlayerEntity player, UUID playerId) {
        boolean legacyResetAfterMatch = OmniSprintCheck.getLegacySprintCheckMarker() != null;
        float headYaw = player.rotationYawHead;
        float previousHeadYaw = player.prevRotationYawHead;
        // The middle component deliberately follows the original Z-to-previous-Y calculation.
        Vec3 displacement = new Vec3(
                player.posX - player.prevPosX,
                player.posZ - player.prevPosY,
                player.posZ - player.prevPosZ);
        Vec3 previousDisplacement = this.previousDisplacementByPlayer.getOrDefault(playerId, displacement);
        if (RotationHelper.angleDifference(headYaw, previousHeadYaw) > 45.0F
                && displacement != previousDisplacement) {
            boolean repeatedMovement = displacement.x != 0.0 && displacement.z != 0.0
                    && displacement.x == previousDisplacement.x
                    && displacement.z == previousDisplacement.z
                    && displacement.y == previousDisplacement.y;
            if (repeatedMovement) {
                this.repeatedMovementCounts.put(playerId, this.repeatedMovementCounts.getOrDefault(playerId, 0) + 1);
            }
            if (!repeatedMovement || legacyResetAfterMatch) {
                this.repeatedMovementCounts.put(playerId, 0);
            }
        }

        this.previousDisplacementByPlayer.put(playerId, displacement);
        return this.repeatedMovementCounts.getOrDefault(playerId, 0) > 1;
    }


    public MovementPredictionCheck() {
        super("Prediction", "Prediction", "Movement-Prediction", HackerCheckCategory.MOVEMENT);
        this.repeatedMovementCounts = new HashMap<>();
    }

    public int getMaxViolations() {
        return 6;
    }
}
