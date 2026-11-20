package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.util.Session;

import java.net.Proxy;

public class GameConfigurationUserInformation {
    public Proxy proxy;
    public PropertyMap userProperties;
    public Session session;

    public GameConfigurationUserInformation(
            Session aPaValue, PropertyMap propertymap, Proxy proxy) {
        this.session = aPaValue;
        this.userProperties = propertymap;
        this.proxy = proxy;
    }
}
