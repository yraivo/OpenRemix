package remix.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** Applies swim/ladder movement and ice speed changes. setIceSlipperiness updates both normal and packed ice and restores their value when disabled. */

public final class TerrainModule extends ModuleBase {

    private Setting autoSwimSetting;



    private Setting speedOnIceSetting;

    public Setting slimeFlySetting;

    private Setting instantLadderSetting;

    private Setting iceSpeedSetting;

    private final Setting fastLadderSetting = new Setting("FastLadder", this, true);



    public Setting antiCactusSetting;



    private Setting ladderSpeedSetting;



    public void onDisable() {

        super.onDisable();

        this.setIceSlipperiness(0.98F);

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (minecraft.world != null) {

            if (this.autoSwimSetting.asBoolean()

                    && (minecraft.player.handleWaterMovement() || minecraft.player.isInLava())

                    && !minecraft.gameSettings.keyBindJump.isKeyDown()

                    && !minecraft.gameSettings.keyBindSneak.isKeyDown()) {

                minecraft.player.motionY += 0.04;

            }



            if (this.fastLadderSetting.asBoolean()) {

                Block block =

                        minecraft

                                .world

                                .getBlockState(

                                        new BlockPos(

                                                minecraft.player.posX,

                                                minecraft.player.posY + 1.0,

                                                minecraft.player.posZ))

                                .getBlock();

                Block blockValue =

                        minecraft

                                .world

                                .getBlockState(

                                        new BlockPos(

                                                minecraft.player.posX,

                                                minecraft.player.posY + 0.5,

                                                minecraft.player.posZ))

                                .getBlock();

                if ((block instanceof BlockVine

                                || blockValue instanceof BlockVine

                                || blockValue instanceof BlockLadder

                                || block instanceof BlockLadder)

                        && minecraft.player.collidedHorizontally) {

                    minecraft.player.motionY = this.ladderSpeedSetting.asDouble();

                }

            }



            if (minecraft.player.isOnLadder()

                    && MovementUtils.isMoving()

                    && minecraft.player.collidedHorizontally

                    && minecraft.player.onGround

                    && this.instantLadderSetting.asBoolean()) {

                double d1 = -1.0;

                double d0 = 0.0;



                while (d0 < 10.0) {

                    Block blockValue2 =

                            minecraft

                                    .world

                                    .getBlockState(

                                            new BlockPos(

                                                    minecraft.player.posX,

                                                    minecraft.player.posY + d0,

                                                    minecraft.player.posZ))

                                    .getBlock();

                    if (blockValue2 instanceof BlockLadder || blockValue2 instanceof BlockVine) {

                        Block blockValue3 =

                                minecraft

                                        .world

                                        .getBlockState(

                                                new BlockPos(

                                                        minecraft.player.posX,

                                                        minecraft.player.posY + d0 + 2.0,

                                                        minecraft.player.posZ))

                                        .getBlock();

                        Block blockValue4 =

                                minecraft

                                        .world

                                        .getBlockState(

                                                new BlockPos(

                                                        minecraft.player.posX,

                                                        minecraft.player.posY + d0 + 1.0,

                                                        minecraft.player.posZ))

                                        .getBlock();

                        if (!blockValue3.isFullBlock() && !blockValue4.isFullCube()) {

                            d1 = d0;

                        }

                    }



                    d0 += 0.5;

                }



                if (d1 > 1.0) {

                    minecraft.player.onGround = false;

                    minecraft.player.setBoundingBox(

                            minecraft.player.getEntityBoundingBox().offset(0.0, d1, 0.0));

                }

            }

        }

    }



    public TerrainModule() {

        super("Terrain", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));

        this.instantLadderSetting = new Setting("InstantLadder", this, false);

        this.ladderSpeedSetting = new Setting("LadderSpeed", this, 0.27, 0.2, 6.0, 0.01);

        this.iceSpeedSetting = new Setting("IceSpeed", this, true);

        this.speedOnIceSetting = new Setting("SpeedOnIce", this, 2.0, 1.0, 8.0, 0.5);

        this.slimeFlySetting = new Setting("SlimeFly", this, true);

        this.antiCactusSetting = new Setting("AntiCactus", this, false);

        this.autoSwimSetting = new Setting("AutoSwim", this, true);

    }



    private void setIceSlipperiness(float slipperiness) {

        Blocks.ice.slipperiness = slipperiness;

        Blocks.packed_ice.slipperiness = slipperiness;

    }






    @Subscribe(mc = MoveEvent.class)

    public void onMove(MoveEvent event) {

        Block block =

                minecraft

                        .world

                        .getBlockState(

                                new BlockPos(

                                        minecraft.player.posX,

                                        minecraft.player.posY - 0.1,

                                        minecraft.player.posZ))

                        .getBlock();



        if (this.iceSpeedSetting.asBoolean()

                && (block instanceof BlockIce || block instanceof BlockPackedIce)) {

            event.setX(event.getX() * this.speedOnIceSetting.asDouble());

            event.setZ(event.getZ() * this.speedOnIceSetting.asDouble());

            this.setIceSlipperiness(0.6F);



            return;

        }



        this.setIceSlipperiness(0.98F);

    }

}

