package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {

    @Test
    public void shouldReturnNullPointerExceptionWhenValueIsNull(){
        //Arrange
        String value = null;

        //Act
        Executable executable = () -> new ProductName(value);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

}