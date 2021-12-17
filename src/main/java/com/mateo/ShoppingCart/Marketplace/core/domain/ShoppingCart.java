package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ShoppingCart {

    private static final BigDecimal DISCOUNT_PERCENTAGE = BigDecimal.valueOf(0.5);

    private final ClientId clientId;

    /* Usamos Instant para obtener una estampa de tiempo calculada por Java y no
       por el sistema (A diferencia de LocalDate)  */
    private final Instant creationDate;
    private final Instant updateDate;

    /* Definimos una estructura de datos basada en los identificadores de objetos y su valor objeto*/
    private final Map<UUID,Product> products;

    /* Definimos una clase para gestionar los valores monetarios en funcion de la cantidad y la divisa*/
    private final Money total;

    public ShoppingCart(ClientId clientId, Instant creationDate, Instant updateDate, Map<UUID, Product> products, Money total){
        Objects.requireNonNull(clientId, "The Client Id must not be and empty value");


        this.clientId = clientId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.products = products;
        this.total = total;
    }

    public static BigDecimal calculateTotalDiscount(BigDecimal price){
            BigDecimal discount = price.multiply(DISCOUNT_PERCENTAGE);
            BigDecimal calculatedDiscount = price.subtract(discount);
        return calculatedDiscount;
    }

    public static int getClasificationCount(Map<UUID,Product> products, String clasification){
        return 0;
    }


}
