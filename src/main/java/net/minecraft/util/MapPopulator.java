package net.minecraft.util;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPopulator {
    public static Map populateMap(Iterable iterable, Iterable iterable1, Map map) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = iterable1.iterator();
        String textValue = text;

        for (Object object : iterable) {
            map.put(object, iterator.next());
            if (textValue == null) {
                break;
            }
        }

        try {
            if (iterator.hasNext()) {
                throw new NoSuchElementException();
            } else {
                return map;
            }
        } catch (NoSuchElementException nosuchelementexception) {
            throw method_764f1cb4620faac2b457(nosuchelementexception);
        }
    }

    private static NoSuchElementException method_764f1cb4620faac2b457(
            NoSuchElementException nosuchelementexception) {
        return nosuchelementexception;
    }

    public static Map createMap(Iterable iterable, Iterable iterable1) {
        return populateMap(iterable, iterable1, Maps.newLinkedHashMap());
    }
}
