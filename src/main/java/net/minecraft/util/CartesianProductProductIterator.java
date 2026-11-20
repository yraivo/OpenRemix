package net.minecraft.util;

import com.google.common.collect.UnmodifiableIterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CartesianProductProductIterator extends UnmodifiableIterator {
    private int index;
    private Object[] results;
    private Iterable[] iterables;
    private Iterator[] iterators;

    public boolean hasNext() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (this.index == -2) {
            this.index = 0;

            for (Iterator iteratorValue : this.iterators) {
                if (!iteratorValue.hasNext()) {
                    this.endOfData();
                    if (text != null) {
                        break;
                    }
                }

                if (text == null) {
                    break;
                }
            }

            return true;
        } else {
            if (this.index >= this.iterators.length) {
                this.index = this.iterators.length - 1;

                while (this.index >= 0) {
                    Iterator iterator = this.iterators[this.index];
                    if (iterator.hasNext() && text != null) {
                        break;
                    }

                    if (this.index == 0) {
                        this.endOfData();
                        if (text != null) {
                            break;
                        }
                    }

                    iterator = this.iterables[this.index].iterator();
                    this.iterators[this.index] = iterator;
                    if (!iterator.hasNext()) {
                        this.endOfData();
                        if (text != null) {
                            break;
                        }
                    }

                    this.index--;
                    if (text == null) {
                        break;
                    }
                }
            }

            return this.index >= 0;
        }
    }

    public Object next() {
        return this.next0();
    }

    private static NoSuchElementException method_764f1cb4620faac2b457(
            NoSuchElementException nosuchelementexception) {
        return nosuchelementexception;
    }

    public Object[] next0() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        while (this.index < this.iterators.length) {
            this.results[this.index] = this.iterators[this.index].next();
            this.index++;
            if (text == null) {
                break;
            }
        }

        return (Object[]) this.results.clone();
    }

    public CartesianProductProductIterator(Class oclass, Iterable[] aiterable, Object object) {
        this(oclass, aiterable);
    }

    private CartesianProductProductIterator(Class oclass, Iterable[] aiterable) {
        this(oclass, aiterable, Vec3i.getLegacyCoordinateFlowMarker());
    }

    private CartesianProductProductIterator(
            Class oclass, Iterable[] aiterable, String initialFlowMarker) {
        super();

        String text = initialFlowMarker;

        this.index = -2;
        this.iterables = aiterable;
        String textValue = text;
        this.iterators =
                (Iterator[])
                        Cartesian.method_13a08cbfc27389957edf(
                                Iterator.class, this.iterables.length);
        int i = 0;

        while (i < this.iterables.length) {
            this.iterators[i] = aiterable[i].iterator();
            i++;
            if (textValue == null) {
                break;
            }
        }

        this.results = Cartesian.method_13a08cbfc27389957edf(oclass, this.iterators.length);
    }

    private void endOfData() {
        this.index = -1;
        Arrays.fill(this.iterators, null);
        Arrays.fill(this.results, null);
    }
}
