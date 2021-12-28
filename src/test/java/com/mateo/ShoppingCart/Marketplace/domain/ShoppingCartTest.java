package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    public void shouldReturnTheCalculatedTotalCostOfAShoppingCart(){
        //Arrange
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

        Product p1 = new Product(pId, name, description, price, quantity);

        ProductId pId2 = new ProductId(UUID.randomUUID());
        ProductName name2 = new ProductName("Test product name two");
        ProductDescription description2 = new ProductDescription("Test product description two");
        Money price2 = new Money(new BigDecimal(101), usd);
        ProductQuantity quantity2 = new ProductQuantity(1);

        Product p2 = new Product(pId2, name2, description2, price2, quantity2);

        Map<UUID,Product> products = new HashMap<>();

        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Act
        BigDecimal calculatedValue = shoppingCart.getTotal().getValue();

        //Assert
        assertEquals(new BigDecimal(125), calculatedValue);
    }
}