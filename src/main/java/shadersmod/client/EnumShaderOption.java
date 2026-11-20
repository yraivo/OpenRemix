package shadersmod.client;

public enum EnumShaderOption {
    dl(
            initializeConstantState(),
            0,
            "of.options.shaders.ANTIALIASING",
            "antialiasingLevel",
            constantTextFragment(1)),
    ds("NORMAL_MAP", 1, "of.options.shaders.NORMAL_MAP", "normalMapEnabled", "true"),
    dd("SPECULAR_MAP", 2, "of.options.shaders.SPECULAR_MAP", "specularMapEnabled", "true"),
    dq("RENDER_RES_MUL", 3, "of.options.shaders.RENDER_RES_MUL", "renderResMul", "1.0"),
    di("SHADOW_RES_MUL", 4, "of.options.shaders.SHADOW_RES_MUL", "shadowResMul", "1.0"),
    dn("HAND_DEPTH_MUL", 5, "of.options.shaders.HAND_DEPTH_MUL", "handDepthMul", "0.125"),
    db("CLOUD_SHADOW", 6, "of.options.shaders.CLOUD_SHADOW", "cloudShadow", "true"),
    dp("OLD_LIGHTING", 7, "of.options.shaders.OLD_LIGHTING", "oldLighting", "default"),
    SHADER_PACK(
            "SHADER_PACK",
            8,
            "of.options.shaders.SHADER_PACK",
            "shaderPack",
            constantTextFragment(2)),
    dm(
            "TWEAK_BLOCK_DAMAGE",
            9,
            "of.options.shaders.TWEAK_BLOCK_DAMAGE",
            "tweakBlockDamage",
            "false"),
    du(
            "SHADOW_CLIP_FRUSTRUM",
            10,
            "of.options.shaders.SHADOW_CLIP_FRUSTRUM",
            "shadowClipFrustrum",
            "true"),
    df(
            "TEX_MIN_FIL_B",
            11,
            "of.options.shaders.TEX_MIN_FIL_B",
            "TexMinFilB",
            constantTextFragment(3)),
    dv(
            "TEX_MIN_FIL_N",
            12,
            "of.options.shaders.TEX_MIN_FIL_N",
            "TexMinFilN",
            constantTextFragment(4)),
    dk(
            "TEX_MIN_FIL_S",
            13,
            "of.options.shaders.TEX_MIN_FIL_S",
            "TexMinFilS",
            constantTextFragment(5)),
    de(
            "TEX_MAG_FIL_B",
            14,
            "of.options.shaders.TEX_MAG_FIL_B",
            "TexMagFilB",
            constantTextFragment(6)),
    dh(
            "TEX_MAG_FIL_N",
            15,
            "of.options.shaders.TEX_MAG_FIL_N",
            "TexMagFilN",
            constantTextFragment(7)),
    dc(
            "TEX_MAG_FIL_S",
            16,
            "of.options.shaders.TEX_MAG_FIL_S",
            "TexMagFilS",
            constantTextFragment(8));
    private static EnumShaderOption[] dt;

    private static EnumShaderOption[] dj;

    private static String[] packedTextFragments;
    private String resourceKey = null;
    private String propertyKey = null;
    private String valueDefault = null;

    EnumShaderOption(String text, int j, String textValue, String textValue2, String textValue3) {
        this.resourceKey = textValue;
        this.propertyKey = textValue2;
        this.valueDefault = textValue3;
    }

    static {
        dt =
                new EnumShaderOption[] {
                    dl,
                    ds,
                    dd,
                    dq,
                    di,
                    dn,
                    db,
                    dp,
                    shadersmod.client.EnumShaderOption.SHADER_PACK,
                    dm,
                    du,
                    df,
                    dv,
                    dk,
                    de,
                    dh,
                    dc
                };
        dj =
                new EnumShaderOption[] {
                    dl,
                    ds,
                    dd,
                    dq,
                    di,
                    dn,
                    db,
                    dp,
                    shadersmod.client.EnumShaderOption.SHADER_PACK,
                    dm,
                    du,
                    df,
                    dv,
                    dk,
                    de,
                    dh,
                    dc
                };
    }

    // region String decoding support

    // endregion

    public String getPropertyKey() {
        return this.propertyKey;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    // region String decoding support

    // endregion

    public String getValueDefault() {
        return this.valueDefault;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static EnumShaderOption[] __recovery_values171() {
        return dt.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        shadersmod.client.EnumShaderOption.packedTextFragments = new String[9];
        shadersmod.client.EnumShaderOption.packedTextFragments[1] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[2] = new String("");
        shadersmod.client.EnumShaderOption.packedTextFragments[3] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[4] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[5] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[6] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[7] = new String("0");
        shadersmod.client.EnumShaderOption.packedTextFragments[8] = new String("0");
        return "ANTIALIASING";
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
