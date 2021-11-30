package com.example.demo.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
//用户表
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;
    //邮件
    private String email;
    //昵称
    private String name;
    //个人说明
    private String instructions;
    //性别
    private String sex;
    //获赞数
    private int praise;
    //粉丝数
    private int fans;
    //关注数
    private int focusOn;
    //投稿数
    private int contribute;
    //最近登入时间
    private String recentLogin;
    //创建时间
    private String creationTime;
    //用户唯一编码
    private String userId;
    //状态
    private int start;
    //头像
    private String headPortrait;
}
