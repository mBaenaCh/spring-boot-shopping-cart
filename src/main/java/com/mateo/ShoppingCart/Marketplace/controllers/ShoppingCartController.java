package com.mateo.ShoppingCart.Marketplace.controllers;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import com.mateo.ShoppingCart.Marketplace.model.*;
import com.mateo.ShoppingCart.Marketplace.services.ShoppingCartServices;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    private ShoppingCartServices shoppingCartServices;

    public ShoppingCartController(ShoppingCartServices shoppingCartServices){
        this.shoppingCartServices = shoppingCartServices;
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
        Product createdProduct = shoppingCartServices.createProduct(product);

        return new CreateProductOutput(createdProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return shoppingCartServices.getAllProducts();
    }

    //Añadimos a la ruta un parametro de Id que se espera recibir para esta consulta
    @GetMapping(value = "/products/{id}")
    //La anotacion PathVariable nos permite manejar ese parametro que es enviado con la ruta
    public Product getProductById(
            @PathVariable("id") String id){
        final ProductId productId = ProductId.generateUUIDFromString(id);
        return shoppingCartServices.getProductById(productId);
    }

    @GetMapping("/{id}")
    public ShoppingCartOutput getShoppingCartById(
            @PathVariable("id") String id){
        final ClientId clientId = ClientId.generateUUIDFromString(id);

        ShoppingCart foundShoppingCart = shoppingCartServices.getShoppingCartById(clientId);

        ShoppingCartOutput output = new ShoppingCartOutput();
        output.setClientId(foundShoppingCart.getClientId());
        output.setCreatedAt(foundShoppingCart.getCreatedAt());
        output.setUpdatedAt(foundShoppingCart.getUpdatedAt());
        output.setProducts(foundShoppingCart.getProducts());
        output.setTotal(foundShoppingCart.getTotal());

        return output;
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
        final Product updatedProduct = shoppingCartServices.updateProductById(productId, newProduct);

        return new UpdateProductOutput(updatedProduct);
    }

    @PostMapping
    public ShoppingCartOutput createShoppingCart(){
        ClientId clientId = ClientId.generateUUID();
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Map<UUID, Product> products = new HashMap<>();

        ShoppingCart shoppingCart = new ShoppingCart(clientId, createdAt, updatedAt, products);
        shoppingCart.setTotal(new Money(new BigDecimal(0), Badge.USD));
        ShoppingCart createdShoppingCart = shoppingCartServices.createShoppingCart(shoppingCart);
        ShoppingCartOutput output = new ShoppingCartOutput();
        output.setClientId(createdShoppingCart.getClientId());
        output.setCreatedAt(createdShoppingCart.getCreatedAt());
        output.setUpdatedAt(createdShoppingCart.getUpdatedAt());
        output.setProducts(createdShoppingCart.getProducts());
        output.setTotal(createdShoppingCart.getTotal());
        return output;
    }

    @DeleteMapping(value = "/products/{id}")
    public void deleteProductById(
            @PathVariable("id") String id){
        final ProductId productId = ProductId.generateUUIDFromString(id);
        shoppingCartServices.deleteProductById(productId);
    }

    @PutMapping(value = "/{scId}/add-product/{pId}")
    public ShoppingCartOutput addProductToShoppingCart(
            @PathVariable("scId") String scId,
            @PathVariable("pId") String pId){
        final ProductId productId = ProductId.generateUUIDFromString(pId);
        final ClientId clientId = ClientId.generateUUIDFromString(scId);

        ShoppingCart updatedShoppingCart = shoppingCartServices.addProductToShoppingCart(productId, clientId);
        ShoppingCartOutput output = new ShoppingCartOutput();
        output.setClientId(updatedShoppingCart.getClientId());
        output.setCreatedAt(updatedShoppingCart.getCreatedAt());
        output.setUpdatedAt(updatedShoppingCart.getUpdatedAt());
        output.setProducts(updatedShoppingCart.getProducts());
        output.setTotal(updatedShoppingCart.getTotal());

        return output;
    }

    @PutMapping(value = "/{scId}/remove-product/{pId}")
    public ShoppingCartOutput removeProductFromShoppingCart(
            @PathVariable("scId") String scId,
            @PathVariable("pId") String pId){
        final ProductId productId = ProductId.generateUUIDFromString(pId);
        final ClientId clientId = ClientId.generateUUIDFromString(scId);

        ShoppingCart updatedShoppingCart = shoppingCartServices.removeProductFromTheShoppingCart(productId, clientId);

        ShoppingCartOutput output = new ShoppingCartOutput();
        output.setClientId(updatedShoppingCart.getClientId());
        output.setCreatedAt(updatedShoppingCart.getCreatedAt());
        output.setUpdatedAt(updatedShoppingCart.getUpdatedAt());
        output.setProducts(updatedShoppingCart.getProducts());
        output.setTotal(updatedShoppingCart.getTotal());

        return output;
    }

    @PutMapping(value = "/{scId}/increase-quantity/{pId}")
    public ShoppingCartOutput increaseProductQuantity(
            @PathVariable("scId") String scId,
            @PathVariable("pId") String pId){

        final ProductId productId = ProductId.generateUUIDFromString(pId);
        final ClientId clientId = ClientId.generateUUIDFromString(scId);

        ShoppingCart updatedShoppingCart = shoppingCartServices.increaseProductQuantity(productId, clientId);

        ShoppingCartOutput output = new ShoppingCartOutput();

        output.setClientId(updatedShoppingCart.getClientId());
        output.setCreatedAt(updatedShoppingCart.getCreatedAt());
        output.setUpdatedAt(updatedShoppingCart.getUpdatedAt());
        output.setProducts(updatedShoppingCart.getProducts());
        output.setTotal(updatedShoppingCart.getTotal());

        return output;
    }

    @PutMapping(value = "/{scId}/decrease-quantity/{pId}")
    public ShoppingCartOutput decreaseProductQuantity(
            @PathVariable("scId") String scId,
            @PathVariable("pId") String pId){

        final ProductId productId = ProductId.generateUUIDFromString(pId);
        final ClientId clientId = ClientId.generateUUIDFromString(scId);

        ShoppingCart updatedShoppingCart = shoppingCartServices.decreaseProductQuantity(productId, clientId);

        ShoppingCartOutput output = new ShoppingCartOutput();
        output.setClientId(updatedShoppingCart.getClientId());
        output.setCreatedAt(updatedShoppingCart.getCreatedAt());
        output.setUpdatedAt(updatedShoppingCart.getUpdatedAt());
        output.setProducts(updatedShoppingCart.getProducts());
        output.setTotal(updatedShoppingCart.getTotal());
        return output;
    }
}
