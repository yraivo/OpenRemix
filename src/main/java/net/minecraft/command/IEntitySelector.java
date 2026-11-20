package net.minecraft.command;

import com.google.common.base.Predicate;

public final class IEntitySelector {
    public static final Predicate IS_STANDALONE = new IsStandalonePredicate();
    public static final Predicate NOT_SPECTATING = new SelectInventoriesPredicate();
    public static final Predicate selectInventories = new NotSpectatingPredicate();
    public static final Predicate selectAnything = new SelectAnythingPredicate();

    // Helper used only by this class.
    public static final class IsStandalonePredicate implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity.isEntityAlive()
                    && entity.riddenByEntity == null
                    && entity.ridingEntity == null;
        }
    }

    // Helper used only by this class.
    public static final class NotSpectatingPredicate implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.inventory.IInventory && entity.isEntityAlive();
        }
    }

    // Helper used only by this class.
    public static final class SelectAnythingPredicate implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity.isEntityAlive();
        }
    }

    // Helper used only by this class.
    public static final class SelectInventoriesPredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return !(entity instanceof net.minecraft.entity.player.PlayerEntity)
                    || !((net.minecraft.entity.player.PlayerEntity) entity).isSpectator();
        }
    }
}
