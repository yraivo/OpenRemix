package net.minecraft.entity;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataWatcher {
    private Entity owner;

    private Map watchedObjects;
    private static Map dataTypes;
    private static String[] packedTextFragments;
    private boolean isBlank = true;
    private boolean objectChanged;
    private ReadWriteLock lock;

    public short getWatchableObjectShort(int i) {
        return (Short) this.getWatchedObject(i).getObject();
    }

    private static void writeWatchableObjectToPacketBuffer(
            PacketBuffer packetBuffer, DataWatcherWatchableObject dataWatcherWatchableObject)
            throws IOException {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int i =
                (dataWatcherWatchableObject.getObjectType() << 5
                                | dataWatcherWatchableObject.getDataValueId() & 31)
                        & 0xFF;
        packetBuffer.writeByte(i);
        String[] astring = astring1;
        switch (dataWatcherWatchableObject.getObjectType()) {
            case 0:
                packetBuffer.writeByte((Byte) dataWatcherWatchableObject.getObject());
                if (astring == null) {
                    break;
                }
            case 1:
                packetBuffer.writeShort((Short) dataWatcherWatchableObject.getObject());
                if (astring == null) {
                    break;
                }
            case 2:
                packetBuffer.writeInt((Integer) dataWatcherWatchableObject.getObject());
                if (astring == null) {
                    break;
                }
            case 3:
                packetBuffer.writeFloat((Float) dataWatcherWatchableObject.getObject());
                if (astring == null) {
                    break;
                }
            case 4:
                packetBuffer.writeString((String) dataWatcherWatchableObject.getObject());
                if (astring == null) {
                    break;
                }
            case 5:
                ItemStack itemStack = (ItemStack) dataWatcherWatchableObject.getObject();
                packetBuffer.writeItemStackToBuffer(itemStack);
                if (astring == null) {
                    break;
                }
            case 6:
                BlockPos blockPos = (BlockPos) dataWatcherWatchableObject.getObject();
                packetBuffer.writeInt(blockPos.getX());
                packetBuffer.writeInt(blockPos.getY());
                packetBuffer.writeInt(blockPos.getZ());
                if (astring == null) {
                    break;
                }
            case 7:
                Rotations object = (Rotations) dataWatcherWatchableObject.getObject();
                packetBuffer.writeFloat(object.getX());
                packetBuffer.writeFloat(object.getY());
                packetBuffer.writeFloat(object.getZ());
        }
    }

    public List getChanged() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        ArrayList arrayList = null;
        String[] astring = astring1;
        if (this.objectChanged) {
            this.lock.readLock().lock();

            for (java.util.Iterator<?> __recoveryIterator171_4286 =
                            (this.watchedObjects.values()).iterator();
                    (__recoveryIterator171_4286).hasNext(); ) {
                DataWatcherWatchableObject dataWatcherWatchableObject =
                        (DataWatcherWatchableObject) (__recoveryIterator171_4286).next();
                if (dataWatcherWatchableObject.isWatched()) {
                    dataWatcherWatchableObject.setWatched(false);
                    if (arrayList == null) {
                        arrayList = Lists.newArrayList();
                    }

                    arrayList.add(dataWatcherWatchableObject);
                }

                if (astring != null) {
                    break;
                }
            }

            this.lock.readLock().unlock();
        }

        this.objectChanged = false;
        return arrayList;
    }

    public static void writeWatchedListToPacketBuffer(List list, PacketBuffer packetBuffer)
            throws IOException {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (list != null) {
            for (java.util.Iterator<?> __recoveryIterator171_5145 = (list).iterator();
                    (__recoveryIterator171_5145).hasNext(); ) {
                DataWatcherWatchableObject dataWatcherWatchableObject =
                        (DataWatcherWatchableObject) (__recoveryIterator171_5145).next();
                writeWatchableObjectToPacketBuffer(packetBuffer, dataWatcherWatchableObject);
                if (astring != null) {
                    break;
                }
            }
        }

        packetBuffer.writeByte(127);
    }

    public String getWatchableObjectString(int i) {
        return (String) this.getWatchedObject(i).getObject();
    }

    public boolean hasObjectChanged() {
        return this.objectChanged;
    }

    static {
        net.minecraft.entity.DataWatcher.packedTextFragments = new String[4];
        net.minecraft.entity.DataWatcher.packedTextFragments[0] = new String(")");
        net.minecraft.entity.DataWatcher.packedTextFragments[1] = new String("!");
        net.minecraft.entity.DataWatcher.dataTypes = Maps.newHashMap();
        net.minecraft.entity.DataWatcher.dataTypes.put(Byte.class, 0);
        net.minecraft.entity.DataWatcher.dataTypes.put(Short.class, 1);
        net.minecraft.entity.DataWatcher.dataTypes.put(Integer.class, 2);
        net.minecraft.entity.DataWatcher.dataTypes.put(Float.class, 3);
        net.minecraft.entity.DataWatcher.dataTypes.put(String.class, 4);
        net.minecraft.entity.DataWatcher.dataTypes.put(ItemStack.class, 5);
        net.minecraft.entity.DataWatcher.dataTypes.put(BlockPos.class, 6);
        net.minecraft.entity.DataWatcher.dataTypes.put(Rotations.class, 7);
    }

    public DataWatcher(Entity entity) {
        this.watchedObjects = Maps.newHashMap();
        this.lock = new ReentrantReadWriteLock();
        this.owner = entity;
    }

    public void func_111144_e() {
        this.objectChanged = false;
    }

    public float getWatchableObjectFloat(int i) {
        return (Float) this.getWatchedObject(i).getObject();
    }

    public List getAllWatched() {
        ArrayList arrayList = null;
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.lock.readLock().lock();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_11221 =
                        (this.watchedObjects.values()).iterator();
                (__recoveryIterator171_11221).hasNext(); ) {
            DataWatcherWatchableObject dataWatcherWatchableObject =
                    (DataWatcherWatchableObject) (__recoveryIterator171_11221).next();
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }

            arrayList.add(dataWatcherWatchableObject);
            if (astring != null) {
                break;
            }
        }

        this.lock.readLock().unlock();
        return arrayList;
    }

    public boolean getIsBlank() {
        return this.isBlank;
    }

    public void setObjectWatched(int i) {
        DataWatcherWatchableObject.access$002(this.getWatchedObject(i), true);
        this.objectChanged = true;
    }

    public byte getWatchableObjectByte(int i) {
        return (Byte) this.getWatchedObject(i).getObject();
    }

    public void addObject(int i, Object object) {
        Integer integer = (Integer) net.minecraft.entity.DataWatcher.dataTypes.get(object.getClass());
        if (integer == null) {
            throw new IllegalArgumentException("Unknown data type: " + object.getClass());
        }

        if (i > 31) {
            throw new IllegalArgumentException(
                    "Data value id is too big with "
                            + i
                            + "! (Max is "
                            + 31
                            + packedTextFragments[0]);
        }

        if (this.watchedObjects.containsKey(i)) {
            throw new IllegalArgumentException(
                    "Duplicate id value for " + i + packedTextFragments[1]);
        }

        DataWatcherWatchableObject dataWatcherWatchableObject =
                new DataWatcherWatchableObject(integer, i, object);
        this.lock.writeLock().lock();
        this.watchedObjects.put(i, dataWatcherWatchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    public static List readWatchedListFromPacketBuffer(PacketBuffer packetBuffer)
            throws IOException {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        ArrayList arrayList = null;
        String[] astring = astring1;
        byte b0 = packetBuffer.readByte();

        while (b0 != 127) {
            if (arrayList == null) {
                arrayList = Lists.newArrayList();
            }

            int i = (b0 & 224) >> 5;
            int j = b0 & 31;
            DataWatcherWatchableObject dataWatcherWatchableObject = null;
            switch (i) {
                case 0:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, packetBuffer.readByte());
                    break;
                case 1:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, packetBuffer.readShort());
                    break;
                case 2:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, packetBuffer.readInt());
                    break;
                case 3:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, packetBuffer.readFloat());
                    break;
                case 4:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(
                                    i, j, packetBuffer.readStringFromBuffer(32767));
                    break;
                case 5:
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(
                                    i, j, packetBuffer.readItemStackFromBuffer());
                    break;
                case 6:
                    int k = packetBuffer.readInt();
                    int l = packetBuffer.readInt();
                    int i1 = packetBuffer.readInt();
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, new BlockPos(k, l, i1));
                    break;
                case 7:
                    float f = packetBuffer.readFloat();
                    float f1 = packetBuffer.readFloat();
                    float f2 = packetBuffer.readFloat();
                    dataWatcherWatchableObject =
                            new DataWatcherWatchableObject(i, j, new Rotations(f, f1, f2));
            }

            arrayList.add(dataWatcherWatchableObject);
            b0 = packetBuffer.readByte();
            if (astring != null) {
                break;
            }
        }

        return arrayList;
    }

    public void updateObject(int i, Object object) {
        DataWatcherWatchableObject watchedObject = this.getWatchedObject(i);
        if (ObjectUtils.notEqual(object, watchedObject.getObject())) {
            watchedObject.setObject(object);
            this.owner.onDataWatcherUpdate(i);
            watchedObject.setWatched(true);
            this.objectChanged = true;
        }
    }

    public int getWatchableObjectInt(int i) {
        return (Integer) this.getWatchedObject(i).getObject();
    }

    public void addObjectByDataType(int i, int j) {
        DataWatcherWatchableObject dataWatcherWatchableObject =
                new DataWatcherWatchableObject(j, i, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(i, dataWatcherWatchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    public ItemStack getWatchableObjectItemStack(int i) {
        return (ItemStack) this.getWatchedObject(i).getObject();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private DataWatcherWatchableObject getWatchedObject(int i) {
        this.lock.readLock().lock();

        DataWatcherWatchableObject dataWatcherWatchableObject;
        try {
            dataWatcherWatchableObject = (DataWatcherWatchableObject) this.watchedObjects.get(i);
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Getting synched entity data");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Synched entity data");
            crashReportCategory.addCrashSection("Data ID", i);
            throw new ReportedException(crashReport);
        }

        this.lock.readLock().unlock();
        return dataWatcherWatchableObject;
    }

    public void writeTo(PacketBuffer packetBuffer) throws IOException {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.lock.readLock().lock();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_19866 =
                        (this.watchedObjects.values()).iterator();
                (__recoveryIterator171_19866).hasNext(); ) {
            DataWatcherWatchableObject dataWatcherWatchableObject =
                    (DataWatcherWatchableObject) (__recoveryIterator171_19866).next();
            writeWatchableObjectToPacketBuffer(packetBuffer, dataWatcherWatchableObject);
            if (astring != null) {
                break;
            }
        }

        this.lock.readLock().unlock();
        packetBuffer.writeByte(127);
    }

    public Rotations getWatchableObjectRotations(int i) {
        return (Rotations) this.getWatchedObject(i).getObject();
    }

    public void updateWatchedObjectsFromList(List list) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.lock.writeLock().lock();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_41651 = (list).iterator();
                (__recoveryIterator171_41651).hasNext(); ) {
            DataWatcherWatchableObject dataWatcherWatchableObject =
                    (DataWatcherWatchableObject) (__recoveryIterator171_41651).next();
            DataWatcherWatchableObject dataWatcherWatchableObjectValue =
                    (DataWatcherWatchableObject)
                            this.watchedObjects.get(dataWatcherWatchableObject.getDataValueId());
            if (dataWatcherWatchableObjectValue != null) {
                dataWatcherWatchableObjectValue.setObject(dataWatcherWatchableObject.getObject());
                this.owner.onDataWatcherUpdate(dataWatcherWatchableObject.getDataValueId());
            }

            if (astring != null) {
                break;
            }
        }

        this.lock.writeLock().unlock();
        this.objectChanged = true;
    }
}
