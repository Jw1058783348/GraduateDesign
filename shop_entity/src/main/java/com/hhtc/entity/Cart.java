package com.hhtc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/13 14:51
 * @Version 1.0
 */
@Data
public class Cart implements Serializable {
    private Integer id;
    private Integer gid;
    private Integer vid;
    private Integer gnumber;
    private Integer money;
    private Integer uid;
    private Goods goods;
    private Versions versions;
}
