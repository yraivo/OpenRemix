package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;

public class RenderMinecartMobSpawner extends RenderMinecart {
    public RenderMinecartMobSpawner(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    protected void method_f2205da7e06fa62ee7f9(
            EntityMinecartMobSpawner entityMinecartMobSpawner, float f, BlockState blockState) {
        super.method_483853dc58042e156d99(entityMinecartMobSpawner, f, blockState);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            TileEntityMobSpawnerRenderer.renderMob(
                    entityMinecartMobSpawner.getMobSpawnerLogic(),
                    entityMinecartMobSpawner.posX,
                    entityMinecartMobSpawner.posY,
                    entityMinecartMobSpawner.posZ,
                    f);
        }
    }

    protected void method_483853dc58042e156d99(
            EntityMinecart entityMinecart, float p171_1, BlockState blockState) {
        this.method_f2205da7e06fa62ee7f9(
                (net.minecraft.entity.ai.EntityMinecartMobSpawner) entityMinecart, p171_1, blockState);
    }
}
