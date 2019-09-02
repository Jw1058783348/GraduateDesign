package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/7 18:29
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SolrGoods implements Serializable {
    private Integer id;//商品ID
    private Integer gid;//版本ID
    private String gname;//商品名称
    private String gimage;//商品图片
    private String ginfo;//商品信息
    private String gversion;//商品版本
    private Integer gprice;//商品价格
}
