package remix.check;

import remix.util.movement.MovementUtils;
import remix.util.world.BlockCollisionHelper;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlightHoverCheck extends AbstractHackerCheck {
    private Map<UUID, Integer> map = new HashMap<>();

    public FlightHoverCheck() {
        super("FlightHover", "Flight", "Flight (Hover)", HackerCheckCategory.MOVEMENT);
    }

    public boolean checkPlayer(PlayerEntity playerEntity, UUID uuid) {
        String[] astring1 = OmniSprintCheck.getLegacySprintCheckMarker();
        double d0 = Math.abs(playerEntity.posY - playerEntity.prevPosY);
        String[] astring = astring1;
        boolean flag =
                BlockCollisionHelper.isCollidingWithBlocks(playerEntity, 0.15, 0.025);
        if (MovementUtils.hasMoved(playerEntity, 0.075)
                && !playerEntity.isInWeb
                && !playerEntity.isInWater()
                && !playerEntity.isInLava()
                && !playerEntity.isOnLadder()
                && d0 < 0.005
                && !flag) {
            this.map.put(uuid, this.map.getOrDefault(uuid, 0) + 1);
            if (astring == null) {
                return this.map.getOrDefault(uuid, 0) > 6;
            }
        }

        this.map.remove(uuid);
        return false;
    }


    public int getMaxViolations() {
        return 6;
    }

    public int getViolationWeight() {
        return 1;
    }
}
