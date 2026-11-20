package remix.module;

import remix.module.combat.AimAssistModule;
import remix.module.combat.AuraModule;
import remix.module.combat.AutoHealModule;
import remix.module.combat.BowAimbotModule;
import remix.module.combat.CriticalsModule;
import remix.module.combat.ExtendedReachModule;
import remix.module.combat.FastBowModule;
import remix.module.combat.KnockbackPlusModule;
import remix.module.combat.RegenModule;
import remix.module.combat.TPAuraModule;
import remix.module.combat.TargetStrafeModule;
import remix.module.combat.VelocityModule;
import remix.module.exploit.BookControlModule;
import remix.module.exploit.BuildBattlesModule;
import remix.module.exploit.ChatBypassModule;
import remix.module.exploit.CreativeDropModule;
import remix.module.exploit.GhostHandModule;
import remix.module.exploit.PhaseModule;
import remix.module.exploit.PingSpoofModule;
import remix.module.exploit.PluginsModule;
import remix.module.exploit.ServerCrasherModule;
import remix.module.exploit.SignControlModule;
import remix.module.exploit.SpammerModule;
import remix.module.exploit.VanishDetectorModule;
import remix.module.hidden.BanCheckerModule;
import remix.module.hidden.FollowModule;
import remix.module.hidden.HealthDeathListenerModule;
import remix.module.hidden.HypixelLocrawListenerModule;
import remix.module.hidden.InventoryActionsTrackerModule;
import remix.module.hidden.MegaWallsKillTrackerModule;
import remix.module.hidden.MiniWitherStatusModule;
import remix.module.movement.BehindModule;
import remix.module.movement.BlinkModule;
import remix.module.movement.FlightModule;
import remix.module.movement.HighJumpModule;
import remix.module.movement.InventoryPlusModule;
import remix.module.movement.JesusModule;
import remix.module.movement.LongJumpModule;
import remix.module.movement.NoFallModule;
import remix.module.movement.NoSlowDownModule;
import remix.module.movement.ParkourModule;
import remix.module.movement.SneakModule;
import remix.module.movement.SpeedModule;
import remix.module.movement.SpiderModule;
import remix.module.movement.SprintModule;
import remix.module.movement.StepModule;
import remix.module.movement.TerrainModule;
import remix.module.movement.UnstuckModule;
import remix.module.player.AutoFishModule;
import remix.module.player.AutoHypixelModule;
import remix.module.player.AutoMLGModule;
import remix.module.player.AutoRespawnModule;
import remix.module.player.AutoToolModule;
import remix.module.player.ChestAuraModule;
import remix.module.player.ChestStealerModule;
import remix.module.player.DerpModule;
import remix.module.player.FastPlaceModule;
import remix.module.player.FastUseModule;
import remix.module.player.HackerDetectorModule;
import remix.module.player.InvManagerModule;
import remix.module.player.KeepRotationsModule;
import remix.module.player.KillSultsModule;
import remix.module.player.NoClipModule;
import remix.module.player.NoSwingModule;
import remix.module.player.PotionSaverModule;
import remix.module.player.SpeedMineModule;
import remix.module.render.BlockAnimationModule;
import remix.module.render.CameraModule;
import remix.module.render.CaveFinderModule;
import remix.module.render.ChamsModule;
import remix.module.render.ClickGuiModule;
import remix.module.render.CrackModule;
import remix.module.render.ESPModule;
import remix.module.render.FardModule;
import remix.module.render.FreeCamModule;
import remix.module.render.FullBrightModule;
import remix.module.render.HudModule;
import remix.module.render.NameProtectModule;
import remix.module.render.NameTagsModule;
import remix.module.render.NoRenderModule;
import remix.module.render.OnScreenRadarModule;
import remix.module.render.RotationModule;
import remix.module.render.SpectatorModule;
import remix.module.render.StorageESPModule;
import remix.module.render.TracersModule;
import remix.module.render.WireFrameModule;
import remix.module.render.XrayModule;
import remix.module.world.AntiBotModule;
import remix.module.world.AntiTrapModule;
import remix.module.world.AutoConfigModule;
import remix.module.world.CivBreakModule;
import remix.module.world.DisablerModule;
import remix.module.world.FakeHackerModule;
import remix.module.world.FuckerModule;
import remix.module.world.InfiniteModule;
import remix.module.world.NukerModule;
import remix.module.world.ScaffoldModule;
import remix.module.world.SpleefModule;
import remix.module.world.TimerModule;
import remix.module.world.WorldTimeModule;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public final class ModuleManager {
    public FuckerModule fucker;
    public CreativeDropModule creativeDrop;
    public ScaffoldModule scaffold;
    public TracersModule tracers;
    public HighJumpModule highJump;
    public VanishDetectorModule vanishDetector;
    public InvManagerModule invManager;
    public WireFrameModule wireFrame;
    public ServerCrasherModule serverCrasher;
    public VelocityModule velocity;
    public CrackModule crack;
    public NoSlowDownModule noSlowDown;
    public NukerModule nuker;
    public BlockAnimationModule blockAnimation;
    public SpleefModule spleef;
    public CaveFinderModule caveFinder;
    public FastPlaceModule fastPlace;
    public ParkourModule parkour;
    public AutoConfigModule autoConfig;
    public NameProtectModule nameProtect;
    public KnockbackPlusModule knockbackPlus;
    public AntiTrapModule antiTrap;
    public FullBrightModule fullBright;
    public TimerModule timer;
    public BehindModule behind;
    public FardModule fard;
    public SignControlModule signControl;
    public TPAuraModule tpAura;
    public LongJumpModule longJump;
    public HypixelLocrawListenerModule hypixelLocrawListener;
    public AutoMLGModule autoMLG;
    public SpiderModule spider;
    public CameraModule camera;
    public SprintModule sprint;
    private static final EnumMap<ModuleCategory, List<ModuleMetadata>> modulesByCategoryMap =
            new EnumMap<>(ModuleCategory.class);
    public HealthDeathListenerModule healthDeathListener;
    public ChatBypassModule chatBypass;
    public ChestStealerModule chestStealer;
    public DisablerModule disabler;
    public FastBowModule fastBow;
    public OnScreenRadarModule onScreenRadar;
    public JesusModule jesus;
    public SneakModule sneak;
    public GhostHandModule ghostHand;
    public KeepRotationsModule keepRotations;
    public PotionSaverModule potionSaver;
    public NoFallModule noFall;
    public FreeCamModule freeCam;
    public TargetStrafeModule targetStrafe;
    public ChestAuraModule chestAura;
    public FastUseModule fastUse;
    public CriticalsModule criticals;
    public NoClipModule noClip;
    public BuildBattlesModule buildBattles;
    public DerpModule derp;
    public PluginsModule plugins;
    public FakeHackerModule fakeHacker;
    public MegaWallsKillTrackerModule megaWallsKillTracker;
    public SpectatorModule spectator;
    public TerrainModule terrain;
    public AutoToolModule autoTool;
    public PhaseModule phase;
    public BowAimbotModule bowAimbot;
    public RotationModule rotation;
    public UnstuckModule unstuck;
    public PingSpoofModule pingSpoof;
    public BlinkModule blink;
    public ExtendedReachModule extendedReach;
    public AutoHealModule autoHeal;
    public InventoryPlusModule inventoryPlus;
    public StepModule step;
    public HackerDetectorModule hackerDetector;
    public InfiniteModule infinite;
    public final InventoryActionsTrackerModule inventoryActionsTracker = new InventoryActionsTrackerModule();
    public NoRenderModule noRender;
    public ESPModule esp;
    public NoSwingModule noSwing;
    public StorageESPModule storageESP;
    public AimAssistModule aimAssist;
    public WorldTimeModule worldTime;
    public HudModule hud;
    public AntiBotModule antiBot;
    public static final List<ModuleBase> modules = new ArrayList<>();
    public FlightModule flight;
    public RegenModule regen;
    public ChamsModule chams;
    public BookControlModule bookControl;
    public ClickGuiModule clickGui;
    public XrayModule xray;
    public AuraModule aura;
    public SpeedMineModule speedMine;
    public CivBreakModule civBreak;
    public AutoRespawnModule autoRespawn;
    public KillSultsModule killSults;
    public AutoFishModule autoFish;
    public SpammerModule spammer;
    public NameTagsModule nameTags;
    public SpeedModule speed;
    public MiniWitherStatusModule miniWitherStatus;
    public AutoHypixelModule autoHypixel;
    public final BanCheckerModule banChecker = new BanCheckerModule();
    public FollowModule follow;

    public ModuleBase getModuleByName(String text) {
        boolean flag1 = Setting.usesLegacySettingFlow();
        int i = 0;
        boolean flag = flag1;
        int j = modules.size();

        while (i < j) {
            ModuleBase moduleBase = modules.get(i);
            if (moduleBase.getDisplayName().equalsIgnoreCase(text)) {
                return moduleBase;
            }

            i++;
            if (flag) {
                break;
            }
        }

        return null;
    }

    public static List<ModuleMetadata> getModulesByCategory(ModuleCategory moduleCategory) {
        boolean flag = Setting.usesLegacySettingFlow();
        if (remix.module.ModuleManager.modulesByCategoryMap.containsKey(moduleCategory)) {
            return remix.module.ModuleManager.modulesByCategoryMap.get(moduleCategory);
        }

        ArrayList arrayList = new ArrayList();
        int i = 0;
        int j = modules.size();

        while (i < j) {
            ModuleMetadata moduleMetadata = (ModuleMetadata) modules.get(i);
            if (moduleMetadata.getCategory().equals(moduleCategory)) {
                arrayList.add(moduleMetadata);
            }

            i++;
            if (flag) {
                break;
            }
        }

        remix.module.ModuleManager.modulesByCategoryMap.put(moduleCategory, arrayList);
        return arrayList;
    }

    public static ModuleManager create() {
        return new ModuleManager();
    }

    private ModuleManager() {
        this.megaWallsKillTracker = new MegaWallsKillTrackerModule();
        this.miniWitherStatus = new MiniWitherStatusModule();
        this.hypixelLocrawListener = new HypixelLocrawListenerModule();
        this.healthDeathListener = new HealthDeathListenerModule();
        Collections.addAll(
                modules,
                this.aimAssist = new AimAssistModule(),
                this.aura = new AuraModule(),
                this.autoHeal = new AutoHealModule(),
                this.bowAimbot = new BowAimbotModule(),
                this.criticals = new CriticalsModule(),
                this.extendedReach = new ExtendedReachModule(),
                this.fastBow = new FastBowModule(),
                this.regen = new RegenModule(),
                this.targetStrafe = new TargetStrafeModule(),
                this.tpAura = new TPAuraModule(),
                this.velocity = new VelocityModule(),
                this.knockbackPlus = new KnockbackPlusModule(),
                this.bookControl = new BookControlModule(),
                this.buildBattles = new BuildBattlesModule(),
                this.chatBypass = new ChatBypassModule(),
                this.creativeDrop = new CreativeDropModule(),
                this.ghostHand = new GhostHandModule(),
                this.hackerDetector = new HackerDetectorModule(),
                this.phase = new PhaseModule(),
                this.pingSpoof = new PingSpoofModule(),
                this.plugins = new PluginsModule(),
                this.serverCrasher = new ServerCrasherModule(),
                this.signControl = new SignControlModule(),
                this.spammer = new SpammerModule(),
                this.vanishDetector = new VanishDetectorModule(),
                this.clickGui = new ClickGuiModule(),
                this.follow = new FollowModule(),
                this.behind = new BehindModule(),
                this.blink = new BlinkModule(),
                this.flight = new FlightModule(),
                this.freeCam = new FreeCamModule(),
                this.highJump = new HighJumpModule(),
                this.inventoryPlus = new InventoryPlusModule(),
                this.jesus = new JesusModule(),
                this.longJump = new LongJumpModule(),
                this.noSlowDown = new NoSlowDownModule(),
                this.parkour = new ParkourModule(),
                this.sneak = new SneakModule(),
                this.speed = new SpeedModule(),
                this.spider = new SpiderModule(),
                this.sprint = new SprintModule(),
                this.step = new StepModule(),
                this.terrain = new TerrainModule(),
                this.timer = new TimerModule(),
                this.autoFish = new AutoFishModule(),
                this.autoMLG = new AutoMLGModule(),
                this.autoHypixel = new AutoHypixelModule(),
                this.autoTool = new AutoToolModule(),
                this.chestAura = new ChestAuraModule(),
                this.chestStealer = new ChestStealerModule(),
                this.derp = new DerpModule(),
                this.fastPlace = new FastPlaceModule(),
                this.fastUse = new FastUseModule(),
                this.invManager = new InvManagerModule(),
                this.killSults = new KillSultsModule(),
                this.noClip = new NoClipModule(),
                this.noFall = new NoFallModule(),
                this.keepRotations = new KeepRotationsModule(),
                this.noSwing = new NoSwingModule(),
                this.potionSaver = new PotionSaverModule(),
                this.autoRespawn = new AutoRespawnModule(),
                this.speedMine = new SpeedMineModule(),
                this.unstuck = new UnstuckModule(),
                this.antiBot = new AntiBotModule(),
                this.antiTrap = new AntiTrapModule(),
                this.autoConfig = new AutoConfigModule(),
                this.civBreak = new CivBreakModule(),
                this.disabler = new DisablerModule(),
                this.fakeHacker = new FakeHackerModule(),
                this.fucker = new FuckerModule(),
                this.infinite = new InfiniteModule(),
                this.nuker = new NukerModule(),
                this.scaffold = new ScaffoldModule(),
                this.spleef = new SpleefModule(),
                this.worldTime = new WorldTimeModule(),
                this.blockAnimation = new BlockAnimationModule(),
                this.camera = new CameraModule(),
                this.caveFinder = new CaveFinderModule(),
                this.chams = new ChamsModule(),
                this.storageESP = new StorageESPModule(),
                this.crack = new CrackModule(),
                this.esp = new ESPModule(),
                this.nameProtect = new NameProtectModule(),
                this.nameTags = new NameTagsModule(),
                this.onScreenRadar = new OnScreenRadarModule(),
                this.fullBright = new FullBrightModule(),
                this.hud = new HudModule(),
                this.noRender = new NoRenderModule(),
                this.spectator = new SpectatorModule(),
                this.tracers = new TracersModule(),
                this.wireFrame = new WireFrameModule(),
                this.xray = new XrayModule(),
                this.fard = new FardModule(),
                this.rotation = new RotationModule());
        modules.sort(Comparator.comparingInt(moduleBase -> moduleBase.getName().length()));
    }
}
