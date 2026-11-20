package net.minecraft.client.renderer.chunk;

import net.minecraft.util.Facing;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;

public class SetVisibility {
    private static int COUNT_FACES;
    private BitSet bitSet;

    private static String c;

    public void setVisible(Facing facing, Facing facingValue, boolean flag) {
        this.bitSet.set(
                facing.ordinal() + facingValue.ordinal() * net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES, flag);
        this.bitSet.set(
                facingValue.ordinal() + facing.ordinal() * net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES, flag);
    }

    public void setManyVisible(Set set) {
        boolean flag1 = RenderChunk.usesNormalChunkUpdateFlow();
        Iterator iterator = set.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Facing facing = (Facing) iterator.next();
            Iterator iteratorValue = set.iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    Facing facingValue = (Facing) iteratorValue.next();
                    this.setVisible(facing, facingValue, true);
                    if (flag) {
                        continue;
                    }
                }

                if (!flag) {
                    return;
                }
                break;
            }
        }
    }

    public boolean isVisible(Facing facing, Facing facingValue) {
        return this.bitSet.get(
                facing.ordinal() + facingValue.ordinal() * net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES);
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag2 = RenderChunk.usesNormalChunkUpdateFlow();
        stringbuilder.append(' ');
        Facing[] facingArray = Facing.values();
        int length = facingArray.length;
        boolean flag = flag2;
        int j = 0;

        while (j < length) {
            Facing facing = facingArray[j];
            stringbuilder.append(' ').append(facing.toString().toUpperCase().charAt(0));
            j++;
            if (!flag) {
                break;
            }
        }

        stringbuilder.append('\n');

        for (Facing facingValue : Facing.values()) {
            stringbuilder.append(facingValue.toString().toUpperCase().charAt(0));
            Facing[] facingArrayValue = Facing.values();
            int k = facingArrayValue.length;
            int l = 0;

            while (true) {
                if (l < k) {
                    {
                        Facing facingValue2 = facingArrayValue[l];
                        if (facingValue == facingValue2) {
                            stringbuilder.append(c);
                            if (!(flag)) {

                                boolean visible = this.isVisible(facingValue, facingValue2);
                                stringbuilder.append(' ').append((char) (visible ? 'Y' : 'n'));
                            }
                        } else {

                            boolean visible = this.isVisible(facingValue, facingValue2);
                            stringbuilder.append(' ').append((char) (visible ? 'Y' : 'n'));
                        }
                    }

                    l++;
                    if (flag) {
                        continue;
                    }
                }

                stringbuilder.append('\n');
                if (!flag) {
                    return stringbuilder.toString();
                }
                break;
            }
        }

        return stringbuilder.toString();
    }

    // region String decoding support

    // endregion

    public SetVisibility() {
        this.bitSet = new BitSet(net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES * net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES);
    }

    // region String decoding support

    // endregion

    public void setAllVisible(boolean flag) {
        this.bitSet.set(0, this.bitSet.size(), flag);
    }

    static {
        c = "  ";
        net.minecraft.client.renderer.chunk.SetVisibility.COUNT_FACES = Facing.values().length;
    }
}
