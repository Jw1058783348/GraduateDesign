package com.hhtc.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Author JH
 * @Time 2019/5/10 10:09
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    private String token;

    private String phone;
}
