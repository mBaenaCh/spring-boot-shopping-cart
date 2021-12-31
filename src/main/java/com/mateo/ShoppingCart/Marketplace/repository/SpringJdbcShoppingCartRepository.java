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
    public void addProductToShoppingCart(ProductId productId, ClientId id) {

        String query = "UPDATE product SET shopping_cart_id = ? WHERE product_id = ?";

        jdbcTemplate.update(query,
                            id.toString(),
                            productId.toString());
        /* 1) Se añade un producto al mapa de productos
         * 2) Se actualiza el valor del total
         * 3) Se actualiza el valor de fecha de actualizacion
         */

    }

    @Override
    public void deleteProductFromShoppingCart(ProductId id) {
        /* 1) Se elimina un producto del mapa de productos
         * 2) Se actualiza el valor del total
         * 3) Se actualiza el valor de fecha de actualizacion
         */
    }

    @Override
    public void increaseQuantity() {
        /* 1) Se busca el producto al cual se le incrementara el valor de cantidad
         * 2) Se actualiza el valor de cantidad de producto encontrado
         * 3) Se actualiza el valor de precio del producto encontrado
         * 4) Se actualiza el valor del total
         * 5) Se actualiza el valor de fecha de actualizacion
         */
    }

    @Override
    public void decreaseQuantity() {
        /* 1) Se busca el producto al cual se le restara el valor de cantidad
        *  2) Se actualiza el valor de cantidad del producto encontrado
        *  3) Se actualiza el valor de precio del producto encontrado
        *  4) Se actualiza el valor del total
        *  5) Se actualiza el valor de fecha de actualizacion*/
    }
}
