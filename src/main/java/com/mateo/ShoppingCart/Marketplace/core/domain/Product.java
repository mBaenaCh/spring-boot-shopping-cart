package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.util.UUID;

public class Product {
    private final UUID productId;
    private final String name;
    private final String description;
    private final Money price;
    private final String clasification;

    public Product(UUID productId, String name, String description, Money price, String clasification){
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.clasification = clasification;
    }

}
