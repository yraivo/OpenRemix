package net.minecraft.entity;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class LivingEntity extends Entity {
    public float prevRenderYawOffset;
    protected float prevOnGroundSpeedFactor;
    public float limbSwingAmount;
    protected float bek;
    public float renderYawOffset;
    public float limbSwing;
    protected float randomYawVelocity;
    private float absorptionAmount;
    protected int recentlyHit;
    public float attackedAtYaw;
    private int jumpTicks;
    protected int NJ;
    private float landMovementFactor;
    protected boolean dead;
    private BaseAttributeMap attributeMap;
    protected PlayerEntity attackingPlayer;
    protected float onGroundSpeedFactor;
    public int maxHurtResistantTime;
    public float moveForward;
    private boolean potionsNeedUpdate;
    private static String aIO;
    public float moveStrafing;
    private LivingEntity lastAttacker;
    public float prevRotationYawHead;
    private ItemStack[] previousEquipment;
    private LivingEntity entityLivingToAttack;
    public float prevSwingProgress;
    public float swingProgress;
    public float aIV;
    public int bex;
    public float cameraPitch;
    public float lastDamage;
    protected double newRotationYaw;
    public float jumpMovementFactor;
    public static AttributeModifier sprintingSpeedBoostModifier;

    protected int scoreValue;
    protected int newPosRotationIncrements;
    public float prevLimbSwingAmount;
    public boolean isJumping;
    private static String[] packedTextFragments;
    public boolean isSwingInProgress;
    public int maxHurtTime;
    public int deathTime;
    private int lastAttackerTime;
    protected double newRotationPitch;
    private final CombatTracker _combatTracker = new CombatTracker(this);
    protected float beI;
    protected double newPosY;
    private int revengeTimer;
    protected double newPosZ;

    public float prevCameraPitch;
    public static UUID sprintingSpeedBoostModifierUUID;
    public int swingProgressInt;
    public float aJT;
    public float rotationYawHead;
    protected double newPosX;
    private final java.util.Map activePotionsMap = Maps.newHashMap();
    protected float beR;
    public int hurtTime;

    public final void setArrowCountInEntity(int p171_0) {
        this.dataWatcher.updateObject(9, (byte) p171_0);
    }

    public boolean attackEntityAsMob(Entity entity) {
        this.setLastAttacker(entity);
        return false;
    }

    public int getTotalArmorValue() {
        int i = 0;
        ItemStack[] itemStackArray = this.getInventory();
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int j = itemStackArray.length;
        int k = 0;
        String[] astring = astring1;

        while (k < j) {
            ItemStack itemStack = itemStackArray[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                int l = ((ItemArmor) itemStack.getItem()).damageReduceAmount;
                i += l;
            }

            k++;
            if (astring != null) {
                break;
            }
        }

        return i;
    }

    public void heal(float p171_0) {
        float health = this.getHealth();
        if (health > 0.0F) {
            this.setHealth(health + p171_0);
        }
    }

    protected void kill() {
        this.attackEntityFrom(net.minecraft.util.DamageSource.outOfWorld, 4.0F);
    }

    public boolean isPlayerSleeping() {
        return false;
    }

    public boolean canBePushed() {
        return !this.isDead;
    }

    public void moveEntityWithHeading(float f, float f1) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        label123:
        if (this.isServerWorld()) {
            if (this.isInWater()
                    && (!(this instanceof PlayerEntity)
                            || !((PlayerEntity) this).capabilities.isFlying)) {
                double posYValue = this.posY;
                float f3 = 0.8F;
                float f4 = 0.02F;
                float f2 = EnchantmentHelper.getDepthStriderModifier(this);
                if (f2 > 3.0F) {
                    f2 = 3.0F;
                }

                if (!this.onGround) {
                    f2 *= 0.5F;
                }

                if (f2 > 0.0F) {
                    f3 += (0.54600006F - f3) * f2 / 3.0F;
                    f4 += (this.getAIMoveSpeed() * 1.0F - f4) * f2 / 3.0F;
                }

                this.moveFlying(f, f1, f4);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= f3;
                this.motionY *= 0.8F;
                this.motionZ *= f3;
                this.motionY -= 0.02;
                if (!this.collidedHorizontally
                        || !this.isOffsetPositionInLiquid(
                                this.motionX,
                                this.motionY + 0.6F - this.posY + posYValue,
                                this.motionZ)) {
                    break label123;
                }

                this.motionY = 0.3F;
                if (astring == null) {
                    break label123;
                }
            }

            if (this.isInLava()
                    && (!(this instanceof PlayerEntity)
                            || !((PlayerEntity) this).capabilities.isFlying)) {
                double posYValue2 = this.posY;
                this.moveFlying(f, f1, 0.02F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
                this.motionY -= 0.02;
                if (!this.collidedHorizontally
                        || !this.isOffsetPositionInLiquid(
                                this.motionX,
                                this.motionY + 0.6F - this.posY + posYValue2,
                                this.motionZ)) {
                    break label123;
                }

                this.motionY = 0.3F;
                if (astring == null) {
                    break label123;
                }
            }

            float f5 = 0.91F;
            if (this.onGround) {
                f5 =
                        this.world
                                        .getBlockState(
                                                new BlockPos(
                                                        MathHelper.floor(this.posX),
                                                        MathHelper.floor(
                                                                        this.getEntityBoundingBox()
                                                                                .minY)
                                                                - 1,
                                                        MathHelper.floor(this.posZ)))
                                        .getBlock()
                                        .slipperiness
                                * 0.91F;
            }

            float f8;
            {
                float f6 = 0.16277136F / (f5 * f5 * f5);
                if (this.onGround) {
                    f8 = this.getAIMoveSpeed() * f6;
                    if (!(astring == null)) {

                        f8 = this.jumpMovementFactor;
                    }
                } else {

                    f8 = this.jumpMovementFactor;
                }
            }

            this.moveFlying(f, f1, f8);
            f5 = 0.91F;
            if (this.onGround) {
                f5 =
                        this.world
                                        .getBlockState(
                                                new BlockPos(
                                                        MathHelper.floor(this.posX),
                                                        MathHelper.floor(
                                                                        this.getEntityBoundingBox()
                                                                                .minY)
                                                                - 1,
                                                        MathHelper.floor(this.posZ)))
                                        .getBlock()
                                        .slipperiness
                                * 0.91F;
            }

            if (this.isOnLadder()) {
                float f9 = 0.15F;
                this.motionX = MathHelper.clamp(this.motionX, -f9, f9);
                this.motionZ = MathHelper.clamp(this.motionZ, -f9, f9);
                this.fallDistance = 0.0F;
                if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }

                int i = this.isSneaking() && this instanceof PlayerEntity ? 1 : 0;
                if (i != 0 && this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.collidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
            }

            label75:
            {
                if (this.world.isRemote
                        && (!this.world.isBlockLoaded(
                                        new BlockPos((int) this.posX, 0, (int) this.posZ))
                                || !this.world
                                        .getChunkFromBlockCoords(
                                                new BlockPos((int) this.posX, 0, (int) this.posZ))
                                        .isLoaded())) {
                    if (this.posY > 0.0) {
                        this.motionY = -0.1;
                        if (astring == null) {
                            break label75;
                        }
                    }

                    this.motionY = 0.0;
                    if (astring == null) {
                        break label75;
                    }
                }

                this.motionY -= 0.08;
            }

            this.motionY *= 0.98F;
            this.motionX *= f5;
            this.motionZ *= f5;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d2 = this.posX - this.prevPosX;
        double d3 = this.posZ - this.prevPosZ;
        float f7 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;
        if (f7 > 1.0F) {
            f7 = 1.0F;
        }

        this.limbSwingAmount = this.limbSwingAmount + (f7 - this.limbSwingAmount) * 0.4F;
        this.limbSwing = this.limbSwing + this.limbSwingAmount;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.setAbsorptionAmount(compoundTag.getFloat("AbsorptionAmount"));
        String[] astring = astring1;
        if (compoundTag.hasKey("Attributes", 9) && this.world != null && !this.world.isRemote) {
            SharedMonsterAttributes.setAttributeModifiers(
                    this.getAttributeMap(), compoundTag.getTagList("Attributes", 10));
        }

        if (compoundTag.hasKey("ActiveEffects", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("ActiveEffects", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
                PotionEffect potionEffect =
                        PotionEffect.readCustomPotionEffectFromNBT(compoundTagValue);
                if (potionEffect != null) {
                    this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
                }

                i++;
                if (astring != null) {
                    break;
                }
            }
        }

        label53:
        {
            if (compoundTag.hasKey("HealF", 99)) {
                this.setHealth(compoundTag.getFloat("HealF"));
                if (astring == null) {
                    break label53;
                }
            }

            NBTBase tag = compoundTag.getTag("Health");
            if (tag == null) {
                this.setHealth(this.getMaxHealth());
                if (astring == null) {
                    break label53;
                }
            }

            if (tag.getId() == 5) {
                this.setHealth(((NBTTagFloat) tag).getFloat());
                if (astring == null) {
                    break label53;
                }
            }

            if (tag.getId() == 2) {
                this.setHealth(((NBTTagShort) tag).getShort());
            }
        }

        this.hurtTime = compoundTag.getShort("HurtTime");
        this.deathTime = compoundTag.getShort("DeathTime");
        this.revengeTimer = compoundTag.getInteger("HurtByTimestamp");
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public void swingItem() {
        if (!this.isSwingInProgress
                || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2
                || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (this.world instanceof net.minecraft.world.WorldServer) {
                ((net.minecraft.world.WorldServer) this.world)
                        .getEntityTracker()
                        .sendToAllTrackingEntity(
                                this,
                                new net.minecraft.network.play.server.S0BPacketAnimation(this, 0));
            }
        }
    }

    public void fall(float f, float f1) {
        super.fall(f, f1);
        PotionEffect activePotionEffect = this.getActivePotionEffect(Potion.JUMP);
        float f2 = activePotionEffect != null ? activePotionEffect.getAmplifier() + 1 : 0.0F;
        int i = MathHelper.ceil((f - 3.0F - f2) * f1);
        if (i > 0) {
            this.playSound(this.getFallSoundString(i), 1.0F, 1.0F);
            this.attackEntityFrom(DamageSource.fall, i);
            int j = MathHelper.floor(this.posX);
            int k = MathHelper.floor(this.posY - 0.2F);
            int l = MathHelper.floor(this.posZ);
            Block block = this.world.getBlockState(new BlockPos(j, k, l)).getBlock();
            if (block.getMaterial() != Material.AIR) {
                BlockSoundType ajf = block.stepSound;
                this.playSound(
                        ajf.getStepSound(), ajf.getVolume() * 0.5F, ajf.getFrequency() * 0.75F);
            }
        }
    }

    protected void addRandomArmor() {}

    protected void dropEquipment(boolean flag, int i) {}

    protected int decreaseAirSupply(int p171_0) {
        int j = net.minecraft.enchantment.EnchantmentHelper.getRespiration(this);
        return j > 0 && this.rand.nextInt(j + 1) > 0 ? p171_0 : p171_0 - 1;
    }

    public void setPositionAndRotation2(
            double bePValue,
            double beJValue,
            double beKValue,
            float beAValue,
            float beGValue,
            int newPosRotationIncrementsValue,
            boolean p171_6) {
        this.newPosX = bePValue;
        this.newPosY = beJValue;
        this.newPosZ = beKValue;
        this.newRotationYaw = beAValue;
        this.newRotationPitch = beGValue;
        this.newPosRotationIncrements = newPosRotationIncrementsValue;
    }

    protected void onChangedPotionEffect(PotionEffect potionEffect, boolean flag) {
        this.potionsNeedUpdate = true;
        if (flag && !this.world.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(
                    this, this.getAttributeMap(), potionEffect.getAmplifier());
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(
                    this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public LivingEntity func_94060_bK() {
        return (net.minecraft.entity.LivingEntity)
                (this._combatTracker.TZ() != null
                        ? this._combatTracker.TZ()
                        : (this.attackingPlayer != null
                                ? this.attackingPlayer
                                : this.entityLivingToAttack));
    }

    public void addPotionEffect(PotionEffect potionEffect) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.isPotionApplicable(potionEffect)) {
            if (this.activePotionsMap.containsKey(potionEffect.getPotionID())) {
                ((net.minecraft.potion.PotionEffect)
                                this.activePotionsMap.get(potionEffect.getPotionID()))
                        .combine(potionEffect);
                this.onChangedPotionEffect(
                        ((net.minecraft.potion.PotionEffect)
                                this.activePotionsMap.get(potionEffect.getPotionID())),
                        true);
                if (astring == null) {
                    return;
                }
            }

            this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
            this.onNewPotionEffect(potionEffect);
        }
    }

    protected void collideWithEntity(Entity entity) {
        entity.applyEntityCollision(this);
    }

    public void renderBrokenItemStack(ItemStack itemStack) {
        this.playSound("random.break", 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < 5) {
            Vec3 vec3 =
                    new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            vec3 = vec3.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
            vec3 = vec3.rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
            double d0 = -this.rand.nextFloat() * 0.6 - 0.3;
            Vec3 vec3Value = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, d0, 0.6);
            vec3Value = vec3Value.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
            vec3Value = vec3Value.rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
            vec3Value = vec3Value.addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            this.world.spawnParticle(
                    EnumParticleTypes.ITEM_CRACK,
                    vec3Value.x,
                    vec3Value.y,
                    vec3Value.z,
                    vec3.x,
                    vec3.y + 0.05,
                    vec3.z,
                    new int[] {Item.getIdFromItem(itemStack.getItem())});
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    protected void damageArmor(float f) {}

    public LivingEntity getLastAttacker() {
        return this.lastAttacker;
    }

    public static void b(String aIOValue) {
        aIO = aIOValue;
    }

    protected String getHurtSound() {
        return "game.neutral.hurt";
    }

    public java.util.Collection getActivePotionEffects() {
        return this.activePotionsMap.values();
    }

    protected float Jr() {
        return this.isChild()
                ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F
                : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
    }

    public void onItemPickup(Entity entity, int p171_1) {
        if (!entity.isDead && !this.world.isRemote) {
            net.minecraft.entity.EntityTracker entityTracker =
                    ((net.minecraft.world.WorldServer) this.world).getEntityTracker();
            if (entity instanceof net.minecraft.entity.item.EntityItem
                    || entity instanceof net.minecraft.entity.projectile.EntityArrow
                    || entity instanceof net.minecraft.entity.item.EntityXPOrb) {
                entityTracker.sendToAllTrackingEntity(
                        entity,
                        new net.minecraft.network.play.server.S0DPacketCollectItem(
                                entity.getEntityId(), this.getEntityId()));
            }
        }
    }

    public void onKillCommand() {
        this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
    }

    public float getAIMoveSpeed() {
        return this.landMovementFactor;
    }

    public abstract void setCurrentItemOrArmor(int i, ItemStack itemStack);

    public void setSprinting(boolean flag) {
        super.setSprinting(flag);
        IAttributeInstance entityAttribute =
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (entityAttribute.getModifier(
                        net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifierUUID)
                != null) {
            entityAttribute.removeModifier(
                    net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifier);
        }

        if (flag) {
            entityAttribute.applyModifier(
                    net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifier);
        }
    }

    public int getRevengeTimer() {
        return this.revengeTimer;
    }

    protected float getJumpUpwardsMotion() {
        return 0.42F;
    }

    public void updateRidden() {
        super.updateRidden();
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        this.onGroundSpeedFactor = 0.0F;
        this.fallDistance = 0.0F;
    }

    public void removePotionEffect(int i) {
        PotionEffect potionEffect =
                ((net.minecraft.potion.PotionEffect) this.activePotionsMap.remove(i));
        if (potionEffect != null) {
            this.onFinishedPotionEffect(potionEffect);
        }
    }

    protected void markPotionsDirty() {
        this.potionsNeedUpdate = true;
    }

    protected void handleJumpLava() {
        this.motionY += 0.04F;
    }

    protected void onFinishedPotionEffect(PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.world.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(
                    this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }

    protected void nx() {
        int i;
        label14:
        {
            String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            i = this.getArmSwingAnimationEnd();
            String[] astring = astring1;
            if (this.isSwingInProgress) {
                this.swingProgressInt++;
                if (this.swingProgressInt < i) {
                    break label14;
                }

                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
                if (astring == null) {
                    break label14;
                }
            }

            this.swingProgressInt = 0;
        }

        this.swingProgress = (float) this.swingProgressInt / i;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.entity.LivingEntity.packedTextFragments = new String[3];
        net.minecraft.entity.LivingEntity.packedTextFragments[0] = new String("R691sc");
        String s2 = packedTextFragments[0];
        b(s2);
        net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifierUUID =
                UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifier =
                new AttributeModifier(
                                net.minecraft.entity.LivingEntity.sprintingSpeedBoostModifierUUID,
                                "Sprinting speed boost",
                                0.3F,
                                2)
                        .setSaved(false);
    }

    // endregion

    protected void resetPotionEffectMetadata() {
        this.dataWatcher.updateObject(8, (byte) 0);
        this.dataWatcher.updateObject(7, 0);
    }

    public void onDeath(DamageSource damageSource) {
        Entity entity = damageSource.getEntity();
        LivingEntity livingEntity = this.func_94060_bK();
        if (this.scoreValue >= 0 && livingEntity != null) {
            livingEntity.addToPlayerScore(this, this.scoreValue);
        }

        if (entity != null) {
            entity.onKillEntity(this);
        }

        this.dead = true;
        this.getCombatTracker().reset();
        if (!this.world.isRemote) {
            int i = 0;
            if (entity instanceof PlayerEntity) {
                i = EnchantmentHelper.getLootingModifier((LivingEntity) entity);
            }

            if (this.canDropLoot()
                    && this.world.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                this.dropFewItems(this.recentlyHit > 0, i);
                this.dropEquipment(this.recentlyHit > 0, i);
                if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + i * 0.01F) {
                    this.addRandomArmor();
                }
            }
        }

        this.world.setEntityState(this, (byte) 3);
    }

    protected void nr() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        List entitiesWithinAABBExcludingEntity =
                this.world.getEntitiesWithinAABBExcludingEntity(
                        this, this.getEntityBoundingBox().expand(0.2F, 0.0, 0.2F));
        String[] astring = astring1;
        if (entitiesWithinAABBExcludingEntity != null
                && !entitiesWithinAABBExcludingEntity.isEmpty()) {
            int i = 0;
            int j = entitiesWithinAABBExcludingEntity.size();

            while (i < j) {
                Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
                if (entity.canBePushed()) {
                    this.collideWithEntity(entity);
                }

                i++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public boolean isServerWorld() {
        return !this.world.isRemote;
    }

    public CombatTracker getCombatTracker() {
        return this._combatTracker;
    }

    public float getRotationYawHead() {
        return this.rotationYawHead;
    }

    public boolean isPotionActive(Potion potion) {
        return this.activePotionsMap.containsKey(potion.id);
    }

    protected void jump() {
        this.motionY = this.getJumpUpwardsMotion();
        if (this.isPotionActive(Potion.JUMP)) {
            this.motionY =
                    this.motionY
                            + (this.getActivePotionEffect(Potion.JUMP).getAmplifier() + 1) * 0.1F;
        }

        if (this.isSprinting()) {
            float f = this.rotationYaw * (float) (Math.PI / 180.0);
            this.motionX = this.motionX - MathHelper.sin(f) * 0.2F;
            this.motionZ = this.motionZ + MathHelper.cos(f) * 0.2F;
        }

        this.isAirBorne = true;
    }

    public void setHealth(float f) {
        this.dataWatcher.updateObject(6, MathHelper.clamp(f, 0.0F, this.getMaxHealth()));
    }

    public LivingEntity getAITarget() {
        return this.entityLivingToAttack;
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }

    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }

    public PotionEffect getActivePotionEffect(Potion potion) {
        return ((net.minecraft.potion.PotionEffect) this.activePotionsMap.get(potion.id));
    }

    protected float func_110146_f(float f, float f1) {
        float f2 = MathHelper.wrapAngleTo180(f - this.renderYawOffset);
        this.renderYawOffset += f2 * 0.3F;
        float f3 = MathHelper.wrapAngleTo180(this.rotationYaw - this.renderYawOffset);
        int i = !(f3 < -90.0F) && !(f3 >= 90.0F) ? 0 : 1;
        if (f3 < -75.0F) {
            f3 = -75.0F;
        }

        if (f3 >= 75.0F) {
            f3 = 75.0F;
        }

        this.renderYawOffset = this.rotationYaw - f3;
        if (f3 * f3 > 2500.0F) {
            this.renderYawOffset += f3 * 0.2F;
        }

        if (i != 0) {
            f1 *= -1.0F;
        }

        return f1;
    }

    public Vec3 getLookVec() {
        return this.getLook(1.0F);
    }

    public void setRotationYawHead(float beOValue) {
        this.rotationYawHead = beOValue;
    }

    public LivingEntity(World worldValue) {
        super(worldValue);
        this.previousEquipment = new ItemStack[5];
        this.maxHurtResistantTime = 20;
        this.jumpMovementFactor = 0.02F;
        this.potionsNeedUpdate = true;
        this.applyEntityAttributes();
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = true;
        this.aJT = (float) ((java.lang.Math.random() + 1.0) * 0.01F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.aIV = (float) java.lang.Math.random() * 12398.0F;
        this.rotationYaw = (float) (java.lang.Math.random() * java.lang.Math.PI * 2.0);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.6F;
    }

    public Team getTeam() {
        return this.world.getScoreboard().getPlayersTeam(this.getUniqueId().toString());
    }

    public void mountEntity(Entity entity) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.ridingEntity != null && entity == null) {
            if (!this.world.isRemote) {
                this.dismountEntity(this.ridingEntity);
            }

            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }

            this.ridingEntity = null;
            if (astring == null) {
                return;
            }
        }

        super.mountEntity(entity);
    }

    public boolean isPotionApplicable(PotionEffect potionEffect) {
        if (this.getCreatureAttribute() != CreatureAttribute.UNDEAD) {
            return true;
        }

        int potionID = potionEffect.getPotionID();
        return potionID != Potion.REGENERATION.id && potionID != Potion.POISON.id;
    }

    protected boolean isPlayer() {
        return false;
    }

    public Vec3 getLook(float p171_0) {
        if (this instanceof net.minecraft.client.entity.LocalPlayer) {
            return super.getLook(p171_0);
        }

        if (p171_0 == 1.0F) {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
        }

        float f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p171_0;
        float f2 =
                this.prevRotationYawHead
                        + (this.rotationYawHead - this.prevRotationYawHead) * p171_0;
        return this.getVectorForRotation(f1, f2);
    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F;
    }

    public void dismountEntity(Entity entity) {
        double posXValue = entity.posX;
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        double d1 = entity.getEntityBoundingBox().minY + entity.height;
        double posZValue = entity.posZ;
        String[] astring = astring1;
        byte b0 = 1;
        int i = -b0;

        label41:
        while (i <= b0) {
            int j = -b0;

            while (true) {
                if (j < b0) {
                    if (i != 0 || j != 0) {
                        int k = (int) (this.posX + i);
                        int l = (int) (this.posZ + j);
                        BoundingBox boundingBoxValue =
                                this.getEntityBoundingBox().offset(i, 1.0, j);
                        if (this.world.getCollisionBoxes(boundingBoxValue).isEmpty()) {
                            if (World.doesBlockHaveSolidTopSurface(
                                    this.world, new BlockPos(k, (int) this.posY, l))) {
                                this.setPositionAndUpdate(
                                        this.posX + i, this.posY + 1.0, this.posZ + j);
                                return;
                            }

                            if (World.doesBlockHaveSolidTopSurface(
                                            this.world, new BlockPos(k, (int) this.posY - 1, l))
                                    || this.world
                                                    .getBlockState(
                                                            new BlockPos(k, (int) this.posY - 1, l))
                                                    .getBlock()
                                                    .getMaterial()
                                            == Material.water) {
                                posXValue = this.posX + i;
                                d1 = this.posY + 1.0;
                                posZValue = this.posZ + j;
                            }
                        }
                    }

                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label41;
                }
                break;
            }
        }

        this.setPositionAndUpdate(posXValue, d1, posZValue);
    }

    public void knockBack(Entity entity, float f2, double d0, double d1) {
        if (this.rand.nextDouble()
                >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
                        .getAttributeValue()) {
            this.isAirBorne = true;
            float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
            float f1 = 0.4F;
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            this.motionX -= d0 / f * f1;
            this.motionY += f1;
            this.motionZ -= d1 / f * f1;
            if (this.motionY > 0.4F) {
                this.motionY = 0.4F;
            }
        }
    }

    public void handleHealthUpdate(byte b0) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (b0 == 2) {
            this.limbSwingAmount = 1.5F;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.hurtTime = this.maxHurtTime = 10;
            this.attackedAtYaw = 0.0F;
            String text = this.getHurtSound();
            if (text != null) {
                this.playSound(
                        this.getHurtSound(),
                        this.getSoundVolume(),
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.attackEntityFrom(DamageSource.generic, 0.0F);
            if (astring == null) {
                return;
            }
        }

        if (b0 == 3) {
            String textValue = this.getDeathSound();
            if (textValue != null) {
                this.playSound(
                        this.getDeathSound(),
                        this.getSoundVolume(),
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.setHealth(0.0F);
            this.onDeath(DamageSource.generic);
            if (astring == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public float applyArmorCalculations(DamageSource damageSource, float p171_1) {
        if (!damageSource.isUnblockable()) {
            int i = 25 - this.getTotalArmorValue();
            float f1 = p171_1 * i;
            this.damageArmor(p171_1);
            p171_1 = f1 / 25.0F;
        }

        return p171_1;
    }

    // region String decoding support

    // endregion

    public final float getMaxHealth() {
        return (float)
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
    }

    protected void entityInit() {
        this.dataWatcher.addObject(7, 0);
        this.dataWatcher.addObject(8, (byte) 0);
        this.dataWatcher.addObject(9, (byte) 0);
        this.dataWatcher.addObject(6, 1.0F);
    }

    public abstract ItemStack getEquipmentInSlot(int i);

    protected void dropFewItems(boolean flag, int i) {}

    public boolean method_b417d7500ec59d69e373() {
        return false;
    }

    public final float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(6);
    }

    protected void setBeenAttacked() {
        this.velocityChanged =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.rand.nextDouble()
                                        >= this.getEntityAttribute(
                                                        SharedMonsterAttributes.knockbackResistance)
                                                .getAttributeValue()
                                ? 1
                                : 0));
    }

    protected void updateAITick() {
        this.motionY += 0.04F;
    }

    public BaseAttributeMap getAttributeMap() {
        if (this.attributeMap == null) {
            this.attributeMap = new net.minecraft.entity.ai.attributes.ServersideAttributeMap();
        }

        return this.attributeMap;
    }

    private int getArmSwingAnimationEnd() {
        return this.isPotionActive(Potion.DIG_SPEED)
                ? 6 - (1 + this.getActivePotionEffect(Potion.DIG_SPEED).getAmplifier())
                : (this.isPotionActive(Potion.DIG_SLOWDOWN)
                        ? 6
                                + (1
                                                + this.getActivePotionEffect(Potion.DIG_SLOWDOWN)
                                                        .getAmplifier())
                                        * 2
                        : 6);
    }

    public boolean isEntityAlive() {
        return !this.isDead && this.getHealth() > 0.0F;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float bezValue) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (this.world.isRemote) {
            return false;
        }

        this.NJ = 0;
        if (this.getHealth() <= 0.0F) {
            return false;
        }

        if (damageSource.isFireDamage() && this.isPotionActive(Potion.FIRE_RESISTANCE)) {
            return false;
        }

        if ((damageSource == DamageSource.anvil || damageSource == DamageSource.fallingBlock)
                && this.getEquipmentInSlot(4) != null) {
            this.getEquipmentInSlot(4)
                    .damageItem(
                            (int) (bezValue * 4.0F + this.rand.nextFloat() * bezValue * 2.0F),
                            this);
            bezValue *= 0.75F;
        }

        byte b0;
        label92:
        {
            this.limbSwingAmount = 1.5F;
            b0 = 1;
            if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
                if (bezValue <= this.lastDamage) {
                    return false;
                }

                this.damageEntity(damageSource, bezValue - this.lastDamage);
                this.lastDamage = bezValue;
                b0 = 0;
                if (astring == null) {
                    break label92;
                }
            }

            this.lastDamage = bezValue;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(damageSource, bezValue);
            this.hurtTime = this.maxHurtTime = 10;
        }

        Entity entity;
        this.attackedAtYaw = 0.0F;
        entity = damageSource.getEntity();
        if (entity != null) {
            if (entity instanceof LivingEntity) {
                this.setRevengeTarget((LivingEntity) entity);
            }

            if (entity instanceof PlayerEntity) {
                this.recentlyHit = 100;
                this.attackingPlayer = (PlayerEntity) entity;
                if (!(astring == null)) {

                    if (entity instanceof EntityWolf) {
                        EntityWolf entityWolf = (EntityWolf) entity;
                        if (entityWolf.isTamed()) {
                            this.recentlyHit = 100;
                            this.attackingPlayer = null;
                        }
                    }
                }
            } else {

                if (entity instanceof EntityWolf) {
                    EntityWolf entityWolf = (EntityWolf) entity;
                    if (entityWolf.isTamed()) {
                        this.recentlyHit = 100;
                        this.attackingPlayer = null;
                    }
                }
            }
        }

        if (b0 != 0) {
            this.world.setEntityState(this, (byte) 2);
            if (damageSource != DamageSource.drown) {
                this.setBeenAttacked();
            }

            if (entity != null) {
                double deltaPosX = entity.posX - this.posX;
                double deltaPosZ = entity.posZ - this.posZ;

                while (deltaPosX * deltaPosX + deltaPosZ * deltaPosZ < 1.0E-4) {
                    deltaPosX = (Math.random() - Math.random()) * 0.01;
                    deltaPosZ = (Math.random() - Math.random()) * 0.01;
                    if (astring != null) {
                        break;
                    }
                }

                this.attackedAtYaw =
                        (float)
                                (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI
                                        - this.rotationYaw);
                this.knockBack(entity, bezValue, deltaPosX, deltaPosZ);
                if (!(astring == null)) {

                    this.attackedAtYaw = (int) (Math.random() * 2.0) * 180;
                }
            } else {

                this.attackedAtYaw = (int) (Math.random() * 2.0) * 180;
            }
        }

        if (this.getHealth() <= 0.0F) {
            String text = this.getDeathSound();
            if (b0 != 0 && text != null) {
                this.playSound(text, this.getSoundVolume(), this.Jr());
            }

            this.onDeath(damageSource);
            if (astring == null) {
                return true;
            }
        }

        String textValue = this.getHurtSound();
        if (b0 != 0 && textValue != null) {
            this.playSound(textValue, this.getSoundVolume(), this.Jr());
        }

        return true;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setFloat("HealF", this.getHealth());
        compoundTag.setShort("Health", (short) Math.ceil(this.getHealth()));
        compoundTag.setShort("HurtTime", (short) this.hurtTime);
        compoundTag.setInteger("HurtByTimestamp", this.revengeTimer);
        compoundTag.setShort("DeathTime", (short) this.deathTime);
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        compoundTag.setFloat("AbsorptionAmount", this.getAbsorptionAmount());
        ItemStack[] itemStackArray = this.getInventory();
        int length = itemStackArray.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            ItemStack itemStack = itemStackArray[j];
            if (itemStack != null) {
                this.attributeMap.method_09d9b3c802fb50258978(itemStack.getAttributeModifiers());
            }

            j++;
            if (astring != null) {
                break;
            }
        }

        compoundTag.setTag(
                "Attributes",
                SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));

        for (ItemStack itemStackValue : this.getInventory()) {
            if (itemStackValue != null) {
                this.attributeMap.applyAttributeModifiers(itemStackValue.getAttributeModifiers());
            }

            if (astring != null) {
                break;
            }
        }

        if (!this.activePotionsMap.isEmpty()) {
            NBTTagList nbtTagList = new NBTTagList();

            for (java.util.Iterator<?> __recoveryIterator171_73182 =
                            (this.activePotionsMap.values()).iterator();
                    (__recoveryIterator171_73182).hasNext(); ) {
                PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_73182).next();
                nbtTagList.appendTag(potionEffect.writeCustomPotionEffectToNBT(new CompoundTag()));
                if (astring != null) {
                    break;
                }
            }

            compoundTag.setTag("ActiveEffects", nbtTagList);
        }
    }

    public int getLastAttackerTime() {
        return this.lastAttackerTime;
    }

    protected void updateEntityActionState() {}

    public boolean canEntityBeSeen(Entity entity) {
        return this.world.rayTraceBlocks(
                        new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ),
                        new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ))
                == null;
    }

    public void sendEnterCombat() {}

    public int IJ() {
        return this.NJ;
    }

    protected String getDeathSound() {
        return "game.neutral.die";
    }

    public abstract ItemStack[] getInventory();

    public boolean isChild() {
        return false;
    }

    public float getSwingProgress(float p171_0) {
        float f1 = this.swingProgress - this.prevSwingProgress;
        if (f1 < 0.0F) {
            f1++;
        }

        return this.prevSwingProgress + f1 * p171_0;
    }

    public void removePotionEffectClient(int p171_0) {
        this.activePotionsMap.remove(p171_0);
    }

    public abstract ItemStack getCurrentArmor(int i);

    public float applyPotionDamageCalculations(DamageSource damageSource, float f) {
        if (damageSource.isDamageAbsolute()) {
            return f;
        }

        if (this.isPotionActive(Potion.RESISTANCE) && damageSource != DamageSource.outOfWorld) {
            int i = (this.getActivePotionEffect(Potion.RESISTANCE).getAmplifier() + 1) * 5;
            int j = 25 - i;
            float f1 = f * j;
            f = f1 / 25.0F;
        }

        if (f <= 0.0F) {
            return 0.0F;
        }

        int k = EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), damageSource);
        if (k > 20) {
            k = 20;
        }

        if (k > 0) {
            int l = 25 - k;
            float f2 = f * l;
            f = f2 / 25.0F;
        }

        return f;
    }

    protected void updateFallState(double d0, boolean flag, Block block, BlockPos blockPos) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }

        if (!this.world.isRemote && this.fallDistance > 3.0F && flag) {
            BlockState blockState = this.world.getBlockState(blockPos);
            Block blockValue = blockState.getBlock();
            float f = MathHelper.ceil(this.fallDistance - 3.0F);
            if (blockValue.getMaterial() != Material.AIR) {
                double d1 = Math.min(0.2F + f / 15.0F, 10.0F);
                if (d1 > 2.5) {
                    d1 = 2.5;
                }

                int i = (int) (150.0 * d1);
                ((WorldServer) this.world)
                        .spawnParticle(
                                EnumParticleTypes.BLOCK_DUST,
                                this.posX,
                                this.posY,
                                this.posZ,
                                i,
                                0.0,
                                0.0,
                                0.0,
                                0.15F,
                                new int[] {Block.getStateId(blockState)});
            }
        }

        super.updateFallState(d0, flag, block, blockPos);
    }

    public void onUpdate() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.onUpdate();
        String[] astring = astring1;
        if (!this.world.isRemote) {
            int arrowCountInEntity = this.getArrowCountInEntity();
            if (arrowCountInEntity > 0) {
                if (this.bex <= 0) {
                    this.bex = 20 * (30 - arrowCountInEntity);
                }

                this.bex--;
                if (this.bex <= 0) {
                    this.setArrowCountInEntity(arrowCountInEntity - 1);
                }
            }

            int j = 0;

            while (j < 5) {
                ItemStack itemStack = this.previousEquipment[j];
                ItemStack itemStackValue = this.getEquipmentInSlot(j);
                if (!ItemStack.areItemStacksEqual(itemStackValue, itemStack)) {
                    ((WorldServer) this.world)
                            .getEntityTracker()
                            .sendToAllTrackingEntity(
                                    this,
                                    new S04PacketEntityEquipment(
                                            this.getEntityId(), j, itemStackValue));
                    if (itemStack != null) {
                        this.attributeMap.method_09d9b3c802fb50258978(
                                itemStack.getAttributeModifiers());
                    }

                    if (itemStackValue != null) {
                        this.attributeMap.applyAttributeModifiers(
                                itemStackValue.getAttributeModifiers());
                    }

                    this.previousEquipment[j] =
                            itemStackValue == null ? null : itemStackValue.copy();
                }

                j++;
                if (astring != null) {
                    break;
                }
            }

            if (this.ticksExisted % 20 == 0) {
                this.getCombatTracker().reset();
            }
        }

        this.onLivingUpdate();
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f = (float) (d0 * d0 + d1 * d1);
        float aIWValue = this.renderYawOffset;
        float f2 = 0.0F;
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        float f3 = 0.0F;
        if (f > 0.0025000002F) {
            f3 = 1.0F;
            f2 = (float) Math.sqrt(f) * 3.0F;
            aIWValue = (float) Math.atan2(d1, d0) * 180.0F / (float) Math.PI - 90.0F;
        }

        if (this.swingProgress > 0.0F) {
            aIWValue = this.rotationYaw;
        }

        if (!this.onGround) {
            f3 = 0.0F;
        }

        this.onGroundSpeedFactor =
                this.onGroundSpeedFactor + (f3 - this.onGroundSpeedFactor) * 0.3F;
        this.world.theProfiler.startSection("headTurn");
        f2 = this.func_110146_f(aIWValue, f2);
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("rangeChecks");

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
            this.prevRenderYawOffset -= 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
            this.prevRenderYawOffset += 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
            this.prevRotationYawHead -= 360.0F;
            if (astring != null) {
                break;
            }
        }

        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
            this.prevRotationYawHead += 360.0F;
            if (astring != null) {
                break;
            }
        }

        this.world.theProfiler.endSection();
        this.beI += f2;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SharedMonsterAttributes.setLegacyEntityFlowMarker(new String[3]);
        }
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    public boolean isOnTeam(Team team) {
        return this.getTeam() != null && this.getTeam().isSameTeam(team);
    }

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 0;
    }

    protected void damageEntity(DamageSource damageSource, float p171_1) {
        if (!this.isEntityInvulnerable(damageSource)) {
            p171_1 = this.applyArmorCalculations(damageSource, p171_1);
            p171_1 = this.applyPotionDamageCalculations(damageSource, p171_1);
            float f1 = p171_1;
            p171_1 = java.lang.Math.max(p171_1 - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f1 - p171_1));
            if (p171_1 != 0.0F) {
                float health = this.getHealth();
                this.setHealth(health - p171_1);
                this.getCombatTracker().trackDamage(damageSource, health, p171_1);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - p171_1);
            }
        }
    }

    public boolean isOnLadder() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        Block block = this.world.getBlockState(new BlockPos(i, j, k)).getBlock();
        return (block == Blocks.ladder || block == Blocks.vine)
                && (!(this instanceof PlayerEntity) || !((PlayerEntity) this).isSpectator());
    }

    public IAttributeInstance getEntityAttribute(IAttribute iAttribute) {
        return this.getAttributeMap().getAttributeInstance(iAttribute);
    }

    public boolean isOnSameTeam(LivingEntity livingEntity) {
        return this.isOnTeam(livingEntity.getTeam());
    }

    protected String getFallSoundString(int i) {
        return i > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }

    public void setRevengeTarget(LivingEntity beuValue) {
        this.entityLivingToAttack = beuValue;
        this.revengeTimer = this.ticksExisted;
    }

    protected void cQ() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.deathTime++;
        String[] astring = astring1;
        if (this.deathTime == 20) {
            if (!this.world.isRemote
                    && (this.recentlyHit > 0 || this.isPlayer())
                    && this.canDropLoot()
                    && this.world.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                int experiencePoints = this.getExperiencePoints(this.attackingPlayer);

                while (experiencePoints > 0) {
                    int j = EntityXPOrb.method_c640c0ab3979b883b99e(experiencePoints);
                    experiencePoints -= j;
                    this.world.spawnEntityInWorld(
                            new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                    if (astring != null) {
                        break;
                    }
                }
            }

            this.setDead();
            int k = 0;

            while (k < 20) {
                double d2 = this.rand.nextGaussian() * 0.02;
                double d0 = this.rand.nextGaussian() * 0.02;
                double d1 = this.rand.nextGaussian() * 0.02;
                this.world.spawnParticle(
                        EnumParticleTypes.EXPLOSION_NORMAL,
                        this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                        this.posY + this.rand.nextFloat() * this.height,
                        this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                        d2,
                        d0,
                        d1,
                        new int[0]);
                k++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    protected boolean canDropLoot() {
        return !this.isChild();
    }

    public Random getRNG() {
        return this.rand;
    }

    protected void onNewPotionEffect(PotionEffect potionEffect) {
        this.potionsNeedUpdate = true;
        if (!this.world.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(
                    this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }

    public abstract ItemStack getHeldItem();

    protected void nG() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.activePotionsMap.keySet().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            label68:
            {
                Integer integer = (Integer) iterator.next();
                PotionEffect potionEffect =
                        ((net.minecraft.potion.PotionEffect) this.activePotionsMap.get(integer));
                if (!potionEffect.onUpdate(this)) {
                    if (this.world.isRemote) {
                        break label68;
                    }

                    iterator.remove();
                    this.onFinishedPotionEffect(potionEffect);
                    if (astring == null) {
                        break label68;
                    }
                }

                if (potionEffect.getDuration() % 600 == 0) {
                    this.onChangedPotionEffect(potionEffect, false);
                }
            }

            if (astring != null) {
                break;
            }
        }

        if (this.potionsNeedUpdate) {
            if (!this.world.isRemote) {
                this.updatePotionMetadata();
            }

            this.potionsNeedUpdate = false;
        }

        int j = this.dataWatcher.getWatchableObjectInt(7);
        int k = this.dataWatcher.getWatchableObjectByte(8) > 0 ? 1 : 0;
        if (j > 0) {
            int i;
            {
                if (!this.isInvisible()) {
                    i = remix.compat.compiler.JvmBooleanSlots.toInt(this.rand.nextBoolean());
                    if (!(astring == null)) {

                        i = this.rand.nextInt(15) == 0 ? 1 : 0;
                    }
                } else {

                    i = this.rand.nextInt(15) == 0 ? 1 : 0;
                }
            }

            if (k != 0) {
                i &= this.rand.nextInt(5) == 0 ? 1 : 0;
            }

            if (i != 0) {
                double d0 = (j >> 16 & 0xFF) / 255.0;
                double d1 = (j >> 8 & 0xFF) / 255.0;
                double d2 = (j & 0xFF) / 255.0;
                this.world.spawnParticle(
                        k != 0 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        d0,
                        d1,
                        d2,
                        new int[0]);
            }
        }
    }

    public void onEntityUpdate() {
        String[] astring;
        boolean flag;
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        astring = astring1;
        this.world.theProfiler.startSection("livingEntityBaseTick");
        flag = this instanceof PlayerEntity;
        label126:
        if (this.isEntityAlive()) {
            if (this.isEntityInsideOpaqueBlock()) {
                this.attackEntityFrom(DamageSource.inWall, 1.0F);
                if (astring == null) {
                    break label126;
                }
            }

            if (flag && !this.world.getWorldBorder().contains(this.getEntityBoundingBox())) {
                double d0 =
                        this.world.getWorldBorder().getClosestDistance(this)
                                + this.world.getWorldBorder().getDamageBuffer();
                if (d0 < 0.0) {
                    this.attackEntityFrom(
                            DamageSource.inWall,
                            Math.max(
                                    1,
                                    MathHelper.floor(
                                            -d0 * this.world.getWorldBorder().getDamageAmount())));
                }
            }
        }

        if (this.isImmuneToFire() || this.world.isRemote) {
            this.extinguish();
        }

        label110:
        {
            int j = flag && ((PlayerEntity) this).capabilities.disableDamage ? 1 : 0;
            if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water)) {
                if (!this.method_b417d7500ec59d69e373()
                        && !this.isPotionActive(Potion.WATER_BREATHING.id)
                        && j == 0) {
                    this.setAir(this.decreaseAirSupply(this.getAir()));
                    if (this.getAir() == -20) {
                        this.setAir(0);
                        int i = 0;

                        while (i < 8) {
                            float f = this.rand.nextFloat() - this.rand.nextFloat();
                            float f1 = this.rand.nextFloat() - this.rand.nextFloat();
                            float f2 = this.rand.nextFloat() - this.rand.nextFloat();
                            this.world.spawnParticle(
                                    EnumParticleTypes.WATER_BUBBLE,
                                    this.posX + f,
                                    this.posY + f1,
                                    this.posZ + f2,
                                    this.motionX,
                                    this.motionY,
                                    this.motionZ,
                                    new int[0]);
                            i++;
                            if (astring != null) {
                                break;
                            }
                        }

                        this.attackEntityFrom(DamageSource.drown, 2.0F);
                    }
                }

                if (this.world.isRemote
                        || !this.isRiding()
                        || !(this.ridingEntity instanceof LivingEntity)) {
                    break label110;
                }

                this.mountEntity(null);
                if (astring == null) {
                    break label110;
                }
            }

            this.setAir(300);
        }

        if (this.isEntityAlive() && this.isWet()) {
            this.extinguish();
        }

        this.prevCameraPitch = this.cameraPitch;
        if (this.hurtTime > 0) {
            this.hurtTime--;
        }

        if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
            this.hurtResistantTime--;
        }

        if (this.getHealth() <= 0.0F) {
            this.cQ();
        }

        {
            if (this.recentlyHit > 0) {
                this.recentlyHit--;
                if (!(astring == null)) {

                    this.attackingPlayer = null;
                }
            } else {

                this.attackingPlayer = null;
            }
        }

        if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
            this.lastAttacker = null;
        }

        if (this.entityLivingToAttack != null) {
            if (!this.entityLivingToAttack.isEntityAlive()) {
                this.setRevengeTarget(null);
                if (!(astring == null)) {

                    if (this.ticksExisted - this.revengeTimer > 100) {
                        this.setRevengeTarget(null);
                    }
                }
            } else {

                if (this.ticksExisted - this.revengeTimer > 100) {
                    this.setRevengeTarget(null);
                }
            }
        }

        this.nG();
        this.beR = this.beI;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.world.theProfiler.endSection();
    }

    public boolean isPotionActive(int p171_0) {
        return this.activePotionsMap.containsKey(p171_0);
    }

    public void setJumping(boolean beDValue) {
        this.isJumping = beDValue;
    }

    public void sendEndCombat() {}

    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == CreatureAttribute.UNDEAD;
    }

    public static String Kl() {
        return aIO;
    }

    public void setAIMoveSpeed(float beoValue) {
        this.landMovementFactor = beoValue;
    }

    public void performHurtAnimation() {
        this.hurtTime = this.maxHurtTime = 10;
        this.attackedAtYaw = 0.0F;
    }

    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(9);
    }

    public void nD() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Iterator iterator = this.activePotionsMap.keySet().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            PotionEffect potionEffect =
                    ((net.minecraft.potion.PotionEffect) this.activePotionsMap.get(integer));
            if (!this.world.isRemote) {
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }

            if (astring != null) {
                break;
            }
        }
    }

    public void onLivingUpdate() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.jumpTicks > 0) {
            this.jumpTicks--;
        }

        {
            if (this.newPosRotationIncrements > 0) {
                double d0 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
                double d1 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
                double d2 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
                double d3 = MathHelper.wrapAngleTo180(this.newRotationYaw - this.rotationYaw);
                this.rotationYaw = (float) (this.rotationYaw + d3 / this.newPosRotationIncrements);
                this.rotationPitch =
                        (float)
                                (this.rotationPitch
                                        + (this.newRotationPitch - this.rotationPitch)
                                                / this.newPosRotationIncrements);
                this.newPosRotationIncrements--;
                this.setPosition(d0, d1, d2);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                if (!(astring == null)) {

                    if (!this.isServerWorld()) {
                        this.motionX *= 0.98;
                        this.motionY *= 0.98;
                        this.motionZ *= 0.98;
                    }
                }
            } else {

                if (!this.isServerWorld()) {
                    this.motionX *= 0.98;
                    this.motionY *= 0.98;
                    this.motionZ *= 0.98;
                }
            }
        }

        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }

        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }

        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }

        {
            this.world.theProfiler.startSection("ai");
            if (this.isMovementBlocked()) {
                this.isJumping = false;
                this.moveStrafing = 0.0F;
                this.moveForward = 0.0F;
                this.randomYawVelocity = 0.0F;
                if (!(astring == null)) {

                    if (this.isServerWorld()) {
                        this.world.theProfiler.startSection("newAi");
                        this.updateEntityActionState();
                        this.world.theProfiler.endSection();
                    }
                }
            } else {

                if (this.isServerWorld()) {
                    this.world.theProfiler.startSection("newAi");
                    this.updateEntityActionState();
                    this.world.theProfiler.endSection();
                }
            }
        }

        label52:
        {
            this.world.theProfiler.endSection();
            this.world.theProfiler.startSection("jump");
            if (this.isJumping) {
                if (this.isInWater()) {
                    this.updateAITick();
                    if (astring == null) {
                        break label52;
                    }
                }

                if (this.isInLava()) {
                    this.handleJumpLava();
                    if (astring == null) {
                        break label52;
                    }
                }

                if (!this.onGround || this.jumpTicks != 0) {
                    break label52;
                }

                this.jump();
                this.jumpTicks = 10;
                if (astring == null) {
                    break label52;
                }
            }

            this.jumpTicks = 0;
        }

        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98F;
        this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("push");
        if (!this.world.isRemote) {
            this.nr();
        }

        this.world.theProfiler.endSection();
    }

    protected void applyEntityAttributes() {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
    }

    public void setAbsorptionAmount(float bemValue) {
        if (bemValue < 0.0F) {
            bemValue = 0.0F;
        }

        this.absorptionAmount = bemValue;
    }

    protected void updatePotionMetadata() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.activePotionsMap.isEmpty()) {
            this.resetPotionEffectMetadata();
            this.setInvisible(false);
            if (astring == null) {
                return;
            }
        }

        int i = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
        this.dataWatcher.updateObject(
                8, (byte) (PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0));
        this.dataWatcher.updateObject(7, i);
        this.setInvisible(this.isPotionActive(Potion.INVISIBILITY.id));
    }

    public void setLastAttacker(Entity entity) {
        {
            String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            if (entity instanceof LivingEntity) {
                this.lastAttacker = (LivingEntity) entity;
                if (!(astring == null)) {

                    this.lastAttacker = null;
                }
            } else {

                this.lastAttacker = null;
            }
        }

        this.lastAttackerTime = this.ticksExisted;
    }
}
