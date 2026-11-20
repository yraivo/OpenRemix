package remix.module.render;

import remix.ui.clickgui.ClickGuiBackgroundMode;
import remix.ui.clickgui.ClickGuiCheckMode;
import remix.ui.clickgui.ClickGuiOptionsMode;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** ClickGui module: render events and 5 settings. */

public final class ClickGuiModule extends ModuleBase {

    public final Setting<ClickGuiCheckMode> checkModeSetting =

            new Setting("CheckMode", this, ClickGuiCheckMode.CHECK);



    public Setting iconSetting;

    public Setting<ClickGuiOptionsMode> optionsSetting;

    public Setting statusBarSetting;

    public Setting<ClickGuiBackgroundMode> backgroundSetting;



    public ClickGuiModule() {

        super(

                "ClickGui",

                new ModuleDefaults(ModuleCategory.RENDER).keyBind(54));

        this.backgroundSetting = new Setting("Background", this, ClickGuiBackgroundMode.COLORFUL);

        this.optionsSetting = new Setting("Options", this, ClickGuiOptionsMode.LINES);

        this.iconSetting = new Setting("Icon", this, true);

        this.statusBarSetting = new Setting("StatusBar", this, true);

        this.setName("Click gui");

    }



    public void onEnable() {

        super.onEnable();

        minecraft.displayGuiScreen(ClientServices.clickGui);

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.isEnabled()) {

            this.toggle();

        }

    }




}

