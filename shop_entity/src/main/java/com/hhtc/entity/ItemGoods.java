package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author JH
 * @Time 2019/5/8 14:12
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemGoods implements Serializable {
    private Integer id;//商品id
    private String gname;//商品名称
    private String ginfo;//商品信息
    private Integer tid;//商品类别
    private String gcolor;//商品颜色list
    private String gimage;//商品图标,默认图标
    List<Versions> versionsList;
}
