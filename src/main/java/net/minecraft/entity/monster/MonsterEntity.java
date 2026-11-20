package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class MonsterEntity extends EntityCreature implements IMob {

    protected final EntityAIBase field_175455_a =
            new EntityAIAvoidEntity(this, new ExplodingCreeperPredicate(this), 4.0F, 1.0, 2.0);

    private static Entity[] bgN;

    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL
                && this.isValidLightLevel()
                && super.getCanSpawnHere();
    }

    public static Entity[] GU() {
        return bgN;
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }

    public static void b(Entity[] bgNValue) {
        bgN = bgNValue;
    }

    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }

    // region String decoding support

    // endregion

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }

    protected boolean canDropLoot() {
        return true;
    }

    protected String getSwimSound() {
        return "game.hostile.swim";
    }

    protected boolean isValidLightLevel() {
        BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (this.world.getLightFor(EnumSkyBlock.SKY, blockPos) > this.rand.nextInt(32)) {
            return false;
        }

        int lightFromNeighbors = this.world.getLightFromNeighbors(blockPos);
        if (this.world.isThundering()) {
            int j = this.world.getSkylightSubtracted();
            this.world.setSkylightSubtracted(10);
            lightFromNeighbors = this.world.getLightFromNeighbors(blockPos);
            this.world.setSkylightSubtracted(j);
        }

        return lightFromNeighbors <= this.rand.nextInt(8);
    }

    protected String getHurtSound() {
        return "game.hostile.hurt";
    }

    public MonsterEntity(World worldValue) {
        super(worldValue);
        this.experienceValue = 5;
    }

    public boolean attackEntityAsMob(Entity entity) {
        float attributeValue =
                (float)
                        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                                .getAttributeValue();
        int i = 0;
        if (entity instanceof LivingEntity) {
            attributeValue +=
                    EnchantmentHelper.getEnchantmentDamageBonus(
                            this.getHeldItem(), ((LivingEntity) entity).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), attributeValue);
        if (flag) {
            if (i > 0) {
                entity.addVelocity(
                        -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F,
                        0.1,
                        MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * i * 0.5F);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);
            if (j > 0) {
                entity.setFire(j * 4);
            }

            this.applyEnchantments(this, entity);
        }

        return flag;
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return 0.5F - this.world.getLightBrightness(blockPos);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!super.attackEntityFrom(damageSource, f)) {
            return false;
        }

        Entity entity = damageSource.getEntity();
        return this.riddenByEntity != entity && this.ridingEntity != entity ? true : true;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables
    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[1];
        b(aclass_b6df81f333fa31dc98ea);
    }

    // endregion

    public void onLivingUpdate() {
        this.nx();
        float brightness = this.getBrightness(1.0F);
        if (brightness > 0.5F) {
            this.NJ += 2;
        }

        super.onLivingUpdate();
    }

    protected String getFallSoundString(int i) {
        return i > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    protected String getDeathSound() {
        return "game.hostile.die";
    }

    // region String decoding support

    // endregion

    // Helper used only by this class.
    static class ExplodingCreeperPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.monster.MonsterEntity field_179912_a;

        public boolean apply(java.lang.Object object) {
            return this.func_179911_a((net.minecraft.entity.Entity) object);
        }

        ExplodingCreeperPredicate(net.minecraft.entity.monster.MonsterEntity bsWValue) {
            this.field_179912_a = bsWValue;
        }

        public boolean func_179911_a(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.monster.EntityCreeper
                    && ((net.minecraft.entity.monster.EntityCreeper) entity).getCreeperState() > 0;
        }
    }
}
