package remix.module.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.Vec3;
import remix.event.impl.AttackEntityEvent;
import remix.event.impl.EntityPushEvent;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PushOutOfBlocksEvent;
import remix.event.annotation.Subscribe;
import remix.event.impl.WaterPushEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.ui.screen.RemixMainMenuScreen;

import org.lwjgl.input.Keyboard;

/** FreeCam module: render events and 2 settings. */
public final class FreeCamModule extends ModuleBase {
    public EntityOtherPlayerMP bodyClone;

    private int unusedCloneState;
    private final Setting speedSetting = new Setting("Speed", this, 1.0, 1.0, 10.0, 1.0);
    private float savedYaw;

    private double savedY;
    private final Setting setting3DSetting = new Setting("3D", this, true);
    private double savedX;
    private float savedPitch;
    private double savedZ;

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            minecraft.player.renderArmYaw += 500.0F;
            minecraft.player.renderArmPitch += 500.0F;
            minecraft.player.motionX = minecraft.player.motionY = minecraft.player.motionZ = 0.0;
            double speed = this.speedSetting.asDouble();
            boolean setting3D = this.setting3DSetting.asBoolean();
            Vec3 vec3 = minecraft.player.getLook(1.0F);
            double d1 = Math.sin(minecraft.player.rotationYaw * Math.PI / 180.0);
            double d3 = Math.cos(minecraft.player.rotationYaw * Math.PI / 180.0);

            if (setting3D) {
                if (minecraft.gameSettings.keyBindForward.isKeyDown()) {
                    event.setX(vec3.x * speed);
                    event.setY(vec3.y * speed);
                    event.setZ(vec3.z * speed);
                }

                if (minecraft.gameSettings.keyBindBack.isKeyDown()) {
                    event.setX(-vec3.x * speed);
                    event.setY(-vec3.y * speed);
                    event.setZ(-vec3.z * speed);
                }

                if (!minecraft.gameSettings.keyBindForward.isKeyDown()
                        && !minecraft.gameSettings.keyBindBack.isKeyDown()) {
                    event.setY(0.0);
                }
            }

            if (!(minecraft.currentScreen instanceof GuiChat)) {
                if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindJump.getKeyCode())) {
                    event.setY(minecraft.player.motionY + speed);
                } else if (Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())) {
                    event.setY(minecraft.player.motionY - speed);
                } else if (!setting3D) {
                    event.setY(0.0);
                }
            } else {
                event.setY(0.0);
            }

            event.setX(
                    event.getX()
                            + (minecraft.player.movementInput.moveStrafe * d3
                                    - minecraft.player.movementInput.moveForward * d1));
            event.setZ(
                    event.getZ()
                            + (minecraft.player.movementInput.moveForward * d3
                                    + minecraft.player.movementInput.moveStrafe * d1));
            minecraft.player.onGround = true;
            minecraft.player.distanceWalkedModified = 0.0F;
        }
    }

    @Subscribe(mc = AttackEntityEvent.class)
    public void onAttackEntity(AttackEntityEvent event) {
        event.cancel();
    }

    public FreeCamModule() {
        super("FreeCam", new ModuleDefaults(ModuleCategory.RENDER).keyBind(37));
        this.setName("Free Cam");
    }

    public void onEnable() {
        super.onEnable();
        if (minecraft.world == null || minecraft.currentScreen instanceof RemixMainMenuScreen) {
            this.setEnabled(false);
        } else {
            this.savedX = minecraft.player.posX;
            this.savedY = minecraft.player.posY;
            this.savedZ = minecraft.player.posZ;
            this.savedYaw = minecraft.player.rotationYaw;
            this.savedPitch = minecraft.player.rotationPitch;
            this.bodyClone =
                    new EntityOtherPlayerMP(
                            minecraft.world, minecraft.player.getGameProfile());
            this.bodyClone.setPositionAndRotation(
                    this.savedX, this.savedY, this.savedZ, this.savedYaw, this.savedPitch);
            this.bodyClone.rotationYawHead = minecraft.player.rotationYawHead;
            this.bodyClone.inventory = minecraft.player.inventory;
            minecraft.world.addEntityToWorld(-420, this.bodyClone);
        }
    }

    @Subscribe(mc = PushOutOfBlocksEvent.class)
    public void onPushOutOfBlocks(PushOutOfBlocksEvent event) {
        event.cancel();
    }

    @Subscribe(mc = WaterPushEvent.class)
    public void onWaterPush(WaterPushEvent event) {
        event.setCancelled(event.getEntity() == this.bodyClone);
    }

    @Subscribe(mc = EntityPushEvent.class)
    public void onEntityPush(EntityPushEvent event) {
        event.cancel();
    }

    public void onDisable() {
        super.onDisable();
        if (minecraft.player != null) {
            if (minecraft.world.getEntityByID(-420) == null) {
                return;
            }

            minecraft.player.setPositionAndRotation(
                    this.savedX, this.savedY, this.savedZ, this.savedYaw, this.savedPitch);
            minecraft.world.removeEntityFromWorld(-420);
            this.bodyClone = null;
            minecraft.player.capabilities.isFlying = false;
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SEND) {
            Packet packet = event.getPacket();
            if (!(packet instanceof C01PacketChatMessage)
                    && !(packet instanceof C00PacketKeepAlive)) {
                event.cancel();
            }
        }
    }
}
