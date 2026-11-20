package net.minecraft.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityTameable extends AnimalEntity implements IEntityOwnable {

    private static String[] packedTextFragments;
    protected EntityAISit aiSit = new EntityAISit(this);

    public Team getTeam() {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.func_180492_cm();
            if (livingEntity != null) {
                return livingEntity.getTeam();
            }
        }

        return super.getTeam();
    }

    public void setSitting(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        String textValue = text;
        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));
            if (textValue == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -2));
    }

    public void setOwnerId(String text) {
        this.dataWatcher.updateObject(17, text);
    }

    public EntityAISit getAISit() {
        return this.aiSit;
    }

    protected void playTameEffect(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        EnumParticleTypes enumParticleTypes = EnumParticleTypes.HEART;
        String textValue = text;
        if (!flag) {
            enumParticleTypes = EnumParticleTypes.SMOKE_NORMAL;
        }

        int i = 0;

        while (i < 7) {
            double d0 = this.rand.nextGaussian() * 0.02;
            double d1 = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            this.world.spawnParticle(
                    enumParticleTypes,
                    this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    this.posY + 0.5 + this.rand.nextFloat() * this.height,
                    this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    d0,
                    d1,
                    d2,
                    new int[0]);
            i++;
            if (textValue != null) {
                break;
            }
        }
    }

    public void onDeath(DamageSource damageSource) {
        if (!this.world.isRemote
                && this.world.getGameRules().getGameRuleBooleanValue("showDeathMessages")
                && this.jvm171_d_45ffeb83d14f3c()
                && this.func_180492_cm() instanceof EntityPlayerMP) {
            ((EntityPlayerMP) this.func_180492_cm())
                    .addChatMessage(this.getCombatTracker().getDeathMessage());
        }

        super.onDeath(damageSource);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        {
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.writeEntityToNBT(compoundTag);
            String textValue = text;
            if (this.getOwnerId() == null) {
                compoundTag.setString("OwnerUUID", packedTextFragments[1]);
                if (!(textValue == null)) {

                    compoundTag.setString("OwnerUUID", this.getOwnerId());
                }
            } else {

                compoundTag.setString("OwnerUUID", this.getOwnerId());
            }
        }

        compoundTag.setBoolean("Sitting", this.isSitting());
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 7) {
            this.playTameEffect(true);
            if (text == null) {
                return;
            }
        }

        if (b0 == 6) {
            this.playTameEffect(false);
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public boolean isOwner(LivingEntity livingEntity) {
        return livingEntity == this.func_180492_cm();
    }

    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public boolean isSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public boolean isTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void setTamed(boolean flag) {
        {
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            byte b0 = this.dataWatcher.getWatchableObjectByte(16);
            String textValue = text;
            if (flag) {
                this.dataWatcher.updateObject(16, (byte) (b0 | 4));
                if (!(textValue == null)) {

                    this.dataWatcher.updateObject(16, (byte) (b0 & -5));
                }
            } else {

                this.dataWatcher.updateObject(16, (byte) (b0 & -5));
            }
        }

        this.setupTamedAI();
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
        this.dataWatcher.addObject(17, packedTextFragments[0]);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        String text;
        {
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.readEntityFromNBT(compoundTag);
            text = packedTextFragments[2];
            String textValue2 = textValue;
            if (compoundTag.hasKey("OwnerUUID", 8)) {
                text = compoundTag.getString("OwnerUUID");
                if (!(textValue2 == null)) {

                    String textValue3 = compoundTag.getString("Owner");
                    text = PreYggdrasilConverter.getStringUUIDFromName(textValue3);
                }
            } else {

                String textValue3 = compoundTag.getString("Owner");
                text = PreYggdrasilConverter.getStringUUIDFromName(textValue3);
            }
        }

        if (text.length() > 0) {
            this.setOwnerId(text);
            this.setTamed(true);
        }

        this.aiSit.setSitting(compoundTag.getBoolean("Sitting"));
        this.setSitting(compoundTag.getBoolean("Sitting"));
    }

    public Entity getOwner() {
        return this.func_180492_cm();
    }

    public EntityTameable(World worldValue) {
        super(worldValue);
        this.setupTamedAI();
    }

    protected void setupTamedAI() {}

    static {
        net.minecraft.entity.passive.EntityTameable.packedTextFragments = new String[5];
        net.minecraft.entity.passive.EntityTameable.packedTextFragments[0] = new String("");
        net.minecraft.entity.passive.EntityTameable.packedTextFragments[1] = new String("");
        net.minecraft.entity.passive.EntityTameable.packedTextFragments[2] = new String("");
    }

    public LivingEntity func_180492_cm() {
        try {
            UUID uuid = UUID.fromString(this.getOwnerId());
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public boolean shouldAttackEntity(
            LivingEntity livingEntity, LivingEntity livingEntityValue) {
        return true;
    }

    public boolean isOnSameTeam(LivingEntity livingEntity) {
        if (this.isTamed()) {
            LivingEntity livingEntityValue = this.func_180492_cm();
            if (livingEntity == livingEntityValue) {
                return true;
            }

            if (livingEntityValue != null) {
                return livingEntityValue.isOnSameTeam(livingEntity);
            }
        }

        return super.isOnSameTeam(livingEntity);
    }
}
