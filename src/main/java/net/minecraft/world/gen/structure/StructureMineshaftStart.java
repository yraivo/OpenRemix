package net.minecraft.world.gen.structure;

import net.minecraft.world.World;

import java.util.Random;

public class StructureMineshaftStart extends StructureStart {
    public StructureMineshaftStart() {}

    public StructureMineshaftStart(World world, Random random, int i, int j) {
        super(i, j);
        StructureMineshaftPiecesRoom structureMineshaftPiecesRoom =
                new StructureMineshaftPiecesRoom(0, random, (i << 4) + 2, (j << 4) + 2);
        this.components.add(structureMineshaftPiecesRoom);
        structureMineshaftPiecesRoom.buildComponent(
                structureMineshaftPiecesRoom, this.components, random);
        this.cj();
        this.markAvailableHeight(world, random, 10);
    }
}
