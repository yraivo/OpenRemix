package remix.check;

import remix.util.entity.RotationHelper;
import remix.util.movement.MovementUtils;
import remix.util.world.BlockCollisionHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OmniSprintCheck extends AbstractHackerCheck {

    private Map<UUID, Integer> inconsistentSprintCounts;
    private static String[] legacySprintCheckMarker;

    private Map<UUID, Float> headYawChangeByPlayer = new HashMap<>();

    public boolean checkPlayer(PlayerEntity player, UUID playerId) {
        boolean touchingGround = BlockCollisionHelper.isCollidingWithBlocks(player, 0.15, 0.02);
        float movementYaw = RotationHelper.rotationsBetweenUnsmoothed(
                player.getPositionVector(), new Vec3(player.prevPosX, player.prevPosY, player.prevPosZ))[0];
        float headYawChange = Math.abs(
                RotationHelper.angleDifference(player.rotationYawHead, player.prevRotationYawHead));
        boolean legacyResetAfterMatch = getLegacySprintCheckMarker() != null;
        float relativeYaw = RotationHelper.angleDifference(movementYaw, player.rotationYawHead);
        boolean sprintingAgainstMovement = MovementUtils.hasMoved(player, 0.02)
                && player.isSprinting() && player.hurtTime == 0 && touchingGround
                && relativeYaw > 90.0F + headYawChange;
        if (sprintingAgainstMovement) {
            this.inconsistentSprintCounts.put(playerId, this.inconsistentSprintCounts.getOrDefault(playerId, 0) + 1);
        }
        if (!sprintingAgainstMovement || legacyResetAfterMatch) {
            this.inconsistentSprintCounts.remove(playerId);
        }

        this.headYawChangeByPlayer.put(playerId, headYawChange);
        boolean flagged = this.inconsistentSprintCounts.getOrDefault(playerId, 0) >= 8;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacySprintCheckMarker(new String[2]);
        }
        return flagged;
    }

    public int getViolationWeight() {
        return 1;
    }

    public int getMaxViolations() {
        return 6;
    }

    // region String decoding support

    // endregion

    public static void setLegacySprintCheckMarker(String[] astring) {
        remix.check.OmniSprintCheck.legacySprintCheckMarker = astring;
    }

    public static String[] getLegacySprintCheckMarker() {
        return remix.check.OmniSprintCheck.legacySprintCheckMarker;
    }

    public OmniSprintCheck() {
        super("SprintDirection", "SprintDirection", "Omni-Sprint", HackerCheckCategory.MOVEMENT);
        this.inconsistentSprintCounts = new HashMap<>();
    }

    // region String decoding support

    // endregion

    static {
        setLegacySprintCheckMarker(((java.lang.String[]) (null)));
    }
}
