package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID productId; //Identificador unico de un objeto
    private final String name;
    private final String description;
    private final Money price; //Valor monetario basado en
    private String clasification;

    public Product(UUID productId, String name, String description, Money price){
        Objects.requireNonNull(productId, "The Product id must not be empty");

        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;

        if(this.price.getValue().compareTo(BigDecimal.valueOf(50.0)) < 50){
            this.clasification = "Cheap";
        } else if (50 < this.price.getValue() < 199){
            this.clasification = "Regular";
        } else if(this.price.getValue() > 199){
            this.clasification = "Expensive";
        }

    }

}
