package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class MapGenStructureIO {

    private static Map startClassToNameMap;
    private static Map startNameToClassMap;

    private static Map componentNameToClassMap;
    private static Map componentClassToNameMap;
    private static Logger logger;

    private static void registerStructure(Class oclass, String text) {
        net.minecraft.world.gen.structure.MapGenStructureIO.startNameToClassMap.put(text, oclass);
        net.minecraft.world.gen.structure.MapGenStructureIO.startClassToNameMap.put(oclass, text);
    }

    public static String getStructureComponentName(StructureComponent structureComponent) {
        return (String)
                net.minecraft.world.gen.structure.MapGenStructureIO.componentClassToNameMap.get(structureComponent.getClass());
    }

    static {
        net.minecraft.world.gen.structure.MapGenStructureIO.logger = LogManager.getLogger();
        net.minecraft.world.gen.structure.MapGenStructureIO.startNameToClassMap = Maps.newHashMap();
        net.minecraft.world.gen.structure.MapGenStructureIO.startClassToNameMap = Maps.newHashMap();
        net.minecraft.world.gen.structure.MapGenStructureIO.componentNameToClassMap = Maps.newHashMap();
        net.minecraft.world.gen.structure.MapGenStructureIO.componentClassToNameMap = Maps.newHashMap();
        registerStructure(StructureMineshaftStart.class, "Mineshaft");
        registerStructure(MapGenVillageStart.class, "Village");
        registerStructure(MapGenNetherBridgeStart.class, "Fortress");
        registerStructure(MapGenStrongholdStart.class, "Stronghold");
        registerStructure(MapGenScatteredFeatureStart.class, "Temple");
        registerStructure(StructureOceanMonumentStartMonument.class, "Monument");
        StructureMineshaftPieces.registerStructurePieces();
        StructureVillagePieces.registerVillagePieces();
        StructureNetherBridgePieces.registerNetherFortressPieces();
        StructureStrongholdPieces.registerStrongholdPieces();
        ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
        StructureOceanMonumentPieces.registerOceanMonumentPieces();
    }

    public static StructureStart getStructureStart(CompoundTag compoundTag, World world) {
        int j = StructureComponent.f();
        StructureStart structureStart = null;
        int i = j;

        try {
            Class oclass =
                    (Class)
                            net.minecraft.world.gen.structure.MapGenStructureIO.startNameToClassMap.get(
                                    compoundTag.getString("id"));
            if (oclass != null) {
                structureStart = (StructureStart) oclass.newInstance();
            }
        } catch (Exception exception) {
            net.minecraft.world.gen.structure.MapGenStructureIO.logger.warn(
                    "Failed Start with id " + compoundTag.getString("id"));
            exception.printStackTrace();
        }

        if (structureStart != null) {
            structureStart.readStructureComponentsFromNBT(world, compoundTag);
            if (i == 0) {
                return structureStart;
            }
        }

        net.minecraft.world.gen.structure.MapGenStructureIO.logger.warn(
                "Skipping Structure with id " + compoundTag.getString("id"));
        return structureStart;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static String getStructureStartName(StructureStart structureStart) {
        return (String) net.minecraft.world.gen.structure.MapGenStructureIO.startClassToNameMap.get(structureStart.getClass());
    }

    public static StructureComponent getStructureComponent(
            CompoundTag compoundTag, World world) {
        int j = StructureComponent.cR();
        StructureComponent structureComponent = null;
        int i = j;

        try {
            Class oclass =
                    (Class)
                            net.minecraft.world.gen.structure.MapGenStructureIO.componentNameToClassMap.get(
                                    compoundTag.getString("id"));
            if (oclass != null) {
                structureComponent = (StructureComponent) oclass.newInstance();
            }
        } catch (Exception exception) {
            net.minecraft.world.gen.structure.MapGenStructureIO.logger.warn(
                    "Failed Piece with id " + compoundTag.getString("id"));
            exception.printStackTrace();
        }

        if (structureComponent != null) {
            structureComponent.readStructureBaseNBT(world, compoundTag);
            if (i != 0) {
                return structureComponent;
            }
        }

        net.minecraft.world.gen.structure.MapGenStructureIO.logger.warn(
                "Skipping Piece with id " + compoundTag.getString("id"));
        return structureComponent;
    }

    static void registerStructureComponent(Class oclass, String text) {
        net.minecraft.world.gen.structure.MapGenStructureIO.componentNameToClassMap.put(text, oclass);
        net.minecraft.world.gen.structure.MapGenStructureIO.componentClassToNameMap.put(oclass, text);
    }
}
