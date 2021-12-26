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

    @Test
    public void shouldReturnExceptionWhenTheValueIsEmpty(){
        //Arrange
        String value = "";

        //Act
        Executable executable = () -> new ProductName(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void shouldReturnExceptionWhenTheValueHasMoreThan150Characters(){
        //Arrange
        String value = "Nam quis nulla. Integer malesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, tempus molestie, porttitor ut, iaculis quis,";

        //Act
        Executable executable = () -> new ProductName(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void shouldReturnExceptionWhenTheValueHasSpecialCharacters(){
        //Arrange
        String value = "Nam quis nulla. Integer @lesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, tempus molestie, porttitor ut, iaculis quis,";

        //Act
        Executable executable = () -> new ProductName(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }


}