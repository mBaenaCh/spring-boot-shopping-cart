package com.mateo.ShoppingCart.Marketplace.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class Product {

    private final ProductId productId; //Identificador unico de un objeto
    private final ProductName name;
    private final ProductDescription description;
    private final Money price; //Valor monetario basado en
    private final String clasification;
    private final ProductQuantity quantity;

    public Product(ProductId productId, ProductName name, ProductDescription description, Money price, ProductQuantity quantity){
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