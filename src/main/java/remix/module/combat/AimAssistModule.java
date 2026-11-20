package remix.module.combat;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import remix.event.impl.PlayerUpdateEvent;
import remix.util.entity.RaycastHelper;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;



/** Adjusts yaw toward the living entity under the crosshair after applying target filters. The original comparison with Float.NaN is preserved. */

public final class AimAssistModule extends ModuleBase {

    private Setting playersSetting;

    private Setting mobsSetting;



    private Setting neutralSetting;



    private Setting teamsSetting;

    private Setting invisiblesSetting;

    private final Setting stepsSetting = new Setting("Steps", this, 3.0, 1.0, 10.0, 1.0);



    private Setting animalsSetting;



    public AimAssistModule() {

        super("AimAssist", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.playersSetting = new Setting("Players", this, true);

        this.mobsSetting = new Setting("Mobs", this, false);

        this.animalsSetting = new Setting("Animals", this, false);

        this.neutralSetting = new Setting("Neutral", this, false);

        this.teamsSetting = new Setting("Teams", this, false);

        this.invisiblesSetting = new Setting("Invisibles", this, false);

        this.setName("Aim Assist");

    }






    private boolean isEligibleTarget(LivingEntity target) {

        if (target == null) {

            return false;

        }



        AntiBotModule antiBot = ClientServices.moduleManager.antiBot;

        LocalPlayer player = minecraft.player;

        boolean isPlayer = target instanceof PlayerEntity;

        return target != player

                && !target.isDead

                && (target.getHealth() != 0.0F || Float.isNaN(target.getHealth()))

                && (!target.isInvisible() || this.invisiblesSetting.asBoolean())

                && (isPlayer

                                && this.playersSetting.asBoolean()

                                && (!antiBot.isEnabled() || !antiBot.isBot(target))

                        || target instanceof AnimalEntity && this.animalsSetting.asBoolean()

                        || (target instanceof MonsterEntity

                                        || target instanceof EntityGolem

                                        || target instanceof SlimeEntity)

                                && this.mobsSetting.asBoolean()

                        || (target instanceof BatEntity

                                        || target instanceof SquidEntity

                                        || target instanceof EntityVillager)

                                && this.neutralSetting.asBoolean())

                && (!isPlayer || !ClientServices.friendManager.isFriend(target.getName()))

                && (!RaycastHelper.isSameColoredTeam(target) || !this.teamsSetting.asBoolean());

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        RayTraceResult objectMouseOver = minecraft.objectMouseOver;

        LocalPlayer player = minecraft.player;

        if (player != null

                && minecraft.world != null

                && objectMouseOver != null

                && objectMouseOver.typeOfHit == RayHitType.ENTITY) {

            Entity entityHit = objectMouseOver.entityHit;

            if (entityHit instanceof LivingEntity) {

                LivingEntity livingEntity = (LivingEntity) entityHit;

                if (this.isEligibleTarget(livingEntity)) {

                    float yawErrorToTarget = this.getYawErrorToTarget(player, entityHit);

                    if (yawErrorToTarget == Float.NaN) {

                        player.rotationYaw =

                                (float)

                                        (player.rotationYaw

                                                + yawErrorToTarget / this.stepsSetting.asDouble());

                    }

                }

            }

        }

    }



    private float getYawErrorToTarget(Entity sourceEntity, Entity targetEntity) {

        double deltaX = targetEntity.posX - sourceEntity.posX;

        double deltaZ = targetEntity.posZ - sourceEntity.posZ;

        double quadrantAngle = Math.toDegrees(Math.atan(deltaZ / deltaX));

        if (deltaZ < 0.0 && deltaX < 0.0) {

            double d2 = 90.0 + quadrantAngle;

            return MathHelper.wrapAngleTo180(-(sourceEntity.rotationYaw - (float) d2));

        }



        if (deltaZ < 0.0 && deltaX > 0.0) {

            double d4 = -90.0 + quadrantAngle;

            return MathHelper.wrapAngleTo180(-(sourceEntity.rotationYaw - (float) d4));

        }



        double d5 = Math.toDegrees(-Math.atan(deltaX / deltaZ));

        return MathHelper.wrapAngleTo180(-(sourceEntity.rotationYaw - (float) d5));

    }

}

