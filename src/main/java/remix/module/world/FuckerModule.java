package remix.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.Vec3;
import remix.module.world.FuckerMode;
import remix.event.MotionPhase;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import com.google.common.collect.UnmodifiableIterator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** Fucker module: motion events and 9 settings. */
public final class FuckerModule extends ModuleBase {
    private final Setting cakeSetting;
    private final Setting teamsSetting;
    private final Setting rangeSetting;
    public BlockPos targetBlock;
    private final Setting fuckerSetting;
    private final Setting swingSetting;
    private final Setting openSetting;
    private final Setting rotateSetting;
    private final Setting dragonEggSetting;
    private final Facing[] adjacentFaces;
    private final Setting<FuckerMode> modeSetting;
    private final Setting breakOpenSetting;
    private final Map<Integer, String> teamColorNames;
    private boolean breakingObstruction;
    private final Setting bedSetting;

    public FuckerModule() {
        super("Fucker", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
        this.modeSetting = new Setting("Mode", this, FuckerMode.BREAK);
        this.rangeSetting = new Setting("Range", this, 4.3, 1.0, 6.0, 0.1);
        this.fuckerSetting = new Setting("Fucker", this, true);
        this.bedSetting = new Setting("Bed", this, true);
        this.cakeSetting = new Setting("Cake", this, true);
        this.dragonEggSetting = new Setting("Egg", this, true);
        this.openSetting = new Setting("Open", this, true);
        this.breakOpenSetting = new Setting("BreakOpen", this, true);
        this.rotateSetting = new Setting("Rotate", this, true);
        this.swingSetting = new Setting("Swing", this, true);
        this.teamsSetting = new Setting("Teams", this, true);
        this.adjacentFaces =
                new Facing[] {Facing.UP, Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST};
        this.teamColorNames = new HashMap<>();
        this.teamColorNames.put(0, "black");
        this.teamColorNames.put(170, "darkblue");
        this.teamColorNames.put(43520, "darkgreen");
        this.teamColorNames.put(43690, "darkpurple");
        this.teamColorNames.put(11141120, "darkred");
        this.teamColorNames.put(11141290, "purple");
        this.teamColorNames.put(16755200, "gold");
        this.teamColorNames.put(11184810, "gray");
        this.teamColorNames.put(5592405, "darkgray");
        this.teamColorNames.put(5592575, "blue");
        this.teamColorNames.put(5635925, "limegreen");
        this.teamColorNames.put(5636095, "aqualightblue");
        this.teamColorNames.put(16733525, "red");
        this.teamColorNames.put(16733695, "purple");
        this.teamColorNames.put(16777045, "yellow");
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        LocalPlayer player = minecraft.player;
        ClientWorld world = minecraft.world;

        if (event.getPhase() == MotionPhase.PRE) {
            double range = (int) this.rangeSetting.asDouble();
            if (this.targetBlock != null) {
                Block block = world.getBlockState(this.targetBlock).getBlock();
                if (!this.isEnabledTargetBlock(block) || player.getDistanceSq(this.targetBlock) > range) {
                    this.targetBlock = null;
                }
            }

            int r = (int) range;
            this.targetBlock = this.findNearestTargetBlock(player, world, r);

            if (this.targetBlock == null) {
                return;
            }

            if (this.teamsSetting.asBoolean()) {
                BlockPos blockPos = this.targetBlock.add(0, -1, 0);
                BlockState blockState = minecraft.world.getBlockState(blockPos);
                Block blockValue2 = blockState.getBlock();
                ScorePlayerTeam team = (ScorePlayerTeam) minecraft.player.getTeam();
                if (team != null
                        && (blockValue2 == Blocks.wool || blockValue2 == Blocks.stained_hardened_clay)) {
                    UnmodifiableIterator unmodifiableiterator =
                            blockState.getProperties().entrySet().iterator();

                    while (unmodifiableiterator.hasNext()) {
                        Entry entry = (Entry) unmodifiableiterator.next();
                        if (entry.getKey() instanceof PropertyEnum) {
                            PropertyEnum propertyEnum = (PropertyEnum) entry.getKey();
                            if (!propertyEnum.getName().equals("color")) {
                                break;
                            }

                            String text = FontRenderer.getFormatFromString(team.getColorPrefix());
                            if (text.length() >= 2) {
                                int colorCode = minecraft.fontRendererObj.getColorCode(text.charAt(1));
                                String orDefault = this.teamColorNames.getOrDefault(colorCode, "white");
                                String textValue = entry.getValue().toString();
                                if (orDefault.contains(textValue.toLowerCase())) {
                                    this.targetBlock = null;
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            if (this.openSetting.asBoolean()) {
                BlockPos targetBlockValue2;
                BlockState blockStateValue = minecraft.world.getBlockState(this.targetBlock);
                if (blockStateValue.getBlock() instanceof BlockBed) {
                    BlockPos blockPosValue = this.targetBlock.offset(
                            ((Facing) blockStateValue.getValue(BlockDirectional.AGE)).getOpposite());
                    targetBlockValue2 = this.findBreakableObstruction(this.targetBlock, blockPosValue);
                } else {
                    targetBlockValue2 = this.findBreakableObstruction(this.targetBlock);
                }

                if (targetBlockValue2 != null) {
                    if (this.breakOpenSetting.asBoolean()) {
                        this.targetBlock = targetBlockValue2;
                        this.breakingObstruction = true;
                    } else {
                        this.targetBlock = null;
                    }
                }
            }

            if (this.targetBlock == null || !this.rotateSetting.asBoolean()) {
                return;
            }

            Block blockValue3 = world.getBlockState(this.targetBlock).getBlock();
            double d7 = (blockValue3.getBlockBoundsMaxX() + blockValue3.getBlockBoundsMinX()) / 2.0;
            double d8 = (blockValue3.getBlockBoundsMaxY() + blockValue3.getBlockBoundsMinY()) / 2.0;
            double d9 = (blockValue3.getBlockBoundsMaxZ() + blockValue3.getBlockBoundsMinZ()) / 2.0;
            float[] afloat = RotationHelper.rotationsToPoint(
                    new Vec3(this.targetBlock).addVector(d7, d8, d9));
            event.setYaw(afloat[0]);
            event.setPitch(afloat[1]);
        } else if (event.getPhase() == MotionPhase.POST) {
            BlockPos targetBlockValue3 = this.targetBlock;
            if (targetBlockValue3 != null) {
                boolean interacted = false;
                FuckerMode fucker = (FuckerMode) this.fuckerSetting.getValue();
                PlayerController playerController = minecraft.playerController;

                if (fucker == FuckerMode.BREAK || this.breakingObstruction) {
                    CivBreakModule civBreak = ClientServices.moduleManager.civBreak;
                    if (civBreak.isEnabled()) {
                        civBreak.targetBlock = targetBlockValue3;
                    }
                    interacted = playerController.onPlayerDamageBlock(targetBlockValue3, Facing.UP);
                    this.breakingObstruction = false;
                } else if (fucker == FuckerMode.USE) {
                    interacted = playerController.onPlayerRightClick(
                            player,
                            world,
                            player.getCurrentEquippedItem(),
                            targetBlockValue3,
                            Facing.UP,
                            new Vec3(targetBlockValue3).addVector(0.5, 0.5, 0.5));
                }

                if (interacted) {
                    if (this.swingSetting.asBoolean()) {
                        player.swingItem();
                    }
                    this.sendPacket(new C0APacketAnimation());
                }
            }
        }
    }

    public String getSuffix() {
        return ((FuckerMode) this.fuckerSetting.getValue()).getDisplayName();
    }

    private boolean isEnabledTargetBlock(Block block) {
        return this.dragonEggSetting.asBoolean() && block == Blocks.dragon_egg
                || this.cakeSetting.asBoolean() && block == Blocks.cake
                || this.bedSetting.asBoolean() && block == Blocks.bed;
    }

    private BlockPos findBreakableObstruction(BlockPos... targetPositions) {
        LocalPlayer player = minecraft.player;
        ClientWorld world = minecraft.world;
        double bestHardness = -1.0;
        BlockPos bestPos = null;

        for (BlockPos targetPos : targetPositions) {
            for (Facing facing : this.adjacentFaces) {
                BlockPos neighborPos = targetPos.offset(facing);
                Block block = world.getBlockState(neighborPos).getBlock();
                if (!this.isEnabledTargetBlock(block)) {
                    if (!block.getMaterial().isSolid()) {
                        return null;
                    }

                    double hardness = block.getPlayerRelativeBlockHardness(player, world, neighborPos);
                    if (hardness > bestHardness) {
                        bestHardness = hardness;
                        bestPos = neighborPos;
                    }
                }
            }
        }

        return bestPos;
    }

    private BlockPos findNearestTargetBlock(LocalPlayer player, ClientWorld world, int r) {
        for (int x = -r; x < r; x++) {
            for (int y = r; y > -r; y--) {
                for (int z = -r; z < r; z++) {
                    BlockPos targetPos = new BlockPos(player.posX + x, player.posY + y, player.posZ + z);
                    Block block = world.getBlockState(targetPos).getBlock();
                    if (this.isEnabledTargetBlock(block)) {
                        return targetPos;
                    }
                }
            }
        }
        return null;
    }

}
