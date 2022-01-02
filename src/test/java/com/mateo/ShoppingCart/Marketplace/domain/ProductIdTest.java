package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class ProductIdTest {

    @Test
    public void shouldReturnNullPointerWhenTheIdIsNull(){
        //Arrange
        UUID id = null;

        //Act
        Executable executable = () -> new ProductId(id);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }
}