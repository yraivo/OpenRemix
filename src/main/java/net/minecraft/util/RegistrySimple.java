package net.minecraft.util;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RegistrySimple implements IRegistry {

    protected final Map registryObjects = this.createUnderlyingMap();

    private static Logger logger;

    public boolean containsKey(Object object) {
        return this.registryObjects.containsKey(object);
    }

    public Set getKeys() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }

    static {
        net.minecraft.util.RegistrySimple.logger = LogManager.getLogger();
    }

    public void putObject(Object object, Object object1) {
        Validate.notNull(object);
        Validate.notNull(object1);
        if (this.registryObjects.containsKey(object)) {
            net.minecraft.util.RegistrySimple.logger.debug("Adding duplicate key '" + object + "' to registry");
        }

        this.registryObjects.put(object, object1);
    }

    protected Map createUnderlyingMap() {
        return Maps.newHashMap();
    }

    public Object getObject(Object object) {
        return this.registryObjects.get(object);
    }

    public Iterator iterator() {
        return this.registryObjects.values().iterator();
    }
}
