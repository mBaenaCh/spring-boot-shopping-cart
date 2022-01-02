package com.mateo.ShoppingCart.Marketplace.model;

import com.mateo.ShoppingCart.Marketplace.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCartOutput {
    private ShoppingCart shoppingCart;
}
