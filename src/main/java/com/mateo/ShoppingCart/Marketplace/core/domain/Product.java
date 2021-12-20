package com.mateo.ShoppingCart.Marketplace.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Product {

    private final ProductId productId; //Identificador unico de un objeto
    private final String name;
    private final String description;
    private final Money price; //Valor monetario basado en
    private final String clasification;
    private final Integer quantity;

    public Product(ProductId productId, String name, String description, Money price, Integer quantity){
        Objects.requireNonNull(productId, "The Product id must not be empty");

        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.clasification = setProductClasification(price);
        this.quantity = quantity;
    }

    public String setProductClasification(Money price){
        String clasification = "";
        if(this.price.getValue().compareTo(new BigDecimal(50)) < 0) {
            clasification = "Cheap";
        } else if (new BigDecimal(50).compareTo(this.price.getValue()) < 0 && this.price.getValue().compareTo(new BigDecimal(199)) < 0){
            clasification = "Regular";
        } else if(this.price.getValue().compareTo(new BigDecimal(199)) > 0){
            clasification = "Expensive";
        }
        return clasification;
    }

}
