package com.mateo.ShoppingCart.Marketplace.configuration.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs.ProductDescriptionParser;
import com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs.ProductIdParser;
import com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs.ProductNameParser;
import com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs.ProductQuantityParser;
import com.mateo.ShoppingCart.Marketplace.domain.ProductDescription;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;
import com.mateo.ShoppingCart.Marketplace.domain.ProductName;
import com.mateo.ShoppingCart.Marketplace.domain.ProductQuantity;

public class InternalModule extends SimpleModule {
    private static final String NAME = "InternalModule";

    public InternalModule(){
        super(NAME, Version.unknownVersion());

        addSerializer(ProductId.class, new ProductIdParser.Serializer());
        addDeserializer(ProductId.class, new ProductIdParser.Deserializer());

        addSerializer(ProductName.class, new ProductNameParser.Serializer());
        addDeserializer(ProductName.class, new ProductNameParser.Deserializer());

        addSerializer(ProductDescription.class, new ProductDescriptionParser.Serializer());
        addDeserializer(ProductDescription.class, new ProductDescriptionParser.Deserializer());

        addSerializer(ProductQuantity.class, new ProductQuantityParser.Serializer());
        addDeserializer(ProductQuantity.class, new ProductQuantityParser.Deserializer());
    }
}
