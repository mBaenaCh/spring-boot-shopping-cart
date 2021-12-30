package com.mateo.ShoppingCart.Marketplace.controllers;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import com.mateo.ShoppingCart.Marketplace.model.CreateProductInput;
import com.mateo.ShoppingCart.Marketplace.model.CreateProductOutput;
import com.mateo.ShoppingCart.Marketplace.services.ProductServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/* Con nuestro controller definiremos las funcionalidades que actuaran sobre nuestro modelo del dominio
 *
 * Dado que nuestro sistema es una API REST, vamos a exponer estas funcionalidades por medio de rutas y
 * metodos HTTP
 */
@RestController

/* Modelamos toda la funcionalidad alrededor de una misma ruta pero bajo distintos metodos HTTP
 */
@RequestMapping(value="/products")
public class ProductsController {

    private ProductServices productServices;

    public ProductsController (ProductServices productServices){
        this.productServices = productServices;
    }

    @PostMapping
    /* La anotacion RequestBody nos permite recibir un JSON que sera convertido al formato especificado
     * en el parametro de entrada
     */
    public CreateProductOutput createProduct(
            @RequestBody CreateProductInput input){
        /* Posteriormente solo nos queda procesar los datos recibidos en el input hacia
         * la tipologia de datos que se espera en el modelo del dominio
         */
        ProductId id = ProductId.generateUUID();
        ProductName productName = new ProductName(input.getName());
        ProductDescription productDescription = new ProductDescription(input.getDescription());
        Money price = new Money( new BigDecimal(input.getPrice().floatValue()), Badge.USD); //Por revisar
        ProductQuantity productQuantity = new ProductQuantity(input.getQuantity());

        Product product = new Product(id, productName, productDescription, price, productQuantity);
        Product createdProduct = productServices.createProduct(product);

        return new CreateProductOutput(createdProduct);
    }

}
