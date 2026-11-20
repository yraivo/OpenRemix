package net.minecraft.command.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CommandTestForBlock extends CommandBase {

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : (astring.length == 4
                        ? getListOfStringsMatchingLastWord(astring, Block.blockRegistry.getKeys())
                        : null);
    }


    public String getCommandUsage(CommandSender commandSender) {
        return "commands.testforblock.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 4) {
                throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
            }
        } catch (NBTException nbtException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtException));
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 0);
        BlockPos blockPos = parseBlockPos(commandSender, astring, 0, false);
        Block block = Block.getBlockFromName(astring[3]);

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (block == null) {
                throw new NumberInvalidException(
                        "commands.setblock.notFound", new Object[] {astring[3]});
            }
        } catch (NBTException nbtExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue));
        }

        int i = -1;
        if (astring.length >= 5) {
            i = parseInt(astring[4], -1, 15);
        }

        World entityWorld = commandSender.getEntityWorld();

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (!entityWorld.isBlockLoaded(blockPos)) {
                throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
            }
        } catch (NBTException nbtExceptionValue2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue2));
        }

        CompoundTag compoundTag = new CompoundTag();
        byte b0 = 0;

        {
            boolean runGuardedCode;
            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                runGuardedCode = !(astring.length < 6 || !block.hasTileEntity());
            } catch (NBTException nbtExceptionValue3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue3));
            }
            if (runGuardedCode) {

                String text =
                        getChatComponentFromNthArg(commandSender, astring, 5).getUnformattedText();

                try {
                    compoundTag = JsonToNBT.getTagFromJson(text);
                    b0 = 1;
                } catch (NBTException nbtExceptionValue4) {
                    throw new CommandException(
                            "commands.setblock.tagError",
                            new Object[] {nbtExceptionValue4.getMessage()});
                }
            }
        }

        BlockState blockState = entityWorld.getBlockState(blockPos);
        Block blockValue = blockState.getBlock();

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (blockValue != block) {
                throw new CommandException(
                        "commands.testforblock.failed.tile",
                        new Object[] {
                            blockPos.getX(),
                            blockPos.getY(),
                            blockPos.getZ(),
                            blockValue.getLocalizedName(),
                            block.getLocalizedName()
                        });
            }
        } catch (NBTException nbtExceptionValue5) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue5));
        }

        if (i > -1) {
            int j = blockState.getBlock().getMetaFromState(blockState);

            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (j != i) {
                    throw new CommandException(
                            "commands.testforblock.failed.data",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ(), j, i});
                }
            } catch (NBTException nbtExceptionValue6) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue6));
            }
        }

        if (b0 != 0) {
            TileEntity tileEntity = entityWorld.getTileEntity(blockPos);

            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (tileEntity == null) {
                    throw new CommandException(
                            "commands.testforblock.failed.tileEntity",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
                }
            } catch (NBTException nbtExceptionValue7) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue7));
            }

            CompoundTag compoundTagValue = new CompoundTag();

            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                tileEntity.writeToNBT(compoundTagValue);
                if (!method_b1e94d9ac0e51bb728b9(compoundTag, compoundTagValue, true)) {
                    throw new CommandException(
                            "commands.testforblock.failed.nbt",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
                }
            } catch (NBTException nbtExceptionValue8) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue8));
            }
        }

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 1);
            notifyOperators(
                    commandSender,
                    this,
                    "commands.testforblock.success",
                    new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
            if (entityArray != null) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }
        } catch (NBTException nbtExceptionValue9) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue9));
        }
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }

    public String getCommandName() {
        return "testforblock";
    }

    public static boolean method_b1e94d9ac0e51bb728b9(
            NBTBase nbtBase, NBTBase nbtBaseValue, boolean flag) {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (nbtBase == nbtBaseValue) {
            return true;
        }

        if (nbtBase == null) {
            return true;
        }

        if (nbtBaseValue == null) {
            return false;
        }

        if (!nbtBase.getClass().equals(nbtBaseValue.getClass())) {
            return false;
        }

        if (nbtBase instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) nbtBase;
            CompoundTag compoundTagValue = (CompoundTag) nbtBaseValue;

            for (java.util.Iterator<?> __recoveryIterator171_15399 =
                            (compoundTag.getKeySet()).iterator();
                    (__recoveryIterator171_15399).hasNext(); ) {
                String text = (String) (__recoveryIterator171_15399).next();
                NBTBase tag = compoundTag.getTag(text);
                if (!method_b1e94d9ac0e51bb728b9(tag, compoundTagValue.getTag(text), flag)) {
                    return false;
                }
            }

            return true;
        } else if (nbtBase instanceof NBTTagList && flag) {
            NBTTagList nbtTagList = (NBTTagList) nbtBase;
            NBTTagList nbtTagListValue = (NBTTagList) nbtBaseValue;
            if (nbtTagList.tagCount() == 0) {
                return nbtTagListValue.tagCount() == 0;
            }

            int i = 0;

            while (i < nbtTagList.tagCount()) {
                NBTBase nbtBaseValue2 = nbtTagList.get(i);
                byte b0 = 0;
                int j = 0;

                while (j < nbtTagListValue.tagCount()) {
                    if (!method_b1e94d9ac0e51bb728b9(nbtBaseValue2, nbtTagListValue.get(j), flag)) {
                        j++;
                        if (entityArray == null) {
                            continue;
                        }
                    }

                    b0 = 1;
                    break;
                }

                if (b0 == 0) {
                    return false;
                }

                i++;
                if (entityArray != null) {
                    break;
                }
            }

            return true;
        } else {
            return nbtBase.equals(nbtBaseValue);
        }
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }
}
