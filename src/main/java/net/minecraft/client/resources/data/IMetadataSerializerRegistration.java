package net.minecraft.client.resources.data;

class IMetadataSerializerRegistration {
    IMetadataSectionSerializer aHB;
    IMetadataSerializer aFc;
    Class aHC;

    IMetadataSerializerRegistration(
            IMetadataSerializer iMetadataSerializer,
            IMetadataSectionSerializer iMetadataSectionSerializer,
            Class oclass,
            Object object) {
        this(iMetadataSerializer, iMetadataSectionSerializer, oclass);
    }

    private IMetadataSerializerRegistration(
            IMetadataSerializer aFcValue, IMetadataSectionSerializer aHBValue, Class oclass) {
        this.aFc = aFcValue;
        this.aHB = aHBValue;
        this.aHC = oclass;
    }
}
