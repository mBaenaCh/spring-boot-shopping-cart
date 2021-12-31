package com.mateo.ShoppingCart.Marketplace.repository;

import com.mateo.ShoppingCart.Marketplace.domain.*;
import org.simpleflatmapper.jdbc.JdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;

public class SpringJdbcShoppingCartRepository implements ShoppingCartRepository{

    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcShoppingCartRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /*Usando JDBC debemos realizar la consulta que deseamos para */

    @Override
    public void addProductToShoppingCart(ProductId productId, ClientId shoppingCartid) {
        /* En este nivel, añadir un producto al carrito solo implica asignar a uno de los productos el id del shopping cart,
        *  que en este caso se representa con el ClientId*/
        String query = "UPDATE product SET shopping_cart_id = ? WHERE product_id = ?";

        jdbcTemplate.update(query,
                            shoppingCartid.toString(),
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
    public void updateProductQuantity(ProductId id, Money price, ProductQuantity quantity) {
        /* En este nivel, incrementar o reducir la cantidad de un producto implica solo modificar los campos de cantidad y precio que vienen del Service ya calculados*/
        String query = "UPDATE product SET price = ?, quantity = ? WHERE product_id = ?";
        jdbcTemplate.update(query,
                            price.getValue(),
                            quantity.asInteger(),
                            id.toString());
    }
}
