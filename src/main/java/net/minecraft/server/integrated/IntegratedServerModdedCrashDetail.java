package net.minecraft.server.integrated;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.Callable;

class IntegratedServerModdedCrashDetail implements Callable {
    IntegratedServer field_76972_a;
    private static String[] packedTextFragments;

    IntegratedServerModdedCrashDetail(IntegratedServer byTValue) {
        this.field_76972_a = byTValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        net.minecraft.server.integrated.IntegratedServerModdedCrashDetail.packedTextFragments = new String[4];
        net.minecraft.server.integrated.IntegratedServerModdedCrashDetail.packedTextFragments[0] = new String("'");
        net.minecraft.server.integrated.IntegratedServerModdedCrashDetail.packedTextFragments[1] = new String("'");
    }

    public String getCrashDetail() {
        String text = ClientBrandRetriever.getClientModName();
        if (!text.equals("vanilla")) {
            return "Definitely; Client brand changed to '" + text + packedTextFragments[0];
        }

        text = this.field_76972_a.getServerModName();
        return !text.equals("vanilla")
                ? "Definitely; Server brand changed to '" + text + packedTextFragments[1]
                : (MinecraftClient.class.getSigners() == null
                        ? "Very likely; Jar signature invalidated"
                        : "Probably not. Jar signature remains and both client + server brands are untouched.");
    }
}
