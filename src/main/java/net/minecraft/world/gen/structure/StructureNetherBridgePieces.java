package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePieces {

    private static StructureNetherBridgePiecesPieceWeight[] secondaryComponents;
    private static StructureNetherBridgePiecesPieceWeight[] primaryComponents;

    static StructureNetherBridgePiece method_190167b43866a26537fc(
            StructureNetherBridgePiecesPieceWeight structureNetherBridgePiecesPieceWeight,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        return method_d7de46912afc7242355e(
                structureNetherBridgePiecesPieceWeight, list, random, i, j, k, facing, l);
    }

    static StructureNetherBridgePiecesPieceWeight[] getSecondaryComponents() {
        return net.minecraft.world.gen.structure.StructureNetherBridgePieces.secondaryComponents;
    }

    static {
        net.minecraft.world.gen.structure.StructureNetherBridgePieces.primaryComponents =
                new StructureNetherBridgePiecesPieceWeight[] {
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesStraight.class, 30, 0, true),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCrossing3.class, 10, 4),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCrossing.class, 10, 4),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesStairs.class, 10, 3),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesThrone.class, 5, 2),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesEntrance.class, 5, 1)
                };
        net.minecraft.world.gen.structure.StructureNetherBridgePieces.secondaryComponents =
                new StructureNetherBridgePiecesPieceWeight[] {
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCorridor5.class, 25, 0, true),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCrossing2.class, 15, 5),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCorridor2.class, 5, 10),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCorridor.class, 5, 10),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCorridor3.class, 10, 3, true),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesCorridor4.class, 7, 2),
                    new StructureNetherBridgePiecesPieceWeight(StructureNetherBridgePiecesNetherStalkRoom.class, 5, 2)
                };
    }

    private static StructureNetherBridgePiece method_d7de46912afc7242355e(
            StructureNetherBridgePiecesPieceWeight structureNetherBridgePiecesPieceWeight,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        Class oclass = structureNetherBridgePiecesPieceWeight.weightClass;
        Object object = null;
        if (oclass == StructureNetherBridgePiecesStraight.class) {
            object =
                    StructureNetherBridgePiecesStraight.method_920d8a91c126ebd5c921(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCrossing3.class) {
            object =
                    StructureNetherBridgePiecesCrossing3.method_da3abdc77193aa845142(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCrossing.class) {
            object =
                    StructureNetherBridgePiecesCrossing.method_0bad2ce1d9bb56db10f8(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesStairs.class) {
            object =
                    StructureNetherBridgePiecesStairs.method_77a90abf570eb3af8f31(
                            list, random, i, j, k, l, facing);
        } else if (oclass == StructureNetherBridgePiecesThrone.class) {
            object =
                    StructureNetherBridgePiecesThrone.method_731cbdded6c8783c2709(
                            list, random, i, j, k, l, facing);
        } else if (oclass == StructureNetherBridgePiecesEntrance.class) {
            object =
                    StructureNetherBridgePiecesEntrance.method_4da62529495b1c9d1b5d(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCorridor5.class) {
            object =
                    StructureNetherBridgePiecesCorridor5.method_0e922694bc8dc0e8d129(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCorridor2.class) {
            object =
                    StructureNetherBridgePiecesCorridor2.method_8389d6414c4f277a518e(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCorridor.class) {
            object =
                    StructureNetherBridgePiecesCorridor.method_1e968fb12ff85b942a1e(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCorridor3.class) {
            object =
                    StructureNetherBridgePiecesCorridor3.method_9cee7f452da891fac37b(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCorridor4.class) {
            object =
                    StructureNetherBridgePiecesCorridor4.method_129520813f4a131fcbc1(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesCrossing2.class) {
            object =
                    StructureNetherBridgePiecesCrossing2.method_012be32adf94e371b2ff(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureNetherBridgePiecesNetherStalkRoom.class) {
            object =
                    StructureNetherBridgePiecesNetherStalkRoom.method_2b0b0c528d1655a58b28(
                            list, random, i, j, k, facing, l);
        }

        return (StructureNetherBridgePiece) object;
    }

    public static void registerNetherFortressPieces() {
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCrossing3.class, "NeBCr");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesEnd.class, "NeBEF");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesStraight.class, "NeBS");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCorridor3.class, "NeCCS");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCorridor4.class, "NeCTB");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesEntrance.class, "NeCE");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCrossing2.class, "NeSCSC");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCorridor.class, "NeSCLT");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCorridor5.class, "NeSC");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCorridor2.class, "NeSCRT");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesNetherStalkRoom.class, "NeCSR");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesThrone.class, "NeMT");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesCrossing.class, "NeRC");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesStairs.class, "NeSR");
        MapGenStructureIO.registerStructureComponent(StructureNetherBridgePiecesStart.class, "NeStart");
    }

    static StructureNetherBridgePiecesPieceWeight[] getPrimaryComponents() {
        return net.minecraft.world.gen.structure.StructureNetherBridgePieces.primaryComponents;
    }
}
