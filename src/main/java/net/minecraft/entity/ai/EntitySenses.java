package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import com.google.common.collect.Lists;

import java.util.List;

public class EntitySenses {
    List unseenEntities;
    EntityLiving entityObj;

    private static String d;
    List seenEntities = Lists.newArrayList();

    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }

    public EntitySenses(EntityLiving bjnValue) {
        this.unseenEntities = Lists.newArrayList();
        this.entityObj = bjnValue;
    }

    // region String decoding support

    // endregion

    static {
        d = "canSee";
    }

    public boolean canSee(Entity entity) {
        String text = EntityAIBase.cJ();
        if (this.seenEntities.contains(entity)) {
            return true;
        }

        if (this.unseenEntities.contains(entity)) {
            return false;
        }

        this.entityObj.world.theProfiler.startSection(d);
        boolean flag = this.entityObj.canEntityBeSeen(entity);
        this.entityObj.world.theProfiler.endSection();
        if (flag) {
            this.seenEntities.add(entity);
            if (text != null) {
                return flag;
            }
        }

        this.unseenEntities.add(entity);
        return flag;
    }

    // region String decoding support

    // endregion
}
