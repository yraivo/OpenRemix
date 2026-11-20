package net.minecraft.entity;

public class DataWatcherWatchableObject {
    private Object watchedObject;
    private int objectType;
    private boolean watched;
    private int dataValueId;

    public DataWatcherWatchableObject(int objectTypeValue, int j, Object aZpValue) {
        this.dataValueId = j;
        this.watchedObject = aZpValue;
        this.objectType = objectTypeValue;
        this.watched = true;
    }

    public boolean isWatched() {
        return this.watched;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public void setObject(Object aZpValue) {
        this.watchedObject = aZpValue;
    }

    public int getDataValueId() {
        return this.dataValueId;
    }

    public Object getObject() {
        return this.watchedObject;
    }

    public void setWatched(boolean watchedValue) {
        this.watched = watchedValue;
    }

    static boolean access$002(
            DataWatcherWatchableObject dataWatcherWatchableObject, boolean watchedValue) {
        return dataWatcherWatchableObject.watched = watchedValue;
    }
}
