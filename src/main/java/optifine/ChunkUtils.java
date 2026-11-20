package optifine;

import net.minecraft.world.chunk.Chunk;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ChunkUtils {
    private static String[] packedTextFragments;

    private static Field fieldHasEntities;
    private static boolean fieldHasEntitiesMissing;

    static {
        optifine.ChunkUtils.packedTextFragments = new String[3];
        optifine.ChunkUtils.packedTextFragments[0] = new String(" ");
        optifine.ChunkUtils.packedTextFragments[1] = new String(" ");
        optifine.ChunkUtils.fieldHasEntities = null;
        optifine.ChunkUtils.fieldHasEntitiesMissing = false;
    }

    private static Field findFieldHasEntities(Chunk chunk) {
        int i = ReflectorMethod.cR();

        try {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayListValue = new ArrayList();
            Field[] afield = Chunk.class.getDeclaredFields();
            int j = 0;

            while (j < afield.length) {
                Field field = afield[j];
                if (field.getType() == boolean.class) {
                    field.setAccessible(true);
                    arrayList.add(field);
                    arrayListValue.add(field.get(chunk));
                }

                j++;
                if (i == 0) {
                    break;
                }
            }

            chunk.setHasEntities(false);
            ArrayList arrayListValue2 = new ArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_25835 = (arrayList).iterator();
                    (__recoveryIterator171_25835).hasNext(); ) {
                Field field1 = (Field) (__recoveryIterator171_25835).next();
                arrayListValue2.add(field1.get(chunk));
                if (i == 0) {
                    break;
                }
            }

            chunk.setHasEntities(true);
            ArrayList arrayListValue3 = new ArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_26150 = (arrayList).iterator();
                    (__recoveryIterator171_26150).hasNext(); ) {
                Field field2 = (Field) (__recoveryIterator171_26150).next();
                arrayListValue3.add(field2.get(chunk));
                if (i == 0) {
                    break;
                }
            }

            ArrayList arrayListValue4 = new ArrayList();
            int k = 0;

            while (k < arrayList.size()) {
                Field field3 = (Field) arrayList.get(k);
                Boolean obool = (Boolean) arrayListValue2.get(k);
                Boolean obool1 = (Boolean) arrayListValue3.get(k);
                if (!obool && obool1) {
                    arrayListValue4.add(field3);
                    Boolean obool2 = (Boolean) arrayListValue.get(k);
                    field3.set(chunk, obool2);
                }

                k++;
                if (i == 0) {
                    break;
                }
            }

            if (arrayListValue4.size() == 1) {
                return (Field) arrayListValue4.get(0);
            }
        } catch (Exception exception) {
            OptiFineConfig.warn(
                    exception.getClass().getName()
                            + packedTextFragments[1]
                            + exception.getMessage());
        }

        OptiFineConfig.warn("Error finding Chunk.hasEntities");
        return null;
    }

    public static boolean hasEntities(Chunk chunk) {
        if (optifine.ChunkUtils.fieldHasEntities == null) {
            if (optifine.ChunkUtils.fieldHasEntitiesMissing) {
                return true;
            }

            optifine.ChunkUtils.fieldHasEntities = findFieldHasEntities(chunk);
            if (optifine.ChunkUtils.fieldHasEntities == null) {
                optifine.ChunkUtils.fieldHasEntitiesMissing = true;
                return true;
            }
        }

        try {
            return optifine.ChunkUtils.fieldHasEntities.getBoolean(chunk);
        } catch (Exception exception) {
            OptiFineConfig.warn("Error calling Chunk.hasEntities");
            OptiFineConfig.warn(
                    exception.getClass().getName()
                            + packedTextFragments[0]
                            + exception.getMessage());
            optifine.ChunkUtils.fieldHasEntitiesMissing = true;
            return true;
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
