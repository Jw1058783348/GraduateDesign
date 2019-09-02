package com.hhtc.shop_front.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hhtc.aop.IsLogin;
import com.hhtc.entity.Address;
import com.hhtc.entity.OrderDetails;
import com.hhtc.entity.Orders;
import com.hhtc.entity.User;
import com.hhtc.service.IGoodService;
import com.hhtc.service.IOrderService;
import com.hhtc.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/15 1:18
 * @Version 1.0
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Reference
    private IUserService userService;
    @Reference
    private IOrderService orderService;
    @Reference
    private IGoodService goodService;
    @RequestMapping("/toSelfInfo")
    @IsLogin
    public String toSelfInfo(User user, Model model){
        model.addAttribute("user",user);
        List<Address> addressList = userService.queryAddressByUserId(user.getId());
        model.addAttribute("addressList",addressList);
        return "selfInfo";
    }

    @RequestMapping("/toOrderList")
    @IsLogin
    public String toOrderList(User user, Model model){
        List<Orders> ordersList= orderService.queryOrderByUid(user.getId());
        model.addAttribute("ordersList",ordersList);
        return "orderList";
    }

    @RequestMapping("/getOrderDetails")
    @IsLogin
    public String getOrderDetails(Integer oid, Model model){
        List<OrderDetails> orderDetailsList= orderService.queryOrderDetailsByOid(oid);
        System.out.println(orderDetailsList);
        model.addAttribute("orderDetailsList",orderDetailsList);
        return "orderDetailsList";
    }

    @RequestMapping("/toUpdateSelfInfo")
    @IsLogin
    public String getOrderDetails(User user,Model model){
        model.addAttribute("user",user);
        return "updateSelfInfo";
    }


    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(User user){
        userService.updateUser(user);
        return "redirect:http://localhost:8084/sso/logout";
    }

    @RequestMapping("/toUpdateAddress")
    public String toUpdateAddress(Integer id,Model model){
        System.out.println("看看id======"+id);
        Address address = userService.queryAddressById(id);
        model.addAttribute("addresses",address);
        return "updateaddress";
     }
     @RequestMapping("/toDelAddress")
    public String toDelAddress(Integer id){
        userService.deleteAddress(id);
         return "redirect:/index/toSelfInfo";
     }
     @RequestMapping("/updateAddress")
    public String updateAddress(Address addresses){
        userService.updateAddress(addresses);
        return "redirect:/index/toSelfInfo";
     }

}
