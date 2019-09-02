package com.hhtc.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.hhtc.entity.OrderDetails;
import com.hhtc.entity.Orders;
import com.hhtc.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/15 21:57
 * @Version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IOrderService orderService;


    @RequestMapping("/list")
    @ResponseBody
    public String ordersList(String page, String limit) {
        //request.getSession().setAttribute("imagePath",imagePath);
        int pageSize = Integer.parseInt(limit);
        int currentPage = (Integer.parseInt(page) - 1) * pageSize;
        List<Orders> orders = orderService.queryAll(currentPage, pageSize);
        Integer count = orderService.queryCount();
        System.out.println(orders);
        /*System.out.println("当前页="+page);
        System.out.println("限制条数="+limit);
        System.out.println(goods);*/
        return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + new Gson().toJson(orders) + "}";//code:接口状态,msg:提示文本,count:数据长度,data:数据列表
    }


     @RequestMapping("/getOrderDetailsList")
        @ResponseBody
        public String getOrderDetailsList(String page, String limit) {
            //request.getSession().setAttribute("imagePath",imagePath);
            int pageSize = Integer.parseInt(limit);
            int currentPage = (Integer.parseInt(page) - 1) * pageSize;
            List<OrderDetails> ordersDetails = orderService.queryAllDetails(currentPage, pageSize);
            Integer count = orderService.queryDetailsCount();
            System.out.println(ordersDetails);
            /*System.out.println("当前页="+page);
            System.out.println("限制条数="+limit);
            System.out.println(goods);*/
            return "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + new Gson().toJson(ordersDetails) + "}";//code:接口状态,msg:提示文本,count:数据长度,data:数据列表
        }


    @RequestMapping("/SaveOrUpdateOrder")
    @ResponseBody
    public String SaveOrUpdateOrder(Orders orders){
        if(orders.getId()!=null){
            orderService.updateOrder(orders);
            return "修改成功,请点击右上角关闭";
        }else{
            return "添加成功";
        }
    }

    @RequestMapping("/SaveOrUpdateOrderDetails")
    @ResponseBody
    public String SaveOrUpdateOrderDetails(OrderDetails orderDetails){
        System.out.println("修改订单详情怎么老是报错=============="+orderDetails);
        if(orderDetails.getId()!=null){
            OrderDetails orderDetailsOld=orderService.queryOrderDetailsById(orderDetails.getId());
            Integer oldOPrice=orderDetailsOld.getGnumber()*orderDetailsOld.getGprice();
            orderService.updateorderDetails(orderDetails);
            Integer change=orderDetails.getGnumber()*orderDetails.getGprice();
            Orders order = orderService.getOrderByOid(orderDetails.getOid());
            //更新订单总价
            change=order.getOprice()-oldOPrice+change;
            order.setOprice(change);
            orderService.updateOrder(order);

            return "修改成功,请点击右上角关闭";
        }else{
            return "添加成功";
        }
    }

    /**
     * 删除商品
     *
     * @param id 被删除的id
     * @return
     */
    @RequestMapping("/delOrder/{id}")
    @ResponseBody
    public String delGoods(@PathVariable("id") Integer id)  {
        //System.out.println(id);
        //orderService.deleteVersions(id);
        orderService.deleteOrder(id);
        return "删除成功";
    }
    @RequestMapping("/delOrderDetails")
    @ResponseBody
    public String delOrderDetails(Integer id,Integer oid,Integer gprice,Integer gnumber)  {
        //执行总订单更新价格
        Integer change =gprice*gnumber;
        //System.out.println(id);
        //orderService.deleteVersions(id);
        Orders orders = orderService.getOrderByOid(oid);
        Integer oprice=orders.getOprice();
        change=oprice-change;
        orders.setOprice(change);
        orderService.updateOrder(orders);
        //好了订单详情你可以删了
        orderService.deleteOrderDetails(id);
        return "删除成功";
    }


    @RequestMapping("/getOrderByOid")
    @ResponseBody
    public String getOrderByOid(Integer id)  {
        //System.out.println(id);
        //orderService.deleteVersions(id);
        Orders orders=orderService.getOrderByOid(id);
        return new Gson().toJson(orders);
    }


}
