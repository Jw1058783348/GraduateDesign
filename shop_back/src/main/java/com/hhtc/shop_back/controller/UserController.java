package com.hhtc.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.hhtc.entity.Address;
import com.hhtc.entity.User;
import com.hhtc.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/10 18:23
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Reference
    private IUserService userService;
    @RequestMapping("/list")
    @ResponseBody
    public String getUserList(String page, String limit){
        int pageSize = Integer.parseInt(limit);
        int currentPage = (Integer.parseInt(page) - 1) * pageSize;
        List<User> users=userService.queryAllUsers(pageSize,currentPage);
        System.out.println(users.toString());
        Integer count=userService.queryCount();
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + new Gson().toJson(users) + "}";
    }
    @RequestMapping("/saveorupdate")
    @ResponseBody
    public String saveorupdate(User user){
        System.out.println("saveorupdate="+user);
        if(user.getId()!=null){
            userService.updateUser(user);
            return "修改成功，点击右上角关闭";
        }else{
            userService.addUser(user);
            return "添加成功，点击右上角关闭";
        }
    }

    @RequestMapping("/saveorupdateAddress")
    @ResponseBody
    public String saveorupdateAddress(Address address, String isdefault2){
        if(isdefault2.equals("是")){
            address.setIsdefault(1);
        }else{
            address.setIsdefault(0);
        }
        System.out.println("saveorupdateAddress="+address);
        if(address.getId()!=null){
            userService.updateAddress(address);
            return "修改成功，点击右上角关闭";
        }else{
            userService.addAddress(address);
            return "添加成功，点击右上角关闭";
        }
    }
    @RequestMapping("/delUser/{id}")
    @ResponseBody
    public String delUser(@PathVariable("id") Integer id) {
        //System.out.println(id);
        userService.deleteUser(id);
        return "删除成功";
    }

    @RequestMapping("/delAddress/{id}")
    @ResponseBody
    public String delAddress(@PathVariable("id") Integer id) {
        //System.out.println(id);
        userService.deleteAddress(id);
        return "删除成功";
    }

    @RequestMapping("/batchDel")
    @ResponseBody
    public String batchDel(String usersId[]) {
        System.out.println("调用了batchdel方法");
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < usersId.length; i++) {
            System.out.println(usersId[i]);
            ids.add(Integer.valueOf(usersId[i]));
        }
        userService.batchDel(ids);
        System.out.println("删除成功");
        return "批量删除成功!";
    }

    @RequestMapping("/adminlogin")
    public String adminlogin(String username, String password, Model model, HttpServletRequest request){
        boolean result=userService.adminLogin(username,password);
        if (result==true) {
            request.getSession().setAttribute("loginUser",new User(null,username,password,null,null,null,null));
            model.addAttribute("username",username);
            model.addAttribute("password",password);
            return "index";
        }else{
            model.addAttribute("msg","用户名或者密码错误");
            return "login";
        }
    }
    @RequestMapping("/getAdmin")
    @ResponseBody
    public String getAdmin(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        return new Gson().toJson(loginUser);

    }
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(User user){
        userService.updateAdmin(user.getPassword());
        return "修改成功";
    }
    @RequestMapping("/getUserById")
    @ResponseBody
    public String getUserById(Integer id){
        System.out.println("kankan id ==="+id);
        User user=userService.getUserById(id);
        return new Gson().toJson(user);
    }

    @RequestMapping("/addressList")
    @ResponseBody
    public String addressList(String page, String limit){
        int pageSize = Integer.parseInt(limit);
        int currentPage = (Integer.parseInt(page) - 1) * pageSize;
        List<Address> addressesList=userService.queryAllAddress(pageSize,currentPage);
        Integer count=userService.queryAddressCount();
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + new Gson().toJson(addressesList) + "}";
    }
}
