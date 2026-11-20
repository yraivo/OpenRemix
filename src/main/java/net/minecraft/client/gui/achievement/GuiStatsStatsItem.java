package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;

public class GuiStatsStatsItem extends GuiStatsStats {

    public GuiStats avr;

    protected void drawSlot(int i, int j, int k, int i1, int j1, int k1) {
        StatCrafting statCrafting = this.W(i);
        Item item = statCrafting.qT();
        GuiStats.access$1500(this.avr, j + 40, k, item);
        int l = Item.getIdFromItem(item);
        this.method_bc4804773aaae4095cc7(StatList.objectBreakStats[l], j + 115, k, i % 2 == 0);
        this.method_bc4804773aaae4095cc7(StatList.objectCraftStats[l], j + 165, k, i % 2 == 0);
        this.method_bc4804773aaae4095cc7(statCrafting, j + 215, k, i % 2 == 0);
    }

    protected void drawListHeader(int i, int j, Tessellator tessellator) {
        Entity[] entityArray;
        {
            Entity[] entityArrayValue = ResourceLocation.cB();
            super.drawListHeader(i, j, tessellator);
            entityArray = entityArrayValue;
            if (this.x == 0) {
                GuiStats.access$400(this.avr, i + 115 - 18 + 1, j + 1 + 1, 72, 18);

            } else {

                GuiStats.access$400(this.avr, i + 115 - 18, j + 1, 72, 18);
            }
        }

        {
            if (this.x == 1) {
                GuiStats.access$400(this.avr, i + 165 - 18 + 1, j + 1 + 1, 18, 18);

            } else {

                GuiStats.access$400(this.avr, i + 165 - 18, j + 1, 18, 18);
            }
        }

        if (this.x == 2) {
            GuiStats.access$400(this.avr, i + 215 - 18 + 1, j + 1 + 1, 36, 18);

            return;
        }

        GuiStats.access$400(this.avr, i + 215 - 18, j + 1, 36, 18);
    }


    protected String S(int i) {
        return i == 1 ? "stat.crafted" : (i == 2 ? "stat.used" : "stat.depleted");
    }

    public GuiStatsStatsItem(GuiStats avrValue, MinecraftClient minecraftClient) {
        super(avrValue, minecraftClient);
        ResourceLocation.cB();

        this.avr = avrValue;

        this.statsHolder = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_7221 = (StatList.itemStats).iterator();
                (__recoveryIterator171_7221).hasNext(); ) {
            StatCrafting statCrafting = (StatCrafting) (__recoveryIterator171_7221).next();
            byte b0;
            {
                b0 = 0;
                int idFromItem = Item.getIdFromItem(statCrafting.qT());
                if (GuiStats.access$100(avrValue).writeStat(statCrafting) > 0) {
                    b0 = 1;

                } else {

                    if (StatList.objectBreakStats[idFromItem] != null
                            && GuiStats.access$100(avrValue)
                                            .writeStat(StatList.objectBreakStats[idFromItem])
                                    > 0) {
                        b0 = 1;

                    } else {

                        if (StatList.objectCraftStats[idFromItem] != null
                                && GuiStats.access$100(avrValue)
                                                .writeStat(StatList.objectCraftStats[idFromItem])
                                        > 0) {
                            b0 = 1;
                        }
                    }
                }
            }

            if (b0 != 0) {
                this.statsHolder.add(statCrafting);
            }
        }

        this.statSorter = new StatSorterComparator(this, avrValue);
    }

    // Helper used only by this class.
    public static class StatSorterComparator implements java.util.Comparator {
        net.minecraft.client.gui.achievement.GuiStats avR;
        net.minecraft.client.gui.achievement.GuiStatsStatsItem avS;

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.compare(
                    (net.minecraft.stats.StatCrafting) object,
                    (net.minecraft.stats.StatCrafting) object1);
        }

        public int compare(
                net.minecraft.stats.StatCrafting statCrafting,
                net.minecraft.stats.StatCrafting statCraftingValue) {
            int idFromItem = net.minecraft.item.Item.getIdFromItem(statCrafting.qT());
            int j = net.minecraft.item.Item.getIdFromItem(statCraftingValue.qT());
            net.minecraft.stats.StatBase statBase = null;
            net.minecraft.stats.StatBase statBaseValue = null;
            if (this.avS.A == 0) {
                statBase = net.minecraft.stats.StatList.objectBreakStats[idFromItem];
                statBaseValue = net.minecraft.stats.StatList.objectBreakStats[j];
            } else if (this.avS.A == 1) {
                statBase = net.minecraft.stats.StatList.objectCraftStats[idFromItem];
                statBaseValue = net.minecraft.stats.StatList.objectCraftStats[j];
            } else if (this.avS.A == 2) {
                statBase = net.minecraft.stats.StatList.objectUseStats[idFromItem];
                statBaseValue = net.minecraft.stats.StatList.objectUseStats[j];
            }

            if (statBase != null || statBaseValue != null) {
                if (statBase == null) {
                    return 1;
                }

                if (statBaseValue == null) {
                    return -1;
                }

                int k =
                        net.minecraft.client.gui.achievement.GuiStats.access$100(this.avS.avr)
                                .writeStat(statBase);
                int l =
                        net.minecraft.client.gui.achievement.GuiStats.access$100(this.avS.avr)
                                .writeStat(statBaseValue);
                if (k != l) {
                    return (k - l) * this.avS.B;
                }
            }

            return idFromItem - j;
        }

        public StatSorterComparator(
                net.minecraft.client.gui.achievement.GuiStatsStatsItem avSValue,
                net.minecraft.client.gui.achievement.GuiStats avRValue) {
            this.avS = avSValue;
            this.avR = avRValue;
        }
    }
}
