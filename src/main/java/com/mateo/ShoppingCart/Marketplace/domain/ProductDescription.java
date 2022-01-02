package com.mateo.ShoppingCart.Marketplace.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class ProductDescription {

    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s:]{0,250}$");
    private final String value;

    public ProductDescription (String value){
        Objects.requireNonNull(value, "Product description can not be null");

        String trimmedValue = value.trim();

        if(trimmedValue.length() <= 0 || trimmedValue.length() > 250){
            throw new IllegalArgumentException("Product description can not be empty or have more than  250 characters");
        }

        Boolean isValid = pattern.matcher(trimmedValue).matches();

        if(!isValid){
            throw new IllegalArgumentException("Product description can not have any special characters");
        }

        this.value = trimmedValue;
    }
    @Override
    public String toString(){
        return this.value;
    }

}
