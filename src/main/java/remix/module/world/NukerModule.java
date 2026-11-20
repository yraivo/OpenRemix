package remix.module.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Nuker module: world events and 3 settings. */
public final class NukerModule extends ModuleBase {

    private Setting reverseSetting;
    private Block scannedBlock;
    private final Setting radiusSetting = new Setting("Radius", this, 3.0, 2.0, 5.0, 1.0);

    private BlockPos scannedPosition;

    private Setting instantSetting;

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            int radius = (int) this.radiusSetting.asDouble();
            int actionCount = 0;

            for (int dx = radius; dx >= -radius; dx--) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = radius; dz >= -radius; dz--) {
                        int x = (int) (minecraft.player.posX + dx);
                        int y = (int) (minecraft.player.posY + dy);
                        int z = (int) (minecraft.player.posZ + dz);
                        this.scannedPosition = new BlockPos(x, y, z);
                        this.scannedBlock =
                                minecraft.world.getBlockState(this.scannedPosition).getBlock();
                        if (this.scannedBlock != Blocks.air) {
                            if (this.reverseSetting.asBoolean()) {
                                RayTraceResult rayTraceResult =
                                        minecraft.world.rayTraceBlocks(
                                                minecraft
                                                        .player
                                                        .getPositionVector()
                                                        .addVector(0.0, -2.0, 0.0),
                                                new Vec3(this.scannedPosition)
                                                        .addVector(0.0, 0.5, 0.0));
                                if (rayTraceResult != null
                                        && rayTraceResult.typeOfHit == RayHitType.BLOCK) {
                                    minecraft.playerController.onPlayerRightClick(
                                            minecraft.player,
                                            minecraft.world,
                                            minecraft.player.getCurrentEquippedItem(),
                                            rayTraceResult.getBlockPos(),
                                            rayTraceResult.sideHit,
                                            rayTraceResult.hitVec);
                                    this.sendPacket(
                                            new UseItemPacket(
                                                    rayTraceResult.getBlockPos(),
                                                    rayTraceResult.sideHit.getIndex(),
                                                    minecraft.player.inventory.getCurrentItem(),
                                                    this.scannedPosition.getX(),
                                                    this.scannedPosition.getY(),
                                                    this.scannedPosition.getZ()));
                                }
                            } else if (this.instantSetting.asBoolean()) {
                                this.sendPacket(
                                        new DiggingPacket(
                                                DiggingAction.START_DESTROY_BLOCK,
                                                this.scannedPosition,
                                                Facing.UP));
                                this.sendPacket(
                                        new DiggingPacket(
                                                DiggingAction.STOP_DESTROY_BLOCK,
                                                this.scannedPosition,
                                                Facing.UP));
                            } else {
                                minecraft.playerController.onPlayerDamageBlock(
                                        this.scannedPosition, Facing.DOWN);
                            }

                            if (++actionCount > 7) {
                                actionCount = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    public NukerModule() {
        super("Nuker", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
        this.reverseSetting = new Setting("Reverse", this, false);
        this.instantSetting = new Setting("Instant", this, true);
    }
}
