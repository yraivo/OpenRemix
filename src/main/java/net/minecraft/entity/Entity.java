package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSoundType;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.MotionPhase;
import remix.event.impl.SafeWalkEvent;
import remix.event.impl.StepEvent;
import remix.event.impl.StrafeEvent;
import remix.module.combat.AuraModule;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class Entity implements CommandSender {
    public boolean isAirBorne;
    protected boolean inPortal;
    public float stepHeight;
    public int serverPosY;
    public boolean preventEntitySpawning;
    public float width;
    public double motionX;
    private double entityRiderYawDelta;
    public double prevPosY;
    private CommandResultStats cmdResultStats;
    public boolean onGround;
    public double renderDistanceWeight;
    public boolean addedToChunk;
    public int chunkCoordX;
    public double prevPosZ;
    public boolean collided;
    public float prevRotationPitch;
    public int serverPosX;
    public float distanceWalkedOnStepModified;
    public float height;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private static String[] legacyGlobalFlowMarker;
    public double lastTickPosY;
    public int ticksExisted;
    public int serverPosZ;
    private int entityId;
    public double posY;
    public World world;
    public float prevDistanceWalkedModified;

    private boolean isOutsideBorder;
    protected int teleportDirection;
    public double posX;
    private int nextStepDistance;
    public float entityCollisionReduction;
    private double entityRiderPitchDelta;
    public boolean collidedVertically;
    public int chunkCoordY;
    public int chunkCoordZ;
    protected int portalCounter;
    public float distanceWalkedModified;
    private boolean legacyInvisibilitySample;
    public float fallDistance;
    public double motionY;
    public int timeUntilPortal;

    private static int nextEntityID;
    public float rotationPitch;
    public double motionZ;
    public int dimension;
    public double prevPosX;
    protected UUID entityUniqueID;
    public float prevRotationYaw;
    protected boolean firstUpdate;
    public double posZ;
    public boolean forceSpawn;
    public boolean ignoreFrustumCheck;
    public boolean isInWeb;
    private static String[] packedTextFragments;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public int fireResistance;
    private int fire;
    public boolean collidedHorizontally;
    public float rotationYaw;
    public boolean velocityChanged;
    protected boolean inWater;
    public boolean isDead;
    public boolean noClip;
    private boolean invulnerable;
    public double lastTickPosZ;
    public BoundingBox boundingBox;
    public double lastTickPosX;
    protected static BoundingBox ZERO_AABB;
    protected Random rand;
    public int hurtResistantTime;

    public boolean isInvisible() {
        return this.getFlag(5);
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    private boolean isLiquidPresentInAABB(BoundingBox boundingBoxValue) {
        return ((boolean)
                        (((java.util.List)
                                        (this.world.getCollidingBoundingBoxes(
                                                this, boundingBoxValue)))
                                .isEmpty()))
                && !this.world.isAnyLiquid(boundingBoxValue);
    }

    public void spawnRunningParticles() {
        if (this.isSprinting() && !this.isInWater()) {
            this.createRunningParticles();
        }
    }

    protected HoverEvent getHoverEvent() {
        CompoundTag compoundTag = new CompoundTag();
        String sx = EntityList.getEntityString(this);
        compoundTag.setString("id", this.getUniqueId().toString());
        if (sx != null) {
            compoundTag.setString("type", sx);
        }

        compoundTag.setString("name", this.getName());
        return new HoverEvent(
                HoverEventAction.SHOW_ENTITY, new ChatComponentText(compoundTag.toString()));
    }

    public void setCustomNameTag(String text) {
        this.dataWatcher.updateObject(2, text);
    }

    public boolean interactAt(PlayerEntity playerEntity, Vec3 vec3) {
        return false;
    }

    protected void doBlockCollisions() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        BlockPos blockPos =
                new BlockPos(
                        this.getEntityBoundingBox().minX + 0.001,
                        this.getEntityBoundingBox().minY + 0.001,
                        this.getEntityBoundingBox().minZ + 0.001);
        BlockPos blockPosValue =
                new BlockPos(
                        this.getEntityBoundingBox().maxX - 0.001,
                        this.getEntityBoundingBox().maxY - 0.001,
                        this.getEntityBoundingBox().maxZ - 0.001);
        String[] astring = astring1;
        if (this.world.isAreaLoaded(blockPos, blockPosValue)) {
            int x = blockPos.getX();

            while (x <= blockPosValue.getX()) {
                int j = blockPos.getY();

                while (j <= blockPosValue.getY()) {
                    int k = blockPos.getZ();

                    while (k <= blockPosValue.getZ()) {
                        BlockPos blockPosValue2 = new BlockPos(x, j, k);
                        BlockState blockState = this.world.getBlockState(blockPosValue2);

                        try {
                            blockState
                                    .getBlock()
                                    .onEntityCollidedWithBlock(
                                            this.world, blockPosValue2, blockState, this);
                        } catch (Throwable throwable) {
                            CrashReport crashReport =
                                    CrashReport.makeCrashReport(
                                            throwable, "Colliding entity with block");
                            CrashReportCategory crashReportCategory =
                                    crashReport.makeCategory("Block being collided with");
                            CrashReportCategory.addBlockInfo(
                                    crashReportCategory, blockPosValue2, blockState);
                            throw new ReportedException(crashReport);
                        }

                        k++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                x++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public Entity(World worldValue) {
        this.entityId = net.minecraft.entity.Entity.nextEntityID++;
        this.renderDistanceWeight = 1.0;
        this.boundingBox = net.minecraft.entity.Entity.ZERO_AABB;
        this.width = 0.6F;
        this.height = 1.8F;
        this.nextStepDistance = 1;
        this.rand = new Random();
        this.fireResistance = 1;
        this.firstUpdate = true;
        this.legacyInvisibilitySample =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!(this instanceof LocalPlayer) && this.rand.nextBoolean() ? 1 : 0));
        this.entityUniqueID = MathHelper.randomUuid(this.rand);
        this.cmdResultStats = new CommandResultStats();
        this.world = worldValue;
        this.setPosition(0.0, 0.0, 0.0);
        if (worldValue != null) {
            this.dimension = worldValue.provider.getDimensionId();
        }

        this.dataWatcher = new DataWatcher(this);
        this.dataWatcher.addObject(0, (byte) 0);
        this.dataWatcher.addObject(1, (short) 300);
        this.dataWatcher.addObject(3, (byte) 0);
        this.dataWatcher.addObject(2, packedTextFragments[0]);
        this.dataWatcher.addObject(4, (byte) 0);
        this.entityInit();
    }

    public boolean isOffsetPositionInLiquid(double p171_0, double p171_1, double p171_2) {
        net.minecraft.util.BoundingBox boundingBoxValue =
                this.getEntityBoundingBox().offset(p171_0, p171_1, p171_2);
        return this.isLiquidPresentInAABB(boundingBoxValue);
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return true;
    }

    public boolean isInLava() {
        if (isFreeCamActive()) {
            return false;
        }
        return this.world.isMaterialInBB(
                this.getEntityBoundingBox().expand(-0.1F, -0.4F, -0.1F), Material.lava);
    }

    public void handleHealthUpdate(byte b0) {}

    // region String decoding support

    // endregion

    public double getDistanceSq(BlockPos blockPos) {
        return blockPos.distanceSq(this.posX, this.posY, this.posZ);
    }

    public EntityItem dropItem(Item item, int i) {
        return this.dropItemWithOffset(item, i, 0.0F);
    }

    public String getName() {
        if (this.jvm171_d_45ffeb83d14f3c()) {
            return this.getCustomNameTag();
        }

        String sx = EntityList.getEntityString(this);
        if (sx == null) {
            sx = "generic";
        }

        return StatCollector.translateToLocal("entity." + sx + ".name");
    }

    public boolean isSpectatedByPlayer(EntityPlayerMP entityPlayerMP) {
        return true;
    }

    public void copyDataFromOld(Entity entity) {
        net.minecraft.nbt.CompoundTag compoundTag = new net.minecraft.nbt.CompoundTag();
        entity.writeToNBT(compoundTag);
        this.readFromNBT(compoundTag);
        this.timeUntilPortal = entity.timeUntilPortal;
        this.teleportDirection = entity.teleportDirection;
    }

    public boolean jvm171_d_45ffeb83d14f3c() {
        try {
            if (this.dataWatcher.getWatchableObjectString(2).length() > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public double getDistanceSqToEntity(Entity entity) {
        double deltaPosX = this.posX - entity.posX;
        double deltaPosY = this.posY - entity.posY;
        double deltaPosZ = this.posZ - entity.posZ;
        return deltaPosX * deltaPosX + deltaPosY * deltaPosY + deltaPosZ * deltaPosZ;
    }

    public BlockPos getPosition() {
        return new net.minecraft.util.BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }

    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0F;
    }

    protected String getSplashSound() {
        return "game.neutral.swim.splash";
    }

    public void moveToBlockPosAndAngles(BlockPos blockPos, float f, float f1) {
        this.setLocationAndAngles(
                blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, f, f1);
    }

    public boolean isSlient() {
        try {
            if (this.dataWatcher.getWatchableObjectByte(4) == 1) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public boolean isPushedByWater() {
        return true;
    }

    protected void preparePlayerToSpawn() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.world != null) {
            while (true) {
                if (this.posY > 0.0 && this.posY < 256.0) {
                    this.setPosition(this.posX, this.posY, this.posZ);
                    if (!this.world
                                    .getCollidingBoundingBoxes(this, this.getEntityBoundingBox())
                                    .isEmpty()
                            || astring != null) {
                        this.posY++;
                        if (astring == null) {
                            continue;
                        }
                    }
                }

                this.motionX = this.motionY = this.motionZ = 0.0;
                this.rotationPitch = 0.0F;
                break;
            }
        }
    }

    public boolean isEntityInsideOpaqueBlock() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.noClip) {
            return false;
        }

        int i = 0;

        while (i < 8) {
            double d0 = this.posX + ((i >> 0) % 2 - 0.5F) * this.width * 0.8F;
            double d1 = this.posY + ((i >> 1) % 2 - 0.5F) * 0.1F;
            double d2 = this.posZ + ((i >> 2) % 2 - 0.5F) * this.width * 0.8F;
            if (this.world
                    .getBlockState(new BlockPos(d0, d1 + this.getEyeHeight(), d2))
                    .getBlock()
                    .isVisuallyOpaque()) {
                return true;
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        return false;
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

    public boolean isImmuneToExplosions() {
        return false;
    }

    private void resetPositionToBoundingBox() {
        this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
        this.posY = this.getEntityBoundingBox().minY;
        this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
    }

    public double getDistanceSq(double p171_0, double p171_1, double p171_2) {
        double d3 = this.posX - p171_0;
        double d4 = this.posY - p171_1;
        double d5 = this.posZ - p171_2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public int getBrightnessForRender(float f) {
        BlockPos blockPos = new BlockPos(this.posX, 0.0, this.posZ);
        if (this.world.isBlockLoaded(blockPos)) {
            double d0 =
                    (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 0.66;
            int i = MathHelper.floor(this.posY + d0);
            return this.world.getCombinedLight(blockPos.offsetUp(i), 0);
        } else {
            return 0;
        }
    }

    public double getDistance(double d0, double d1, double d2) {
        double d3 = this.posX - d0;
        double d4 = this.posY - d1;
        double d5 = this.posZ - d2;
        return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public void mountEntity(Entity ridingEntityValue) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        String[] astring = astring1;
        if (ridingEntityValue == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(
                        this.ridingEntity.posX,
                        this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height,
                        this.ridingEntity.posZ,
                        this.rotationYaw,
                        this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }

            this.ridingEntity = null;
            if (astring == null) {
                return;
            }
        }

        if (this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
        }

        Entity ridingEntityValue2 = ridingEntityValue.ridingEntity;

        while (ridingEntityValue2 != null) {
            if (ridingEntityValue2 == this) {
                return;
            }

            ridingEntityValue2 = ridingEntityValue2.ridingEntity;
            if (astring != null) {
                break;
            }
        }

        this.ridingEntity = ridingEntityValue;
        ridingEntityValue.riddenByEntity = this;
    }

    public void addEntityCrashInfo(CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Entity Type", new EntityTypeCrashDetail(this));
        crashReportCategory.addCrashSection("Entity ID", this.entityId);
        crashReportCategory.addCrashSectionCallable("Entity Name", new EntityNameCrashDetail(this));
        crashReportCategory.addCrashSection(
                "Entity's Exact location",
                String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        crashReportCategory.addCrashSection(
                "Entity's Block location",
                CrashReportCategory.getCoordinateInfo(
                        MathHelper.floor(this.posX),
                        MathHelper.floor(this.posY),
                        MathHelper.floor(this.posZ)));
        crashReportCategory.addCrashSection(
                "Entity's Momentum",
                String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
        crashReportCategory.addCrashSectionCallable(
                "Entity's Rider", new EntityPassengerCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Entity's Vehicle", new EntityVehicleCrashDetail(this));
    }

    public Vec3 getPositionVector() {
        return new net.minecraft.util.Vec3(this.posX, this.posY, this.posZ);
    }

    public Vec3 getPositionEyes(float p171_0) {
        try {
            if (p171_0 == 1.0F) {
                return new net.minecraft.util.Vec3(
                        this.posX, this.posY + this.getEyeHeight(), this.posZ);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        double d0 = this.prevPosX + (this.posX - this.prevPosX) * p171_0;
        double d1 = this.prevPosY + (this.posY - this.prevPosY) * p171_0 + this.getEyeHeight();
        double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * p171_0;
        return new net.minecraft.util.Vec3(d0, d1, d2);
    }

    public boolean isEntityAlive() {
        try {
            if (!this.isDead) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.entity.Entity.packedTextFragments = new String[2];
        net.minecraft.entity.Entity.packedTextFragments[0] = new String("");
        setLegacyGlobalFlowMarker(null);
        net.minecraft.entity.Entity.ZERO_AABB = new BoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    // endregion

    public void moveEntity(double moveX, double moveY, double moveZ) {
        boolean legacyCollisionFlow = SharedMonsterAttributes.getLegacyEntityFlowMarker() != null;
        if (this.noClip) {
            this.setBoundingBox(this.getEntityBoundingBox().offset(moveX, moveY, moveZ));
            this.resetPositionToBoundingBox();
            if (!legacyCollisionFlow) {
                return;
            }
        }

        this.world.theProfiler.startSection("move");
        double oldX = this.posX;
        double oldY = this.posY;
        double oldZ = this.posZ;
        if (this.isInWeb) {
            this.isInWeb = false;
            moveX *= 0.25;
            moveY *= 0.05F;
            moveZ *= 0.25;
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
        }

        double requestedX = moveX;
        double requestedY = moveY;
        double requestedZ = moveZ;
        SafeWalkEvent safeWalkEvent = new SafeWalkEvent();
        if (this instanceof LocalPlayer) {
            RemixClient.eventBus.post(safeWalkEvent);
        }

        // SafeWalk listeners request edge protection by cancelling the event.
        boolean protectAgainstLedges =
                (this.onGround && this.isSneaking() && this instanceof PlayerEntity)
                        || safeWalkEvent.isCancelled();
        if (protectAgainstLedges) {
            while (moveX != 0.0
                    && this.world
                            .getCollidingBoundingBoxes(
                                    this, this.getEntityBoundingBox().offset(moveX, -1.0, 0.0))
                            .isEmpty()) {
                moveX = reduceLedgeMotion(moveX, legacyCollisionFlow);
                requestedX = moveX;
                if (legacyCollisionFlow) {
                    break;
                }
            }
            while (moveZ != 0.0
                    && this.world
                            .getCollidingBoundingBoxes(
                                    this, this.getEntityBoundingBox().offset(0.0, -1.0, moveZ))
                            .isEmpty()) {
                moveZ = reduceLedgeMotion(moveZ, legacyCollisionFlow);
                requestedZ = moveZ;
                if (legacyCollisionFlow) {
                    break;
                }
            }
            while (moveX != 0.0
                    && moveZ != 0.0
                    && this.world
                            .getCollidingBoundingBoxes(
                                    this, this.getEntityBoundingBox().offset(moveX, -1.0, moveZ))
                            .isEmpty()) {
                moveX = reduceLedgeMotion(moveX, legacyCollisionFlow);
                requestedX = moveX;
                moveZ = reduceLedgeMotion(moveZ, legacyCollisionFlow);
                requestedZ = moveZ;
                if (legacyCollisionFlow) {
                    break;
                }
            }
        }

        List<BoundingBox> obstacles =
                this.world.getCollidingBoundingBoxes(
                        this, this.getEntityBoundingBox().addCoord(moveX, moveY, moveZ));
        BoundingBox originalBox = this.getEntityBoundingBox();

        moveY = clipCollisionY(obstacles, this.getEntityBoundingBox(), moveY, legacyCollisionFlow);

        this.setBoundingBox(this.getEntityBoundingBox().offset(0.0, moveY, 0.0));
        boolean canStepUp = this.onGround || (requestedY != moveY && requestedY < 0.0);

        moveX = clipCollisionX(obstacles, this.getEntityBoundingBox(), moveX, legacyCollisionFlow);

        this.setBoundingBox(this.getEntityBoundingBox().offset(moveX, 0.0, 0.0));

        moveZ = clipCollisionZ(obstacles, this.getEntityBoundingBox(), moveZ, legacyCollisionFlow);

        this.setBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, moveZ));
        float allowedStepHeight = this.stepHeight;
        StepEvent stepEvent = new StepEvent(MotionPhase.PRE, allowedStepHeight);
        if (this instanceof LocalPlayer) {
            RemixClient.eventBus.post(stepEvent);
        }

        allowedStepHeight = stepEvent.getHeight();
        if (!stepEvent.isCancelled()
                && allowedStepHeight > 0.0F
                && canStepUp
                && (requestedX != moveX || requestedZ != moveZ)) {
            double flatX = moveX;
            double flatY = moveY;
            double flatZ = moveZ;
            BoundingBox flatBox = this.getEntityBoundingBox();
            this.setBoundingBox(originalBox);
            moveY = allowedStepHeight;
            List<BoundingBox> stepObstacles =
                    this.world.getCollidingBoundingBoxes(
                            this,
                            this.getEntityBoundingBox().addCoord(requestedX, moveY, requestedZ));
            BoundingBox sweptStepBox = this.getEntityBoundingBox();
            BoundingBox horizontalSweep = sweptStepBox.addCoord(requestedX, 0.0, requestedZ);
            double sweptLift = moveY;

            sweptLift =
                    clipCollisionY(stepObstacles, horizontalSweep, sweptLift, legacyCollisionFlow);

            sweptStepBox = sweptStepBox.offset(0.0, sweptLift, 0.0);
            double sweptStepX = requestedX;

            sweptStepX =
                    clipCollisionX(stepObstacles, sweptStepBox, sweptStepX, legacyCollisionFlow);

            sweptStepBox = sweptStepBox.offset(sweptStepX, 0.0, 0.0);
            double sweptStepZ = requestedZ;

            sweptStepZ =
                    clipCollisionZ(stepObstacles, sweptStepBox, sweptStepZ, legacyCollisionFlow);

            sweptStepBox = sweptStepBox.offset(0.0, 0.0, sweptStepZ);
            BoundingBox verticalStepBox = this.getEntityBoundingBox();
            double verticalLift = moveY;

            verticalLift =
                    clipCollisionY(
                            stepObstacles, verticalStepBox, verticalLift, legacyCollisionFlow);

            verticalStepBox = verticalStepBox.offset(0.0, verticalLift, 0.0);
            double verticalStepX = requestedX;

            verticalStepX =
                    clipCollisionX(
                            stepObstacles, verticalStepBox, verticalStepX, legacyCollisionFlow);

            verticalStepBox = verticalStepBox.offset(verticalStepX, 0.0, 0.0);
            double verticalStepZ = requestedZ;

            verticalStepZ =
                    clipCollisionZ(
                            stepObstacles, verticalStepBox, verticalStepZ, legacyCollisionFlow);

            verticalStepBox = verticalStepBox.offset(0.0, 0.0, verticalStepZ);
            double sweptStepDistanceSquared = sweptStepX * sweptStepX + sweptStepZ * sweptStepZ;
            double verticalStepDistanceSquared =
                    verticalStepX * verticalStepX + verticalStepZ * verticalStepZ;
            boolean sweptStepIsBetter = sweptStepDistanceSquared > verticalStepDistanceSquared;
            if (sweptStepIsBetter) {
                moveX = sweptStepX;
                moveZ = sweptStepZ;
                moveY = -sweptLift;
                this.setBoundingBox(sweptStepBox);
            }
            if (!sweptStepIsBetter || legacyCollisionFlow) {
                moveX = verticalStepX;
                moveZ = verticalStepZ;
                moveY = -verticalLift;
                this.setBoundingBox(verticalStepBox);
            }

            moveY =
                    clipCollisionY(
                            stepObstacles, this.getEntityBoundingBox(), moveY, legacyCollisionFlow);
            // Keep the recovered second downward sweep, including the single-obstacle mode.
            moveY =
                    clipCollisionY(
                            stepObstacles, this.getEntityBoundingBox(), moveY, legacyCollisionFlow);

            this.setBoundingBox(this.getEntityBoundingBox().offset(0.0, moveY, 0.0));
            if (flatX * flatX + flatZ * flatZ >= moveX * moveX + moveZ * moveZ) {
                moveX = flatX;
                moveY = flatY;
                moveZ = flatZ;
                this.setBoundingBox(flatBox);
            }

            double stepRise = this.getEntityBoundingBox().minY - this.posY;
            if (this instanceof LocalPlayer) {
                RemixClient.eventBus.post(new StepEvent(MotionPhase.POST, (float) stepRise));
            }
        }

        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("rest");
        this.resetPositionToBoundingBox();
        this.collidedHorizontally =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (requestedX == moveX && requestedZ == moveZ ? 0 : 1));
        this.collidedVertically =
                remix.compat.compiler.JvmBooleanSlots.fromInt((requestedY != moveY ? 1 : 0));
        this.onGround =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.collidedVertically && requestedY < 0.0 ? 1 : 0));
        this.collided =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!this.collidedHorizontally && !this.collidedVertically ? 0 : 1));
        int l = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.2F);
        int i1 = MathHelper.floor(this.posZ);
        BlockPos blockPos = new BlockPos(l, j, i1);
        Block block = this.world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.AIR) {
            Block blockValue = this.world.getBlockState(blockPos.offsetDown()).getBlock();
            if (blockValue instanceof BlockFence
                    || blockValue instanceof BlockWall
                    || blockValue instanceof BlockFenceGate) {
                block = blockValue;
                blockPos = blockPos.offsetDown();
            }
        }

        this.updateFallState(moveY, this.onGround, block, blockPos);
        if (requestedX != moveX) {
            this.motionX = 0.0;
        }

        if (requestedZ != moveZ) {
            this.motionZ = 0.0;
        }

        if (requestedY != moveY) {
            block.onLanded(this.world, this);
        }

        if (this.canTriggerWalking() && !protectAgainstLedges && this.ridingEntity == null) {
            double walkX = this.posX - oldX;
            double walkY = this.posY - oldY;
            double walkZ = this.posZ - oldZ;
            if (block != Blocks.ladder) {
                walkY = 0.0;
            }

            if (block != null && this.onGround) {
                block.onEntityCollidedWithBlock(this.world, blockPos, this);
            }

            this.distanceWalkedModified =
                    (float)
                            (this.distanceWalkedModified
                                    + MathHelper.sqrt(walkX * walkX + walkZ * walkZ) * 0.6);
            this.distanceWalkedOnStepModified =
                    (float)
                            (this.distanceWalkedOnStepModified
                                    + MathHelper.sqrt(walkX * walkX + walkY * walkY + walkZ * walkZ)
                                            * 0.6);
            if (this.distanceWalkedOnStepModified > this.nextStepDistance
                    && block.getMaterial() != Material.AIR) {
                this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;
                if (this.isInWater()) {
                    float f1 =
                            MathHelper.sqrt(
                                            this.motionX * this.motionX * 0.2F
                                                    + this.motionY * this.motionY
                                                    + this.motionZ * this.motionZ * 0.2F)
                                    * 0.35F;
                    if (f1 > 1.0F) {
                        f1 = 1.0F;
                    }

                    this.playSound(
                            this.getSwimSound(),
                            f1,
                            1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                }

                this.playStepSound(blockPos, block);
            }
        }

        try {
            this.doBlockCollisions();
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Checking entity block collision");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashReportCategory);
            throw new ReportedException(crashReport);
        }

        boolean wet = this.isWet();
        boolean touchingFire =
                this.world.isFlammableWithin(
                        this.getEntityBoundingBox().contract(0.001, 0.001, 0.001));
        boolean resetFireResistance = !touchingFire;
        if (touchingFire) {
            this.dealFireDamage(1);
            if (!wet && ++this.fire == 0) {
                this.setFire(8);
                resetFireResistance = legacyCollisionFlow;
            }
        }
        if (resetFireResistance && this.fire <= 0) {
            this.fire = -this.fireResistance;
        }

        if (wet && this.fire > 0) {
            this.playSound(
                    "random.fizz",
                    0.7F,
                    1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
            this.fire = -this.fireResistance;
        }

        this.world.theProfiler.endSection();
    }

    private static double reduceLedgeMotion(double motion, boolean legacyCollisionFlow) {
        double increment = 0.05;
        if (motion < increment && motion >= -increment) {
            motion = 0.0;
            if (!legacyCollisionFlow) {
                return motion;
            }
        }
        if (motion > 0.0) {
            motion -= increment;
            if (!legacyCollisionFlow) {
                return motion;
            }
        }
        return motion + increment;
    }

    private static double clipCollisionY(
            List<BoundingBox> obstacles,
            BoundingBox movingBox,
            double offset,
            boolean legacyCollisionFlow) {
        for (BoundingBox obstacle : obstacles) {
            offset = obstacle.calculateYOffset(movingBox, offset);
            if (legacyCollisionFlow) {
                break;
            }
        }
        return offset;
    }

    private static double clipCollisionX(
            List<BoundingBox> obstacles,
            BoundingBox movingBox,
            double offset,
            boolean legacyCollisionFlow) {
        for (BoundingBox obstacle : obstacles) {
            offset = obstacle.calculateXOffset(movingBox, offset);
            if (legacyCollisionFlow) {
                break;
            }
        }
        return offset;
    }

    private static double clipCollisionZ(
            List<BoundingBox> obstacles,
            BoundingBox movingBox,
            double offset,
            boolean legacyCollisionFlow) {
        for (BoundingBox obstacle : obstacles) {
            offset = obstacle.calculateZOffset(movingBox, offset);
            if (legacyCollisionFlow) {
                break;
            }
        }
        return offset;
    }

    protected NBTTagList newDoubleNBTList(double... adouble) {
        NBTTagList nbtTagList = new NBTTagList();
        double[] adouble1 = adouble;
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int length = adouble.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            double d0 = adouble1[j];
            nbtTagList.appendTag(new NBTTagDouble(d0));
            j++;
            if (astring != null) {
                break;
            }
        }

        return nbtTagList;
    }

    protected void setRotation(float p171_0, float p171_1) {
        this.rotationYaw = p171_0 % 360.0F;
        this.rotationPitch = p171_1 % 360.0F;
    }

    public void setAir(int p171_0) {
        this.dataWatcher.updateObject(1, (short) p171_0);
    }

    public boolean isBurning() {
        int var1 = this.world != null && this.world.isRemote ? 1 : 0;
        return !this.isImmuneToFire && (this.fire > 0 || var1 != 0 && this.getFlag(0));
    }

    public boolean isOutsideBorder() {
        return this.isOutsideBorder;
    }

    public void setPositionAndRotation(
            double posXValue,
            double posYValue,
            double posZValue,
            float rotationYawValue,
            float rotationPitchValue) {
        this.prevPosX = this.posX = posXValue;
        this.prevPosY = this.posY = posYValue;
        this.prevPosZ = this.posZ = posZValue;
        this.prevRotationYaw = this.rotationYaw = rotationYawValue;
        this.prevRotationPitch = this.rotationPitch = rotationPitchValue;
        double d3 = this.prevRotationYaw - rotationYawValue;

        try {
            if (d3 < -180.0) {
                this.prevRotationYaw += 360.0F;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        try {
            if (d3 >= 180.0) {
                this.prevRotationYaw -= 360.0F;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw (reportedExceptionValue);
        }

        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(rotationYawValue, rotationPitchValue);
    }

    public void setInvisible(boolean p171_0) {
        this.setFlag(5, p171_0);
    }

    public boolean isRiding() {
        try {
            if (this.ridingEntity != null) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public boolean writeToNBTOptional(CompoundTag compoundTag) {
        String sx = this.getEntityString();
        if (!this.isDead && sx != null && this.riddenByEntity == null) {
            compoundTag.setString("id", sx);
            this.writeToNBT(compoundTag);
            return true;
        } else {
            return false;
        }
    }

    public boolean isEating() {
        return this.getFlag(4);
    }

    public void setAlwaysRenderNameTag(boolean alwaysRender) {
        this.dataWatcher.updateObject(3, (byte) (alwaysRender ? 1 : 0));
    }

    public void onDataWatcherUpdate(int i) {}

    public void fall(float p171_0, float p171_1) {
        try {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.fall(p171_0, p171_1);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public final Vec3 getVectorForRotation(float f, float f1) {
        float f2 = MathHelper.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
        float f5 = MathHelper.sin(-f * (float) (Math.PI / 180.0));
        return new Vec3(f3 * f4, f5, f2 * f4);
    }

    public void onKillCommand() {
        this.setDead();
    }

    protected void applyEnchantments(LivingEntity livingEntity, Entity entity) {
        try {
            if (entity instanceof net.minecraft.entity.LivingEntity) {
                net.minecraft.enchantment.EnchantmentHelper.applyThornEnchantments(
                        (net.minecraft.entity.LivingEntity) entity, livingEntity);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        net.minecraft.enchantment.EnchantmentHelper.applyArthropodEnchantments(
                livingEntity, entity);
    }

    public void setPositionAndRotation2(
            double d0, double d1, double d2, float f, float f1, int k, boolean flag) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.setPosition(d0, d1, d2);
        this.setRotation(f, f1);
        String[] astring = astring1;
        List collidingBoundingBoxes =
                this.world.getCollidingBoundingBoxes(
                        this, this.getEntityBoundingBox().contract(0.03125, 0.0, 0.03125));
        if (!collidingBoundingBoxes.isEmpty()) {
            double d3 = 0.0;
            int i = 0;
            int j = collidingBoundingBoxes.size();

            while (i < j) {
                BoundingBox boundingBoxValue = (BoundingBox) collidingBoundingBoxes.get(i);
                if (boundingBoxValue.maxY > d3) {
                    d3 = boundingBoxValue.maxY;
                }

                i++;
                if (astring != null) {
                    break;
                }
            }

            d1 += d3 - this.getEntityBoundingBox().minY;
            this.setPosition(d0, d1, d2);
        }
    }

    public void setPositionAndUpdate(double p171_0, double p171_1, double p171_2) {
        this.setLocationAndAngles(p171_0, p171_1, p171_2, this.rotationYaw, this.rotationPitch);
    }

    public BoundingBox getEntityBoundingBox() {
        return this.boundingBox;
    }

    public void setWorld(World worldValue) {
        this.world = worldValue;
    }

    public float getEyeHeight() {
        return this.height * 0.85F;
    }

    public void turn(float f, float f1) {
        float rotationPitchValue = this.rotationPitch;
        float rotationYawValue = this.rotationYaw;
        this.rotationYaw = (float) (this.rotationYaw + f * 0.15);
        this.rotationPitch = (float) (this.rotationPitch - f1 * 0.15);
        this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
        this.prevRotationPitch = this.prevRotationPitch + (this.rotationPitch - rotationPitchValue);
        this.prevRotationYaw = this.prevRotationYaw + (this.rotationYaw - rotationYawValue);
    }

    public void setDead() {
        this.isDead = true;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    protected void setSize(float widthValue, float heightValue) {
        if (widthValue != this.width || heightValue != this.height) {
            float widthValue2 = this.width;
            this.width = widthValue;
            this.height = heightValue;
            this.setBoundingBox(
                    new net.minecraft.util.BoundingBox(
                            this.getEntityBoundingBox().minX,
                            this.getEntityBoundingBox().minY,
                            this.getEntityBoundingBox().minZ,
                            this.getEntityBoundingBox().minX + this.width,
                            this.getEntityBoundingBox().minY + this.height,
                            this.getEntityBoundingBox().minZ + this.width));
            if (this.width > widthValue2 && !this.firstUpdate && !this.world.isRemote) {
                this.moveEntity(widthValue2 - this.width, 0.0, widthValue2 - this.width);
            }
        }
    }

    protected boolean pushOutOfBlocks(double d0, double d1, double d2) {
        BlockPos blockPos = new BlockPos(d0, d1, d2);
        double d3 = d0 - blockPos.getX();
        double d4 = d1 - blockPos.getY();
        double d5 = d2 - blockPos.getZ();
        List list = this.world.getCollisionBoxes(this.getEntityBoundingBox());
        if (list.isEmpty() && !this.world.isBlockFullCube(blockPos)) {
            return false;
        }

        byte b0 = 3;
        double d6 = 9999.0;
        if (!this.world.isBlockFullCube(blockPos.offsetWest()) && d3 < d6) {
            d6 = d3;
            b0 = 0;
        }

        if (!this.world.isBlockFullCube(blockPos.offsetEast()) && 1.0 - d3 < d6) {
            d6 = 1.0 - d3;
            b0 = 1;
        }

        if (!this.world.isBlockFullCube(blockPos.offsetUp()) && 1.0 - d4 < d6) {
            d6 = 1.0 - d4;
            b0 = 3;
        }

        if (!this.world.isBlockFullCube(blockPos.offsetNorth()) && d5 < d6) {
            d6 = d5;
            b0 = 4;
        }

        if (!this.world.isBlockFullCube(blockPos.offsetSouth()) && 1.0 - d5 < d6) {
            d6 = 1.0 - d5;
            b0 = 5;
        }

        float motionZValue = this.rand.nextFloat() * 0.2F + 0.1F;
        if (b0 == 0) {
            this.motionX = -motionZValue;
        }

        if (b0 == 1) {
            this.motionX = motionZValue;
        }

        if (b0 == 3) {
            this.motionY = motionZValue;
        }

        if (b0 == 4) {
            this.motionZ = -motionZValue;
        }

        if (b0 == 5) {
            this.motionZ = motionZValue;
        }

        return true;
    }

    public boolean isWet() {
        return this.inWater
                || this.world.canLightningStrike(
                        new net.minecraft.util.BlockPos(this.posX, this.posY, this.posZ))
                || this.world.canLightningStrike(
                        new net.minecraft.util.BlockPos(
                                this.posX, this.posY + this.height, this.posZ));
    }

    public static String[] getLegacyGlobalFlowMarker() {
        return net.minecraft.entity.Entity.legacyGlobalFlowMarker;
    }

    public Vec3 getLook(float p171_0) {
        try {
            if (p171_0 == 1.0F) {
                return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        float f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p171_0;
        float f2 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * p171_0;
        return this.getVectorForRotation(f1, f2);
    }

    public boolean canBePushed() {
        return false;
    }

    public void addChatMessage(ChatComponent chatComponent) {}

    public Vec3 getLookVec() {
        return null;
    }

    public BoundingBox getCollisionBox(Entity entity) {
        return null;
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        return false;
    }

    public CompoundTag getNBTTagCompound() {
        return null;
    }

    public static void setLegacyGlobalFlowMarker(String[] astring) {
        net.minecraft.entity.Entity.legacyGlobalFlowMarker = astring;
    }

    protected void createRunningParticles() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.2F);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getRenderType() != -1) {
            this.world.spawnParticle(
                    EnumParticleTypes.BLOCK_CRACK,
                    this.posX + (this.rand.nextFloat() - 0.5) * this.width,
                    this.getEntityBoundingBox().minY + 0.1,
                    this.posZ + (this.rand.nextFloat() - 0.5) * this.width,
                    -this.motionX * 4.0,
                    1.5,
                    -this.motionZ * 4.0,
                    new int[] {Block.getStateId(blockState)});
        }
    }

    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }

    public boolean isInRangeToRender3d(double p171_0, double p171_1, double p171_2) {
        double d3 = this.posX - p171_0;
        double d4 = this.posY - p171_1;
        double d5 = this.posZ - p171_2;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;
        return this.isInRangeToRenderDist(d6);
    }

    protected abstract void writeEntityToNBT(CompoundTag compoundTag);

    protected void resetHeight() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        float f =
                MathHelper.sqrt(
                                this.motionX * this.motionX * 0.2F
                                        + this.motionY * this.motionY
                                        + this.motionZ * this.motionZ * 0.2F)
                        * 0.2F;
        String[] astring = astring1;
        if (f > 1.0F) {
            f = 1.0F;
        }

        this.playSound(
                this.getSplashSound(),
                f,
                1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
        float f1 = MathHelper.floor(this.getEntityBoundingBox().minY);
        int i = 0;

        while (i < 1.0F + this.width * 20.0F) {
            float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
            float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
            this.world.spawnParticle(
                    EnumParticleTypes.WATER_BUBBLE,
                    this.posX + f2,
                    f1 + 1.0F,
                    this.posZ + f3,
                    this.motionX,
                    this.motionY - this.rand.nextFloat() * 0.2F,
                    this.motionZ,
                    new int[0]);
            i++;
            if (astring != null) {
                break;
            }
        }

        i = 0;

        while (i < 1.0F + this.width * 20.0F) {
            float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
            float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
            this.world.spawnParticle(
                    EnumParticleTypes.WATER_SPLASH,
                    this.posX + f4,
                    f1 + 1.0F,
                    this.posZ + f5,
                    this.motionX,
                    this.motionY,
                    this.motionZ,
                    new int[0]);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public void copyCommandStatsFrom(Entity entity) {
        this.cmdResultStats.copyFrom(entity.getCommandStats());
    }

    public void setOutsideBorder(boolean flag) {
        this.isOutsideBorder = flag;
    }

    public void setInPortal() {
        boolean legacyPortalFlow = SharedMonsterAttributes.getLegacyEntityFlowMarker() != null;
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
            if (!legacyPortalFlow) {
                return;
            }
        }

        double movementX = this.prevPosX - this.posX;
        double movementZ = this.prevPosZ - this.posZ;
        if (!this.world.isRemote && !this.inPortal) {
            boolean enterOnXAxis =
                    MathHelper.abs((float) movementX) > MathHelper.abs((float) movementZ);
            Facing direction =
                    enterOnXAxis && !legacyPortalFlow
                            ? (movementX > 0.0 ? Facing.WEST : Facing.EAST)
                            : (movementZ > 0.0 ? Facing.NORTH : Facing.SOUTH);
            this.teleportDirection = direction.getHorizontalIndex();
        }
        this.inPortal = true;
    }

    public int getPortalCooldown() {
        return 300;
    }

    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(2);
    }

    @Override
    public String toString() {
        return String.format(
                "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
                this.getClass().getSimpleName(),
                this.getName(),
                this.entityId,
                this.world == null ? "~NULL~" : this.world.getWorldInfo().getWorldName(),
                this.posX,
                this.posY,
                this.posZ);
    }

    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }

    public void playSound(String text, float p171_1, float p171_2) {
        try {
            if (!this.isSlient()) {
                this.world.playSoundAtEntity(this, text, p171_1, p171_2);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public float getBrightness(float f) {
        BlockPos blockPos = new BlockPos(this.posX, 0.0, this.posZ);
        if (this.world.isBlockLoaded(blockPos)) {
            double d0 =
                    (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * 0.66;
            int i = MathHelper.floor(this.posY + d0);
            return this.world.getLightBrightness(blockPos.offsetUp(i));
        } else {
            return 0.0F;
        }
    }

    public double getMountedYOffset() {
        return this.height * 0.75;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public boolean isEntityEqual(Entity entity) {
        try {
            if (this == entity) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    protected void dealFireDamage(int p171_0) {
        try {
            if (!this.isImmuneToFire) {
                this.attackEntityFrom(net.minecraft.util.DamageSource.inFire, p171_0);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        String[] legacyEntityFlowMarker = SharedMonsterAttributes.getLegacyEntityFlowMarker();

        try {
            NBTTagList positionTag = compoundTag.getTagList("Pos", 6);
            NBTTagList motionTag = compoundTag.getTagList("Motion", 6);
            NBTTagList rotationTag = compoundTag.getTagList("Rotation", 5);
            this.motionX = motionTag.getDoubleAt(0);
            this.motionY = motionTag.getDoubleAt(1);
            this.motionZ = motionTag.getDoubleAt(2);
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }

            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }

            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }

            this.prevPosX = this.lastTickPosX = this.posX = positionTag.getDoubleAt(0);
            this.prevPosY = this.lastTickPosY = this.posY = positionTag.getDoubleAt(1);
            this.prevPosZ = this.lastTickPosZ = this.posZ = positionTag.getDoubleAt(2);
            this.prevRotationYaw = this.rotationYaw = rotationTag.getFloat(0);
            this.prevRotationPitch = this.rotationPitch = rotationTag.getFloat(1);
            this.fallDistance = compoundTag.getFloat("FallDistance");
            this.fire = compoundTag.getShort("Fire");
            this.setAir(compoundTag.getShort("Air"));
            this.onGround = compoundTag.getBoolean("OnGround");
            this.dimension = compoundTag.getInteger("Dimension");
            this.invulnerable = compoundTag.getBoolean("Invulnerable");
            this.timeUntilPortal = compoundTag.getInteger("PortalCooldown");
            boolean hasBinaryUuid =
                    compoundTag.hasKey("UUIDMost", 4) && compoundTag.hasKey("UUIDLeast", 4);
            if (hasBinaryUuid) {
                this.entityUniqueID =
                        new UUID(compoundTag.getLong("UUIDMost"), compoundTag.getLong("UUIDLeast"));
                if (legacyEntityFlowMarker != null) {
                    setLegacyGlobalFlowMarker(new String[5]);
                }
            }
            if ((!hasBinaryUuid || legacyEntityFlowMarker != null)
                    && compoundTag.hasKey("UUID", 8)) {
                this.entityUniqueID = UUID.fromString(compoundTag.getString("UUID"));
            }

            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (compoundTag.hasKey("CustomName", 8)
                    && compoundTag.getString("CustomName").length() > 0) {
                this.setCustomNameTag(compoundTag.getString("CustomName"));
            }

            this.setAlwaysRenderNameTag(compoundTag.getBoolean("CustomNameVisible"));
            this.cmdResultStats.readStatsFromNBT(compoundTag);
            this.setSilent(compoundTag.getBoolean("Silent"));
            this.readEntityFromNBT(compoundTag);
            if (this.shouldSetPositionAfterLoading()) {
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Entity being loaded");
            this.addEntityCrashInfo(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    public void setEating(boolean p171_0) {
        this.setFlag(4, p171_0);
    }

    public boolean canAttackWithItem() {
        return true;
    }

    public BoundingBox getCollisionBoundingBox() {
        return null;
    }

    public RayTraceResult rayTrace(double d0, float f) {
        Vec3 positionEyes = this.getPositionEyes(f);
        Vec3 vec3 = this.getLook(f);
        Vec3 vec3Value = positionEyes.addVector(vec3.x * d0, vec3.y * d0, vec3.z * d0);
        return this.world.rayTraceBlocks(positionEyes, vec3Value, false, false, true);
    }

    public float getCollisionBorderSize() {
        float f = 0.0F;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        AuraModule aura = ClientServices.moduleManager.aura;
        if (aura.isEnabled()) {
            LivingEntity currentTarget = aura.currentTarget;
            if (currentTarget == this) {
                f = (float) aura.hitboxOffset.asDouble();
            }
        }

        return 0.1F + f;
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        return false;
    }

    public void setSneaking(boolean flag) {
        this.setFlag(1, flag);
    }

    @Override
    public boolean equals(Object p171_0) {
        return p171_0 instanceof net.minecraft.entity.Entity
                && ((net.minecraft.entity.Entity) p171_0).entityId == this.entityId;
    }

    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }

    public CommandResultStats getCommandStats() {
        return this.cmdResultStats;
    }

    public Entity[] getParts() {
        return null;
    }

    public ChatComponent getDisplayName() {
        ChatComponentText chatComponentText = new ChatComponentText(this.getName());
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getUniqueId().toString());
        return chatComponentText;
    }

    public void setSilent(boolean silent) {
        this.dataWatcher.updateObject(4, (byte) (silent ? 1 : 0));
    }

    public boolean verifyExplosion(
            Explosion explosion,
            World worldValue,
            BlockPos blockPos,
            BlockState blockState,
            float f) {
        return true;
    }

    protected abstract void entityInit();

    public void writeToNBT(CompoundTag compoundTag) {
        try {
            compoundTag.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
            compoundTag.setTag(
                    "Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
            compoundTag.setTag(
                    "Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            compoundTag.setFloat("FallDistance", this.fallDistance);
            compoundTag.setShort("Fire", (short) this.fire);
            compoundTag.setShort("Air", (short) this.getAir());
            compoundTag.setBoolean("OnGround", this.onGround);
            compoundTag.setInteger("Dimension", this.dimension);
            compoundTag.setBoolean("Invulnerable", this.invulnerable);
            compoundTag.setInteger("PortalCooldown", this.timeUntilPortal);
            compoundTag.setLong("UUIDMost", this.getUniqueId().getMostSignificantBits());
            compoundTag.setLong("UUIDLeast", this.getUniqueId().getLeastSignificantBits());
            if (this.getCustomNameTag() != null && this.getCustomNameTag().length() > 0) {
                compoundTag.setString("CustomName", this.getCustomNameTag());
                compoundTag.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
            }

            this.cmdResultStats.writeStatsToNBT(compoundTag);
            if (this.isSlient()) {
                compoundTag.setBoolean("Silent", this.isSlient());
            }

            this.writeEntityToNBT(compoundTag);
            if (this.ridingEntity != null) {
                CompoundTag compoundTagValue = new CompoundTag();
                if (this.ridingEntity.writeMountToNBT(compoundTagValue)) {
                    compoundTag.setTag("Riding", compoundTagValue);
                }
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Entity being saved");
            this.addEntityCrashInfo(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    // region String decoding support

    // endregion

    public void setCurrentItemOrArmor(int i, ItemStack itemStack) {}

    protected void setOnFireFromLava() {
        try {
            if (!this.isImmuneToFire) {
                this.attackEntityFrom(net.minecraft.util.DamageSource.lava, 4.0F);
                this.setFire(15);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public boolean isSneaking() {
        return this.getFlag(1);
    }

    public Entity getCommandSenderEntity() {
        return this;
    }

    public boolean isInvisibleToPlayer(PlayerEntity playerEntity) {
        return !playerEntity.isSpectator() && this.isInvisible();
    }

    public void onChunkLoaded() {}

    public void moveFlying(float strafe, float forward, float acceleration) {
        float yaw = this.rotationYaw;
        if (this == MinecraftClient.getMinecraft().player) {
            StrafeEvent input = new StrafeEvent(yaw, forward, strafe);
            RemixClient.eventBus.post(input);
            yaw = input.getYaw();
            forward = input.getForward();
            strafe = input.getStrafe();
        }

        float inputLengthSquared = strafe * strafe + forward * forward;
        if (!(inputLengthSquared >= 1.0E-4F)) {
            return;
        }
        float scale = acceleration / Math.max(1.0F, MathHelper.sqrt(inputLengthSquared));
        strafe *= scale;
        forward *= scale;
        float yawRadians = yaw * (float) Math.PI / 180.0F;
        float sin = MathHelper.sin(yawRadians);
        float cos = MathHelper.cos(yawRadians);
        this.motionX += strafe * cos - forward * sin;
        this.motionZ += forward * cos + strafe * sin;
    }

    public World getEntityWorld() {
        return this.world;
    }

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p171_1) {
        try {
            if (this.isEntityInvulnerable(damageSource)) {
                return false;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        this.setBeenAttacked();
        return false;
    }

    public void setFire(int p171_0) {
        int j = p171_0 * 20;
        j = net.minecraft.enchantment.EnchantmentProtection.getFireTimeForEntity(this, j);

        try {
            if (this.fire < j) {
                this.fire = j;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public void clientUpdateEntityNBT(CompoundTag compoundTag) {}

    public boolean isSprinting() {
        return this.getFlag(3);
    }

    public void setSprinting(boolean p171_0) {
        this.setFlag(3, p171_0);
    }

    public ItemStack[] getInventory() {
        return null;
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {
        this.cmdResultStats.applyResult(this, commandResultStatsType, i);
    }

    public void applyEntityCollision(Entity entity) {
        if (entity.riddenByEntity != this
                && entity.ridingEntity != this
                && !entity.noClip
                && !this.noClip) {
            double deltaPosX = entity.posX - this.posX;
            double deltaPosZ = entity.posZ - this.posZ;
            double d2 = MathHelper.maxAbs(deltaPosX, deltaPosZ);
            if (d2 >= 0.01F) {
                d2 = MathHelper.sqrt(d2);
                deltaPosX /= d2;
                deltaPosZ /= d2;
                double d3 = 1.0 / d2;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }

                deltaPosX *= d3;
                deltaPosZ *= d3;
                deltaPosX *= 0.05F;
                deltaPosZ *= 0.05F;
                deltaPosX *= 1.0F - this.entityCollisionReduction;
                deltaPosZ *= 1.0F - this.entityCollisionReduction;
                if (this.riddenByEntity == null) {
                    this.addVelocity(-deltaPosX, 0.0, -deltaPosZ);
                }

                if (entity.riddenByEntity == null) {
                    entity.addVelocity(deltaPosX, 0.0, deltaPosZ);
                }
            }
        }
    }

    public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
        try {
            this.attackEntityFrom(net.minecraft.util.DamageSource.lightningBolt, 5.0F);
            this.fire++;
            if (this.fire == 0) {
                this.setFire(8);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public boolean sendCommandFeedback() {
        return false;
    }

    public void onEntityUpdate() {
        boolean legacyFlow = SharedMonsterAttributes.getLegacyEntityFlowMarker() != null;
        this.world.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }

        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.world.isRemote && this.world instanceof WorldServer) {
            this.world.theProfiler.startSection("portal");
            MinecraftServer server = ((WorldServer) this.world).getMinecraftServer();
            this.updatePortalProgress(server, legacyFlow);
            if (this.timeUntilPortal > 0) {
                this.timeUntilPortal--;
            }
            this.world.theProfiler.endSection();
        }

        this.spawnRunningParticles();
        this.handleWaterMovement();
        this.tickBurning(legacyFlow);
        if (this.isInLava()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5F;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.world.isRemote) {
            this.setFlag(0, this.fire > 0);
        }

        this.firstUpdate = false;
        this.world.theProfiler.endSection();
    }

    private void updatePortalProgress(MinecraftServer server, boolean legacyFlow) {
        int maximumPortalTime = this.getMaxInPortalTime();
        if (this.inPortal) {
            if (!server.getAllowNether()) {
                return;
            }
            if (this.ridingEntity == null && this.portalCounter++ >= maximumPortalTime) {
                this.portalCounter = maximumPortalTime;
                this.timeUntilPortal = this.getPortalCooldown();
                int destination =
                        this.world.provider.getDimensionId() == -1 && !legacyFlow ? 0 : -1;
                this.travelToDimension(destination);
            }
            this.inPortal = false;
            if (!legacyFlow) {
                return;
            }
        }

        if (this.portalCounter > 0) {
            this.portalCounter -= 4;
        }
        if (this.portalCounter < 0) {
            this.portalCounter = 0;
        }
    }

    private void tickBurning(boolean legacyFlow) {
        if (this.world.isRemote) {
            this.fire = 0;
            if (!legacyFlow) {
                return;
            }
        }
        if (this.fire <= 0) {
            return;
        }

        if (this.isImmuneToFire) {
            this.fire -= 4;
            if (this.fire >= 0) {
                return;
            }
            this.fire = 0;
            if (!legacyFlow) {
                return;
            }
        }
        if (this.fire % 20 == 0) {
            this.attackEntityFrom(DamageSource.onFire, 1.0F);
        }
        this.fire--;
    }

    public void extinguish() {
        this.fire = 0;
    }

    public boolean handleWaterMovement() {
        boolean legacyEntityFlow = SharedMonsterAttributes.getLegacyEntityFlowMarker() != null;
        if (isFreeCamActive()) {
            return false;
        }

        BoundingBox waterBounds =
                this.getEntityBoundingBox().expand(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001);
        if (this.world.handleMaterialAcceleration(waterBounds, Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.fallDistance = 0.0F;
            this.inWater = true;
            this.fire = 0;
            if (!legacyEntityFlow) {
                return true;
            }
        }

        this.inWater = false;
        return false;
    }

    private static boolean isFreeCamActive() {
        RemixClient.INSTANCE.getClientServices();
        return ClientServices.moduleManager.freeCam.isEnabled();
    }

    public float getRotationYawHead() {
        return 0.0F;
    }

    public void travelToDimension(int dimensionValue) {
        if (!this.world.isRemote && !this.isDead) {
            this.world.theProfiler.startSection("changeDimension");
            MinecraftServer server = MinecraftServer.getServer();
            int j = this.dimension;
            WorldServer worldServer = server.worldServerForDimension(j);
            WorldServer worldServerValue = server.worldServerForDimension(dimensionValue);
            this.dimension = dimensionValue;
            if (j == 1 && dimensionValue == 1) {
                worldServerValue = server.worldServerForDimension(0);
                this.dimension = 0;
            }

            this.world.removeEntity(this);
            this.isDead = false;
            this.world.theProfiler.startSection("reposition");
            server.getConfigurationManager()
                    .transferEntityToWorld(this, j, worldServer, worldServerValue);
            this.world.theProfiler.endStartSection("reloading");
            Entity entity =
                    EntityList.createEntityByName(
                            EntityList.getEntityString(this), worldServerValue);
            if (entity != null) {
                entity.copyDataFromOld(this);
                if (j == 1 && dimensionValue == 1) {
                    BlockPos blockPos =
                            this.world.getTopSolidOrLiquidBlock(worldServerValue.getSpawnPoint());
                    entity.moveToBlockPosAndAngles(
                            blockPos, entity.rotationYaw, entity.rotationPitch);
                }

                worldServerValue.spawnEntityInWorld(entity);
            }

            this.isDead = true;
            this.world.theProfiler.endSection();
            worldServer.resetUpdateEntityTick();
            worldServerValue.resetUpdateEntityTick();
            this.world.theProfiler.endSection();
        }
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        BlockSoundType ajf = block.stepSound;
        String[] astring = astring1;
        if (this.world.getBlockState(blockPos.offsetUp()).getBlock() == Blocks.snow_layer) {
            ajf = Blocks.snow_layer.stepSound;
            this.playSound(ajf.getStepSound(), ajf.getVolume() * 0.15F, ajf.getFrequency());
            if (astring == null) {
                return;
            }
        }

        if (!block.getMaterial().isLiquid()) {
            this.playSound(ajf.getStepSound(), ajf.getVolume() * 0.15F, ajf.getFrequency());
        }
    }

    public int getMaxInPortalTime() {
        return 0;
    }

    protected boolean shouldSetPositionAfterLoading() {
        return true;
    }

    public boolean hitByEntity(Entity entity) {
        return false;
    }

    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {}

    public double getDistanceSqToCenter(BlockPos blockPos) {
        return blockPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
    }

    public void onKillEntity(LivingEntity livingEntity) {}

    protected void updateFallState(double deltaY, boolean landed, Block block, BlockPos position) {
        boolean legacyFallFlow = SharedMonsterAttributes.getLegacyEntityFlowMarker() != null;
        if (landed) {
            if (!(this.fallDistance > 0.0F)) {
                return;
            }
            if (block != null) {
                block.onFallenUpon(this.world, position, this, this.fallDistance);
            }
            if (block == null || legacyFallFlow) {
                this.fall(this.fallDistance, 1.0F);
            }

            this.fallDistance = 0.0F;
            if (!legacyFallFlow) {
                return;
            }
        }
        if (deltaY < 0.0) {
            this.fallDistance = (float) (this.fallDistance - deltaY);
        }
    }

    protected abstract void readEntityFromNBT(CompoundTag compoundTag);

    public double getYOffset() {
        return 0.0;
    }

    public void performHurtAnimation() {}

    public void setRotationYawHead(float f) {}

    public int getMaxFallHeight() {
        return 3;
    }

    protected String getSwimSound() {
        return "game.neutral.swim";
    }

    public UUID getUniqueId() {
        return this.entityUniqueID;
    }

    public void setEntityId(int entityIdValue) {
        this.entityId = entityIdValue;
    }

    @Override
    public int hashCode() {
        return this.entityId;
    }

    public float getDistanceToEntity(Entity entity) {
        float deltaPosX = (float) (this.posX - entity.posX);
        float deltaPosY = (float) (this.posY - entity.posY);
        float deltaPosZ = (float) (this.posZ - entity.posZ);
        return MathHelper.sqrt(
                deltaPosX * deltaPosX + deltaPosY * deltaPosY + deltaPosZ * deltaPosZ);
    }

    public void updateRiderPosition() {
        try {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.setPosition(
                        this.posX,
                        this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                        this.posZ);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public boolean isInsideOfMaterial(Material material) {
        double d0 = this.posY + this.getEyeHeight();
        BlockPos blockPos = new BlockPos(this.posX, d0, this.posZ);
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial() == material) {
            float f =
                    BlockLiquid.getLiquidHeightPercent(
                                    blockState.getBlock().getMetaFromState(blockState))
                            - 0.11111111F;
            float f1 = blockPos.getY() + 1 - f;
            int i = d0 < f1 ? 1 : 0;
            return (i != 0 || !(this instanceof PlayerEntity)) && i != 0;
        } else {
            return false;
        }
    }

    protected NBTTagList newFloatNBTList(float... afloat) {
        NBTTagList nbtTagList = new NBTTagList();
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int i = 0;
        String[] astring = astring1;
        int j = afloat.length;

        while (i < j) {
            nbtTagList.appendTag(new NBTTagFloat(afloat[i]));
            i++;
            if (astring != null) {
                break;
            }
        }

        return nbtTagList;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public void setLocationAndAngles(
            double posXValue,
            double posYValue,
            double posZValue,
            float rotationYawValue,
            float rotationPitchValue) {
        this.lastTickPosX = this.prevPosX = this.posX = posXValue;
        this.lastTickPosY = this.prevPosY = this.posY = posYValue;
        this.lastTickPosZ = this.prevPosZ = this.posZ = posZValue;
        this.rotationYaw = rotationYawValue;
        this.rotationPitch = rotationPitchValue;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    protected boolean getFlag(int p171_0) {
        try {
            if ((this.dataWatcher.getWatchableObjectByte(0) & 1 << p171_0) != 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public boolean isEntityInvulnerable(DamageSource damageSource) {
        return this.invulnerable
                && damageSource != net.minecraft.util.DamageSource.outOfWorld
                && !damageSource.isCreativePlayer();
    }

    public boolean getAlwaysRenderNameTag() {
        try {
            if (this.dataWatcher.getWatchableObjectByte(3) == 1) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public float getExplosionResistance(
            Explosion explosion, World worldValue, BlockPos blockPos, BlockState blockState) {
        return blockState.getBlock().getExplosionResistance(this);
    }

    public void addVelocity(double p171_0, double p171_1, double p171_2) {
        this.motionX += p171_0;
        this.motionY += p171_1;
        this.motionZ += p171_2;
        this.isAirBorne = true;
    }

    public void setBoundingBox(BoundingBox boundingBoxValue) {
        this.boundingBox = boundingBoxValue;
    }

    public boolean isInRangeToRenderDist(double p171_0) {
        double averageEdgeLength = this.getEntityBoundingBox().getAverageEdgeLength();
        averageEdgeLength *= 64.0 * this.renderDistanceWeight;

        try {
            if (p171_0 < averageEdgeLength * averageEdgeLength) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public boolean canRenderOnFire() {
        return this.isBurning();
    }

    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }

    protected void setFlag(int i, boolean flag) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        byte b0 = this.dataWatcher.getWatchableObjectByte(0);
        String[] astring = astring1;
        if (flag) {
            this.dataWatcher.updateObject(0, (byte) (b0 | 1 << i));
            if (astring == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(0, (byte) (b0 & ~(1 << i)));
    }

    public boolean isInWater() {
        return this.inWater;
    }

    public void copyLocationAndAnglesFrom(Entity entity) {
        this.setLocationAndAngles(
                entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
    }

    public void updateRidden() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
            if (astring == null) {
                return;
            }
        }

        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.onUpdate();
        if (this.ridingEntity != null) {
            this.ridingEntity.updateRiderPosition();
            this.entityRiderYawDelta =
                    this.entityRiderYawDelta
                            + (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
            this.entityRiderPitchDelta =
                    this.entityRiderPitchDelta
                            + (this.ridingEntity.rotationPitch
                                    - this.ridingEntity.prevRotationPitch);

            while (this.entityRiderYawDelta >= 180.0) {
                this.entityRiderYawDelta -= 360.0;
                if (astring != null) {
                    break;
                }
            }

            while (this.entityRiderYawDelta < -180.0) {
                this.entityRiderYawDelta += 360.0;
                if (astring != null) {
                    break;
                }
            }

            while (this.entityRiderPitchDelta >= 180.0) {
                this.entityRiderPitchDelta -= 360.0;
                if (astring != null) {
                    break;
                }
            }

            while (this.entityRiderPitchDelta < -180.0) {
                this.entityRiderPitchDelta += 360.0;
                if (astring != null) {
                    break;
                }
            }

            double d0 = this.entityRiderYawDelta * 0.5;
            double d1 = this.entityRiderPitchDelta * 0.5;
            float f = 10.0F;
            if (d0 > f) {
                d0 = f;
            }

            if (d0 < -f) {
                d0 = -f;
            }

            if (d1 > f) {
                d1 = f;
            }

            if (d1 < -f) {
                d1 = -f;
            }

            this.entityRiderYawDelta -= d0;
            this.entityRiderPitchDelta -= d1;
        }
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }

    public boolean writeMountToNBT(CompoundTag compoundTag) {
        String sx = this.getEntityString();
        if (!this.isDead && sx != null) {
            compoundTag.setString("id", sx);
            this.writeToNBT(compoundTag);
            return true;
        } else {
            return false;
        }
    }

    public int getTeleportDirection() {
        return this.teleportDirection;
    }

    public void addToPlayerScore(Entity entity, int i) {}

    public EntityItem entityDropItem(ItemStack itemStack, float f) {
        if (itemStack.stackSize != 0 && itemStack.getItem() != null) {
            EntityItem entityItem =
                    new EntityItem(this.world, this.posX, this.posY + f, this.posZ, itemStack);
            entityItem.setDefaultPickupDelay();
            this.world.spawnEntityInWorld(entityItem);
            return entityItem;
        } else {
            return null;
        }
    }

    public Facing getHorizontalFacing() {
        return Facing.getHorizontal(MathHelper.floor(this.rotationYaw * 4.0F / 360.0F + 0.5) & 3);
    }

    protected void kill() {
        this.setDead();
    }

    public EntityItem dropItemWithOffset(Item item, int i, float f) {
        return this.entityDropItem(new ItemStack(item, i, 0), f);
    }
}
