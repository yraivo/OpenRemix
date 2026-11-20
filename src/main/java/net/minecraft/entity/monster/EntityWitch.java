package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class EntityWitch extends MonsterEntity implements IRangedAttackMob {
    private int witchAttackTimer;
    private static UUID MODIFIER_UUID;
    private static Item[] witchDrops;

    private static AttributeModifier MODIFIER;

    public void handleHealthUpdate(byte b0) {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (b0 == 15) {
            int j = 0;

            while (j < this.rand.nextInt(35) + 10) {
                this.world.spawnParticle(
                        EnumParticleTypes.SPELL_WITCH,
                        this.posX + this.rand.nextGaussian() * 0.13F,
                        this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.13F,
                        this.posZ + this.rand.nextGaussian() * 0.13F,
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                j++;
                if (i == 0) {
                    break;
                }
            }

            if (i != 0) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    // region String decoding support

    // endregion

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, (byte) 0);
    }

    public void onLivingUpdate() {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (!this.world.isRemote) {
            label93:
            {
                if (this.getAggressive()) {
                    if (this.witchAttackTimer-- > 0) {
                        break label93;
                    }

                    this.method_19fd2cb92de7572f3a6f(false);
                    ItemStack itemStack = this.getHeldItem();
                    this.setCurrentItemOrArmor(0, (ItemStack) null);
                    if (itemStack != null && itemStack.getItem() == Items.potionitem) {
                        List list = ItemPotion.getPotionEffects(itemStack);
                        if (list != null) {
                            for (java.util.Iterator<?> __recoveryIterator171_3337 =
                                            (list).iterator();
                                    (__recoveryIterator171_3337).hasNext(); ) {
                                PotionEffect potionEffect =
                                        (PotionEffect) (__recoveryIterator171_3337).next();
                                this.addPotionEffect(new PotionEffect(potionEffect));
                                if (i != 0) {
                                    break;
                                }
                            }
                        }
                    }

                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                            .removeModifier(net.minecraft.entity.monster.EntityWitch.MODIFIER);
                    if (i == 0) {
                        break label93;
                    }
                }

                short short1;
                label96:
                {
                    short1 = -1;
                    if (this.rand.nextFloat() < 0.15F
                            && this.isInsideOfMaterial(Material.water)
                            && !this.isPotionActive(Potion.WATER_BREATHING)) {
                        short1 = 8237;
                        if (i == 0) {
                            break label96;
                        }
                    }

                    if (this.rand.nextFloat() < 0.15F
                            && this.isBurning()
                            && !this.isPotionActive(Potion.FIRE_RESISTANCE)) {
                        short1 = 16307;
                        if (i == 0) {
                            break label96;
                        }
                    }

                    if (this.rand.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
                        short1 = 16341;
                        if (i == 0) {
                            break label96;
                        }
                    }

                    if (this.rand.nextFloat() < 0.25F
                            && this.getAttackTarget() != null
                            && !this.isPotionActive(Potion.MOVE_SPEED)
                            && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                        short1 = 16274;
                        if (i == 0) {
                            break label96;
                        }
                    }

                    if (this.rand.nextFloat() < 0.25F
                            && this.getAttackTarget() != null
                            && !this.isPotionActive(Potion.MOVE_SPEED)
                            && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                        short1 = 16274;
                    }
                }

                if (short1 > -1) {
                    this.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, short1));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.method_19fd2cb92de7572f3a6f(true);
                    IAttributeInstance entityAttribute =
                            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                    entityAttribute.removeModifier(net.minecraft.entity.monster.EntityWitch.MODIFIER);
                    entityAttribute.applyModifier(net.minecraft.entity.monster.EntityWitch.MODIFIER);
                }
            }

            if (this.rand.nextFloat() < 7.5E-4F) {
                this.world.setEntityState(this, (byte) 15);
            }
        }

        super.onLivingUpdate();
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.entity.monster.EntityWitch.MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        net.minecraft.entity.monster.EntityWitch.MODIFIER =
                new AttributeModifier(
                                net.minecraft.entity.monster.EntityWitch.MODIFIER_UUID, "Drinking speed penalty", -0.25, 0)
                        .setSaved(false);
        net.minecraft.entity.monster.EntityWitch.witchDrops =
                new Item[] {
                    Items.glowstone_dust,
                    Items.sugar,
                    Items.redstone,
                    Items.spider_eye,
                    Items.glass_bottle,
                    Items.gunpowder,
                    Items.stick,
                    Items.stick
                };
    }

    protected String getHurtSound() {
        return null;
    }

    public float applyPotionDamageCalculations(DamageSource damageSource, float f) {
        f = super.applyPotionDamageCalculations(damageSource, f);
        if (damageSource.getEntity() == this) {
            f = 0.0F;
        }

        if (damageSource.isMagicDamage()) {
            f = (float) (f * 0.15);
        }

        return f;
    }

    protected String getLivingSound() {
        return null;
    }

    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f1) {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (!this.getAggressive()) {
            EntityPotion entityPotion;
            double d1;
            double d2;
            double d3;
            float f;
            label32:
            {
                entityPotion = new EntityPotion(this.world, this, 32732);
                double d0 = livingEntity.posY + livingEntity.getEyeHeight() - 1.1F;
                entityPotion.rotationPitch -= -20.0F;
                d1 = livingEntity.posX + livingEntity.motionX - this.posX;
                d2 = d0 - this.posY;
                d3 = livingEntity.posZ + livingEntity.motionZ - this.posZ;
                f = MathHelper.sqrt(d1 * d1 + d3 * d3);
                if (f >= 8.0F && !livingEntity.isPotionActive(Potion.MOVE_SLOWDOWN)) {
                    entityPotion.setPotionDamage(32698);
                    if (i != 0) {
                        break label32;
                    }
                }

                if (livingEntity.getHealth() >= 8.0F
                        && !livingEntity.isPotionActive(Potion.POISON)) {
                    entityPotion.setPotionDamage(32660);
                    if (i != 0) {
                        break label32;
                    }
                }

                if (f <= 3.0F
                        && !livingEntity.isPotionActive(Potion.WEAKNESS)
                        && this.rand.nextFloat() < 0.25F) {
                    entityPotion.setPotionDamage(32696);
                }
            }

            entityPotion.setThrowableHeading(d1, d2 + f * 0.2F, d3, 0.75F, 8.0F);
            this.world.spawnEntityInWorld(entityPotion);
        }
    }

    protected void dropFewItems(boolean flag, int i) {
        int k = this.rand.nextInt(3) + 1;
        int k1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int l = 0;
        int j = k1;

        while (l < k) {
            int i1 = this.rand.nextInt(3);
            Item item =
                    net.minecraft.entity.monster.EntityWitch.witchDrops[
                            this.rand.nextInt(net.minecraft.entity.monster.EntityWitch.witchDrops.length)];
            if (i > 0) {
                i1 += this.rand.nextInt(i + 1);
            }

            int j1 = 0;

            while (true) {
                if (j1 < i1) {
                    this.dropItem(item, 1);
                    j1++;
                    if (j == 0) {
                        continue;
                    }
                }

                l++;
                if (j != 0) {
                    return;
                }
                break;
            }
        }
    }

    public void method_19fd2cb92de7572f3a6f(boolean flag) {
        this.getDataWatcher().updateObject(21, (byte) (flag ? 1 : 0));
    }

    public EntityWitch(World worldValue) {
        super(worldValue);
        this.setSize(0.6F, 1.95F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
    }

    public float getEyeHeight() {
        return 1.62F;
    }

    public boolean getAggressive() {
        return this.getDataWatcher().getWatchableObjectByte(21) == 1;
    }

    protected String getDeathSound() {
        return null;
    }
}
