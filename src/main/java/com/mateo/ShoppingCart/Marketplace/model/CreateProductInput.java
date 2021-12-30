package com.mateo.ShoppingCart.Marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data //Nos trae los metodos getter, setter, toString, etc.
public class CreateProductInput {
    private String productId;
    private String name;
    private String description;
    private Number price; //Teniendo en cuenta los valores numericos enteros y flotantes que podriamos recibir en el JSON
    private Integer quantity;
}
