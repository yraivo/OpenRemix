package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class CommandFill extends CommandBase {

    private static String[] packedTextFragments;

    public int getRequiredPermissionLevel() {
        return 2;
    }

    private static NBTException method_63611f3fd5128a257bff(
            NBTException nbtException) {
        return nbtException;
    }

    public String getCommandName() {
        return "fill";
    }

    static {
        net.minecraft.command.CommandFill.packedTextFragments = new String[5];
        net.minecraft.command.CommandFill.packedTextFragments[0] = new String("x");
        net.minecraft.command.CommandFill.packedTextFragments[1] = new String("y");
        net.minecraft.command.CommandFill.packedTextFragments[2] = new String("z");
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 7) {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 0);
        BlockPos blockPos = parseBlockPos(commandSender, astring, 0, false);
        BlockPos blockPosValue = parseBlockPos(commandSender, astring, 3, false);
        Block block =
                CommandBase.getBlockByText(commandSender, astring[6]);
        int i = 0;
        if (astring.length >= 8) {
            i = parseInt(astring[7], 0, 15);
        }

        BlockPos blockPosValue2 =
                new BlockPos(
                        Math.min(blockPos.getX(), blockPosValue.getX()),
                        Math.min(blockPos.getY(), blockPosValue.getY()),
                        Math.min(blockPos.getZ(), blockPosValue.getZ()));
        BlockPos blockPosValue3 =
                new BlockPos(
                        Math.max(blockPos.getX(), blockPosValue.getX()),
                        Math.max(blockPos.getY(), blockPosValue.getY()),
                        Math.max(blockPos.getZ(), blockPosValue.getZ()));
        int j =
                (blockPosValue3.getX() - blockPosValue2.getX() + 1)
                        * (blockPosValue3.getY() - blockPosValue2.getY() + 1)
                        * (blockPosValue3.getZ() - blockPosValue2.getZ() + 1);
        if (j > 32768) {
            throw new CommandException(
                    "commands.fill.tooManyBlocks", new Object[] {j, 32768});
        }

        if (blockPosValue2.getY() >= 0 && blockPosValue3.getY() < 256) {
            World entityWorld = commandSender.getEntityWorld();
            int k = blockPosValue2.getZ();

            while (k < blockPosValue3.getZ() + 16) {
                int l = blockPosValue2.getX();

                while (l < blockPosValue3.getX() + 16) {
                    if (!entityWorld.isBlockLoaded(
                            new BlockPos(l, blockPosValue3.getY() - blockPosValue2.getY(), k))) {
                        throw new CommandException(
                                "commands.fill.outOfWorld", new Object[0]);
                    }

                    l += 16;
                    if (flag) {
                        break;
                    }
                }

                k += 16;
                if (flag) {
                    break;
                }
            }

            CompoundTag compoundTag = new CompoundTag();
            byte b0 = 0;
            if (astring.length >= 10 && block.hasTileEntity()) {
                String text =
                        getChatComponentFromNthArg(commandSender, astring, 9).getUnformattedText();

                try {
                    compoundTag = JsonToNBT.getTagFromJson(text);
                    b0 = 1;
                } catch (NBTException nbtException) {
                    throw new CommandException(
                            "commands.fill.tagError",
                            new Object[] {nbtException.getMessage()});
                }
            }

            ArrayList arrayList = Lists.newArrayList();
            j = 0;
            int z = blockPosValue2.getZ();

            while (z <= blockPosValue3.getZ()) {
                int j1 = blockPosValue2.getY();

                while (j1 <= blockPosValue3.getY()) {
                    int k1 = blockPosValue2.getX();

                    while (k1 <= blockPosValue3.getX()) {
                        label224:
                        {
                            BlockPos blockPosValue4;
                            blockPosValue4 = new BlockPos(k1, j1, z);
                            label182:
                            if (astring.length >= 9) {
                                if (!astring[8].equals("outline") && !astring[8].equals("hollow")) {
                                    if (astring[8].equals("destroy")) {
                                        entityWorld.destroyBlock(blockPosValue4, true);
                                        if (!flag) {
                                            break label182;
                                        }
                                    }

                                    if (astring[8].equals("keep")) {
                                        if (entityWorld.isAirBlock(blockPosValue4)) {
                                            break label182;
                                        }

                                        if (!flag) {
                                            break label224;
                                        }
                                    }

                                    if (!astring[8].equals("replace") || block.hasTileEntity()) {
                                        break label182;
                                    }

                                    if (astring.length > 9) {
                                        Block blockValue =
                                                CommandBase
                                                        .getBlockByText(
                                                                commandSender, astring[9]);
                                        if (entityWorld.getBlockState(blockPosValue4).getBlock()
                                                        != blockValue
                                                && !flag) {
                                            break label224;
                                        }
                                    }

                                    if (astring.length <= 10) {
                                        break label182;
                                    }

                                    int l1 =
                                            CommandBase.parseInt(
                                                    astring[10]);
                                    BlockState blockState =
                                            entityWorld.getBlockState(blockPosValue4);
                                    if (blockState.getBlock().getMetaFromState(blockState) != l1
                                            && !flag) {
                                        break label224;
                                    }

                                    if (!flag) {
                                        break label182;
                                    }
                                }

                                if (k1 != blockPosValue2.getX()
                                        && k1 != blockPosValue3.getX()
                                        && j1 != blockPosValue2.getY()
                                        && j1 != blockPosValue3.getY()
                                        && z != blockPosValue2.getZ()
                                        && z != blockPosValue3.getZ()) {
                                    if (!astring[8].equals("hollow")) {
                                        break label224;
                                    }

                                    entityWorld.setBlockState(
                                            blockPosValue4, Blocks.air.getDefaultState(), 2);
                                    arrayList.add(blockPosValue4);
                                    if (!flag) {
                                        break label224;
                                    }
                                }
                            }

                            TileEntity tileEntity = entityWorld.getTileEntity(blockPosValue4);
                            if (tileEntity != null) {
                                if (tileEntity instanceof IInventory) {
                                    ((IInventory) tileEntity).clearInventory();
                                }

                                entityWorld.setBlockState(
                                        blockPosValue4,
                                        Blocks.barrier.getDefaultState(),
                                        block == Blocks.barrier ? 2 : 4);
                            }

                            BlockState blockStateValue = block.getStateFromMeta(i);
                            if (entityWorld.setBlockState(blockPosValue4, blockStateValue, 2)) {
                                arrayList.add(blockPosValue4);
                                j++;
                                if (b0 != 0) {
                                    TileEntity tileEntityValue =
                                            entityWorld.getTileEntity(blockPosValue4);
                                    if (tileEntityValue != null) {
                                        compoundTag.setInteger(
                                                packedTextFragments[0], blockPosValue4.getX());
                                        compoundTag.setInteger(
                                                packedTextFragments[1], blockPosValue4.getY());
                                        compoundTag.setInteger(
                                                packedTextFragments[2], blockPosValue4.getZ());
                                        tileEntityValue.readFromNBT(compoundTag);
                                    }
                                }
                            }
                        }

                        k1++;
                        if (flag) {
                            break;
                        }
                    }

                    j1++;
                    if (flag) {
                        break;
                    }
                }

                z++;
                if (flag) {
                    break;
                }
            }

            for (java.util.Iterator<?> __recoveryIterator171_37556 = (arrayList).iterator();
                    (__recoveryIterator171_37556).hasNext(); ) {
                BlockPos blockPosValue5 = (BlockPos) (__recoveryIterator171_37556).next();
                Block blockValue2 = entityWorld.getBlockState(blockPosValue5).getBlock();
                entityWorld.notifyNeighborsRespectDebug(blockPosValue5, blockValue2);
                if (flag) {
                    break;
                }
            }

            if (j <= 0) {
                throw new CommandException("commands.fill.failed", new Object[0]);
            }

            commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, j);
            notifyOperators(
                    commandSender, this, "commands.fill.success", new Object[] {j});
            if (!flag) {
                return;
            }
        }

        throw new CommandException("commands.fill.outOfWorld", new Object[0]);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.fill.usage";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : (astring.length > 3 && astring.length <= 6
                        ? method_cb1cf55a8a3937d053ae(astring, 3, blockPos)
                        : (astring.length == 7
                                ? getListOfStringsMatchingLastWord(
                                        astring, Block.blockRegistry.getKeys())
                                : (astring.length == 9
                                        ? getListOfStringsMatchingLastWord(
                                                astring,
                                                new String[] {
                                                    "replace", "destroy", "keep", "hollow",
                                                    "outline"
                                                })
                                        : null)));
    }
}
