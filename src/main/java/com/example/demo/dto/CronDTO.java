package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;

@Data
//定时任务
public class CronDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //定时任务链接
    private String cronLink;
    //链接类型
    private String linkType;
    //链接值
    private String linkValue;
    //定时时间
    private String regularTime;
    //定时任务状态 0停用 1正常
    private int cron_state;
}
