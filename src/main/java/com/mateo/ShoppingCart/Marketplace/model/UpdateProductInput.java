package com.mateo.ShoppingCart.Marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProductInput {
    private String name;
    private String description;
    private Number price;
    private Integer quantity;
}
