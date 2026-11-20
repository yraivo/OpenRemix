package net.minecraft.client.particle;

import net.minecraft.block.state.BlockState;
import net.minecraft.world.World;

public class EntityBlockDustFX extends EntityDiggingFX {
    protected EntityBlockDustFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double motionXValue,
            double motionYValue,
            double motionZValue,
            BlockState blockState) {
        super(worldValue, d0, d1, d2, motionXValue, motionYValue, motionZValue, blockState);
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
    }
}
