package remix.module.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import optifine.MathUtils;
import remix.event.MotionPhase;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render2DEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

/** Fard module: render events and 1 settings. */
public final class FardModule extends ModuleBase {
    private ClientFontRenderer textFont;
    private float velocityY;

    private float imageX;
    private float velocityX;
    private ResourceLocation imageTexture;
    private long previousFrameMillis;
    private float imageY;
    private String[] randomWords;

    private final Setting textSetting = new Setting("Text", this, false);

    @Override
    public void onEnable() {
        this.previousFrameMillis = System.currentTimeMillis();
        NotificationManager.post(
                new NotificationManager(
                        new String[] {"Stop this nonsense", "Fard XDDDDD"},
                        NotificationPriority.HIGH,
                        4000));
        super.onEnable();
    }

    public FardModule() {
        super("Fard", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.textFont = RemixClient.fonts.roboto32;
        this.imageTexture = new ResourceLocation("remix/poop.png");
        this.randomWords = new String[] {"Fard", "Shid", "Poop", "Penis"};
        this.velocityX = 500.0F;
        this.velocityY = 500.0F;
    }

    @Subscribe(mc = Render2DEvent.class)
    public void onRender2D(Render2DEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            ScaledResolution resolution = event.getResolution();
            if (this.textSetting.asBoolean()) {
                for (int i = 0; i < 50; i++) {
                    String textValue =
                            this.randomWords[MathUtils.random.nextInt(this.randomWords.length)];
                    float f = MathUtils.aN() * resolution.getWidth();
                    float f1 = MathUtils.aN() * resolution.getHeight();
                    this.textFont.drawCenteredStringWithShadow(textValue, f, f1, -1);
                }
            }

            Render2D3DUtils.drawIcon(
                    this.imageTexture, this.imageX, this.imageY, 190.0F, 190.0F);
            float f2 =
                    (float) (System.currentTimeMillis() - this.previousFrameMillis) / 1000.0F;
            this.imageX += this.velocityX * f2;
            this.imageY += this.velocityY * f2;
            if (this.imageX >= resolution.getWidth() - 95) {
                this.velocityX = -500.0F;
            } else if (this.imageX < 0.0F) {
                this.velocityX = 500.0F;
            }

            if (this.imageY >= resolution.getHeight() - 95) {
                this.velocityY = -500.0F;
            } else if (this.imageY < 0.0F) {
                this.velocityY = 500.0F;
            }

            this.previousFrameMillis = System.currentTimeMillis();
        }
    }
}
