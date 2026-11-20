package net.minecraft.client.renderer;

import net.minecraft.util.Facing;

public enum BlockModelRendererEnumNeighborInfo {
    DOWN(
            "DOWN",
            0,
            "DOWN",
            0,
            new Facing[] {Facing.WEST, Facing.EAST, Facing.NORTH, Facing.SOUTH},
            0.5F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.SOUTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.SOUTH
            }),
    UP(
            "UP",
            1,
            "UP",
            1,
            new Facing[] {Facing.EAST, Facing.WEST, Facing.NORTH, Facing.SOUTH},
            1.0F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.SOUTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.SOUTH
            }),
    NORTH(
            "NORTH",
            2,
            "NORTH",
            2,
            new Facing[] {Facing.UP, Facing.DOWN, Facing.EAST, Facing.WEST},
            0.8F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_WEST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_EAST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_EAST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_WEST
            }),
    SOUTH(
            "SOUTH",
            3,
            "SOUTH",
            3,
            new Facing[] {Facing.WEST, Facing.EAST, Facing.DOWN, Facing.UP},
            0.8F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.WEST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_WEST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.WEST,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.WEST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.EAST
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_EAST,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.EAST,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.EAST
            }),
    WEST(
            "WEST",
            4,
            "WEST",
            4,
            new Facing[] {Facing.UP, Facing.DOWN, Facing.NORTH, Facing.SOUTH},
            0.6F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.SOUTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.SOUTH
            }),
    EAST(
            "EAST",
            5,
            "EAST",
            5,
            new Facing[] {Facing.DOWN, Facing.UP, Facing.NORTH, Facing.SOUTH},
            0.6F,
            true,
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.SOUTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.FLIP_DOWN,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.DOWN,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.NORTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_NORTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.NORTH
            },
            new BlockModelRendererOrientation[] {
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.SOUTH,
                BlockModelRendererOrientation.FLIP_UP,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.FLIP_SOUTH,
                BlockModelRendererOrientation.UP,
                BlockModelRendererOrientation.SOUTH
            });
    private static BlockModelRendererEnumNeighborInfo[] aPf;

    private static BlockModelRendererEnumNeighborInfo[] aPc;

    protected boolean e;
    protected BlockModelRendererOrientation[] aPg;

    protected BlockModelRendererOrientation[] aPk;
    protected float ic;
    protected BlockModelRendererOrientation[] aPn;
    protected BlockModelRendererOrientation[] aPo;
    protected Facing[] aPp;

    public static BlockModelRendererEnumNeighborInfo getNeighbourInfo(Facing facing) {
        return aPc[facing.getIndex()];
    }

    BlockModelRendererEnumNeighborInfo(
            String text,
            int j,
            String textValue,
            int k,
            Facing[] aPpValue,
            float f,
            boolean flag,
            BlockModelRendererOrientation[] aPoValue,
            BlockModelRendererOrientation[] aPnValue,
            BlockModelRendererOrientation[] aPkValue,
            BlockModelRendererOrientation[] aPgValue) {
        this.aPp = aPpValue;
        this.ic = f;
        this.e = flag;
        this.aPo = aPoValue;
        this.aPn = aPnValue;
        this.aPk = aPkValue;
        this.aPg = aPgValue;
    }

    // region String decoding support

    // endregion

    static {
        aPc = new BlockModelRendererEnumNeighborInfo[6];
        aPf = new BlockModelRendererEnumNeighborInfo[] {net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.DOWN, net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.UP, net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.NORTH, net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.SOUTH, net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.WEST, net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.EAST};
        aPc[Facing.DOWN.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.DOWN;
        aPc[Facing.UP.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.UP;
        aPc[Facing.NORTH.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.NORTH;
        aPc[Facing.SOUTH.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.SOUTH;
        aPc[Facing.WEST.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.WEST;
        aPc[Facing.EAST.getIndex()] = net.minecraft.client.renderer.BlockModelRendererEnumNeighborInfo.EAST;
    }

    // region String decoding support

    // endregion

}
