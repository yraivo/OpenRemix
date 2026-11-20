package remix.module.combat;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.PlayerLookPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import optifine.MathUtils;
import remix.util.path.AStarPathfinder;
import remix.event.MotionPhase;
import remix.event.impl.PlayerUpdateEvent;
import remix.util.entity.RaycastHelper;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render3DEvent;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;



/** Collects targets, computes outbound paths, sends position/look/attack packets and traverses the path backwards. attackPaths are retained for rendering; original target predicate precedence is preserved. */

public final class TPAuraModule extends ModuleBase {

    private List<Entity> targets;

    private List<List<Vec3>> attackPaths;



    private Setting rangeSetting;

    private final Setting apsSetting = new Setting("APS", this, 13.0, 1.0, 20.0, 1.0);

    private Setting maxTargetsSetting;



    private Setting teamsSetting;






    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        this.targets = this.collectTargets();

        if (!this.targets.isEmpty()) {

            this.targets.sort((entity, entityValue) -> {

                double d0 = MathUtils.getDistanceToPlayer(entity);

                double d1 = MathUtils.getDistanceToPlayer(entityValue);

                return Double.compare(d0, d1);

            });



            if (!this.timer.elapsedAndReset(1000L / (long) this.apsSetting.asDouble())) {

                return;

            }



            int i = 0;

            if (!this.attackPaths.isEmpty()) {

                this.attackPaths.clear();

            }



            for (Entity entityValue2 : this.targets) {

                if (!(i < this.maxTargetsSetting.asDouble())) {

                    break;

                }



                AStarPathfinder pathfinder =

                        new AStarPathfinder(

                                new BlockPos(minecraft.player), new BlockPos(entityValue2));

                pathfinder.findPath();

                List list = pathfinder.getPathWaypoints();

                if (list.isEmpty()) {

                    return;

                }



                this.attackPaths.add(list);



                for (Object item : list) {

                    Vec3 vec3 = (Vec3) item;

                    this.sendPacketWithoutEvent(

                            new PlayerPositionPacket(vec3.x, vec3.y, vec3.z, true));

                }



                Vec3 vec3Value =

                        ((Vec3) list.get(list.size() - 1))

                                .addVector(0.0, minecraft.player.getEyeHeight(), 0.0);

                Vec3 vec3Value2 = new Vec3(vec3Value.x, vec3Value.y, vec3Value.z);

                float[] afloat =

                        RotationHelper.rotationsBetweenUnsmoothed(

                                entityValue2.getPositionEyes(1.0F), vec3Value2);

                this.sendPacketWithoutEvent(new PlayerLookPacket(afloat[0], afloat[1], true));

                minecraft.player.swingItem();

                this.sendPacketWithoutEvent(

                        new UseEntityPacket(entityValue2, UseEntityAction.ATTACK));



                for (int j = list.size() - 1; j >= 0; j--) {

                    Vec3 vec3Value3 = (Vec3) list.get(j);

                    this.sendPacketWithoutEvent(

                            new PlayerPositionPacket(

                                    vec3Value3.x, vec3Value3.y, vec3Value3.z, true));

                }



                i++;

            }

            return;

        }



        if (!this.attackPaths.isEmpty()) {

            this.attackPaths.clear();

        }

    }



    public TPAuraModule() {

        super("TPAura", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.rangeSetting = new Setting("Range", this, 50.0, 6.0, 250.0, 1.0);

        this.maxTargetsSetting = new Setting("MaxTargets", this, 9.0, 1.0, 10.0, 1.0);

        this.teamsSetting = new Setting("Teams", this, true);

        this.targets = new ArrayList<>();

        this.attackPaths = new ArrayList<>();

        this.setName("TP Aura");

    }



    private boolean isEligibleTarget(Entity candidate) {

        if (!(candidate instanceof LivingEntity)) {

            return false;

        }



        LivingEntity livingTarget = (LivingEntity) candidate;

        return livingTarget instanceof PlayerEntity

                        && livingTarget.getDistanceToEntity(minecraft.player)

                                <= this.rangeSetting.asDouble()

                        && livingTarget != minecraft.player

                        && !livingTarget.isInvisible()

                        && !livingTarget.isDead

                        && !ClientServices.friendManager.isFriend(livingTarget.getName())

                        && !RaycastHelper.isSameColoredTeam(livingTarget)

                || !this.teamsSetting.asBoolean();

    }



    private List<Entity> collectTargets() {

        ArrayList<Entity> targets = new ArrayList<>();

        for (Entity candidate : minecraft.world.loadedEntities) {

            if (this.isEligibleTarget(candidate)) {

                targets.add(candidate);

            }

        }

        return targets;

    }



    @Subscribe(mc = Render3DEvent.class)

    public void onRender3D(Render3DEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            for (List list : this.attackPaths) {

                Render2D3DUtils.beginUnoccludedWorldOverlay();

                GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);

                GlStateManager.translate(

                        -minecraft.getRenderManager().getRenderPosX(),

                        -minecraft.getRenderManager().getRenderPosY(),

                        -minecraft.getRenderManager().getRenderPosZ());

                GlStateManager.glLineWidth(1.0F);

                GlStateManager.ab(3);



                for (Object item : list) {

                    Vec3 vec3 = (Vec3) item;

                    GlStateManager.vertex3d(vec3.x, vec3.y, vec3.z);

                }



                GlStateManager.mO();

                Render2D3DUtils.endUnoccludedWorldOverlay();

            }

        }

    }

}

