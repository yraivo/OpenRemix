package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.Random;

public class StructureOceanMonumentPiecesFitSimpleRoomTopHelper
        implements StructureOceanMonumentPiecesMonumentRoomFitHelper {
    public StructureOceanMonumentPiecesFitSimpleRoomTopHelper(
            OceanMonumentAccessMarker structureOceanMonumentPiecesSwitchEnumFacing) {
        this();
    }

    public StructureOceanMonumentPiecesPiece method_4a728e1e54c66005ef26(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            Random random) {
        structureOceanMonumentPiecesRoomDefinition.d = true;
        return new StructureOceanMonumentPiecesSimpleTopRoom(
                facing, structureOceanMonumentPiecesRoomDefinition, random);
    }

    public boolean method_9f371031dfea5fef8e92(
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        return !structureOceanMonumentPiecesRoomDefinition.aCM[Facing.WEST.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.aCM[Facing.EAST.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.aCM[Facing.NORTH.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.aCM[Facing.SOUTH.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.aCM[Facing.UP.getIndex()];
    }

    private StructureOceanMonumentPiecesFitSimpleRoomTopHelper() {}
}
