package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;

public enum WorldSettingsGameType {
    NOT_SET(initializeConstantState(), 0, -1, constantTextFragment(2)),
    SURVIVAL("SURVIVAL", 1, 0, "survival"),
    CREATIVE("CREATIVE", 2, 1, "creative"),
    ADVENTURE("ADVENTURE", 3, 2, "adventure"),
    SPECTATOR("SPECTATOR", 4, 3, "spectator");
    private static WorldSettingsGameType[] aid;

    private static WorldSettingsGameType[] aic;

    String name;
    private String d;
    int id;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public String fg() {
        return this.d;
    }

    public int getID() {
        return this.id;
    }

    public static WorldSettingsGameType getByID(int i) {
        WorldSettingsGameType[] worldSettingsGameTypeArray = values();
        int j = worldSettingsGameTypeArray.length;
        World.getLegacyWorldFlowMarker();

        int k = 0;

        while (k < j) {
            WorldSettingsGameType worldSettingsGameType = worldSettingsGameTypeArray[k];
            if (worldSettingsGameType.id == i) {
                return worldSettingsGameType;
            }

            k++;
        }

        return net.minecraft.world.WorldSettingsGameType.SURVIVAL;
    }

    // region String decoding support

    // endregion

    public boolean isSurvivalOrAdventure() {
        return this == net.minecraft.world.WorldSettingsGameType.SURVIVAL
                || this == net.minecraft.world.WorldSettingsGameType.ADVENTURE;
    }

    public boolean isAdventure() {
        return this == net.minecraft.world.WorldSettingsGameType.ADVENTURE
                || this == net.minecraft.world.WorldSettingsGameType.SPECTATOR;
    }

    public String getName() {
        return this.name;
    }

    WorldSettingsGameType(String text, int k, int j, String textValue) {
        this.d = text;
        this.id = j;
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    public void configurePlayerCapabilities(PlayerCapabilities playerCapabilities) {
        {
            World.getLegacyWorldFlowMarker();

            if (this == net.minecraft.world.WorldSettingsGameType.CREATIVE) {
                playerCapabilities.allowFlying = true;
                playerCapabilities.isCreativeMode = true;
                playerCapabilities.disableDamage = true;

            } else {

                if (this == net.minecraft.world.WorldSettingsGameType.SPECTATOR) {
                    playerCapabilities.allowFlying = true;
                    playerCapabilities.isCreativeMode = false;
                    playerCapabilities.disableDamage = true;
                    playerCapabilities.isFlying = true;

                } else {

                    playerCapabilities.allowFlying = false;
                    playerCapabilities.isCreativeMode = false;
                    playerCapabilities.disableDamage = false;
                    playerCapabilities.isFlying = false;
                }
            }
        }

        playerCapabilities.allowEdit =
                remix.compat.compiler.JvmBooleanSlots.fromInt((!this.isAdventure() ? 1 : 0));
    }

    public boolean isCreative() {
        return this == net.minecraft.world.WorldSettingsGameType.CREATIVE;
    }

    static {
        aic =
                new WorldSettingsGameType[] {
                    net.minecraft.world.WorldSettingsGameType.NOT_SET,
                    net.minecraft.world.WorldSettingsGameType.SURVIVAL,
                    net.minecraft.world.WorldSettingsGameType.CREATIVE,
                    net.minecraft.world.WorldSettingsGameType.ADVENTURE,
                    net.minecraft.world.WorldSettingsGameType.SPECTATOR
                };
        aid =
                new WorldSettingsGameType[] {
                    net.minecraft.world.WorldSettingsGameType.NOT_SET,
                    net.minecraft.world.WorldSettingsGameType.SURVIVAL,
                    net.minecraft.world.WorldSettingsGameType.CREATIVE,
                    net.minecraft.world.WorldSettingsGameType.ADVENTURE,
                    net.minecraft.world.WorldSettingsGameType.SPECTATOR
                };
    }

    public static WorldSettingsGameType getByName(String text) {
        World.getLegacyWorldFlowMarker();

        WorldSettingsGameType[] worldSettingsGameTypeArray = values();
        int length = worldSettingsGameTypeArray.length;
        int j = 0;

        while (j < length) {
            WorldSettingsGameType worldSettingsGameType = worldSettingsGameTypeArray[j];
            if (worldSettingsGameType.name.equals(text)) {
                return worldSettingsGameType;
            }

            j++;
        }

        return net.minecraft.world.WorldSettingsGameType.SURVIVAL;
    }

    private static WorldSettingsGameType[] __recovery_values171() {
        return aic.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.world.WorldSettingsGameType.packedTextFragments = new String[3];
        net.minecraft.world.WorldSettingsGameType.packedTextFragments[2] = new String("");
        return "NOT_SET";
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
