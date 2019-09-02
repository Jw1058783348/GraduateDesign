package com.hhtc.service;

import com.hhtc.entity.Goods;
import com.hhtc.entity.Types;
import com.hhtc.entity.Versions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/16 13:52
 * @Version 1.0
 */
public interface IGoodService {
    List<Goods> queryAll(Integer currentPage,Integer pageSize);
    Integer queryCount();
    Goods saveGoods(Goods goods);
    void delete(Integer id);
    int updateGoods(Goods goods);
    void batchDel(List<Integer> ids);
    List<Types> queryTypes();
    List<Versions> queryVersions(Integer gid);
    Versions addVersion(Versions versions);
    Integer queryKeyId();
    void deleteVersions(Integer id);
    void batchDelVersions(@Param("ids") List<Integer> ids);
    void updateVersions(Versions versions);

    Integer updateTypes(Types types);

    Integer addTypes(Types types);

    Integer deleteTypes(Integer id);

    Goods getGoodsById(Integer gid);

    Versions queryVersionsByVid(Integer vid);
}
