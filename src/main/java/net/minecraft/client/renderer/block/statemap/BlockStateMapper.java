package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class BlockStateMapper {
    private Map blockStateMap = Maps.newIdentityHashMap();
    private Set setBuiltInBlocks = Sets.newIdentityHashSet();

    public Map putAllStateModelLocations() {
        StateMapperBase.method_650532ba484532b3efb0();

        IdentityHashMap identityhashmap = Maps.newIdentityHashMap();

        for (java.util.Iterator<?> __recoveryIterator171_684 = (Block.blockRegistry).iterator();
                (__recoveryIterator171_684).hasNext(); ) {
            Block block = (Block) (__recoveryIterator171_684).next();
            if (!this.setBuiltInBlocks.contains(block)) {
                identityhashmap.putAll(
                        ((IStateMapper)
                                        com.google.common.base.MoreObjects.firstNonNull(
                                                this.blockStateMap.get(block),
                                                new DefaultStateMapper()))
                                .putStateModelLocations(block));
            }
        }

        return identityhashmap;
    }

    public void registerBuiltInBlocks(Block... blockArray) {
        Collections.addAll(this.setBuiltInBlocks, blockArray);
    }

    public void registerBlockStateMapper(Block block, IStateMapper iStateMapper) {
        this.blockStateMap.put(block, iStateMapper);
    }
}
