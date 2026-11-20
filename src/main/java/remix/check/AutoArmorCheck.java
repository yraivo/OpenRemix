package remix.check;

import remix.util.movement.MovementUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AutoArmorCheck extends AbstractHackerCheck {
    private Map<UUID, Integer> dP;

    private Map<UUID, ItemStack> dS = new HashMap<>();

    private Map<UUID, Boolean> dT;
    private Map<UUID, Integer> dV;

    public boolean checkPlayer(PlayerEntity playerEntity, UUID uuid) {
        int totalArmorValue = playerEntity.getTotalArmorValue();
        String[] astring1 = OmniSprintCheck.getLegacySprintCheckMarker();
        int j = this.dP.getOrDefault(uuid, totalArmorValue);
        boolean flag = MovementUtils.hasMoved(playerEntity, 0.2);
        boolean orDefault = this.dT.getOrDefault(uuid, flag);
        String[] astring = astring1;
        ItemStack currentEquippedItem = playerEntity.getCurrentEquippedItem();
        ItemStack orDefaultValue = this.dS.getOrDefault(uuid, currentEquippedItem);
        if (totalArmorValue > j) {
            if (orDefault
                    && flag
                    && !this.isArmorItem(currentEquippedItem)
                    && !this.isArmorItem(orDefaultValue)) {
                this.dV.put(uuid, this.dV.getOrDefault(uuid, 0) + 1);
                return this.dV.getOrDefault(uuid, 0) >= 3;
            }

            this.dV.put(uuid, 0);
        }

        this.dP.put(uuid, totalArmorValue);
        this.dT.put(uuid, flag);
        this.dS.put(uuid, currentEquippedItem);
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return false;
    }

    private boolean isArmorItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemArmor;
    }

    public int getViolationWeight() {
        return 1;
    }

    public AutoArmorCheck() {
        super("AutoArmor", "AutoArmor", "Auto-Armor", HackerCheckCategory.MOVEMENT);
        this.dP = new HashMap<>();
        this.dT = new HashMap<>();
        this.dV = new HashMap<>();
    }


    public int getMaxViolations() {
        return 6;
    }
}
