package remix.check;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import remix.util.movement.MovementUtils;
import remix.util.world.BlockCollisionHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GroundSpoofCheck extends AbstractHackerCheck {

    private static int[] legacyGroundCheckMarker;
    private Map<UUID, Integer> falseAirborneCounts;

    private Map<UUID, Double> previousYByPlayer;
    private Map<UUID, Integer> falseGroundCounts;

    public static int[] getLegacyGroundCheckMarker() {
        return remix.check.GroundSpoofCheck.legacyGroundCheckMarker;
    }

    public boolean checkPlayer(PlayerEntity player, UUID playerId) {
        boolean touchingGround =
                BlockCollisionHelper.isCollidingWithBlocks(
                        player,
                        0.15,
                        Math.max(
                                this.previousYByPlayer.getOrDefault(playerId, player.posY)
                                        - player.posY,
                                0.025));
        getLegacyGroundCheckMarker();

        double verticalChange = Math.abs(player.posY - player.prevPosY);
        boolean falseGroundClaim =
                player.onGround && !touchingGround && MovementUtils.hasMoved(player, 0.075);
        boolean falseAirborneClaim =
                !player.onGround
                        && touchingGround
                        && MovementUtils.hasMoved(player, 0.075)
                        && verticalChange < 0.025;
        if (!player.isRiding()) {
            if (falseAirborneClaim) {
                // The original uses the false-ground count as the source for this update.
                this.falseAirborneCounts.put(
                        playerId, this.falseGroundCounts.getOrDefault(playerId, 0) + 1);
            } else {
                this.falseAirborneCounts.remove(playerId);
            }
            if (falseGroundClaim) {
                this.falseGroundCounts.put(
                        playerId, this.falseGroundCounts.getOrDefault(playerId, 0) + 1);
            } else {
                this.falseGroundCounts.remove(playerId);
            }
        }

        this.previousYByPlayer.put(playerId, player.posY);
        return this.falseAirborneCounts.getOrDefault(playerId, 0) > 7
                || this.falseGroundCounts.getOrDefault(playerId, 0) > 7;
    }

    static {
        int[] aint = new int[3];
        setLegacyGroundCheckMarker(aint);
    }

    // region String decoding support

    // endregion

    public GroundSpoofCheck() {
        super("GroundSpoof", "GroundSpoof", "Ground-Spoof", HackerCheckCategory.MISC);

        this.previousYByPlayer = new HashMap<>();
        this.falseAirborneCounts = new HashMap<>();
        this.falseGroundCounts = new HashMap<>();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyGroundCheckMarker(new int[2]);
        }
    }

    public int getMaxViolations() {
        return 5;
    }

    // region String decoding support

    // endregion

    public int getViolationWeight() {
        return 1;
    }

    public static void setLegacyGroundCheckMarker(int[] aint) {
        remix.check.GroundSpoofCheck.legacyGroundCheckMarker = aint;
    }
}
