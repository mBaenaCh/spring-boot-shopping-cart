package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ProductDescriptionTest {

    @Test
    public void shouldReturnNullPointerExceptionWhenValueIsNull(){
        //Arrange
        String value = null;

        //Act
        Executable executable = () -> new ProductDescription(value);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnExceptionWhenValueIsEmpty(){
        //Arrange
        String value = "";

        //Act
        Executable executable = () -> new ProductDescription(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}