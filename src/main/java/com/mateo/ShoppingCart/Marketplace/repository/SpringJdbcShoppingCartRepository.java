package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Component
public class SpringJdbcShoppingCartRepository implements ShoppingCartRepository {
    /*  Instanciamos un objeto del tipo JdbcTemplate que sera usado para la gestion
    *   de la conexion a la base de datos.
    *
    *   La implementacion de cada metodo sera de mayor nivel, por lo que reduciremos
    *   complejidad y no tendremos que pensar en aspectos como la conexion o cierre de
    *   una interaccion con la base de datos (bd).
    * */

    private final JdbcTemplate jdbcTemplate;
    public static final Calendar timezoneUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public SpringJdbcShoppingCartRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /* Definicion del RowMapper basado en el objeto de mi modelo del dominio
    *
    *  Buscara mapear lo encontrado en una consulta a un objeto de mi modelo
    *
    *  Basa su funcionalidad en la aplicacion de una funcion lambda que nos ahorra
    *  el tener que recorrer una lista de resultados bajo un ciclo con una condicion
    *
    *  PENDIENTE: Crear un ProductMapper que implemente de RowMapper<Product>
    * */

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) -> {
        ProductId id = ProductId.generateUUIDFromString(resultSet.getString("product_id"));
        ProductName name = new ProductName(resultSet.getString("name"));
        ProductDescription description = new ProductDescription(resultSet.getString("description"));
        Money price = new Money(resultSet.getBigDecimal("price"), Badge.USD);
        ProductQuantity quantity = new ProductQuantity(resultSet.getInt("quantity"));
        return new Product(
                    id,
                    name,
                    description,
                    price,
                    quantity
        );
    };

    private final RowMapper<ShoppingCart> shoppingCartRowMapper = (resultSet, rowNum) -> {
        ClientId id = ClientId.generateUUIDFromString(resultSet.getString("client_id"));
        Timestamp tsCreatedAt = resultSet.getTimestamp("created_at",timezoneUTC);
        Timestamp tsUpdatedAt = resultSet.getTimestamp("updated_at", timezoneUTC);
        Instant createdAt = tsCreatedAt != null ? tsCreatedAt.toInstant() : null;
        Instant updatedAt = tsUpdatedAt != null ? tsUpdatedAt.toInstant() : null;
        Map<UUID, Product> products = getAllProductsFromShoppingCart();

        ShoppingCart foundShoppingCart = new ShoppingCart(id,
                createdAt,
                updatedAt,
                products);

        foundShoppingCart.setTotal(foundShoppingCart.calculateTotalPrice(products));
        return foundShoppingCart;
    };

    public Map<UUID,Product> getAllProductsFromShoppingCart(){
        String query = "SELECT product_id, name, description, quantity, price FROM product INNER JOIN shopping_cart ON product.shopping_cart_id = shopping_cart.client_id";
        List<Product> products = jdbcTemplate.query(query, productRowMapper);
        Map<UUID, Product> mapOfProducts = new HashMap<>();

        //Me quedo grande hacerlo con Stream y Map.uniqueIndex de Guava :(
        for (Product product : products) {
            mapOfProducts.put(product.getProductId().value(), product);
        }
        return mapOfProducts;
    }

    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM product";

        return jdbcTemplate.query(query, productRowMapper);
    }

    @Override
    public Product getProductById(ProductId id) {
        String query = "SELECT * FROM product WHERE product_id = ?";
        return jdbcTemplate.queryForObject(query, productRowMapper, id.toString());
    }

    @Override
    public ShoppingCart getShoppingCartById(ClientId id) {
        String query = "SELECT * FROM shopping_cart WHERE client_id = ?";
        return jdbcTemplate.queryForObject(query, shoppingCartRowMapper, id.toString());
    }

    @Override
    public void createShoppingCart(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_cart(client_id, created_at, updated_at, total) VALUES(?, ?, ?, ?)";

        Instant createdAt = shoppingCart.getCreatedAt();
        Instant updatedAt = shoppingCart.getUpdatedAt();

        Timestamp tsCreatedAt = createdAt != null ? Timestamp.from(createdAt) : null;
        Timestamp tsUpdatedAt = updatedAt != null ? Timestamp.from(updatedAt) : null;

        jdbcTemplate.update(query, ps -> {
                            ps.setString(1, shoppingCart.getClientId().toString());
                            ps.setTimestamp(2, tsCreatedAt, timezoneUTC);
                            ps.setTimestamp(3, tsUpdatedAt, timezoneUTC);
                            ps.setBigDecimal(4, shoppingCart.getTotal().getValue());
        });

    }

    @Override
    public void createProduct(Product product) {
        String query = "INSERT INTO product(product_id, name, description, price, quantity) VALUES(?, ?, ?, ?, ?)";
        /*Puedo crear un elemento en la tabla al mapear, como parametros de la consulta, las propiedades que puede retornar mi objeto
        *
        * PENDIENTE: update vs query (uso de rowmapper)*/
        jdbcTemplate.update(query, ps -> {
                            ps.setString(1, product.getProductId().toString());
                            ps.setString(2, product.getName().toString());
                            ps.setString(3, product.getDescription().toString());
                            ps.setBigDecimal(4, product.getPrice().getValue());
                            ps.setInt(5, product.getQuantity().asInteger());
        });
    }

    @Override
    public void updateProductById(ProductId id, Product product) {
        String query = "UPDATE product SET product_id = ?, name = ?, description = ?, price = ?, quantity = ? WHERE product_id = ?";
        jdbcTemplate.update(query,
                            id.toString(),
                            product.getName().toString(),
                            product.getDescription().toString(),
                            product.getPrice().getValue(),
                            product.getQuantity().asInteger(),
                            id.toString());
    }

    @Override
    public void deleteProductById(ProductId id) {
        String query = "DELETE FROM product WHERE product_id = ?";
        jdbcTemplate.update(query, id.toString());
    }

    @Override
    public void addProductToShoppingCart(ProductId productId, ClientId shoppingCartId) {
        /* En este nivel, añadir un producto al carrito solo implica asignar a uno de los productos el id del shopping cart,
         *  que en este caso se representa con el ClientId*/
        String query = "UPDATE product SET shopping_cart_id = ? WHERE product_id = ?";

        jdbcTemplate.update(query,
                shoppingCartId.toString(),
                productId.toString());
    }

    @Override
    public void removeProductFromShoppingCart(ProductId id) {
        /* En este nivel, eliminar un producto del carrito de compra solo implica limpiar el campo de shopping cart id del producto indicado*/
        String query = "UPDATE product SET shopping_cart_id = NULL WHERE product_id = ?";
        jdbcTemplate.update(query,
                id.toString());
    }

    @Override
    public void updateProductQuantity(ProductId productId, Money price, ProductQuantity productQuantity) {
        /* En este nivel, incrementar o reducir la cantidad de un producto implica solo modificar los campos de cantidad y precio que vienen del Service ya calculados*/
        String query = "UPDATE product SET price = ?, quantity = ? WHERE product_id = ?";
        jdbcTemplate.update(query,
                price.getValue(),
                productQuantity.asInteger(),
                productId.toString());
    }

    @Override
    public void updateShoppingCartById(ClientId clientId, Instant updatedAt, Money total){
        String query = "UPDATE shopping_cart SET updated_at = ?, total = ? WHERE client_id = ?";
        Timestamp tsUpdatedAt = updatedAt != null ? Timestamp.from(updatedAt) : null;
        jdbcTemplate.update(query, ps -> {
                                    ps.setTimestamp(1, tsUpdatedAt, timezoneUTC);
                                    ps.setBigDecimal(2, total.getValue());
                                    ps.setString(3, clientId.toString());
                });
    }

}
