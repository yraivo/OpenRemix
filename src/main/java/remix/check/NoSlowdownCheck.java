package remix.check;

import remix.util.movement.MovementUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoSlowdownCheck extends AbstractHackerCheck {

    private Map<UUID, Integer> map = new HashMap<>();

    public int getMaxViolations() {
        return 6;
    }

    public int getViolationWeight() {
        return 1;
    }


    public boolean checkPlayer(PlayerEntity playerEntity, UUID uuid) {
        String[] astring1 = OmniSprintCheck.getLegacySprintCheckMarker();
        ItemStack currentEquippedItem = playerEntity.getCurrentEquippedItem();
        String[] astring = astring1;
        int i =
                currentEquippedItem == null
                                || !(currentEquippedItem.getItem() instanceof ItemFood)
                                        && !(currentEquippedItem.getItem() instanceof SwordItem)
                                        && !(currentEquippedItem.getItem() instanceof ItemBow)
                        ? 0
                        : 1;
        if (playerEntity.isSprinting()
                && MovementUtils.hasMoved(playerEntity, 0.075)
                && (playerEntity.isUsingItem() && i != 0 || playerEntity.isSneaking())) {
            this.map.put(uuid, this.map.getOrDefault(uuid, 0) + 1);
            if (astring == null) {
                return this.map.getOrDefault(uuid, 0) > 6;
            }
        }

        this.map.put(uuid, 0);
        return this.map.getOrDefault(uuid, 0) > 6;
    }

    public NoSlowdownCheck() {
        super("NoSlowdown", "NoSlowDown", "No Slowdown", HackerCheckCategory.MOVEMENT);
    }
}
