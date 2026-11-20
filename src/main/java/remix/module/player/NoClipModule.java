package remix.module.player;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import remix.event.impl.BlockCollisionEvent;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.movement.MovementUtils;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PushOutOfBlocksEvent;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import org.lwjgl.input.Keyboard;

/** NoClip module: player events and 3 settings. */
public final class NoClipModule extends ModuleBase {

    private Setting speedSetting = new Setting("Speed", this, 1.0, 1.0, 5.0, 1.0);

    private Setting controlSetting = new Setting("Control", this, true);
    private int clipTicks;
    private Setting infiniteSetting = new Setting("Infinite", this, true);

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.POST) {
            boolean colliding =
                    MovementPhysicsHelper.isCollidingWithBlocks(
                            minecraft.player.getEntityBoundingBox());
            this.clipTicks = !colliding && !minecraft.player.isSneaking() ? 0 : this.clipTicks + 1;
            if (this.infiniteSetting.asBoolean()
                    && minecraft.player != null
                    && this.clipTicks > 4
                    && this.clipTicks < 12) {
                minecraft
                        .getNetworkHandler()
                        .addToSendQueue(
                                new PlayerPositionPacket(
                                        minecraft.player.posX + 2.147483647E9,
                                        minecraft.player.posZ + 2.147483647E9,
                                        minecraft.player.posZ + 2.147483647E9,
                                        true));
            }
        }
    }

    @Subscribe(mc = PushOutOfBlocksEvent.class)
    public void onPushOutOfBlocks(PushOutOfBlocksEvent event) {
        event.setCancelled(true);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED
                && this.infiniteSetting.asBoolean()
                && minecraft.player != null
                && event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            if (this.clipTicks > 0 && this.clipTicks < 15) {
                this.sendPacket(
                        new PlayerPositionLookPacket(
                                packet.getX(),
                                packet.getY(),
                                packet.getZ(),
                                packet.yaw,
                                packet.pitch,
                                true));
                event.setCancelled(true);
            }
        }
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        boolean colliding =
                MovementPhysicsHelper.isCollidingWithBlocks(
                        minecraft.player.getEntityBoundingBox());

        if (event.getPhase() == MotionPhase.PRE
                && this.controlSetting.asBoolean()
                && (colliding || minecraft.player.isSneaking())) {
            double speed = this.speedSetting.asDouble();
            minecraft.player.motionX = minecraft.player.motionY = minecraft.player.motionZ = 0.0;
            minecraft.player.noClip = true;

            if (!(minecraft.currentScreen instanceof GuiChat)) {
                if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())) {
                    event.setY(minecraft.player.motionY + speed / 5.0);
                } else if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())) {
                    event.setY(minecraft.player.motionY - speed / 5.0);
                } else {
                    event.setY(0.0);
                }
            } else {
                event.setY(0.0);
            }

            if (MovementUtils.isMoving()) {
                MovementUtils.setHorizontalMotion(event, MovementUtils.getSpeedWithPotion() * speed);
            }

            minecraft.player.distanceWalkedModified = 0.0F;
            minecraft.player.onGround = true;
        }
    }

    public NoClipModule() {
        super("NoClip", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.setName("No Clip");
    }

    @Subscribe(mc = BlockCollisionEvent.class)
    public void onBlockCollision(BlockCollisionEvent event) {
        boolean colliding =
                MovementPhysicsHelper.isCollidingWithBlocks(
                        minecraft.player.getEntityBoundingBox());
        if (colliding || minecraft.player.isSneaking()) {
            event.setBounds(null);
        }
    }
}
