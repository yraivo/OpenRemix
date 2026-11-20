package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom {
    public static WeightedRandomItem getRandomItem(Random random, Collection collection, int i) {
        try {
            if (i <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        int j = random.nextInt(i);
        return getRandomItem(collection, j);
    }

    public static WeightedRandomItem getRandomItem(Random random, Collection collection) {
        return getRandomItem(random, collection, getTotalWeight(collection));
    }

    public static int getTotalWeight(Collection collection) {
        int i = 0;
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = collection.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            WeightedRandomItem weightedRandomItem = (WeightedRandomItem) iterator.next();
            i += weightedRandomItem.itemWeight;
            if (textValue == null) {
                break;
            }
        }

        return i;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public static WeightedRandomItem getRandomItem(Collection collection, int i) {
        for (java.util.Iterator<?> __recoveryIterator171_1775 = (collection).iterator();
                (__recoveryIterator171_1775).hasNext(); ) {
            WeightedRandomItem weightedRandomItem =
                    (WeightedRandomItem) (__recoveryIterator171_1775).next();
            i -= weightedRandomItem.itemWeight;
            if (i < 0) {
                return weightedRandomItem;
            }
        }

        return null;
    }
}
