package com.hhtc.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhtc.dao.IOrderDao;
import com.hhtc.entity.OrderDetails;
import com.hhtc.entity.Orders;
import com.hhtc.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/14 23:28
 * @Version 1.0
 */
@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderDao orderDao;

    @Override
    public Orders addOrder(Orders orders) {
         orderDao.addOrder(orders);
         return orders;
    }

    @Override
    public Integer addOrderDetaails(OrderDetails orderDetails) {
        return orderDao.addOrderDetaails(orderDetails);
    }

    @Override
    public List<Orders> queryOrderByUid(Integer uid) {
        return orderDao.queryOrderByUid(uid);
    }

    @Override
    public List<OrderDetails> queryOrderDetailsByOid(Integer oid) {
        return orderDao.queryOrderDetailsByOid(oid);
    }

    @Override
    public List<Orders> queryAll(Integer currentPage, Integer pageSize) {
        return orderDao.queryAll(currentPage,pageSize);
    }

    @Override
    public Integer queryCount() {
        return orderDao.queryCount();
    }

    @Override
    public Integer updateOrder(Orders orders) {
        return orderDao.updateOrder(orders);
    }

    @Override
    public Integer deleteOrder(Integer id) {
        return orderDao.deleteOrder(id);
    }

    @Override
    public Orders getOrderByOid(Integer id) {
        return orderDao.getOrderByOid(id);
    }

    @Override
    public Integer deleteOrderDetails(Integer id) {
        return orderDao.delOrderDetails(id);
    }

    @Override
    public List<OrderDetails> queryAllDetails(int currentPage, int pageSize) {
        return orderDao.queryAllDetails(currentPage,pageSize);
    }

    @Override
    public Integer queryDetailsCount() {
        return orderDao.queryDetailsCount();
    }

    @Override
    public Integer updateorderDetails(OrderDetails orderDetails) {
        return orderDao.updateorderDetails(orderDetails);
    }

    @Override
    public OrderDetails queryOrderDetailsById(Integer id) {
        return orderDao.queryOrderDetailsById(id);
    }
}
