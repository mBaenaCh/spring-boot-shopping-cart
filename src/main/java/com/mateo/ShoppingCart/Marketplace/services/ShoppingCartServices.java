package com.mateo.ShoppingCart.Marketplace.services;

import com.mateo.ShoppingCart.Marketplace.domain.ClientId;
import com.mateo.ShoppingCart.Marketplace.domain.Product;
import com.mateo.ShoppingCart.Marketplace.domain.ProductId;
import com.mateo.ShoppingCart.Marketplace.domain.ShoppingCart;
import com.mateo.ShoppingCart.Marketplace.repository.ShoppingCartRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class ShoppingCartServices {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServices(ShoppingCartRepository shoppingCartRepository){
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart addProductToShoppingCart(Product product, ShoppingCart shoppingCart){

        //Añadimos el producto nuevo a la lista de productos del shopping cart
        shoppingCart.addProduct(product);

        //Actualizamos la fecha de modificacion
        shoppingCart.setUpdatedAt(Instant.now());

        //Creamos la instancia actualizada
        ShoppingCart updatedShoppingCart = new ShoppingCart(shoppingCart.getClientId(),
                                                            shoppingCart.getCreatedAt(),
                                                            shoppingCart.getUpdatedAt(),
                                                            shoppingCart.getProducts()
                                                            );

        //Calculamos el nuevo valor total
        updatedShoppingCart.setTotal(shoppingCart.calculateTotalPrice(updatedShoppingCart.getProducts()));

        //Modificamos valores en la base de datos
        shoppingCartRepository.addProductToShoppingCart(product.getProductId(), shoppingCart.getClientId());

        return updatedShoppingCart;
    }
}
