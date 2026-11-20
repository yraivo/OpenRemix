package net.minecraft.block.material;

import net.minecraft.block.material.MaterialMaterial1;

public class Material {
    public static final Material wood = new Material(MapColor.woodColor).setBurning();
    public static final Material cake = new Material(MapColor.stoneColor).setNoPushMobility();
    public static final Material grass = new Material(MapColor.aVA);
    public static final Material vine =
            new MaterialLogic(MapColor.aVH)
                    .setBurning()
                    .setNoPushMobility()
                    .setReplaceable();
    public static final Material rock = new Material(MapColor.aVs).setRequiresTool();
    public static final Material AIR = new MaterialTransparent(MapColor.stoneColor);
    public static final Material carpet = new MaterialLogic(MapColor.aVo).setBurning();
    public static final Material sand = new Material(MapColor.sandColor);
    public static final Material dragonEgg = new Material(MapColor.aVH).setNoPushMobility();
    public static final Material redstoneLight = new Material(MapColor.stoneColor).Fo();
    private boolean lt;
    private boolean canBurn;
    public static final Material clay = new Material(MapColor.aVq);
    public static final Material iron = new Material(MapColor.ironColor).setRequiresTool();
    private int mobilityFlag;
    public static final Material plants =
            new MaterialLogic(MapColor.aVH).setNoPushMobility();
    public static final Material cloth = new Material(MapColor.aVo).setBurning();
    public static final Material ice = new Material(MapColor.aaf).Fm().Fo();
    public static final Material cactus = new Material(MapColor.aVH).Fm().setNoPushMobility();
    public static final Material fire =
            new MaterialTransparent(MapColor.stoneColor).setNoPushMobility();
    public static final Material circuits =
            new MaterialLogic(MapColor.stoneColor).setNoPushMobility();
    public static final Material barrier =
            new Material(MapColor.stoneColor).setRequiresTool().setImmovableMobility();
    public static final Material ground = new Material(MapColor.dirtColor);
    private MapColor materialMapColor;
    public static final Material portal =
            new MaterialPortal(MapColor.stoneColor).setImmovableMobility();
    public static final Material piston = new Material(MapColor.aVs).setImmovableMobility();
    public static final Material water = new MaterialLiquid(MapColor.waterColor).setNoPushMobility();
    public static final Material craftedSnow = new Material(MapColor.snowColor).setRequiresTool();
    public static final Material packedIce = new Material(MapColor.aaf).Fo();
    public static final Material leaves =
            new Material(MapColor.aVH).setBurning().Fm().setNoPushMobility();
    public static final Material coral = new Material(MapColor.aVH).setNoPushMobility();
    public static final Material gourd = new Material(MapColor.aVH).setNoPushMobility();
    private static String[] nm;
    private boolean requiresNoTool = true;
    private boolean nn;
    private boolean replaceable;
    public static final Material web =
            new MaterialMaterial1(MapColor.aVo).setRequiresTool().setNoPushMobility();
    public static final Material anvil =
            new Material(MapColor.ironColor).setRequiresTool().setImmovableMobility();
    public static final Material sponge = new Material(MapColor.aVo);
    public static final Material tnt = new Material(MapColor.tntColor).setBurning().Fm();
    public static final Material lava = new MaterialLiquid(MapColor.tntColor).setNoPushMobility();
    public static final Material glass = new Material(MapColor.stoneColor).Fm().Fo();
    public static final Material snow =
            new MaterialLogic(MapColor.snowColor)
                    .setReplaceable()
                    .Fm()
                    .setRequiresTool()
                    .setNoPushMobility();

    protected Material setRequiresTool() {
        this.requiresNoTool = false;
        return this;
    }

    private Material Fm() {
        this.nn = true;
        return this;
    }

    protected Material setBurning() {
        this.canBurn = true;
        return this;
    }

    public Material(MapColor aWoValue) {
        this.materialMapColor = aWoValue;
    }

    protected Material setImmovableMobility() {
        this.mobilityFlag = 2;
        return this;
    }

    protected Material Fo() {
        this.lt = true;
        return this;
    }

    public boolean getCanBurn() {
        return this.canBurn;
    }

    protected Material setNoPushMobility() {
        this.mobilityFlag = 1;
        return this;
    }

    public boolean isLiquid() {
        return false;
    }

    public boolean isOpaque() {
        return this.nn ? false : this.blocksMovement();
    }

    public MapColor getMaterialMapColor() {
        return this.materialMapColor;
    }

    public boolean blocksMovement() {
        return true;
    }

    public int getMaterialMobility() {
        return this.mobilityFlag;
    }

    public static String[] Fr() {
        return nm;
    }

    // region Static initialization and string tables
    static {
        String[] astring = new String[2];
        b(astring);
    }

    // endregion

    public Material setReplaceable() {
        this.replaceable = true;
        return this;
    }

    public boolean isReplaceable() {
        return this.replaceable;
    }

    public boolean isSolid() {
        return true;
    }

    public boolean isToolNotRequired() {
        return this.requiresNoTool;
    }

    public static void b(String[] astring) {
        nm = astring;
    }

    public boolean blocksLight() {
        return true;
    }
}
