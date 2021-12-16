package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public class ShoppingCart {

    private static final BigDecimal DISCOUNT_PERCENTAGE = 0.5;

    private final ClientId clientId;
    private final Instant creationDate;
    private final Instant updateDate;
    private final Map<Product> products;
    private final Money total;

    public ShoppingCart(ClientId clientId, Instant creationDate, Instant updateDate, Map<Product> products, Money total){
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

    public static int getClasificationCount(Map<Product> products, String clasification){
        return 0;
    }


}
