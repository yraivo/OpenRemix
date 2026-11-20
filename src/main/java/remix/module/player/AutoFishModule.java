package remix.module.player;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import remix.util.inventory.InventoryItemHelper;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** AutoFish module: player events and 3 settings. */

public final class AutoFishModule extends ModuleBase {

    private boolean waitingToCast;



    private Setting waitDelaySetting;

    private final Setting throwDistanceSetting =

            new Setting("ThrowDistance", this, 20.0, 1.0, 20.0, 1.0);



    private Setting hookDelaySetting;






    public AutoFishModule() {

        super("AutoFish", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.waitDelaySetting = new Setting("WaitDelay", this, 2000.0, 1000.0, 10000.0, 1000.0);

        this.hookDelaySetting = new Setting("HookDelay", this, 100.0, 100.0, 1000.0, 100.0);

        this.setName("Auto Fish");

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        int idFromItem = Item.getIdFromItem(Items.fishing_rod);

        int j = InventoryItemHelper.getArmorSlotForIndex(idFromItem);



        LocalPlayer player = minecraft.player;

        if (this.waitingToCast) {

            double throwDistance = this.throwDistanceSetting.asDouble();

            Vec3 positionEyes = player.getPositionEyes(1.0F);

            Vec3 vec3 = player.getLook(1.0F);

            Vec3 vec3Value =

                    positionEyes.addVector(

                            vec3.x * throwDistance, vec3.y * throwDistance, vec3.z * throwDistance);

            RayTraceResult rayTraceResult =

                    minecraft.world.rayTraceBlocks(positionEyes, vec3Value, true, false, false);

            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayHitType.BLOCK) {

                BlockState blockState = minecraft.world.getBlockState(rayTraceResult.getBlockPos());

                if (blockState.getBlock().getMaterial() == Material.water

                        && this.timer.hasElapsed((long) this.waitDelaySetting.asDouble())) {

                    if (j != -1) {

                        player.inventory.currentItem = j;

                        minecraft.playerController.updateController();

                    }



                    minecraft.rightClickMouse();

                    this.waitingToCast = false;

                    this.timer.reset();

                }

            }



            return;

        }



        if (player.fishEntity != null) {

            EntityFishHook bdF = player.fishEntity;

            int k =

                    bdF.motionX == 0.0

                                    && bdF.motionZ == 0.0

                                    && bdF.motionY > -0.03

                                    && bdF.getAngler() == player

                            ? 1

                            : 0;

            if (k != 0 && this.timer.hasElapsed((long) this.hookDelaySetting.asDouble())) {

                minecraft.rightClickMouse();

                this.waitingToCast = true;

                this.timer.reset();

            }



            return;

        }



        this.waitingToCast = true;

    }

}

