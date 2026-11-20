package net.minecraft.profiler;

import net.minecraft.profiler.PlayerUsageSnooper;

import java.util.TimerTask;

class PlayerUsageSnooperTimerTask extends TimerTask {

    PlayerUsageSnooper playerUsageSnooper;


    @Override
    public void run() {
        // Optional update check or telemetry worker disabled.
    }

    PlayerUsageSnooperTimerTask(PlayerUsageSnooper bNLValue) {
        this.playerUsageSnooper = bNLValue;
    }
}
