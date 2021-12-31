package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;
import com.mateo.ShoppingCart.Marketplace.domain.ProductQuantity;
import com.mateo.ShoppingCart.Marketplace.domain.ShoppingCart;

public interface ShoppingCartRepository {
    void createShoppingCart(ShoppingCart shoppingCart);

    void addProductToShoppingCart(Product product);

    void deleteProductFromShoppingCart(ProductId id);

    void increaseQuantity();

    void decreaseQuantity();
}
