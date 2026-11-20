package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class LanguageManager implements IResourceManagerReloadListener {

    private Map languageMap = Maps.newHashMap();

    private static Logger logger;
    private String currentLanguage;
    protected static Locale currentLocale;
    private IMetadataSerializer theMetadataSerializer;

    public boolean isCurrentLocaleUnicode() {
        return net.minecraft.client.resources.LanguageManager.currentLocale.isUnicode();
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public void parseLanguageMetadata(List list) {
        String text = I18n.getLegacyResourceFlowMarker();
        this.languageMap.clear();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_21885 = (list).iterator();
                (__recoveryIterator171_21885).hasNext(); ) {
            IResourcePack iResourcePack = (IResourcePack) (__recoveryIterator171_21885).next();
            try {
                LanguageMetadataSection packMetadata =
                        (LanguageMetadataSection)
                                iResourcePack.getPackMetadata(
                                        this.theMetadataSerializer, "language");
                if (packMetadata != null) {
                    for (java.util.Iterator<?> __recoveryIterator171_22253 =
                                    (packMetadata.getLanguages()).iterator();
                            (__recoveryIterator171_22253).hasNext(); ) {
                        Language language = (Language) (__recoveryIterator171_22253).next();
                        if (!this.languageMap.containsKey(language.getLanguageCode())) {
                            this.languageMap.put(language.getLanguageCode(), language);
                        }

                        if (textValue == null) {
                            break;
                        }
                    }
                }
            } catch (RuntimeException runtimeexception) {
                net.minecraft.client.resources.LanguageManager.logger.warn(
                        "Unable to parse metadata section of resourcepack: "
                                + iResourcePack.getPackName(),
                        runtimeexception);
            } catch (IOException ioexception) {
                net.minecraft.client.resources.LanguageManager.logger.warn(
                        "Unable to parse metadata section of resourcepack: "
                                + iResourcePack.getPackName(),
                        ioexception);
            }

            if (textValue == null) {
                break;
            }
        }
    }

    public Language getCurrentLanguage() {
        return this.languageMap.containsKey(this.currentLanguage)
                ? (Language) this.languageMap.get(this.currentLanguage)
                : (Language) this.languageMap.get("en_US");
    }

    public void setCurrentLanguage(Language language) {
        this.currentLanguage = language.getLanguageCode();
    }

    static {
        net.minecraft.client.resources.LanguageManager.logger = LogManager.getLogger();
        net.minecraft.client.resources.LanguageManager.currentLocale = new Locale();
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        ArrayList arrayList = Lists.newArrayList(new String[] {"en_US"});
        if (!"en_US".equals(this.currentLanguage)) {
            arrayList.add(this.currentLanguage);
        }

        net.minecraft.client.resources.LanguageManager.currentLocale.loadLocaleDataFiles(
                iResourceManager, arrayList);
        StringTranslate.replaceWith(net.minecraft.client.resources.LanguageManager.currentLocale.properties);
    }

    public boolean isCurrentLanguageBidirectional() {
        return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
    }

    public LanguageManager(IMetadataSerializer aKdValue, String text) {
        this.theMetadataSerializer = aKdValue;
        this.currentLanguage = text;
        I18n.setLocale(net.minecraft.client.resources.LanguageManager.currentLocale);
    }

    public SortedSet getLanguages() {
        return Sets.newTreeSet(this.languageMap.values());
    }
}
