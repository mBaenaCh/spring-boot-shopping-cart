package com.mateo.ShoppingCart.Marketplace.controllers;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import com.mateo.ShoppingCart.Marketplace.model.CreateProductInput;
import com.mateo.ShoppingCart.Marketplace.model.CreateProductOutput;
import com.mateo.ShoppingCart.Marketplace.model.UpdateProductInput;
import com.mateo.ShoppingCart.Marketplace.model.UpdateProductOutput;
import com.mateo.ShoppingCart.Marketplace.services.ShoppingCartServices;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/* Con nuestro controller definiremos las funcionalidades que actuaran sobre nuestro modelo del dominio
 *
 * Dado que nuestro sistema es una API REST, vamos a exponer estas funcionalidades por medio de rutas y
 * metodos HTTP
 */
@RestController

/* Modelamos toda la funcionalidad alrededor de una misma ruta pero bajo distintos metodos HTTP
 */
@RequestMapping(value="/api/shopping-cart")
public class ShoppingCartController {

    private ShoppingCartServices productService;

    public ShoppingCartController(ShoppingCartServices shoppingCartServices){
        this.productService = shoppingCartServices;
    }

    @PostMapping(value="/products")
    /* La anotacion RequestBody nos permite recibir un JSON que sera convertido al formato especificado
     * en el parametro de entrada */
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

        /* Creamos una instancia basada en el modelo del dominio para su gestion en las capas subyacentes*/
        Product product = new Product(id, productName, productDescription, price, productQuantity);
        Product createdProduct = productService.createProduct(product);

        return new CreateProductOutput(createdProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    //Añadimos a la ruta un parametro de Id que se espera recibir para esta consulta
    @GetMapping(value = "/products/{id}")
    //La anotacion PathVariable nos permite manejar ese parametro que es enviado con la ruta
    public Product getProductById(
            @PathVariable("id") String id){
        final ProductId productId = ProductId.generateUUIDFromString(id);
        return productService.getProductById(productId);
    }

    @PutMapping(value = "/products/{id}")
    public UpdateProductOutput updateProductById(
            @PathVariable("id") String id,
            @RequestBody UpdateProductInput input){
        final ProductId productId = ProductId.generateUUIDFromString(id);
        Product newProduct = new Product(
                productId,
                new ProductName(input.getName()),
                new ProductDescription(input.getDescription()),
                new Money(new BigDecimal(input.getPrice().floatValue()), Badge.USD),
                new ProductQuantity(input.getQuantity())
        );
        final Product updatedProduct = productService.updateProductById(productId, newProduct);

        return new UpdateProductOutput(updatedProduct);
    }

    @DeleteMapping(value = "/products/{id}")
    public void deleteProductById(
            @PathVariable("id") String id){
        final ProductId productId = ProductId.generateUUIDFromString(id);
        productService.deleteProductById(productId);
    }

    @PutMapping(value = "/add-product/{id}")
    public void addProductToShoppingCart(
            @PathVariable("id") String id){

    }

    @PutMapping(value = "/remove-product/{id}")
    public void removeProductFromShoppingCart(
            @PathVariable("id") String id){

    }

    @PutMapping(value = "/increase-quantity/{id}")
    public void increaseProductQuantity(
            @PathVariable("id") String id){

    }

    @PutMapping(value = "/decrease-quantity/{id}")
    public void decreaseProductQuantity(
            @PathVariable("id") String id){

    }
}
