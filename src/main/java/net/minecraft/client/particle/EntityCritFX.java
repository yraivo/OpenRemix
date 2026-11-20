package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityCritFX extends EntitySmokeFX {
    protected EntityCritFX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue, d0, d1, d2, d3, d4, d5, 2.5F);
    }
}
