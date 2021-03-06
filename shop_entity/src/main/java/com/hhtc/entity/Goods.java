package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/3/16 13:46
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods implements Serializable {
    private Integer id;//商品id
    private String gname;//商品名称
    private String ginfo;//商品信息
    private Integer tid;//商品类别
    private String gcolor;//商品颜色list
    private String gimage ="group1/M00/00/00/wKiagFyQkJWAePwpAABqbeBQOCI316.jpg";//商品图标,默认图标
    private String gversion;
}
