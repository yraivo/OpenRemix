package optifine;

import com.google.common.collect.Iterators;

import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class NextTickHashSet extends TreeSet {
    private int minZ;
    private static final int UNDEFINED = Integer.MIN_VALUE;
    private int maxZ;
    private int maxX;

    private LongHashMap longHashMap;

    private int minX;


    @Override
    public boolean contains(Object object) {
        try {
            if (!(object instanceof NextTickListEntry)) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        NextTickListEntry nextTickListEntry = (NextTickListEntry) object;
        Set set = this.getSubSet(nextTickListEntry, false);

        try {
            if (set == null) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        return set.contains(nextTickListEntry);
    }

    @Override
    public Iterator iterator() {
        int i = ReflectorMethod.f();

        try {
            if (this.minX == Integer.MIN_VALUE) {
                return super.iterator();
            }
        } catch (IllegalStateException illegalstateexception2) {
            throw method_b3985d2d68c259fdadde(illegalstateexception2);
        }

        try {
            if (this.size() <= 0) {
                return com.google.common.collect.ImmutableList.of().iterator();
            }
        } catch (IllegalStateException illegalstateexception4) {
            throw method_b3985d2d68c259fdadde(illegalstateexception4);
        }

        int j = this.minX >> 4;
        int k = this.minZ >> 4;
        int l = this.maxX >> 4;
        int i1 = this.maxZ >> 4;
        ArrayList arrayList = new ArrayList();
        int j1 = j;

        label61:
        while (j1 <= l) {
            int k1 = k;

            while (true) {
                if (k1 <= i1) {
                    Set set = this.getSubSet(j1, k1, false);

                    try {
                        if (set != null) {
                            arrayList.add(set.iterator());
                        }
                    } catch (IllegalStateException illegalstateexception1) {
                        throw method_b3985d2d68c259fdadde(illegalstateexception1);
                    }

                    k1++;
                    if (i == 0) {
                        continue;
                    }
                }

                j1++;
                if (i != 0) {
                    break label61;
                }
                break;
            }
        }

        try {
            if (arrayList.size() <= 0) {
                return com.google.common.collect.ImmutableList.of().iterator();
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        try {
            if (arrayList.size() == 1) {
                return (Iterator) arrayList.get(0);
            }
        } catch (IllegalStateException illegalstateexception3) {
            throw method_b3985d2d68c259fdadde(illegalstateexception3);
        }

        return Iterators.concat(arrayList.iterator());
    }

    @Override
    public boolean add(Object object) {
        try {
            if (!(object instanceof NextTickListEntry)) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception2) {
            throw method_b3985d2d68c259fdadde(illegalstateexception2);
        }

        NextTickListEntry nextTickListEntry = (NextTickListEntry) object;

        try {
            if (nextTickListEntry == null) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        Set set = this.getSubSet(nextTickListEntry, true);
        boolean flag = set.add(nextTickListEntry);
        boolean flag1 = super.add(object);

        try {
            if (flag != flag1) {
                throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
            } else {
                return flag1;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }
    }

    public void clearIteratorLimits() {
        this.minX = Integer.MIN_VALUE;
        this.minZ = Integer.MIN_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxZ = Integer.MIN_VALUE;
    }

    @Override
    public boolean remove(Object object) {
        try {
            if (!(object instanceof NextTickListEntry)) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception2) {
            throw method_b3985d2d68c259fdadde(illegalstateexception2);
        }

        NextTickListEntry nextTickListEntry = (NextTickListEntry) object;
        Set set = this.getSubSet(nextTickListEntry, false);

        try {
            if (set == null) {
                return false;
            }
        } catch (IllegalStateException illegalstateexception1) {
            throw method_b3985d2d68c259fdadde(illegalstateexception1);
        }

        boolean flag = set.remove(nextTickListEntry);
        boolean flag1 = super.remove(nextTickListEntry);

        try {
            if (flag != flag1) {
                throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
            } else {
                return flag1;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }
    }

    public NextTickHashSet(Set set) {
        this(set, ReflectorMethod.cR());
    }

    private NextTickHashSet(Set set, int initialFlowToken) {
        super();

        int j = initialFlowToken;

        int i = j;
        this.longHashMap = new LongHashMap();
        this.minX = Integer.MIN_VALUE;
        this.minZ = Integer.MIN_VALUE;
        this.maxX = Integer.MIN_VALUE;
        this.maxZ = Integer.MIN_VALUE;

        for (Object object : set) {
            this.add(object);
            if (i == 0) {
                break;
            }
        }
    }

    private Set getSubSet(NextTickListEntry nextTickListEntry, boolean flag) {
        try {
            if (nextTickListEntry == null) {
                return null;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        BlockPos blockPos = nextTickListEntry.position;
        int i = blockPos.getX() >> 4;
        int j = blockPos.getZ() >> 4;
        return this.getSubSet(i, j, flag);
    }

    private Set getSubSet(int i, int j, boolean flag) {
        long k = ChunkCoordIntPair.chunkXZ2Int(i, j);
        HashSet hashset = (HashSet) this.longHashMap.getValueByKey(k);

        try {
            if (hashset != null || !flag) {
                return hashset;
            }
        } catch (IllegalStateException illegalstateexception) {
            throw method_b3985d2d68c259fdadde(illegalstateexception);
        }

        hashset = new HashSet();
        this.longHashMap.add(k, hashset);
        return hashset;
    }

    public void setIteratorLimits(int i, int j, int k, int l) {
        this.minX = Math.min(i, k);
        this.minZ = Math.min(j, l);
        this.maxX = Math.max(i, k);
        this.maxZ = Math.max(j, l);
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }
}
