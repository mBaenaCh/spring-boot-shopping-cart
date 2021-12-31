package com.mateo.ShoppingCart.Marketplace.services;


import com.mateo.ShoppingCart.Marketplace.domain.*;
import com.mateo.ShoppingCart.Marketplace.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.time.Instant;

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

        //Modificamos el producto en el repositorio
        shoppingCartRepository.addProductToShoppingCart(product.getProductId(), shoppingCart.getClientId());

        return updatedShoppingCart;
    }

    public ShoppingCart removeProductFromTheShoppingCart(ShoppingCart shoppingCart, ProductId id){

        //Se elimina un producto del mapa de productos
        shoppingCart.getProducts().remove(id);

        //Creamos la instancia actualizada a retornar
        ShoppingCart updatedShoppingCart = new ShoppingCart(shoppingCart.getClientId(),
                                                            shoppingCart.getCreatedAt(),
                                                            shoppingCart.getCreatedAt(),
                                                            shoppingCart.getProducts());

        //Actualizamos el valor total del carrito
        updatedShoppingCart.setTotal(shoppingCart.calculateTotalPrice(updatedShoppingCart.getProducts()));

        //Actualizamos la fecha de modificacion
        updatedShoppingCart.setUpdatedAt(Instant.now());

        shoppingCartRepository.removeProductFromShoppingCart(id);

        return updatedShoppingCart;
    }

    public ShoppingCart increaseProductQuantity(ProductId id, ProductQuantity quantity, ShoppingCart shoppingCart){

        ShoppingCart updatedShoppingCart = new ShoppingCart(shoppingCart.getClientId(),
                                                            shoppingCart.getCreatedAt(),
                                                            shoppingCart.getUpdatedAt(),
                                                            shoppingCart.getProducts());

        Product foundProduct = updatedShoppingCart.getProducts().get(id);

        //Incrementamos la cantidad del producto de interes
        foundProduct.getQuantity().incrementValue();

        //Actualizamos el valor del producto en funcion de la cantidad
        foundProduct.setPriceByQuantity(quantity);

        //Creamos un nuevo objeto money que sera usado en la operacion del repository
        Money updatedPrice = new Money(foundProduct.getPrice().getValue(), Badge.USD);
        shoppingCartRepository.updateProductQuantity(foundProduct.getProductId(), updatedPrice, quantity);

        //Actualizamos el valor del total del shopping cart
        updatedShoppingCart.getProducts().remove(id);
        updatedShoppingCart.addProduct(foundProduct);
        updatedShoppingCart.calculateTotalPrice(updatedShoppingCart.getProducts());

        //Actualizamos la fecha de ultima modificacion
        updatedShoppingCart.setUpdatedAt(Instant.now());
        
        return updatedShoppingCart;
    }
}
