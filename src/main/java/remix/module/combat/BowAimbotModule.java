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
import net.minecraft.item.ItemBow;
import net.minecraft.util.MathHelper;
import remix.event.MotionPhase;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.command.TargetsClientCommand;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;



import java.util.ArrayList;



/** Filters and orders bow targets, rotates through targets without visibility, then calculates projectile rotations from charge, smoothing and prediction settings. */

public final class BowAimbotModule extends ModuleBase {



    private Setting smoothnessSetting;

    private int targetIndex;

    private Setting animalsSetting;

    private Setting teamsSetting;

    private Setting neutralSetting;

    private Setting mobsSetting;

    private float[] lastRotations;



    private Setting invisiblesSetting;



    private Setting playersSetting;

    private final Setting predictSetting = new Setting("Predict", this, true);

    private final Setting lockviewSetting = new Setting("Lockview", this, true);



    public BowAimbotModule() {

        super("BowAimbot", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.smoothnessSetting = new Setting("Smoothness", this, 0.0, 0.0, 100.0, 1.0);

        this.playersSetting = new Setting("Players", this, true);

        this.mobsSetting = new Setting("Mobs", this, false);

        this.animalsSetting = new Setting("Animals", this, false);

        this.neutralSetting = new Setting("Neutral", this, false);

        this.teamsSetting = new Setting("Teams", this, false);

        this.invisiblesSetting = new Setting("Invisibles", this, false);

        this.lastRotations = new float[2];

        this.targetIndex = 0;

        this.setName("Bow Aimbot");

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



    @Subscribe(mc = MotionEvent.class)

    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            ArrayList<LivingEntity> targets = new ArrayList<>();



            for (Entity entity : minecraft.world.loadedEntities) {

                if (entity instanceof LivingEntity) {

                    LivingEntity livingEntity = (LivingEntity) entity;

                    if (this.isEligibleTarget(livingEntity)) {

                        targets.add(livingEntity);

                    }

                }

            }



            if (!targets.isEmpty()) {

                ArrayList<LivingEntity> priorityTargets = new ArrayList<>();

                for (LivingEntity livingEntityValue : targets) {

                    if (TargetsClientCommand.isTarget(livingEntityValue)) {

                        priorityTargets.add(livingEntityValue);

                    }

                }



                LivingEntity bestTarget;

                if (priorityTargets.size() == 1) {

                    targets = priorityTargets;

                    bestTarget = targets.get(0);

                } else {

                    if (!priorityTargets.isEmpty()) {

                        targets = priorityTargets;

                    }



                    targets.sort((e1, e2) -> {

                        double d0x = minecraft.player.getDistanceSqToEntity(e1);

                        double d1x = minecraft.player.getDistanceSqToEntity(e2);

                        return Double.compare(d0x, d1x);

                    });

                    if (this.targetIndex >= targets.size()) {

                        this.targetIndex = 0;

                    }



                    bestTarget = targets.get(this.targetIndex);

                }



                if ((minecraft.player.isUsingItem() || minecraft.gameSettings.keyBindUseItem.pressed)

                        && minecraft.player.inventory.getCurrentItem() != null

                        && minecraft.player.inventory.getCurrentItem().getItem()

                                instanceof ItemBow) {

                    if (minecraft.player.canEntityBeSeen(bestTarget)) {

                        FastBowModule fastBow = ClientServices.moduleManager.fastBow;

                        int chargeTicks = fastBow.isEnabled() ? 20 : minecraft.player.getItemInUseDuration();

                        float f = chargeTicks / 20.0F;

                        f = (f * f + f * 2.0F) / 3.0F;

                        f = MathHelper.clamp(f, 0.0F, 1.0F);

                        double d0 = f * 3.0F;



                        float[] afloat =

                                this.lockviewSetting.asBoolean()

                                        ? new float[] {

                                            minecraft.player.rotationYaw,

                                            minecraft.player.rotationPitch

                                        }

                                        : this.lastRotations;

                        float[] afloat1 =

                                RotationHelper.projectileRotations(

                                        bestTarget,

                                        this.smoothnessSetting.asDouble(),

                                        this.predictSetting.asBoolean(),

                                        afloat,

                                        d0,

                                        0.05F);

                        if (Double.isNaN(afloat1[1])) {

                            return;

                        }



                        event.setYaw(afloat1[0]);

                        event.setPitch(afloat1[1]);

                        if (this.lockviewSetting.asBoolean()) {

                            minecraft.player.rotationYaw = afloat1[0];

                            minecraft.player.rotationPitch = afloat1[1];

                        }

                    } else {

                        this.targetIndex++;

                        if (this.targetIndex >= targets.size()) {

                            this.targetIndex = 0;

                        }

                    }

                }

            }



            this.lastRotations = new float[] {event.getYaw(), event.getPitch()};

        }

    }

}

