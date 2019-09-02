package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/3/20 14:40
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Types implements Serializable {
    private Integer id;
    private String tname;
}
