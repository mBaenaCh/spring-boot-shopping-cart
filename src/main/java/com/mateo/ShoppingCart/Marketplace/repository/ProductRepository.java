package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;

import java.util.List;

public interface ProductRepository {
    List<Product> getAllProducts();

    Product getProductById(ProductId id);

    void createProduct(Product product);

    void updateProductById(ProductId id, Product product);

    void deleteProductById(ProductId id);

}