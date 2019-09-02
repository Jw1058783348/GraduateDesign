package com.hhtc.dao;

import com.hhtc.entity.Goods;
import com.hhtc.entity.Types;
import com.hhtc.entity.Versions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/16 13:54
 * @Version 1.0
 */
public interface IGoodsDao {
    List<Goods> queryAll(Integer currentPage,Integer pageSize);
    Integer queryCount();
    int insert(Goods goods);
    void delete(Integer id);
    void deleteVersions(Integer id);
    int update(Goods goods);
    void batchDel(@Param("ids") List<Integer> ids);
    List<Types> queryTypes();
    List<Versions> queryVersions(Integer gid);
    int addVersions(Versions versions);
    void updateVersions(Versions versions);
    Integer queryKeyId();
    void batchDelVersions(@Param("ids") List<Integer> ids);

    Integer updateTypes(Types types);

    Integer addTypes(Types types);

    Integer deleteTypes(Integer id);

    Goods getGoodsById(Integer gid);

    Versions queryVersionsByVid(Integer vid);
}
