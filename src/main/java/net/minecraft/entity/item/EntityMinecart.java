package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBaseEnumRailDirection;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
    private double velocityZ;
    private double minecartY;
    private boolean isInReverse;
    private double velocityY;
    private double minecartPitch;
    private double minecartX;
    private static String[] packedTextFragments;
    private double minecartZ;
    private double velocityX;
    private double minecartYaw;
    private static int[][][] matrix;
    private String entityName;
    private int turnProgress;

    public Vec3 method_450b342917e7b7196cd5(double d0, double d1, double d2, double d3) {
        int i = MathHelper.floor(d0);
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        int j = MathHelper.floor(d1);
        boolean flag = flag1;
        int k = MathHelper.floor(d2);
        if (BlockRailBase.isRailBlock(this.world, new BlockPos(i, j - 1, k))) {
            j--;
        }

        BlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
        if (BlockRailBase.isRailBlock(blockState)) {
            BlockRailBaseEnumRailDirection value =
                    (BlockRailBaseEnumRailDirection)
                            blockState.getValue(
                                    ((BlockRailBase) blockState.getBlock()).getShapeProperty());
            d1 = j;
            if (value.isAscending()) {
                d1 = j + 1;
            }

            int[][] aint = net.minecraft.entity.item.EntityMinecart.matrix[value.getMetadata()];
            double d4 = aint[1][0] - aint[0][0];
            double d5 = aint[1][2] - aint[0][2];
            double d6 = Math.sqrt(d4 * d4 + d5 * d5);
            d4 /= d6;
            d5 /= d6;
            d0 += d4 * d3;
            d2 += d5 * d3;
            if (aint[0][1] != 0
                    && MathHelper.floor(d0) - i == aint[0][0]
                    && MathHelper.floor(d2) - k == aint[0][2]) {
                d1 += aint[0][1];
                if (flag) {
                    return this.method_efda42170a89d3d2d9f9(d0, d1, d2);
                }
            }

            if (aint[1][1] != 0
                    && MathHelper.floor(d0) - i == aint[1][0]
                    && MathHelper.floor(d2) - k == aint[1][2]) {
                d1 += aint[1][1];
            }

            return this.method_efda42170a89d3d2d9f9(d0, d1, d2);
        } else {
            return null;
        }
    }

    public void setDamage(float p171_0) {
        this.dataWatcher.updateObject(19, p171_0);
    }

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.velocityX = this.motionX = motionXValue;
        this.velocityY = this.motionY = motionYValue;
        this.velocityZ = this.motionZ = motionZValue;
    }

    protected void applyDrag() {
        boolean flag = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (this.riddenByEntity != null) {
            this.motionX *= 0.997F;
            this.motionY *= 0.0;
            this.motionZ *= 0.997F;
            if (flag) {
                return;
            }
        }

        this.motionX *= 0.96F;
        this.motionY *= 0.0;
        this.motionZ *= 0.96F;
    }

    public boolean hasCustomName() {
        return this.entityName != null;
    }

    public void setHasDisplayTile(boolean p171_0) {
        this.getDataWatcher().updateObject(22, (byte) (p171_0 ? 1 : 0));
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.air.getDefaultState();
    }

    public int getDisplayTileOffset() {
        return !this.hasDisplayTile()
                ? this.getDefaultDisplayTileOffset()
                : this.getDataWatcher().getWatchableObjectInt(21);
    }

    public double getMountedYOffset() {
        return this.height * 0.5 - 0.2F;
    }

    public abstract EntityMinecartEnumMinecartType getMinecartType();

    public void killMinecart(DamageSource damageSource) {
        this.setDead();
        ItemStack itemStack = new ItemStack(Items.minecart, 1);
        if (this.entityName != null) {
            itemStack.setStackDisplayName(this.entityName);
        }

        this.entityDropItem(itemStack, 0.0F);
    }

    public String getName() {
        return this.entityName != null ? this.entityName : super.getName();
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        if (this.hasDisplayTile()) {
            compoundTag.setBoolean("CustomDisplayTile", true);
            BlockState blockState = this.getDisplayTile();
            ResourceLocation nameForObject =
                    (ResourceLocation) Block.blockRegistry.getNameForObject(blockState.getBlock());
            compoundTag.setString(
                    "DisplayTile",
                    nameForObject == null ? packedTextFragments[0] : nameForObject.toString());
            compoundTag.setInteger(
                    "DisplayData", blockState.getBlock().getMetaFromState(blockState));
            compoundTag.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }

        if (this.entityName != null && this.entityName.length() > 0) {
            compoundTag.setString("CustomName", this.entityName);
        }
    }

    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
    }

    public void func_174899_a(BlockState blockState) {
        this.getDataWatcher().updateObject(20, net.minecraft.block.Block.getStateId(blockState));
        this.setHasDisplayTile(true);
    }

    public String getCustomNameTag() {
        return this.entityName;
    }

    protected double getMaximumSpeed() {
        return 0.4;
    }

    public EntityMinecart(
            World worldValue, double prevPosXValue, double prevPosYValue, double prevPosZValue) {
        this(worldValue);
        this.setPosition(prevPosXValue, prevPosYValue, prevPosZValue);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosXValue;
        this.prevPosY = prevPosYValue;
        this.prevPosZ = prevPosZValue;
    }

    public void setPosition(double posXValue, double posYValue, double posZValue) {
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        float f = this.width / 2.0F;
        float heightValue = this.height;
        this.setBoundingBox(
                new net.minecraft.util.BoundingBox(
                        posXValue - f,
                        posYValue,
                        posZValue - f,
                        posXValue + f,
                        posYValue + heightValue,
                        posZValue + f));
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean hasDisplayTile() {
        return this.getDataWatcher().getWatchableObjectByte(22) == 1;
    }

    public void setRollingAmplitude(int p171_0) {
        this.dataWatcher.updateObject(17, p171_0);
    }

    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        boolean flag = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (compoundTag.getBoolean("CustomDisplayTile")) {
            label34:
            {
                int integer = compoundTag.getInteger("DisplayData");
                if (compoundTag.hasKey("DisplayTile", 8)) {
                    Block block = Block.getBlockFromName(compoundTag.getString("DisplayTile"));
                    if (block == null) {
                        this.func_174899_a(Blocks.air.getDefaultState());
                        if (flag) {
                            break label34;
                        }
                    }

                    this.func_174899_a(block.getStateFromMeta(integer));
                    if (flag) {
                        break label34;
                    }
                }

                Block blockValue = Block.getBlockById(compoundTag.getInteger("DisplayTile"));
                if (blockValue == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                    if (flag) {
                        break label34;
                    }
                }

                this.func_174899_a(blockValue.getStateFromMeta(integer));
            }

            this.setDisplayTileOffset(compoundTag.getInteger("DisplayOffset"));
        }

        if (compoundTag.hasKey("CustomName", 8)
                && compoundTag.getString("CustomName").length() > 0) {
            this.entityName = compoundTag.getString("CustomName");
        }
    }

    public EntityMinecart(World worldValue) {
        super(worldValue);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.7F);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(17, new java.lang.Integer(0));
        this.dataWatcher.addObject(18, new java.lang.Integer(1));
        this.dataWatcher.addObject(19, new java.lang.Float(0.0F));
        this.dataWatcher.addObject(20, new java.lang.Integer(0));
        this.dataWatcher.addObject(21, new java.lang.Integer(6));
        this.dataWatcher.addObject(22, (byte) 0);
    }

    public BoundingBox getCollisionBoundingBox() {
        return null;
    }

    public Vec3 method_efda42170a89d3d2d9f9(double d0, double d1, double d2) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        int i = MathHelper.floor(d0);
        boolean flag = flag1;
        int j = MathHelper.floor(d1);
        int k = MathHelper.floor(d2);
        if (BlockRailBase.isRailBlock(this.world, new BlockPos(i, j - 1, k))) {
            j--;
        }

        BlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
        if (!BlockRailBase.isRailBlock(blockState)) {
            return null;
        }

        double d3;
        double d4;
        double d5;
        double d9;
        double d10;
        double d11;
        double d14;
        label36:
        {
            BlockRailBaseEnumRailDirection value =
                    (BlockRailBaseEnumRailDirection)
                            blockState.getValue(
                                    ((BlockRailBase) blockState.getBlock()).getShapeProperty());
            int[][] aint = net.minecraft.entity.item.EntityMinecart.matrix[value.getMetadata()];
            d14 = 0.0;
            d3 = i + 0.5 + aint[0][0] * 0.5;
            d4 = j + 0.0625 + aint[0][1] * 0.5;
            d5 = k + 0.5 + aint[0][2] * 0.5;
            double d6 = i + 0.5 + aint[1][0] * 0.5;
            double d7 = j + 0.0625 + aint[1][1] * 0.5;
            double d8 = k + 0.5 + aint[1][2] * 0.5;
            d9 = d6 - d3;
            d10 = (d7 - d4) * 2.0;
            d11 = d8 - d5;
            if (d9 == 0.0) {
                d0 = i + 0.5;
                d14 = d2 - k;
                if (flag) {
                    break label36;
                }
            }

            if (d11 == 0.0) {
                d2 = k + 0.5;
                d14 = d0 - i;
                if (flag) {
                    break label36;
                }
            }

            double d12 = d0 - d3;
            double d13 = d2 - d5;
            d14 = (d12 * d9 + d13 * d11) * 2.0;
        }

        d0 = d3 + d9 * d14;
        d1 = d4 + d10 * d14;
        d2 = d5 + d11 * d14;
        if (d10 < 0.0) {
            d1++;
        }

        if (d10 > 0.0) {
            d1 += 0.5;
        }

        return new Vec3(d0, d1, d2);
    }

    public void onActivatorRailPass(int i, int j, int k, boolean flag) {}

    public void setDead() {
        super.setDead();
    }

    protected void method_b304a2560f61dd0c2107(BlockPos blockPos, BlockState blockState) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        this.fallDistance = 0.0F;
        Vec3 vec3 = this.method_efda42170a89d3d2d9f9(this.posX, this.posY, this.posZ);
        this.posY = blockPos.getY();
        byte b0 = 0;
        byte b1 = 0;
        BlockRailBase block = (BlockRailBase) blockState.getBlock();
        boolean flag = flag1;
        if (block == Blocks.golden_rail) {
            b0 =
                    remix.compat.compiler.JvmBooleanSlots.toByte(
                            (Boolean) blockState.getValue(BlockRailPowered.POWERED));
            b1 = (byte) (b0 == 0 ? 1 : 0);
        }

        BlockRailBaseEnumRailDirection value =
                (BlockRailBaseEnumRailDirection) blockState.getValue(block.getShapeProperty());
        switch (value) {
            case ASCENDING_EAST:
                this.motionX -= 0.0078125;
                this.posY++;
                if (flag) {
                    break;
                }

                Entity.setLegacyGlobalFlowMarker(new String[2]);
            case ASCENDING_WEST:
                this.motionX += 0.0078125;
                this.posY++;
                if (flag) {
                    break;
                }
            case ASCENDING_NORTH:
                this.motionZ += 0.0078125;
                this.posY++;
                if (flag) {
                    break;
                }
            case ASCENDING_SOUTH:
                this.motionZ -= 0.0078125;
                this.posY++;
        }

        int[][] aint = net.minecraft.entity.item.EntityMinecart.matrix[value.getMetadata()];
        double d1 = aint[1][0] - aint[0][0];
        double d2 = aint[1][2] - aint[0][2];
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        double d4 = this.motionX * d1 + this.motionZ * d2;
        if (d4 < 0.0) {
            d1 = -d1;
            d2 = -d2;
        }

        double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d5 > 2.0) {
            d5 = 2.0;
        }

        this.motionX = d5 * d1 / d3;
        this.motionZ = d5 * d2 / d3;
        if (this.riddenByEntity instanceof LivingEntity) {
            double aIB = ((LivingEntity) this.riddenByEntity).moveForward;
            if (aIB > 0.0) {
                double d7 = -Math.sin(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
                double d8 = Math.cos(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
                double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;
                if (d9 < 0.01) {
                    this.motionX += d7 * 0.1;
                    this.motionZ += d8 * 0.1;
                    b1 = 0;
                }
            }
        }

        if (b1 != 0) {
            double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d17 < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
                if (!(flag)) {

                    this.motionX *= 0.5;
                    this.motionY *= 0.0;
                    this.motionZ *= 0.5;
                }
            } else {

                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }

        double d18;
        double d19;
        double d20;
        label143:
        {
            d18 = 0.0;
            d19 = blockPos.getX() + 0.5 + aint[0][0] * 0.5;
            d20 = blockPos.getZ() + 0.5 + aint[0][2] * 0.5;
            double d21 = blockPos.getX() + 0.5 + aint[1][0] * 0.5;
            double d10 = blockPos.getZ() + 0.5 + aint[1][2] * 0.5;
            d1 = d21 - d19;
            d2 = d10 - d20;
            if (d1 == 0.0) {
                this.posX = blockPos.getX() + 0.5;
                d18 = this.posZ - blockPos.getZ();
                if (flag) {
                    break label143;
                }
            }

            if (d2 == 0.0) {
                this.posZ = blockPos.getZ() + 0.5;
                d18 = this.posX - blockPos.getX();
                if (flag) {
                    break label143;
                }
            }

            double d11 = this.posX - d19;
            double d12 = this.posZ - d20;
            d18 = (d11 * d1 + d12 * d2) * 2.0;
        }

        this.posX = d19 + d1 * d18;
        this.posZ = d20 + d2 * d18;
        this.setPosition(this.posX, this.posY, this.posZ);
        double motionXValue = this.motionX;
        double motionZValue = this.motionZ;
        if (this.riddenByEntity != null) {
            motionXValue *= 0.75;
            motionZValue *= 0.75;
        }

        {
            double maximumSpeed = this.getMaximumSpeed();
            motionXValue = MathHelper.clamp(motionXValue, -maximumSpeed, maximumSpeed);
            motionZValue = MathHelper.clamp(motionZValue, -maximumSpeed, maximumSpeed);
            this.moveEntity(motionXValue, 0.0, motionZValue);
            if (aint[0][1] != 0
                    && MathHelper.floor(this.posX) - blockPos.getX() == aint[0][0]
                    && MathHelper.floor(this.posZ) - blockPos.getZ() == aint[0][2]) {
                this.setPosition(this.posX, this.posY + aint[0][1], this.posZ);
                if (!(flag)) {

                    if (aint[1][1] != 0
                            && MathHelper.floor(this.posX) - blockPos.getX() == aint[1][0]
                            && MathHelper.floor(this.posZ) - blockPos.getZ() == aint[1][2]) {
                        this.setPosition(this.posX, this.posY + aint[1][1], this.posZ);
                    }
                }
            } else {

                if (aint[1][1] != 0
                        && MathHelper.floor(this.posX) - blockPos.getX() == aint[1][0]
                        && MathHelper.floor(this.posZ) - blockPos.getZ() == aint[1][2]) {
                    this.setPosition(this.posX, this.posY + aint[1][1], this.posZ);
                }
            }
        }

        this.applyDrag();
        Vec3 vec3Value = this.method_efda42170a89d3d2d9f9(this.posX, this.posY, this.posZ);
        if (vec3Value != null && vec3 != null) {
            double d14 = (vec3.y - vec3Value.y) * 0.05;
            d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d5 > 0.0) {
                this.motionX = this.motionX / d5 * (d5 + d14);
                this.motionZ = this.motionZ / d5 * (d5 + d14);
            }

            this.setPosition(this.posX, vec3Value.y, this.posZ);
        }

        int j = MathHelper.floor(this.posX);
        int i = MathHelper.floor(this.posZ);
        if (j != blockPos.getX() || i != blockPos.getZ()) {
            d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = d5 * (j - blockPos.getX());
            this.motionZ = d5 * (i - blockPos.getZ());
        }

        if (b0 != 0) {
            double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (d15 > 0.01) {
                double d16 = 0.06;
                this.motionX = this.motionX + this.motionX / d15 * d16;
                this.motionZ = this.motionZ + this.motionZ / d15 * d16;
                if (flag) {
                    return;
                }
            }

            if (value == BlockRailBaseEnumRailDirection.EAST_WEST) {
                if (this.world.getBlockState(blockPos.offsetWest()).getBlock().isNormalCube()) {
                    this.motionX = 0.02;
                    if (flag) {
                        return;
                    }
                }

                if (!this.world.getBlockState(blockPos.offsetEast()).getBlock().isNormalCube()) {
                    return;
                }

                this.motionX = -0.02;
                if (flag) {
                    return;
                }
            }

            if (value == BlockRailBaseEnumRailDirection.NORTH_SOUTH) {
                if (this.world.getBlockState(blockPos.offsetNorth()).getBlock().isNormalCube()) {
                    this.motionZ = 0.02;
                    if (flag) {
                        return;
                    }
                }

                if (this.world.getBlockState(blockPos.offsetSouth()).getBlock().isNormalCube()) {
                    this.motionZ = -0.02;
                }
            }
        }
    }

    public void setRollingDirection(int p171_0) {
        this.dataWatcher.updateObject(18, p171_0);
    }

    public ChatComponent getDisplayName() {
        if (this.hasCustomName()) {
            ChatComponentText chatComponentText = new ChatComponentText(this.entityName);
            chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueId().toString());
            return chatComponentText;
        } else {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(this.getName(), new Object[0]);
            chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueId().toString());
            return chatComponentTranslation;
        }
    }

    public BlockState getDisplayTile() {
        return !this.hasDisplayTile()
                ? this.getDefaultDisplayTile()
                : net.minecraft.block.Block.getStateById(
                        this.getDataWatcher().getWatchableObjectInt(20));
    }

    public static EntityMinecart createMinecart(
            World worldValue,
            double d0,
            double d1,
            double d2,
            EntityMinecartEnumMinecartType entityMinecartEnumMinecartType) {
        switch (entityMinecartEnumMinecartType) {
            case CHEST:
                return new EntityMinecartChest(worldValue, d0, d1, d2);
            case FURNACE:
                return new EntityMinecartFurnace(worldValue, d0, d1, d2);
            case TNT:
                return new EntityMinecartTNT(worldValue, d0, d1, d2);
            case SPAWNER:
                return new EntityMinecartMobSpawner(worldValue, d0, d1, d2);
            case HOPPER:
                return new EntityMinecartHopper(worldValue, d0, d1, d2);
            case COMMAND_BLOCK:
                return new EntityMinecartCommandBlock(worldValue, d0, d1, d2);
            default:
                return new EntityMinecartEmpty(worldValue, d0, d1, d2);
        }
    }

    public void setCustomNameTag(String bbqValue) {
        this.entityName = bbqValue;
    }

    static {
        net.minecraft.entity.item.EntityMinecart.packedTextFragments = new String[3];
        net.minecraft.entity.item.EntityMinecart.packedTextFragments[0] = new String("");
        net.minecraft.entity.item.EntityMinecart.matrix =
                new int[][][] {
                    {{0, 0, -1}, {0, 0, 1}},
                    {{-1, 0, 0}, {1, 0, 0}},
                    {{-1, -1, 0}, {1, 0, 0}},
                    {{-1, 0, 0}, {1, -1, 0}},
                    {{0, 0, -1}, {0, -1, 1}},
                    {{0, -1, -1}, {0, 0, 1}},
                    {{0, 0, 1}, {1, 0, 0}},
                    {{0, 0, 1}, {-1, 0, 0}},
                    {{0, 0, -1}, {-1, 0, 0}},
                    {{0, 0, -1}, {1, 0, 0}}
                };
    }

    protected void moveDerailedMinecart() {
        double maximumSpeed = this.getMaximumSpeed();
        this.motionX = MathHelper.clamp(this.motionX, -maximumSpeed, maximumSpeed);
        this.motionZ = MathHelper.clamp(this.motionZ, -maximumSpeed, maximumSpeed);
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.95F;
            this.motionY *= 0.95F;
            this.motionZ *= 0.95F;
        }
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public boolean canBePushed() {
        return true;
    }

    public void setPositionAndRotation2(
            double bbkValue,
            double bbgValue,
            double bbmValue,
            float bboValue,
            float bbjValue,
            int p171_5,
            boolean p171_6) {
        this.minecartX = bbkValue;
        this.minecartY = bbgValue;
        this.minecartZ = bbmValue;
        this.minecartYaw = bboValue;
        this.minecartPitch = bbjValue;
        this.turnProgress = p171_5 + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean flag = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (this.world.isRemote || this.isDead) {
            return true;
        }

        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + f * 10.0F);
        int i =
                damageSource.getEntity() instanceof PlayerEntity
                                && ((PlayerEntity) damageSource.getEntity())
                                        .capabilities
                                        .isCreativeMode
                        ? 1
                        : 0;
        if (i != 0 || this.getDamage() > 40.0F) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity((Entity) null);
            }

            if (i != 0 && !this.hasCustomName()) {
                this.setDead();
                if (flag) {
                    return true;
                }
            }

            this.killMinecart(damageSource);
        }

        return true;
    }

    public int getDefaultDisplayTileOffset() {
        return 6;
    }

    public void setDisplayTileOffset(int p171_0) {
        this.getDataWatcher().updateObject(21, p171_0);
        this.setHasDisplayTile(true);
    }

    public void onUpdate() {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        if (this.posY < -64.0) {
            this.kill();
        }

        if (!this.world.isRemote && this.world instanceof WorldServer) {
            label138:
            {
                this.world.theProfiler.startSection("portal");
                MinecraftServer minecraftServer = ((WorldServer) this.world).getMinecraftServer();
                int maxInPortalTime = this.getMaxInPortalTime();
                if (this.inPortal) {
                    if (!minecraftServer.getAllowNether()) {
                        break label138;
                    }

                    if (this.ridingEntity == null && this.portalCounter++ >= maxInPortalTime) {
                        byte b0;
                        {
                            this.portalCounter = maxInPortalTime;
                            this.timeUntilPortal = this.getPortalCooldown();
                            if (this.world.provider.getDimensionId() == -1) {
                                b0 = 0;
                                if (!(!flag)) {

                                    b0 = -1;
                                }
                            } else {

                                b0 = -1;
                            }
                        }

                        this.travelToDimension(b0);
                    }

                    this.inPortal = false;
                    if (!flag) {
                        break label138;
                    }
                }

                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }

            if (this.timeUntilPortal > 0) {
                this.timeUntilPortal--;
            }

            this.world.theProfiler.endSection();
        }

        if (this.world.isRemote) {
            if (this.turnProgress > 0) {
                double d4 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                double d0 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                double d1 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                double d2 = MathHelper.wrapAngleTo180(this.minecartYaw - this.rotationYaw);
                this.rotationYaw = (float) (this.rotationYaw + d2 / this.turnProgress);
                this.rotationPitch =
                        (float)
                                (this.rotationPitch
                                        + (this.minecartPitch - this.rotationPitch)
                                                / this.turnProgress);
                this.turnProgress--;
                this.setPosition(d4, d0, d1);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                if (!flag) {
                    return;
                }
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (!flag) {
                return;
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.04F;
        int k = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int l = MathHelper.floor(this.posZ);
        if (BlockRailBase.isRailBlock(this.world, new BlockPos(k, j - 1, l))) {
            j--;
        }

        label100:
        {
            BlockPos blockPos = new BlockPos(k, j, l);
            BlockState blockState = this.world.getBlockState(blockPos);
            if (BlockRailBase.isRailBlock(blockState)) {
                this.method_b304a2560f61dd0c2107(blockPos, blockState);
                if (blockState.getBlock() != Blocks.activator_rail) {
                    break label100;
                }

                this.onActivatorRailPass(
                        k, j, l, (Boolean) blockState.getValue(BlockRailPowered.POWERED));
                if (!flag) {
                    break label100;
                }
            }

            this.moveDerailedMinecart();
        }

        this.doBlockCollisions();
        this.rotationPitch = 0.0F;
        double d5 = this.prevPosX - this.posX;
        double d6 = this.prevPosZ - this.posZ;
        if (d5 * d5 + d6 * d6 > 0.001) {
            this.rotationYaw = (float) (Math.atan2(d6, d5) * 180.0 / Math.PI);
            if (this.isInReverse) {
                this.rotationYaw += 180.0F;
            }
        }

        double d3 = MathHelper.wrapAngleTo180(this.rotationYaw - this.prevRotationYaw);
        if (d3 < -170.0 || d3 >= 170.0) {
            this.rotationYaw += 180.0F;
            this.isInReverse =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.isInReverse ? 1 : 0));
        }

        this.setRotation(this.rotationYaw, this.rotationPitch);

        for (java.util.Iterator<?> __recoveryIterator171_59793 =
                        (this.world.getEntitiesWithinAABBExcludingEntity(
                                        this, this.getEntityBoundingBox().expand(0.2F, 0.0, 0.2F)))
                                .iterator();
                (__recoveryIterator171_59793).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_59793).next();
            if (entity != this.riddenByEntity
                    && entity.canBePushed()
                    && entity instanceof EntityMinecart) {
                entity.applyEntityCollision(this);
            }

            if (flag) {
                break;
            }
        }

        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            if (this.riddenByEntity.ridingEntity == this) {
                this.riddenByEntity.ridingEntity = null;
            }

            this.riddenByEntity = null;
        }

        this.handleWaterMovement();
    }

    public BoundingBox getCollisionBox(Entity entity) {
        return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }

    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void applyEntityCollision(Entity entity) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (!this.world.isRemote
                && !entity.noClip
                && !this.noClip
                && entity != this.riddenByEntity) {
            if (entity instanceof LivingEntity
                    && !(entity instanceof PlayerEntity)
                    && !(entity instanceof EntityIronGolem)
                    && this.getMinecartType() == EntityMinecartEnumMinecartType.RIDEABLE
                    && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01
                    && this.riddenByEntity == null
                    && entity.ridingEntity == null) {
                entity.mountEntity(this);
            }

            double deltaPosX = entity.posX - this.posX;
            double deltaPosZ = entity.posZ - this.posZ;
            double d2 = deltaPosX * deltaPosX + deltaPosZ * deltaPosZ;
            if (d2 >= 1.0E-4F) {
                d2 = MathHelper.sqrt(d2);
                deltaPosX /= d2;
                deltaPosZ /= d2;
                double d3 = 1.0 / d2;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }

                deltaPosX *= d3;
                deltaPosZ *= d3;
                deltaPosX *= 0.1F;
                deltaPosZ *= 0.1F;
                deltaPosX *= 1.0F - this.entityCollisionReduction;
                deltaPosZ *= 1.0F - this.entityCollisionReduction;
                deltaPosX *= 0.5;
                deltaPosZ *= 0.5;
                if (entity instanceof EntityMinecart) {
                    double deltaPosXValue = entity.posX - this.posX;
                    double deltaPosZValue = entity.posZ - this.posZ;
                    Vec3 vec3 = new Vec3(deltaPosXValue, 0.0, deltaPosZValue).normalize();
                    Vec3 vec3Value =
                            new Vec3(
                                            MathHelper.cos(
                                                    this.rotationYaw * (float) Math.PI / 180.0F),
                                            0.0,
                                            MathHelper.sin(
                                                    this.rotationYaw * (float) Math.PI / 180.0F))
                                    .normalize();
                    double d6 = Math.abs(vec3.dotProduct(vec3Value));
                    if (d6 < 0.8F) {
                        return;
                    }

                    label66:
                    {
                        double d7 = entity.motionX + this.motionX;
                        double d8 = entity.motionZ + this.motionZ;
                        if (((EntityMinecart) entity).getMinecartType()
                                        == EntityMinecartEnumMinecartType.FURNACE
                                && this.getMinecartType()
                                        != EntityMinecartEnumMinecartType.FURNACE) {
                            this.motionX *= 0.2F;
                            this.motionZ *= 0.2F;
                            this.addVelocity(
                                    entity.motionX - deltaPosX, 0.0, entity.motionZ - deltaPosZ);
                            entity.motionX *= 0.95F;
                            entity.motionZ *= 0.95F;
                            if (!flag) {
                                break label66;
                            }
                        }

                        if (((EntityMinecart) entity).getMinecartType()
                                        != EntityMinecartEnumMinecartType.FURNACE
                                && this.getMinecartType()
                                        == EntityMinecartEnumMinecartType.FURNACE) {
                            entity.motionX *= 0.2F;
                            entity.motionZ *= 0.2F;
                            entity.addVelocity(
                                    this.motionX + deltaPosX, 0.0, this.motionZ + deltaPosZ);
                            this.motionX *= 0.95F;
                            this.motionZ *= 0.95F;
                            if (!flag) {
                                break label66;
                            }
                        }

                        d7 /= 2.0;
                        d8 /= 2.0;
                        this.motionX *= 0.2F;
                        this.motionZ *= 0.2F;
                        this.addVelocity(d7 - deltaPosX, 0.0, d8 - deltaPosZ);
                        entity.motionX *= 0.2F;
                        entity.motionZ *= 0.2F;
                        entity.addVelocity(d7 + deltaPosX, 0.0, d8 + deltaPosZ);
                    }

                    if (!flag) {
                        return;
                    }
                }

                this.addVelocity(-deltaPosX, 0.0, -deltaPosZ);
                entity.addVelocity(deltaPosX / 4.0, 0.0, deltaPosZ / 4.0);
            }
        }
    }
}
