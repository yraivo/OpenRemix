package net.minecraft.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior10;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior2;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior3;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior4;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior5;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior6;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior7;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior8;
import net.minecraft.init.BootstrapBlockDispenserDispenserBehavior9;
import net.minecraft.init.BootstrapEntityArrowDispenserBehavior;
import net.minecraft.init.BootstrapEntityEggDispenserBehavior;
import net.minecraft.init.BootstrapEntityExpBottleDispenserBehavior;
import net.minecraft.init.BootstrapEntitySnowballDispenserBehavior;
import net.minecraft.init.BootstrapEnumDyeColorDispenserBehavior;
import net.minecraft.init.BootstrapItemBucketDispenserBehavior;
import net.minecraft.init.BootstrapItemPotionDispenserBehavior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class Bootstrap {
    private static Logger LOGGER;
    private static boolean alreadyRegistered;
    private static PrintStream SYSOUT;
    private static int dD;

    // region String decoding support

    // endregion

    public static int cR() {
        return dD;
    }

    static void registerDispenserBehaviors() {
        int j = cR();
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.arrow, new BootstrapEntityArrowDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.egg, new BootstrapEntityEggDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.snowball, new BootstrapEntitySnowballDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.experience_bottle, new BootstrapEntityExpBottleDispenserBehavior());
        int i = j;
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.potionitem, new BootstrapItemPotionDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.spawn_egg, new BootstrapBlockDispenserDispenserBehavior10());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.fireworks, new BootstrapBlockDispenserDispenserBehavior6());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.fire_charge, new BootstrapBlockDispenserDispenserBehavior4());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.boat, new BootstrapBlockDispenserDispenserBehavior2());
        BootstrapItemBucketDispenserBehavior bootstrapItemBucketDispenserBehavior = new BootstrapItemBucketDispenserBehavior();
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.lava_bucket, bootstrapItemBucketDispenserBehavior);
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.water_bucket, bootstrapItemBucketDispenserBehavior);
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.bucket, new BootstrapBlockDispenserDispenserBehavior8());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.flint_and_steel, new BootstrapBlockDispenserDispenserBehavior3());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.dye, new BootstrapEnumDyeColorDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Item.getItemFromBlock(Blocks.tnt), new BootstrapBlockDispenserDispenserBehavior());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Items.skull, new BootstrapBlockDispenserDispenserBehavior5());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Item.getItemFromBlock(Blocks.pumpkin), new BootstrapBlockDispenserDispenserBehavior7());
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                Item.getItemFromBlock(Blocks.command_block), new BootstrapBlockDispenserDispenserBehavior9());
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public static void register() {
        if (!net.minecraft.init.Bootstrap.alreadyRegistered) {
            net.minecraft.init.Bootstrap.alreadyRegistered = true;
            if (net.minecraft.init.Bootstrap.LOGGER.isDebugEnabled()) {
                redirectOutputToLog();
            }

            Block.registerBlocks();
            BlockFire.init();
            Item.registerItems();
            StatList.init();
            registerDispenserBehaviors();
        }
    }

    public static boolean isRegistered() {
        return net.minecraft.init.Bootstrap.alreadyRegistered;
    }

    // region String decoding support

    // endregion

    public static int f() {
        int i = cR();
        return i == 0 ? 126 : 0;
    }

    static {
        b(85);
        net.minecraft.init.Bootstrap.SYSOUT = System.out;
        net.minecraft.init.Bootstrap.alreadyRegistered = false;
        net.minecraft.init.Bootstrap.LOGGER = LogManager.getLogger();
    }

    public static void printToSYSOUT(String text) {
        net.minecraft.init.Bootstrap.SYSOUT.println(text);
    }

    public static void b(int i) {
        dD = i;
    }

    private static void redirectOutputToLog() {
        System.setErr(new LoggingPrintStream("STDERR", System.err));
        System.setOut(new LoggingPrintStream("STDOUT", net.minecraft.init.Bootstrap.SYSOUT));
    }
}
