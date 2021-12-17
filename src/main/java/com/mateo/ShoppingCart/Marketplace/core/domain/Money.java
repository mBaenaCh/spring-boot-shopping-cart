package com.mateo.ShoppingCart.Marketplace.core.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
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
