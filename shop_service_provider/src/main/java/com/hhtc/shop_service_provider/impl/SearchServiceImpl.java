package com.hhtc.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhtc.entity.SolrGoods;
import com.hhtc.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/7 20:22
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;
    @Override
    public Integer addIndex(SolrGoods solrGoods) {
        try {
            SolrInputDocument solrDocument=new SolrInputDocument();
            solrDocument.addField("id",solrGoods.getGid());//商品版本id作为索引id
            solrDocument.addField("gname",solrGoods.getGname());
            solrDocument.addField("ginfo",solrGoods.getGinfo());
            solrDocument.addField("gimage",solrGoods.getGimage());
            solrDocument.addField("gversion",solrGoods.getGversion());
            solrDocument.addField("gprice",solrGoods.getGprice());
            solrDocument.addField("gid",solrGoods.getId());//商品的id作为索引对象的商品id

            solrClient.add(solrDocument);
            solrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<SolrGoods> queryIndex(String keyword) {
        String queryStr=null;
        if (keyword==null||keyword.trim().equals("")){
            queryStr="*:*";
        }else{
            queryStr=String.format("gname:%s || ginfo:%s",keyword,keyword);
    }

        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery(queryStr);
        List<SolrGoods> solrGoodsList=new ArrayList<SolrGoods>();
        try {
            QueryResponse response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            for(SolrDocument document:results){

                Integer id= Integer.parseInt(document.get("id")+"");
                String gname= (String) document.get("gname");
                String gimage= (String) document.get("gimage");
                Integer gid= Integer.parseInt(document.get("gid")+"");
                String gversion= (String) document.get("gversion");
                Integer gprice = Integer.parseInt(document.get("gprice")+"");
                SolrGoods solrGoods=new SolrGoods(id,gid,gname,gimage,null,gversion,gprice);
                solrGoodsList.add(solrGoods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solrGoodsList;
    }

    @Override
    public Integer delIndex(Integer id) {
        try {
            solrClient.deleteById("3");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
