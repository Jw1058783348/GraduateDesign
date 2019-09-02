package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/10 10:38
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultData<T> implements Serializable {
    private Integer code;//返回码
    private String msg;//返回消息
    private T data;//数据
}
