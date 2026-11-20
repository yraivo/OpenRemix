package net.minecraft.world.gen.structure;

import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MapGenNetherBridgeStart extends StructureStart {
    public MapGenNetherBridgeStart() {}

    public MapGenNetherBridgeStart(World world, Random random, int i, int j) {
        super(i, j);
        int i1 = StructureComponent.f();
        StructureNetherBridgePiecesStart structureNetherBridgePiecesStart =
                new StructureNetherBridgePiecesStart(random, (i << 4) + 2, (j << 4) + 2);
        this.components.add(structureNetherBridgePiecesStart);
        int k = i1;
        structureNetherBridgePiecesStart.buildComponent(
                structureNetherBridgePiecesStart, this.components, random);
        List list = structureNetherBridgePiecesStart.iy;

        while (!list.isEmpty()) {
            int l = random.nextInt(list.size());
            StructureComponent structureComponent = (StructureComponent) list.remove(l);
            structureComponent.buildComponent(structureNetherBridgePiecesStart, this.components, random);
            if (k != 0) {
                break;
            }
        }

        this.cj();
        this.setRandomHeight(world, random, 48, 70);
    }
}
