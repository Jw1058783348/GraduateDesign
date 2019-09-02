package com.hhtc.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhtc.dao.IGoodsDao;
import com.hhtc.entity.Goods;
import com.hhtc.entity.Types;
import com.hhtc.entity.Versions;
import com.hhtc.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/16 13:53
 * @Version 1.0
 */
@Service
public class GoodsServiceImpl implements IGoodService {
    @Autowired
    private IGoodsDao goodsDao;
    @Override
    public List<Goods> queryAll(Integer currentPage,Integer pageSize) {
        return goodsDao.queryAll(currentPage,pageSize);
    }

    @Override
    public Integer queryCount() {
        return goodsDao.queryCount();
    }

    @Override
    public Goods saveGoods(Goods goods) {
         goodsDao.insert(goods);
         return goods;
    }

    @Override
    public void delete(Integer id) {
        goodsDao.delete(id);
    }

    @Override
    public int updateGoods(Goods goods) {
        return goodsDao.update(goods);
    }

    @Override
    public void batchDel(List<Integer> ids) {
        goodsDao.batchDel(ids);
    }

    @Override
    public List<Types> queryTypes() {
        return goodsDao.queryTypes();
    }

    @Override
    public List<Versions> queryVersions(Integer gid) {
        return goodsDao.queryVersions(gid);
    }

    @Override
    public Versions addVersion(Versions versions) {
        goodsDao.addVersions(versions);
        return versions;
    }

    @Override
    public Integer queryKeyId() {
        return goodsDao.queryKeyId();
    }

    @Override
    public void deleteVersions(Integer id) {
        goodsDao.deleteVersions(id);
    }

    @Override
    public void batchDelVersions(List<Integer> ids) {
        goodsDao.batchDelVersions(ids);
    }

    @Override
    public void updateVersions(Versions versions) {
        goodsDao.updateVersions(versions);
    }

    @Override
    public Integer updateTypes(Types types) {
        return goodsDao.updateTypes(types);
    }

    @Override
    public Integer addTypes(Types types) {
        return goodsDao.addTypes(types);
    }

    @Override
    public Integer deleteTypes(Integer id) {
        return goodsDao.deleteTypes(id);
    }

    @Override
    public Goods getGoodsById(Integer gid) {
        return goodsDao.getGoodsById(gid);
    }

    @Override
    public Versions queryVersionsByVid(Integer vid) {
        return goodsDao.queryVersionsByVid(vid);
    }
}
