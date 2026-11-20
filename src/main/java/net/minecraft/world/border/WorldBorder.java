package net.minecraft.world.border;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.ChunkCoordIntPair;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class WorldBorder {
    private long startTime;
    private double centerZ;
    private long endTime;
    private double damageAmount;
    private int warningDistance;
    private double centerX;
    private final List listeners = Lists.newArrayList();
    private int warningTime;
    private int worldSize;
    private double startDiameter;
    private static String[] ku;
    private double endDiameter;
    private double damageBuffer;

    public int getWarningTime() {
        return this.warningTime;
    }

    public void setDamageBuffer(double damageBufferValue) {
        Fr();

        this.damageBuffer = damageBufferValue;

        for (java.util.Iterator<?> __recoveryIterator171_1116 = (this.getListeners()).iterator();
                (__recoveryIterator171_1116).hasNext(); ) {
            IBorderListener iBorderListener = (IBorderListener) (__recoveryIterator171_1116).next();
            iBorderListener.onDamageBufferChanged(this, damageBufferValue);
        }
    }

    public EnumBorderStatus getStatus() {
        return this.endDiameter < this.startDiameter
                ? EnumBorderStatus.SHRINKING
                : (this.endDiameter > this.startDiameter
                        ? EnumBorderStatus.GROWING
                        : EnumBorderStatus.STATIONARY);
    }

    public double getTargetSize() {
        return this.endDiameter;
    }

    public static void b(String[] astring) {
        ku = astring;
    }

    public double minZ() {
        double d0 = this.getCenterZ() - this.getDiameter() / 2.0;
        if (d0 < -this.worldSize) {
            d0 = -this.worldSize;
        }

        return d0;
    }

    public double maxZ() {
        double d0 = this.getCenterZ() + this.getDiameter() / 2.0;
        if (d0 > this.worldSize) {
            d0 = this.worldSize;
        }

        return d0;
    }

    public long getTimeUntilTarget() {
        long i;
        if (this.getStatus() != EnumBorderStatus.STATIONARY) {
            i = this.endTime - System.currentTimeMillis();
        } else {

            i = 0L;
        }

        return i;
    }

    public double getDamageBuffer() {
        return this.damageBuffer;
    }

    public void setWarningTime(int warningTimeValue) {
        Fr();

        this.warningTime = warningTimeValue;

        for (java.util.Iterator<?> __recoveryIterator171_2757 = (this.getListeners()).iterator();
                (__recoveryIterator171_2757).hasNext(); ) {
            IBorderListener iBorderListener = (IBorderListener) (__recoveryIterator171_2757).next();
            iBorderListener.onWarningTimeChanged(this, warningTimeValue);
        }
    }

    public boolean contains(BlockPos blockPos) {
        return blockPos.getX() + 1 > this.minX()
                && blockPos.getX() < this.maxX()
                && blockPos.getZ() + 1 > this.minZ()
                && blockPos.getZ() < this.maxZ();
    }

    public int getSize() {
        return this.worldSize;
    }

    public double minX() {
        double d0 = this.getCenterX() - this.getDiameter() / 2.0;
        if (d0 < -this.worldSize) {
            d0 = -this.worldSize;
        }

        return d0;
    }

    public double getClosestDistance(double d0, double d1) {
        Fr();
        double d2 = d1 - this.minZ();
        double d3 = this.maxZ() - d1;
        double d4 = d0 - this.minX();
        double d5 = this.maxX() - d0;
        double d6 = Math.min(d4, d5);
        d6 = Math.min(d6, d2);
        double d7 = Math.min(d6, d3);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new String[5]);
        }

        return d7;
    }

    public double maxX() {
        double d0 = this.getCenterX() + this.getDiameter() / 2.0;
        if (d0 > this.worldSize) {
            d0 = this.worldSize;
        }

        return d0;
    }

    public void setTransition(double endDiameterValue) {
        this.startDiameter = endDiameterValue;
        Fr();

        this.endDiameter = endDiameterValue;
        this.endTime = System.currentTimeMillis();
        this.startTime = this.endTime;
        Iterator iterator = this.getListeners().iterator();

        while (iterator.hasNext()) {
            IBorderListener iBorderListener = (IBorderListener) iterator.next();
            iBorderListener.onSizeChanged(this, endDiameterValue);
        }
    }

    public void setSize(int worldSizeValue) {
        this.worldSize = worldSizeValue;
    }

    public double getResizeSpeed() {
        return this.endTime == this.startTime
                ? 0.0
                : java.lang.Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime);
    }

    static {
        if (Fr() == null) {
            b(new String[2]);
        }
    }

    public void addListener(IBorderListener iBorderListener) {
        this.listeners.add(iBorderListener);
    }

    public void setCenter(double centerXValue, double centerZValue) {
        Fr();

        this.centerX = centerXValue;
        this.centerZ = centerZValue;

        for (java.util.Iterator<?> __recoveryIterator171_5678 = (this.getListeners()).iterator();
                (__recoveryIterator171_5678).hasNext(); ) {
            IBorderListener iBorderListener = (IBorderListener) (__recoveryIterator171_5678).next();
            iBorderListener.onCenterChanged(this, centerXValue, centerZValue);
        }
    }

    public double getCenterZ() {
        return this.centerZ;
    }

    public void setDamageAmount(double damageAmountValue) {
        Fr();

        this.damageAmount = damageAmountValue;

        for (java.util.Iterator<?> __recoveryIterator171_6190 = (this.getListeners()).iterator();
                (__recoveryIterator171_6190).hasNext(); ) {
            IBorderListener iBorderListener = (IBorderListener) (__recoveryIterator171_6190).next();
            iBorderListener.onDamageAmountChanged(this, damageAmountValue);
        }
    }

    public double getClosestDistance(Entity entity) {
        return this.getClosestDistance(entity.posX, entity.posZ);
    }

    public WorldBorder() {
        this.centerX = 0.0;
        this.centerZ = 0.0;
        Fr();

        this.startDiameter = 6.0E7;
        this.endDiameter = this.startDiameter;
        this.worldSize = 29999984;
        this.damageAmount = 0.2;
        this.damageBuffer = 5.0;
        this.warningTime = 15;
        this.warningDistance = 5;
    }

    public double getDiameter() {
        if (this.getStatus() != EnumBorderStatus.STATIONARY) {
            double d0 =
                    (float) (System.currentTimeMillis() - this.startTime) / (float) (this.endTime - this.startTime);
            if (d0 < 1.0) {
                return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
            }

            this.setTransition(this.endDiameter);
        }

        return this.startDiameter;
    }

    public void setTransition(
            double startDiameterValue, double endDiameterValue, long i) {
        Fr();

        this.startDiameter = startDiameterValue;
        this.endDiameter = endDiameterValue;
        this.startTime = System.currentTimeMillis();
        this.endTime = this.startTime + i;
        Iterator iterator = this.getListeners().iterator();

        while (iterator.hasNext()) {
            IBorderListener iBorderListener = (IBorderListener) iterator.next();
            iBorderListener.onTransitionStarted(this, startDiameterValue, endDiameterValue, i);
        }
    }

    public boolean contains(BoundingBox boundingBox) {
        return boundingBox.maxX > this.minX()
                && boundingBox.minX < this.maxX()
                && boundingBox.maxZ > this.minZ()
                && boundingBox.minZ < this.maxZ();
    }

    public static String[] Fr() {
        return ku;
    }

    public boolean contains(ChunkCoordIntPair chunkCoordIntPair) {
        return chunkCoordIntPair.getXEnd() > this.minX()
                && chunkCoordIntPair.getXStart() < this.maxX()
                && chunkCoordIntPair.getZEnd() > this.minZ()
                && chunkCoordIntPair.getZStart() < this.maxZ();
    }

    protected List getListeners() {
        return Lists.newArrayList(this.listeners);
    }

    public double getCenterX() {
        return this.centerX;
    }

    public int getWarningDistance() {
        return this.warningDistance;
    }

    public double getDamageAmount() {
        return this.damageAmount;
    }

    public void setWarningDistance(int warningDistanceValue) {
        net.minecraft.world.border.WorldBorder.Fr();

        this.warningDistance = warningDistanceValue;
        java.util.Iterator iterator = this.getListeners().iterator();

        while (iterator.hasNext()) {
            net.minecraft.world.border.IBorderListener iBorderListener = (net.minecraft.world.border.IBorderListener) iterator.next();
            iBorderListener.onWarningDistanceChanged(this, warningDistanceValue);
        }
    }
}
