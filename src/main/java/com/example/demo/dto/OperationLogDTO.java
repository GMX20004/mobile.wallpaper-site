package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//获取访问数
public class OperationLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //操作人
    private int userId;
    //操作行动
    private String action;
    //操作时间
    private String operatingTime;
    //操作地址
    private String ipAddress;
}
