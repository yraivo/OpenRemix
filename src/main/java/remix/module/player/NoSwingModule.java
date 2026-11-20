package remix.module.player;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.util.Facing;
import remix.module.player.NoSwingMode;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** NoSwing module: player events and 1 settings. */
public final class NoSwingModule extends ModuleBase {

    private Facing lastDigFacing;
    private boolean suppressSwing;

    private final Setting<NoSwingMode> modeSetting = new Setting("Mode", this, NoSwingMode.NCP);

    public NoSwingModule() {
        super("NoSwing", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.setName("No Swing");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.lastDigFacing = null;
        this.suppressSwing = false;
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SEND) {
            Packet packet = event.getPacket();
            if (packet instanceof UseEntityPacket) {
                UseEntityPacket useEntityPacket = (UseEntityPacket) packet;
                Entity entity = useEntityPacket.getEntityFromWorld(minecraft.world);
                if (entity != null) {
                    UseEntityAction action = useEntityPacket.getAction();
                    if (action == UseEntityAction.INTERACT_AT || action == UseEntityAction.INTERACT) {
                        this.suppressSwing = true;
                    } else if (action == UseEntityAction.ATTACK) {
                        this.sendPacketWithoutEvent(new C0APacketAnimation());
                    }
                }
            } else if (packet instanceof DiggingPacket) {
                DiggingPacket diggingPacket = (DiggingPacket) packet;
                if (diggingPacket.getStatus() == DiggingAction.START_DESTROY_BLOCK) {
                    this.lastDigFacing = diggingPacket.getFacing();
                    this.suppressSwing = true;
                }
            } else if (packet instanceof UseItemPacket) {
                ItemStack heldItem = minecraft.player.getHeldItem();
                this.suppressSwing = heldItem != null && !(heldItem.getItem() instanceof ItemBlock);
            } else if (packet instanceof C0APacketAnimation) {
                NoSwingMode mode = (NoSwingMode) this.modeSetting.getValue();
                if (mode == NoSwingMode.VANILLA) {
                    this.suppressSwing = true;
                    event.cancel();
                } else if (mode == NoSwingMode.NCP) {
                    if (minecraft.playerController.curBlockDamageMP < 0.01F) {
                        this.sendPacketWithoutEvent(packet);
                    } else {
                        this.suppressSwing = true;
                    }
                    event.setCancelled(this.suppressSwing);
                }
            }
        } else if (event.getDirection() == PacketDirection.SENT) {
            if (minecraft.playerController.getIsHittingBlock() && this.lastDigFacing != null) {
                this.sendPacketWithoutEvent(
                        new DiggingPacket(
                                DiggingAction.ABORT_DESTROY_BLOCK,
                                minecraft.playerController.currentBlock,
                                this.lastDigFacing));
            }
        }
    }

    public boolean shouldSuppressSwing() {
        return this.suppressSwing;
    }
}
