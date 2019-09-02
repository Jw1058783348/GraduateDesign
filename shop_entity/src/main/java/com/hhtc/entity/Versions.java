package com.hhtc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/3/22 18:40
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Versions implements Serializable {
    private Integer id;
    private String gversion;
    private Integer gprice;
    private Integer gid;
}
