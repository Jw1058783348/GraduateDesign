package com.hhtc.shop_back.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author JH
 * @Time 2019/3/16 12:04
 * @Version 1.0
 */
@Controller
public class IndexController {

    @Value("${image.path}")
    private String imagePath;//图片路径
    /**
     * 同一得到跳转页面
     * @param page 需要跳转的页面
     * @return 需要跳转的视图
     */
    @RequestMapping("/toPage/{page}")
    public String toPage(@PathVariable("page") String page, Model model){
        model.addAttribute("imagePath",imagePath);
        System.out.println("正在跳往"+page+"页面");
        return page;
    }

    @RequestMapping("/")
    public String index(){
        return "login";
    }
}
