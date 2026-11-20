package net.minecraft.entity;

import net.minecraft.block.state.BlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
    private int activatorRailCooldown;
    private final CommandBlockLogic commandBlockLogic = new MinecartCommandBlockLogic(this);
    private static String[] packedTextFragments;

    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.COMMAND_BLOCK;
    }

    // region String decoding support

    // endregion

    public void onActivatorRailPass(int i, int j, int k, boolean flag) {
        if (flag && this.ticksExisted - this.activatorRailCooldown >= 4) {
            this.getCommandBlockLogic().trigger(this.world);
            this.activatorRailCooldown = this.ticksExisted;
        }
    }

    public void onDataWatcherUpdate(int i) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.onDataWatcherUpdate(i);
        String[] astring = astring1;
        if (i == 24) {
            try {
                this.commandBlockLogic.setLastOutput(
                        IChatComponentSerializer.jsonToComponent(
                                this.getDataWatcher().getWatchableObjectString(24)));
                return;
            } catch (Throwable throwable) {
                if (astring == null) {
                    return;
                }
            }
        }

        if (i == 23) {
            this.commandBlockLogic.setCommand(this.getDataWatcher().getWatchableObjectString(23));
        }
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.commandBlockLogic.readDataFromNBT(compoundTag);
        this.getDataWatcher().updateObject(23, this.getCommandBlockLogic().getCustomName());
        this.getDataWatcher()
                .updateObject(
                        24,
                        IChatComponentSerializer.componentToJson(
                                this.getCommandBlockLogic().getLastOutput()));
    }

    public EntityMinecartCommandBlock(World worldValue) {
        super(worldValue);
        this.activatorRailCooldown = 0;
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        this.commandBlockLogic.tryOpenEditCommandBlock(playerEntity);
        return false;
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.command_block.getDefaultState();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.entity.EntityMinecartCommandBlock.packedTextFragments[0] = new String("");
        net.minecraft.entity.EntityMinecartCommandBlock.packedTextFragments[1] = new String("");
    }

    // endregion

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        this.commandBlockLogic.writeDataToNBT(compoundTag);
    }

    private static Throwable method_c246cb5bfc0059f69bf5(Throwable throwable) {
        return throwable;
    }

    public EntityMinecartCommandBlock(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
        this.activatorRailCooldown = 0;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(23, packedTextFragments[0]);
        this.getDataWatcher().addObject(24, packedTextFragments[1]);
    }
}
