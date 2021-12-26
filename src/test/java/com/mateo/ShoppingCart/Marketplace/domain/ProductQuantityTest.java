package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ProductQuantityTest {

    @Test
    public void shouldReturnNullPointerExceptionWhenValueIsNull(){
        //Arrange
        Integer value = null;

        //Act
        Executable executable = () -> new ProductQuantity(value);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnExceptionWhenValueIsNegative(){
        //Arrange
        Integer value = -5;

        //Act
        Executable executable = () -> new ProductQuantity(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}