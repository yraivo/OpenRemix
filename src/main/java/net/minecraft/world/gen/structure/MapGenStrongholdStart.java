package net.minecraft.world.gen.structure;

import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MapGenStrongholdStart extends StructureStart {
    public MapGenStrongholdStart() {}

    public MapGenStrongholdStart(World world, Random random, int i, int j) {
        super(i, j);
        StructureStrongholdPieces.prepareStructurePieces();
        int i1 = StructureComponent.cR();
        StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2 =
                new StructureStrongholdPiecesStairs2(0, random, (i << 4) + 2, (j << 4) + 2);
        int k = i1;
        this.components.add(structureStrongholdPiecesStairs2);
        structureStrongholdPiecesStairs2.buildComponent(
                structureStrongholdPiecesStairs2, this.components, random);
        List list = structureStrongholdPiecesStairs2.iy;

        while (!list.isEmpty()) {
            int l = random.nextInt(list.size());
            StructureComponent structureComponent = (StructureComponent) list.remove(l);
            structureComponent.buildComponent(
                    structureStrongholdPiecesStairs2, this.components, random);
            if (k == 0) {
                break;
            }
        }

        this.cj();
        this.markAvailableHeight(world, random, 10);
    }
}
