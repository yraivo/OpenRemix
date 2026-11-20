package remix.module.world;

import net.minecraft.block.Block;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayHitType;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.Render2DEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;



/** CivBreak module: world events and 4 settings. */

public final class CivBreakModule extends ModuleBase {

    public BlockPos targetBlock;

    private boolean targetInRange;

    private Setting stopDestroyBlockSetting;

    private final Setting rangeSetting = new Setting("Range", this, 4.3, 1.0, 6.0, 0.1);



    private final Setting finishInRangeSetting = new Setting("FinishInRange", this, false);



    private static String[] packedTextFragments;

    private final Setting breakInRangeSetting = new Setting("BreakInRange", this, false);



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        PlayerController playerController = minecraft.playerController;

        if (minecraft.gameSettings.keyBindAttack.pressed

                && minecraft.objectMouseOver.typeOfHit == RayHitType.BLOCK) {

            this.targetBlock = playerController.currentBlock;

            this.targetInRange = true;

        }



        BlockPos targetBlockValue = this.targetBlock;

        if (targetBlockValue != null) {

            LocalPlayer player = minecraft.player;

            ClientWorld world = minecraft.world;

            Block block = world.getBlockState(targetBlockValue).getBlock();

            this.targetInRange =

                    player.getDistance(

                            targetBlockValue.getX(),

                            targetBlockValue.getY(),

                            targetBlockValue.getZ())

                            <= this.rangeSetting.asDouble();

            if (block.getMaterial().isSolid()) {

                if (this.breakInRangeSetting.asBoolean() && !this.targetInRange) {

                    return;

                }



                boolean fullyDamaged =

                        playerController.curBlockDamageMP

                                + block.getPlayerRelativeBlockHardness(

                                        player, world, targetBlockValue)

                                >= 1.0F;

                if (!fullyDamaged || this.targetInRange || !this.finishInRangeSetting.asBoolean()) {

                    playerController.onPlayerDamageBlock(

                            targetBlockValue, player.getHorizontalFacing());

                    if (this.stopDestroyBlockSetting.asBoolean()) {

                        this.sendPacketWithoutEvent(

                                new DiggingPacket(

                                        DiggingAction.STOP_DESTROY_BLOCK,

                                        targetBlockValue,

                                        player.getHorizontalFacing()));

                    }



                    player.swingItem();

                }

            }

        }

    }



    public CivBreakModule() {

        super("CivBreak", ModuleDefaults.ofCategory(ModuleCategory.WORLD));

        this.stopDestroyBlockSetting = new Setting("StopDestroyBlock", this, false);

        this.setName("Civ Break");

    }




    // region Static initialization and string tables

    static {

        remix.module.world.CivBreakModule.packedTextFragments = new String[3];

        remix.module.world.CivBreakModule.packedTextFragments[0] = new String("%");

    }



    // endregion



    @Subscribe(mc = Render2DEvent.class)

    public void onRender2D(Render2DEvent event) {

        BlockPos targetBlockValue = this.targetBlock;

        if (targetBlockValue != null) {

            ClientWorld world = minecraft.world;

            LocalPlayer player = minecraft.player;

            Block block = world.getBlockState(targetBlockValue).getBlock();

            if (block.getMaterial().isSolid()) {

                PlayerController playerController = minecraft.playerController;



                float f2 = event.getWidth() / 2.0F - 100.0F;

                float f3 = event.getHeight() / 2.0F - 5.0F - 30.0F;

                float f4 =

                        Math.min(

                                playerController.curBlockDamageMP

                                        + block.getPlayerRelativeBlockHardness(

                                                player, world, targetBlockValue),

                                1.0F);

                String text = (int) (f4 * 100.0F) + packedTextFragments[0];

                ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto19;

                clientFontRenderer.drawCenteredStringWithShadow(text, f2 + 100.0F, f3, -1);

            }

        }

    }



    public void onDisable() {

        this.targetInRange = false;

        this.targetBlock = null;

        super.onDisable();

    }

}

