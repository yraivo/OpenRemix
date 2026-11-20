package remix.module.player;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import remix.event.MotionPhase;
import remix.util.movement.MovementUtils;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

import java.util.Collection;

/** PotionSaver module: player events and 0 settings. */
public final class PotionSaverModule extends ModuleBase {
    private int primaryEffectTicks;
    private boolean canPausePotionTicks;

    private int secondaryEffectTicks;

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            LocalPlayer player = minecraft.player;
            Collection activePotionEffects = player.getActivePotionEffects();
            this.canPausePotionTicks =
                    !MovementUtils.isMoving()
                            && !activePotionEffects.isEmpty()
                            && !player.isUsingItem()
                            && !player.isSwingInProgress
                            && !event.hasRotationChanged();

            for (Object obj : activePotionEffects) {
                PotionEffect potionEffect = (PotionEffect) obj;
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                if (potion.isUsable()) {
                    this.primaryEffectTicks++;
                } else if (potion.isBadEffect()) {
                    this.secondaryEffectTicks++;
                }
            }

            event.setCancelled(this.shouldPausePotionTicks());
            if (!this.canPausePotionTicks) {
                this.primaryEffectTicks = 0;
                this.secondaryEffectTicks = 0;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.canPausePotionTicks = false;
        this.primaryEffectTicks = 0;
        this.secondaryEffectTicks = 0;
    }

    public boolean shouldPausePotionTicks() {
        return this.primaryEffectTicks >= this.secondaryEffectTicks && this.canPausePotionTicks;
    }

    public PotionSaverModule() {
        super("PotionSaver", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.setName("Potion Saver");
    }
}
