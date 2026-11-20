package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.world.World;

public class EntityBlockDustFXFactory implements IParticleFactory {
    public EntityFX getEntityFX(
            int i,
            World world,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        BlockState blockState = Block.getStateById(aint[0]);
        return blockState.getBlock().getRenderType() == -1
                ? null
                : new EntityBlockDustFX(world, d0, d1, d2, d3, d4, d5, blockState)
                        .func_174845_l();
    }
}
