package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

public enum MediaType {
    ATOM_FEEDS(initializeConstantState(), "atom+xml"),
    ECMASCRIPT(MediaTypeCategory.APPLICATION, "ecmascript"),
    EDI_X12(MediaTypeCategory.APPLICATION, "EDI-X12"),
    EDIFACT(MediaTypeCategory.APPLICATION, "EDIFACT"),
    JSON(MediaTypeCategory.APPLICATION, "json"),
    JAVASCRIPT(MediaTypeCategory.APPLICATION, "javascript"),
    OCTET_STREAM(MediaTypeCategory.APPLICATION, "octet-stream"),
    OGG_APPLICATION(MediaTypeCategory.APPLICATION, "ogg"),
    PDF(MediaTypeCategory.APPLICATION, "pdf"),
    POSTSCRIPT(MediaTypeCategory.APPLICATION, "postscript"),
    RDF(MediaTypeCategory.APPLICATION, "rdf+xml"),
    RSS(MediaTypeCategory.APPLICATION, "rss+xml"),
    SOAP(MediaTypeCategory.APPLICATION, "soap+xml"),
    FONT_WOFF(MediaTypeCategory.APPLICATION, "font-woff"),
    XHTML(MediaTypeCategory.APPLICATION, "xhtml+xml"),
    XML_APPLICATION(MediaTypeCategory.APPLICATION, "xml"),
    DTD(MediaTypeCategory.APPLICATION, "xml-dtd"),
    XOP(MediaTypeCategory.APPLICATION, "xop+xml"),
    ZIP(MediaTypeCategory.APPLICATION, "zip"),
    GZIP(MediaTypeCategory.APPLICATION, "gzip"),
    APPLICATION_EXAMPLE(MediaTypeCategory.APPLICATION, "example"),
    NACL(MediaTypeCategory.APPLICATION, "x-nacl"),
    URL_ENCODED(MediaTypeCategory.APPLICATION, "x-www-form-urlencoded"),
    BASIC_AUDIO(MediaTypeCategory.AUDIO, "basic"),
    L24(MediaTypeCategory.AUDIO, "L24"),
    MP4_AUDIO(MediaTypeCategory.AUDIO, "mp4"),
    MPEG_AUDIO(MediaTypeCategory.AUDIO, "mpeg"),
    OGG_AUDIO(MediaTypeCategory.AUDIO, "ogg"),
    OPUS(MediaTypeCategory.AUDIO, "opus"),
    VORBIS(MediaTypeCategory.AUDIO, "vorbis"),
    REALAUDIO(MediaTypeCategory.AUDIO, "vnd.rn-realaudio"),
    WAV(MediaTypeCategory.AUDIO, "vnd.wave"),
    WEBM_AUDIO(MediaTypeCategory.AUDIO, "webm"),
    AUDIO_EXAMPLE(MediaTypeCategory.AUDIO, "example"),
    EXAMPLE(MediaTypeCategory.EXAMPLE, constantTextFragment(7)),
    GIF(MediaTypeCategory.IMAGE, "gif"),
    JPEG(MediaTypeCategory.IMAGE, "jpeg"),
    PJPEG(MediaTypeCategory.IMAGE, "pjpeg"),
    X_JPS(MediaTypeCategory.IMAGE, "x-jps"),
    PNG(MediaTypeCategory.IMAGE, "png"),
    X_QUICKTIME(MediaTypeCategory.IMAGE, "x-quicktime"),
    SVG(MediaTypeCategory.IMAGE, "svg+xml"),
    IMAGE_EXAMPLE(MediaTypeCategory.IMAGE, "example"),
    HTTP(MediaTypeCategory.MESSAGE, "http"),
    IMDN(MediaTypeCategory.MESSAGE, "imdn+xml"),
    PARTIAL(MediaTypeCategory.MESSAGE, "partial"),
    EML(MediaTypeCategory.MESSAGE, "rfc882"),
    MESSAGE_EXAMPLE(MediaTypeCategory.MESSAGE, "example"),
    IGES(MediaTypeCategory.MODEL, "iges"),
    IGS(MediaTypeCategory.MODEL, "iges"),
    MESH(MediaTypeCategory.MODEL, "mesh"),
    VRML(MediaTypeCategory.MODEL, "vrml"),
    X3D(MediaTypeCategory.MODEL, "x3d"),
    X3D_BINARY(MediaTypeCategory.MODEL, "x3d+binary"),
    X3D_FAST_INFOSET(MediaTypeCategory.MODEL, "x3d+fastinfoset"),
    X3D_VRML(MediaTypeCategory.MODEL, "x3d-vrml"),
    X3D_XML(MediaTypeCategory.MODEL, "x3d+xml"),
    MODEL_EXAMPLE(MediaTypeCategory.MODEL, "example"),
    MIXED(MediaTypeCategory.MULTIPART, "mixed"),
    ALTERNATIVE(MediaTypeCategory.MULTIPART, "alternative"),
    RELATED(MediaTypeCategory.MULTIPART, "related"),
    FORM_DATA(MediaTypeCategory.MULTIPART, "form-data"),
    SIGNED(MediaTypeCategory.MULTIPART, "signed"),
    X_ZIP(MediaTypeCategory.MULTIPART, "x-zip"),
    ENCRYPTED(MediaTypeCategory.MULTIPART, "encrypted"),
    MULTIPART_EXAMPLE(MediaTypeCategory.MULTIPART, "example"),
    COMMAND(MediaTypeCategory.TEXT, "cmd"),
    CSS(MediaTypeCategory.TEXT, "css"),
    CSV(MediaTypeCategory.TEXT, "csv"),
    PLAIN(MediaTypeCategory.TEXT, "plain"),
    X_C(MediaTypeCategory.TEXT, "x-c"),
    TEXT_EXAMPLE(MediaTypeCategory.TEXT, "example"),
    X_SCRIPT_PHYTON(MediaTypeCategory.TEXT, "x-script.phyton"),
    HTML(MediaTypeCategory.TEXT, "html"),
    JAVASCRIPT_DEPRECATED(MediaTypeCategory.TEXT, "javascript"),
    PLAIN_TEXT(MediaTypeCategory.TEXT, "plain"),
    RTF(MediaTypeCategory.TEXT, "rtf"),
    VCARD(MediaTypeCategory.TEXT, "vcard"),
    ABC_MUSIC(MediaTypeCategory.TEXT, "vnd.abc"),
    XML_TEXT(MediaTypeCategory.TEXT, "xml"),
    AVI(MediaTypeCategory.VIDEO, "avi"),
    VIDEO_EXAMPLE(MediaTypeCategory.VIDEO, "example"),
    MPEG_1_VIDEO(MediaTypeCategory.VIDEO, "mpeg"),
    MPEG_VIDEO(MediaTypeCategory.VIDEO, "mpeg"),
    MP4_VIDEO(MediaTypeCategory.VIDEO, "mp4"),
    OGG_VIDEO(MediaTypeCategory.VIDEO, "ogg"),
    QUICKTIME(MediaTypeCategory.VIDEO, "quicktime"),
    WEBM_VIDEO(MediaTypeCategory.VIDEO, "webm"),
    MATROSKA(MediaTypeCategory.VIDEO, "x-matroska"),
    MSVIDEO(MediaTypeCategory.VIDEO, "msvideo"),
    X_MSVIDEO(MediaTypeCategory.VIDEO, "video/x-msvideo"),
    AVS(MediaTypeCategory.VIDEO, "video/avs-video"),
    WMV(MediaTypeCategory.VIDEO, "x-ms-wmv"),
    FLV(MediaTypeCategory.VIDEO, "x-flv");
    private static MediaType[] bQT;

    private String subtype;
    private static String a;
    private static String[] packedTextFragments;
    private MediaTypeCategory category;
    private String mimeType;

    MediaType(MediaTypeCategory bREValue, String text) {
        this.subtype = text;
        this.category = bREValue;
        this.mimeType =
                this.getCategory()
                        .getMimePrefix()
                        .concat(!text.isEmpty() ? __enum_read_bQO()[0] : __enum_read_bQO()[1])
                        .concat(text);
    }

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.getMimeType();
    }

    public MediaTypeCategory getCategory() {
        return this.category;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    // region String decoding support

    // endregion

    public static MediaType fromMimeType(String text) {
        HttpRequestOptions.p();

        String textValue = text.replace(packedTextFragments[2], packedTextFragments[3]);

        if (text.contains(packedTextFragments[4])) {
            textValue = text.substring(text.indexOf(58));
        }

        for (MediaType mediaType : values()) {
            if (mediaType.getMimeType().trim().equalsIgnoreCase(text.trim())
                    || mediaType.getMimeType().trim().equalsIgnoreCase(textValue.trim())) {
                return mediaType;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            HttpRequestOptions.b(new Entity[1]);
        }

        return null;
    }

    static {
        bQT =
                new MediaType[] {
                    remix.compat.legacy.http.MediaType.ATOM_FEEDS,
                    remix.compat.legacy.http.MediaType.ECMASCRIPT,
                    remix.compat.legacy.http.MediaType.EDI_X12,
                    remix.compat.legacy.http.MediaType.EDIFACT,
                    remix.compat.legacy.http.MediaType.JSON,
                    remix.compat.legacy.http.MediaType.JAVASCRIPT,
                    remix.compat.legacy.http.MediaType.OCTET_STREAM,
                    remix.compat.legacy.http.MediaType.OGG_APPLICATION,
                    remix.compat.legacy.http.MediaType.PDF,
                    remix.compat.legacy.http.MediaType.POSTSCRIPT,
                    remix.compat.legacy.http.MediaType.RDF,
                    remix.compat.legacy.http.MediaType.RSS,
                    remix.compat.legacy.http.MediaType.SOAP,
                    remix.compat.legacy.http.MediaType.FONT_WOFF,
                    remix.compat.legacy.http.MediaType.XHTML,
                    remix.compat.legacy.http.MediaType.XML_APPLICATION,
                    remix.compat.legacy.http.MediaType.DTD,
                    remix.compat.legacy.http.MediaType.XOP,
                    remix.compat.legacy.http.MediaType.ZIP,
                    remix.compat.legacy.http.MediaType.GZIP,
                    remix.compat.legacy.http.MediaType.APPLICATION_EXAMPLE,
                    remix.compat.legacy.http.MediaType.NACL,
                    remix.compat.legacy.http.MediaType.URL_ENCODED,
                    remix.compat.legacy.http.MediaType.BASIC_AUDIO,
                    remix.compat.legacy.http.MediaType.L24,
                    remix.compat.legacy.http.MediaType.MP4_AUDIO,
                    remix.compat.legacy.http.MediaType.MPEG_AUDIO,
                    remix.compat.legacy.http.MediaType.OGG_AUDIO,
                    remix.compat.legacy.http.MediaType.OPUS,
                    remix.compat.legacy.http.MediaType.VORBIS,
                    remix.compat.legacy.http.MediaType.REALAUDIO,
                    remix.compat.legacy.http.MediaType.WAV,
                    remix.compat.legacy.http.MediaType.WEBM_AUDIO,
                    remix.compat.legacy.http.MediaType.AUDIO_EXAMPLE,
                    remix.compat.legacy.http.MediaType.EXAMPLE,
                    remix.compat.legacy.http.MediaType.GIF,
                    remix.compat.legacy.http.MediaType.JPEG,
                    remix.compat.legacy.http.MediaType.PJPEG,
                    remix.compat.legacy.http.MediaType.X_JPS,
                    remix.compat.legacy.http.MediaType.PNG,
                    remix.compat.legacy.http.MediaType.X_QUICKTIME,
                    remix.compat.legacy.http.MediaType.SVG,
                    remix.compat.legacy.http.MediaType.IMAGE_EXAMPLE,
                    remix.compat.legacy.http.MediaType.HTTP,
                    remix.compat.legacy.http.MediaType.IMDN,
                    remix.compat.legacy.http.MediaType.PARTIAL,
                    remix.compat.legacy.http.MediaType.EML,
                    remix.compat.legacy.http.MediaType.MESSAGE_EXAMPLE,
                    remix.compat.legacy.http.MediaType.IGES,
                    remix.compat.legacy.http.MediaType.IGS,
                    remix.compat.legacy.http.MediaType.MESH,
                    remix.compat.legacy.http.MediaType.VRML,
                    remix.compat.legacy.http.MediaType.X3D,
                    remix.compat.legacy.http.MediaType.X3D_BINARY,
                    remix.compat.legacy.http.MediaType.X3D_FAST_INFOSET,
                    remix.compat.legacy.http.MediaType.X3D_VRML,
                    remix.compat.legacy.http.MediaType.X3D_XML,
                    remix.compat.legacy.http.MediaType.MODEL_EXAMPLE,
                    remix.compat.legacy.http.MediaType.MIXED,
                    remix.compat.legacy.http.MediaType.ALTERNATIVE,
                    remix.compat.legacy.http.MediaType.RELATED,
                    remix.compat.legacy.http.MediaType.FORM_DATA,
                    remix.compat.legacy.http.MediaType.SIGNED,
                    remix.compat.legacy.http.MediaType.X_ZIP,
                    remix.compat.legacy.http.MediaType.ENCRYPTED,
                    remix.compat.legacy.http.MediaType.MULTIPART_EXAMPLE,
                    remix.compat.legacy.http.MediaType.COMMAND,
                    remix.compat.legacy.http.MediaType.CSS,
                    remix.compat.legacy.http.MediaType.CSV,
                    remix.compat.legacy.http.MediaType.PLAIN,
                    remix.compat.legacy.http.MediaType.X_C,
                    remix.compat.legacy.http.MediaType.TEXT_EXAMPLE,
                    remix.compat.legacy.http.MediaType.X_SCRIPT_PHYTON,
                    remix.compat.legacy.http.MediaType.HTML,
                    remix.compat.legacy.http.MediaType.JAVASCRIPT_DEPRECATED,
                    remix.compat.legacy.http.MediaType.PLAIN_TEXT,
                    remix.compat.legacy.http.MediaType.RTF,
                    remix.compat.legacy.http.MediaType.VCARD,
                    remix.compat.legacy.http.MediaType.ABC_MUSIC,
                    remix.compat.legacy.http.MediaType.XML_TEXT,
                    remix.compat.legacy.http.MediaType.AVI,
                    remix.compat.legacy.http.MediaType.VIDEO_EXAMPLE,
                    remix.compat.legacy.http.MediaType.MPEG_1_VIDEO,
                    remix.compat.legacy.http.MediaType.MPEG_VIDEO,
                    remix.compat.legacy.http.MediaType.MP4_VIDEO,
                    remix.compat.legacy.http.MediaType.OGG_VIDEO,
                    remix.compat.legacy.http.MediaType.QUICKTIME,
                    remix.compat.legacy.http.MediaType.WEBM_VIDEO,
                    remix.compat.legacy.http.MediaType.MATROSKA,
                    remix.compat.legacy.http.MediaType.MSVIDEO,
                    remix.compat.legacy.http.MediaType.X_MSVIDEO,
                    remix.compat.legacy.http.MediaType.AVS,
                    remix.compat.legacy.http.MediaType.WMV,
                    remix.compat.legacy.http.MediaType.FLV
                };
    }

    // region String decoding support

    // endregion

    public String getSubtype() {
        return this.subtype;
    }

    // region String decoding support

    // endregion

    private static MediaType[] __recovery_values171() {
        return bQT.clone();
    }

    private static java.lang.String[] __enum_read_bQO() {
        return packedTextFragments;
    }

    // Initialize shared state before the first constant is published.
    private static MediaTypeCategory initializeConstantState() {
        remix.compat.legacy.http.MediaType.packedTextFragments = new String[8];
        remix.compat.legacy.http.MediaType.packedTextFragments[0] = new String("/");
        remix.compat.legacy.http.MediaType.packedTextFragments[1] = new String("");
        remix.compat.legacy.http.MediaType.packedTextFragments[2] = new String(";");
        remix.compat.legacy.http.MediaType.packedTextFragments[3] = new String("");
        remix.compat.legacy.http.MediaType.packedTextFragments[4] = new String(":");
        remix.compat.legacy.http.MediaType.packedTextFragments[5] = new String("/");
        remix.compat.legacy.http.MediaType.packedTextFragments[6] = new String("uxU\u0003hxC");
        remix.compat.legacy.http.MediaType.packedTextFragments[7] = new String("");
        a = MediaType.packedTextFragments[5];
        return MediaTypeCategory.APPLICATION;
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
