package net.minecraft.world.gen.structure;

import net.minecraft.util.BlockPos;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesStairs2 extends StructureStrongholdPiecesStairs {
    public List iy = Lists.newArrayList();
    public StructureStrongholdPiecesPieceWeight strongholdPieceWeight;
    public StructureStrongholdPiecesPortalRoom strongholdPortalRoom;

    public StructureStrongholdPiecesStairs2(int p171_0, Random random, int p171_2, int p171_3) {
        super(0, random, p171_2, p171_3);
    }

    public StructureStrongholdPiecesStairs2() {}

    public BlockPos getBoundingBoxCenter() {
        return this.strongholdPortalRoom != null
                ? this.strongholdPortalRoom.getBoundingBoxCenter()
                : super.getBoundingBoxCenter();
    }
}
