package remix.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;



/** AntiTrap module: world events and 0 settings. */

public final class AntiTrapModule extends ModuleBase {






    public AntiTrapModule() {

        super(

                "AntiTrap",

                new ModuleDefaults(ModuleCategory.WORLD).visible(true));

        this.setName("Anti Trap");

    }



    @Subscribe(mc = MotionEvent.class)

    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            LocalPlayer player = minecraft.player;

            BlockPos blockPos = new BlockPos(player);

            BlockPos blockPosValue = blockPos.add(0.0, player.getEyeHeight(), 0.0);

            Block block = minecraft.world.getBlockState(blockPosValue).getBlock();

            if (block instanceof BlockObsidian || minecraft.player.isEntityInsideOpaqueBlock()) {

                Block blockValue = minecraft.world.getBlockState(blockPos).getBlock();

                boolean isAirOrLiquid = (blockValue instanceof BlockAir) || (blockValue instanceof BlockLiquid);



                while (isAirOrLiquid

                        && Math.abs(

                                        minecraft.player.posY

                                                + minecraft.player.getEyeHeight()

                                                - blockPos.getY()

                                                - 1.0)

                                <= 2.0) {

                    blockPos = blockPos.add(0, -1, 0);

                    blockValue = minecraft.world.getBlockState(blockPos).getBlock();

                    isAirOrLiquid = (blockValue instanceof BlockAir) || (blockValue instanceof BlockLiquid);

                }



                if (minecraft.playerController.onPlayerDamageBlock(blockPos, Facing.UP)) {

                    player.swingItem();

                }



                event.setPitch(90.0F);

            }

        }

    }

}

