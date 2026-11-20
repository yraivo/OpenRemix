package remix.module.world;

import net.minecraft.block.BlockSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.Vec3;

import remix.event.MotionEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.entity.RotationHelper;

import java.util.ArrayList;
import java.util.List;

/** Spleef module: world events and 1 settings. */
public final class SpleefModule extends ModuleBase {

    private final Setting rangeSetting = new Setting("Range", this, 5.0, 1.0, 6.0, 1.0);

    private PlayerEntity targetPlayer;

    private BlockPos targetSnowBlock;

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        switch (event.getPhase()) {
            case PRE:
                List<PlayerEntity> nearbyPlayers =
                        this.collectNearbyPlayers(this.rangeSetting.asDouble());

                if (!nearbyPlayers.isEmpty()) {

                    this.targetPlayer = nearbyPlayers.get(0);

                    List<BlockPos> snowBlocks =
                            this.sortBlocksByDistance(
                                    this.targetPlayer,
                                    this.collectSnowBlocks(this.targetPlayer, 4));

                    if (!snowBlocks.isEmpty()) {

                        this.targetSnowBlock = snowBlocks.get(0);

                        float[] rotations =
                                RotationHelper.rotationsToPoint(new Vec3(this.targetSnowBlock));

                        event.setYaw(rotations[0]);

                        event.setPitch(rotations[1]);
                    }

                    break;
                }

                this.targetPlayer = null;

                this.targetSnowBlock = null;

                break;

            case POST:
                if (this.targetPlayer != null && this.targetSnowBlock != null) {

                    minecraft.player.swingItem();

                    minecraft.playerController.onPlayerDamageBlock(
                            this.targetSnowBlock,
                            Facing.getHorizontal(
                                    (int)
                                            RotationHelper.rotationsToPoint(
                                                    new Vec3(this.targetSnowBlock))[0]));
                }

                break;
        }
    }

    private List<PlayerEntity> sortPlayersByDistance(List<PlayerEntity> players) {

        players.sort((playerEntity, playerEntityValue) -> {
            float distanceToEntity = playerEntity.getDistanceToEntity(minecraft.player);

            float distanceToEntityValue = playerEntityValue.getDistanceToEntity(minecraft.player);

            return Float.compare(distanceToEntity, distanceToEntityValue);
        });

        return players;
    }

    public SpleefModule() {

        super("Spleef", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
    }

    private List<PlayerEntity> collectNearbyPlayers(double range) {

        ArrayList<PlayerEntity> arrayList = new ArrayList<>();

        for (Entity entity : minecraft.world.loadedEntities) {

            if (entity instanceof PlayerEntity && entity != minecraft.player) {

                PlayerEntity playerEntity = (PlayerEntity) entity;

                if (playerEntity.getDistanceToEntity(minecraft.player) <= range) {

                    arrayList.add(playerEntity);
                }
            }
        }

        return this.sortPlayersByDistance(arrayList);
    }

    private List<BlockPos> sortBlocksByDistance(PlayerEntity target, List<BlockPos> blocks) {

        blocks.sort((b1, b2) -> {
            double d0 = target.getDistanceSq(b1);

            double d1 = target.getDistanceSq(b2);

            return Double.compare(d0, d1);
        });

        return blocks;
    }

    private List<BlockPos> collectSnowBlocks(PlayerEntity target, int radius) {

        ArrayList<BlockPos> arrayList = new ArrayList<>();

        for (int x = -radius; x < radius; x++) {

            for (int y = radius; y > -radius; y--) {

                for (int z = -radius; z < radius; z++) {

                    double d0 = target.posX + x;

                    double d1 = target.posY + y;

                    double d2 = target.posZ + z;

                    BlockPos blockPos = new BlockPos(d0, d1, d2);

                    if (target.getDistanceSq(blockPos) <= radius
                            && minecraft.world.getBlockState(blockPos).getBlock()
                                    instanceof BlockSnowBlock) {

                        arrayList.add(blockPos);
                    }
                }
            }
        }

        return arrayList;
    }
}
