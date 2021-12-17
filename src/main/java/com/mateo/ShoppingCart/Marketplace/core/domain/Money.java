package com.mateo.ShoppingCart.Marketplace.core.domain;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal value;
    private final Badge badge;

    public Money(BigDecimal value, Badge badge){
        this.value = value;
        this.badge = badge;
    }

    public BigDecimal getValue() {
        return value;
    }
}
