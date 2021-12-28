package com.mateo.ShoppingCart.Marketplace.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Getter
public class ShoppingCart {

    private static final BigDecimal DISCOUNT_PERCENTAGE = BigDecimal.valueOf(0.5);

    private final ClientId clientId;

    /* Usamos Instant para obtener una estampa de tiempo calculada por Java y no
       por el sistema (A diferencia de LocalDate)  */
    private final Instant createdAt;
    private final Instant updatedAt;

    /* Definimos una estructura de datos basada en los identificadores de objetos y el objeto asociado al identificador*/
    private final Map<UUID,Product> products;

    /* Definimos una clase para gestionar los valores monetarios en funcion de la cantidad y la divisa*/
    private final Money total;

    public ShoppingCart(ClientId clientId, Instant createdAt, Instant updatedAt, Map<UUID, Product> products){
        Objects.requireNonNull(clientId, "The Client Id must not be null");
        Objects.requireNonNull(createdAt, "The creation date must not be null");
        Objects.requireNonNull(updatedAt, "The update date must not be null");
        Objects.requireNonNull(products, "The products list must not be null");

        Boolean isValid = validateProductsList(products);
        if(!isValid){
            throw new IllegalArgumentException("There can not be more than 1 Expensive product or more than 10 Normal products");
        }

        this.clientId = clientId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.products = products;
        this.total = calculateTotalPrice(products);
    }

    /*Funcionalidades del Shopping cart*/

    public Money calculateTotalPrice(Map<UUID, Product> products){

        Money totalValue;
        BigDecimal sum = new BigDecimal(0);
        sum = products.values(). //Obtenemos los objetos del mapa
                stream().
                map(Product::getPrice). //Extraemos los objetos money
                map(Money::getValue).   //Extraemos los valores de los objetos money
                reduce(BigDecimal.ZERO, (a,b) -> a.add(b));

        totalValue = new Money(sum, Badge.USD);
        return totalValue;
    }

    public static BigDecimal productDiscountCalculator(BigDecimal price, BigDecimal discount){
            BigDecimal discountCalculation = price.multiply(discount);
            BigDecimal appliedDiscount = price.subtract(discountCalculation);
        return appliedDiscount;
    }

    public Integer getAmountOfProductsPerClasification(String clasification, Map<UUID, Product> products){

        Long amount = products.entrySet(). //Definimos los elementos a recorrer en el Map
                stream().                  // Recorremos los elementos
                filter(e -> e.getValue().getClasification().equals(clasification)). //Filtramos los elementos que correspondan a la clasificacion ingresada
                map(Map.Entry::getValue).count(); //Contamos los elementos que satisfacen el filtro

        return amount.intValue();
    }

    public Boolean validateProductsList(Map<UUID, Product> products) {
        Integer amountOfExpensive = getAmountOfProductsPerClasification("Expensive", products);
        Integer amountOfNormal = getAmountOfProductsPerClasification("Normal", products);

        if (amountOfExpensive > 1 || amountOfNormal > 10) {
            return false;
        } else {
            return true;
        }
    }
}
