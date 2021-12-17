package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.util.Objects;
import java.util.UUID;

/*
*  - value: UUID -> 180 bits -> 36 characters
* */
public record ClientId(UUID value) {
    public ClientId{
        Objects.requireNonNull(value);
    }
    /* Metodo estatico para generar un UUID random*/
    public static ClientId generateUUID(){
        return new ClientId(UUID.randomUUID());
    }

    /* Metodo estatico para generar un UUID en funcion de un String*/
    public static ClientId generateUUIDFromString(String value){
        return new ClientId(UUID.fromString(value));
    }
}
