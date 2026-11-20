package remix.event.discovery;

import net.minecraft.entity.Entity;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;

public final class MethodDiscoveryStrategy implements remix.event.discovery.MemberDiscoveryStrategy {
    private final java.util.Map methodCache = new WeakHashMap<>();
    private static String b;
    private static String[] packedTextFragments;

    @Override
    public void collectMembers(Class oclass, java.util.List list) {
        String text = method_c60693f457aa70be7160();
        if (oclass != null) {
            int i = list.size();
            Method[] amethod = oclass.getDeclaredMethods();
            int j = 0;
            int k = amethod.length;

            while (j < k) {
                Method method = amethod[j];
                int l = 0;

                while (true) {
                    if (l < i) {
                        Method method1 = (Method) list.get(l);
                        if (this.isOverriddenMethod(method1, method) && text != null) {
                            break;
                        }

                        l++;
                        if (text != null) {
                            continue;
                        }
                    }

                    list.add(method);
                    break;
                }

                j++;
                if (text == null) {
                    break;
                }
            }

            this.collectMembers(oclass.getSuperclass(), list);
            Class[] aclass = oclass.getInterfaces();
            k = 0;
            int length = aclass.length;

            while (k < length) {
                Class oclass1 = aclass[k];
                this.collectMembers(oclass1, list);
                k++;
                if (text == null) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public Method[] discoverMethods(Class oclass) {
        method_c60693f457aa70be7160();
        Reference reference = ((java.lang.ref.Reference) this.methodCache.get(oclass));
        if (reference != null) {
            Method[] amethod = (Method[]) reference.get();
            if (amethod != null) {
                return (Method[]) amethod.clone();
            }
        }

        ArrayList arrayList = new ArrayList();
        this.collectMembers(oclass, arrayList);
        Method[] amethod1 =
                ((java.lang.reflect.Method[]) arrayList.toArray(new Method[arrayList.size()]));
        this.methodCache.put(oclass, new SoftReference<>(amethod1));
        Method[] amethod2 = (Method[]) amethod1.clone();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }

        return amethod2;
    }

    // region String decoding support

    // endregion

    @Override
    public AnnotatedElement[] discoverMembers(Class oclass) {
        return this.discoverMethods(oclass);
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() == null) {
            b(packedTextFragments[1]);
        }
    }

    public static String method_c60693f457aa70be7160() {
        return b;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.event.discovery.MethodDiscoveryStrategy.packedTextFragments[0] = new String("ofNk");
        remix.event.discovery.MethodDiscoveryStrategy.packedTextFragments[1] = new String("ZJrYsb");
    }

    // endregion

    public boolean isOverriddenMethod(Method method, Method method1) {
        return !Modifier.isPrivate(method.getModifiers())
                && method.getName().equals(method1.getName())
                && method.getReturnType().isAssignableFrom(method1.getReturnType())
                && Arrays.equals(method.getParameterTypes(), method1.getParameterTypes());
    }

    @Override
    public boolean isDuplicateMember(
            AnnotatedElement annotatedelement, AnnotatedElement annotatedelement1) {
        return this.isOverriddenMethod(
                (Method) annotatedelement, (Method) annotatedelement1);
    }

    public static void b(String text) {
        b = text;
    }
}
