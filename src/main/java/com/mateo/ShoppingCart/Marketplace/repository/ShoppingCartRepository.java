package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.*;

public interface ShoppingCartRepository {

    void addProductToShoppingCart(ProductId productId, ClientId shoppingCartId);

    void removeProductFromShoppingCart(ProductId productId);

    void updateProductQuantity(ProductId productId, Money price, ProductQuantity productQuantity);

}
