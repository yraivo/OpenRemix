package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.Random;

public class StructureOceanMonumentPiecesYDoubleRoomFitHelper
        implements StructureOceanMonumentPiecesMonumentRoomFitHelper {
    public StructureOceanMonumentPiecesYDoubleRoomFitHelper(
            OceanMonumentAccessMarker structureOceanMonumentPiecesSwitchEnumFacing) {
        this();
    }

    public boolean method_9f371031dfea5fef8e92(
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        return structureOceanMonumentPiecesRoomDefinition.aCM[Facing.UP.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()].d;
    }

    public StructureOceanMonumentPiecesPiece method_4a728e1e54c66005ef26(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            Random random) {
        structureOceanMonumentPiecesRoomDefinition.d = true;
        structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()].d = true;
        return new StructureOceanMonumentPiecesDoubleYRoom(
                facing, structureOceanMonumentPiecesRoomDefinition, random);
    }

    private StructureOceanMonumentPiecesYDoubleRoomFitHelper() {}
}
