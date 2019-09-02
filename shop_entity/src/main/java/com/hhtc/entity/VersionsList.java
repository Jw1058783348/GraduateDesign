package com.hhtc.entity;

import lombok.*;

import java.util.List;

/**
 * @Author JH
 * @Time 2019/3/23 10:43
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VersionsList {
    private List<Versions> versionsList;//用来接收前端的多个版本信息
}
