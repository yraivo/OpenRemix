package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.Random;

public class StructureOceanMonumentPiecesZDoubleRoomFitHelper
        implements StructureOceanMonumentPiecesMonumentRoomFitHelper {
    public StructureOceanMonumentPiecesPiece method_4a728e1e54c66005ef26(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            Random random) {
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue =
                structureOceanMonumentPiecesRoomDefinition;
        if (!structureOceanMonumentPiecesRoomDefinition.aCM[Facing.NORTH.getIndex()]
                || structureOceanMonumentPiecesRoomDefinition.bMV[Facing.NORTH.getIndex()].d) {
            structureOceanMonumentPiecesRoomDefinitionValue =
                    structureOceanMonumentPiecesRoomDefinition.bMV[Facing.SOUTH.getIndex()];
        }

        structureOceanMonumentPiecesRoomDefinitionValue.d = true;
        structureOceanMonumentPiecesRoomDefinitionValue.bMV[Facing.NORTH.getIndex()].d = true;
        return new StructureOceanMonumentPiecesDoubleZRoom(
                facing, structureOceanMonumentPiecesRoomDefinitionValue, random);
    }

    public StructureOceanMonumentPiecesZDoubleRoomFitHelper(
            OceanMonumentAccessMarker structureOceanMonumentPiecesSwitchEnumFacing) {
        this();
    }

    private StructureOceanMonumentPiecesZDoubleRoomFitHelper() {}

    public boolean method_9f371031dfea5fef8e92(
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        return structureOceanMonumentPiecesRoomDefinition.aCM[Facing.NORTH.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.bMV[Facing.NORTH.getIndex()].d;
    }
}
