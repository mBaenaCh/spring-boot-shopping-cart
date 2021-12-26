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

    @Test
    public void shouldReturnExceptionWhenValueHasMoreThan250Characters(){
        //Arrange
        String value = "Nam quis nulla. Integer malesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, tempus molestie, porttitor ut, iaculis quis, sem. Phasellus rhoncus. Aenean id metus id velit ullamcorper pulvinar. Vestibulum fermentum tortor i";

        //Act
        Executable executable = () -> new ProductDescription(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void shouldReturnExceptionWhenTheValueHasSpecialCharacters(){
        //Arrange
        String value = "Nam quis nulla. Integer malesuada. &n in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, tempus molestie, porttitor ut, iaculis quis, sem. Phasellus rhoncus. Aenean id metus id velit ullamcorper ";

        //Act
        Executable executable = () -> new ProductDescription(value);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

}