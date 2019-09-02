package com.hhtc.service;

import com.hhtc.entity.Cart;
import com.hhtc.entity.User;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/13 18:18
 * @Version 1.0
 */
public interface ICartService {
    Cart addCart(Cart cart, User user);

    Cart queryCartByVid(Integer vid);

    Integer updateCart(Cart queryResult);

    List<Cart> queryCartListByUid(Integer id);

    Integer delCartById(Integer id);
}
