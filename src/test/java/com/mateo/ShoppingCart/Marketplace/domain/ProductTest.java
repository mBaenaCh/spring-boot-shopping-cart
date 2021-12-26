package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    public void shouldReturnNullPointerExceptionWhenTheProductIdIsNull(){
        //Arrange
        ProductId id = null;
        ProductName name = new ProductName("Test product name");
        ProductDescription description = new ProductDescription("Test product description");
        Badge usd = Badge.USD;
        Money price = new Money(new BigDecimal(49), usd);
        ProductQuantity quantity = new ProductQuantity(1);

        //Act
        Executable executable = () -> new Product(id, name, description, price, quantity);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheProductNameIsNull(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        ProductName name = null;
        ProductDescription description = new ProductDescription("Test product description");
        Badge usd = Badge.USD;
        Money price = new Money(new BigDecimal(49), usd);
        ProductQuantity quantity = new ProductQuantity(1);

        //Act
        Executable executable = () -> new Product(id, name, description, price, quantity);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheProductDescriptionIsNull(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        ProductName name = new ProductName("Test product name");
        ProductDescription description = null;
        Badge usd = Badge.USD;
        Money price = new Money(new BigDecimal(49), usd);
        ProductQuantity quantity = new ProductQuantity(1);

        //Act
        Executable executable = () -> new Product(id, name, description, price, quantity);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenThePriceIsNull(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        ProductName name = new ProductName("Test product name");
        ProductDescription description = new ProductDescription("Test product description");
        Money price = null;
        ProductQuantity quantity = new ProductQuantity(1);

        //Act
        Executable executable = () -> new Product(id, name, description, price, quantity);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheProductQuantityIsNull(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        ProductName name = new ProductName("Test product name");
        ProductDescription description = new ProductDescription("Test product description");
        Badge usd = Badge.USD;
        Money price = new Money(new BigDecimal(49), usd);
        ProductQuantity quantity = null;

        //Act
        Executable executable = () -> new Product(id, name, description, price, quantity);

        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnCheapWhenTheGivenPriceIsBelow50(){
        //Arrange
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        ProductName name = new ProductName("Test product name");
        ProductDescription description = new ProductDescription("Test product description");
        Badge usd = Badge.USD;
        Money price = new Money(new BigDecimal(49), usd);
        ProductQuantity quantity = new ProductQuantity(1);

        //Act
        Product p1 = new Product(id, name, description, price, quantity);

        //Assert
        assertEquals("Cheap", p1.getClasification());
    }

    @Test
    public void shouldReturnRegularWhenTheGivenPriceIsAbove50(){
        //Arrange
            UUID id = UUID.randomUUID();
            ProductId prodId = new ProductId(id);
            ProductName name = new ProductName("Product name test");
            ProductDescription description = new ProductDescription("Product description test");
            Badge usd = Badge.USD;
            Money price = new Money(new BigDecimal(51), usd);
            ProductQuantity quantity = new ProductQuantity(1);
        //Act
            Product p2 = new Product(prodId, name, description, price, quantity);
        //Assert
            assertEquals("Regular", p2.getClasification());

    }


}