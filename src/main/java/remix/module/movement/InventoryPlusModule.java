package remix.module.movement;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.ui.clickgui.ClickGuiScreen;



import org.lwjgl.input.Keyboard;



/** Updates movement keys and arrow-key camera rotation while inventory-like screens are open, and optionally suppresses the player-inventory close packet. */

public final class InventoryPlusModule extends ModuleBase {



    private Setting moreSetting;



    private final Setting moveSetting = new Setting("Move", this, true);



    @Subscribe(mc = PacketEvent.class)

    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.SEND

                && this.moreSetting.asBoolean()

                && event.getPacket() instanceof C0DPacketCloseWindow) {

            C0DPacketCloseWindow packet = (C0DPacketCloseWindow) event.getPacket();

            if (packet.getWindowId() == 0) {

                event.cancel();

            }

        }

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.moveSetting.asBoolean()) {

            KeyBinding[] keyBindingArray =

                    new KeyBinding[] {

                        minecraft.gameSettings.keyBindForward,

                        minecraft.gameSettings.keyBindBack,

                        minecraft.gameSettings.keyBindLeft,

                        minecraft.gameSettings.keyBindRight,

                        minecraft.gameSettings.keyBindJump

                    };

            if (minecraft.currentScreen instanceof GuiContainer

                    || minecraft.currentScreen instanceof ClickGuiScreen) {

                for (KeyBinding keyBinding : keyBindingArray) {

                    KeyBinding.setKeyBindState(

                            keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));

                }



                if (Keyboard.isKeyDown(200)) {

                    minecraft.player.rotationPitch -= 5.0F;

                }



                if (Keyboard.isKeyDown(208)) {

                    minecraft.player.rotationPitch += 5.0F;

                }



                if (Keyboard.isKeyDown(203)) {

                    minecraft.player.rotationYaw -= 5.0F;

                }



                if (Keyboard.isKeyDown(205)) {

                    minecraft.player.rotationYaw += 5.0F;

                }



                if (minecraft.player.rotationPitch > 90.0F) {

                    minecraft.player.rotationPitch = 90.0F;

                }



                if (!(minecraft.player.rotationPitch < -90.0F)) {

                    return;

                }



                minecraft.player.rotationPitch = -90.0F;



                return;

            }



            if (minecraft.currentScreen == null) {

                for (KeyBinding keyBindingValue : keyBindingArray) {

                    if (!Keyboard.isKeyDown(keyBindingValue.getKeyCode())) {

                        KeyBinding.setKeyBindState(keyBindingValue.getKeyCode(), false);

                    }

                }

            }

        }

    }



    public InventoryPlusModule() {

        super(

                "InventoryPlus",

                new ModuleDefaults(ModuleCategory.MOVEMENT).visible(false));

        this.moreSetting = new Setting("More", this, true);

        this.setName("Inventory+");

    }




}

