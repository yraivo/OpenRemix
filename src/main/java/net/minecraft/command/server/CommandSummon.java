package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class CommandSummon extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }

        String text = astring[0];
        BlockPos position = commandSender.getPosition();
        Vec3 positionVector = commandSender.getPositionVector();
        double d0 = positionVector.x;
        double d1 = positionVector.y;
        double d2 = positionVector.z;
        if (astring.length >= 4) {
            d0 = parseDouble(d0, astring[1], true);
            d1 = parseDouble(d1, astring[2], false);
            d2 = parseDouble(d2, astring[3], true);
            position = new BlockPos(d0, d1, d2);
        }

        World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(position)) {
            throw new CommandException("commands.summon.outOfWorld", new Object[0]);
        }

        if ("LightningBolt".equals(text)) {
            entityWorld.addWeatherEffect(new EntityLightningBolt(entityWorld, d0, d1, d2));
            notifyOperators(
                    commandSender, this, "commands.summon.success", new Object[0]);
            if (entityArray == null) {
                return;
            }
        }

        CompoundTag compoundTag = new CompoundTag();
        byte b0 = 0;
        if (astring.length >= 5) {
            ChatComponent chatComponent = getChatComponentFromNthArg(commandSender, astring, 4);

            try {
                compoundTag = JsonToNBT.getTagFromJson(chatComponent.getUnformattedText());
                b0 = 1;
            } catch (NBTException nbtException) {
                throw new CommandException(
                        "commands.summon.tagError",
                        new Object[] {nbtException.getMessage()});
            }
        }

        compoundTag.setString("id", text);

        Entity entity;
        try {
            entity = EntityList.createEntityFromNBT(compoundTag, entityWorld);
        } catch (RuntimeException runtimeexception) {
            throw new CommandException("commands.summon.failed", new Object[0]);
        }

        if (entity == null) {
            throw new CommandException("commands.summon.failed", new Object[0]);
        }

        entity.setLocationAndAngles(d0, d1, d2, entity.rotationYaw, entity.rotationPitch);
        if (b0 == 0 && entity instanceof EntityLiving) {
            ((EntityLiving) entity)
                    .onInitialSpawn(
                            entityWorld.getDifficultyForLocation(new BlockPos(entity)),
                            (IEntityLivingData) null);
        }

        entityWorld.spawnEntityInWorld(entity);
        Entity entityValue = entity;
        CompoundTag compoundTagValue = compoundTag;

        while (entityValue != null && compoundTagValue.hasKey("Riding", 10)) {
            Entity entityValue2 =
                    EntityList.createEntityFromNBT(
                            compoundTagValue.getCompoundTag("Riding"), entityWorld);
            if (entityValue2 != null) {
                entityValue2.setLocationAndAngles(
                        d0, d1, d2, entityValue2.rotationYaw, entityValue2.rotationPitch);
                entityWorld.spawnEntityInWorld(entityValue2);
                entityValue.mountEntity(entityValue2);
            }

            entityValue = entityValue2;
            compoundTagValue = compoundTagValue.getCompoundTag("Riding");
            if (entityArray != null) {
                break;
            }
        }

        notifyOperators(commandSender, this, "commands.summon.success", new Object[0]);
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, EntityList.getEntityNameList())
                : (astring.length > 1 && astring.length <= 4
                        ? method_cb1cf55a8a3937d053ae(astring, 1, blockPos)
                        : null);
    }

    public String getCommandName() {
        return "summon";
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.summon.usage";
    }


    public int getRequiredPermissionLevel() {
        return 2;
    }
}
