package com.hhtc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/12 21:01
 * @Version 1.0
 */
@Data
public class Address  implements Serializable {
    private Integer id;
    private String person;
    private String address;
    private String phone;
    private Integer code=0;
    private Integer uid;
    private Integer isdefault=0;
}
