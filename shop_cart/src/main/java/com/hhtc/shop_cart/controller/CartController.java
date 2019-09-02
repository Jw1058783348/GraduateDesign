package com.hhtc.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hhtc.aop.IsLogin;
import com.hhtc.entity.Cart;
import com.hhtc.entity.Goods;
import com.hhtc.entity.User;
import com.hhtc.entity.Versions;
import com.hhtc.service.ICartService;
import com.hhtc.service.IGoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/13 10:44
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Reference
    private ICartService cartService;
    @Reference
    private IGoodService goodService;

    @RequestMapping("/add")
    @IsLogin(true)
    public String addCart(Cart cart, User user){
        Cart queryResult=cartService.queryCartByVid(cart.getVid());
        if(queryResult!=null){
            queryResult.setGnumber(queryResult.getGnumber()+cart.getGnumber());
            cartService.updateCart(queryResult);
        }else{
            cartService.addCart(cart,user);
        }
        return "addCartSuccess";
    }
    @RequestMapping("/list")
    @IsLogin(true)
    public String list(User user, Model model){
        List<Cart> cartList= cartService.queryCartListByUid(user.getId());
        Integer allPrice=0;
        for (int i = 0; i <cartList.size() ; i++) {
            Goods goods=goodService.getGoodsById(cartList.get(i).getGid());
            cartList.get(i).setGoods(goods);
            Versions versions=goodService.queryVersionsByVid(cartList.get(i).getVid());
            cartList.get(i).setVersions(versions);
            Integer price=(cartList.get(i).getVersions().getGprice())*(cartList.get(i).getGnumber());
            System.out.println("price=="+price);
            allPrice=allPrice+price;
        }
        model.addAttribute("cartList",cartList);
        model.addAttribute("allPrice",allPrice);
        return "cartList";
    }

    @RequestMapping("/delCart")
    @ResponseBody
    public String delCart(Integer id){
        System.out.println("删除id="+id+"的购物车记录");
        cartService.delCartById(id);
        return "删除成功";
    }
}
