package com.mateo.ShoppingCart.Marketplace.domain;

import java.util.Objects;

public class ProductQuantity {

    private final Integer value;

    public ProductQuantity(Integer value){

        //Cantidad del producto no puede ser nulo
        Objects.requireNonNull(value, "Product quantity can not be null");

        //Validacion del rango de valores que puede tomar la cantidad del producto
        if(value <= 0 || value > 50){
            throw new IllegalArgumentException("Product quantity can not be negative or greagter than 50");
        }
        this.value = value;
    }
    //Para incrementar el valor de la cantidad del producto
    public ProductQuantity increment(){
        return new ProductQuantity(value + 1);
    }

    //Para retornar el valor de esta clase
    public Integer productQuantityAsInteger(){
        return this.value;
    }

}
