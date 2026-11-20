package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class EntityFallingBlock extends Entity {
    private int fallHurtMax;
    private BlockState fallTile;
    private boolean hurtEntities;
    public CompoundTag tileEntityData;
    public boolean shouldDropItem = true;

    private float fallHurtAmount;
    private static String[] packedTextFragments;
    private boolean canSetAsBlock;

    public int fallTime;

    public EntityFallingBlock(World worldValue) {
        super(worldValue);
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canRenderOnFire() {
        return false;
    }

    public BlockState getBlock() {
        return this.fallTile;
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        Block block = this.fallTile.getBlock();
        boolean flag = flag1;
        if (block.getMaterial() == Material.AIR) {
            this.setDead();
            if (flag) {
                return;
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.fallTime++ == 0) {
            BlockPos blockPos = new BlockPos(this);
            if (this.world.getBlockState(blockPos).getBlock() == block) {
                this.world.setBlockToAir(blockPos);
                if (!(flag)) {

                    if (!this.world.isRemote) {
                        this.setDead();
                        return;
                    }
                }
            } else {

                if (!this.world.isRemote) {
                    this.setDead();
                    return;
                }
            }
        }

        this.motionY -= 0.04F;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (!this.world.isRemote) {
            BlockPos blockPosValue = new BlockPos(this);
            if (this.onGround) {
                this.motionX *= 0.7F;
                this.motionZ *= 0.7F;
                this.motionY *= -0.5;
                if (this.world.getBlockState(blockPosValue).getBlock() == Blocks.piston_extension) {
                    return;
                }

                this.setDead();
                if (!this.canSetAsBlock
                        && this.world.canBlockBePlaced(
                                block,
                                blockPosValue,
                                true,
                                Facing.UP,
                                (Entity) null,
                                (ItemStack) null)
                        && !BlockFalling.canFallInto(this.world, blockPosValue.offsetDown())
                        && this.world.setBlockState(blockPosValue, this.fallTile, 3)) {
                    if (block instanceof BlockFalling) {
                        ((BlockFalling) block).onEndFalling(this.world, blockPosValue);
                    }

                    if (this.tileEntityData == null || !(block instanceof ITileEntityProvider)) {
                        return;
                    }

                    TileEntity tileEntity = this.world.getTileEntity(blockPosValue);
                    if (tileEntity != null) {
                        CompoundTag compoundTag = new CompoundTag();
                        tileEntity.writeToNBT(compoundTag);

                        for (java.util.Iterator<?> __recoveryIterator171_6683 =
                                        (this.tileEntityData.getKeySet()).iterator();
                                (__recoveryIterator171_6683).hasNext(); ) {
                            String text = (String) (__recoveryIterator171_6683).next();
                            NBTBase tag = this.tileEntityData.getTag(text);
                            if (!text.equals(packedTextFragments[0])
                                    && !text.equals(packedTextFragments[1])
                                    && !text.equals(packedTextFragments[2])) {
                                compoundTag.setTag(text, tag.copy());
                            }

                            if (!flag) {
                                break;
                            }
                        }

                        tileEntity.readFromNBT(compoundTag);
                        tileEntity.markDirty();
                    }

                    if (flag) {
                        return;
                    }
                }

                if (!this.shouldDropItem
                        || this.canSetAsBlock
                        || !this.world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
                    return;
                }

                this.entityDropItem(
                        new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                if (flag) {
                    return;
                }
            }

            if (this.fallTime > 100
                            && !this.world.isRemote
                            && (blockPosValue.getY() < 1 || blockPosValue.getY() > 256)
                    || this.fallTime > 600) {
                if (this.shouldDropItem
                        && this.world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
                    this.entityDropItem(
                            new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                }

                this.setDead();
            }
        }
    }

    static {
        net.minecraft.entity.item.EntityFallingBlock.packedTextFragments = new String[6];
        net.minecraft.entity.item.EntityFallingBlock.packedTextFragments[0] = new String("x");
        net.minecraft.entity.item.EntityFallingBlock.packedTextFragments[1] = new String("y");
        net.minecraft.entity.item.EntityFallingBlock.packedTextFragments[2] = new String("z");
        net.minecraft.entity.item.EntityFallingBlock.packedTextFragments[3] = new String("");
    }

    public void setHurtEntities(boolean flag) {
        this.hurtEntities = flag;
    }

    public void fall(float f, float f1) {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        Block block = this.fallTile.getBlock();
        boolean flag = flag1;
        if (this.hurtEntities) {
            int i = MathHelper.ceil(f - 1.0F);
            if (i > 0) {
                ArrayList arrayList =
                        Lists.newArrayList(
                                this.world.getEntitiesWithinAABBExcludingEntity(
                                        this, this.getEntityBoundingBox()));
                int j = block == Blocks.anvil ? 1 : 0;
                DamageSource damageSource = j != 0 ? DamageSource.anvil : DamageSource.fallingBlock;

                for (java.util.Iterator<?> __recoveryIterator171_32992 = (arrayList).iterator();
                        (__recoveryIterator171_32992).hasNext(); ) {
                    Entity entity = (Entity) (__recoveryIterator171_32992).next();
                    entity.attackEntityFrom(
                            damageSource,
                            Math.min(MathHelper.floor(i * this.fallHurtAmount), this.fallHurtMax));
                    if (flag) {
                        break;
                    }
                }

                if (j != 0 && this.rand.nextFloat() < 0.05F + i * 0.05) {
                    int k = (Integer) this.fallTile.getValue(BlockAnvil.DAMAGE);
                    if (++k > 2) {
                        this.canSetAsBlock = true;
                        if (!flag) {
                            return;
                        }
                    }

                    this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, k);
                }
            }
        }
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        boolean flag;
        label46:
        {
            boolean flag1 = EntityMinecartEnumMinecartType.d();
            int i = compoundTag.getByte("Data") & 255;
            flag = flag1;
            if (compoundTag.hasKey("Block", 8)) {
                this.fallTile =
                        Block.getBlockFromName(compoundTag.getString("Block")).getStateFromMeta(i);
                if (!flag) {
                    break label46;
                }
            }

            if (compoundTag.hasKey("TileID", 99)) {
                this.fallTile =
                        Block.getBlockById(compoundTag.getInteger("TileID")).getStateFromMeta(i);
                if (!flag) {
                    break label46;
                }
            }

            this.fallTile =
                    Block.getBlockById(compoundTag.getByte("Tile") & 255).getStateFromMeta(i);
        }

        Block block;
        {
            this.fallTime = compoundTag.getByte("Time") & 255;
            block = this.fallTile.getBlock();
            if (compoundTag.hasKey("HurtEntities", 99)) {
                this.hurtEntities = compoundTag.getBoolean("HurtEntities");
                this.fallHurtAmount = compoundTag.getFloat("FallHurtAmount");
                this.fallHurtMax = compoundTag.getInteger("FallHurtMax");
                if (!(!flag)) {

                    if (block == Blocks.anvil) {
                        this.hurtEntities = true;
                    }
                }
            } else {

                if (block == Blocks.anvil) {
                    this.hurtEntities = true;
                }
            }
        }

        if (compoundTag.hasKey("DropItem", 99)) {
            this.shouldDropItem = compoundTag.getBoolean("DropItem");
        }

        if (compoundTag.hasKey("TileEntityData", 10)) {
            this.tileEntityData = compoundTag.getCompoundTag("TileEntityData");
        }

        if (block == null || block.getMaterial() == Material.AIR) {
            this.fallTile = Blocks.sand.getDefaultState();
        }
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.air;
        ResourceLocation nameForObject =
                (ResourceLocation) Block.blockRegistry.getNameForObject(block);
        compoundTag.setString(
                "Block", nameForObject == null ? packedTextFragments[3] : nameForObject.toString());
        compoundTag.setByte("Data", (byte) block.getMetaFromState(this.fallTile));
        compoundTag.setByte("Time", (byte) this.fallTime);
        compoundTag.setBoolean("DropItem", this.shouldDropItem);
        compoundTag.setBoolean("HurtEntities", this.hurtEntities);
        compoundTag.setFloat("FallHurtAmount", this.fallHurtAmount);
        compoundTag.setInteger("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            compoundTag.setTag("TileEntityData", this.tileEntityData);
        }
    }

    protected void entityInit() {}

    public EntityFallingBlock(
            World worldValue,
            double prevPosXValue,
            double prevPosYValue,
            double prevPosZValue,
            BlockState baWValue) {
        super(worldValue);
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
        this.fallTile = baWValue;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.setPosition(prevPosXValue, prevPosYValue, prevPosZValue);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosXValue;
        this.prevPosY = prevPosYValue;
        this.prevPosZ = prevPosZValue;
    }

    public void addEntityCrashInfo(CrashReportCategory crashReportCategory) {
        super.addEntityCrashInfo(crashReportCategory);
        if (this.fallTile != null) {
            Block block = this.fallTile.getBlock();
            crashReportCategory.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
            crashReportCategory.addCrashSection(
                    "Immitating block data", block.getMetaFromState(this.fallTile));
        }
    }

    public World getWorld() {
        return this.world;
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
}
