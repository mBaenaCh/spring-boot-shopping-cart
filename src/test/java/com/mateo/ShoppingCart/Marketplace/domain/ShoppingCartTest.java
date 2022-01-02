package com.mateo.ShoppingCart.Marketplace.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    ProductName name4 = new ProductName("Test product name four");
    ProductDescription description4 = new ProductDescription("Test product description four");
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
        shoppingCart.addProduct(p3);
        shoppingCart.addProduct(p4);
        //Act
        BigDecimal calculatedValue = shoppingCart.calculateTotalPrice(shoppingCart.getProducts()).getValue();
        shoppingCart.setTotal(new Money(calculatedValue, Badge.USD));
        //Assert
        assertEquals(new BigDecimal(427), shoppingCart.getTotal().getValue());
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

    @Test
    public void shouldReturnIllegalArgumentExceptionWhenTheListOfProductsHasMoreThan1ExpensiveProduct(){
        //Arrange
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        products.put(p3.getProductId().value(), p3);
        products.put(p4.getProductId().value(), p4);

        ProductId pId5 = new ProductId(UUID.randomUUID());
        ProductName name5 = new ProductName("Test product name five");
        ProductDescription description5 = new ProductDescription("Test product description five");
        Money price5 = new Money(new BigDecimal(201), usd);
        ProductQuantity quantity5 = new ProductQuantity(1);
        Product p5 = new Product(pId5, name5, description5, price5, quantity5);

        products.put(p5.getProductId().value(), p5);


        //Act
        Executable executable = () -> new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheClientIdIsNull(){
        //Arrange
        ClientId clientId1 = null;

        //Act
        Executable executable = () -> new ShoppingCart(clientId1, createdAt, updatedAt, products);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheCreatedAtDateIsNull(){
        //Arrange
        Instant createdAt1 = null;

        //Act
        Executable executable = () -> new ShoppingCart(clientId, createdAt1, updatedAt, products);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheUpdatedAtDateIsNull(){
        //Arrange
        Instant updatedAt1 = null;

        //Act
        Executable executable = () -> new ShoppingCart(clientId, createdAt, updatedAt1, products);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnNullPointerExceptionWhenTheMapOfProductsIsNull(){
        //Arrange
        Map<UUID, Product> products1 = null;

        //Act
        Executable executable = () -> new ShoppingCart(clientId, createdAt, updatedAt, products1);
        //Assert
        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void shouldReturnTheCalculatedDiscountAppliedToAPrice(){
        //Arrange
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        products.put(p3.getProductId().value(), p3);

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);
        //Act
        shoppingCart.setTotal(shoppingCart.calculateTotalPrice(products));
        BigDecimal newTotal = shoppingCart.productDiscountCalculator(shoppingCart.getTotal().getValue(), new BigDecimal(0.10));

        //Assert
        //Pendiente por consultar aproximacion en numeros BigDecimal, dado que la operacion de la funcion da distinta a la esperada
        assertEquals(new BigDecimal(293.4).setScale(2, RoundingMode.HALF_EVEN), newTotal.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void shouldReturnTheAmountOfRepeatedProducts(){
        //Arrange
        p1.getQuantity().incrementValue();
        p1.getQuantity().incrementValue();
        p2.getQuantity().incrementValue();
        p2.getQuantity().incrementValue();
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Act
        Integer amountOfRepeated = shoppingCart.getAmountOfRepeatedProducts(products);

        //Assert
        assertEquals(2, amountOfRepeated);
    }

    @Test
    public void shouldReturnADiscountPercentageWithAGivenAmountOfRepeatedProducts(){
        //Arrange
        p1.getQuantity().incrementValue();
        p1.getQuantity().incrementValue();
        //p2.getQuantity().incrementValue();
        p2.getQuantity().incrementValue();
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Act
        Integer amountOfRepeated = shoppingCart.getAmountOfRepeatedProducts(products);
        BigDecimal discountPercentage = shoppingCart.getDiscountPercentage( amountOfRepeated);

        //Assert
        assertEquals(new BigDecimal(0.3), discountPercentage);
    }

    @Test
    public void shouldReduceTheTotalPriceWhenAProductIsDeletedFromTheList(){
        //Arrange
        products.put(p1.getProductId().value(), p1);
        products.put(p2.getProductId().value(), p2);
        products.put(p3.getProductId().value(), p3);

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);

        //Act
        shoppingCart.deleteProduct(p1.getProductId().value());
        BigDecimal total = shoppingCart.calculateTotalPrice(products).getValue();
        //Assert
        assertEquals(new BigDecimal(302), total);
    }

    @Test
    public void shouldAddAProductToTheShoppingCart(){
        //Arrange
        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);
        //Act
        shoppingCart.addProduct(p1);
        shoppingCart.addProduct(p2);
        //Assert
        assertEquals(2, shoppingCart.getProducts().size());
    }
}