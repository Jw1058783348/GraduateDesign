package com.hhtc.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hhtc.aop.IsLogin;
import com.hhtc.entity.*;
import com.hhtc.service.ICartService;
import com.hhtc.service.IGoodService;
import com.hhtc.service.IOrderService;
import com.hhtc.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author JH
 * @Time 2019/5/14 19:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Reference
    private IUserService userService;
    @Reference
    private ICartService cartService;
    @Reference
    private IGoodService goodService;
    @Reference
    private IOrderService orderService;

    @RequestMapping("/toEdit")
    @IsLogin(true)
    public String toEdit(User user, Model model,Address address){

        System.out.println("user==="+user);
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

        System.out.println("临时添加地址====="+address);
        List<Address> addressList=new ArrayList<>();
        if(address.getPerson()!=null&&!"".equals(address.getPerson())){
            address.setUid(user.getId());
            Address addressResult=userService.addAddress(address);
            addressList.add(addressResult);
        }else{
             addressList=userService.queryAddressByUserId(user.getId());
        }
        model.addAttribute("cartList",cartList);
        model.addAttribute("allPrice",allPrice);
        model.addAttribute("addressList",addressList);
        return "orderEdit";
    }

    @RequestMapping("/addOrder")
    @IsLogin(true)
    public String addOrder(User user,String aid,String common){


        List<Cart> cartList= cartService.queryCartListByUid(user.getId());
        Integer allPrice=0;
        for (int i = 0; i <cartList.size() ; i++) {
            Goods goods=goodService.getGoodsById(cartList.get(i).getGid());
            cartList.get(i).setGoods(goods);
            Versions versions=goodService.queryVersionsByVid(cartList.get(i).getVid());
            cartList.get(i).setVersions(versions);
            Integer price=(cartList.get(i).getVersions().getGprice())*(cartList.get(i).getGnumber());
            allPrice=allPrice+price;
        }
        //生成订单
        Orders orders=new Orders();
        //设置订单总价
        orders.setOprice(allPrice);
        //设置订单的用户id
        orders.setUid(user.getId());
        //设置订单留言
        orders.setCommon(common);
        Address address=userService.queryAddressById(Integer.parseInt(aid));
        //设置订单收货人
        orders.setPerson(address.getPerson());
        //设置订单收货地址
        orders.setAddress(address.getAddress());
        //设置订单收货手机号
        orders.setPhone(address.getPhone());
        //生成订单编号
        String orderid= UUID.randomUUID().toString();
        //设置订单编号
        orders.setOrderid(orderid);
        //生成时间
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orders.setCreateTime(simpleDateFormat.format(date));
        //添加订单
        orders=orderService.addOrder(orders);

        System.out.println(orders);
        //生成订单详情
        for (int i = 0; i <cartList.size() ; i++) {
            OrderDetails orderDetails=new OrderDetails();
            //设置订单的id
            orderDetails.setOid(orders.getId());
            //设置商品id
            orderDetails.setGid(cartList.get(i).getGid());
            //设置商品数量
            orderDetails.setGnumber(cartList.get(i).getGnumber());
            //设置商品信息
            orderDetails.setGinfo(cartList.get(i).getGoods().getGinfo());
            //设置商品名称
            orderDetails.setGname(cartList.get(i).getGoods().getGname());
            //设置商品图片
            orderDetails.setGimage(cartList.get(i).getGoods().getGimage());
            //设置商品版本
            orderDetails.setGversion(cartList.get(i).getVersions().getGversion());
            //设置商品价格
            orderDetails.setGprice(cartList.get(i).getVersions().getGprice());
            //添加订单详情
            orderService.addOrderDetaails(orderDetails);
            //删除购物车记录
            cartService.delCartById(cartList.get(i).getId());
        }
        return "redirect:http://localhost:8081/";
    }

    @RequestMapping("/toGouMai")
    @IsLogin(true)
    public  String toGouMai(User user,Cart cart,Model model){
          cart.setUid(user.getId());
          cart.setGoods(goodService.getGoodsById(cart.getGid()));
          cart.setVersions(goodService.queryVersionsByVid(cart.getVid()));
          List<Cart> cartList=new ArrayList<>();
          cart=cartService.addCart(cart,user);
          cartList.add(cart);
          model.addAttribute("cartList",cartList);
          Integer allPrice=cart.getVersions().getGprice();
          model.addAttribute("allPrice",allPrice);
             List<Address> addressList = userService.queryAddressByUserId(user.getId());
            model.addAttribute("addressList",addressList);
          return "orderEdit";
    }
}
