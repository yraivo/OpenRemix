package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandClone extends CommandBase {

    private static String[] packedTextFragments;

    static {
        net.minecraft.command.CommandClone.packedTextFragments = new String[5];
        net.minecraft.command.CommandClone.packedTextFragments[0] = new String("x");
        net.minecraft.command.CommandClone.packedTextFragments[1] = new String("y");
        net.minecraft.command.CommandClone.packedTextFragments[2] = new String("z");
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.clone.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length < 9) {
            throw new WrongUsageException("commands.clone.usage", new Object[0]);
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 0);
        BlockPos blockPos = parseBlockPos(commandSender, astring, 0, false);
        BlockPos blockPosValue = parseBlockPos(commandSender, astring, 3, false);
        BlockPos blockPosValue2 = parseBlockPos(commandSender, astring, 6, false);
        StructureBoundingBox structureBoundingBox =
                new StructureBoundingBox(blockPos, blockPosValue);
        StructureBoundingBox structureBoundingBoxValue =
                new StructureBoundingBox(
                        blockPosValue2, blockPosValue2.add(structureBoundingBox.func_175896_b()));
        int i =
                structureBoundingBox.getXSize()
                        * structureBoundingBox.getYSize()
                        * structureBoundingBox.getZSize();
        if (i > 32768) {
            throw new CommandException(
                    "commands.clone.tooManyBlocks", new Object[] {i, 32768});
        }

        byte b0 = 0;
        Block block = null;
        int j = -1;
        if ((astring.length < 11 || !astring[10].equals("force") && !astring[10].equals("move"))
                && structureBoundingBox.intersectsWith(structureBoundingBoxValue)) {
            throw new CommandException("commands.clone.noOverlap", new Object[0]);
        }

        if (astring.length >= 11 && astring[10].equals("move")) {
            b0 = 1;
        }

        if (structureBoundingBox.minY >= 0
                && structureBoundingBox.maxY < 256
                && structureBoundingBoxValue.minY >= 0
                && structureBoundingBoxValue.maxY < 256) {
            World entityWorld = commandSender.getEntityWorld();
            if (entityWorld.isAreaLoaded(structureBoundingBox)
                    && entityWorld.isAreaLoaded(structureBoundingBoxValue)) {
                byte b1;
                b1 = 0;
                label239:
                if (astring.length >= 10) {
                    if (astring[9].equals("masked")) {
                        b1 = 1;
                        if (flag) {
                            break label239;
                        }
                    }

                    if (astring[9].equals("filtered")) {
                        if (astring.length < 12) {
                            throw new WrongUsageException(
                                    "commands.clone.usage", new Object[0]);
                        }

                        block = getBlockByText(commandSender, astring[11]);
                        if (astring.length >= 13) {
                            j = parseInt(astring[12], 0, 15);
                        }
                    }
                }

                ArrayList arrayList = Lists.newArrayList();
                ArrayList arrayListValue = Lists.newArrayList();
                ArrayList arrayListValue2 = Lists.newArrayList();
                LinkedList linkedlist = Lists.newLinkedList();
                BlockPos blockPosValue3 =
                        new BlockPos(
                                structureBoundingBoxValue.minX - structureBoundingBox.minX,
                                structureBoundingBoxValue.minY - structureBoundingBox.minY,
                                structureBoundingBoxValue.minZ - structureBoundingBox.minZ);
                int k = structureBoundingBox.minZ;

                label232:
                while (k <= structureBoundingBox.maxZ) {
                    int l = structureBoundingBox.minY;

                    while (true) {
                        if (l <= structureBoundingBox.maxY) {
                            int minX = structureBoundingBox.minX;

                            while (minX <= structureBoundingBox.maxX) {
                                BlockPos blockPosValue4 = new BlockPos(minX, l, k);
                                BlockPos blockPosValue5 = blockPosValue4.add(blockPosValue3);
                                BlockState blockState = entityWorld.getBlockState(blockPosValue4);
                                label221:
                                if ((b1 == 0 || blockState.getBlock() != Blocks.air)
                                        && (block == null
                                                || blockState.getBlock() == block
                                                        && (j < 0
                                                                || blockState
                                                                                .getBlock()
                                                                                .getMetaFromState(
                                                                                        blockState)
                                                                        == j))) {
                                    TileEntity tileEntity =
                                            entityWorld.getTileEntity(blockPosValue4);
                                    if (tileEntity != null) {
                                        CompoundTag compoundTag = new CompoundTag();
                                        tileEntity.writeToNBT(compoundTag);
                                        arrayListValue.add(
                                                new CommandCloneStaticCloneData(
                                                        blockPosValue5, blockState, compoundTag));
                                        linkedlist.addLast(blockPosValue4);
                                        if (flag) {
                                            break label221;
                                        }
                                    }

                                    if (!blockState.getBlock().isFullBlock()
                                            && !blockState.getBlock().isFullCube()) {
                                        arrayListValue2.add(
                                                new CommandCloneStaticCloneData(
                                                        blockPosValue5,
                                                        blockState,
                                                        (CompoundTag) null));
                                        linkedlist.addFirst(blockPosValue4);
                                        if (flag) {
                                            break label221;
                                        }
                                    }

                                    arrayList.add(
                                            new CommandCloneStaticCloneData(
                                                    blockPosValue5,
                                                    blockState,
                                                    (CompoundTag) null));
                                    linkedlist.addLast(blockPosValue4);
                                }

                                minX++;
                                if (!flag) {
                                    break;
                                }
                            }

                            l++;
                            if (flag) {
                                continue;
                            }
                        }

                        k++;
                        if (!flag) {
                            break label232;
                        }
                        break;
                    }
                }

                if (b0 != 0) {
                    for (java.util.Iterator<?> __recoveryIterator171_14371 =
                                    (linkedlist).iterator();
                            (__recoveryIterator171_14371).hasNext(); ) {
                        BlockPos blockPosValue6 = (BlockPos) (__recoveryIterator171_14371).next();
                        TileEntity tileEntityValue = entityWorld.getTileEntity(blockPosValue6);
                        if (tileEntityValue instanceof IInventory) {
                            ((IInventory) tileEntityValue).clearInventory();
                        }

                        entityWorld.setBlockState(
                                blockPosValue6, Blocks.barrier.getDefaultState(), 2);
                        if (!flag) {
                            break;
                        }
                    }

                    for (java.util.Iterator<?> __recoveryIterator171_15071 =
                                    (linkedlist).iterator();
                            (__recoveryIterator171_15071).hasNext(); ) {
                        BlockPos blockPosValue7 = (BlockPos) (__recoveryIterator171_15071).next();
                        entityWorld.setBlockState(blockPosValue7, Blocks.air.getDefaultState(), 3);
                        if (!flag) {
                            break;
                        }
                    }
                }

                ArrayList arrayListValue3 = Lists.newArrayList();
                arrayListValue3.addAll(arrayList);
                arrayListValue3.addAll(arrayListValue);
                arrayListValue3.addAll(arrayListValue2);
                List list = Lists.reverse(arrayListValue3);

                for (java.util.Iterator<?> __recoveryIterator171_15664 = (list).iterator();
                        (__recoveryIterator171_15664).hasNext(); ) {
                    CommandCloneStaticCloneData commandCloneStaticCloneData =
                            (CommandCloneStaticCloneData) (__recoveryIterator171_15664).next();
                    TileEntity tileEntityValue2 =
                            entityWorld.getTileEntity(commandCloneStaticCloneData.vh);
                    if (tileEntityValue2 instanceof IInventory) {
                        ((IInventory) tileEntityValue2).clearInventory();
                    }

                    entityWorld.setBlockState(
                            commandCloneStaticCloneData.vh, Blocks.barrier.getDefaultState(), 2);
                    if (!flag) {
                        break;
                    }
                }

                i = 0;

                for (java.util.Iterator<?> __recoveryIterator171_16353 =
                                (arrayListValue3).iterator();
                        (__recoveryIterator171_16353).hasNext(); ) {
                    CommandCloneStaticCloneData commandCloneStaticCloneDataValue =
                            (CommandCloneStaticCloneData) (__recoveryIterator171_16353).next();
                    if (entityWorld.setBlockState(
                            commandCloneStaticCloneDataValue.vh, commandCloneStaticCloneDataValue.blockState, 2)) {
                        i++;
                        if (!flag) {
                            break;
                        }
                    }
                }

                for (java.util.Iterator<?> __recoveryIterator171_16728 =
                                (arrayListValue).iterator();
                        (__recoveryIterator171_16728).hasNext(); ) {
                    CommandCloneStaticCloneData commandCloneStaticCloneDataValue2 =
                            (CommandCloneStaticCloneData) (__recoveryIterator171_16728).next();
                    TileEntity tileEntityValue3 =
                            entityWorld.getTileEntity(commandCloneStaticCloneDataValue2.vh);
                    if (commandCloneStaticCloneDataValue2.aYW != null && tileEntityValue3 != null) {
                        commandCloneStaticCloneDataValue2.aYW.setInteger(
                                packedTextFragments[0], commandCloneStaticCloneDataValue2.vh.getX());
                        commandCloneStaticCloneDataValue2.aYW.setInteger(
                                packedTextFragments[1], commandCloneStaticCloneDataValue2.vh.getY());
                        commandCloneStaticCloneDataValue2.aYW.setInteger(
                                packedTextFragments[2], commandCloneStaticCloneDataValue2.vh.getZ());
                        tileEntityValue3.readFromNBT(commandCloneStaticCloneDataValue2.aYW);
                        tileEntityValue3.markDirty();
                    }

                    entityWorld.setBlockState(
                            commandCloneStaticCloneDataValue2.vh, commandCloneStaticCloneDataValue2.blockState, 2);
                    if (!flag) {
                        break;
                    }
                }

                for (java.util.Iterator<?> __recoveryIterator171_17941 = (list).iterator();
                        (__recoveryIterator171_17941).hasNext(); ) {
                    CommandCloneStaticCloneData commandCloneStaticCloneDataValue3 =
                            (CommandCloneStaticCloneData) (__recoveryIterator171_17941).next();
                    entityWorld.notifyNeighborsRespectDebug(
                            commandCloneStaticCloneDataValue3.vh,
                            commandCloneStaticCloneDataValue3.blockState.getBlock());
                    if (!flag) {
                        break;
                    }
                }

                List listValue = entityWorld.getPendingBlockUpdatesInBounds(structureBoundingBox, false);
                if (listValue != null) {
                    for (java.util.Iterator<?> __recoveryIterator171_18409 = (listValue).iterator();
                            (__recoveryIterator171_18409).hasNext(); ) {
                        NextTickListEntry nextTickListEntry =
                                (NextTickListEntry) (__recoveryIterator171_18409).next();
                        if (structureBoundingBox.isVecInside(nextTickListEntry.position)) {
                            BlockPos blockPosValue8 =
                                    nextTickListEntry.position.add(blockPosValue3);
                            entityWorld.scheduleBlockUpdate(
                                    blockPosValue8,
                                    nextTickListEntry.getBlock(),
                                    (int)
                                            (nextTickListEntry.scheduledTime
                                                    - entityWorld
                                                            .getWorldInfo()
                                                            .getWorldTotalTime()),
                                    nextTickListEntry.priority);
                        }

                        if (!flag) {
                            break;
                        }
                    }
                }

                if (i <= 0) {
                    throw new CommandException("commands.clone.failed", new Object[0]);
                }

                commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, i);
                notifyOperators(
                        commandSender, this, "commands.clone.success", new Object[] {i});
                if (Entity.getLegacyGlobalFlowMarker() != null) {
                    CommandException.method_a8745b073fa8adfa1857(!flag);
                }
            } else {
                throw new CommandException("commands.clone.outOfWorld", new Object[0]);
            }
        } else {
            throw new CommandException("commands.clone.outOfWorld", new Object[0]);
        }
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : (astring.length > 3 && astring.length <= 6
                        ? method_cb1cf55a8a3937d053ae(astring, 3, blockPos)
                        : (astring.length > 6 && astring.length <= 9
                                ? method_cb1cf55a8a3937d053ae(astring, 6, blockPos)
                                : (astring.length == 10
                                        ? getListOfStringsMatchingLastWord(
                                                astring,
                                                new String[] {"replace", "masked", "filtered"})
                                        : (astring.length == 11
                                                ? getListOfStringsMatchingLastWord(
                                                        astring,
                                                        new String[] {"normal", "force", "move"})
                                                : (astring.length == 12
                                                                && "filtered".equals(astring[9])
                                                        ? getListOfStringsMatchingLastWord(
                                                                astring,
                                                                Block.blockRegistry.getKeys())
                                                        : null)))));
    }

    public String getCommandName() {
        return "clone";
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }
}
