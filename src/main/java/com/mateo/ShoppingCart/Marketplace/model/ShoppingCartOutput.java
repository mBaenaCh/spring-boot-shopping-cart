package com.mateo.ShoppingCart.Marketplace.model;

import com.mateo.ShoppingCart.Marketplace.domain.ClientId;
import com.mateo.ShoppingCart.Marketplace.domain.Money;
import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCartOutput {
    private ClientId clientId;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Product> products;
    private Money total;

    public void setProducts(Map<UUID, Product> productsMap){
        this.products = new ArrayList<Product>(productsMap.values());
    }
}
