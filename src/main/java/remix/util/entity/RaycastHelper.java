package remix.util.entity;

import remix.util.time.ElapsedTimer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import optifine.Reflector;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.util.List;

public final class RaycastHelper {
    private static final MinecraftClient ec = MinecraftClient.getMinecraft();

    public static RayTraceResult rayTraceBlocks(double d0, float f, float[] afloat) {
        Vec3 positionEyes = ec.player.getPositionEyes(f);
        Vec3 vectorForRotation = ec.player.getVectorForRotation(afloat[1], afloat[0]);
        Vec3 vec3 =
                positionEyes.addVector(
                        vectorForRotation.x * d0,
                        vectorForRotation.y * d0,
                        vectorForRotation.z * d0);
        return ec.world.rayTraceBlocks(positionEyes, vec3, false, false, true);
    }

    public static boolean isSameColoredTeam(LivingEntity livingEntity) {
        ScorePlayerTeam team = (ScorePlayerTeam) ec.player.getTeam();
        ScorePlayerTeam teamValue = (ScorePlayerTeam) livingEntity.getTeam();
        if (team != null && teamValue != null) {
            String text = FontRenderer.getFormatFromString(team.getColorPrefix());
            String textValue = FontRenderer.getFormatFromString(teamValue.getColorPrefix());
            if (textValue.length() >= 2 && text.length() >= 2) {
                int colorCode = ec.fontRendererObj.getColorCode(text.charAt(1));
                int j = ec.fontRendererObj.getColorCode(textValue.charAt(1));
                return colorCode == j;
            } else {
                return team.isSameTeam(teamValue);
            }
        } else {
            return false;
        }
    }

    public static RayTraceResult rayTrace(float[] afloat, double d0, boolean flag, float f) {
        Entity entity = ec.getRenderViewEntity();
        ElapsedTimer.fM();

        RayTraceResult rayTraceResult = null;

        if (entity != null && ec.world != null) {
            double d1 = d0;
            if (!flag) {
                rayTraceResult = rayTraceBlocks(d1, f, afloat);
            }

            Vec3 positionEyes = entity.getPositionEyes(f);
            if (rayTraceResult != null && rayTraceResult.hitVec != null) {
                d1 = rayTraceResult.hitVec.distanceTo(positionEyes);
            }

            Vec3 vectorForRotation = entity.getVectorForRotation(afloat[1], afloat[0]);
            Vec3 vec3 =
                    positionEyes.addVector(
                            vectorForRotation.x * d1,
                            vectorForRotation.y * d1,
                            vectorForRotation.z * d1);
            Entity closestEntity = null;
            Vec3 closestHit = null;
            List nearbyEntities =
                    ec.world.getEntitiesInAABBexcluding(
                            entity,
                            entity.getEntityBoundingBox()
                                    .addCoord(
                                            vectorForRotation.x * d1,
                                            vectorForRotation.y * d1,
                                            vectorForRotation.z * d1)
                                    .expand(1.0, 1.0, 1.0),
                            Predicates.and(new Predicate[] {IEntitySelector.NOT_SPECTATING}));
            double closestDistance = d1;

            for (Object entry : nearbyEntities) {
                Entity candidate = (Entity) entry;
                float border = candidate.getCollisionBorderSize();
                BoundingBox bounds = candidate.getEntityBoundingBox().expand(border, border, border);
                RayTraceResult intercept = bounds.calculateIntercept(positionEyes, vec3);
                if (bounds.isVecInside(positionEyes)) {
                    if (closestDistance >= 0.0) {
                        closestEntity = candidate;
                        closestHit = intercept == null ? positionEyes : intercept.hitVec;
                        closestDistance = 0.0;
                    }
                    continue;
                }
                if (intercept == null) {
                    continue;
                }

                double distance = positionEyes.distanceTo(intercept.hitVec);
                if (distance < closestDistance || closestDistance == 0.0) {
                    boolean canInteractWithMount = Reflector.forgeCanRiderInteract.exists()
                            && Reflector.callBoolean(candidate, Reflector.forgeCanRiderInteract, new Object[0]);
                    if (candidate == entity.ridingEntity && !canInteractWithMount) {
                        if (closestDistance == 0.0) {
                            closestEntity = candidate;
                            closestHit = intercept.hitVec;
                        }
                    } else {
                        closestEntity = candidate;
                        closestHit = intercept.hitVec;
                        closestDistance = distance;
                    }
                }
            }

            if (closestEntity != null && (closestDistance < d1 || rayTraceResult == null)) {
                rayTraceResult = new RayTraceResult(closestEntity, closestHit);
            }
        }

        return rayTraceResult;
    }

    public static boolean isEntityInRay(float[] afloat, double d0, Entity entity, float f) {
        Entity entityValue = ec.getRenderViewEntity();
        if (entityValue != null && ec.world != null) {
            Vec3 positionEyes = entityValue.getPositionEyes(f);
            Vec3 vectorForRotation = entityValue.getVectorForRotation(afloat[1], afloat[0]);
            Vec3 vec3 =
                    positionEyes.addVector(
                            vectorForRotation.x * d0,
                            vectorForRotation.y * d0,
                            vectorForRotation.z * d0);
            float collisionBorderSize = entity.getCollisionBorderSize();
            BoundingBox boundingBox =
                    entity.getEntityBoundingBox()
                            .expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
            RayTraceResult rayTraceResult =
                    boundingBox.calculateIntercept(positionEyes, vec3);
            return rayTraceResult != null
                    && rayTraceResult.hitVec.distanceTo(ec.player.getPositionEyes(f)) <= d0;
        } else {
            return false;
        }
    }
}
