package remix.module.player;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.PlayerMovementPacket;

import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** FastUse module: player events and 1 settings. */
public final class FastUseModule extends ModuleBase {

    private final Setting<FastUseMode> modeSetting = new Setting("Mode", this, FastUseMode.NCP);


    public FastUseModule() {

        super("FastUse", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.setName("Fast Use");
    }





    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            ItemStack currentEquippedItem = minecraft.player.getCurrentEquippedItem();

            if (minecraft.player.isUsingItem()
                    && (currentEquippedItem.getItem() instanceof ItemFood
                            || currentEquippedItem.getItem() instanceof ItemPotion)) {

                switch (((FastUseMode) this.modeSetting.getValue())) {
                    case NCP:
                        this.sendPacket(new PlayerMovementPacket(event.onGround));

                        minecraft.player.setItemInUseCount(
                                Math.max(minecraft.player.getItemInUseCount() - 1, 0));

                        break;

                    case FAITHFUL:
                        while (minecraft.player.getItemInUseCount() != 0) {

                            this.sendPacket(new PlayerMovementPacket(event.onGround));

                            minecraft.player.setItemInUseCount(
                                    Math.max(minecraft.player.getItemInUseCount() - 1, 0));
                        }
                }
            }
        }
    }
}
