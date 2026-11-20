package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {

    public static PropertyBool EXPLODE;

    public void explode(
            World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity) {
        if (!world.isRemote && (Boolean) blockState.getValue(net.minecraft.block.BlockTNT.EXPLODE)) {
            EntityTNTPrimed entityTNTPrimed =
                    new EntityTNTPrimed(
                            world,
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F,
                            livingEntity);
            world.spawnEntityInWorld(entityTNTPrimed);
            world.playSoundAtEntity(entityTNTPrimed, "game.tnt.primed", 1.0F, 1.0F);
        }
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (world.isBlockPowered(blockPos)) {
            this.onBlockDestroyedByPlayer(
                    world, blockPos, blockState.withProperty(net.minecraft.block.BlockTNT.EXPLODE, true));
            world.setBlockToAir(blockPos);
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockTNT.EXPLODE, (i & 1) > 0);
    }

    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (playerEntity.getCurrentEquippedItem() != null) {
            Item item = playerEntity.getCurrentEquippedItem().getItem();
            if (item == Items.flint_and_steel || item == Items.fire_charge) {
                this.explode(
                        world,
                        blockPos,
                        blockState.withProperty(net.minecraft.block.BlockTNT.EXPLODE, true),
                        playerEntity);
                world.setBlockToAir(blockPos);
                if (item == Items.flint_and_steel) {
                    playerEntity.getCurrentEquippedItem().damageItem(1, playerEntity);
                    if (i != 0) {
                        return true;
                    }
                }

                if (!playerEntity.capabilities.isCreativeMode) {
                    playerEntity.getCurrentEquippedItem().stackSize--;
                }

                return true;
            }
        }

        return super.onBlockActivated(world, blockPos, blockState, playerEntity, facing, f, f1, f2);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockTNT.EXPLODE});
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!world.isRemote && entity instanceof EntityArrow) {
            EntityArrow entityArrow = (EntityArrow) entity;
            if (entityArrow.isBurning()) {
                this.explode(
                        world,
                        blockPos,
                        world.getBlockState(blockPos).withProperty(net.minecraft.block.BlockTNT.EXPLODE, true),
                        entityArrow.shootingEntity instanceof LivingEntity
                                ? (LivingEntity) entityArrow.shootingEntity
                                : null);
                world.setBlockToAir(blockPos);
            }
        }
    }

    public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
        if (!world.isRemote) {
            EntityTNTPrimed entityTNTPrimed =
                    new EntityTNTPrimed(
                            world,
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F,
                            explosion.getExplosivePlacedBy());
            entityTNTPrimed.fuse =
                    world.rand.nextInt(entityTNTPrimed.fuse / 4) + entityTNTPrimed.fuse / 8;
            world.spawnEntityInWorld(entityTNTPrimed);
        }
    }

    public BlockTNT() {
        super(Material.tnt);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockTNT.EXPLODE, false));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, BlockState blockState) {
        this.explode(world, blockPos, blockState, (LivingEntity) null);
    }

    static {
        net.minecraft.block.BlockTNT.EXPLODE = PropertyBool.create("explode");
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        if (world.isBlockPowered(blockPos)) {
            this.onBlockDestroyedByPlayer(
                    world, blockPos, blockState.withProperty(net.minecraft.block.BlockTNT.EXPLODE, true));
            world.setBlockToAir(blockPos);
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockTNT.EXPLODE)) ? 1 : 0;
    }
}
