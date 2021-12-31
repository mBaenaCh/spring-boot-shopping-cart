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
    public void increaseProductQuantity() {
        /* 1) Se busca el producto al cual se le incrementara el valor de cantidad
         * 2) Se actualiza el valor de cantidad de producto encontrado
         * 3) Se actualiza el valor de precio del producto encontrado
         * 4) Se actualiza el valor del total
         * 5) Se actualiza el valor de fecha de actualizacion
         */
    }

    @Override
    public void decreaseProductQuantity() {
        /* 1) Se busca el producto al cual se le restara el valor de cantidad
        *  2) Se actualiza el valor de cantidad del producto encontrado
        *  3) Se actualiza el valor de precio del producto encontrado
        *  4) Se actualiza el valor del total
        *  5) Se actualiza el valor de fecha de actualizacion*/
    }
}
