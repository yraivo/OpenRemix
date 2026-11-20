package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class BlockStateStateImplemenation extends BlockStateBase {

    private ImmutableMap properties;
    private ImmutableTable propertyValueTable;

    private Block block;

    public ImmutableMap getProperties() {
        return this.properties;
    }

    private Map getPropertiesWithValue(IProperty iProperty, Comparable comparable) {
        HashMap hashmap = Maps.newHashMap(this.properties);
        hashmap.put(iProperty, comparable);
        return hashmap;
    }

    public void buildPropertyValueTable(Map map) {
        int[] aint = BlockStateContainer.xZ();

        try {
            if (this.propertyValueTable != null) {
                throw new IllegalStateException();
            }
        } catch (IllegalArgumentException illegalargumentexception1) {
            throw method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        HashBasedTable hashbasedtable = HashBasedTable.create();
        UnmodifiableIterator unmodifiableiterator = this.properties.keySet().iterator();

        label37:
        while (unmodifiableiterator.hasNext()) {
            IProperty iProperty = (IProperty) unmodifiableiterator.next();
            Iterator iterator = iProperty.getAllowedValues().iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Comparable comparable = (Comparable) iterator.next();

                    try {
                        if (comparable != this.properties.get(iProperty)) {
                            hashbasedtable.put(
                                    iProperty,
                                    comparable,
                                    map.get(
                                            this.getPropertiesWithValue(
                                                    iProperty, comparable)));
                        }
                    } catch (IllegalArgumentException illegalargumentexception) {
                        throw method_9a70918140b0a7642dba(illegalargumentexception);
                    }

                    if (aint == null) {
                        continue;
                    }
                }

                if (aint != null) {
                    break label37;
                }
                break;
            }
        }

        this.propertyValueTable = ImmutableTable.copyOf(hashbasedtable);
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public int hashCode() {
        return this.properties.hashCode();
    }

    private BlockStateStateImplemenation(Block aVfValue, ImmutableMap immutablemap) {
        this.block = aVfValue;
        this.properties = immutablemap;
    }

    BlockStateStateImplemenation(Block block, ImmutableMap immutablemap, Object object) {
        this(block, immutablemap);
    }

    public Block getBlock() {
        return this.block;
    }

    public Collection getPropertyNames() {
        return Collections.unmodifiableCollection(this.properties.keySet());
    }

    public boolean equals(Object object) {
        try {
            if (this == object) {
                return true;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return false;
    }


    public Comparable getValue(IProperty iProperty) {
        try {
            if (!this.properties.containsKey(iProperty)) {
                throw new IllegalArgumentException(
                        "Cannot get property "
                                + iProperty
                                + " as it does not exist in "
                                + this.block.getBlockState());
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return (Comparable) iProperty.getValueClass().cast(this.properties.get(iProperty));
    }

    public BlockState withProperty(IProperty iProperty, Comparable comparable) {
        try {
            if (!this.properties.containsKey(iProperty)) {
                throw new IllegalArgumentException(
                        "Cannot set property "
                                + iProperty
                                + " as it does not exist in "
                                + this.block.getBlockState());
            }
        } catch (IllegalArgumentException illegalargumentexception2) {
            throw method_9a70918140b0a7642dba(illegalargumentexception2);
        }

        try {
            if (!iProperty.getAllowedValues().contains(comparable)) {
                return this;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        try {
            if (this.properties.get(iProperty) == comparable) {
                return this;
            }
        } catch (IllegalArgumentException illegalargumentexception1) {
            throw method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        return (BlockState) this.propertyValueTable.get(iProperty, comparable);
    }
}
