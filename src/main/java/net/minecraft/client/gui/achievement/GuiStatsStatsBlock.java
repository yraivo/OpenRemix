package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;

public class GuiStatsStatsBlock extends GuiStatsStats {

    public GuiStats avr;


    public GuiStatsStatsBlock(GuiStats avrValue, MinecraftClient minecraftClient) {
        super(avrValue, minecraftClient);
        this.avr = avrValue;
        ResourceLocation.cB();

        this.statsHolder = Lists.newArrayList();
        Iterator iterator = StatList.objectMineStats.iterator();

        while (iterator.hasNext()) {
            StatCrafting statCrafting;
            byte b0;
            {
                statCrafting = (StatCrafting) iterator.next();
                b0 = 0;
                int idFromItem = Item.getIdFromItem(statCrafting.qT());
                if (GuiStats.access$100(avrValue).writeStat(statCrafting) > 0) {
                    b0 = 1;

                } else {

                    if (StatList.objectUseStats[idFromItem] != null
                            && GuiStats.access$100(avrValue)
                                            .writeStat(StatList.objectUseStats[idFromItem])
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

    protected void drawSlot(int i, int j, int k, int i1, int j1, int k1) {
        StatCrafting statCrafting = this.W(i);
        Item item = statCrafting.qT();
        GuiStats.access$1500(this.avr, j + 40, k, item);
        int l = Item.getIdFromItem(item);
        this.method_bc4804773aaae4095cc7(StatList.objectCraftStats[l], j + 115, k, i % 2 == 0);
        this.method_bc4804773aaae4095cc7(StatList.objectUseStats[l], j + 165, k, i % 2 == 0);
        this.method_bc4804773aaae4095cc7(statCrafting, j + 215, k, i % 2 == 0);
    }

    protected void drawListHeader(int i, int j, Tessellator tessellator) {
        Entity[] entityArray;
        {
            Entity[] entityArrayValue = ResourceLocation.cB();
            super.drawListHeader(i, j, tessellator);
            entityArray = entityArrayValue;
            if (this.x == 0) {
                GuiStats.access$400(this.avr, i + 115 - 18 + 1, j + 1 + 1, 18, 18);

            } else {

                GuiStats.access$400(this.avr, i + 115 - 18, j + 1, 18, 18);
            }
        }

        {
            if (this.x == 1) {
                GuiStats.access$400(this.avr, i + 165 - 18 + 1, j + 1 + 1, 36, 18);

            } else {

                GuiStats.access$400(this.avr, i + 165 - 18, j + 1, 36, 18);
            }
        }

        if (this.x == 2) {
            GuiStats.access$400(this.avr, i + 215 - 18 + 1, j + 1 + 1, 54, 18);

            return;
        }

        GuiStats.access$400(this.avr, i + 215 - 18, j + 1, 54, 18);
    }

    protected String S(int i) {
        return i == 0 ? "stat.crafted" : (i == 1 ? "stat.used" : "stat.mined");
    }

    // Helper used only by this class.
    public static class StatSorterComparator implements java.util.Comparator {
        net.minecraft.client.gui.achievement.GuiStatsStatsBlock avQ;
        net.minecraft.client.gui.achievement.GuiStats avR;

        public int compare(
                net.minecraft.stats.StatCrafting statCrafting,
                net.minecraft.stats.StatCrafting statCraftingValue) {
            int idFromItem = net.minecraft.item.Item.getIdFromItem(statCrafting.qT());
            int j = net.minecraft.item.Item.getIdFromItem(statCraftingValue.qT());
            net.minecraft.stats.StatBase statBase = null;
            net.minecraft.stats.StatBase statBaseValue = null;
            if (this.avQ.A == 2) {
                statBase = net.minecraft.stats.StatList.mineBlockStatArray[idFromItem];
                statBaseValue = net.minecraft.stats.StatList.mineBlockStatArray[j];
            } else if (this.avQ.A == 0) {
                statBase = net.minecraft.stats.StatList.objectCraftStats[idFromItem];
                statBaseValue = net.minecraft.stats.StatList.objectCraftStats[j];
            } else if (this.avQ.A == 1) {
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
                        net.minecraft.client.gui.achievement.GuiStats.access$100(this.avQ.avr)
                                .writeStat(statBase);
                int l =
                        net.minecraft.client.gui.achievement.GuiStats.access$100(this.avQ.avr)
                                .writeStat(statBaseValue);
                if (k != l) {
                    return (k - l) * this.avQ.B;
                }
            }

            return idFromItem - j;
        }

        public StatSorterComparator(
                net.minecraft.client.gui.achievement.GuiStatsStatsBlock avQValue,
                net.minecraft.client.gui.achievement.GuiStats avRValue) {
            this.avQ = avQValue;
            this.avR = avRValue;
        }

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.compare(
                    (net.minecraft.stats.StatCrafting) object,
                    (net.minecraft.stats.StatCrafting) object1);
        }
    }
}
