package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    UUID id = UUID.randomUUID();
    ClientId clientId = new ClientId(id);
    Instant createdAt = Instant.now();
    Instant updatedAt = Instant.now();

    ProductId pId = new ProductId(UUID.randomUUID());
    ProductName name = new ProductName("Test product name");
    ProductDescription description = new ProductDescription("Test product description");
    Badge usd = Badge.USD;
    Money price = new Money(new BigDecimal(24), usd);
    ProductQuantity quantity = new ProductQuantity(1);

    ProductId pId2 = new ProductId(UUID.randomUUID());
    ProductName name2 = new ProductName("Test product name two");
    ProductDescription description2 = new ProductDescription("Test product description two");
    Money price2 = new Money(new BigDecimal(101), usd);
    ProductQuantity quantity2 = new ProductQuantity(1);

    ProductId pId3 = new ProductId(UUID.randomUUID());
    ProductName name3 = new ProductName("Test product name three");
    ProductDescription description3 = new ProductDescription("Test product description three");
    Money price3 = new Money(new BigDecimal(201), usd);
    ProductQuantity quantity3 = new ProductQuantity(1);

    ProductId pId4 = new ProductId(UUID.randomUUID());
    ProductName name4 = new ProductName("Test product name three");
    ProductDescription description4 = new ProductDescription("Test product description three");
    Money price4 = new Money(new BigDecimal(101), usd);
    ProductQuantity quantity4 = new ProductQuantity(1);

    Product p1 = new Product(pId, name, description, price, quantity);
    Product p2 = new Product(pId2, name2, description2, price2, quantity2);
    Product p3 = new Product(pId3, name3, description3, price3, quantity3);
    Product p4 = new Product(pId4, name4, description4, price4, quantity4);

    Map<UUID,Product> products = new HashMap<>();

    @Test
    public void shouldReturnTheCalculatedTotalCostOfAShoppingCart(){
        //Arrange
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Act
        BigDecimal calculatedValue = shoppingCart.getTotal().getValue();

        //Assert
        assertEquals(new BigDecimal(125), calculatedValue);
    }

    @Test
    public void shouldReturnTheAmountOfProductsOfAGivenClasification(){
        //Arrange
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        products.put(p3.getProductId().value(), p3);
        products.put(p4.getProductId().value(), p4);

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);
        //Act
        Integer amountOfExpensive = shoppingCart.getAmountOfProductsPerClasification("Expensive", products);
        Integer amountOfRegular = shoppingCart.getAmountOfProductsPerClasification("Regular", products);
        Integer amountOfCheap = shoppingCart.getAmountOfProductsPerClasification("Cheap", products);

        //Assert
        assertEquals(1,amountOfExpensive);
        assertEquals(2,amountOfRegular);
        assertEquals(1,amountOfCheap);
    }
}