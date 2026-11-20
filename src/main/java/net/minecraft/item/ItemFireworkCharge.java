package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemFireworkCharge extends Item {

    private static String[] packedTextFragments;

    static {
        net.minecraft.item.ItemFireworkCharge.packedTextFragments = new String[4];
        net.minecraft.item.ItemFireworkCharge.packedTextFragments[0] = new String("");
        net.minecraft.item.ItemFireworkCharge.packedTextFragments[1] = new String(" ");
    }

    public static NBTBase getExplosionTag(ItemStack itemStack, String text) {
        if (itemStack.hasTagCompound()) {
            CompoundTag compoundTag = itemStack.getTag().getCompoundTag("Explosion");
            if (compoundTag != null) {
                return compoundTag.getTag(text);
            }
        }

        return null;
    }

    public static void addExplosionInfo(CompoundTag compoundTag, List list) {
        String text;
        {
            String textValue = ItemStack.getLegacyItemStackFlowMarker();
            byte b0 = compoundTag.getByte("Type");
            text = textValue;
            if (b0 >= 0 && b0 <= 4) {
                list.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
                if (!(text == null)) {

                    list.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
                }
            } else {

                list.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
            }
        }

        int[] aint = compoundTag.getIntArray("Colors");
        if (aint.length > 0) {
            byte b1 = 1;
            String textValue2 = packedTextFragments[0];
            int[] aint1 = aint;
            int k = aint.length;
            int i = 0;

            while (i < k) {
                int j = aint1[i];
                if (b1 == 0) {
                    textValue2 = textValue2 + ", ";
                }

                b1 = 0;
                byte b2 = 0;
                int l = 0;

                while (l < ItemDye.dyeColors.length) {
                    if (j == ItemDye.dyeColors[l]) {
                        b2 = 1;
                        textValue2 =
                                textValue2
                                        + StatCollector.translateToLocal(
                                                "item.fireworksCharge."
                                                        + EnumDyeColor.byDyeDamage(l)
                                                                .getUnlocalizedName());
                        if (text == null) {
                            break;
                        }
                    }

                    l++;
                    if (text != null) {
                        break;
                    }
                }

                if (b2 == 0) {
                    textValue2 =
                            textValue2
                                    + StatCollector.translateToLocal(
                                            "item.fireworksCharge.customColor");
                }

                i++;
                if (text != null) {
                    break;
                }
            }

            list.add(textValue2);
        }

        int[] aint2 = compoundTag.getIntArray("FadeColors");
        if (aint2.length > 0) {
            byte b3 = 1;
            String textValue3 =
                    StatCollector.translateToLocal("item.fireworksCharge.fadeTo")
                            + packedTextFragments[1];
            int[] aint3 = aint2;
            int j1 = aint2.length;
            int k1 = 0;

            while (k1 < j1) {
                int l1 = aint3[k1];
                if (b3 == 0) {
                    textValue3 = textValue3 + ", ";
                }

                b3 = 0;
                byte b4 = 0;
                int i1 = 0;

                while (i1 < 16) {
                    if (l1 == ItemDye.dyeColors[i1]) {
                        b4 = 1;
                        textValue3 =
                                textValue3
                                        + StatCollector.translateToLocal(
                                                "item.fireworksCharge."
                                                        + EnumDyeColor.byDyeDamage(i1)
                                                                .getUnlocalizedName());
                        if (text == null) {
                            break;
                        }
                    }

                    i1++;
                    if (text != null) {
                        break;
                    }
                }

                if (b4 == 0) {
                    textValue3 =
                            textValue3
                                    + StatCollector.translateToLocal(
                                            "item.fireworksCharge.customColor");
                }

                k1++;
                if (text != null) {
                    break;
                }
            }

            list.add(textValue3);
        }

        boolean booleanValue = compoundTag.getBoolean("Trail");
        if (booleanValue) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }

        boolean booleanValueValue = compoundTag.getBoolean("Flicker");
        if (booleanValueValue) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        if (itemStack.hasTagCompound()) {
            CompoundTag compoundTag = itemStack.getTag().getCompoundTag("Explosion");
            if (compoundTag != null) {
                addExplosionInfo(compoundTag, list);
            }
        }
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (i != 1) {
            return super.getColorFromItemStack(itemStack, i);
        }

        NBTBase nbtBase = getExplosionTag(itemStack, "Colors");
        if (!(nbtBase instanceof NBTTagIntArray)) {
            return 9079434;
        }

        NBTTagIntArray nbtTagIntArray = (NBTTagIntArray) nbtBase;
        int[] aint = nbtTagIntArray.getIntArray();
        if (aint.length == 1) {
            return aint[0];
        }

        int j = 0;
        int k = 0;
        int l = 0;
        int[] aint1 = aint;
        int length = aint.length;
        int j1 = 0;

        while (j1 < length) {
            int k1 = aint1[j1];
            j += (k1 & 0xFF0000) >> 16;
            k += (k1 & 0xFF00) >> 8;
            l += (k1 & 0xFF) >> 0;
            j1++;
            if (text != null) {
                break;
            }
        }

        j /= aint.length;
        k /= aint.length;
        l /= aint.length;
        return j << 16 | k << 8 | l;
    }
}
