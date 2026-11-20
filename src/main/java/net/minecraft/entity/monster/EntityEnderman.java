package net.minecraft.entity.monster;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Set;
import java.util.UUID;

public class EntityEnderman extends MonsterEntity {

    private static UUID attackingSpeedBoostModifierUUID;

    private boolean isAggressive;
    private static AttributeModifier attackingSpeedBoostModifier;
    private static Set carriableBlocks;

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0);
    }

    private boolean shouldAttackPlayer(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.armorInventory[3];
        if (itemStack != null && itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        }

        Vec3 vec3 = playerEntity.getLook(1.0F).normalize();
        Vec3 vec3Value =
                new Vec3(
                        this.posX - playerEntity.posX,
                        this.getEntityBoundingBox().minY
                                + this.height / 2.0F
                                - (playerEntity.posY + playerEntity.getEyeHeight()),
                        this.posZ - playerEntity.posZ);
        double d0 = vec3Value.lengthVector();
        vec3Value = vec3Value.normalize();
        double d1 = vec3.dotProduct(vec3Value);
        return d1 > 1.0 - 0.025 / d0 && playerEntity.canEntityBeSeen(this);
    }

    public EntityEnderman(World worldValue) {
        super(worldValue);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityEndermanAIPlaceBlock(this));
        this.tasks.addTask(11, new EntityEndermanAITakeBlock(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityEndermanAIFindPlayer(this));
        this.targetTasks.addTask(
                3,
                new EntityAINearestAttackableTarget(
                        this,
                        EntityEndermite.class,
                        10,
                        true,
                        false,
                        new PlayerSpawnedEndermitePredicate(this)));
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (damageSource.getEntity() == null
                || !(damageSource.getEntity() instanceof EntityEndermite)) {
            if (!this.world.isRemote) {
                this.setScreaming(true);
            }

            if (damageSource instanceof EntityDamageSource
                    && damageSource.getEntity() instanceof PlayerEntity) {
                if (damageSource.getEntity() instanceof EntityPlayerMP
                        && ((EntityPlayerMP) damageSource.getEntity())
                                .theItemInWorldManager.isCreative()) {
                    this.setScreaming(false);
                    if (!(i == 0)) {

                        this.isAggressive = true;
                    }
                } else {

                    this.isAggressive = true;
                }
            }

            if (damageSource instanceof EntityDamageSourceIndirect) {
                this.isAggressive = false;
                int j = 0;

                while (j < 64) {
                    if (this.teleportRandomly()) {
                        return true;
                    }

                    j++;
                    if (i != 0) {
                        break;
                    }
                }

                return false;
            }
        }

        boolean flag = super.attackEntityFrom(damageSource, f);
        if (damageSource.isUnblockable() && this.rand.nextInt(10) != 0) {
            this.teleportRandomly();
        }

        return flag;
    }

    static {
        net.minecraft.entity.monster.EntityEnderman.attackingSpeedBoostModifierUUID =
                UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        net.minecraft.entity.monster.EntityEnderman.attackingSpeedBoostModifier =
                new AttributeModifier(
                                net.minecraft.entity.monster.EntityEnderman
                                        .attackingSpeedBoostModifierUUID,
                                "Attacking speed boost",
                                0.15F,
                                0)
                        .setSaved(false);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks = Sets.newIdentityHashSet();
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.grass);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.dirt);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.sand);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.gravel);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.yellow_flower);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.red_flower);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.brown_mushroom);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.red_mushroom);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.tnt);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.cactus);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.clay);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.pumpkin);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.melon_block);
        net.minecraft.entity.monster.EntityEnderman.carriableBlocks.add(Blocks.mycelium);
    }

    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if (this.isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }

        if (this.world.isDaytime()) {
            float brightness = this.getBrightness(1.0F);
            if (brightness > 0.5F
                    && this.world.isAgainstSky(new BlockPos(this))
                    && this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F) {
                this.setAttackTarget((LivingEntity) null);
                this.setScreaming(false);
                this.isAggressive = false;
                this.teleportRandomly();
            }
        }

        super.updateAITasks();
    }

    protected String getHurtSound() {
        return "mob.endermen.hit";
    }

    protected boolean teleportToEntity(Entity entity) {
        Vec3 vec3 =
                new Vec3(
                        this.posX - entity.posX,
                        this.getEntityBoundingBox().minY
                                + this.height / 2.0F
                                - entity.posY
                                + entity.getEyeHeight(),
                        this.posZ - entity.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.x * d0;
        double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.y * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.z * d0;
        return this.teleportTo(d1, d2, d3);
    }

    static AttributeModifier access$000() {
        return net.minecraft.entity.monster.EntityEnderman.attackingSpeedBoostModifier;
    }

    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        BlockState blockState = this.getHeldBlockState();
        compoundTag.setShort("carried", (short) Block.getIdFromBlock(blockState.getBlock()));
        compoundTag.setShort(
                "carriedData", (short) blockState.getBlock().getMetaFromState(blockState));
    }

    public void setHeldBlockState(BlockState blockState) {
        this.dataWatcher.updateObject(16, (short) (Block.getStateId(blockState) & 65535));
    }

    static boolean access$100(EntityEnderman entityEnderman, PlayerEntity playerEntity) {
        return entityEnderman.shouldAttackPlayer(playerEntity);
    }

    public float getEyeHeight() {
        return 2.55F;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Short((short) 0));
        this.dataWatcher.addObject(17, new Byte((byte) 0));
        this.dataWatcher.addObject(18, new Byte((byte) 0));
    }

    protected String getDeathSound() {
        return "mob.endermen.death";
    }

    protected boolean teleportTo(double posXValue, double posYValue, double posZValue) {
        double posXValue2 = this.posX;
        int k = EntitySpiderGroupData.method_7eec03da329a017ec143();
        double posYValue2 = this.posY;
        int i = k;
        double posZValue2 = this.posZ;
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        byte b0 = 0;
        BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
        if (this.world.isBlockLoaded(blockPos)) {
            byte b1 = 0;

            while (b1 == 0 && blockPos.getY() > 0) {
                {
                    BlockPos blockPosValue = blockPos.offsetDown();
                    Block block = this.world.getBlockState(blockPosValue).getBlock();
                    if (block.getMaterial().blocksMovement()) {
                        b1 = 1;
                        if (!(i != 0)) {

                            this.posY--;
                            blockPos = blockPosValue;
                        }
                    } else {

                        this.posY--;
                        blockPos = blockPosValue;
                    }
                }

                if (i == 0) {
                    break;
                }
            }

            if (b1 != 0) {
                super.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                if (this.world
                                .getCollidingBoundingBoxes(this, this.getEntityBoundingBox())
                                .isEmpty()
                        && !this.world.isAnyLiquid(this.getEntityBoundingBox())) {
                    b0 = 1;
                }
            }
        }

        if (b0 == 0) {
            this.setPosition(posXValue2, posYValue2, posZValue2);
            return false;
        }

        short short1 = 128;
        int j = 0;

        while (j < short1) {
            double d9 = j / (short1 - 1.0);
            float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
            float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
            double d6 =
                    posXValue2
                            + (this.posX - posXValue2) * d9
                            + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            double d7 =
                    posYValue2
                            + (this.posY - posYValue2) * d9
                            + this.rand.nextDouble() * this.height;
            double d8 =
                    posZValue2
                            + (this.posZ - posZValue2) * d9
                            + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            this.world.spawnParticle(EnumParticleTypes.PORTAL, d6, d7, d8, f, f1, f2, new int[0]);
            j++;
            if (i == 0) {
                break;
            }
        }

        this.world.playSoundEffect(
                posXValue2, posYValue2, posZValue2, "mob.endermen.portal", 1.0F, 1.0F);
        this.playSound("mob.endermen.portal", 1.0F, 1.0F);
        return true;
    }

    public void onLivingUpdate() {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (this.world.isRemote) {
            int j = 0;

            while (j < 2) {
                this.world.spawnParticle(
                        EnumParticleTypes.PORTAL,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height - 0.25,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        (this.rand.nextDouble() - 0.5) * 2.0,
                        -this.rand.nextDouble(),
                        (this.rand.nextDouble() - 0.5) * 2.0,
                        new int[0]);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        this.isJumping = false;
        super.onLivingUpdate();
    }

    protected void dropFewItems(boolean flag, int i) {
        int i1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        Item item = this.getDropItem();
        int j = i1;
        if (item != null) {
            int k = this.rand.nextInt(2 + i);
            int l = 0;

            while (l < k) {
                this.dropItem(item, 1);
                l++;
                if (j != 0) {
                    break;
                }
            }
        }
    }

    static Set access$300() {
        return net.minecraft.entity.monster.EntityEnderman.carriableBlocks;
    }

    public BlockState getHeldBlockState() {
        return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 65535);
    }

    static boolean access$202(EntityEnderman entityEnderman, boolean bgXValue) {
        return entityEnderman.isAggressive = bgXValue;
    }

    protected Item getDropItem() {
        return Items.ender_pearl;
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        double d1 = this.posY + (this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d0, d1, d2);
    }

    public void setScreaming(boolean flag) {
        this.dataWatcher.updateObject(18, (byte) (flag ? 1 : 0));
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        BlockState blockState;
        {
            int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
            super.readEntityFromNBT(compoundTag);
            int i = j;
            if (compoundTag.hasKey("carried", 8)) {
                blockState =
                        Block.getBlockFromName(compoundTag.getString("carried"))
                                .getStateFromMeta(compoundTag.getShort("carriedData") & 65535);
                if (!(i == 0)) {

                    blockState =
                            Block.getBlockById(compoundTag.getShort("carried"))
                                    .getStateFromMeta(compoundTag.getShort("carriedData") & 65535);
                }
            } else {

                blockState =
                        Block.getBlockById(compoundTag.getShort("carried"))
                                .getStateFromMeta(compoundTag.getShort("carriedData") & 65535);
            }
        }

        this.setHeldBlockState(blockState);
    }

    // Helper used only by this class.
    static class PlayerSpawnedEndermitePredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.monster.EntityEnderman field_179949_a;

        public boolean func_179948_a(net.minecraft.entity.monster.EntityEndermite entityEndermite) {
            return entityEndermite.isSpawnedByPlayer();
        }

        PlayerSpawnedEndermitePredicate(net.minecraft.entity.monster.EntityEnderman bsRValue) {
            this.field_179949_a = bsRValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.func_179948_a((net.minecraft.entity.monster.EntityEndermite) object);
        }
    }
}
