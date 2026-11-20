package remix.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.MoveEvent;
import remix.event.annotation.Subscribe;
import remix.event.impl.ItemSlowdownEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.combat.AuraModule;
import remix.setting.Setting;

/**
 * Controls item-use slowdown and special-block movement. Some modes release/reapply item use around
 * motion events; groundOffsetPhase alternates the grounded vertical offset.
 */
public final class NoSlowDownModule extends ModuleBase {

    private static DiggingPacket releaseUseItemPacket;

    public Setting soulsandSetting;

    private int groundOffsetPhase;

    private Setting wetSetting;

    private Setting slimeSetting;

    private Setting slowdownSetting;

    private final Setting<NoSlowDownMode> modeSetting =
            new Setting("Mode", this, NoSlowDownMode.VANILLA);

    private Setting noWebSetting;





    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        AuraModule aura = ClientServices.moduleManager.aura;

        switch (((NoSlowDownMode) this.modeSetting.getValue())) {
            case HYPIXEL:

            case NCP:
                if (!minecraft.player.isBlocking() || aura.blocking) {

                    break;
                }

                if (event.getPhase() == MotionPhase.PRE) {

                    this.sendPacketWithoutEvent(releaseUseItemPacket);

                    break;
                }

                if (this.modeSetting.getValue() == NoSlowDownMode.HYPIXEL) {

                    this.sendPacketWithoutEvent(
                            new UseItemPacket(BlockPos.ORIGIN, 255, null, 0.0F, 0.0F, 0.0F));

                    break;
                }

                this.sendPacketWithoutEvent(
                        new UseItemPacket(
                                new BlockPos(-1, -1, -1),
                                255,
                                minecraft.player.getCurrentEquippedItem(),
                                0.0F,
                                0.0F,
                                0.0F));

                break;

            case LEMON:
                if (event.getPhase() == MotionPhase.PRE) {

                    if (minecraft.player.isBlocking() && minecraft.player.onGround) {

                        switch (this.groundOffsetPhase) {
                            case 0:
                                event.setY(event.getY() + 0.24);

                                event.onGround = false;

                                this.groundOffsetPhase = 1;

                                return;

                            case 1:
                                event.setY(event.getY() + 0.0626);

                                event.onGround = false;

                                this.groundOffsetPhase = 0;

                                return;

                            default:
                                this.groundOffsetPhase = 0;

                                return;
                        }
                    }

                    this.groundOffsetPhase = 0;
                }
        }
    }

    @Subscribe(mc = ItemSlowdownEvent.class)
    public void onItemSlowdown(ItemSlowdownEvent event) {

        event.setMultiplier(this.slowdownSetting.asDouble());
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            if (minecraft.player.isInsideOfMaterial(Material.web)
                    && this.noWebSetting.asBoolean()) {

                event.setX(event.getX() * 2.0);

                event.setZ(event.getZ() * 2.0);

                return;
            }

            if (this.isStandingOnBlockType(
                            minecraft.player.getEntityBoundingBox(), Blocks.slime_block)
                    && this.slimeSetting.asBoolean()) {

                if (!(Math.abs(minecraft.player.motionY) < 0.1) || minecraft.player.isSneaking()) {

                    return;
                }

                event.setX(event.getX() * 3.0);

                event.setZ(event.getZ() * 3.0);

                return;
            }

            if (minecraft.player.isWet()
                    && this.wetSetting.asBoolean()
                    && !minecraft.player.isInsideOfMaterial(Material.AIR)
                    && minecraft.player.ticksExisted % 2 == 0) {

                event.setX(event.getX() * 2.5);

                event.setZ(event.getZ() * 2.5);
            }
        }
    }


    // region Static initialization and string tables

    static {
        releaseUseItemPacket =
                new DiggingPacket(DiggingAction.RELEASE_USE_ITEM, BlockPos.ORIGIN, Facing.DOWN);
    }

    // endregion

    public NoSlowDownModule() {

        super("NoSlowDown", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));

        this.slowdownSetting = new Setting("Slowdown", this, 1.0, 0.1, 1.0, 0.1);

        this.soulsandSetting = new Setting("Soulsand", this, true);

        this.noWebSetting = new Setting("NoWeb", this, true);

        this.slimeSetting = new Setting("Slime", this, true);

        this.wetSetting = new Setting("Wet", this, true);

        this.setName("No Slow Down");
    }

    public String getSuffix() {

        return ((NoSlowDownMode) this.modeSetting.getValue()).getDisplayName();
    }

    private boolean isStandingOnBlockType(BoundingBox bounds, Block blockType) {

        boolean foundMatch = false;

        int endX = MathHelper.floor(bounds.maxX + 1.0);

        int endZ = MathHelper.floor(bounds.maxZ + 1.0);

        int blockY = (int) bounds.addCoord(0.0, -0.01, 0.0).minY;

        int startX = MathHelper.floor(bounds.minX);

        int startZ = MathHelper.floor(bounds.minZ);

        for (int blockX = startX; blockX < endX; blockX++) {

            for (int blockZ = startZ; blockZ < endZ; blockZ++) {

                Block blockBelow =
                        minecraft
                                .world
                                .getBlockState(new BlockPos(blockX, blockY, blockZ))
                                .getBlock();

                if (blockBelow != Blocks.air) {

                    if (Block.getIdFromBlock(blockBelow) != Block.getIdFromBlock(blockType)) {

                        return false;
                    }

                    foundMatch = true;
                }
            }
        }

        return foundMatch;
    }
}
