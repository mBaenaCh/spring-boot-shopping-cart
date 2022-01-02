package com.mateo.ShoppingCart.Marketplace.configuration.jackson.codecs;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mateo.ShoppingCart.Marketplace.domain.Badge;
import com.mateo.ShoppingCart.Marketplace.domain.Money;

import java.io.IOException;
import java.math.BigDecimal;

public class MoneyParser {

    public static class Serializer extends JsonSerializer<Money>{

        @Override
        public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            BigDecimal price = value.getValue();
            gen.writeNumber(price);
        }
    }

    public static class Deserializer extends JsonDeserializer<Money>{

        @Override
        public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            BigDecimal price = p.getDecimalValue();
            return new Money(price, Badge.USD);
        }
    }
}
