package remix.module.render;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;

import remix.client.ClientServices;
import remix.command.ClientCommandManager;
import remix.event.InputType;
import remix.event.annotation.Subscribe;
import remix.event.impl.ClientTickEvent;
import remix.input.InputEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

/** Spectator module: render events and 0 settings. */
public final class SpectatorModule extends ModuleBase {

    private Entity spectatedEntity;


    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {

        this.clearInvalidTarget();
        if (minecraft.player == null || minecraft.world == null) {
            return;
        }
        InputType type = event.getType();

        if (type == InputType.LEFT_CLICK && minecraft.getRenderViewEntity() == minecraft.player) {

            RayTraceResult objectMouseOver = minecraft.objectMouseOver;

            if (objectMouseOver != null
                    && objectMouseOver.typeOfHit == RayHitType.ENTITY
                    && this.canSpectateEntity(objectMouseOver.entityHit)) {

                this.spectatedEntity = objectMouseOver.entityHit;

                minecraft.setRenderViewEntity(this.spectatedEntity);
            }

            return;
        }

        if (type == InputType.RIGHT_CLICK && this.spectatedEntity != null) {

            this.stopSpectating();
        }
    }

    public void onEnable() {

        super.onEnable();

        GuiNewChat chatGUI = minecraft.ingameGUI == null ? null : minecraft.ingameGUI.getChatGUI();

        if (chatGUI != null && ClientServices.commands != null) {

            ClientCommandManager.printMessage("Click someone to spectate them.");
        }
    }

    public SpectatorModule() {

        super("Spectator", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
    }

    public void onDisable() {

        super.onDisable();

        this.stopSpectating();
    }

    @Subscribe(mc = ClientTickEvent.class)
    public void onClientTick(ClientTickEvent event) {
        this.clearInvalidTarget();
    }

    public void stopSpectating() {
        if (this.spectatedEntity != null
                && minecraft.getRenderViewEntity() == this.spectatedEntity) {
            minecraft.setRenderViewEntity(minecraft.player);
        }
        this.spectatedEntity = null;
    }

    private void clearInvalidTarget() {
        if (this.spectatedEntity == null) {
            return;
        }
        if (minecraft.player == null
                || minecraft.world == null
                || this.spectatedEntity.world != minecraft.world
                || minecraft.world.getEntityByID(this.spectatedEntity.getEntityId())
                        != this.spectatedEntity) {
            this.stopSpectating();
        } else if (minecraft.getRenderViewEntity() != this.spectatedEntity) {
            this.spectatedEntity = null;
        }
    }

    private boolean canSpectateEntity(Entity entity) {

        return entity instanceof PlayerEntity
                && entity.world == minecraft.world
                && minecraft.world.getEntityByID(entity.getEntityId()) == entity
                && entity != minecraft.player
                && entity != ClientServices.moduleManager.freeCam.bodyClone
                && entity != ClientServices.moduleManager.blink.ghostPlayer;
    }
}
