package net.minecraft.command;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import java.util.List;

public class CommandCompare extends CommandBase {

    private static String[] packedTextFragments;

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.compare.usage";
    }

    @Override
    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : (astring.length > 3 && astring.length <= 6
                        ? method_cb1cf55a8a3937d053ae(astring, 3, blockPos)
                        : (astring.length > 6 && astring.length <= 9
                                ? method_cb1cf55a8a3937d053ae(astring, 6, blockPos)
                                : (astring.length == 10
                                        ? getListOfStringsMatchingLastWord(astring, "masked", "all")
                                        : null)));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandName() {
        return "testforblocks";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length < 9) {
            throw new WrongUsageException("commands.compare.usage", new Object[0]);
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
        if (i > 524288) {
            throw new CommandException(
                    "commands.compare.tooManyBlocks", new Object[] {i, 524288});
        }

        if (structureBoundingBox.minY >= 0
                && structureBoundingBox.maxY < 256
                && structureBoundingBoxValue.minY >= 0
                && structureBoundingBoxValue.maxY < 256) {
            World entityWorld = commandSender.getEntityWorld();
            if (!entityWorld.isAreaLoaded(structureBoundingBox)
                    || !entityWorld.isAreaLoaded(structureBoundingBoxValue)) {
                throw new CommandException("commands.compare.outOfWorld", new Object[0]);
            }

            byte b0 = 0;
            if (astring.length > 9 && astring[9].equals("masked")) {
                b0 = 1;
            }

            i = 0;
            BlockPos blockPosValue3 =
                    new BlockPos(
                            structureBoundingBoxValue.minX - structureBoundingBox.minX,
                            structureBoundingBoxValue.minY - structureBoundingBox.minY,
                            structureBoundingBoxValue.minZ - structureBoundingBox.minZ);
            int j = structureBoundingBox.minZ;

            while (j <= structureBoundingBox.maxZ) {
                int k = structureBoundingBox.minY;

                while (k <= structureBoundingBox.maxY) {
                    int l = structureBoundingBox.minX;

                    while (l <= structureBoundingBox.maxX) {
                        BlockPos blockPosValue4 = new BlockPos(l, k, j);
                        BlockPos blockPosValue5 = blockPosValue4.add(blockPosValue3);
                        byte b1 = 0;
                        BlockState blockState = entityWorld.getBlockState(blockPosValue4);
                        if (b0 == 0 || blockState.getBlock() != Blocks.air) {
                            label92:
                            {
                                if (blockState == entityWorld.getBlockState(blockPosValue5)) {
                                    {
                                        TileEntity tileEntity =
                                                entityWorld.getTileEntity(blockPosValue4);
                                        TileEntity tileEntityValue =
                                                entityWorld.getTileEntity(blockPosValue5);
                                        if (tileEntity != null && tileEntityValue != null) {
                                            CompoundTag compoundTag = new CompoundTag();
                                            tileEntity.writeToNBT(compoundTag);
                                            compoundTag.removeTag(packedTextFragments[0]);
                                            compoundTag.removeTag(packedTextFragments[1]);
                                            compoundTag.removeTag(packedTextFragments[2]);
                                            CompoundTag compoundTagValue = new CompoundTag();
                                            tileEntityValue.writeToNBT(compoundTagValue);
                                            compoundTagValue.removeTag(packedTextFragments[3]);
                                            compoundTagValue.removeTag(packedTextFragments[4]);
                                            compoundTagValue.removeTag(packedTextFragments[5]);
                                            if (!compoundTag.equals(compoundTagValue)) {
                                                b1 = 1;
                                            }

                                            if (!(!flag)) {

                                                if (tileEntity != null) {
                                                    b1 = 1;
                                                }
                                            }
                                        } else {

                                            if (tileEntity != null) {
                                                b1 = 1;
                                            }
                                        }
                                    }

                                    if (!flag) {
                                        break label92;
                                    }
                                }

                                b1 = 1;
                            }

                            i++;
                            if (b1 != 0) {
                                throw new CommandException(
                                        "commands.compare.failed", new Object[0]);
                            }
                        }

                        l++;
                        if (flag) {
                            break;
                        }
                    }

                    k++;
                    if (flag) {
                        break;
                    }
                }

                j++;
                if (flag) {
                    break;
                }
            }

            commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, i);
            notifyOperators(commandSender, this, "commands.compare.success", i);
            if (flag) {
                throw new CommandException("commands.compare.outOfWorld", new Object[0]);
            }

            if (!flag) {
                return;
            }
        }

        throw new CommandException("commands.compare.outOfWorld", new Object[0]);
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    static {
        net.minecraft.command.CommandCompare.packedTextFragments = new String[8];
        net.minecraft.command.CommandCompare.packedTextFragments[0] = new String("x");
        net.minecraft.command.CommandCompare.packedTextFragments[1] = new String("y");
        net.minecraft.command.CommandCompare.packedTextFragments[2] = new String("z");
        net.minecraft.command.CommandCompare.packedTextFragments[3] = new String("x");
        net.minecraft.command.CommandCompare.packedTextFragments[4] = new String("y");
        net.minecraft.command.CommandCompare.packedTextFragments[5] = new String("z");
    }
}
