package com.mateo.ShoppingCart.Marketplace.services;

/* La capa de servicio es aquella dedicada a manejar la logica del negocio
 *  de tal modo que podamos gestionar las transacciones necesarias para el
 *  repository*/

import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;
import com.mateo.ShoppingCart.Marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices {
    private ProductRepository repository;

    public ProductServices(ProductRepository repository){
        this.repository = repository;
    }

    public List<Product> getAllProducts(){
        return repository.getAllProducts();
    }

    public Product createProduct(Product product){
        repository.createProduct(product);
        return product;
    }

    public Product getProductById(ProductId id){
        return repository.getProductById(id);
    }

    public Product updateProductById(ProductId id, Product product){
        repository.updateProductById(id, product);

        return product;
    }
}
