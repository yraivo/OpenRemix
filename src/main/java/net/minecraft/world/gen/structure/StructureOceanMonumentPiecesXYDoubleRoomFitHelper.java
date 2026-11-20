package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.Random;

public class StructureOceanMonumentPiecesXYDoubleRoomFitHelper
        implements StructureOceanMonumentPiecesMonumentRoomFitHelper {
    public StructureOceanMonumentPiecesXYDoubleRoomFitHelper(
            OceanMonumentAccessMarker structureOceanMonumentPiecesSwitchEnumFacing) {
        this();
    }

    public boolean method_9f371031dfea5fef8e92(
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.EAST.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.bMV[Facing.EAST.getIndex()].d
                && structureOceanMonumentPiecesRoomDefinition.aCM[Facing.UP.getIndex()]
                && !structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()].d) {
            StructureOceanMonumentPiecesRoomDefinition
                    structureOceanMonumentPiecesRoomDefinitionValue =
                            structureOceanMonumentPiecesRoomDefinition.bMV[Facing.EAST.getIndex()];
            return structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.UP.getIndex()]
                    && !structureOceanMonumentPiecesRoomDefinitionValue.bMV[Facing.UP.getIndex()].d;
        } else {
            return false;
        }
    }

    private StructureOceanMonumentPiecesXYDoubleRoomFitHelper() {}

    public StructureOceanMonumentPiecesPiece method_4a728e1e54c66005ef26(
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            Random random) {
        structureOceanMonumentPiecesRoomDefinition.d = true;
        structureOceanMonumentPiecesRoomDefinition.bMV[Facing.EAST.getIndex()].d = true;
        structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()].d = true;
        structureOceanMonumentPiecesRoomDefinition
                        .bMV[Facing.EAST.getIndex()]
                        .bMV[Facing.UP.getIndex()]
                        .d =
                true;
        return new StructureOceanMonumentPiecesDoubleXYRoom(
                facing, structureOceanMonumentPiecesRoomDefinition, random);
    }
}
