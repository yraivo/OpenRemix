package remix.module.movement;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/**
 * Behind module: chooses a nearby non-friend player by facing error, sends an incremental path
 * behind that player, then updates local position and viewing rotations.
 */
public final class BehindModule extends ModuleBase {

    private Setting distanceSetting;
    private final Setting delaySetting = new Setting("Delay", this, 2000.0, 100.0, 5000.0, 100.0);

    public BehindModule() {
        super("Behind", new ModuleDefaults(ModuleCategory.MOVEMENT));
        this.distanceSetting = new Setting("Distance", this, 3.0, 1.0, 5.0, 1.0);
    }

    private float getTargetFacingError(PlayerEntity target) {
        float[] rotations = this.rotationsFromTargetToPlayer(target);
        if (rotations != null) {
            float deltaYaw = target.rotationYaw - rotations[0];
            float deltaPitch = target.rotationPitch - rotations[1];
            return MathHelper.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
        } else {
            return -1.0F;
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (!this.timer.hasElapsed((long) this.delaySetting.asDouble())) {
            return;
        }

        PlayerEntity bestFacingPlayer = this.findBestFacingPlayer();
        if (bestFacingPlayer == null) {
            return;
        }

        float targetFacingError = this.getTargetFacingError(bestFacingPlayer);
        float distance = minecraft.player.getDistanceToEntity(bestFacingPlayer) + 0.001F;
        if (distance > this.distanceSetting.asDouble() || targetFacingError > 60.0F) {
            return;
        }

        float[] rotations = this.rotationsFromTargetToPlayer(bestFacingPlayer);
        float pitch = 0.0F;
        float yaw = rotations[0];
        float cosPitch = MathHelper.cos(-pitch * (float) (Math.PI / 180.0) - (float) Math.PI);
        float sinPitch = MathHelper.sin(-pitch * (float) (Math.PI / 180.0) - (float) Math.PI);
        float cosYaw = -MathHelper.cos(-yaw * (float) (Math.PI / 180.0));
        float sinYaw = MathHelper.sin(-yaw * (float) (Math.PI / 180.0));
        Vec3 lookVector = new Vec3(sinPitch * cosYaw, sinYaw, cosPitch * cosYaw);

        double targetX = bestFacingPlayer.posX - lookVector.x * 2.0;
        double targetY = minecraft.player.posY;
        double targetZ = bestFacingPlayer.posZ - lookVector.z * 2.0;
        BlockPos targetBlockPos = new BlockPos(targetX, targetY, targetZ);

        double currentX = minecraft.player.posX;
        double currentY = minecraft.player.posY;
        double currentZ = minecraft.player.posZ;
        double destX = targetBlockPos.getX();
        double destY = targetBlockPos.getY();
        double destZ = targetBlockPos.getZ();

        double totalDist = Math.abs(currentX - destX) + Math.abs(currentY - destY) + Math.abs(currentZ - destZ);
        int steps = 0;

        while (totalDist > 0.0) {
            totalDist = Math.abs(currentX - destX) + Math.abs(currentY - destY) + Math.abs(currentZ - destZ);
            if (steps > 90 || minecraft.player.getDistance(currentX, currentY, currentZ) > 4.5) {
                break;
            }

            double diffX = currentX - destX;
            double diffY = currentY - destY;
            double diffZ = currentZ - destZ;
            double moveStep = MovementUtils.getSpeedWithPotion();

            if (diffX < 0.0) {
                currentX += Math.min(Math.abs(diffX), moveStep);
            } else if (diffX > 0.0) {
                currentX -= Math.min(Math.abs(diffX), moveStep);
            }

            if (diffY < 0.0) {
                currentY += Math.min(Math.abs(diffY), 0.25);
            } else if (diffY > 0.0) {
                currentY -= Math.min(Math.abs(diffY), 0.25);
            }

            if (diffZ < 0.0) {
                currentZ += Math.min(Math.abs(diffZ), moveStep);
            } else if (diffZ > 0.0) {
                currentZ -= Math.min(Math.abs(diffZ), moveStep);
            }

            this.sendPacketWithoutEvent(
                    new PlayerPositionPacket(currentX, minecraft.player.posY, currentZ, true));
            steps++;
        }

        this.timer.reset();
        minecraft.player.setPosition(
                targetBlockPos.getX() + 0.5,
                minecraft.player.posY + 0.1,
                targetBlockPos.getZ() + 0.5);
        minecraft.player.motionY -= 0.01;
        float[] aimRotations = this.rotationsToTargetPosition(bestFacingPlayer);
        minecraft.player.rotationYaw = aimRotations[0];
        minecraft.player.rotationPitch = aimRotations[1];
        minecraft.player.playSound("mob.endermen.portal", 1.0F, 1.0F);
    }

    private PlayerEntity findBestFacingPlayer() {
        float bestError = 10000.0F;
        PlayerEntity bestPlayer = null;

        for (PlayerEntity playerEntity : minecraft.world.playerEntities) {
            if (!ClientServices.friendManager.isFriend(playerEntity.getName())
                    && playerEntity != minecraft.player
                    && minecraft.player.getDistanceToEntity(playerEntity) < 6.0F) {
                float targetFacingError = this.getTargetFacingError(playerEntity);
                if (targetFacingError < bestError) {
                    bestError = targetFacingError;
                    bestPlayer = playerEntity;
                }
            }
        }

        return bestPlayer;
    }

    private float[] rotationsToTargetPosition(LivingEntity target) {
        double deltaX = target.posX - minecraft.player.posX;
        double deltaZ = target.posZ - minecraft.player.posZ;
        double deltaY = target.posY - minecraft.player.posY;
        double horizontalDistance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0 / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(deltaY, horizontalDistance) * 180.0 / Math.PI));
        return new float[] {yaw, pitch};
    }

    private float[] rotationsFromTargetToPlayer(PlayerEntity target) {
        if (target == null) {
            return null;
        }

        double deltaPosX = minecraft.player.posX - target.posX;
        double deltaPosZ = minecraft.player.posZ - target.posZ;
        double eyeOffset =
                minecraft.player.posY
                        + minecraft.player.getEyeHeight() / 2.0F
                        - (target.posY + target.getEyeHeight());
        double horizontalDistance = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ);
        float yaw = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(eyeOffset, horizontalDistance) * 180.0 / Math.PI));
        return new float[] {
            target.rotationYaw + MathHelper.wrapAngleTo180(yaw - target.rotationYaw),
            target.rotationPitch + MathHelper.wrapAngleTo180(pitch - target.rotationPitch)
        };
    }
}
