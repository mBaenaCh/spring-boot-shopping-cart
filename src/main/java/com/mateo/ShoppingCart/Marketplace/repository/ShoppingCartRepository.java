package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.*;

import java.util.List;

public interface ShoppingCartRepository {
    List<Product> getAllProducts();

    Product getProductById(ProductId id);

    ShoppingCart getShoppingCartById(ClientId id);

    void createShoppingCart(ShoppingCart shoppingCart);

    void createProduct(Product product);

    void updateProductById(ProductId id, Product product);

    void deleteProductById(ProductId id);

    void addProductToShoppingCart(ProductId productId, ClientId shoppingCartId);

    void removeProductFromShoppingCart(ProductId productId);

    void updateProductQuantity(ProductId productId, Money price, ProductQuantity productQuantity);

}