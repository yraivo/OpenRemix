package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.Random;

public class StructureOceanMonumentPiecesXDoubleRoomFitHelper
        implements StructureOceanMonumentPiecesMonumentRoomFitHelper {
    public StructureOceanMonumentPiecesPiece method_4a728e1e54c66005ef26(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            Random random) {
        structureOceanMonumentPiecesRoomDefinition.d = true;
        structureOceanMonumentPiecesRoomDefinition.bMV[Facing.EAST.getIndex()].d = true;
        return new StructureOceanMonumentPiecesDoubleXRoom(
                facing, structureOceanMonumentPiecesRoomDefinition, random);
    }

    public StructureOceanMonumentPiecesXDoubleRoomFitHelper(
            OceanMonumentAccessMarker structureOceanMonumentPiecesSwitchEnumFacing) {
        this();
    }

    private StructureOceanMonumentPiecesXDoubleRoomFitHelper() {}

    public boolean method_9f371031dfea5fef8e92(
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        return structureOceanMonumentPiecesRoomDefinition.aCM[Facing.EAST.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.bMV[Facing.EAST.getIndex()].d;
    }
}
