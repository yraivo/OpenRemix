package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.Random;

public enum FacingPlane implements Predicate, Iterable {
    HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0),
    VERTICAL("VERTICAL", 1, "VERTICAL", 1);

    private static FacingPlane[] PLANE_VALUES;

    // region String decoding support

    // endregion

    public Facing[] facings() {
        switch (this) {
            case HORIZONTAL:
                return new Facing[] {Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST};
            case VERTICAL:
                return new Facing[] {Facing.UP, Facing.DOWN};
            default:
                throw new Error("Someone's been tampering with the universe!");
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.util.FacingPlane.PLANE_VALUES = new FacingPlane[] {HORIZONTAL, VERTICAL};
    }

    // endregion

    public boolean apply(Object object) {
        return this.apply((Facing) object);
    }

    public boolean apply(Facing facing) {
        return facing != null && facing.getAxis().getPlane() == this;
    }

    @Override
    public Iterator iterator() {
        return Iterators.forArray(this.facings());
    }

    // region String decoding support

    // endregion

    public Facing random(Random random) {
        Facing[] facingArray = this.facings();
        return facingArray[random.nextInt(facingArray.length)];
    }

    FacingPlane(String text, int j, String textValue, int k) {}

    // region String decoding support

    // endregion

}
