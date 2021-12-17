package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private final UUID productId;
    private final String name;
    private final String description;
    private final Money price;
    private final String clasification;

    public Product(UUID productId, String name, String description, Money price){
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
