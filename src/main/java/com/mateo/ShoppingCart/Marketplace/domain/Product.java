package com.mateo.ShoppingCart.Marketplace.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Product {

    private final ProductId productId; //Identificador unico de un objeto
    private final ProductName name;
    private final ProductDescription description;
    private Money price; //Valor monetario
    private final String clasification;
    private ProductQuantity quantity; //Deberia ser final? Yo digo que no, es una variable que deberia cambiar de estado para cada objeto

    public Product(ProductId productId, ProductName name, ProductDescription description, Money price, ProductQuantity quantity){
        Objects.requireNonNull(productId, "The Product id must not be null");
        Objects.requireNonNull(name, "The product name must not be null");
        Objects.requireNonNull(description, "The product description must not be null");
        Objects.requireNonNull(price, "The product price must not be null");
        Objects.requireNonNull(quantity, "The product quantity must not be null");

        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.clasification = setProductClasification(price);
        this.quantity = quantity;
    }

    /*POR MEJORAR: usar un enum o variables globales para nombrar los "tipos de clasificaciones"*/
    public String setProductClasification(Money price){
        String clasification = "";
        if(price.getValue().compareTo(new BigDecimal(50)) < 0) {
            clasification = "Cheap";
        } else if (price.getValue().compareTo(new BigDecimal(50)) > 0 && price.getValue().compareTo(new BigDecimal(199)) < 0){
            clasification = "Regular";
        } else if(price.getValue().compareTo(new BigDecimal(199)) > 0){
            clasification = "Expensive";
        }
        return clasification;
    }

    public void setPriceByQuantity(ProductQuantity quantity){
        BigDecimal newPrice = this.price.getValue().multiply(new BigDecimal(quantity.asInteger()));
        this.price = new Money(newPrice, Badge.USD);
    }
}