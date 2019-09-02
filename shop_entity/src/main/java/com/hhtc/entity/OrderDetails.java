package com.hhtc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/14 23:39
 * @Version 1.0
 */
@Data
public class OrderDetails implements Serializable {

    private Integer id;
    private Integer oid;
    private Integer gid;
    private String gname;
    private String ginfo;
    private Integer gprice;
    private String gimage;
    private String gversion;
    private Integer gnumber;
}
