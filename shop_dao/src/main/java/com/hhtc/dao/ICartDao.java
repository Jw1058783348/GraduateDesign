package com.hhtc.dao;

import com.hhtc.entity.Cart;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/13 18:26
 * @Version 1.0
 */
public interface ICartDao {
    Integer addCart(Cart cart);

    Cart queryCartByVid(Integer vid);

    Integer updateCart(Cart queryResult);

    List<Cart> queryCartListByUid(Integer uid);

    Integer delCartById(Integer id);
}
