package com.renjie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Fan
 * @Date 2020/11/16
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
