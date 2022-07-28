package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//获取访问数
public class AccessDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //访问数
    private String accessNumber;
    //日期
    private String dateTime;
}
