package net.minecraft.world.gen.structure;

public class StructureOceanMonumentPieces {


    public static void registerOceanMonumentPieces() {
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesMonumentBuilding.class, "OMB");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesMonumentCoreRoom.class, "OMCR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesDoubleXRoom.class, "OMDXR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesDoubleXYRoom.class, "OMDXYR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesDoubleYRoom.class, "OMDYR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesDoubleYZRoom.class, "OMDYZR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesDoubleZRoom.class, "OMDZR");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesEntryRoom.class, "OMEntry");
        MapGenStructureIO.registerStructureComponent(
                StructureOceanMonumentPiecesPenthouse.class, "OMPenthouse");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesSimpleRoom.class, "OMSimple");
        MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPiecesSimpleTopRoom.class, "OMSimpleT");
    }
}
