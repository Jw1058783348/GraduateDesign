package com.hhtc.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.hhtc.entity.*;
import com.hhtc.service.IGoodService;
import com.hhtc.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/16 13:28
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {
    @Reference
    private IGoodService goodService;
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Value("${image.path}")
    private String imagePath;//图片路径

    /**
     * 获取商品列表
     *
     * @param page  当前页
     * @param limit 每页显示的条数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String goodsManager(String page, String limit) {
        //request.getSession().setAttribute("imagePath",imagePath);
        int pageSize = Integer.parseInt(limit);
        int currentPage = (Integer.parseInt(page) - 1) * pageSize;
        List<Goods> goods = goodService.queryAll(currentPage, pageSize);
        Integer count = goodService.queryCount();
        System.out.println(goods);
        /*System.out.println("当前页="+page);
        System.out.println("限制条数="+limit);
        System.out.println(goods);*/
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + new Gson().toJson(goods) + "}";//code:接口状态,msg:提示文本,count:数据长度,data:数据列表
    }

    /**
     * 添加或者编辑商品信息
     *
     * @param goods 商品属性
     * @param gfile 商品图标
     * @return
     * @throws IOException
     */
    @RequestMapping("/SaveOrUpdateGoods")
    @ResponseBody
    public String SaveOrUpdateGoods(Goods goods, MultipartFile gfile, VersionsList versionsList) throws Exception {
        if(goods.getId()!=null){
            System.out.println("调用了updateGoods");
            if (!gfile.isEmpty()) {
                System.out.println(goods);
                System.out.println(gfile.getOriginalFilename());
                System.out.println(gfile.getSize());
                String path = null;
                StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(gfile.getInputStream(), gfile.getSize(), "jpg", null);
                path = result.getFullPath();
                System.out.println("上传到fasfdfs的路径:" + path);
                goods.setGimage(path);
            }
            System.out.println("图片上传后的信 ：" + goods);
            List<Versions> vls=versionsList.getVersionsList();
            goods.setGversion(String.valueOf(vls.size()));
            goodService.updateGoods(goods);
            for (int i = 0; i <vls.size() ; i++) {
                vls.get(i).setGid(goods.getId());
                //System.out.println(vls.get(i));
                if(vls.get(i).getId()!=null){
                    goodService.updateVersions(vls.get(i));
                }else{
                    goodService.addVersion(vls.get(i));
                }
            }
            return "商品修改成功,请点击右上角关闭";
        }else{
            System.out.println("调用了addGoods");
            if (!gfile.isEmpty()) {
                System.out.println(goods);
                System.out.println(gfile.getOriginalFilename());
                System.out.println(gfile.getSize());
                String path = null;
                StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(gfile.getInputStream(), gfile.getSize(), "jpg", null);
                path = result.getFullPath();
                System.out.println("上传到fasfdfs的路径:" + path);
                goods.setGimage(path);
            }
            System.out.println("图片上传后的信息：" + goods);
            List<Versions> vls=versionsList.getVersionsList();
            goods.setGversion(String.valueOf(vls.size()));
            goods=goodService.saveGoods(goods);
            //Integer gid=goodService.queryKeyId();
            int gid=goods.getId();
            System.out.println("看看主键="+gid);
            List<SolrGoods> solrGoodsList=new ArrayList<SolrGoods>();
            for (int i = 0; i <vls.size() ; i++) {
                if(!vls.get(i).getGversion().equals("")&&vls.get(i).getGversion()!=null&&vls.get(i).getGprice()!=null){
                    vls.get(i).setGid(gid);
                    Versions versions=vls.get(i);
                    versions=goodService.addVersion(versions);
                    vls.set(i,versions);
                    //添加索引对象
                    SolrGoods solrGoods =new SolrGoods();
                    solrGoods.setId(goods.getId());
                    solrGoods.setGname(goods.getGname());
                    solrGoods.setGimage(goods.getGimage());
                    solrGoods.setGinfo(goods .getGinfo());
                    solrGoods.setGid(versions.getId());
                    solrGoods.setGprice(vls.get(i).getGprice());
                    solrGoods.setGversion(vls.get(i).getGversion());
                    solrGoodsList.add(solrGoods);
                }
            }
            ItemGoods itemGoods=new ItemGoods(goods.getId(),goods.getGname(),goods.getGinfo(),goods.getTid(),goods.getGcolor(),goods.getGimage(),vls);
            HttpClientUtil.sendJson("Http://localhost:8082/search/addIndex",new Gson().toJson(solrGoodsList));
            HttpClientUtil.sendJson("Http://localhost:8083/item/createHtml",new Gson().toJson(itemGoods));

            return "添加商品成功，请点击右边关闭窗口";
        }
    }

    /**
     * 删除商品
     *
     * @param id 被删除的商品id
     * @return
     */
    @RequestMapping("/delGoods/{id}")
    @ResponseBody
    public String delGoods(@PathVariable("id") Integer id) throws IOException {
        //System.out.println(id);
        goodService.deleteVersions(id);
        goodService.delete(id);
        HttpClientUtil.sendJson("Http://localhost:8082/search/addIndex","{id:"+id+"}");
        return "删除成功";
    }
    /**
     * 删除商品类别
     *
     * @param id 被删除的商品id
     * @return
     */
    @RequestMapping("/delTypes/{id}")
    @ResponseBody
    public String delTypes(@PathVariable("id") Integer id) {
        //System.out.println(id);
        goodService.deleteTypes(id);
        return "删除成功";
    }


    /**
     * 批量删除商品信息
     *
     * @param goodsId 商品id数组
     * @return
     */
    @RequestMapping("/batchDel")
    @ResponseBody
    public String batchDel(String goodsId[]) {
        System.out.println("调用了batchdel方法");
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < goodsId.length; i++) {
            System.out.println(goodsId[i]);
            ids.add(Integer.valueOf(goodsId[i]));
        }
        goodService.batchDelVersions(ids);
        goodService.batchDel(ids);
        System.out.println("删除成功");
        return "批量删除成功!";
    }


    /**
     * ajax获取商品的类别种类
     * @return
     */
    @RequestMapping("/queryTypes")
    @ResponseBody
    public String queryTypes(){
        List<Types> types = goodService.queryTypes();
        return new Gson().toJson(types);
    }
    /**
     * ajax获取商品的类别种类
     * @return
     */
    @RequestMapping("/queryTypesList")
    @ResponseBody
    public String queryTypesList(){
        List<Types> types = goodService.queryTypes();
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + new Gson().toJson(types) + "}";
    }


    /**
     * ajax获取商品的版本信息
     * @return
     */
    @RequestMapping("/queryVersions")
    @ResponseBody
    public String queryVersions(Integer gid){
        List<Versions> versions = goodService.queryVersions(gid);
        System.out.println(versions.toString());
        return new Gson().toJson(versions);
    }
    @RequestMapping("/typesSaveOrUpdateGoods")
    @ResponseBody
    public String typesSaveOrUpdateGoods(Types types){
       if(types.getId()!=null){
           goodService.updateTypes(types);
           return "商品品牌修改成功";
       }else{
           goodService.addTypes(types);
           return "商品品牌添加成功";
       }
    }


}
