package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();

    Product getProductById(ProductId productId);

    void createProduct(Product product);

    void updateProductById(ProductId productId, Product product);

    void deleteProductById(ProductId productId);

}
