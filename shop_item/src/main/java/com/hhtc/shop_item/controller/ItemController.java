package com.hhtc.shop_item.controller;

import com.hhtc.entity.Goods;
import com.hhtc.entity.ItemGoods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JH
 * @Time 2019/5/8 13:37
 * @Version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private Configuration configuration;
    /**
     * 生成静态页面
     * @return
     */
    @RequestMapping("/createHtml")
    public String createHtml(@RequestBody ItemGoods itemGoods, HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        Goods goods=new Goods(itemGoods.getId(),itemGoods.getGname(),itemGoods.getGinfo(),itemGoods.getTid(),itemGoods.getGcolor(),itemGoods.getGimage(),String.valueOf(itemGoods.getVersionsList().size()));
        map.put("goods",goods);
        map.put("context",request.getContextPath());
        Writer out=null;
        String path=this.getClass().getResource("/static/html/").getPath();
        try {
            for (int i = 0; i <itemGoods.getVersionsList().size() ; i++) {
                System.out.println("itemGoodsList("+i+")="+itemGoods.getVersionsList().get(i));
                map.put("versions",itemGoods.getVersionsList().get(i));
                    Template template = configuration.getTemplate("goods.ftl");
                    out=new FileWriter(path+itemGoods.getVersionsList().get(i).getId()+".html");
                    template.process(map,out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
