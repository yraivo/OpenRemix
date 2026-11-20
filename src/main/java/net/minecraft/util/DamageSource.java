package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.world.Explosion;

public class DamageSource {
    public static DamageSource anvil;
    private boolean fireDamage;
    private float hungerDamage = 0.3F;
    public static DamageSource lightningBolt;
    public String damageType;
    public static DamageSource onFire;
    public static DamageSource inWall;
    private boolean magicDamage;
    private boolean isDamageAllowedInCreativeMode;
    private boolean difficultyScaled;
    public static DamageSource drown;
    public static DamageSource wither;
    public static DamageSource outOfWorld;
    public static DamageSource fallingBlock;
    public static DamageSource magic;
    public static DamageSource inFire;
    public static DamageSource generic;
    private boolean explosion;
    public static DamageSource lava;
    private boolean projectile;

    public static DamageSource starve;
    public static DamageSource fall;
    private boolean damageIsAbsolute;
    public static DamageSource cactus;
    private boolean isUnblockable;

    public String getDamageType() {
        return this.damageType;
    }

    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }

    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }

    public Entity getEntity() {
        return null;
    }

    public boolean isProjectile() {
        return this.projectile;
    }

    public Entity getSourceOfDamage() {
        return this.getEntity();
    }

    public static DamageSource causeMobDamage(LivingEntity livingEntity) {
        return new EntityDamageSource("mob", livingEntity);
    }

    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }

    public boolean isFireDamage() {
        return this.fireDamage;
    }

    protected DamageSource(String text) {
        this.damageType = text;
    }

    public static DamageSource causeThornsDamage(Entity entity) {
        return new EntityDamageSource("thorns", entity).setIsThornsDamage().setMagicDamage();
    }

    public static DamageSource setExplosionSource(Explosion explosionValue) {
        return explosionValue != null && explosionValue.getExplosivePlacedBy() != null
                ? new EntityDamageSource("explosion.player", explosionValue.getExplosivePlacedBy())
                        .setDifficultyScaled()
                        .setExplosion()
                : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }

    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }

    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0F;
        return this;
    }

    public static DamageSource causeFireballDamage(
            EntityFireball entityFireball, Entity entity) {
        return entity == null
                ? new EntityDamageSourceIndirect("onFire", entityFireball, entityFireball)
                        .setFireDamage()
                        .setProjectile()
                : new EntityDamageSourceIndirect("fireball", entityFireball, entity)
                        .setFireDamage()
                        .setProjectile();
    }

    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }

    public float getHungerDamage() {
        return this.hungerDamage;
    }

    public boolean isMagicDamage() {
        return this.magicDamage;
    }

    public static DamageSource causePlayerDamage(PlayerEntity playerEntity) {
        return new EntityDamageSource("player", playerEntity);
    }

    public boolean isExplosion() {
        return this.explosion;
    }

    public ChatComponent getDeathMessage(LivingEntity livingEntity) {
        LivingEntity livingEntityValue = livingEntity.func_94060_bK();
        String text = "death.attack." + this.damageType;
        String textValue = text + ".player";
        return livingEntityValue != null && StatCollector.canTranslate(textValue)
                ? new ChatComponentTranslation(
                        textValue,
                        new Object[] {
                            livingEntity.getDisplayName(), livingEntityValue.getDisplayName()
                        })
                : new ChatComponentTranslation(text, new Object[] {livingEntity.getDisplayName()});
    }

    public boolean isUnblockable() {
        return this.isUnblockable;
    }

    public boolean isCreativePlayer() {
        Entity entity = this.getEntity();
        return entity instanceof PlayerEntity
                && ((PlayerEntity) entity).capabilities.isCreativeMode;
    }

    public static DamageSource causeThrownDamage(Entity entity, Entity entityValue) {
        return new EntityDamageSourceIndirect("thrown", entity, entityValue).setProjectile();
    }

    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }

    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = true;
        this.hungerDamage = 0.0F;
        return this;
    }

    public static DamageSource causeArrowDamage(EntityArrow entityArrow, Entity entity) {
        return new EntityDamageSourceIndirect("arrow", entityArrow, entity).setProjectile();
    }

    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }

    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }

    static {
        net.minecraft.util.DamageSource.inFire = new DamageSource("inFire").setFireDamage();
        net.minecraft.util.DamageSource.lightningBolt = new DamageSource("lightningBolt");
        net.minecraft.util.DamageSource.onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
        net.minecraft.util.DamageSource.lava = new DamageSource("lava").setFireDamage();
        net.minecraft.util.DamageSource.inWall = new DamageSource("inWall").setDamageBypassesArmor();
        net.minecraft.util.DamageSource.drown = new DamageSource("drown").setDamageBypassesArmor();
        net.minecraft.util.DamageSource.starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
        net.minecraft.util.DamageSource.cactus = new DamageSource("cactus");
        net.minecraft.util.DamageSource.fall = new DamageSource("fall").setDamageBypassesArmor();
        net.minecraft.util.DamageSource.outOfWorld =
                new DamageSource("outOfWorld")
                        .setDamageBypassesArmor()
                        .setDamageAllowedInCreativeMode();
        net.minecraft.util.DamageSource.generic = new DamageSource("generic").setDamageBypassesArmor();
        net.minecraft.util.DamageSource.magic =
                new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
        net.minecraft.util.DamageSource.wither = new DamageSource("wither").setDamageBypassesArmor();
        net.minecraft.util.DamageSource.anvil = new DamageSource("anvil");
        net.minecraft.util.DamageSource.fallingBlock = new DamageSource("fallingBlock");
    }

    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }

    public static DamageSource causeIndirectMagicDamage(Entity entity, Entity entityValue) {
        return new EntityDamageSourceIndirect("indirectMagic", entity, entityValue)
                .setDamageBypassesArmor()
                .setMagicDamage();
    }
}
