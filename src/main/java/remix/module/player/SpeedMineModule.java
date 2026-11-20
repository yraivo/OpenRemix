package remix.module.player;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** SpeedMine module: player events and 1 settings. */

public final class SpeedMineModule extends ModuleBase {



    private boolean digging;

    private BlockPos digPosition;

    private Setting minespeedSetting = new Setting("Minespeed", this, 1.0, 1.0, 3.0, 0.2);



    private float breakProgress;

    private Facing digFacing;






    @Subscribe(mc = PacketEvent.class)

    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.SEND

                && minecraft.playerController != null

                && !minecraft.playerController.isInCreativeMode()

                && event.getPacket() instanceof DiggingPacket) {

            DiggingPacket packet = (DiggingPacket) event.getPacket();

            if (packet.getStatus() == DiggingAction.START_DESTROY_BLOCK) {

                this.digging = true;

                this.digPosition = packet.getPosition();

                this.digFacing = packet.getFacing();

                this.breakProgress = 0.0F;



                return;

            }



            if (packet.getStatus() == DiggingAction.STOP_DESTROY_BLOCK

                    || packet.getStatus() == DiggingAction.ABORT_DESTROY_BLOCK) {

                this.digging = false;

                this.digPosition = null;

                this.digFacing = null;

            }

        }

    }



    public SpeedMineModule() {

        super("SpeedMine", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.setName("Speed Mine");

        this.digging = false;

        this.breakProgress = 0.0F;

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (minecraft.playerController.isInCreativeMode()) {

            minecraft.playerController.blockHitDelay = 0;

        } else {

            if (this.digging) {

                Block block = minecraft.world.getBlockState(this.digPosition).getBlock();

                this.breakProgress =

                        (float)

                                (this.breakProgress

                                        + block.getPlayerRelativeBlockHardness(

                                                        minecraft.player,

                                                        minecraft.world,

                                                        this.digPosition)

                                                * this.minespeedSetting.asDouble());

                if (this.breakProgress >= 1.0F) {

                    minecraft.world.setBlockState(

                            this.digPosition, Blocks.air.getDefaultState(), 11);

                    this.sendPacketWithoutEvent(

                            new DiggingPacket(

                                    DiggingAction.STOP_DESTROY_BLOCK,

                                    this.digPosition,

                                    this.digFacing));

                    this.breakProgress = 0.0F;

                    this.digging = false;

                }

            }

        }

    }

}

