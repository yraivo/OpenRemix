package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class TileEntityBeacon extends TileEntityLockable
        implements IInventory, IUpdatePlayerListBox {
    private boolean isComplete;
    private float field_146014_j;
    private int levels;
    public static Potion[][] effectsList;
    private int primaryEffect;
    private ItemStack payment;
    private long beamRenderCounter;
    private int secondaryEffect;

    private String customName;

    private final List beamSegments = Lists.newArrayList();

    public List getBeamSegments() {
        return this.beamSegments;
    }

    private void addEffectsToPlayers() {
        MobSpawnerBaseLogic.GU();

        if (this.isComplete
                && this.levels > 0
                && !this.worldObj.isRemote
                && this.primaryEffect > 0) {
            double d0 = this.levels * 10 + 10;
            byte b0 = 0;
            if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
                b0 = 1;
            }

            int x = this.pos.getX();
            int j = this.pos.getY();
            int k = this.pos.getZ();
            BoundingBox boundingBox =
                    new BoundingBox(x, j, k, x + 1, j + 1, k + 1)
                            .expand(d0, d0, d0)
                            .addCoord(0.0, this.worldObj.getHeight(), 0.0);
            List entitiesWithinAABB =
                    this.worldObj.getEntitiesWithinAABB(PlayerEntity.class, boundingBox);

            for (java.util.Iterator<?> __recoveryIterator171_2973 = (entitiesWithinAABB).iterator();
                    (__recoveryIterator171_2973).hasNext(); ) {
                PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_2973).next();
                playerEntity.addPotionEffect(
                        new PotionEffect(this.primaryEffect, 180, b0, true, true));
            }

            if (this.levels >= 4
                    && this.primaryEffect != this.secondaryEffect
                    && this.secondaryEffect > 0) {
                for (java.util.Iterator<?> __recoveryIterator171_3361 =
                                (entitiesWithinAABB).iterator();
                        (__recoveryIterator171_3361).hasNext(); ) {
                    PlayerEntity playerEntityValue =
                            (PlayerEntity) (__recoveryIterator171_3361).next();
                    playerEntityValue.addPotionEffect(
                            new PotionEffect(this.secondaryEffect, 180, 0, true, true));
                }
            }
        }
    }

    public ItemStack getStackInSlot(int i) {
        return i == 0 ? this.payment : null;
    }

    static {
        net.minecraft.tileentity.TileEntityBeacon.effectsList =
                new Potion[][] {
                    {Potion.MOVE_SPEED, Potion.DIG_SPEED},
                    {Potion.RESISTANCE, Potion.JUMP},
                    {Potion.DAMAGE_BOOST},
                    {Potion.REGENERATION}
                };
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    public void updateBeacon() {
        this.updateSegmentColors();
        this.addEffectsToPlayers();
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerBeacon(inventoryPlayer, this);
    }

    public boolean receiveClientEvent(int i, int j) {
        if (i == 1) {
            this.updateBeacon();
            return true;
        } else {
            return super.receiveClientEvent(i, j);
        }
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (i == 0 && this.payment != null) {
            ItemStack bBpValue = this.payment;
            this.payment = null;
            return bBpValue;
        } else {
            return null;
        }
    }

    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setName(String text) {
        this.customName = text;
    }

    public float shouldBeamRender() {
        if (!this.isComplete) {
            return 0.0F;
        }

        int i = (int) (this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
        this.beamRenderCounter = this.worldObj.getTotalWorldTime();
        if (i > 1) {
            this.field_146014_j -= i / 40.0F;
            if (this.field_146014_j < 0.0F) {
                this.field_146014_j = 0.0F;
            }
        }

        this.field_146014_j += 0.025F;
        if (this.field_146014_j > 1.0F) {
            this.field_146014_j = 1.0F;
        }

        return this.field_146014_j;
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return itemStack.getItem() == Items.emerald
                || itemStack.getItem() == Items.diamond
                || itemStack.getItem() == Items.gold_ingot
                || itemStack.getItem() == Items.iron_ingot;
    }

    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }

    public int getField(int i) {
        switch (i) {
            case 0:
                return this.levels;
            case 1:
                return this.primaryEffect;
            case 2:
                return this.secondaryEffect;
            default:
                return 0;
        }
    }

    public TileEntityBeacon() {
        this.levels = -1;
    }

    public Packet getDescriptionPacket() {
        CompoundTag compoundTag = new CompoundTag();
        this.writeToNBT(compoundTag);
        return new S35PacketUpdateTileEntity(this.pos, 3, compoundTag);
    }

    public int getSizeInventory() {
        return 1;
    }

    public void clearInventory() {
        this.payment = null;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.primaryEffect = compoundTag.getInteger("Primary");
        this.secondaryEffect = compoundTag.getInteger("Secondary");
        this.levels = compoundTag.getInteger("Levels");
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.beacon";
    }

    public int getFieldCount() {
        return 3;
    }

    public void setField(int i, int j) {
        MobSpawnerBaseLogic.GU();

        switch (i) {
            case 0:
                this.levels = j;

                break;

            case 1:
                this.primaryEffect = j;

                break;

            case 2:
                this.secondaryEffect = j;
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setInteger("Primary", this.primaryEffect);
        compoundTag.setInteger("Secondary", this.secondaryEffect);
        compoundTag.setInteger("Levels", this.levels);
    }

    public ItemStack decrStackSize(int i, int j) {
        if (i != 0 || this.payment == null) {
            return null;
        } else if (j >= this.payment.stackSize) {
            ItemStack bBpValue = this.payment;
            this.payment = null;
            return bBpValue;
        } else {
            this.payment.stackSize -= j;
            return new ItemStack(this.payment.getItem(), j, this.payment.getItemDamage());
        }
    }

    public String getGuiID() {
        return "minecraft:beacon";
    }

    public void update() {
        long totalWorldTime = this.worldObj.getTotalWorldTime();

        totalWorldTime %= 80L;

        if (totalWorldTime == 0L) {
            this.updateBeacon();
        }
    }

    public void setInventorySlotContents(int i, ItemStack bBpValue) {
        if (i == 0) {
            this.payment = bBpValue;
        }
    }

    private void updateSegmentColors() {
        int levelsValue = this.levels;
        int j = this.pos.getX();
        int k = this.pos.getY();
        Entity[] entityArray = MobSpawnerBaseLogic.GU();
        int l = this.pos.getZ();
        this.levels = 0;
        this.beamSegments.clear();
        this.isComplete = true;
        Entity[] entityArrayValue = entityArray;
        TileEntityBeaconBeamSegment tileEntityBeaconBeamSegment =
                new TileEntityBeaconBeamSegment(EntitySheep.getDyeRgb(EnumDyeColor.WHITE));
        this.beamSegments.add(tileEntityBeaconBeamSegment);
        byte b0 = 1;
        int i1 = k + 1;

        while (i1 < this.worldObj.getActualHeight()) {
            label126:
            {
                float[] afloat;
                {
                    BlockPos blockPos = new BlockPos(j, i1, l);
                    BlockState blockState = this.worldObj.getBlockState(blockPos);
                    if (blockState.getBlock() == Blocks.stained_glass) {
                        afloat =
                                EntitySheep.getDyeRgb(
                                        (EnumDyeColor)
                                                blockState.getValue(BlockStainedGlass.COLOR));

                        
                    } else {

                        if (blockState.getBlock() != Blocks.stained_glass_pane) {
                            if (blockState.getBlock().getLightOpacity() >= 15) {
                                this.isComplete = false;
                                this.beamSegments.clear();

                                break;
                            }

                            tileEntityBeaconBeamSegment.incrementHeight();

                            break label126;
                        }

                        afloat =
                                EntitySheep.getDyeRgb(
                                        (EnumDyeColor)
                                                blockState.getValue(BlockStainedGlassPane.COLOR));
                    }
                }

                if (b0 == 0) {
                    afloat =
                            new float[] {
                                (tileEntityBeaconBeamSegment.getColors()[0] + afloat[0]) / 2.0F,
                                (tileEntityBeaconBeamSegment.getColors()[1] + afloat[1]) / 2.0F,
                                (tileEntityBeaconBeamSegment.getColors()[2] + afloat[2]) / 2.0F
                            };
                }

                {
                    if (Arrays.equals(afloat, tileEntityBeaconBeamSegment.getColors())) {
                        tileEntityBeaconBeamSegment.incrementHeight();

                        
                    } else {

                        tileEntityBeaconBeamSegment = new TileEntityBeaconBeamSegment(afloat);
                        this.beamSegments.add(tileEntityBeaconBeamSegment);
                    }
                }

                b0 = 0;
            }

            i1++;
        }

        if (this.isComplete) {
            i1 = 1;

            while (i1 <= 4) {
                int k1 = k - i1;
                if (k1 < 0 && entityArrayValue != null) {
                    break;
                }

                byte b1 = 1;
                int l1 = j - i1;

                while (l1 <= j + i1 && b1 != 0) {
                    int j1 = l - i1;

                    while (j1 <= l + i1) {
                        Block block =
                                this.worldObj.getBlockState(new BlockPos(l1, k1, j1)).getBlock();
                        if (block != Blocks.emerald_block
                                && block != Blocks.gold_block
                                && block != Blocks.diamond_block
                                && block != Blocks.iron_block) {
                            b1 = 0;

                            break;
                        }

                        j1++;
                    }

                    l1++;
                }

                if (b1 == 0 && entityArrayValue != null) {
                    break;
                }

                this.levels = i1++;
            }

            if (this.levels == 0) {
                this.isComplete = false;
            }
        }

        if (!this.worldObj.isRemote && this.levels == 4 && levelsValue < this.levels) {
            for (java.util.Iterator<?> __recoveryIterator171_41043 =
                            (this.worldObj.getEntitiesWithinAABB(
                                            PlayerEntity.class,
                                            new BoundingBox(j, k, l, j, k - 4, l)
                                                    .expand(10.0, 5.0, 10.0)))
                                    .iterator();
                    (__recoveryIterator171_41043).hasNext(); ) {
                PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_41043).next();
                playerEntity.triggerAchievement(AchievementList.fullBeacon);
            }
        }
    }
}
