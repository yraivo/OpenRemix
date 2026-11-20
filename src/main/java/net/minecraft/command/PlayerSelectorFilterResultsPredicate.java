package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BoundingBox;

import com.google.common.base.Predicate;

public final class PlayerSelectorFilterResultsPredicate implements Predicate {
    BoundingBox val$axisAlignedBB;

    public boolean apply(Entity entity) {
        return entity.posX >= this.val$axisAlignedBB.minX
                        && entity.posY >= this.val$axisAlignedBB.minY
                        && entity.posZ >= this.val$axisAlignedBB.minZ
                ? entity.posX < this.val$axisAlignedBB.maxX
                        && entity.posY < this.val$axisAlignedBB.maxY
                        && entity.posZ < this.val$axisAlignedBB.maxZ
                : false;
    }

    public PlayerSelectorFilterResultsPredicate(BoundingBox boundingBox) {
        this.val$axisAlignedBB = boundingBox;
    }

    public boolean apply(Object object) {
        return this.apply((Entity) object);
    }
}
