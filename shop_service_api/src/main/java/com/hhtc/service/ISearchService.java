package com.hhtc.service;

import com.hhtc.entity.SolrGoods;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/7 20:19
 * @Version 1.0
 */
public interface ISearchService {
    Integer addIndex(SolrGoods solrGoods);
    List<SolrGoods> queryIndex(String keyword);

    Integer delIndex(Integer id);
}
