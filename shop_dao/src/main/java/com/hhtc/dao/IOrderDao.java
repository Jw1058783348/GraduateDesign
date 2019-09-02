package com.hhtc.dao;

import com.hhtc.entity.OrderDetails;
import com.hhtc.entity.Orders;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/14 23:29
 * @Version 1.0
 */
public interface IOrderDao {
    Integer addOrder(Orders orders);

    Integer addOrderDetaails(OrderDetails orderDetails);

    List<Orders> queryOrderByUid(Integer uid);

    List<OrderDetails> queryOrderDetailsByOid(Integer oid);

    List<Orders> queryAll(Integer currentPage, Integer pageSize);

    Integer queryCount();

    Integer updateOrder(Orders orders);

    Integer deleteOrder(Integer id);

    Orders getOrderByOid(Integer id);

    Integer delOrderDetails(Integer id);

    List<OrderDetails> queryAllDetails(int currentPage, int pageSize);

    Integer queryDetailsCount();

    Integer updateorderDetails(OrderDetails orderDetails);

    OrderDetails queryOrderDetailsById(Integer id);
}
