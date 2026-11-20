package remix.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;

import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.impl.BlockCollisionEvent;
import remix.event.impl.JumpEvent;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.movement.MovementUtils;
import remix.util.world.LiquidStepHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Replaces liquid collision bounds, alternates a small surface position offset, or boosts
 * water-exit motion depending on mode. alternateSurfaceOffset intentionally persists across event
 * handlers.
 */
public final class JesusModule extends ModuleBase {

    private Setting<JesusMode> modeSetting = new Setting("Mode", this, JesusMode.NCP);

    private boolean alternateSurfaceOffset;

    private boolean waterExitBoostActive;

    public JesusModule() {

        super("Jesus", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
    }


    public boolean isOnLiquidSurface() {

        Block block =
                minecraft
                        .world
                        .getBlockState(
                                new BlockPos(
                                        minecraft.player.posX,
                                        minecraft.player.getEntityBoundingBox().minY,
                                        minecraft.player.posZ))
                        .getBlock();

        return block instanceof BlockLiquid && !this.waterExitBoostActive;
    }





    @Subscribe(mc = BlockCollisionEvent.class)
    public void onBlockCollision(BlockCollisionEvent event) {

        if (minecraft.player != null
                && minecraft.player.fallDistance <= 3.0F
                && !minecraft.player.isSneaking()
                && !minecraft.player.isInWater()
                && !minecraft.player.isInLava()
                && LiquidStepHelper.aY()
                && minecraft.world.getBlockState(event.getPosition()).getBlock()
                        instanceof BlockLiquid
                && event.getPosition().getY() <= minecraft.player.boundingBox.minY) {

            switch (((JesusMode) this.modeSetting.getValue())) {
                case NCP:

                case NORMAL:
                    event.setBounds(
                            new BoundingBox(
                                    event.getPosition().getX(),
                                    event.getPosition().getY(),
                                    event.getPosition().getZ(),
                                    event.getPosition().getX() + 1.0,
                                    event.getPosition().getY() + 1.0,
                                    event.getPosition().getZ() + 1.0));
            }
        }
    }

    @Subscribe(mc = JumpEvent.class)
    public void onJump(JumpEvent event) {

        event.setCancelled(this.isOnLiquidSurface());
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (this.modeSetting.getValue() == JesusMode.NORMAL
                && event.getPacket() instanceof PlayerMovementPacket
                && minecraft
                                .world
                                .getBlockState(
                                        new BlockPos(
                                                minecraft.player.posX + minecraft.player.motionX,
                                                minecraft.player.posY - 1.0E-5,
                                                minecraft.player.posZ + minecraft.player.motionZ))
                                .getBlock()
                        instanceof BlockLiquid) {

            event.setPacket(
                    new PlayerPositionPacket(
                            minecraft.player.posX,
                            minecraft.player.posY - 1.0E-5,
                            minecraft.player.posZ,
                            false));
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.modeSetting.getValue() == JesusMode.NCP) {

            Block block =
                    minecraft
                            .world
                            .getBlockState(
                                    new BlockPos(
                                            minecraft.player.posX,
                                            minecraft.player.getEntityBoundingBox().minY,
                                            minecraft.player.posZ))
                            .getBlock();

            boolean flag = block instanceof BlockLiquid;

            if (flag
                    && this.alternateSurfaceOffset
                    && minecraft.gameSettings.keyBindJump.pressed
                    && !minecraft.player.isInWater()) {

                minecraft.player.motionY = MovementUtils.getJumpMotion();
            }
        }
    }

    private List<Block> collectCollidingBlocks(BoundingBox bounds) {

        List collisionBounds = minecraft.world.getCollidingBoundingBoxes(minecraft.player, bounds);

        ArrayList blocks = new ArrayList();

        if (collisionBounds.isEmpty()) {

            return blocks;
        }

        for (java.util.Iterator<?> __recoveryIterator171_8896 = (collisionBounds).iterator();
                (__recoveryIterator171_8896).hasNext(); ) {

            BoundingBox boundingBox = (BoundingBox) (__recoveryIterator171_8896).next();

            BlockPos blockPos =
                    new BlockPos(
                            (boundingBox.minX + boundingBox.maxX) / 2.0,
                            (boundingBox.minY + boundingBox.maxY) / 2.0,
                            (boundingBox.minZ + boundingBox.maxZ) / 2.0);

            Block block = minecraft.world.getBlockState(blockPos).getBlock();

            blocks.add(block);
        }

        return blocks;
    }

    public String getSuffix() {

        return ((JesusMode) this.modeSetting.getValue()).getDisplayName();
    }





    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            switch (((JesusMode) this.modeSetting.getValue())) {
                case NCP:
                    List collidingBlocks =
                            this.collectCollidingBlocks(
                                    minecraft
                                            .player
                                            .getEntityBoundingBox()
                                            .addCoord(0.0, -0.005, 0.0));

                    int i = !collidingBlocks.isEmpty() ? 1 : 0;

                    for (java.util.Iterator<?> __recoveryIterator171_30862 =
                                    (collidingBlocks).iterator();
                            (__recoveryIterator171_30862).hasNext(); ) {

                        Block block = (Block) (__recoveryIterator171_30862).next();

                        if (!(block instanceof BlockLiquid)) {

                            i = 0;

                            break;
                        }
                    }

                    if (i != 0) {

                        if (this.alternateSurfaceOffset) {

                            event.setY(event.getY() - 0.005);
                        }

                        if (this.alternateSurfaceOffset) {

                            event.onGround = false;
                        }
                    }

                    this.alternateSurfaceOffset = !this.alternateSurfaceOffset;

                    break;

                case DOLPHIN:
                    if (minecraft.player.onGround || minecraft.player.isOnLadder()) {

                        this.waterExitBoostActive = false;
                    }

                    if (this.waterExitBoostActive && minecraft.player.motionY > -0.2) {

                        minecraft.player.motionY *= 1.155;
                    }

                    Block blockValue =
                            minecraft
                                    .world
                                    .getBlockState(
                                            new BlockPos(
                                                    minecraft.player.posX,
                                                    minecraft.player.posY,
                                                    minecraft.player.posZ))
                                    .getBlock();

                    Block blockValue2 =
                            minecraft
                                    .world
                                    .getBlockState(
                                            new BlockPos(
                                                    minecraft.player.posX,
                                                    minecraft.player.posY + 0.5,
                                                    minecraft.player.posZ))
                                    .getBlock();

                    if (blockValue instanceof BlockLiquid && !minecraft.player.isSneaking()) {

                        if (blockValue2 instanceof BlockAir) {

                            minecraft.player.motionY = 0.5;

                            this.waterExitBoostActive = true;

                            return;
                        }

                        minecraft.player.motionY = 0.085;
                    }
            }
        }
    }
}
