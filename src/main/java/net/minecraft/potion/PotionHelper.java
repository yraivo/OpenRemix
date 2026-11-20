package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;

import optifine.CustomColors;
import optifine.OptiFineConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PotionHelper {
    private static String[] packedTextFragments;
    public static String gunpowderEffect;
    public static String ghastTearEffect;
    public static String speckledMelonEffect;
    public static String rabbitFootEffect;
    public static String goldenCarrotEffect;
    public static String sugarEffect;
    public static String redstoneEffect;
    public static String glowstoneEffect;
    private static Map potionAmplifiers;
    public static String spiderEyeEffect;
    private static String[] potionPrefixes;
    private static Map potionRequirements;
    public static String fermentedSpiderEyeEffect;
    public static String blazePowderEffect;
    public static String magmaCreamEffect;
    public static String pufferfishEffect;
    public static String a;
    private static Map DATAVALUE_COLORS;

    public static void clearPotionColorCache() {
        net.minecraft.potion.PotionHelper.DATAVALUE_COLORS.clear();
    }

    public static int method_216cab78ca4939917c75(int i, int j, int k, int l, int i1, int j1) {
        int[] aint = PotionEffect.getLegacyPotionFlowMarker();
        int k1 =
                (checkFlag(i, j) ? 16 : 0)
                        | (checkFlag(i, k) ? 8 : 0)
                        | (checkFlag(i, l) ? 4 : 0)
                        | (checkFlag(i, i1) ? 2 : 0)
                        | (checkFlag(i, j1) ? 1 : 0);
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }

        return k1;
    }

    private static int method_d24d17aebc405b596335(
            boolean flag, boolean flag1, boolean flag2, int i, int j, int k, int l) {
        int i1;
        label48:
        {
            int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
            i1 = 0;
            int[] aint = aint1;
            if (flag) {
                i1 = isFlagUnset(l, j);
                if (aint == null) {
                    break label48;
                }
            }

            if (i != -1) {
                if (i == 0 && method_e52c8f2535cd88b2ee66(l) == j) {
                    i1 = 1;
                    if (aint == null) {
                        break label48;
                    }
                }

                if (i == 1 && method_e52c8f2535cd88b2ee66(l) > j) {
                    i1 = 1;
                    if (aint == null) {
                        break label48;
                    }
                }

                if (i != 2 || method_e52c8f2535cd88b2ee66(l) >= j) {
                    break label48;
                }

                i1 = 1;
                if (aint == null) {
                    break label48;
                }
            }

            i1 = isFlagSet(l, j);
        }

        if (flag1) {
            i1 *= k;
        }

        if (flag2) {
            i1 *= -1;
        }

        return i1;
    }

    private static int method_8fb11d651c99032c9a8b(
            int i, int j, boolean flag, boolean flag1, boolean flag2) {
        int[] aint = PotionEffect.getLegacyPotionFlowMarker();
        if (flag2) {
            if (!checkFlag(i, j)) {
                return 0;
            }
        } else {
            if (flag) {
                i &= ~(1 << j);
                if (aint == null) {
                    return i;
                }
            }

            if (flag1) {
                if ((i & 1 << j) == 0) {
                    i |= 1 << j;
                    if (aint == null) {
                        return i;
                    }
                }

                i &= ~(1 << j);
                if (aint == null) {
                    return i;
                }
            }

            i |= 1 << j;
        }

        return i;
    }

    public static boolean checkFlag(int i, int j) {
        return (i & 1 << j) != 0;
    }

    public static List getPotionEffects(int i, boolean flag) {
        ArrayList arrayList = null;
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        Potion[] bzT = Potion.potionTypes;
        int[] aint = aint1;

        for (Potion potion : bzT) {
            if (potion != null && (!potion.isUsable() || flag)) {
                String text =
                        (String)
                                net.minecraft.potion.PotionHelper.potionRequirements.get(
                                        potion.getId());
                if (text != null) {
                    int j = parsePotionEffects(text, 0, text.length(), i);
                    if (j > 0) {
                        int k = 0;
                        String textValue =
                                (String)
                                        net.minecraft.potion.PotionHelper.potionAmplifiers.get(
                                                potion.getId());
                        if (textValue != null) {
                            k = parsePotionEffects(textValue, 0, textValue.length(), i);
                            if (k < 0) {
                                k = 0;
                            }
                        }

                        label38:
                        {
                            if (potion.isInstant()) {
                                j = 1;
                                if (aint == null) {
                                    break label38;
                                }
                            }

                            j = 1200 * (j * 3 + (j - 1) * 2);
                            j >>= k;
                            j = (int) Math.round(j * potion.getEffectiveness());
                            if ((i & 16384) != 0) {
                                j = (int) Math.round(j * 0.75 + 0.5);
                            }
                        }

                        if (arrayList == null) {
                            arrayList = Lists.newArrayList();
                        }

                        PotionEffect potionEffect = new PotionEffect(potion.getId(), j, k);
                        if ((i & 16384) != 0) {
                            potionEffect.setSplashPotion(true);
                        }

                        arrayList.add(potionEffect);
                    }
                }
            }

            if (aint != null) {
                break;
            }
        }

        return arrayList;
    }

    private static int method_e52c8f2535cd88b2ee66(int i) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        int j = 0;
        int[] aint = aint1;

        while (i > 0) {
            i &= i - 1;
            j++;
            if (aint != null) {
                break;
            }
        }

        return j;
    }

    public static int applyIngredient(int i, String text) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        byte b0 = 0;
        int[] aint = aint1;
        int j = text.length();
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        int k = 0;
        int l = b0;

        while (l < j) {
            label66:
            {
                char c0 = text.charAt(l);
                if (c0 >= '0' && c0 <= '9') {
                    k *= 10;
                    k += c0 - '0';
                    b1 = 1;
                    if (aint == null) {
                        break label66;
                    }
                }

                if (c0 == '!') {
                    if (b1 != 0) {
                        i =
                                method_8fb11d651c99032c9a8b(
                                        i,
                                        k,
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b2),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b4));
                        b4 = 0;
                        b2 = 0;
                        b3 = 0;
                        b1 = 0;
                        k = 0;
                    }

                    b2 = 1;
                    if (aint == null) {
                        break label66;
                    }
                }

                if (c0 == '-') {
                    if (b1 != 0) {
                        i =
                                method_8fb11d651c99032c9a8b(
                                        i,
                                        k,
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b2),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b4));
                        b4 = 0;
                        b2 = 0;
                        b3 = 0;
                        b1 = 0;
                        k = 0;
                    }

                    b3 = 1;
                    if (aint == null) {
                        break label66;
                    }
                }

                if (c0 == '+') {
                    if (b1 == 0) {
                        break label66;
                    }

                    i =
                            method_8fb11d651c99032c9a8b(
                                    i,
                                    k,
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(b2),
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(b4));
                    b4 = 0;
                    b2 = 0;
                    b3 = 0;
                    b1 = 0;
                    k = 0;
                    if (aint == null) {
                        break label66;
                    }
                }

                if (c0 == '&') {
                    if (b1 != 0) {
                        i =
                                method_8fb11d651c99032c9a8b(
                                        i,
                                        k,
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b2),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b4));
                        b4 = 0;
                        b2 = 0;
                        b3 = 0;
                        b1 = 0;
                        k = 0;
                    }

                    b4 = 1;
                }
            }

            l++;
            if (aint != null) {
                break;
            }
        }

        if (b1 != 0) {
            i =
                    method_8fb11d651c99032c9a8b(
                            i,
                            k,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b2),
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b4));
        }

        return i & 32767;
    }

    static {
        net.minecraft.potion.PotionHelper.packedTextFragments = new String[10];
        net.minecraft.potion.PotionHelper.packedTextFragments[0] =
                new String(
                        "H\b[\u0007+:\u000eI\u001e[\u0013+=\u001fZ\u0017\u0019W\u000fHdu\u0011\u0019J\u001eGbc\u0011\u001aH\u001aS`wV\u0007_");
        net.minecraft.potion.PotionHelper.packedTextFragments[1] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[2] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[3] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[4] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[5] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[6] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[7] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[8] = new String("5");
        net.minecraft.potion.PotionHelper.packedTextFragments[9] = new String("5");
        net.minecraft.potion.PotionHelper.ghastTearEffect = "+0-1-2-3&4-4+13";
        a = null;
        net.minecraft.potion.PotionHelper.potionRequirements = Maps.newHashMap();
        net.minecraft.potion.PotionHelper.potionAmplifiers = Maps.newHashMap();
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.REGENERATION.getId(), "0 & !1 & !2 & !3 & 0+6");
        net.minecraft.potion.PotionHelper.sugarEffect = "-0+1-2-3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.MOVE_SPEED.getId(), "!0 & 1 & !2 & !3 & 1+6");
        net.minecraft.potion.PotionHelper.magmaCreamEffect = "+0+1-2-3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.FIRE_RESISTANCE.getId(), "0 & 1 & !2 & !3 & 0+6");
        net.minecraft.potion.PotionHelper.speckledMelonEffect = "+0-1+2-3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.HEAL.getId(), "0 & !1 & 2 & !3");
        net.minecraft.potion.PotionHelper.spiderEyeEffect = "-0-1+2-3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.POISON.getId(), "!0 & !1 & 2 & !3 & 2+6");
        net.minecraft.potion.PotionHelper.fermentedSpiderEyeEffect = "-0+3-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.WEAKNESS.getId(), "!0 & !1 & !2 & 3 & 3+6");
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.HARM.getId(), "!0 & !1 & 2 & 3");
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.MOVE_SLOWDOWN.getId(), "!0 & 1 & !2 & 3 & 3+6");
        net.minecraft.potion.PotionHelper.blazePowderEffect = "+0-1-2+3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.DAMAGE_BOOST.getId(), "0 & !1 & !2 & 3 & 3+6");
        net.minecraft.potion.PotionHelper.goldenCarrotEffect = "-0+1+2-3+13&4-4";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.NIGHT_VISION.getId(), "!0 & 1 & 2 & !3 & 2+6");
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.INVISIBILITY.getId(), "!0 & 1 & 2 & 3 & 2+6");
        net.minecraft.potion.PotionHelper.pufferfishEffect = "+0-1+2+3+13&4-4";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.WATER_BREATHING.getId(), "0 & !1 & 2 & 3 & 2+6");
        net.minecraft.potion.PotionHelper.rabbitFootEffect = "+0+1-2+3&4-4+13";
        net.minecraft.potion.PotionHelper.potionRequirements.put(
                Potion.JUMP.getId(), "0 & 1 & !2 & 3");
        net.minecraft.potion.PotionHelper.glowstoneEffect = "+5-6-7";
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.MOVE_SPEED.getId(), packedTextFragments[1]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.DIG_SPEED.getId(), packedTextFragments[2]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.DAMAGE_BOOST.getId(), packedTextFragments[3]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.REGENERATION.getId(), packedTextFragments[4]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.HARM.getId(), packedTextFragments[5]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.HEAL.getId(), packedTextFragments[6]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.RESISTANCE.getId(), packedTextFragments[7]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.POISON.getId(), packedTextFragments[8]);
        net.minecraft.potion.PotionHelper.potionAmplifiers.put(
                Potion.JUMP.getId(), packedTextFragments[9]);
        net.minecraft.potion.PotionHelper.redstoneEffect = "-5+6-7";
        net.minecraft.potion.PotionHelper.gunpowderEffect = "+14&13-13";
        net.minecraft.potion.PotionHelper.DATAVALUE_COLORS = Maps.newHashMap();
        net.minecraft.potion.PotionHelper.potionPrefixes =
                new String[] {
                    "potion.prefix.mundane",
                    "potion.prefix.uninteresting",
                    "potion.prefix.bland",
                    "potion.prefix.clear",
                    "potion.prefix.milky",
                    "potion.prefix.diffuse",
                    "potion.prefix.artless",
                    "potion.prefix.thin",
                    "potion.prefix.awkward",
                    "potion.prefix.flat",
                    "potion.prefix.bulky",
                    "potion.prefix.bungling",
                    "potion.prefix.buttered",
                    "potion.prefix.smooth",
                    "potion.prefix.suave",
                    "potion.prefix.debonair",
                    "potion.prefix.thick",
                    "potion.prefix.elegant",
                    "potion.prefix.fancy",
                    "potion.prefix.charming",
                    "potion.prefix.dashing",
                    "potion.prefix.refined",
                    "potion.prefix.cordial",
                    "potion.prefix.sparkling",
                    "potion.prefix.potent",
                    "potion.prefix.foul",
                    "potion.prefix.odorless",
                    "potion.prefix.rank",
                    "potion.prefix.harsh",
                    "potion.prefix.acrid",
                    "potion.prefix.gross",
                    "potion.prefix.stinky"
                };
    }

    // region String decoding support

    // endregion

    public static int calcPotionLiquidColor(Collection collection) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        int i = 3694022;
        int[] aint = aint1;
        if (collection != null && !collection.isEmpty()) {
            float fx = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            for (java.util.Iterator<?> __recoveryIterator171_20512 = (collection).iterator();
                    (__recoveryIterator171_20512).hasNext(); ) {
                PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_20512).next();
                if (potionEffect.getIsShowParticles()) {
                    int j = Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();
                    if (OptiFineConfig.isCustomColors()) {
                        j = CustomColors.method_e0b8a2f32478f200146b(potionEffect.getPotionID(), j);
                    }

                    int k = 0;

                    while (k <= potionEffect.getAmplifier()) {
                        fx += (j >> 16 & 0xFF) / 255.0F;
                        f1 += (j >> 8 & 0xFF) / 255.0F;
                        f2 += (j >> 0 & 0xFF) / 255.0F;
                        f3++;
                        k++;
                        if (aint != null) {
                            break;
                        }
                    }
                }

                if (aint != null) {
                    break;
                }
            }

            if (f3 == 0.0F) {
                return 0;
            }

            fx = fx / f3 * 255.0F;
            f1 = f1 / f3 * 255.0F;
            f2 = f2 / f3 * 255.0F;
            return (int) fx << 16 | (int) f1 << 8 | (int) f2;
        } else {
            if (OptiFineConfig.isCustomColors()) {
                i = CustomColors.method_e0b8a2f32478f200146b(0, i);
            }

            return i;
        }
    }

    // region String decoding support

    // endregion

    private static int isFlagSet(int i, int j) {
        return checkFlag(i, j) ? 1 : 0;
    }

    private static int parsePotionEffects(String text, int i, int j, int k) {
        int[] aint = PotionEffect.getLegacyPotionFlowMarker();
        if (i < text.length() && j >= 0 && i < j) {
            int l = text.indexOf(124, i);
            if (l >= 0 && l < j) {
                int k2 = parsePotionEffects(text, i, l - 1, k);
                if (k2 > 0) {
                    return k2;
                }

                int l2 = parsePotionEffects(text, l + 1, j, k);
                return l2 > 0 ? l2 : 0;
            } else {
                int i1 = text.indexOf(38, i);
                if (i1 >= 0 && i1 < j) {
                    int j1 = parsePotionEffects(text, i, i1 - 1, k);
                    if (j1 <= 0) {
                        return 0;
                    }

                    int i3 = parsePotionEffects(text, i1 + 1, j, k);
                    return i3 <= 0 ? 0 : (j1 > i3 ? j1 : i3);
                } else {
                    byte b0 = 0;
                    byte b1 = 0;
                    byte b2 = 0;
                    byte b3 = 0;
                    byte b4 = 0;
                    byte b5 = -1;
                    int k1 = 0;
                    int l1 = 0;
                    int i2 = 0;
                    int j2 = i;

                    while (j2 < j) {
                        label139:
                        {
                            char c0 = text.charAt(j2);
                            if (c0 >= '0' && c0 <= '9') {
                                if (b0 != 0) {
                                    l1 = c0 - '0';
                                    b1 = 1;
                                    if (aint == null) {
                                        break label139;
                                    }
                                }

                                k1 *= 10;
                                k1 += c0 - '0';
                                b2 = 1;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 == '*') {
                                b0 = 1;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 == '!') {
                                if (b2 != 0) {
                                    i2 +=
                                            method_d24d17aebc405b596335(
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b3),
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b1),
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b4),
                                                    b5,
                                                    k1,
                                                    l1,
                                                    k);
                                    b3 = 0;
                                    b4 = 0;
                                    b0 = 0;
                                    b1 = 0;
                                    b2 = 0;
                                    l1 = 0;
                                    k1 = 0;
                                    b5 = -1;
                                }

                                b3 = 1;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 == '-') {
                                if (b2 != 0) {
                                    i2 +=
                                            method_d24d17aebc405b596335(
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b3),
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b1),
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            b4),
                                                    b5,
                                                    k1,
                                                    l1,
                                                    k);
                                    b3 = 0;
                                    b4 = 0;
                                    b0 = 0;
                                    b1 = 0;
                                    b2 = 0;
                                    l1 = 0;
                                    k1 = 0;
                                    b5 = -1;
                                }

                                b4 = 1;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 != '=' && c0 != '<' && c0 != '>') {
                                if (c0 != '+' || b2 == 0) {
                                    break label139;
                                }

                                i2 +=
                                        method_d24d17aebc405b596335(
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b4),
                                                b5,
                                                k1,
                                                l1,
                                                k);
                                b3 = 0;
                                b4 = 0;
                                b0 = 0;
                                b1 = 0;
                                b2 = 0;
                                l1 = 0;
                                k1 = 0;
                                b5 = -1;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (b2 != 0) {
                                i2 +=
                                        method_d24d17aebc405b596335(
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b4),
                                                b5,
                                                k1,
                                                l1,
                                                k);
                                b3 = 0;
                                b4 = 0;
                                b0 = 0;
                                b1 = 0;
                                b2 = 0;
                                l1 = 0;
                                k1 = 0;
                                b5 = -1;
                            }

                            if (c0 == '=') {
                                b5 = 0;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 == '<') {
                                b5 = 2;
                                if (aint == null) {
                                    break label139;
                                }
                            }

                            if (c0 == '>') {
                                b5 = 1;
                            }
                        }

                        j2++;
                        if (aint != null) {
                            break;
                        }
                    }

                    if (b2 != 0) {
                        i2 +=
                                method_d24d17aebc405b596335(
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b3),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(b4),
                                        b5,
                                        k1,
                                        l1,
                                        k);
                    }

                    return i2;
                }
            }
        } else {
            return 0;
        }
    }

    public static String getPotionPrefix(int i) {
        int j = getPotionPrefixIndex(i);
        return net.minecraft.potion.PotionHelper.potionPrefixes[j];
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static boolean getAreAmbient(Collection collection) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        Iterator iterator = collection.iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            PotionEffect potionEffect = (PotionEffect) iterator.next();
            if (!potionEffect.getIsAmbient()) {
                return false;
            }

            if (aint != null) {
                break;
            }
        }

        return true;
    }

    private static int isFlagUnset(int i, int j) {
        return checkFlag(i, j) ? 0 : 1;
    }

    public static int getLiquidColor(int i, boolean flag) {
        if (!flag) {
            if (net.minecraft.potion.PotionHelper.DATAVALUE_COLORS.containsKey(i)) {
                return (Integer) net.minecraft.potion.PotionHelper.DATAVALUE_COLORS.get(i);
            }

            int j = calcPotionLiquidColor(getPotionEffects(i, false));
            net.minecraft.potion.PotionHelper.DATAVALUE_COLORS.put(i, j);
            return j;
        } else {
            return calcPotionLiquidColor(getPotionEffects(i, true));
        }
    }

    public static int getPotionPrefixIndex(int i) {
        return method_216cab78ca4939917c75(i, 5, 4, 3, 2, 1);
    }
}
