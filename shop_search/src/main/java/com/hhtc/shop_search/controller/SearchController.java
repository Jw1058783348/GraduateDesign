package com.hhtc.shop_search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hhtc.entity.SolrGoods;
import com.hhtc.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/29 11:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Value("${image.path}")
    private String path;
    @Reference
    private ISearchService searchService;
    @RequestMapping("/list")
    public String searchList(String keyword, Model model){
        System.out.println("关键字是="+keyword);
        List<SolrGoods> solrGoodsList = searchService.queryIndex(keyword);
        for (int i = 0; i <solrGoodsList.size() ; i++) {
            System.out.println(solrGoodsList.get(i));
        }
        model.addAttribute("solrGoodsList",solrGoodsList);
        model.addAttribute("path",path);
        model.addAttribute("keyword",keyword);
        return "searchlist";
    }

     @RequestMapping("/addIndex")
     @ResponseBody
    public String addIndex(@RequestBody List<SolrGoods> solrGoodsList){
        //同步索引库
        /*for(SolrGoods solrGoods:solrGoodsList){
            System.out.println("添加索引工程接收到的数据"+solrGoods);
            searchService.addIndex(solrGoods);
        }*/
         for (int i = 0; i <solrGoodsList.size() ; i++) {
             System.out.println("添加索引工程接收到的数据"+solrGoodsList.get(i));
             searchService.addIndex(solrGoodsList.get(i));
         }
        return "success";
    }
    @RequestMapping("/delIndex")
     @ResponseBody
    public String delIndex(@RequestBody Integer id){
        searchService.delIndex(id);
        return "success";
    }



}
