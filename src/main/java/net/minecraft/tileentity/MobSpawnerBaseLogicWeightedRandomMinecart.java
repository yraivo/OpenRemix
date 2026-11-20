package net.minecraft.tileentity;

import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.WeightedRandomItem;

public class MobSpawnerBaseLogicWeightedRandomMinecart extends WeightedRandomItem {
    MobSpawnerBaseLogic field_98221_d;

    private String entityType;
    private CompoundTag nbtData;

    public CompoundTag toNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setTag("Properties", this.nbtData);
        compoundTag.setString("Type", this.entityType);
        compoundTag.setInteger("Weight", this.itemWeight);
        return compoundTag;
    }

    public MobSpawnerBaseLogicWeightedRandomMinecart(
            MobSpawnerBaseLogic mobSpawnerBaseLogic, CompoundTag compoundTag, String text) {
        this(mobSpawnerBaseLogic, compoundTag, text, 1);
    }

    public MobSpawnerBaseLogicWeightedRandomMinecart(
            MobSpawnerBaseLogic mobSpawnerBaseLogic, CompoundTag compoundTag) {
        this(
                mobSpawnerBaseLogic,
                compoundTag.getCompoundTag("Properties"),
                compoundTag.getString("Type"),
                compoundTag.getInteger("Weight"));
    }

    static String access$000(
            MobSpawnerBaseLogicWeightedRandomMinecart mobSpawnerBaseLogicWeightedRandomMinecart) {
        return mobSpawnerBaseLogicWeightedRandomMinecart.entityType;
    }

    private MobSpawnerBaseLogicWeightedRandomMinecart(
            MobSpawnerBaseLogic bGQValue, CompoundTag bvDValue, String text, int i) {
        super(i);
        MobSpawnerBaseLogic.GU();

        this.field_98221_d = bGQValue;

        if (text.equals("Minecart")) {
            if (bvDValue != null) {
                text =
                        EntityMinecartEnumMinecartType.byNetworkID(bvDValue.getInteger("Type"))
                                .getName();

            } else {

                text = "MinecartRideable";
            }
        }

        this.nbtData = bvDValue;
        this.entityType = text;
    }

    static CompoundTag access$100(
            MobSpawnerBaseLogicWeightedRandomMinecart mobSpawnerBaseLogicWeightedRandomMinecart) {
        return mobSpawnerBaseLogicWeightedRandomMinecart.nbtData;
    }


}
