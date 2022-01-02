package com.mateo.ShoppingCart.Marketplace.configuration.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs.*;
import com.mateo.ShoppingCart.Marketplace.domain.*;

public class InternalModule extends SimpleModule {
    private static final String NAME = "InternalModule";

    public InternalModule(){
        super(NAME, Version.unknownVersion());

        addSerializer(ProductId.class, new ProductIdParser.Serializer());
        addDeserializer(ProductId.class, new ProductIdParser.Deserializer());

        addSerializer(ClientId.class, new ClientIdParser.Serializer());
        addDeserializer(ClientId.class, new ClientIdParser.Deserializer());

        addSerializer(ProductName.class, new ProductNameParser.Serializer());
        addDeserializer(ProductName.class, new ProductNameParser.Deserializer());

        addSerializer(ProductDescription.class, new ProductDescriptionParser.Serializer());
        addDeserializer(ProductDescription.class, new ProductDescriptionParser.Deserializer());

        addSerializer(ProductQuantity.class, new ProductQuantityParser.Serializer());
        addDeserializer(ProductQuantity.class, new ProductQuantityParser.Deserializer());

    }
}
