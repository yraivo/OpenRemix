package remix.module.combat;

import remix.setting.SettingOption;
import remix.util.entity.RotationHelper;
import remix.util.function.TriFunction;
import remix.util.inventory.InventoryItemHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.LivingEntity;
import remix.module.combat.AuraModule;

public enum AuraTargetSort implements SettingOption {
    ANGLE(
            "Angle",
            (remix.util.function.TriFunction<
                            net.minecraft.entity.LivingEntity, net.minecraft.entity.LivingEntity, float[], java.lang.Integer>)
                    ((class_db6392e3d5c955f92780, class_db6392e3d5c955f927801, afloat) -> {
                        float f = RotationHelper.yawToEntity(class_db6392e3d5c955f92780);
                        float f1 = RotationHelper.yawToEntity(class_db6392e3d5c955f927801);
                        return Float.compare(f, f1);
                    })),
    DISTANCE(
            "Distance",
            (remix.util.function.TriFunction<
                            net.minecraft.entity.LivingEntity, net.minecraft.entity.LivingEntity, float[], java.lang.Integer>)
                    ((class_db6392e3d5c955f92780, class_db6392e3d5c955f927801, afloat) -> {
                        double d0 =
                                class_db6392e3d5c955f92780.getDistanceToEntity(
                                        MinecraftClient.getMinecraft().player);
                        double d1 =
                                class_db6392e3d5c955f927801.getDistanceToEntity(
                                        MinecraftClient.getMinecraft().player);
                        return Double.compare(d0, d1);
                    })),
    HEALTH(
            "Health",
            (remix.util.function.TriFunction<
                            net.minecraft.entity.LivingEntity, net.minecraft.entity.LivingEntity, float[], java.lang.Integer>)
                    ((class_db6392e3d5c955f92780, class_db6392e3d5c955f927801, afloat) -> {
                        double d0 = class_db6392e3d5c955f92780.getHealth();
                        double d1 = class_db6392e3d5c955f927801.getHealth();
                        return Double.compare(d0, d1);
                    })),
    SMART(
            "Smart",
            (remix.util.function.TriFunction<
                            net.minecraft.entity.LivingEntity, net.minecraft.entity.LivingEntity, float[], java.lang.Integer>)
                    ((class_db6392e3d5c955f92780, class_db6392e3d5c955f927801, afloat) -> {
                        LocalPlayer class_7e19c7f4e2ea1cb76ab5 = AuraModule.getMinecraft().player;
                        float f =
                                InventoryItemHelper.getDamageVsLiving(
                                        class_db6392e3d5c955f92780,
                                        class_7e19c7f4e2ea1cb76ab5.getHeldItem());
                        float f1 = class_db6392e3d5c955f92780.getHealth();
                        float f2 = f1 - f;
                        float f3 =
                                InventoryItemHelper.getDamageVsLiving(
                                        class_db6392e3d5c955f927801,
                                        class_7e19c7f4e2ea1cb76ab5.getHeldItem());
                        float f4 = class_db6392e3d5c955f927801.getHealth();
                        float f5 = f4 - f3;
                        return Float.compare(f2, f5);
                    })),
    HURTTIME(
            "HurtTime",
            (remix.util.function.TriFunction<
                            net.minecraft.entity.LivingEntity, net.minecraft.entity.LivingEntity, float[], java.lang.Integer>)
                    ((class_db6392e3d5c955f92780, class_db6392e3d5c955f927801, afloat) -> {
                        double d0 = class_db6392e3d5c955f92780.hurtTime;
                        double d1 = class_db6392e3d5c955f927801.hurtTime;
                        return Double.compare(d0, d1);
                    }));

    private String displayName;

    private TriFunction<LivingEntity, LivingEntity, float[], Integer> QX;

    public int compareTargets(
            LivingEntity livingEntity, LivingEntity livingEntityValue, float[] afloat) {
        return (Integer) this.QX.a(livingEntity, livingEntityValue, afloat);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    AuraTargetSort(
            String displayNameValue,
            TriFunction<LivingEntity, LivingEntity, float[], Integer>
                    triFunction) {
        this.QX = triFunction;
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
