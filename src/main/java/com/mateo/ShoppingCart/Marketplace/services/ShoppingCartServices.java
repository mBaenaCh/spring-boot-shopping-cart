package com.mateo.ShoppingCart.Marketplace.services;

/* La capa de servicio es aquella dedicada a manejar la logica del negocio
 *  de tal modo que podamos gestionar las transacciones necesarias para el
 *  repository*/

import com.mateo.ShoppingCart.Marketplace.domain.*;
import com.mateo.ShoppingCart.Marketplace.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ShoppingCartServices {
    private ShoppingCartRepository repository;

    public ShoppingCartServices(ShoppingCartRepository repository){
        this.repository = repository;
    }

    public List<Product> getAllProducts(){
        return repository.getAllProducts();
    }

    public Product getProductById(ProductId id){
        return repository.getProductById(id);
    }

    public ShoppingCart getShoppingCartById(ClientId id) {
        return repository.getShoppingCartById(id);
    }

    public Product createProduct(Product product){
        repository.createProduct(product);
        return product;
    }

    public Product updateProductById(ProductId id, Product product){
        repository.updateProductById(id, product);

        return product;
    }

    public void deleteProductById(ProductId id){
        repository.deleteProductById(id);
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart){
        repository.createShoppingCart(shoppingCart);

        return shoppingCart;
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
        repository.addProductToShoppingCart(product.getProductId(), shoppingCart.getClientId());

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

        repository.removeProductFromShoppingCart(id);

        return updatedShoppingCart;
    }

    public ShoppingCart increaseProductQuantity(ProductId id, ShoppingCart shoppingCart){

        ShoppingCart updatedShoppingCart = new ShoppingCart(shoppingCart.getClientId(),
                shoppingCart.getCreatedAt(),
                shoppingCart.getUpdatedAt(),
                shoppingCart.getProducts());

        Product foundProduct = updatedShoppingCart.getProducts().get(id);

        //Incrementamos la cantidad del producto de interes
        foundProduct.getQuantity().incrementValue();

        //Actualizamos el valor del producto en funcion de la cantidad
        foundProduct.setPriceByQuantity(foundProduct.getQuantity());

        //Creamos un nuevo objeto money que sera usado en la operacion del repository
        Money updatedPrice = new Money(foundProduct.getPrice().getValue(), Badge.USD);
        repository.updateProductQuantity(foundProduct.getProductId(), updatedPrice, foundProduct.getQuantity());

        //Actualizamos el valor del total del shopping cart
        updatedShoppingCart.getProducts().remove(id);
        updatedShoppingCart.addProduct(foundProduct);
        updatedShoppingCart.calculateTotalPrice(updatedShoppingCart.getProducts());

        //Actualizamos la fecha de ultima modificacion
        updatedShoppingCart.setUpdatedAt(Instant.now());

        return updatedShoppingCart;
    }

    public ShoppingCart decreaseProductQuantity(ProductId id, ShoppingCart shoppingCart){
        ShoppingCart updatedShoppingCart = new ShoppingCart(shoppingCart.getClientId(),
                shoppingCart.getCreatedAt(),
                shoppingCart.getUpdatedAt(),
                shoppingCart.getProducts());

        Product foundProduct = updatedShoppingCart.getProducts().get(id);
        //Validamos si el item a eliminar tiene solo 1 unidad
        if( foundProduct.getQuantity().asInteger() == 1 ){
            return removeProductFromTheShoppingCart(updatedShoppingCart, id);
        } else {

            //Disminuimos el valor de cantidad del objeto encontrado
            foundProduct.getQuantity().decreaseValue();

            //Actualizamos el precio del producto en funcion de la cantidad
            foundProduct.setPriceByQuantity(foundProduct.getQuantity());
            Money updatedPrice = new Money(foundProduct.getPrice().getValue(), Badge.USD);

            repository.updateProductQuantity(foundProduct.getProductId(), updatedPrice, foundProduct.getQuantity());

            //Actualizamos el producto dentro del shopping cart
            updatedShoppingCart.getProducts().remove(id);
            updatedShoppingCart.addProduct(foundProduct);

            //Actualizamos el valor de total del shopping cart
            updatedShoppingCart.calculateTotalPrice(updatedShoppingCart.getProducts());

            updatedShoppingCart.setUpdatedAt(Instant.now());

            return updatedShoppingCart;
        }
    }
}
