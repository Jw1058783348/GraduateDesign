package com.hhtc.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhtc.dao.ICartDao;
import com.hhtc.entity.Cart;
import com.hhtc.entity.User;
import com.hhtc.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/13 18:20
 * @Version 1.0
 */
@Service
public class CartServiceImpl  implements ICartService {
    @Autowired
    private ICartDao cartDao;

    @Override
    public Cart addCart(Cart cart, User user) {

        cart.setUid(user.getId());
        cartDao.addCart(cart);
        return cart;
    }

    @Override
    public Cart queryCartByVid(Integer vid) {
        Cart cart=cartDao.queryCartByVid(vid);
        return cart;
    }

    @Override
    public Integer updateCart(Cart queryResult) {
        return cartDao.updateCart(queryResult);
    }

    @Override
    public List<Cart> queryCartListByUid(Integer uid) {
        return cartDao.queryCartListByUid(uid);
    }

    @Override
    public Integer delCartById(Integer id) {
        return cartDao.delCartById(id);
    }


}
