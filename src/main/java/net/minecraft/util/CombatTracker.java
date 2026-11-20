package net.minecraft.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class CombatTracker {
    private int field_94555_c;
    private boolean field_94553_e;
    private int field_152776_e;
    private boolean field_94552_d;
    private String field_94551_f;

    private LivingEntity fighter;

    private int field_152775_d;
    private final List combatEntries = Lists.newArrayList();

    private CombatEntry TY() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        CombatEntry combatEntry = null;
        String textValue = text;
        CombatEntry combatEntryValue = null;
        byte b0 = 0;
        float fx = 0.0F;
        int i = 0;

        while (i < this.combatEntries.size()) {
            CombatEntry combatEntryValue2 = (CombatEntry) this.combatEntries.get(i);
            CombatEntry combatEntryValue3 =
                    i > 0 ? (CombatEntry) this.combatEntries.get(i - 1) : null;
            if ((combatEntryValue2.getDamageSrc() == DamageSource.fall
                            || combatEntryValue2.getDamageSrc() == DamageSource.outOfWorld)
                    && combatEntryValue2.aP() > 0.0F
                    && (combatEntry == null || combatEntryValue2.aP() > fx)) {
                {
                    if (i > 0) {
                        combatEntry = combatEntryValue3;
                        if (!(textValue != null)) {

                            combatEntry = combatEntryValue2;
                        }
                    } else {

                        combatEntry = combatEntryValue2;
                    }
                }

                fx = combatEntryValue2.aP();
            }

            if (combatEntryValue2.func_94562_g() != null
                    && (combatEntryValue == null || combatEntryValue2.getDamage() > b0)) {
                combatEntryValue = combatEntryValue2;
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        if (fx > 5.0F && combatEntry != null) {
            return combatEntry;
        } else {
            return (null);
        }
    }


    public LivingEntity TZ() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        LivingEntity livingEntity = null;
        PlayerEntity playerEntity = null;
        float fx = 0.0F;
        float f1 = 0.0F;
        Iterator iterator = this.combatEntries.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            CombatEntry combatEntry = (CombatEntry) iterator.next();
            if (combatEntry.getDamageSrc().getEntity() instanceof PlayerEntity
                    && (playerEntity == null || combatEntry.getDamage() > f1)) {
                f1 = combatEntry.getDamage();
                playerEntity = (PlayerEntity) combatEntry.getDamageSrc().getEntity();
            }

            if (combatEntry.getDamageSrc().getEntity() instanceof LivingEntity
                    && (livingEntity == null || combatEntry.getDamage() > fx)) {
                fx = combatEntry.getDamage();
                livingEntity = (LivingEntity) combatEntry.getDamageSrc().getEntity();
            }

            if (textValue == null) {
                break;
            }
        }

        return (LivingEntity)
                (playerEntity != null && f1 >= fx / 3.0F ? playerEntity : livingEntity);
    }

    public CombatTracker(LivingEntity bETValue) {
        this.fighter = bETValue;
    }

    public void trackDamage(DamageSource damageSource, float f, float f1) {
        this.reset();
        this.func_94545_a();
        CombatEntry combatEntry =
                new CombatEntry(
                        damageSource,
                        this.fighter.ticksExisted,
                        f,
                        f1,
                        this.field_94551_f,
                        this.fighter.fallDistance);
        this.combatEntries.add(combatEntry);
        this.field_94555_c = this.fighter.ticksExisted;
        this.field_94553_e = true;
        if (combatEntry.isLivingDamageSrc()
                && !this.field_94552_d
                && this.fighter.isEntityAlive()) {
            this.field_94552_d = true;
            this.field_152775_d = this.fighter.ticksExisted;
            this.field_152776_e = this.field_152775_d;
            this.fighter.sendEnterCombat();
        }
    }

    public int cR() {
        return this.field_94552_d
                ? this.fighter.ticksExisted - this.field_152775_d
                : this.field_152776_e - this.field_152775_d;
    }

    public LivingEntity getFighter() {
        return this.fighter;
    }

    public void reset() {
        int i = this.field_94552_d ? 300 : 100;
        if (this.field_94553_e
                && (!this.fighter.isEntityAlive()
                        || this.fighter.ticksExisted - this.field_94555_c > i)) {
            boolean flag = this.field_94552_d;
            this.field_94553_e = false;
            this.field_94552_d = false;
            this.field_152776_e = this.fighter.ticksExisted;
            if (flag) {
                this.fighter.sendEndCombat();
            }

            this.combatEntries.clear();
        }
    }

    public void func_94545_a() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        this.func_94542_g();
        String textValue = text;
        if (this.fighter.isOnLadder()) {
            {
                Block block =
                        this.fighter
                                .world
                                .getBlockState(
                                        new BlockPos(
                                                this.fighter.posX,
                                                this.fighter.getEntityBoundingBox().minY,
                                                this.fighter.posZ))
                                .getBlock();
                if (block == Blocks.ladder) {
                    this.field_94551_f = "ladder";
                    if (!(textValue != null)) {

                        if (block == Blocks.vine) {
                            this.field_94551_f = "vines";
                        }
                    }
                } else {

                    if (block == Blocks.vine) {
                        this.field_94551_f = "vines";
                    }
                }
            }

            if (textValue != null) {
                return;
            }
        }

        if (this.fighter.isInWater()) {
            this.field_94551_f = "water";
        }
    }

    private void func_94542_g() {
        this.field_94551_f = null;
    }

    public ChatComponent getDeathMessage() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (this.combatEntries.size() == 0) {
            return new ChatComponentTranslation(
                    "death.attack.generic", this.fighter.getDisplayName());
        }

        CombatEntry combatEntry = this.TY();
        CombatEntry combatEntryValue =
                (CombatEntry) this.combatEntries.get(this.combatEntries.size() - 1);
        ChatComponent chatComponent = combatEntryValue.getDamageSrcDisplayName();
        Entity entity = combatEntryValue.getDamageSrc().getEntity();
        if (combatEntry != null && combatEntryValue.getDamageSrc() == DamageSource.fall) {
            ChatComponentTranslation chatComponentTranslation;
            label71:
            {
                ChatComponent chatComponentValue = combatEntry.getDamageSrcDisplayName();
                if (combatEntry.getDamageSrc() != DamageSource.fall
                        && combatEntry.getDamageSrc() != DamageSource.outOfWorld) {
                    if (chatComponentValue != null
                            && (chatComponent == null
                                    || !chatComponentValue.equals(chatComponent))) {
                        {
                            Entity entityValue = combatEntry.getDamageSrc().getEntity();
                            ItemStack itemStack =
                                    entityValue instanceof LivingEntity
                                            ? ((LivingEntity) entityValue).getHeldItem()
                                            : null;
                            if (itemStack != null && itemStack.hasDisplayName()) {
                                chatComponentTranslation =
                                        new ChatComponentTranslation(
                                                "death.fell.assist.item",
                                                this.fighter.getDisplayName(),
                                                chatComponentValue,
                                                itemStack.getChatComponent());
                                if (!(text != null)) {

                                    chatComponentTranslation =
                                            new ChatComponentTranslation(
                                                    "death.fell.assist",
                                                    this.fighter.getDisplayName(),
                                                    chatComponentValue);
                                }
                            } else {

                                chatComponentTranslation =
                                        new ChatComponentTranslation(
                                                "death.fell.assist",
                                                this.fighter.getDisplayName(),
                                                chatComponentValue);
                            }
                        }

                        if (text != null) {
                            break label71;
                        }
                    }

                    if (chatComponent != null) {
                        {
                            ItemStack itemStackValue =
                                    entity instanceof LivingEntity
                                            ? ((LivingEntity) entity).getHeldItem()
                                            : null;
                            if (itemStackValue != null && itemStackValue.hasDisplayName()) {
                                chatComponentTranslation =
                                        new ChatComponentTranslation(
                                                "death.fell.finish.item",
                                                this.fighter.getDisplayName(),
                                                chatComponent,
                                                itemStackValue.getChatComponent());
                                if (!(text != null)) {

                                    chatComponentTranslation =
                                            new ChatComponentTranslation(
                                                    "death.fell.finish",
                                                    this.fighter.getDisplayName(),
                                                    chatComponent);
                                }
                            } else {

                                chatComponentTranslation =
                                        new ChatComponentTranslation(
                                                "death.fell.finish",
                                                this.fighter.getDisplayName(),
                                                chatComponent);
                            }
                        }

                        if (text != null) {
                            break label71;
                        }
                    }

                    chatComponentTranslation =
                            new ChatComponentTranslation(
                                    "death.fell.killer", this.fighter.getDisplayName());
                    if (text != null) {
                        break label71;
                    }
                }

                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "death.fell.accident." + this.func_94548_b(combatEntry),
                                this.fighter.getDisplayName());
            }

            if (text != null) {
                return (ChatComponent) chatComponentTranslation;
            }
        }

        ChatComponent chatComponentValue2 =
                combatEntryValue.getDamageSrc().getDeathMessage(this.fighter);
        return chatComponentValue2;
    }

    private String func_94548_b(CombatEntry combatEntry) {
        return combatEntry.func_94562_g() == null ? "generic" : combatEntry.func_94562_g();
    }
}
