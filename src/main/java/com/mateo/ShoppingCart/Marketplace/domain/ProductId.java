package com.mateo.ShoppingCart.Marketplace.domain;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId{
        Objects.requireNonNull(value, "Product id must not be null");
    }

    public static ProductId generateUUID(){
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId generateUUIDFromString(String value){
        return new ProductId(UUID.fromString(value));
    }

    @Override
    public String toString(){
        return this.value.toString();
    }
}