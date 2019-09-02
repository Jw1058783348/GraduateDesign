package com.hhtc.shop_sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.hhtc.entity.ResultData;
import com.hhtc.entity.User;
import com.hhtc.service.IUserService;
import com.hhtc.utils.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author JH
 * @Time 2019/5/10 9:59
 * @Version 1.0
 */
@Controller
@RequestMapping("/sso")
public class SsoController {
    @Reference
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/toLogin")
    public String toLogin(String returnURL,Model model){
        model.addAttribute("returnURL",returnURL);
        return "login";

    }@RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/Login")
    public String Login(String username, String password, Model model, HttpServletResponse response,String returnURL){
        ResultData<User> resultData = userService.login(username, password);
        System.out.println("原来的页面的URL==========="+returnURL);
        switch (resultData.getCode()){
            case 100:
                if(returnURL==null||("").equals(returnURL)){
                    returnURL="http://localhost:8081/";
                }
                String token = UUID.randomUUID().toString();
                redisTemplate.opsForValue().set(token,resultData.getData());
                System.out.println(resultData.getData());
                redisTemplate.expire(token,7, TimeUnit.DAYS);
                Cookie cookie=new Cookie(Constact.LOGIN_TOKEN_NAME,token);
                cookie.setMaxAge(60*60*24*7);//设置有效时长
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            default:
                model.addAttribute("msg",resultData.getMsg());
                return "login";
        }
        return "redirect:"+returnURL;
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(@CookieValue(value = Constact.LOGIN_TOKEN_NAME,required = false) String token){
        System.out.println("token="+token);
        User user =null;
        if(token!=null){
            user= (User) redisTemplate.opsForValue().get(token);
            System.out.println("checkLogin的user="+user);
        }
        return user != null ? "isLogin('" + new Gson().toJson(user) + "')" : "isLogin(null)";
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(value = Constact.LOGIN_TOKEN_NAME, required = false) String token, HttpServletResponse response){
        if(token != null){
            //清空redis
            redisTemplate.delete(token);
            //删除cookie
            Cookie cookie = new Cookie(Constact.LOGIN_TOKEN_NAME, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "login";
    }

    @RequestMapping("/register")
    public String register(User user,Model model){
        boolean result=userService.queryByUsername(user.getUsername());
        if(result){
            model.addAttribute("msg","用户名已存在");
            return "register";
        }else{
            Integer addResult=userService.addUser(user);
            model.addAttribute("msg","注册成功");
            return "register";
        }
    }
}
