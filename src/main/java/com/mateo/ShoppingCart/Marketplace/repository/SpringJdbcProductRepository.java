package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class SpringJdbcProductRepository implements ProductRepository{
    /*  Instanciamos un objeto del tipo JdbcTemplate que sera usado para la gestion
    *   de la conexion a la base de datos.
    *
    *   La implementacion de cada metodo sera de mayor nivel, por lo que reduciremos
    *   complejidad y no tendremos que pensar en aspectos como la conexion o cierre de
    *   una interaccion con la base de datos (bd).
    * */

    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcProductRepository(JdbcTemplate jdbcTemplate){
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

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) -> {
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

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductById(ProductId productId) {
        return null;
    }

    @Override
    public void createProduct(Product product) {

    }

    @Override
    public void updateProductById(ProductId productId, Product product) {

    }

    @Override
    public void deleteProductById(ProductId productId) {

    }
}
