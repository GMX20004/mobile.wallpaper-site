package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//获取访问数
public class AnnouncementDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //类型 -1关闭公告0标题1开始时间2结束时间3内容
    private int type;
    //内容
    private String content;
}
