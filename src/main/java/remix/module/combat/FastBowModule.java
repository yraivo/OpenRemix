package remix.module.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import remix.module.combat.FastBowMode;
import remix.util.movement.MovementUtils;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** Sends the configured bow-use movement sequence and release packet while a bow is held. No additional state machine exists beyond the shared timer. */

public final class FastBowModule extends ModuleBase {

    private Setting packetAmountSetting;



    private Setting<FastBowMode> modeSetting = new Setting("Mode", this, FastBowMode.NCP);



    public FastBowModule() {

        super("FastBow", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.packetAmountSetting = new Setting("PacketAmount", this, 20.0, 10.0, 40.0, 1.0);

        this.setName("Fast Bow");

    }



    public String getSuffix() {

        return ((FastBowMode) this.modeSetting.getValue()).getDisplayName();

    }



    @Subscribe(mc = MotionEvent.class)

    public void onMotion(MotionEvent event) {

        if (this.modeSetting.getValue() == FastBowMode.NCP) {

            if (minecraft.player.getCurrentEquippedItem() == null

                    || !(minecraft.player.getCurrentEquippedItem().getItem() instanceof ItemBow)

                    || !minecraft.gameSettings.keyBindUseItem.pressed

                    || MovementUtils.isMoving()) {

                return;

            }



            this.sendPacketWithoutEvent(

                    new UseItemPacket(minecraft.player.inventory.getCurrentItem()));



            if (!this.timer.elapsedAndReset(85L)) {

                return;

            }



            minecraft.player.connection.addToSendQueue(new PlayerMovementPacket(true));

            minecraft.player.connection.addToSendQueue(new PlayerMovementPacket(true));

            this.sendPacketWithoutEvent(

                    new DiggingPacket(

                            DiggingAction.RELEASE_USE_ITEM, BlockPos.ORIGIN, Facing.DOWN));

            this.timer.reset();

            return;

        }



        if (minecraft.player.onGround

                && minecraft.player.getCurrentEquippedItem() != null

                && minecraft.player.getCurrentEquippedItem().getItem() instanceof ItemBow

                && minecraft.gameSettings.keyBindUseItem.pressed) {

            this.sendPacketWithoutEvent(

                    new UseItemPacket(minecraft.player.getCurrentEquippedItem()));

            int packetCount = (int) this.packetAmountSetting.asDouble();



            for (int i = 0; i < packetCount; i++) {

                this.sendPacketWithoutEvent(

                        new PlayerPositionPacket(

                                minecraft.player.posX,

                                minecraft.player.posY,

                                minecraft.player.posZ,

                                minecraft.player.onGround));

                if (this.modeSetting.getValue() == FastBowMode.FLAG) {

                    this.sendPacketWithoutEvent(

                            new PlayerPositionPacket(

                                    minecraft.player.posX,

                                    minecraft.player.posY - 0.005,

                                    minecraft.player.posZ,

                                    minecraft.player.onGround));

                }

            }



            this.sendPacketWithoutEvent(

                    new DiggingPacket(

                            DiggingAction.RELEASE_USE_ITEM, BlockPos.ORIGIN, Facing.DOWN));

        }

    }




}

