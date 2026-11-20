package remix.event.discovery;

import net.minecraft.entity.Entity;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

public final class FieldDiscoveryStrategy implements remix.event.discovery.MemberDiscoveryStrategy {
    private final java.util.Map fieldCache = new WeakHashMap<>();

    public Field[] method_b5f016aa4b1897aac4be(Class oclass) {
        String text = MethodDiscoveryStrategy.method_c60693f457aa70be7160();
        Reference reference = ((java.lang.ref.Reference) this.fieldCache.get(oclass));
        String textValue = text;
        if (reference != null) {
            Field[] afield = (Field[]) reference.get();
            if (afield != null) {
                return (Field[]) afield.clone();
            }
        }

        ArrayList arrayList = new ArrayList();
        this.collectMembers(oclass, arrayList);
        Field[] afield1 =
                ((java.lang.reflect.Field[]) arrayList.toArray(new Field[arrayList.size()]));
        this.fieldCache.put(oclass, new SoftReference<>(afield1));
        Field[] afield2 = (Field[]) afield1.clone();
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return afield2;
    }

    @Override
    public boolean isDuplicateMember(
            AnnotatedElement annotatedElement, AnnotatedElement annotatedElementValue) {
        return this.isSameField(
                (java.lang.reflect.Field) annotatedElement,
                (java.lang.reflect.Field) annotatedElementValue);
    }

    @Override
    public void collectMembers(Class oclass, java.util.List list) {
        String text = MethodDiscoveryStrategy.method_c60693f457aa70be7160();
        if (oclass != null) {
            Field[] afield = oclass.getDeclaredFields();
            int i = 0;
            int j = afield.length;

            while (i < j) {
                Field field = afield[i];
                Iterator iterator = list.iterator();

                while (true) {
                    if (iterator.hasNext()) {
                        Field field1 = (Field) iterator.next();
                        if (this.isSameField(field1, field) && text != null) {
                            break;
                        }

                        if (text != null) {
                            continue;
                        }
                    }

                    list.add(field);
                    break;
                }

                i++;
                if (text == null) {
                    break;
                }
            }

            this.collectMembers(oclass.getSuperclass(), list);
            Class[] aclass = oclass.getInterfaces();
            j = 0;
            int k = aclass.length;

            while (j < k) {
                Class oclass1 = aclass[j];
                this.collectMembers(oclass1, list);
                j++;
                if (text == null) {
                    break;
                }
            }
        }
    }

    public boolean isSameField(Field field, Field fieldValue) {
        return !java.lang.reflect.Modifier.isPrivate(field.getModifiers())
                && field.getName().equals(fieldValue.getName());
    }

    @Override
    public AnnotatedElement[] discoverMembers(Class oclass) {
        return this.method_b5f016aa4b1897aac4be(oclass);
    }
}
