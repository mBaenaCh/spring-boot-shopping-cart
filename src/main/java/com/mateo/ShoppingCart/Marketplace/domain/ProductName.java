package com.mateo.ShoppingCart.Marketplace.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProductName {

    private static final Pattern pattern = Pattern.compile("^[a-zA-Z\\s:]{0,150}$");
    private final String value;

    public ProductName(String value){
        Objects.requireNonNull(value, "Product name can not be null");

        String trimmedValue = value.trim();

        if(trimmedValue.length() <= 0 || trimmedValue.length() > 150){
            throw new IllegalArgumentException("Product name can not be empty or have more than 150 characters");
        }

        Boolean isValid = pattern.matcher(trimmedValue).matches();

        if(!isValid){
            throw new IllegalArgumentException("Product name can not have special characters or numbers");
        }

        this.value = trimmedValue;

    }
    @Override
    public String toString(){
        return this.value;
    }
}
