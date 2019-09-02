package com.hhtc.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/12 22:34
 * @Version 1.0
 */
@Data
public class Orders implements Serializable {
    private Integer id;
    private String orderid;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private Integer code=0;
    private Integer oprice;
    private Integer status=0;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String createTime;
    private String common;//留言
}
